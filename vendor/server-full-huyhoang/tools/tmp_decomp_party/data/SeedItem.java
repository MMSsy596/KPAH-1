/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  real.Char
 *  real.Map
 *  real.MessageCreator
 */
package data;

import data.Database;
import data.SeedTemplate;
import real.Char;
import real.Map;
import real.MessageCreator;

public class SeedItem {
    public int idDB = 0;
    public int ownerID;
    public byte idFarm;
    public byte lvTree;
    public byte idTemplate = (byte)-1;
    public byte buy = 0;
    public byte lvPlot = 1;
    public byte rankTree = 0;
    public long timeLive;
    static byte seconds = (byte)60;

    public SeedItem(int idFrame, int idSeedTemplate, byte buy) {
        this.idFarm = (byte)idFrame;
        this.idTemplate = (byte)idSeedTemplate;
        this.buy = buy;
    }

    public byte getIdImgLevel() {
        return this.idTemplate > -1 ? Map.seedsTemplate[this.idTemplate].idImageTree[this.lvTree] : (byte)0;
    }

    public byte getDelYImgLv() {
        return this.idTemplate > -1 ? Map.seedsTemplate[this.idTemplate].deltaY[this.lvTree] : (byte)0;
    }

    public String getNameTree() {
        String s = "\u00f4 \u0111\u1ea5t " + (this.idFarm + 1) + " c\u1ea5p \u0111\u1ed9 " + this.lvPlot + " " + (this.idTemplate > -1 ? "\u0111ang tr\u1ed3ng c\u00e2y " + Map.seedsTemplate[this.idTemplate].name : "");
        return s;
    }

    public SeedTemplate getTemplate() {
        return Map.seedsTemplate[this.idFarm];
    }

    public SeedTemplate getTemplateTree() {
        return Map.seedsTemplate[this.idTemplate];
    }

    public int getTotalTimeLive() {
        if (this.idTemplate > -1) {
            short total = Map.seedsTemplate[this.idTemplate].timeLive[Map.seedsTemplate[this.idTemplate].timeLive.length - 1];
            int second = total * seconds - this.getTimeLive();
            return second > 0 ? second : 0;
        }
        return 0;
    }

    public int getTimeLive() {
        return this.idTemplate > -1 ? (int)((System.currentTimeMillis() - this.timeLive) / 1000L) : 0;
    }

    public void setLevelByTime() {
        if (this.idTemplate > -1) {
            int time = this.getTimeLive();
            short[] timeLive = Map.seedsTemplate[this.idTemplate].timeLive;
            byte i = (byte)(timeLive.length - 1);
            while (i >= 0) {
                if (time > timeLive[i] * seconds) {
                    this.lvTree = (byte)(i + 1);
                    break;
                }
                i = (byte)(i - 1);
            }
        }
    }

    public String upLevelPlot(Char p) {
        if (this.buy == 0) {
            return "Kh\u00f4ng th\u1ec3 n\u00e2ng c\u1ea5p";
        }
        if (this.idTemplate > -1 && !this.isDie()) {
            return "Kh\u00f4ng th\u1ec3 n\u00e2ng c\u1ea5p khi \u0111ang tr\u1ed3ng c\u00e2y";
        }
        if (p.lvHouse >= this.lvPlot + 1 && p.lvStore >= this.lvPlot + 1) {
            short[] leaf = SeedTemplate.LEAF_UP_LEVEL_PLOT[this.lvPlot - 1];
            if (p.potions[104] >= leaf[0] && p.potions[103] >= leaf[1] && p.potions[105] >= leaf[2]) {
                if (this.lvPlot < 6) {
                    this.lvPlot = (byte)(this.lvPlot + 1);
                    p.potions[104] = p.potions[104] - leaf[0];
                    p.potions[103] = p.potions[103] - leaf[1];
                    p.potions[105] = p.potions[105] - leaf[2];
                    p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
                    p.map.doSendFarm(p);
                    Database.instance.saveOrtherLog("tob_log_farm", p.getName(), String.valueOf(this.idFarm + 1) + " lvPlot= " + this.lvPlot, "ulvp");
                    return "N\u00e2ng c\u1ea5p th\u00e0nh c\u00f4ng. C\u1ea5p \u0111\u1ed9 hi\u1ec7n t\u1ea1i c\u1ee7a \u00f4 \u0111\u1ea5t l\u00e0 " + this.lvPlot;
                }
                return "C\u1ea5p \u0111\u1ed9 cao nh\u1ea5t c\u1ee7a \u00f4 \u0111\u1ea5t l\u00e0 6.";
            }
            return "B\u1ea1n ph\u1ea3i c\u1ea7n " + leaf[0] + " l\u00e1 v\u00e0ng, " + leaf[1] + " l\u00e1 xanh v\u00e0 " + leaf[2] + " l\u00e1 \u0111\u1ecf";
        }
        return "Nh\u00e0 ch\u00ednh v\u00e0 kho ph\u1ea3i \u0111\u1ea1t c\u1ea5p \u0111\u1ed9 " + (this.lvPlot + 1) + " tr\u1edf l\u00ean";
    }

    public boolean isDie() {
        return this.idTemplate > -1 && this.lvTree > Map.seedsTemplate[this.idTemplate].maxLv;
    }

    public boolean checkAvaliable() {
        if (this.idTemplate == -1) {
            return true;
        }
        return this.getTimeLive() >= Map.seedsTemplate[this.idTemplate].timeLive[Map.seedsTemplate[this.idTemplate].timeLive.length - 2] * seconds;
    }

    public String doHavest(Char p) {
        if (this.buy == 0) {
            return "B\u1ea1n ch\u01b0a mua \u00f4 \u0111\u1ea5t n\u00e0y";
        }
        if (this.isDie() || this.idTemplate == -1) {
            return "C\u00e2y \u0111\u00e3 ch\u1ebft ho\u1eb7c \u00f4 \u0111\u1ea5t ch\u01b0a c\u00f3 tr\u1ed3ng c\u00e2y";
        }
        if (this.lvTree < Map.seedsTemplate[this.idTemplate].maxLv - 1) {
            return "C\u00e2y ch\u01b0a th\u1ec3 thu ho\u1ea1ch";
        }
        if (this.lvTree >= Map.seedsTemplate[this.idTemplate].maxLv) {
            return "Kh\u00f4ng th\u1ec3 thu ho\u1ea1ch do c\u00e2y \u0111\u00e3 ch\u1ebft";
        }
        byte[] xValue = new byte[]{1, 4, 3, 2, 5};
        String[] mau = new String[]{"trang", "Xanh la", "do", "xanh duong", "tim"};
        String infoReturn = "\u0110\u00e3 thu ho\u1ea1ch " + this.getTemplateTree().name + ". S\u1ea3n l\u01b0\u1ee3ng thu \u0111\u01b0\u1ee3c l\u00e0 " + this.getTemplateTree().value * xValue[this.rankTree] + " s\u1ea3n ph\u1ea9m";
        switch (this.idTemplate) {
            case 0: 
            case 3: 
            case 6: {
                p.potions[103] = p.potions[103] + Map.seedsTemplate[this.idTemplate].value * xValue[this.rankTree];
                p.sendMessage(MessageCreator.createServerAlertMessage((String)("B\u1ea1n thu \u0111\u01b0\u1ee3c " + Map.seedsTemplate[this.idTemplate].value * xValue[this.rankTree] + " l\u00e1 xanh"), (String)""));
                Database.instance.saveOrtherLog("tob_log_farm", p.getName(), String.valueOf(this.idFarm + 1) + " thu hoach " + this.getTemplateTree().name + "_" + mau[this.rankTree] + " s\u1ea3n l\u01b0\u1ee3ng " + this.getTemplateTree().value * xValue[this.rankTree] + " l\u00e1 xanh", "havest");
                p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
                infoReturn = "B\u1ea1n thu \u0111\u01b0\u1ee3c " + this.getTemplateTree().value * xValue[this.rankTree] + " l\u00e1 xanh";
                break;
            }
            case 1: 
            case 4: 
            case 7: {
                p.potions[105] = p.potions[105] + this.getTemplateTree().value * xValue[this.rankTree];
                p.sendMessage(MessageCreator.createServerAlertMessage((String)("B\u1ea1n thu \u0111\u01b0\u1ee3c " + this.getTemplateTree().value * xValue[this.rankTree] + " l\u00e1 \u0111\u1ecf"), (String)""));
                Database.instance.saveOrtherLog("tob_log_farm", p.getName(), String.valueOf(this.idFarm + 1) + " thu hoach " + this.getTemplateTree().name + "_" + mau[this.rankTree] + " s\u1ea3n l\u01b0\u1ee3ng " + this.getTemplateTree().value * xValue[this.rankTree] + " l\u00e1 \u0111\u1ecf", "havest");
                p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
                infoReturn = "B\u1ea1n thu \u0111\u01b0\u1ee3c " + this.getTemplateTree().value * xValue[this.rankTree] + " l\u00e1 \u0111\u1ecf";
                break;
            }
            case 2: 
            case 5: 
            case 8: {
                p.potions[104] = p.potions[104] + this.getTemplateTree().value * xValue[this.rankTree];
                p.sendMessage(MessageCreator.createServerAlertMessage((String)("B\u1ea1n thu \u0111\u01b0\u1ee3c " + this.getTemplateTree().value * xValue[this.rankTree] + " l\u00e1 v\u00e0ng"), (String)""));
                Database.instance.saveOrtherLog("tob_log_farm", p.getName(), String.valueOf(this.idFarm + 1) + " thu hoach " + this.getTemplateTree().name + "_" + mau[this.rankTree] + " s\u1ea3n l\u01b0\u1ee3ng " + this.getTemplateTree().value * xValue[this.rankTree] + " l\u00e1 v\u00e0ng", "havest");
                p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
                infoReturn = "B\u1ea1n thu \u0111\u01b0\u1ee3c " + this.getTemplateTree().value * xValue[this.rankTree] + " l\u00e1 v\u00e0ng";
                break;
            }
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: {
                try {
                    Map cfr_ignored_0 = p.map;
                    Map.addXPForChar((Char)p, (long)(this.getTemplateTree().value * xValue[this.rankTree]), (boolean)false, (String)"seeditem");
                    Database.instance.saveOrtherLog("tob_log_farm", p.getName(), String.valueOf(this.idFarm + 1) + " thu hoach " + this.getTemplateTree().name + "_" + mau[this.rankTree] + " s\u1ea3n l\u01b0\u1ee3ng " + this.getTemplateTree().value * xValue[this.rankTree] + " XP", "hxp");
                    infoReturn = "B\u1ea1n nh\u1eadn \u0111\u01b0\u1ee3c " + this.getTemplateTree().value * xValue[this.rankTree] + " exp";
                }
                catch (Exception exception) {}
                break;
            }
            case 15: {
                break;
            }
            case 16: {
                break;
            }
            case 17: {
                break;
            }
        }
        this.idTemplate = (byte)-1;
        this.lvTree = 0;
        this.timeLive = 0L;
        p.map.doSendFarm(p);
        return infoReturn;
    }

    public void delTree() {
        this.reset();
    }

    public void reset() {
        this.idTemplate = (byte)-1;
        this.lvTree = 0;
        this.timeLive = 0L;
    }
}

