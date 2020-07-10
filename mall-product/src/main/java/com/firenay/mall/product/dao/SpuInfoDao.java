package com.firenay.mall.product.dao;

import com.firenay.mall.product.entity.SpuInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * spu信息
 * 
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2020-05-31 17:06:04
 */
@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfoEntity> {

	/**
	 * 修改上架成功的商品的状态
	 */
	void updateSpuStatus(@Param("spuId") Long spuId, @Param("code") int code);
}
