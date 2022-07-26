package com.zhuangjie.gulimall.product.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuangjie.gulimall.product.vo.AttrVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuangjie.common.utils.PageUtils;
import com.zhuangjie.common.utils.Query;

import com.zhuangjie.gulimall.product.dao.AttrDao;
import com.zhuangjie.gulimall.product.entity.AttrEntity;
import com.zhuangjie.gulimall.product.service.AttrService;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public PageUtils queryAttr(Long categoryId, Integer type, Map<String, Object> params) {
        // 根据三级分类id、key、分页信息查找属性
        String key = (String)params.get("key");
        Long page = Long.parseLong(params.get("page").toString());
        Long limit = Long.parseLong(params.get("limit").toString());

        Page<AttrEntity> attrEntityPage = new Page<>(page,limit);
        Page<AttrVo> attrVoPage = baseMapper.queryAttrByCategoryIdAndKey(attrEntityPage, categoryId,type, key);
        return new PageUtils(attrVoPage);
    }

    @Override
    public List<Long> selectSearchAttrIds(List<Long> attrIds) {

        /**
         * SELECT attr_id FROM `pms_attr` WHERE attr_id IN(?) AND search_type = 1
         */
        if (attrIds.size() > 0) {
            return baseMapper.selectSearchAttrIds(attrIds);
        }
        return null;

    }

}