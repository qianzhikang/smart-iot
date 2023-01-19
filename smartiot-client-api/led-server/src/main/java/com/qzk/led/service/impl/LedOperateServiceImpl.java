package com.qzk.led.service.impl;

import com.alibaba.fastjson.JSON;
import com.qzk.common.purview.domain.entity.Device;
import com.qzk.common.purview.valid.ValidUser;
import com.qzk.common.result.RestResult;
import com.qzk.led.dto.LedControlDto;
import com.qzk.led.emqx.dto.LedInfo;
import com.qzk.led.emqx.util.LedUtils;
import com.qzk.led.service.LedOperateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description 灯控服务实现类
 * @Date 2023-01-13-14-22
 * @Author qianzhikang
 */
@Slf4j
@Service
public class LedOperateServiceImpl implements LedOperateService {

    @Resource
    private LedUtils ledUtils;

    @Resource
    private ValidUser validUser;

    /**
     * led灯控制
     *
     * @param ledControlDto led灯信息
     * @param request 请求信息
     * @return RestResult
     */
    @Override
    public RestResult ledControl(HttpServletRequest request, LedControlDto ledControlDto) {
        // 用户id
        Integer id = (Integer) request.getAttribute("id");
        // 判断用户权限，若权限正确将返回 device 对象，不正确返回 null
        Device device = validUser.canOperateDevice(id, ledControlDto.getLedId());
        if (device != null) {
            // 操作方法
            ledOperate(device,ledControlDto.getLedModel());
            return new RestResult<>().success();
        }
        return new RestResult<>().error("操作失败");
    }


    /**
     * 灯控制方法封装
     *
     * @param device   设备信息
     * @param ledModel 设备状态
     */
    private void ledOperate(Device device, String ledModel) {
        LedInfo ledInfo = LedInfo.builder().ledNum(device.getDeviceNum()).ledModel(ledModel).build();
        if (!ledUtils.isConnect()) {
            ledUtils.connect(device.getDeviceUsername(), device.getDevicePassword());
        }
        ledUtils.publish(device.getTopic(), JSON.toJSONString(ledInfo));
    }
}
