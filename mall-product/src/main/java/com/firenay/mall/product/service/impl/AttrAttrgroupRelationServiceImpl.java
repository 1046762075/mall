package com.firenay.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firenay.common.utils.PageUtils;
import com.firenay.common.utils.Query;
import com.firenay.mall.product.dao.AttrAttrgroupRelationDao;
import com.firenay.mall.product.entity.AttrAttrgroupRelationEntity;
import com.firenay.mall.product.service.AttrAttrgroupRelationService;
import com.firenay.mall.product.vo.AttrGroupRelationVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public void saveBatch(List<AttrGroupRelationVo> vos) {
    	// 对拷数据 然后批量保存
		List<AttrAttrgroupRelationEntity> entities = vos.stream().map(item -> {
			AttrAttrgroupRelationEntity entity = new AttrAttrgroupRelationEntity();
			BeanUtils.copyProperties(item, entity);
			entity.setAttrSort(0);
			return entity;
		}).collect(Collectors.toList());
		this.saveBatch(entities);
	}
}