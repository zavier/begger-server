package com.zavier.server;

import com.zavier.server.config.ServerConfig;
import com.zavier.server.connector.Connector;
import com.zavier.server.connector.ConnectorFactory;
import com.zavier.server.connector.impl.SocketConnectorConfig;
import com.zavier.server.connector.impl.SocketConnectorFactory;
import com.zavier.server.event.impl.SocketEventListener;
import com.zavier.server.handler.impl.EchoEventHandler;
import com.zavier.server.impl.SimpleServer;
import java.util.ArrayList;
import java.util.List;

public class ServerFactory {
    public static Server getServer(ServerConfig serverConfig) {
        List<Connector> connectorList = new ArrayList<>();
        SocketEventListener socketEventListener = new SocketEventListener(new EchoEventHandler());
        ConnectorFactory connectorFactory = new SocketConnectorFactory(new SocketConnectorConfig(serverConfig.getPort()), socketEventListener);
        connectorList.add(connectorFactory.getConnector());
        return new SimpleServer(serverConfig, connectorList);
    }
}
