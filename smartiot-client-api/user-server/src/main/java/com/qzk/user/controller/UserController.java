package com.qzk.user.controller;

import com.qzk.common.auth.Authentication;
import com.qzk.common.purview.domain.dto.LoginDto;
import com.qzk.common.purview.domain.dto.RegisterDto;
import com.qzk.common.purview.domain.dto.UserInfoDto;
import com.qzk.common.result.RestResult;
import com.qzk.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Description 用户控制器
 * @Date 2022-12-15-18-53
 * @Author qianzhikang
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 注册接口
     *
     * @param registerDto 注册参数
     * @return result
     */
    @PostMapping("/register")
    public RestResult<Object> register(@Valid @RequestBody RegisterDto registerDto) {
        return userService.addUser(registerDto);
    }

    /**
     * 登陆接口
     *
     * @param loginDto 登陆
     * @return result
     */
    @PostMapping("/login")
    public RestResult<Object> login(@Valid @RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }


    /**
     * 获取用户信息
     * @param userId 用户id
     * @return result
     */
    @GetMapping("/info")
    @Authentication
    public RestResult<Object> getUserInfo(HttpServletRequest request,@RequestParam("userId") Integer userId) {
        return userService.getUserInfo(request,userId);
    }


    /**
     * 修改用户信息
     * @param userInfoDto 用户id
     * @return result
     */
    @PostMapping("/info-audit")
    @Authentication
    public RestResult<Object> auditUserInfo(HttpServletRequest request, @RequestBody UserInfoDto userInfoDto) {
        return userService.auditUserInfo(request,userInfoDto);
    }

    /**
     * 修改密码
     * @param request 请求域
     * @param oldPsw  老密码
     * @param newPsw  新密码
     * @return
     */
    @PostMapping("/psw-update")
    @Authentication
    public RestResult<Object> pswUpdate(HttpServletRequest request,
                                        @RequestParam("userId") Integer userId,
                                        @RequestParam("oldPsw") String oldPsw,
                                        @RequestParam("newPsw") String newPsw) {
        return userService.auditPsw(request,userId,oldPsw,newPsw);
    }




    /**
     * 登出接口
     *
     * @param request 请求参数
     * @return result
     */
    @PostMapping("/logout")
    @Authentication
    public RestResult<Object> logout(HttpServletRequest request) {
        return userService.logout(request);
    }

}
