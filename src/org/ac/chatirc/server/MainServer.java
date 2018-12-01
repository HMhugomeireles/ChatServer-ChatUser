package org.ac.chatirc.server;

import org.ac.chatirc.server.comunication.Server;

import java.io.IOException;

public class MainServer {

    private static final int SERVER_PORT = 9080;

    public static void main(String[] args) {

        try {

            Server server = new Server(SERVER_PORT);
            server.start();

        } catch (IOException e) {
            System.err.println("Error on start the server socket:: " + e.getMessage());
        }



    }
}
