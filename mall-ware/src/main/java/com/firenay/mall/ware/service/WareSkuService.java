package com.firenay.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.firenay.common.to.es.SkuHasStockVo;
import com.firenay.common.to.mq.OrderTo;
import com.firenay.common.to.mq.StockLockedTo;
import com.firenay.common.utils.PageUtils;
import com.firenay.mall.ware.entity.WareSkuEntity;
import com.firenay.mall.ware.vo.WareSkuLockVo;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2020-06-06 21:09:28
 */
public interface WareSkuService extends IService<WareSkuEntity> {

	void unlockStock(StockLockedTo to);

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 保存库存的时候顺便查到商品价格
	 */
	double addStock(Long skuId, Long wareId, Integer skuNum);

	/**
	 * 查询是否有库存
	 */
	List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);

	/**
	 * 为某个订单锁定库存
	 */
	Boolean orderLockStock(WareSkuLockVo vo);

	/**
	 * 由于订单超时而自动释放订单之后来解锁库存
	 */
	void unlockStock(OrderTo to);
}

