package com.qzk.ac.emqx.util;

import com.qzk.ac.emqx.callback.AcCallback;
import com.qzk.ac.emqx.client.AcClient;
import com.qzk.common.constant.EmqxConst;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description AC控制工具类
 * @Date 2023-01-19-13-42
 * @Author qianzhikang
 */
@Component
@Slf4j
public class AcUtils {

    @Resource
    private AcClient acClient;


    @Resource
    private AcCallback acCallback;


    /**
     * 连接消息服务器
     *
     * @throws MqttException 连接异常
     */
    public void connect(String username,String password) {
        try {
            MqttClient clientInstance = acClient.getAcClient();
            // MQTT 连接选项
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(username);
            connOpts.setPassword(password.toCharArray());
            // 保留会话
            connOpts.setCleanSession(true);
            // 设置回调
            clientInstance.setCallback(acCallback);
            // 建立连接
            log.info("Connecting to broker: " + EmqxConst.BROKER);
            clientInstance.connect(connOpts);
            log.info("Connected");
            // 为回调方法赋值
            acCallback.setUsername(username);
            acCallback.setPassword(password);
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
            MqttClient clientInstance = acClient.getAcClient();
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
            MqttClient clientInstance = acClient.getAcClient();
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
        MqttClient clientInstance = acClient.getAcClient();
        return clientInstance.isConnected();
    }

}
