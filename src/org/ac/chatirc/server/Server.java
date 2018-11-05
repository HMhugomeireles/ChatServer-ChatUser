package org.ac.chatirc.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final int NUMBER_THREADS = 100;

    private ServerSocket serverSocket;
    private ExecutorService clientsService;
    private List<Socket> clientsSockets;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientsService = Executors.newFixedThreadPool(NUMBER_THREADS);
        clientsSockets = new LinkedList<>();
    }

    public void start(){
        int count = 1;
        while(true){
            waitConnection(count);
            count++;
        }
    }

    private void waitConnection(int numberClient){
        Socket clientSocket = null;

        try {
            clientSocket = serverSocket.accept();

            String nameClient = "Guess-" + numberClient;

            clientsService.submit(new ReceiveClient(clientSocket, clientsSockets, nameClient ));

        } catch (IOException e) {
            System.err.println("Error on accept connecting. " + e.getMessage());
        }

    }

}
