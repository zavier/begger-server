package com.zavier.server.connector.impl;

import com.zavier.server.connector.Connector;
import com.zavier.server.connector.ConnectorFactory;

public class SocketConnectorFactory implements ConnectorFactory {

    private final SocketConnectorConfig socketConnectorConfig;

    public SocketConnectorFactory(
        SocketConnectorConfig socketConnectorConfig) {
        this.socketConnectorConfig = socketConnectorConfig;
    }

    @Override
    public Connector getConnector() {
        return new SocketConnector(this.socketConnectorConfig.getPort());
    }
}
