package com.qzk.user.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName t_user_group
 */
@TableName(value ="t_user_group")
@Data
public class UserGroup implements Serializable {
    /**
     * 用户-组关联id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 组id
     */
    private Integer groupId;

    /**
     * 用户id
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