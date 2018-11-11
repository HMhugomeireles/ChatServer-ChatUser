package org.ac.chatirc.client;

import org.ac.chatirc.server.commands.TreatmentInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    private Socket clientSocket;
    private String hostName;
    private boolean closeConnection;
    private HandleInput handleInput;

    public Client(String hostName, int port) throws IOException {
        this.hostName = hostName;
        closeConnection = false;
        clientSocket = new Socket(hostName, port);
        handleInput = new HandleInput(clientSocket,this);
    }

    public void connect() {

        onReadFromServer();

        scannerAndSend();

        if (isCloseConnection()) {
            System.out.println("Your are disconnect from server.");
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error on close socket.");
            }
            return;
        }
    }

    private void onReadFromServer() {

        while (!isCloseConnection()) {
            System.out.println("1");
            readFromServer();
        }

    }

    private void readFromServer() {
        BufferedReader inMessage;
        System.out.println("read");
        try {

            inMessage = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line = inMessage.readLine();

            if (isServerCommand(line)) {

                String[] action = line.split(" ");

                Runnable sendFile = new FileSend(action[2], hostName, Integer.parseInt(action[1]), this);
                Thread uploadFile = new Thread(sendFile);

                uploadFile.start();
                return;
            }

            System.out.println(line);

        } catch (IOException e) {
            System.err.println("Error to try read from the server. " + e.getMessage());
        }
        System.out.println("2");
    }

    private void scannerAndSend() {
        Runnable scanner = null;
        try {
            scanner = new HandleInput(clientSocket, this);
        } catch (IOException e) {
            System.err.println("Error on start read input. " + e.getMessage());
        }
        Thread input = new Thread(scanner);
        input.start();
    }

    private boolean isServerCommand(String line) {
        return TreatmentInput.isCommand(line);
    }

    public boolean isCloseConnection() {
        return closeConnection;
    }

    public void setCloseConnection(boolean state) {
        closeConnection = state;
    }


}
