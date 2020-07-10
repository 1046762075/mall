package com.firenay.mall.product.feign;

import com.firenay.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>Title: WareFeignService</p>
 * Description：
 * date：2020/6/8 20:26
 */
@FeignClient("mall-ware")
public interface WareFeignService {

	/**
	 * 修改真个系统的 R 带上泛型
	 */
	@PostMapping("/ware/waresku/hasStock")
//	List<SkuHasStockVo> getSkuHasStock(@RequestBody List<Long> SkuIds);
	R getSkuHasStock(@RequestBody List<Long> SkuIds);
}
