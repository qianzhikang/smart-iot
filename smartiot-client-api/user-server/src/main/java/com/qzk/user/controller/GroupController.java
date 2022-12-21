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

    @PostMapping("/create")
    @Authentication
    public RestResult createGroup(HttpServletRequest request, @RequestParam("groupName")String groupName){
        return groupService.create(request,groupName);
    }
}
