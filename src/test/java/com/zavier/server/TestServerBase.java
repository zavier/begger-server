package com.zavier.server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TestServerBase {
    private static Logger logger = LoggerFactory.getLogger(TestServerBase.class);

    protected void startServer(Server server) {
        new Thread(() -> {
            try {
                server.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    protected void waitServerStart(Server server) {
        // 如果 server 未启动，等待
        while (server.getStatus().equals(ServerStatus.STOPED)) {
            logger.info("等待server启动");
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
