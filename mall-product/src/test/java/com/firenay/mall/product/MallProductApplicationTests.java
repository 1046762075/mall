package com.firenay.mall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.firenay.mall.product.dao.AttrGroupDao;
import com.firenay.mall.product.dao.SkuSaleAttrValueDao;
import com.firenay.mall.product.entity.BrandEntity;
import com.firenay.mall.product.service.BrandService;
import com.firenay.mall.product.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest
public class MallProductApplicationTests {

	@Resource
	private BrandService brandService;

	@Resource
	private CategoryService categoryService;

	@Resource
	private RedissonClient RedissonClient;

	@Resource
	private StringRedisTemplate StringRedisTemplate;

	@Resource
	private AttrGroupDao attrGroupDao;

	@Resource
	private SkuSaleAttrValueDao skuSaleAttrValueDao;

	@Test
	public void testRedisson(){
		System.out.println(RedissonClient);
	}

	@Test
	public void attrGroupTest(){
		System.out.println(attrGroupDao.getAttrGroupWithAttrsBySpuId(225L,3L));
	}

	@Test
	public void SkuSaleAttrValueTest(){
		System.out.println(skuSaleAttrValueDao.getSaleAttrsBuSpuId(3L));
	}

	@Test
	public void testRedis(){
		// 写入 redis
		ValueOperations<String, String> operations = StringRedisTemplate.opsForValue();
		operations.set("redis","firenay");
		
		// 读取redis值
		String redis = operations.get("redis");
		System.out.println("读取到的类型：" + redis);
	}
	/**
	 * 查找父节点的路径
	 */
	@Test
	public void testFindPath(){
		Long[] paths = categoryService.findCateLogPath(225L);
		System.out.println(Arrays.asList(paths));
	}

	@Test
	public void testBrand() {
		BrandEntity brandEntity = new BrandEntity();
//		brandEntity.setDescript("test");
//		brandEntity.setName("华为");
//		if(brandService.save(brandEntity)){
//			System.out.println("保存成功");
//		}
//		brandEntity.setBrandId(1L);
//		brandEntity.setLogo("华为");
//		brandService.updateById(brandEntity);

		brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1L)).forEach(System.out::println);
	}

}
