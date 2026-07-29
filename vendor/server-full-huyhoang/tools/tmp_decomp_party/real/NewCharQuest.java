/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  data.InfoQuestTemplate
 *  real.Char
 *  real.Map
 *  real.MessageCreator
 */
package real;

import data.Database;
import data.InfoQuestTemplate;
import data.NewClan;
import real.Char;
import real.Map;
import real.MessageCreator;

public class NewCharQuest {
    public byte main_sub = 0;
    public static byte FINISH = 1;
    public static byte WORKING = (byte)2;
    public static byte UN_RECEIVE = 0;
    public static byte DONE = (byte)3;
    public short id_quest = 0;
    public short[] nItem = null;
    public short[] monsterKilled = null;
    public short state_quest = 0;

    public NewCharQuest(boolean isSub) {
        if (isSub) {
            this.id_quest = (short)-1;
        }
    }

    public NewCharQuest(short id, byte main_sub) {
        this.main_sub = main_sub;
        this.id_quest = id;
        InfoQuestTemplate info = this.getTemplate();
        this.nItem = new short[info.totalitemget.size()];
        this.monsterKilled = new short[info.totalMonsKilled.size()];
    }

    public InfoQuestTemplate getTemplate() {
        if (this.main_sub == 0) {
            return (InfoQuestTemplate)Map.allMainQuest.get(this.id_quest);
        }
        if (this.main_sub == 2) {
            return (InfoQuestTemplate)Map.allClanQuest.get(this.id_quest);
        }
        return (InfoQuestTemplate)Map.allSubQuest.get(this.id_quest);
    }

    public void initQuest(short idQuest, String[] nITemGet, String[] nMonsKilled, String finish) {
        this.id_quest = idQuest;
        this.nItem = new short[nITemGet.length];
        int i = 0;
        while (i < nITemGet.length) {
            try {
                this.nItem[i] = Short.parseShort(nITemGet[i]);
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++i;
        }
        this.monsterKilled = new short[nMonsKilled.length];
        i = 0;
        while (i < nMonsKilled.length) {
            try {
                this.monsterKilled[i] = Short.parseShort(nMonsKilled[i]);
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++i;
        }
        try {
            this.state_quest = Byte.parseByte(finish);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public String getInfoItemGet() {
        String info = "";
        if (this.nItem != null) {
            info = String.valueOf(this.nItem[0]);
            int i = 1;
            while (i < this.nItem.length) {
                info = String.valueOf(info) + "," + this.nItem[i];
                ++i;
            }
        }
        return info;
    }

    public String getInfoMonsKilled() {
        String info = "";
        if (this.monsterKilled != null) {
            info = String.valueOf(this.monsterKilled[0]);
            int i = 1;
            while (i < this.monsterKilled.length) {
                info = String.valueOf(info) + "," + this.monsterKilled[i];
                ++i;
            }
        }
        return info;
    }

    public void desTroy() {
        this.state_quest = (short)-1;
    }

    public void reset() {
        int i;
        this.state_quest = 0;
        if (this.nItem != null) {
            i = 0;
            while (i < this.nItem.length) {
                this.nItem[i] = 0;
                ++i;
            }
        }
        if (this.monsterKilled != null) {
            i = 0;
            while (i < this.monsterKilled.length) {
                this.monsterKilled[i] = 0;
                ++i;
            }
        }
    }

    public void addGift(Char p) {
        try {
            this.getTemplate().addGift(p);
            if (this.main_sub == 0 && this.id_quest == 7) {
                p.skill[0] = 1;
                p.sendMessage(MessageCreator.createMainCharInfoMessage((Char)p));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addGiftClan(Char p) {
        try {
            if (p.idClan == -1) {
                return;
            }
            InfoQuestTemplate info = this.getTemplate();
            NewClan clan = NewClan.getClan(p.idClan);
            NewClan.addXpClan(clan, info.exp);
            int pdevote = 40 + Map.r.nextInt(11);
            clan.pDevote += (long)pdevote;
            p.pointCongHienClan += pdevote;
            p.sendMessage(MessageCreator.createMainCharInfoMessage((Char)p));
            Database.instance.saveOrtherLog("", p.charname, String.valueOf(info.name) + "_" + info.exp, "qclan");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

