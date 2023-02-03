package com.qzk.ac.emqx.client;

import com.qzk.common.constant.EmqxConst;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description emqx服务器连接AC控制器客户端
 * @Date 2023-01-19-13-35
 * @Author qianzhikang
 */
@Configuration
@Slf4j
public class AcClient {

    /**
     * 获取AC客户端（单例）
     * @return EMQX客户端
     */
    @Bean
    public MqttClient getAcClient(){
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            return new MqttClient(EmqxConst.BROKER,EmqxConst.AC_API_CLIENT_ID,persistence);
        }catch (MqttException e){
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
