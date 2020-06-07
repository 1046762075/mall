package com.firenay.mall.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MallThirdPartyApplication {

//	http://127.0.0.1:30000/third/party/oss/policy			获取签名数据
//	http://127.0.0.1:88/api/third/party/oss/policy
	public static void main(String[] args) {
		SpringApplication.run(MallThirdPartyApplication.class, args);
	}

}
