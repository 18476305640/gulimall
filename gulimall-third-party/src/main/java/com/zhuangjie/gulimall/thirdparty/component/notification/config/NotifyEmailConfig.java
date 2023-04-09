package com.zhuangjie.gulimall.thirdparty.component.notification.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.notify.email")
@Data
@Component
public class NotifyEmailConfig {
    // 发件人电子邮箱
    private String from;
    // 邮箱平台的服务主机
    private String host;
    // 授权码
    private String authKey;

}
