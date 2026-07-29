/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.AdvInfo
 *  data.Database
 *  real.AdminHandler
 *  real.Char
 *  real.Map
 *  server.TeamServer
 */
package data;

import data.AdvInfo;
import data.CharInfo;
import data.Database;
import data.Net;
import java.util.Hashtable;
import java.util.Vector;
import real.AdminHandler;
import real.Char;
import real.Map;
import server.TeamServer;

public class Logdata {
    public static Vector<String> query = new Vector();
    public static Hashtable<String, Integer> topRich = new Hashtable();
    public static Hashtable<String, Integer> topLv = new Hashtable();
    public static Vector<CharInfo> charTopRich = new Vector();
    public static Vector<CharInfo> charTopLv = new Vector();
    public static byte idAdv = 0;
    public static byte idAdv2 = 0;

    public static void addChar(Char p, int type) {
        CharInfo cc = Map.createCharInfo((Char)p);
        cc.money = p.getxu();
        cc.luong = p.getLuong();
        charTopRich.add(cc);
        charTopLv.add(cc);
    }

    public static void checkAddCharTop(CharInfo cc, int typetop) {
        try {
            if (typetop == 0) {
                CharInfo me = null;
                int pos = -1;
                int i = 0;
                while (i < Map.topRich.size()) {
                    CharInfo c = (CharInfo)Map.topRich.get(i);
                    if (c.name.equals(cc.name)) {
                        me = c;
                    } else if (c.money < cc.money && pos == -1) {
                        pos = i;
                    }
                    ++i;
                }
                if (me != null) {
                    if (pos == -1) {
                        me.money = cc.money;
                        me.luong = cc.luong;
                        me.wearingItem = cc.wearingItem;
                    } else {
                        Map.topRich.remove(me);
                    }
                }
                if (pos != -1) {
                    cc.money = cc.money;
                    cc.luong = cc.luong;
                    if (me == null) {
                        Map.topRich.remove(Map.topRich.size() - 1);
                    }
                    Map.topRich.insertElementAt(cc, pos);
                }
            } else if (typetop == 1) {
                CharInfo me = null;
                int pos = -1;
                int i = 0;
                while (i < Map.topLv.size()) {
                    CharInfo c = (CharInfo)Map.topLv.get(i);
                    if (c.name.equals(cc.name)) {
                        me = c;
                    } else if (c.money < cc.money && pos == -1) {
                        pos = i;
                    }
                    ++i;
                }
                if (me != null) {
                    if (pos == -1) {
                        me.money = cc.money;
                        me.luong = cc.luong;
                        me.wearingItem = cc.wearingItem;
                    } else {
                        Map.topLv.remove(me);
                    }
                }
                if (pos != -1) {
                    cc.money = cc.money;
                    cc.luong = cc.luong;
                    if (me == null) {
                        Map.topLv.remove(Map.topLv.size() - 1);
                    }
                    Map.topLv.insertElementAt(cc, pos);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public Logdata() {
        new Thread(){

            @Override
            public void run() {
                Thread.currentThread().setName("LOG DATA");
                while (TeamServer.running && !AdminHandler.isStopServer) {
                    Vector data = Database.cloneVectorString(query);
                    query.removeAllElements();
                    if (data.size() > 0) {
                        Database.instance.saveAllLog(data);
                    }
                    if (System.currentTimeMillis() - Map.timeGettop > 900000L && !Map.getTop) {
                        Map.getTop = true;
                        Map.timeGettop = System.currentTimeMillis();
                        if (topRich.size() == 0) {
                            Map.topRich = Database.instance.getTopRich();
                        }
                        if (topLv.size() == 0) {
                            Map.topLv = Database.instance.getTopLV();
                        }
                        Map.topClan = Database.instance.getTopClan();
                        int i = 0;
                        while (i < 3) {
                            Map.topLvNation.set(i, Database.instance.getTopCountry(i, 0));
                            Map.topHonor.set(i, Database.instance.getTopCountry(i, 1));
                            ++i;
                        }
                        Map.getTop = false;
                    }
                    if (topRich.size() > 0) {
                        while (charTopLv.size() > 0) {
                            CharInfo cc = charTopLv.remove(0);
                            Logdata.checkAddCharTop(cc, 1);
                        }
                        while (charTopRich.size() > 0) {
                            CharInfo cc = charTopRich.remove(0);
                            Logdata.checkAddCharTop(cc, 0);
                        }
                    }
                    try {
                        Thread.sleep(100L);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        if (Map.openLog) {
            new Thread(){

                @Override
                public void run() {
                    try {
                        while (!AdminHandler.isStopServer) {
                            try {
                                String info = Net.getHttpADV("http://27.0.14.67/adsinkpah.txt");
                                System.out.println(info);
                                String[] dataAdv = Char.split((String)info, (String)"\n");
                                Char.infoAdv.removeAllElements();
                                int i = 0;
                                while (i < dataAdv.length) {
                                    String[] data = Char.split((String)dataAdv[i], (String)";");
                                    int idGame = Integer.parseInt(Map.getNumberFromString((String)data[0]).trim());
                                    if (idGame != 9) {
                                        AdvInfo av = new AdvInfo();
                                        av.linkDown = String.valueOf(data[1]) + "&from=kpah";
                                        av.infoDownload = "B\u1ea1n th\u1eadt s\u1ef1 mu\u1ed1n t\u1ea3i game " + data[2] + " ?";
                                        int j = 3;
                                        while (j < data.length) {
                                            av.info.add(String.valueOf(data[2]) + ":" + data[j]);
                                            ++j;
                                        }
                                        Char.infoAdv.add(av);
                                    }
                                    ++i;
                                }
                                if (Char.infoAdv.size() > 0) {
                                    if (Char.infoAdv.size() == 1) {
                                        idAdv = 0;
                                    } else {
                                        int id = Map.r.nextInt(Char.infoAdv.size());
                                        while (id == idAdv) {
                                            id = Map.r.nextInt(Char.infoAdv.size());
                                        }
                                        idAdv = (byte)id;
                                    }
                                }
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                            Thread.sleep(900000L);
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }.start();
            new Thread(){

                @Override
                public void run() {
                    try {
                        while (!AdminHandler.isStopServer) {
                            try {
                                String info = Net.getHttpADV("http://27.0.14.67/ads/adsinkpah.txt");
                                String[] dataAdv = Char.split((String)info, (String)"\n");
                                Char.infoAdv2.removeAllElements();
                                int i = 0;
                                while (i < dataAdv.length) {
                                    String[] data = Char.split((String)dataAdv[i], (String)";");
                                    int idGame = Integer.parseInt(Map.getNumberFromString((String)data[0]).trim());
                                    if (idGame != 9) {
                                        AdvInfo av = new AdvInfo();
                                        av.linkDown = String.valueOf(data[1]) + "&from=kpah";
                                        av.infoDownload = "B\u1ea1n th\u1eadt s\u1ef1 mu\u1ed1n t\u1ea3i game " + data[2] + " ?";
                                        int j = 3;
                                        while (j < data.length) {
                                            av.info.add(String.valueOf(data[2]) + ":" + data[j]);
                                            ++j;
                                        }
                                        Char.infoAdv2.add(av);
                                    }
                                    ++i;
                                }
                                if (Char.infoAdv2.size() > 0) {
                                    if (Char.infoAdv2.size() == 1) {
                                        idAdv2 = 0;
                                    } else {
                                        int id = Map.r.nextInt(Char.infoAdv2.size());
                                        while (id == idAdv2) {
                                            id = Map.r.nextInt(Char.infoAdv2.size());
                                        }
                                        idAdv2 = (byte)id;
                                    }
                                }
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                            Thread.sleep(900000L);
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }.start();
        }
    }
}

