package com.zhuangjie.gulimall.product.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuangjie.gulimall.product.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhuangjie.gulimall.product.vo.AttrVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性
 * 
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-21 20:01:13
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {
    Page<AttrVo> queryAttrByCategoryIdAndKey(@Param("page") Page<AttrEntity> page, @Param("categoryId") Long categoryId,@Param("type") Integer type,  @Param("key") String key);
    List<Long> selectSearchAttrIds(@Param("attrIds") List<Long> attrIds);
}
