package com.qzk.user.domain.dto;
import lombok.Data;
/**
 * @Description 设备信息传输类
 * @Date 2023-01-09-16-13
 * @Author qianzhikang
 */
@Data
public class DeviceDto {
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
     * 设备类型
     */
    private Integer typeId;

    /**
     * room场景id
     */
    private Integer roomId;
}
