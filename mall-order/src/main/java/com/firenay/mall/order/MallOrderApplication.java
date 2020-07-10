package com.firenay.mall.order;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


/**
 * 引入AMQP场景启动器
 *
 *  监听消息：RabbitListener: 监听队列 [这个队列必须存在]
 *
 *  同一个对象事务方法默认失效, 原因 绕过了代理对象 事务使用代理对象来控制的
 *  解决：使用代理对象来调用事务方法
 *  	1. 引入 spring-boot-starter-aop 它帮我们引入了aspectj
 *  	2. @EnableAspectJAutoProxy(exposeProxy = true) [对外暴露代理对象] 开启动态代理功能 而不是jdk默认的动态代理 即使没有接口也可以创建动态代理
 *		3. 本类互调用代理对象		AopContext
 *
 * 	Seata控制分布式事务
 * 		1. 每一个微服务必须创建undo_log
 * 		2. 安装事务协调器：https://github.com/seata/seata/releases		1.0.0版本
 * 		3. 解压并启动seata-server
 * 			registry.conf 注册中心配置 修改registry type=nacos
 * 		4. 每个想要使用分布式事务的微服务都要用seata DataSourceProxy代理自己的数据源
 * 		5. 每个微服务都不必须导入
 * 			修改file.conf：vgroup_mapping.{当前应用的名字}-fescar-service-group
 * 	    6. 启动测试分布式事务
 * 	   	7. 给分布式大事务入口标注 @GlobalTransactional
 * 	   	8. 每一个远程的小事务用 @Transactional
 *
 * 	   	第一次启动报了RabbitMQ相关的error别慌 这是RabbitMQ正在创建队列 、交换机、绑定信息 还没刷新导致的
 *
 * 	   		启动报错去ware服务的测试类运行所有代码
 */
//@EnableAspectJAutoProxy(exposeProxy = true)
@EnableFeignClients
@EnableRedisHttpSession
@EnableDiscoveryClient
@EnableRabbit
@SpringBootApplication
public class MallOrderApplication {

//	http://127.0.0.1:9000/sendMQ?num=10
//	http://order.glmall.com/test/createOrder
	public static void main(String[] args) {
		SpringApplication.run(MallOrderApplication.class, args);
	}
}
