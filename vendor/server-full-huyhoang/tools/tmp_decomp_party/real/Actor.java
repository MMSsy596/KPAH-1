/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  real.Char
 *  real.Map
 */
package real;

import real.Char;
import real.Map;

public class Actor {
    public static final byte CAT_PLAYER = 0;
    public static final byte CAT_MONSTER = 1;
    public static final byte CAT_NPC = 2;
    public static final byte CAT_ITEM = 3;
    public static final byte CAT_POTION = 4;
    public static final byte CAT_PARTY = 5;
    public static final byte CAT_GEM_ITEM = 6;
    public static final byte CAT_SPECIAL_ITEM = 7;
    public static final byte ANIMAL_ITEM = 8;
    public static final byte CAT_SEED_ITEM = 9;
    public static final byte CAT_MY_GROUND = 10;
    public static final byte CAT_MY_TREE = 11;
    public static final byte CAT_PET = 12;
    public static final byte CAT_TREE = 13;
    public static final byte CAT_ITEM_QUEST = 14;
    public static final byte CAT_EXPLOTION = 126;
    public static final byte CAT_NOT_FOCUS = 127;
    public long time_drop = Long.MAX_VALUE;
    public int idPartyBoss = 0;
    private short type;
    public byte cat;
    public short id;
    public Map map;
    public long timeBeStune;
    public int x;
    public int y;
    public int region = 0;
    public boolean moved = true;
    public boolean isSelling;

    public Actor() {
    }

    public int getTypePk() {
        return -1;
    }

    public short getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = (short)type;
    }

    public Actor(byte catagory) {
        this.cat = catagory;
    }

    public boolean near(Actor p2, int distance) {
        if (p2 == null) {
            return false;
        }
        if (p2.region != this.region) {
            return false;
        }
        return Math.abs(this.x - p2.x) <= distance && Math.abs(this.y - p2.y) <= distance;
    }

    public boolean near(int x1, int y1, int x2, int y2, int distance) {
        return Math.abs(x1 - x2) <= distance && Math.abs(y1 - y2) <= distance;
    }

    public int getX(int inCountry) {
        return this.x;
    }

    public int getY(int inCountry) {
        return this.y;
    }

    public boolean haveBackDam() {
        return false;
    }

    public boolean haveBaoKich() {
        return false;
    }

    public boolean resistThroughArmor() {
        return false;
    }

    public boolean haveDodge() {
        return false;
    }

    public int getBackDam(int dam) {
        return 0;
    }

    public long[] getExpPetEatfromItem(Char p) {
        return new long[2];
    }

    public boolean isItemQuest() {
        return false;
    }

    public String getName() {
        return "";
    }

    public int getPcGiamSatThuong() {
        return 0;
    }

    public short getQuantity() {
        return 0;
    }

    public short getIcon() {
        return -1;
    }

    public int getTimeReborn() {
        return -1;
    }

    public boolean isMonsterVantieu() {
        return false;
    }

    public int getCharID() {
        return -1;
    }

    public boolean isRuongVanTieu() {
        return false;
    }

    public boolean isRuongMayMan() {
        return false;
    }

    public void setNguoiTuyet(int id) {
    }

    public boolean isCongThanh() {
        return false;
    }

    public void charKillBoss(Char p) {
    }

    public boolean isBossTruRong() {
        return false;
    }

    public void setTarget(Actor ac) {
    }

    public int getIDClan() {
        return -1;
    }

    public int getIdCharThanThu() {
        return -1;
    }

    public boolean canFocus(Char me) {
        return true;
    }

    public int getValueLua(int type) {
        return 0;
    }

    public void setCharcanBuy(String charname) {
    }

    public String getCharcanBuy() {
        return "";
    }

    public int getMinPriceSell() {
        return 10000;
    }

    public boolean isChoiSell() {
        return false;
    }

    public boolean isMyHoVe(Char p) {
        return false;
    }

    public boolean canSellTown() {
        return false;
    }
}

