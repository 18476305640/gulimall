package com.zhuangjie.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuangjie.common.utils.PageUtils;
import com.zhuangjie.gulimall.product.entity.CategoryEntity;
import com.zhuangjie.gulimall.product.vo.indexPage.Catelog2Vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-21 20:01:13
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();

    void removeMenuByIds(List<Long> ids);

    void updateCategoryBrandRelationTableCategoryNameColumnByCategoryId(Long catId);

    List<CategoryEntity> getLevel1Categorys();

    HashMap<String, List<Catelog2Vo>> getCatalog2and3();

    HashMap<String, List<Catelog2Vo>> cacheGetCatalog2and3();

    HashMap<String, List<Catelog2Vo>> getCatalog2and3FormRedis();

    HashMap<String, List<Catelog2Vo>> getCatalog2and3FormRedisAndLock();

    HashMap<String, List<Catelog2Vo>> getCatalog2and3FormRedisCacheRedissonLock();


    HashMap<String, List<Catelog2Vo>> getCatalog2and3FormRedisCacheRedissonLock2();

}

