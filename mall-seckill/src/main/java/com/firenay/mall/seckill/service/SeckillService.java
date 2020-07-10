package com.firenay.mall.seckill.service;

import com.firenay.mall.seckill.to.SeckillSkuRedisTo;

import java.util.List;

/**
 * <p>Title: SeckillService</p>
 * Description：
 * date：2020/7/6 17:32
 */
public interface SeckillService {

	void uploadSeckillSkuLatest3Day();

	/**
	 * 获取当前可以参与秒杀的商品信息
	 */
	List<SeckillSkuRedisTo> getCurrentSeckillSkus();

	SeckillSkuRedisTo getSkuSeckillInfo(Long skuId);

	String kill(String killId, String key, Integer num);
}
