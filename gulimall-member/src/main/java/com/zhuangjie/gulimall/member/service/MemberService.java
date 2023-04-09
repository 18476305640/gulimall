package com.zhuangjie.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuangjie.common.utils.PageUtils;
import com.zhuangjie.gulimall.member.entity.MemberEntity;
import com.zhuangjie.gulimall.member.exception.PhoneExsitException;
import com.zhuangjie.gulimall.member.exception.UsernameExistException;
import com.zhuangjie.gulimall.member.vo.MemberRegistVo;

import java.util.Map;

/**
 * 会员
 *
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-22 11:33:33
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void regist(MemberRegistVo vo);

    void checkPhoneUnique(String phone) throws PhoneExsitException;

    void checkUsernameUnique(String username) throws UsernameExistException;
}

