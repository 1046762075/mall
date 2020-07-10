package com.firenay.mall.order.feign;

import com.firenay.mall.order.vo.OrderItemVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * <p>Title: CartFeignService</p>
 * Description：
 * date：2020/6/30 18:08
 */
@FeignClient("mall-cart")
public interface CartFeignService {

	@GetMapping("/currentUserCartItems")
	List<OrderItemVo> getCurrentUserCartItems();

}
