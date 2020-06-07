package com.firenay.mall.product.vo;

import lombok.Data;

import java.math.BigDecimal;
/**
 * <p>Title: AttrVo</p>
 * Description：成直积分、购物积分
 *
 * date：2020/6/2 19:23
 */
@Data
public class Bounds {

    private BigDecimal buyBounds;
    private BigDecimal growBounds;

}