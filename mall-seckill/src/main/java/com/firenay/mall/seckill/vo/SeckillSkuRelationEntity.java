package com.firenay.mall.seckill.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>Title: SeckillSkuRelationEntity</p>
 * Description：
 * date：2020/7/6 18:50
 */
@Data
public class SeckillSkuRelationEntity {
	private Long id;
	/**
	 * 活动id
	 */
	private Long promotionId;
	/**
	 * 活动场次id
	 */
	private Long promotionSessionId;
	/**
	 * 商品id
	 */
	private Long skuId;
	/**
	 * 秒杀价格
	 */
	private BigDecimal seckillPrice;
	/**
	 * 秒杀总量
	 */
	private BigDecimal seckillCount;
	/**
	 * 每人限购数量
	 */
	private BigDecimal seckillLimit;
	/**
	 * 排序
	 */
	private Integer seckillSort;
}
