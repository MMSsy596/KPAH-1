package server;

import data.*;
import io.LogInController;
import io.Session;
import io.SessionManager;
import logNQSH.DBUserLogger;
import real.Map;
import real.*;
import real.cmd.LoginHandler;
import real.cmd.SelectCharHandler;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.SwingUtilities;

public class TeamServer {

    public static boolean running;
    public static int PORT;
    private static ServerControllerManager controllerManager;
    private static ServerSocket listenSocket;
    public static long timeStartServer;
    public static long timeRemoveSecssion;
    public static int maxCCU;
    public static int minCCU;
    public static int maxUser;
    public static int minCCUAD;
    public static int maxCCUAD;
    public static short LIMIT_CCU;
    static RealController realController;
    public static long timUpBoard;
    public static long timeOutBoard;
    public static long timeRemoveChar;
    public static long timeRemovePool;
    public static long timeBoardRun;
    public static long timeDownBoard;
    public static long timeDouble;
    public static boolean isDouble;
    public static int update;
    public static int maxBackup;
    public static String lastDay;
    public static LinkedBlockingQueue<String> userLogQueue;
    public static GameCanvas gameCanvas;
    public static int nConnect;
    public static int server;
    public static int portNapTien;
    public static boolean haveMsgAdmin;
    public static boolean changRange;
    public static byte MAX_SNAKE;
    private static String url_run;
    private static int timeStart;
    public static boolean isStart;
    private static int minute;
    public static byte language;
    public static Vector<InfoClientConnect> V_IP_BLOCK;
    public static Hashtable<String, InfoClientConnect> ALL_IPCONNECT_BLOCK;
    public static Hashtable<String, InfoClientConnect> ALL_IPCONNECT;
    static Hashtable<String, CharInfo> charinfo;
    public static String stHelp;

    public static String ipLogin = null;
    public static int portLogin = 8023;

    public static boolean ExpVantieu;
    public static boolean ExpNhanSam;
    public static boolean LuckyExp;
    public static boolean ExpQua;
    public static boolean CodeTest;
    private static boolean enableAdminPanel;

    public static boolean isSuKien83;
    public static boolean isSuKienTrungThul2016;
    public static boolean isSuKienTet2017;
    public static boolean isSuKienGioTo2016;
    public static boolean isSuKienNoel2023;
    public static boolean isSuKienTetduonglich2024;


    static {
        TeamServer.running = true;
        TeamServer.listenSocket = null;
        TeamServer.maxCCU = 0;
        TeamServer.minCCU = 0;
        TeamServer.maxUser = 0;
        TeamServer.minCCUAD = 0;
        TeamServer.LIMIT_CCU = 1000;
        TeamServer.timeBoardRun = 3000L;
        TeamServer.timeDownBoard = 1500L;
        TeamServer.timeDouble = System.currentTimeMillis();
        TeamServer.isDouble = false;
        TeamServer.update = 0;
        TeamServer.maxBackup = 0;
        TeamServer.lastDay = "";
        TeamServer.userLogQueue = new LinkedBlockingQueue<>();
        long exp = LevelDetail.getXpFromLevel(40);
        LevelDetail.getLevelFromExp(exp + 750000000L);
        LevelPet.initExpTemplate();
        TeamServer.gameCanvas = new GameCanvas();
        TeamServer.nConnect = 0;
        TeamServer.server = 1;
        TeamServer.portNapTien = 19153;
        TeamServer.haveMsgAdmin = false;
        TeamServer.changRange = false;
        TeamServer.MAX_SNAKE = 2;
        TeamServer.isStart = false;
        TeamServer.language = 0;
        TeamServer.V_IP_BLOCK = new Vector<>();
        TeamServer.ALL_IPCONNECT_BLOCK = new Hashtable<>();
        TeamServer.ALL_IPCONNECT = new Hashtable<>();
        TeamServer.charinfo = new Hashtable<>();
        TeamServer.stHelp = "";

        TeamServer.ExpVantieu = false;
        TeamServer.LuckyExp = false;
        TeamServer.ExpQua = false;
        TeamServer.CodeTest = false;
        TeamServer.enableAdminPanel = false;

    }

    public static String getCurrentDayTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(new Date(System.currentTimeMillis()));
    }

    public ServerController getController(int serviceID) {
        return TeamServer.controllerManager.getController(serviceID);
    }

    public static void main(String[] args) {

        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            System.setErr(new PrintStream(System.err, true, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        run();
    }

    public void shutdown() {
        TeamServer.running = false;
        try {
            if (TeamServer.listenSocket != null) {
                TeamServer.listenSocket.close();
            }
        } catch (IOException ignored) {
        }
    }

    public static String myTake() throws InterruptedException {
        return TeamServer.userLogQueue.take();
    }

    public static void addQueue(String info) {
        try {
            if (TeamServer.userLogQueue.size() < 10000) {
                TeamServer.userLogQueue.add(info);
            }
        } catch (Exception ignored) {
        }
    }

    public static void reloadMaxLG() {
        try {
            Properties p = new Properties();
            p.load(Files.newInputStream(Paths.get("server.ini")));
            TeamServer.MAX_SNAKE = Byte.parseByte(p.getProperty("sv.LG"));

        } catch (Exception e) {
            System.out.println("Load configuration file error!");
            e.printStackTrace();
        }
    }

    public static boolean isServerIndo() {
        return TeamServer.language == 1;
    }

    public static boolean isServerLienDau() {
        return TeamServer.server == 50;
    }

    public static boolean isServerLocal() {
        return TeamServer.server == 0;
    }

    public static boolean isServerTest() {
        return TeamServer.server == 0;
    }

    public static boolean isServerHoaLu() {
        return TeamServer.server == 7 && Map.openLog;
    }

    public static boolean isServerKinhMon() {
        return TeamServer.server == 5 && Map.openLog;
    }

    public static boolean isServerHoangMy() {
        return TeamServer.server == 3 && Map.openLog;
    }

    public static boolean isServerTrangTien() {
        return TeamServer.server == 2 && Map.openLog;
    }

    public static boolean isServerDaiLa() {
        return TeamServer.server == 1 && !Map.openLog;
    }

    public static boolean isServerDaiViet() {
        return TeamServer.server == 3 && !Map.openLog;
    }

    public static boolean isServerVanLang() {
        return TeamServer.server == 5 && !Map.openLog;
    }

    public static void run() {
        System.out.println("LoginServer disabled: using local kpah1.team_user authentication.");

        try {
            Properties p = new Properties();
            p.load(Files.newInputStream(Paths.get("server.ini")));
            TeamServer.enableAdminPanel = isEnabled(p.getProperty("sv.adminpanel", "0"));
            BaoTriDaily.Server = Integer.parseInt(p.getProperty("api.sv"));
            BaoTriDaily.domain = p.getProperty("api.url");
            TeamServer.PORT = Integer.parseInt(p.getProperty("sv.port"));
            TeamServer.LIMIT_CCU = Short.parseShort(p.getProperty("limit.ccu"));
            TeamServer.server = Integer.parseInt(p.getProperty("sv.server"));
            TeamServer.portNapTien = Integer.parseInt(p.getProperty("sv.portnap"));
            TeamServer.MAX_SNAKE = Byte.parseByte(p.getProperty("sv.LG"));
            TeamServer.changRange = (Byte.parseByte(p.getProperty("sv.range")) == 1);
            UserLogger.PATH = p.getProperty("sv.pathLog");
            TeamServer.ipLogin = p.getProperty("sv.iplogin");
            TeamServer.portLogin = Integer.parseInt(p.getProperty("sv.portlogin"));
            Database.setLink(p.getProperty("db.host"), p.getProperty("db.name"), p.getProperty("db.user"), p.getProperty("db.password"), p.getProperty("db.maxco"));
            Database.setLinkBoardNap(p.getProperty("db.host"), p.getProperty("db.nameNap"), p.getProperty("db.userNap"), p.getProperty("db.pass"), p.getProperty("db.maxNap"));
            Database.setLinkGiftCode(p.getProperty("db.host"), p.getProperty("db.nameNap"), p.getProperty("db.userNap"), p.getProperty("db.pass"), p.getProperty("db.maxNap"));
            Database.setLink1(p.getProperty("db.host1"), p.getProperty("db.name1"), p.getProperty("db.user1"), p.getProperty("db.password1"), p.getProperty("db.maxco1"));
            try (java.sql.Connection ignored = Database.instance.getConnection()) {
                // Fail fast here so wrong DB credentials don't trigger noisy startup cascades.
            } catch (Exception dbEx) {
                System.out.println("Database login failed. Check server.ini keys: db.host, db.name, db.user, db.password.");
                dbEx.printStackTrace();
                return;
            }
            Database.tableRace = p.getProperty("sv.tablerace");
            Database.createRace = Byte.parseByte(p.getProperty("sv.createRace"));
            Database.dayCreateRace = p.getProperty("sv.dayrace");
            TeamServer.url_run = p.getProperty("db.run");
            TeamServer.timeStart = Integer.parseInt(p.getProperty("db.hour"));
            TeamServer.minute = Integer.parseInt(p.getProperty("db.minute"));
            TeamServer.language = Byte.parseByte(p.getProperty("sv.lang"));
            Map.openLog = (Byte.parseByte(p.getProperty("sv.me")) == 1);
            System.out.println("Luckyexp config: " + p.getProperty("sv.Luckyexp"));
            TeamServer.ExpVantieu = Byte.parseByte(p.getProperty("sv.expvantieu")) == 1;
            TeamServer.LuckyExp = Byte.parseByte(p.getProperty("sv.Luckyexp")) == 1;
            TeamServer.ExpQua = Byte.parseByte(p.getProperty("sv.ExpQua")) == 1;
            TeamServer.CodeTest = Byte.parseByte(p.getProperty("sv.CodeTest")) == 1;
            Database.ENABLE_ACTIVE_CHECK = Byte.parseByte(p.getProperty("sv.EnableAvtiveCheck")) == 1;

            TeamServer.isSuKienTrungThul2016 = Byte.parseByte(p.getProperty("sv.isSuKienTrungThul2016")) == 1;
            TeamServer.isSuKienTet2017 = Byte.parseByte(p.getProperty("sv.isSuKienTet2017")) == 1;
            TeamServer.isSuKienGioTo2016 = Byte.parseByte(p.getProperty("sv.isSuKienGioTo2016")) == 1;
            TeamServer.isSuKienNoel2023 = Byte.parseByte(p.getProperty("sv.isSuKienNoel2023")) == 1;
            TeamServer.isSuKienTetduonglich2024 = Byte.parseByte(p.getProperty("sv.isSuKienTetduonglich2024")) == 1;
            startAdminPanelIfEnabled();
            new Logdata();
            try {
                String danhsach = p.getProperty("sv.ph");
                Char.except_quay_so = Char.split(danhsach, ",");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String danhsach = p.getProperty("sv.choi");

                Char.chartopchoitrungthu = danhsach;
            } catch (Exception e) {
                e.printStackTrace();

            }
        } catch (Exception e) {
            System.out.println("Load configuration file error!");
            e.printStackTrace();
            return;
        }

        Database.startSaveChar();
        TeamServer.timeRemovePool = System.currentTimeMillis();
        System.out.println("Starting server...");
        (TeamServer.controllerManager = new ServerControllerManager()).addServerController(2, TeamServer.realController = new RealController());
        Map.loadNpcServer();
        // real.plugins.addShop.loadShopDB_ND();
        RealController.intance.initData();

        Database.addClanToMap();
        Map.getAllTopBegin();
        Map.startSendInfoAdv();
        Map.addWeddingToPrivateMap();
        new Thread(DBUserLogger.instance).start();
        DataGame.load();
        Market.init();
        Database.loadAllCharSell();
        String nt = new Date(System.currentTimeMillis()).toString();
        if (!Char.isSuKienHaloween2016() && !Char.isSuKienTet2017() && !Char.isSuKienNoel() && !Char.isSuKienGioTo2016() && !Char.isSuKienHe2017() && !Char.isSuKienMini() && !Char.isSuKienMiniChucNu() && !Char.isSuKienTrungThul2016()) {
            Map.isSale = nt.startsWith("Tue");
        }
        if (Char.getDayOpen(0L).equals("2015-10-06")) {
            TeamServer.isDouble = true;
            TeamServer.timeDouble = System.currentTimeMillis();
            Map.doubleALL = 2;
        }
        Database.instance.loadAllMonsterVantieu();
        Map.initGiftCode();
        Char.timeGetPhuongHoang = System.currentTimeMillis() + 36000000L;
        Map.ThreadProcessHoiSinh();
        Char.startThreadXoso();
        Char.startThreadXosoLow();
        Char.startNauBanh();
        if (!real.BaoTriDaily.Running) {
            new Thread(new real.BaoTriDaily()).start();
        }
        String daycheck = Char.getDayTime(0L);
        if (daycheck.compareTo("2020-11-18 17:15:00") < 0) {
            Char.initINfoCheckTrungItemVV();
            Char.total_open = 0;
            Char.numberLucky = 5000;
            Database.instance.saveEvent(Map.event.getInfo());
            System.err.println("KHOI TAO GIA TRI RANNDOM NHAN QUA TEAMSERVER " + Map.getInfoSavetrungpet());
        }
        if (isServerLienDau()) {
            Map.onSMS = false;
        }
        new Thread(() -> {
            while (true) {
                Calendar cl = Calendar.getInstance();
                int iHour = cl.get(Calendar.HOUR_OF_DAY);
                int iMinute = cl.get(Calendar.MINUTE);
                if (iHour == TeamServer.timeStart && iMinute >= TeamServer.minute && iMinute <= TeamServer.minute + 1 && !TeamServer.isStart) {
                    TeamServer.isStart = true;
                    try {
                        Thread.sleep(30000L);
                        System.out.println("CHUAN BI TAT SV");
                        ((AdminHandler) RealController.getHandler(47)).stopServer();
                        Thread.sleep(30000L);
                        System.exit(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (iHour != TeamServer.timeStart) {
                    TeamServer.isStart = false;
                }

                try {
                    Thread.sleep(30000L);
                    Database.instance.loadTimeX2Server();
                } catch (InterruptedException ignored) {
                }
            }
        }).start();
        new Thread(() -> {
            Thread.currentThread().setName("THREAD CHECK TIME LIFE BOSS");
            while (TeamServer.running) {
                try {
                    if (!Map.openLog) {
                        if (TeamServer.userLogQueue.size() == 0) {
                            Thread.sleep(10L);
                        } else {
                            String info = TeamServer.myTake();
                            Net.requestLink(info);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("LOI GOI LINK VTC TRONG TEAM");
                }
                if (TeamServer.isDouble) {
                    Map.doubleALL = 2;
                    if (System.currentTimeMillis() - TeamServer.timeDouble > 86400000L) {
                        TeamServer.isDouble = false;
                        Map.doubleALL = 1;
                    }
                }
                if (System.currentTimeMillis() - TeamServer.timUpBoard >= TeamServer.timeBoardRun) {
                    if (TeamServer.timUpBoard != 0L) {
                        TeamServer.timeOutBoard = System.currentTimeMillis();
                    }
                    TeamServer.timUpBoard = System.currentTimeMillis();
                }
                try {
                    Thread.sleep(10L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    for (int i = 0; i < Map.boss.size(); ++i) {
                        Map.boss.get(i).checkTimeLife();
                    }
                    for (int i = 0; i < Map.bossTime.size(); ++i) {
                        Map.bossTime.get(i).checkTimeLife();
                    }
                    Map.refreshIdleRandomBosses();
                    Thread.sleep(1000L);
                } catch (Exception ignored) {
                }
            }
        }).start();
        new NapTien();
        readText("map/help.txt");
        new Thread(() -> {
            Thread.currentThread().setName("LOG CCU");
            while (TeamServer.running) {
                TeamServer.maxCCU = (Math.max(TeamServer.maxCCU, SessionManager.instance.size()));
                TeamServer.minCCU = (Math.min(TeamServer.minCCU, SessionManager.instance.size()));
                TeamServer.maxUser = (Math.max(TeamServer.maxUser, CharManager.instance.totalChar()));
                long t = System.currentTimeMillis();
                if (t - TeamServer.timeRemovePool > 60000L) {
                    try {
                        Database.connPool.closeIdleConnection();
                        Database.connPoolNap.closeIdleConnection();
                    } catch (Exception ignored) {
                    }
                    TeamServer.timeRemovePool = System.currentTimeMillis();
                }
                if (t - TeamServer.timeStartServer > 3600000L) {
                    TeamServer.timeStartServer = System.currentTimeMillis();

                    TeamServer.maxCCU = SessionManager.instance.size();
                    TeamServer.minCCU = SessionManager.instance.size();
                }
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        TeamServer.timeStartServer = System.currentTimeMillis();
        TeamServer.timeRemoveSecssion = System.currentTimeMillis();
        TeamServer.maxCCU = 0;
        TeamServer.minCCU = 0;
        new Thread(() -> {
            Thread.currentThread().setName("HUY SESSION WA LAU");
            TeamServer.timeRemoveChar = System.currentTimeMillis();
            while (TeamServer.running) {
                long t = TeamServer.timeRemoveSecssion = System.currentTimeMillis();
                Vector<Session> sessionList = SessionManager.instance.sessionList;
                int size = sessionList.size();
                int firmwareAnd = 0;
                for (int i = size - 1; i >= 0; --i) {
                    try {
                        Session s = sessionList.get(i);
                        if (s != null) {
                            CCUProvider ccu = Database.allProvider.get("0");
                            if (ccu != null) {
                                ++ccu.tempCCU;
                                int[] ccuFirmWareTemp = ccu.ccuFirmWareTemp;
                                byte firmWare = s.firmWare;
                                ++ccuFirmWareTemp[firmWare];
                            }
                            if (s.firmWare == 1) {
                                ++firmwareAnd;
                            }
                            if (s.userID == -1 && t - s.connectTime > 60000L) {
                                s.disconnect(5);
                            } else if (t - s.lastActiveTime > 600000L) {
                                s.disconnect(6);
                            }
                        }
                    } catch (Exception ignored) {
                    }
                }
                TeamServer.maxCCUAD = firmwareAnd;
                try {
                    CCUProvider ccu2 = Database.allProvider.get("0");
                    ccu2.ccu = ccu2.tempCCU;
                    ccu2.tempCCU = 0;
                    for (int k = 0; k < ccu2.ccuFirmWareTemp.length; ++k) {
                        ccu2.ccuFirmWare[k] = ccu2.ccuFirmWareTemp[k];
                        ccu2.ccuFirmWareTemp[k] = 0;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(30000L);
                } catch (InterruptedException ignored) {
                }
            }
        }).start();
        Map.loadImageCharWeaponeData();

        SelectCharHandler.loadImgSkill();
        OtherHandle.loadImgHorse();


        RealController.add_more_idMapSend2Client();

        try {
            InetSocketAddress address = new InetSocketAddress(ipLogin, PORT);
            TeamServer.listenSocket = new ServerSocket();
            TeamServer.listenSocket.bind(address);
            System.out.println("Listen on port " + TeamServer.PORT);
            while (TeamServer.running) {
                try {
                    Socket clientSocket = TeamServer.listenSocket.accept();
                    String ipconnect = "";
                    System.out.println("Client connect : " + clientSocket.getInetAddress().getHostAddress());
                    try {
                        InetAddress inet = clientSocket.getInetAddress();
                        ipconnect = inet.getHostAddress();
                        if (TeamServer.ALL_IPCONNECT_BLOCK.get(ipconnect) != null) {
                            System.out.println("IP BLOCK " + ipconnect);
                            clientSocket.close();
                            continue;
                        }
                        InfoClientConnect info = TeamServer.ALL_IPCONNECT.get(ipconnect);
                        if (info == null) {
                            info = new InfoClientConnect();
                            info.ip = ipconnect;
                            info.countConnect();
                            TeamServer.ALL_IPCONNECT.put(ipconnect, info);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    if (AdminHandler.isStopServer) {
                        clientSocket.close();
                    } else {
                        Session conn = new Session(clientSocket, TeamServer.realController);
                        conn.ip = ipconnect;
                        conn.start();
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        } catch (BindException bindEx) {
            RealController.intance.saveAllChar(false);
            System.exit(0);
            System.out.println("SERVER EXIT");
        } catch (Exception genEx) {
            genEx.printStackTrace();
        }
        System.out.println("Server shuting down");
        System.out.println("Server stop");
    }

    private static boolean isEnabled(String value) {
        return value != null && ("1".equals(value.trim()) || "true".equalsIgnoreCase(value.trim()) || "yes".equalsIgnoreCase(value.trim()));
    }

    private static void startAdminPanelIfEnabled() {
        if (!TeamServer.enableAdminPanel) {
            System.out.println("AdminPanel disabled. Set sv.adminpanel=1 in server.ini to enable it.");
            return;
        }
        SwingUtilities.invokeLater(() -> {
            AdminPanel panel = new AdminPanel();
            panel.setVisible(true);
        });
    }

    public static Vector<String> readData(String st, String filename) {
        Vector<String> test = new Vector<>();
        try {
            FileInputStream fis = new FileInputStream(st);
            DataInputStream is = new DataInputStream(fis);
            InputStreamReader isr = null;
            try {
                isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
            TeamServer.stHelp = "";
            int count = 0;
            try {
                int ch;
                while ((ch = isr.read()) > -1) {
                    char c = (char) ch;
                    if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
                        TeamServer.stHelp = String.valueOf(TeamServer.stHelp) + (char) ch;
                    } else {
                        if (TeamServer.stHelp.trim().length() <= 0) {
                            continue;
                        }
                        test.add(TeamServer.stHelp);
                        TeamServer.stHelp = "";
                        ++count;
                    }
                }
                System.out.println("SO LUONG CHAR " + filename + " la >> " + count);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            try {
                isr.close();
            } catch (Exception ignored) {
            }
            try {
                is.close();
            } catch (Exception ignored) {
            }
            try {
                fis.close();
            } catch (Exception ignored) {
            }
        } catch (Exception ignored) {
        }
        return test;
    }

    private static String readText(String st) {
        final String test = "";
        try {
            FileInputStream fis = new FileInputStream(st);
            DataInputStream is = new DataInputStream(fis);
            InputStreamReader isr = null;
            try {
                isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
            TeamServer.stHelp = "";
            try {
                int ch;
                while ((ch = isr.read()) > -1) {
                    TeamServer.stHelp = String.valueOf(TeamServer.stHelp) + (char) ch;
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            try {
                isr.close();
            } catch (Exception ignored) {
            }
            try {
                is.close();
            } catch (Exception ignored) {
            }
            try {
                fis.close();
            } catch (Exception ignored) {
            }
        } catch (Exception ignored) {
        }
        return test;
    }

    public static String getNameServer() {
        if (TeamServer.server == 5) {
            return "KM";
        }
        if (TeamServer.server == 3) {
            return "MS";
        }
        if (TeamServer.server == 2) {
            return "TA";
        }
        return "KM";
    }

}
