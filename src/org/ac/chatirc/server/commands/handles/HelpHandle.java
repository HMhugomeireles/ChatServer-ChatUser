package org.ac.chatirc.server.commands.handles;

import org.ac.chatirc.server.commands.CommandHandle;
import org.ac.chatirc.server.commands.CommandList;
import org.ac.chatirc.server.comunication.Server;
import org.ac.chatirc.server.comunication.User;

public class HelpHandle implements CommandHandle {

    @Override
    public void dispatch(Server server, User user, String line) {

        server.sendTo(CommandList.getAllCommandsList(),user.getName());

    }

}
