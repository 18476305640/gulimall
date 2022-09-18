package com.zhuangjie.gulimall.product.enums;

import lombok.Getter;

@Getter
public enum  AttrEnum {
    SALE_ATTR(0,"销售属性"),
    BASE_ATTR(1,"基本属性");
    private int type;
    private String typeName;

    AttrEnum(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }


}
