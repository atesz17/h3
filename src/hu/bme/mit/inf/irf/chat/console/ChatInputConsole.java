package hu.bme.mit.inf.irf.chat.console;

import hu.bme.mit.inf.irf.chat.data.Message;
import hu.bme.mit.inf.irf.chat.network.MQTTSubscriberCallback;
import java.util.Scanner;

public class ChatInputConsole implements Runnable {

    private final MQTTSubscriberCallback mqttConnection;
    private final String author;

    public ChatInputConsole(final MQTTSubscriberCallback mqttConnection,
            final String author) {
        this.mqttConnection = mqttConnection;
        this.author = author;
    }

    @Override
    public void run() {
        StringBuilder builder = new StringBuilder("Welcome ");
        builder.append(author);
        builder.append("! Let's start chatting!");

        System.out.println(builder.toString());

        Scanner scanner = new Scanner(System.in);

        while (!Thread.currentThread().isInterrupted()) {
            String text = scanner.nextLine();

            Message message = new Message(author, text);
            mqttConnection.publishMessage(message);
        }
    }

}
