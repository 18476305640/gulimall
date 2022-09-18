package com.zhuangjie.gulimall.product.service.impl;

import com.mysql.cj.util.StringUtils;
import com.zhuangjie.gulimall.product.dao.AttrDao;
import com.zhuangjie.gulimall.product.entity.AttrEntity;
import com.zhuangjie.gulimall.product.service.AttrService;
import com.zhuangjie.gulimall.product.vo.AttrGroupVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuangjie.common.utils.PageUtils;
import com.zhuangjie.common.utils.Query;

import com.zhuangjie.gulimall.product.dao.AttrGroupDao;
import com.zhuangjie.gulimall.product.entity.AttrGroupEntity;
import com.zhuangjie.gulimall.product.service.AttrGroupService;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        String key = (String) params.get("key");
        // select * from pms_attr_group where catelog_id=? and (attr_group_id=key or attr_group_name like %key%)
        QueryWrapper<AttrGroupEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(catelogId!=0,"catelog_id",catelogId);
        if(!StringUtils.isNullOrEmpty(key)) {
            queryWrapper.and(qw->{
                qw.eq("attr_group_id",key).or().like("attr_group_name",key);
            });
        }
        IPage<AttrGroupEntity> page =  baseMapper.selectPage(new Query<AttrGroupEntity>().getPage(params), queryWrapper);
        return new PageUtils(page);

    }


    @Override
    public List<AttrGroupVo> queryAttrGroupWithAttrByCatelogId(Long catelogId) {
        QueryWrapper<AttrGroupEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("catelog_id", catelogId);
        List<AttrGroupEntity> attrGroupEntities = baseMapper.selectList(queryWrapper);
        List<AttrGroupVo> attrGroupVos = attrGroupEntities.stream().map(item -> {
            Long attrGroupId = item.getAttrGroupId();
            AttrGroupVo attrGroupVo = new AttrGroupVo();
            BeanUtils.copyProperties(item, attrGroupVo);

            List<AttrEntity> attrs = baseMapper.queryAttrsByAttrgroupId(attrGroupId);
            attrGroupVo.setAttrs(attrs);
            return attrGroupVo;
        }).collect(Collectors.toList());

        return attrGroupVos;
    }

}