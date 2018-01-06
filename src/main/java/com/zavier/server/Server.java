package com.zavier.server;

public interface Server {
    void start();

    void stop();

    ServerStatus getStatus();

    int getPort();
}
