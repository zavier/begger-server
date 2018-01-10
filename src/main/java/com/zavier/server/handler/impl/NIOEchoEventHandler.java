package com.zavier.server.handler.impl;

import com.zavier.server.handler.AbstractEventHandler;
import com.zavier.server.handler.HandlerException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class NIOEchoEventHandler extends AbstractEventHandler<SelectionKey> {

    @Override
    protected void doHandle(SelectionKey key) {
        try {
            if (key.isReadable()) {
                SocketChannel client = (SocketChannel) key.channel();
                ByteBuffer output = (ByteBuffer) key.attachment();
                client.read(output);
            } else if (key.isWritable()) {
                SocketChannel client = (SocketChannel) key.channel();
                ByteBuffer output = (ByteBuffer) key.attachment();
                output.flip();
                client.write(output);
                output.compact();
            }
        } catch (IOException e) {
            throw new HandlerException(e);
        }
    }
}
