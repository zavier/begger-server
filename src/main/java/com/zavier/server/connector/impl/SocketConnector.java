package com.zavier.server.connector.impl;

import com.zavier.server.connector.Connector;
import com.zavier.server.connector.ConnectorException;
import com.zavier.server.event.EventListener;
import com.zavier.server.io.IoUtil;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketConnector extends Connector<Socket> {
    private static final Logger logger = LoggerFactory.getLogger(SocketConnector.class);

    private ServerSocket serverSocket;
    private int port;
    private boolean started;
    private EventListener<Socket> eventListener;

    public SocketConnector(int port, EventListener<Socket> eventListener) {
        this.port = port;
        this.eventListener = eventListener;
    }

    @Override
    protected void init() throws ConnectorException {
        // 监听本地端口
        try {
            this.serverSocket = new ServerSocket(this.port);
            this.started = true;
        } catch (IOException e) {
            throw new ConnectorException(e);
        }
    }

    @Override
    protected void acceptConnect() throws ConnectorException {
        new Thread(() -> {
            while (true && started) {
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                    whenAccept(socket);
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                } finally {
                    IoUtil.closeQuietly(socket);
                }
            }
        }).start();
    }

    @Override
    protected void whenAccept(Socket socketConnect) throws ConnectorException {
        eventListener.onEvent(socketConnect);
    }

    @Override
    public void stop() {
        this.started = false;
        IoUtil.closeQuietly(this.serverSocket);
    }
}
