package com.qzk.dh11.socket;

import com.qzk.common.exception.ApiException;
import com.qzk.common.purview.domain.entity.Device;
import com.qzk.common.purview.mapper.DeviceMapper;
import com.qzk.common.purview.mapper.DeviceRoomMapper;
import com.qzk.dh11.emqx.callback.Dh11Callback;
import com.qzk.dh11.emqx.util.Dh11Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 温湿度模块socket连接
 * 服务端点说明： /dh11/用户id/dh11设备id/空调控制设备id（未安装默认null）
 * @Date 2023-01-18-16-06
 * @Author qianzhikang
 */
@ServerEndpoint(value = "/dh11/{userId}/{deviceId}/{acId}")
@Component
@Slf4j
public class Dh11Socket {
    /**
     * concurrent 包中的线程安全map，用来存放每个客户端对应的Dh11Socket对象
     */
    private static ConcurrentHashMap<Integer, Dh11Socket> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的链接会话，获取参数，发送数据
     */
    private Session session;

    private Integer userId;

    private static Dh11Utils dh11Utils;

    private static Dh11Callback dh11Callback;

    private static ApplicationContext applicationContext;

    private DeviceMapper deviceMapper;

    private DeviceRoomMapper deviceRoomMapper;
    @Autowired
    public void setLedControl(Dh11Utils dh11Utils){
        Dh11Socket.dh11Utils = dh11Utils;
    }

    @Autowired
    public void setLedCallback(Dh11Callback dh11Callback) {
        Dh11Socket.dh11Callback = dh11Callback;
    }


    public static void setApplicationContext(ApplicationContext applicationContext) {
        Dh11Socket.applicationContext = applicationContext;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId,
                       @PathParam("deviceId") Integer deviceId,
                       @PathParam("acId") Integer acId) {
        System.out.println(userId);
        System.out.println(deviceId);
        System.out.println(acId);
        // 初次连接赋值
        this.userId = userId;
        this.session = session;
        // 加入在线map，键=userId，值=session
        webSocketMap.put(userId, this);

        // 从spring上下文获取mapper
        deviceMapper = applicationContext.getBean(DeviceMapper.class);
        //deviceRoomMapper = applicationContext.getBean(DeviceRoomMapper.class);
        try {
            Device device = deviceMapper.selectById(deviceId);
            Assert.notNull(device,"非法设备参数");
            //DeviceRoom deviceRoom = deviceRoomMapper.selectOne(new LambdaQueryWrapper<DeviceRoom>().eq(DeviceRoom::getDeviceId, device.getId()));
            //Integer roomId = deviceRoom.getRoomId();
            //List<DeviceRoom> deviceRooms = deviceRoomMapper.selectList(new LambdaQueryWrapper<DeviceRoom>().eq(DeviceRoom::getRoomId, roomId));
            //List<DeviceRoom> collect = deviceRooms.stream().filter(item -> !Objects.equals(item.getDeviceId(), deviceId)).collect(Collectors.toList());
            //List<Device> devices = deviceMapper.selectList(new LambdaQueryWrapper<Device>().in(Device::getId, collect));
            if (!dh11Utils.isConnect()){
                dh11Utils.connect(device.getDeviceUsername(),device.getDevicePassword());
            }
            // 订阅主题
            dh11Utils.subscribe(device.getTopic());
            // 为mqtt的回调方法，设置当前userId
            dh11Callback.setUserId(userId);
            dh11Callback.setDeviceId(acId);
            dh11Callback.setDh11Username(device.getDeviceUsername());
            dh11Callback.setDh11Password(device.getDevicePassword());
        }catch (Exception e){
            throw new ApiException(e.getMessage());
        }



    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketMap.remove(this.userId);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 消息
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端消息:" + message);
    }


    /** 发送错误时的处理
     * @param session 当前session
     * @param error 错误
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误,原因:"+error.getMessage());
    }

    /**
     * 发送消息
     * @param userId 发送消息的目标对象
     * @param message 消息内容
     */
    public void sendOneMessage(Integer userId,String message) {
        Session session = webSocketMap.get(userId).session;
        if (session != null && session.isOpen()) {
            log.info("【websocket消息】 单点消息:" + message);
            session.getAsyncRemote().sendText(message);
        }
    }
}
