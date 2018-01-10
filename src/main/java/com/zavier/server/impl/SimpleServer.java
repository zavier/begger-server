package com.zavier.server.impl;

import com.zavier.server.Server;
import com.zavier.server.ServerStatus;
import com.zavier.server.connector.AbstractConnector;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleServer implements Server {
    private static final Logger logger = LoggerFactory.getLogger(SimpleServer.class);

    private volatile ServerStatus serverStatus = ServerStatus.STOPED;
    private List<AbstractConnector> connectorList;

    public SimpleServer(List<AbstractConnector> connectorList) {
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
    public List<AbstractConnector> getConnectorList() {
        return connectorList;
    }
}
