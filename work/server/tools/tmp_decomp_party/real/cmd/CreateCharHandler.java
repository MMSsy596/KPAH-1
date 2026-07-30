/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  io.Message
 *  real.Map
 *  real.MessageCreator
 *  real.cmd.LoginHandler
 *  server.TeamServer
 */
package real.cmd;

import data.Database;
import io.Message;
import io.Session;
import java.io.DataInputStream;
import java.io.IOException;
import real.Map;
import real.MessageCreator;
import real.cmd.ICommandHandler;
import real.cmd.LoginHandler;
import server.InfoClientConnect;
import server.TeamServer;

public class CreateCharHandler
implements ICommandHandler {
    @Override
    public void process(Session session, Message message) throws IOException {
        DataInputStream dis = message.dis;
        String charName = dis.readUTF();
        byte classID = dis.readByte();
        byte headStyle = dis.readByte();
        int gender = -1;
        byte nation = 0;
        try {
            gender = dis.readByte();
            nation = dis.readByte();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (TeamServer.isServerLienDau()) {
            session.sendMessage(MessageCreator.createServerAlertMessage((String)"Kh\u00f4ng th\u1ec3 t\u1ea1o nh\u00e2n v\u1eadt trong m\u00e1y ch\u1ee7 li\u00ean \u0111\u1ea5u", (String)""));
            return;
        }
        if (LoginHandler.stopLogin) {
            Message m = new Message(2);
            m.dos.writeUTF("1M\u00e1y ch\u1ee7 \u0111ang \u0111ang qu\u00e1 t\u1ea3i, vui l\u00f2ng ch\u1ecdn m\u00e1y ch\u1ee7 kh\u00e1c.");
            session.sendMessage(m);
            m.cleanup();
            message.cleanup();
            return;
        }
        if (session.userID == -1) {
            return;
        }
        InfoClientConnect info = (InfoClientConnect)TeamServer.ALL_IPCONNECT.get(session.ip);
        if (info != null) {
            info.countTaoChar();
            if (info.maxTaoChar >= 10) {
                System.out.println("IP BLOCK taochar " + session.ip);
                try {
                    Thread.sleep(3000L);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                return;
            }
        }
        if (nation == 2) {
            session.sendMessage(MessageCreator.createServerAlertMessage((String)"L\u00e3nh th\u1ed5 Huy\u1ec1n V\u0169 \u0111\u00e3 \u0111\u00f3ng. Vui l\u00f2ng ch\u1ecdn l\u00e3nh th\u1ed5 Thanh Long ho\u1eb7c H\u1eafc H\u1ed5.", (String)""));
            return;
        }
        if (!CreateCharHandler.checkInfo(charName) || Database.haveBadWord((String)charName) || charName.length() > 10 || charName.length() < 4) {
            try {
                session.sendMessage(MessageCreator.createServerAlertMessage((String)"T\u00ean nh\u00e2n v\u1eadt kh\u00f4ng h\u1ee3p l\u1ec7", (String)""));
                return;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (Map.checkFullNation((int)nation, (int)1, (int)1)) {
            session.sendMessage(MessageCreator.createServerAlertMessage((String)(String.valueOf(Map.nameCountry[nation]) + " l\u00e3nh \u0111\u00e3 qu\u00e1 \u0111\u00f4ng. Vui l\u00f2ng ch\u1ecdn l\u00e3nh th\u1ed5 kh\u00e1c."), (String)""));
            return;
        }
        int chID = Database.instance.addChar(session, charName, (int)headStyle, (int)classID, gender, (int)nation);
        if (chID == -1) {
            Message m = new Message(14);
            session.sendMessage(m);
            m.cleanup();
        } else {
            Message m = MessageCreator.createCharListMessage((int)session.userID, (Session)session);
            session.sendMessage(m);
            m.cleanup();
        }
    }

    public static boolean checkInfo(String userName) {
        String nick = userName;
        if (nick.equals("")) {
            return false;
        }
        if (nick.length() < 4 || nick.length() > 15) {
            return false;
        }
        int i = 0;
        while (i < nick.length()) {
            char ch = nick.charAt(i);
            if (!(ch >= '0' && ch <= '9' || ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z')) {
                return false;
            }
            ++i;
        }
        return true;
    }

    public static boolean checkInfoLogin(String userName) {
        String nick = userName;
        if (nick.equals("")) {
            return false;
        }
        int i = 0;
        while (i < nick.length()) {
            char ch = nick.charAt(i);
            if (!(ch >= '0' && ch <= '9' || ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z')) {
                return false;
            }
            ++i;
        }
        return true;
    }
}

