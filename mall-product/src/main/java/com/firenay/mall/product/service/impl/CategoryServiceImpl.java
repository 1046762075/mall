package com.firenay.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firenay.common.utils.PageUtils;
import com.firenay.common.utils.Query;
import com.firenay.mall.product.dao.CategoryDao;
import com.firenay.mall.product.entity.CategoryEntity;
import com.firenay.mall.product.service.CategoryBrandRelationService;
import com.firenay.mall.product.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

	@Resource
	private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public List<CategoryEntity> listWithTree() {
		return baseMapper.selectList(null);
	}

	@Override
	public void removeMenuByIds(List<Long> asList) {
    	// TODO 检查当前节点是否被别的地方引用
		baseMapper.deleteBatchIds(asList);
	}

	@Override
	public Long[] findCateLogPath(Long catelogId) {
    	List<Long> paths = new ArrayList<>();
		paths = findParentPath(catelogId, paths);
		// 收集的时候是顺序 前端是逆序显示的 所以用集合工具类给它逆序一下
		Collections.reverse(paths);
		return paths.toArray(new Long[paths.size()]);
	}

	/**
	 * 级联更新所有数据
	 */
	@Transactional
	@Override
	public void updateCascade(CategoryEntity category) {
		this.updateById(category);
		categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
	}

	/**
	 * 递归收集所有节点
	 */
	private List<Long> findParentPath(Long catlogId ,List<Long> paths){
    	// 1、收集当前节点id
		paths.add(catlogId);
    	CategoryEntity byId = this.getById(catlogId);
		if(byId.getParentCid() != 0){
			findParentPath(byId.getParentCid() ,paths);
		}
		return paths;
	}
}