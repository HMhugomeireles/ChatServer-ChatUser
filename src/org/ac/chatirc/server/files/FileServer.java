package org.ac.chatirc.server.files;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileServer implements Runnable {

    private final int PORT_FILES = 9595;
    private final int NUMBER_FILE_THREADS = 5;

    private static final String resources = "resources/";

    private ServerSocket serverSocket;
    private ExecutorService fileService;
    private Queue<FileHandle> qfiles;

    public FileServer() {

    }

    private void init() {
        try {
            serverSocket = new ServerSocket(PORT_FILES);
        } catch (IOException e) {
            System.err.println("Error on start the server to manage files. " + e.getMessage());
        }
        fileService = Executors.newFixedThreadPool(NUMBER_FILE_THREADS);
        qfiles = new LinkedList<>();
    }

    private void waitingConnection() {
        Socket userFileConnection = null;

        try {
            userFileConnection = serverSocket.accept();

            fileService.execute(addQfile(userFileConnection, this));

        } catch (IOException e) {
            System.err.println("Error on accept connection. " + e.getMessage());
        }

    }

    private FileHandle addQfile(Socket socket, FileServer fileServer) {
        FileHandle fileHandle = new FileHandle(socket, fileServer);
        synchronized (qfiles) {
            qfiles.add(fileHandle);
        }
        return fileHandle;
    }


    @Override
    public void run() {

        while (true) {
            waitingConnection();
        }

    }
}
