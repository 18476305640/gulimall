package com.zhuangjie.gulimall.product.dao;

import com.zhuangjie.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-21 20:01:13
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
