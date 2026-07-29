package real.plugins;

import real.Char;
import real.MessageCreator;

public class addNPC {

    static short[][] posNpcThanTai = new short[][]{{272, 352}, {224, 432}, {288, 480}};
    static short[][] posNpcSANTA = new short[][]{{880, 672}, {560, 816}, {560, 704}};
    static short[][] posNpcVuLan = new short[][]{{368, 608}, {288, 512}, {560, 704}};
    static short[][] posNpcCayLixi = new short[][]{{368, 512}, {288, 416}, {368, 512}};

    public static void addNPC(Char player) {
        if (Char.isSuKienTetduonglich2024()) {
            player.sendMessage(MessageCreator.createMsgNpc("Chuc mung nam moi", posNpcVuLan[player.inCountry][0], posNpcVuLan[player.inCountry][1], 28, 31, 2, 14, -35, (byte) 1));
        }
        if (Char.isSuKienTet2017()) {
            player.sendMessage(MessageCreator.createMsgNpc("Nha Vua", posNpcVuLan[player.inCountry][0], posNpcVuLan[player.inCountry][1], 28, 31, 2, 14, -49, (byte) 1));
        } else if (Char.isSuKienGioTo2016()) {
            if (player.mapID == 0 || player.mapID == 301 || player.mapID == 302 || player.mapID == 303 || player.mapID == 304 || player.mapID == 70 || player.mapID == 1701 || player.mapID == 1702 || player.mapID == 1703 || player.mapID == 1704 || player.mapID == 80 || player.mapID == 1901 || player.mapID == 1902 || player.mapID == 1903 || player.mapID == 1904) {
                player.sendMessage(MessageCreator.createMsgNpc("Phu Thuy De Thuong", posNpcSANTA[player.inCountry][0], posNpcSANTA[player.inCountry][1], 28, 31, 2, 20, -49, (byte) 1));
            }
        } else if (Char.isSuKienHe2017()) {
            if (player.mapID == 0 || player.mapID == 301 || player.mapID == 302 || player.mapID == 303 || player.mapID == 304 || player.mapID == 70 || player.mapID == 1701 || player.mapID == 1702 || player.mapID == 1703 || player.mapID == 1704 || player.mapID == 80 || player.mapID == 1901 || player.mapID == 1902 || player.mapID == 1903 || player.mapID == 1904) {
                player.sendMessage(MessageCreator.createMsgNpc("Me hien", posNpcVuLan[player.inCountry][0], posNpcVuLan[player.inCountry][1], 15, 31, 2, 20, -49, (byte) 1));
            }
        }
        if (Char.isSuKienNoel2023()) {
            player.sendMessage(MessageCreator.createMsgNpc("Cay thong Noel", posNpcVuLan[player.inCountry][0], posNpcVuLan[player.inCountry][1], 86, 127, 2, 23, -36, (byte) 1));
            player.sendMessage(MessageCreator.createMsgNpc("Ong Gia Noel", posNpcSANTA[player.inCountry][0], posNpcSANTA[player.inCountry][1], 20, 31, 2, 24, -35, (byte) 1));
        }
        if (Char.isSuKienHaloween2016() && player.getLevel() >= 40) {
            if (player.mapID == 0 || player.mapID == 301 || player.mapID == 302 || player.mapID == 303 || player.mapID == 304 || player.mapID == 70 || player.mapID == 1701 || player.mapID == 1702 || player.mapID == 1703 || player.mapID == 1704 || player.mapID == 80 || player.mapID == 1901 || player.mapID == 1902 || player.mapID == 1903 || player.mapID == 1904) {
                player.sendMessage(MessageCreator.createMsgNpc("Phu Thuy De Thuong", posNpcVuLan[player.inCountry][0], posNpcVuLan[player.inCountry][1], 15, 31, 2, 20, -49, (byte) 1));
            }
        }
        if (Char.isSuKienTrungThul2016() && player.getLevel() >= 40) {
            if (player.mapID == 0 || player.mapID == 301 || player.mapID == 302 || player.mapID == 303 || player.mapID == 304 || player.mapID == 70 || player.mapID == 1701 || player.mapID == 1702 || player.mapID == 1703 || player.mapID == 1704 || player.mapID == 80 || player.mapID == 1901 || player.mapID == 1902 || player.mapID == 1903 || player.mapID == 1904) {
                player.sendMessage(MessageCreator.createMsgNpc("tien.nu", posNpcSANTA[player.inCountry][0], posNpcSANTA[player.inCountry][1], 15, 31, 2, 14, -49, (byte) 1));
            }
        }
    }
}
