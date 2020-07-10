package com.firenay.mall.ware.vo;

import lombok.Data;

/**
 * <p>Title: LockStockResult</p>
 * Description：
 * date：2020/7/2 11:19
 */
@Data
public class LockStockResult {
	private Long skuId;

	private Integer num;

	private Boolean locked;

}
