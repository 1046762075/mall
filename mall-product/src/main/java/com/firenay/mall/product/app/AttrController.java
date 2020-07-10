package com.firenay.mall.product.app;

import com.firenay.common.utils.PageUtils;
import com.firenay.common.utils.R;
import com.firenay.mall.product.entity.ProductAttrValueEntity;
import com.firenay.mall.product.service.AttrService;
import com.firenay.mall.product.service.ProductAttrValueService;
import com.firenay.mall.product.vo.AttrRespVo;
import com.firenay.mall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
 * 商品属性
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2020-05-31 17:06:04
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {

    @Autowired
    private AttrService attrService;

	@Autowired
	private ProductAttrValueService productAttrValueService;

	@PostMapping("/update/{spuId}")
	public R updateSpiAttr(@PathVariable("spuId") Long spuId, @RequestBody List<ProductAttrValueEntity> entities){
		productAttrValueService.updateSpuAttr(spuId, entities);
		return R.ok();
	}

	/**
	 * 查询属性规格
	 */
	@GetMapping("/base/listforspu/{spuId}")
    public R baseAttrListForSpu(@PathVariable("spuId") Long spuId){
		List<ProductAttrValueEntity> entities = productAttrValueService.baseAttrListForSpu(spuId);
    	return R.ok().put("data", entities);
	}

    @GetMapping("/{attrType}/list/{catelogId}")
    public R baseAttrList(@RequestParam Map<String, Object> params ,@PathVariable("catelogId") Long catelogId, @PathVariable("attrType") String attrType){

		PageUtils page = attrService.queryBaseAttrPage(params, catelogId, attrType);
    	return R.ok().put("page", page);
	}

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId){
		AttrRespVo respVo = attrService.getAttrInfo(attrId);
		R ok = R.ok();
		ok.put("data", respVo);
        return ok.put("attr", respVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrVo attrVo){
		attrService.saveAttr(attrVo);

        return R.ok();
    }

    /**
     * 更改规格参数：参数名、参数id、参数、状态的一一对应
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrVo attrVo){
		attrService.updateAttr(attrVo);
        return R.ok();
    }

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

		return R.ok();
	}
}
