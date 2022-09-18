package com.zhuangjie.gulimall.product.dao;

import com.zhuangjie.gulimall.product.entity.AttrEntity;
import com.zhuangjie.gulimall.product.entity.AttrGroupEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组
 * 
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-21 20:01:13
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

    List<AttrEntity> queryAttrsByAttrgroupId(@Param("attrGroupId") Long attrGroupId);
}
