package com.qzk.common.purview.domain.dto;

import lombok.Data;
import java.util.Date;

/**
 * @Description 修改用户信息传输类
 * @Date 2023-02-03-14-08
 * @Author qianzhikang
 */
@Data
public class UserInfoDto {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String name;

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
