package org.ac.chatirc.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceiveServer implements Runnable {

    private BufferedReader inMessage;

    public ReceiveServer(Socket clientSocket){

        try {
            inMessage = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Error to try read from the server. " + e.getMessage());
        }
    }

    private void read(){

        try {
            System.out.println(inMessage.readLine());
        } catch (IOException e) {
            System.err.println("Error to read from server. " + e.getMessage());
        }

    }

    @Override
    public void run() {

        while (true){
            read();
        }

    }
}
