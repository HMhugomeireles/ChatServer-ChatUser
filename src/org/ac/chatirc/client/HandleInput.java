package org.ac.chatirc.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class HandleInput implements Runnable {

    private Socket clientSocket;
    private Client client;

    public HandleInput(Socket clientSocket, Client client)  {
        this.client = client;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            while (!clientSocket.isClosed()) {
                String input = scanner.nextLine();

                writer.println(input);
            }

            System.exit(0);
        } catch (IOException e) {
            System.err.println("Error handling socket connection: " + e.getMessage());
        }

    }
}
