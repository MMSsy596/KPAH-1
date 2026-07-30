/*
 * Decompiled with CFR 0.152.
 */
package data;

public class CharTemplate {
    int gender;
    int map;
    int weapon;
    int x;
    int y;
    int nHP1;
    int nMP1;
    int hp;
    int mp;
    int skill1;
    int strength;
    int agitity;
    int spirit;
    int dextrity;
    int luck;

    public CharTemplate(int gender, int map, int weapon, int x, int y, int nHP1, int nMP1, int hp, int mp, int skill1, int strength, int agitity, int spirit, int dextrity, int luck) {
        this.gender = gender;
        this.map = map;
        this.weapon = weapon;
        this.x = x;
        this.y = y;
        this.nHP1 = nHP1;
        this.nMP1 = nMP1;
        this.hp = hp;
        this.mp = mp;
        this.skill1 = skill1;
        this.strength = strength;
        this.agitity = agitity;
        this.spirit = spirit;
        this.dextrity = dextrity;
        this.luck = luck;
    }

    public String getDbInfo(int headStyle, int gender, String charname, int charClass, int hb, int mp, int mapID, int myCountry) {
        String pInfo = "" + headStyle;
        pInfo = String.valueOf(pInfo) + "," + gender;
        pInfo = String.valueOf(pInfo) + "," + charname;
        pInfo = String.valueOf(pInfo) + "," + charClass;
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",1";
        pInfo = String.valueOf(pInfo) + "," + this.hp;
        pInfo = String.valueOf(pInfo) + "," + mp;
        pInfo = String.valueOf(pInfo) + "," + mapID;
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",-1";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",1";
        pInfo = String.valueOf(pInfo) + ",1";
        pInfo = String.valueOf(pInfo) + ",1";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",20";
        pInfo = String.valueOf(pInfo) + "," + myCountry;
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        pInfo = String.valueOf(pInfo) + ",0";
        return pInfo;
    }
}

