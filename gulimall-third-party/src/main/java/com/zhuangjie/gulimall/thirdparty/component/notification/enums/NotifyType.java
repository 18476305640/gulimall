package com.zhuangjie.gulimall.thirdparty.component.notification.enums;


import com.zhuangjie.gulimall.thirdparty.component.notification.service.EmailService;
import com.zhuangjie.gulimall.thirdparty.component.notification.service.MessageService;
import com.zhuangjie.gulimall.thirdparty.component.notification.service.SmsService;


/**
 * 通知类型
 *
 * @author zhuangjie
 * @date 2023/04/09
 */
public enum NotifyType {
    EMILE("邮箱", EmailService.class),
    SMS("手机短信", SmsService.class);

    private String name;
    private Class<?> type;

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    NotifyType(String name, Class<?> type ) {
        this.name = name;
        this.type = type;

    }
}
