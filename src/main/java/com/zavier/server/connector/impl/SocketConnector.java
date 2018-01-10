package com.zavier.server.connector.impl;

import com.zavier.server.connector.AbstractConnector;
import com.zavier.server.connector.ConnectorException;
import com.zavier.server.event.EventListener;
import com.zavier.server.io.IoUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketConnector extends AbstractConnector<Socket> {
    private static final Logger logger = LoggerFactory.getLogger(SocketConnector.class);

    private static final String LOCALHOST = "localhost";
    private static final int DEFAULT_BACKLOG = 50;
    private final int port;
    private final String host;
    private final int backlog;
    private ServerSocket serverSocket;
    private volatile boolean started = false;
    private EventListener<Socket> eventListener;

    public SocketConnector(int port, EventListener<Socket> eventListener) {
        this(port, LOCALHOST, DEFAULT_BACKLOG, eventListener);
    }

    public SocketConnector(int port, String host, int backlog,
        EventListener<Socket> eventListener) {
        this.port = port;
        this.host = StringUtils.isBlank(host) ? LOCALHOST : host;
        this.backlog = backlog;
        this.eventListener = eventListener;
    }

    @Override
    protected void init() throws ConnectorException {
        // 监听本地端口
        try {
            InetAddress inetAddress = InetAddress.getByName(this.host);
            this.serverSocket = new ServerSocket(this.port, backlog, inetAddress);
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

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getHost() {
        return host;
    }
}
