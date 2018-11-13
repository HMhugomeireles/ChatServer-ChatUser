package org.ac.chatirc.server.commands.handles;

import org.ac.chatirc.server.commands.CommandHandle;
import org.ac.chatirc.server.comunication.Server;
import org.ac.chatirc.server.comunication.User;

public class CloseHandle implements CommandHandle {


    @Override
    public void dispatch(Server server, User user, String outMessage) {

        server.removeUser(user);
        server.broadcast("@Server:: " + user.getName() + " disconnect from the server.");
        user.close();

    }
}
