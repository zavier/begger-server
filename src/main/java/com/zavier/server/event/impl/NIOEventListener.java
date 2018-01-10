package com.zavier.server.event.impl;

import com.zavier.server.event.AbstractEventListener;
import com.zavier.server.handler.EventHandler;
import java.nio.channels.SelectionKey;

public class NIOEventListener extends AbstractEventListener<SelectionKey> {

    private final EventHandler<SelectionKey> eventHandler;

    public NIOEventListener(
        EventHandler<SelectionKey> eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    protected EventHandler<SelectionKey> getEventHandler(SelectionKey event) {
        return this.eventHandler;
    }
}
