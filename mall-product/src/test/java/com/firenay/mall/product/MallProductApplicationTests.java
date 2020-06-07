package com.firenay.mall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.firenay.mall.product.entity.BrandEntity;
import com.firenay.mall.product.service.BrandService;
import com.firenay.mall.product.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest
public class MallProductApplicationTests {

	@Resource
	private BrandService brandService;

	@Resource
	private CategoryService categoryService;

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
