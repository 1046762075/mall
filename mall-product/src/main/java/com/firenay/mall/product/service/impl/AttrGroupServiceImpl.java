package com.firenay.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firenay.common.utils.PageUtils;
import com.firenay.common.utils.Query;
import com.firenay.mall.product.dao.AttrGroupDao;
import com.firenay.mall.product.entity.AttrEntity;
import com.firenay.mall.product.entity.AttrGroupEntity;
import com.firenay.mall.product.service.AttrGroupService;
import com.firenay.mall.product.service.AttrService;
import com.firenay.mall.product.vo.AttrGroupWithAttrsVo;
import com.firenay.mall.product.vo.SpuItemAttrGroup;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

	@Autowired
	private AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
		String key = (String) params.get("key");
		QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
		if(!StringUtils.isEmpty(key)){
			wrapper.and((obj)->
				obj.eq("attr_group_id", key).or().like("attr_group_name", key)
			);
		}
		if(catelogId == 0){
			IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params),
					wrapper);
			return new PageUtils(page);
		}else {
			wrapper.eq("catelog_id",catelogId);
			IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params),
					wrapper);
			return new PageUtils(page);
		}
	}

	/**
	 * 根据分类id 查出所有的分组以及这些组里边的属性
	 */
	@Override
	public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrByCatelogId(Long catelogId) {

		// 1.查询这个品牌id下所有分组
		List<AttrGroupEntity> attrGroupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));

		// 2.查询所有属性
		List<AttrGroupWithAttrsVo> collect = attrGroupEntities.stream().map(group ->{
			// 先对拷分组数据
			AttrGroupWithAttrsVo attrVo = new AttrGroupWithAttrsVo();
			BeanUtils.copyProperties(group, attrVo);
			// 按照分组id查询所有关联属性并封装到vo
			List<AttrEntity> attrs = attrService.getRelationAttr(attrVo.getAttrGroupId());
			attrVo.setAttrs(attrs);
			return attrVo;
		}).collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<SpuItemAttrGroup> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId) {

		// 1.出当前Spu对应的所有属性的分组信息 以及当前分组下所有属性对应的值
		// 1.1 查询所有分组
		AttrGroupDao baseMapper = this.getBaseMapper();

		return baseMapper.getAttrGroupWithAttrsBySpuId(spuId, catalogId);
	}
}