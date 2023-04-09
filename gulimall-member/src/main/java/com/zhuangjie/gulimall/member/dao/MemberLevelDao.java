package com.zhuangjie.gulimall.member.dao;

import com.zhuangjie.gulimall.member.entity.MemberLevelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员等级
 * 
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-22 11:33:33
 */
@Mapper
public interface MemberLevelDao extends BaseMapper<MemberLevelEntity> {

    MemberLevelEntity getDefaultLevel();

}
