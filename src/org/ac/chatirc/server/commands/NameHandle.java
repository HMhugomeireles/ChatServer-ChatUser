package org.ac.chatirc.server.commands;

import org.ac.chatirc.server.Message;
import org.ac.chatirc.server.Server;
import org.ac.chatirc.server.User;

public class NameHandle implements CommandHandle {

    @Override
    public void dispatch(Server server, User user, String line) {

        server.broadcast(user.getName()
                + Message.CHANGE_NAME
                        + TreatmentInput.getUserFromLine(line));

        user.setName(TreatmentInput.getUserFromLine(line));

    }

}
