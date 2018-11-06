package org.ac.chatirc.server;

public class Message {

    public static final String UNKNOWN = "Server don't know the command.";

    public static final String CONNECTION = " connect to the server.";

    public static final String CHANGE_NAME = " change the name to ";

    public static final String WHOAMI = "You use the name ->";


    public static String buildMessage(String message, String sender){

        StringBuilder tempMessage = new StringBuilder();

        tempMessage.append(sender + ":: whisper to you -> " + message);

        return tempMessage.toString();
    }

}
