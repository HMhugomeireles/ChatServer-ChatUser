package org.ac.chatirc.server.commands;

public class TreatmentInput {

    public static boolean isCommand(String line) {
        String firstChar = line.substring(0, 1);

        if (firstChar.equals("/")) {
            return true;
        }
        return false;

    }

    public static String getCommandFromLine(String line) {

        if (line.contains(" ")) {

            return line.substring(0, line.indexOf(" "));
        }

        return line;
    }

    public static String getUserFromLine(String line) {

        String temp = line.replaceFirst(getCommandFromLine(line), "")
                .replaceFirst(" ", "");

        if (temp.contains(" ")) {

            return temp.substring(0, temp.indexOf(" "));

        }

        return temp.toString();

    }

    public static String getMessageFromLine(String line) {

        return line.replaceFirst(getCommandFromLine(line), "")
                .replaceFirst(" ", "");

    }

    public static String getMessageFromLineToUser(String line) {

        return line.replaceFirst(getCommandFromLine(line), "")
                .replaceFirst(" ", "")
                .replaceFirst(getUserFromLine(line), "")
                .replaceFirst(" ", "");

    }


}
