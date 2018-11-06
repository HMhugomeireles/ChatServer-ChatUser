package org.ac.chatirc.server.commands;

import org.ac.chatirc.server.Server;
import org.ac.chatirc.server.User;

public interface CommandHandle {

    void dispatch(Server server, User user, String line);

}
