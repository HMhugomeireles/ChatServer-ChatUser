package org.ac.chatirc.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class HandleInput implements Runnable {

    private PrintWriter outMessage;
    private Socket clientSocket;
    private Client client;

    public HandleInput(Socket clientSocket, Client client) throws IOException {

        this.client = client;
        this.clientSocket = clientSocket;
        outMessage = new PrintWriter(clientSocket.getOutputStream(), true);

    }
    private void read() {

        Scanner reader = new Scanner(System.in);
        outMessage.println(reader.nextLine());

    }

    @Override
    public void run() {
        System.out.println("input");
        while(!client.isCloseConnection()){
            read();
        }

    }
}
