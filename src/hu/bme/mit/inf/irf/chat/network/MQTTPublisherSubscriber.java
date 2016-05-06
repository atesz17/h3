package hu.bme.mit.inf.irf.chat.network;

import hu.bme.mit.inf.irf.chat.data.Message;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import static org.eclipse.paho.client.mqttv3.MqttException.REASON_CODE_CLIENT_NOT_CONNECTED;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTTPublisherSubscriber {

    private MqttAsyncClient client;
    private String topic;
    private int qos;

    public MQTTPublisherSubscriber(final MQTTConfiguration config) {
        try {
            String address = config.getFullAddress();
            String clientId = config.getClientID();

            this.qos = config.getQOS();
            this.topic = config.getTopic();

            MemoryPersistence persistence = new MemoryPersistence();
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            this.client = new MqttAsyncClient(address, clientId, persistence);
            this.client.connect(connOpts);
            Thread.sleep(500);
            this.client.subscribe(topic, qos);
        } catch (MqttException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void testMqttConnection(final MQTTConfiguration config) throws MqttException, InterruptedException {
        String address = config.getFullAddress();
        String clientId = config.getClientID();

        MemoryPersistence persistence = new MemoryPersistence();
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);

        MqttAsyncClient client = new MqttAsyncClient(address, clientId,
                persistence);
        client.connect(connOpts);
        Thread.sleep(500);
        if (!client.isConnected()) {
            throw new MqttException(REASON_CODE_CLIENT_NOT_CONNECTED);
        }
        client.disconnect();
    }

    public void subscribeCallback(final MqttCallback callback) {
        this.client.setCallback(callback);
    }

    public void publishMessage(final Message message) {
        try {
            byte[] payload = message.toJson().getBytes();
            client.publish(topic, payload, qos, false);
        } catch (MqttException ex) {
            ex.printStackTrace();
        }
    }

    public String getSubscribedTopic() {
        return topic;
    }

}
