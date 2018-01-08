package com.zavier.server.event;

public interface EventListener<T> {

    /**
     * 事件发生时的回调方法
     * @param event 事件对象
     * @throws EventException
     */
    void onEvent(T event) throws EventException;
}
