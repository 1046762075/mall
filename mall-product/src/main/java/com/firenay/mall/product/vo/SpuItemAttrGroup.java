package com.firenay.mall.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class SpuItemAttrGroup{
	private String groupName;

	private List<SpuBaseAttrVo> attrs;
}