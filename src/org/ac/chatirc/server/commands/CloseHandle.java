package org.ac.chatirc.server.commands;

import org.ac.chatirc.server.Server;
import org.ac.chatirc.server.User;

public class CloseHandle implements CommandHandle {


    @Override
    public void dispatch(Server server, User user, String outMessage) {

        server.removeUser(user);
        server.broadcast("@Server:: " + user.getName() + " disconnect from the server.");
        user.close();

    }
}
