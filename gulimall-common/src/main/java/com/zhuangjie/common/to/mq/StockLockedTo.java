package com.zhuangjie.common.to.mq;

import lombok.Data;

import java.util.List;

@Data
public class StockLockedTo {

    private Long id; //库存工作单的id
    private StockDetailTo detail;//工作详情的所有id
}
