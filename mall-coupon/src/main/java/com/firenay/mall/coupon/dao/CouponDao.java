package com.firenay.mall.coupon.dao;

import com.firenay.mall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2020-05-30 00:57:53
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
