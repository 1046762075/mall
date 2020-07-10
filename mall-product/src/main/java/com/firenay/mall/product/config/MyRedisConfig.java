package com.firenay.mall.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: MyRedisConfig</p>
 * Description：
 * date：2020/6/11 12:33
 */
@Configuration
public class MyRedisConfig {

	@Value("${ipAddr}")
	private String ipAddr;

	@Bean(destroyMethod = "shutdown")
	public RedissonClient redisson() {
		Config config = new Config();
		// 创建单例模式的配置
		config.useSingleServer().setAddress("redis://" + ipAddr + ":6379");
		return Redisson.create(config);
	}
}
