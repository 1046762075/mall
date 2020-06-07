package com.firenay.mall.product.vo;

import lombok.Data;

/**
 * <p>Title: AttrRespVo</p>
 * Description：
 * date：2020/6/2 19:56
 */
@Data
public class AttrRespVo extends AttrVo{

	private String catelogName;

	private String groupName;

	private Long[] catelogPath;
}
