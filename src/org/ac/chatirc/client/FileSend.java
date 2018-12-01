package org.ac.chatirc.client;

import java.io.*;
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
            byte [] fileBytes  = new byte [(int)file.length()];
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(fileBytes,0,fileBytes.length);
            OutputStream os = fileSocket.getOutputStream();
            System.out.println(file.getName());
            System.out.println(file.isFile());
            //System.out.println("Sending " + file.list().toString() + "(" + fileBytes.length + " bytes)");
            os.write(fileBytes,0, fileBytes.length);
            os.flush();
            System.out.println("Done.");

        }catch (IOException e){
            System.err.println("Error on file. " + e.getMessage());
        }
    }



    @Override
    public void run() {
        connect();
    }
}
