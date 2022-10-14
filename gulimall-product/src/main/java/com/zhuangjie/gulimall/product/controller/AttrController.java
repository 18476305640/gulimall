package com.zhuangjie.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zhuangjie.common.utils.Query;
import com.zhuangjie.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.zhuangjie.gulimall.product.entity.AttrGroupEntity;
import com.zhuangjie.gulimall.product.entity.ProductAttrValueEntity;
import com.zhuangjie.gulimall.product.enums.AttrEnum;
import com.zhuangjie.gulimall.product.service.AttrAttrgroupRelationService;
import com.zhuangjie.gulimall.product.service.CategoryService;
import com.zhuangjie.gulimall.product.service.ProductAttrValueService;
import com.zhuangjie.gulimall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.zhuangjie.gulimall.product.entity.AttrEntity;
import com.zhuangjie.gulimall.product.service.AttrService;
import com.zhuangjie.common.utils.PageUtils;
import com.zhuangjie.common.utils.R;



/**
 * 商品属性
 *
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-21 20:23:27
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;
    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    ///product/attr/info/{attrId}

    // /product/attr/base/listforspu/{spuId}
    @GetMapping("/base/listforspu/{spuId}")
    public R baseAttrlistforspu(@PathVariable("spuId") Long spuId){

        List<ProductAttrValueEntity> entities = productAttrValueService.baseAttrlistforspu(spuId);

        return R.ok().put("data",entities);
    }
    ///product/attr/update/{spuId}
    @PostMapping("/update/{spuId}")
    public R updateSpuAttr(@PathVariable("spuId") Long spuId,
                           @RequestBody List<ProductAttrValueEntity> entities){

        productAttrValueService.updateSpuAttr(spuId,entities);

        return R.ok();
    }
    /**
     * 查询规格属性
     */
    @RequestMapping("/base/list/{categoryId}")
    public R baseList(@RequestParam Map<String, Object> params, @PathVariable("categoryId") Long categoryId ){
        // 根据三级分类id、key、分页信息查找属性
        PageUtils page = attrService.queryAttr(categoryId, AttrEnum.BASE_ATTR.getType(),params);
        return R.ok().put("page", page);
    }
    /**
     * 查询销售属性
     */
    @RequestMapping("/sale/list/{categoryId}")
    public R saleList(@RequestParam Map<String, Object> params, @PathVariable("categoryId") Long categoryId ){
        // 根据三级分类id、key、分页信息查找属性
        PageUtils page = attrService.queryAttr(categoryId, AttrEnum.SALE_ATTR.getType(),params);
        return R.ok().put("page", page);
    }


    @Autowired
    private CategoryService categoryService;
    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId){
        AttrVo attrVo = new AttrVo();
        AttrEntity attr = attrService.getById(attrId);
        BeanUtils.copyProperties(attr,attrVo);

        QueryWrapper<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntityQueryWrapper = new QueryWrapper<>();
        attrAttrgroupRelationEntityQueryWrapper.eq("attr_id",attrId);
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationService.getOne(attrAttrgroupRelationEntityQueryWrapper);

        if (attrAttrgroupRelationEntity != null) {
            attrVo.setAttrGroupId(attrAttrgroupRelationEntity.getAttrGroupId());
        }
        /*三级分类的id*/
        Long[] idPath = new Long[3];
        idPath[2] = attr.getCatelogId();
        if (idPath[2] != null) { // 如果没有category_id就不要查找 idPath了
            for (int i = 1; i >=0; i--) {
                idPath[i] = categoryService.getById(idPath[i+1]).getParentCid();
            }
            System.out.println(idPath);
            attrVo.setCatelogIdPath(idPath);
        }

        return R.ok().put("attrVo", attrVo);
    }

    /**
     * 保存
     */
    @Transactional /*事务*/
    @RequestMapping("/save")
    public R save(@RequestBody AttrVo attrVo){
        // 添加属性记录
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo,attrEntity);
		attrService.save(attrEntity);

		/*获取新增属性记录的id，下面的关联需要*/
        QueryWrapper<AttrEntity> attrEntityQueryWrapper = new QueryWrapper<>();
        attrEntityQueryWrapper.eq("catelog_id",attrVo.getCatelogId())
                .eq("attr_type",attrVo.getAttrType())
                .eq("attr_name",attrVo.getAttrName());
        AttrEntity saveRecord = attrService.getOne(attrEntityQueryWrapper);
        attrVo.setAttrId(saveRecord.getAttrId());
        attrVo.setAttrSort(0);

        // 如果是规则属性，添加与属性分组的关联记录
        if(attrVo.getAttrType() == AttrEnum.BASE_ATTR.getType()) {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(attrVo,attrAttrgroupRelationEntity);
            attrAttrgroupRelationService.save(attrAttrgroupRelationEntity);
        }


        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrVo attrVo){
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo,attrEntity);
        attrService.updateById(attrEntity);
        /*更新关联表*/
        if(attrVo.getAttrType() != AttrEnum.BASE_ATTR.getType()) return R.ok();
        QueryWrapper<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntityQueryWrapper = new QueryWrapper<>();
        attrAttrgroupRelationEntityQueryWrapper.eq("attr_id",attrVo.getAttrId());
        if (attrAttrgroupRelationService.list(attrAttrgroupRelationEntityQueryWrapper).size() > 0 ) {
            // 更新
            UpdateWrapper<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntityUpdateWrapper = new UpdateWrapper<>();
            attrAttrgroupRelationEntityUpdateWrapper.set("attr_group_id",attrVo.getAttrGroupId())
                .eq("attr_id",attrVo.getAttrId());

            attrAttrgroupRelationService.update(attrAttrgroupRelationEntityUpdateWrapper);
        }else {
            // 保存
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrId(attrVo.getAttrId());
            attrAttrgroupRelationEntity.setAttrGroupId(attrVo.getAttrGroupId());
            attrAttrgroupRelationEntity.setAttrSort(0);
            attrAttrgroupRelationService.save(attrAttrgroupRelationEntity);
        }

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
