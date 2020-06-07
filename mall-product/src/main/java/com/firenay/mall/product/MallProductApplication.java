package com.firenay.mall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.firenay.mall.product.feign")
@EnableDiscoveryClient
@MapperScan("com.firenay.mall.product.dao")
@SpringBootApplication
public class MallProductApplication {

//	http://localhost:10001/product/category/list/tree
//	http://127.0.0.1:88/api/product/category/list/tree
//	http://127.0.0.1:88/api/product/attrgroup/list/1?page=1&key=aa

//	JSR303最终测试：POSTman :{"name":"aaa","logo":"https://github.com/1046762075","sort":0,"firstLetter":"d","showStatus":0}
	public static void main(String[] args) {
		SpringApplication.run(MallProductApplication.class, args);
	}
}
