package com.zhuangjie.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuangjie.common.utils.PageUtils;
import com.zhuangjie.gulimall.product.entity.BrandEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌
 *
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-21 20:01:13
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateCategoryBrandRelationNameColumnByBrandId(Long brandId);

    List<BrandEntity> getBrandsByIds(List<Long> brandIds);
}

