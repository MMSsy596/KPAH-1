/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 *  real.MessageCreator
 *  real.RealController
 */
package real.cmd;

import io.Message;
import io.Session;
import java.io.IOException;
import real.MessageCreator;
import real.RealController;
import real.cmd.ICommandHandler;

public class PartyHandler
implements ICommandHandler {
    @Override
    public void process(Session session, Message message) throws IOException {
        int size = RealController.intance.idGen.getSize(5);
        if (session.idPrivateParty != -1 || size <= 0) {
            Message m = MessageCreator.createServerAlertMessage((String)"Kh\u00f4ng th\u1ec3 t\u1ea1o th\u00eam party", (String)"");
            session.sendMessage(m);
            m.cleanup();
            return;
        }
        short idP = RealController.intance.idGen.getID(5, "tao party");
        session.idPrivateParty = idP;
        Message m = new Message(48);
        m.dos.writeShort(idP);
        session.sendMessage(m);
        m.cleanup();
    }
}
