package com.zavier.server.impl;

import com.zavier.server.Server;
import com.zavier.server.ServerStatus;
import com.zavier.server.connector.Connector;
import com.zavier.server.connector.impl.SocketConnector;
import com.zavier.server.config.ServerConfig;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleServer implements Server {
    private static final Logger logger = LoggerFactory.getLogger(SimpleServer.class);

    private volatile ServerStatus serverStatus = ServerStatus.STOPED;
    private int port;
    private List<Connector> connectorList;

    public SimpleServer(ServerConfig serverConfig, List<Connector> connectorList) {
        this.port = serverConfig.getPort();
        this.connectorList = connectorList;
    }

    @Override
    public void start() {
        connectorList.stream().forEach(connector -> connector.start());
        this.serverStatus = ServerStatus.STARTED;
    }

    @Override
    public void stop() {
        connectorList.stream().forEach(connector -> connector.stop());
        this.serverStatus = ServerStatus.STOPED;
        logger.info("Server stop");
    }

    @Override
    public ServerStatus getStatus() {
        return serverStatus;
    }

    @Override
    public int getPort() {
        return port;
    }
}
