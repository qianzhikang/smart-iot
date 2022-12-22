package com.qzk.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzk.common.exception.ApiException;
import com.qzk.common.result.RestResult;
import com.qzk.user.domain.entity.Group;
import com.qzk.user.domain.entity.UserGroup;
import com.qzk.user.mapper.UserGroupMapper;
import com.qzk.user.service.GroupService;
import com.qzk.user.mapper.GroupMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author qianzhikang
 * @description 针对表【t_group】的数据库操作Service实现
 * @createDate 2022-12-15 15:41:36
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group>
        implements GroupService {

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private UserGroupMapper userGroupMapper;

    /**
     * 创建用户组接口
     *
     * @param request   request请求
     * @param groupName 传输dto
     * @return result
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult<Object> create(HttpServletRequest request, String groupName) {
        Integer id = (Integer) request.getAttribute("id");
        Group group = Group.builder()
                .groupName(groupName)
                .ownerId(id)
                .build();
        try {
            // 查询已存在的用户组，防止重复创建同名组
            List<Group> results = groupMapper.selectList(new LambdaQueryWrapper<Group>().eq(Group::getOwnerId, id).eq(Group::getGroupName, groupName));
            if (CollectionUtils.isEmpty(results)) {
                // 插入用户组表
                int groupRow = groupMapper.insertAndReturnId(group);
                Assert.isTrue(groupRow == 1, "创建失败");
                // 插入用户组关系表
                UserGroup userGroup = UserGroup.builder()
                        .groupId(group.getId())
                        .userId(id)
                        .build();
                int userGroupRow = userGroupMapper.insert(userGroup);
                Assert.isTrue(userGroupRow == 1, "创建失败");
                return new RestResult<>().success("创建成功");
            } else {
                return new RestResult<>().error("您已经拥有同名用户组！");
            }
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    /**
     * 查询当前用户的用户组列表
     *
     * @return
     */
    @Override
    public RestResult findAll(HttpServletRequest request) {
        try {
            Integer id = (Integer) request.getAttribute("id");
            List<Group> groups = groupMapper.selectList(new LambdaQueryWrapper<Group>().eq(Group::getOwnerId, id));
            return new RestResult<>().success(groups);
        }catch (Exception e){
            throw new ApiException(e.getMessage());
        }

    }

    /**
     * 删除用户组
     *
     * @param request 请求参数
     * @param groupId 用户组id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult remove(HttpServletRequest request, Integer groupId) {
        int groupRow = groupMapper.delete(new LambdaQueryWrapper<Group>().eq(Group::getId, groupId));
        Assert.isTrue(groupRow == 1,"未找到对应记录,删除用户组失败");
        int userGroupRow = userGroupMapper.delete(new LambdaQueryWrapper<UserGroup>().eq(UserGroup::getGroupId, groupId));
        Assert.isTrue(userGroupRow >= 1,"无对应记录,删除用户组关系记录失败");
        return new RestResult<>().success("删除用户组成功！");
    }

    /**
     * 修改用户组名
     *
     * @param request   请求参数
     * @param groupId   用户组id
     * @param groupName 用户组名
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult audit(HttpServletRequest request, Integer groupId, String groupName) {
        Integer id = (Integer)request.getAttribute("id");
        Group group = groupMapper.selectById(groupId);
        if (group != null && group.getOwnerId().equals(id)){
            group.setGroupName(groupName);
            groupMapper.update(group,new LambdaQueryWrapper<Group>().eq(Group::getId,groupId));
            return new RestResult<>().success("修改成功");
        }else {
            return new RestResult<>().error("无法修改不存在或不属于自己的用户组");
        }
    }
}




