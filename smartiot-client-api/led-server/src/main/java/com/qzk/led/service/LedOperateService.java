package com.qzk.led.service;

import com.qzk.common.result.RestResult;
import com.qzk.led.dto.LedControlDto;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 设备操作服务接口
 * @Date 2023-01-13-14-22
 * @Author qianzhikang
 */
@Service
public interface LedOperateService {
    /**
     * led灯控制
     * @param ledControlDto led灯信息
     * @return
     */
    RestResult ledControl(HttpServletRequest request,LedControlDto ledControlDto);
}
