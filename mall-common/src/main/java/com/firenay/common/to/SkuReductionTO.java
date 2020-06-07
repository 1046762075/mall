package com.firenay.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>Title: SkuReductionTO</p>
 * Description：
 * date：2020/6/5 17:33
 */
@Data
public class SkuReductionTO {

	private Long skuId;

	/***
	 * fullCount、discount、countStatus  打折信息
	 */
	private int fullCount;

	private BigDecimal discount;

	private int countStatus;

	private BigDecimal fullPrice;

	private BigDecimal reducePrice;

	private int priceStatus;

	private List<MemberPrice> memberPrice;
}
