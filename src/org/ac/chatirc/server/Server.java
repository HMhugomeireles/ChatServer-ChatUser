package org.ac.chatirc.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final int NUMBER_THREADS = 100;

    private ServerSocket serverSocket;
    private ExecutorService clientsService;
    private List<User> users;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientsService = Executors.newFixedThreadPool(NUMBER_THREADS);
        users = Collections.synchronizedList(new LinkedList<>());
    }

    public void start() {
        int count = 1;

        System.out.println("Server start and wait connection. " + serverSocket.toString());

        while (true) {

            waitConnection(count);
            count++;

        }
    }

    private void waitConnection(int numberClient) {
        Socket userSocket = null;

        try {
            userSocket = serverSocket.accept();

            String nameUser = "Guess-" + numberClient;

            clientsService.submit(addConnection(userSocket, nameUser));

            broadcast(nameUser + Message.CONNECTION);

        } catch (IOException e) {
            System.err.println("Error on accept connecting. " + e.getMessage());
        }

    }

    private User addConnection(Socket userSocket, String name) {

        synchronized (users) {

            User user = new User(name, userSocket, this);
            users.add(user);

            return user;
        }
    }

    public String listUser(){
        StringBuilder userList = new StringBuilder("List of users connect::\n");

        synchronized (users){

            for (User user: users){
                userList.append("\n").append(user.getName());
            }

        }
        userList.append("\n");

        return userList.toString();
    }

    public void broadcast(String message) {

        synchronized (users) {

            System.out.println(message);

            for (User user : users) {

                user.write(message);

            }

        }

    }

    public void sendTo(String message, String destination) {

        synchronized (users){

            for (User user: users){

                if (user.getName().equals(destination)){

                    user.write(message);

                }

            }

        }

    }

    public void removeUser(User user){
        users.remove(user);
    }

}
