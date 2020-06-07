package com.firenay.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.firenay.common.utils.PageUtils;
import com.firenay.mall.product.entity.BrandEntity;
import com.firenay.mall.product.entity.CategoryBrandRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2020-05-31 17:06:04
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

	void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

	void updateBrand(Long brandId, String name);

	void updateCategory(Long catId, String name);

	List<BrandEntity> getBrandsByCatId(Long catId);
}

