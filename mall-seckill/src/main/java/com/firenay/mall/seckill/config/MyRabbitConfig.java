package com.firenay.mall.seckill.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: MyRabbitConfig</p>
 * Description：配置序列化方式
 * date：2020/7/9 20:05
 */
@Configuration
public class MyRabbitConfig {

	@Bean
	public MessageConverter messageConverter(){
		return new Jackson2JsonMessageConverter();
	}
}
