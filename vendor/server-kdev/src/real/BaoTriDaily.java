/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package real;

import data.Database;
import data.Logdata;
import data.NewClan;
import io.Message;
import io.SessionManager;
import java.awt.Desktop;
import java.io.File;
import java.util.Date;
import java.util.Random;
import server.TeamServer;
import util.Logger;
import util.Net;

/**
 *
 * @author phong
 */
public class BaoTriDaily implements Runnable {

    public static boolean Running = false;
    public static boolean BaoTriTay = false;
    public static String domain = "";
    public static int Server = 1;
    
    // Thêm biến để lưu giờ và phút bảo trì
    private static int maintenanceHour;
    private static int maintenanceMinute;

    // Khởi tạo thời gian bảo trì random khi class được load
    static {
        Random r = new Random();
        // Random giờ từ 4-5
        maintenanceHour = 4 + r.nextInt(2); 
        // Random phút từ 30-59 nếu là 4h, hoặc 0-30 nếu là 5h
        if (maintenanceHour == 4) {
            maintenanceMinute = 30 + r.nextInt(30);
        } else {
            maintenanceMinute = r.nextInt(31);
        }
        System.out.println("Thời gian bảo trì được set: " + maintenanceHour + ":" + maintenanceMinute);
    }

    @Override
    public synchronized void run() {
        try {
            if (Running) {
                return;
            }
            Running = true;
            System.out.println("Thời gian bảo trì được set: " + maintenanceHour + ":" + maintenanceMinute);
            Database.instance.loadTangExp();
            Database.instance.loadTangNap();
            Database.instance.loadPercentDrop();
            Database.instance.loadTileDrop();
            while (Running) {
                //Map.openvantieu = false;
                Date d = new Date();
                if (d.getSeconds() % 5 == 0) {
                    BaoTriTay = Database.instance.baotriCMD();
                }
                if (d.getSeconds() == 0 && isOnlineReportEnabled()) {
                    Net.getHttp(domain + "/put_online.php?online=" + SessionManager.instance.size() + "&sv=" + Server);
                }
                if (d.getMinutes() == 0 && d.getSeconds() == 0) {
                    Database.instance.loadTangExp();
                    Database.instance.loadTangNap();
                    Database.instance.loadPercentDrop();
                    Database.instance.loadTileDrop();
                }
                if ((d.getHours() == maintenanceHour && d.getMinutes() == maintenanceMinute && d.getSeconds() <= 5) || BaoTriTay) {
                    try {
                        this.notifyAll();
                    } catch (final Exception ex) {
                    }
                    BaoTriTay = false;
                    System.out.println("CHUAN BI THOAT");
                    AdminHandler.isStopServer = true;
                    System.out.println("update clandata");
                    NewClan.updateAllClan();
                    System.out.println("save char, event, log");
                    RealController.intance.saveAllChar(true);
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
                    Logger.logStringWithDate(String.valueOf(MapTown.count_all_item_sell_per_day) + "_" + ItemSell.ID_ITEM_SELL_AUTO_INCREMENT, "matketinfo.txt");
                    try {
                        Desktop.getDesktop().open(new File("run.sh"));
                    } catch (Exception e) {
                    }
                    System.exit(0);
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
        }
    }

    private static boolean isOnlineReportEnabled() {
        return domain != null && domain.startsWith("http") && domain.indexOf("domain.com") == -1;
    }
}
