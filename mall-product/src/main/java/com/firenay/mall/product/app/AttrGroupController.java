package com.firenay.mall.product.app;

import com.firenay.common.utils.PageUtils;
import com.firenay.common.utils.R;
import com.firenay.mall.product.entity.AttrEntity;
import com.firenay.mall.product.entity.AttrGroupEntity;
import com.firenay.mall.product.service.AttrAttrgroupRelationService;
import com.firenay.mall.product.service.AttrGroupService;
import com.firenay.mall.product.service.AttrService;
import com.firenay.mall.product.service.CategoryService;
import com.firenay.mall.product.vo.AttrGroupRelationVo;
import com.firenay.mall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 属性分组
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2020-05-31 17:06:04
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {

    @Autowired
    private AttrGroupService attrGroupService;

	@Autowired
	private CategoryService categoryService;

	@Resource
	private AttrService attrService;

	@Resource
	private AttrAttrgroupRelationService relationService;

	@PostMapping("/attr/relation")
	public R addRelation(@RequestBody List<AttrGroupRelationVo> vos){
		relationService.saveBatch(vos);
		return R.ok();
	}

	@GetMapping("/{catelogId}/withattr")
	public R getAttrGroupWithAttrs(@PathVariable("catelogId") Long catelogId){
		// 1.查询当前分类下的所有属性分组
		List<AttrGroupWithAttrsVo> vos = attrGroupService.getAttrGroupWithAttrByCatelogId(catelogId);
		// 2.查询每个分组的所有信息
		return R.ok().put("data", vos);
	}

	@GetMapping("/{attrgroupId}/attr/relation")
	public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId){
		// 获取当前分组关联的所有属性
		List<AttrEntity> entities = attrService.getRelationAttr(attrgroupId);
		return R.ok().put("data", entities);
	}

	@GetMapping("/{attrgroupId}/noattr/relation")
	public R attrNoRelation(@RequestParam Map<String, Object> params, @PathVariable("attrgroupId") Long attrgroupId){
		// 传入所有分页信息 、分组id
		PageUtils page = attrService.getNoRelationAttr(params, attrgroupId);
		return R.ok().put("data", page);
	}
    /**
     * 列表
	 * http://127.0.0.1:88/api/product/attrgroup/list/1?page=1&key=aa
     */
    @RequestMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("catelogId") Long catelogId){
		PageUtils page =  attrGroupService.queryPage(params, catelogId);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
		// 用当前当前分类id查询完整路径并写入 attrGroup
		attrGroup.setCatelogPath(categoryService.findCateLogPath(attrGroup.getCatelogId()));
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("${moduleNamez}:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

	@PostMapping("/attr/relation/delete")
	public R deleteRelation(@RequestBody AttrGroupRelationVo[] vos){
		attrService.deleteRelation(vos);
		return R.ok();
	}

}
