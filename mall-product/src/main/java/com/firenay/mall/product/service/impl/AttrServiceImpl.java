package com.firenay.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firenay.common.constant.ProductConstant;
import com.firenay.common.utils.PageUtils;
import com.firenay.common.utils.Query;
import com.firenay.mall.product.dao.AttrAttrgroupRelationDao;
import com.firenay.mall.product.dao.AttrDao;
import com.firenay.mall.product.dao.AttrGroupDao;
import com.firenay.mall.product.dao.CategoryDao;
import com.firenay.mall.product.entity.AttrAttrgroupRelationEntity;
import com.firenay.mall.product.entity.AttrEntity;
import com.firenay.mall.product.entity.AttrGroupEntity;
import com.firenay.mall.product.entity.CategoryEntity;
import com.firenay.mall.product.service.AttrService;
import com.firenay.mall.product.service.CategoryService;
import com.firenay.mall.product.vo.AttrGroupRelationVo;
import com.firenay.mall.product.vo.AttrRespVo;
import com.firenay.mall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

	@Resource
	private AttrAttrgroupRelationDao relationDao;

	@Resource
	private AttrGroupDao attrGroupDao;

	@Resource
	private CategoryDao categoryDao;

	@Resource
	private CategoryService categoryService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<AttrEntity> page = this.page(
				new Query<AttrEntity>().getPage(params),
				new QueryWrapper<>()
		);
		return new PageUtils(page);
	}

	@Transactional
	@Override
	public void saveAttr(AttrVo attrVo) {
		AttrEntity attrEntity = new AttrEntity();
		BeanUtils.copyProperties(attrVo, attrEntity);
		//1、保存基本数据
		this.save(attrEntity);
		//2、保存关联关系
		if (attrVo.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && attrVo.getAttrGroupId() != null) {
			AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
			relationEntity.setAttrGroupId(attrVo.getAttrGroupId());
			relationEntity.setAttrId(attrEntity.getAttrId());
			relationEntity.setAttrSort(0);
			relationDao.insert(relationEntity);
		}
	}

	/**
	 * 规格参数的分页模糊查询
	 */
	@Override
	public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType) {
		QueryWrapper<AttrEntity> waWrapper = new QueryWrapper<AttrEntity>().eq("attr_type", "base".equalsIgnoreCase(attrType)?ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode():ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());

		if (catelogId != ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode()) {
			// 如果是 base 就是基本属性 插入1 否则插入0
			waWrapper.eq("catelog_id", catelogId);
		}
		String key = (String) params.get("key");
		if (!StringUtils.isEmpty(key)) {
			waWrapper.and((w) -> {
				w.eq("attr_id", key).or().like("attr_name", key);
			});
		}
		IPage<AttrEntity> page = this.page(
				new Query<AttrEntity>().getPage(params),
				waWrapper
		);
		PageUtils pageUtils = new PageUtils(page);
		// 先查询三级分类名字、分组名字 再封装
		List<AttrEntity> records = page.getRecords();
		// attrRespVos 就是最终封装好的Vo
		List<AttrRespVo> attrRespVos = records.stream().map((attrEntity) -> {
			AttrRespVo attrRespVo = new AttrRespVo();
			BeanUtils.copyProperties(attrEntity, attrRespVo);
			// 1.设置分类和分组的名字  先获取中间表对象  给attrRespVo 封装分组名字
			if("base".equalsIgnoreCase(attrType)){
				// attr的关联关系 当它没有分组的时候就不保存了
				AttrAttrgroupRelationEntity entity = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
				if (entity != null && entity.getAttrGroupId() != null) {
					AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(entity);
					attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
				}
			}
			// 2.查询分类id 给attrRespVo 封装三级分类名字
			CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
			if (categoryEntity != null) {
				attrRespVo.setCatelogName(categoryEntity.getName());
			}
			return attrRespVo;
		}).collect(Collectors.toList());
		pageUtils.setList(attrRespVos);
		return pageUtils;
	}

	@Cacheable(value = "attr", key = "'attrinfo:' + #root.args[0]")
	@Transactional
	@Override
	public AttrRespVo getAttrInfo(Long attrId) {
		AttrRespVo respVo = new AttrRespVo();
		AttrEntity attrEntity = this.getById(attrId);
		BeanUtils.copyProperties(attrEntity, respVo);

		// 基本类型才进行修改
		if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
			//1、设置分组信息
			AttrAttrgroupRelationEntity attrgroupRelation = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
			if (attrgroupRelation != null) {
				respVo.setAttrGroupId(attrgroupRelation.getAttrGroupId());
				AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupRelation.getAttrGroupId());
				if (attrGroupEntity != null) {
					respVo.setGroupName(attrGroupEntity.getAttrGroupName());
				}
			}
		}

		//2、设置分类信息
		Long catelogId = attrEntity.getCatelogId();
		Long[] catelogPath = categoryService.findCateLogPath(catelogId);
		respVo.setCatelogPath(catelogPath);

		CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
		if (categoryEntity != null) {
			respVo.setCatelogName(categoryEntity.getName());
		}
		return respVo;
	}

	/**
	 * 更改规格参数：参数名、参数id、参数、状态的一一对应
	 */
	@Transactional
	@Override
	public void updateAttr(AttrVo attrVo) {
		AttrEntity attrEntity = new AttrEntity();
		BeanUtils.copyProperties(attrVo, attrEntity);
		this.updateById(attrEntity);

		// 基本类型才进行修改
		if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
			// 修改分组关联
			AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();

			relationEntity.setAttrGroupId(attrVo.getAttrGroupId());
			relationEntity.setAttrId(attrVo.getAttrId());
			// 查询 attr_id 在 pms_attr_attrgroup_relation 表中是否已经存在 不存在返回0 表示这是添加 反之返回1 为修改 [这里的修改可以修复之前没有设置上的属性]
			Integer count = relationDao.selectCount(new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrVo.getAttrId()));
			if(count > 0){
				relationDao.update(relationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrVo.getAttrId()));
			}else {
				relationDao.insert(relationEntity);
			}
		}
	}

	/**
	 * 根据分组id查找关联属性
	 */
	@Override
	public List<AttrEntity> getRelationAttr(Long attrgroupId) {
		List<AttrAttrgroupRelationEntity> entities = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId));
		List<Long> attrIds = entities.stream().map((attr) -> attr.getAttrId()).collect(Collectors.toList());
		// 根据这个属性查询到的id可能是空的
		if(attrIds == null || attrIds.size() == 0){
			return null;
		}
		return this.listByIds(attrIds);
	}

	/**
	 * 批量删除分组关联关系
	 */
	@Override
	public void deleteRelation(AttrGroupRelationVo[] vos) {
		// 将页面收集的数据拷到 AttrAttrgroupRelationEntity
		List<AttrAttrgroupRelationEntity> entities = Arrays.asList(vos).stream().map((v) -> {
			AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
			BeanUtils.copyProperties(v, relationEntity);
			return relationEntity;
		}).collect(Collectors.toList());
		relationDao.deleteBatchRelation(entities);
	}

	/**
	 * 获取当前分组没有关联的属性
	 */
	@Override
	public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {
		//1、当前分组只能关联自己所属的分类里面的所有属性
		AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
		Long catelogId = attrGroupEntity.getCatelogId();
		// 2、当前分组只能别的分组没有引用的属性																									并且这个分组的id不是我当前正在查的id
		//2.1)、当前分类下的其他分组
		List<AttrGroupEntity> group = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
		// 得到当前分类下面的所有分组id
		List<Long> collect = group.stream().map(item -> {
			return item.getAttrGroupId();
		}).collect(Collectors.toList());

		//2.2)、查询这些分组关联的属性
		List<AttrAttrgroupRelationEntity> groupId = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", collect));
		// 再次获取跟这些分组有关的属性id的集合
		List<Long> attrIds = groupId.stream().map(item -> {
			return item.getAttrId();
		}).collect(Collectors.toList());

		//2.3)、从当前分类的所有属性中移除这些属性；[因这些分组已经存在被选了 就不用再显示了]
		QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId).eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
		if(attrIds != null && attrIds.size() > 0){
			wrapper.notIn("attr_id", attrIds);
		}
		// 当搜索框中有key并且不为空的时候 进行模糊查询
		String key = (String) params.get("key");
		if(!StringUtils.isEmpty(key)){
			wrapper.and((w)->{
				w.eq("attr_id",key).or().like("attr_name",key);
			});
		}
		// 将最后返回的结果进行封装
		IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);

		PageUtils pageUtils = new PageUtils(page);
		return pageUtils;
	}

	/**
	 * SELECT attr_id FROM `pms_attr` WHERE attr_id IN (?) AND search_type = 1
	 */
	@Override
	public List<Long> selectSearchAttrIds(List<Long> attrIds) {
		return baseMapper.selectSearchAttrIds(attrIds);
	}
}