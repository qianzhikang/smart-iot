package com.qzk.user.mapper;

import com.qzk.user.domain.entity.Device;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qzk.user.domain.vo.DeviceVo;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
* @author qianzhikang
* @description 针对表【t_device】的数据库操作Mapper
* @createDate 2023-01-09 16:10:56
* @Entity com.qzk.user.domain.entity.Device
*/
public interface DeviceMapper extends BaseMapper<Device> {
    /**
     * 插入设备信息，返回设备id赋值给device
     * @param device 设备信息
     * @return 受影响行数
     */
    Integer insertDeviceReturnId(Device device);


    /**
     * 获取所有id对应的设备信息
     * @param ids 设备id集合
     * @return List<DeviceVo>
     */
    List<DeviceVo> getDevicesInIds(List<Integer> ids);
}




