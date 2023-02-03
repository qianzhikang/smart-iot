package com.qzk.ac.service.impl;

import com.alibaba.fastjson.JSON;
import com.qzk.ac.dto.AcControlDto;
import com.qzk.ac.emqx.dto.AcInfo;
import com.qzk.ac.emqx.util.AcUtils;
import com.qzk.ac.service.AcOperateService;
import com.qzk.common.purview.domain.entity.Device;
import com.qzk.common.purview.valid.ValidUser;
import com.qzk.common.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description 空调服务实现类
 * @Date 2023-01-19-13-26
 * @Author qianzhikang
 */
@Slf4j
@Service
public class AcOperateServiceImpl implements AcOperateService {
    @Resource
    private AcUtils acUtils;

    @Resource
    private ValidUser validUser;

    /**
     * 空调控制
     *
     * @param request      请求域
     * @param acControlDto 空调控制信息
     * @return
     */
    @Override
    public RestResult acControl(HttpServletRequest request, AcControlDto acControlDto) {
        // 用户id
        Integer id = (Integer) request.getAttribute("id");
        // 判断用户权限，若权限正确将返回 device 对象，不正确返回 null
        Device device = validUser.canOperateDevice(id, acControlDto.getDeviceId());
        if (device != null) {
            // 操作方法
            acOperate(device,acControlDto);
            return new RestResult<>().success();
        }
        return new RestResult<>().error("操作失败");
    }


    private void acOperate(Device device,AcControlDto acControlDto){
        // 连接emqx 发布消息
        AcInfo acInfo = AcInfo.builder()
                .acNum(device.getDeviceNum())
                .acModel(acControlDto.getAcModel())
                .acPower(acControlDto.getAcPower())
                .temp(acControlDto.getTemp())
                .type(acControlDto.getType())
                .build();
        if (!acUtils.isConnect()) {
            acUtils.connect(device.getDeviceUsername(), device.getDevicePassword());
        }
        acUtils.publish(device.getTopic(),JSON.toJSONString(acInfo));
    }
}
