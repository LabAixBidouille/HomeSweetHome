package com.springapp.callback;

import com.springapp.pojo.DataUnit;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * Hello world!
 *
 */
public class MqttMessage implements MqttCallback
{
    private final Logger log = LoggerFactory.getLogger(MqttMessage.class);

    public String getTopic() {
        return this.topicMqtt;
    }

    private final String topicMqtt;
    private final String topicWS;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public MqttMessage(String topicMqtt, String topicWS, SimpMessagingTemplate simpMessagingTemplate) {
        this.topicMqtt = topicMqtt;
        this.topicWS = topicWS;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        log.debug("delivery completed : " + token);
    }

    public void messageArrived(String topic, org.eclipse.paho.client.mqttv3.MqttMessage message)
            throws Exception {
        if (message != null) {
            // Ne pas enlever le trim car le message se termine par '\0' du C
            String msg = message.toString().trim();
            System.out.println(topic + " : " + message.toString());
            simpMessagingTemplate.convertAndSend(topicWS, new DataUnit(message.toString()));
        }
    }

    public void connectionLost(Throwable cause) {
        log.warn("connection lost");
        cause.printStackTrace();
    }

}
