// oled显示板库
#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>

// DH11 温湿度传感器库
#include <Adafruit_Sensor.h>
#include <DHT.h>

// esp-8266联网库
#include <ESP8266WiFi.h>
#include <DNSServer.h>
#include <ESP8266WebServer.h>
#include <WiFiManager.h>
#include <PubSubClient.h>
// json 库
#include <ArduinoJson.h>

// MQTT Broker 配置
const char *mqtt_broker = "127.0.0.1";
const char *topic = "dh11_topic";
const char *mqtt_username = "DH11Manage";
const char *mqtt_password = "666666";
const char *client_id = "DH11";
const int mqtt_port = 1883;

#define SCREEN_WIDTH 128  // OLED display width, in pixels
#define SCREEN_HEIGHT 64  // OLED display height, in pixels
// Declaration for an SSD1306 display connected to I2C (SDA, SCL pins)
Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, -1);  //128*64像素，逐行式
#define DHTPIN 14                                                  //定义引脚D5
#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);


// emqx对象初始化
WiFiClient espClient;
PubSubClient client(espClient);


void setup() {
  Serial.begin(115200);  // 串口通信

  // 联网配置
  WiFiManager wifiManager;
  wifiManager.autoConnect("温湿度监测器");
  if (WiFi.status() != WL_CONNECTED) {
    Serial.println("failed to connect and hit timeout");
    //reset and try again, or maybe put it to deep sleep
    ESP.reset();
    delay(1000);
  }
  Serial.println("");
  Serial.print("ESP8266 Connected to ");
  Serial.println(WiFi.SSID());  // WiFi名称
  Serial.print("IP address:\t");
  Serial.println(WiFi.localIP());  // IP

  // mqtt服务器连接
  client.setServer(mqtt_broker, mqtt_port);
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

  dht.begin();                                     // 开始监测温度
  if (!display.begin(SSD1306_SWITCHCAPVCC, 0x3C))  // 扫描OLED的地址，默认0x3c
  {
    Serial.println(F("SSD1306 allocation failed"));
    for (;;)
      ;
  }
  delay(2000);
  display.clearDisplay();       //  清屏
  display.setTextColor(WHITE);  //开像素点发光
}
void loop() {
  client.loop();
  // 2.5 秒监测一次温度，并且向服务器发送温度消息
  delay(2500);
  float t = dht.readTemperature();
  float h = dht.readHumidity();
  if (isnan(h) || isnan(t))  // 如果都没有采集到数据，则打印失败
  {
    Serial.println("Failed to read from DHT sensor!");
  }

  StaticJsonDocument<32> doc;

  doc["temp"] = t;
  doc["humi"] = h;
  char output[128];
  serializeJson(doc, output);

  // 发布消息
  client.publish(topic, output);

  Serial.println(t, 2);
  display.clearDisplay();   // 清屏
  display.setTextSize(1);   // 字体
  display.setCursor(0, 0);  //显示位置在第一行第一个
  display.print("Temp: ");
  display.setTextSize(2);
  display.print(t);
  display.print(" ");
  display.setTextSize(1);
  display.cp437(true);  //使用cp437符号集
  display.write(167);   //对应的温度符号
  display.setTextSize(2);
  display.print("C");  // display humidity
  display.setTextSize(1);
  display.setCursor(0, 25);
  display.print("Humi: ");
  display.setTextSize(2);
  display.print(h);
  display.print(" %");
  display.display();
}