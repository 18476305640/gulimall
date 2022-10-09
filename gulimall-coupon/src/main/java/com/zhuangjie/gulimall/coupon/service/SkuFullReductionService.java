package com.zhuangjie.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuangjie.common.to.SkuReductionTo;
import com.zhuangjie.common.utils.PageUtils;
import com.zhuangjie.gulimall.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-22 09:41:41
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuReduction(SkuReductionTo reductionTo);
}

