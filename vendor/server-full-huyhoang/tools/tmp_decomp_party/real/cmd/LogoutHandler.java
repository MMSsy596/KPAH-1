/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 *  real.Char
 *  real.CharManager
 */
package real.cmd;

import io.Message;
import io.Session;
import java.io.IOException;
import real.Char;
import real.CharManager;
import real.cmd.ICommandHandler;

public class LogoutHandler
implements ICommandHandler {
    @Override
    public void process(Session session, Message message) throws IOException {
        Char p = CharManager.instance.getByUserID(session.userID);
        if (p != null) {
            p.outdelay = 5;
        }
    }
}

