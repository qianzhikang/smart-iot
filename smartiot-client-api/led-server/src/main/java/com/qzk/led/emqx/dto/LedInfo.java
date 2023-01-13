package com.qzk.led.emqx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description mqtt传输层
 * @Date 2022-11-22-17-11
 * @Author qianzhikang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LedInfo {
    /** 灯编号 */
    private Integer ledNum;
    /** 灯状态 */
    private String ledModel;
}
