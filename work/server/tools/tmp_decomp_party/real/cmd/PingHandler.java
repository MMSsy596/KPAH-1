/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 */
package real.cmd;

import io.Message;
import io.Session;
import java.io.IOException;
import real.cmd.ICommandHandler;

public class PingHandler
implements ICommandHandler {
    @Override
    public void process(Session session, Message message) throws IOException {
        Message m = new Message(11);
        session.sendMessage(m);
    }
}

