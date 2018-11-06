package org.ac.chatirc.server.commands;

import org.ac.chatirc.server.Message;
import org.ac.chatirc.server.Server;
import org.ac.chatirc.server.User;

public class UnknowHandle implements CommandHandle {

    @Override
    public void dispatch(Server server, User user, String line) {

        server.sendTo(Message.UNKNOWN,user.getName());

    }

}
