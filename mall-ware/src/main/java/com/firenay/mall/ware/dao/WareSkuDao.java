package com.firenay.mall.ware.dao;

import com.firenay.mall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品库存 -- 更新库存 -- 查询库存
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2020-06-06 21:09:28
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

	void addStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);

	Long getSkuStock(@Param("id") Long id);

	List<Long> listWareIdHasSkuStock(@Param("skuId") Long skuId);

	Long lockSkuStock(@Param("skuId") Long skuId,@Param("wareId") Long wareId, @Param("num") Integer num);

	void unlockStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("num") Integer num);
}
