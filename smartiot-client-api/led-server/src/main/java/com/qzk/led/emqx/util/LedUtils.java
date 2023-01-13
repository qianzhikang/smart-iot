package com.qzk.led.emqx.util;

import com.qzk.common.constant.EmqxConst;
import com.qzk.led.emqx.callback.LedCallback;
import com.qzk.led.emqx.client.LedClient;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description Led灯EMQX工具
 * @Date 2023-01-11-14-51
 * @Author qianzhikang
 */
@Component
@Slf4j
public class LedUtils {

    @Resource
    private LedClient ledClient;


    @Resource
    private LedCallback ledCallback;

    /**
     * 连接消息服务器
     *
     * @throws MqttException 连接异常
     */
    public void connect(String username,String password) {
        try {
            MqttClient clientInstance = ledClient.getLedClient();
            // MQTT 连接选项
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(username);
            connOpts.setPassword(password.toCharArray());
            // 保留会话
            connOpts.setCleanSession(true);
            // 设置回调
            clientInstance.setCallback(ledCallback);
            // 建立连接
            log.info("Connecting to broker: " + EmqxConst.BROKER);
            clientInstance.connect(connOpts);
            log.info("Connected");
            // 为回调方法赋值
            ledCallback.setUsername(username);
            ledCallback.setPassword(password);
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
            MqttClient clientInstance = ledClient.getLedClient();
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
            MqttClient clientInstance = ledClient.getLedClient();
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
        MqttClient clientInstance = ledClient.getLedClient();
        return clientInstance.isConnected();
    }

}
