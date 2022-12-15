package com.qzk.user.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName t_room
 */
@TableName(value ="t_room")
@Data
public class Room implements Serializable {
    /**
     * 房间id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 房间名称
     */
    private String roomName;

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

    /**
     * 所属用户组id
     */
    private Integer groupId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}