package com.zhuangjie.gulimall.thirdparty.component.notification.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.notify.sms")
@Data
@Component
public class NotifySmsConfig {
    private String host;
    private String path;
    // 模板
    private String sign;
    private String skin;
    private String appcode;
}
