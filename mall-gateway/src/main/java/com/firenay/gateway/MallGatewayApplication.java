package com.firenay.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MallGatewayApplication {

//	http://127.0.0.1:88/api/third/party/oss/policy
	public static void main(String[] args) {
		SpringApplication.run(MallGatewayApplication.class, args);
	}
}
