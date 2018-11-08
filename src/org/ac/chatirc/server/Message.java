package org.ac.chatirc.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

    public static final String UNKNOWN = "Server don't know the command.";

    public static final String CONNECTION = " connect to the server.";

    public static final String CHANGE_NAME = " change the name to ";

    public static final String WHOAMI = "You use the name ->";


    public static String buildMessage(String message, String sender){

        StringBuilder tempMessage = new StringBuilder();

        tempMessage.append(Message.getHourNow() + sender + ":: whisper to you -> " + message);

        return tempMessage.toString();
    }

    public static String getHourNow(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();

        StringBuilder hourNow = new StringBuilder();

        hourNow.append(dateFormat.format(date));
        hourNow.append("->");

        return hourNow.toString();

    }

}
