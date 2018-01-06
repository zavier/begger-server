package com.zavier.server.config;

public class ServerConfig {
    public static final int DEFAULT_PORT = 18080;
    private final int port;

    public ServerConfig(int port) {
        this.port = port;
    }

    public ServerConfig() {
        port = DEFAULT_PORT;
    }

    public int getPort() {
        return port;
    }
}
