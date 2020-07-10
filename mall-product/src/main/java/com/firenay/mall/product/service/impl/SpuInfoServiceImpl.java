package com.firenay.mall.product.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firenay.common.constant.ProductConstant;
import com.firenay.common.to.SkuReductionTO;
import com.firenay.common.to.SpuBoundTO;
import com.firenay.common.to.es.SkuEsModel;
import com.firenay.common.to.es.SkuHasStockVo;
import com.firenay.common.utils.PageUtils;
import com.firenay.common.utils.Query;
import com.firenay.common.utils.R;
import com.firenay.mall.product.dao.SpuInfoDao;
import com.firenay.mall.product.entity.*;
import com.firenay.mall.product.feign.CouponFeignService;
import com.firenay.mall.product.feign.SearchFeignService;
import com.firenay.mall.product.feign.WareFeignService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

	@Autowired
	private BrandService brandService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private WareFeignService wareFeignService;

	/**
	 * feign 远程调用优惠券服务
	 */
	@Autowired
	private CouponFeignService couponFeignService;

	@Autowired
	private SearchFeignService searchFeignService;

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

	/**
	 * 不一样的属性：skuPrice、skuImg、hasStock、hotScore、
	 * 			brandName、brandImg、catalogName、attrs
	 * @param spuId
	 */
	@Override
	public void up(Long spuId) {
		// 1 组装数据 查出当前spuId对应的所有sku信息
		List<SkuInfoEntity> skus = skuInfoService.getSkusBySpuId(spuId);
		// 查询这些sku是否有库存
		List<Long> skuids = skus.stream().map(sku -> sku.getSkuId()).collect(Collectors.toList());
		// 2 封装每个sku的信息

		// 3.查询当前sku所有可以被用来检索的规格属性
		// 获取所有的spu商品的id 然后查询这些id中那些是可以被检索的 [数据库中目前 4、5、6、11不可检索]
		List<ProductAttrValueEntity> baseAttrs = attrValueService.baseAttrListForSpu(spuId);

		List<Long> attrIds = baseAttrs.stream().map(attr -> attr.getAttrId()).collect(Collectors.toList());
		// 可检索的id集合
		Set<Long> isSet = new HashSet<>(attrService.selectSearchAttrIds(attrIds));

		// 根据商品id 过滤不可检索的商品 最后映射号检索属性
		List<SkuEsModel.Attrs> attrs = baseAttrs.stream().filter(item -> isSet.contains(item.getAttrId())).map(item -> {
			SkuEsModel.Attrs attr = new SkuEsModel.Attrs();
			BeanUtils.copyProperties(item, attr);
			return attr;
		}).collect(Collectors.toList());

		// skuId 对应 是否有库存
		Map<Long, Boolean> stockMap = null;
		try {
			// 3.1 发送远程调用 库存系统查询是否有库存
			R hasStock = wareFeignService.getSkuHasStock(skuids);
			// 构造器受保护 所以写成内部类对象
			stockMap = hasStock.getData(new TypeReference<List<SkuHasStockVo>>(){}).stream().collect(Collectors.toMap(SkuHasStockVo::getSkuId, item -> item.getHasStock()));
			log.warn("服务调用成功" + hasStock);
		} catch (Exception e) {
			log.error("库存服务调用失败: 原因{}",e);
		}

		Map<Long, Boolean> finalStockMap = stockMap;
		List<SkuEsModel> collect = skus.stream().map(sku -> {
			SkuEsModel esModel = new SkuEsModel();
			BeanUtils.copyProperties(sku, esModel);
			esModel.setSkuPrice(sku.getPrice());
			esModel.setSkuImg(sku.getSkuDefaultImg());
			// 4 设置库存
			if(finalStockMap == null){
				esModel.setHasStock(true);
			}else {
				esModel.setHasStock(finalStockMap.get(sku.getSkuId()));
			}
			// TODO 1.热度评分 0
			esModel.setHotScore(0L);

			BrandEntity brandEntity = brandService.getById(esModel.getBrandId());

			// brandName、brandImg
			esModel.setBrandName(brandEntity.getName());
			esModel.setBrandImg(brandEntity.getLogo());

			// 查询分类信息
			CategoryEntity categoryEntity = categoryService.getById(esModel.getCatalogId());
			esModel.setCatalogName(categoryEntity.getName());

			// 保存商品的属性
			esModel.setAttrs(attrs);
			return esModel;
		}).collect(Collectors.toList());

		// 5.发给ES进行保存  mall-search
		R r = searchFeignService.productStatusUp(collect);
		if(r.getCode() == 0){
			// 远程调用成功
			baseMapper.updateSpuStatus(spuId, ProductConstant.StatusEnum.SPU_UP.getCode());
		}else{
			// 远程调用失败 TODO 接口幂等性 重试机制
			/**
			 * Feign 的调用流程  Feign有自动重试机制
			 * 1. 发送请求执行
			 * 2.
			 */
		}
	}

	@Override
	public SpuInfoEntity getSpuInfoBySkuId(Long skuId) {

		return getById(skuInfoService.getById(skuId).getSpuId());
	}
}