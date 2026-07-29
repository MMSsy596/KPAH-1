package data;

import io.Message;
import io.Session;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;
import real.Char;
import real.Map;
import real.MessageCreator;
import real.cmd.ICommandHandler;

public class OtherHandle implements ICommandHandler {

    public static Calendar cl = Calendar.getInstance();
    public static int year;
    static byte MAX_ANIMAL;
    public static byte[][][] headerHorse;
    public static byte[][] dataHorse;

    static {
        year = cl.get(1);
        MAX_ANIMAL = 19;
    }

    public void process(Session session, Message message) throws IOException {
        switch (message.command) {
            case -40:
                this.sendKey(session);
                break;
            case -32:
                int idActor = message.dis.readShort();
                int idpopup = message.dis.readByte();
                String text = "";
                text = message.dis.readUTF();
                byte ok = message.dis.readByte();
                if (ok == 1) {
                    Message m = new Message(39);
                    m.dos.writeUTF(session.unameReg);
                    m.dos.writeBoolean(session.available);
                    m.dos.writeUTF(session.syntax);
                    m.dos.writeUTF(session.centerReg);
                    session.sendMessage(m);
                }
                break;
            case -31:
                int idActor1 = message.dis.readShort();
                int idMenu1 = message.dis.readByte();
                String text1 = message.dis.readUTF().trim().toLowerCase();
                String infoalert = "ÄÄƒng kÃ½ thÃ nh cÃ´ng. DÆ°á»›i 18 tuá»•i chá»‰ Ä‘Æ°á»£c chÆ¡i 3h má»—i ngÃ y";
                if (idActor1 == 0) {
                    if (text1.equals("")) {
                        infoalert = "Há» vÃ  tÃªn khÃ´ng há»£p lá»‡";
                        session.sendMessage(MessageCreator.createServerAlertMessage(infoalert, ""));
                        return;
                    }

                    session.hoten = text1;
                    session.sendMessage(MessageCreator.createMsgInputText(1, 0, "NgÃ y sinh(dd mm yyyy)", 0));
                } else if (idActor1 == 1) {
                    if (text1.equals("")) {
                        infoalert = "NgÃ y sinh khÃ´ng há»£p lá»‡";
                        session.sendMessage(MessageCreator.createServerAlertMessage(infoalert, ""));
                        return;
                    }

                    boolean isDuoi12 = false;

                    try {
                        String[] data = Char.split(text1, " ");
                        Integer.parseInt(data[0]);
                        Integer.parseInt(data[1]);
                        if (Integer.parseInt(data[2].trim()) > year - 12) {
                            isDuoi12 = true;
                        }
                    } catch (Exception var12) {
                        infoalert = "NgÃ y sinh khÃ´ng há»£p lá»‡";
                        session.sendMessage(MessageCreator.createServerAlertMessage(infoalert, ""));
                        return;
                    }

                    if (isDuoi12) {
                        infoalert = "Game chá»‰ dÃ nh cho ngÆ°á»i chÆ¡i tá»« 12 tuá»•i trá»Ÿ lÃªn";
                        session.sendMessage(MessageCreator.createServerAlertMessage(infoalert, ""));
                        return;
                    }

                    session.ngaysinh = text1;
                    session.sendMessage(MessageCreator.createMsgInputText(2, 0, "Äá»‹a chá»‰", 0));
                } else if (idActor1 == 2) {
                    if (text1.equals("")) {
                        infoalert = "Äá»‹a chá»‰ khÃ´ng há»£p lá»‡";
                        session.sendMessage(MessageCreator.createServerAlertMessage(infoalert, ""));
                        return;
                    }

                    session.diachi = text1;
                    session.sendMessage(MessageCreator.createMsgInputText(3, 0, "Cmnd", 1));
                } else if (idActor1 == 3) {
                    if (text1.equals("")) {
                        infoalert = "Cmnd khÃ´ng há»£p lá»‡";
                        session.sendMessage(MessageCreator.createServerAlertMessage(infoalert, ""));
                        return;
                    }

                    session.cmnd = text1;
                    session.sendMessage(MessageCreator.createMsgInputText(4, 0, "NgÃ y cáº¥p", 0));
                } else if (idActor1 == 4) {
                    if (text1.equals("")) {
                        infoalert = "NgÃ y cáº¥p khÃ´ng há»£p lá»‡";
                        session.sendMessage(MessageCreator.createServerAlertMessage(infoalert, ""));
                        return;
                    }

                    session.ngaycap = text1;
                    session.sendMessage(MessageCreator.createMsgInputText(5, 0, "NÆ¡i cáº¥p", 0));
                } else if (idActor1 == 5) {
                    if (text1.equals("")) {
                        infoalert = "nÆ¡i cáº¥p khÃ´ng há»£p lá»‡";
                        session.sendMessage(MessageCreator.createServerAlertMessage(infoalert, ""));
                        return;
                    }

                    session.noicap = text1;
                    String info = session.hoten + ":" + session.diachi + ":" + session.cmnd + ":" + session.ngaycap + ":" + session.noicap;
                    String registeredUsername = session.usernameReg;
                    if (registeredUsername == null || registeredUsername.trim().equals("")) {
                        registeredUsername = session.unameReg;
                    }
                    int localUserId = Database.instance.getLocalTeamUserId(registeredUsername);
                    Database.instance.addInfoAcount(registeredUsername, info, session.ngaysinh, localUserId);
                    session.sendMessage(MessageCreator.createServerAlertMessage("Da luu thong tin dang ky cho tai khoan " + registeredUsername + ".", ""));
                }
                break;
            case -27:
                this.doSendWeaponeData(session, message);
                break;
            case -8:
                this.doSendIconPotion(session, message);
        }

    }

    public void doRegister() {
    }

    private void sendKey(Session s) {
        try {
            byte[] key = new byte[1];
            Random rnd = new Random();
            rnd.nextBytes(key);
            Message m = new Message(-40);
            m.dos.writeByte(key.length);
            m.dos.write(key);
            s.sendMessage(m);

            for (int i = 0; i < key.length - 1; ++i) {
                key[i + 1] ^= key[i];
            }

            s.setKey(key);
        } catch (Exception var6) {
        }

    }

    public static void loadImgHorse() {
        FileInputStream fis = null;
        DataInputStream dis = null;

        try {
            String[] urlHeader = new String[]{
                "map/horse/ngua_data",
                "map/trau/ngua_data",
                "map/cop/ngua_data",
                "map/soi/ngua_data",
                "map/hac/ngua_data",
                "map/ph/phlua/phuonghoang_data",
                "map/hactho/ngua_data",
                "map/tuanloc/tuanloc_data",
                "map/ph/phbang/phuonghoang_data",
                
                "map/ph/phlua7mau/phuonghoang_data", // 12
                "map/ph/phlua7mau/phuonghoang_data", // 13
                "map/ph/phlua7mau/phuonghoang_data",
                "map/ph/phlua7mau/phuonghoang_data",
                "map/ph/phlua7mau/phuonghoang_data",
                "map/ph/phlua7mau/phuonghoang_data",
                "map/ph/phmoc/phuonghoang_data", // 18
                "map/ph/phvang/phuonghoang_data",
                "map/ph/phtho/phuonghoang_data",
                "map/ph/phtho/phuonghoang_data",// Ä‘Æ°Æ¡ng khang bÄƒng
            };

            dataHorse = new byte[MAX_ANIMAL][];
            headerHorse = new byte[MAX_ANIMAL][][];

            for (int i = 0; i < MAX_ANIMAL; ++i) {
                fis = new FileInputStream(urlHeader[i]);
                dis = new DataInputStream(fis);

                dataHorse[i] = new byte[dis.available()];
                dis.read(dataHorse[i], 0, dataHorse[i].length);

                int[] nPalate = new int[]{4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1,1,1,1,1};

                headerHorse[i] = new byte[nPalate[i]][];

                try {
                    dis.close();
                } catch (Exception var29) {
                }

                try {
                    fis.close();
                } catch (Exception var28) {
                }

                String[] urlData = new String[]{
                    "map/horse/ngua", // cÃ²n nÃ y cÃ³ 3 ngá»±a
                    "map/trau/ngua",
                    "map/cop/ngua",
                    "map/soi/ngua",
                    "map/hac/ngua",
                    "map/ph/phlua/phuonghoang",
                    "map/hactho/ngua",
                    "map/tuanloc/tuanloc",
                    "map/ph/phbang/phuonghoang",
                    "map/ph/phlua7mau/phuonghoang",
                    "map/ph/phlua7mau/phuonghoang",
                    "map/ph/phlua7mau/phuonghoang",
                    "map/ph/phlua7mau/phuonghoang",
                    "map/ph/phlua7mau/phuonghoang",
                    "map/ph/phlua7mau/phuonghoang",
                    "map/ph/phmoc/phuonghoang", // 18
                    "map/ph/phvang/phuonghoang",
                    "map/ph/phtho/phuonghoang",
                    "map/ph/phtho/phuonghoang",//lá»£n bÄƒng
                };

                for (int j = 0; j < headerHorse[i].length; ++j) {
                    try {
                        fis = new FileInputStream(urlData[i] + j + "_header");
                        dis = new DataInputStream(fis);
                        headerHorse[i][j] = new byte[dis.available()];
                        dis.read(headerHorse[i][j], 0, headerHorse[i][j].length);

                        try {
                            dis.close();
                        } catch (Exception var26) {
                        }

                        try {
                            fis.close();
                        } catch (Exception var25) {
                        }
                    } catch (Exception var27) {
                        var27.printStackTrace();
                    }
                }
            }
        } catch (Exception var30) {
            var30.printStackTrace();
            System.out.println("LOI LOAD HINH HORSE");
        } finally {
            try {
                dis.close();
            } catch (Exception var24) {
            }

            try {
                fis.close();
            } catch (Exception var23) {
            }

        }

    }

    private void doSendIconPotion(Session s, Message message) {
        try {
            int type = message.dis.readByte();
            int hascode = message.dis.readByte();
            if (type == 2) {
                if (Map.versionPotion != hascode) {
                    Message msg = new Message(-8);
                    msg.dos.writeByte(type);
                    msg.dos.writeByte(Map.versionPotion);
                    msg.dos.writeShort(Map.imgPotion.length);
                    msg.dos.write(Map.imgPotion);
                    msg.dos.writeShort(Map.imgItemIcon.length);
                    msg.dos.write(Map.imgItemIcon);
                    msg.dos.writeByte(dataHorse.length);

                    for (int i = 0; i < dataHorse.length; ++i) {

//                        System.out.println("wwrite dataa horse index: " + i);

                        msg.dos.writeShort(dataHorse[i].length);
                        msg.dos.write(dataHorse[i]);

                        msg.dos.writeByte(headerHorse[i].length);
                        for (int j = 0; j < headerHorse[i].length; ++j) {
                            msg.dos.writeShort(headerHorse[i][j].length);
                            msg.dos.write(headerHorse[i][j]);
                        }
                    }

                    s.sendMessage(msg);
                }
            } else if (type == 3) {
                int var10000 = Map.versionGem;
            }
        } catch (Exception var8) {

            var8.printStackTrace();
        }

    }

    private void doSendWeaponeData(Session session, Message message) {
        try {
            int type = message.dis.readByte();
            int typeItem = message.dis.readByte();
            int style = message.dis.readByte();
            int color = message.dis.readByte();
            session.sendMessage(MessageCreator.createCharWeaponeData(type, typeItem, style, color));
        } catch (Exception var7) {
        }

    }
}
