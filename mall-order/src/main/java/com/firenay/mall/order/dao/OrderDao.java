package com.firenay.mall.order.dao;

import com.firenay.mall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 订单
 * 
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2020-05-30 00:54:56
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

	void updateOrderStatus(@Param("orderSn") String orderSn, @Param("code") Integer code);
}
