package com.zhuangjie.gulimall.thirdparty.component.notification.service.impl;

import com.zhuangjie.gulimall.thirdparty.component.notification.config.NotifySmsConfig;
import com.zhuangjie.gulimall.thirdparty.component.notification.service.SmsService;
import com.zhuangjie.gulimall.thirdparty.utils.HttpUtils;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class AliyunSmsService implements SmsService {

    @Autowired
    private NotifySmsConfig notifySmsConfig;

    @Override
    public void sendHandle(String target, String content) throws Exception {}

    @Override
    public void sendCodeHandle(String mobile, String code) throws Exception {
        System.out.println("使用阿里短信服务...");
        if (true) return;
        String method = "POST";
        String validTime = "5";

        Map<String, String> headers = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + notifySmsConfig.getAppcode());
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", mobile);
        querys.put("param", "**code**:"+code+",**minute**:"+validTime);
        //smsSignId（短信前缀）和templateId（短信模板），可登录国阳云控制台自助申请。参考文档：http://help.guoyangyun.com/Problem/Qm.html
        querys.put("smsSignId",notifySmsConfig.getSign());
        querys.put("templateId", notifySmsConfig.getSkin());
        Map<String, String> bodys = new HashMap<String, String>();

        /**
         * 重要提示如下:
         * HttpUtils请从\r\n\t    \t* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java\r\n\t    \t* 下载
         *
         * 相应的依赖请参照
         * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
         */
        HttpUtils.doPost(notifySmsConfig.getHost(), notifySmsConfig.getPath(), method, headers, querys, bodys);
    }
}
