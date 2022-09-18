package com.zhuangjie.gulimall.product.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zhuangjie.gulimall.product.entity.CategoryBrandRelationEntity;
import com.zhuangjie.gulimall.product.service.CategoryBrandRelationService;
import com.zhuangjie.gulimall.product.valid.AddGroup;
import com.zhuangjie.gulimall.product.valid.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhuangjie.gulimall.product.entity.BrandEntity;
import com.zhuangjie.gulimall.product.service.BrandService;
import com.zhuangjie.common.utils.PageUtils;
import com.zhuangjie.common.utils.R;

import javax.validation.Valid;


/**
 * 品牌
 *
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-21 20:23:27
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;



    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@Validated({AddGroup.class}) @RequestBody BrandEntity brand){
		brandService.save(brand);

        return R.ok();
    }

    /**
     * 修改
     */
    @Transactional
    @RequestMapping("/update")
    public R update(@Validated({UpdateGroup.class}) @RequestBody BrandEntity brand){
		brandService.updateById(brand);

		// 更新的时间还要维护 “pms_category_brand_relation” 冗余的字段 brandName
        brandService.updateCategoryBrandRelationNameColumnByBrandId(brand.getBrandId());

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
