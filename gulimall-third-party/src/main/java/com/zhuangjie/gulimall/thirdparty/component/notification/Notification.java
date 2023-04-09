package com.zhuangjie.gulimall.thirdparty.component.notification;

import com.zhuangjie.gulimall.thirdparty.component.notification.enums.NotifyType;
import com.zhuangjie.gulimall.thirdparty.component.notification.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class Notification {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private List<MessageService> messageServices;

    private volatile Map<Class<MessageService>, List<MessageService>> messageServiceContainer;

    public MessageService getBeansByMessageServiceType(Class<? extends MessageService> messageServiceClass) throws Exception {
        if (messageServiceContainer == null) {
            synchronized (this) {
                if (messageServiceContainer == null) {
                    // 初始化MessageService容器（分类）
                    messageServiceContainer = new HashMap<>();
                    for (MessageService messageService : messageServices) {
                        // 获取实现的所有接口
                        Class<?>[] interfaces = messageService.getClass().getInterfaces();
                        for (Class<?> key : interfaces) {
                            // 判断接口是否为 MessageService 的子接口
                            if (key != MessageService.class && MessageService.class.isAssignableFrom(key)) {
                                List<MessageService> list = messageServiceContainer.computeIfAbsent((Class<MessageService>) key, k -> new ArrayList<>());
                                list.add(messageService);
                            }
                        }
                    }
                }
            }
        }
        // 从MessageService容器获取符合类型的全部实例
        List<MessageService> typeMessageServices = messageServiceContainer.get(messageServiceClass);
        if (typeMessageServices == null || typeMessageServices.size() == 0) throw new Exception("没有找到符合的MessageService实例，进行消息的发送！");
        // 使用随机算法从实例中随机获取一个，返回
        return typeMessageServices.get(new Random().nextInt(typeMessageServices.size()));
    }
    public void send(NotifyType notifyType, String mobile, String content) throws Exception {
        Class<MessageService> type = (Class<MessageService>) notifyType.getType();
        MessageService finalChoiceMessageService = getBeansByMessageServiceType(type);
        finalChoiceMessageService.send(mobile, content);
    }

    public void sendCode(NotifyType notifyType, String mobile, String code) throws Exception {
        Class<MessageService> type = (Class<MessageService>) notifyType.getType();
        MessageService finalChoiceMessageService = getBeansByMessageServiceType(type);
        finalChoiceMessageService.sendCode(mobile, code);
    }

}
