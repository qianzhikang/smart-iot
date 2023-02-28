package com.qzk.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzk.common.exception.ApiException;
import com.qzk.common.purview.domain.dto.LoginDto;
import com.qzk.common.purview.domain.dto.RegisterDto;
import com.qzk.common.purview.domain.dto.UserInfoDto;
import com.qzk.common.purview.domain.entity.User;
import com.qzk.common.purview.domain.entity.UserGroup;
import com.qzk.common.purview.domain.vo.LoginVo;
import com.qzk.common.purview.domain.vo.UserInfoVo;
import com.qzk.common.purview.mapper.UserGroupMapper;
import com.qzk.common.purview.mapper.UserMapper;
import com.qzk.common.redis.TokenSaveRedisDao;
import com.qzk.common.result.RestResult;
import com.qzk.common.utils.JwtUtil;
import com.qzk.common.utils.MD5Util;
import com.qzk.common.constant.ApplicationConst;

import com.qzk.common.domain.dto.UserTokenDto;

import com.qzk.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author qianzhikang
 * @description 针对表【t_user】的数据库操作Service实现
 * @createDate 2022-12-15 15:41:36
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserGroupMapper userGroupMapper;

    @Resource
    private TokenSaveRedisDao tokenSaveRedisDao;

    /**
     * 用户登陆
     *
     * @param loginDto 登陆信息
     * @return result
     */
    @Override
    public RestResult<Object> login(LoginDto loginDto) {
        try {
            User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhone, loginDto.getPhone()));
            Assert.notNull(user, "用户不存在!");
            String salt = user.getSalt();
            String password = loginDto.getPassword();

            // 查询用户所在的用户组
            List<UserGroup> userGroups = userGroupMapper.selectList(new LambdaQueryWrapper<UserGroup>().eq(UserGroup::getUserId, user.getId()));
            if (user.getPassword().equals(MD5Util.getMD5Ciphertext(password, salt))) {
                UserTokenDto userTokenDto = new UserTokenDto(user.getId());
                String token = JwtUtil.generateJwt(ApplicationConst.JWT_SECRET, userTokenDto.toMap());
                tokenSaveRedisDao.saveToken(user.getId(), token);
                LoginVo loginVo = new LoginVo();
                loginVo.setUserId(user.getId());
                loginVo.setName(user.getName());
                loginVo.setPhone(user.getPhone());
                loginVo.setAvatar(user.getAvatar());
                loginVo.setGender(user.getGender());
                loginVo.setBirth(user.getBirth());
                loginVo.setToken(token);
                if (!userGroups.isEmpty()){
                    loginVo.setGroupId(userGroups.get(0).getGroupId());
                }
                return new RestResult<>().success("登陆成功",loginVo);
            } else {
                return new RestResult<>().error("密码错误");
            }
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    /**
     * 用户注册
     *
     * @param registerDto 注册信息
     * @return result
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult<Object> addUser(RegisterDto registerDto) {
        try {
            User dbUser = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhone, registerDto.getPhone()));
            Assert.isNull(dbUser, "该手机号已被注册！");
            User user = new User();
            BeanUtils.copyProperties(registerDto, user);
            String salt = MD5Util.generateSalt();
            user.setSalt(salt);
            user.setPassword(MD5Util.getMD5Ciphertext(registerDto.getPassword(), salt));
            int row = userMapper.insert(user);
            Assert.isTrue(row == 1, "注册失败！");
            return new RestResult<>().success("注册成功");
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    /**
     * 用户登出
     *
     * @param request 请求
     * @return result
     */
    @Override
    public RestResult<Object> logout(HttpServletRequest request) {
        Integer id = (Integer) request.getAttribute("id");
        tokenSaveRedisDao.removeToken(id);
        return new RestResult<>().success("已登出");
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户id
     * @return result
     */
    @Override
    public RestResult<Object> getUserInfo(HttpServletRequest request, Integer userId) {
        Integer id = (Integer) request.getAttribute("id");
        Assert.isTrue(id.equals(userId), "非法获取信息");

        User user = userMapper.selectById(id);
        Assert.notNull(user, "用户不存在");
        return new RestResult<>().success(UserInfoVo.builder().
                name(user.getName()).
                phone(user.getPhone())
                .avatar(user.getAvatar())
                .gender(user.getGender())
                .birth(user.getBirth())
                .build());
    }

    /**
     * 修改用户信息
     *
     * @param request     请求域
     * @param userInfoDto 用户信息
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult<Object> auditUserInfo(HttpServletRequest request, UserInfoDto userInfoDto) {
        Integer id = (Integer) request.getAttribute("id");
        Assert.isTrue(id.equals(userInfoDto.getUserId()), "不能修改非本人信息");
        // 2. 按id查询用户信息
        User user = userMapper.selectById(id);
        Assert.notNull(user, "用户不存在或已被禁用");
        // 3. 将修改的信息赋值给user对象
        BeanUtil.copyProperties(userInfoDto, user, CopyOptions.create().ignoreNullValue());
        // 4. 更新数据库
        userMapper.updateById(user);
        return new RestResult<>().success("修改成功");
    }

    /**
     * 修改密码
     *
     * @param request 请求域
     * @param oldPsw  老密码
     * @param newPsw  新密码
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult<Object> auditPsw(HttpServletRequest request, Integer userId, String oldPsw, String newPsw) {
        Integer id = (Integer) request.getAttribute("id");
        Assert.isTrue(id.equals(userId), "不能修改非本人信息");
        // 2. 按id查询用户信息，校验密码
        User user = userMapper.selectById(id);
        Assert.notNull(user, "用户不存在或已被禁用");
        String oldSaltPsw = MD5Util.getMD5Ciphertext(oldPsw, user.getSalt());
        Assert.isTrue(oldSaltPsw.equals(user.getPassword()), "旧密码错误");

        // 3. 新密码加密入库
        String newSaltPsw = MD5Util.getMD5Ciphertext(newPsw, user.getSalt());
        user.setPassword(newSaltPsw);
        int row = userMapper.updateById(user);
        Assert.isTrue(row == 1, "修改失败");
        return new RestResult<>().success("修改成功");
    }
}




