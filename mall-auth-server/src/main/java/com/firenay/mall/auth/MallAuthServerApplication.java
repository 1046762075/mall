package com.firenay.mall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

// 整合redis作为session存储

/**
 *	原理：首先导入 RedisHttpSessionConfiguration 配置类
 *			这个类在容器中添加了 RedisIndexedSessionRepository 组件	这个组件就是用来存让装饰后的session的bean
 *				相当于是redis操作session的dao[增删改查]
 *			同时它又继承了 SpringHttpSessionConfiguration 配置类 这个类对包装后的Cookie进行了初始化
 *			当服务启动的时候 这个类会自动注入	SessionRepository [我们自己写的配置文件就实现了这个接口]
 *				这个组件又在容器中注册了一个 springSessionRepositoryFilter 过滤器	;这个过滤器将原生的request、response、session包装成 SessionRepositoryRequestWrapper
 *				以后对session的操作都将在redis进行了
 */
@EnableRedisHttpSession
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MallAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallAuthServerApplication.class, args);
	}
}
