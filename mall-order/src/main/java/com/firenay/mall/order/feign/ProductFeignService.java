package com.firenay.mall.order.feign;

import com.firenay.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>Title: ProductFeignService</p>
 * Description：
 * date：2020/7/2 0:43
 */
@FeignClient("mall-product")
public interface ProductFeignService {

	@GetMapping("/product/spuinfo/skuId/{id}")
	R getSkuInfoBySkuId(@PathVariable("id") Long skuId);
}
