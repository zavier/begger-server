package com.zavier.server.connector;


public abstract class Connector<T> implements LifeCycle {

    @Override
    public void start() {
        init();
        acceptConnect();
    }

    protected abstract void init() throws ConnectorException;

    protected abstract void acceptConnect() throws ConnectorException;

    protected abstract void whenAccept(T connect) throws ConnectorException;
}
