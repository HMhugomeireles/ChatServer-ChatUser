package org.ac.chatirc.server.commands.handles;

import org.ac.chatirc.server.commands.CommandHandle;
import org.ac.chatirc.server.commands.TreatmentInput;
import org.ac.chatirc.server.commands.messages.Message;
import org.ac.chatirc.server.comunication.Server;
import org.ac.chatirc.server.comunication.User;

public class WhisperHandle implements CommandHandle {

    @Override
    public void dispatch(Server server, User user, String line) {

        server.sendTo(Message.buildMessage
                (TreatmentInput.getMessageFromLineToUser(line), user.getName())
                , TreatmentInput.getUserFromLine(line));

    }

}
