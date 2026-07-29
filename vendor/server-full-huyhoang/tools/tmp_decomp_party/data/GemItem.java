/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  real.GemTemplate
 *  real.Map
 */
package data;

import real.Actor;
import real.GemTemplate;
import real.Map;

public class GemItem
extends Actor {
    public byte place;
    public short idGemTemplate;
    public short realId;
    public short idOffGemPlayer;
    public int belongUser = 0;
    public boolean isBoss = false;
    public boolean islock;
    public int belongParty = 0;
    public int ownerId;
    public int dbownerId;
    public int dbid;
    public int prizeSell = 0;
    public String charCanBuy = "";

    public GemItem(int idtemplate) {
        this.idGemTemplate = (short)idtemplate;
        this.cat = (byte)6;
    }

    public GemTemplate getTemplate() {
        return Map.gemTemplate[this.idGemTemplate];
    }

    @Override
    public String getCharcanBuy() {
        return this.charCanBuy;
    }

    @Override
    public void setCharcanBuy(String charname) {
        this.charCanBuy = charname;
    }
}

