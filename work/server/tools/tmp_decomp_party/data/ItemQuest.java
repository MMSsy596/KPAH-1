/*
 * Decompiled with CFR 0.152.
 */
package data;

import real.Actor;

public class ItemQuest
extends Actor {
    public static String[] NAME_ITEM_QUEST = new String[]{"N\u1ecdc b\u1ecd c\u1ea1p", "R\u1eafn", "Kh\u00fac g\u1ed7", "R\u1ebft \u0111\u1ecf", "L\u00e1 th\u01b0", "Th\u1ecbt heo", "L\u00f4ng \u0111u\u00f4i H\u1ed3ng K\u00ea", "M\u00f3ng c\u00e1 s\u1ea5u", "Da \u1ebfch", "C\u1ef1a g\u00e0"};
    public static byte[] ICON_IMAGE;
    String name = "";
    public int belongUser = 0;
    public short quantity;
    private short icon;
    public int id_char;

    static {
        byte[] byArray = new byte[10];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 3;
        byArray[4] = 4;
        byArray[5] = 5;
        byArray[6] = 6;
        byArray[7] = 7;
        byArray[8] = 8;
        byArray[9] = 9;
        ICON_IMAGE = byArray;
    }

    public ItemQuest(short id, int quantity) {
        super((byte)14);
        this.setType(id);
        this.name = NAME_ITEM_QUEST[id];
        this.quantity = (short)quantity;
        this.icon = id;
    }

    @Override
    public boolean isItemQuest() {
        return true;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public short getQuantity() {
        return this.quantity;
    }

    @Override
    public short getIcon() {
        return this.icon;
    }
}

