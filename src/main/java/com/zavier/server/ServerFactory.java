package com.zavier.server;

import com.zavier.server.config.ServerConfig;
import com.zavier.server.connector.AbstractConnector;
import com.zavier.server.connector.ConnectorFactory;
import com.zavier.server.connector.impl.SocketChannelConnector;
import com.zavier.server.connector.impl.SocketConnectorConfig;
import com.zavier.server.connector.impl.SocketConnectorFactory;
import com.zavier.server.event.impl.NIOEventListener;
import com.zavier.server.event.impl.SocketEventListener;
import com.zavier.server.handler.impl.FileEventHandler;
import com.zavier.server.handler.impl.NIOEchoEventHandler;
import com.zavier.server.impl.SimpleServer;
import java.util.ArrayList;
import java.util.List;

public class ServerFactory {
    public static Server getServer(ServerConfig serverConfig) {
        List<AbstractConnector> connectorList = new ArrayList<>();
        SocketEventListener socketEventListener = new SocketEventListener(new FileEventHandler(System.getProperty("user.dir")));
        ConnectorFactory connectorFactory = new SocketConnectorFactory(new SocketConnectorConfig(serverConfig.getPort()), socketEventListener);
        // NIO
        NIOEventListener nioEventListener = new NIOEventListener(new NIOEchoEventHandler());
        // 监听 18081 端口
        SocketChannelConnector socketChannelConnector = new SocketChannelConnector(18081,
            nioEventListener);
        connectorList.add(connectorFactory.getConnector());
        connectorList.add(socketChannelConnector);
        return new SimpleServer(connectorList);
    }
}
