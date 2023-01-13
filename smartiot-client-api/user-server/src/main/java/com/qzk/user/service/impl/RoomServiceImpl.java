package com.qzk.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzk.common.purview.domain.entity.DeviceRoom;
import com.qzk.common.purview.domain.entity.Group;
import com.qzk.common.purview.domain.entity.Room;
import com.qzk.common.purview.domain.entity.UserGroup;
import com.qzk.common.purview.mapper.*;
import com.qzk.common.result.RestResult;

import com.qzk.user.service.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qianzhikang
 * @description 针对表【t_room】的数据库操作Service实现
 * @createDate 2022-12-15 15:41:36
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room>
        implements RoomService {

    @Resource
    private GroupMapper groupMapper;
    @Resource
    private UserGroupMapper userGroupMapper;

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private DeviceRoomMapper deviceRoomMapper;

    @Resource
    private DeviceMapper deviceMapper;

    /**
     * 创建新场景
     *
     * @param request  请求参数
     * @param groupId  用户组id
     * @param roomName 场景名称
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult create(HttpServletRequest request, Integer groupId, String roomName) {
        Integer id = (Integer) request.getAttribute("id");
        // 是否是用户组的拥有者
        if (isOwner(groupId, id)) {
            // 查询同名场景，阻止同名场景创建
            List<Room> rooms = roomMapper.selectList(new LambdaQueryWrapper<Room>().eq(Room::getGroupId, groupId).eq(Room::getRoomName, roomName));
            if (!rooms.isEmpty()) {
                return new RestResult<>().error("已拥有同名场景！");
            }
            roomMapper.insert(Room.builder().groupId(groupId).roomName(roomName).build());
            return new RestResult<>().success("场景创建成功！");
        }
        return new RestResult<>().error("无法在不属于自己的用户组中创建场景！");
    }

    /**
     * 查询场景列表
     *
     * @param request 请求参数
     * @param groupId 用户组id
     * @return
     */
    @Override
    public RestResult getRoomList(HttpServletRequest request, Integer groupId) {
        Integer id = (Integer) request.getAttribute("id");
        if (isUsers(groupId, id)) {
            List<Room> rooms = roomMapper.selectList(new LambdaQueryWrapper<Room>().eq(Room::getGroupId, groupId));
            return new RestResult<>().success(rooms);
        }
        return new RestResult<>().error("非对应用户组成员！");
    }

    /**
     * 修改场景信息
     *
     * @param request  请求参数
     * @param roomId  场景id
     * @param roomName 新名称
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult auditRoom(HttpServletRequest request, Integer roomId, String roomName) {
        Integer id = (Integer) request.getAttribute("id");
        Room room = roomMapper.selectById(roomId);
        if (room != null){
            if (isOwner(room.getGroupId(),id)){
                room.setRoomName(roomName);
                roomMapper.update(room,new LambdaQueryWrapper<Room>().eq(Room::getId,roomId));
                return new RestResult<>().success("修改成功！");
            }
        }
        return new RestResult<>().error("无法修改不属于用户的场景！");
    }

    /**
     * 删除场景信息
     *
     * @param request 请求参数
     * @param roomId   场景id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult removeRoom(HttpServletRequest request, Integer roomId) {
        Integer id = (Integer) request.getAttribute("id");
        Room room = roomMapper.selectById(roomId);
        Assert.notNull(room,"不存在的场景id！");
        if (isOwner(room.getGroupId(),id)){
            // 1.查询设备-场景关联表中，设备的id集合
            List<DeviceRoom> deviceRooms = deviceRoomMapper.selectList(new LambdaQueryWrapper<DeviceRoom>().eq(DeviceRoom::getRoomId, roomId));
            // 获取设备id集合
            List<Integer> deviceIds = deviceRooms.stream().mapToInt(DeviceRoom::getDeviceId).boxed().collect(Collectors.toList());
            // 2.删除设备表内对应设备id
            int deviceRow = deviceMapper.deleteBatchIds(deviceIds);
            Assert.isTrue(deviceRow == deviceIds.size(),"删除失败！");
            // 3.删除设备场景关联表中的记录，按场景id删除
            int deviceRoomRow = deviceRoomMapper.delete(new LambdaQueryWrapper<DeviceRoom>().eq(DeviceRoom::getRoomId, roomId));
            Assert.isTrue(deviceRoomRow == deviceRow,"删除失败！");
            // 4.删除场景表中对应id的记录
            int roomRow = roomMapper.deleteById(roomId);
            Assert.isTrue(roomRow == 1,"删除失败");
        }
        return new RestResult<>().success("删除成功！");
    }


    /**
     * 判断userId对应的用户是否为用户组的拥有者
     *
     * @param groupId 用户组id
     * @param userId  当前用户id
     * @return 布尔值
     */
    private Boolean isOwner(Integer groupId, Integer userId) {
        Group group = groupMapper.selectById(groupId);
        List<UserGroup> userGroups = userGroupMapper.selectList(new LambdaQueryWrapper<UserGroup>().eq(UserGroup::getGroupId, groupId));
        //if (group != null && userGroups != null) {
        //    if (userId.equals(group.getOwnerId())){
        //        if (userGroups.stream().anyMatch(item -> item.getUserId().equals(userId))) {
        //            return true;
        //        }
        //    }
        //}
        // 对应id的用户组非空 && 用户组关系表记录非空 && 当前用户为用户组拥有者 && 关系表中含有用户id记录 返回true
        return group != null && userGroups != null && userId.equals(group.getOwnerId()) && userGroups.stream().anyMatch(item -> item.getUserId().equals(userId));
    }


    /**
     * 判断userId对应的用户是否属于对应用户组
     *
     * @param groupId 用户组id
     * @param userId  当前用户id
     * @return 布尔值
     */
    private Boolean isUsers(Integer groupId, Integer userId) {
        Group group = groupMapper.selectById(groupId);
        List<UserGroup> userGroups = userGroupMapper.selectList(new LambdaQueryWrapper<UserGroup>().eq(UserGroup::getGroupId, groupId));
        // 对应id的用户组非空 && 用户组关系表记录非空 && 关系表中含有用户id记录 返回true
        return group != null && userGroups != null && userGroups.stream().anyMatch(item -> item.getUserId().equals(userId));
    }
}




