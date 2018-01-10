package com.zavier.server.connector.impl;

import com.zavier.server.connector.AbstractConnector;
import com.zavier.server.connector.ConnectorFactory;
import com.zavier.server.event.impl.SocketEventListener;

public class SocketConnectorFactory implements ConnectorFactory {

    private final SocketConnectorConfig socketConnectorConfig;
    private final SocketEventListener socketEventListener;

    public SocketConnectorFactory(
        SocketConnectorConfig socketConnectorConfig, SocketEventListener socketEventListener) {
        this.socketConnectorConfig = socketConnectorConfig;
        this.socketEventListener = socketEventListener;
    }

    @Override
    public AbstractConnector getConnector() {
        return new SocketConnector(this.socketConnectorConfig.getPort(), socketEventListener);
    }
}
