package com.firenay.mall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 任何配置文件都可以放在配置中心
 *  bootstrap.properties 来指定加载那些配置文件即可
 */
@EnableDiscoveryClient
@SpringBootApplication
public class MallCouponApplication {

//	http://localhost:7000/coupon/coupon/test
//	http://localhost:7000/coupon/coupon/list
//	http://localhost:7000/coupon/coupon/member/list
//	http://localhost:8000/member/member/coupons
	public static void main(String[] args) {
		SpringApplication.run(MallCouponApplication.class, args);
	}

}
