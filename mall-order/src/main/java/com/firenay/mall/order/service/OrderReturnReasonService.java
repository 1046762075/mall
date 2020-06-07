package com.firenay.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.firenay.common.utils.PageUtils;
import com.firenay.mall.order.entity.OrderReturnReasonEntity;

import java.util.Map;

/**
 * 退货原因
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2020-05-30 00:54:56
 */
public interface OrderReturnReasonService extends IService<OrderReturnReasonEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

