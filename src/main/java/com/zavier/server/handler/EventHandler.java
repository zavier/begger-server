package com.zavier.server.handler;

public interface EventHandler<T> {
    void handle(T event);
}
