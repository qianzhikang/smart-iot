package com.qzk.led.emqx.callback;

import com.qzk.led.emqx.util.LedUtils;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description led灯的emqx回调
 * @Date 2023-01-11-14-51
 * @Author qianzhikang
 */
@Component
@Slf4j
public class LedCallback implements MqttCallback {
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
    private LedUtils ledUtil;

    public void connectionLost(Throwable cause) {
        // 连接丢失后，一般在这里面进行重连
        log.info("连接断开，可以做重连");
        ledUtil.connect(username,password);
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
