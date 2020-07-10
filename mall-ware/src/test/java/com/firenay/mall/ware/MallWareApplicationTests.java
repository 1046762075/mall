package com.firenay.mall.ware;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class MallWareApplicationTests {

	@Resource
	private AmqpAdmin amqpAdmin;

	/**
	 * 订单服务启动不了 运行这个
	 */
	@Test
	public void run(){
		Map<String ,Object> arguments = new HashMap<>();
		arguments.put("x-dead-letter-exchange", "order-event-exchange");
		arguments.put("x-dead-letter-routing-key", "order.release.order");
		arguments.put("x-message-ttl", 900000);
		Queue queue = new Queue("order.delay.queue", true, false, false, arguments);
		amqpAdmin.declareQueue(queue);

		queue = new Queue("order.release.order.queue", true, false, false);
		amqpAdmin.declareQueue(queue);

		Binding binding = new Binding("order.delay.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.create.order", null);
		amqpAdmin.declareBinding(binding);

		binding = new Binding("order.release.order.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.release.order", null);
		amqpAdmin.declareBinding(binding);

		binding = new Binding("stock.release.stock.queue", Binding.DestinationType.QUEUE, "order-event-exchange",  "order.release.other.#", null);
		amqpAdmin.declareBinding(binding);

		amqpAdmin.declareQueue(new Queue("order.seckill.order.queue", true, false, false));

		amqpAdmin.declareBinding(new Binding("order.seckill.order.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.seckill.order", null));
	}

	@Test
	public void orderDelayQueue(){
		Map<String ,Object> arguments = new HashMap<>();
		arguments.put("x-dead-letter-exchange", "order-event-exchange");
		arguments.put("x-dead-letter-routing-key", "order.release.order");
		arguments.put("x-message-ttl", 900000);
		Queue queue = new Queue("order.delay.queue", true, false, false, arguments);
		amqpAdmin.declareQueue(queue);
	}

	@Test
	public void orderReleaseOrderQueue(){
		Queue queue = new Queue("order.release.order.queue", true, false, false);
		amqpAdmin.declareQueue(queue);
	}


	/**
	 * String name, boolean durable, boolean autoDelete, Map<String, Object> arguments
	 * @return
	 */
	@Test
	public void orderEventExchange(){

		TopicExchange topicExchange = new TopicExchange("order-event-exchange", true, false);
		amqpAdmin.declareExchange(topicExchange);
	}

	/**
	 * String destination, DestinationType destinationType, String exchange, String routingKey, @Nullable Map<String, Object> arguments
	 */
	@Bean
	public void orderCreateOrderBinding(){

		Binding binding = new Binding("order.delay.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.create.order", null);
		amqpAdmin.declareBinding(binding);
	}

	@Test
	public void orderReleaseOrderBinding(){

		Binding binding = new Binding("order.release.order.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.release.order", null);
		amqpAdmin.declareBinding(binding);
	}

	/**
	 * 订单释放直接和库存释放进行绑定
	 */
	@Test
	public void orderReleaseOtherBinding(){

		Binding binding = new Binding("stock.release.stock.queue", Binding.DestinationType.QUEUE, "order-event-exchange",  "order.release.other.#", null);
		amqpAdmin.declareBinding(binding);
	}

	@Test
	public void orderSecKillQueue(){
		amqpAdmin.declareQueue(new Queue("order.seckill.order.queue", true, false, false));
	}

	@Test
	public void orderSecKillQueueBinding(){
		amqpAdmin.declareBinding(new Binding("order.seckill.order.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.seckill.order", null));
	}
}
