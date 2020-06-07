package com.firenay.mall.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
/**
 * <p>Title: PurchaseDoneVo</p>
 * Description：采购单
 * date：2020/6/6 23:23
 */
@Data
public class PurchaseDoneVo {

	/**
	 * 采购单id
	 */
    @NotNull
    private Long id;

    private List<PurchaseItemDoneVo> items;
}
