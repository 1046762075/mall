package com.firenay.mall.member.dao;

import com.firenay.mall.member.entity.MemberLevelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员等级
 * 
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2020-05-30 00:49:16
 */
@Mapper
public interface MemberLevelDao extends BaseMapper<MemberLevelEntity> {

	MemberLevelEntity getDefaultLevel();
}
