package org.labaix.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.labaix.controler.DataController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class TemperatureMessage implements MqttCallback
{
    private String topic;

    private Logger log = LoggerFactory.getLogger(TemperatureMessage.class);

    public String getTopic() {
        return this.topic;
    }

    public TemperatureMessage(String topic) {
        this.topic = topic;
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        log.debug("delivery completed : " + token);
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
        log.warn("connection lost");
        cause.printStackTrace();
    }

}