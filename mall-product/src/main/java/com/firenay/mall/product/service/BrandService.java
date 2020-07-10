package com.firenay.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.firenay.common.utils.PageUtils;
import com.firenay.mall.product.entity.BrandEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2020-05-31 17:06:04
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

	/**
	 * 当品牌进行更新的时候 保证关联表的数据也需要进行更新
	 */
	void updateDetail(BrandEntity brand);

	List<BrandEntity> getBrandByIds(List<Long> brandIds);
}

