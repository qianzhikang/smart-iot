package com.qzk.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzk.common.purview.domain.entity.Group;
import com.qzk.common.purview.domain.entity.User;
import com.qzk.common.purview.domain.entity.UserGroup;
import com.qzk.common.purview.domain.vo.GroupMemberVo;
import com.qzk.common.purview.mapper.GroupMapper;
import com.qzk.common.purview.mapper.UserGroupMapper;
import com.qzk.common.purview.mapper.UserMapper;
import com.qzk.common.result.RestResult;

import com.qzk.user.service.UserGroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author qianzhikang
 * @description 针对表【t_user_group】的数据库操作Service实现
 * @createDate 2022-12-15 15:41:36
 */
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup>
        implements UserGroupService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private UserGroupMapper userGroupMapper;

    /**
     * 添加用户组成员
     *
     * @param request  请求参数
     * @param groupId  用户组id
     * @param phone 用户手机
     * @return result
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult addMember(HttpServletRequest request, Integer groupId, String phone) {
        // 取出用户id
        Integer id = (Integer) request.getAttribute("id");
        Group group = groupMapper.selectById(groupId);
        // 确保用户组存在且修改者为用户组创建者本人
        if (group != null && group.getOwnerId().equals(id)) {
            User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhone,phone));
            Assert.notNull(user,"用户不存在！");
            // 查询用户组成员，避免重复添加
            List<UserGroup> userGroups = userGroupMapper.selectList(new LambdaQueryWrapper<UserGroup>().eq(UserGroup::getGroupId, groupId));
            List<UserGroup> collect = userGroups.stream().filter(item -> item.getUserId().equals(user.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)) {
                return new RestResult<>().error("该用户已经在当前用户组中！");
            }

            // 确保被添加的用户存在且不是自己
            if (user != null && !user.getId().equals(id)) {
                int row = userGroupMapper.insert(UserGroup.builder().groupId(groupId).userId(user.getId()).build());
                return row == 1 ? new RestResult<>().success("添加成功") : new RestResult<>().error("添加失败");
            } else {
                return new RestResult<>().error("无法添加自己或不存在的用户！");
            }
        } else {
            return new RestResult<>().error("无法操作不存在或不属于自己的用户组！");
        }
    }

    /**
     * 查询对应用户组的成员列表
     *
     * @param request 请求参数
     * @param groupId 用户组id
     * @return result
     */
    @Override
    public RestResult memberList(HttpServletRequest request, Integer groupId) {
        Integer id = (Integer) request.getAttribute("id");
        // 查询关系表，取出所有组相关的记录
        List<UserGroup> userGroups = userGroupMapper.selectList(new LambdaQueryWrapper<UserGroup>().eq(UserGroup::getGroupId, groupId));
        if (!CollectionUtils.isEmpty(userGroups)) {
            ArrayList<Integer> userIds = new ArrayList<>();
            // 取出组中用户的id集合
            userGroups.forEach(item -> userIds.add(item.getUserId()));
            // 判断是否为组中用户
            if (userIds.contains(id)) {
                List<User> users = userMapper.selectList(new LambdaQueryWrapper<User>().in(User::getId, userIds));
                // 封装返回数据
                List<GroupMemberVo> result = users.stream().map(item -> {
                    GroupMemberVo groupMemberVo = new GroupMemberVo();
                    BeanUtils.copyProperties(item, groupMemberVo);
                    return groupMemberVo;
                }).collect(Collectors.toList());
                return new RestResult<>().success(result);
            }
        }
        return new RestResult<>().error("非组内成员，无法查看！");
    }

    /**
     * 删除组内成员
     *
     * @param request  请求参数
     * @param groupId  组id
     * @param memberId 成员id
     * @return result
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult removeMember(HttpServletRequest request, Integer groupId, Integer memberId) {
        Integer id = (Integer) request.getAttribute("id");
        Group group = groupMapper.selectById(groupId);
        // 验证是否为用户组创建者
        if (Objects.nonNull(group) && group.getOwnerId().equals(id)) {
            userGroupMapper.delete(new LambdaQueryWrapper<UserGroup>().eq(UserGroup::getGroupId, groupId).eq(UserGroup::getUserId, memberId));
            return new RestResult().success("删除成功！");
        }
        return new RestResult<>().error("无法操作不存在或不属于自己的用户组！");
    }
}




