package org.ac.chatirc.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket clientSocket;
    private PrintWriter outMessage;
    private Thread serverSide;

    public Client(String hostName, int port) throws IOException {
        clientSocket = new Socket(hostName, port);

        outMessage = new PrintWriter(clientSocket.getOutputStream(), true);

        serverSide = new Thread(new ReceiveServer(clientSocket));
    }

    public void startCommunication() {

        receiveMessage();

        while (true) {

            read();

        }

    }

    private void receiveMessage(){

        serverSide.start();

    }


    private void read() {

        Scanner reader = new Scanner(System.in);
        outMessage.println(reader.nextLine());

    }

}
