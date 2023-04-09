package com.zhuangjie.gulimall.thirdparty.component.notification.service;

public interface MessageService {
    default void send(String phone, String content) throws Exception {}
    default void sendCode(String phone, String code) throws Exception {}
}
