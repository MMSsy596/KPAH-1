package real;

import data.Database;
import data.NewClan;

import java.sql.SQLException;
import java.util.Vector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class NpcReceiveCard {

    public int idClan = -1;
    public byte posNpc = 0;
    public Char charGive = null;
    public String nameClan = "";
    public short time = 0;
    public static byte[] x = new byte[]{40, 18, 61};
    public static byte[] y = new byte[]{15, 45, 45};
    public static String[] npc = new String[]{"Trần thống lĩnh", "Tả thống lĩnh", "Hữu thống lĩnh"};
    public byte inCountry = 0;
    static short timeGiveCard = 60;
    public static boolean[] isFinish = new boolean[3];
    public static String[] nameCountry = new String[]{"Thanh long", "Hắc hổ", "Huyền vũ"};

    public NpcReceiveCard(int pos, int country) {
        this.posNpc = (byte) pos;
        this.inCountry = (byte) country;
    }

    public boolean giveCard(Char p, boolean isClanTown) {
        int posNpc = this.checkInRange(p.x / 16, p.y / 16);
        if (posNpc == -1) {
            return false;
        } else if (this.charGive != null) {
            return false;
        } else if (p.idClan == -1) {
            return false;
        } else if (this.idClan == p.idClan) {
            return false;
        } else {
            this.time = timeGiveCard;
            this.charGive = p;
            p.timeGiveCardTown = System.currentTimeMillis();
            Database.instance.saveOrtherLog("", p.charname, p.idClan + " x= " + p.x / 16 + " y= " + p.y / 16 + " mid= " + p.mapID + " posnpc= " + this.posNpc + " " + Char.getDayTime(0L), "stGive");
            return true;
        }
    }

    private int checkInRange(int xChar, int yChar) {
        return xChar >= x[this.posNpc] - 3 && xChar <= x[this.posNpc] + 3 && yChar >= y[this.posNpc] - 3 && yChar <= y[this.posNpc] + 3 ? 1 : -1;
    }

    public void update() {
        try {
            NewClan cinfo;
            if (this.charGive != null && this.charGive.hp > 0 && this.charGive.timeGiveCardTown > 0L) {
                if (System.currentTimeMillis() - this.charGive.timeGiveCardTown > (long) (timeGiveCard * 1000)) {
                    this.idClan = this.charGive.idClan;
                    Map mapok = this.charGive.map;
                    cinfo = Map.getClaninfoByID(this.idClan);
                    this.nameClan = cinfo.name;
                    this.charGive.map.doSend2AllChar(MessageCreator.createServerAlertAutoOffMessage("Bang " + this.nameClan + " lãnh thổ " + nameCountry[this.inCountry] + " đã giao thẻ thành công tại " + npc[this.posNpc]), this.inCountry);
                    String charname = this.charGive.charname;
                    this.charGive.timeGiveCardTown = 0L;

                    try {
                        Database.instance.saveLogClan(charname + "_" + this.idClan, "gcok", "Giao the ok tai " + npc[this.posNpc] + " " + Char.getDayTime(0L));
                        this.charGive.map.doSend2AllChar(MessageCreator.createMsgStartGetTown(this.inCountry), this.inCountry);
                    } catch (Exception var8) {
                    }

                    NpcReceiveCard npc1 = (NpcReceiveCard) ((Vector) Map.npcReceiveCard.get(this.inCountry)).get(0);
                    NpcReceiveCard npc2 = (NpcReceiveCard) ((Vector) Map.npcReceiveCard.get(this.inCountry)).get(1);
                    NpcReceiveCard npc3 = (NpcReceiveCard) ((Vector) Map.npcReceiveCard.get(this.inCountry)).get(2);
                    if (npc1.idClan == npc2.idClan && npc1.idClan == npc3.idClan && npc1.idClan != -1) {
                        this.charGive.map.curday[this.inCountry] = "";
                        this.charGive.timeGiveCardTown = 0L;
                        Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage("Chúc mừng bang " + this.nameClan.toUpperCase() + " thuộc lãnh thổ " + nameCountry[this.inCountry] + " đã chiếm được thành."));

//                        String tenBangChu = cinfo.master;
//                        Char bangChu = CharManager.instance.getCharByCharName(tenBangChu);
//                        if (bangChu != null) {
//                            // cho quà
//                            bangChu.addDanhHieu(8935, 4320L); // danh hiệu vinh danh bang hội 3đ
//                            long soLuongXu = 200_000_000;
//                            bangChu.addXu(soLuongXu, "map");
//                            Database.instance.addCharWinThanh(cinfo.name, bangChu, 1, soLuongXu);
//                            bangChu.sendMessage(MessageCreator.createServerAlertMessage("chúc mừng bạn nhận được 200tr xu, vì là chủ bang.", ""));
//                            bangChu.sendMessage(MessageCreator.createCharInventoryMessage(bangChu, 0));
//                        }
                         // Update town status in database
    //                      try {
    // // First verify the clan exists and can receive town status
    //                         String checkClanSQL = "SELECT id_clan FROM tob_clan WHERE id_clan = ? AND town = 0";
    //                         Connection conn = Database.instance.getConnection();
                            
    //                         // Use PreparedStatement to prevent SQL injection
    //                         PreparedStatement checkStmt = conn.prepareStatement(checkClanSQL);
    //                         checkStmt.setInt(1, this.idClan);
    //                         ResultSet rs = checkStmt.executeQuery();
                            
    //                         if (rs.next()) {
    //                             // Clan exists and doesn't already have town status
    //                             String updateTownSQL = "UPDATE tob_clan SET town = 1, last_update_time = ? WHERE id_clan = ?";
    //                             PreparedStatement updateStmt = conn.prepareStatement(updateTownSQL);
    //                             updateStmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
    //                             updateStmt.setInt(2, this.idClan);
                                
    //                             int updated = updateStmt.executeUpdate();
    //                             if (updated != 1) {
    //                                 throw new SQLException("Failed to update clan town status");
    //                             }
                                
    //                             // Log the successful update
    //                             Database.instance.saveLogClan(this.nameClan, "town_update", 
    //                                 "Clan received town status at " + Char.getDayTime(0L));
    //                         }
                           
                            
    //                         // Close resources
    //                         rs.close();
    //                         checkStmt.close();
    //                         conn.close();
                            
    //                     } catch (SQLException e) {
    //                         e.printStackTrace();
    //                         // Handle the error appropriately
    //                         Database.instance.saveLogClan(this.nameClan, "town_update_error", 
    //                             "Failed to update town status: " + e.getMessage());
    //                     }
                        if (npc1.charGive != null) {
                            npc1.charGive.timeGiveCardTown = 0L;
                        }

                        if (npc2.charGive != null) {
                            npc2.charGive.timeGiveCardTown = 0L;
                        }

                        if (npc3.charGive != null) {
                            npc3.charGive.timeGiveCardTown = 0L;
                        }

                        npc1.charGive = null;
                        npc2.charGive = null;
                        npc3.charGive = null;
                        Map.idClanTown[this.inCountry] = (short) this.idClan;
                        if (cinfo != null) {
                            cinfo.giveGif();
                        }

                        NewClan.updateTown(this.idClan, 1, this.inCountry);
                        cinfo.updateNewClandata2DB();
                        Database.instance.saveLogClan(this.nameClan, "cgt", "đã chiếm thành " + Char.getDayTime(0L));
                        isFinish[this.inCountry] = true;
                        Map.getTown[this.inCountry] = false;
                        Char pmaster = CharManager.instance.getCharByCharName(cinfo.master.toLowerCase());
                        if (pmaster != null) {
                            pmaster.rankGov = 0;
                            Char.gov[pmaster.myCountry].king = pmaster.charname;
                            pmaster.calculateAttrib();
                            pmaster.sendMessage(MessageCreator.createMainCharInfoMessage(pmaster));
                            pmaster.sendMessage(MessageCreator.createCharWearingMessage(pmaster, pmaster));
                            pmaster.sendToNearPlayer(MessageCreator.createCharInfo(pmaster));
                            pmaster.sendToNearPlayer(MessageCreator.createCharWearingMessage(pmaster, pmaster));
                            MessageCreator.createMsgCharMonster(pmaster, pmaster);
                        }
                    }

                    mapok.sendAllPlayer(MessageCreator.createMsgStartGetTown(this.inCountry), this.inCountry);
                    this.time = 0;
                    this.charGive = null;
                } else {
                    this.time = (short) ((int) ((long) timeGiveCard - (System.currentTimeMillis() - this.charGive.timeGiveCardTown) / 1000L));
                    if (this.checkInRange(this.charGive.x / 16, this.charGive.y / 16) == -1 || this.charGive.mapID != Map.idMapTown || this.charGive.myCountry != this.charGive.inCountry) {
                        Database.instance.saveOrtherLog("", this.charGive.charname, this.charGive.idClan + " x= " + this.charGive.x / 16 + " y= " + this.charGive.y / 16 + " mid= " + this.charGive.mapID + " posnpc= " + this.posNpc + " > " + this.charGive.inCountry, "ngoairange");
                        this.charGive.timeGiveCardTown = 0L;
                        cinfo = Map.getClaninfoByID(this.charGive.idClan);
                        this.charGive.map.doSend2AllChar(MessageCreator.createServerAlertAutoOffMessage("Bang " + cinfo.name + " giao thẻ thất bại"), this.charGive.myCountry);
                        this.time = 0;
                        this.charGive.map.sendAllPlayer(MessageCreator.createMsgStartGetTown(this.inCountry), this.inCountry);
                        this.charGive = null;
                    }
                }
            } else if (this.charGive != null && (this.charGive.hp <= 0 || this.charGive.mapID != Map.idMapTown || this.charGive.myCountry != this.charGive.inCountry)) {
                this.charGive.timeGiveCardTown = 0L;
                Database.instance.saveOrtherLog("", this.charGive.charname, this.charGive.idClan + " x= " + this.charGive.x / 16 + " y= " + this.charGive.y / 16 + " mid= " + this.charGive.mapID + " posnpc= " + this.posNpc + " > " + this.charGive.inCountry, "ngoairange1");
                cinfo = Map.getClaninfoByID(this.charGive.idClan);
                this.charGive.map.doSend2AllChar(MessageCreator.createServerAlertAutoOffMessage("Bang " + cinfo.name + " giao thẻ thất bại"), this.charGive.myCountry);
                this.time = 0;
                this.charGive.map.sendAllPlayer(MessageCreator.createMsgStartGetTown(this.inCountry), this.inCountry);
                this.charGive = null;
            }
        } catch (Exception var9) {
            var9.printStackTrace();
        }

    }

    public String getNameCharGive() {
        return this.charGive == null ? "" : this.charGive.charname;
    }

    public short getIDCharGive() {
        return this.charGive == null ? 32000 : this.charGive.id;
    }

    public String getNameNpc() {
        return npc[this.posNpc];
    }
}
