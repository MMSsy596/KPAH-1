/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  real.Char
 *  real.MessageCreator
 */
package data;

import java.io.IOException;
import java.util.Vector;
import real.Char;
import real.MessageCreator;

public class TuBinhTemplate {
    public byte id;
    public byte nmanh = (byte)5;
    public byte[] manh = new byte[4];
    public String name;
    public short attack;
    public short def;
    public short crit;
    public short dog;
    public short hp;
    public short mp;
    public short level;
    public static Vector<TuBinhTemplate> ALL_TU_BINH = new Vector();
    public static byte MAX_CO_VAT = (byte)4;

    public TuBinhTemplate getTemplate() {
        return ALL_TU_BINH.get(this.id);
    }

    public String getInfoSave() {
        return String.valueOf(this.manh[0]) + "," + this.manh[1] + "," + this.manh[2] + "," + this.manh[3];
    }

    public boolean isFullManh() {
        return this.manh[0] == this.getTemplate().nmanh && this.manh[1] == this.getTemplate().nmanh && this.manh[2] == this.getTemplate().nmanh && this.manh[3] == this.getTemplate().nmanh;
    }

    public String[] getInfo() {
        String[] info = new String[]{"", ""};
        info[0] = "T\u1ea5n c\u00f4ng: +" + this.getTemplate().attack;
        info[0] = String.valueOf(info[0]) + "|Ph\u00f2ng th\u1ee7: +" + this.getTemplate().def;
        info[0] = String.valueOf(info[0]) + "|Ch\u00ed m\u1ea1ng: +" + this.getTemplate().crit;
        info[0] = String.valueOf(info[0]) + "|N\u00e9 tr\u00e1nh: +" + this.getTemplate().dog;
        info[0] = String.valueOf(info[0]) + "|HP: +" + this.getTemplate().hp;
        info[0] = String.valueOf(info[0]) + "|MP: +" + this.getTemplate().mp;
        info[1] = "M\u1ea3nh 1: " + this.manh[0] + "/" + this.getTemplate().nmanh;
        info[1] = String.valueOf(info[1]) + "|M\u1ea3nh 2: " + this.manh[1] + "/" + this.getTemplate().nmanh;
        info[1] = String.valueOf(info[1]) + "|M\u1ea3nh 3: " + this.manh[2] + "/" + this.getTemplate().nmanh;
        info[1] = String.valueOf(info[1]) + "|M\u1ea3nh 4: " + this.manh[3] + "/" + this.getTemplate().nmanh;
        return info;
    }

    public int getIdImage() {
        return this.id;
    }

    public boolean addManh(int idManh, Char p, int nManh) {
        if (this.manh[idManh] < this.getTemplate().nmanh) {
            int n = idManh;
            this.manh[n] = (byte)(this.manh[n] + nManh);
            if (this.manh[idManh] > this.getTemplate().nmanh) {
                this.manh[idManh] = this.getTemplate().nmanh;
            }
            return true;
        }
        return false;
    }

    public boolean addManh(int idManh, Char p) {
        if (this.manh[idManh] < this.getTemplate().nmanh) {
            int n = idManh;
            this.manh[n] = (byte)(this.manh[n] + 1);
            p.sendMessage(MessageCreator.createServerAlertMessage((String)("Ch\u00fac m\u1eebng b\u1ea1n \u0111\u00e3 gh\u00e9p \u0111\u01b0\u1ee3c " + this.manh[idManh] + "/" + this.getTemplate().nmanh + " m\u1ea3nh " + this.getTemplate().name + " " + (idManh + 1)), (String)""));
            if (this.isFullManh()) {
                p.calculateAttrib();
                try {
                    p.sendMessage(MessageCreator.createMainCharInfoMessage((Char)p));
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            return true;
        }
        return false;
    }

    public int getAttack() {
        if (this.isFullManh()) {
            return this.getTemplate().attack;
        }
        return 0;
    }

    public int getDef() {
        if (this.isFullManh()) {
            return this.getTemplate().def;
        }
        return 0;
    }

    public int getCrit() {
        if (this.isFullManh()) {
            return this.getTemplate().crit;
        }
        return 0;
    }

    public int getDog() {
        if (this.isFullManh()) {
            return this.getTemplate().dog;
        }
        return 0;
    }

    public int getHP() {
        if (this.isFullManh()) {
            return this.getTemplate().hp;
        }
        return 0;
    }

    public int getMP() {
        if (this.isFullManh()) {
            return this.getTemplate().mp;
        }
        return 0;
    }
}

