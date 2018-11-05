package org.ac.chatirc.server;

public enum CommandList {
    CLOSE("/quit"),
    UNKNOWN("/");

    private String command;

    CommandList(String command){
        this.command = command;
    }

    public static CommandList getCommandInput(String commandInput) {
        for (CommandList cmd : values()) {
            if (cmd.command.equalsIgnoreCase(commandInput)) {
                return cmd;
            }
        }

        return UNKNOWN;
    }

    public String getCommand() {
        return command;
    }
}
