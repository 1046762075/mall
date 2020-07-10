package com.firenayl.mall.search.vo;

import lombok.Data;

import java.util.List;

/**
 * <p>Title: SearchParam</p>
 * Description：封装页面所有可能传递过来的关键字
 * 		catalog3Id=225&keyword=华为&sort=saleCount_asc&hasStock=0/1&brandId=25&brandId=30
 * date：2020/6/12 22:56
 */
@Data
public class SearchParam {

	/**
	 * 全文匹配关键字
	 */
	private String keyword;

	/**
	 * 三级分类id
	 */
	private Long catalog3Id;

	private String sort;

	private Integer hasStock;

	/**
	 * 价格区间
	 */
	private String skuPrice;

	/**
	 * 品牌id 可以多选
	 */
	private List<Long> brandId;

	/**
	 * 按照属性进行筛选
	 */
	private List<String> attrs;

	/**
	 * 页码
	 */
	private Integer pageNum = 1;

	/**
	 * 原生所有查询属性
	 */
	private String _queryString;
}
