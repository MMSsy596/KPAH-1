/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 *  real.Char
 *  real.CharManager
 *  real.MessageCreator
 */
package real.cmd;

import io.Message;
import io.Session;
import java.io.DataInputStream;
import java.io.IOException;
import real.Actor;
import real.Char;
import real.CharManager;
import real.MessageCreator;
import real.cmd.ICommandHandler;

public class RequestCharInfoHandler
implements ICommandHandler {
    @Override
    public void process(Session session, Message message) throws IOException {
        short id = 0;
        Char ch = null;
        DataInputStream dis = message.dis;
        id = (short)dis.readUnsignedShort();
        ch = CharManager.instance.getByCharID(id);
        if (ch == null) {
            return;
        }
        if (session.userID == ch.id) {
            return;
        }
        Char charGet = CharManager.instance.getByUserID(session.userID);
        if (charGet != null) {
            try {
                if (ch.posNPCInVilage != null) {
                    Char p = CharManager.instance.getCharByCharName(session.charname);
                    if (p != null) {
                        session.sendMessage(MessageCreator.createCharInfoNpc((Char)ch, (int)p.inCountry, (int)p.mapID));
                    } else if (charGet.near((Actor)ch, (int)charGet.rangeRemoveMonster[0]) && (charGet.map == ch.map || ch.isBot != -1)) {
                        session.sendMessage(MessageCreator.createCharInfo((Char)ch));
                        if (!ch.isCharCopy()) {
                            MessageCreator.createMsgCharMonster((Char)ch, (Char)charGet);
                        } else {
                            MessageCreator.createMsgCharMonsterCopy((Char)ch, (Char)charGet);
                        }
                    }
                } else if (charGet.near((Actor)ch, (int)charGet.rangeRemoveMonster[0]) && charGet.map == ch.map || ch.isBot != -1) {
                    session.sendMessage(MessageCreator.createCharInfo((Char)ch));
                    if (!ch.isCharCopy()) {
                        MessageCreator.createMsgCharMonster((Char)charGet, (Char)ch);
                    } else {
                        MessageCreator.createMsgCharMonsterCopy((Char)charGet, (Char)ch);
                    }
                    ch.sendEffToChar(charGet);
                }
                if (charGet.typeConfig < 3 && charGet.near((Actor)ch, (int)charGet.rangeRemoveMonster[0]) && charGet.map == ch.map || ch.isBot != -1) {
                    session.sendMessage(MessageCreator.createCharWearingCharInfo((Char)ch, (Char)charGet));
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

