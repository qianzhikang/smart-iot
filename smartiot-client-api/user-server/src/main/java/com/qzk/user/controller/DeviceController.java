package com.qzk.user.controller;

import com.qzk.common.auth.Authentication;
import com.qzk.common.result.RestResult;
import com.qzk.user.domain.dto.DeviceDto;
import com.qzk.user.service.DeviceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/add")
    @Authentication
    public RestResult addDevice(HttpServletRequest request, @RequestBody DeviceDto deviceDto){
        return deviceService.addDevice(request,deviceDto);
    }
}
