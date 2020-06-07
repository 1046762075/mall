package com.firenay.mall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.firenay.mall.member.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class MallMemberApplication {

//	http://localhost:8000/member/member/coupons
	public static void main(String[] args) {
		SpringApplication.run(MallMemberApplication.class, args);
	}

}
