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
#include <LiquidCrystal.h>

/**************************
 * CONFIGURATION
 **************************/

// IP for the Broker
char hostname[] = "192.168.0.9";

// The port for the broker
int port = 1883;

// The topic where to publish the message
const char* topicTemperature = "LABdemo/+/temperature/+";
const char* topicHumidite = "LABdemo/humidite/+";

byte mac[] = { 0x00, 0x11, 0x22, 0x33, 0x44, 0x54 };  // adresse MAC arduino
byte ip[] = { 192,168,0,27 }; // adresse IP arduino récepteur

// broches shield LCD DFRobot DFR0009
const int RS=8; 
const int E=9;

const int D4=4;
const int D5=5;
const int D6=6;
const int D7=7;

// Variable utile pour ne pas rafraichir 
// inutilement l'afficheur
int previousTemperature = -1;
int previousHumidite = -1;

LiquidCrystal lcd(RS, E, D4, D5, D6, D7);// initialisation LCD en mode 4 bits 

/**************************
 * DECLARATIONS
 **************************/
char printbuf[100];
int arrivedcount = 0;
 
EthernetStack ipstack;
MQTT::Message message;
MQTT::Client<EthernetStack, Countdown> client = MQTT::Client<EthernetStack, Countdown>(ipstack);

/**************************
 * PARTIE MQTT SUBSCRIBE
 **************************/

void messageTempArrived(MQTT::MessageData& md)
{
  MQTT::Message &message = md.message;
  
  sprintf(printbuf, "Message %d arrived: qos %d, retained %d, dup %d, packetid %d\n", 
                ++arrivedcount, message.qos, message.retained, message.dup, message.id);
  Serial.print(printbuf);
  sprintf(printbuf, "Payload %s\n", (char*)message.payload);
  Serial.print(printbuf);
  lcd.setCursor(0, 1) ; // positionne le curseur à l'endroit voulu (colonne, ligne) (1ère colonne =0, 2ème ligne=1) 
  lcd.print((char*)message.payload);
  lcd.setCursor(3, 1) ;// positionne le curseur à : 15ème colonne =14, 2ème ligne=1
  lcd.print("  ");
  lcd.setCursor(3, 1) ;// positionne le curseur à : 15ème colonne =14, 2ème ligne=1
  lcd.write(0xDF); // affichage °
  lcd.print("C");  
}

void messageHumiArrived(MQTT::MessageData& md)
{
  MQTT::Message &message = md.message;
  
  sprintf(printbuf, "Message %d arrived: qos %d, retained %d, dup %d, packetid %d\n", 
                ++arrivedcount, message.qos, message.retained, message.dup, message.id);
  Serial.print(printbuf);
  sprintf(printbuf, "Payload %s\n", (char*)message.payload);
  Serial.print(printbuf);
  lcd.setCursor(11, 1) ; // positionne le curseur à l'endroit voulu (colonne, ligne) (1ère colonne =0, 2ème ligne=1) 
  lcd.print((char*)message.payload);
  lcd.setCursor(14, 1) ;// positionne le curseur à : 15ème colonne =14, 2ème ligne=1
  lcd.print("%");  

}

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
  data.clientID.cstring = (char*)"arduino-sample2";
  rc = client.connect(data);
  if (rc != 0)
  {
    sprintf(printbuf, "rc from MQTT connect is %d\n", rc);
    Serial.print(printbuf);
  }
  Serial.println("MQTT connected");
  
  rc = client.subscribe(topicTemperature, MQTT::QOS1, messageTempArrived);   
  if (rc != 0)
  {
    sprintf(printbuf, "Temperature // rc from MQTT subscribe is %d\n", rc);
    Serial.print(printbuf);
  }
  Serial.println("MQTT subscribed for temperature");
  
  rc = client.subscribe(topicHumidite, MQTT::QOS1, messageHumiArrived);   
  if (rc != 0)
  {
    sprintf(printbuf, "Humidite // rc from MQTT subscribe is %d\n", rc);
    Serial.print(printbuf);
  }
  Serial.println("MQTT subscribed for humidite");
}

/**************************
 * SETUP ARDUINO
 **************************/
 
void setup()
{
  Serial.begin(9600);
  lcd.begin(16,2); // Initialise le LCD avec 16 colonnes x 2 lignes
  lcd.print("TEMP   /   HUMI") ; // affiche la chaîne texte - message de test 
  Ethernet.begin(mac,ip);
  Serial.println("MQTT Transmission Data");
  connect();
}

/**************************
 * LOOP ARDUINO
 **************************/
 
void loop()
{
  // If client is not connected, you have to connect to network...
  if (!client.isConnected()) 
      connect();   
  client.yield(1000);  
}
