package com.qzk.user.controller;

import com.qzk.common.auth.Authentication;
import com.qzk.common.result.RestResult;
import com.qzk.user.domain.dto.DeviceDto;
import com.qzk.user.service.DeviceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description 设备管理控制器
 * @Date 2023-01-09-16-02
 * @Author qianzhikang
 */
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Resource
    private DeviceService deviceService;

    /**
     * 添加设备接口
     * @param request 请求信息
     * @param deviceDto 设备信息
     * @return RestResult
     */
    @PostMapping("/add")
    @Authentication
    public RestResult addDevice(HttpServletRequest request, @RequestBody DeviceDto deviceDto){
        return deviceService.addDevice(request,deviceDto);
    }

    /**
     * 删除设备
     * @param request 请求信息
     * @param roomId 场景id
     * @param deviceId 设备id
     * @return RestResult
     */
    @PostMapping("/remove")
    @Authentication
    public RestResult removeDevice(HttpServletRequest request,
                                   @RequestParam("roomId") Integer roomId,
                                   @RequestParam("deviceId") Integer deviceId){
        return deviceService.removeDevice(request,roomId,deviceId);
    }
}
