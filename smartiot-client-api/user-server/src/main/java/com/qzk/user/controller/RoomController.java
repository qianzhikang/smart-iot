package com.qzk.user.controller;

import com.qzk.common.auth.Authentication;
import com.qzk.common.result.RestResult;
import com.qzk.user.service.RoomService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description 场景控制器
 * @Date 2022-12-23-14-17
 * @Author qianzhikang
 */
@RestController
@RequestMapping("/room")
public class RoomController {

    @Resource
    private RoomService roomService;

    /**
     * 创建场景接口
     * @param request 请求
     * @param groupId 用户组id
     * @param roomName 场景名称
     * @return
     */
    @PostMapping("/create")
    @Authentication
    public RestResult createRoom(HttpServletRequest request,
                                 @RequestParam("groupId") Integer groupId,
                                 @RequestParam("roomName") String roomName){
        return roomService.create(request,groupId,roomName);
    }


    /**
     * 查询场景列表
     * @param request 请求参数
     * @param groupId 用户组id
     * @return
     */
    @GetMapping("/list")
    @Authentication
    public RestResult getRoomList(HttpServletRequest request,
                                  @RequestParam("groupId") Integer groupId){
        return roomService.getRoomList(request,groupId);

    }

    /**
     * 修改场景信息
     * @param request 请求参数
     * @param roomId 场景id
     * @param roomName 新名称
     * @return
     */
    @PostMapping("/edit")
    @Authentication
    public RestResult audit(HttpServletRequest request,
                            @RequestParam("roomId") Integer roomId,
                            @RequestParam("roomName") String roomName){
        return roomService.auditRoom(request,roomId,roomName);
    }

}
