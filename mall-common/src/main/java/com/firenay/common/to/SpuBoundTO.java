package com.firenay.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>Title: SpuBoundTO</p>
 * Description：远程调用对象  成长积分、购物积分
 * date：2020/6/5 17:12
 */
@Data
public class SpuBoundTO {

	private Long spuId;

	private BigDecimal buyBounds;

	private BigDecimal growBounds;
}
