package mqttdemo;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Hello world!
 *
 */
public class App implements MqttCallback
{
    public static void main( String[] args )
    {
        App app = new App();
        app.publishSimpleMessage();
        //app.casPlusComplique();
    }

    public void publishSimpleMessage() {
        try {
            MqttClient client = new MqttClient("tcp://192.168.1.25:1883", "pahomqttpublish1");
            client.connect();

            client.setCallback(this);
            client.subscribe("pahodemo/alarm");

            MqttMessage message = new MqttMessage();
            message.setPayload("Bonjour Laurent".getBytes());
            client.publish("pahodemo/alarm", message);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("delivery completed : " + token);
    }

    public void messageArrived(String topic, MqttMessage message)
            throws Exception {
        if (message != null && message.toString().contains("Alerte")) {
            System.out.println(topic + " // Cherie, appelle la police : " + message.toString());
        }
    }

    public void connectionLost(Throwable cause) {
        System.out.println("connection lost");
    }



    public static void casPlusComplique() {

        String topic        = "MQTT Examples";
        String content      = "Message from MqttPublishSample";
        int qos             = 1;
        String broker       = "tcp://raspberrypi.local:1883";
        String clientId     = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: "+content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }

}
