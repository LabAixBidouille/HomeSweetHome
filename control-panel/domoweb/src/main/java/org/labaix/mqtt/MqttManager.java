package org.labaix.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;

/**
 * Created by nicolas on 27/01/15.
 */
public class MqttManager {

    private TemperatureMessage temperature = new TemperatureMessage("LABDemo/temperature");

    public void startReception() {
        try {
            MqttClient client = new MqttClient("tcp://192.168.0.9:1883", "receiver1");
            client.connect();
            client.subscribe(temperature.getTopic());
            client.setCallback(temperature);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
