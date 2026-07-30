/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  server.TeamServer
 */
package data;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import server.TeamServer;

public class Logger
implements Runnable {
    public static int TYPE_LOGIN = 1;
    public static int TYPE_LOGOUT = 2;
    public static int TYPE_CHAT = 3;
    public static int TYPE_TRANSFER_MONEY = 4;
    public static int TYPE_BUY_AVATAR = 5;
    public static int TYPE_UPDATE_MONEY = 6;
    public static int TYPE_NAP_MONEY = 7;
    private static String[] TYPE_NAMES = new String[]{"", "[LOGIN]", "[LOGOUT]", "[CHAT]", "[TRANSFER MONEY]", "[BUY AVATAR]", "[UPDATE MONEY]", "[NAP MONEY]"};
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
    private static SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
    private static Logger hnd;
    private static List<LogItem> list;
    public static boolean exit;

    private Logger() {
        hnd = this;
        list = new Vector<LogItem>(1000);
        new Thread(this).start();
    }

    public static Logger getInstance() {
        if (hnd == null) {
            new Logger();
        }
        return hnd;
    }

    public static String toDateString(long datetime) {
        return dateFormat.format(new Date(datetime));
    }

    public static void log(int type, String username, String value, String text) {
        if (hnd == null) {
            new Logger();
        }
        Logger logger = hnd;
        logger.getClass();
        LogItem log = logger.new LogItem(type, username, value, text);
        list.add(log);
    }

    public static void writeToFile() {
        String st = "\t";
        if (list != null && list.size() > 0) {
            try {
                String filename = String.valueOf(dateFormat1.format(new Date(Logger.list.get((int)0).time))) + ".txt";
                FileWriter outFile = new FileWriter("serverlog/" + filename, true);
                PrintWriter out = new PrintWriter(outFile);
                while (list.size() > 0) {
                    LogItem log = list.remove(0);
                    try {
                        out.print(TYPE_NAMES[log.type]);
                        out.print(st);
                        out.print(dateFormat.format(new Date(log.time)));
                        out.print(st);
                        out.print(log.username);
                        out.print(st);
                        out.print(log.value);
                        out.print(st);
                        out.print(log.text);
                        out.println();
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                out.flush();
                out.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void logError(Exception e) {
        try {
            FileWriter outFile = new FileWriter("errorlog.txt", true);
            PrintWriter out = new PrintWriter(outFile);
            out.println(dateFormat.format(new Date()));
            e.printStackTrace(out);
            out.println();
            out.flush();
            out.close();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void logErrorPet(Exception e) {
        try {
            FileWriter outFile = new FileWriter("errorlogPet.txt", true);
            PrintWriter out = new PrintWriter(outFile);
            out.println(dateFormat.format(new Date()));
            e.printStackTrace(out);
            out.println();
            out.flush();
            out.close();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void logHack(String text) {
        try {
            FileWriter outFile = new FileWriter("hacklog.txt", true);
            PrintWriter out = new PrintWriter(outFile);
            out.println(dateFormat.format(new Date()));
            out.println(text);
            out.flush();
            out.close();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (TeamServer.running) {
            Logger.writeToFile();
            try {
                Thread.sleep(60000L);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
    }

    private class LogItem {
        public long time = System.currentTimeMillis();
        public int type;
        public String username;
        public String value;
        public String text;

        public LogItem(int type, String username, String value, String text) {
            this.type = type;
            this.username = username;
            this.value = value;
            this.text = text;
        }
    }
}

