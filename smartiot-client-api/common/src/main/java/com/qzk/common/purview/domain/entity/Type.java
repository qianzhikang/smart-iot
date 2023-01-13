package com.qzk.common.purview.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName t_type
 */
@TableName(value ="t_type")
@Data
public class Type implements Serializable {
    /**
     * 类型id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}