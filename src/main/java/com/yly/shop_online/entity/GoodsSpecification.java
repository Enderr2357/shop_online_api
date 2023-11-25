package com.yly.shop_online.entity;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author ender2357
 * @since 2023-11-09
 */
@Getter
@Setter
@TableName(value = "t_goods_specification",autoResultMap = true)
@ApiModel(value = "GoodsSpecification对象", description = "")
public class GoodsSpecification {

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商品id")
    @TableField("goods_id")
    private String goodsId;

    @ApiModelProperty("规格名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("规格名称")
    @TableField(value = "value",typeHandler = JacksonTypeHandler.class)
    private List<JSONObject> value;

    @ApiModelProperty("逻辑删除(0-未删除，1已删除)")
    @TableField("delete_flag")
    @TableLogic
    private Integer deleteFlag;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
