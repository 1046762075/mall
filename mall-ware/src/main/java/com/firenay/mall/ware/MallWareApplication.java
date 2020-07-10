package com.firenay.mall.ware;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>Title: MallWareApplication</p>
 * Description：仓储模块		第一次启动报了RabbitMQ相关的error别慌 这是RabbitMQ正在创建队列 、交换机、绑定信息 还没刷新导致的
 * date：2020/6/6 20:49
 */

@EnableRabbit
@EnableFeignClients
@EnableTransactionManagement
@EnableDiscoveryClient
@MapperScan("com.firenay.mall.ware.dao")
@SpringBootApplication
public class MallWareApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallWareApplication.class, args);
	}

	//引入分页插件
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		// 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        paginationInterceptor.setOverflow(true);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setLimit(1000);
		return paginationInterceptor;
	}

//	@Bean
//	public DataSource dataSource(DataSourceProperties dataSourceProperties){
//		HikariDataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//		if(StringUtils.hasText(dataSourceProperties.getName())){
//			dataSource.setPoolName(dataSourceProperties.getName());
//		}
//		return new DataSourceProxy(dataSource);
//	}
}
