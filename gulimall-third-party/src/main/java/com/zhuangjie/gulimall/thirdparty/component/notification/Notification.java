package com.zhuangjie.gulimall.thirdparty.component.notification;

import com.zhuangjie.gulimall.thirdparty.component.notification.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Notification {

    @Autowired
    private ApplicationContext applicationContext;

    public void send(Class<? extends MessageService> serviceClass, String mobile, String content) throws Exception {
        MessageService smsService = applicationContext.getBean(serviceClass);
        smsService.send(mobile,content);

    }
    public void sendCode(Class<? extends MessageService> serviceClass, String mobile, String code) throws Exception {
        MessageService smsService = applicationContext.getBean(serviceClass);
        smsService.sendCode(mobile,code);
    }

}
