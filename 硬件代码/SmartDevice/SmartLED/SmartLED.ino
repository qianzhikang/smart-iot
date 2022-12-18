#include <ESP8266WiFi.h>
#include <DNSServer.h>
#include <ESP8266WebServer.h>
#include <WiFiManager.h>
#include <PubSubClient.h>
#include <ArduinoJson.h>

// MQTT Broker 配置
const char *mqtt_broker = "127.0.0.1";
const char *topic = "led_topic";
const char *mqtt_username = "LedManage";
const char *mqtt_password = "666666";
const int mqtt_port = 1883;

// led灯的开关状态常量
const String led_model_on = "on";
const String led_model_off = "off";
// led的灯位置
const int led_1 = 1;
const int led_2 = 2;

// 开发板针脚
const int pin0 = 16;
const int pin1 = 5;

// emqx对象初始化
WiFiClient espClient;
PubSubClient client(espClient);
void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  // 建立WiFiManager对象
  WiFiManager wifiManager;

  // 清除ESP8266所存储的WiFi连接信息以便测试WiFiManager工作效果
  // wifiManager.resetSettings();
  // Serial.println("ESP8266 WiFi Settings Cleared");

  // 自动连接WiFi。以下语句的参数是连接ESP8266时的WiFi名称
  wifiManager.autoConnect("智能LED灯网络配置");

  if (WiFi.status() != WL_CONNECTED) {
    Serial.println("failed to connect and hit timeout");
    wifiManager.resetSettings();
    delay(1000);
  }

  // 如果您希望该WiFi添加密码，可以使用以下语句：
  // wifiManager.autoConnect("AutoConnectAP", "12345678");
  // 以上语句中的12345678是连接AutoConnectAP的密码

  // WiFi连接成功后将通过串口监视器输出连接成功信息
  Serial.println("");
  Serial.print("ESP8266 Connected to ");
  Serial.println(WiFi.SSID());  // WiFi名称
  Serial.print("IP address:\t");
  Serial.println(WiFi.localIP());  // IP

  //设置对应引脚为输出模式
  pinMode(pin0, OUTPUT);
  pinMode(pin1, OUTPUT);


  // mqtt服务器连接
  client.setServer(mqtt_broker, mqtt_port);
  client.setCallback(callback);  // 设置接收消息服务器值
  while (!client.connected()) {
    Serial.println("Connecting to public emqx mqtt broker.....");
    String client_id = "Led";
    if (client.connect(client_id.c_str(), mqtt_username, mqtt_password)) {
      Serial.println("Public emqx mqtt broker connected");
    } else {
      Serial.print("failed with state ");
      Serial.print(client.state());
      delay(2000);
    }
  }
  // 订阅主题
  client.subscribe("led_topic");
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
  // 创建ArduinoJson的转换对象
  StaticJsonDocument<32> doc;
  // 转换错误的异常对象
  DeserializationError error = deserializeJson(doc, str, length);
  // 转换错误时执行的逻辑
  if (error) {
    Serial.print(F("deserializeJson() failed: "));
    Serial.println(error.f_str());
    return;
  }
  // 取出转换后的内容
  int ledNum = doc["ledNum"];              // 引脚灯
  const char *ledModel = doc["ledModel"];  // 当前状态
  String ledStatus = (String)ledModel;     // 将当前状态的char转换为string用于开关状态的判断

  Serial.println(ledNum);
  Serial.println(ledStatus);
  Serial.println("-----------------------");

  // 比较开关状态，相同返回 0
  int status_on = led_model_on.compareTo(ledStatus);
  int status_off = led_model_off.compareTo(ledStatus);

  // 开灯逻辑
  if (status_on == 0) {
    Serial.print("status_on:");
    Serial.println(status_on);
    if (ledNum == 1) {
      Serial.println(ledNum);
      digitalWrite(pin0, HIGH);  //实现LED的IO口拉高
    }
    if (ledNum == 2) {
      Serial.println(ledNum);
      digitalWrite(pin1, HIGH);  //实现LED的IO口拉高
    }
    // 发布消息
    // client.publish("led_result", "{\"ledNum\":1,\"ledModel\":\"on\"}");
  }
  // 关灯逻辑
  if (status_off == 0) {
    Serial.print("status_off:");
    Serial.println(status_off);
    if (ledNum == 1) {
      Serial.println(ledNum);
      digitalWrite(pin0, LOW);  //实现LED的IO口拉低
    }
    if (ledNum == 2) {
      Serial.println(ledNum);
      digitalWrite(pin1, LOW);  //实现LED的IO口拉低
    }
    // 发布消息
    // client.publish("led_result", "{\"ledNum\":1,\"ledModel\":\"off\"}");
  }
}
void loop() {
  client.loop();
}