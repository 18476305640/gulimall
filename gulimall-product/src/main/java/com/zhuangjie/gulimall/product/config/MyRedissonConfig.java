package com.zhuangjie.gulimall.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Redisson分布式锁配置
 * @author manzhuangjie
 */
@Configuration
public class MyRedissonConfig {
    @Bean(destroyMethod="shutdown")
    public RedissonClient redisson() throws IOException {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://192.168.87.101:6379");
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;

        /*分布式*/
//        @Bean(destroyMethod="shutdown")
//        RedissonClient redisson() throws IOException {
//            Config config = new Config();
//            config.useClusterServers()
//                    .addNodeAddress("127.0.0.1:7004", "127.0.0.1:7001");
//            return Redisson.create(config);
//        }
    }

}
