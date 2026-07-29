/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.DataGame
 *  io.Message
 *  real.Map
 */
package real.cmd;

import data.DataGame;
import io.Message;
import io.Session;
import java.io.IOException;
import real.Map;
import real.MonsterTemplate;
import real.cmd.ICommandHandler;

public class RequestTemplateInfo
implements ICommandHandler {
    @Override
    public void process(Session session, Message message) throws IOException {
        short type = (short)message.dis.readUnsignedByte();
        short idTemplate = message.dis.readShort();
        try {
            if (type == 0) {
                Message msg = new Message(100);
                msg.dos.writeByte(type);
                msg.dos.writeShort(idTemplate);
                msg.dos.writeByte(MonsterTemplate.info[idTemplate].length);
                msg.dos.write(MonsterTemplate.info[idTemplate]);
                MonsterTemplate mt = (MonsterTemplate)Map.monsterTemplates.get(idTemplate);
                msg.dos.writeUTF(mt.name);
                msg.dos.writeByte(mt.he);
                msg.dos.writeInt(mt.maxhp);
                msg.dos.writeByte(mt.palate);
                msg.dos.writeByte(mt.spalate);
                msg.dos.writeByte(mt.isNewMonster);
                if (mt.isNewMonster == 1) {
                    msg.dos.writeShort(DataGame.dataBoss[mt.spalate].length);
                    msg.dos.write(DataGame.dataBoss[mt.spalate]);
                }
                session.sendMessage(msg);
            }
        }
        catch (Exception e) {
            System.err.println("THONG TIN TEMPLATEINFO " + idTemplate);
            e.printStackTrace();
        }
    }
}

