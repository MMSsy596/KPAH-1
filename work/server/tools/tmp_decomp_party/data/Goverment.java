/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  real.Char
 *  real.CharManager
 *  real.MessageCreator
 */
package data;

import java.io.IOException;
import real.Char;
import real.CharManager;
import real.MessageCreator;

public class Goverment {
    public static final byte KING = 0;
    public static final byte DAITHAN = 1;
    public static final byte TUONGQUAN = 2;
    public static final byte BODAU = 3;
    public static final String[] NAME = new String[]{"Vua", "\u0110\u1ea1i th\u1ea7n", "T\u01b0\u1edbng qu\u00e2n", "B\u1ed9 \u0111\u1ea7u"};
    public String king = "";
    public String[] daithan = new String[]{"", ""};
    public String[] tuongquan = new String[]{"", ""};
    public String[] bodau = new String[]{"", ""};

    public boolean addMember(int pos, String charname) {
        charname = charname.trim();
        block0 : switch (pos) {
            case 0: {
                if (!this.king.equals("")) {
                    return false;
                }
                this.king = charname;
                break;
            }
            case 1: {
                if (!this.daithan[0].equals("") && !this.daithan[1].equals("")) {
                    return false;
                }
                int i = 0;
                while (i < 2) {
                    if (this.daithan[i].equals("")) {
                        this.daithan[i] = charname.toLowerCase();
                        break block0;
                    }
                    ++i;
                }
                break;
            }
            case 2: {
                if (!this.tuongquan[0].equals("") && !this.tuongquan[1].equals("")) {
                    return false;
                }
                int i = 0;
                while (i < 2) {
                    if (this.tuongquan[i].equals("")) {
                        this.tuongquan[i] = charname.toLowerCase();
                        break block0;
                    }
                    ++i;
                }
                break;
            }
            case 3: {
                if (!this.bodau[0].equals("") && !this.bodau[1].equals("")) {
                    return false;
                }
                int i = 0;
                while (i < 2) {
                    if (this.bodau[i].equals("")) {
                        this.bodau[i] = charname.toLowerCase();
                        break block0;
                    }
                    ++i;
                }
                break;
            }
        }
        return true;
    }

    public boolean removeMember(int pos, String charname) {
        charname = charname.trim();
        switch (pos) {
            case 0: {
                return false;
            }
            case 1: {
                int i = 0;
                while (i < 2) {
                    if (this.daithan[i].equals(charname.toLowerCase())) {
                        this.daithan[i] = "";
                        return true;
                    }
                    ++i;
                }
                break;
            }
            case 2: {
                int i = 0;
                while (i < 2) {
                    if (this.tuongquan[i].equals(charname.toLowerCase())) {
                        this.tuongquan[i] = "";
                        return true;
                    }
                    ++i;
                }
                break;
            }
            case 3: {
                int i = 0;
                while (i < 2) {
                    if (this.bodau[i].equals(charname.toLowerCase())) {
                        this.bodau[i] = "";
                        return true;
                    }
                    ++i;
                }
                break;
            }
        }
        return false;
    }

    public void removeAll() {
        this.king = "";
        this.daithan = new String[]{"", ""};
        this.bodau = new String[]{"", ""};
        this.tuongquan = new String[]{"", ""};
    }

    public void reset() {
        Char p = CharManager.instance.getCharByCharName(this.king);
        if (p != null) {
            p.rankGov = (byte)-1;
            p.potions[78] = 0;
            p.potions[28] = 0;
            if (p.potions[27] == 1) {
                p.potions[27] = 0;
            }
            p.potions[87] = 0;
            p.potions[85] = 0;
            p.calculateAttrib();
            try {
                p.sendMessage(MessageCreator.createMainCharInfoMessage((Char)p));
            }
            catch (IOException iOException) {
                // empty catch block
            }
            p.sendMessage(MessageCreator.createCharWearingMessage((Char)p, (Char)p));
            p.sendToNearPlayer(MessageCreator.createCharWearingMessage((Char)p, (Char)p));
            p.sendToNearPlayer(MessageCreator.createCharInfo((Char)p));
            MessageCreator.createMsgCharMonster((Char)p, (Char)p);
            try {
                p.sendMessage(MessageCreator.createMainCharInfoMessage((Char)p));
            }
            catch (IOException iOException) {
                // empty catch block
            }
            p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
        }
        int i = 0;
        while (i < 2) {
            p = CharManager.instance.getCharByCharName(this.daithan[i]);
            this.daithan[i] = "";
            if (p != null) {
                p.rankGov = (byte)-1;
                p.potions[78] = 0;
                p.potions[28] = 0;
                if (p.potions[27] == 1) {
                    p.potions[27] = 0;
                }
                p.potions[87] = 0;
                p.potions[85] = 0;
                try {
                    p.sendMessage(MessageCreator.createMainCharInfoMessage((Char)p));
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                p.sendMessage(MessageCreator.createCharWearingMessage((Char)p, (Char)p));
                p.sendToNearPlayer(MessageCreator.createCharWearingMessage((Char)p, (Char)p));
                p.sendToNearPlayer(MessageCreator.createCharInfo((Char)p));
                p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
            }
            ++i;
        }
        i = 0;
        while (i < 2) {
            p = CharManager.instance.getCharByCharName(this.tuongquan[i]);
            this.tuongquan[i] = "";
            if (p != null) {
                p.rankGov = (byte)-1;
                p.potions[78] = 0;
                p.potions[28] = 0;
                if (p.potions[27] == 1) {
                    p.potions[27] = 0;
                }
                p.potions[87] = 0;
                p.potions[85] = 0;
                try {
                    p.sendMessage(MessageCreator.createMainCharInfoMessage((Char)p));
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                p.sendMessage(MessageCreator.createCharWearingMessage((Char)p, (Char)p));
                p.sendToNearPlayer(MessageCreator.createCharWearingMessage((Char)p, (Char)p));
                p.sendToNearPlayer(MessageCreator.createCharInfo((Char)p));
                p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
            }
            ++i;
        }
        i = 0;
        while (i < 2) {
            p = CharManager.instance.getCharByCharName(this.bodau[i]);
            this.bodau[i] = "";
            if (p != null) {
                p.rankGov = (byte)-1;
                p.potions[78] = 0;
                p.potions[28] = 0;
                if (p.potions[27] == 1) {
                    p.potions[27] = 0;
                }
                p.potions[87] = 0;
                p.potions[85] = 0;
                try {
                    p.sendMessage(MessageCreator.createMainCharInfoMessage((Char)p));
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                p.sendMessage(MessageCreator.createCharWearingMessage((Char)p, (Char)p));
                p.sendToNearPlayer(MessageCreator.createCharWearingMessage((Char)p, (Char)p));
                p.sendToNearPlayer(MessageCreator.createCharInfo((Char)p));
                p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
            }
            ++i;
        }
        this.removeAll();
    }

    public String getInfo() {
        String info = this.king;
        String[] info1 = new String[]{"", ""};
        int i = 0;
        while (i < 2) {
            info1[i] = this.daithan[i];
            ++i;
        }
        info = String.valueOf(info) + "," + info1[0] + "," + info1[1];
        info1[0] = "";
        info1[1] = "";
        i = 0;
        while (i < 2) {
            info1[i] = this.tuongquan[i];
            ++i;
        }
        info = String.valueOf(info) + "," + info1[0] + "," + info1[1];
        info1[0] = "";
        info1[1] = "";
        i = 0;
        while (i < 2) {
            info1[i] = this.bodau[i];
            ++i;
        }
        info = String.valueOf(info) + "," + info1[0] + "," + info1[1];
        return info;
    }

    public byte getRankGov(String charname) {
        if (charname.equals(this.king)) {
            return 0;
        }
        int i = 0;
        while (i < 2) {
            if (this.daithan[i].equals(charname.toLowerCase())) {
                return 1;
            }
            ++i;
        }
        i = 0;
        while (i < 2) {
            if (this.tuongquan[i].equals(charname.toLowerCase())) {
                return 2;
            }
            ++i;
        }
        i = 0;
        while (i < 2) {
            if (this.bodau[i].equals(charname.toLowerCase())) {
                return 3;
            }
            ++i;
        }
        return -1;
    }
}

