package com.zhuangjie.gulimall.order.dao;

import com.zhuangjie.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-22 11:51:06
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
