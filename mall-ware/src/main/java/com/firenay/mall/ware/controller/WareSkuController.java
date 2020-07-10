package com.firenay.mall.ware.controller;

import com.firenay.common.exception.BizCodeEnum;
import com.firenay.common.exception.NotStockException;
import com.firenay.common.to.es.SkuHasStockVo;
import com.firenay.common.utils.PageUtils;
import com.firenay.common.utils.R;
import com.firenay.mall.ware.entity.WareSkuEntity;
import com.firenay.mall.ware.service.WareSkuService;
import com.firenay.mall.ware.vo.WareSkuLockVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2020-06-06 21:09:28
 */
@Slf4j
@RestController
@RequestMapping("ware/waresku")
public class WareSkuController {

    @Autowired
    private WareSkuService wareSkuService;

    @PostMapping("/lock/order")
    public R orderLockStock(@RequestBody WareSkuLockVo vo){
		try {
			wareSkuService.orderLockStock(vo);
			return R.ok();
		} catch (NotStockException e) {
			log.warn("\n" + e.getMessage());
		}
		return R.error(BizCodeEnum.NOT_STOCK_EXCEPTION.getCode(), BizCodeEnum.NOT_STOCK_EXCEPTION.getMsg());
	}

	/**
	 * 查询sku是否有库存
	 * 返回当前id stock量
	 */
	@PostMapping("/hasStock")
//	public List<SkuHasStockVo> getSkuHasStock(@RequestBody List<Long> SkuIds){
	public R getSkuHasStock(@RequestBody List<Long> SkuIds){
		List<SkuHasStockVo> vos = wareSkuService.getSkuHasStock(SkuIds);
		return R.ok().setData(vos);
	}

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wareSkuService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		WareSkuEntity wareSku = wareSkuService.getById(id);
        return R.ok().put("wareSku", wareSku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody WareSkuEntity wareSku){
		wareSkuService.save(wareSku);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody WareSkuEntity wareSku){
		wareSkuService.updateById(wareSku);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		wareSkuService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}
