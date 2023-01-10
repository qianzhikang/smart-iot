package com.qzk.user.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @author qianzhikang
 * @TableName t_device
 */
@TableName(value ="t_device")
@Data
public class Device implements Serializable {
    /**
     * 设备ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 设备编号
     */
    private Integer deviceNum;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备端id（MQTT）
     */
    private String clientId;

    /**
     * 设备唯一用户名（MQTT）
     */
    private String deviceUsername;

    /**
     * 设备唯一密码（MQTT）
     */
    private String devicePassword;

    /**
     * 设备主题
     */
    private String topic;

    /**
     * 设备状态：0:禁用，1:可用
     */
    private Integer deviceStatus;

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
     * 设备类型
     */
    private Integer typeId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}