//package com.zhuangjie.gulimall.order.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import io.seata.rm.datasource.DataSourceProxy;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import javax.sql.DataSource;
//
///**
// * 使用Seata对数据源进行代理
// * Created by macro on 2019/11/11.
// */
//
////用于Seata的数据源配置
//@Configuration
//public class DataSourceProxyConfig {
//
//
//    /**
//     * 从配置文件获取属性构造datasource，注意前缀，这里用的是druid，根据自己情况配置,
//     * 原生datasource前缀取"spring.datasource"
//     *
//     * @return
//     */
//    @ConfigurationProperties(prefix = "spring.datasource")
//    @Bean
//    public DruidDataSource druidDataSource(){
//        return new DruidDataSource();
//    }
//    /**
//     * 构造datasource代理对象，替换原来的datasource
//     *
//     * @param druidDataSource
//     * @return
//     */
//    @Primary
//    @Bean("dataSource")
//    public DataSourceProxy dataSourceProxy(DataSource druidDataSource) {
//        return new DataSourceProxy(druidDataSource);
//    }
//}