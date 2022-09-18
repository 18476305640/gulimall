package com.zhuangjie.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuangjie.gulimall.product.entity.BrandEntity;
import com.zhuangjie.gulimall.product.entity.CategoryEntity;
import com.zhuangjie.gulimall.product.service.BrandService;
import com.zhuangjie.gulimall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhuangjie.gulimall.product.entity.CategoryBrandRelationEntity;
import com.zhuangjie.gulimall.product.service.CategoryBrandRelationService;
import com.zhuangjie.common.utils.PageUtils;
import com.zhuangjie.common.utils.R;

import javax.validation.constraints.NotNull;


/**
 * 品牌分类关联
 *
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-21 20:23:27
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /*http://localhost/api/product/categorybrandrelation
    /brands/list?t=1661590427715&catId=363*/



    @RequestMapping("/brands/list")
    public R brandsList(@RequestParam(value = "catId",required = true) Long catId){
        List<BrandEntity> brands =  categoryBrandRelationService.getRelationBrandsByCategoryId(catId);
        return R.ok().put("data", brands);
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam("brandId") Long brandId){
        QueryWrapper<CategoryBrandRelationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("brand_id",brandId);
        List<CategoryBrandRelationEntity> list = categoryBrandRelationService.list(queryWrapper);
        return R.ok().put("data", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelationEntity){

        CategoryEntity categoryEntity = categoryService.getById(categoryBrandRelationEntity.getCategoryId());
        BrandEntity brandEntity = brandService.getById( categoryBrandRelationEntity.getBrandId());

        categoryBrandRelationEntity.setBrandName(brandEntity.getName());
        categoryBrandRelationEntity.setCategoryName(categoryEntity.getName());
        categoryBrandRelationService.save(categoryBrandRelationEntity);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
