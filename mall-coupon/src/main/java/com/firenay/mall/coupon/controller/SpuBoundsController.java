package com.firenay.mall.coupon.controller;

import com.firenay.common.utils.PageUtils;
import com.firenay.common.utils.R;
import com.firenay.mall.coupon.entity.SpuBoundsEntity;
import com.firenay.mall.coupon.service.SpuBoundsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;



/**
 * 商品spu积分设置
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2020-05-30 00:57:53
 */
@RestController
@RequestMapping("coupon/spubounds")
public class SpuBoundsController {

	@Autowired
	private SpuBoundsService spuBoundsService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	//@RequiresPermissions("coupon:spubounds:list")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = spuBoundsService.queryPage(params);

		return R.ok().put("page", page);
	}


	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id){
		SpuBoundsEntity spuBounds = spuBoundsService.getById(id);

		return R.ok().put("spuBounds", spuBounds);
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public R save(@RequestBody SpuBoundsEntity spuBounds){
		spuBoundsService.save(spuBounds);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody SpuBoundsEntity spuBounds){
		spuBoundsService.updateById(spuBounds);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	//@RequiresPermissions("coupon:spubounds:delete")
	public R delete(@RequestBody Long[] ids){
		spuBoundsService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
