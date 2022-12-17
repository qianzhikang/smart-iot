package com.qzk.user.controller;

import com.qzk.common.result.RestResult;
import com.qzk.user.domain.dto.LoginDto;
import com.qzk.user.domain.dto.RegisterDto;
import com.qzk.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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


    @PostMapping("/register")
    public RestResult<Object> register(@Valid @RequestBody RegisterDto registerDto){
        return userService.addUser(registerDto);
    }

    /**
     * 登陆接口
     * @param loginDto 登陆
     * @return result
     */
    @PostMapping("/login")
    public RestResult<Object> login(@Valid @RequestBody LoginDto loginDto){
        return userService.login(loginDto);
    }
}
