package hu.bme.mit.inf.irf.chat.network;

import java.util.UUID;

public class MQTTConfiguration {

    private final String protocol;
    private final String address;
    private final int port;

    private final int qos;
    private final String topic;

    public MQTTConfiguration(final int port) {
        this.protocol = "tcp";
        this.address = "localhost";
        this.port = port;

        this.qos = 2;
        this.topic = "irfchat";
    }

    public String getClientID() {
        return UUID.randomUUID().toString();
    }

    public String getFullAddress() {
        return protocol + "://" + address + ":" + port;
    }

    public int getQOS() {
        return this.qos;
    }

    public String getTopic() {
        return this.topic;
    }
}
