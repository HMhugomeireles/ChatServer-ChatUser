package org.ac.chatirc.server.commands;

public enum CommandList {
    CLOSE("/quit"),
    LIST_CMD("/cmdlist"),
    NAME("/name"),
    WHISPER("/whisper"),
    UNKNOWN("");

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

    public static String buildStringList(){

        StringBuilder cmdList = new StringBuilder();
        cmdList.append("\n");

        for (CommandList cmd: values()){
            cmdList.append(cmd.getCommand()+ "\n");
        }

        return cmdList.toString();
    }

    public String getCommand() {
        return command;
    }
}
