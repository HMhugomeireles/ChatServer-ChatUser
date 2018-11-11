package org.ac.chatirc.server;

import java.io.*;
import java.net.Socket;

public class File implements Runnable {

    private static final String resources = "resources/";

    private Socket fileSocket;
    private Server server;



    public File(Socket fileSocket, Server server) {
        this.fileSocket = fileSocket;
        this.server = server;
    }

    public void receiveFile(){
        BufferedReader readFile = null;

        try{

            byte[] mybytearray = new byte[1024];
            InputStream is = fileSocket.getInputStream();
            FileOutputStream fos = new FileOutputStream(resources);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int bytesRead = is.read(mybytearray, 0, mybytearray.length);
            bos.write(mybytearray, 0, bytesRead);
            bos.close();
            fileSocket.close();

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


    @Override
    public void run() {



    }

}
