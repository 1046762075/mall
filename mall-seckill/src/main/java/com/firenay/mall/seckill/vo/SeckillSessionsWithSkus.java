package com.firenay.mall.seckill.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>Title: SeckillSessionsWithSkus</p>
 * Description：
 * date：2020/7/6 18:48
 */
@Data
public class SeckillSessionsWithSkus {

	private Long id;
	/**
	 * 场次名称
	 */
	private String name;
	/**
	 * 每日开始时间
	 */
	private Date startTime;
	/**
	 * 每日结束时间
	 */
	private Date endTime;
	/**
	 * 启用状态
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;

	private List<SeckillSkuRelationEntity> relationSkus;
}
