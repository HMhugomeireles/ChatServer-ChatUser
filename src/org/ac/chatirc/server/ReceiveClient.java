package org.ac.chatirc.server;

import org.ac.chatirc.server.commands.CommandList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ReceiveClient implements Runnable {

    private String nameClient;
    private Socket client;
    private List<Socket> clientConnectsList;

    public ReceiveClient(Socket clientSocket, List<Socket> clientSocketsList, String nameClient) {
        this.client = clientSocket;
        this.clientConnectsList = clientSocketsList;
        this.nameClient = nameClient;

        addConnection(clientSocket);
    }

    private void addConnection(Socket clientConnection) {

        synchronized (clientConnectsList) {

            clientConnectsList.add(clientConnection);

        }
    }

    private String read() throws IOException {

        BufferedReader serverRead = new BufferedReader(
                new InputStreamReader(
                        client.getInputStream()));

        String line = serverRead.readLine();

        System.out.println(nameClient + ":: " + line);

        return line;

    }

    private void write(String message) {

        synchronized (clientConnectsList) {

            for (Socket clientConnect : clientConnectsList) {

                PrintWriter printWriter = null;
                try {
                    printWriter = new PrintWriter(clientConnect.getOutputStream(), true);

                    printWriter.println(nameClient + ":: " + message);

                } catch (IOException e) {
                    System.err.println("Error to open the write message from server to users. " + e.getMessage());
                } catch (NullPointerException e) {
                    System.err.println("Error to print on user");
                }

            }

        }

    }

    private void writeClient(String message, Socket clientSocket) {

        PrintWriter printWriter = null;
        try {

            printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            printWriter.print("Server command list: " + message);
            printWriter.flush();


        } catch (IOException e) {
            System.err.println("Error to open the write message from server to users. " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Error to print on user");
        }


    }

    private void verifyCommands(String input)  {

        if (!input.contains("/")) {

            write(input);
            return;

        }

        String[] clientInput = input.split(" ");

        CommandList cmd = CommandList.getCommandInput(clientInput[0]);

        switch (cmd) {
            case CLOSE:
                synchronized (clientConnectsList) {
                    clientConnectsList.remove(this);
                    System.out.println(nameClient + " disconnect the server.");
                    closeSocket();
                }
                break;
            case LIST_CMD:
                writeClient(CommandList.buildStringList(), client);
                break;
            case NAME:
                write("change the name to " + clientInput[1]);
                setNameClient(clientInput[1]);
                break;
            case WHISPER:

                break;
        }


    }

    private void setNameClient(String name){
        nameClient = name;
    }

    private void closeSocket() {
        try {
            client.close();
        } catch (IOException e) {
            System.err.println("Error on close the connecting. " + e.getMessage());
        }
    }

    @Override
    public void run() {

        System.out.println(nameClient + " connect to the server.");

        while (!client.isClosed()) {

            String input = null;

            try {

                input = read();

                verifyCommands(input);

            } catch (IOException e) {
                System.err.println("Error ");
            }


        }

    }
}
