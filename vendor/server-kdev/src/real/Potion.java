// 
// Decompiled by Procyon v0.6.0
// 

package real;

public class Potion extends Actor
{
    public static final short TYPE_GOLD = 0;
    public static final short TYPE_HP1 = 1;
    public static final short TYPE_HP2 = 2;
    public static final short TYPE_HP3 = 3;
    public static final short TYPE_MP1 = 4;
    public static final short TYPE_MP2 = 5;
    public static final short TYPE_MP3 = 6;
    public static final short TYPE_BINH_TANG_TOC = 7;
    public static final short TYPE_BINH_TANG_TOC_VANG = 8;
    public static final short TYPE_NHAN_SAM = 9;
    public static final short TYPE_EXP_DON1 = 10;
    public static final short TYPE_EXP_DON2 = 11;
    public static final short TYPE_EXP_DON3 = 12;
    public static final short TYPE_BINH_TANG_TOC_BAC = 13;
    public static final short TYPE_PK1 = 14;
    public static final short TYPE_PK2 = 15;
    public static final short TYPE_PK3 = 16;
    public static final short TYPE_PK4 = 17;
    public static final short TYPE_PK5 = 18;
    public static final short TYPE_THO_DIA_PHU = 19;
    public static final short TYPE_HP4 = 21;
    public static final short TYPE_HP5 = 22;
    public static final short TYPE_MP4 = 23;
    public static final short TYPE_MP5 = 24;
    public static final short TYPE_RESET_TN = 25;
    public static final short TYPE_RESET_KN = 26;
    public static final short TYPE_THAN_HANH_PHU = 27;
    public static final short TYPE_FORBIT_CHAT = 28;
    public static final short TYPE_PHIEU_CONG_ICH = 29;
    public static final short TYPE_HORSE1 = 30;
    public static final short TYPE_METAL = 31;
    public static final short TYPE_DAU_TRUONG = 32;
    public static final short TYPE_CARD_TOWN = 33;
    public static final short TYPE_HORSE2 = 34;
    public static final short TYPE_TUAN_LOC = 36;
    public static final short TYPE_HOA_MAI_9 = 51;
    public static final short TYPE_HOA_DAO_9 = 55;
    public static final short TYPE_HOA_HONG_1 = 58;
    public static final short TYPE_HOA_HONG_9 = 59;
    public static final short TYPE_BI = 62;
    public static final short TYPE_LUCKY_BOX = 63;
    public static final short TYPE_TRAU = 64;
    public static final short TYPE_COP = 65;
    public static final short TYPE_SOI = 66;
    public static final short TYPE_HAC = 67;
    public static final short TYPE_BACH_MA = 68;
    public static final short TYPE_VEQUAYSO = 69;
    public static final short TYPE_PH_BANG = 70;
    public static final short TYPE_HOA_KY_LAN = 71;
    public static final short TYPE_VE_PP_WORLDCUP = 76;
    public static final short TYPE_LIEN_HOA = 78;
    public static final short TYPE_HOP_THAN_KY = 79;
    public static final short TYPE_BINH_TANG_5 = 80;
    public static final short TYPE_GOLD_TIME_150 = 81;
    public static final short TYPE_SUMMONS = 85;
    public static final short TYPE_CRAZY = 84;
    public static final short TYPE_PHUONG_HOANG = 86;
    public static final short TYPE_CARD_PHONG_CHUC = 87;
    public static final short TYPE_RING_WEDDING1 = 88;
    public static final short TYPE_RING_WEDDING2 = 89;
    public static final short TYPE_RING_WEDDING3 = 90;
    public static final short TYPE_KHAI_MO = 91;
    public static final short TYPE_WEDDING_CARD = 92;
    public static final short TYPE_HP7000 = 93;
    public static final short TYPE_HP15 = 94;
    public static final short TYPE_MP7000 = 95;
    public static final short TYPE_MP15 = 96;
    public static final short TYPE_THE_DOI_TIEU_XU = 97;
    public static final short TYPE_PHU_PHUTHE = 98;
    public static final short TYPE_PHAO_HOA = 99;
    public static final short TYPE_SPEAKER = 100;
    public static final short TYPE_GOLD_BOX = 101;
    public static final short TYPE_SILVER_BOX = 102;
    public static final short TYPE_GREEN_LEAF = 103;
    public static final short TYPE_DAY_THUNG = 104;
    public static final short TYPE_ATTACK_FOOD = 113;
    public static final short TYPE_ALL_FOOD = 114;
    public static final short TYPE_THE_DOI_TIEU_LUONG = 116;
    public static final short TYPE_SUPER_CRAZY = 123;
    public static final short TYPE_GOLD_100_1H = 35;
    public static final short TYPE_THE_THEM_LUOT_VAN_TIEU = 82;
    public static final short TYPE_TU_HON_DAN_C1 = 83;
    public static final short TYPE_DEF_FOOD = 112;
    public static final short TYPE_HUYET_LINH_THAO = 71;
    public static final short TYPE_HUYET_BO_DE = 72;
    public static final short TYPE_VE_QUAY_PH = 118;
    public static final short TYPE_GOLD_TIME_3H = 75;
    public static final short TYPE_TUI_NHAT_NGOC = 73;
    public static final short TYPE_TRUNG_DUONG_KHANG = 72;
    public static final short TYPE_TRUNG_BACH_COT = 74;
    public static final short TYPE_TIEN_DAN = 119;
    public static final short TYPE_MANH_BAN_DO1 = 77;
    public static final short TYPE_MANH_BAN_DO2 = 105;
    public static final short TYPE_MANH_BAN_DO3 = 117;
    public static final short TYPE_TRUNG_LAN_SU_TU = 115;
    public static final short TYPE_THIT = 120;
    public static final short TYPE_TRUNG = 121;
    public static final short TYPE_BANH_DEO = 124;
    public static final short TYPE_BANH_NUONG = 125;
    public static final short TYPE_HOP_BANH_THAP_CAM = 133;
    public static final short TYPE_HOP_BANH_DAC_BIET = 134;
    public static final short TYPE_HAT_MAM = 135;
    public static final short TYPE_NEN = 136;
    public static final short TYPE_BOT_MY = 142;
    public static final short TYPE_KEO = 137;
    public static final short TYPE_BI_NGO = 138;
    public static final short TYPE_HOP_QUA = 139;
    public static final short TYPE_HOP_QUA_HALLOWEEN = 140;
    public static final short TYPE_HOP_QUA_TANG_HALLOWEEN = 141;
    public static final short TYPE_HOP_QUA_BI_MAT = 143;
    public static final short TYPE_SAO_VANG = 144;
    public static final short TYPE_DUA_HAU = 145;
    public static final short TYPE_KEM = 146;
    public static final short TYPE_TUI_QUA_MAY_MAN_NOEL = 147;
    public static final short TYPE_DAU_XANH = 122;
    public static final short TYPE_GAO_NEP = 148;
    public static final short TYPE_THIT_MO = 149;
    public static final short TYPE_LA_DONG = 150;
    public static final short TYPE_BANH_CHUNG = 151;
    public static final short TYPE_BANH_TET = 152;
    public static final short TYPE_GIAY = 153;
    public static final short TYPE_THUOC_PHAO = 154;
    public static final short TYPE_PHAO_HOA_TET = 155;
    public static final short TYPE_LUA_THUONG = 156;
    public static final short TYPE_LUA_VUA = 157;
    public static final short TYPE_LUA_TO = 158;
    public static final short TYPE_LI_XI = 159;
    public static final short TYPE_CT_HP = 126;
    public static final short TYPE_CT_MP = 127;
    public static final short TYPE_CT_BUFF_CRAZY = 128;
    public static final short TYPE_CT_HON_DON = 129;
    public static final short TYPE_CT_HON_NGUYEN = 130;
    public static final short TYPE_CT_HOA_CONG = 131;
    public static final short TYPE_CT_HOA_DUOC = 132;
    public static final short TYPE_QUA_BONG_BAC = 160;
    public static final short TYPE_QUA_BONG_VANG = 161;
    
    public short quantity;
    public int belongUser;
    public int id_char;
    private short type;
    
    public Potion(final short type, final int quantity, final Map map) {
        super((byte)4);
        this.belongUser = 0;
        this.type = 0;
        this.type = type;
        this.quantity = (short)quantity;
        this.map = map;
    }
    
    @Override
    public short getType() {
        return this.type;
    }
}
