package com.zhuangjie.gulimall.auth.feign;

import com.zhuangjie.common.utils.R;
import com.zhuangjie.gulimall.auth.vo.SocialUser;
import com.zhuangjie.gulimall.auth.vo.UserLoginVo;
import com.zhuangjie.gulimall.auth.vo.UserRegistVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("gulimall-member")
public interface MemberFeignService {

    @PostMapping("/member/member/regist")
    R regist(@RequestBody UserRegistVo vo);

    @PostMapping("/member/member/login")
    R login(@RequestBody UserLoginVo vo);

    @PostMapping("/member/member/oauth2/login")
    R oauthlogin(@RequestBody SocialUser socialUser) throws Exception;
}