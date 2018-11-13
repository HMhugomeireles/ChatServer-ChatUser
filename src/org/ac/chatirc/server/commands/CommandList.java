package org.ac.chatirc.server.commands;


import org.ac.chatirc.server.commands.handles.*;

public enum CommandList {
    CLOSE("/quit", new CloseHandle(), " ->Close the connection to the server."),
    HELP("/help", new HelpHandle(), " ->List all commands from the server."),
    NAME("/name", new NameHandle(), " ->Change the name /name <New name> "),
    WHISPER("/whisper", new WhisperHandle(), " ->Send direct message to the user /whisper <User> <Message>"),
    WHOAMI("/whoami", new WhoamiHandle(), " ->Show the name you use."),
    USERS_LIST("/userlist", new UserlistHandle(), " ->List all users connect."),
    UNKNOWN("", new UnknowHandle(), "");

    private String command;
    private CommandHandle handle;
    private String description;

    CommandList(String command, CommandHandle handle, String description) {
        this.command = command;
        this.handle = handle;
        this.description = description;
    }

    public static CommandList getCommandInput(String commandInput) {

        for (CommandList cmd : values()) {
            if (cmd.command.equalsIgnoreCase(commandInput)) {
                return cmd;
            }
        }

        return UNKNOWN;
    }

    public static String getAllCommandsList() {

        StringBuilder cmdList = new StringBuilder();

        for (CommandList cmd : values()) {
            cmdList.append("\n").append(cmd.getCommand()).append(cmd.description);
        }

        return cmdList.toString();
    }

    public String getCommand() {
        return command;
    }

    public CommandHandle getHandle() {
        return handle;
    }

    private String getDescription() {
        return description;
    }

}
