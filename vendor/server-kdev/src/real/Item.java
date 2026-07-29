// 
// Decompiled by Procyon v0.6.0
// 
package real;

import data.CreateItemTemplate;
import data.Database;
import data.ItemLuckyDraw;
import java.util.Hashtable;
import java.util.Vector;
import java.util.ArrayList;

public class Item extends Actor {

    public static byte COLOR_WHITE;
    public static byte COLOR_GREEN;
    public static byte COLOR_RED;
    public static byte COLOR_BLUE;
    public static byte COLOR_MAGENTA;
    public static byte COLOR_YELLOW;
    public static byte MAX_ATT_KHAM;
    public static byte MAX_ATT;
    public String dbAttribute;
    public String dbInfo;
    public String dbInfoKham;
    public static final byte PLACE_INVENTORY = 0;
    public static final byte PLACE_WEARING = 1;
    public static final byte PLACE_STORE = 2;
    public static final byte PLACE_BAG = 3;
    public static final byte PLACE_MODEL_WEARING = 4;
    public static final byte PLACE_A_WEARING = 5;
    public static final byte TYPE_AO = 0;
    public static final byte TYPE_QUAN = 1;
    public static final byte TYPE_NON = 2;
    public static final byte TYPE_KIEM = 3;
    public static final byte TYPE_DAIDAO = 4;
    public static final byte TYPE_DUA = 5;
    public static final byte TYPE_BUA = 6;
    public static final byte TYPE_CUNG = 7;
    public static final byte TYPE_NHAN = 8;
    public static final byte TYPE_DAYCHUYEN = 9;
    public static final byte TYPE_GIAY = 10;
    public static final byte TYPE_GANG = 11;
    public static final byte TYPE_NGOC = 12;
    public static final byte TYPE_CUOC = 13;
    public static final byte TYPE_HO_VE_LENH = 21;
    public static final byte TYPE_GIAP_ANIMAL = 14;
    public static final byte TYPE_HO_UYEN_ANIMAL = 15;
    public static final byte TYPE_NON_ANIMAL = 16;
    public static final byte TYPE_BAN_DAP_ANIMAL = 17;
    public static final byte TYPE_YEN_CUONG_ANIMAL = 18;
    public static final byte TYPE_COAT = 19;
    public static final byte TYPE_XA_TIEU_LENH = 23;
    public static final byte TYPE_LENH_BAI_TRAN_YEU_TRAN = 24;
    public static final byte TYPE_TRUNG_PET_MOI = 26;
    public static final byte TYPE_ITEM_PET_HKL = 28;
    public static final byte ATB_GIAM_ST_VAT = 0;
    public static final byte ATB_GIAM_ST_MA = 1;
    public static final byte ATB_ADD_ATTACK = 2;
    public static final byte ATB_XUYEN_GIAP = 3;
    public static final byte ATB_PHAN_DAM = 4;
    public static final byte ATB_TANG_CONG = 0;
    public static final byte ATB_TANG_THU_MA = 1;
    public static final byte ATB_TANG_THU_VAT = 2;
    public static final byte ATB_NE_TRANH = 2;
    public static final byte ATB_SUC_MANH = 4;
    public static final byte ATB_KHEO_LEO = 5;
    public static final byte ATB_TINH_THAN = 6;
    public static final byte ATB_SUC_KHOE = 7;
    public static final byte ATB_BAO_KICH = 4;
    public static final byte ATB_SET_LAN = 5;
    public static final byte ATB_DOC_LAN = 6;
    public static final byte ATB_BANG_LAN = 7;
    public static final byte ATB_TANG_SET_LAN = 8;
    public static final byte ATB_TANG_BANG_LAN = 9;
    public static final byte ATB_TANG_DOC_LAN = 10;
    public static final byte ATB_HOA_NGUOI_TUYET = 14;
    public static final byte ATB_NEW_KHANG_BAO_KICH = 15;
    public static final byte ATB_NEW_HUT_HP = 16;
    public static final byte ATB_NEW_HOI_HP = 17;
    public static final byte ATB_NEW_HOI_MP = 18;
    public static final byte ATB_NEW_TAN_PHE = 19;
    public static final byte ATB_NEW_HAP_THU_SAT_THUONG = 20;
    public static final byte ATB_NEW_CAM_LANG = 21;
    public static final byte ATB_NEW_TANNG_PC_ROT_ITEM = 22;
    public static final byte ATB_NEW_TANG_PC_ROT_XU = 23;
    public static final byte ATB_NEW_KHANG_BANG_LAN = 24;
    public static final byte ATB_NEW_KHANG_SET_LAN = 25;
    public static final byte ATB_NEW_KHANG_DOC_LAN = 26;
    public static final byte ATB_NEW_TANG_THU_TB_CHAR = 27;
    public static final byte ATB_LUA_LAN = 28;
    public static final byte ATB_TANG_LUA_LAN = 29;
    public static final byte ATB_TANG_ST_LUA_LAN = 30;
    public static final byte ATB_KHANG_LUA_LAN = 31;
    public static final byte ATB_ATTACK_HAC_AM = 32;
    public static final byte ATB_ATTACK_ANH_SANG = 33;
    public static final byte ATB_XH_HAC_AM = 34;
    public static final byte ATB_XH_ANH_SANG = 35;
    public static final byte ATB_HOANG_SO = 36;
    public static final byte ATB_XH_HOANG_SO = 37;
    public static final byte ATB_ATTACK_BY_OWNER = 38;
    public static final byte ATB_SUC_KHOE_THAN_THU = 39;
    public static final byte ATB_SAT_THUONG_MAX_HP = 40;
    public static final byte ATB_RU_NGU = 41;
    public static final byte ATB_CHOANG = 42;
    public static final byte ATB_BONG = 43;
    public static final byte ATB_TRUNG_DOC = 44;
    public static final byte ATB_SUY_YEU = 45;
    public static final byte ATB_CHUYEN_ST_QUA_MP = 46;
    public static final byte ATB_GIA_TANG_TAN_CONG = 47;
    public static final byte ATB_HUT_MAU = 48;
    public static final byte ATB_SAT_THUONG_DOC = 49;
    public static final byte ATB_NEW_TANG_PC_EXP = 50;
    public static final byte ATB_TAC_DONG_SUY_YEU = 51;
    public static final byte ATB_TY_LE_HUT_HP_BY_HIT = 52;
    public static final byte ATB_SAT_THUONG_KHI_BONG_NHAN_THEM = 53;
    public static final byte ATB_SAT_THUONG_CHUAN = 54;
    public static final byte ATB_CHI_SO_VU_KHI_TANG = 55;
    public static final byte ATB_TY_LE_GAY_SAT_THUONG_CHUAN = 56;
    public static final byte ATB_GIAM_ST_CUOI = 57;
    public static final byte ATB_NGUYET_ANH = 58;
    public static final byte ATB_KHANG_HOA_HE = 59;
    public static final byte ATB_KHANG_THUY_HE = 60;
    public static final byte ATB_KHANG_MOC_HE = 61;
    public static final byte ATB_KHANG_KIM_HE = 62;
    public static final byte ATB_KHANG_THO_HE = 63;
    public static final byte ATB_GIA_BAN_LUONG = 64;
    public static final byte ATB_SO_LUONG = 65;
    public static final int TRUNG_CU = 684;
    public static final int TRUNG_RONG = 685;
    public static final int TRUNG_YEU_TINH = 686;
    public static final int TRUNG_DOI = 687;
    public static final int TRUNG_DAI_BANG = 688;
    public static final int CU_C1 = 689;
    public static final int CU_C2 = 690;
    public static final int CU_C3 = 691;
    public static final int CU_C4 = 692;
    public static final int CU_C5 = 693;
    public static final int RONG_C1 = 694;
    public static final int RONG_C2 = 695;
    public static final int RONG_C3 = 696;
    public static final int RONG_C4 = 697;
    public static final int RONG_C5 = 698;
    public static final int YEU_TINH_C1 = 699;
    public static final int YEU_TINH_C2 = 700;
    public static final int YEU_TINH_C3 = 701;
    public static final int YEU_TINH_C4 = 702;
    public static final int YEU_TINH_C5 = 703;
    public static final int DOI_C1 = 704;
    public static final int DOI_C2 = 705;
    public static final int DOI_C3 = 706;
    public static final int DOI_C4 = 707;
    public static final int DOI_C5 = 708;
    public static final int DAI_BANG_C1 = 709;
    public static final int DAI_BANG_C2 = 710;
    public static final int DAI_BANG_C3 = 711;
    public static final int DAI_BANG_C4 = 712;
    public static final int DAI_BANG_C5 = 713;
    public static final int TU_HON_DAN = 714;
    public static final int HOA_KY_LAN_CAP_1 = 681;
    public static final int HOA_KY_LAN_CAP_2 = 682;
    public static final int HOA_KY_LAN_CAP_3 = 683;
    public static final int HUYEN_THIET_KIEM = 715;
    public static final int YEN_NGUYET_DAO = 716;
    public static final int NGOC_TY_BUT = 717;
    public static final int TRAN_HON_BUA = 718;
    public static final int KHONG_TUOC_CUNG = 719;
    public static final int HUYET_LINH_THAO = 720;
    public static final int HUYET_BO_DE = 721;
    public static final int SACH_SKILL_PET = 722;
    public static final int PHI_PHONG_LONG_THU = 616;
    public static final int PHI_PHONG_WORLD_CUP = 608;
    public static final int CHOI_BANG_BIEN_HINH = 617;
    public static final int CHOI_SET_BIEN_HINH = 618;
    public static final int CHOI_DOC_BIEN_HINH = 619;
    public static final int CHOI_BANG = 613;
    public static final int CHOI_SET = 614;
    public static final int CHOI_DOC = 615;
    public static final int THE_MUA_BAN = 675;
    public static final int XA_TIEU_LENH_x2 = 676;
    public static final int XA_TIEU_LENH_x3 = 677;
    public static final int HO_VE_LENH_4x1 = 630;
    public static final int HO_VE_LENH_4x2 = 631;
    public static final int HO_VE_LENH_4x3 = 632;
    public static final int HO_VE_LENH_5x1 = 633;
    public static final int HO_VE_LENH_5x2 = 634;
    public static final int HO_VE_LENH_5x3 = 635;
    public static final int HO_VE_LENH_6x1 = 636;
    public static final int HO_VE_LENH_6x2 = 637;
    public static final int HO_VE_LENH_6x3 = 638;
    public static final int HAC_LANG = 723;
    public static final int THAN_NU = 724;
    public static final int BACH_MI = 725;
    public static final int THUY_VU_HONG_SAM = 726;
    public static final int GAY_GIANG_SINH = 728;
    public static final int CHOI_LUA_LAN_BIEN_HINH = 729;
    public static final int DONG_XU_BAC = 730;
    public static final int DONG_XU_XANH = 731;
    public static final int DONG_XU_VANG = 732;
    public static final int DONG_XU_DO = 733;
    public static final int DONG_XU_XANH_LA = 734;
    public static final int RUONG_MAY_MAN = 735;
    public static final int AO_SON_TINH = 736;
    public static final int AO_THUY_TINH = 737;
    public static final int AO_TIEU_LONG_NU = 740;
    public static final int AO_DUONG_QUA = 741;
    public static final int AO_TIEN_NU = 743;
    public static final int AO_TINH_QUAN = 744;
    public static final int RUONG_BAT_BAO = 738;
    public static final int TIEN_DON = 739;
    public static final int PHI_PHONG_KHONG_TUOC = 742;
    public static final int PHI_PHONG_HUYEN_VU = 750;
    public static final int PHU_PHU_THE = 751;
    public static final int RUONG_NGUYEN_LIEU_CAO_CAP = 752;
    public static final int RUONG_NGUYEN_LIEU_SO_CAP = 753;
    public static final int AO_DAI_NAM_2021 = 754;
    public static final int AO_DAI_NU_2021 = 755;
    public static final int MAT_NA_BI_NGO = 745;
    public static final int MAT_NA_QUY = 746;
    public static final int MAT_NA_SOI = 747;
    public static final int MAT_NA_ZOOMBI = 748;
    public static final int MAT_NA_PANDA = 749;
    
    public static final int NGUYET_LINH_TRUONG = 796;
    
    public static final int NGHE_THUONG = 797;
    public static final int NGOC_NU = 798;
    public static final int XICH_CUOC = 799;
    public static final int KIM_DONG = 800;
    public static final int LOI_CONG = 801;

    public static final int MIEU_MAT_XANH = 833;
    public static final int MIEU_QUY_TOC = 834;
    public static final int GIAP_RONG = 839;

    public int quanlity;
    public static boolean[][] attribShowedForType;
    public int owner;
    public int dbowner;
    public int idItem;
    public byte clazz;
    public byte nhadSock;
    public byte nSocAdd;
    public byte magic_physic;
    public int minuteExist;
    public byte slotWearing;
    public byte place;
    public byte colorName;
    public int idAnimal;
    public short durable;
    public short level;
    public short mDurable;
    public short item_quest_belong;
    public short[] atb;
    public byte plus;
    public byte hangItem;
    public byte pos;
    public byte beKick;
    public byte heItem;
    public byte lock;
    public byte idCountry;
    public int prizeSell;
    public int idSellMarket;
    public long timeLoan;
    public Vector<Short> gemKham;
    public int classTemplate;
    public int tempateID;
    public Hashtable<Short, InfoItemAttribute> allAtb;
    static final String[] attrName;
    static String[] na;
    public int dbid;
    public int belongUser;
    public boolean isBoss;
    public boolean isItemShop;
    public int belongParty;
    public String charSeal;
    public String charCanBuy;
    public String dateCreate;
    public byte typetieu;
    public byte idNguHanh2;
    public byte isItemDrop;
    public static int[][] COST_RESET_SOCK;
    public long timeExpireHkl;
    public int id_dongxu_trung;
    public static byte[] PC_DEF_COAT;
    public static byte[] HP_COAT;
    public static short[][][] VALUE_NEW_ATTRIBUTE;
    public static short[] ID_NAME_NEW_ATTRIBUTE;
    public short[][][] VALUE_NGUHANH_NEW_ATTRIBUTE;
    public static int[][] OPTION_RANDOM;
    static byte[] value_hang_item;
    public byte[] newAtb;
    public byte indexWearing;
    public byte[] addMoreLevelSkill;
    public byte[] lockAtb;
    public int identify;
    public short switchAttNguhanh;
    public short resetItem;
    public boolean isUpdate;
    public short[] otherAtt;
    public byte oldKham;
    public String myChar;
    public int TinhLuyen;
    public int GiaHan;
    public int CaiTao;

    static {
        Item.COLOR_WHITE = 0;
        Item.COLOR_GREEN = 1;
        Item.COLOR_RED = 2;
        Item.COLOR_BLUE = 3;
        Item.COLOR_MAGENTA = 4;
        Item.COLOR_YELLOW = 5;
        Item.MAX_ATT_KHAM = 18;
        Item.MAX_ATT = 33;
        final boolean[][] attribShowedForType = new boolean[30][];
        attribShowedForType[0] = new boolean[]{false, true, true, true, false, false, true, false, false, false};
        attribShowedForType[1] = new boolean[]{false, true, true, true, false, false, true, false, false, false};
        attribShowedForType[2] = new boolean[]{false, true, true, true, false, false, true, false, false, false};
        attribShowedForType[3] = new boolean[]{true, false, false, true, true, false, false, false, false, false};
        attribShowedForType[4] = new boolean[]{true, false, false, true, true, false, false, false, false, false};
        attribShowedForType[5] = new boolean[]{true, false, false, true, true, false, false, false, false, false};
        attribShowedForType[6] = new boolean[]{true, false, false, true, true, false, false, false, false, false};
        attribShowedForType[7] = new boolean[]{true, false, false, true, true, false, false, false, false, false};
        final int n = 8;
        final boolean[] array = new boolean[10];
        array[3] = (array[0] = true);
        attribShowedForType[n] = array;
        attribShowedForType[9] = new boolean[]{true, false, false, false, true, false, false, false, false, false};
        attribShowedForType[10] = new boolean[]{false, true, true, false, false, false, true, false, false, false};
        attribShowedForType[11] = new boolean[]{false, true, false, true, true, false, true, false, false, false};
        attribShowedForType[12] = new boolean[]{false, false, false, true, true, true, false, false, false, false};
        final int n2 = 13;
        final boolean[] array2 = new boolean[10];
        array2[0] = true;
        attribShowedForType[n2] = array2;
        attribShowedForType[14] = new boolean[]{false, true, false, false, false, false, true, false, false, false};
        final int n3 = 15;
        final boolean[] array3 = new boolean[10];
        array3[3] = (array3[0] = true);
        attribShowedForType[n3] = array3;
        attribShowedForType[16] = new boolean[]{false, true, false, false, false, false, true, false, false, false};
        attribShowedForType[17] = new boolean[]{true, false, false, false, true, false, false, false, false, false};
        attribShowedForType[18] = new boolean[]{false, true, false, false, false, false, true, false, false, false};
        attribShowedForType[19] = new boolean[]{false, true, false, false, false, false, true, false, false, false};
        attribShowedForType[20] = new boolean[10];
        attribShowedForType[21] = new boolean[10];
        attribShowedForType[22] = new boolean[10];
        attribShowedForType[23] = new boolean[10];
        attribShowedForType[24] = new boolean[10];
        attribShowedForType[25] = new boolean[10];
        attribShowedForType[26] = new boolean[10];
        attribShowedForType[27] = new boolean[10];
        attribShowedForType[28] = new boolean[10];
        attribShowedForType[29] = new boolean[10];
        Item.attribShowedForType = attribShowedForType;
        attrName = new String[]{"Tấn công", "PhTòng thủ", "Né tránh", "Chính xác", "Chí mạng", "Sức khoẻ", "Chỉ số 6", "Chỉ số 7", "Chỉ số 8", "Chỉ số 9"};
        Item.na = new String[]{"Giáp nhẹ", "Giáp nặng", "Giáp vải", "Giáp nặng", "Giáp nhẹ"};
        Item.COST_RESET_SOCK = new int[][]{{20000, 25000, 20}, {50000, 60000, 20}, {100000, 120000, 30}, {150000, 170000, 30}, {200000, 230000, 40}, {250000, 280000, 40}, {300000, 350000, 50}, {350000, 380000, 50}, {400000, 500000, 60}, {500000, 650000, 60}, {600000, 800000, 70}, {750000, 1000000, 70}, {850000, 1200000, 80}, {950000, 1400000, 80}, new int[0]};
        Item.PC_DEF_COAT = new byte[]{26, 31, 36, 41, 46, 51, 56, 61, 66, 71, 76, 81, 86, 91};
        Item.HP_COAT = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        Item.VALUE_NEW_ATTRIBUTE = new short[][][]{{new short[0]}, {{20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30}, {31, 32, 33, 34, 35, 36, 37, 38, 33, 39, 40}, {41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55}}, {{1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10}}, {{1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10}}, {{5, 10}, {15, 20}, {25, 30}}, {new short[6]}, {{5, 10}, {15, 20}, {25, 30}}, {{5, 10, 15, 20, 15, 30}, {35, 40, 45, 50, 55, 60, 65, 70}, {75, 80, 85, 90, 95, 100}}, {{5, 10, 15, 20, 15, 30}, {35, 40, 45, 50, 55, 60, 65, 70}, {75, 80, 85, 90, 95, 100}}, {{5, 10}, {15, 20}, {25, 30, 25, 40}}, {{5, 10}, {15, 20}, {25, 30, 25, 40}}, {{5, 10}, {15, 20}, {25, 30, 25, 40}}, {{5, 10}, {15, 20}, {25, 30, 25, 40}}, {{5, 10}, {15, 20}, {25, 30, 25, 40}}};
        Item.ID_NAME_NEW_ATTRIBUTE = new short[]{76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 92};
        Item.OPTION_RANDOM = new int[][]{{28, 7, 6, 5}, {17, 18}, new int[1], {1, 2}, {50}, {23}};
        Item.value_hang_item = new byte[]{0, 5, 4, 3, 2, 1};
    }

    public ItemTemplates getTemplate() {
        if (this.classTemplate == -1) {
            return (ItemTemplates) Map.itemTemplates.get(5).get(new Integer(this.tempateID));
        }
        return (ItemTemplates) Map.itemTemplates.get(this.classTemplate).get(new Integer(this.tempateID));
    }

    /**
     * TODO EFF Cái này sửa trong database
     *
     * @return
     */
    public int getIdEff() {
        if (isVuKhiThanBinhNangCap2()) {
            return this.getTemplate().idEff + 8700;
        } else if (isVuKhiThanBinhNewUpdate09092024()) {
            return this.getTemplate().idEff + 8700;
        } else if (isNguyetLinhTruong()) {
            return this.getTemplate().idEff + 8700;
        } else if (isXichCuoc()) {
            return this.getTemplate().idEff + 8700;
        } else if (isQuatBaiTieu()) {
            return this.getTemplate().idEff + 8700;
        } else if (isGayNhuY()) {
            return this.getTemplate().idEff + 8700;
        } else if (isLuongNhatDao()) {
            return this.getTemplate().idEff + 8700;
        } else if (isCanh()) {
            return this.getTemplate().idEff + 8700;
        } else {
            return this.getTemplate().idEff;
        }

    }

    public boolean isCanh() {
        return (this.getTemplate().id >= 898 && this.getTemplate().id <= 902);
    }

    

    public boolean isGayGiangSinh() {
        return this.getTemplate().id == 728;
    }

    public boolean isChoiLuaLanBienHinh() {
        return this.getTemplate().id == 729;
    }

    public boolean isMatna() {
        return this.isMatNaBiNgo()
                || this.isMatNaPanda()
                || this.isMatNaQuy()
                || this.isMatNaSoi()
                || this.isMatNaZoombi()
                || this.isMatNaTho()
                || this.isMatNaCao();
    }

    public boolean isMatNaBiNgo() {
        return this.getTemplate().id == 745;
    }

    public boolean isMatNaQuy() {
        return this.getTemplate().id == 746;
    }

    public boolean isMatNaSoi() {
        return this.getTemplate().id == 747;
    }

    public boolean isMatNaZoombi() {
        return this.getTemplate().id == 748;
    }

    public boolean isMatNaPanda() {
        return this.getTemplate().id == 749;
    }

    public boolean isMatNaTho() {
        return this.getTemplate().id == 837;
    }

    public boolean isMatNaCao() {
        return this.getTemplate().id == 838;
    }
    // TODO thêm AVATAR
    public int getIndexDxy() {
        if (this.isGayGiangSinh()) {
            return 0;
        }
        if (this.isChoiLuaLanBienHinh()) {
            return 0;
        }
        if (this.isNguyetLinhTruong()) {
            return 5;
        }
        if (this.isXichCuoc()) {
            return 0;
        }
        if (this.isVuKhiThanBinhNewUpdate09092024()) {
            return 0;
        }
        if (this.isVuKhiThanBinhNangCap2()) {
            return 0;
        }
        if (this.isQuatBaiTieu()) {
            return 9; //9
        }
        if (this.isGayNhuY()) {
            return 8;
        }
        if (this.isLuongNhatDao()) {
            return 7; //7
        }
        return -1;
    }
    // TODO THÊM AVATAR (2)
    public int getIdBigImgAvatar() {
        if (this.isGayGiangSinh()) {
            return this.getTemplate().idBigImgAvatar;
        }
        
        if (this.isChoiLuaLanBienHinh()) {
            return this.getTemplate().idBigImgAvatar;
        }
        if (this.isVuKhiThanBinhNewUpdate09092024()) {
            return this.getTemplate().idBigImgAvatar;
        }
        if (this.isVuKhiThanBinhNangCap2()) {
            return this.getTemplate().idBigImgAvatar;
        }
        if (this.isNguyetLinhTruong()) {
            return 715;
        }
        if (this.isXichCuoc()) {
            return this.getTemplate().idBigImgAvatar;
        }
        if (this.isQuatBaiTieu()) {
            return 823;
        }
        if (this.isGayNhuY()) {
            return 824;
        }
        if (this.isLuongNhatDao()) {
            return 825;
        }
        return -1;
    }

    public void setTemplate(final int template_id, final int clazz, final int classTemplate, final int idtemplate) {
        try {
            ItemTemplates template = null;
            final ItemTemplates m = (ItemTemplates) Map.itemTemplates.get(clazz).get(new Integer(template_id));
            if (m != null) {
                template = new ItemTemplates();
                this.setType(template.type = m.type);
                if (template.type < 3 || template.type > 7 || (idtemplate >= 613 && idtemplate <= 615)) {
                    this.clazz = (byte) clazz;
                } else {
                    this.clazz = m.clazz;
                }
                this.durable = m.durable;
                this.mDurable = (short) (this.durable * 10);
            }
        } catch (final Exception ex) {
        }
        this.classTemplate = classTemplate;
        this.tempateID = idtemplate;
    }

    public String getInfoSend2Char() {
        return "";
    }

    public Item() {
        super((byte) 3);
        this.dbAttribute = null;
        this.dbInfo = null;
        this.dbInfoKham = null;
        this.quanlity = 1;
        this.idItem = 0;
        this.nhadSock = 0;
        this.nSocAdd = 0;
        this.magic_physic = 2;
        this.minuteExist = 0;
        this.slotWearing = 0;
        this.idAnimal = -1;
        this.level = 0;
        this.mDurable = 0;
        this.atb = new short[33];
        this.plus = 0;
        this.hangItem = -1;
        this.pos = 1;
        this.beKick = 0;
        this.heItem = -1;
        this.lock = 0;
        this.idCountry = 0;
        this.prizeSell = 0;
        this.idSellMarket = -1;
        this.timeLoan = 0L;
        this.gemKham = new Vector<Short>();
        this.allAtb = new Hashtable<Short, InfoItemAttribute>();
        this.dbid = 0;
        this.belongUser = 0;
        this.isBoss = false;
        this.isItemShop = false;
        this.belongParty = 0;
        this.charSeal = "";
        this.charCanBuy = "";
        this.dateCreate = "";
        this.typetieu = 0;
        this.idNguHanh2 = -1;
        this.isItemDrop = 0;
        this.timeExpireHkl = 0L;
        this.id_dongxu_trung = -1;
        this.VALUE_NGUHANH_NEW_ATTRIBUTE = new short[][][]{{{1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2}, {1, 2, 3}}, {{4, 5, 6}, {4, 5, 6}, {4, 5, 6}, {1, 2, 3}, {4, 5, 6}}, {{7, 8, 9}, {7, 8, 9}, {7, 8, 9}, {2, 3, 4, 5}, {7, 8, 9}}};
        this.newAtb = new byte[10];
        this.indexWearing = 0;
        this.addMoreLevelSkill = new byte[15];
        this.lockAtb = new byte[3];
        this.switchAttNguhanh = 1000;
        this.resetItem = 5000;
        this.isUpdate = true;
        this.otherAtt = new short[80]; // so luong otherAtt
        this.oldKham = 0;
        this.myChar = "";
        this.dateCreate = Char.getDayTime(0L);
        this.GiaHan = 5;
    }

    public Item(final ItemTemplates template, final boolean isAddRandomValue, final int clazz, final int classTemplate, final int idtemplate) {
        super((byte) 3);
        this.dbAttribute = null;
        this.dbInfo = null;
        this.dbInfoKham = null;
        this.quanlity = 1;
        this.idItem = 0;
        this.nhadSock = 0;
        this.nSocAdd = 0;
        this.magic_physic = 2;
        this.minuteExist = 0;
        this.slotWearing = 0;
        this.idAnimal = -1;
        this.level = 0;
        this.mDurable = 0;
        this.atb = new short[33];
        this.plus = 0;
        this.hangItem = -1;
        this.pos = 1;
        this.beKick = 0;
        this.heItem = -1;
        this.lock = 0;
        this.idCountry = 0;
        this.prizeSell = 0;
        this.idSellMarket = -1;
        this.timeLoan = 0L;
        this.gemKham = new Vector<Short>();
        this.allAtb = new Hashtable<Short, InfoItemAttribute>();
        this.dbid = 0;
        this.belongUser = 0;
        this.isBoss = false;
        this.isItemShop = false;
        this.belongParty = 0;
        this.charSeal = "";
        this.charCanBuy = "";
        this.dateCreate = "";
        this.typetieu = 0;
        this.idNguHanh2 = -1;
        this.isItemDrop = 0;
        this.timeExpireHkl = 0L;
        this.id_dongxu_trung = -1;
        this.VALUE_NGUHANH_NEW_ATTRIBUTE = new short[][][]{{{1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2}, {1, 2, 3}}, {{4, 5, 6}, {4, 5, 6}, {4, 5, 6}, {1, 2, 3}, {4, 5, 6}}, {{7, 8, 9}, {7, 8, 9}, {7, 8, 9}, {2, 3, 4, 5}, {7, 8, 9}}};
        this.newAtb = new byte[10];
        this.indexWearing = 0;
        this.addMoreLevelSkill = new byte[15];
        this.lockAtb = new byte[3];
        this.switchAttNguhanh = 1000;
        this.resetItem = 5000;
        this.isUpdate = true;
        this.otherAtt = new short[80]; // so luong otherAtt
        this.oldKham = 0;
        this.myChar = "";
        this.clazz = template.type;
        this.durable = template.durable;
        this.mDurable = (short) (this.durable * 10);
        this.GiaHan = 5;
        for (byte i = 0; i < 10; ++i) {
            if (isAddRandomValue) {
                this.atb[i] = (short) Map.rand10(template.atb[i]);
            } else {
                this.atb[i] = template.atb[i];
            }
        }
        this.clazz = (byte) clazz;
        this.setType(template.type);
        this.classTemplate = classTemplate;
        this.tempateID = idtemplate;
        this.dateCreate = Char.getDayTime(0L);
        if (idtemplate == 728) {
            this.lockAtb[0] = 10;
        }
        if (idtemplate >= 745 && idtemplate <= 748) {
            int option, valueOption;
            switch (idtemplate) {
                case 745: {
                    option = 0;
                    valueOption = 5;
                    break;
                }
                case 746: {
                    option = 1;
                    valueOption = 5;
                    break;
                }
                case 747: {
                    option = 2;
                    valueOption = 10;
                    break;
                }
                case 748: {
                    option = 3;
                    valueOption = 30;
                    break;
                }
                default: {
                    option = 0;
                    valueOption = 5;
                    break;
                }
            }
            this.createOptionMatNa(option, valueOption);
        }
    }

    public String getInfoSave() {
        return String.valueOf(this.getDBInfo()) + "|" + this.getAttribute();
    }

    public void setInfo2HashAllAtb() {
        for (short i = 0; i < this.atb.length; ++i) {
            if (i < 10) {
                if (Item.attribShowedForType[this.getType()][i] && this.atb[i] > 0) {
                    InfoItemAttribute info = this.allAtb.get(i);
                    if (info == null) {
                        info = new InfoItemAttribute(i, this.atb[i]);
                    }
                    this.allAtb.put(i, info);
                }
            } else if (this.atb[i] > 0) {
                InfoItemAttribute info = this.allAtb.get(i);
                if (info == null) {
                    info = new InfoItemAttribute(i, this.atb[i]);
                }
                this.allAtb.put(i, info);
            }
        }
        int count = this.atb.length;
        for (short j = 0; j < this.newAtb.length; ++j) {
            if (this.newAtb[j] > 0) {
                InfoItemAttribute info2 = this.allAtb.get(j);
                if (info2 == null) {
                    info2 = new InfoItemAttribute(j, this.atb[j]);
                }
                this.allAtb.put(j, info2);
            }
        }
        count += this.newAtb.length;
        for (short j = 0; j < this.addMoreLevelSkill.length; ++j) {
            if (this.addMoreLevelSkill[j] > 0) {
                InfoItemAttribute info2 = this.allAtb.get(j);
                if (info2 == null) {
                    info2 = new InfoItemAttribute(j, this.atb[j]);
                }
                this.allAtb.put(j, info2);
            }
        }
        count += this.addMoreLevelSkill.length;
        for (short j = 0; j < this.lockAtb.length; ++j) {
            if (this.lockAtb[j] > 0) {
                this.allAtb.put(j, new InfoItemAttribute(j, this.atb[j]));
            }
        }
        count += this.lockAtb.length;
        for (short j = 0; j < this.otherAtt.length; ++j) {
            if (this.otherAtt[j] > 0) {
                InfoItemAttribute info2 = this.allAtb.get(j);
                if (info2 == null) {
                    info2 = new InfoItemAttribute(j, this.atb[j]);
                }
                this.allAtb.put(j, info2);
            }
        }
    }

    public int getValueUpByVuKhiThoiTrang(final int value, final Item itemVkThoiTrang) {
        if (!this.isWeapone() || this.isVukhiThoiTrang() || itemVkThoiTrang == null) {
            return 0;
        }
        final long pc = itemVkThoiTrang.otherAtt[55];
        return (int) (value * pc / 1000L);
    }

    public Vector<InfoItemAttribute> getInfoAtbItemWearing(final Char p) {
        final Vector<InfoItemAttribute> allAtb = new Vector<InfoItemAttribute>();
        if (Map.isModelClothes(this.getTemplate().atb[9])) {
            if (this.atb[0] > 0) {
                allAtb.add(new InfoItemAttribute((short) 58, this.atb[0]));
            }
            if (this.atb[1] > 0) {
                allAtb.add(new InfoItemAttribute((short) 59, this.atb[1]));
                allAtb.add(new InfoItemAttribute((short) 60, this.atb[1]));
            }
            if (this.atb[2] > 0) {
                allAtb.add(new InfoItemAttribute((short) 33, this.atb[2] / 1000));
            }
            if (this.atb[3] > 0) {
                allAtb.add(new InfoItemAttribute((short) 34, this.atb[3] / 1000));
            }
            if (this.tempateID == 577 || this.tempateID == 578) {
                allAtb.add(new InfoItemAttribute((short) 69, 500));
                allAtb.add(new InfoItemAttribute((short) 70, 500));
                allAtb.add(new InfoItemAttribute((short) 71, 500));
                allAtb.add(new InfoItemAttribute((short) 90, 500));
            }
            if (ItemLuckyDraw.isChoi(this.tempateID)) {
                for (byte i = 0; i < this.atb.length; ++i) {
                    if (i < 10) {
                        if (Item.attribShowedForType[this.getType()][i] && this.atb[i] > 0) {
                            allAtb.add(new InfoItemAttribute(i, this.atb[i]));
                            this.allAtb.put((short) i, new InfoItemAttribute(i, this.atb[i]));
                        }
                    } else if (this.atb[i] > 0) {
                        allAtb.add(new InfoItemAttribute(i, this.atb[i]));
                    }
                }
                int count = this.atb.length;
                for (byte j = 0; j < this.newAtb.length; ++j) {
                    if (this.newAtb[j] > 0) {
                        allAtb.add(new InfoItemAttribute((byte) (j + count), this.newAtb[j]));
                    }
                }
                count += this.newAtb.length;
                for (byte j = 0; j < this.addMoreLevelSkill.length; ++j) {
                    if (this.addMoreLevelSkill[j] > 0) {
                        allAtb.add(new InfoItemAttribute((byte) (j + count), this.addMoreLevelSkill[j]));
                    }
                }
                count += this.addMoreLevelSkill.length;
                for (byte j = 0; j < this.lockAtb.length; ++j) {
                    if (this.lockAtb[j] > 0) {
                        allAtb.add(new InfoItemAttribute((byte) (j + count), this.lockAtb[j]));
                    }
                }
                count += this.lockAtb.length;
                for (byte j = 0; j < this.otherAtt.length; ++j) {
                    if (this.otherAtt[j] > 0) {
                        allAtb.add(new InfoItemAttribute((byte) (j + count), this.otherAtt[j]));
                    }
                }
            }
        } else {
            long pc = 0L;
            if (p.itemVukhiThoiTrang != null && this.isWeapone()) {
                pc = p.itemVukhiThoiTrang.otherAtt[55];
            }
            for (byte k = 0; k < this.atb.length; ++k) {
                if (k < 10) {
                    if (Item.attribShowedForType[this.getType()][k] && this.atb[k] > 0) {
                        allAtb.add(new InfoItemAttribute(k, this.atb[k] + (int) (this.atb[k] * pc / 1000L)));
                        this.allAtb.put((short) k, new InfoItemAttribute(k, this.atb[k] + (int) (this.atb[k] * pc / 1000L)));
                    }
                } else if (this.atb[k] > 0) {
                    allAtb.add(new InfoItemAttribute(k, this.atb[k] + (int) (this.atb[k] * pc / 1000L)));
                }
            }
            int count2 = this.atb.length;
            for (byte l = 0; l < this.newAtb.length; ++l) {
                if (this.newAtb[l] > 0) {
                    allAtb.add(new InfoItemAttribute((byte) (l + count2), this.newAtb[l]));
                }
            }
            count2 += this.newAtb.length;
            for (byte l = 0; l < this.addMoreLevelSkill.length; ++l) {
                if (this.addMoreLevelSkill[l] > 0) {
                    allAtb.add(new InfoItemAttribute((byte) (l + count2), this.addMoreLevelSkill[l]));
                }
            }
            count2 += this.addMoreLevelSkill.length;
            for (byte l = 0; l < this.lockAtb.length; ++l) {
                if (this.lockAtb[l] > 0) {
                    allAtb.add(new InfoItemAttribute((byte) (l + count2), this.lockAtb[l]));
                }
            }
            count2 += this.lockAtb.length;
            for (byte l = 0; l < this.otherAtt.length; ++l) {
                if (this.otherAtt[l] > 0) {
                    allAtb.add(new InfoItemAttribute((byte) (l + count2), this.otherAtt[l]));
                }
            }
            if (p.itemVukhiThoiTrang != null && this.isWeapone()) {
                allAtb.add(new InfoItemAttribute((byte) (54 + count2), p.itemVukhiThoiTrang.otherAtt[54]));
                allAtb.add(new InfoItemAttribute((byte) (55 + count2), p.itemVukhiThoiTrang.otherAtt[55]));
                allAtb.add(new InfoItemAttribute((byte) (56 + count2), p.itemVukhiThoiTrang.otherAtt[56]));
            }
        }
        return allAtb;
    }

    public Vector<InfoItemAttribute> getInfoAtbItem() {
        final Vector<InfoItemAttribute> allAtb = new Vector<InfoItemAttribute>();
        if (Map.isModelClothes(this.getTemplate().atb[9])) {
            if (this.atb[0] > 0) {
                allAtb.add(new InfoItemAttribute((short) 58, this.atb[0]));
            }
            if (this.atb[1] > 0) {
                allAtb.add(new InfoItemAttribute((short) 59, this.atb[1]));
                allAtb.add(new InfoItemAttribute((short) 60, this.atb[1]));
            }
            if (this.atb[2] > 0) {
                allAtb.add(new InfoItemAttribute((short) 33, this.atb[2] / 1000));
            }
            if (this.atb[3] > 0) {
                allAtb.add(new InfoItemAttribute((short) 34, this.atb[3] / 1000));
            }
            if (this.tempateID == 577 || this.tempateID == 578) {
                allAtb.add(new InfoItemAttribute((short) 69, 500));
                allAtb.add(new InfoItemAttribute((short) 70, 500));
                allAtb.add(new InfoItemAttribute((short) 71, 500));
                allAtb.add(new InfoItemAttribute((short) 90, 500));
            }
            if (this.tempateID == 725 || this.tempateID == 726) {
                allAtb.add(new InfoItemAttribute((short) 118, 500));
            }
            // TODOO Thêm Option áo TT
            
            if (this.tempateID == 743 || this.tempateID == 744) {
                allAtb.add(new InfoItemAttribute((short) 119, 100));
            }
            if (ItemLuckyDraw.isChoi(this.tempateID) || this.isMatna() || this.isCanh() || this.isManhSuuTap() ) {
                for (short i = 0; i < this.atb.length; ++i) {
                    if (i < 10) {
                        if (Item.attribShowedForType[this.getType()][i] && this.atb[i] > 0) {
                            allAtb.add(new InfoItemAttribute(i, this.atb[i]));
                            this.allAtb.put(i, new InfoItemAttribute(i, this.atb[i]));
                        }
                    } else if (this.atb[i] > 0) {
                        allAtb.add(new InfoItemAttribute(i, this.atb[i]));
                    }
                }
                int count = this.atb.length;
                for (short j = 0; j < this.newAtb.length; ++j) {
                    if (this.newAtb[j] > 0) {
                        allAtb.add(new InfoItemAttribute((short) (j + count), this.newAtb[j]));
                    }
                }
                count += this.newAtb.length;
                for (short j = 0; j < this.addMoreLevelSkill.length; ++j) {
                    if (this.addMoreLevelSkill[j] > 0) {
                        allAtb.add(new InfoItemAttribute((short) (j + count), this.addMoreLevelSkill[j]));
                    }
                }
                count += this.addMoreLevelSkill.length;
                for (short j = 0; j < this.lockAtb.length; ++j) {
                    if (this.lockAtb[j] > 0) {
                        allAtb.add(new InfoItemAttribute((short) (j + count), this.lockAtb[j]));
                    }
                }
                count += this.lockAtb.length;
                for (byte k = 0; k < this.otherAtt.length; ++k) {
                    if (this.otherAtt[k] > 0) {
                        allAtb.add(new InfoItemAttribute((short) (k + count), this.otherAtt[k]));
                    }
                }
            }
        } else {
            for (short i = 0; i < this.atb.length; ++i) {
                if (i < 10) {
                    if (Item.attribShowedForType[this.getType()][i] && this.atb[i] > 0) {
                        allAtb.add(new InfoItemAttribute(i, this.atb[i]));
                        this.allAtb.put(i, new InfoItemAttribute(i, this.atb[i]));
                    }
                } else if (this.atb[i] > 0) {
                    allAtb.add(new InfoItemAttribute(i, this.atb[i]));
                }
            }
            int count = this.atb.length;
            for (short j = 0; j < this.newAtb.length; ++j) {
                if (this.newAtb[j] > 0) {
                    allAtb.add(new InfoItemAttribute((short) (j + count), this.newAtb[j]));
                }
            }
            count += this.newAtb.length;
            for (short j = 0; j < this.addMoreLevelSkill.length; ++j) {
                if (this.addMoreLevelSkill[j] > 0) {
                    allAtb.add(new InfoItemAttribute((short) (j + count), this.addMoreLevelSkill[j]));
                }
            }
            count += this.addMoreLevelSkill.length;
            for (short j = 0; j < this.lockAtb.length; ++j) {
                if (this.lockAtb[j] > 0) {
                    allAtb.add(new InfoItemAttribute((short) (j + count), this.lockAtb[j]));
                }
            }
            count += this.lockAtb.length;
            for (short j = 0; j < this.otherAtt.length; ++j) {
                if (this.otherAtt[j] > 0) {
                    allAtb.add(new InfoItemAttribute((short) (j + count), this.otherAtt[j]));
                }
            }
        }
        return allAtb;
    }

    public String getInfoOptionPet() {
        String info = "";
        NameAttributeItem nAtt = null;
        for (int i = 0; i < this.newAtb.length; ++i) {
            if (this.newAtb[i] != 0) {
                nAtt = ItemTemplates.ALL_NAME_ATTRIBUTE_ITEM.get(i + this.atb.length);
                info = String.valueOf(info) + "\n" + nAtt.name + ": " + nAtt.getValue(this.newAtb[i]);
            }
        }
        for (int i = 0; i < this.addMoreLevelSkill.length; ++i) {
            if (this.addMoreLevelSkill[i] != 0) {
                info = String.valueOf(info) + "\n" + ItemTemplates.ALL_NAME_ATTRIBUTE_ITEM.get(i + this.atb.length + this.newAtb.length).name + " " + this.addMoreLevelSkill[i];
            }
        }
        for (int i = 0; i < this.lockAtb.length; ++i) {
            if (this.lockAtb[i] != 0) {
                nAtt = ItemTemplates.ALL_NAME_ATTRIBUTE_ITEM.get(i + this.atb.length + this.newAtb.length + this.addMoreLevelSkill.length);
                info = String.valueOf(info) + "\n" + nAtt.name + ": " + nAtt.getValue(this.lockAtb[i]);
            }
        }
        if (this.atb[2] != 0) {
            nAtt = ItemTemplates.ALL_NAME_ATTRIBUTE_ITEM.get(2);
            info = String.valueOf(info) + "\n" + nAtt.name + ": " + nAtt.getValue(this.atb[2]);
        }
        for (byte j = 0; j < this.otherAtt.length; ++j) {
            if (this.otherAtt[j] != 0) {
                nAtt = ItemTemplates.ALL_NAME_ATTRIBUTE_ITEM.get(j + this.atb.length + this.newAtb.length + this.addMoreLevelSkill.length + this.lockAtb.length);
                info = String.valueOf(info) + "\n" + nAtt.name + ": " + nAtt.getValue(this.otherAtt[j]);
            }
        }
        return info;
    }

    public String getAttribute() {
        String info = new StringBuilder().append(this.atb[0]).toString();
        for (int i = 1; i < this.atb.length; ++i) {
            info = String.valueOf(info) + "," + this.atb[i];
        }
        for (int i = 0; i < this.newAtb.length; ++i) {
            info = String.valueOf(info) + "," + this.newAtb[i];
        }
        for (int i = 0; i < this.addMoreLevelSkill.length; ++i) {
            info = String.valueOf(info) + "," + this.addMoreLevelSkill[i];
        }
        for (int i = 0; i < this.lockAtb.length; ++i) {
            info = String.valueOf(info) + "," + this.lockAtb[i];
        }
        for (byte j = 0; j < this.otherAtt.length; ++j) {
            info = String.valueOf(info) + "," + this.otherAtt[j];
        }
        return info;
    }

    public String getGemKham() {
        String info = "";
        if (this.gemKham.size() > 0) {
            info = String.valueOf(info) + this.gemKham.get(0);
            for (short i = 1; i < this.gemKham.size(); ++i) {
                info = String.valueOf(info) + "," + this.gemKham.get(i);
            }
        }
        return info;
    }

    public void initGemKham(final String info) {
        try {
            final String[] data = Char.split(info, ",");
            for (short i = 0; i < data.length; ++i) {
                this.gemKham.add(Short.parseShort(data[i]));
            }
        } catch (final Exception ex) {
        }
    }

    public void switchAttNguHanh() {
        for (int i = 30; i < 33; ++i) {
            if (this.atb[i] > 0) {
                if (i == 30) {
                    this.atb[i - 2] = ((this.atb[i - 2] > this.atb[i]) ? this.atb[i - 2] : this.atb[i]);
                } else {
                    this.atb[i - 1] = ((this.atb[i - 1] > this.atb[i]) ? this.atb[i - 1] : this.atb[i]);
                }
                this.atb[i] = 0;
            }
        }
    }

    public int countItemDuplicate(final Item it) {
        if (this.tempateID == it.tempateID && this.clazz == it.clazz && it.dateCreate.equals(this.dateCreate) && this.colorName == it.colorName && this.hangItem == it.hangItem && this.heItem == it.heItem && this.magic_physic == it.magic_physic) {
            return 1;
        }
        return 0;
    }

    public boolean compareItem(final Item it) {
        if (this.clazz != it.clazz || this.tempateID != it.tempateID || this.plus != it.plus || this.level != it.level || this.nhadSock != it.nhadSock || this.nSocAdd != it.nSocAdd || this.magic_physic != it.magic_physic || this.colorName != it.colorName || this.hangItem != it.hangItem || this.heItem != it.heItem || !this.charSeal.equals(it.charSeal)) {
            return false;
        }
        for (int i = 0; i < this.atb.length; ++i) {
            if (this.atb[i] != it.atb[i]) {
                return false;
            }
        }
        for (int i = 0; i < this.newAtb.length; ++i) {
            if (this.newAtb[i] != it.newAtb[i]) {
                return false;
            }
        }
        for (int i = 0; i < this.addMoreLevelSkill.length; ++i) {
            if (this.addMoreLevelSkill[i] != it.addMoreLevelSkill[i]) {
                return false;
            }
        }
        for (int i = 0; i < this.lockAtb.length; ++i) {
            if (this.lockAtb[i] != it.lockAtb[i]) {
                return false;
            }
        }
        for (int i = 0; i < this.otherAtt.length; ++i) {
            if (this.otherAtt[i] != it.otherAtt[i]) {
                return false;
            }
        }
        return true;
    }

    public void initInfoFromDB() {
        if (this.dbInfo != null) {
            final String[] data = Char.split(this.dbInfo, ",");
            this.clazz = Byte.parseByte(data[0]);
            final int itemTemplate = Short.parseShort(data[1]);
            try {
                this.setTemplate(itemTemplate, this.clazz, this.clazz, itemTemplate);
            } catch (final Exception e) {
                e.printStackTrace();
                System.out.println(String.valueOf(this.dbInfo) + " >>> " + itemTemplate);
            }
            this.place = Byte.parseByte(data[2]);
            this.durable = Short.parseShort(data[3]);
            this.mDurable = Short.parseShort(data[4]);
            this.plus = Byte.parseByte(data[5]);
            this.level = Short.parseShort(data[6]);
            if (this.level == -1 || this.level == 0) {
                this.level = this.getTemplate().level;
            }
            if (this.mDurable == -1) {
                this.mDurable = (short) (this.durable * 10);
            }
            try {
                this.nhadSock = Byte.parseByte(data[7]);
                this.nSocAdd = Byte.parseByte(data[8]);
                this.magic_physic = Byte.parseByte(data[9]);
                this.minuteExist = Integer.parseInt(data[10]);
                this.colorName = Byte.parseByte(data[11]);
                this.hangItem = Byte.parseByte(data[12]);
                this.heItem = Byte.parseByte(data[13]);
                this.lock = Byte.parseByte(data[14]);
                this.charSeal = data[15];
                this.idAnimal = Integer.parseInt(data[16]);
                this.slotWearing = Byte.parseByte(data[17]);
                this.oldKham = Byte.parseByte(data[18]);
                this.dateCreate = data[19];
                this.pos = Byte.parseByte(data[20]);
                this.timeLoan = Long.parseLong(data[21]);
                this.typetieu = Byte.parseByte(data[22]);
                this.idNguHanh2 = Byte.parseByte(data[23]);
                this.isItemDrop = Byte.parseByte(data[24]);
                this.idSellMarket = Integer.parseInt(data[25]);
                this.isItemShop = (Byte.parseByte(data[26]) == 1);
                this.timeExpireHkl = Long.parseLong(data[27]);
                this.id_dongxu_trung = Integer.parseInt(data[28]);
                this.quanlity = Integer.parseInt(data[29]);
                this.TinhLuyen = Integer.parseInt(data[30]);
                this.GiaHan = Integer.parseInt(data[31]);
                this.CaiTao = Integer.parseInt(data[32]);
            } catch (final Exception ex) {
            }
            if (this.typetieu > 6) {
                this.typetieu = 0;
            }
            if ((this.minuteExist == 0 && this.timeLoan > 0L) || (this.minuteExist < 10 && this.minuteExist > 0)) {
                this.minuteExist = this.getTemplate().ndayLoan;
            }
        }
    }

    public boolean isItemGomNhom() {
        return this.isTienDon();
    }

    public boolean isTienDon() {
        return this.tempateID == 739;
    }

    public boolean dogetbackChoiDuaTop() {
        try {
            if (this.minuteExist > 0 && this.isChoiSell() && Char.split(this.dateCreate, " ")[0].equals("2019-10-04")) {
                return true;
            }
        } catch (final Exception ex) {
        }
        return false;
    }

    public void doResetTimeChoiDuaTop() {
        try {
            if (this.minuteExist > 0) {
                if (this.isChoiSell() && Char.split(this.dateCreate, " ")[0].equals("2019-10-04")) {
                    this.minuteExist = 86400;
                    this.timeLoan = System.currentTimeMillis();
                }
                System.out.println(String.valueOf(this.dateCreate) + ": so phut thue sau: " + this.getName() + " " + this.minuteExist);
            }
        } catch (final Exception ex) {
        }
    }

    public boolean isHetHan() {
        return this.getMinuteLoan(false) <= 0 && this.timeLoan > 0L;
    }

    public int getMinuteLoan(final boolean isSendclient) {
        if (Map.isItemThanThu(this.getTemplate().type) && this.timeExpireHkl > 0L && System.currentTimeMillis() - this.timeExpireHkl >= 0L) {
            this.minuteExist = 1;
            return 0;
        }
        final long time = this.minuteExist;
        if (this.timeLoan > 0L && System.currentTimeMillis() - this.timeLoan >= time * 60000L) {
            return 0;
        }
        return isSendclient ? 0 : this.minuteExist;
    }

    public void initAtt(final int[] material) {
        try {
            this.addMoreLevelSkill = new byte[15];
            this.newAtb = new byte[10];
            int max = 0;
            int index = 3;
            if (material != null) {
                for (int i = 0; i < material.length; ++i) {
                    if (material[i] > max) {
                        max = material[i];
                        index = i;
                    }
                }
            }
            byte[][] pc = null;
            int id = Map.r.nextInt(2);
            pc = new byte[][]{new byte[1], new byte[1], new byte[1], {5, 6}, {7, 8}, {9}};
            this.newAtb[id] = pc[index][Map.r.nextInt(pc[index].length)];
            id = Map.r.nextInt(4) + 2;
            pc = new byte[][]{new byte[1], new byte[1], new byte[1], {1, 1}, {2, 3}, {4, 4}};
            this.newAtb[id] = pc[index][Map.r.nextInt(pc[index].length)];
            id = Map.r.nextInt(3) + 6;
            if (id == 6) {
                final short type = this.getType();
                if ((type >= 3 && type <= 7) || type == 8 || type == 9) {
                    final int[] nskill = {8, 8, 10, 8, 8};
                    this.addMoreLevelSkill[Map.r.nextInt(nskill[this.clazz])] = 1;
                    this.setAnotherSkill();
                } else {
                    pc = new byte[][]{new byte[1], new byte[1], new byte[1], {1}, {2}, {3}};
                    this.newAtb[id + Map.r.nextInt(2) + 1] = pc[index][0];
                }
            } else {
                pc = new byte[][]{new byte[1], new byte[1], new byte[1], {1}, {2}, {3}};
                this.newAtb[id] = pc[index][0];
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void setSoluong(final int sl) {
    }

    public void setXuSell(final int xu) {
    }

    public void setLuongSell(final int l) {
    }

    public int getSoluong() {
        return 0;
    }

    public int getXuSell() {
        return 0;
    }

    public int getLuongSell() {
        return 0;
    }

    public void setAnotherSkill() {
        if (this.addMoreLevelSkill[3] > 0 && this.clazz == 0) {
            this.addMoreLevelSkill[3] = 0;
            this.addMoreLevelSkill[3 + Map.r.nextInt(4) + 1] = 1;
        }
    }

    public void lockItem(final int[] material) {
        try {
            int max = 0;
            int index = 3;
            if (material != null) {
                for (int i = 0; i < material.length; ++i) {
                    if (material[i] > max) {
                        max = material[i];
                        index = i;
                    }
                }
            }
            final byte[][] pc = {{1, 2, 3, 4, 5, 6, 1, 2, 1, 2, 1, 2, 1, 3, 3, 3, 3, 4, 4, 5, 5, 6, 6}, {1, 2, 3, 4, 5, 6, 1, 2, 1, 2, 1, 2, 1, 3, 3, 3, 3, 4, 4, 5, 5, 6, 6}, {1, 2, 3, 4, 5, 6, 1, 2, 1, 2, 1, 2, 1, 3, 3, 3, 3, 4, 4, 5, 5, 6, 6}, {1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5}, {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 11, 12, 12, 12, 12, 13, 13, 13, 14, 14, 15, 16, 17}, {18, 18, 18, 18, 18, 18, 18, 18, 17, 17, 17, 17, 17, 17, 17, 17}};
            if (material == null) {
                pc[3] = new byte[]{1, 2, 3};
            }
            final short type = this.getType();
            if ((type >= 3 && type <= 7) || type == 8 || type == 9) {
                pc[5] = new byte[]{18, 19, 20, 21, 18, 18, 18, 18, 18, 18, 18, 19, 19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 17, 17, 17};
                if (this.lock == 0 && this.lockAtb[0] > 0) {
                    final byte[] lockAtb = this.lockAtb;
                    final int n = 0;
                    ++lockAtb[n];
                } else {
                    this.lockAtb[0] = pc[index][Map.r.nextInt(pc[index].length)];
                    if (this.lock == 0 && this.lockAtb[0] > 19) {
                        this.lockAtb[0] = 19;
                    }
                }
            } else {
                byte tempPC = pc[index][Map.r.nextInt(pc[index].length)];
                final int ran = Map.r.nextInt(1000);
                if (index == 5 && material != null) {
                    if (ran <= 0) {
                        tempPC = 21;
                    } else if (ran <= 5) {
                        tempPC = 20;
                    } else if (ran <= 15) {
                        tempPC = 19;
                    }
                }
                if (this.magic_physic == 0) {
                    if (this.lock == 0 && this.lockAtb[1] > 0 && this.lockAtb[2] > 0) {
                        final byte[] lockAtb2 = this.lockAtb;
                        final int n2 = 1;
                        ++lockAtb2[n2];
                        final byte[] lockAtb3 = this.lockAtb;
                        final int n3 = 2;
                        ++lockAtb3[n3];
                    } else {
                        this.lockAtb[1] = tempPC;
                        if (this.lock == 0 && this.lockAtb[1] >= 17) {
                            this.lockAtb[1] = 15;
                        }
                        this.lockAtb[2] = (byte) (tempPC / 5);
                    }
                } else if (this.magic_physic == 1) {
                    if (this.lock == 0 && this.lockAtb[1] > 0 && this.lockAtb[2] > 0) {
                        final byte[] lockAtb4 = this.lockAtb;
                        final int n4 = 1;
                        ++lockAtb4[n4];
                        final byte[] lockAtb5 = this.lockAtb;
                        final int n5 = 2;
                        ++lockAtb5[n5];
                    } else {
                        this.lockAtb[1] = (byte) (tempPC / 5);
                        this.lockAtb[2] = tempPC;
                        if (this.lock == 0 && this.lockAtb[2] >= 17) {
                            this.lockAtb[2] = 15;
                        }
                    }
                }
            }
            if (this.lockAtb[1] > 21) {
                this.lockAtb[1] = 21;
            }
            if (this.lockAtb[2] > 21) {
                this.lockAtb[2] = 21;
            }
            if (this.lockAtb[0] > 21) {
                this.lockAtb[0] = 21;
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void lockItem(final int lvNgoc) {
        try {
            final int max = 0;
            final int index = lvNgoc;
            final byte[][] pc = {{1, 2, 3, 4, 5, 6, 1, 2, 1, 2, 1, 2, 1, 3, 3, 3, 3, 4, 4, 5, 5, 6, 6}, {1, 2, 3, 4, 5, 6, 1, 2, 1, 2, 1, 2, 1, 3, 3, 3, 3, 4, 4, 5, 5, 6, 6}, {1, 2, 3, 4, 5, 6, 1, 2, 1, 2, 1, 2, 1, 3, 3, 3, 3, 4, 4, 5, 5, 6, 6}, {1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5}, {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 11, 12, 12, 12, 12, 13, 13, 13, 14, 14, 15, 16, 17}, {18, 18, 18, 18, 18, 18, 18, 18, 17, 17, 17, 17, 17, 17, 17, 17}};
            final short type = this.getType();
            if ((type >= 3 && type <= 7) || type == 8 || type == 9) {
                pc[5] = new byte[]{18, 19, 20, 21, 18, 18, 18, 18, 18, 18, 18, 19, 19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 17, 17, 17};
                if (this.lock == 0 && this.lockAtb[0] > 0) {
                    final byte[] lockAtb = this.lockAtb;
                    final int n = 0;
                    ++lockAtb[n];
                } else {
                    this.lockAtb[0] = pc[index][Map.r.nextInt(pc[index].length)];
                    if (this.lock == 0 && this.lockAtb[0] > 19) {
                        this.lockAtb[0] = 19;
                    }
                }
            } else {
                pc[5] = new byte[]{18, 19, 20, 21, 22, 20, 19, 19, 21, 20, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 19, 19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 17, 17, 17, 17};
                byte tempPC = pc[index][Map.r.nextInt(pc[index].length)];
                final int ran = Map.r.nextInt(1000);
                final int[][] pcUp = {new int[6], new int[6], new int[6], {0, 0, 0, 2, 10, 30}, {0, 0, 0, 5, 25, 75}, {0, 0, 0, 10, 50, 150}};
                if (ran <= pcUp[index][3]) {
                    tempPC = 25;
                } else if (ran <= pcUp[index][4]) {
                    tempPC = 24;
                } else if (ran <= pcUp[index][5]) {
                    tempPC = 23;
                }
                if (this.magic_physic == 0) {
                    if (this.lock == 0 && this.lockAtb[1] > 0 && this.lockAtb[2] > 0) {
                        final byte[] lockAtb2 = this.lockAtb;
                        final int n2 = 1;
                        ++lockAtb2[n2];
                        final byte[] lockAtb3 = this.lockAtb;
                        final int n3 = 2;
                        ++lockAtb3[n3];
                    } else {
                        this.lockAtb[1] = tempPC;
                        if (this.lock == 0 && this.lockAtb[1] >= 17) {
                            this.lockAtb[1] = 15;
                        }
                        this.lockAtb[2] = (byte) (tempPC / 5);
                    }
                } else if (this.magic_physic == 1) {
                    if (this.lock == 0 && this.lockAtb[1] > 0 && this.lockAtb[2] > 0) {
                        final byte[] lockAtb4 = this.lockAtb;
                        final int n4 = 1;
                        ++lockAtb4[n4];
                        final byte[] lockAtb5 = this.lockAtb;
                        final int n5 = 2;
                        ++lockAtb5[n5];
                    } else {
                        this.lockAtb[1] = (byte) (tempPC / 5);
                        this.lockAtb[2] = tempPC;
                        if (this.lock == 0 && this.lockAtb[2] >= 17) {
                            this.lockAtb[2] = 15;
                        }
                    }
                }
            }
            if (this.lockAtb[1] > 21) {
                this.lockAtb[1] = 21;
            }
            if (this.lockAtb[2] > 21) {
                this.lockAtb[2] = 21;
            }
            if (this.lockAtb[0] > 21) {
                this.lockAtb[0] = 21;
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void reFixItemAnimal(final String charname) {
        if (this.magic_physic == 2) {
            this.magic_physic = (byte) Map.r.nextInt(2);
        }
        final String info = String.valueOf(this.getName()) + "_" + this.plus + "_" + this.getInfoSave();
        if (this.magic_physic == 0) {
            if (this.atb[6] < this.atb[1]) {
                this.atb[6] = this.atb[1];
                this.atb[1] = (short) (this.atb[6] / 10);
                final short type = this.getType();
                if (type != 17 && type != 15) {
                    if (this.lockAtb[1] < this.lockAtb[2]) {
                        this.lockAtb[1] = this.lockAtb[2];
                    }
                    this.lockAtb[2] = (byte) (this.lockAtb[1] / 5);
                }
                Database.instance.saveOrtherLog("", charname, info, "refix");
            }
        } else if (this.magic_physic == 1 && this.atb[1] < this.atb[6]) {
            this.atb[1] = this.atb[6];
            this.atb[6] = (short) (this.atb[1] / 10);
            final short type = this.getType();
            if (type != 17 && type != 15) {
                if (this.lockAtb[2] < this.lockAtb[1]) {
                    this.lockAtb[2] = this.lockAtb[1];
                }
                this.lockAtb[1] = (byte) (this.lockAtb[2] / 5);
            }
            Database.instance.saveOrtherLog("", charname, info, "refix");
        }
    }

    public void lockItemAnimal(final int hang) {
        try {
            final int index = hang;
            final byte[][] pc = {{1, 2, 3, 4, 5, 6, 1, 2, 1, 2, 1, 2, 1, 3, 3, 3, 3, 4, 4, 5, 5, 6, 6}, {1, 2, 3, 4, 5, 6, 1, 2, 1, 2, 1, 2, 1, 3, 3, 3, 3, 4, 4, 5, 5, 6, 6}, {1, 2, 3, 4, 5, 6, 1, 2, 1, 2, 1, 2, 1, 3, 3, 3, 3, 4, 4, 5, 5, 6, 6}, {1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5}, {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 11, 12, 12, 12, 12, 13, 13, 13, 14, 14, 15, 16, 17}, {18, 19, 18, 18, 18, 19, 18, 18, 18, 19, 19, 19, 19, 18, 19, 18, 18, 19, 18, 18, 18}};
            final short type = this.getType();
            if (type == 17 || type == 15) {
                if (this.lock == 0 && this.lockAtb[0] > 0) {
                    final byte[] lockAtb = this.lockAtb;
                    final int n = 0;
                    ++lockAtb[n];
                } else {
                    final byte tempPC = pc[index][Map.r.nextInt(pc[index].length)];
                    this.lockAtb[0] = tempPC;
                    if (this.lock == 0 && this.lockAtb[0] > 19) {
                        this.lockAtb[0] = 19;
                    }
                }
            } else {
                final byte tempPC = pc[index][Map.r.nextInt(pc[index].length)];
                if (this.magic_physic == 0) {
                    if (this.lock == 0 && this.lockAtb[1] > 0 && this.lockAtb[2] > 0) {
                        final byte[] lockAtb2 = this.lockAtb;
                        final int n2 = 1;
                        ++lockAtb2[n2];
                        final byte[] lockAtb3 = this.lockAtb;
                        final int n3 = 2;
                        ++lockAtb3[n3];
                    } else {
                        this.lockAtb[1] = tempPC;
                        this.lockAtb[2] = (byte) (tempPC / 5);
                    }
                } else if (this.magic_physic == 1) {
                    if (this.lock == 0 && this.lockAtb[1] > 0 && this.lockAtb[2] > 0) {
                        final byte[] lockAtb4 = this.lockAtb;
                        final int n4 = 1;
                        ++lockAtb4[n4];
                        final byte[] lockAtb5 = this.lockAtb;
                        final int n5 = 2;
                        ++lockAtb5[n5];
                    } else {
                        this.lockAtb[1] = (byte) (tempPC / 5);
                        this.lockAtb[2] = tempPC;
                    }
                }
            }
            if (this.lockAtb[1] > 21) {
                this.lockAtb[1] = 21;
            }
            if (this.lockAtb[2] > 21) {
                this.lockAtb[2] = 21;
            }
            if (this.lockAtb[0] > 21) {
                this.lockAtb[0] = 21;
            }
            this.resetItem = 5000;
        } catch (final Exception ex) {
        }
    }

    public void lockItemAnimal(final byte[] material) {
        try {
            int max = 0;
            int index = 3;
            if (material != null) {
                for (int i = 0; i < material.length; ++i) {
                    if (material[i] > max) {
                        max = material[i];
                        index = i;
                    }
                }
            }
            final byte[][] pc = {{1, 2, 3, 4, 5, 6, 1, 2, 1, 2, 1, 2, 1, 3, 3, 3, 3, 4, 4, 5, 5, 6, 6}, {1, 2, 3, 4, 5, 6, 1, 2, 1, 2, 1, 2, 1, 3, 3, 3, 3, 4, 4, 5, 5, 6, 6}, {1, 2, 3, 4, 5, 6, 1, 2, 1, 2, 1, 2, 1, 3, 3, 3, 3, 4, 4, 5, 5, 6, 6}, {1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5}, {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 11, 12, 12, 12, 12, 13, 13, 13, 14, 14, 15, 16, 17}, {18, 19, 18, 18, 18, 19, 18, 18, 18, 19, 19, 19, 19, 18, 19, 18, 18, 19, 18, 18, 18}};
            if (material == null) {
                pc[3] = new byte[]{1, 2, 3};
            }
            final short type = this.getType();
            if (type == 17 || type == 15) {
                if (this.lock == 0 && this.lockAtb[0] > 0) {
                    final byte[] lockAtb = this.lockAtb;
                    final int n = 0;
                    ++lockAtb[n];
                } else {
                    byte tempPC = pc[index][Map.r.nextInt(pc[index].length)];
                    final int ran = Map.r.nextInt(1000);
                    if (index == 5 && material != null) {
                        if (ran <= 10) {
                            tempPC = 21;
                        } else if (ran <= 30) {
                            tempPC = 20;
                        }
                    }
                    this.lockAtb[0] = tempPC;
                    if (this.lock == 0 && this.lockAtb[0] > 19) {
                        this.lockAtb[0] = 19;
                    }
                }
            } else {
                byte tempPC = pc[index][Map.r.nextInt(pc[index].length)];
                final int ran = Map.r.nextInt(1000);
                if (index == 5 && material != null) {
                    if (ran <= 10) {
                        tempPC = 21;
                    } else if (ran <= 20) {
                        tempPC = 20;
                    }
                }
                if (this.magic_physic == 0) {
                    if (this.lock == 0 && this.lockAtb[1] > 0 && this.lockAtb[2] > 0) {
                        final byte[] lockAtb2 = this.lockAtb;
                        final int n2 = 1;
                        ++lockAtb2[n2];
                        final byte[] lockAtb3 = this.lockAtb;
                        final int n3 = 2;
                        ++lockAtb3[n3];
                    } else {
                        this.lockAtb[1] = tempPC;
                        this.lockAtb[2] = (byte) (tempPC / 5);
                    }
                } else if (this.magic_physic == 1) {
                    if (this.lock == 0 && this.lockAtb[1] > 0 && this.lockAtb[2] > 0) {
                        final byte[] lockAtb4 = this.lockAtb;
                        final int n4 = 1;
                        ++lockAtb4[n4];
                        final byte[] lockAtb5 = this.lockAtb;
                        final int n5 = 2;
                        ++lockAtb5[n5];
                    } else {
                        this.lockAtb[1] = (byte) (tempPC / 5);
                        this.lockAtb[2] = tempPC;
                    }
                }
            }
            if (this.lockAtb[1] > 21) {
                this.lockAtb[1] = 21;
            }
            if (this.lockAtb[2] > 21) {
                this.lockAtb[2] = 21;
            }
            if (this.lockAtb[0] > 21) {
                this.lockAtb[0] = 21;
            }
            this.resetItem = 5000;
        } catch (final Exception ex) {
        }
    }

    public int[] getIDMaterialGetBack() {
        final byte[] id = CreateItemTemplate.getIDMAterial(this.getTemplate().type, this.magic_physic, 0);
        final int[] idstart = {CreateItemTemplate.startIDMaterial[id[0]], CreateItemTemplate.startIDMaterial[id[1]]};
        return idstart;
    }

    public boolean isHoanMy() {
        return this.colorName == Item.COLOR_GREEN;
    }

    public boolean isDo() {
        return this.colorName == Item.COLOR_RED;
    }

    public boolean isXanh() {
        return this.colorName == Item.COLOR_BLUE;
    }

    public boolean isTrang() {
        return this.colorName == Item.COLOR_WHITE;
    }

    public boolean isTim() {
        return this.colorName == Item.COLOR_MAGENTA;
    }

    public boolean isNhatpham() {
        return this.hangItem == 1;
    }

    public boolean isNhipham() {
        return this.hangItem == 2;
    }

    public boolean isTampham() {
        return this.hangItem == 3;
    }

    public boolean isTupham() {
        return this.hangItem == 4;
    }

    public boolean isKoPham() {
        return this.hangItem > 4;
    }

    public int getTotalMaterial(final boolean special) {
        int total = 0;
        int[] a = {2, 3, 4, 5, 6, 7, 8};
        if (special) {
            a = new int[]{3, 5, 7, 9, 11, 13};
        }
        if (this.level >= 50) {
            if (Map.isWeapone(this.getTemplate().type)) {
                total = a[(this.level - 51) / 10];
            } else {
                total = a[(this.level - 50) / 10];
            }
        }
        return total;
    }

    public boolean isWeapone() {
        return Map.isWeapone(this.getTemplate().type);
    }

    public boolean isHaveAttLock() {
        return this.lockAtb[0] != 0 || this.lockAtb[1] != 0 || this.lockAtb[2] != 0 || this.colorName != 1;
    }

    public boolean changLevelSkill() {
        final short type = this.getType();
        if ((type >= 3 && type <= 7) || type == 8 || type == 9) {
            final int[] nskill = {8, 8, 10, 8, 8};
            this.addMoreLevelSkill = new byte[15];
            final int id = Map.r.nextInt(nskill[this.clazz]);
            this.addMoreLevelSkill[id] = 1;
            this.setAnotherSkill();
            return true;
        }
        return false;
    }

    public String getDBInfo() {
        String info = new StringBuilder().append(this.clazz).toString();
        info = String.valueOf(info) + "," + this.getTemplate().id;
        info = String.valueOf(info) + "," + this.place;
        info = String.valueOf(info) + "," + this.durable;
        info = String.valueOf(info) + "," + this.mDurable;
        info = String.valueOf(info) + "," + this.plus;
        info = String.valueOf(info) + "," + this.level;
        info = String.valueOf(info) + "," + this.nhadSock;
        info = String.valueOf(info) + "," + this.nSocAdd;
        info = String.valueOf(info) + "," + this.magic_physic;
        info = String.valueOf(info) + "," + this.minuteExist;
        info = String.valueOf(info) + "," + this.colorName;
        info = String.valueOf(info) + "," + this.hangItem;
        info = String.valueOf(info) + "," + this.heItem;
        info = String.valueOf(info) + "," + this.lock;
        info = String.valueOf(info) + "," + this.charSeal;
        info = String.valueOf(info) + "," + this.idAnimal;
        info = String.valueOf(info) + "," + this.slotWearing;
        info = String.valueOf(info) + "," + this.oldKham;
        info = String.valueOf(info) + "," + this.dateCreate;
        info = String.valueOf(info) + "," + this.pos;
        info = String.valueOf(info) + "," + this.timeLoan;
        info = String.valueOf(info) + "," + this.typetieu;
        info = String.valueOf(info) + "," + this.idNguHanh2;
        info = String.valueOf(info) + "," + this.isItemDrop;
        info = String.valueOf(info) + "," + this.idSellMarket;
        info = String.valueOf(info) + "," + (this.isItemShop ? 1 : 0);
        info = String.valueOf(info) + "," + this.timeExpireHkl;
        info = String.valueOf(info) + "," + this.id_dongxu_trung;
        info = String.valueOf(info) + "," + this.quanlity;
        info = String.valueOf(info) + "," + this.TinhLuyen;
        info = String.valueOf(info) + "," + this.GiaHan;
        info = String.valueOf(info) + "," + this.CaiTao;
        return info;
    }

    public Item cloneItem() {
        final Item newItem = new Item(this.getTemplate(), false, this.clazz, this.classTemplate, this.tempateID);
        newItem.belongUser = this.belongUser;
        newItem.plus = this.plus;
        newItem.level = this.level;
        newItem.mDurable = this.mDurable;
        newItem.durable = this.durable;
        newItem.colorName = this.colorName;
        newItem.tempateID = this.tempateID;
        newItem.classTemplate = this.classTemplate;
        newItem.clazz = this.clazz;
        newItem.nhadSock = this.nhadSock;
        newItem.nSocAdd = this.nSocAdd;
        newItem.magic_physic = this.magic_physic;
        newItem.isItemDrop = this.isItemDrop;
        newItem.typetieu = this.typetieu;
        newItem.setType(this.getType());
        newItem.minuteExist = this.minuteExist;
        newItem.timeLoan = this.timeLoan;
        newItem.charSeal = this.charSeal;
        newItem.heItem = this.heItem;
        newItem.hangItem = this.hangItem;
        newItem.dateCreate = this.dateCreate;
        newItem.atb = this.atb;
        newItem.newAtb = this.newAtb;
        newItem.lockAtb = this.lockAtb;
        newItem.addMoreLevelSkill = this.addMoreLevelSkill;
        newItem.otherAtt = this.otherAtt;
        newItem.idNguHanh2 = this.idNguHanh2;
        newItem.isItemDrop = this.isItemDrop;
        newItem.pos = this.pos;
        newItem.oldKham = this.oldKham;
        newItem.slotWearing = this.slotWearing;
        newItem.idSellMarket = this.idSellMarket;
        newItem.isItemShop = this.isItemShop;
        return newItem;
    }

    public String getMoneyReset() {
        String info = "";
        int lv = this.level;
        if (this.level % 5 != 0) {
            --lv;
        }
        final int index = (lv - 20) / 5;
        switch (this.nhadSock) {
            case 1:
            case 2:
            case 3: {
                info = String.valueOf(info) + Item.COST_RESET_SOCK[index][0] + " xu ";
                info = String.valueOf(info) + Item.COST_RESET_SOCK[index][2] + " lượng";
                break;
            }
            case 4:
            case 5:
            case 6: {
                info = String.valueOf(info) + Item.COST_RESET_SOCK[index][0] + " xu ";
                info = String.valueOf(info) + Item.COST_RESET_SOCK[index][2] + " lượng";
                break;
            }
        }
        return info;
    }

    public void resetAtbKham(final Char p) {
        try {
            this.oldKham = 0;
            if (this.oldKham == 1 || this.nhadSock == 0) {
                p.sendMessage(MessageCreator.createServerAlertMessage("Hiện tại chưa hỗ trợ.", ""));
                return;
            }
            int lv = this.level;
            if (this.level % 5 != 0) {
                --lv;
            }
            final int index = (lv - 20) / 5;
            switch (this.nhadSock) {
                case 1:
                case 2:
                case 3: {
                    if (p.getxu() < Item.COST_RESET_SOCK[index][0]) {
                        return;
                    }
                    if (p.getLuong() < Item.COST_RESET_SOCK[index][2]) {
                        return;
                    }
                    p.subXu(Item.COST_RESET_SOCK[index][0], true, "item 1");
                    p.subLuong(Item.COST_RESET_SOCK[index][2]);
                    p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                    Database.instance.saveLogThongKeChiTieu("Thao ngoc kham_luong", 0, 1, Item.COST_RESET_SOCK[index][2], "luong");
                    Database.instance.saveLogThongKeChiTieu("Thao ngoc kham_xu", 0, 1, Item.COST_RESET_SOCK[index][0], "xu");
                    break;
                }
                case 4:
                case 5:
                case 6: {
                    if (p.getxu() < Item.COST_RESET_SOCK[index][1]) {
                        return;
                    }
                    if (p.getLuong() < Item.COST_RESET_SOCK[index][2]) {
                        return;
                    }
                    p.subXu(Item.COST_RESET_SOCK[index][1], true, "item 2");
                    p.subLuong(Item.COST_RESET_SOCK[index][2]);
                    p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                    Database.instance.saveLogThongKeChiTieu("Thao ngoc kham_luong", 0, 1, Item.COST_RESET_SOCK[index][2], "luong");
                    Database.instance.saveLogThongKeChiTieu("Thao ngoc kham_xu", 0, 1, Item.COST_RESET_SOCK[index][1], "xu");
                    break;
                }
            }
            String info = String.valueOf(this.getTemplate().name) + "_" + this.plus + "_" + this.getAttribute() + "_" + this.getDBInfo();
            this.nhadSock = 0;
            for (int i = 10; i < 28; ++i) {
                this.atb[i] = 0;
            }
            info = String.valueOf(info) + " | " + this.getAttribute() + "_" + this.getDBInfo();
            p.calculateAttrib();
            p.sendMessage(MessageCreator.createMainCharInfoMessage(p));
            p.sendMessage(MessageCreator.createCharWearingMessage(p, p));
            p.sendToNearPlayer(MessageCreator.createCharInfo(p));
            Database.instance.saveOrtherLog("", p.getName(), info, "thaongoc");
            p.sendMessage(MessageCreator.createServerAlertMessage("Đã tháo ngọc khảm.", ""));
            MessageCreator.createMsgCharMonster(p, p);
        } catch (final Exception ex) {
        }
    }

    public void resetAtt() {
        if (this.colorName != 4 && (this.getTemplate().type < 3 || this.getTemplate().type == 10 || this.getTemplate().type == 11 || this.getTemplate().type == 12 || this.getTemplate().type == 8) && this.charSeal.equals("")) {
            final short[] attTemp = this.getTemplate().atb;
            if (this.getTemplate().type == 8) {
                int total = 0;
                final int[] pluss = {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 10};
                for (int i = 0; i < this.plus; ++i) {
                    total += pluss[i + 1];
                }
                if (this.atb[0] > attTemp[0] + attTemp[0] / 4 + total || this.atb[0] < attTemp[0] - attTemp[0] / 4 + total) {
                    this.atb[0] = (short) (attTemp[0] + attTemp[0] / 4 + total);
                }
            }
            int j = 1;
            while (j < 10) {
                if (this.atb[j] > attTemp[j] + attTemp[j] / 4 + this.plus) {
                    for (int k = 1; k < 10; ++k) {
                        this.atb[k] = (short) (attTemp[k] + attTemp[k] / 4 + this.plus);
                    }
                    if (this.magic_physic == 0) {
                        this.atb[6] = this.atb[1];
                        final short[] atb = this.atb;
                        final int n = 1;
                        atb[n] /= 10;
                        break;
                    }
                    if (this.magic_physic == 1) {
                        this.atb[6] = (short) (this.atb[1] / 10);
                        break;
                    }
                    break;
                } else {
                    ++j;
                }
            }
        }
    }

    public void getHangITem() {
        int index = 0;
        for (int i = 28; i < 33; ++i) {
            if (this.atb[i] > 0) {
                index = i - 28;
                break;
            }
        }
        int[][][] pcAttSp = {{{8, 9, 8, 8, 8, 9}, {6, 7, 6, 6, 6, 7}, {4, 5, 4, 4, 4, 5}, {2, 3, 2, 2, 2, 3}, {1, 2, 1, 1, 1, 2}}, {{8, 9, 8, 8, 8, 9}, {6, 7, 6, 6, 6, 7}, {4, 5, 4, 4, 4, 5}, {2, 3, 2, 2, 2, 3}, {1, 2, 1, 1, 1, 2}}, {{8, 9, 8, 8, 8, 9}, {6, 7, 6, 6, 6, 7}, {4, 5, 4, 4, 4, 5}, {2, 3, 2, 2, 2, 3}, {1, 2, 1, 1, 1, 2}}, {{5}, {4}, {3}, {2}, {1}}, {{8, 9, 8, 8, 8, 9}, {6, 7, 6, 6, 6, 7}, {4, 5, 4, 4, 4, 5}, {2, 3, 2, 2, 2, 3}, {1, 2, 1, 1, 1, 2}}};
        if (this.colorName == 1) {
            pcAttSp = new int[][][]{{{8, 9, 8, 8, 8, 9}, {6, 7, 6, 6, 6, 7}, {4, 5, 4, 4, 4, 5}, {1, 2, 3}, new int[0]}, {{8, 9, 8, 8, 8, 9}, {6, 7, 6, 6, 6, 7}, {4, 5, 4, 4, 4, 5}, {1, 2, 3}, new int[0]}, {{8, 9, 8, 8, 8, 9}, {6, 7, 6, 6, 6, 7}, {4, 5, 4, 4, 4, 5}, {1, 2, 3}, new int[0]}, {{5}, {4}, {3}, {1, 2}, new int[0]}, {{8, 9, 8, 8, 8, 9}, {6, 7, 6, 6, 6, 7}, {4, 5, 4, 4, 4, 5}, {1, 2, 3}, new int[0]}};
        }
        for (int j = 0; j < pcAttSp[index].length; ++j) {
            for (int k = 0; k < pcAttSp[index][j].length; ++k) {
                if (this.atb[index + 28] == pcAttSp[index][j][k]) {
                    this.hangItem = (byte) k;
                    break;
                }
            }
        }
    }

    public void resetNewItem() {
        Label_0135:
        {
            if (this.getTemplate().id < 268 || this.getTemplate().id > 464) {
                if (this.getTemplate().id < 467 || this.getTemplate().id > 506) {
                    break Label_0135;
                }
            }
            try {
                final short type = this.getType();
                if (Map.isWeapone(type)) {
                    this.createItemWeapone(this.colorName, this.getTemplate().id, this.clazz, this.level, this.plus);
                } else {
                    this.createItemArmor(this.colorName, this.getTemplate().id, this.clazz, this.level, this.plus, type, this.getTemplate().gender);
                }
            } catch (final Exception ex) {
            }
        }
        this.resetItem = 4000;
    }

    private Item createItemWeapone(final int type, final int idTemplate, final int charClass, final int level, final int plusItem) {
        Item item = null;
        final int[] templateBasic = {83, 84, 85, 174, 175, 176, 177, 178, 179, 180, 181, 90, 91, 92, 182, 183, 184, 185, 186, 187, 188, 189, 97, 98, 99, 190, 191, 192, 193, 194, 195, 196, 197, 104, 105, 106, 198, 199, 200, 201, 202, 203, 204, 205, 111, 112, 113, 206, 207, 208, 209, 210, 211, 212, 213};
        final int[] pcItem = {10, 40, 30, 20};
        final ItemTemplates mTemplate = (ItemTemplates) Map.itemTemplates.get(5).get(new Integer(idTemplate));
        final ItemTemplates subTemplate = (ItemTemplates) Map.itemTemplates.get(5).get(new Integer(templateBasic[charClass * 11 + (level - 21) / 5]));
        final short[] att = new short[33];
        final int[] plus = {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 10};
        for (int i = 0; i < 10; ++i) {
            att[i] = (short) (subTemplate.atb[i] + subTemplate.atb[i] * 20 / 100);
        }
        for (int i = 0; i < 14; ++i) {
            for (int j = 0; j < 10; ++j) {
                final short[] array = att;
                final int n = j;
                array[n] += (short) ((j > 0) ? 2 : plus[i + 1]);
            }
        }
        for (int i = 0; i < 10; ++i) {
            att[i] += (short) (att[i] * pcItem[type] / 100);
        }
        final byte addMore = 5;
        final byte pointAdd = CreateItemTemplate.getPointAddForRankReset(level, this.hangItem);
        final byte bu = CreateItemTemplate.getBuCong(level, this.colorName, this.hangItem, this.getTemplate().type);
        for (int k = 0; k < 10; ++k) {
            final short[] array2 = att;
            final int n2 = k;
            array2[n2] += (short) (addMore + pointAdd + bu);
        }
        item = new Item(mTemplate, false, mTemplate.clazz, mTemplate.clazz, idTemplate);
        if (plusItem > 0) {
            for (int l = 0; l < plusItem; ++l) {
                for (int m = 0; m < 10; ++m) {
                    final short[] array3 = att;
                    final int n3 = m;
                    array3[n3] += (short) ((m > 0) ? 1 : plus[l + 1]);
                }
            }
            item.plus = (byte) (plusItem - 1);
            if (this.atb[0] < att[0]) {
                this.atb[0] = att[0];
                this.resetItem = 4000;
            }
        } else if (this.atb[0] < att[0]) {
            this.atb[0] = att[0];
            this.resetItem = 4000;
        }
        item.atb = att;
        item.durable = subTemplate.durable;
        item.mDurable = (short) (item.durable * 10);
        return item;
    }

    private Item createItemArmor(final int type, final int idTemplate, final int charClass, final int level, final int plusItem, final int typeItem, final int gender) {
        Item item = null;
        final int[][] templateBasic = {{9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26}, {35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52}, {61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78}, {83, 84, 85, 174, 175, 176, 177, 178, 179, 180, 181}, {90, 91, 92, 182, 183, 184, 185, 186, 187, 188, 189}, {97, 98, 99, 190, 191, 192, 193, 194, 195, 196, 197}, {104, 105, 106, 198, 199, 200, 201, 202, 203, 204, 205}, {111, 112, 113, 206, 207, 208, 209, 210, 211, 212, 213}, {117, 118, 119, 120, 121, 122, 123, 124, 125}, {129, 130, 131, 132, 133, 134, 135, 136, 137}, {141, 142, 143, 144, 145, 146, 147, 148, 149}, {153, 154, 155, 156, 157, 158, 159, 160, 161}, {165, 166, 167, 168, 169, 170, 171, 172, 173}};
        final int[][] sValueItem = {{1, 40, 5}, {1, 37, 5}, {1, 35, 5}, new int[0], new int[0], new int[0], new int[0], new int[0], {0, 30, 5}, {0, 32, 5}, {1, 37, 5}, {1, 35, 5}, {5, 6, 1}};
        final int[] pcItem = {10, 40, 30, 20};
        final ItemTemplates mTemplate = (ItemTemplates) Map.itemTemplates.get(5).get(new Integer(idTemplate));
        final ItemTemplates subTemplate = (ItemTemplates) Map.itemTemplates.get(5).get(new Integer(templateBasic[typeItem][(typeItem < 3) ? ((level - 20) * 2 / 5 + ((gender == 1) ? 1 : 0)) : ((level - 20) / 5)]));
        final short[] att = new short[33];
        int[] plus = {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 10, 20, 30, 40};
        for (int i = 0; i < 10; ++i) {
            att[i] = (short) (subTemplate.atb[i] + subTemplate.atb[i] * 20 / 100);
        }
        for (int i = 0; i < 14; ++i) {
            for (int j = 0; j < 10; ++j) {
                if (j != 4 && j != 2) {
                    final int congthem = 8;
                    final short[] array = att;
                    final int n = j;
                    array[n] += (short) ((j > 0) ? congthem : (plus[i + 1] - 2));
                } else if (i % 2 == 0 && j == 2) {
                    final short[] array2 = att;
                    final int n2 = j;
                    ++array2[n2];
                }
            }
        }
        for (int i = 0; i < 10; ++i) {
            att[i] += (short) (att[i] * pcItem[type] / 100);
        }
        final byte addMore = 5;
        final byte pointAdd = CreateItemTemplate.getPointAddForRankReset(level, this.hangItem);
        final byte bu = CreateItemTemplate.getBuCong(level, this.colorName, this.hangItem, this.getTemplate().type);
        for (int k = 0; k < 10; ++k) {
            final short[] array3 = att;
            final int n3 = k;
            array3[n3] += (short) (addMore + pointAdd + bu);
        }
        item = new Item(mTemplate, false, charClass, charClass, idTemplate);
        if (plusItem > 0) {
            plus = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 10};
            for (int l = 0; l < plusItem; ++l) {
                for (int m = 0; m < 10; ++m) {
                    final short[] array4 = att;
                    final int n4 = m;
                    array4[n4] += (short) ((m > 0) ? 1 : plus[l + 1]);
                }
            }
            item.plus = (byte) (plusItem - 1);
            if (typeItem == 8 || typeItem == 9) {
                if (this.atb[0] < att[0]) {
                    this.atb[0] = att[0];
                    this.resetItem = 4000;
                }
            } else if (typeItem != 12) {
                int index = 1;
                if (this.magic_physic == 0) {
                    index = 6;
                }
                if (this.atb[index] < att[1] || Map.abs(this.atb[index] - att[1]) >= 10) {
                    this.atb[index] = att[1];
                    this.resetItem = 4000;
                    if (index == 6) {
                        this.atb[1] = (short) (this.atb[6] / 10);
                    } else if (index == 1) {
                        this.atb[6] = (short) (this.atb[1] / 10);
                    }
                }
            }
        } else if (typeItem == 8 || typeItem == 9) {
            if (this.atb[0] < att[0]) {
                this.atb[0] = att[0];
                this.resetItem = 4000;
            }
        } else if (typeItem != 12) {
            int index = 1;
            if (this.magic_physic == 0) {
                index = 6;
            }
            if (this.atb[index] < att[1] || Map.abs(this.atb[index] - att[1]) >= 10) {
                this.atb[index] = att[1];
                this.resetItem = 4000;
                if (index == 6) {
                    this.atb[1] = (short) (this.atb[6] / 10);
                } else if (index == 1) {
                    this.atb[6] = (short) (this.atb[1] / 10);
                }
            }
        }
        item.atb = att;
        item.durable = subTemplate.durable;
        item.mDurable = (short) (item.durable * 10);
        return item;
    }

    public int[] doAddNewAttributeUseBot() {
        final int ran = Map.r.nextInt(1000);
        final Vector<Integer> idNguhanh = new Vector<Integer>();
        for (int i = 28; i < 33; ++i) {
            if (this.idNguHanh2 == i) {
                this.atb[i] = 0;
            }
            if (this.atb[i] == 0) {
                idNguhanh.add(i);
            }
        }
        final int[] idNewAtt = {15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 31};
        for (int j = 0; j < idNewAtt.length; ++j) {
            this.otherAtt[idNewAtt[j]] = 0;
        }
        int indexLv = (this.level - 50) / 10;
        if (indexLv > 2) {
            indexLv = 2;
        }
        int maxAtt = Map.r.nextInt(3) + 1;
        final int[] resul = new int[maxAtt * 2];
        int ii = 0;
        if (ran < 20) {
            final int id = idNguhanh.get(Map.r.nextInt(idNguhanh.size()));
            this.atb[id] = this.VALUE_NGUHANH_NEW_ATTRIBUTE[indexLv][id - 28][Map.r.nextInt(this.VALUE_NGUHANH_NEW_ATTRIBUTE[indexLv][id - 28].length)];
            this.idNguHanh2 = (byte) id;
            resul[0] = id;
            resul[1] = this.atb[id];
            --maxAtt;
            ii += 2;
        }
        final int[][] group = {{6, 1, 4}, {2, 3, 7, 8, 9, 10, 11, 12, 13}};
        idNguhanh.removeAllElements();
        if (this.isWeapone() || this.getTemplate().type == 8 || this.getTemplate().type == 9 || this.getTemplate().type == 12) {
            for (int k = 0; k < group[0].length; ++k) {
                idNguhanh.add(group[0][k]);
            }
        } else {
            for (int k = 0; k < group[1].length; ++k) {
                idNguhanh.add(group[1][k]);
            }
        }
        while (maxAtt > 0) {
            final int id2 = idNguhanh.remove(Map.r.nextInt(idNguhanh.size()));
            final int value = Item.VALUE_NEW_ATTRIBUTE[id2][indexLv][Map.r.nextInt(Item.VALUE_NEW_ATTRIBUTE[id2][indexLv].length)];
            this.otherAtt[idNewAtt[id2]] = (byte) value;
            resul[ii] = Item.ID_NAME_NEW_ATTRIBUTE[id2];
            resul[ii + 1] = value;
            ii += 2;
            --maxAtt;
        }
        return resul;
    }

    public int[] doAddNewAttribute(final int type) {
        int max = 3;
        if (type == 1) {
            if (this.hangItem == 2) {
                max = 4;
            } else if (this.hangItem == 1) {
                max = 5;
            }
        }
        final int[] index = {5, 6, 7, 28};
        final int id = index[Map.r.nextInt(index.length)];
        for (int i = 0; i < index.length; ++i) {
            this.otherAtt[index[i]] = 0;
        }
        int value = Map.r.nextInt(3) + 1;
        final int ran = Map.r.nextInt(100);
        if (max == 5) {
            if (ran < 3) {
                value = 5;
            } else if (ran < 8) {
                value = 4;
            }
        } else if (max == 4 && ran < 8) {
            value = 4;
        }
        this.otherAtt[id] = (byte) value;
        return new int[]{id, value};
    }

    public void createOptionUpbangSetDocLan() {
    }

    public void createAttChoiVinhVien() {
        if (this.tempateID == 617) {
            this.otherAtt[10] = 50;
            this.atb[30] = 10;
            this.otherAtt[11] = 10;
        } else if (this.tempateID == 618) {
            this.otherAtt[8] = 50;
            this.atb[30] = 10;
            this.otherAtt[12] = 10;
        } else if (this.tempateID == 619) {
            this.otherAtt[9] = 50;
            this.atb[30] = 10;
            this.otherAtt[13] = 10;
        } else if (this.tempateID == 729) {
            this.otherAtt[29] = 50;
            this.atb[30] = 10;
            this.otherAtt[30] = 10;
        
        } else if (this.tempateID == 882) {
           
            this.otherAtt[29] = 20;
            this.otherAtt[73] = 2;
            this.otherAtt[74] = 1;
            this.otherAtt[75] = 3;
            this.otherAtt[76] = 1;
            this.atb[30] = 5;
        }
    }

    public void removeOptionHoaNguoiTuyetChoi() {
        if (this.tempateID >= 617 && this.tempateID <= 619) {
            this.otherAtt[14] = 0;
        }
    }

    public void setOptionHoaNguoiTuyetChoi() {
        if ((this.tempateID >= 617 && this.tempateID <= 619) || this.tempateID == 729) {
            this.otherAtt[14] = 50;
        }
    }

    public void createAttGiap() {
        if (this.tempateID == MIEU_MAT_XANH) {
            // tạo chỉ số đi
        } else if (this.tempateID == MIEU_QUY_TOC) {
            // tạo chỉ số đi
        } else if (this.tempateID == GIAP_RONG) {
            this.atb[30] = 10;
            this.otherAtt[14] = 100;
        }
    }

    public void createOptionCanh(final int id, final int value) {
        if (id == 0) {
            this.lockAtb[0] = (byte) value;
        } else if (id == 1) {
            this.lockAtb[1] = (byte) value;
            this.lockAtb[2] = (byte) value;
        } else if (id == 2) {
            this.otherAtt[50] = (short) value;
        } else if (id == 3) {
            this.otherAtt[4] = (short) value;
        } else if (id == 4) {
            this.otherAtt[9] = (short) value;
            this.otherAtt[10] = (short) value;
            this.otherAtt[29] = (short) value;
            this.otherAtt[8] = (short) value;
        }
    }


    public void createOptionMatNa(final int id, final int value) {
        if (id == 0) {
            this.lockAtb[0] = (byte) value;
        } else if (id == 1) {
            this.lockAtb[1] = (byte) value;
            this.lockAtb[2] = (byte) value;
        } else if (id == 2) {
            this.otherAtt[50] = (short) value;
        } else if (id == 3) {
            this.otherAtt[4] = (short) value;
        } else if (id == 4) {
            this.otherAtt[9] = (short) value;
            this.otherAtt[10] = (short) value;
            this.otherAtt[29] = (short) value;
            this.otherAtt[8] = (short) value;
        } else if (id == 5) {
            this.otherAtt[59] = (short) 10;
        } else if (id == 6) {
            this.otherAtt[60] = (short) 10;
        } else if (id == 7) {
            this.otherAtt[61] = (short) 10;
        } else if (id == 8) {
            this.otherAtt[95] = (short) 10;
        } else if (id == 9) {
            this.otherAtt[63] = (short) 10;
        } else if (id == 10) { // atb mặt nạ thỏ
            // Thuộc tính Lan
            this.otherAtt[9] = (short) 50;
            this.otherAtt[10] = (short) 50;
            this.otherAtt[29] = (short) 50;
            this.otherAtt[8] = (short) 50;

            this.otherAtt[1] = (short) 10;
            this.otherAtt[16] = (short) 10;

            this.otherAtt[68] = (short) 10;
            this.otherAtt[71] = (short) 10;

        } else if (id == 11) { // atb mặt nạ cáo
            // Thuộc tính Lan
            this.otherAtt[9] = (short) 50;
            this.otherAtt[10] = (short) 50;
            this.otherAtt[29] = (short) 50;
            this.otherAtt[8] = (short) 50;

            this.otherAtt[0] = (short) 10;
            this.otherAtt[15] = (short) 5;
            this.otherAtt[68] = (short) 10;
            this.otherAtt[71] = (short) 10;    
        }
    }

    public void createAttChoi() {
        if (this.tempateID == 613 || this.tempateID == 617) {
            this.otherAtt[10] = (byte) ((Map.r.nextInt(5) + 2) * 10);
            this.atb[30] = 10;
        } else if (this.tempateID == 614 || this.tempateID == 618) {
            this.otherAtt[8] = (byte) ((Map.r.nextInt(4) + 1) * 10);
            this.atb[30] = 10;
            this.otherAtt[12] = 10;
        } else if (this.tempateID == 615 || this.tempateID == 619) {
            this.otherAtt[9] = (byte) ((Map.r.nextInt(4) + 1) * 10);
            this.atb[30] = 10;
            this.otherAtt[13] = 10;
        } else if (this.tempateID == 729) {
            this.otherAtt[29] = (byte) ((Map.r.nextInt(4) + 1) * 10);
            this.atb[30] = 10;
            this.otherAtt[30] = 10;
        }
        if ((this.tempateID >= 617 && this.tempateID <= 619) || this.tempateID == 729) {
            this.otherAtt[14] = 50;
        }
    }

    public boolean isPhiPhongKhongTuoc() {
        return this.getTemplate().id == 742;
    }

    public boolean isPhiPhongHuyenVu() {
        return this.getTemplate().id == 750;
    }

    public boolean isPhiPhongTim() {
        return this.getTemplate().id == 840;
    }

    public boolean isPhiPhongBongDa() {
        return this.getTemplate().id == 795;
    }

    public boolean isPhiPhongWorldCup() {
        return this.getTemplate().id == 608;
    }

    public boolean isPhiPhongWordCupHetHan() {
        return this.getTemplate().id == 608 && (this.timeLoan != 0L || this.minuteExist != 0);
    }

    public boolean isHacLang() {
        return this.getTemplate().id == 723;
    }

    public boolean isThanNu() {
        return this.getTemplate().id == 724;
    }

    public boolean isSonTinh() {
        return this.getTemplate().id == 736;
    }

    public boolean isThuyTinh() {
        return this.getTemplate().id == 737;
    }

    public boolean isTieuLongNu() {
        return this.getTemplate().id == 723;
    }

    public boolean isDuongQua() {
        return this.getTemplate().id == 724 || this.getTemplate().id == 728 || (this.getTemplate().id >= 745 && this.getTemplate().id <= 748);
    }

    public boolean isTienNu() {
        return this.getTemplate().id == 743;
    }

    public boolean isTinhQuan() {
        return this.getTemplate().id == 744;
    }

    public boolean isMieuMatXanh() {
        return this.getTemplate().id == 833;
    }

    public boolean isMieuQuyToc() {
        return this.getTemplate().id == 834;
    }

    public boolean isGiapRong() {
        return this.getTemplate().id == 839;
    }

   


    public boolean isItemThoiTrang() {
        return this.getTemplate().atb[9] > 0;
    }

    public int getLuaLan() {
        if (this.isGayGiangSinh() || this.isChoiLuaLanBienHinh()) {
            return 1;
        }
        return 0;
    }

    public boolean isItemThoiTrangCanSellTown() {
        return (
                this.tempateID == 725 || this.tempateID == 726 || this.tempateID == 724 || this.tempateID == 723 ||
                this.tempateID == 797 || this.tempateID == 798 || this.tempateID == 799 || this.tempateID == 800 || this.tempateID == 801
                || this.tempateID == 833
                || this.tempateID == 834
                || this.tempateID == 839) && this.timeLoan > 0L && this.minuteExist > 0;
    }

    @Override
    public boolean canSellTown() {
        return this.isItemThoiTrangCanSellTown() 
                || (this.getTemplate().id >= 715 && this.getTemplate().id <= 719 && this.lock == 0) 
                || (this.getTemplate().id == 796 && this.lock == 0)
                || (this.isHoVeLenh() && this.dateCreate.compareTo("2020-09-22 10:00:00") >= 0 
                && this.dateCreate.compareTo("2020-10-22 23:59:59") <= 0) 
                || (this.isMatna() 
                && this.timeLoan > 0L 
                && this.minuteExist > 0);
    }

    public boolean isVuKhiThanBinhNewUpdate09092024() {
        return (this.getTemplate().id >= 758 && this.getTemplate().id <= 772);
    }
    public boolean isVuKhiThoiTrang2() {
        return (this.getTemplate().id >= 758 && this.getTemplate().id <= 772);
    }

    public boolean isVuKhiThanTL() {
        return (this.getTemplate().id >= 758 && this.getTemplate().id <= 772);
    }
    
    public boolean isNguyetLinhTruong() {
        return this.getTemplate().id == 796;
    }

    public boolean isXichCuoc() {
        return this.getTemplate().id == 799;
    }

    public boolean isQuatBaiTieu() {
        return this.getTemplate().id == 890;
    }

    public boolean isGayNhuY() {
        return this.getTemplate().id == 891;
    }

    public boolean isLuongNhatDao() {
        return this.getTemplate().id == 892;
    }

    public boolean isThanKhi() {
        return this.isQuatBaiTieu() || this.isGayNhuY() || this.isLuongNhatDao();
    }
   

    

    public boolean isVukhiThoiTrang() {

        switch (this.getTemplate().id) {
            case 728: // gậy giáng sinh
                return true;
          

            default:
                if ((this.getTemplate().id >= 715 && this.getTemplate().id <= 719)) {
                    // vũ khí thời trang theo class
                    return true;
                } else if (isVuKhiThanBinhNewUpdate09092024()) {
                    // Vũ khí thần binh mới
                    return true;
                } else if (isNguyetLinhTruong()) {
                    // Nguyệt Linh Trượng
                    return true;
                } else if (isThanKhi()) {
                    // Gậy gay nhu y, quạt bài tiêu, lượng nhật đao
                    return true;
             
                }

                return false;

        }

    }

    public boolean isVuKhiThoiTrangTool() {
        return this.isGayGiangSinh() || this.isChoiLuaLanBienHinh();
    }

    
    public void createAttributeNguyetLinhTruong() {
        if (!this.isQuatBaiTieu() && !this.isGayNhuY() && !this.isLuongNhatDao() && !this.isNguyetLinhTruong()) {
            return;
        }
    
        this.colorName = Item.COLOR_MAGENTA;
        
        if (this.isQuatBaiTieu() || this.isGayNhuY() || this.isLuongNhatDao()) {
            // Tạo mảng chứa các index có thể chọn
            ArrayList<Integer> possibleLines = new ArrayList<>();
            possibleLines.add(54);
            possibleLines.add(55);
            possibleLines.add(56);
            
            // Xác định số dòng sẽ xuất hiện dựa vào tỷ lệ
            int chance = Map.r.nextInt(100);
            int numLines;
            if (chance < 60) {
                numLines = 1; // 60% 1 dòng
            } else if (chance < 90) {
                numLines = 2; // 30% 2 dòng
            } else {
                numLines = 3; // 10% 3 dòng
            }
            
            // Random chọn và tạo giá trị cho số dòng tương ứng
            for (int i = 0; i < numLines; i++) {
                // Random chọn 1 index từ các index còn lại
                int randomIndex = Map.r.nextInt(possibleLines.size());
                int lineId = possibleLines.get(randomIndex);
                possibleLines.remove(randomIndex);
                
                // Tạo giá trị cho dòng được chọn
                if (lineId == 54) {
                    if (Map.r.nextInt(100) < 5) {
                        this.otherAtt[54] = (short) (Map.r.nextInt(81) + 190);
                    } else if (Map.r.nextInt(100) < 25) {
                        this.otherAtt[54] = (short) (Map.r.nextInt(81) + 110);
                    } else {
                        this.otherAtt[54] = (short) (Map.r.nextInt(71) + 40);
                    }
                } else if (lineId == 55) {
                    this.otherAtt[55] = (short) (Map.r.nextInt(491) + 70);
                } else if (lineId == 56) {
                    if (Map.r.nextInt(100) < 5) {
                        this.otherAtt[56] = (short) (Map.r.nextInt(98) + 205);
                    } else if (Map.r.nextInt(100) < 25) {
                        this.otherAtt[56] = (short) (Map.r.nextInt(97) + 130);
                    } else {
                        this.otherAtt[56] = (short) (Map.r.nextInt(86) + 40);
                    }
                }
            }
            return;
        }
    
        // Original NguyetLinhTruong logic - giữ nguyên không thay đổi
        if (Map.r.nextInt(100) < 5) {
            this.otherAtt[54] = (short) (Map.r.nextInt(81) + 160);
        } else if (Map.r.nextInt(100) < 25) {
            this.otherAtt[54] = (short) (Map.r.nextInt(81) + 80);
        } else {
            this.otherAtt[54] = (short) (Map.r.nextInt(71) + 10);
        }
        
        this.otherAtt[55] = (short) (Map.r.nextInt(491) + 10);
        
        if (Map.r.nextInt(100) < 5) {
            this.otherAtt[56] = (short) (Map.r.nextInt(98) + 192);
        } else if (Map.r.nextInt(100) < 25) {
            this.otherAtt[56] = (short) (Map.r.nextInt(97) + 96);
        } else {
            this.otherAtt[56] = (short) (Map.r.nextInt(86) + 10);
        }
    }

    public void createAttributeNguyetLinhTruongMax() {
        if (!this.isNguyetLinhTruong()) {
            return;
        }
        this.colorName = Item.COLOR_MAGENTA;
        if (this.isGayGiangSinh()) {
            this.otherAtt[29] = 1000;
            this.otherAtt[54] = 50;
            this.otherAtt[56] = 30;
        } else {

            this.otherAtt[54] = (short) (81 + 160);
            this.otherAtt[56] = (short) (98 + 192);
            this.otherAtt[55] = (short) (491 + 10);
        }
    }
    
    
    
    public void createAttributeVuKhiThoiTrang() {
        if (!this.isVukhiThoiTrang()) {
            return;
        }
        this.colorName = Item.COLOR_MAGENTA;
        if (this.isGayGiangSinh()) {
            this.otherAtt[29] = 1000;
            this.otherAtt[54] = 50;
            this.otherAtt[56] = 30;
        } else {
            if (Map.r.nextInt(100) < 5) {
                this.otherAtt[54] = (short) (Map.r.nextInt(81) + 160);
            } else if (Map.r.nextInt(100) < 25) {
                this.otherAtt[54] = (short) (Map.r.nextInt(81) + 80);
            } else {
                this.otherAtt[54] = (short) (Map.r.nextInt(71) + 10);
            }
            if (Map.r.nextInt(100) < 5) {
                this.otherAtt[56] = (short) (Map.r.nextInt(98) + 192);
            } else if (Map.r.nextInt(100) < 25) {
                this.otherAtt[56] = (short) (Map.r.nextInt(97) + 96);
            } else {
                this.otherAtt[56] = (short) (Map.r.nextInt(86) + 10);
            }
            this.otherAtt[55] = (short) (Map.r.nextInt(491) + 10);
        }
    }

    public void createAttributeVuKhiThoiTrangMax() {
        if (!this.isVukhiThoiTrang()) {
            return;
        }
        this.colorName = Item.COLOR_MAGENTA;
        if (this.isGayGiangSinh()) {
            this.otherAtt[29] = 1000;
            this.otherAtt[54] = 50;
            this.otherAtt[56] = 30;
        } else {

            this.otherAtt[54] = (short) (81 + 160);
            this.otherAtt[56] = (short) (98 + 192);
            this.otherAtt[55] = (short) (491 + 10);
        }
    }

    public void createAttributeThanKhiVip() {
        if (!this.isThanKhi()) {
            return;
        }
        this.colorName = Item.COLOR_MAGENTA;

        this.newAtb[0] = 12;
        this.newAtb[1] = 12;
        this.newAtb[2] = 10;
        this.newAtb[3] = 10;
        this.newAtb[4] = 10;
        this.newAtb[5] = 10;
        this.newAtb[7] = 10;

        this.lockAtb[0] = 25;
        this.lockAtb[1] = 25;
        this.lockAtb[2] = 25;

        this.otherAtt[54] = 260;
        this.otherAtt[55] = 550;
        this.otherAtt[56] = 320;
    }

    public void createAttributeItemModel(final boolean isMax) {
        if (this.isTienNu() || this.isTinhQuan()) {
            this.otherAtt[58] = 100;
        }
    }

    public boolean isPhiphonghehehe() {
        return this.isPhiPhongKhongTuoc() || this.isPhiPhongHuyenVu() || this.isPhiPhongBongDa() || this.isPhiPhongWorldCup() || this.isPhiPhongTim();
    }

    public void createAttributeItemCoat(final boolean ismax, final byte mv, final int khang) {
        this.magic_physic = ((mv == -1) ? ((byte) Map.r.nextInt(2)) : mv);
        int def1 = 0;
        if (this.isPhiPhongKhongTuoc() || this.isPhiPhongHuyenVu() || this.isPhiPhongBongDa() || this.isPhiPhongWorldCup() || this.isPhiPhongTim()) {
            def1 = 80;
        } else {
            def1 = Item.PC_DEF_COAT[(this.level - 35) / 5] + (ismax ? 5 : Map.r.nextInt(5));
        }
        final int def2 = def1 / 10;
        if (this.magic_physic == 1) {
            this.atb[6] = (short) (this.atb[1] / 10);
        } else {
            this.atb[6] = this.atb[1];
            final short[] atb = this.atb;
            final int n = 1;
            atb[n] /= 10;
        }
        this.otherAtt[2] = (byte) ((this.magic_physic == 0) ? def1 : def2);
        this.otherAtt[3] = (byte) ((this.magic_physic == 1) ? def1 : def2);
        this.otherAtt[4] = (byte) (ismax ? ((this.getTemplate().id == 616 || this.getTemplate().id == 608) ? 30 : 20) : (Map.r.nextInt(20) + 1));
        this.newAtb[0] = Item.HP_COAT[this.level / 35];
        if (this.getTemplate().id == 742) {
            this.otherAtt[4] = (byte) (ismax ? 35 : (Map.r.nextInt(20) + 1));
            this.newAtb[0] = 8;
        }
        if (this.getTemplate().id == 750) {
            this.otherAtt[4] = (byte) (ismax ? 30 : (Map.r.nextInt(20) + 1));
            this.newAtb[0] = 8;
            if (khang == -1) {
                final int id = Map.r.nextInt(5) + 59;
                this.otherAtt[id] = 300;
            } else {
                this.otherAtt[khang + 59] = 300;
            }
        }
    }

    public Vector<String> getInfoLucky() {
        final ItemTemplates it = (ItemTemplates) Map.itemTemplates.get(5).get(this.tempateID);
        final int pt = it.atb[1] + it.atb[1] / 4;
        final String info = "Thủ :" + pt;
        final String info2 = "Thủ trang bị tăng: " + Item.PC_DEF_COAT[(this.level - 35) / 5] + 5 + "%";
        final String info3 = "Bạo kích: 3%";
        final Vector<String> result = new Vector<String>();
        result.add("Bạn có thể trúng");
        result.add(String.valueOf(it.name) + " " + it.level);
        result.add(info);
        result.add(info2);
        result.add(info3);
        return result;
    }

    public String getNameItemPetEat() {
        String name = this.getTemplate().name;
        final String[] mg = {"ma pháp", "vậy lý", "", ""};
        if (this.magic_physic < 2) {
            name = String.valueOf(name) + " " + mg[this.magic_physic];
        }
        final String[] n = {"", "Hoàn mỹ", "Đỏ", "Xanh"};
        final String[] p = {"", "Nhất phẩm", "Nhị phẩm", "Tam phẩm", "Tứ phẩm", "NGũ phẩm"};
        if (this.colorName > 0 && this.colorName < 4) {
            name = String.valueOf(name) + " " + n[this.colorName];
        }
        if (this.hangItem > 1 && this.hangItem < 6) {
            name = String.valueOf(name) + " " + p[this.hangItem];
        }
        if (this.plus > 0) {
            name = String.valueOf(name) + " +" + this.plus;
        }
        return name;
    }

    public String getCharSeal() {
        StringBuilder result = new StringBuilder();
        
        // Add original charSeal if exists
        if (!this.charSeal.equals("")) {
            result.append(this.charSeal);
        }
        
        // Add hh info only if item has time
        if (this.minuteExist > 0 && this.timeLoan > 0L) {
            if (result.length() > 0) {
                result.append("\n");
            }
            final long time = this.minuteExist;
            final String[] info = Char.split(Char.getDayTimeByTime(this.timeLoan + time * 60000L), " ");
            result.append("hh: ").append(info[0]).append("\n-").append(info[1]);
        }
        
        // Only add tinh luyen and gia han if item can be refined
        if (this.isItemTinhLuyen()) {
            if (result.length() > 0) {
                result.append("\n");
            }
            result.append("Tinh luyện: ").append(this.TinhLuyen).append("%")
                  .append("\nGia hạn: ").append(this.GiaHan);
        }
        // cải tạo cải trang
        if (this.CaiTao > 0) {
            result.append("- Tinh Hoa: ").append(this.CaiTao).append("%").append("\n");
        }

        if (this.isTBSuuTap()) {
            result.append("- cấp: ").append(this.plus).append("\n");
            int cost = 100 * (this.plus + 1);
            result.append("- Giá nâng cấp: " + cost + "\n" +" Lượng");
  
        }
    
        
        return result.toString();
    }

    @Override
    public String getName() {
        String name = this.getTemplate().name;
        final String[] mg = {"ma pháp", "vậy lý", "", ""};
        if (this.magic_physic < 2) {
            name = String.valueOf(name) + " " + mg[this.magic_physic];
        }
        final String[] n = {"", "Hoàn mỹ", "Đỏ", "Xanh"};
        final String[] p = {"", "Nhất phẩm", "Nhị phẩm", "Tam phẩm", "Tứ phẩm", "NGũ phẩm"};
        if (this.colorName > 0 && this.colorName < 4) {
            name = String.valueOf(name) + " " + n[this.colorName];
        }
        if (this.hangItem > 1 && this.hangItem < 6) {
            name = String.valueOf(name) + " " + p[this.hangItem];
        }
        if (this.plus > 0) {
            name = String.valueOf(name) + " +" + this.plus;
        }
        return this.getTemplate().name;
    }

    @Override
    public boolean isRuongVanTieu() {
        return this.getTemplate().id == 611 || this.getTemplate().id == 612;
    }

    public int getIdTemplateUpLevel() {
        if (this.getTemplate().idItemUpLevel > -1) {
            return this.getTemplate().idItemUpLevel;
        }
        return this.getTemplate().id + ((this.getTemplate().type > 2) ? 1 : 2);
    }

    public void doAddOptionThanThu(final int lv) {
        this.colorName = Item.COLOR_GREEN;
        if (lv == 1) {
            this.otherAtt[24] = 50;
            this.otherAtt[25] = 50;
            this.otherAtt[26] = 50;
            this.otherAtt[31] = 50;
            this.otherAtt[38] = 5;
            this.otherAtt[39] = 1000;
        } else if (lv == 2) {
            this.otherAtt[24] = 50;
            this.otherAtt[25] = 50;
            this.otherAtt[26] = 50;
            this.otherAtt[31] = 50;
            this.otherAtt[38] = 20;
            this.otherAtt[39] = 1000;
        } else if (lv == 3) {
            this.otherAtt[24] = 50;
            this.otherAtt[25] = 50;
            this.otherAtt[26] = 50;
            this.otherAtt[31] = 50;
            this.otherAtt[38] = 30;
            this.otherAtt[40] = 20;
            this.otherAtt[39] = 1000;
        }
        this.lock = 1;
    }

    public void doAddOptionPetCu(final int lv) {
        if (lv == 1) {
            this.otherAtt[41] = 50;
            this.otherAtt[46] = 1000;
        } else if (lv == 2) {
            this.otherAtt[41] = 100;
            this.otherAtt[46] = 2000;
        } else if (lv == 3) {
            this.otherAtt[41] = 150;
            this.otherAtt[46] = 3000;
        } else if (lv == 4) {
            this.otherAtt[41] = 200;
            this.otherAtt[46] = 7000;
        } else if (lv == 5) {
            this.otherAtt[41] = 250;
            this.otherAtt[46] = 10000;
        }
    }

    public void doAddOptionPetRong(final int lv) {
        if (lv == 1) {
            this.otherAtt[42] = 50;
            this.otherAtt[47] = 1000;
        } else if (lv == 2) {
            this.otherAtt[42] = 50;
            this.otherAtt[47] = 2000;
        } else if (lv == 3) {
            this.otherAtt[42] = 80;
            this.otherAtt[47] = 3000;
        } else if (lv == 4) {
            this.otherAtt[42] = 100;
            this.otherAtt[47] = 5000;
        } else if (lv == 5) {
            this.otherAtt[42] = 150;
            this.otherAtt[47] = 8000;
        }
    }

    public void doAddOptionPetYeuTinh(final int lv) {
        if (lv == 1) {
            this.otherAtt[43] = 50;
            this.otherAtt[4] = 20;
            this.otherAtt[53] = 5;
        } else if (lv == 2) {
            this.otherAtt[43] = 70;
            this.otherAtt[4] = 30;
            this.otherAtt[53] = 10;
        } else if (lv == 3) {
            this.otherAtt[43] = 100;
            this.otherAtt[4] = 50;
            this.otherAtt[53] = 15;
        } else if (lv == 4) {
            this.otherAtt[43] = 100;
            this.otherAtt[4] = 80;
            this.otherAtt[53] = 20;
        } else if (lv == 5) {
            this.otherAtt[43] = 120;
            this.otherAtt[4] = 100;
            this.otherAtt[53] = 30;
        }
    }

    public void doAddOptionPetDoi(final int lv) {
        if (lv == 1) {
            this.otherAtt[44] = 50;
            this.otherAtt[49] = 1000;
            this.otherAtt[48] = 500;
            this.otherAtt[52] = 30;
        } else if (lv == 2) {
            this.otherAtt[44] = 50;
            this.otherAtt[49] = 2000;
            this.otherAtt[48] = 1000;
            this.otherAtt[52] = 50;
        } else if (lv == 3) {
            this.otherAtt[44] = 70;
            this.otherAtt[49] = 3000;
            this.otherAtt[48] = 1500;
            this.otherAtt[52] = 100;
        } else if (lv == 4) {
            this.otherAtt[44] = 100;
            this.otherAtt[49] = 5000;
            this.otherAtt[48] = 2000;
            this.otherAtt[52] = 200;
        } else if (lv == 5) {
            this.otherAtt[44] = 200;
            this.otherAtt[49] = 5000;
            this.otherAtt[48] = 3000;
            this.otherAtt[52] = 400;
        }
    }

    public void doAddOptionPetDaiBang(final int lv) {
        if (lv == 1) {
            this.otherAtt[45] = 50;
            this.atb[2] = 50;
            this.otherAtt[51] = 10;
        } else if (lv == 2) {
            this.otherAtt[45] = 100;
            this.atb[2] = 100;
            this.otherAtt[51] = 20;
        } else if (lv == 3) {
            this.otherAtt[45] = 150;
            this.atb[2] = 200;
            this.otherAtt[51] = 30;
        } else if (lv == 4) {
            this.otherAtt[45] = 200;
            this.atb[2] = 250;
            this.otherAtt[51] = 50;
        } else if (lv == 5) {
            this.otherAtt[45] = 300;
            this.atb[2] = 350;
            this.otherAtt[51] = 70;
        }
    }

    public void doAddOptionRandomPet(final int lv, final boolean random) {
        short[][] value = {{10, 10, 10, 10}, {10, 10}, {3}, {3, 3}, {5}, {10}};
        if (lv == 1 || random) {
            final Vector<Integer> indexOption = new Vector<Integer>();
            indexOption.add(0);
            indexOption.add(1);
            indexOption.add(2);
            indexOption.add(3);
            indexOption.add(4);
            indexOption.add(5);
            for (int i = 0; i < Item.OPTION_RANDOM.length; ++i) {
                for (int j = 0; j < Item.OPTION_RANDOM[i].length; ++j) {
                    if (i == 0 || i == 1 || i == 4 || i == 5) {
                        this.otherAtt[Item.OPTION_RANDOM[i][j]] = 0;
                    } else {
                        this.lockAtb[Item.OPTION_RANDOM[i][j]] = 0;
                    }
                }
            }
            for (int i = 0; i < 3; ++i) {
                final int pos = indexOption.remove(Map.r.nextInt(indexOption.size()));
                if (pos == 0 || pos == 1 || pos == 4 || pos == 5) {
                    for (int k = 0; k < Item.OPTION_RANDOM[pos].length; ++k) {
                        this.otherAtt[Item.OPTION_RANDOM[pos][k]] = value[pos][k];
                    }
                } else if (pos == 2 || pos == 3) {
                    for (int k = 0; k < Item.OPTION_RANDOM[pos].length; ++k) {
                        this.lockAtb[Item.OPTION_RANDOM[pos][k]] = (byte) value[pos][k];
                    }
                }
            }
        }
        if (lv == 2) {
            value = new short[][]{{15, 15, 15, 15}, {20, 20}, {4}, {4, 4}, {7}, {20}};
        } else if (lv == 3) {
            value = new short[][]{{20, 20, 20, 20}, {50, 50}, {5}, {5, 5}, {10}, {30}};
        } else if (lv == 4) {
            value = new short[][]{{30, 30, 30, 30}, {100, 100}, {6}, {6, 6}, {15}, {50}};
        } else if (lv == 5) {
            value = new short[][]{{50, 50, 50, 50}, {500, 500}, {8}, {8, 8}, {25}, {100}};
        }
        for (int l = 0; l < Item.OPTION_RANDOM.length; ++l) {
            for (int m = 0; m < Item.OPTION_RANDOM[l].length; ++m) {
                if (l == 0 || l == 1 || l == 4 || l == 5) {
                    if (this.otherAtt[Item.OPTION_RANDOM[l][m]] > 0) {
                        this.otherAtt[Item.OPTION_RANDOM[l][m]] = value[l][m];
                    }
                } else if (this.lockAtb[Item.OPTION_RANDOM[l][m]] > 0) {
                    this.lockAtb[Item.OPTION_RANDOM[l][m]] = (byte) value[l][m];
                }
            }
        }
    }

    public void doAddOptionPet(final int lv) {
        if (lv == 1) {
            this.newAtb[2] = 10;
            this.newAtb[3] = 10;
            this.newAtb[4] = 10;
            this.newAtb[5] = 10;
        } else if (lv == 2) {
            this.newAtb[2] = 12;
            this.newAtb[3] = 12;
            this.newAtb[4] = 12;
            this.newAtb[5] = 12;
        } else if (lv == 3) {
            this.newAtb[2] = 15;
            this.newAtb[3] = 15;
            this.newAtb[4] = 15;
            this.newAtb[5] = 15;
        } else if (lv == 4) {
            this.newAtb[2] = 20;
            this.newAtb[3] = 20;
            this.newAtb[4] = 20;
            this.newAtb[5] = 20;
        } else if (lv == 5) {
            this.newAtb[2] = 25;
            this.newAtb[3] = 25;
            this.newAtb[4] = 25;
            this.newAtb[5] = 25;
        }
        if (this.isCu()) {
            this.doAddOptionPetCu(lv);
        } else if (this.isRong()) {
            this.doAddOptionPetRong(lv);
        } else if (this.isDoi()) {
            this.doAddOptionPetDoi(lv);
        } else if (this.isYeuTinh()) {
            this.doAddOptionPetYeuTinh(lv);
        } else if (this.isDaiBang()) {
            this.doAddOptionPetDaiBang(lv);
        }
        this.doAddOptionRandomPet(lv, false);
    }

    public boolean isDaiBang() {
        return this.tempateID >= 709 && this.tempateID <= 713;
    }

    public boolean isDoi() {
        return this.tempateID >= 704 && this.tempateID <= 708;
    }

    public boolean isYeuTinh() {
        return this.tempateID >= 699 && this.tempateID <= 703;
    }

    public boolean isRong() {
        return this.tempateID >= 694 && this.tempateID <= 698;
    }

    public boolean isCu() {
        return this.tempateID >= 689 && this.tempateID <= 693;
    }

    public boolean istrungPet() {
        return this.getTemplate().type == 26;
    }

    public boolean isPet() {
        return this.getTemplate().type == 27;
    }

    public boolean isSachSkillPet() {
        return this.tempateID == 722;
    }

    public boolean isHuyetLinhThao() {
        return this.tempateID == 720;
    }

    public boolean isHuyetBoDe() {
        return this.tempateID == 721;
    }

    public boolean isDongXuBac() {
        return this.tempateID == 730;
    }

    public boolean isDongXuXanh() {
        return this.tempateID == 731;
    }

    public boolean isDongXuVang() {
        return this.tempateID == 732;
    }

    public boolean isDongXuDo() {
        return this.tempateID == 733;
    }

    public boolean isDongXuXanhla() {
        return this.tempateID == 734;
    }

    @Override
    public boolean isRuongMayMan() {
        return this.tempateID == 735;
    }

    public boolean isAoDai2021() {
        return this.tempateID == 754 || this.tempateID == 755;
    }

    public boolean isRuongBatBao() {
        return this.tempateID == 738;
    }

    public boolean isRuongNguyenLieu() {
        return this.tempateID == 753 || this.tempateID == 752;
    }

    public boolean isPhuPhuThe() {
        return this.tempateID == 751;
    }
   
    public boolean isItemTinhLuyen() {
        return (this.getTemplate().id >= 758 && this.getTemplate().id <= 787);
    }

    @Override
    public long[] getExpPetEatfromItem(final Char p) {
        if (this.tempateID == 714) {
            final long exp = LevelPet.getPCExp(1, p.petUsing.lvDetail.lv);
            return new long[]{exp, 1L};
        }
        if (this.isHuyetLinhThao()) {
            final long exp = LevelPet.getPCExp(1, p.petUsing.lvDetail.lv);
            return new long[]{exp, 1L};
        }
        final long[] value = new long[2];
        if (this.colorName == Item.COLOR_WHITE) {
            value[0] = 100L;
            value[1] = 20L;
        } else if (this.colorName == Item.COLOR_BLUE) {
            value[0] = 10000L;
            value[1] = 10L;
        } else if (this.colorName == Item.COLOR_RED) {
            value[0] = 100000L;
            value[1] = 5L;
        } else if (this.colorName == Item.COLOR_GREEN) {
            value[0] = 200000L;
            value[1] = 1L;
        }
        final long[] array = value;
        final int n = 0;
        array[n] += this.level * 10;
        if (this.hangItem > -1) {
            final long[] array2 = value;
            final int n2 = 0;
            array2[n2] += Item.value_hang_item[this.hangItem] * 50;
        }
        if (p.isAdmin && Map.adminTest) {
            value[0] = 1000000000L;
        }
        return value;
    }

    public boolean isHoVeLenh() {
        return this.getTemplate().type == 21;
    }

    public boolean isLanSuVu() {
        return this.tempateID == 882;
    }

    @Override
    public boolean isChoiSell() {
        return this.tempateID >= 617 && this.tempateID <= 619;
    }

    public boolean isChoi() {
        return this.isChoiSell() || (this.tempateID >= 613 && this.tempateID <= 615);
    }

    @Override
    public String getCharcanBuy() {
        return this.charCanBuy;
    }

    @Override
    public void setCharcanBuy(final String charname) {
        this.charCanBuy = charname;
    }

    public boolean isTuHonDan() {
        return this.tempateID == 714;
    }

    public void setTimeExpire(final int timeExpire) {
    }

    public int getTimeExpire() {
        return 0;
    }

    @Override
    public int getMinPriceSell() {
        if (this.colorName == Item.COLOR_WHITE) {
            return 10000;
        }
        if (this.colorName == Item.COLOR_BLUE) {
            return 20000;
        }
        if (this.colorName == Item.COLOR_YELLOW) {
            return 40000;
        }
        if (this.colorName == Item.COLOR_MAGENTA) {
            return 80000;
        }
        if (this.colorName == Item.COLOR_GREEN) {
            return 200000;
        }
        if (Map.isPhiPhong(this.getTemplate().type)) {
            return 50000000;
        }
        return super.getMinPriceSell();
    }
    public boolean isVuKhiThanBinhNangCap() {
        return (this.getTemplate().id >= 773 && this.getTemplate().id <= 787);
    }

    public boolean isVuKhiThanBinhNangCap2() {
        // Check if item is equipped (PLACE_WEARING = 1)
        if (this.place == PLACE_WEARING) {
            // Check if item is a weapon (type 3-7)
            int type = this.getTemplate().type;
            if (type >= 3 && type <= 7) {
                return this.isVuKhiThanBinhNangCap();
            }
        }
        return false;
    }

    public boolean isManhSuuTap() {
        return (this.getTemplate().id >= 848 && this.getTemplate().id <= 859);
    }

    public boolean isTBSuuTap() {
        return (this.getTemplate().id >= 860 && this.getTemplate().id <= 871);
    }

    public boolean isCaiTrangSuuTap() {
        return (this.getTemplate().id >= 903 && this.getTemplate().id <= 904);
    }


    public void setAttributeByBatGioi() {

            switch(this.plus) {
                case 1:
                    this.otherAtt[50] = 2;
                    this.level = 35;
                    break;
                case 2:
                this.otherAtt[50] = 4;
                this.level = 40;
                    break;
                case 3:
                this.otherAtt[50] = 6;  
                this.otherAtt[78] = 1000;
                this.level = 40;
                    break;
                case 4:
                    this.otherAtt[50] = 8;
                    this.atb[4] = 1000;
                    this.level = 45;
                    break;
                case 5:
                    this.otherAtt[50] = 10;
                    this.atb[4] = 1000;
                    this.level = 45;
                    break;
                case 6:
                    this.otherAtt[50] = 12;
                    this.atb[4] = 1000;
                    this.level = 50;
                    break;
                case 7:
                    this.otherAtt[50] = 14;
                    this.atb[4] = 1000;
                    this.level = 50;
                    break;
                case 8:
                    this.otherAtt[50] = 16;
                    this.otherAtt[78] = 3000;
                    this.level = 55;
                    break;
                case 9:
                    this.otherAtt[50] = 18;
                    this.atb[4] = 3000;
                    this.level = 55;
                    break;
                case 10:
                    this.otherAtt[50] = 20;
                    this.atb[4] = 3000;
                    this.level = 60;
                    break;
                case 11:
                    this.otherAtt[50] = 22;
                    this.atb[4] = 3000;
                    this.level = 60;
                    break;
                case 12:
                    this.otherAtt[50] = 24;
                    this.atb[4] = 3000;
                    this.level = 65;
                    break;
                case 13:
                    this.otherAtt[50] = 26;
                    this.otherAtt[78] = 5000;
                    this.level = 65;
                    break;
                case 14:
                    this.otherAtt[50] = 28;
                    this.atb[4] = 5000;
                    this.level = 70;
                    break;
                case 15:
                    this.otherAtt[50] = 30;
                    this.otherAtt[78] = (byte) 10000;
                    this.level = 70;
                    break;                            
                }   
        }
        public void setAttributeBySaTang( ) {

            switch(this.plus) {
                case 1:
                    this.otherAtt[77] = 1;
                    this.level = 35;
                    break;
                case 2:
                this.otherAtt[77] = 2;
                    this.level = 40;
                    break;
                case 3:
                this.otherAtt[77] = 3;  
                this.otherAtt[79] = 1;
                this.level = 40;
                    break;
                case 4:
                    this.otherAtt[77] = 4;
                    this.otherAtt[79] = 1;
                    this.level = 45;
                    break;
                case 5:
                    this.otherAtt[77] = 5;
                    this.otherAtt[79] = 1;
                    this.level = 45;
                    break;
                case 6:
                    this.otherAtt[77] = 6;
                    this.otherAtt[79] = 1;
                    this.level = 50;
                    break;
                case 7:
                    this.otherAtt[77] = 7;
                    this.otherAtt[79] = 1;
                    this.level = 50;
                    break;
                case 8:
                    this.otherAtt[77] = 8;
                    this.otherAtt[79] = 2;
                    this.level = 55;
                    break;
                case 9:
                    this.otherAtt[77] = 9;
                    this.otherAtt[79] = 2;
                    this.level = 55;
                    break;
                case 10:
                    this.otherAtt[77] = 10;
                    this.otherAtt[79] = 2;
                    this.level = 60;    
                    break;
                case 11:
                    this.otherAtt[77] = 11;
                    this.otherAtt[79] = 2;
                    this.level = 60;
                    break;
                case 12:
                    this.otherAtt[77] = 12;
                    this.otherAtt[79] = 2;
                    this.level = 65;
                    break;
                case 13:
                    this.otherAtt[77] = 13;
                    this.otherAtt[79] = 3;
                    this.level = 65;
                    break;
                case 14:
                    this.otherAtt[77] = 14;
                    this.otherAtt[79] = 3;
                    this.level = 70;
                    break;
                case 15:
                    this.otherAtt[77] = 15;
                    this.otherAtt[79] = 5;
                    this.level = 70;
                    break;                            
            
        }
    }
}
