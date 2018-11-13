package org.ac.chatirc.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class FileSend implements Runnable {

    private File file;
    private Socket fileSocket;
    private Client client;

    public FileSend(String path, String hostname, int port, Client client) throws IOException {
        file = new File(path);
        fileSocket = new Socket(hostname,port);
        this.client = client;
    }

    private void connect(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileSocket.getInputStream()));

            String line = bufferedReader.readLine();
            client.printMessage(line);

        }catch (IOException e){
            System.err.println("Error on controller");
        }
    }



    @Override
    public void run() {

    }
}
