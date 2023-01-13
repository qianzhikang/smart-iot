package com.qzk.user.service;

import com.qzk.common.purview.domain.entity.DeviceRoom;
import com.qzk.common.result.RestResult;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author qianzhikang
* @description 针对表【t_device_room】的数据库操作Service
* @createDate 2023-01-09 16:10:56
*/
public interface DeviceRoomService extends IService<DeviceRoom> {

    /**
     * 查询当前场景下的所有设备信息
     * @param request 请求信息
     * @param roomId  场景id
     * @return
     */
    RestResult getRoomDevices(HttpServletRequest request, Integer roomId);
}
