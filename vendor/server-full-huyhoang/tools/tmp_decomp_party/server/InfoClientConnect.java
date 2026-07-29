/*
 * Decompiled with CFR 0.152.
 */
package server;

import java.util.Hashtable;
import java.util.Vector;
import util.Logger;

public class InfoClientConnect {
    public String ip;
    public int count;
    public int count1;
    public int count2;
    public int maxConnectPerSecond = 0;
    public int maxLogin = 0;
    public int maxTaoChar = 0;
    public long timeConnect = System.currentTimeMillis();
    public long timeLogin = System.currentTimeMillis();
    public long timetaoChar = System.currentTimeMillis();
    public long timeSelectChar = System.currentTimeMillis();
    public long timeWriteLog = System.currentTimeMillis();
    public Vector<String> charname = new Vector();
    public Hashtable<String, String> hcharname = new Hashtable();

    public void addUser(String uname) {
        if (this.hcharname.get(uname) != null) {
            return;
        }
        this.hcharname.put(uname, uname);
        this.charname.add(uname);
    }

    public boolean canSelectChar() {
        if (System.currentTimeMillis() - this.timeSelectChar >= 5000L) {
            this.timeSelectChar = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public void countConnect() {
        if (System.currentTimeMillis() - this.timeConnect < 1000L) {
            ++this.count;
            this.maxConnectPerSecond = this.maxConnectPerSecond > this.count ? this.maxConnectPerSecond : this.count;
        } else {
            ++this.count;
            this.timeConnect = System.currentTimeMillis();
            this.maxConnectPerSecond = this.maxConnectPerSecond > this.count ? this.maxConnectPerSecond : this.count;
            this.count = 0;
        }
    }

    public void countLogin() {
        if (System.currentTimeMillis() - this.timeLogin < 1000L) {
            ++this.count1;
            this.maxLogin = this.maxLogin > this.count1 ? this.maxLogin : this.count1;
        } else {
            ++this.count1;
            this.timeLogin = System.currentTimeMillis();
            this.maxLogin = this.maxLogin > this.count1 ? this.maxLogin : this.count1;
            this.count1 = 0;
        }
    }

    public void countTaoChar() {
        if (System.currentTimeMillis() - this.timetaoChar < 1000L) {
            ++this.count2;
            this.maxTaoChar = this.maxTaoChar > this.count2 ? this.maxTaoChar : this.count2;
        } else {
            ++this.count2;
            this.timetaoChar = System.currentTimeMillis();
            this.maxTaoChar = this.maxTaoChar > this.count2 ? this.maxTaoChar : this.count2;
            this.count2 = 0;
        }
    }

    public void writeLog() {
        if (System.currentTimeMillis() - this.timeWriteLog < 300000L) {
            return;
        }
        this.timeWriteLog = System.currentTimeMillis();
        String info = "";
        int i = 0;
        while (i < this.charname.size()) {
            info = String.valueOf(info) + this.charname.get(i) + ",";
            ++i;
        }
        Logger.logStringWithDate(info, String.valueOf(this.ip) + ".txt");
    }
}

