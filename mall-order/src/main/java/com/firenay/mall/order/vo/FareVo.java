package com.firenay.mall.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>Title: FareVo</p>
 * Description：
 * date：2020/7/2 0:05
 */
@Data
public class FareVo {
	private MemberAddressVo memberAddressVo;

	private BigDecimal fare;
}
