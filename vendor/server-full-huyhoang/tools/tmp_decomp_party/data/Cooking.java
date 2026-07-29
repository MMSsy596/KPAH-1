/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  io.Message
 *  real.Char
 *  real.Map
 *  real.MessageCreator
 */
package data;

import data.CharCooking;
import data.Database;
import io.Message;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;
import real.Char;
import real.Map;
import real.MessageCreator;

public class Cooking {
    public byte type = 0;
    public long timeStart = 0L;
    public byte timeCook = (byte)60;
    public byte timeChamnc = 0;
    public byte water = 0;
    public Hashtable<String, CharCooking> listCharCooking = new Hashtable();
    public Hashtable<String, CharCooking> listCharGift = new Hashtable();
    public Vector<CharCooking> vCharCook = new Vector();
    public Vector<CharCooking> vCharGift = new Vector();
    boolean isStart = false;
    public boolean isAdminEnd = false;
    public boolean isAdminStart = false;
    public static byte timeNauBanh = (byte)18;
    public static String[] namenoibanh = new String[]{"", "N\u1ed3i b\u00e1nh ch\u01b0ng", "N\u1ed1i b\u00e1nh t\u00e9t"};

    public synchronized boolean ChamNuoc() {
        if (Char.getDayOpen((long)0L).equals("2017-01-24")) {
            return false;
        }
        if (this.isChamNc()) {
            this.timeChamnc = (byte)(this.timeChamnc + 1);
            return true;
        }
        return false;
    }

    public boolean doGopNguyenLieu(Char p, int nbanh) {
        CharCooking c = this.listCharCooking.get(p.charname);
        if (c == null) {
            return false;
        }
        c.gop += nbanh;
        return true;
    }

    public void setListCharCooking(String info) {
        if (info == null || info.equals("")) {
            return;
        }
        try {
            String[] data = Char.split((String)info, (String)",");
            int i = 0;
            while (i < data.length) {
                String[] data1 = Char.split((String)data[i], (String)":");
                CharCooking c = new CharCooking();
                c.name = data1[0].toLowerCase();
                c.gop = Integer.parseInt(data1[1]);
                this.listCharCooking.put(c.name, c);
                this.vCharCook.add(c);
                ++i;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void setListCharGift(String info) {
        if (info == null || info.equals("")) {
            return;
        }
        try {
            String[] data = Char.split((String)info, (String)",");
            int i = 0;
            while (i < data.length) {
                String[] data1 = Char.split((String)data[i], (String)":");
                CharCooking c = new CharCooking();
                c.name = data1[0].toLowerCase();
                c.nbanh = Integer.parseInt(data1[1]);
                this.listCharGift.put(c.name, c);
                this.vCharGift.add(c);
                ++i;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void startCook() {
        if (Char.getDayOpen((long)0L).equals("2017-01-24")) {
            return;
        }
        Calendar cl = Calendar.getInstance();
        int ihour = cl.get(11);
        int minute = cl.get(12);
        if ((this.isAdminStart || ihour == timeNauBanh) && !this.isStart) {
            this.isStart = true;
            this.timeChamnc = 0;
            this.timeCook = (byte)60;
            this.timeStart = System.currentTimeMillis();
            this.water = 0;
            try {
                Map.sendAllCharServer((int)-1, (Message)MessageCreator.createServerAlertAutoOffMessage((String)("B\u1eaft \u0111\u1ea7u n\u1ea5u b\u00e1nh " + (this.type == 1 ? "b\u00e1nh ch\u01b0ng" : "b\u00e1nh t\u00e9t"))));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public void isEnd() {
        Calendar cl = Calendar.getInstance();
        int ihour = cl.get(11);
        int minute = cl.get(12);
        if (this.isStart && (ihour > timeNauBanh || this.isAdminEnd)) {
            this.isStart = false;
            this.isAdminEnd = false;
            this.timeStart = 0L;
            Cooking.quickSort(this.vCharCook, 0);
            System.out.println("KET THUC NAU BANH: " + this.vCharCook.size());
            int i = 0;
            while (i < this.vCharCook.size()) {
                CharCooking c = this.vCharCook.get(i);
                CharCooking cg = this.listCharGift.get(c.name);
                if (cg == null) {
                    cg = new CharCooking();
                    cg.name = c.name;
                    this.listCharGift.put(c.name, cg);
                    this.vCharGift.add(cg);
                }
                int nbanh = c.gop;
                c.gop = 0;
                if (nbanh > 30) {
                    nbanh = 30;
                }
                if (this.water == 1) {
                    nbanh /= 2;
                } else if (this.water == 2) {
                    nbanh = 0;
                }
                cg.nbanh += nbanh;
                if (i < 10) {
                    if (this.water == 0) {
                        cg.nbanh += 30;
                    } else if (this.water == 0) {
                        cg.nbanh += 15;
                    }
                }
                ++i;
            }
            this.vCharCook.removeAllElements();
            this.listCharCooking.clear();
            Database.instance.saveCooking(this, (int)this.type);
            Database.instance.saveOrtherLog("", namenoibanh[this.type], "so luong banh khi ket thuc: " + this.water, "ecook");
            try {
                Map.sendAllCharServer((int)-1, (Message)MessageCreator.createServerAlertAutoOffMessage((String)"\u0110\u00e3 k\u1ebft th\u00fac n\u1ea5u b\u00e1nh"));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public String getListChar() {
        String info = "";
        int i = 0;
        while (i < this.vCharCook.size()) {
            if (!info.equals("")) {
                info = String.valueOf(info) + ",";
            }
            CharCooking c = this.vCharCook.get(i);
            info = String.valueOf(info) + c.name + ":" + c.gop;
            ++i;
        }
        return info;
    }

    public String getListCharGift() {
        String info = "";
        int i = 0;
        while (i < this.vCharGift.size()) {
            if (!info.equals("")) {
                info = String.valueOf(info) + ",";
            }
            CharCooking c = this.vCharGift.get(i);
            info = String.valueOf(info) + c.name + ":" + c.nbanh;
            ++i;
        }
        return info;
    }

    public CharCooking addCharCook(Char p) {
        CharCooking c = this.listCharCooking.get(p.charname.toLowerCase());
        if (c == null) {
            c = new CharCooking();
            c.name = p.charname;
            c.gop = 0;
            this.listCharCooking.put(p.charname, c);
            this.vCharCook.add(c);
        }
        return c;
    }

    public String getNameOption() {
        Calendar cl = Calendar.getInstance();
        int ihour = cl.get(11);
        if (this.isChamNc() || ihour == timeNauBanh) {
            return "Ch\u00e2m n\u01b0\u1edbc";
        }
        return this.type == 1 ? "G\u00f3p b\u00e1nh ch\u01b0ng" : "G\u00f3p b\u00e1nh t\u00e9t";
    }

    public synchronized boolean isChamNc() {
        Calendar cl = Calendar.getInstance();
        int ihour = cl.get(11);
        if (ihour != timeNauBanh) {
            return false;
        }
        if (this.timeChamnc == 0 && this.timeCook <= 40) {
            return true;
        }
        return this.timeChamnc == 1 && this.timeCook <= 20;
    }

    public int getBanhNhan(CharCooking c) {
        if (this.water == 0) {
            return c.nbanh;
        }
        if (this.water == 1) {
            if (c.nbanh == 1) {
                return 1;
            }
            return c.nbanh / 2;
        }
        return 0;
    }

    public void update() {
        long t = System.currentTimeMillis();
        if (this.timeStart > 0L && this.timeCook > 0) {
            if (t - this.timeStart >= 60000L) {
                this.timeStart = System.currentTimeMillis();
                this.timeCook = (byte)(this.timeCook - 1);
                if (this.timeCook == 20) {
                    if (this.timeChamnc == 0) {
                        this.water = 1;
                    }
                } else if (this.timeCook == 0) {
                    if (this.timeChamnc == 0) {
                        this.water = (byte)2;
                    } else if (this.timeChamnc == 1) {
                        this.water = 1;
                    }
                }
                try {
                    if (this.isChamNc()) {
                        Map.sendAllCharServer((int)-1, (Message)MessageCreator.createServerAlertAutoOffMessage((String)(String.valueOf(namenoibanh[this.type]) + " ch\u01b0a \u0111\u01b0\u1ee3c ch\u00e2m n\u01b0\u1edbc")));
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            this.isEnd();
        } else {
            this.startCook();
        }
    }

    public void doGetBanh(Char p) {
        if (this.water == 2) {
            return;
        }
        CharCooking c = this.listCharCooking.get(p.charname);
    }

    public static void quickSort(Vector<CharCooking> actors, int type) {
        Cooking.recQuickSort(actors, 0, actors.size() - 1, type);
    }

    private static void recQuickSort(Vector<CharCooking> actors, int left, int right, int type) {
        try {
            if (right - left <= 0) {
                return;
            }
            long pivot = actors.elementAt((int)right).gop;
            int partition = Cooking.partitionIt(actors, left, right, pivot, type);
            Cooking.recQuickSort(actors, left, partition - 1, type);
            Cooking.recQuickSort(actors, partition + 1, right, type);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private static int partitionIt(Vector<CharCooking> actors, int left, int right, long pivot, int type) {
        int leftPtr = left - 1;
        int rightPtr = right;
        try {
            while (true) {
                if ((long)actors.elementAt((int)(++leftPtr)).gop > pivot) {
                    continue;
                }
                while (rightPtr > 0 && (long)actors.elementAt((int)(--rightPtr)).gop < pivot) {
                }
                if (leftPtr >= rightPtr) break;
                Cooking.swap(actors, leftPtr, rightPtr, type);
            }
            Cooking.swap(actors, leftPtr, right, type);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return leftPtr;
    }

    private static void swap(Vector<CharCooking> actors, int dex1, int dex2, int type) {
        CharCooking temp = actors.elementAt(dex2);
        if (actors.elementAt((int)dex2).gop != actors.elementAt((int)dex1).gop) {
            actors.setElementAt(actors.elementAt(dex1), dex2);
            actors.setElementAt(temp, dex1);
        }
    }
}

