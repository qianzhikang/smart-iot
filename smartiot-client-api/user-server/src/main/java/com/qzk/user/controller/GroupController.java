package com.qzk.user.controller;

import com.qzk.common.auth.Authentication;
import com.qzk.common.result.RestResult;
import com.qzk.user.service.GroupService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description 用户组控制器
 * @Date 2022-12-21-13-41
 * @Author qianzhikang
 */
@RestController
@RequestMapping("/group")
public class GroupController {

    @Resource
    private GroupService groupService;

    /**
     * 创建用户组
     * @param request 请求参数
     * @param groupName 组名
     * @return
     */
    @PostMapping("/create")
    @Authentication
    public RestResult createGroup(HttpServletRequest request, @RequestParam("groupName")String groupName){
        return groupService.create(request,groupName);
    }

    /**
     * 获取所有用户组
     * @param request 请求参数
     * @return result
     */
    @GetMapping("/all")
    @Authentication
    public RestResult all(HttpServletRequest request){
        return groupService.findAll(request);
    }

    /**
     * 删除用户组
     * @param request 请求参数
     * @param groupId 用户组id
     * @return
     */
    @PostMapping("/delete")
    @Authentication
    public RestResult remove(HttpServletRequest request,@RequestParam("groupId") Integer groupId){
        return groupService.remove(request,groupId);
    }

    /**
     * 修改用户组名
     * @param request 请求参数
     * @param groupId 组id
     * @param groupName 新组名
     * @return
     */
    @PostMapping("/edit")
    @Authentication
    public RestResult audit(HttpServletRequest request,@RequestParam("groupId") Integer groupId,@RequestParam("groupName") String groupName){
        return groupService.audit(request,groupId,groupName);
    }
}
