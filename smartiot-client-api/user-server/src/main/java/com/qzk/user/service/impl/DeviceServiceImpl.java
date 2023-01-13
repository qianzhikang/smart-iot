package com.qzk.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzk.common.purview.domain.dto.DeviceDto;
import com.qzk.common.purview.domain.entity.*;
import com.qzk.common.purview.mapper.*;
import com.qzk.common.result.RestResult;

import com.qzk.user.service.DeviceService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author qianzhikang
 * @description 针对表【t_device】的数据库操作Service实现
 * @createDate 2023-01-09 16:10:56
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device>
        implements DeviceService {

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private UserGroupMapper userGroupMapper;


    @Resource
    private DeviceMapper deviceMapper;


    @Resource
    private DeviceRoomMapper deviceRoomMapper;

    @Resource
    private GroupMapper groupMapper;


    /**
     * 添加设备
     *
     * @param request   请求信息
     * @param deviceDto 设备信息
     * @return RestResult
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult addDevice(HttpServletRequest request, DeviceDto deviceDto) {
        Integer id = (Integer) request.getAttribute("id");

        if (hasPermission(id, deviceDto.getRoomId())) {
            // 建立设备对象
            Device device = new Device();
            BeanUtils.copyProperties(deviceDto, device);

            // 处理设备重复绑定问题
            Device saveDevice = deviceMapper.selectOne(new LambdaQueryWrapper<Device>()
                    .eq(Device::getClientId, device.getClientId()).eq(Device::getDeviceUsername, device.getDeviceUsername()));
            Assert.isNull(saveDevice, "该设备已被绑定！");

            // 插入设备记录
            device.setDeviceStatus(1);
            int deviceRow = deviceMapper.insertDeviceReturnId(device);
            Assert.isTrue(deviceRow == 1, "参数错误，添加设备失败！");

            // 建立关系表对象，插入关系记录
            DeviceRoom deviceRoom = DeviceRoom.builder()
                    .deviceId(device.getId())
                    .roomId(deviceDto.getRoomId())
                    .build();
            int relationRow = deviceRoomMapper.insert(deviceRoom);
            Assert.isTrue(relationRow == 1, "参数错误，添加设备失败！");

            return new RestResult<>().success("插入成功！");
        }
        return new RestResult<>().error("当前用户无权对此场景添加设备！");
    }

    /**
     * 删除设备
     *
     * @param request  请求信息
     * @param roomId   场景id
     * @param deviceId 设备id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult removeDevice(HttpServletRequest request, Integer roomId, Integer deviceId) {

        Integer id = (Integer) request.getAttribute("id");

        // 判断是否是对应用户组的拥有者
        if (isGroupOwner(id, roomId)) {
            int deviceRow = deviceMapper.deleteById(deviceId);
            Assert.isTrue(deviceRow == 1, "不存在的设备！");
            int relationRow = deviceRoomMapper.delete(new LambdaQueryWrapper<DeviceRoom>().eq(DeviceRoom::getDeviceId, deviceId));
            Assert.isTrue(relationRow == 1, "参数错误，删除设备失败！");
            return new RestResult<>().success("删除成功！");
        }
        return new RestResult<>().error("无删除权限！");
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


    /**
     * 判断当前用户是否是当前场景下所属用户组的拥有者
     *
     * @param userId 用户id
     * @param roomId 场景id
     * @return
     */
    private Boolean isGroupOwner(Integer userId, Integer roomId) {
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




