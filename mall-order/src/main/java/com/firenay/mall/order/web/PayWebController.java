package com.firenay.mall.order.web;

import com.alipay.api.AlipayApiException;
import com.firenay.mall.order.config.AlipayTemplate;
import com.firenay.mall.order.service.OrderService;
import com.firenay.mall.order.vo.PayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>Title: PayWebController</p>
 * Description：
 * date：2020/7/4 19:59
 */
@Controller
public class PayWebController {

	@Autowired
	private AlipayTemplate alipayTemplate;

	@Autowired
	private OrderService orderService;

	/**
	 * 告诉浏览器我们会返回一个html页面
	 */
	@ResponseBody
	@GetMapping(value = "/payOrder", produces = "text/html")
	public String payOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {

		PayVo payVo = orderService.getOrderPay(orderSn);
		return alipayTemplate.pay(payVo);
	}
}
