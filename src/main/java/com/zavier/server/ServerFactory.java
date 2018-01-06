package com.zavier.server;

import com.zavier.server.config.ServerConfig;
import com.zavier.server.impl.SimpleServer;

public class ServerFactory {
    public static Server getServer(ServerConfig serverConfig) {
        return new SimpleServer(serverConfig);
    }
}
