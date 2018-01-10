package com.zavier.server.connector.impl;

import com.zavier.server.connector.AbstractConnector;
import com.zavier.server.connector.ConnectorException;
import com.zavier.server.event.EventListener;
import com.zavier.server.io.IoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class SocketChannelConnector extends AbstractConnector<SelectionKey> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketChannelConnector.class);
    private static final String LOCALHOST = "localhost";
    private static final int DEFAULT_BACKLOG = 50;
    private final int port;
    private final String host;
    private final int backLog;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean started = false;
    private final EventListener<SelectionKey> eventListener;

    public SocketChannelConnector(int port, String host, int backLog, EventListener<SelectionKey> eventListener) {
        this.port = port;
        this.host = host;
        this.backLog = backLog;
        this.eventListener = eventListener;
    }

    public SocketChannelConnector(int port, EventListener<SelectionKey> eventHandler) {
        this(port, LOCALHOST, DEFAULT_BACKLOG, eventHandler);
    }

    @Override
    protected void init() throws ConnectorException {
        try {
            InetAddress inetAddress = InetAddress.getByName(this.host);
            SocketAddress socketAddress = new InetSocketAddress(inetAddress, this.port);
            this.serverSocketChannel =
                    ServerSocketChannel.open();
            this.serverSocketChannel.configureBlocking(false);
            this.serverSocketChannel.bind(socketAddress, backLog);
            this.started = true;
        } catch (IOException e) {
            throw new ConnectorException(e);
        }
    }

    @Override
    protected void acceptConnect() throws ConnectorException {

        new Thread(() -> {
            try {
                Selector selector = Selector.open();
                SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                while (true) {
                    selector.select();
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator iterator = selectedKeys.iterator();
                    while (iterator.hasNext()) {
                        key = (SelectionKey) iterator.next();
                        if (key.isAcceptable()) {
                            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                            SocketChannel socketChannel = ssc.accept();
                            socketChannel.configureBlocking(false);
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, buffer);
                            iterator.remove();
                            LOGGER.info("NIO Connector accept Connect from {}",
                                    socketChannel.getRemoteAddress());
                        } else if (key.isReadable()) {
                            read(key);
                            iterator.remove();
                        } else if (key.isWritable()) {
                            write(key);
                            iterator.remove();
                        }
                    }
                }
            } catch (ClosedChannelException e) {
                throw new ConnectorException(e);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    private void write(SelectionKey key) {
        whenAccept(key);
    }

    private void read(SelectionKey key) {
        whenAccept(key);
    }

    @Override
    protected void whenAccept(SelectionKey key) throws ConnectorException {
        eventListener.onEvent(key);
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public void stop() {
        this.started = false;
        IoUtil.closeQuietly(this.serverSocketChannel);
    }
}
