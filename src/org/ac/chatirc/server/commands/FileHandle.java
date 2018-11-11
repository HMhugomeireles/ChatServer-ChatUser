package org.ac.chatirc.server.commands;

import org.ac.chatirc.server.Message;
import org.ac.chatirc.server.Server;
import org.ac.chatirc.server.User;

public class FileHandle implements CommandHandle {

    @Override
    public void dispatch(Server server, User user, String line) {

        server.sendTo(Message.FILE_MESSAGE
                + server.PORT_FILES + " "
                + TreatmentInput.getMessageFromLineToUser(line), user.getName());
        System.out.println("\nUser " + user.getName() + " goes send one file.\n");

    }

}
