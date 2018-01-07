package com.zavier.server;

import com.zavier.server.config.ServerConfig;
import com.zavier.server.io.IoUtil;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class TestServerAcceptRequest extends TestServerBase{
    private static final Logger logger = LoggerFactory.getLogger(TestServerAcceptRequest.class);

    private static Server server;
    private static final int TIMEOUT = 500;

    @BeforeClass
    public static void init() {
        ServerConfig serverConfig = new ServerConfig();
        server = ServerFactory.getServer(serverConfig);
    }

    @Test
    public void testServerAcceptRequest() {
        // 如果server未启动，则启动
        if (server.getStatus().equals(ServerStatus.STOPED)) {
            startServer(server);
            waitServerStart(server);

            Socket socket = new Socket();
            InetSocketAddress endpoint = new InetSocketAddress("localhost",
                ServerConfig.DEFAULT_PORT);
            try {
                socket.connect(endpoint, TIMEOUT);
                assertTrue("服务器启动后，能接受请求", socket.isConnected());
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            } finally {
                IoUtil.closeQuietly(socket);
            }
        }
    }

    @AfterClass
    public static void destroy() {
        server.stop();
    }
}
