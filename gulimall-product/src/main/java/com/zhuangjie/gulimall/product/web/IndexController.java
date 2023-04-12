package com.zhuangjie.gulimall.product.web;

import com.alibaba.fastjson.JSON;
import com.zhuangjie.gulimall.product.entity.CategoryEntity;
import com.zhuangjie.gulimall.product.service.CategoryService;
import com.zhuangjie.gulimall.product.vo.indexPage.Catelog2Vo;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping({"/","/index.html"})
    public String indexPage(Model model, HttpSession session){


        System.out.println(""+Thread.currentThread().getId());
        //TODO 1、查出所有的1级分类
        List<CategoryEntity> categoryEntities =  categoryService.getLevel1Categorys();

        // 视图解析器进行拼串：
        // classpath:/templates/ +返回值+  .html
        model.addAttribute("categorys",categoryEntities);
        System.out.println(categoryEntities);
        return "index";
    }



    @GetMapping("/index/ctalog")
    @ResponseBody
    public Object catalog2and3() {
//        HashMap<String, List<Catelog2Vo>> catelog2Vos =  categoryService.getCatalog2and3();
//        HashMap<String, List<Catelog2Vo>> catelog2Vos =  categoryService.cacheGetCatalog2and3();
//        HashMap<String, List<Catelog2Vo>> catelog2Vos =  categoryService.getCatalog2and3FormRedis();
//        HashMap<String, List<Catelog2Vo>> catelog2Vos =  categoryService.getCatalog2and3FormRedisAndLock();
//        HashMap<String, List<Catelog2Vo>> catelog2Vos =  categoryService.getCatalog2and3FormRedisCacheRedissonLock();
        HashMap<String, List<Catelog2Vo>> catelog2Vos =  categoryService.getCatalog2and3FormRedisCacheRedissonLock2();
        return catelog2Vos;
    }

    @Autowired
    private RedissonClient redissonClient;



    @GetMapping("/park")
    @ResponseBody
    public String lockDoor() throws InterruptedException {
        RSemaphore park = redissonClient.getSemaphore("park");
        // 如果是 park.acquire(); 会阻塞，而下面这种只会试一下
        boolean b = park.tryAcquire();
        return "占用了?"+b;

    }
    @GetMapping("/go")
    @ResponseBody
    public String gogogo()  {
        RSemaphore park = redissonClient.getSemaphore("park");
        park.release();
        return "一辆车开出走了~";
    }
}
