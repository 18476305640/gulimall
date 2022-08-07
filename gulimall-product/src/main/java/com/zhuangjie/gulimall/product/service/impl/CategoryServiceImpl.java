package com.zhuangjie.gulimall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuangjie.common.utils.PageUtils;
import com.zhuangjie.common.utils.Query;

import com.zhuangjie.gulimall.product.dao.CategoryDao;
import com.zhuangjie.gulimall.product.entity.CategoryEntity;
import com.zhuangjie.gulimall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }
    //菜单树
    @Override
    public List<CategoryEntity> listWithTree() {
        System.out.println("==>"+baseMapper);
        //1、查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);
        //2、组装父子的树形结构
        List<CategoryEntity> levelMenus  = entities.stream().filter(
                categoryEntity -> categoryEntity.getParentCid() == 0
        ).map(item -> {
            item.setChildren(getChildrens(item, entities));
            return item;
        }).sorted(
                (item1, item2) ->  Optional.ofNullable(item1.getSort()).orElse(0) - Optional.ofNullable(item2.getSort()).orElse(0)
        ).collect(Collectors.toList());

        return levelMenus;
    }

    //递归查找所有菜单的子菜单
    private List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> collect = all.stream().filter(
                item -> item.getParentCid() == root.getCatId()
        ).map(item -> {
            item.setChildren(getChildrens(item, all));
            return item;
        }).sorted(
                (item1, item2) ->  Optional.ofNullable(item1.getSort()).orElse(0) - Optional.ofNullable(item2.getSort()).orElse(0)
        ).collect(Collectors.toList());
        return collect;
    }

}