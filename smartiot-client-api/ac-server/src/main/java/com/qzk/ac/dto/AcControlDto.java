package com.qzk.ac.dto;

import lombok.Data;

/**
 * @Description 空调控制传输类
 * @Date 2023-01-19-13-29
 * @Author qianzhikang
 */
@Data
public class AcControlDto {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 设备id
     */
    private Integer deviceId;
    /**
     * 模式（冷/热）0：cool  1：heat
     */
    private Integer acModel;
    /**
     * 电源模式
     */
    private String acPower;
    /**
     * 温度（16-30）
     */
    private Integer temp;
    /**
     * type 区分为开关机还是调温度 0：开关机，1：调温度，2：切模式（冷/热）
     */
    private Integer type;
}
