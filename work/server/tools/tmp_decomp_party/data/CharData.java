/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  real.Item
 */
package data;

import java.util.Vector;
import real.Item;

public class CharData {
    public int id;
    public String charname;
    public byte headStyle;
    public byte charClass = 0;
    public byte myCountry = (byte)-1;
    public byte gender;
    public int xp;
    public int hp;
    public int mp;
    public int map;
    public int x;
    public int y;
    public int gold;
    public int lv;
    public int using = 1;
    public long timeDell = 0L;
    public int idBigAvtPP = -1;
    public int indexXyPP = 0;
    public int nFramePP = 1;
    public int idBigAvtAo = -1;
    public int indexXyAo = 1;
    public int nFrameAo = 1;
    public int idBigAvtVK = -1;
    public int indexXyVK = 0;
    public int nFrameVK = 1;
    public short nHP1;
    public short nHP2;
    public short nHP3;
    public short nMP1;
    public short nMP2;
    public short nMP3;
    public short nAr1;
    public short nAr2;
    public short nAr3;
    public Vector<Item> item = new Vector();
}

