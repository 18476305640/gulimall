package com.zhuangjie.gulimall.thirdparty;

import com.aliyun.oss.OSSClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.InputStream;

@SpringBootTest
class GulimallThirdPartyApplicationTests {
    @Resource
    private OSSClient ossClient;

    @Test
    void contextLoads() {
        // 填写Bucket名称
        String bucketName = "gulimall-zhuangjie-resource";

        // 要上传的文件路径
        String filePath= "D:\\system\\图片\\IMG_0029.JPG";
        // 保存在bucket上的“文件名” 或“路径/文件名”
        String objectName = "2022-8-11/IMG_0030.JPG";
        try {
            InputStream inputStream = new FileInputStream(filePath);
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, inputStream);
        } catch (Exception e) {
            System.out.println("OSS上传文件失败："+e.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

}
