package org.ac.chatirc.server.commands.handles;

import org.ac.chatirc.server.commands.CommandHandle;
import org.ac.chatirc.server.commands.messages.Message;
import org.ac.chatirc.server.comunication.Server;
import org.ac.chatirc.server.comunication.User;

public class UnknowHandle implements CommandHandle {

    @Override
    public void dispatch(Server server, User user, String line) {

        server.sendTo(Message.UNKNOWN,user.getName());

    }

}
