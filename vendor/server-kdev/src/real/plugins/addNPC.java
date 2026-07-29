
package real.plugins;

import real.*;
import real.MessageCreator;

/**
 *
 * @author 5hquocme
 */
public class addNPC {

    static short[][] posNpcThanTai = new short[][]{{272, 352}, {224, 432}, {288, 480}};

    static short[][] posNpcSANTA = new short[][]{{880, 672}, {560, 816}, {560, 704}};

    static short[][] posNpcVuLan = new short[][]{{368, 608}, {288, 512}, {560, 704}};

    static short[][] posNpcCayLixi = new short[][]{{368, 512}, {288, 416}, {368, 512}};

    // static short[][] posNpcAdmin = new short[][]{{368, 608}, {288, 512}, {560, 704}};

    public static void addNPC(Char player) {
        // player.sendMessage(MessageCreator.createMsgNpc("Admin", posNpcVuLan[player.inCountry][0], posNpcVuLan[player.inCountry][1], 28, 31, 2, 20, -99, (byte) 1));
        if (Char.isSuKienTetduonglich2024()) {
            player.sendMessage(MessageCreator.createMsgNpc("Chúc mừng năm mới", posNpcVuLan[player.inCountry][0], posNpcVuLan[player.inCountry][1], 28, 31, 2, 14, -35, (byte) 1));
        }
         if (Char.isSuKienTet2017()) {
             player.sendMessage(MessageCreator.createMsgNpc("Nhà Vua", posNpcVuLan[player.inCountry][0], posNpcVuLan[player.inCountry][1], 28, 31, 2, 14, -49, (byte) 1));
         }
        if (Char.isSuKienGioTo2016()) {
             if (player.mapID == 0 || player.mapID == 301 || player.mapID == 302 || player.mapID == 303 || player.mapID == 304 || player.mapID == 70 || player.mapID == 1701 || player.mapID == 1702 || player.mapID == 1703 || player.mapID == 1704 || player.mapID == 80 || player.mapID == 1901 || player.mapID == 1902 || player.mapID == 1903 || player.mapID == 1904) {
                player.sendMessage(MessageCreator.createMsgNpc("Phù Thủy Dễ Thương", posNpcSANTA[player.inCountry][0], posNpcSANTA[player.inCountry][1], 28, 31, 2, 20, -49, (byte) 1));
            }
         }
         if (Char.isSuKienHe2017()) {
             if (player.mapID == 0 || player.mapID == 301 || player.mapID == 302 || player.mapID == 303 || player.mapID == 304 || player.mapID == 70 || player.mapID == 1701 || player.mapID == 1702 || player.mapID == 1703 || player.mapID == 1704 || player.mapID == 80 || player.mapID == 1901 || player.mapID == 1902 || player.mapID == 1903 || player.mapID == 1904) {
                player.sendMessage(MessageCreator.createMsgNpc("Mẹ hiền", posNpcVuLan[player.inCountry][0], posNpcVuLan[player.inCountry][1], 15, 31, 2, 20, -49, (byte) 1));
            }
         } 
        if (Char.isSuKienNoel2023()) {
            player.sendMessage(MessageCreator.createMsgNpc("Cây thông Noel", posNpcVuLan[player.inCountry][0], posNpcVuLan[player.inCountry][1], 86, 127, 2, 23, -36, (byte) 1));
            player.sendMessage(MessageCreator.createMsgNpc("ông Già Noel", posNpcSANTA[player.inCountry][0], posNpcSANTA[player.inCountry][1], 20, 31, 2, 24, -35, (byte) 1));
        }
        if (Char.isSuKienHaloween2016() && player.getLevel() >= 40) 
        {
            if (player.mapID == 0 || player.mapID == 301 || player.mapID == 302 || player.mapID == 303 || player.mapID == 304 || player.mapID == 70 || player.mapID == 1701 || player.mapID == 1702 || player.mapID == 1703 || player.mapID == 1704 || player.mapID == 80 || player.mapID == 1901 || player.mapID == 1902 || player.mapID == 1903 || player.mapID == 1904) {
                 player.sendMessage(MessageCreator.createMsgNpc("Phù Thủy Dễ Thương", posNpcVuLan[player.inCountry][0], posNpcVuLan[player.inCountry][1], 15, 31, 2, 20, -49, (byte) 1));
            }
        }
        if (Char.isSuKienTrungThul2016() && player.getLevel() >= 40) {
            if (player.mapID == 0 || player.mapID == 301 || player.mapID == 302 || player.mapID == 303 || player.mapID == 304 || player.mapID == 70 || player.mapID == 1701 || player.mapID == 1702 || player.mapID == 1703 || player.mapID == 1704 || player.mapID == 80 || player.mapID == 1901 || player.mapID == 1902 || player.mapID == 1903 || player.mapID == 1904) {
                player.sendMessage(MessageCreator.createMsgNpc("tien.nu", posNpcSANTA[player.inCountry][0], posNpcSANTA[player.inCountry][1], 15, 31, 2, 14, -49, (byte) 1));
            }
        }
    }
}
