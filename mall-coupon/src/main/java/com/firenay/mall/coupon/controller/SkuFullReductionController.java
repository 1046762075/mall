package com.firenay.mall.coupon.controller;

import com.firenay.common.to.SkuReductionTO;
import com.firenay.common.utils.PageUtils;
import com.firenay.common.utils.R;
import com.firenay.mall.coupon.entity.SkuFullReductionEntity;
import com.firenay.mall.coupon.service.SkuFullReductionService;
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
 * 商品满减信息
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2020-05-30 00:57:53
 */
@RestController
@RequestMapping("coupon/skufullreduction")
public class SkuFullReductionController {

	@Autowired
	private SkuFullReductionService skuFullReductionService;

	@PostMapping("/saveinfo")
	public R saveInfo(@RequestBody SkuReductionTO reductionTo){

		skuFullReductionService.saveSkuReduction(reductionTo);
		return R.ok();
	}


	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = skuFullReductionService.queryPage(params);

		return R.ok().put("page", page);
	}


	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id){
		SkuFullReductionEntity skuFullReduction = skuFullReductionService.getById(id);

		return R.ok().put("skuFullReduction", skuFullReduction);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody SkuFullReductionEntity skuFullReduction){
		skuFullReductionService.save(skuFullReduction);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody SkuFullReductionEntity skuFullReduction){
		skuFullReductionService.updateById(skuFullReduction);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids){
		skuFullReductionService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
