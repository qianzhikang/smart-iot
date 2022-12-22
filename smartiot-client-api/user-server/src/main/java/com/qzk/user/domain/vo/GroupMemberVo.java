package com.qzk.user.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 用户组成员返回类
 * @Date 2022-12-22-17-42
 * @Author qianzhikang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupMemberVo {
    /**
     * id
     */
    private Integer id;
    /**
     * 用户名
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;
}
