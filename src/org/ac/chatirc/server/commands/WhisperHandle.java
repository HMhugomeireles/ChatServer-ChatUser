package org.ac.chatirc.server.commands;

import org.ac.chatirc.server.Message;
import org.ac.chatirc.server.Server;
import org.ac.chatirc.server.User;

public class WhisperHandle implements CommandHandle {

    @Override
    public void dispatch(Server server, User user, String line) {

        server.sendTo(Message.buildMessage
                (TreatmentInput.getMessageFromLineToUser(line), user.getName())
                , TreatmentInput.getUserFromLine(line));

    }

}
