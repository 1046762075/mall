package com.firenay.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firenay.common.utils.PageUtils;
import com.firenay.common.utils.Query;
import com.firenay.mall.product.dao.ProductAttrValueDao;
import com.firenay.mall.product.entity.ProductAttrValueEntity;
import com.firenay.mall.product.service.ProductAttrValueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public void saveProductAttr(List<ProductAttrValueEntity> collect) {
		this.saveBatch(collect);
	}

	@Override
	public List<ProductAttrValueEntity> baseAttrListForSpu(Long spuId) {
		return this.baseMapper.selectList(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));
	}

	@Transactional
	@Override
	public void updateSpuAttr(Long spuId, List<ProductAttrValueEntity> entities) {
		// 1.删除 spuId 之前对应的属性
		this.baseMapper.delete(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));

		// 2.保存页面传过来的数据
		List<ProductAttrValueEntity> collect = entities.stream().map(entity -> {
			entity.setSpuId(spuId);
			entity.setAttrSort(0);
			return entity;
		}).collect(Collectors.toList());
		this.saveBatch(collect);
	}
}