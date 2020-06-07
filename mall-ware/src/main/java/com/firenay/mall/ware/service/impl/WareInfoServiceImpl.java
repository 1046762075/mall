package com.firenay.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firenay.common.utils.PageUtils;
import com.firenay.common.utils.Query;
import com.firenay.mall.ware.dao.WareInfoDao;
import com.firenay.mall.ware.entity.WareInfoEntity;
import com.firenay.mall.ware.service.WareInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
		QueryWrapper<WareInfoEntity> wrapper = new QueryWrapper<>();

		String key = (String) params.get("key");
		if(!StringUtils.isEmpty(key)){
			wrapper.eq("id", key).or().like("name",key).or().like("address", key).or().like("areacode", key);
		}
		IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
				wrapper
        );
        return new PageUtils(page);
    }
}