package com.zhuangjie.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuangjie.common.to.SkuHasStockVo;
import com.zhuangjie.common.to.mq.OrderTo;
import com.zhuangjie.common.to.mq.StockLockedTo;
import com.zhuangjie.common.utils.PageUtils;
import com.zhuangjie.gulimall.ware.entity.WareSkuEntity;
import com.zhuangjie.gulimall.ware.vo.WareSkuLockVo;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-22 12:28:54
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockVo> getSkusHasStock(List<Long> skuIds);

    Boolean orderLockStock(WareSkuLockVo vo);

    void unlockStock(StockLockedTo to);

    void unlockStock(OrderTo orderTo);
}

