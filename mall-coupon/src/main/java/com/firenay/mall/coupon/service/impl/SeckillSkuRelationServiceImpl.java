package com.firenay.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firenay.common.utils.PageUtils;
import com.firenay.common.utils.Query;
import com.firenay.mall.coupon.dao.SeckillSkuRelationDao;
import com.firenay.mall.coupon.entity.SeckillSkuRelationEntity;
import com.firenay.mall.coupon.service.SeckillSkuRelationService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("seckillSkuRelationService")
public class SeckillSkuRelationServiceImpl extends ServiceImpl<SeckillSkuRelationDao, SeckillSkuRelationEntity> implements SeckillSkuRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
		QueryWrapper<SeckillSkuRelationEntity> wrapper = new QueryWrapper<>();
		String promotionSessionId = (String) params.get("promotionSessionId");
		// 场次ID不是空
		if(!StringUtils.isEmpty(promotionSessionId)){
			wrapper.eq("promotion_session_id",promotionSessionId);
		}

		IPage<SeckillSkuRelationEntity> page = this.page(
                new Query<SeckillSkuRelationEntity>().getPage(params),
				wrapper
        );

        return new PageUtils(page);
    }
}