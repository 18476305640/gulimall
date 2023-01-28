package com.zhuangjie.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuangjie.common.utils.PageUtils;
import com.zhuangjie.gulimall.product.entity.AttrGroupEntity;
import com.zhuangjie.gulimall.product.vo.AttrGroupVo;
import com.zhuangjie.gulimall.product.vo.SpuItemAttrGroupVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-21 20:01:13
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catelogId);

    List<AttrGroupVo> queryAttrGroupWithAttrByCatelogId(Long catelogId);

    List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId);
}

