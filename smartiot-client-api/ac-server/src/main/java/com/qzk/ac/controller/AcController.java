package com.qzk.ac.controller;

import com.qzk.ac.dto.AcControlDto;
import com.qzk.ac.service.AcOperateService;
import com.qzk.common.auth.Authentication;
import com.qzk.common.result.RestResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description 空调控制接口
 * @Date 2023-01-19-13-25
 * @Author qianzhikang
 */
@RestController
@RequestMapping("/air")
public class AcController {

    @Resource
    private AcOperateService acOperateService;
    /**
     * 空调控制接口
     * @param acControlDto 空调传输类
     * @return RestResult
     */
    @PostMapping("/control")
    @Authentication
    public RestResult acControl(HttpServletRequest request, @RequestBody AcControlDto acControlDto){
        return acOperateService.acControl(request,acControlDto);
    }
}
