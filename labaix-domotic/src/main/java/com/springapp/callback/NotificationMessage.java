package com.springapp.callback;

import com.springapp.pojo.DataUnit;
import com.springapp.pojo.Notification;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class NotificationMessage implements MqttCallback
{
    private final Logger log = LoggerFactory.getLogger(NotificationMessage.class);

    private List<Notification> notifications;

    public String getTopic() {
        return this.topic;
    }
    private final String topic;


    public NotificationMessage(String topic, List<Notification> notifications) {
        this.topic = topic;
        this.notifications = notifications;
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        log.debug("delivery completed : " + token);
    }

    public void messageArrived(String topic, MqttMessage message)
            throws Exception {
        if (message != null) {
            if (notifications.contains(new Notification(topic))==false) {
                log.info(topic + " : discovered");
                notifications.add(new Notification(topic));
            }
        }
    }

    public void connectionLost(Throwable cause) {
        log.warn("connection lost");
        cause.printStackTrace();
    }

}
