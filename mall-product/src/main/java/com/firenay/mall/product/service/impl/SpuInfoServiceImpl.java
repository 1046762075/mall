package com.firenay.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firenay.common.to.SkuReductionTO;
import com.firenay.common.to.SpuBoundTO;
import com.firenay.common.utils.PageUtils;
import com.firenay.common.utils.Query;
import com.firenay.common.utils.R;
import com.firenay.mall.product.dao.SpuInfoDao;
import com.firenay.mall.product.entity.AttrEntity;
import com.firenay.mall.product.entity.ProductAttrValueEntity;
import com.firenay.mall.product.entity.SkuImagesEntity;
import com.firenay.mall.product.entity.SkuInfoEntity;
import com.firenay.mall.product.entity.SkuSaleAttrValueEntity;
import com.firenay.mall.product.entity.SpuInfoDescEntity;
import com.firenay.mall.product.entity.SpuInfoEntity;
import com.firenay.mall.product.feign.CouponFeignService;
import com.firenay.mall.product.service.*;
import com.firenay.mall.product.vo.Attr;
import com.firenay.mall.product.vo.BaseAttrs;
import com.firenay.mall.product.vo.Bounds;
import com.firenay.mall.product.vo.Images;
import com.firenay.mall.product.vo.Skus;
import com.firenay.mall.product.vo.SpuSaveVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

	@Autowired
	private SpuInfoDescService spuInfoDescService;

	@Autowired
	private SpuImagesService spuImagesService;

	@Autowired
	private AttrService attrService;

	@Autowired
	private ProductAttrValueService attrValueService;

	@Autowired
	private SkuInfoService skuInfoService;

	@Autowired
	private SkuImagesService skuImagesService;

	@Autowired
	private SkuSaleAttrValueService skuSaleAttrValueService;

	/**
	 * feign 远程调用优惠券服务
	 */
	@Autowired
	private CouponFeignService couponFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

	/**
	 * 保存所有数据 [33kb左右]
	 */
	@Transactional
	@Override
	public void saveSpuInfo(SpuSaveVo vo) {

		// 1.保存spu基本信息 pms_sku_info
		SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
		spuInfoEntity.setCreateTime(new Date());
		spuInfoEntity.setUpdateTime(new Date());
		BeanUtils.copyProperties(vo, spuInfoEntity);
		this.saveBatchSpuInfo(spuInfoEntity);
		// 2.保存spu的表述图片  pms_spu_info_desc
		List<String> decript = vo.getDecript();
		SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
		spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
		// String join 的方式将它们用逗号分隔
		spuInfoDescEntity.setDecript(String.join(",", decript));
		spuInfoDescService.saveSpuInfoDesc(spuInfoDescEntity);
		// 3.保存spu的图片集  pms_sku_images

		// 先获取所有图片
		List<String> images = vo.getImages();
		// 保存图片的时候 并且保存这个是那个spu的图片
		spuImagesService.saveImages(spuInfoEntity.getId() ,images);
		// 4.保存spu的规格属性  pms_product_attr_value
		List<BaseAttrs> baseAttrs = vo.getBaseAttrs();
		List<ProductAttrValueEntity> collect = baseAttrs.stream().map(attr -> {
			ProductAttrValueEntity valueEntity = new ProductAttrValueEntity();
			valueEntity.setAttrId(attr.getAttrId());
			// 可能页面没用传入属性名字 根据属性id查到所有属性 给名字赋值
			AttrEntity attrEntity = attrService.getById(attr.getAttrId());
			valueEntity.setAttrName(attrEntity.getAttrName());
			valueEntity.setAttrValue(attr.getAttrValues());
			valueEntity.setQuickShow(attr.getShowDesc());
			valueEntity.setSpuId(spuInfoEntity.getId());

			return valueEntity;
		}).collect(Collectors.toList());
		attrValueService.saveProductAttr(collect);
		// 5.保存当前spu对应所有sku信息
		Bounds bounds = vo.getBounds();
		SpuBoundTO spuBoundTO = new SpuBoundTO();
		BeanUtils.copyProperties(bounds, spuBoundTO);
		spuBoundTO.setSpuId(spuInfoEntity.getId());
		R r = couponFeignService.saveSpuBounds(spuBoundTO);
		if(r.getCode() != 0){
			log.error("远程保存spu积分信息失败");
		}
		// 1).spu的积分信息 sms_spu_bounds
		List<Skus> skus = vo.getSkus();
		if(skus != null && skus.size() > 0){
			// 提前查找默认图片
			skus.forEach(item -> {
				String dufaultImg = "";
				for (Images img : item.getImages()) {
					if(img.getDefaultImg() == 1){
						dufaultImg = img.getImgUrl();
					}
				}
				// 2).基本信息的保存 pms_sku_info
				// skuName 、price、skuTitle、skuSubtitle 这些属性需要手动保存
				SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
				BeanUtils.copyProperties(item, skuInfoEntity);
				// 设置spu的品牌id
				skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
				skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
				skuInfoEntity.setSpuId(spuInfoEntity.getId());
				skuInfoEntity.setSkuDefaultImg(dufaultImg);
				skuInfoEntity.setSaleCount((long) (Math.random()*2888));
				skuInfoService.saveSkuInfo(skuInfoEntity);

				// 3).保存sku的图片信息  pms_sku_images
				// sku保存完毕 自增主键就出来了 收集所有图片
				Long skuId = skuInfoEntity.getSkuId();
				List<SkuImagesEntity> imagesEntities = item.getImages().stream().map(img -> {
					SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
					skuImagesEntity.setId(skuId);
					skuImagesEntity.setImgUrl(img.getImgUrl());
					skuImagesEntity.setDefaultImg(img.getDefaultImg());
					return skuImagesEntity;
				}).filter(entity ->
						// 返回true就会保存 返回false就会过滤
					!StringUtils.isEmpty(entity.getImgUrl())
				).collect(Collectors.toList());
				skuImagesService.saveBatch(imagesEntities);

				// 4).sku的销售属性  pms_sku_sale_attr_value
				List<Attr> attr = item.getAttr();
				List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attr.stream().map(a -> {
					// 对拷页面传过来的三个属性
					SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
					BeanUtils.copyProperties(a, skuSaleAttrValueEntity);
					skuSaleAttrValueEntity.setSkuId(skuId);
					return skuSaleAttrValueEntity;
				}).collect(Collectors.toList());
				skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

				// 5.) sku的优惠、满减、会员价格等信息  [跨库]
				SkuReductionTO skuReductionTO = new SkuReductionTO();
				BeanUtils.copyProperties(item, skuReductionTO);
				skuReductionTO.setSkuId(skuId);
				if(skuReductionTO.getFullCount() > 0 || (skuReductionTO.getFullPrice().compareTo(new BigDecimal("0")) > 0)){
					R r1 = couponFeignService.saveSkuReduction(skuReductionTO);
					if(r1.getCode() != 0){
						log.error("远程保存sku优惠信息失败");
					}
				}
			});
		}
	}

	@Override
	public void saveBatchSpuInfo(SpuInfoEntity spuInfoEntity) {
		this.baseMapper.insert(spuInfoEntity);
	}

	/**
	 * spu管理模糊查询
	 */
	@Override
	public PageUtils queryPageByCondition(Map<String, Object> params) {

		QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();

		// 根据 spu管理带来的条件进行叠加模糊查询
		String key = (String) params.get("key");
		if(!StringUtils.isEmpty(key)){
			wrapper.and(w -> w.eq("id", key).or().like("spu_name",key));
		}

		String status = (String) params.get("status");
		if(!StringUtils.isEmpty(status)){
			wrapper.eq("publish_status", status);
		}

		String brandId = (String) params.get("brandId");
		if(!StringUtils.isEmpty(brandId) && !"0".equalsIgnoreCase(brandId)){
			wrapper.eq("brand_id", brandId);
		}

		String catelogId = (String) params.get("catelogId");
		if(!StringUtils.isEmpty(catelogId) && !"0".equalsIgnoreCase(catelogId)){
			wrapper.eq("catalog_id", catelogId);
		}

		IPage<SpuInfoEntity> page = this.page(
				new Query<SpuInfoEntity>().getPage(params),
				wrapper
		);
		return new PageUtils(page);
	}
}