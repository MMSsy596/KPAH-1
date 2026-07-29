/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 *  real.Char
 *  real.Item
 */
package real;

import io.Message;
import java.util.Hashtable;
import java.util.Vector;
import real.Char;
import real.CharSellVip;
import real.InfoItemAttribute;
import real.Item;

public class ShopVip {
    public static Hashtable<Integer, CharSellVip> charSeller = new Hashtable();
    public static Vector<Vector<Vector<Item>>> ALL_ITEM_SELL;
    static byte[] coloritem;

    static {
        int i = 0;
        while (i < 20) {
            Vector coloritem = new Vector();
            int j = 0;
            while (j < 4) {
                coloritem.add(new Vector());
                ++j;
            }
            ALL_ITEM_SELL.add(coloritem);
            ++i;
        }
        byte[] byArray = new byte[4];
        byArray[1] = 3;
        byArray[2] = 2;
        byArray[3] = 1;
        coloritem = byArray;
    }

    public static void doSellItemVip(Char player, Message msg) {
        try {
            short idItem = msg.dis.readShort();
            int price = msg.dis.readInt();
            if (price < 0) {
                return;
            }
            Item item = player.getItemFormVector(player.iItems, idItem);
            if (item.lock == 1) {
                return;
            }
            if (item != null && !item.isSelling) {
                item.myChar = player.charname;
                item.isSelling = true;
                item.prizeSell = price;
                ALL_ITEM_SELL.get(item.getTemplate().type).get(item.colorName).add(item);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void doSearchItem(Char player, Message msg) {
        try {
            player.allItemBuy.removeAllElements();
            byte type = msg.dis.readByte();
            short level = msg.dis.readShort();
            byte pham = msg.dis.readByte();
            byte colorItem = msg.dis.readByte();
            Vector<Item> it = ALL_ITEM_SELL.get(type).get(coloritem[colorItem]);
            int i = 0;
            while (i < it.size()) {
                Item item = it.get(i);
                if (item.level == level && (item.hangItem == pham || colorItem == 0 && item.hangItem == -1)) {
                    player.allItemBuy.add(item);
                }
                ++i;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void doSendListItem(Char p, int idStart) {
        try {
            int size = 10;
            if (idStart > p.allItemBuy.size()) {
                idStart = 0;
            }
            Message msg = new Message(-58);
            msg.dos.writeByte(1);
            if (size + idStart > p.allItemBuy.size()) {
                size = p.allItemBuy.size() - idStart;
            }
            msg.dos.writeShort(size);
            int i = idStart;
            while (i < p.allItemBuy.size()) {
                Item it = (Item)p.allItemBuy.get(i);
                msg.dos.writeByte(it.clazz);
                msg.dos.writeShort(it.id);
                msg.dos.writeShort(it.getTemplate().id);
                msg.dos.writeByte(it.plus);
                msg.dos.writeInt(it.prizeSell);
                msg.dos.writeByte(it.level);
                msg.dos.writeByte(it.nhadSock);
                msg.dos.writeByte(it.nSocAdd);
                Vector allAtb = it.getInfoAtbItem();
                msg.dos.writeByte(allAtb.size());
                int j = 0;
                while (j < allAtb.size()) {
                    InfoItemAttribute info = (InfoItemAttribute)allAtb.get(j);
                    msg.dos.writeByte(info.id);
                    msg.dos.writeShort(info.value);
                    j = (byte)(j + 1);
                }
                msg.dos.writeByte(it.colorName);
                msg.dos.writeByte(it.heItem);
                msg.dos.writeByte(it.hangItem);
                msg.dos.writeByte(it.magic_physic);
                msg.dos.writeUTF(it.myChar);
                ++i;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

