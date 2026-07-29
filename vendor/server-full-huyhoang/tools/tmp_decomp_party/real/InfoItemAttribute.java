/*
 * Decompiled with CFR 0.152.
 */
package real;

import real.ItemTemplates;
import real.NameAttributeItem;

public class InfoItemAttribute {
    public static final byte WHITE = 0;
    public static final byte GREEN = 1;
    public static final byte RED = 2;
    public static final byte BLUE = 3;
    public static final byte MAGENTA = 4;
    public byte colorPaint = 0;
    public short id;
    public short value;
    public static short ATTACK = 0;
    public static short DEF_PHYS = 1;
    public static short NE_TRANH = (short)2;
    public static short CHINH_XAC = (short)3;
    public static short CRIT = (short)4;
    public static short SUC_KHOE = (short)5;
    public static short DEF_MAGIC = (short)6;
    public static short CHI_SO7 = (short)7;
    public static short CHI_SO8 = (short)8;
    public static short CHI_SO9 = (short)9;
    public static short UP_STRENGH = (short)10;
    public static short UP_NHANH_NHEN = (short)11;
    public static short UP_TINH_THAN = (short)12;
    public static short UP_SUC_KHOE = (short)13;
    public static short GAY_MU = (short)14;
    public static short DONG_BANG = (short)15;
    public static short TRUNG_DOC = (short)16;
    public static short GAY_CHOANG = (short)17;
    public static short HOA_THACH = (short)18;
    public static short GIAM_TOC = (short)19;
    public static short KHANG_GIAM_TOC = (short)20;
    public static short KHANG_TRUNG_DOC = (short)21;
    public static short KHANG_GAY_MU = (short)22;
    public static short KHANG_DONG_BANG = (short)23;
    public static short KHANG_GAY_CHOANG = (short)24;
    public static short KHANG_HOA_THACH = (short)25;
    public static short UP_X2_PER_HIT = (short)26;
    public static short UP_TYLE_ROT_BAO_VAT = (short)27;
    public static short GIAM_ST_VAT = (short)28;
    public static short GIAM_ST_MA = (short)29;
    public static short UP_ATTACK_NGU_HANH = (short)30;
    public static short XUYEN_GIAP = (short)31;
    public static short PHAN_ST = (short)32;
    public static short UP_HP = (short)33;
    public static short UP_MP = (short)34;
    public static short CONG_SM = (short)35;
    public static short CONG_KL = (short)36;
    public static short CONG_TT = (short)37;
    public static short CONG_SK = (short)38;
    public static short CONG_SKILL = (short)39;
    public static short UP_CRIT = (short)40;
    public static short UP_ST_CRIT = (short)41;
    public static short CHUA_DUNG = (short)42;
    public static short CONG_SKILL1 = (short)43;
    public static short CONG_SKILL2 = (short)44;
    public static short CONG_SKILL3 = (short)45;
    public static short CONG_SKILL4 = (short)46;
    public static short CONG_SKILL5 = (short)47;
    public static short CONG_SKILL6 = (short)48;
    public static short CONG_SKILL7 = (short)49;
    public static short CONG_SKILL8 = (short)50;
    public static short CONG_SKILL9 = (short)51;
    public static short CONG_SKILL10 = (short)52;
    public static short CONG_SKILL11 = (short)53;
    public static short CONG_SKILL12 = (short)54;
    public static short CONG_SKILL13 = (short)55;
    public static short CONG_SKILL14 = (short)56;
    public static short CONG_SKILL15 = (short)57;
    public static short UP_ATTACK = (short)58;
    public static short UP_DEF_MAGIC = (short)59;
    public static short UP_DEF_PHYS = (short)60;
    public static short BO_QUA_TC_MA = (short)61;
    public static short BO_QUA_TC_VAT = (short)62;
    public static short UP_DEF_MAGIC_TRANG_BI = (short)63;
    public static short UP_DEF_PHYS_TRANG_BI = (short)64;
    public static short BAO_KICH = (short)65;
    public static short SET_LAN = (short)66;
    public static short DOC_LAN = (short)67;
    public static short BANG_LAN = (short)68;
    public static short UP_SET_LAN = (short)69;
    public static short UP_DOC_LAN = (short)70;
    public static short UP_BANG_LAN = (short)71;
    public static short UP_ST_BANG_LAN = (short)72;
    public static short UP_ST_SET_LAN = (short)73;
    public static short UP_ST_DOC_LAN = (short)74;
    public static short HOA_NGUOI_TUYET = (short)75;
    public static short KHANG_BAO_KICH = (short)76;
    public static short HUT_HP = (short)77;
    public static short HOI_HP = (short)78;
    public static short HOI_MP = (short)79;
    public static short TAN_PHE = (short)80;
    public static short HAP_THU_SAT_THUONG = (short)81;
    public static short LAM_THINH = (short)82;
    public static short UP_TYLE_ROT_ITEM = (short)83;
    public static short UP_TYLE_ROT_XU = (short)84;
    public static short KHANG_BANG_LAN = (short)85;
    public static short KHANG_SET_LAN = (short)86;
    public static short KHANG_DOC_LAN = (short)87;

    public InfoItemAttribute(short id, int value) {
        this.id = id;
        this.value = (short)value;
        if (id >= 10 && id < 28) {
            this.colorPaint = (byte)3;
        } else if (id >= 28 && id < 33 || id == 61 || id == 62) {
            this.colorPaint = 1;
        }
    }

    public byte getColorName() {
        return this.colorPaint;
    }

    public String getInfoValue() {
        NameAttributeItem name = ItemTemplates.ALL_NAME_ATTRIBUTE_ITEM.get(this.id);
        if (name.isPercent == 0) {
            return String.valueOf(this.value);
        }
        if (name.isPercent == 2) {
            return String.valueOf(this.value / 10) + "." + this.value % 10 + "%";
        }
        return String.valueOf(this.value) + "%";
    }

    public static String getNameAtt(int id) {
        NameAttributeItem name = ItemTemplates.ALL_NAME_ATTRIBUTE_ITEM.get(id);
        if (name != null) {
            return name.name;
        }
        return "";
    }

    public static String getInfoValue(int id, int value) {
        NameAttributeItem name = ItemTemplates.ALL_NAME_ATTRIBUTE_ITEM.get(id);
        if (name.isPercent == 0) {
            return String.valueOf(value);
        }
        if (name.isPercent == 2) {
            return String.valueOf(value / 10) + "." + value % 10 + "%";
        }
        return String.valueOf(value) + "%";
    }
}

