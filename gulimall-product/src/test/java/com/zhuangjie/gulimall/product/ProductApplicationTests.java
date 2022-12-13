package com.zhuangjie.gulimall.product;

import com.zhuangjie.gulimall.product.entity.BrandEntity;
import com.zhuangjie.gulimall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

@SpringBootTest
class ProductApplicationTests {

    @Autowired
    private BrandService brandService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Test
    void redisTest () {
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        // 保存
        stringStringValueOperations.set("hello", UUID.randomUUID().toString());
        // 查询
        String hello = stringStringValueOperations.get("hello");
        System.out.println("之前存储的值："+hello);

    }
//    @Test
//    void contextLoads() {
//        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setName("华为");
//        brandEntity.setDescript("手机、笔记本、路由器~");
//        brandService.save(brandEntity);
//
//    }
//    @Test
//    public void ossTest() throws FileNotFoundException {
//        // bucket 的概览里
//        String endpoint = "https://oss-cn-shanghai.aliyuncs.com";
//        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
//        String accessKeyId = "LTAI5tAyUdDxcCYZvSj8iVzh";
//        String accessKeySecret = "MlcubNQqBXWxdAloToEIeyab9Hgexa";
//        // 填写Bucket名称
//        String bucketName = "gulimall-zhuangjie-resource";
//
//        // 要上传的文件路径
//        String filePath= "D:\\system\\图片\\IMG_0029.JPG";
//        // 保存在bucket上的“文件名” 或“路径/文件名”
//        String objectName = "2022-8-11/IMG_0029.JPG";
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//        try {
//            InputStream inputStream = new FileInputStream(filePath);
//            // 创建PutObject请求。
//            ossClient.putObject(bucketName, objectName, inputStream);
//        } catch (Exception e) {
//            System.out.println("OSS上传文件失败："+e.getMessage());
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }
//    }

}
