package com.firenay.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firenay.common.utils.PageUtils;
import com.firenay.common.utils.Query;
import com.firenay.mall.order.dao.OrderItemDao;
import com.firenay.mall.order.entity.OrderEntity;
import com.firenay.mall.order.entity.OrderItemEntity;
import com.firenay.mall.order.service.OrderItemService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@RabbitListener(queues = {"${myRabbitmq.queue}"})
@Service("orderItemService")
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OrderItemEntity> implements OrderItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderItemEntity> page = this.page(
                new Query<OrderItemEntity>().getPage(params),
                new QueryWrapper<OrderItemEntity>()
        );

        return new PageUtils(page);
    }

	/**
	 * 	1.Message message: 原生消息类型 详细信息
	 * 	2.T<发送消息的类型> OrderEntity orderEntity  [Spring自动帮我们转换]
	 * 	3.Channel channel: 当前传输数据的通道
	 *
	 * 	// 同一个消息只能被一个人收到
	 *
	 *
	 * 	@RabbitListener： 只能标注在类、方法上 配合 @RabbitHandler
	 * 	@RabbitHandler: 只能标注在方法上	[重载区分不同的消息]
	 */

	@RabbitHandler
    public void receiveMessageA(Message message, OrderEntity orderEntity, Channel channel){
		System.out.println("接受到消息: " + message + "\n内容：" + orderEntity);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) { }
		// 这个是一个数字 通道内自增
		long deliveryTag = message.getMessageProperties().getDeliveryTag();
		try {
			// 只签收当前货物 不批量签收
			channel.basicAck(deliveryTag, false);

			// deliveryTag: 货物的标签  	multiple: 是否批量拒收 requeue: 是否重新入队
//			channel.basicNack(deliveryTag, false,true);
//			批量拒绝
//			channel.basicReject();
		} catch (IOException e) {
			System.out.println("网络中断");
		}
		System.out.println(orderEntity.getReceiverName() + " 消息处理完成");
	}

	@RabbitHandler
	public void receiveMessageB(Message message, OrderItemEntity orderEntity, Channel channel){
		System.out.println("接受到消息: " + message + "\n内容：" + orderEntity);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) { }
		long deliveryTag = message.getMessageProperties().getDeliveryTag();
		try {
			channel.basicAck(deliveryTag, false);
		} catch (IOException e) {
			System.out.println("网络中断");
		}
		System.out.println(orderEntity.getOrderSn() + " 消息处理完成");
	}
}