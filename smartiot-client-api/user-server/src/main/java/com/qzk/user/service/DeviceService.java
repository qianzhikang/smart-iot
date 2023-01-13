package com.qzk.user.service;

import com.qzk.common.purview.domain.dto.DeviceDto;
import com.qzk.common.purview.domain.entity.Device;
import com.qzk.common.result.RestResult;

import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author qianzhikang
* @description 针对表【t_device】的数据库操作Service
* @createDate 2023-01-09 16:10:56
*/
public interface DeviceService extends IService<Device> {

    /**
     * 添加设备
     * @param deviceDto 设备信息
     * @param request 请求信息
     * @return RestResult
     */
    RestResult addDevice(HttpServletRequest request, DeviceDto deviceDto);

    /**
     * 删除设备
     * @param request 请求信息
     * @param roomId 场景id
     * @param deviceId 设备id
     * @return
     */
    RestResult removeDevice(HttpServletRequest request,Integer roomId,Integer deviceId);
}
