package com.zhuangjie.gulimall.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.zhuangjie.common.enums.BizCodeEnume;
import com.zhuangjie.gulimall.member.exception.PhoneExsitException;
import com.zhuangjie.gulimall.member.exception.UsernameExistException;
import com.zhuangjie.gulimall.member.feign.CouponFeignService;
import com.zhuangjie.gulimall.member.vo.MemberLoginVo;
import com.zhuangjie.gulimall.member.vo.MemberRegistVo;
import com.zhuangjie.gulimall.member.vo.SocialUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zhuangjie.gulimall.member.entity.MemberEntity;
import com.zhuangjie.gulimall.member.service.MemberService;
import com.zhuangjie.common.utils.PageUtils;
import com.zhuangjie.common.utils.R;



/**
 * 会员
 *
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-22 11:33:33
 */
@RestController
@RequestMapping("member/member")
public class MemberController {


    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponFeignService couponFeignService;

    @PostMapping("/regist")
    public R regist(@RequestBody MemberRegistVo vo){

        try{
            memberService.regist(vo);
        }catch (PhoneExsitException e){
            return R.error(BizCodeEnume.PHONE_EXIST_EXCEPTION.getCode(), BizCodeEnume.PHONE_EXIST_EXCEPTION.getMsg());
        }catch (UsernameExistException e){
            return R.error(BizCodeEnume.USER_EXIST_EXCEPTION.getCode(), BizCodeEnume.USER_EXIST_EXCEPTION.getMsg());
        }

        return R.ok();
    }



    @RequestMapping("/getCoupons")
    public R list(){
        R membercoupons = couponFeignService.membercoupons();
        Object coupons = membercoupons.get("coupons");
        return R.ok().put("coupons",coupons).put("message","远程调用成功~");
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }
    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
    @PostMapping("/login")
    public R login(@RequestBody MemberLoginVo vo){

        MemberEntity entity =  memberService.login(vo);
        if(entity!=null){
            return R.ok().data(entity);
        }else{
            return R.error(BizCodeEnume.LOGINACCT_PASSWORD_INVAILD_EXCEPTION.getCode(), BizCodeEnume.LOGINACCT_PASSWORD_INVAILD_EXCEPTION.getMsg());
        }
    }
    @PostMapping("/oauth2/login")
    public R oauthlogin(@RequestBody SocialUser socialUser) throws Exception {

        MemberEntity entity =  memberService.login(socialUser);
        if(entity!=null){
            //TODO 1、登录成功处理
            return R.ok().data(entity);
        }else{
            return R.error(BizCodeEnume.LOGINACCT_PASSWORD_INVAILD_EXCEPTION.getCode(), BizCodeEnume.LOGINACCT_PASSWORD_INVAILD_EXCEPTION.getMsg());
        }
    }


}
