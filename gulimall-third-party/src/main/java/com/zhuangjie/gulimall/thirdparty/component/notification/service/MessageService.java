package com.zhuangjie.gulimall.thirdparty.component.notification.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public interface MessageService {
    static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 20, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20), (runnable, threadPoolExecutor)->{
        //拒绝策略是：如果线程池还没有关闭，就交给线程处理。 模拟的是内置的拒绝策略 CallerRunsPolicy
        if (!threadPoolExecutor.isShutdown()) {
            runnable.run();
        }
    });
    default void send(String target, String content) {
        threadPool.execute(()->{
            try {
                sendHandle(target,content);
            } catch (Exception e) {
                // 发送消息失败的处理，比如使用MQ进行重试
                throw new RuntimeException(e);
            }
        });

    }
    default void sendCode(String target, String code)  {
        threadPool.execute(()->{
            try {
                sendCodeHandle(target,code);
            } catch (Exception e) {
                // 发送消息失败的处理，比如使用MQ进行重试
                throw new RuntimeException(e);
            }
        });
    }

    void sendHandle(String target, String content) throws Exception;
    void sendCodeHandle(String target, String code) throws Exception;
}
