package com.zavier.server.impl;

import com.zavier.server.Server;
import com.zavier.server.ServerStatus;
import com.zavier.server.config.ServerConfig;
import com.zavier.server.io.IoUtil;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleServer implements Server {
    private static final Logger logger = LoggerFactory.getLogger(SimpleServer.class);

    private volatile ServerStatus serverStatus = ServerStatus.STOPED;
    private ServerSocket serverSocket;
    private final int PORT;

    public SimpleServer(ServerConfig serverConfig) {
        PORT = serverConfig.getPort();
    }

    @Override
    public void start() throws IOException {
        // 监听本地端口，不成功则抛出异常
        this.serverSocket = new ServerSocket(this.PORT);
        this.serverStatus = ServerStatus.STARTED;
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                logger.info("新增连接：" + socket.getInetAddress() + ":" + socket.getPort());
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            } finally {
                IoUtil.closeQuietly(socket);
            }
        }
    }

    @Override
    public void stop() {
        if (this.serverStatus.equals(ServerStatus.STOPED)) {
            System.out.println("Sorry! Server has stoped!");
            return;
        }
        IoUtil.closeQuietly(this.serverSocket);
        this.serverStatus = ServerStatus.STOPED;
        logger.info("Server stop");
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
