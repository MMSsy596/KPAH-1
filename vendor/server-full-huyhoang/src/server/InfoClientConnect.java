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
    public long lastSeen = System.currentTimeMillis();
    public long blockUntil = 0L;
    public long blockLogAt = 0L;
    public String lastBlockReason = "";
    private long connectWindowStart = System.currentTimeMillis();
    private int connectWindowCount = 0;
    public Vector<String> charname = new Vector<>();
    public Hashtable<String, String> hcharname = new Hashtable<>();

    public synchronized void addUser(String uname) {
        if (uname == null || uname.trim().isEmpty() || this.hcharname.get(uname) != null) {
            return;
        }
        this.hcharname.put(uname, uname);
        this.charname.add(uname);
    }

    public synchronized boolean canSelectChar() {
        this.lastSeen = System.currentTimeMillis();
        if (System.currentTimeMillis() - this.timeSelectChar >= 5000L) {
            this.timeSelectChar = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public synchronized void countConnect() {
        this.lastSeen = System.currentTimeMillis();
        if (System.currentTimeMillis() - this.timeConnect < 1000L) {
            ++this.count;
            this.maxConnectPerSecond = Math.max(this.maxConnectPerSecond, this.count);
        } else {
            ++this.count;
            this.timeConnect = System.currentTimeMillis();
            this.maxConnectPerSecond = Math.max(this.maxConnectPerSecond, this.count);
            this.count = 0;
        }
    }

    public synchronized boolean registerConnect(long now, int windowMs, int maxConnect, long blockMs) {
        this.lastSeen = now;
        this.countConnect();
        if (this.connectWindowStart <= 0L || now - this.connectWindowStart >= windowMs) {
            this.connectWindowStart = now;
            this.connectWindowCount = 0;
        }
        ++this.connectWindowCount;
        if (this.connectWindowCount > maxConnect) {
            this.blockUntil = now + blockMs;
            this.lastBlockReason = "connect_flood";
            return false;
        }
        return true;
    }

    public synchronized boolean isCurrentlyBlocked(long now) {
        this.lastSeen = now;
        return this.blockUntil > now;
    }

    public synchronized boolean shouldWriteBlockLog(long now) {
        if (this.blockLogAt > now - 30000L) {
            return false;
        }
        this.blockLogAt = now;
        return true;
    }

    public synchronized boolean isExpired(long now, long ttlMs) {
        return this.blockUntil <= now && now - this.lastSeen > ttlMs;
    }

    public synchronized void countLogin() {
        this.lastSeen = System.currentTimeMillis();
        if (System.currentTimeMillis() - this.timeLogin < 1000L) {
            ++this.count1;
            this.maxLogin = Math.max(this.maxLogin, this.count1);
        } else {
            ++this.count1;
            this.timeLogin = System.currentTimeMillis();
            this.maxLogin = Math.max(this.maxLogin, this.count1);
            this.count1 = 0;
        }
    }

    public synchronized void countTaoChar() {
        this.lastSeen = System.currentTimeMillis();
        if (System.currentTimeMillis() - this.timetaoChar < 1000L) {
            ++this.count2;
            this.maxTaoChar = Math.max(this.maxTaoChar, this.count2);
        } else {
            ++this.count2;
            this.timetaoChar = System.currentTimeMillis();
            this.maxTaoChar = Math.max(this.maxTaoChar, this.count2);
            this.count2 = 0;
        }
    }

    public synchronized void writeLog() {
        if (System.currentTimeMillis() - this.timeWriteLog < 300000L) {
            return;
        }
        this.timeWriteLog = System.currentTimeMillis();
        String info = "";
        int i = 0;
        while (i < this.charname.size()) {
            info = info + this.charname.get(i) + ",";
            ++i;
        }
        Logger.logStringWithDate(info, this.ip + ".txt");
    }
}
