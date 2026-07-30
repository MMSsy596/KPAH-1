/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  real.Map
 *  real.Potion
 */
package data;

import data.Xoso;
import java.util.Calendar;
import java.util.Random;
import real.Map;
import real.Potion;

public class EventGame {
    public byte[] value = new byte[3];
    public long[] timeBossAppear = new long[7];
    public byte timeDrop_T = 0;
    public byte maxT = 0;
    public byte nTDay = (byte)10;
    public byte countT = 0;
    public byte[] veso = new byte[100];
    public static byte[] COUNTRY_BOSS_APPEAR = new byte[2];
    public static boolean[] ALL_BOSS_DIE = new boolean[2];
    Potion ptT = null;
    static boolean isCheck = false;

    public EventGame() {
        Random r = new Random(System.currentTimeMillis());
        int country = r.nextInt(2);
        EventGame.COUNTRY_BOSS_APPEAR[0] = (byte)country;
        EventGame.COUNTRY_BOSS_APPEAR[1] = (byte)country;
    }

    public static void initCountTryBoss() {
        int country = Map.r.nextInt(2);
        EventGame.COUNTRY_BOSS_APPEAR[0] = (byte)country;
        EventGame.COUNTRY_BOSS_APPEAR[1] = (byte)country;
    }

    public static synchronized void checkBossDie() {
        if (ALL_BOSS_DIE[0] && ALL_BOSS_DIE[1]) {
            EventGame.ALL_BOSS_DIE[0] = false;
            EventGame.ALL_BOSS_DIE[1] = false;
            EventGame.initCountTryBoss();
        }
    }

    public String getInfo() {
        String info = String.valueOf(this.value[0]);
        info = String.valueOf(info) + "," + this.value[1];
        info = String.valueOf(info) + "," + this.value[2];
        info = String.valueOf(info) + "," + this.timeBossAppear[0];
        info = String.valueOf(info) + "," + this.timeBossAppear[1];
        info = String.valueOf(info) + "," + this.timeBossAppear[2];
        info = String.valueOf(info) + "," + this.timeBossAppear[3];
        info = String.valueOf(info) + "," + this.timeBossAppear[4];
        info = String.valueOf(info) + "," + this.timeBossAppear[5];
        info = String.valueOf(info) + "," + this.timeBossAppear[6];
        return info;
    }

    public String getInfoT() {
        String info = String.valueOf(this.maxT) + "," + this.nTDay + "," + this.countT;
        return info;
    }

    public synchronized byte getMoneyLuckyBuyBag(int percent) {
        if (this.value[0] < 5 && percent <= 10) {
            return 0;
        }
        if (this.value[1] < 15 && percent <= 30) {
            return 1;
        }
        if (percent <= 15) {
            return 2;
        }
        return 3;
    }

    public synchronized Potion dropT(Map m) {
        if (this.ptT != null) {
            return null;
        }
        if (this.maxT >= 500) {
            return null;
        }
        if (this.countT >= this.nTDay) {
            return null;
        }
        if (Map.randomMillion() >= 1000) {
            return null;
        }
        this.ptT = new Potion(118, 1, m);
        return this.ptT;
    }

    public void reSetPotionT() {
        this.ptT = null;
    }

    public boolean checkNewDay() {
        Calendar cl = Calendar.getInstance();
        int iHour = cl.get(11);
        if (iHour == 16 && Map.winNumber == -1) {
            Xoso.selectWinNumber();
        }
        if (iHour >= 16 && iHour < 17) {
            Xoso.numberBuy = new byte[100];
            Xoso.min = 9;
            Xoso.max = 0;
        }
        if (iHour >= 0 && iHour < 16) {
            Map.winNumber = (byte)-1;
        }
        if (iHour == 0 && !isCheck) {
            isCheck = true;
            this.countT = 0;
            this.nTDay = (byte)(Map.r.nextInt(5) + 4);
            return true;
        }
        if (iHour != 0 && isCheck) {
            isCheck = false;
        }
        return false;
    }
}

