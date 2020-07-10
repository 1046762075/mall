package com.firenay.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firenay.common.utils.PageUtils;
import com.firenay.common.utils.Query;
import com.firenay.mall.ware.dao.WareOrderTaskDao;
import com.firenay.mall.ware.entity.WareOrderTaskEntity;
import com.firenay.mall.ware.service.WareOrderTaskService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("wareOrderTaskService")
public class WareOrderTaskServiceImpl extends ServiceImpl<WareOrderTaskDao, WareOrderTaskEntity> implements WareOrderTaskService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareOrderTaskEntity> page = this.page(
                new Query<WareOrderTaskEntity>().getPage(params),
                new QueryWrapper<WareOrderTaskEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public WareOrderTaskEntity getOrderTaskByOrderSn(String orderSn) {

		return this.getOne(new QueryWrapper<WareOrderTaskEntity>().eq("order_sn", orderSn));
	}

}