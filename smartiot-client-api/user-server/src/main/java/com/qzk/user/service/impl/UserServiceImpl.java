package com.qzk.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzk.common.exception.ApiException;
import com.qzk.common.result.RestResult;
import com.qzk.common.utils.MD5Util;
import com.qzk.user.domain.dto.LoginDto;
import com.qzk.user.domain.entity.User;
import com.qzk.user.service.UserService;
import com.qzk.user.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
* @author qianzhikang
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2022-12-15 15:41:36
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    /**
     * 用户登陆
     * @param loginDto 登陆信息
     * @return result
     */
    @Override
    public RestResult<Object> login(LoginDto loginDto) {
        try {
            User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhone, loginDto.getPhone()));
            Assert.notNull(user,"用户不存在!");
            String salt = user.getSalt();
            String password = loginDto.getPassword();
            if (user.getPassword().equals(MD5Util.getMD5Ciphertext(password,salt))) {
                return new RestResult<>().success("登陆成功");
            }else {
                return new RestResult<>().error("密码错误");
            }
        }catch (Exception e){
            throw new ApiException(e.getMessage());
        }
    }
}




