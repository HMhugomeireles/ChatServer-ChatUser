package org.ac.chatirc.server.files;

import org.ac.chatirc.server.comunication.Server.;

import java.io.*;
import java.net.Socket;

public class FileHandle implements Runnable {



    private Socket fileSocket;
    private FileServer fileServer;


    public FileHandle(Socket fileSocket, FileServer fileServer) {
        this.fileSocket = fileSocket;
        this.fileServer = fileServer;
    }

    public void receiveFile(){
        BufferedReader readFile = null;

        try{

            byte[] bytesfile = new byte[1024];

            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(fileSocket.getOutputStream()));
            DataInputStream dataInputStream = new DataInputStream(new DataInputStream(fileSocket.getInputStream()));


            dataInputStream.read(bytesfile,0,bytesfile.length);


        } catch (IOException e) {
            System.err.println("Error on receive the file. " + e.getMessage());
        }

    }

    public static void write(java.io.File file, OutputStream outputStream) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer, 0, buffer.length);

        while (bytesRead != -1) {
            outputStream.write(buffer, 0, bytesRead);
            bytesRead = inputStream.read(buffer, 0, buffer.length);
        }
    }

    public void sendMessage(){
        try {
            PrintWriter printWriter = new PrintWriter(fileSocket.getOutputStream(), true);

            printWriter.println("Started upload...");

        } catch (IOException e){
            System.err.println("Error on print. " + e.getMessage());
        }
    }

    @Override
    public void run() {



    }

}
