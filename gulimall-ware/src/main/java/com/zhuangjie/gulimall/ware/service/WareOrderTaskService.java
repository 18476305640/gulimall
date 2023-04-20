package com.zhuangjie.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuangjie.common.utils.PageUtils;
import com.zhuangjie.gulimall.ware.entity.WareOrderTaskEntity;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-22 12:28:54
 */
public interface WareOrderTaskService extends IService<WareOrderTaskEntity> {

    PageUtils queryPage(Map<String, Object> params);

    WareOrderTaskEntity getOrderTaskByOrderSn(String orderSn);
}

