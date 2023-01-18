package com.qzk.dh11;

import com.qzk.dh11.socket.Dh11Socket;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description
 * @Date 2023-01-18-16-00
 * @Author qianzhikang
 */
@SpringBootApplication
@MapperScan({"com.qzk.dh11.mapper","com.qzk.common.purview"})
@ComponentScan(basePackages = {"com.qzk.common","com.qzk.dh11"})
public class Dh11Application {
    public static void main(String[] args) {
        // 处理websocket无法注入mapper的问题
        SpringApplication springApplication = new SpringApplication(Dh11Application.class);
        ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
        // 在websocket中设置spring配置上下文
        Dh11Socket.setApplicationContext(configurableApplicationContext);
        // 在定时任务中设置spring配置上下文
        //QuartzAcJob.setApplicationContext(configurableApplicationContext);
    }
}
