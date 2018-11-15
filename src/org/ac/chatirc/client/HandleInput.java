package org.ac.chatirc.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class HandleInput implements Runnable {

    private final int PORT_FILES_SOCKET = 9595;

    private Socket clientSocket;
    private Client client;

    public HandleInput(Socket clientSocket, Client client)  {
        this.client = client;
        this.clientSocket = clientSocket;
    }

    private boolean isCommandSendFile(String line){
        return line.contains("/file");
    }

    private void prepareAndStartUpload(String line) {
        String[] action = line.split(" ");

        try {

            Runnable sendFile = new FileSend(action[2], "localhost", PORT_FILES_SOCKET, client);
            Thread uploadFile = new Thread(sendFile);

            uploadFile.start();

        } catch (IOException e) {
            System.err.println("Error on prepare the upload file. " + e.getMessage());
        }

    }

    @Override
    public void run() {
        try {
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            while (!clientSocket.isClosed()) {
                String input = scanner.nextLine();

                if (isCommandSendFile(input)) {
                    prepareAndStartUpload(input);
                }

                writer.println(input);
            }

            System.exit(0);
        } catch (IOException e) {
            System.err.println("Error handling socket connection: " + e.getMessage());
        }

    }
}
