package com.qzk.user.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName t_group
 */
@TableName(value ="t_group")
@Data
public class Group implements Serializable {
    /**
     * 用户组id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 组名
     */
    private String groupName;

    /**
     * 组内用户id
     */
    private Integer userId;

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