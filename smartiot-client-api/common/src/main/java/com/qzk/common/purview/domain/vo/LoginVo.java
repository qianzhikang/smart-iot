package com.qzk.common.purview.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description 登陆返回信息
 * @Date 2022-12-19-15-25
 * @Author qianzhikang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginVo {
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

    /**
     * token
     */
    private String token;
}
