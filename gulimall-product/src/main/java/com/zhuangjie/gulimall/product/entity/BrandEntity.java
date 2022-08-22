package com.zhuangjie.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.zhuangjie.gulimall.product.valid.AddGroup;
import com.zhuangjie.gulimall.product.valid.UpdateGroup;
import com.zhuangjie.gulimall.product.valid.examine.ListValue;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

/**
 * 品牌
 * 
 * @author zhuangjie
 * @email 2119299531@qq.com
 * @date 2022-07-21 20:01:13
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@Null(message = "新增时，不需要携带id",groups = AddGroup.class)
	@NotNull(message = "更新时，必须携带id",groups = UpdateGroup.class)
	@TableId
	private Long brandId;
	/**
	 * 品牌名
	 */
	private String name;
	/**
	 * 品牌logo地址
	 */
	@URL(message = "logo必须是一个url地址")
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@ListValue(values = {0,1},groups = {AddGroup.class,UpdateGroup.class})
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@Pattern(regexp = "^[a-zA-Z]$",message = "检索首字母只有一个字母",groups = {AddGroup.class,UpdateGroup.class})
	private String firstLetter;
	/**
	 * 排序
	 */
	@Min(value = 0,groups = {AddGroup.class,UpdateGroup.class})
	private Integer sort;

}
