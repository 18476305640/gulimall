package com.zhuangjie.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuangjie.gulimall.product.service.CategoryService;
import com.zhuangjie.gulimall.product.vo.AttrGroupVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhuangjie.gulimall.product.entity.AttrGroupEntity;
import com.zhuangjie.gulimall.product.service.AttrGroupService;
import com.zhuangjie.common.utils.PageUtils;
import com.zhuangjie.common.utils.R;



/**
 * 属性分组
 *
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-21 20:23:27
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("catelogId") Long catelogId){
//        PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params,catelogId);
        return R.ok().put("page", page);
    }

//    @RequestMapping("/list")
//    public R list(@RequestParam("catelogId") Integer catelogId) {
//        QueryWrapper<AttrGroupEntity> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("category_id",catelogId);
//        List<AttrGroupEntity> list = attrGroupService.list(queryWrapper);
//
//        return R.ok().put("data",list);
//    }



    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long[] pathIds = new Long[3];
        pathIds[2]  = attrGroup.getCatelogId();
        for (int i = 1; i >= 0; i--) {
            pathIds[i] = categoryService.getById(pathIds[i+1]).getParentCid();
        }
        attrGroup.setCatelogPath(pathIds);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);


        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

    /*/product/attrgroup/{catelogId}/withattr*/

    @RequestMapping("/{catelogId}/withattr")
    public R attrGroupWithAttr(@PathVariable("catelogId") Long catelogId) {
        List<AttrGroupVo> attrGroupVos =  attrGroupService.queryAttrGroupWithAttrByCatelogId(catelogId);
        return R.ok().put("data",attrGroupVos);
    }

}
