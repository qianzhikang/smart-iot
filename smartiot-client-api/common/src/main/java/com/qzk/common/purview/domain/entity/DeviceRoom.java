package com.qzk.common.purview.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author qianzhikang
 * @TableName t_device_room
 */
@TableName(value ="t_device_room")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceRoom implements Serializable {
    /**
     * 设备-房间关联id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 设备编号
     */
    private Integer deviceId;

    /**
     * 房间id
     */
    private Integer roomId;

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