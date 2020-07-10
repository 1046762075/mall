package com.firenay.mall.member.config;

import com.firenay.mall.member.interceptor.LoginUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * <p>Title: MemberWebConfig</p>
 * Description：
 * date：2020/7/4 22:36
 */
@Configuration
public class MemberWebConfig implements WebMvcConfigurer {

	@Autowired
	private LoginUserInterceptor loginUserInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginUserInterceptor).addPathPatterns("/**").excludePathPatterns(Arrays.asList("/login","/member/member/login"));
	}
}
