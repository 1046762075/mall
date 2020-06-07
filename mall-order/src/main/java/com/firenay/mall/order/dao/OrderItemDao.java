package com.firenay.mall.order.dao;

import com.firenay.mall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2020-05-30 00:54:56
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
