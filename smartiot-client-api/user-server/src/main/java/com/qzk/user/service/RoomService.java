package com.qzk.user.service;

import com.qzk.common.result.RestResult;
import com.qzk.user.domain.entity.Room;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author qianzhikang
* @description 针对表【t_room】的数据库操作Service
* @createDate 2022-12-15 15:41:36
*/
public interface RoomService extends IService<Room> {

    /**
     * 创建新场景
     * @param request 请求参数
     * @param groupId 用户组id
     * @param roomName 场景名称
     * @return
     */
    RestResult create(HttpServletRequest request, Integer groupId, String roomName);

    /**
     * 查询场景列表
     * @param request 请求参数
     * @param groupId 用户组id
     * @return
     */
    RestResult getRoomList(HttpServletRequest request, Integer groupId);

    /**
     * 修改场景信息
     * @param request 请求参数
     * @param roomId 场景id
     * @param roomName 新名称
     * @return
     */
    RestResult auditRoom(HttpServletRequest request, Integer roomId, String roomName);

    /**
     * 删除场景信息
     * @param request  请求参数
     * @param roomId    场景id
     * @return
     */
    RestResult removeRoom(HttpServletRequest request, Integer roomId);
}
