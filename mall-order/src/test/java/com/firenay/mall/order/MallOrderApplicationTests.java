package com.firenay.mall.order;

import com.firenay.mall.order.entity.OrderEntity;
import com.firenay.mall.order.entity.OrderItemEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Slf4j
@SpringBootTest
public class MallOrderApplicationTests {

	@Resource
	private AmqpAdmin amqpAdmin;

	@Resource
	private RabbitTemplate rabbitTemplate;

	@Value("${myRabbitmq.queue}")
	private String queue;

	@Value("${myRabbitmq.exchange}")
	private String exchange;

	@Value("${myRabbitmq.routeKey}")
	private String routeKey;

	/**
	 * 发送的消息是一个对象 必须实现序列化
	 */
	@Test
	public void sendMessageTest(){
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
		for (int i = 0; i < 10; i++) {
			if(i % 2 == 0){
				entity.setReceiverName("FIRE-" + i);
				rabbitTemplate.convertAndSend(this.exchange, this.routeKey, entity, new CorrelationData(UUID.randomUUID().toString().replace("-","")));
			}else {
				orderEntity.setOrderSn("mall-" + i);
				rabbitTemplate.convertAndSend(this.exchange, this.routeKey, orderEntity, new CorrelationData(UUID.randomUUID().toString().replace("-","")));
			}
			log.info("\n路由键：" + this.routeKey + "的消息发送成功");
		}
	}

	/**
	 * 		  目的地					目的地类型				交换机				路由键
	 * String destination, DestinationType destinationType, String exchange, String routingKey,
	 *                        @Nullable Map<String, Object> arguments
	 */

	@Test
	public void bindIng() {

		Binding binding = new Binding(this.queue, Binding.DestinationType.QUEUE, this.exchange, this.routeKey, null);
		amqpAdmin.declareBinding(binding);
		log.info("\n[" + binding.getExchange() + "] 与 [" + binding.getDestination() + "] 绑定成功");
	}

	@Test
	public void createQueue() {

		// 持久化：true  是否排他：false 是否自动删除：false
		Queue queue = new Queue(this.queue, true, false, false);

		amqpAdmin.declareQueue(queue);
		log.info("\nQueue [" + queue.getName() + "] 创建成功");
	}

	/**
	 * 1、创建Exchange Queue Binding
	 * 2、发送消息
	 */
	@Test
	public void createExchange() {
		DirectExchange exchange = new DirectExchange(this.exchange, true, false);

		amqpAdmin.declareExchange(exchange);
		log.info("\nExchange [" + exchange.getName() + "] 创建成功");
	}

}
