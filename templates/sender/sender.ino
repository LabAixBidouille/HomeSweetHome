/*******************************************************************************
 * Copyleft (c) 2014 LabAixBidouille
 *
 * Contributors:
 *    Nicolas MULLER
 *******************************************************************************/

#define MQTTCLIENT_QOS2 1

#include <SPI.h>
#include <Ethernet.h>
#include <EthernetStack.h>
#include <Countdown.h>
#include <MQTTClient.h>

/**************************
 * CONFIGURATION
 **************************/

// IP for the Broker
char hostname[] = "192.168.1.25";

// The port for the broker
int port = 1883;

// The topic where to publish the message
const char* topic = "pahodemo/alarm";

// replace with your device's MAC
byte mac[] = { 0x00, 0x11, 0x22, 0x33, 0x44, 0x55 };  

/**************************
 * DECLARATIONS
 **************************/
 
int compteur = 0;
char printbuf[100];

// Send and receive QoS  message
char buf[200];

EthernetStack ipstack;
MQTT::Message message;
MQTT::Client<EthernetStack, Countdown> client = MQTT::Client<EthernetStack, Countdown>(ipstack);

/**************************
 * METHODES
 **************************/
 
// Connection to the Broker MQTT
void connect()
{
  sprintf(printbuf, "Connecting to %s:%d\n", hostname, port);
  Serial.print(printbuf);
  int rc = ipstack.connect(hostname, port);
  if (rc != 1)
  {
    sprintf(printbuf, "rc from TCP connect is %d\n", rc);
    Serial.print(printbuf);
  }
 
  Serial.println("MQTT connecting");
  MQTTPacket_connectData data = MQTTPacket_connectData_initializer;       
  data.MQTTVersion = 3;
  data.clientID.cstring = (char*)"arduino-sample";
  rc = client.connect(data);
  if (rc != 0)
  {  
    sprintf(printbuf, "rc from MQTT connect is %d\n", rc);
    Serial.print(printbuf);
  }
  Serial.println("MQTT connected");  
}

/**************************
 * SETUP ARDUINO
 **************************/
 
void setup()
{
  Serial.begin(9600);
  Ethernet.begin(mac);
  Serial.println("MQTT Alarme demo");
  connect();
}

/**************************
 * LOOP ARDUINO
 **************************/
 
void loop()
{
  // If client is not connected, you have to connect to network...
  if (!client.isConnected()) connect();
    
    sprintf(buf, "Message %d \n", compteur++);
  
    Serial.println(buf);
    message.retained = false;
    message.dup = false;
    message.payload = (void*)buf;
    message.qos = MQTT::QOS1;
    message.payloadlen = strlen(buf)+1;
    int rc = client.publish(topic, message);
              
    delay(1000);
}

