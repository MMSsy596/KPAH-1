/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  data.Text
 *  io.Message
 *  real.Char
 *  real.Map
 *  real.MessageCreator
 *  real.cmd.LoginHandler
 *  server.TeamServer
 */
package real;

import data.Database;
import data.Text;
import io.Message;
import io.Session;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Random;
import real.Char;
import real.Map;
import real.MessageCreator;
import real.cmd.ICommandHandler;
import real.cmd.LoginHandler;
import server.TeamServer;

public class RequestRegisterHandler
implements ICommandHandler {
    public static boolean checkInfoLogin(String userName) {
        String nick = userName.trim().toLowerCase();
        if (nick.equals("")) {
            return false;
        }
        int i = 0;
        while (i < nick.length()) {
            char ch = nick.charAt(i);
            if (!(ch >= '0' && ch <= '9' || ch >= 'a' && ch <= 'z' || ch == '@' || ch == '_' || ch == '.' || ch >= 'A' && ch <= 'Z')) {
                return false;
            }
            ++i;
        }
        return true;
    }

    @Override
    public void process(Session session, Message message) throws IOException {
        boolean available;
        String agent;
        String provider;
        String bigProvider;
        String uPass;
        String uname;
        block41: {
            if (!Map.onSMS) {
                session.sendMessage(MessageCreator.createServerAlertMessage((String)"Ch\u1ee9c n\u0103ng \u0111ang b\u1ea3o tr\u00ec. Xin c\u00e1c b\u1ea1n quay l\u1ea1i sau. Xin c\u1ea3m \u01a1n", (String)""));
                return;
            }
            DataInputStream dis = message.dis;
            uname = dis.readUTF();
            uPass = dis.readUTF();
            if (uname.length() < 5 || uname.length() > 20 || !RequestRegisterHandler.checkInfoLogin(uname)) {
                Message m = MessageCreator.createServerAlertMessage((String)Text.REGIST_ERR_ACOUNT, (String)"");
                session.sendMessage(m);
                return;
            }
            if (uPass.length() < 4) {
                Message m = MessageCreator.createServerAlertMessage((String)Text.REGIST_ERR_PASS, (String)"");
                session.sendMessage(m);
                return;
            }
            bigProvider = "0";
            provider = "0";
            agent = "0";
            String IMEI = "";
            int versionCode = 1;
            try {
                bigProvider = dis.readUTF();
                provider = dis.readUTF();
                agent = dis.readUTF();
                IMEI = dis.readUTF();
                versionCode = dis.readInt();
                session.IMEI = IMEI;
                session.versionCode = versionCode;
                session.firmWare = dis.readByte();
            }
            catch (Exception exception) {
                // empty catch block
            }
            session.usernameReg = uname;
            available = false;
            try {
                if (!TeamServer.isServerIndo()) {
                    if (bigProvider.equals("0")) {
                        if (agent.trim().length() > 4) {
                            agent = "0";
                            provider = "0";
                        }
                        available = Database.instance.checkUsername(uname);
                        if (session.firmWare == 2 && session.versionCode > LoginHandler.versionCode) {
                            String sResult = "2";
                            Random r = new Random(System.currentTimeMillis());
                            String IMEIRandom = "AppStore3" + String.valueOf(r.nextInt(10000));
                            try {
                                String link = "http://my.teamobi.com/register/kpah/?username=" + uname + "&email=&pass=" + uPass + "&pro=" + provider;
                                session.sendMessage(MessageCreator.createServerAlertMessage((String)("B\u1ea1n mu\u1ed1n \u0111\u0103ng k\u00fd t\u00e0i kho\u1ea3n " + uname + "?"), (String)link));
                                return;
                            }
                            catch (Exception link) {
                                if (sResult.equals("0")) {
                                    session.sendMessage(MessageCreator.createServerAlertMessage((String)"Ch\u00fac m\u1eebng b\u1ea1n \u0111\u00e3 \u0111\u0103ng k\u00fd th\u00e0nh c\u00f4ng", (String)""));
                                    return;
                                }
                                if (sResult.equals("1")) {
                                    session.sendMessage(MessageCreator.createServerAlertMessage((String)"C\u00f3 l\u1ed7i x\u1ea3y ra. M\u00e3 l\u1ed7i 456", (String)""));
                                    return;
                                }
                                session.sendMessage(MessageCreator.createServerAlertMessage((String)"1 \u0111i\u1ec7n tho\u1ea1i ch\u1ec9 c\u00f3 th\u1ec3 \u0111\u0103ng k\u00fd \u0111\u01b0\u1ee3c 1 t\u00e0i kho\u1ea3n.", (String)""));
                                return;
                            }
                        }
                        if (session.firmWare != 2) break block41;
                        String sResult = "2";
                        try {
                            String link = "http://my.teamobi.com/register/kpah/?username=" + uname + "&email=&pass=" + uPass + "&pro=" + provider;
                            session.sendMessage(MessageCreator.createServerAlertMessage((String)("B\u1ea1n mu\u1ed1n \u0111\u0103ng k\u00fd t\u00e0i kho\u1ea3n " + uname + "?"), (String)link));
                            return;
                        }
                        catch (Exception link) {
                            if (sResult.equals("0")) {
                                session.sendMessage(MessageCreator.createServerAlertMessage((String)"Ch\u00fac m\u1eebng b\u1ea1n \u0111\u00e3 \u0111\u0103ng k\u00fd th\u00e0nh c\u00f4ng", (String)""));
                                return;
                            }
                            if (sResult.equals("1")) {
                                session.sendMessage(MessageCreator.createServerAlertMessage((String)"C\u00f3 l\u1ed7i x\u1ea3y ra. M\u00e3 l\u1ed7i 456", (String)""));
                                return;
                            }
                            break block41;
                        }
                    }
                    available = Database.instance.checkUsernameVTC(uname);
                    break block41;
                }
                boolean bl = available = Database.instance.addNewAcountAuto(uname, uPass) != -1;
                if (!available) {
                    session.sendMessage(MessageCreator.createServerAlertMessage((String)Text.REGIST_ERR_PHONE_NUMBER, (String)""));
                    return;
                }
                session.sendMessage(MessageCreator.createServerAlertMessage((String)(String.valueOf(Text.REGIST_CHUC_MUNG) + " " + uname + " " + Text.REGIST_VOIMATKHAU + " " + uPass + ". " + Text.REGIST_BAO_MAT), (String)""));
                return;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (uname.toLowerCase().equals("lamkckc")) {
            available = false;
        }
        Message m = new Message(39);
        m.dos.writeUTF(String.valueOf(uname.trim()) + " " + uPass.trim());
        m.dos.writeBoolean(available);
        if (!available) {
            session.sendMessage(MessageCreator.createServerAlertMessage((String)"Xin ch\u1ecdn t\u00ean t\u00e0i kho\u1ea3n kh\u00e1c", (String)""));
            return;
        }
        if (bigProvider.equals("0")) {
            session.unameReg = String.valueOf(uname.trim()) + " " + uPass.trim();
            session.available = available;
            session.username = uname.trim().toLowerCase();
            int i = 0;
            while (i < Database.smsNapMe.decript.size()) {
                String dec = Database.smsNapMe.decript.get(i);
                if (dec.toLowerCase().equals("reg" + provider)) {
                    String syntax = Database.smsNapMe.syntax.get(i);
                    if (syntax.indexOf("REF") > -1) {
                        String data = syntax.substring(0, syntax.indexOf("REF") + 3);
                        syntax = syntax.replace(data, String.valueOf(data) + agent);
                    } else if (!agent.equals("0")) {
                        String data = syntax.substring(0, syntax.indexOf(" "));
                        syntax = syntax.replaceAll(data, String.valueOf(data) + agent);
                    }
                    m.dos.writeUTF(syntax);
                    m.dos.writeUTF(Database.smsNapMe.center.get(i));
                    session.syntax = syntax;
                    session.centerReg = Database.smsNapMe.center.get(i);
                    if (session.firmWare == 0 || Char.onOffThamdinh == 0) {
                        session.sendMessage(MessageCreator.createMsgPopUp((int)0, (int)0, (String)("\u0110\u1ec3 t\u1ea1o t\u00e0i kho\u1ea3n. 1 tin nh\u1eafn s\u1ebd \u0111\u01b0\u1ee3c g\u1eedi \u0111\u1ebfn \u0111\u1ea7u s\u1ed1 " + Database.smsNapMe.center.get(i) + " tr\u1ecb gi\u00e1 500vn\u0111")));
                    } else {
                        session.sendMessage(MessageCreator.createMsgInputText((int)0, (int)0, (String)"H\u1ecd v\u00e0 t\u00ean", (int)0));
                    }
                    return;
                }
                ++i;
            }
        } else if (bigProvider.equals("1")) {
            try {
                String stSMS = "";
                String port = "6036";
                if (Database.agent.contains(String.valueOf(agent))) {
                    int i = 0;
                    while (i < Database.smsNapVTC.decript.size()) {
                        if (Database.smsNapVTC.agent.get(i).equals(agent) && Database.smsNapVTC.decript.get(i).toLowerCase().equals("reg")) {
                            stSMS = Database.smsNapVTC.syntax.get(i);
                            port = Database.smsNapVTC.center.get(i);
                            break;
                        }
                        ++i;
                    }
                } else {
                    int i = 0;
                    while (i < Database.smsNapVTC.decript.size()) {
                        if (Database.smsNapVTC.agent.get(i).equals("3") && Database.smsNapVTC.decript.get(i).toLowerCase().equals("reg")) {
                            stSMS = Database.smsNapVTC.syntax.get(i);
                            port = Database.smsNapVTC.center.get(i);
                            break;
                        }
                        ++i;
                    }
                }
                if (stSMS.equals("")) {
                    stSMS = "NQ TDK";
                }
                session.unameReg = String.valueOf(uname.trim()) + " " + uPass.trim();
                session.available = available;
                m.dos.writeUTF(String.valueOf(stSMS) + agent + " ");
                m.dos.writeUTF(port);
                session.syntax = String.valueOf(stSMS) + agent + " ";
                session.centerReg = port;
                session.sendMessage(MessageCreator.createMsgPopUp((int)0, (int)0, (String)("\u0110\u1ec3 t\u1ea1o t\u00e0i kho\u1ea3n. 1 tin nh\u1eafn s\u1ebd \u0111\u01b0\u1ee3c g\u1eedi \u0111\u1ebfn \u0111\u1ea7u s\u1ed1 " + port + " tr\u1ecb gi\u00e1 500vn\u0111")));
                return;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        session.sendMessage(m);
        m.cleanup();
    }

    public static void doProcessInput(Session s, Message msg) {
        try {
            short idActor = msg.dis.readShort();
            byte idMenu = msg.dis.readByte();
            String text = msg.dis.readUTF().trim().toLowerCase();
            if (idActor != 0 && idActor != 1 && idActor != 2 && idActor != 3 && idActor != 4 && idActor == 5) {
                s.sendMessage(MessageCreator.createMsgPopUp((int)0, (int)0, (String)("\u0110\u1ec3 t\u1ea1o t\u00e0i kho\u1ea3n. 1 tin nh\u1eafn s\u1ebd \u0111\u01b0\u1ee3c g\u1eedi \u0111\u1ebfn \u0111\u1ea7u s\u1ed1 " + s.centerReg + " tr\u1ecb gi\u00e1 5000vn\u0111")));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

