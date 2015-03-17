package com.springapp.controllers;

import com.springapp.callback.*;
import com.springapp.pojo.Notification;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolas on 01/03/15.
 */
@Controller
@RequestMapping(value="/")
public class DashboardController {

    static final Logger LOG = LoggerFactory.getLogger(DashboardController.class);

    private TaskScheduler scheduler = new ConcurrentTaskScheduler();

    private List<Notification> notifications = new ArrayList<Notification>();

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public String dashboard() {
        return "dashboard";
    }

    /**
     * greeting every 1 second
     */
    @PostConstruct
    private void broadcastTimePeriodically() {
        try {
            MqttMessage temperatureMessage = new MqttMessage("labaix/temperature", "/topic/temperature", simpMessagingTemplate);
            MqttMessage humiditeMessage = new MqttMessage("labaix/humidite", "/topic/humidite", simpMessagingTemplate);
            MqttMessage temperatureMessageSN = new MqttMessage("labaix/temperature/sn", "/topic/temperature/sn", simpMessagingTemplate);
            MqttMessage humiditeMessageSN = new MqttMessage("labaix/humidite/sn", "/topic/humidite/sn", simpMessagingTemplate);            NotificationMessage notificationMessage = new NotificationMessage("labaix/#", notifications);

            MqttClient client = new MqttClient("tcp://localhost:1883", "receiver1");
            client.connect();
            client.subscribe(temperatureMessage.getTopic());
            client.setCallback(temperatureMessage);

            MqttClient client2 = new MqttClient("tcp://localhost:1883", "receiver2");
            client2.connect();
            client2.subscribe(humiditeMessage.getTopic());
            client2.setCallback(humiditeMessage);

            MqttClient client3 = new MqttClient("tcp://localhost:1883", "receiver3");
            client3.connect();
            client3.subscribe(temperatureMessageSN.getTopic());
            client3.setCallback(temperatureMessageSN);

            MqttClient client4 = new MqttClient("tcp://localhost:1883", "receiver4");
            client4.connect();
            client4.subscribe(humiditeMessageSN.getTopic());
            client4.setCallback(humiditeMessageSN);

            MqttClient client5 = new MqttClient("tcp://localhost:1883", "receiver5");
            client5.connect();
            client5.subscribe(notificationMessage.getTopic());
            client5.setCallback(notificationMessage);

            scheduler.scheduleAtFixedRate(new Runnable() {
                @Override public void run() {
                    updateNotificationsPanel();
                }
            }, 1000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateNotificationsPanel() {
        simpMessagingTemplate.convertAndSend("/topic/notifications", notifications);
    }
}
