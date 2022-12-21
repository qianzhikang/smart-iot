package com.qzk.user.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author qianzhikang
 * @TableName t_group
 */
@TableName(value ="t_group")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
     * 创建者id
     */
    private Integer ownerId;

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