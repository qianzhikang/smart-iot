package com.qzk.user.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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