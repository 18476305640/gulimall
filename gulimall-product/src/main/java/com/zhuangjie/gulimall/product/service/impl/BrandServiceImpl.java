package com.zhuangjie.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zhuangjie.gulimall.product.entity.CategoryBrandRelationEntity;
import com.zhuangjie.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuangjie.common.utils.PageUtils;
import com.zhuangjie.common.utils.Query;

import com.zhuangjie.gulimall.product.dao.BrandDao;
import com.zhuangjie.gulimall.product.entity.BrandEntity;
import com.zhuangjie.gulimall.product.service.BrandService;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        System.out.println("参数："+params);
        String key = (String) params.get("key");
        QueryWrapper<BrandEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("brand_id",key).or().like("name",key);

        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void updateCategoryBrandRelationNameColumnByBrandId(Long brandId) {
        //  更新的时间还要维护 “pms_category_brand_relation” 冗余的字段 brandName
        BrandEntity db_brandEntity = baseMapper.selectById(brandId);
        UpdateWrapper<CategoryBrandRelationEntity> categoryBrandRelationEntityUpdateWrapper = new UpdateWrapper<>();
        categoryBrandRelationEntityUpdateWrapper.set("brand_name",db_brandEntity.getName()).eq("brand_id",db_brandEntity.getBrandId());
        categoryBrandRelationService.update(categoryBrandRelationEntityUpdateWrapper);
    }
    @Override
    public List<BrandEntity> getBrandsByIds(List<Long> brandIds) {
        return baseMapper.selectList(new QueryWrapper<BrandEntity>().in("brand_id",brandIds));
    }

}