package com.qzk.led.controller;

import com.qzk.common.auth.Authentication;
import com.qzk.common.purview.valid.ValidUser;
import com.qzk.common.result.RestResult;
import com.qzk.led.dto.LedControlDto;
import com.qzk.led.service.LedOperateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description LED灯控制接口
 * @Date 2023-01-11-15-04
 * @Author qianzhikang
 */
@RestController
@RequestMapping("/led")
public class LedController {

    @Resource
    private LedOperateService ledOperateService;

    @PostMapping("/control")
    @Authentication
    public RestResult LedControl(HttpServletRequest httpServletRequest, @RequestBody LedControlDto ledControlDto){
        return ledOperateService.ledControl(httpServletRequest,ledControlDto);
    }
}
