package com.zavier.server.handler.impl;

import com.zavier.server.handler.AbstractEventHandler;
import com.zavier.server.handler.HandlerException;
import com.zavier.server.io.IoUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileEventHandler extends AbstractEventHandler<Socket> {

    private final String docBase;

    public FileEventHandler(String docBase) {
        this.docBase = docBase;
    }

    @Override
    protected void doHandle(Socket socket) {
        getFile(socket);
    }

    private void getFile(Socket socket) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            Scanner scanner = new Scanner(inputStream, "UTF-8");
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.append("Server connected. Welcome to File Server.\n");
            printWriter.flush();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if ("stop".equals(line)) {
                    printWriter.append("bye bye.\n");
                    printWriter.flush();
                    break;
                } else {
                    Path filePath = Paths.get(this.docBase, line);
                    // 如果是目录，就打印文件列表
                    if (Files.isDirectory(filePath)) {
                        printWriter.append("目录 ").append(filePath.toString())
                            .append(" 下有文件： ").append("\n");
                        Files.list(filePath).forEach(fileName -> {
                            printWriter.append(fileName.getFileName().toString())
                                .append("\n").flush();
                        });
                        // 如果文件可读，就打印文件内容
                    } else if (Files.isReadable(filePath)) {
                        printWriter.append("File ").append(filePath.toString())
                            .append(" 的内容是: ").append("\n").flush();
                        Files.copy(filePath, outputStream);
                        printWriter.append("\n");
                        // 其他情况返回文件找不到
                    } else {
                        printWriter.append("File ").append(filePath.toString())
                            .append(" is not found.").append("\n").flush();
                    }
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
