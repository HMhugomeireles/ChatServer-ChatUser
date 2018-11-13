package org.ac.chatirc.server.commands.handles;

import org.ac.chatirc.server.commands.CommandHandle;
import org.ac.chatirc.server.commands.messages.Message;
import org.ac.chatirc.server.comunication.Server;
import org.ac.chatirc.server.comunication.User;

public class WhoamiHandle implements CommandHandle {

    @Override
    public void dispatch(Server server, User user, String line) {

        System.out.println(user.getName());
        server.sendTo(Message.WHOAMI
                + user.getName(), user.getName());

    }

}
