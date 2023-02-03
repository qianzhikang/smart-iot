package com.qzk.ac.service;

import com.qzk.ac.dto.AcControlDto;
import com.qzk.common.result.RestResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 空调服务接口
 * @Date 2023-01-19-13-26
 * @Author qianzhikang
 */
public interface AcOperateService {
    /**
     * 空调控制
     * @param request  请求域
     * @param acControlDto 空调控制信息
     * @return
     */
    RestResult acControl(HttpServletRequest request, AcControlDto acControlDto);
}


