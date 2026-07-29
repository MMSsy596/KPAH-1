package data;

import io.Message;
import real.*;
import server.TeamServer;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NapTien {

    public NapTien() {
        // TODO tắt thread nạp tiền
    //    this.runNapTienThread();
    }

    private void runNapTienThread() {
        new Thread(() -> {
            System.err.println("Naptien Thread run on port " + TeamServer.portNapTien);
            Thread.currentThread().setName("Naptien");
            int retryCount = 0;
            int maxRetries = 3;
            
            while (retryCount < maxRetries) {
                try (ServerSocket sock = new ServerSocket(TeamServer.portNapTien)) {
                    // Reset retry count on successful binding
                    retryCount = 0;
                    
                    while (TeamServer.running) {
                        Socket clientSocket;
                        try {
                            clientSocket = sock.accept();
                            try (
                                    BufferedInputStream buffer = new BufferedInputStream(clientSocket.getInputStream()); DataInputStream input = new DataInputStream(buffer)) {
                                String str = input.readUTF();
                                Pattern pattern = Pattern.compile("^(\\w+) (\\w+)? ?(\\w+)? ?(.*)");
                                Matcher matcher = pattern.matcher(str);
                                if (!matcher.matches() || !matcher.group(1).equals("admin5hquoc")) {
                                    return;
                                }
                                String command = "";
                                String param = "";
                                String param2 = "";
                                try {
                                    command = matcher.group(2);
                                } catch (Exception ignored) {

                                }
                                try {
                                    param = matcher.group(3);
                                } catch (Exception ignored) {

                                }
                                try {
                                    param2 = matcher.group(4);
                                } catch (Exception ignored) {

                                }
                                System.err.println("Nhan duoc lenh tu web: " + str);
                                switch (command) {
                                    case "!": {
                                        Char p = CharManager.instance.getCharByCharName(param);
                                        if (p != null) {
                                            p.sendMessage(MessageCreator.createServerAlertMessage("Tài khoản của bạn tạm bị khóa trong giây lát để admin giải quyết. Xin đăng nhập sau ít phút nữa", ""));
                                            p.getSession().disconnect(8);
                                        }
                                        break;
                                    }
                                    case "*": {
                                        Database.instance.loadBadWords();
                                        break;
                                    }
                                    case "shutdown": {
                                       
                                    ((AdminHandler) RealController.getHandler()).adminRequest(command, true);
                                        break;
                                    }
                                    case "setinfo": {
                                        Database.instance.getInfoAdmin();
                                        break;
                                    } case "checkgift": {
                                        Char p = CharManager.instance.getCharByCharName(param);
                                        if (p != null) {
                                            p.doAddGiftEventOffline();
                                        }
                                        break;
                                    }
                                    case "checknap": {
                                        Char p = CharManager.instance.getCharByCharName(param);
                                        if (p != null) {
                                            MoneyInfo dMoney = NapTien.this.checkNapTien(p.getName());
                                            final String xu = "xu";
                                            if (dMoney.xu > 0) {
                                                p.addXu(dMoney.xu, "runNapTienThread naptien");
                                                String text = "Bạn vừa nạp " + dMoney.xu + " " + xu + " cho nhân vật " + param;
                                                if (TeamServer.isServerIndo()) {
                                                    text = "You topped up " + dMoney.xu + " coins into " + param;
                                                }
                                                Message m = new Message(27);
                                                m.dos.writeShort(p.id);
                                                m.dos.writeUTF(text);
                                                p.sendMessage(m);
                                                if (dMoney.xu >= 10) {
                                                    Database.instance.saveLogThongKeChiTieu("nap xu", 0, 1, dMoney.luong, "xu nap");
                                                }
                                            }
                                            if (dMoney.luong > 0) {
                                                p.luongNap += dMoney.luong / 2;

                                                //  Database.instance.updateLuongNap(dMoney.luong, p);
                                                p.diemNapVip += dMoney.luong;
                                                Database.instance.addCharXaiNapLuong(p, 0);
                                                p.addLuong(dMoney.luong);
                                                if (true) { // Khuyến mãi lượng khoá.
                                                    int lk = dMoney.luong / 2;
                                                    if (lk <= 0) {
                                                        lk = 1;
                                                    }
                                                    p.lockLuong += lk;
                                                    Database.instance.saveOrtherLog("", p.getName(), "GET LUONG " + lk + " từ " + dMoney.luong, "nhanlk");
                                                }
                                                String text = "Bạn vừa nạp " + dMoney.luong + " lượng cho nhân vật " + param;
                                                if (TeamServer.isServerIndo()) {
                                                    text = "You topped up " + dMoney.luong + " gold into " + param;
                                                }
                                                Message m = new Message(27);
                                                m.dos.writeShort(p.id);
                                                m.dos.writeUTF(text);
                                                p.sendMessage(m);
                                                if (dMoney.luong >= 10) {
                                                    Database.instance.saveLogThongKeChiTieu("nap luong", 0, 1, dMoney.luong, "luong nap");
                                                }
                                            }
                                            p.vetangluong += dMoney.vetangluong;
                                            if (p.firstNapMoney == 0 && Char.isSuKienHalowwen2015() && Map.openLog) {
                                                String kq = Database.instance.getFirstNap(p);
                                                if (!kq.equals("")) {
                                                    p.sendMessage(MessageCreator.createServerAlertMessage(kq, ""));
                                                    p.firstNapMoney = 1;
                                                    Database.instance.saveOrtherLog("", p.charname, kq, "firstx2");
                                                }
                                            }
                                            Database.instance.updateCharPotion(p);
                                            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                                        }
                                        break;
                                    }
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (java.net.BindException e) {
                    retryCount++;
                    System.err.println("Port " + TeamServer.portNapTien + " is already in use. Retry attempt " + retryCount + "/" + maxRetries);
                    try {
                        // Wait before retrying
                        Thread.sleep(5000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                } catch (IOException e1) {
                    System.err.println("ERROR BIND naptienThread: " + e1.getMessage());
                    e1.printStackTrace();
                    break;
                }
            }
            
            if (retryCount >= maxRetries) {
                System.err.println("Failed to bind to port " + TeamServer.portNapTien + " after " + maxRetries + " attempts");
            }
        }).start();
    }

    public synchronized MoneyInfo checkNapTien(String userName) {
        Connection conn = null;
        Statement stmt = null;
        MoneyInfo money = new MoneyInfo();
        String info;
        try {
            conn = Database.connPoolNap.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM board_naptien WHERE username='" + userName + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                money.xu = rs.getInt("xu");
                money.luong = rs.getInt("luong");
                money.vetangluong = rs.getInt("ve");
                info = "Get xu: " + money.xu + " | Get luong: " + money.luong + " | get ve: " + money.vetangluong;
                try {
                    sql = "DELETE FROM `board_naptien` WHERE `id` ='" + rs.getInt("id") + "'";
                    stmt.execute(sql);
                    sql = "insert tob_get_board_nap set charname='" + userName + "',info='" + info + "',dateGet=now()";
                    stmt.execute(sql);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLException ex) {
            System.out.println("DBErr: checkNapTien()");
            ex.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPoolNap.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPoolNap.free(conn);
        } catch (Exception ignored) {
        }
        return money;
    }
}
