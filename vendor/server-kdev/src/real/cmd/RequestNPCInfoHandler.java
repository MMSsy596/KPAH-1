// 
// Decompiled by Procyon v0.6.0
// 
package real.cmd;

import java.io.IOException;
import real.CharChienTruong;
import real.Item;
import data.Animal;
import data.Database;
import java.util.Calendar;
import data.NewClan;
import real.MapChienTruongMoba;
import data.Logdata;
import data.AdvInfo;
import real.LiveActor;
import real.Map;
import data.CharCooking;
import real.MessageCreator;
import real.Char;
import real.CharManager;
import server.TeamServer;
import io.Message;
import io.Session;
import static real.Char.ID_MAT_NA;
import static real.Char.getDayTime;
import real.GemTemplate;

import real.plugins.GiftCode;

public class RequestNPCInfoHandler implements ICommandHandler {

    public static int[] idMapLoad;
    public static int[][] xyNPC;
    public static int[][] posHoaTieu;
    public static int[][] IDMAP_HOA_TIEU;
    public static short[][] ID_MATERIAL_LOW;
    public static short[][] ID_MATERIAL_HIGHT;
    public static int[][] ID_TU_BINH;
    public static short[] ID_NGOC_RONG;
    public static short[] ID_XUONG;
    public static short[] ID_BOT;
    public static short[] ID_DUC;
    public static short[][] ID_DA_NGU_HOP;
    public static short[] ID_NGOC_HUYEN_MINH;

    static {
        GemTemplate.TYPE_IMBUE = 0;
        GemTemplate.TYPE_KHAM = 1;
        GemTemplate.TYPE_DUC_LO = 2;
        GemTemplate.TYPE_CREATE_ITEM = 3;
        GemTemplate.TYPE_TU_BINH = 4;
        GemTemplate.TYPE_HOP_ITEM_ANIMAL = 5;
        GemTemplate.TYPE_ADD_NEW_ATTRIBUTE = 6;
        GemTemplate.TYPE_LOCK_ANIMAL = 7;
        GemTemplate.TYPE_LOCK_ITEM = 8;
        GemTemplate.TYPE_BOT = 9;
        GemTemplate.TYPE_DA_TIEN_GIAI = 10;
        GemTemplate.TYPE_UNKNOW = 11;
        GemTemplate.TYPE_UNKNOW1 = 12;
        GemTemplate.LKD_30 = 11;
        GemTemplate.LKD_35 = 66;
        GemTemplate.LKD_40 = 245;
        GemTemplate.DA_TIEN_GIAI = 250;
        GemTemplate.BOT_XANH_LA = 249;
        GemTemplate.BOT_TRANG = 246;
        GemTemplate.BOT_XANH = 247;
        GemTemplate.BOT_DO = 248;
        GemTemplate.DUC_TUONG_KHAM_1 = 60;
        GemTemplate.DUC_TUONG_KHAM_2 = 61;
        GemTemplate.DUC_TUONG_KHAM_3 = 62;
        GemTemplate.DUC_TUONG_KHAM_4 = 63;
        GemTemplate.DUC_TUONG_KHAM_5 = 64;
        GemTemplate.DUC_TUONG_KHAM_6 = 65;
        GemTemplate.DA_MAY_MAN_C3 = 7;
        GemTemplate.DA_MAY_MAN_C4 = 155;
        GemTemplate.DA_MAY_MAN_C5 = 156;
        GemTemplate.XUONG_C6 = 234;
        GemTemplate.XUONG_C5 = 233;
        GemTemplate.XUONG_C4 = 232;
        GemTemplate.XUONG_C3 = 231;
        GemTemplate.XUONG_C2 = 230;
        GemTemplate.XUONG_C1 = 229;
        GemTemplate.VAI_C1 = 68;
        GemTemplate.SAT_C1 = 75;
        GemTemplate.NGOC_C1 = 82;
        GemTemplate.GO_THUONG_C1 = 89;
        GemTemplate.DA_MEM_C1 = 96;
        GemTemplate.DA_TACH_NL_DAC_BIET = 178;
        GemTemplate.NGOC_HUYEN_MINH_1 = 235;
        GemTemplate.NGOC_HUYEN_MINH_2 = 236;
        GemTemplate.NGOC_HUYEN_MINH_3 = 237;
        GemTemplate.NGOC_HUYEN_MINH_4 = 238;
        GemTemplate.NGOC_HUYEN_MINH_5 = 239;
        GemTemplate.NGOC_HUYEN_MINH_6 = 240;
        GemTemplate.DA_NGU_HOP_4 = 152;
        GemTemplate.DA_NGU_HOP_5 = 153;
        GemTemplate.DA_NGU_HOP_6 = 154;
        GemTemplate.DA_THUOC_TINH_05 = 228;
        GemTemplate.GIAY_PHAO = 251;
        GemTemplate.THUOC_PHAO = 252;
        GemTemplate.MANG_CAU = 253;
        GemTemplate.SUNG = 254;
        GemTemplate.DUA = 255;
        GemTemplate.DU_DU = 256;
        GemTemplate.XOAI = 257;
        GemTemplate.HOA_THACH_C1 = 258;
        GemTemplate.HOA_THACH_C2 = 259;
        GemTemplate.NGOC_1s = 260;
        GemTemplate.NGOC_2s = 261;
        GemTemplate.NGOC_3s = 262;
        GemTemplate.NGOC_4s = 263;
        GemTemplate.NGOC_5s = 264;
        GemTemplate.NGOC_6s = 265;
        GemTemplate.NGOC_7s = 266;
        GemTemplate.BAN_DO_KHO_BAU = 267;
        GemTemplate.ID_MATERIAL_LOW = new short[][]{{68, 75, 82, 89, 96}, {69, 76, 83, 90, 97}, {70, 77, 84, 91, 98}, {71, 78, 85, 92, 99}, {72, 79, 86, 93, 100}, {73, 80, 87, 94, 101}};
        GemTemplate.ID_MATERIAL_HIGHT = new short[][]{{103, 110, 117, 124, 131}, {104, 111, 118, 125, 132}, {105, 112, 119, 126, 133}, {106, 113, 120, 127, 134}, {107, 114, 121, 128, 135}, {108, 115, 122, 129, 136}};
        GemTemplate.ID_TU_BINH = new int[][]{{159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174}, {179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194}, {195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210}, {211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226}};
        GemTemplate.ID_NGOC_RONG = new short[]{GemTemplate.NGOC_1s, GemTemplate.NGOC_2s, GemTemplate.NGOC_3s, GemTemplate.NGOC_4s, GemTemplate.NGOC_5s, GemTemplate.NGOC_6s, GemTemplate.NGOC_7s};
        GemTemplate.ID_XUONG = new short[]{229, 230, 231, 232, 233, 234};
        GemTemplate.ID_BOT = new short[]{246, 247, 248, 249};
        GemTemplate.ID_DUC = new short[]{60, 61, 62, 63, 64, 65, 66};
        GemTemplate.ID_DA_NGU_HOP = new short[][]{{137, 138, 139, 140, 141, 142}, {143, 144, 145, 146, 147, 148}, {149, 150, 151, 152, 153, 154}};
        GemTemplate.ID_NGOC_HUYEN_MINH = new short[]{235, 236, 237, 238, 239, 240};
        RequestNPCInfoHandler.idMapLoad = new int[]{11, 13, 14, 15, 18, 19, 20, 23, 24, 10, 114, 5, 25};
        RequestNPCInfoHandler.xyNPC = new int[][]{{68, 63}, {12, 48}, {30, 6}, {9, 19}, {44, 54}, {55, 53}, {8, 42}, {47, 5}, {21, 5}, {71, 123}, {64, 8}, {44, 41}, {68, 63}, {68, 63}, {68, 63}};
        RequestNPCInfoHandler.posHoaTieu = new int[][]{{71, 123, 9, 19, 12, 48, 55, 53}, {9, 19, 8, 42, 47, 5, 44, 54, 64, 8}, {68, 63, 55, 53}, {71, 123, 44, 54}, {9, 19, 44, 54, 71, 123, 68, 63}, {30, 6, 68, 63, 21, 5, 12, 48, 64, 8}, {71, 123, 47, 5, 9, 19, 8, 42}, {71, 123, 64, 8, 44, 54, 8, 42}, {12, 48, 55, 53}, {68, 63, 30, 6, 55, 53, 64, 8}, {12, 48, 30, 6, 55, 53, 21, 5, 8, 42}};
        RequestNPCInfoHandler.IDMAP_HOA_TIEU = new int[][]{{10, 15, 13, 19}, {15, 20, 23, 18, 114}, {11, 19}, {10, 18}, {15, 18, 10, 11}, {14, 11, 24, 13, 114}, {10, 23, 15, 20}, {10, 114, 18, 20}, {13, 19}, {11, 14, 19, 114}, {13, 14, 19, 24, 20}};
    }

    public static int[] getXYMap(final int index, final int option) {
        final int[] xy = {RequestNPCInfoHandler.posHoaTieu[index][option * 2], RequestNPCInfoHandler.posHoaTieu[index][option * 2 + 1]};
        return xy;
    }

    public static int getIDMap(final int index, final int option) {
        return RequestNPCInfoHandler.IDMAP_HOA_TIEU[index][option];
    }

    @Override
    public void process(final Session session, final Message message) throws IOException {
        final Message m = new Message(23);
        final byte npcType = message.dis.readByte();
        if (TeamServer.isServerLocal()) {
            System.out.println("NPC REQUEST " + npcType);
        }
        //System.out.println("NPC REQUEST " + npcType);
        Char p = null;
        Message msg = null;
        String[] menu = {""};
        switch (npcType) {
            // Npc ADMIN
            case -2: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    menu = new String[]{"Hỗ Trợ", "Thông tin boss", "Đang Cập Nhật"};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                    return;
                }
                break;
            }
            case -67: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null && Char.isSuKienTet2017()) {
                    menu = new String[]{"Nhận lì xì", "Code lì xì", "Tưới nước"};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                    return;
                }
                break;
            }
            case -65: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p == null) {
                    break;
                }
                if (Char.isSuKienTet2017()) {
                    final CharCooking c = Char.nauBanhChung.listCharCooking.get(p.charname);
                    final String nameoption = Char.nauBanhChung.getNameOption();
                    menu = new String[]{nameoption, "Số bánh góp: " + ((c != null) ? c.gop : 0), "Số bánh nhận: " + ((c != null) ? c.nbanh : 0)};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                if (!Char.isFinishAllSuKienTrungThul2016()) {
                    menu = new String[]{"Đốt đèn", "Thả đèn(50L+1 nến)", "Thả đèn(5tr xu+1 nến)", "Mua nến", "Đổi NLT (20 nến + 10kL)"};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                    break;
                }
                break;
            }
            case -66: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null && Char.isSuKienTet2017()) {
                    final CharCooking c = Char.nauBanhTet.listCharCooking.get(p.charname);
                    final String nameoption = Char.nauBanhTet.getNameOption();
                    menu = new String[]{nameoption, "Số bánh góp: " + ((c != null) ? c.gop : 0), "Số bánh nhận: " + ((c != null) ? c.nbanh : 0)};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -64: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null && p.charCopy != null && !p.charCopy.isFollow() && Map.inRangeActor(p, p.charCopy, 50)) {
                    menu = new String[]{"Dẫn về"};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -63: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    menu = new String[]{"Về làng"};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -62: {
                return;
            }
            case -54: {
                return;
            }
            case -55: {
                return;
            }
            case -56: {
                return;
            }
            case -57: {
                return;
            }
            case -58: {
                return;
            }
            case -59: {
                return;
            }
            case -60: {
                return;
            }
            case -61: {
                return;
            }
            case -53:
            case -52: {
                return;
            }
            case -51: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null && p.monster != null) {
                    menu = new String[]{"Trả tiêu"};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -50: {
                p = CharManager.instance.getByUserID(session.userID);
//                if (p != null) {
//                    menu = new String[]{"Xuống ngựa", "Đổi danh hiệu", "Đăng ký lôi đài", "Vào sảnh chờ", "Xem lôi đài"};
//                    msg = MessageCreator.createMsgMenu(menu, npcType, 10);
//                    session.sendMessage(msg);
//                }
                if (p != null && p.luot_van_tieu > 0) {
                    if (p.myCountry == p.inCountry) {
                        menu = new String[]{"Nhận tiêu cá nhân(" + p.luot_van_tieu + ")", "Xuống ngựa", "Đổi danh hiệu", "Đăng ký lôi đài", "Vào sảnh chờ", "Xem lôi đài"};
                        msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                        session.sendMessage(msg);
                    }
                } else {
                    menu = new String[]{"Xuống ngựa", "Đổi danh hiệu", "Đăng ký lôi đài", "Vào sảnh chờ", "Xem lôi đài"};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 10);
                    session.sendMessage(msg);
                }
                return;

            }
            case -49: { // todo npc sự kiện
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    menu = new String[]{"Làm rương quà", "Đổi nguyên liệu", "Điểm nạp"};
                    if (Char.isSuKien2011l2016()) {
                        menu = new String[]{"Làm rương quà", "Đổi nguyên liệu", "Điểm nạp", "Lồng đèn hoa sen", "Chổi băng(" + Map.totalChoiBan + ")", "Chổi độc(" + Map.totalChoiBan + ")", "Chổi điện(" + Map.totalChoiBan + ")"};
                    } else if (Char.isBanLongDenHoaSen()) {
                        menu = new String[]{"Làm rương quà", "Đổi nguyên liệu", "Điểm nạp", "Lồng đèn hoa sen"};
                    } else if (!Char.isFinishAllSuKienHe2017()) {
                        menu = new String[]{"Tìm trẻ lạc", "Đổi nguyên liệu", "Top pháo"};
                    }
                    if (!Char.isFinishAllSuKienTrungThul2016()) {
                        menu = new String[]{"Làm bánh nướng", "Làm bánh dẻo", "Đổi hộp bánh", "Đổi nguyên liệu", "Nhận lồng đèn", "Đổi pet(20bd+70l)", "Bảng xếp hạng"};
                    }
                    if (!Char.isFinishAllSuKienHaloween2016()) {
                        menu = new String[]{"Làm hộp quà", "Đổi nguyên liệu", "Đổi điểm lượng(" + p.luongxainhanRuong + "đ)"};
                    }
                    if (!Char.isFinishAllSuKienGioTo2016()) {
                        menu = new String[]{"Đổi quà", "Đổi nguyên liệu"};
                        if (TeamServer.isServerHoaLu() || TeamServer.isServerLocal()) {
                            menu = new String[]{"Đổi quà", "Đổi nguyên liệu", "Điểm dùng lượng(" + p.luongxainhanRuong + ")", "Đổi rương bát bảo(" + p.luongxai + " điểm)", "Đổi xu"};
                        }
                    }
                    if (Char.isSuKienNoel2023()) {

                        menu = new String[]{"Đổi kem", "Đổi Tuần Lộc", "Đổi nguyên liệu", "Đổi túi quà may mắn", "Đổi đồ Bạch Mi", "Đổi đồ Thuý Vũ Hồng Sam", "Đổi quà đặc biệt"};

                    }
                    if (Char.isSuKienWordcup2017()) {
                        menu = new String[]{"Đổi bóng"};
                    }
                    if (!Char.isFinishAllSuKienTet2017()) {
                        menu = new String[]{"Nhận bánh chưng", "Nhận bánh tét", "Dâng bánh chưng", "Đổi nguyên liệu", "Làm pháo", "Đăng ký nấu bánh chưng", "Đăng ký nấu bánh tét", "Top pháo hoa"};
                    }
                    if (!Char.isFinishSuKienMiniChucNu()) {
                        menu = new String[]{"Tìm Chức Nữ", "Đổi nguyên liệu", "Đổi phi phong ma", "Đổi phi phong vật", "Đổi Bạch thử (7ngày)"};
                    }
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -48:
            case -47:
            case -46:
            case -45:
            case -44:
            case -43:
            case -42:
            case -41:
            case -40:
            case -39:
            case -38: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    menu = new String[RequestNPCInfoHandler.IDMAP_HOA_TIEU[npcType + 48].length];
                    for (int i = 0; i < RequestNPCInfoHandler.IDMAP_HOA_TIEU[npcType + 48].length; ++i) {
                        menu[i] = Map.getNameMap(RequestNPCInfoHandler.IDMAP_HOA_TIEU[npcType + 48][i]);
                    }
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -37: {
                if (!Map.openMarket) {
                    session.sendMessage(MessageCreator.createServerAlertMessage("Chức năng đang bảo trì", ""));
                    return;
                }
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    menu = new String[]{"Mua bán"};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -99: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    menu = new String[]{
             
                        "Nhận quà nạp tích lũy",
                        "Bật phi phong VIP", 
                        "Tinh luyện thần binh",
                        "Kích ẩn mặt nạ cáo hoặc thỏ"
                    };
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -36: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    if (getDayTime(0L).compareTo("2023-11-05 00:00:00") >= 0) {
                        p.sendMessage(MessageCreator.createMsgChat(p.id, "Sự kiện nhận quà đã kết thúc."));
                        return;
                    }
                    if (p.lvDetail.lv < 40) {
                        p.sendMessage(MessageCreator.createMsgChat(p.id, "Bạn phải đạt cấp độ 40 để nhận quà."));
                        return;
                    }
                    if (p.qua_npc == 0) {
                        int[] valuemn = {5, 5, 10, 30, 50};
                        int randomMN = Map.r.nextInt(valuemn.length);
                        Item newitem = Map.doCreateMatNa3(p, 10080, ID_MAT_NA[randomMN], randomMN, valuemn[randomMN]);
                        // session.sendMessage(MessageCreator.createServerAlertMessage("Nhận " + newitem.getName() + " 7 ngày", ""));
                        p.sendMessage(MessageCreator.createMsgChat(p.id, "Nhận " + newitem.getName() + " 7 ngày"));
                        p.qua_npc = 1;
                        Database.instance.saveOrtherLog("", p.charname, "nhan qua tang haloween", "hopquahlw");
                        Database.instance.UpdateQua_NPC(p);
                    } else {
                        //session.sendMessage(MessageCreator.createServerAlertMessage("Bạn đã nhận quà này rồi.", ""));
                        p.sendMessage(MessageCreator.createMsgChat(p.id, "Bạn đã nhận quà này rồi."));
                    }
                }
                return;
            }
            case -35: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    if (!Char.isSuKienTetduonglich2024()) {
                        p.sendMessage(MessageCreator.createMsgChat(p.id, "Sự kiện tết dương lịch 2024 đã kết thúc, vui lòng quay lại sau."));
                        return;
                    }

                    if (p.lvDetail.lv < 40) {
                        p.sendMessage(MessageCreator.createMsgChat(p.id, "Bạn phải đạt cấp độ 40 để nhận quà."));
                        return;
                    }
                    if (p.potions[137] < 10) {
                        p.sendMessage(MessageCreator.createMsgChat(p.id, "Bạn phải có ít nhất 10 kẹo để đổi quà"));
                        return;
                    }
                    if (p.getLuong() < 50) {
                        p.sendMessage(MessageCreator.createMsgChat(p.id, "Bạn phải có ít nhất 50 lượng mở để đổi quà"));
                        return;
                    }
                    p.subLuong(50);
                    p.potions[137] = p.potions[137] - 10;
                    int Qua = Map.ranDom(0, 20);
                    if (Qua == 9) {
                        long day = Map.ranDom(3, 7);
                        p.createHeo(day, true);
                        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
                        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 2));
                        Database.instance.saveOrtherLog("", p.charname, "da doi duong khang", "duongkhang");
                        p.sendMessage(MessageCreator.createMsgChat(p.id, "Bạn đã nhận đương khang " + day + " ngày."));
                        return;
                    }
                    if (Qua == 8) {
                        int day = Map.ranDom(3, 7);
                        int[] valuemn = {5, 5, 10, 30, 50};
                        int randomMN = Map.r.nextInt(valuemn.length);
                        Item newitem = Map.doCreateMatNa3(p, (60 * 24 * day), ID_MAT_NA[randomMN], randomMN, valuemn[randomMN]);
                        // session.sendMessage(MessageCreator.createServerAlertMessage("Nhận " + newitem.getName() + " 7 ngày", ""));
                        p.sendMessage(MessageCreator.createMsgChat(p.id, "Nhận " + newitem.getName() + " " + day + " ngày"));
                        return;
                    }
                    if (Qua == 7) {
                        long day = Map.ranDom(3, 7);
                        p.createTuanLoc(day);
                        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
                        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 2));
                        Database.instance.saveOrtherLog("", p.charname, "da doi linh thu tuan loc", "tuanloc");
                        p.sendMessage(MessageCreator.createMsgChat(p.id, "Bạn đã nhận linh thú tuần lộc " + day + " ngày."));
                        return;
                    }
                    if (Qua == 6) {
                        long day = Map.ranDom(3, 7);
                        p.createPoro(day, true);
                        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
                        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 2));
                        Database.instance.saveOrtherLog("", p.charname, "da doi dai bach thu", "dai bach thu");
                        p.sendMessage(MessageCreator.createMsgChat(p.id, "Bạn đã nhận đại bạch thử " + day + " ngày."));
                        return;
                    }
                    if (Qua == 5) {
                        long day = Map.ranDom(3, 7);
                        p.createNguaXuong(day, true);
                        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
                        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 2));
                        Database.instance.saveOrtherLog("", p.charname, "da doi bach cot", "bach cot");
                        p.sendMessage(MessageCreator.createMsgChat(p.id, "Bạn đã nhận bạch cốt mã " + day + " ngày."));
                        return;
                    }
                    if (Qua == 4) {
                        int luongLock = Map.ranDom(100, 1000);
                        p.lockLuong += luongLock;
                        p.sendMessage(MessageCreator.createMsgChat(p.id, "Bạn đã nhận " + luongLock + " lượng khoá."));
                        return;
                    }
                    int StoneC = Map.random(GemTemplate.ID_MATERIAL_LOW[5].length);
                    for (int stone1 = 0; stone1 < 5; stone1++) {
                        p.doAddGemItem(GemTemplate.ID_MATERIAL_LOW[5][StoneC], 1, true);
                    }
                    p.sendMessage(MessageCreator.createCharGemItem(p));
                    p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                    //p.sendMessage(MessageCreator.createServerAlertMessage("Bạn nhận được 3 nguyên liệu sơ cấp 6 khóa", ""));
                    p.sendMessage(MessageCreator.createMsgChat(p.id, "Bạn nhận được 5 nguyên liệu sơ cấp 6 khóa"));
                }
                return;
            }
            case -34: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null && p.map.nRegion > 0) {
                    menu = new String[p.map.nRegion];
                    for (int i = 0; i < p.map.nRegion; ++i) {
                        menu[i] = "Khu " + (i + 1);
                    }
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -32: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    menu = new String[]{"Chuyển sang Thanh Long", "Chuyển sang Hắc Hổ"};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -31: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    menu = new String[]{"Thách đấu cá nhân", "Trấn yêu trận", "Đăng ký chiến trường", "Nhận quà khu liên đấu", "Nhận quà Liên trảm", "Nhận quà top trụ", "Núi châu báu"};
                    if (!Char.isFinishAllSuKienMini()) {
                        menu = new String[]{"Thách đấu cá nhân", "Trấn yêu trận", "Đăng ký chiến trường", "Nhận quà khu liên đấu", "Nhận quà Liên trảm", "Nhận quà top trụ", "Núi châu báu", "Đổi đồ Bạch Mi", "Đổi đồ Thuý Vũ Hồng Sam", "Đổi quà đặc biệt"};
                    }
                    if (p.idArena > -1) {
                        menu[0] = "Vào lại đấu trường";
                    }
                    if (p.idTongKim > -1) {
                        menu[1] = "Vào lại chiến trường";
                    }
                    final CharChienTruong c2 = MapChienTruongMoba.getCharChienTruong(p.charname);
                    if (c2 != null) {
                        menu[2] = "Vào chiến trường";
                    }
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -30: {
                return;
            }
            case -29: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null && (p.idClan > -1 || p.map.isMapLienDau())) {
                    menu = new String[]{"Giao thẻ"};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -28: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    p = CharManager.instance.getByUserID(session.userID);
                    menu = new String[]{"Thả boss"};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -27: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    p = CharManager.instance.getByUserID(session.userID);
                    menu = new String[]{"Đăng ký tiệc cưới", "Vào tiệc cưới", "Khai tiệc"};
                    if (!p.nameHusband_wife.equals("")) {
                        menu = new String[]{"Đăng ký tiệc cưới", "Vào tiệc cưới", "Khai tiệc", "Ly dị(150L)", "Đơn phương ly dị (300L)"};
                    }
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -26: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    if (p.inCountry == 0) {
                        menu = new String[]{"Dương đông", "Đông Dương đông"};
                    } else if (p.inCountry == 1) {
                        menu = new String[]{"Sơn nam", "Đông Sơn nam"};
                    } else if (p.inCountry == 2) {
                        menu = new String[]{"Lâm tây", "Đông Lâm tây"};
                    }
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -24: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    p = CharManager.instance.getByUserID(session.userID);
                    if (p != null) {
                        if (!Map.openLog) {
                            menu = new String[]{"Mua vé số(1t xu)", "Số đã mua", "Quay số", "Mua 2 vé quay số(10l)", "Mua 2 vé quay số(10t xu)", "Mua 2 vé quay số đặc biệt(10l)","Mua 2 Vé quay Phượng Hoàng(50l)"};
                            if (p.lastLV == 35 && p.gif35 == 0) {
                                menu = new String[]{"Mua vé số(1t xu)", "Số đã mua", "Quay số", "Mua 2 vé quay số(10L)", "Mua 2 vé quay số(10t xu)", "Mua 2 vé quay số đặc biệt(10l)","Mua 1 Vé quay Phượng Hoàng(50l)", "Quà 35 ma", "Quà 35 vật"};
                            }
                        } else {
                            menu = new String[]{"Mua vé số(1t xu)", "Số đã mua", "Quay số", "Mua 2 vé quay số(10l)", "Mua 2 vé quay số(10t xu)", "Mua 2 vé quay số đặc biệt(10l)","Mua 2 Vé quay Phượng Hoàng(50l)", "Nhập mã quà tặng", "Kim cương", "Nạp sms", "Tặng lượng(" + p.vetangluong + " vé)"};
                            if (p.lastLV == 35 && p.gif35 == 0) {
                                menu = new String[]{"Mua vé số(1t xu)", "Số đã mua", "Quay số", "Mua 2 vé quay số(10L)", "Mua 2 vé quay số(10t xu)", "Mua 2 vé quay số đặc biệt(10l)", "Mua 2 Vé quay Phượng Hoàng(50l)", "Quà 35 ma", "Quà 35 vật", "Nhập mã quà tặng", "Kim cương", "Nạp sms", "Tặng lượng(" + p.vetangluong + " vé)"};
                            }
                        }
                        msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                        session.sendMessage(msg);
                    }
                }
                return;
            }
            case -23: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null && p.myCountry == -1) {
                    if (p.idClan > -1) {
                        final NewClan clan = NewClan.getClan(p.idClan);
                        if (clan != null) {
                            if (clan.country == -1) {
                                menu = Map.nameCountry;
                                msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                                session.sendMessage(msg);
                            } else {
                                session.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có bang chủ mới có thể chọn quốc gia cho bang.", ""));
                            }
                        }
                    } else {
                        menu = Map.nameCountry;
                        msg = MessageCreator.createMsgMenu(menu, npcType, 1);
                        session.sendMessage(msg);
                    }
                }
                return;
            }
            case -25: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null && p.myCountry > -1 && p.idClan > -1 && Map.idClanTown[p.myCountry] == p.idClan) {
                    final NewClan cl = NewClan.getClan(p.idClan);
                    if (cl != null && cl.master.equals(p.charname)) {
                        final String[] hour = {"Bảo hộ 6h-10h (15t xu bang)", "Bảo hộ 11h-15h (15t xu bang)", "Bảo hộ 16h-20h (15t xu bang)", "Bảo hộ 20h-24h (15t xu bang)"};
                        menu = new String[]{hour[Char.getIdOpenShield(Calendar.getInstance().get(11))], "Bảo hộ toàn bộ (50t xu bang)", "Đăng ký tấn công lãnh thổ"};
                        p.sendMessage(MessageCreator.createMsgMenu(menu, -25, 0));
                    }
                }
                return;
            }
            case -22: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    if (p.myCountry == 2) {
                        return;
                    }
                    String[][] vilage = {{"Đến biên giới " + Map.nameCountry[1]}, {"Đến biên giới " + Map.nameCountry[0]}};
                    if (!Map.pkAuto) {
                        vilage = new String[][]{{"Đến biên giới " + Map.nameCountry[1], "Trường giang"}, {"Đến biên giới " + Map.nameCountry[0], "Trường giang"}};
                    }
                    msg = MessageCreator.createMsgMenu(vilage[p.inCountry], npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -7: {
                return;
            }
            case -21: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    menu = new String[]{"Hồi hồ", "Đông Hồi hồ"};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -20: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    menu = new String[]{"Hồi hồ", "Đông Hồi hồ"};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -19: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    menu = new String[]{"Bình kiều", "Chiến hạm"};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -18: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    menu = new String[]{"Nâng cấp kho."};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -17: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    menu = new String[]{"Nâng cấp nhà."};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -16: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    menu = new String[]{"Đăng ký."};
                    if (p.petUsing != null) {
                        menu = new String[]{"Đăng ký", "Cho pet ăn", "Nâng cấp", "Nâng cấp dùng lkd 30", "Nâng cấp dùng lkd 35", "Nâng cấp dùng lkd 40"};
                    }
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -15: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    menu = new String[]{"Đổi hệ ngũ hành.", "Tháo ngọc khảm", "Đặt mật khẩu rương", "Vòng quay", "Đồ đã bán."};
                    if (Char.isOpenDauGiaChoiBlackFriday()) {
                        menu = new String[]{"Đổi hệ ngũ hành.", "Tháo ngọc khảm", "Đặt mật khẩu rương", "Vòng quay", "Đồ đã bán.", "Đấu giá"};
                    } else if (Char.isOpenSellBlackFriday()) {
                        menu = new String[]{"Đổi hệ ngũ hành.", "Tháo ngọc khảm", "Đặt mật khẩu rương", "Vòng quay", "Đồ đã bán.", "Mua đồ thời trang", "Mua lồng đèn", "Nhận lại tiền cọc"};
                        if (!p.passWord.equals("")) {
                            menu[2] = "Xóa mật khẩu rương";
                        }
                        session.sendMessage(MessageCreator.createMsgMenu(menu, npcType, 12));
                        return;
                    }
                    if (!p.passWord.equals("")) {
                        menu[2] = "Xóa mật khẩu rương";
                    }
                    if (Char.isSuKienBlackFriday()) {
                        menu = new String[]{"Đổi hệ ngũ hành.", "Tháo ngọc khảm", "Đặt mật khẩu rương", "Vòng quay", "Đồ đã bán.", "Đấu giá", "Mua Chổi Băng(30 ngày)", "Mua Chổi Điện(30 ngày)", "Mua Chổi Độc(30 ngày)", "Mua Chổi Lửa(30 ngày)", "Mua pet Bạch Thử(60 ngày)"};
                        session.sendMessage(MessageCreator.createMsgMenu(menu, npcType, 10));
                        return;
                    }
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -9: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    final String[][] nameVilage = {{"Làng Dương đông", "Đông Dương đông"}, {"Làng Sơn nam", "Đông Sơn nam"}, {"Làng Lâm tây", "Đông Lâm tây"}};
                    menu = nameVilage[p.inCountry];
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -10: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    menu = new String[]{"Hợp đục", "Mở rộng hành trang(150L)", "Thử vận may", "Chuyển lãnh thổ(300l)", "Nhận quà Giftcode", "Đăng ký liên đấu"};
                    if (p.canReceiveGiftOffline > -1) {
                        menu = new String[]{"Hợp đục", "Mở rộng hành trang(150L)", "Thử vận may", "Chuyển lãnh thổ(300l)", "Nhận quà Giftcode", "Đăng ký liên đấu", "Nhận quà top"};
                    }
                    if (p.canReceiveGiftEvent()) {
                        menu = new String[]{"Hợp đục", "Mở rộng hành trang(150L)", "Thử vận may", "Chuyển lãnh thổ(300l)", "Nhận quà Giftcode", "Đăng ký liên đấu", "Nhận quà"};
                    }
                    if (p.maxBag > 1) {
                        menu = new String[]{"Hợp đục", "Mở rộng hành trang(500L)", "Thử vận may", "Chuyển lãnh thổ(300l)", "Nhận quà Giftcode", "Đăng ký liên đấu"};
                        if (p.canReceiveGiftOffline > -1) {
                            menu = new String[]{"Hợp đục", "Mở rộng hành trang(500L)", "Thử vận may", "Chuyển lãnh thổ(300l)", "Nhận quà Giftcode", "Đăng ký liên đấu", "Nhận quà top"};
                        }
                        if (p.canReceiveGiftEvent()) {
                            menu = new String[]{"Hợp đục", "Mở rộng hành trang(500L)", "Thử vận may", "Chuyển lãnh thổ(300l)", "Nhận quà Giftcode", "Đăng ký liên đấu", "Nhận quà"};
                        }
                    }
                    if (!TeamServer.isServerLienDau()) {
                        final boolean isReg = Database.instance.isCharRegisterLiendau(p.charname);
                        if (isReg) {
                            menu[5] = "Cập nhật thông tin";
                        }
                    }
                    if (TeamServer.isServerLienDau()) {
                        menu = new String[]{"Hợp đục", "Mở rộng hành trang(500L)", "Thử vận may", "Chuyển lãnh thổ(300l)", "Nhận quà Giftcode", "Vào chiến trường", "Kết quả"};
                    }
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -11: {
                p = CharManager.instance.getByUserID(session.userID);
                msg = MessageCreator.createMsgMenu(new String[]{"Nguyên liệu thường", "Nguyên liệu khóa", "Ngọc huyền minh", "Ngọc huyền minh khoá", "Bột thường", "Bột khoá"}, npcType, 0);
                if (p.hlt_buy > 0) {
                    msg = MessageCreator.createMsgMenu(new String[]{"Nguyên liệu thường", "Nguyên liệu khóa", "Ngọc huyền minh", "Ngọc huyền minh khoá", "Bột thường", "Bột khoá", "Huyết linh thảo(" + p.hlt_buy + ")"}, npcType, 0);
                }
                session.sendMessage(msg);
                return;
            }
            case -12: {
                msg = MessageCreator.createMsgMenu(new String[]{"Nguyên liệu thường", "Nguyên liệu khóa", "Xương không khóa", "Xương khóa"}, npcType, 0);
                session.sendMessage(msg);
                return;
            }
            case -8: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    p.infoItemCreate = null;
                    menu = new String[]{"Vũ khí", "Trang bị", "Trang bị thú",
                        "Nhận VIP theo cấp(10/30/50)",
                        "Hoạt động giờ",
                        (p.isTatPhiPhong() ? "Bật" : "Tắt") + " phi phong", 
                        (p.isTatMatNa() ? "Bật" : "Tắt") + " mặt nạ",
                        (p.isTatCanh() ? "Bật" : "Tắt") + " cánh",
                        "Tinh Luyện Thần Binh","Gia Hạn VKTT(150l 7n)","Tạo Thần Khí(VIP 3)","Cải Tạo Cải Trang","Bộ Sưu Tập"};
                    if (TeamServer.isServerLienDau()) {
                        menu = new String[]{"Vũ khí", "Trang bị", "Trang bị thú"};
                    }
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -14: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    menu = new String[]{"Quay bằng lượng", "Quay bằng xu"};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -6: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    menu = new String[]{"Tạo thú", "Luyện thú"};
                    if (p.animalRide != null) {
                        menu = new String[]{"Tạo thú", "Luyện thú", "Nâng cấp thú(" + Animal.price[3][p.animalRide.level] + " lượng)","Hóa Hình"};
                        if ((p.animalRide.isPhuongHoangBang() && p.animalRide.level >= 4 && p.animalRide.KHANG_BI_CHAY == 0) || (p.animalRide.isPhuongHoangLua() && p.animalRide.level >= 4 && p.animalRide.KHANG_DONG_BANG == 0) || (p.animalRide.isLan() && p.animalRide.level >= 4 && p.animalRide.KHANG_HOA_TUYET == 0)) {
                            menu = new String[]{"Tạo thú", "Luyện thú", "Nâng cấp thú(" + Animal.price[3][p.animalRide.level] + " lượng)","Hóa Hình", "Nâng cấp kháng(200l)"};
                        }
                    }
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -4: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null && (p.idClan > -1 || p.map.isMapLienDau())) {
                    menu = new String[]{"Giao thẻ"};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -5: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null && (p.idClan > -1 || p.map.isMapLienDau())) {
                    menu = new String[]{"Giao thẻ"};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -13: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null && p.idClan > -1) {
                    menu = new String[]{"Kỹ năng bang", "Kỹ năng thành viên", "Mua 500 điểm cống hiến(100L)"};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
            case -3: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null && p.idClan > -1) {
                    menu = new String[]{"Nâng cấp bang", "Danh sách quản lý bang", "Đặt thuế"};
                    if (p.rankClan == 0) {
                        final NewClan clan = NewClan.getClan(p.idClan);
                        final long money = clan.getMoneyDistributeSalary(p.idClan);
                        menu = new String[]{"Nâng cấp bang", "Danh sách quản lý bang", "Đặt thuế", "Phong chức", "Giáng chức", "Phát lương(max " + money + " xu)", "Lương bang chúng", "Phát lương nhanh(max " + money + " xu)"};
                    }
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                }
                return;
            }
           
            case 1:
            case 26: {
                m.dos.writeByte(0);
                m.dos.writeByte(12);
                m.dos.writeByte(1);
                m.dos.writeByte(2);
                m.dos.writeByte(3);
                m.dos.writeByte(4);
                m.dos.writeByte(5);
                m.dos.writeByte(6);
                m.dos.writeByte(14);
                m.dos.writeByte(15);
                m.dos.writeByte(16);
                m.dos.writeByte(17);
                m.dos.writeByte(18);
                m.dos.writeByte(19);
                break;
            }
            case 24: {
                m.dos.writeByte(1);
                m.dos.writeByte(-1);
                m.dos.writeShort(50);
                for (int i = 214; i <= 263; ++i) {
                    m.dos.writeShort(i);
                }
                break;
            }
            case 28: {
                m.dos.writeByte(1);
                m.dos.writeByte(-1);
                m.dos.writeShort(40);
                for (int i = 174; i <= 213; ++i) {
                    m.dos.writeShort(i);
                }
                break;
            }
            case 2: {
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    menu = new String[]{"Mua vũ khí", "Mua trang bị thú"};
                    if (TeamServer.isServerIndo()) {
                        menu = new String[]{"Membeli senjata"};
                    }
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                    return;
                }
                if (p == null) {
                    return;
                }
                m.dos.writeByte(1);
                m.dos.writeByte(-1);
                if (p.mapID < 200) {
                    m.dos.writeShort(97);
                    for (int i = 79; i <= 113; ++i) {
                        m.dos.writeShort(i);
                    }
                    for (int i = 465; i <= 466; ++i) {
                        m.dos.writeShort(i);
                    }
                    for (int i = 509; i <= 568; ++i) {
                        m.dos.writeShort(i);
                    }
                    break;
                }
                if (p.mapID > 200) {
                    m.dos.writeShort(40);
                    for (int i = 174; i <= 213; ++i) {
                        m.dos.writeShort(i);
                    }
                    break;
                }
                break;
            }
            case 27: {
                final int giap1 = message.dis.readByte();
                p = CharManager.instance.getByUserID(session.userID);
                if (p != null) {
                    p.classBuy = (byte) giap1;
                    menu = new String[]{"Trang bị ma pháp", "Trang bị vật lý"};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                    return;
                }
                m.dos.writeByte(1);
                m.dos.writeByte(giap1);
                m.dos.writeShort(164);
                for (int j = 21; j <= 26; ++j) {
                    m.dos.writeShort(j);
                }
                for (int j = 47; j <= 52; ++j) {
                    m.dos.writeShort(j);
                }
                for (int j = 73; j <= 78; ++j) {
                    m.dos.writeShort(j);
                }
                for (int j = 123; j <= 125; ++j) {
                    m.dos.writeShort(j);
                }
                for (int j = 135; j <= 137; ++j) {
                    m.dos.writeShort(j);
                }
                for (int j = 147; j <= 149; ++j) {
                    m.dos.writeShort(j);
                }
                for (int j = 159; j <= 161; ++j) {
                    m.dos.writeShort(j);
                }
                for (int j = 171; j <= 173; ++j) {
                    m.dos.writeShort(j);
                }
                break;
            }
            case 3: {
                final int giap2 = message.dis.readByte();
                p = CharManager.instance.getByUserID(session.userID);
                if (p == null) {
                    return;
                }
                if (p != null) {
                    p.classBuy = (byte) giap2;
                    menu = new String[]{"Trang bị ma pháp", "Trang bị vật lý"};
                    msg = MessageCreator.createMsgMenu(menu, npcType, 0);
                    session.sendMessage(msg);
                    return;
                }
                m.dos.writeByte(1);
                m.dos.writeByte(giap2);
                if (p.mapID < 200) {
                    m.dos.writeShort(103);
                    for (int k = 3; k <= 20; ++k) {
                        m.dos.writeShort(k);
                    }
                    for (int k = 27; k <= 46; ++k) {
                        m.dos.writeShort(k);
                    }
                    for (int k = 53; k <= 72; ++k) {
                        m.dos.writeShort(k);
                    }
                    for (int k = 114; k <= 122; ++k) {
                        m.dos.writeShort(k);
                    }
                    for (int k = 126; k <= 134; ++k) {
                        m.dos.writeShort(k);
                    }
                    for (int k = 138; k <= 146; ++k) {
                        m.dos.writeShort(k);
                    }
                    for (int k = 150; k <= 158; ++k) {
                        m.dos.writeShort(k);
                    }
                    for (int k = 162; k <= 170; ++k) {
                        m.dos.writeShort(k);
                    }
                    break;
                }
                if (p.mapID > 200) {
                    m.dos.writeShort(33);
                    for (int k = 21; k <= 26; ++k) {
                        m.dos.writeShort(k);
                    }
                    for (int k = 47; k <= 52; ++k) {
                        m.dos.writeShort(k);
                    }
                    for (int k = 73; k <= 78; ++k) {
                        m.dos.writeShort(k);
                    }
                    for (int k = 123; k <= 125; ++k) {
                        m.dos.writeShort(k);
                    }
                    for (int k = 135; k <= 137; ++k) {
                        m.dos.writeShort(k);
                    }
                    for (int k = 147; k <= 149; ++k) {
                        m.dos.writeShort(k);
                    }
                    for (int k = 159; k <= 161; ++k) {
                        m.dos.writeShort(k);
                    }
                    for (int k = 171; k <= 173; ++k) {
                        m.dos.writeShort(k);
                    }
                    break;
                }
                break;
            }
            case 9:
            case 30: {
                m.dos.writeByte(2);
                p = CharManager.instance.getByUserID(session.userID);
                if (p == null) {
                    return;
                }
                if (p.mapID != 108 && p.mapID != 262 && p.mapID != 78 && p.mapID != 88) {
                    return;
                }
                p.isPutItem2Bag = true;
                m.dos.writeByte(p.bItem.size());
                for (int k = 0; k < p.bItem.size(); ++k) {
                    m.dos.writeByte(p.bItem.get(k).clazz);
                    m.dos.writeShort(p.bItem.get(k).id);
                    final int template = p.bItem.get(k).getTemplate().id;
                    m.dos.writeShort(template);
                    m.dos.writeByte(p.bItem.get(k).plus);
                    m.dos.writeByte(p.bItem.get(k).level);
                    m.dos.writeShort(p.bItem.get(k).mDurable);
                    m.dos.writeShort(p.bItem.get(k).durable);
                }
                break;
            }
            case 6:
            case 29: {
                m.dos.writeByte(3);
                break;
            }
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20: {
                p = CharManager.instance.getByUserID(session.userID);
                m.dos.writeByte(4);
                m.dos.writeByte(Map.npcSell[p.myCountry][npcType - 11].shop.length);
                for (int k = 0; k < Map.npcSell[p.myCountry][npcType - 11].shop.length; ++k) {
                    m.dos.writeByte(Map.npcSell[p.myCountry][npcType - 11].shop[k].id);
                }
                m.dos.writeByte(npcType);
                m.dos.writeByte(message.dis.readByte());
                break;
            }
        }
        session.sendMessage(m);
    }
}
