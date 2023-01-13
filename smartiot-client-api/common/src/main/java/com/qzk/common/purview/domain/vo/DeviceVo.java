package com.qzk.common.purview.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description TODO
 * @Date 2023-01-10-16-39
 * @Author qianzhikang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceVo {
    /**
     * 设备ID
     */
    private Integer id;

    /**
     * 设备类型
     */
    private Integer typeId;

    /**
     * 设备编号
     */
    private Integer deviceNum;

    /**
     * 设备名称
     */
    private String deviceName;

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
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
