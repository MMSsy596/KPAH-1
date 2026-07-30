/*
 * Decompiled with CFR 0.152.
 */
package data;

import data.ItemInfo;
import java.util.Vector;

public class CharInfo {
    public String name = "";
    public byte level = 0;
    public byte headStyle = 0;
    public byte titlesClan = (byte)3;
    public byte country = (byte)-1;
    public Vector<ItemInfo> wearingItem = new Vector();
    public int luong = 0;
    public int idClan;
    public int idDB;
    public int lastXu;
    public int lastLuong;
    public int userid;
    public int honor;
    public int pk;
    public int nKillInArena;
    public int bekill;
    public long money = 0L;
    public long exp = 0L;
    public long timeNuiChaubau = 0L;
    public boolean isLockGift = false;
    public byte fail = 0;

    public long getTimeNuiChauBau() {
        long time;
        if (this.timeNuiChaubau > 0L && (time = (this.timeNuiChaubau - System.currentTimeMillis()) / 1000L) > 0L) {
            return time;
        }
        return 0L;
    }
}

