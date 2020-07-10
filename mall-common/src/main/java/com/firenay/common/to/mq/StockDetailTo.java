package com.firenay.common.to.mq;

import lombok.Data;

/**
 * <p>Title: StockDetailTo</p>
 * Description：
 * date：2020/7/3 20:47
 */
@Data
public class StockDetailTo {
	private Long id;
	/**
	 * sku_id
	 */
	private Long skuId;
	/**
	 * sku_name
	 */
	private String skuName;
	/**
	 * 购买个数
	 */
	private Integer skuNum;
	/**
	 * 工作单id
	 */
	private Long taskId;
	/**
	 * 仓库id
	 */
	private Long wareId;
	/**
	 * 1-已锁定  2-已解锁  3-扣减
	 */
	private Integer lockStatus;
}
