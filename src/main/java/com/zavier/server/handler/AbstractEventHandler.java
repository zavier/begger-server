package com.zavier.server.handler;

public abstract class AbstractEventHandler<T> implements EventHandler<T> {

    @Override
    public void handle(T obj) {
        beforeHandle(obj);
        doHandle(obj);
        afterHandle(obj);
    }

    protected void beforeHandle(T obj) {}

    protected abstract void doHandle(T obj);

    protected void afterHandle(T obj) {};
}
