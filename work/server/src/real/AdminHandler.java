// 
// Decompiled by Procyon v0.6.0
// 
package real;

import real.cmd.LoginHandler;
import util.Logger;
import server.TeamServer;
import data.CharInfo;
import java.util.Vector;
import data.Logdata;
import data.Database;
import data.NewClan;
import java.io.IOException;
import io.Message;
import io.Session;
import real.cmd.ICommandHandler;
import server.localadmin.LocalAdminHttpServer;

public class AdminHandler implements ICommandHandler {

    public static boolean isStopServer;

    static {
        AdminHandler.isStopServer = false;
    }

    @Override
    public void process(final Session session, final Message message) throws IOException {
        try {
            final String st = this.processAdminCommand(message.dis.readUTF());
            final Message m = new Message(47);
            m.dos.writeUTF(st);
            session.sendMessage(m);
            m.cleanup();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void adminRequest(final String info, final boolean shutdown) {
        if (shutdown) {
            try {
                this.notifyAll();
            } catch (final Exception ex) {
            }
//            try {
//                RealController.maquee = "hệ thống chuẩn bị bảo trì sau 1 phút, vui lòng thoát game để tránh mất mát ngoài ý muốn..";
//                if (RealController.maquee.length() > 0) {
//                    final Message m = MessageCreator.createServerInfoMessage();
//                    for (int j = 0; j < CharManager.instance.vChars.size(); ++j) {
//                        CharManager.instance.vChars.elementAt(j).sendMessage(m);
//                    }
//                    m.cleanup();
//                }
//                Thread.sleep(30000);
//            }
//            catch (final Exception ex2) {}
            this.shutdownServer(true);
        } else if (info.startsWith("setinfo")) {
            try {
                RealController.maquee = info.substring(8);
                if (RealController.maquee.length() > 0) {
                    final Message m = MessageCreator.createServerInfoMessage();
                    for (int j = 0; j < CharManager.instance.vChars.size(); ++j) {
                        CharManager.instance.vChars.elementAt(j).sendMessage(m);
                    }
                    m.cleanup();
                }
            } catch (final Exception ex2) {
            }
        }
    }

    public void shutdownServer(boolean scheduleRestart) {
        try {
            this.notifyAll();
        } catch (final Exception ex) {
        }
        System.out.println("CHUAN BI THOAT");
        System.out.println("BAT DAU KHOA REQUEST MOI");
        RealController.intance.beginMaintenancePreparation();
        System.out.println("update clandata");
        NewClan.updateAllClan();
        System.out.println("save char, event, log");
        RealController.intance.saveAllChar(true);
        AdminHandler.isStopServer = true;
        System.out.println("SAVE CHARSELL");
        ItemMarket.saveAllCharSell();
        MonsterVantieu.saveAllMonster();
        Database.instance.saveCooking(Char.nauBanhChung, 1);
        Database.instance.saveCooking(Char.nauBanhTet, 2);
        Database.instance.saveEvent(Map.event.getInfo());
        Database.instance.saveAllLog(Logdata.query);
        System.out.println("save topLienTram");
        for (int i = 0; i < 3; ++i) {
            Database.instance.saveTopienTram(Map.topPK.get(i));
        }
        TeamServer.running = false;
        System.out.println("CHUAN BI THOAT4");
        System.out.println("THOAT");
        Logger.logStringWithDate(String.valueOf(MapTown.count_all_item_sell_per_day) + "_" + ItemSell.ID_ITEM_SELL_AUTO_INCREMENT, "logs/runtime/matketinfo.txt");
        Database.connPool.freeAll();
        if (scheduleRestart) {
            TeamServer.scheduleLauncherRestart();
        }
        LocalAdminHttpServer.stop();
        System.exit(0);
    }

    public void stopServer() {
        this.shutdownServer(false);
    }

    private String processAdminCommand(final String st) throws IOException {
        System.out.println("NOI DUNG adminhandler nhan dc : >>" + st);
        if (st.startsWith("shutdown")) {
            this.shutdownServer(true);
            return "done.";
        }
        if (st.startsWith("setinfo")) {
            RealController.maquee = st.substring(8);
            if (RealController.maquee.length() > 0) {
                final Message m = MessageCreator.createServerInfoMessage();
                for (int j = 0; j < CharManager.instance.vChars.size(); ++j) {
                    CharManager.instance.vChars.elementAt(j).sendMessage(m);
                }
                m.cleanup();
            }
            return "done.";
        }
        if (st.startsWith("stoplogin")) {
            LoginHandler.stopLogin = true;
            return "done2";
        }
        if (st.startsWith("onlogin")) {
            LoginHandler.stopLogin = false;
            return "done2";
        }
        return "unknown command.";
    }
}
