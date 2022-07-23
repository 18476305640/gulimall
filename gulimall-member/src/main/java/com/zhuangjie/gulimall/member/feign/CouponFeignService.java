package com.zhuangjie.gulimall.member.feign;

import com.zhuangjie.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("gulimall-coupon") //告诉Feign调用注册中心的哪个服务
public interface CouponFeignService {
    //告诉Feign调用该服务的哪个API
    @RequestMapping("/coupon/coupon/member/list")
    public R membercoupons();
}
