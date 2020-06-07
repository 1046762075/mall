package com.firenay.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.firenay.common.utils.PageUtils;
import com.firenay.mall.ware.entity.PurchaseEntity;
import com.firenay.mall.ware.vo.MergeVo;
import com.firenay.mall.ware.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2020-06-06 21:09:28
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

	PageUtils queryPageUnreceivePurchase(Map<String, Object> params);

	void mergePurchase(MergeVo mergeVo);

	void received(List<Long> ids);

	void done(PurchaseDoneVo doneVo);
}

