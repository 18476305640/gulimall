package com.zhuangjie.gulimall.thirdparty.controller;

import com.zhuangjie.common.utils.R;
import com.zhuangjie.gulimall.thirdparty.component.notification.Notification;
import com.zhuangjie.gulimall.thirdparty.component.notification.enums.NotifyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class SmsController {
    @Autowired
    private Notification notification;

    @GetMapping("/sendcode")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        try {
            notification.sendCode(NotifyType.EMILE, phone, code);
        } catch (Exception e) {
            System.err.println("[ERROR] 短信发送失败，原因：" + e.getMessage());
            return R.error("短信发送失效！");
        }
        System.out.println("[发短信] " + phone + "，短信发送成功！");
        return R.ok("短信发送成功！");
    }
}
