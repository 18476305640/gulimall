package com.zhuangjie.gulimall.ware.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.zhuangjie.common.utils.R;
import com.zhuangjie.gulimall.ware.feign.MemberFeignService;
import com.zhuangjie.gulimall.ware.vo.FareVo;
import com.zhuangjie.gulimall.ware.vo.MemberAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuangjie.common.utils.PageUtils;
import com.zhuangjie.common.utils.Query;

import com.zhuangjie.gulimall.ware.dao.WareInfoDao;
import com.zhuangjie.gulimall.ware.entity.WareInfoEntity;
import com.zhuangjie.gulimall.ware.service.WareInfoService;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {
    @Autowired
    MemberFeignService memberFeignService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareInfoEntity> wareInfoEntityQueryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (key !=null && !key.isEmpty()) {
            wareInfoEntityQueryWrapper.eq("id",key)
            .or().like("name",key)
            .or().like("address",key)
            .or().like("areacode",key);
        }
        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                wareInfoEntityQueryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public FareVo getFare(Long addrId) {
        FareVo fareVo = new FareVo();
        R r = memberFeignService.addrInfo(addrId);
        MemberAddressVo data = (MemberAddressVo) r.getData("memberReceiveAddress",new TypeReference<MemberAddressVo>() {
        });
        if(data!=null){
            String phone = data.getPhone();
            //123
            String substring = phone.substring(phone.length() - 1, phone.length());
            BigDecimal bigDecimal = new BigDecimal(substring);
            fareVo.setAddress(data);
            fareVo.setFare(bigDecimal);
            return fareVo;
        }
        return null;
    }

}