package org.ac.chatirc.client;

import java.io.IOException;
import java.util.Scanner;

public class MainClient {

    public static void main(String[] args) {

        Scanner readScanner = new Scanner(System.in);

        System.out.print("What the server you want connect? ");
        String hostName = readScanner.nextLine();
        System.out.print("What the port of the server? ");
        int port = Integer.parseInt(readScanner.nextLine());

        try {

            Client client = new Client(hostName,port);
            client.connect();

        } catch (IOException e) {
            System.err.println("Error on connection to the server. " + e.getMessage());
        }

    }

}
