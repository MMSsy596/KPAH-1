/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  real.Char
 */
package real;

import java.util.Vector;
import real.Char;

public class Party {
    public static short MAX_MEMBER = (short)5;
    public short idParty = (short)-1;
    public short idMaster = (short)-1;
    public Vector<Char> userParty = new Vector();

    public Party() {
        this.idMaster = (short)-1;
        this.idParty = (short)-1;
    }

    public Party(short id) {
        this.idParty = id;
    }

    public void addUser(Char user) {
        int i = 0;
        while (i < this.userParty.size()) {
            if (this.userParty.elementAt((int)i).id == user.id) {
                return;
            }
            ++i;
        }
        this.userParty.addElement(user);
    }

    public Char remoUser(short idUser) {
        int i = 0;
        while (i < this.userParty.size()) {
            Char ch = this.userParty.elementAt(i);
            if (ch.id == idUser) {
                this.userParty.removeElementAt(i);
                return ch;
            }
            ++i;
        }
        return null;
    }

    public boolean checkConditionLydi() {
        return this.userParty.size() == 2 && this.userParty.get((int)0).gender != this.userParty.get((int)1).gender && this.userParty.get((int)0).nameHusband_wife.equals(this.userParty.get((int)1).charname);
    }

    public boolean checkConditionMarry() {
        return this.userParty.size() > 0 && this.userParty.size() <= 2 && this.userParty.get((int)0).gender != this.userParty.get((int)1).gender && this.userParty.get((int)0).married == 0 && this.userParty.get((int)1).married == 0 && this.userParty.get((int)0).lvDetail.lv >= 40 && this.userParty.get((int)1).lvDetail.lv >= 40;
    }

    public void partyRemove() {
        this.userParty.removeAllElements();
        this.idParty = (short)-1;
        this.idMaster = (short)-1;
    }
}

