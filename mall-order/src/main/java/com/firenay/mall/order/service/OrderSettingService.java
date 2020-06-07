package com.firenay.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.firenay.common.utils.PageUtils;
import com.firenay.mall.order.entity.OrderSettingEntity;

import java.util.Map;

/**
 * 订单配置信息
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2020-05-30 00:54:56
 */
public interface OrderSettingService extends IService<OrderSettingEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

