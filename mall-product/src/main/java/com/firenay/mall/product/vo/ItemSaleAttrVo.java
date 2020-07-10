package com.firenay.mall.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class ItemSaleAttrVo{
	private Long attrId;

	private String attrName;

	private List<AttrValueWithSkuIdVo> attrValues;
}