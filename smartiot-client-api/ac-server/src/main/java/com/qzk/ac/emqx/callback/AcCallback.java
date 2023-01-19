package com.qzk.ac.emqx.callback;

import com.qzk.ac.emqx.util.AcUtils;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description AC控制器回调
 * @Date 2023-01-19-13-40
 * @Author qianzhikang
 */
@Component
@Slf4j
public class AcCallback implements MqttCallback {

    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Lazy
    @Resource
    private AcUtils acUtils;

    public void connectionLost(Throwable cause) {
        // 连接丢失后，一般在这里面进行重连
        log.warn("连接断开，可以做重连");
        if (!acUtils.isConnect()){
            acUtils.connect(username,password);
        }
    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe后得到的消息会执行到这里面
        log.info("接收消息主题:" + topic);
        log.info("接收消息Qos:" + message.getQos());
        log.info("接收消息内容:" + new String(message.getPayload()));
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        log.info("deliveryComplete---------" + token.isComplete());
    }
}
