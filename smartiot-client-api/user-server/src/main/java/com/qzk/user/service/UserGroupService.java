package com.qzk.user.service;

import com.qzk.common.purview.domain.entity.UserGroup;
import com.qzk.common.result.RestResult;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author qianzhikang
* @description 针对表【t_user_group】的数据库操作Service
* @createDate 2022-12-15 15:41:36
*/
public interface UserGroupService extends IService<UserGroup> {

    /**
     * 添加用户组成员
     * @param request 请求参数
     * @param groupId 用户组id
     * @param phone 用户id
     * @return result
     */
    RestResult addMember(HttpServletRequest request, Integer groupId, String phone);

    /**
     * 查询对应用户组的成员列表
     * @param request 请求参数
     * @param groupId 用户组id
     * @return result
     */
    RestResult memberList(HttpServletRequest request, Integer groupId);

    /**
     * 删除组内成员
     * @param request 请求参数
     * @param groupId 组id
     * @param memberId 成员id
     * @return result
     */
    RestResult removeMember(HttpServletRequest request, Integer groupId, Integer memberId);
}
