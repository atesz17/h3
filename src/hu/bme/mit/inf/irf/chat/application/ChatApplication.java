package hu.bme.mit.inf.irf.chat.application;

import hu.bme.mit.inf.irf.chat.authentication.RegistrationManager;
import static hu.bme.mit.inf.irf.chat.authentication.UserAuthentication.getAuthenticatedUsername;
import hu.bme.mit.inf.irf.chat.console.ChatInputConsole;
import hu.bme.mit.inf.irf.chat.network.MQTTBrokerManager;
import hu.bme.mit.inf.irf.chat.network.MQTTConfiguration;
import static hu.bme.mit.inf.irf.chat.network.MQTTPublisherSubscriber.testMqttConnection;
import hu.bme.mit.inf.irf.chat.network.MQTTSubscriberCallback;
import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttException;
import static org.eclipse.paho.client.mqttv3.MqttException.REASON_CODE_CLIENT_NOT_CONNECTED;


public class ChatApplication {

    public static final void main(String[] args) {
        // parse command line arguments
        int port = -1;
        File configXML = null;
        List<String> errors = new ArrayList<>();

        if (args.length == 2) {
            // port
            try {
                port = Integer.parseInt(args[0]);

                if (port <= 0 || port >= 65536) {
                    errors.add("Invalid port: " + port);
                }
            } catch (NumberFormatException e) {
                errors.add("Not a number: " + port);
            }

            // configuration file
            configXML = new File(args[1]);

            if (!configXML.exists()) {
                errors.add("The configuration file does not exists");
            } else if (!configXML.canRead()) {
                errors.add("The configuration file cannot be read");
            }
        } else {
            errors.add("Missing parameters");
        }

        if (errors.size() > 0) {
            // print errors
            System.err.println("An error occured");

            for (String error : errors) {
                System.err.println("  " + error);
            }

            System.err
                    .println(
                            "Usage: java -jar ChatApplication.jar <port> <configxml>");
        } else {
            try {
                // register users
                RegistrationManager regManager = new RegistrationManager();
                regManager.loadUsers(configXML);

                // authenticate the user
                String username = getAuthenticatedUsername(regManager);

                // start the broker, if not started yet
                MQTTBrokerManager broker = new MQTTBrokerManager();
                MQTTConfiguration connectionConfiguration
                        = new MQTTConfiguration(port);
                startBroker(connectionConfiguration, broker, port);

                // connect the chat client
                MQTTSubscriberCallback subscriber
                        = new MQTTSubscriberCallback(
                                connectionConfiguration,
                                System.out);

                // start the chat
                ChatInputConsole cons = new ChatInputConsole(
                        subscriber, username);
                new Thread(cons).start();

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                if (ex instanceof MqttException) {
                    if (((MqttException) ex).getReasonCode() == REASON_CODE_CLIENT_NOT_CONNECTED) {
                        System.err.println("Try another port number.");
                    }
                }
                System.exit(-1);
            }
        }
    }

    public static void startBroker(
            final MQTTConfiguration connectionConfiguration,
            final MQTTBrokerManager broker,
            final int port) throws Exception {
        try {
            testMqttConnection(connectionConfiguration);
        } catch (MqttException ex) {
            try {
                // start the broker, because it was not started yet
                broker.startBroker(port);
            } catch (IOException e) {
                // if the connection after starting the broker is unsuccessful
                if (e instanceof BindException) {
                    testMqttConnection(connectionConfiguration);
                } else {
                    throw new IOException(e);
                }
            }

        }
    }
}
