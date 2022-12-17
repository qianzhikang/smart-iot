package com.qzk.user.domain.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @Description 注册传输类
 * @Date 2022-12-17-16-08
 * @Author qianzhikang
 */
@Data
public class RegisterDto {
    /**
     * 用户名
     */
    private String name;

    /**
     * 手机号
     */
    @Pattern(regexp = "0?(13|14|15|17|18)[0-9]{9}",message = "手机号码格式不正确")
    private String phone;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

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
