package com.zavier.server;

import com.zavier.server.connector.AbstractConnector;
import java.io.IOException;
import java.util.List;

public interface Server {

    /**
     * 启动服务器
     * @throws IOException
     */
    void start() throws IOException;

    /**
     * 关闭服务器
     */
    void stop();

    /**
     * 获取服务器启停状态
     * @return
     */
    ServerStatus getStatus();

    List<AbstractConnector> getConnectorList();

}
