package org.ac.chatirc.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket clientSocket;


    public Client(String hostName, int port) throws IOException {
        clientSocket = new Socket(hostName,port);
    }

    public void startComunication(){

        while(true){

            write(read());

        }

    }

    private String read(){

        Scanner reader = new Scanner(System.in);



    }

    private void write(String message){





    }

}
