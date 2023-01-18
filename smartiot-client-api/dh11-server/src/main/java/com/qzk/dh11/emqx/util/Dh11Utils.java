package com.qzk.dh11.emqx.util;

import com.qzk.common.constant.EmqxConst;
import com.qzk.dh11.emqx.callback.Dh11Callback;
import com.qzk.dh11.emqx.client.Dh11Client;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description 温湿度检测EMQX工具
 * @Date 2023-01-18-16-10
 * @Author qianzhikang
 */
@Component
@Slf4j
public class Dh11Utils {
    @Resource
    private Dh11Client dh11Client;
    @Resource
    private Dh11Callback dh11Callback;

    /**
     * 连接消息服务器
     *
     * @throws MqttException 连接异常
     */
    public void connect(String username,String password) {
        try {
            MqttClient clientInstance = dh11Client.getDh11Client();
            // MQTT 连接选项
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(username);
            connOpts.setPassword(password.toCharArray());
            // 保留会话
            connOpts.setCleanSession(true);
            // 设置回调
            clientInstance.setCallback(dh11Callback);
            // 建立连接
            log.info("Connecting to broker: " + EmqxConst.BROKER);
            clientInstance.connect(connOpts);
            log.info("Connected");
        } catch (MqttException e) {
            log.error(e.getMessage());
            throw new RuntimeException("服务器连接失败");
        }
    }

    /**
     * 订阅主题
     *
     * @param subTopic 主题名称
     * @throws MqttException 订阅异常
     */
    public void subscribe(String subTopic) {
        try {
            MqttClient clientInstance = dh11Client.getDh11Client();
            clientInstance.subscribe(subTopic);
        } catch (MqttException e) {
            log.error(e.getMessage());
            throw new RuntimeException("服务器订阅异常");
        }
    }

    /**
     * 发布消息
     *
     * @param pubTopic 目标主题
     * @param content  消息内容
     * @throws MqttException 发布异常
     */
    public void publish(String pubTopic, String content) {
        try {
            MqttClient clientInstance = dh11Client.getDh11Client();
            MqttMessage mqttMessage = new MqttMessage(content.getBytes());
            // 2：确保消息只发一次  1：发很多次确保收到
            mqttMessage.setQos(2);
            clientInstance.publish(pubTopic, new MqttMessage(content.getBytes()));
        } catch (MqttException e) {
            log.error(e.getMessage());
            throw new RuntimeException("服务器发布异常");
        }
    }


    /**
     * 检查是否连接状态
     * @return
     */
    public Boolean isConnect() {
        MqttClient clientInstance = dh11Client.getDh11Client();
        return clientInstance.isConnected();
    }
}
