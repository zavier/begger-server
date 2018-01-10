package com.zavier.server.event.impl;

import com.zavier.server.event.AbstractEventListener;
import com.zavier.server.handler.EventHandler;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketEventListener extends AbstractEventListener<Socket> {
    private static final Logger logger = LoggerFactory.getLogger(SocketEventListener.class);

    private final EventHandler<Socket> eventHandler;

    public SocketEventListener(EventHandler<Socket> eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    protected EventHandler<Socket> getEventHandler(Socket event) {
        return eventHandler;
    }

}
