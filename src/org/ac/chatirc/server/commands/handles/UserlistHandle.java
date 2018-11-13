package org.ac.chatirc.server.commands.handles;

import org.ac.chatirc.server.commands.CommandHandle;
import org.ac.chatirc.server.comunication.Server;
import org.ac.chatirc.server.comunication.User;

public class UserlistHandle implements CommandHandle {

    @Override
    public void dispatch(Server server, User user, String line) {

        server.sendTo(server.listUser(), user.getName());

    }

}
