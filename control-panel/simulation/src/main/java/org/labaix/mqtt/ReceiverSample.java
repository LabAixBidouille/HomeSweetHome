package org.labaix.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.labaix.DataController;

/**
 * Hello world!
 *
 */
public class ReceiverSample implements MqttCallback
{
    public static void main( String[] args )
    {
        ReceiverSample app = new ReceiverSample();
        app.startReception();
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("delivery completed : " + token);
    }

    public void messageArrived(String topic, MqttMessage message)
            throws Exception {
            if (message != null) {
                // Ne pas enlever le trim car le message se termine par '\0' du C
                String msg = message.toString().trim();
                if (topic.contains("temperature")) {
                    DataController.temperature = Integer.parseInt(msg);
                } else if (topic.contains("humidite")) {
                    DataController.humidite = Integer.parseInt(msg);
                }
                System.out.println(topic + " : " + message.toString());
            }
    }

    public void connectionLost(Throwable cause) {
        System.out.println("connection lost");
        cause.printStackTrace();
    }

    public void startReception() {
        try {
            MqttClient client = new MqttClient("tcp://192.168.0.9:1883", "receiver1");
            client.connect();
            client.subscribe("LABdemo/temperature");
            client.setCallback(this);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}