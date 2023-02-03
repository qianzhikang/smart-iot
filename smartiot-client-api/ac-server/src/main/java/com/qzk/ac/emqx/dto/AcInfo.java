package com.qzk.ac.emqx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description AC控制器EMQX通讯信息传输格式类
 * @Date 2023-01-19-13-39
 * @Author qianzhikang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcInfo {
    /**
     * 空调编号
     */
    private Integer acNum;
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
