package org.ac.chatirc.server.commands;

import org.ac.chatirc.server.comunication.Server;
import org.ac.chatirc.server.comunication.User;

public interface CommandHandle {

    void dispatch(Server server, User user, String line);

}
