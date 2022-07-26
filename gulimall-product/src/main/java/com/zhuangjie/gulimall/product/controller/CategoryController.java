package com.zhuangjie.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.zhuangjie.gulimall.product.entity.CategoryEntity;
import com.zhuangjie.gulimall.product.service.CategoryService;
import com.zhuangjie.common.utils.PageUtils;
import com.zhuangjie.common.utils.R;



/**
 * 商品三级分类
 *
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-21 20:23:27
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查出所有分类以及子分类，以树形结构组装起来
     */
    @RequestMapping("/list/tree")
    public R list(@RequestParam Map<String, Object> params){
        List<CategoryEntity> entities =  categoryService.listWithTree();
        return R.ok().put("data", entities);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);
        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }


    /**
     * 修改
     */
    @Transactional
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateById(category);
        // 当category更新时，修改 “pms_category_brand_relation” 表的 冗余字段 "category_name"
		categoryService.updateCategoryBrandRelationTableCategoryNameColumnByCategoryId(category.getCatId());
        return R.ok();
    }
    /**
     * 批量修改
     */
    @RequestMapping("/update/sort")
    public R updateSort(@RequestBody CategoryEntity[] categorys){
        System.out.println("后端正在批量修改中");
        categoryService.updateBatchById(Arrays.asList(categorys));
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		categoryService.removeMenuByIds(Arrays.asList(ids));
        return R.ok();
    }



}
