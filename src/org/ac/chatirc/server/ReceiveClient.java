package org.ac.chatirc.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

public class ReceiveClient implements Runnable {

    private String nameClient;
    private Socket client;
    private List<Socket> clientConnectsList;

    public ReceiveClient(Socket clientSocket, List<Socket> clientSocketsList, String nameClient) {
        this.client = clientSocket;
        this.clientConnectsList = clientSocketsList;
        this.nameClient = nameClient;

        addConnection(clientSocket);
    }

    private void addConnection(Socket clientConnection) {

        synchronized (clientConnectsList) {

            clientConnectsList.add(clientConnection);

        }
    }

    private String read() throws IOException {
        BufferedReader serverRead = new BufferedReader(
                new InputStreamReader(
                        client.getInputStream()));

        String line = serverRead.readLine();

        System.out.println(nameClient + ":: " + line);

        return line;

    }

    private void write(String message) throws IOException {

        synchronized (clientConnectsList) {

            for (Socket clientConnect : clientConnectsList) {

                PrintWriter printWriter = new PrintWriter(clientConnect.getOutputStream(), true);
                printWriter.println(nameClient + ":: " + message);

            }

        }

    }

    private void closeSocket() {
        try {
            client.close();
        } catch (IOException e) {
            System.err.println("Error on close the connecting. " + e.getMessage());
        }
    }

    @Override
    public void run() {

        System.out.println(nameClient + " connect to the server.");

        while (!client.isClosed()) {

            try {
                write(read());
            } catch (IOException e) {
                System.err.println("Error on read/write the message. " + e.getMessage());
            }

        }

        closeSocket();

    }
}
