package com.zavier.server.connector;


public abstract class Connector implements LifeCycle {

    @Override
    public void start() {
        init();
        acceptConnect();
    }

    protected abstract void init() throws ConnectorException;

    protected abstract void acceptConnect() throws ConnectorException;
}
