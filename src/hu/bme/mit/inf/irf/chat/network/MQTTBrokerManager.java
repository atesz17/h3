package hu.bme.mit.inf.irf.chat.network;

import io.moquette.server.Server;
import java.io.IOException;
import java.util.Properties;

public class MQTTBrokerManager {

    private final Server broker;

    public MQTTBrokerManager() {
        broker = new Server();
    }

    public void startBroker(final int port) throws IOException {
        Properties prop = new Properties();
        prop.put("port", String.valueOf(port));
        prop.put("websocket_port", "8080");
        prop.put("host", "0.0.0.0");
        prop.put("allow_anonymous", "true");

        broker.startServer(prop);
    }

}
