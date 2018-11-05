package org.ac.chatirc.client;

import javax.sound.midi.Soundbank;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceiveServer implements Runnable {

    private BufferedReader inMessage;
    private Socket clientSocket;

    public ReceiveServer(Socket clientSocket) {

        this.clientSocket = clientSocket;

        try {
            inMessage = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Error to try read from the server. " + e.getMessage());
        }
    }

    @Override
    public void run() {

        while (true) {
            try {

                String line = inMessage.readLine();

                while (line != null) {

                    System.out.println(line);
                    line = inMessage.readLine();

                }
                synchronized (clientSocket) {
                    System.out.println("You are disconnect from the server.");
                    clientSocket.close();
                    break;
                }
            } catch (IOException e) {
                System.err.println("Error to read from server. " + e.getMessage());
            }
        }

    }
}
