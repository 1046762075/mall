package com.firenay.mall.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>Title: OrderItemVo</p>
 * Description：
 * date：2020/6/30 16:38
 */
@Data
public class OrderItemVo {

	private Long skuId;

	/**
	 * 是否被选中
	 */
	private Boolean check = true;

	private String title;

	private String image;

	private List<String> skuAttr;

	/**
	 * 价格
	 */
	private BigDecimal price;

	/**
	 * 数量
	 */
	private Integer count;

	private BigDecimal totalPrice;

	/**
	 * 是否有货
	 */
//	private boolean hasStock;

	/**
	 * 重量
	 */
	private BigDecimal weight;
}
