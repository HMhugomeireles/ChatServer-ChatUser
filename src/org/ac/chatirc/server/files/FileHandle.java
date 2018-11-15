package org.ac.chatirc.server.files;

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
        FileOutputStream saveFile = null;

        try{

            byte[] bytesData = new byte[1024];

            InputStream inputStream = fileSocket.getInputStream();

            FileOutputStream fileOutputStream = new FileOutputStream(FileServer.RESOURCES);

            BufferedOutputStream bufferedOutput = new BufferedOutputStream(fileOutputStream);

            int readBytes = inputStream.read(bytesData,0,bytesData.length);

            int currentBytesRead = readBytes;

            while(readBytes > -1) {
                if (readBytes >= 0){
                    currentBytesRead+= readBytes;
                }
                readBytes = inputStream.read(bytesData, currentBytesRead, (bytesData.length-currentBytesRead));
            }

            bufferedOutput.write(bytesData, 0, currentBytesRead);
            bufferedOutput.flush();

            fileServer.sendMessageDone("File receive.", "Guess-1");


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
