package real;

import static real.Map.posNpcCayLixi;
import static real.Map.posNpcSANTA;
import static real.Map.posNpcThanTai;
import static real.Map.posNpcVuLan;
import server.TeamServer;

/**
 *
 * @author TOM
 */
public class sendInfoNpc {

    public static void sendInfoNpc(Char player) {
        try {
            if ((player.mapID == 0 || player.mapID == 301 || player.mapID == 302 || player.mapID == 303 || player.mapID == 304 || player.mapID == 70 || player.mapID == 1701 || player.mapID == 1702 || player.mapID == 1703 || player.mapID == 1704 || player.mapID == 80 || player.mapID == 1901 || player.mapID == 1902 || player.mapID == 1903 || player.mapID == 1904) && !TeamServer.isServerIndo()) {
                player.sendMessage(MessageCreator.createMsgNpc("Thần tài", posNpcThanTai[player.inCountry][0], posNpcThanTai[player.inCountry][1], 28, 31, 2, 14, -24, (byte) 1));
                if (!Char.isFinishAllSuKienHe2017()) {
                    player.sendMessage(MessageCreator.createMsgNpc("Mẹ hiền", posNpcVuLan[player.inCountry][0], posNpcVuLan[player.inCountry][1], 15, 31, 2, 20, -49, (byte) 1));
                }

                if (!Char.isFinishAllSuKienTrungThul2016()) {
                    player.sendMessage(MessageCreator.createMsgNpc("Lồng đèn kéo quân", posNpcVuLan[player.inCountry][0], posNpcVuLan[player.inCountry][1], 80, 120, 3, 21, -65, (byte) 1));
                }

                if (!Char.isFinishAllSuKienNoel()) {
                    player.sendMessage(MessageCreator.createMsgNpc("Cây thông Noel", posNpcVuLan[player.inCountry][0], posNpcVuLan[player.inCountry][1], 86, 127, 2, 23, -65, (byte) 1));
                    player.sendMessage(MessageCreator.createMsgNpc("ông Già Noel", posNpcSANTA[player.inCountry][0], posNpcSANTA[player.inCountry][1], 20, 31, 2, 24, -49, (byte) 1));
                }

                if (!Char.isFinishAllSuKienTet2017()) {
                    player.sendMessage(MessageCreator.createMsgNpc("Nồi bánh chưng", posNpcVuLan[player.inCountry][0] - 30, posNpcVuLan[player.inCountry][1], 50, 60, 3, 25, -65, (byte) 1));
                    player.sendMessage(MessageCreator.createMsgNpc("Nồi bánh tét", posNpcVuLan[player.inCountry][0] + 30, posNpcVuLan[player.inCountry][1], 50, 60, 3, 26, -66, (byte) 1));
                }

                if (Char.isSuKienTet2017()) {
                    if (player.inCountry == 0) {
                        player.sendMessage(MessageCreator.createMsgNpc("Cây lì xì", posNpcCayLixi[player.inCountry][0], posNpcCayLixi[player.inCountry][1], 82, 102, 3, 29, -67, (byte) 1));
                    } else {
                        player.sendMessage(MessageCreator.createMsgNpc("Cây lì xì", posNpcCayLixi[player.inCountry][0], posNpcCayLixi[player.inCountry][1], 77, 96, 1, 30, -67, (byte) 1));
                    }
                }
            }

            if (player.mapID == 118) {
                player.sendMessage(MessageCreator.createMsgNpc("Cổng dịch chuyển", 736, 640, 180, 180, 3, 13, -22, (byte) 1));
            }

            if (player.map.nRegion > 0 && player.map.pRegion != null) {
                player.sendMessage(MessageCreator.createMsgNpc("Khu " + (player.region + 1), player.map.pRegion[0] * 16, player.map.pRegion[1] * 16, 27, 34, 1, 15, -34, (byte) 1));
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }
}
