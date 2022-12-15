package com.qzk.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzk.user.domain.entity.Group;
import com.qzk.user.service.GroupService;
import com.qzk.user.mapper.GroupMapper;
import org.springframework.stereotype.Service;

/**
* @author qianzhikang
* @description 针对表【t_group】的数据库操作Service实现
* @createDate 2022-12-15 15:41:36
*/
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group>
    implements GroupService{

}




