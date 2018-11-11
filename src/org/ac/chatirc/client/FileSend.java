package org.ac.chatirc.client;

import java.io.File;
import java.io.IOException;
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



    @Override
    public void run() {

    }
}
