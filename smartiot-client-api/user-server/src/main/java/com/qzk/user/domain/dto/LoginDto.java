package com.qzk.user.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Description 登陆Dto
 * @Date 2022-12-15-19-04
 * @Author qianzhikang
 */
@Data
public class LoginDto {
    @Pattern(regexp = "/^1((34[0-8])|(8\\d{2})|(([35][0-35-9]|4[579]|66|7[35678]|9[1389])\\d{1}))\\d{7}$/\n",message = "手机号码格式不正确")
    private String phone;
    @NotBlank(message = "密码不能为空")
    private String password;
}
