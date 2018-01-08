package com.zavier.server.event.impl;

import com.zavier.server.event.EventException;
import com.zavier.server.event.EventListener;
import com.zavier.server.io.IoUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketEventListener implements EventListener<Socket> {
    private static final Logger logger = LoggerFactory.getLogger(SocketEventListener.class);

    @Override
    public void onEvent(Socket socket) throws EventException {
        logger.info("新增连接：" + socket.getInetAddress() + ":" + socket.getPort());
        try {
            echo(socket);
        } catch (IOException e) {
            throw new EventException(e);
        }
    }

    private void echo(Socket socket) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            Scanner scanner = new Scanner(inputStream);
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.append("Server connected. Welcom to echo.\n");
            printWriter.flush();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if ("stop".equals(line)) {
                    printWriter.append("bye bye.\n");
                    printWriter.flush();
                    break;
                } else {
                    printWriter.append(line);
                    printWriter.append("\n");
                    printWriter.flush();
                }
            }
        } finally {
            IoUtil.closeQuietly(inputStream);
            IoUtil.closeQuietly(outputStream);
        }
    }
}
