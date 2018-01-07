package com.zavier.server;

import com.zavier.server.Server;
import com.zavier.server.ServerFactory;
import com.zavier.server.ServerStatus;
import com.zavier.server.config.ServerConfig;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class TestServer extends TestServerBase{
    private static final Logger logger = LoggerFactory.getLogger(TestServer.class);
    private static Server server;

    @BeforeClass
    public static void init() {
        ServerConfig serverConfig = new ServerConfig();
        server = ServerFactory.getServer(serverConfig);
    }

    @Test
    public void testServerStart() throws IOException {
        startServer(server);
        waitServerStart(server);
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
