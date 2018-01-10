package com.zavier.server.event;

import com.zavier.server.event.EventException;
import com.zavier.server.handler.EventHandler;
import com.zavier.server.event.EventListener;

public abstract class AbstractEventListener<T> implements EventListener<T> {

    /**
     * 事件处理流程模板方法
     * @param event 事件对象
     * @throws EventException
     */
    @Override
    public void onEvent(T event) throws EventException {
        EventHandler<T> eventHandler = getEventHandler(event);
        eventHandler.handle(event);
    }

    /**
     * 返回事件处理器
     * @param event
     * @return
     */
    protected abstract EventHandler<T> getEventHandler(T event);
}
