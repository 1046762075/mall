package com.firenay.mall.order.controller;

import com.firenay.mall.order.entity.OrderEntity;
import com.firenay.mall.order.entity.OrderItemEntity;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * <p>Title: RabbitController</p>
 * Description：
 * date：2020/6/29 15:50
 */
@RestController
public class RabbitController {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${myRabbitmq.exchange}")
	private String exchange;

	@Value("${myRabbitmq.routeKey}")
	private String routeKey;


	@GetMapping("/sendMQ")
	public String sendMQ(@RequestParam(value = "num", required = false, defaultValue = "10") Integer num){

		OrderEntity entity = new OrderEntity();
		entity.setId(1L);
		entity.setCommentTime(new Date());
		entity.setCreateTime(new Date());
		entity.setConfirmStatus(0);
		entity.setAutoConfirmDay(1);
		entity.setGrowth(1);
		entity.setMemberId(12L);

		OrderItemEntity orderEntity = new OrderItemEntity();
		orderEntity.setCategoryId(225L);
		orderEntity.setId(1L);
		orderEntity.setOrderSn("mall");
		orderEntity.setSpuName("华为");
		for (int i = 0; i < num; i++) {
			if(i % 2 == 0){
				entity.setReceiverName("FIRE-" + i);
				rabbitTemplate.convertAndSend(this.exchange, this.routeKey, entity, new CorrelationData(UUID.randomUUID().toString().replace("-","")));
			}else {
				orderEntity.setOrderSn("mall-" + i);
				rabbitTemplate.convertAndSend(this.exchange, this.routeKey, orderEntity, new CorrelationData(UUID.randomUUID().toString().replace("-","")));
				// 测试消息发送失败
//				rabbitTemplate.convertAndSend(this.exchange, this.routeKey + "test", orderEntity);
			}
		}
		return "ok";
	}
}
