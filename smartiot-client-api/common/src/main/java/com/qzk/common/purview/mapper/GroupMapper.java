package com.qzk.common.purview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qzk.common.purview.domain.entity.Group;

/**
* @author qianzhikang
* @description 针对表【t_group】的数据库操作Mapper
* @createDate 2022-12-15 15:41:36
* @Entity com.qzk.user.domain.entity.Group
*/
public interface GroupMapper extends BaseMapper<Group> {
    Integer insertAndReturnId(Group group);
}




