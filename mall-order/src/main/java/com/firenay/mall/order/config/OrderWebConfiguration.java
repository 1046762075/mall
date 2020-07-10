package com.firenay.mall.order.config;

import com.firenay.mall.order.interceptor.LoginUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>Title: OrderWebConfiguration</p>
 * Description：
 * date：2020/6/29 22:41
 */
@Configuration
public class OrderWebConfiguration implements WebMvcConfigurer {

	@Autowired
	private LoginUserInterceptor loginUserInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 放行支付宝回调请求
		registry.addInterceptor(loginUserInterceptor).addPathPatterns("/**").excludePathPatterns("/payed/notify");
	}
}
