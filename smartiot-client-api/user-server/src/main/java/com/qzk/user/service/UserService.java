package com.qzk.user.service;

import com.qzk.common.result.RestResult;
import com.qzk.user.domain.dto.LoginDto;
import com.qzk.user.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
