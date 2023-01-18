package com.qzk.dh11.emqx.client;

import com.qzk.common.constant.EmqxConst;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description emqx服务器连接DH11客户端
 * @Date 2023-01-18-16-12
 * @Author qianzhikang
 */
@Configuration
public class Dh11Client {
    /**
     * 获取温湿度客户端（单例）
     * @return EMQX客户端
     */
    @Bean
    public MqttClient getDh11Client(){
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            return new MqttClient(EmqxConst.BROKER,EmqxConst.DH11_API_CLIENT_ID,persistence);
        }catch (MqttException e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
