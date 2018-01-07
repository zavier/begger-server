package com.zavier.server.connector.impl;

public class SocketConnectorConfig {
    private int port;

    public SocketConnectorConfig(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
