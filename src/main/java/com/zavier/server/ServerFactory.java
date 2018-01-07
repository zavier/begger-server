package com.zavier.server;

import com.zavier.server.config.ServerConfig;
import com.zavier.server.connector.Connector;
import com.zavier.server.connector.ConnectorFactory;
import com.zavier.server.connector.impl.SocketConnectorConfig;
import com.zavier.server.connector.impl.SocketConnectorFactory;
import com.zavier.server.impl.SimpleServer;
import java.util.ArrayList;
import java.util.List;

public class ServerFactory {
    public static Server getServer(ServerConfig serverConfig) {
        List<Connector> connectorList = new ArrayList<>();
        ConnectorFactory connectorFactory = new SocketConnectorFactory(new SocketConnectorConfig(serverConfig.getPort()));
        connectorList.add(connectorFactory.getConnector());
        return new SimpleServer(serverConfig, connectorList);
    }
}
