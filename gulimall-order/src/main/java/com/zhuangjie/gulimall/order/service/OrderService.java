package com.zhuangjie.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuangjie.common.utils.PageUtils;
import com.zhuangjie.gulimall.order.entity.OrderEntity;
import com.zhuangjie.gulimall.order.vo.OrderConfirmVo;
import com.zhuangjie.gulimall.order.vo.OrderSubmitVo;
import com.zhuangjie.gulimall.order.vo.SubmitOrderResponseVo;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 订单
 *
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-22 11:51:06
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException;

    SubmitOrderResponseVo submitOrder(OrderSubmitVo vo);

    OrderEntity getOrderByOrderSn(String orderSn);

    void closeOrder(OrderEntity entity);

}

