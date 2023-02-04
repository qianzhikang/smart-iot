# 智能物联项目

## 项目介绍

项目实现一套通过网络可远程的物联网硬件设备和配套的客户端程序和其管理后台。

### 项目包含

- 客户端API
- 客户端应用
- 基于Arduino平台的硬件代码
- 后台管理系统前端
- 后台管理系统API

### 已接入的硬件设备

- 可控LED灯
- 带OLED屏幕的环境温湿度检测器
- 空调控制设备

### 设备展示：

**`可控LED灯`**

![可控LED灯](https://github.com/qianzhikang/smart-iot/blob/main/images/led.jpeg)



**`OLED屏的温湿度检测设备`**

![OLED屏幕DH11温湿度检测设备](https://github.com/qianzhikang/smart-iot/blob/main/images/dh11.jpeg)



**`红外空调控制设备`**

![红外空调控制设备](https://github.com/qianzhikang/smart-iot/blob/main/images/ac.jpeg)



### 硬件通信方式

- 基于MQTT协议，使用EMQX消息服务器的 订阅/发布 模式实现硬件通信。
- Java后端接口配合EMQX服务器SDK工具实现接口与服务器通信



## 项目进度

**用户模块**

- [2022.12.15] 项目初始化,仓库创建

- [2022.12.17] 登陆、注册接口；处理Redis的key值乱码问题（添加RedisConfig配置）

- [2022.12.18] 添加硬件代码

- [2022.12.19] 登陆接口返回值调整；登出接口；注解认证模块

- [2022.12.21] 用户组创建、用户组列表、删除接口

- [2022.12.22] 用户组名称修改、添加/删除/查询用户组成员

- [2022.12.23] 场景增、改、查接口

- [2023.1.10]  添加设备、删除设备、场景内查询所有设备

- [2023.1.11] 删除场景接口

- [2023.2.3] 获取个人信息、修改个人信息、上传头像、修改密码

**智能LED灯模块**

- [2023.1.13] 项目架构重构，LED灯控制模块实现
- [2023.1.18] LED灯控接口测试；

**温湿度监测模块**

- [2023.1.18] DH11温湿度模块实现、测试

**空调遥控模块**

- [2023.1.19] AC控制模块工具编写、服务框架搭建

- [2023.2.3] AC控制模块实现

**微服务化**

- [2023.2.4] 各个模块接入nacos，实现远程配置中心
