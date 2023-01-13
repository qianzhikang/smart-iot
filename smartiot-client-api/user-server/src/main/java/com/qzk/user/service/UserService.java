package com.qzk.user.service;

import com.qzk.common.purview.domain.dto.LoginDto;
import com.qzk.common.purview.domain.dto.RegisterDto;
import com.qzk.common.purview.domain.entity.User;
import com.qzk.common.result.RestResult;

import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author qianzhikang
* @description 针对表【t_user】的数据库操作Service
* @createDate 2022-12-15 15:41:36
*/
public interface UserService extends IService<User> {

    /**
     * 用户登陆
     * @param loginDto 登陆信息
     * @return result
     */
    RestResult<Object> login(LoginDto loginDto);

    /**
     * 用户注册
     * @param registerDto 注册信息
     * @return result
     */
    RestResult<Object> addUser(RegisterDto registerDto);

    /**
     * 用户登出
     * @param request 请求
     * @return result
     */
    RestResult<Object> logout(HttpServletRequest request);
}
