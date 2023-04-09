package com.zhuangjie.gulimall.thirdparty.component.notification.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 通知短信配置
 *
 * @author zhuangjie
 * @date 2023/04/09
 */
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
