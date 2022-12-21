package com.qzk.user.service;

import com.qzk.common.result.RestResult;
import com.qzk.user.domain.entity.Group;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author qianzhikang
* @description 针对表【t_group】的数据库操作Service
* @createDate 2022-12-15 15:41:36
*/
public interface GroupService extends IService<Group> {

    /**
     * 创建用户组接口
     * @param request request请求
     * @param groupName 组名
     * @return
     */
    RestResult create(HttpServletRequest request, String groupName);
}
