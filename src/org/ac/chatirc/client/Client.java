package org.ac.chatirc.client;

import org.ac.chatirc.server.commands.TreatmentInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    private Socket clientSocket;
    private String hostName;
    private boolean connection;

    public Client(String hostName, int port) throws IOException {
        this.hostName = hostName;
        connection = true;
        clientSocket = new Socket(hostName, port);
    }

    public void connect() {
        Thread keyboard = new Thread(new HandleInput(clientSocket,this));
        keyboard.start();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while (!clientSocket.isClosed()) {
                waitMessage(reader);
            }

        } catch (IOException e) {
            System.err.println("Error handling socket connection: " + e.getMessage());
        }

    }


    private void waitMessage(BufferedReader reader) throws IOException {
        String message = reader.readLine();

        if (message == null) {
            System.out.println("Connection closed from server side");
            System.exit(0);
        }

        System.out.println(message);
    }

    private void disconnect() throws IOException {
        if (!isConnection()) {
            System.out.println("Your are disconnect from server.");
            clientSocket.close();
            System.exit(0);
        }
    }

    private void readFromServer() {
        BufferedReader inMessage;

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

    }

    private boolean isServerCommand(String line) {
        return TreatmentInput.isCommand(line);
    }

    public boolean isConnection() {
        return connection;
    }

    public void setConnection(boolean state) {
        connection = state;
    }


}
