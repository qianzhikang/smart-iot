// 红外协议库
#include <Arduino.h>
#include <IRremoteESP8266.h>
#include <IRsend.h>

// wifi联网库
#include <ESP8266WiFi.h>
#include <DNSServer.h>
#include <ESP8266WebServer.h>
#include <WiFiManager.h>
#include <PubSubClient.h>
#include <ArduinoJson.h>

// 格力常量库
#include "ir_Gree_raw.h"

// MQTT Broker 配置
const char *mqtt_broker = "127.0.0.1";
const char *topic = "ac_topic";
const char *mqtt_username = "ACManage";
const char *mqtt_password = "666666";
const char *client_id = "AirConditioner";
const int mqtt_port = 1883;

// emqx对象初始化
WiFiClient espClient;
PubSubClient client(espClient);
// 红外发射模块信号引脚 D2（GPIO 4）
const uint16_t kIrLed = 4;
IRsend irsend(kIrLed);

void setup() {
  Serial.begin(115200);
  irsend.begin();
  // 建立WiFiManager对象
  WiFiManager wifiManager;
  // 自动连接WiFi。以下语句的参数是连接ESP8266时的WiFi名称
  wifiManager.autoConnect("智能空调遥控设备");
  if (WiFi.status() != WL_CONNECTED) {
    Serial.println("failed to connect and hit timeout");
    wifiManager.resetSettings();
    delay(1000);
  }
  // WiFi连接成功后将通过串口监视器输出连接成功信息
  Serial.println("");
  Serial.print("ESP8266 Connected to ");
  Serial.println(WiFi.SSID());  // WiFi名称
  Serial.print("IP address:\t");
  Serial.println(WiFi.localIP());  // IP

  // mqtt服务器连接
  client.setServer(mqtt_broker, mqtt_port);
  client.setCallback(callback);  // 设置接收消息服务器值
  while (!client.connected()) {
    Serial.println("Connecting to public emqx mqtt broker.....");

    if (client.connect(client_id, mqtt_username, mqtt_password)) {
      Serial.println("Public emqx mqtt broker connected");
    } else {
      Serial.print("failed with state ");
      Serial.print(client.state());
      delay(2000);
    }
  }
  // 订阅主题
  client.subscribe(topic);
}


// 订阅主题收到消息的回调
void callback(char *topic, byte *payload, unsigned int length) {
  Serial.print("Message arrived in topic: ");
  Serial.println(topic);
  Serial.print("Message:");
  // char数组，用于中转换payload的数组
  char str[length];
  // 循环取出payload中的内容，赋值给str中转串
  for (int i = 0; i < length; i++) {
    str[i] = (char)payload[i];
    Serial.print((char)payload[i]);
  }
  Serial.println();
  Serial.println("-----------------------");

  // json解析
  StaticJsonDocument<96> doc;
  DeserializationError error = deserializeJson(doc, str, length);
  if (error) {
    Serial.print(F("deserializeJson() failed: "));
    Serial.println(error.f_str());
    return;
  }
  int acNum = doc["acNum"];              // 1 空调编号
  int acModel = doc["acModel"];          // 0 制冷 1 制热 调模式/调温度时需要加上模式 2自动模式
  const char *acPower = doc["acPower"];  // "on" 开启  "off" 关闭
  int temp = doc["temp"];                // 26 温度
  int type = doc["type"];                // 0：开关机，1：调温度，2：切模式（冷/热/自动）;
  String currPow = (String)acPower;

  // 确认空调编号
  if (acNum == 1) {
    // 开关机逻辑
    if (type == 0) {
      int status_on = currPow.compareTo("on");
      int status_off = currPow.compareTo("off");
      if (status_on == 0) {
        // irsend.sendRaw(powerOn, 279, 38);
        irsend.sendRaw(autoOn, 279, 38);
        Serial.print("send-on");
      }
      if (status_off == 0) {
        irsend.sendRaw(powerOff, 279, 38);
        Serial.print("send-off");
      }
    }

    // 调温度逻辑
    if (type == 1) {
      //todo 调温度
      if (acModel == 0) {
        switch (temp) {
          case 16:
            /* code */
            irsend.sendRaw(cool16, 279, 38);
            break;
          case 17:
            /* code */
            irsend.sendRaw(cool17, 279, 38);
            break;
          case 18:
            /* code */
            irsend.sendRaw(cool18, 279, 38);
            break;
          case 19:
            /* code */
            irsend.sendRaw(cool19, 279, 38);
            break;
          case 20:
            /* code */
            irsend.sendRaw(cool20, 279, 38);
            break;
          case 21:
            /* code */
            irsend.sendRaw(cool21, 279, 38);
            break;
          case 22:
            /* code */
            irsend.sendRaw(cool22, 279, 38);
            break;
          case 23:
            /* code */
            irsend.sendRaw(cool23, 279, 38);
            break;
          case 24:
            /* code */
            irsend.sendRaw(cool24, 279, 38);
            break;
          case 25:
            /* code */
            irsend.sendRaw(cool25, 279, 38);
            break;
          case 26:
            /* code */
            irsend.sendRaw(cool26, 279, 38);
            break;
          case 27:
            /* code */
            irsend.sendRaw(cool27, 279, 38);
            break;
          case 28:
            /* code */
            irsend.sendRaw(cool28, 279, 38);
            break;
          case 29:
            /* code */
            irsend.sendRaw(cool29, 279, 38);
            break;
          case 30:
            /* code */
            irsend.sendRaw(cool30, 279, 38);
            break;
          default:
            break;
        }
      }
      if (acModel == 1) {
        switch (temp) {
          case 16:
            /* code */
            irsend.sendRaw(heat16, 279, 38);
            break;
          case 17:
            /* code */
            irsend.sendRaw(heat17, 279, 38);
            break;
          case 18:
            /* code */
            irsend.sendRaw(heat18, 279, 38);
            break;
          case 19:
            /* code */
            irsend.sendRaw(heat19, 279, 38);
            break;
          case 20:
            /* code */
            irsend.sendRaw(heat20, 279, 38);
            break;
          case 21:
            /* code */
            irsend.sendRaw(heat21, 279, 38);
            break;
          case 22:
            /* code */
            irsend.sendRaw(heat22, 279, 38);
            break;
          case 23:
            /* code */
            irsend.sendRaw(heat23, 279, 38);
            break;
          case 24:
            /* code */
            irsend.sendRaw(heat24, 279, 38);
            break;
          case 25:
            /* code */
            irsend.sendRaw(heat25, 279, 38);
            break;
          case 26:
            /* code */
            irsend.sendRaw(heat26, 279, 38);
            break;
          case 27:
            /* code */
            irsend.sendRaw(heat27, 279, 38);
            break;
          case 28:
            /* code */
            irsend.sendRaw(heat28, 279, 38);
            break;
          case 29:
            /* code */
            irsend.sendRaw(heat29, 279, 38);
            break;
          case 30:
            /* code */
            irsend.sendRaw(heat30, 279, 38);
            break;
          default:
            break;
        }
      }
    }

    // 切换冷/热逻辑
    if (type == 2) {
      if (acModel == 0) {
        irsend.sendRaw(coolModel, 279, 38);
      }
      if (acModel == 1) {
        irsend.sendRaw(heatModel, 279, 38);
      }
      if (acModel == 2) {
        irsend.sendRaw(autoOn, 279, 38);
      }
    }
  }
}

void loop() {
  client.loop();
}