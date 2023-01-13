package com.qzk.user.service;

import com.qzk.common.purview.domain.entity.Group;
import com.qzk.common.result.RestResult;
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

    /**
     * 查询当前用户的用户组列表
     * @param request 请求参数
     * @return
     */
    RestResult findAll(HttpServletRequest request);

    /**
     * 删除用户组
     * @param request 请求参数
     * @param groupId 用户组id
     * @return
     */
    RestResult remove(HttpServletRequest request, Integer groupId);

    /**
     * 修改用户组名
     * @param request 请求参数
     * @param groupId 用户组id
     * @param groupName 用户组名
     * @return
     */
    RestResult audit(HttpServletRequest request, Integer groupId, String groupName);
}
