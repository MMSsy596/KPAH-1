/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  real.Char
 *  real.Map
 *  real.MessageCreator
 *  real.cmd.LoginHandler
 */
package real;

import data.Database;
import data.GemItem;
import real.Char;
import real.Map;
import real.MessageCreator;
import real.cmd.LoginHandler;

public class InfoGifLucky {
    public byte id = 0;
    public int value = 0;
    public int idGif = -1;
    short typeBox = 0;
    public static short[][] idMaterial = new short[][]{{71, 78, 85, 92, 99}, {72, 79, 86, 93, 100}, {73, 80, 87, 94, 101}, {106, 113, 120, 127, 134}, {107, 114, 121, 128, 135}, {108, 115, 122, 129, 136}};

    public InfoGifLucky(byte id, short type) {
        this.id = id;
        this.typeBox = type;
    }

    public void createGifSilver(boolean spc, byte money) {
        switch (this.id) {
            case 0: {
                byte[] x10 = new byte[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 2, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5};
                this.value = x10[Map.r.nextInt(x10.length)] * 10000;
                break;
            }
            case 1: {
                byte[] x10 = new byte[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 2, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5};
                this.value = x10[Map.r.nextInt(x10.length)] * 100000;
                break;
            }
            case 2: {
                if (!spc) {
                    int ran = Map.r.nextInt(100);
                    if (ran < 10) {
                        short[] id = new short[]{69, 76, 83, 90, 97};
                        this.idGif = id[Map.r.nextInt(id.length)];
                        byte[] soluong = new byte[]{2, 4, 6};
                        this.value = soluong[money];
                        break;
                    }
                    this.idGif = Map.r.nextInt(2) + 61;
                    this.value = Map.r.nextInt(2) + 2;
                    break;
                }
                short[] id = new short[]{70, 77, 84, 91, 98};
                this.idGif = id[Map.r.nextInt(id.length)];
                byte[] soluong = new byte[]{1, 3, 4};
                this.value = soluong[money];
                break;
            }
            case 3: {
                byte[] id = new byte[]{112, 113};
                this.idGif = id[Map.r.nextInt(id.length)];
                this.value = 1;
            }
        }
    }

    public void createGifGold(boolean spc, int money) {
        switch (this.id) {
            case 0: {
                this.id = (byte)4;
                int rd = Map.r.nextInt(100);
                if (rd < 90) {
                    byte[] soluong = new byte[]{30, 60, 90};
                    this.idGif = Map.r.nextInt(100) < 50 ? 93 : 95;
                    this.value = soluong[money];
                    break;
                }
                if (rd < 95) {
                    byte[] soluong = new byte[]{1, 2, 3};
                    this.idGif = 100;
                    this.value = soluong[money];
                    break;
                }
                byte[] soluong = new byte[]{2, 3, 4};
                this.idGif = 84;
                this.value = soluong[money];
                break;
            }
            case 1: {
                byte[] x10 = new byte[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 2, 4, 4, 4, 4, 4, 5, 5, 5, 5};
                this.value = x10[Map.r.nextInt(x10.length)] * 100000 + 100000;
                break;
            }
            case 2: {
                if (!spc) {
                    int ran = Map.r.nextInt(100);
                    if (ran < 10) {
                        short[] id = new short[]{63, 64, 65};
                        this.idGif = id[Map.r.nextInt(id.length)];
                        byte[] soluong = new byte[]{1, 2, 3};
                        this.value = soluong[money];
                        break;
                    }
                    if (ran < 35) {
                        short[] id = new short[]{105, 112, 119, 126, 133};
                        this.idGif = id[Map.r.nextInt(id.length)];
                        byte[] soluong = new byte[]{2, 3, 5};
                        this.value = soluong[money];
                        break;
                    }
                    if (ran < 90) {
                        if (Map.r.nextInt(100) <= 90) {
                            short[] id = idMaterial[3];
                            this.idGif = id[Map.r.nextInt(id.length)];
                        } else {
                            short[] id = idMaterial[0];
                            this.idGif = id[Map.r.nextInt(id.length)];
                        }
                        this.value = 1;
                        break;
                    }
                    if (ran >= 100) break;
                    short[] id = new short[]{70, 77, 84, 91, 98};
                    this.idGif = id[Map.r.nextInt(id.length)];
                    byte[] soluong = new byte[]{2, 3, 4};
                    this.value = soluong[money];
                    break;
                }
                int ran = Map.r.nextInt(100);
                if (ran <= 25) {
                    byte[] id = new byte[]{66, 11};
                    this.idGif = id[Map.r.nextInt(id.length)];
                    this.value = 1;
                    break;
                }
                if (ran <= 65) {
                    if (Map.r.nextInt(100) <= 50) {
                        short[] id = idMaterial[5];
                        this.idGif = id[Map.r.nextInt(id.length)];
                    } else {
                        short[] id = idMaterial[2];
                        this.idGif = id[Map.r.nextInt(id.length)];
                    }
                    this.value = 1;
                    break;
                }
                if (ran >= 100) break;
                if (Map.r.nextInt(100) <= 50) {
                    short[] id = idMaterial[4];
                    this.idGif = id[Map.r.nextInt(id.length)];
                } else {
                    short[] id = idMaterial[1];
                    this.idGif = id[Map.r.nextInt(id.length)];
                }
                this.value = 1;
                break;
            }
            case 3: {
                this.idGif = 114;
                this.value = 1;
            }
        }
    }

    public String removeGifLixi(Char p) {
        String info = "";
        switch (this.id) {
            case 0: {
                p.addXu((long)this.value, "infogiftlucky 1");
                info = String.valueOf(this.value) + " xu";
                this.value = 0;
                break;
            }
            case 1: {
                Map cfr_ignored_0 = p.map;
                Map.addXpCharEvent((Char)p, (long)this.value, (boolean)false, (String)"InfoGifLucky removeGifLixi");
                info = String.valueOf(this.value) + " exp";
                this.value = 0;
                break;
            }
            case 2: {
                if (this.idGif <= -1) break;
                if (p.listGemitem[this.idGif] == 0) {
                    GemItem gem = new GemItem(this.idGif);
                    gem.ownerId = p.charDBID;
                    gem.realId = p.getIDItem();
                    p.gemItem.add(gem);
                }
                int n = this.idGif;
                p.listGemitem[n] = p.listGemitem[n] + this.value;
                int n2 = this.idGif;
                p.allGemGet[n2] = p.allGemGet[n2] + this.value;
                info = String.valueOf(this.value) + " " + Map.gemTemplate[this.idGif].name;
                this.value = 0;
                this.idGif = -1;
                break;
            }
            case 3: {
                if (this.idGif <= -1) break;
                int n = this.idGif;
                p.potions[n] = p.potions[n] + this.value;
                info = String.valueOf(this.value) + " " + LoginHandler.PORTION_NAME[this.idGif];
                this.idGif = -1;
                this.value = 0;
                break;
            }
            case 4: {
                if (this.idGif <= -1) break;
                int n = this.idGif;
                p.potions[n] = p.potions[n] + this.value;
                info = String.valueOf(this.value) + " " + LoginHandler.PORTION_NAME[this.idGif];
                this.value = 0;
                this.idGif = -1;
            }
        }
        return info;
    }

    public String removeGif(Char p) {
        String info = "";
        switch (this.id) {
            case 0: {
                p.addXu((long)this.value, "infogiftlucky 2");
                info = String.valueOf(this.value) + " xu";
                this.value = 0;
                p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
                break;
            }
            case 1: {
                Map cfr_ignored_0 = p.map;
                Map.addXpCharEvent((Char)p, (long)this.value, (boolean)false, (String)"InfoGifLucky removeGif");
                info = String.valueOf(this.value) + " exp";
                this.value = 0;
                break;
            }
            case 2: {
                if (this.idGif <= -1) break;
                if (p.listGemitem[this.idGif] == 0) {
                    GemItem gem = new GemItem(this.idGif);
                    gem.ownerId = p.charDBID;
                    gem.realId = p.getIDItem();
                    p.gemItem.add(gem);
                }
                int n = this.idGif;
                p.listGemitem[n] = p.listGemitem[n] + this.value;
                int n2 = this.idGif;
                p.allGemGet[n2] = p.allGemGet[n2] + this.value;
                info = String.valueOf(this.value) + " " + Map.gemTemplate[this.idGif].name;
                this.value = 0;
                this.idGif = -1;
                p.sendMessage(MessageCreator.createCharGemItem((Char)p));
                break;
            }
            case 3: {
                if (this.idGif <= -1) break;
                int n = this.idGif;
                p.potions[n] = p.potions[n] + this.value;
                info = String.valueOf(this.value) + " " + LoginHandler.PORTION_NAME[this.idGif];
                this.idGif = -1;
                this.value = 0;
                p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
                break;
            }
            case 4: {
                if (this.idGif <= -1) break;
                int n = this.idGif;
                p.potions[n] = p.potions[n] + this.value;
                info = String.valueOf(this.value) + " " + LoginHandler.PORTION_NAME[this.idGif];
                this.value = 0;
                this.idGif = -1;
                p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
            }
        }
        Database.instance.saveOrtherLog("", p.getName(), info.trim().equals("") ? "H\u1ed9p kh\u00f4ng tr\u00fang th\u01b0\u1edfng" : info, "luck" + (this.typeBox == 0 ? "SILV" : "GOLD"));
        return info;
    }

    public String getInfoGif() {
        String st = "";
        switch (this.id) {
            case 0: {
                st = String.valueOf(this.value) + " xu";
                break;
            }
            case 1: {
                st = String.valueOf(this.value) + " exp";
                break;
            }
            case 2: {
                st = String.valueOf(this.value) + " " + Map.gemTemplate[this.idGif].name;
                break;
            }
            case 3: {
                st = String.valueOf(this.value) + " " + LoginHandler.PORTION_NAME[this.idGif];
                break;
            }
            case 4: {
                st = String.valueOf(this.value) + " " + LoginHandler.PORTION_NAME[this.idGif];
            }
        }
        return st;
    }
}

