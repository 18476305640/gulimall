package com.zhuangjie.gulimall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zhuangjie.gulimall.product.entity.CategoryBrandRelationEntity;
import com.zhuangjie.gulimall.product.service.CategoryBrandRelationService;
import com.zhuangjie.gulimall.product.vo.indexPage.Catelog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;
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
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 分布式锁
     */
    @Autowired
    private RedissonClient redissonClient;

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
        System.out.println("==>" + baseMapper);
        //1、查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);
        //2、组装父子的树形结构
        List<CategoryEntity> levelMenus = entities.stream().filter(
                categoryEntity -> categoryEntity.getParentCid() == 0
        ).map(item -> {
            item.setChildren(getChildrens(item, entities));
            return item;
        }).sorted(
                (item1, item2) -> Optional.ofNullable(item1.getSort()).orElse(0) - Optional.ofNullable(item2.getSort()).orElse(0)
        ).collect(Collectors.toList());

        return levelMenus;
    }

    @Override
    public void removeMenuByIds(List<Long> ids) {
        baseMapper.deleteBatchIds(ids);
    }


    @CacheEvict(value = "category",allEntries = true)
    @Override
    public void updateCategoryBrandRelationTableCategoryNameColumnByCategoryId(Long catId) {
        // 当category更新时，修改 “pms_category_brand_relation” 表的 冗余字段 "category_name"
        CategoryEntity categoryEntity = baseMapper.selectById(catId);
        UpdateWrapper<CategoryBrandRelationEntity> categoryBrandRelationEntityUpdateWrapper = new UpdateWrapper<>();
        categoryBrandRelationEntityUpdateWrapper
                .set("category_name", categoryEntity.getName())
                .eq("category_id", categoryEntity.getCatId());
        categoryBrandRelationService.update(categoryBrandRelationEntityUpdateWrapper);

    }

        @Cacheable(value = {"category"}, key = "#root.method.name",sync = true)
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        System.out.println("获取一级category分类.....");
        long l = System.currentTimeMillis();
        List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        return categoryEntities;
    }

    @Override
    public HashMap<String, List<Catelog2Vo>> getCatalog2and3() {
        QueryWrapper<CategoryEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("cat_level", 2).or().eq("cat_level", 3);
        List<CategoryEntity> categoryEntities = baseMapper.selectList(wrapper);
        // 添加二三级数据集合
        List<Catelog2Vo> catelog2Vos = new ArrayList<>();
        List<Catelog2Vo.Catelog3Vo> catelog3Vos = new ArrayList<>();

        // 将查询的数据填充到集合中
        for (CategoryEntity categoryEntity : categoryEntities) {
            if (categoryEntity.getCatLevel().equals(2)) {
                Catelog2Vo catelog2Vo = new Catelog2Vo();
                catelog2Vo.setCatalog1Id(categoryEntity.getParentCid());
                catelog2Vo.setId(categoryEntity.getCatId());
                catelog2Vo.setName(categoryEntity.getName());
                catelog2Vo.setCatalog3List(new ArrayList<Catelog2Vo.Catelog3Vo>());
//                BeanUtils.copyProperties(categoryEntity,catelog2Vo);
                catelog2Vos.add(catelog2Vo);
            } else if (categoryEntity.getCatLevel().equals(3)) {
                Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo();
                catelog3Vo.setCatalog2Id(categoryEntity.getParentCid());
                catelog3Vo.setId(categoryEntity.getCatId());
                catelog3Vo.setName(categoryEntity.getName());
//                BeanUtils.copyProperties(categoryEntity,catelog3Vo);
                catelog3Vos.add(catelog3Vo);
            }
        }
        // 数据结构化
        for (Catelog2Vo.Catelog3Vo catelog3Vo : catelog3Vos) {
            Long catalog2Id = catelog3Vo.getCatalog2Id();
            for (Catelog2Vo catelog2Vo : catelog2Vos) {
                if (catalog2Id.equals(catelog2Vo.getId())) {
                    catelog2Vo.getCatalog3List().add(catelog3Vo);
                    break;
                }
            }
        }
        // 将二级整理成map key是一级的id, value是二级数组形式
        HashMap<String, List<Catelog2Vo>> result = new HashMap<>();
        for (Catelog2Vo catelog2Vo : catelog2Vos) {
            List<Catelog2Vo> catelog2Voss = null;
            if ((catelog2Voss = result.get("" + catelog2Vo.getCatalog1Id())) == null) {
                catelog2Voss = new ArrayList<>();
                result.put("" + catelog2Vo.getCatalog1Id(), catelog2Voss);
            }
            catelog2Voss.add(catelog2Vo);

        }

        return result;
    }

    HashMap<String, Object> cacheMap = new HashMap<>();

    /**
     * 使用本地缓存的 " getCatalog2and3 "
     *
     * @return
     */
    @Override
    public HashMap<String, List<Catelog2Vo>> cacheGetCatalog2and3() {
        HashMap<String, List<Catelog2Vo>> cacheGetCatalog2and3 = (HashMap<String, List<Catelog2Vo>>) cacheMap.get("cacheGetCatalog2and3");
        if (cacheGetCatalog2and3 == null) {
            cacheGetCatalog2and3 = getCatalog2and3();
            cacheMap.put("cacheGetCatalog2and3", cacheGetCatalog2and3);
        }
        return cacheGetCatalog2and3;
    }


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public HashMap<String, List<Catelog2Vo>> getCatalog2and3FormRedis() {
        // 从本地获取
        HashMap<String, List<Catelog2Vo>> result = (HashMap<String, List<Catelog2Vo>>) cacheMap.get("cacheGetCatalog2and3");
        if (result == null) {
            synchronized (this) {
                // 双重检查
                if (result != null || (result = (HashMap<String, List<Catelog2Vo>>) cacheMap.get("cacheGetCatalog2and3")) != null) {
                    System.out.println("想要从数据库查询，但经双重检查已经有了，直接返回");
                    return result;
                }
                // 无法从本地获取，准备从redis获取 || 双重检查失败，还是没有
                String jsonData = redisTemplate.opsForValue().get("cacheGetCatalog2and3");
                result = JSON.parseObject(jsonData, new TypeReference<HashMap<String, List<Catelog2Vo>>>() {
                });
                if (result == null) {
                    // 从数据库获取
                    System.out.println("从数据库中查询....");
                    result = getCatalog2and3();
                    // 保存到redis
                    redisTemplate.opsForValue().set("cacheGetCatalog2and3", JSON.toJSONString(result));
                }
                // 保存到本地
                cacheMap.put("cacheGetCatalog2and3", result);
            }
        } else {
            System.out.println("本地已经有了！");
        }
        return result;
    }
    /**
     * 以加锁的方式从reids中获取2-3级分类
     * @return
     */
    private HashMap<String, List<Catelog2Vo>> getCategory2and3levelFromRedis() {
        System.out.println("查询分类信息");
        RReadWriteLock cacheGetCatalog2and3 = redissonClient.getReadWriteLock("category-2and3");
        RLock rLock = cacheGetCatalog2and3.readLock();
        HashMap<String, List<Catelog2Vo>>  result = null;
        try {
            rLock.lock();
            String jsonData = redisTemplate.opsForValue().get("cacheGetCatalog2and3");
            result = JSON.parseObject(jsonData, new TypeReference<HashMap<String, List<Catelog2Vo>>>() {
            });
        } finally {
            rLock.unlock();
        }
        return result;
    }

    @Override
    public HashMap<String, List<Catelog2Vo>> getCatalog2and3FormRedisCacheRedissonLock() {
        HashMap<String, List<Catelog2Vo>> result = getCategory2and3levelFromRedis();
        if (result == null) {
            // 从数据库获取
            RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("category-2and3");
            RLock rLock = readWriteLock.writeLock();
            try {
                rLock.lock();
                String _jsonData = redisTemplate.opsForValue().get("cacheGetCatalog2and3");
                result = JSON.parseObject(_jsonData, new TypeReference<HashMap<String, List<Catelog2Vo>>>() {
                });
                if((result != null)) { return result; }
                System.out.println("从数据库中查询....");
                result = getCatalog2and3();
                // 保存到redis, 设置过期时间
                redisTemplate.opsForValue().set("cacheGetCatalog2and3", JSON.toJSONString(result),30,TimeUnit.SECONDS);
            }finally {
                rLock.unlock();
            }

        }
        return result;

    }


    @Cacheable(value = {"category"},key = "#root.method.name")
    @Override
    public HashMap<String, List<Catelog2Vo>> getCatalog2and3FormRedisCacheRedissonLock2() {
        System.out.println("从数据库中查询....");
        HashMap<String, List<Catelog2Vo>> catalog2and3 = getCatalog2and3();
        return catalog2and3;
    }


    // 从本地或redis获取
    public HashMap<String, List<Catelog2Vo>> getCatalog2and3FromLocal() {
        // 从本地获取
        HashMap<String, List<Catelog2Vo>> result = (HashMap<String, List<Catelog2Vo>>) cacheMap.get("cacheGetCatalog2and3");
        if (result == null) {
            // 无法从本地获取，准备从redis获取 || 双重检查失败，还是没有
            String jsonData = redisTemplate.opsForValue().get("cacheGetCatalog2and3");
            result = JSON.parseObject(jsonData, new TypeReference<HashMap<String, List<Catelog2Vo>>>() {
            });
        }
        return result;
    }
    /**
     * 将从数据库获取的数据保存在本地缓存与redis中
     */
    public void saveCatalog2and3ToLocal(HashMap<String, List<Catelog2Vo>> data) {
        // 保存到本地缓存
        cacheMap.put("cacheGetCatalog2and3", data);
        // 保存到redis
        redisTemplate.opsForValue().set("cacheGetCatalog2and3", JSON.toJSONString(data));
    }



    @CachePut
    @Override
    public HashMap<String, List<Catelog2Vo>> getCatalog2and3FormRedisAndLock() {
        HashMap<String, List<Catelog2Vo>> catalog2and3FromLocal = getCatalog2and3FromLocal();
        if (catalog2and3FromLocal == null) {
            // 分布式锁-加锁
            String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
            for (int i = 0; i < 1000; i++) {
                if (redisTemplate.opsForValue().setIfAbsent("lock", uuid, 300, TimeUnit.SECONDS)) {
                    try {
                        if ((catalog2and3FromLocal = getCatalog2and3FromLocal()) != null) {
                            return catalog2and3FromLocal;
                        }
                        System.out.println("从数据库获取...");
                        catalog2and3FromLocal = getCatalog2and3();
                        // 保存到本地
                        saveCatalog2and3ToLocal(catalog2and3FromLocal);
                    } finally {
                        // 分布式锁-解锁
                        String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1]\n" +
                                "then\n" +
                                "    return redis.call(\"del\",KEYS[1])\n" +
                                "else\n" +
                                "    return 0\n" +
                                "end";
                        redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Arrays.asList("lock"), uuid);
                        return catalog2and3FromLocal;
                    }
                } else {
                    // 加锁失败，休眠重试
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 睡醒检查
                    if((catalog2and3FromLocal = getCatalog2and3FromLocal()) != null) {
                        return catalog2and3FromLocal;
                    }
                }
            }
        }
        System.out.println("本地或redis存在");
        return catalog2and3FromLocal;
    }


    //递归查找所有菜单的子菜单
    private List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> collect = all.stream().filter(
                item -> item.getParentCid().longValue() == root.getCatId().longValue()
        ).map(item -> {
            item.setChildren(getChildrens(item, all));
            return item;
        }).sorted(
                (item1, item2) -> Optional.ofNullable(item1.getSort()).orElse(0) - Optional.ofNullable(item2.getSort()).orElse(0)
        ).collect(Collectors.toList());
        System.out.println("collect=>" + collect);
        return collect;
    }

}