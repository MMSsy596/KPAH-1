/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package real;

import data.Database;
import data.Logdata;
import data.NewClan;
import data.UserLogger;
import io.Message;
import io.SessionManager;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Vector;
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
    private static final int DEFAULT_MAINTENANCE_HOUR = 5;
    private static final int DEFAULT_MAINTENANCE_MINUTE = 30;
    private static volatile int maintenanceHour = DEFAULT_MAINTENANCE_HOUR;
    private static volatile int maintenanceMinute = DEFAULT_MAINTENANCE_MINUTE;
    private static volatile String lastAutoMaintenanceKey = "";
    private static volatile String luckyBagLastGlobalResetKey = "";

    public static void configureMaintenanceSchedule(final int hour, final int minute) {
        maintenanceHour = Math.max(0, Math.min(23, hour));
        maintenanceMinute = Math.max(0, Math.min(59, minute));
    }

    public static void configureLuckyBagGlobalResetState(final String lastResetKey) {
        luckyBagLastGlobalResetKey = lastResetKey == null ? "" : lastResetKey.trim();
    }

    public static void configureAutoMaintenanceState(final String lastMaintenanceKey) {
        lastAutoMaintenanceKey = lastMaintenanceKey == null ? "" : lastMaintenanceKey.trim();
    }

    public static String getMaintenanceScheduleText() {
        return maintenanceHour + ":" + ((maintenanceMinute < 10) ? ("0" + maintenanceMinute) : Integer.toString(maintenanceMinute));
    }

    private static String getCurrentMaintenanceKey() {
        final Calendar cal = Calendar.getInstance();
        final int currentHour = cal.get(Calendar.HOUR_OF_DAY);
        final int currentMinute = cal.get(Calendar.MINUTE);
        if (currentHour < maintenanceHour || (currentHour == maintenanceHour && currentMinute < maintenanceMinute)) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        return UserLogger.dateFormat.format(cal.getTime());
    }

    private static synchronized void persistAutoMaintenanceKey(final String maintenanceKey) {
        lastAutoMaintenanceKey = maintenanceKey == null ? "" : maintenanceKey.trim();
        try {
            java.util.Map<String, String> updates = new LinkedHashMap<>();
            updates.put("sv.lastAutoMaintenanceKey", lastAutoMaintenanceKey);
            TeamServer.updateServerIni(updates);
        } catch (Exception e) {
            System.out.println("Khong luu duoc moc bao tri tu dong: " + lastAutoMaintenanceKey);
            e.printStackTrace();
        }
    }

    private static synchronized boolean shouldRunAutoMaintenance(final boolean startup) {
        final String maintenanceKey = getCurrentMaintenanceKey();
        if (maintenanceKey == null || maintenanceKey.trim().isEmpty()) {
            return false;
        }
        final String normalizedKey = maintenanceKey.trim();
        if (lastAutoMaintenanceKey == null || lastAutoMaintenanceKey.trim().isEmpty()) {
            persistAutoMaintenanceKey(normalizedKey);
            System.out.println("Khoi tao moc bao tri tu dong: " + normalizedKey + (startup ? " (startup)" : ""));
            return false;
        }
        if (lastAutoMaintenanceKey.compareTo(normalizedKey) >= 0) {
            return false;
        }
        if (startup) {
            persistAutoMaintenanceKey(normalizedKey);
            System.out.println("Dong bo moc bao tri tu dong khi khoi dong: " + normalizedKey);
            return false;
        }
        persistAutoMaintenanceKey(normalizedKey);
        return true;
    }

    private static synchronized void persistLuckyBagGlobalResetKey(final String resetKey) {
        luckyBagLastGlobalResetKey = resetKey == null ? "" : resetKey.trim();
        try {
            java.util.Map<String, String> updates = new LinkedHashMap<>();
            updates.put("sv.luckyBagLastGlobalResetKey", luckyBagLastGlobalResetKey);
            TeamServer.updateServerIni(updates);
        } catch (Exception e) {
            System.out.println("Khong luu duoc moc reset tui qua may man: " + luckyBagLastGlobalResetKey);
            e.printStackTrace();
        }
    }

    private static synchronized boolean applyLuckyBagGlobalResetIfNeeded(final String trigger) {
        final String resetKey = Char.getCurrentLuckyBagResetKey();
        if (resetKey == null || resetKey.trim().isEmpty()) {
            return false;
        }
        final String normalizedResetKey = resetKey.trim();
        if (luckyBagLastGlobalResetKey == null || luckyBagLastGlobalResetKey.trim().isEmpty()) {
            persistLuckyBagGlobalResetKey(normalizedResetKey);
            System.out.println("Khoi tao moc reset tui qua may man: " + normalizedResetKey + " (" + trigger + ")");
            return false;
        }
        if (luckyBagLastGlobalResetKey.compareTo(normalizedResetKey) >= 0) {
            return false;
        }

        int onlineResetCount = 0;
        int onlineRemovedBagCount = 0;
        Set<Integer> onlineCharIds = new HashSet<>();
        Vector<Char> onlinePlayers = (Vector<Char>) CharManager.instance.vChars.clone();
        for (int i = 0; i < onlinePlayers.size(); i++) {
            Char player = onlinePlayers.get(i);
            if (player == null || player.isBot != -1 || player.charDBID <= 0) {
                continue;
            }
            onlineCharIds.add(player.charDBID);
        }
        for (int i = 0; i < onlinePlayers.size(); i++) {
            Char player = onlinePlayers.get(i);
            if (player == null || player.isBot != -1 || player.charDBID <= 0) {
                continue;
            }
            int removedLuckyBags = player.forceResetLuckyBagDaily(normalizedResetKey);
            onlineResetCount++;
            onlineRemovedBagCount += removedLuckyBags;
            Database.instance.saveCharAuto(player);
            try {
                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                String info = removedLuckyBags > 0
                        ? "Da reset hop qua may man luc " + getMaintenanceScheduleText() + " va xoa " + removedLuckyBags + " tui qua cu chua dung."
                        : "Da reset hop qua may man ngay moi luc " + getMaintenanceScheduleText() + ".";
                player.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
            } catch (Exception ignored) {
            }
        }

        int[] offlineSummary = Database.instance.resetLuckyBagDailyForAllCharacters(normalizedResetKey, onlineCharIds);
        persistLuckyBagGlobalResetKey(normalizedResetKey);
        System.out.println(
                "Lucky bag global reset [" + trigger + "] key=" + normalizedResetKey
                        + " | online=" + onlineResetCount
                        + " | offline=" + offlineSummary[0]
                        + " | xoa_tui_online=" + onlineRemovedBagCount
                        + " | xoa_tui_offline=" + offlineSummary[1]
        );
        return true;
    }

    @Override
    public synchronized void run() {
        try {
            if (Running) {
                return;
            }
            Running = true;
            System.out.println("Thoi gian bao tri co dinh: " + getMaintenanceScheduleText());
            Database.instance.loadTangExp();
            Database.instance.loadTangNap();
            Database.instance.loadPercentDrop();
            Database.instance.loadTileDrop();
            shouldRunAutoMaintenance(true);
            applyLuckyBagGlobalResetIfNeeded("startup");
            while (Running) {
                Date d = new Date();
                applyLuckyBagGlobalResetIfNeeded("scheduler");
                if (d.getSeconds() % 5 == 0) {
                    BaoTriTay = Database.instance.baotriCMD();
                }
                if (d.getSeconds() == 0) {
                    Net.getHttp(domain + "/put_online.php?online=" + SessionManager.instance.size() + "&sv=" + Server);
                }
                if (d.getMinutes() == 0 && d.getSeconds() == 0) {
                    Database.instance.loadTangExp();
                    Database.instance.loadTangNap();
                    Database.instance.loadPercentDrop();
                    Database.instance.loadTileDrop();
                }
                if (shouldRunAutoMaintenance(false) || BaoTriTay) {
                    try {
                        this.notifyAll();
                    } catch (final Exception ex) {
                    }
                    BaoTriTay = false;
                    System.out.println("Bat dau bao tri " + getMaintenanceScheduleText() + " - se save SQL truoc khi tat server");
                    ((AdminHandler) RealController.getHandler(47)).shutdownServer(true);
                    return;
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
        }
    }
}
