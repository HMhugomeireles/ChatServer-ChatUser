package org.ac.chatirc.server;

import org.ac.chatirc.server.commands.CommandList;
import org.ac.chatirc.server.commands.TreatmentInput;

import java.io.*;
import java.net.Socket;
import java.util.Calendar;

public class User implements Runnable {

    private String name;
    private Socket userSocket;
    private Server server;
    private BufferedReader reader;
    private PrintWriter printer;

    public User(String name, Socket userSocket, Server server) {

        this.name = name;
        this.userSocket = userSocket;
        this.server = server;

    }

    public void read() {

        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            userSocket.getInputStream()));

            String line = reader.readLine();

            if(TreatmentInput.isCommand(line)){

                CommandList cmd = CommandList.getCommandInput(TreatmentInput.getCommandFromLine(line));

                cmd.getHandle().dispatch(server, this, line);

                return;
            }

            server.broadcast(Message.getHourNow() + name + ":: " + line);

        } catch (IOException e) {
            System.err.println("Error to input stream. " + e.getMessage());
        }

    }

    public void write(String message) {

        try {

            printer = new PrintWriter(userSocket.getOutputStream(),true);
            printer.println(message);

        } catch (IOException e) {
            System.err.println("Error on printer the message. " + e.getMessage());
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void close(){
        try {
            userSocket.close();
        } catch (IOException e) {
            System.err.println("Error to close the socket. " + e.getMessage());
        }
    }

    @Override
    public void run() {

        while (!userSocket.isClosed()) {

            read();

        }

    }
}
