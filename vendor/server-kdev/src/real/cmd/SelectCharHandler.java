   // 
// Decompiled by Procyon v0.6.0
// 
package real.cmd;

import Constant.Checker;
import com.khanhdz.event.TichLuyOnline;
import java.io.IOException;
import real.Item;
import real.EffectBuff;
import real.RegisterAttack;
import data.Net;
import real.RealController;
import real.Map;
import real.Wedding;
import real.MessageCreator;
import data.Database;
import java.util.Date;
import java.util.Vector;
import real.Char;
import real.CharManager;
import server.TeamServer;
import server.InfoClientConnect;
import io.Message;
import io.Session;
import io.SessionManager;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import static real.cmd.LoginHandler.createMsgLogin;

public class SelectCharHandler implements ICommandHandler {

    static byte[][] imgSkill;

    public static void loadImgSkill() {
        if (SelectCharHandler.imgSkill == null) {
            SelectCharHandler.imgSkill = new byte[5][];
            for (int i = 0; i < 5; ++i) {
                try {
                    final FileInputStream fis = new FileInputStream("map/sk" + i + ".png");
                    final DataInputStream dis = new DataInputStream(fis);
                    SelectCharHandler.imgSkill[i] = new byte[dis.available()];
                    int byteRead = 0;
                    while (byteRead != -1) {
                        if (byteRead >= SelectCharHandler.imgSkill[i].length) {
                            break;
                        }
                        final int len = dis.read(SelectCharHandler.imgSkill[i], byteRead, SelectCharHandler.imgSkill[i].length - byteRead);
                        if (len > 0) {
                            byteRead += len;
                        }
                        if (len == -1) {
                            break;
                        }
                    }
                    try {
                        fis.close();
                    } catch (final Exception ex) {
                    }
                    try {
                        dis.close();
                    } catch (final Exception ex2) {
                    }
                } catch (final Exception ex3) {
                }
            }
        }
    }

    @Override
    public void process(final Session session, final Message message) throws IOException {
        try {
            Char p = null;
            final DataInputStream dis = message.dis;
            final int type = dis.readByte();
            final int charDBID = dis.readInt();
            if (session.userID == -1) {
                session.disconnect(20);
                return;
            }
            final InfoClientConnect infoSelect = TeamServer.ALL_IPCONNECT.get(session.ip);
            if (infoSelect != null && !infoSelect.canSelectChar()) {
                try {
                    Thread.sleep(2000L);
                } catch (final Exception ex) {
                }
            }
            Char p2 = CharManager.instance.getByUserID(session.userID);
            if (p2 != null) {
                if (p2.getSession() != null) {
                    p2.getSession().disconnect(10);
                } else {
                    CharManager.instance.kickPlayer(p2, "selectcharhandler 1");
                }
                session.disconnect(11);
                return;
            }
            p = new Char(session);
            Vector<Item> charItem = null;
            final Vector<Date> lastLog = new Vector<Date>();
            charItem = Database.instance.getChar(p, charDBID, lastLog);
            if (p.userID != session.userID) {
                Database.instance.saveOrtherLog("", session.username, "hack dang nhap char " + p.charname + " > " + session.userID + " > " + p.userID, "hackchonchar");
                session.disconnect(0);
                return;
            }
//            if(type > 0 && type < 4)
//            {
//                session.sendMessage(MessageCreator.createServerAlertMessage("Bảo trì chức năng xoá nhân vật!", ""));
//                return;
//            }

            if (type == 1 && p.using == 0) {
                session.sendMessage(MessageCreator.createServerAlertMessage("Chức năng đang bảo trì", ""));
                return;
            }
            if (type == 2) {
                if (p.using == 1) {
//                    session.sendMessage(MessageCreator.createServerAlertMessage("Chức năng đang bảo trì", ""));
//                    return;
                    Database.instance.updateDelChar(charDBID, 0);
                    session.sendMessage(MessageCreator.createServerAlertMessage("Nhân vật đã xoá, không thể khôi phục.", ""));
//                    Database.instance.banChar(p, "xoa nhan vat");
                    session.sendMessage(MessageCreator.createCharListMessage(session.userID, p.getSession()));

                }
                return;
            }
            if (type == 3) {
                if (p.using == 0) {
//                    session.sendMessage(MessageCreator.createServerAlertMessage("Không thể khôi phục nhân vật", ""));
//                    return;

                    session.sendMessage(MessageCreator.createServerAlertMessage("Nhân vật đã được khôi phục.", ""));
                    Database.instance.updateDelChar(charDBID, 1);
                    session.sendMessage(MessageCreator.createCharListMessage(session.userID, p.getSession()));

                }
                return;
            }
            p.userID = session.userID;
            p.initInfoFromDB(charItem, lastLog);
            p.timeXP = (short) session.timeXp;


                if (p.timeOutGame - System.currentTimeMillis() > 570000L && !p.isAdmin && TeamServer.server != 100 && !TeamServer.isServerLocal()) {
                    long time = (p.timeOutGame - System.currentTimeMillis()) / 1000L - 570L;
                    if (time <= 0L) {
                        time = 1L;
                    }
                    final String[] a = {"Bạn chỉ có thể vào lại game sau " + time + " giây nữa", "You only join to game after " + time + " seconds"};
                    if (TeamServer.isServerIndo()) {
                        a[0] = "Anda hanya bergabung ke permainan setelah 5 detik";
                    }
                    session.sendMessage(MessageCreator.createServerAlertMessage(a[0], ""));
                    session.userID = -1;
                    return;
                }
           

            if (!Database.check_active(p.userID)) {
                session.sendMessage(MessageCreator.createServerAlertMessage(LoginHandler.KichHoatTaiKhoan, ""));
                session.userID = -1;
                return;
            }
            p2 = CharManager.instance.getCharByCharName(p.charname);
            if (p2 != null) {
                if (p2.getSession() != null) {
                    p2.getSession().disconnect(9);
                } else {
                    CharManager.instance.kickPlayer(p2, "selectcharhandler 2");
                }
                session.disconnect(12);
                return;
            }
            p.idWedding = -1;
            if (p.idWedding > -1) {
                final Wedding wd = Map.allWedding.get(p.idWedding);
                if (wd != null) {
                    wd.listMember.put(p.charDBID, p.userID);
                    if (wd.action == 1) {
                        p.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Đang diễn ra tiệc cưới mà bạn được mời tham dự. Hãy nhanh chân lên thành Trấn Danh để dự tiệc."));
                    }
                } else {
                    p.idWedding = -1;
                }
            }
            if (p.getSession().bigProvider.equals("1") && p.getSession().privateProvider.equals("1") && !TeamServer.isServerIndo()) {
                TeamServer.addQueue(String.valueOf(p.getSession().username) + "/login/login/" + p.charname);
            }
            try {
              
                Database.instance.saveLogActiveUser(String.valueOf(p.charname) + "_login", String.valueOf(p.lvDetail.lv) + " " + "_" + p.getxu() + "_" + p.getLuong() + "_" + p.getLuongLock() + "_" + p.luongxainhanRuong);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            p.setTimeOutBuff();
            p.id = RealController.intance.idGen.getID(0, "get char from db");
            if (!p.charname.toLowerCase().equals("chienthan")) {
                if (p.strength == 0 || p.agitity == 0) {
                    final int allBasicPoint = p.lvDetail.getBasicPoint(p.lastLV) + (p.lastLV - 1) * 5;
                    p.basepoint = (short) (allBasicPoint - (p.lastLV - 1) * 5);
                    p.skillpoint = (short) (p.lastLV - 1);
                    p.hp = 100;
                    p.mp = 100;
                    p.strength = (short) (Database.basicPoint[p.charClass][0] + (p.lastLV - 1));
                    p.agitity = (short) (Database.basicPoint[p.charClass][1] + (p.lastLV - 1));
                    p.spirit = (short) (Database.basicPoint[p.charClass][2] + (p.lastLV - 1));
                    p.health = (short) (Database.basicPoint[p.charClass][3] + (p.lastLV - 1));
                    p.luck = (short) (Database.basicPoint[p.charClass][4] + (p.lastLV - 1));
                    final int firstSkill = p.skill[0];
                    for (int i = 0; i < p.skill.length; ++i) {
                        if (p.skill[i] >= 0) {
                            p.skill[i] = 0;
                        }
                    }
                    if (firstSkill > 0 || p.lvDetail.lv >= 3) {
                        p.skill[0] = 1;
                    }
                }
                if (p.skillpoint < 0) {
                    p.skillpoint = 0;
                }
                if (p.basepoint < 0) {
                    p.basepoint = 0;
                }
            }
            p.calculateAttrib();
            if (!CharManager.instance.put(p)) {
                final Char pp = CharManager.instance.getByUserID(p.userID);
                if (pp != null) {
                    if (pp.getSession() != null) {
                        pp.getSession().disconnect(13);
                    } else {
                        CharManager.instance.kickPlayer(pp, "selectcharhandler 3");
                    }
                }
                CharManager.instance.remove(p);
                session.disconnect(14);
                return;
            }
            if (RealController.maquee2 != null && RealController.maquee2.length() > 0 && RealController.maquee2 != null && RealController.maquee2.length() > 0) {
                for (int j = 0; j < RealController.dayAlertMarquest2.length; ++j) {
                    if (RealController.dayAlertMarquest2[j].indexOf(Char.getDayOpen(0L)) > -1) {
                        session.sendMessage(MessageCreator.createServerInfoMessage1());
                        break;
                    }
                }
            }
            session.charname = p.charname;
            p.sendMessage(MessageCreator.createSkillInfoMessage(p));
            if (Map.getTown[0] || Map.getTown[1] || Map.getTown[2]) {
                if (p.myCountry > -1) {
                    p.sendMessage(MessageCreator.createMsgStartGetTown(p.myCountry));
                }
                String info = "";
                for (int k = 0; k < 3; ++k) {
                    info = String.valueOf(info) + (Map.getTown[k] ? (String.valueOf(Map.nameCountry[k]) + ",") : "");
                }
                p.sendMessage(MessageCreator.createServerAlertAutoOffMessage("đang diễn ra sự kiện chiếm thành"));
            }
            p.typeFirmware = session.firmWare;
            p.setSizeScreen(session.w, session.h);
            p.calculatorHPMP();
            final Map toMap = RealController.mapList.get(p.mapID);
            if (toMap != null) {
                toMap.playerJoin(p);
            } else {
                final Map offlineMap = RealController.mapList.get(-1);
                offlineMap.playerJoin(p);
            }
            Message m = MessageCreator.createMapMessage(p);
            session.sendMessage(m);
            p.map.doSendDynamicObj(p);
            m = MessageCreator.createMainCharInfoMessage(p);
            try {
                session.sendMessage(m);
                m.cleanup();
            } catch (final Exception e2) {
                try {
                    m = MessageCreator.createMainCharInfoMessage(p);
                    session.sendMessage(m);
                    m.cleanup();
                } catch (final Exception ex2) {
                }
            }
            try {
                m = MessageCreator.createCharWearingMessage(p, p);
                session.sendMessage(m);
                m.cleanup();
                m = MessageCreator.sendWpaLogin(p);
                if (m != null) {
                    session.sendMessage(m);
                    m.cleanup();
                }
                p.sendMessage(MessageCreator.createAnimalWearingMessage(p, p));
            } catch (final Exception e2) {
                e2.printStackTrace();
                try {
                    m = MessageCreator.createCharWearingMessage(p, p);
                    session.sendMessage(m);
                    m.cleanup();
                } catch (final Exception ex3) {
                }
            }
            p.map.sendInfoNpc(p);
            p.map.sendDynamicEff(p);
            try {
                p.sendMessage(MessageCreator.createCharGemItem(p));
            } catch (final Exception e2) {
                e2.printStackTrace();
            }
            try {
                p.sendMessage(MessageCreator.createCharSpecialItem(p));
            } catch (final Exception e2) {
                e2.printStackTrace();
            }
            Label_1964:
            {
                if (p.x2 == 0) {
                    if (p.myCountry <= -1 || Map.minuteX2Country[p.myCountry] <= 0L) {
                        break Label_1964;
                    }
                }
                try {
                    m = new Message(86);
                    m.dos.writeByte(2);
                    m.dos.writeByte(p.x2);
                    m.dos.writeInt(p.getGoldTime());
                    m.dos.writeUTF(p.getInfoX2());
                    p.sendMessage(m);
                    m.cleanup();
                } catch (final Exception e2) {
                    e2.printStackTrace();
                }
            }
//            Database.instance.countChar(p, session.userID);
            Database.instance.getBoardNapTien(p);
            Database.instance.getBoardCreated(p);
            Database.instance.sendNotiLogin(p, m);
            p.doAddGiftEventOffline();
//            p.doAddQuaTichLuy(1);
            p.doAddQuaTichLuy(2);
            p.doAddQuaTichLuy(3);
            //p.setMocNap();
            if (p.getLuong() < 0) {
                Database.instance.banChar(p, "amLuong");
                session.disconnect(11);
            }
            if (p.getLuongLock() < 0) {
                Database.instance.banChar(p, "amLuongLock");
                session.disconnect(11);
            }
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 2));
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 3));
            p.sendInfoQuest();
            try {
                p.sendMessage(MessageCreator.createMsgFriendList(p));
            } catch (final Exception e2) {
                System.out.println("LOI GUI DANH SACH BAN");
            }
            p.checkCreateCharHire();
            if (RealController.maquee.length() > 0) {
                m = MessageCreator.createServerInfoMessage();
                session.sendMessage(m);
            }
            final Message msg = new Message(-8);
            msg.dos.writeByte(1);
            msg.dos.writeByte(0);
            msg.dos.writeShort(SelectCharHandler.imgSkill[p.charClass].length);
            msg.dos.write(SelectCharHandler.imgSkill[p.charClass]);
            p.sendMessage(msg);
            if (session.bigProvider.equals("1")) {
                if (session.privateProvider.equals("1")) {
                    if (p.agenID == -1) {
                        p.agenID = (short) Integer.parseInt(Net.getAgenID(p.getSession().username));
                    }
                    if (p.agenID == -1) {
                        p.agenID = 3;
                    }
                    session.sendMessage(MessageCreator.createSMS(p, "1", new StringBuilder(String.valueOf(p.agenID)).toString(), true));
                } else {
                    session.privateProvider.equals("0");
                }
            } else if (session.bigProvider.equals("0")) {
                String[] provider_agent = Database.instance.getProviderFromTeamUser(session.userID, session.clientProvider, session.agentProvider);
                session.clientProvider = provider_agent[0];
                if (provider_agent[1].equals("-1")) {
                    Database.instance.updateAgentTeamUser(session.userID, session.agentProvider);
                } else {
                    session.agentProvider = provider_agent[1];
                }
                if (p.charname.equals("chienthan") || p.charname.equals("chienthan@tl") || p.charname.equals("chienthan@ms")) {
                    System.out.println("PROVIDER : " + session.clientProvider);
                }
                if (session.idServer == 1) {
                    provider_agent = Database.instance.getProviderFromTeamUser1(session.userID, session.clientProvider, session.agentProvider);
                    session.clientProvider = provider_agent[0];
                    if (provider_agent[1].equals("-1")) {
                        Database.instance.updateAgentTeamUser1(session.userID, session.agentProvider);
                    } else {
                        session.agentProvider = provider_agent[1];
                    }
                    if (p.charname.equals("chienthan") || p.charname.equals("chienthan@tl") || p.charname.equals("chienthan@ms")) {
                        System.out.println("PROVIDER server1: " + session.clientProvider);
                    }
                }
                session.sendMessage(MessageCreator.createSMS(p, session.clientProvider, session.agentProvider, false));
            }
            try {
                // p.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Chào mừng " + p.charname + " đã đến với OpenBeta của Ngũ Đế Online Private, chúc bạn một ngày chơi game vui vẻ nhé. Khi train quái, bạn sẽ nhận được thêm lượng khoá khi còn dưới 500 lượng khoá."));
                if (p.isCharNu() && Char.getDayOpen(0L).equals("2017-03-08")) {
                    p.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Chúc " + p.charname + " 8/3 thật vui vẻ và nhiều niềm vui"));
                }
                session.sendMessage(MessageCreator.createConfig(p));
                session.sendMessage(MessageCreator.createInboxMessage(p));
                if (Map.weather != null) {
                    session.sendMessage(MessageCreator.createMsgWeather(Map.weather.id, Map.weather.n, Map.weather.time));
                }
                if (Map.goldTime) {
                    session.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Giờ vàng đã bắt đầu."));
                }
                if (p.myCountry > -1 && Map.eventKillMonster[p.myCountry]) {
                    session.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Đang diễn ra sự kiện săn Mãnh thú."));
                }
                if (p.myCountry > -1 && Map.nationBeAttack[p.myCountry] > -1) {
                    session.sendMessage(MessageCreator.createServerAlertAutoOffMessage(String.valueOf(Map.nameCountry[Map.nationBeAttack[p.myCountry]]) + " sẽ tấn công lãnh thổ của bạn vào lúc 20h ngày " + Map.nationWar.get(Map.nationBeAttack[p.myCountry]).dayAttack));
                }
                final String infoBoss = Map.getBossAppearMessage(p.inCountry);
                if (!infoBoss.equals("")) {
                    session.sendMessage(MessageCreator.createServerAlertAutoOffMessage(infoBoss));
                }
                p.sendMessage(MessageCreator.createSkillClan(p));
                p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
                p.sendMessage(MessageCreator.createCharInventoryMessage(p, 2));
                p.sendMessage(MessageCreator.createCharInventoryMessage(p, 3));
                if ((Map.checkRcvXP || (Char.isAcountTest(p) && Char.onOffTime180 == 1)) && Map.openLog && p.timeXP >= 180) {
                    p.sendMessage(MessageCreator.createServerAlertMessage("Bạn đã chơi được " + p.timeXP + " phút. Bạn không thể chơi tiếp trong ngày hôm nay.", ""));
                    Thread.sleep(5000L);
                    session.disconnect(1001);
                }
                if (p.charname.equals("chienthan")) {
                    p.sendMessage(MessageCreator.createServerAlertMessage(" Thông báo:\n- Đang có " + SessionManager.instance.size() + " người chơi đang online.", ""));
                }
                if (p.petUsing != null && !p.petUsing.isPetTool() && p.petUsing.getIdEffPet() > -1 && p.addEffBuff(p.petUsing.getIdEffPet(), System.currentTimeMillis() + 320000000L, EffectBuff.BY_ACTOR, 0) != null) {
                    p.sendEffToChar(p);
                }
                message.cleanup();
                // TODO fixx hảo kì luân
                session.sendMessage(createMsgLogin(session.vsTile));
            } catch (final Exception ex4) {
            }

            TichLuyOnline.Instance.onlineGame(p);

        } catch (final Exception e3) {
            e3.printStackTrace();
        }
    }
}
