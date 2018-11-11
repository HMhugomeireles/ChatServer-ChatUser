package org.ac.chatirc.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final int NUMBER_THREADS = 100;
    public final int PORT_FILES = 9595;

    private ServerSocket serverSocket;
    private ServerSocket serverFileSocket;
    private ExecutorService clientsService;
    private ExecutorService fileService;
    private List<User> users;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverFileSocket = new ServerSocket(PORT_FILES);
        clientsService = Executors.newFixedThreadPool(NUMBER_THREADS);
        fileService = Executors.newSingleThreadExecutor();
        users = Collections.synchronizedList(new LinkedList<>());
    }

    public void start() {
        int count = 1;

        System.out.println("Server start and wait connection. " + serverSocket.toString());

        while (true) {

            waitConnection(count);
            waitFile();
            count++;
        }
    }

    private void waitConnection(int numberClient) {
        Socket userSocket = null;

        try {
            userSocket = serverSocket.accept();

            String nameUser = "Guest-" + numberClient;

            //clientsService.submit();
            clientsService.execute(addConnection(userSocket, nameUser));

            broadcast(nameUser + Message.CONNECTION);

        } catch (IOException e) {
            System.err.println("Error on accept connecting. " + e.getMessage());
        }

    }

    private void waitFile(){
        Socket userFileConnection = null;

        try{
            userFileConnection = serverFileSocket.accept();

            File file = new File(userFileConnection, this);

            fileService.execute(file);

        }catch (IOException e){
            System.err.println("Error on accept file connecting. " + e.getMessage());
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
