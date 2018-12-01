package org.ac.chatirc.server.files;

import org.ac.chatirc.server.comunication.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileServer implements Runnable {

    private final int PORT_FILES = 9595;
    private final int NUMBER_FILE_THREADS = 5;

    public static final String RESOURCES = "resources/";

    private ServerSocket serverSocket;
    private ExecutorService fileService;
    private List<FileHandle> fileHandlesList;
    private Server server;

    public FileServer(Server server) {
        this.server = server;
        init();
    }

    private void init() {
        try {
            serverSocket = new ServerSocket(PORT_FILES);
        } catch (IOException e) {
            System.err.println("Error on start the server to manage fileHandlesList. " + e.getMessage());
        }
        fileService = Executors.newFixedThreadPool(NUMBER_FILE_THREADS);
        fileHandlesList = new LinkedList<>();
    }

    private void waitingConnection() {
        Socket userFileConnection = null;

        try {
            userFileConnection = serverSocket.accept();

            fileService.execute(addFileHandleList(userFileConnection, this));

        } catch (IOException e) {
            System.err.println("Error on accept connection. " + e.getMessage());
        }

    }

    private FileHandle addFileHandleList(Socket socket, FileServer fileServer) {

        FileHandle fileHandle = new FileHandle(socket, fileServer);

        synchronized (fileHandlesList) {
            fileHandlesList.add(fileHandle);
        }

        return fileHandle;
    }

    public void sendMessageDone(String message, String name){
        server.displayMessage(message);
        server.sendTo(message, name);
    }

    @Override
    public void run() {

        while (true) {
            waitingConnection();
        }

    }
}
