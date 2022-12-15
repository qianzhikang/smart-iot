package com.qzk.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @Description 用户服务应用
 * @Date 2022-12-15-14-59
 * @Author qianzhikang
 */
@SpringBootApplication
@MapperScan("com.qzk.user.mapper")
@ComponentScan(basePackages = {"com.qzk.common","com.qzk.user"})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
