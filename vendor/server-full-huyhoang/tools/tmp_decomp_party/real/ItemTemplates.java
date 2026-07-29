/*
 * Decompiled with CFR 0.152.
 */
package real;

import java.util.Vector;
import real.NameAttributeItem;

public class ItemTemplates {
    public static final byte TYPE_AO = 0;
    public static final byte TYPE_QUAN = 1;
    public static final byte TYPE_NON = 2;
    public static final byte TYPE_KIEM = 3;
    public static final byte TYPE_DAO = 4;
    public static final byte TYPE_DUA = 5;
    public static final byte TYPE_BUA = 6;
    public static final byte TYPE_CUNG = 7;
    public static final byte TYPE_QUEST = 8;
    public int id;
    public String name;
    public byte type;
    public byte style;
    public byte gender;
    public byte he;
    public byte level;
    public byte ystart;
    public byte color;
    public short durable;
    public short idIcon;
    public short idItemUpLevel = (short)-1;
    public short idBigImgAvatar = (short)-1;
    public short[] atb = new short[10];
    public byte droppercent;
    public int price;
    public int idEff = -1;
    public byte clazz;
    public int kind_class;
    public short ndayLoan = 0;
    public static Vector<NameAttributeItem> ALL_NAME_ATTRIBUTE_ITEM = new Vector();
    public static final String[] ALL_NAME_ATTRIBUTE = new String[]{"T\u1ea5n c\u00f4ng", "Th\u1ee7 v\u1eadt", "N\u00e9 tr\u00e1nh", "Ch\u00ednh x\u00e1c", "Ch\u00ed m\u1ea1ng", "S\u1ee9c kh\u1ecfe", "Th\u1ee7 ma", "ch\u1ec9 s\u1ed1 7", "ch\u1ec9 s\u1ed1 8", "ch\u1ec9 s\u1ed1 9", "T\u0103ng s\u1ee9c m\u1ea1nh", "T\u0103ng nhanh nh\u1eb9n", "t\u0103ng tinh th\u1ea7n", "T\u0103ng s\u1ee9c kho\u1ebb", "G\u00e2y m\u00f9", "\u0110\u00f3ng b\u0103ng", "Tr\u00fang \u0111\u1ed9c", "G\u00e2y cho\u00e1ng", "Ho\u00e1 th\u1ea1ch", "Gi\u1ea3m t\u1ed1c", "Kh\u00e1ng gi\u1ea3m t\u1ed1c", "Kh\u00e1ng tr\u00fang \u0111\u1ed9c", "Kh\u00e1ng g\u00e2y m\u00f9", "Kh\u00e1ng \u0111\u00f3ng b\u0103ng", "Kh\u00e1ng g\u00e2y cho\u00e1ng", "Kh\u00e1ng ho\u00e1 th\u1ea1ch", "T\u0103ng x2 m\u1ed7i l\u1ea7n \u0111\u00e1nh", "T\u0103ng t\u1ef7 l\u1ec7 r\u1edbt b\u1ea3o v\u1eadt", "Gi\u1ea3m st v\u1eadt", "Gi\u1ea3m st ma", "T\u0103ng t\u1ea5n c\u00f4ng", "Xuy\u00ean gi\u00e1p", "Ph\u1ea3n s\u00e1t th\u01b0\u01a1ng", "T\u0103ng mp", "T\u0103ng hp", "C\u1ed9ng s\u1ee9c m\u1ea1nh", "C\u1ed9ng kh\u00e9o l\u00e9o", "C\u1ed9ng tinh th\u1ea7n", "C\u1ed9ng s\u1ee9c kho\u1ebb", "C\u1ed9ng k\u1ef9 n\u0103ng", "T\u0103ng ch\u00ed m\u1ea1ng", "T\u0103ng st ch\u00ed m\u1ea1ng", "ch\u01b0a d\u00f9ng", "k\u1ef9 n\u0103ng 1 c\u1ed9ng th\u00eam", "k\u1ef9 n\u0103ng 2 c\u1ed9ng th\u00eam", "k\u1ef9 n\u0103ng 3 c\u1ed9ng th\u00eam", "k\u1ef9 n\u0103ng 4 c\u1ed9ng th\u00eam", "k\u1ef9 n\u0103ng 5 c\u1ed9ng th\u00eam", "k\u1ef9 n\u0103ng 6 c\u1ed9ng th\u00eam", "k\u1ef9 n\u0103ng 7 c\u1ed9ng th\u00eam", "k\u1ef9 n\u0103ng 8 c\u1ed9ng th\u00eam", "k\u1ef9 n\u0103ng 9 c\u1ed9ng th\u00eam", "k\u1ef9 n\u0103ng 10 c\u1ed9ng th\u00eam", "k\u1ef9 n\u0103ng 11 c\u1ed9ng th\u00eam", "k\u1ef9 n\u0103ng 12 c\u1ed9ng th\u00eam", "k\u1ef9 n\u0103ng 13 c\u1ed9ng th\u00eam", "k\u1ef9 n\u0103ng 14 c\u1ed9ng th\u00eam", "k\u1ef9 n\u0103ng 15 c\u1ed9ng th\u00eam", "T\u0103ng c\u00f4ng", "T\u0103ng th\u1ee7 ma", "T\u0103ng th\u1ee7 v\u1eadt", "B\u1ecf qua tc ma", "B\u1ecf qua tc v\u1eadt"};
}

