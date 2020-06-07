package com.firenay.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firenay.common.utils.PageUtils;
import com.firenay.common.utils.Query;
import com.firenay.common.utils.R;
import com.firenay.mall.ware.dao.WareSkuDao;
import com.firenay.mall.ware.entity.WareSkuEntity;
import com.firenay.mall.ware.feign.ProductFeignService;
import com.firenay.mall.ware.service.WareSkuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

	@Resource
	private WareSkuDao wareSkuDao;

	@Resource
	private ProductFeignService productFeignService;

	/**
	 * 商品库存的模糊查询
	 * skuId: 1
	 * wareId: 1
	 */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
		QueryWrapper<WareSkuEntity> wrapper = new QueryWrapper<>();
		String id = (String) params.get("skuId");
		if(!StringUtils.isEmpty(id)){
			wrapper.eq("sku_id", id);
		}
		id = (String) params.get("wareId");
		if(!StringUtils.isEmpty(id)){
			wrapper.eq("ware_id", id);
		}
		IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
				wrapper
        );
        return new PageUtils(page);
    }

	/**
	 * 添加库存
	 * wareId: 仓库id
	 * return 返回商品价格
	 */
	@Transactional
	@Override
	public double addStock(Long skuId, Long wareId, Integer skuNum) {
		// 1.如果还没有这个库存记录 那就是新增操作
		List<WareSkuEntity> entities = wareSkuDao.selectList(new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));
		double price = 0.0;
		// TODO 还可以用什么办法让异常出现以后不回滚？高级
		WareSkuEntity entity = new WareSkuEntity();
		try {
			R info = productFeignService.info(skuId);
			Map<String,Object> data = (Map<String, Object>) info.get("skuInfo");

			if(info.getCode() == 0){
				entity.setSkuName((String) data.get("skuName"));
				// 设置商品价格
				price = (Double) data.get("price");
			}
		}catch (Exception e){
			System.out.println("com.firenay.mall.ware.service.impl.WareSkuServiceImpl：远程调用出错");
		}
		// 新增操作
		if(entities == null || entities.size() == 0){
			entity.setSkuId(skuId);
			entity.setStock(skuNum);
			entity.setWareId(wareId);
			entity.setStockLocked(0);
			wareSkuDao.insert(entity);
		}else {
			wareSkuDao.addStock(skuId, wareId, skuNum);
		}
		return price;
	}
}