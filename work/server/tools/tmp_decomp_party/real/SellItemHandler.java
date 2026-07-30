/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 *  real.Char
 *  real.CharManager
 */
package real;

import io.Message;
import io.Session;
import java.io.IOException;
import real.Char;
import real.CharManager;
import real.ShopVip;
import real.cmd.ICommandHandler;

public class SellItemHandler
implements ICommandHandler {
    @Override
    public void process(Session session, Message message) throws IOException {
        try {
            byte type = message.dis.readByte();
            Char p = CharManager.instance.getByUserID(session.userID);
            if (p == null) {
                return;
            }
            if (type == 0) {
                ShopVip.doSearchItem(p, message);
            } else if (type != 1) {
                // empty if block
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

