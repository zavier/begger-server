package com.zavier.server.handler.impl;

import com.zavier.server.handler.AbstractEventHandler;
import com.zavier.server.handler.HandlerException;
import com.zavier.server.io.IoUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoEventHandler extends AbstractEventHandler<Socket> {

    @Override
    protected void doHandle(Socket socket) {
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
        } catch (IOException e) {
            throw new HandlerException(e);
        } finally {
            IoUtil.closeQuietly(inputStream);
            IoUtil.closeQuietly(outputStream);
        }
    }
}
