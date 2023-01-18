package com.qzk.dh11.emqx.callback;

import com.qzk.dh11.emqx.util.Dh11Utils;
import com.qzk.dh11.socket.Dh11Socket;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.Trigger;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Scheduler;

/**
 * @Description DH11模块回调
 * @Date 2023-01-18-16-04
 * @Author qianzhikang
 */
@Slf4j
@Component
public class Dh11Callback implements MqttCallbackExtended {

    @Lazy
    @Autowired
    private Dh11Socket dh11Socket;

    @Lazy
    @Autowired
    private Dh11Utils dh11Utils;

    //@Lazy
    //@Autowired
    //private SchedulerConfig schedulerConfig;

    private Integer userId;

    private Integer deviceId;

    private String dh11Username;
    private String dh11Password;

    @Override
    public void connectComplete(boolean b, String s) {
        log.info("mqtt已连接");
    }

    @Override
    public void connectionLost(Throwable throwable) {
        log.info("mqtt连接丢失");
        if (dh11Utils.isConnect()) {
            dh11Utils.connect(dh11Username, dh11Password);
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        log.info("主题:------{}",topic);
        log.info("Qos:------{}",mqttMessage.getQos());
        log.info("消息:------{}",new String(mqttMessage.getPayload()));
        dh11Socket.sendOneMessage(this.userId,new String(mqttMessage.getPayload()));
        // 定时任务部分
        //try {
        //    Scheduler scheduler = schedulerConfig.scheduler();
        //    JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(deviceId.toString(), userId));
        //    Trigger trigger = scheduler.getTrigger(TriggerKey.triggerKey(deviceId.toString(), userId));
        //    if (jobDetail != null){
        //        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        //        jobDataMap.put("envInfo",new String(mqttMessage.getPayload()));
        //        scheduler.deleteJob(JobKey.jobKey(deviceId.toString(), userId));
        //        scheduler.unscheduleJob(TriggerKey.triggerKey(trigger.getKey().getName(),trigger.getKey().getGroup()));
        //        scheduler.scheduleJob(jobDetail,trigger);
        //        scheduler.start();
        //    }
        //} catch (Exception e) {
        //    log.error(e.getMessage());
        //    log.error("定时任务设置失败");
        //}

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public void setDh11Username(String dh11Username) {
        this.dh11Username = dh11Username;
    }

    public void setDh11Password(String dh11Password) {
        this.dh11Password = dh11Password;
    }
}
