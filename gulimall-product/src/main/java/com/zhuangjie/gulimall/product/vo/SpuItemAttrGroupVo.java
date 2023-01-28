package com.zhuangjie.gulimall.product.vo;

import com.zhuangjie.gulimall.product.vo.spu.Attr;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class SpuItemAttrGroupVo {

    private String groupName;
    private List<Attr> attrs;
}
