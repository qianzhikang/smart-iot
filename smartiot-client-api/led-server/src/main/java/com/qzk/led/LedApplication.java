package com.qzk.led;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description led灯应用
 * @Date 2023-01-11-14-30
 * @Author qianzhikang
 */
@SpringBootApplication
@MapperScan({"com.qzk.led.mapper","com.qzk.common.purview"})
@ComponentScan(basePackages = {"com.qzk.common","com.qzk.led"})
public class LedApplication {
    public static void main(String[] args) {
        SpringApplication.run(LedApplication.class, args);
    }
}
