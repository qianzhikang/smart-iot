package com.qzk.common.purview.valid;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qzk.common.purview.domain.entity.Group;
import com.qzk.common.purview.domain.entity.Room;
import com.qzk.common.purview.domain.entity.UserGroup;
import com.qzk.common.purview.mapper.GroupMapper;
import com.qzk.common.purview.mapper.RoomMapper;
import com.qzk.common.purview.mapper.UserGroupMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 校验权限
 * @Date 2023-01-13-14-31
 * @Author qianzhikang
 */
@Component
public class ValidUser {
    @Resource
    private RoomMapper roomMapper;

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private UserGroupMapper userGroupMapper;

    /**
     * 是否拥有修改权限（当前用户是否属于对应的用户组）
     *
     * @param id     用户id
     * @param roomId 场景id
     * @return Boolean
     */
    public Boolean hasPermission(Integer id, Integer roomId) {
        // 1.  根据 roomId 查询 room 表中信息，取出对应的 groupId
        Room room = roomMapper.selectById(roomId);
        Assert.notNull(room, "不存在的场景ID！");
        Integer groupId = room.getGroupId();

        // 2.  根据 groupId 查询 user_group 表中的 userId 判断是否包含 入参id
        List<UserGroup> userGroups = userGroupMapper.selectList(new LambdaQueryWrapper<UserGroup>().eq(UserGroup::getGroupId, groupId));

        // 3.  判断结果集userGroups中是否包含一条记录为当前 userId 即为该方法的返回值
        return userGroups.stream().anyMatch(userGroup -> userGroup.getUserId().equals(id));
    }


    /**
     * 判断当前用户是否是当前场景下所属用户组的拥有者
     *
     * @param userId 用户id
     * @param roomId 场景id
     * @return
     */
    public Boolean isGroupOwner(Integer userId, Integer roomId) {
        // 1.  根据 roomId 查询 room 表中信息，取出对应的 groupId
        Room room = roomMapper.selectById(roomId);
        Assert.notNull(room, "不存在的场景ID！");
        Integer groupId = room.getGroupId();

        // 2.  根据 groupId 查询 t_group 表中的 userId 判断是否为用户组拥有者
        Group group = groupMapper.selectById(groupId);
        Assert.notNull(group, "参数错误，不存在的用户组！");

        // 3.  判断结果 group 中的 ownerId 是否为当前 userId 即为该方法的返回值
        return group.getOwnerId().equals(userId);
    }
}
