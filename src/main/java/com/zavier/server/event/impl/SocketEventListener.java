package com.zavier.server.event.impl;

import com.zavier.server.event.EventException;
import com.zavier.server.handler.EventHandler;
import com.zavier.server.io.IoUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
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
