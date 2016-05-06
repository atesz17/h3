package hu.bme.mit.inf.irf.chat.network;

import com.google.gson.Gson;
import hu.bme.mit.inf.irf.chat.console.ChatOutputConsole;
import hu.bme.mit.inf.irf.chat.data.Message;
import java.io.PrintStream;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTSubscriberCallback implements MqttCallback {

    private final MQTTPublisherSubscriber mqttSubscriber;
    private final ChatOutputConsole chatOutput;

    public MQTTSubscriberCallback(final MQTTConfiguration conf,
            final PrintStream console) {
        this.mqttSubscriber = new MQTTPublisherSubscriber(conf);
        this.mqttSubscriber.subscribeCallback(this);
        this.chatOutput = new ChatOutputConsole(console);
    }

    public void publishMessage(Message message) {
        mqttSubscriber.publishMessage(message);
    }

    @Override
    public void messageArrived(final String topic, final MqttMessage message) {
        try {
            if (!mqttSubscriber.getSubscribedTopic().equals(topic)) {
                return;
            }

            Message chatMessage = getMessageFromPayload(message);
            chatOutput.printMessage(chatMessage);

        } catch (Exception ex) {
            System.err.println("Chat error, reason: " + ex.getMessage());
        }
    }

    @Override
    public void connectionLost(final Throwable cause) {
        System.err.println("MQTT connection lost, reason: " + cause.getMessage());
    }

    @Override
    public void deliveryComplete(final IMqttDeliveryToken token) {

    }

    private Message getMessageFromPayload(final MqttMessage payload) {
        String messageJson = new String(payload.getPayload());
        Message message = new Gson().fromJson(messageJson, Message.class);
        return message;
    }
}
