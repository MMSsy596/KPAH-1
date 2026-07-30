/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  real.Char
 *  real.CharManager
 *  real.Map
 *  real.MessageCreator
 */
package real;

import data.CharInfo;
import data.Database;
import data.NewClan;
import java.util.Vector;
import real.Char;
import real.CharManager;
import real.InfoGifLucky;
import real.Map;
import real.MessageCreator;

public class RegisterAttack {
    public String dayAttack = "";
    public byte idMyAttack = (byte)-1;
    public byte idNationAttackMe = (byte)-1;
    public long time = System.currentTimeMillis();
    public byte win = 0;
    public byte lose = 0;
    public byte timeX2 = 0;

    public void settime() {
    }

    public void setMyAttack(int idNationMyAttack) {
        this.idMyAttack = (byte)idNationMyAttack;
    }

    public void setNationAttackMe(int idNationAttackMe) {
        this.idNationAttackMe = (byte)idNationAttackMe;
    }

    public void setDayAttack(String dayAttack) {
        this.dayAttack = dayAttack;
    }

    public void update(int country) {
    }

    public void reset() {
        this.dayAttack = "";
        this.idMyAttack = (byte)-1;
        this.idNationAttackMe = (byte)-1;
    }

    public boolean openX2(int country, int hour) {
        if (this.timeX2 > 0) {
            if (hour == 9) {
                Map.x2Country[country] = 3;
                Map.minuteX2Country[country] = System.currentTimeMillis() + (long)(this.timeX2 * 60 * 60 * 1000);
                Database.instance.saveEvent(Map.event.getInfo());
                Database.instance.saveOrtherLog("", Map.nameCountry[country], "Bat 150 " + this.timeX2, "x150");
                this.timeX2 = 0;
            }
            return true;
        }
        return false;
    }

    public void addGif(int country) {
        Vector topLieTram;
        int len;
        NewClan clan;
        int soluong = 0;
        boolean openx150 = false;
        if (this.win == 0 && this.lose == 0) {
            return;
        }
        if (this.win > 0) {
            soluong = 3;
            this.timeX2 = (byte)6;
        }
        if (openx150) {
            Map.x2Country[country] = 3;
            Map.minuteX2Country[country] = System.currentTimeMillis() + 3600000L;
            this.win = 0;
            this.lose = 0;
            Database.instance.saveEvent(Map.event.getInfo());
            Database.instance.saveOrtherLog("", Map.nameCountry[country], "Bat 150 " + this.timeX2, "x150");
            this.timeX2 = 0;
        }
        if (soluong > 0 && (clan = NewClan.getClan(Map.idClanTown[country])) != null) {
            Char p = CharManager.instance.getCharByCharName(clan.master.toLowerCase());
            if (p != null) {
                int i = 0;
                while (i < 5) {
                    p.doAddGemItem((int)InfoGifLucky.idMaterial[2][i], soluong, false);
                    p.doAddGemItem((int)InfoGifLucky.idMaterial[5][i], soluong, false);
                    i = (byte)(i + 1);
                }
                p.sendMessage(MessageCreator.createCharGemItem((Char)p));
                Database.instance.saveOrtherLog("", p.charname, String.valueOf(this.timeX2) + "_" + this.win + "_" + this.lose + "_Nh\u1eadn ng li\u1ec7u 6 m\u1ed7i lo\u1ea1i " + soluong + " vi\u00ean", "gifwar");
            } else {
                Database.instance.saveOrtherLog("", clan.master, String.valueOf(this.timeX2) + "_" + this.win + "_" + this.lose + "_kh\u00f4ng online", "gifwarFail");
            }
        }
        if ((len = (topLieTram = (Vector)Map.topPK.get(country)).size()) > 3) {
            len = 3;
        }
        int[] ngemlientram = new int[]{5, 3, 2};
        int i = 0;
        while (i < len) {
            CharInfo cinfo = (CharInfo)topLieTram.get(i);
            if (cinfo.pk > 0) {
                Char p = CharManager.instance.getCharByCharName(cinfo.name.toLowerCase());
                if (p != null) {
                    String inf = "";
                    byte[] sl = new byte[5];
                    int j = 0;
                    while (j < ngemlientram[i]) {
                        int r;
                        int n = r = Map.r.nextInt(5);
                        sl[n] = (byte)(sl[n] + 1);
                        p.doAddGemItem((int)InfoGifLucky.idMaterial[2][r], 1, true);
                        j = (byte)(j + 1);
                    }
                    p.doAddGemItem(154, ngemlientram[i], true);
                    j = 0;
                    while (j < 5) {
                        if (sl[j] > 0) {
                            inf = String.valueOf(inf) + Map.gemTemplate[InfoGifLucky.idMaterial[2][j]].name + "_" + sl[j] + ",";
                        }
                        ++j;
                    }
                    inf = String.valueOf(inf) + Map.gemTemplate[154].name + "_" + ngemlientram[i];
                    Database.instance.saveOrtherLog("", p.charname, inf, "gifPK");
                } else {
                    Database.instance.saveOrtherLog("", ((CharInfo)topLieTram.get((int)i)).name, "khong online", "gifPKFail");
                }
            } else {
                Database.instance.saveOrtherLog("", ((CharInfo)topLieTram.get((int)i)).name, "ko co diem lien tram " + cinfo.pk, "gifPKFail");
            }
            ++i;
        }
        this.win = 0;
        this.lose = 0;
    }
}

