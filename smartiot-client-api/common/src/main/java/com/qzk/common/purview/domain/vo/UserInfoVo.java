package com.qzk.common.purview.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description 用户信息返回类
 * @Date 2023-02-03-14-05
 * @Author qianzhikang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoVo {
    /**
     * 用户名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别 0:男 1:女
     */
    private Integer gender;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 出生年月
     */
    private Date birth;
}
