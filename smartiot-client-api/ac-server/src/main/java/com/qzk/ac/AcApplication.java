package com.qzk.ac;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description 空调控制应用
 * @Date 2023-01-19-13-23
 * @Author qianzhikang
 */
@SpringBootApplication
@MapperScan("com.qzk.common.purview.mapper")
@ComponentScan(basePackages = {"com.qzk.common","com.qzk.ac"})
public class AcApplication {
    public static void main(String[] args) {
        SpringApplication.run(AcApplication.class, args);
    }
}