package org.ac.chatirc.server.commands;

import org.ac.chatirc.server.Message;
import org.ac.chatirc.server.Server;
import org.ac.chatirc.server.User;

public class WhoamiHandle implements CommandHandle {

    @Override
    public void dispatch(Server server, User user, String line) {

        System.out.println(user.getName());
        server.sendTo(Message.WHOAMI
                + user.getName(), user.getName());

    }

}
