package com.zhuangjie.gulimall.order.feign;

import com.zhuangjie.gulimall.order.vo.MemberAddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@FeignClient("gulimall-member")
public interface MemberFeignService {

    @GetMapping("/member/memberreceiveaddress/{memeberId}/addresses")
    List<MemberAddressVo> getAddress(@PathVariable("memeberId") Long memberId);

}