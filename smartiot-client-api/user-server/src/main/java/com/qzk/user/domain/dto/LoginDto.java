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
    @Pattern(regexp = "0?(13|14|15|17|18)[0-9]{9}",message = "手机号码格式不正确")
    private String phone;
    @NotBlank(message = "密码不能为空")
    private String password;
}
