package com.zhuangjie.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author manzhuangjie
 */
@EnableRedisHttpSession
@EnableFeignClients(basePackages = "com.zhuangjie.gulimall.product.feign")
@EnableDiscoveryClient //开启服务发现 - nacos
@SpringBootApplication
@EnableCaching
//@MapperScan("com.zhuangjie.gulimall.product.dao")
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

}
