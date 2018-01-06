package com.zavier;

import com.zavier.server.Server;
import com.zavier.server.ServerFactory;
import com.zavier.server.ServerStatus;
import com.zavier.server.config.ServerConfig;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestServer {
    private static Server server;

    @BeforeClass
    public static void init() {
        ServerConfig serverConfig = new ServerConfig();
        server = ServerFactory.getServer(serverConfig);
    }

    @Test
    public void testServerStart() {
        server.start();
        assertTrue("服务器启动后，状态是STARTED", server.getStatus().equals(ServerStatus.STARTED));
    }

    @Test
    public void testServerStop() {
        server.stop();
        assertTrue("服务器关闭后，状态是STOPED", server.getStatus().equals(ServerStatus.STOPED));
    }

    @Test
    public void testServerPort() {
        int port = server.getPort();
        assertTrue("默认端口号", ServerConfig.DEFAULT_PORT == port);
    }
}
