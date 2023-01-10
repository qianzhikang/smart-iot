package com.qzk.user.mapper;

import com.qzk.user.domain.entity.Device;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author qianzhikang
* @description 针对表【t_device】的数据库操作Mapper
* @createDate 2023-01-09 16:10:56
* @Entity com.qzk.user.domain.entity.Device
*/
public interface DeviceMapper extends BaseMapper<Device> {
    Integer insertDeviceReturnId(Device device);
}




