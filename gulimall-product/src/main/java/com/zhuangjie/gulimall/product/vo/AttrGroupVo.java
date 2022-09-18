package com.zhuangjie.gulimall.product.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.zhuangjie.gulimall.product.entity.AttrEntity;
import lombok.Data;

/**
 * 属性分组
 *
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-21 20:01:13
 */
@Data
@TableName("pms_attr_group")
public class AttrGroupVo  {
    private static final long serialVersionUID = 1L;

    /**
     * 分组id
     */
    @TableId
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;


    @TableField(exist = false)
    private List<AttrEntity> attrs;

}
