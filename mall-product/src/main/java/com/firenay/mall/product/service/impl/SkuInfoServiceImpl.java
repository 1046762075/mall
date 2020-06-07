package com.firenay.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firenay.common.utils.PageUtils;
import com.firenay.common.utils.Query;
import com.firenay.mall.product.dao.SkuInfoDao;
import com.firenay.mall.product.entity.SkuInfoEntity;
import com.firenay.mall.product.service.SkuInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Map;

@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
		this.baseMapper.insert(skuInfoEntity);
	}

	/**
	 * SKU 区间模糊查询
	 * key: 华为
	 * catelogId: 225
	 * brandId: 2
	 * min: 2
	 * max: 2
	 */
	@Override
	public PageUtils queryPageByCondition(Map<String, Object> params) {

		QueryWrapper<SkuInfoEntity> wrapper = new QueryWrapper<>();
		String key = (String) params.get("key");
		if(!StringUtils.isEmpty(key)){
			wrapper.and(w -> w.eq("sku_id", key).or().like("sku_name", key));
		}
		// 三级id没选择不应该拼这个条件  没选应该查询所有
		String catelogId = (String) params.get("catelogId");
		if(!StringUtils.isEmpty(catelogId) && !"0".equalsIgnoreCase(catelogId)){
			wrapper.eq("catalog_id", catelogId);
		}
		String brandId = (String) params.get("brandId");
		if(!StringUtils.isEmpty(brandId) && !"0".equalsIgnoreCase(brandId)){
			wrapper.eq("brand_id", brandId);
		}
		String min = (String) params.get("min");
		if(!StringUtils.isEmpty(min)){
			// gt : 大于;  ge: 大于等于
			wrapper.ge("price", min);
		}
		String max = (String) params.get("max");
		if(!StringUtils.isEmpty(max)){
			try {
				BigDecimal bigDecimal = new BigDecimal(max);
				if(bigDecimal.compareTo(new BigDecimal("0")) == 1){
					// le: 小于等于
					wrapper.le("price", max);
				}
			} catch (Exception e) {
				System.out.println("com.firenay.mall.product.service.impl.SkuInfoServiceImpl：前端传来非数字字符");
			}
		}
		IPage<SkuInfoEntity> page = this.page(
				new Query<SkuInfoEntity>().getPage(params),
				wrapper
		);

		return new PageUtils(page);
	}
}