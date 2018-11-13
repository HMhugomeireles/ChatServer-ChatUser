package org.ac.chatirc.client;

import org.ac.chatirc.server.commands.TreatmentInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    private Socket clientSocket;
    private String hostName;

    public Client(String hostName, int port) throws IOException {
        this.hostName = hostName;
        clientSocket = new Socket(hostName, port);
    }

    public void connect() {
        Thread keyboard = new Thread(new HandleInput(clientSocket, this));
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

        if (!isServerCommand(message)) {
            System.out.println(message);
            return;
        }

        prepareAndStartUpload(message);

    }

    public void printMessage(String message){
        System.out.println(message);
    }

    private void prepareAndStartUpload(String line) {
        String[] action = line.split(" ");

        try {

            Runnable sendFile = new FileSend(action[2], hostName, Integer.parseInt(action[1]), this);
            Thread uploadFile = new Thread(sendFile);

            uploadFile.start();

        } catch (IOException e) {
            System.err.println("Error on prepare the upload file. " + e.getMessage());
        }

    }

    private boolean isServerCommand(String line) {
        String[] commands = line.split(" ");
        if (commands[0].equals("/port")){
            return true;
        }
        return false;
    }

}
