package com.qzk.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzk.common.result.RestResult;
import com.qzk.user.domain.entity.DeviceRoom;
import com.qzk.user.domain.entity.Room;
import com.qzk.user.domain.entity.UserGroup;
import com.qzk.user.domain.vo.DeviceVo;
import com.qzk.user.mapper.DeviceMapper;
import com.qzk.user.mapper.RoomMapper;
import com.qzk.user.mapper.UserGroupMapper;
import com.qzk.user.service.DeviceRoomService;
import com.qzk.user.mapper.DeviceRoomMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author qianzhikang
* @description 针对表【t_device_room】的数据库操作Service实现
* @createDate 2023-01-09 16:10:56
*/
@Service
public class DeviceRoomServiceImpl extends ServiceImpl<DeviceRoomMapper, DeviceRoom>
    implements DeviceRoomService{

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private UserGroupMapper userGroupMapper;


    @Resource
    private DeviceMapper deviceMapper;


    @Resource
    private DeviceRoomMapper deviceRoomMapper;

    /**
     * 查询当前场景下的所有设备信息
     *
     * @param request 请求信息
     * @param roomId  场景id
     * @return
     */
    @Override
    public RestResult getRoomDevices(HttpServletRequest request, Integer roomId) {

        Integer id = (Integer) request.getAttribute("id");

        // 判断是否为用户组成员
        if (hasPermission(id,roomId)){
            List<DeviceRoom> deviceRooms = deviceRoomMapper.selectList(new LambdaQueryWrapper<DeviceRoom>().eq(DeviceRoom::getRoomId, roomId));
            //deviceRooms.stream().mapToInt(DeviceRoom::getDeviceId).mapToObj(s->s).collect(Collectors.toList());
            // boxed 替代 mapToObj , 目的是 返回有泛型的流
            List<Integer> deviceIds = deviceRooms.stream().mapToInt(DeviceRoom::getDeviceId).boxed().collect(Collectors.toList());
            List<DeviceVo> devices = deviceMapper.getDevicesInIds(deviceIds);
            return new RestResult<>().success(devices);
        }
        return new RestResult<>().error("当前用户无权获取场景设备信息！");
    }


    /**
     * 是否拥有修改权限（当前用户是否属于对应的用户组）
     *
     * @param id     用户id
     * @param roomId 场景id
     * @return Boolean
     */
    private Boolean hasPermission(Integer id, Integer roomId) {
        // 1.  根据 roomId 查询 room 表中信息，取出对应的 groupId
        Room room = roomMapper.selectById(roomId);
        Assert.notNull(room, "不存在的场景ID！");
        Integer groupId = room.getGroupId();

        // 2.  根据 groupId 查询 user_group 表中的 userId 判断是否包含 入参id
        List<UserGroup> userGroups = userGroupMapper.selectList(new LambdaQueryWrapper<UserGroup>().eq(UserGroup::getGroupId, groupId));

        // 3.  判断结果集userGroups中是否包含一条记录为当前 userId 即为该方法的返回值
        return userGroups.stream().anyMatch(userGroup -> userGroup.getUserId().equals(id));
    }
}




