/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  io.Message
 *  real.Char
 *  real.Map
 *  real.MessageCreator
 */
package real;

import data.Database;
import io.Message;
import real.Char;
import real.Map;
import real.MapLienDau;
import real.MessageCreator;
import real.NpcReceiveCard;
import util.Logger;

public class NpcReceiveCardLienDau
extends NpcReceiveCard {
    public int idClan = -1;
    public byte posNpc = 0;
    public Char charGive = null;
    public String nameClan = "";
    public short time = 0;
    public static byte[] x = new byte[]{40, 18, 61};
    public static byte[] y = new byte[]{15, 45, 45};
    public static String[] npc = new String[]{"Tr\u1ea7n th\u1ed1ng l\u0129nh", "T\u1ea3 th\u1ed1ng l\u0129nh", "H\u1eefu th\u1ed1ng l\u0129nh"};
    public byte inCountry = 0;
    static short timeGiveCard = (short)60;
    public static boolean[] isFinish = new boolean[3];
    public static String[] nameCountry = new String[]{"Thanh long", "H\u1eafc h\u1ed5", "Huy\u1ec1n v\u0169"};

    public NpcReceiveCardLienDau(int pos, int country) {
        super(pos, country);
        this.posNpc = (byte)pos;
        this.inCountry = (byte)country;
    }

    @Override
    public boolean giveCard(Char p, boolean isClanTown) {
        int posNpc = this.checkInRange(p.x / 16, p.y / 16);
        if (posNpc == -1) {
            return false;
        }
        if (this.charGive != null) {
            return false;
        }
        if (this.idClan == p.myCountry) {
            return false;
        }
        this.time = timeGiveCard;
        this.charGive = p;
        p.timeGiveCardTown = System.currentTimeMillis();
        Database.instance.saveOrtherLog("", p.charname, String.valueOf(p.myCountry) + " x= " + p.x / 16 + " y= " + p.y / 16 + " mid= " + p.mapID + " posnpc= " + this.posNpc + " " + Char.getDayTime((long)0L), "stGive");
        return true;
    }

    private int checkInRange(int xChar, int yChar) {
        if (xChar >= x[this.posNpc] - 3 && xChar <= x[this.posNpc] + 3 && yChar >= y[this.posNpc] - 3 && yChar <= y[this.posNpc] + 3) {
            return 1;
        }
        return -1;
    }

    @Override
    public void update() {
        block14: {
            try {
                if (this.charGive != null && this.charGive.hp > 0 && this.charGive.timeGiveCardTown > 0L) {
                    if (System.currentTimeMillis() - this.charGive.timeGiveCardTown > (long)(NpcReceiveCard.timeGiveCard * 1000)) {
                        this.idClan = this.charGive.myCountry;
                        Map mapok = this.charGive.map;
                        this.nameClan = nameCountry[this.idClan];
                        this.charGive.map.sendAllPlayer(MessageCreator.createServerAlertAutoOffMessage((String)("L\u00e3nh th\u1ed5 " + nameCountry[this.idClan] + " \u0111\u00e3 giao th\u1ebb th\u00e0nh c\u00f4ng t\u1ea1i " + npc[this.posNpc])), (int)this.inCountry);
                        String charname = this.charGive.charname;
                        this.charGive.timeGiveCardTown = 0L;
                        try {
                            Database.instance.saveLogClan(String.valueOf(charname) + "_" + nameCountry[this.idClan], "gcok", "Giao the ok tai " + npc[this.posNpc] + " " + Char.getDayTime((long)0L));
                            this.charGive.map.sendAllPlayer(((MapLienDau)this.charGive.map).createMsgStartGetTown(this.inCountry), (int)this.inCountry);
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        NpcReceiveCardLienDau npc1 = ((MapLienDau)this.charGive.map).npcReceiveCard.get(0);
                        NpcReceiveCardLienDau npc2 = ((MapLienDau)this.charGive.map).npcReceiveCard.get(1);
                        NpcReceiveCardLienDau npc3 = ((MapLienDau)this.charGive.map).npcReceiveCard.get(2);
                        if (npc1.idClan == npc2.idClan && npc1.idClan == npc3.idClan && npc1.idClan != -1) {
                            this.charGive.map.curday[this.inCountry] = "";
                            this.charGive.timeGiveCardTown = 0L;
                            Map.sendAllCharServer((int)-1, (Message)MessageCreator.createServerAlertAutoOffMessage((String)("Ch\u00fac m\u1eebng l\u00e3nh th\u1ed5 " + nameCountry[this.charGive.myCountry] + " \u0111\u00e3 chi\u1ebfm \u0111\u01b0\u1ee3c th\u00e0nh.")));
                            try {
                                Logger.logStringWithDate(String.valueOf(Map.getNameMap((int)this.charGive.map.mapId)) + " " + nameCountry[this.charGive.myCountry], "ketqualiendau.txt");
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                            MapLienDau.NAME_WIN[this.charGive.map.mapId - 30] = nameCountry[this.charGive.myCountry];
                            if (npc1.charGive != null) {
                                npc1.charGive.timeGiveCardTown = 0L;
                            }
                            if (npc2.charGive != null) {
                                npc2.charGive.timeGiveCardTown = 0L;
                            }
                            if (npc3.charGive != null) {
                                npc3.charGive.timeGiveCardTown = 0L;
                            }
                            ((MapLienDau)this.charGive.map).isStart = false;
                            npc1.charGive = null;
                            npc2.charGive = null;
                            npc3.charGive = null;
                            Database.instance.saveLogClan(this.nameClan, "cgt", "\u0111\u00e3 chi\u1ebfm th\u00e0nh " + Char.getDayTime((long)0L));
                        }
                        mapok.sendAllPlayer(((MapLienDau)mapok).createMsgStartGetTown(this.inCountry), (int)this.inCountry);
                        this.time = 0;
                        this.charGive = null;
                        break block14;
                    }
                    this.time = (short)((long)timeGiveCard - (System.currentTimeMillis() - this.charGive.timeGiveCardTown) / 1000L);
                    if (this.checkInRange(this.charGive.x / 16, this.charGive.y / 16) == -1 || !this.charGive.map.isMapLienDau()) {
                        Database.instance.saveOrtherLog("", this.charGive.charname, String.valueOf(this.charGive.idClan) + " x= " + this.charGive.x / 16 + " y= " + this.charGive.y / 16 + " mid= " + this.charGive.mapID + " posnpc= " + this.posNpc + " > " + this.charGive.inCountry, "ngoairange");
                        this.charGive.timeGiveCardTown = 0L;
                        this.charGive.map.sendAllPlayer(MessageCreator.createServerAlertAutoOffMessage((String)("L\u00e3nh th\u1ed5 " + nameCountry[this.charGive.myCountry] + " giao th\u1ebb th\u1ea5t b\u1ea1i")), (int)this.charGive.myCountry);
                        this.time = 0;
                        this.charGive.map.sendAllPlayer(((MapLienDau)this.charGive.map).createMsgStartGetTown(this.inCountry), (int)this.inCountry);
                        this.charGive = null;
                    }
                    break block14;
                }
                if (!(this.charGive == null || this.charGive.hp > 0 && this.charGive.map.isMapLienDau())) {
                    this.charGive.timeGiveCardTown = 0L;
                    Database.instance.saveOrtherLog("", this.charGive.charname, String.valueOf(this.charGive.idClan) + " x= " + this.charGive.x / 16 + " y= " + this.charGive.y / 16 + " mid= " + this.charGive.mapID + " posnpc= " + this.posNpc + " > " + this.charGive.inCountry, "ngoairange1");
                    this.charGive.map.sendAllPlayer(MessageCreator.createServerAlertAutoOffMessage((String)("L\u00e3nh th\u1ed5 " + nameCountry[this.charGive.myCountry] + " giao th\u1ebb th\u1ea5t b\u1ea1i")), (int)this.charGive.myCountry);
                    this.time = 0;
                    this.charGive.map.sendAllPlayer(MessageCreator.createMsgStartGetTown((int)this.inCountry), (int)this.inCountry);
                    this.charGive = null;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getNameCharGive() {
        return this.charGive == null ? "" : this.charGive.charname;
    }

    @Override
    public short getIDCharGive() {
        return this.charGive == null ? (short)32000 : this.charGive.id;
    }

    @Override
    public String getNameNpc() {
        return npc[this.posNpc];
    }
}

