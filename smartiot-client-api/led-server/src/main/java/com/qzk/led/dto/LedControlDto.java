package com.qzk.led.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description LED灯控制传输类
 * @Date 2023-01-13-16-17
 * @Author qianzhikang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LedControlDto {
    /** Led灯设备id */
    private Integer ledId;
    /** Led灯状态 */
    private String ledModel;
}
