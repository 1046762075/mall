package com.firenay.mall.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>Title: MyWebConfig</p>
 * Description：页面映射
 * date：2020/6/25 13:36
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {


	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

		registry.addViewController("/reg.html").setViewName("reg");
	}
}
