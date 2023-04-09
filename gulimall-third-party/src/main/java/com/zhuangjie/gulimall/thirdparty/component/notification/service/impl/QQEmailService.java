package com.zhuangjie.gulimall.thirdparty.component.notification.service.impl;

import com.zhuangjie.gulimall.thirdparty.component.notification.config.NotifyEmailConfig;
import com.zhuangjie.gulimall.thirdparty.component.notification.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
@Component
public class QQEmailService implements EmailService {
    @Autowired
    private NotifyEmailConfig notifyEmailConfig;
    private Session session;

    private Session prepareSession() {
        if (session == null) {
            synchronized (this) {
                if (session == null) {
                    // 获取系统属性
                    Properties properties = System.getProperties();
                    // 设置邮件服务器
                    properties.setProperty("mail.smtp.host", notifyEmailConfig.getHost());
                    // 需要验证用户名密码
                    properties.setProperty("mail.smtp.auth", "true");
                    // 创建默认的回话对象
                    session = Session.getDefaultInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(notifyEmailConfig.getFrom(), notifyEmailConfig.getAuthKey());
                        }
                    });
                }
            }
        }
        return session;
    }

    @Override
    public void sendHandle(String to, String content) throws Exception {
        prepareSession();
        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(notifyEmailConfig.getFrom()));
            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: 头部头字段
            message.setSubject("谷粒商城");
            // 设置消息体
            message.setText(content);
            // 发送消息
            Transport.send(message);
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    @Override
    public void sendCodeHandle(String to, String code) throws Exception {
        prepareSession();
        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(notifyEmailConfig.getFrom()));
            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: 头部头字段
            message.setSubject("谷粒商城");
            // 设置消息体
            message.setText("【谷粒商城】验证码是："+code+" ,5分钟内有效！");
            // 发送消息
            Transport.send(message);
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }



}
