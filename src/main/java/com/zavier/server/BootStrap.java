package com.zavier.server;

import com.zavier.server.config.ServerConfig;
import java.io.IOException;

public class BootStrap {

    public static void main(String[] args) throws IOException {
        ServerConfig serverConfig = new ServerConfig();
        Server server = ServerFactory.getServer(serverConfig);
        server.start();
    }
}
