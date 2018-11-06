package org.ac.chatirc.server.commands;

import org.ac.chatirc.server.Server;
import org.ac.chatirc.server.User;

public class HelpHandle implements CommandHandle {

    @Override
    public void dispatch(Server server, User user, String line) {

        server.sendTo(CommandList.getAllCommandsList(),user.getName());

    }

}
