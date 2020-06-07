package com.firenay.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firenay.common.constant.WareConstant;
import com.firenay.common.utils.PageUtils;
import com.firenay.common.utils.Query;
import com.firenay.mall.ware.dao.PurchaseDao;
import com.firenay.mall.ware.entity.PurchaseDetailEntity;
import com.firenay.mall.ware.entity.PurchaseEntity;
import com.firenay.mall.ware.service.PurchaseDetailService;
import com.firenay.mall.ware.service.PurchaseService;
import com.firenay.mall.ware.service.WareSkuService;
import com.firenay.mall.ware.vo.MergeVo;
import com.firenay.mall.ware.vo.PurchaseDoneVo;
import com.firenay.mall.ware.vo.PurchaseItemDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

	@Autowired
	private PurchaseDetailService detailService;

	@Autowired
	private WareSkuService wareSkuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

	/**
	 * 分页查询
	 */
	@Override
	public PageUtils queryPageUnreceivePurchase(Map<String, Object> params) {
		IPage<PurchaseEntity> page = this.page(
				new Query<PurchaseEntity>().getPage(params),
//				采购状态只能是0,1 ：新建,已分配
				new QueryWrapper<PurchaseEntity>().eq("status", 0).or().eq("status", 1)
		);

		return new PageUtils(page);
	}

	/**
	 * 根据情况修改、创建采购单   [没有更改分配状态]
	 */
	@Transactional
	@Override
	public void mergePurchase(MergeVo mergeVo) {
		Long purchaseId = mergeVo.getPurchaseId();
		// 如果采购id为null 说明没选采购单
		if(purchaseId == null){
			// 新建采购单
			PurchaseEntity purchaseEntity = new PurchaseEntity();
			purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.CREATED.getCode());
			purchaseEntity.setCreateTime(new Date());
			purchaseEntity.setUpdateTime(new Date());
			this.save(purchaseEntity);
			purchaseId = purchaseEntity.getId();
		}
		// 合并采购单 [其实就是修改上面创建的采购单]
		List<Long> items = mergeVo.getItems();

		// 从数据库查询所有要合并的采购单并过滤所有大于 [已分配] 状态的订单
		PurchaseEntity purchaseEntity = new PurchaseEntity();
		List<PurchaseDetailEntity> detailEntities = detailService.getBaseMapper().selectBatchIds(items).stream().filter(entity -> {
			// 如果正在合并采购异常的项就把这个采购项之前所在的采购单的状态 wms_purchase 表的状态修改为 已分配
			if(entity.getStatus() == WareConstant.PurchaseDetailStatusEnum.HASERROR.getCode()){
				purchaseEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());
				purchaseEntity.setId(entity.getPurchaseId());
				this.updateById(purchaseEntity);
			}
			return entity.getStatus() < WareConstant.PurchaseDetailStatusEnum.BUYING.getCode() || entity.getStatus() == WareConstant.PurchaseDetailStatusEnum.HASERROR.getCode();
		}).collect(Collectors.toList());
		// 将符合条件的id集合重新赋值给 items
		items = detailEntities.stream().map(entity -> entity.getId()).collect(Collectors.toList());
		if(items == null || items.size() == 0){
			return;
		}
		// 设置仓库id
		purchaseEntity.setWareId(detailEntities.get(0).getWareId());
		Long finalPurchaseId = purchaseId;
		// 给采购单设置各种属性
		List<PurchaseDetailEntity> collect = items.stream().map(item -> {
			PurchaseDetailEntity entity = new PurchaseDetailEntity();
			entity.setId(item);
			entity.setPurchaseId(finalPurchaseId);
			entity.setStatus(WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());
			return entity;
		}).collect(Collectors.toList());

		// 每次更新完就更新时间
		detailService.updateBatchById(collect);
		purchaseEntity.setId(purchaseId);
		purchaseEntity.setUpdateTime(new Date());
		this.updateById(purchaseEntity);
	}

	/**
	 * 领取采购单
	 * ids：采购单id
	 */
	@Override
	public void received(List<Long> ids) {
		if(ids == null || ids.size() == 0){
			return;
		}
		// 1.确认当前采购单是已分配状态
		List<PurchaseEntity> collect = ids.stream().map(id -> this.getById(id)
				// 只能采购已分配的
		).filter(item -> item.getStatus() == WareConstant.PurchaseStatusEnum.ASSIGNED.getCode() || item.getStatus() == WareConstant.PurchaseStatusEnum.CREATED.getCode())
				.map(item -> {
					item.setStatus(WareConstant.PurchaseStatusEnum.RECEIVE.getCode());
					item.setUpdateTime(new Date());
					return item;
				}).collect(Collectors.toList());
		// 2.被领取之后重新设置采购状态
		this.updateBatchById(collect);

		// 3.改变采购项状态
		collect.forEach(item -> {
			List<PurchaseDetailEntity> entities = detailService.listDetailByPurchaseId(item.getId());

			// 收集所有需要更新的采购单id
			List<PurchaseDetailEntity> detailEntities = entities.stream().map(entity -> {
				PurchaseDetailEntity detailEntity = new PurchaseDetailEntity();
				detailEntity.setId(entity.getId());
				detailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.BUYING.getCode());
				return detailEntity;
			}).collect(Collectors.toList());
			// 根据id  批量更新
			detailService.updateBatchById(detailEntities);
		});
	}

	/**
	 * {
	 * 	"id":"1",
	 * 	"items":[
	 *                {"itemId":1,"status":3,"reason":""},
	 *        {"itemId":3,"status":4,"reason":"无货"}
	 * 	]
	 * }
	 *
	 * id：		采购单id
	 * items：	采购项
	 * itemId：	采购需求id
	 * status：	采购状态
	 */
	@Transactional
	@Override
	public void done(PurchaseDoneVo doneVo) {
		// 1.改变采购单状态
		Long id = doneVo.getId();
		Boolean flag = true;
		List<PurchaseItemDoneVo> items = doneVo.getItems();
		ArrayList<PurchaseDetailEntity> updates = new ArrayList<>();
		double price;
		double p = 0;
		double sum = 0;
		// 2.改变采购项状态
		for (PurchaseItemDoneVo item : items) {
			// 采购失败的情况
			PurchaseDetailEntity detailEntity = new PurchaseDetailEntity();
			if(item.getStatus() == WareConstant.PurchaseDetailStatusEnum.HASERROR.getCode()){
				flag = false;
				detailEntity.setStatus(item.getStatus());
			}else{
				detailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.FINISH.getCode());
				// 3.将成功采购的进行入库
				// 查出当前采购项的详细信息
				PurchaseDetailEntity entity = detailService.getById(item.getItemId());
				// skuId、到那个仓库、sku名字
				price = wareSkuService.addStock(entity.getSkuId(), entity.getWareId(), entity.getSkuNum());
				if(price != p){
					p = entity.getSkuNum() * price;
				}
				detailEntity.setSkuPrice(new BigDecimal(p));
				sum += p;
			}
			// 设置采购成功的id
			detailEntity.setId(item.getItemId());
			updates.add(detailEntity);
		}
		// 批量更新采购单
		detailService.updateBatchById(updates);

		// 对采购单的状态进行更新
		PurchaseEntity purchaseEntity = new PurchaseEntity();
		purchaseEntity.setId(id);
		purchaseEntity.setAmount(new BigDecimal(sum));
		purchaseEntity.setStatus(flag?WareConstant.PurchaseStatusEnum.FINISH.getCode():WareConstant.PurchaseStatusEnum.HASERROR.getCode());
		purchaseEntity.setUpdateTime(new Date());
		this.updateById(purchaseEntity);
	}
}