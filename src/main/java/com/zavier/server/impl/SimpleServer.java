package com.zavier.server.impl;

import com.zavier.server.Server;
import com.zavier.server.ServerStatus;
import com.zavier.server.config.ServerConfig;

public class SimpleServer implements Server {

    private ServerStatus serverStatus = ServerStatus.STOPED;
    public final int DEFAULT_PORT = 18080;
    private final int PORT;

    public SimpleServer(ServerConfig serverConfig) {
        PORT = serverConfig.getPort();
    }

    @Override
    public void start() {
        this.serverStatus = ServerStatus.STARTED;
        System.out.println("Server start");
    }

    @Override
    public void stop() {
        this.serverStatus = ServerStatus.STOPED;
        System.out.println("Server stop");
    }

    @Override
    public ServerStatus getStatus() {
        return serverStatus;
    }

    @Override
    public int getPort() {
        return PORT;
    }
}
