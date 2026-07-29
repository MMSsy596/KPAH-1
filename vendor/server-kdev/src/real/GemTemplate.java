// 
// Decompiled by Procyon v0.6.0
// 

package real;

public class GemTemplate extends OtherItem
{
    public static byte TYPE_IMBUE;
    public static byte TYPE_KHAM;
    public static byte TYPE_DUC_LO;
    public static byte TYPE_CREATE_ITEM;
    public static byte TYPE_TU_BINH;
    public static byte TYPE_HOP_ITEM_ANIMAL;
    public static byte TYPE_ADD_NEW_ATTRIBUTE;
    public static byte TYPE_LOCK_ANIMAL;
    public static byte TYPE_LOCK_ITEM;
    public static byte TYPE_BOT;
    public static byte TYPE_DA_TIEN_GIAI;
    public static byte TYPE_UNKNOW;
    public static byte TYPE_UNKNOW1;
    public static short LKD_30;
    public static short LKD_35;
    public static short LKD_40;
    public static short DA_TIEN_GIAI;
    public static short BOT_XANH_LA;
    public static short BOT_TRANG;
    public static short BOT_XANH;
    public static short BOT_DO;
    public static short DUC_TUONG_KHAM_1;
    public static short DUC_TUONG_KHAM_2;
    public static short DUC_TUONG_KHAM_3;
    public static short DUC_TUONG_KHAM_4;
    public static short DUC_TUONG_KHAM_5;
    public static short DUC_TUONG_KHAM_6;
    public static short DA_MAY_MAN_C3;
    public static short DA_MAY_MAN_C4;
    public static short DA_MAY_MAN_C5;
    public static short XUONG_C6;
    public static short XUONG_C5;
    public static short XUONG_C4;
    public static short XUONG_C3;
    public static short XUONG_C2;
    public static short XUONG_C1;
    public static short VAI_C1;
    public static short SAT_C1;
    public static short NGOC_C1;
    public static short GO_THUONG_C1;
    public static short DA_MEM_C1;
    public static short DA_TACH_NL_DAC_BIET;
    public static short NGOC_HUYEN_MINH_1;
    public static short NGOC_HUYEN_MINH_2;
    public static short NGOC_HUYEN_MINH_3;
    public static short NGOC_HUYEN_MINH_4;
    public static short NGOC_HUYEN_MINH_5;
    public static short NGOC_HUYEN_MINH_6;
    public static short DA_NGU_HOP_4;
    public static short DA_NGU_HOP_5;
    public static short DA_NGU_HOP_6;
    public static short DA_THUOC_TINH_05;
    public static short GIAY_PHAO;
    public static short THUOC_PHAO;
    public static short MANG_CAU;
    public static short SUNG;
    public static short DUA;
    public static short DU_DU;
    public static short XOAI;
    public static short HOA_THACH_C1;
    public static short HOA_THACH_C2;
    public static short NGOC_1s;
    public static short NGOC_2s;
    public static short NGOC_3s;
    public static short NGOC_4s;
    public static short NGOC_5s;
    public static short NGOC_6s;
    public static short NGOC_7s;
    public static short BAN_DO_KHO_BAU;
    public byte[] typeItem;
    public byte typeMoney;
    public short id;
    private int price;
    private int priceSale;
    private int priceBlackFriday;
    public String name;
    public String decript;
    public short value;
    public byte idImage;
    public byte he;
    public byte type;
    public byte sell;
    public byte ep;
    static byte[] indexKham;
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
        GemTemplate.indexKham = new byte[] { 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 10, 11, 12, 13, 14, 15, 10, 11, 12, 13, 14, 15, 10, 11, 12, 13, 14, 15, 16, 17 };
        GemTemplate.ID_MATERIAL_LOW = new short[][] {
            { 68, 75, 82, 89, 96 }, 
            { 69, 76, 83, 90, 97 }, 
            { 70, 77, 84, 91, 98 }, 
            { 71, 78, 85, 92, 99 }, 
            { 72, 79, 86, 93, 100 }, 
            { 73, 80, 87, 94, 101 } 
        };
        GemTemplate.ID_MATERIAL_HIGHT = new short[][] { { 103, 110, 117, 124, 131 }, { 104, 111, 118, 125, 132 }, { 105, 112, 119, 126, 133 }, { 106, 113, 120, 127, 134 }, { 107, 114, 121, 128, 135 }, { 108, 115, 122, 129, 136 } };
        GemTemplate.ID_TU_BINH = new int[][] { 
            { 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174 }, // LV40
            { 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194 }, // LV50
            { 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210 }, // LV60
            { 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226 } // LV70
        };
        GemTemplate.ID_NGOC_RONG = new short[] { GemTemplate.NGOC_1s, GemTemplate.NGOC_2s, GemTemplate.NGOC_3s, GemTemplate.NGOC_4s, GemTemplate.NGOC_5s, GemTemplate.NGOC_6s, GemTemplate.NGOC_7s };
        GemTemplate.ID_XUONG = new short[] { 229, 230, 231, 232, 233, 234 };
        GemTemplate.ID_BOT = new short[] { 246, 247, 248, 249 };
        GemTemplate.ID_DUC = new short[] { 60, 61, 62, 63, 64, 65, 66 };
        GemTemplate.ID_DA_NGU_HOP = new short[][] { { 137, 138, 139, 140, 141, 142 }, { 143, 144, 145, 146, 147, 148 }, { 149, 150, 151, 152, 153, 154 } };
        GemTemplate.ID_NGOC_HUYEN_MINH = new short[] { 235, 236, 237, 238, 239, 240 };
    }
    
    public GemTemplate() {
        this.typeMoney = 0;
        this.priceBlackFriday = 0;
        this.name = "";
        this.decript = "";
        this.idImage = 0;
        this.ep = 0;
    }
    
    public GemTemplate(final int id, final int idImg, final String name, final String dec, final int value, final int he, final int price, final int type, final int sell, final int typeMoney) {
        this.typeMoney = 0;
        this.priceBlackFriday = 0;
        this.name = "";
        this.decript = "";
        this.idImage = 0;
        this.ep = 0;
        this.id = (short)id;
        this.name = name;
        this.decript = dec;
        this.value = (short)value;
        this.idImage = (byte)idImg;
        this.he = (byte)he;
        this.price = price;
        this.type = (byte)type;
        this.sell = (byte)sell;
        if ((id >= 60 && id < 65) || (id >= 67 && id < 73) || (id >= 74 && id < 80) || (id >= 81 && id < 87) || (id >= 88 && id < 94) || (id >= 95 && id < 101) || (id >= 102 && id < 108) || (id >= 109 && id < 115) || (id >= 116 && id < 122) || (id >= 123 && id < 129) || (id >= 130 && id < 136)) {
            this.ep = 1;
        }
        this.typeMoney = (byte)typeMoney;
        if (typeMoney == 1) {
            this.priceBlackFriday = price / 2;
            if (this.priceBlackFriday <= 0) {
                this.priceBlackFriday = 1;
            }
            this.priceSale = price * 70 / 100;
            if (this.priceSale < 0) {
                this.priceSale = 1;
            }
        }
        else {
            this.priceSale = price;
        }
        if (id == 240 || id == 250 || id == 259 || id == 258) {
            this.priceSale = price;
            this.priceBlackFriday = price;
        }
    }
    
    public GemTemplate(final int id, final int idImg, final String name, final String info, final byte[] typeItem, final int he, final int type, final int value, final int sell, final int typeMoney, final int price) {
        this.typeMoney = 0;
        this.priceBlackFriday = 0;
        this.name = "";
        this.decript = "";
        this.idImage = 0;
        this.ep = 0;
        this.id = (short)id;
        this.name = name;
        this.decript = info;
        this.typeItem = typeItem;
        this.idImage = (byte)idImg;
        this.he = (byte)he;
        this.price = price;
        this.type = (byte)type;
        this.sell = (byte)sell;
        this.value = (short)value;
        if ((id >= 60 && id < 65) || (id >= 67 && id < 73) || (id >= 74 && id < 80) || (id >= 81 && id < 87) || (id >= 88 && id < 94) || (id >= 95 && id < 101) || (id >= 102 && id < 108) || (id >= 109 && id < 115) || (id >= 116 && id < 122) || (id >= 123 && id < 129) || (id >= 130 && id < 136)) {
            this.ep = 1;
        }
        this.typeMoney = (byte)typeMoney;
        if (typeMoney == 1) {
            this.priceBlackFriday = price / 2;
            if (this.priceBlackFriday <= 0) {
                this.priceBlackFriday = 1;
            }
            this.priceSale = price * 70 / 100;
            if (this.priceSale < 0) {
                this.priceSale = 1;
            }
        }
        else {
            this.priceSale = price;
        }
        if (id == 240 || id == 250 || id == 259 || id == 258) {
            this.priceSale = price;
            this.priceBlackFriday = price;
        }
    }
    
    public int getPrice(final boolean isSale) {
        if (Char.isSuKienBlackFriday() && this.priceBlackFriday > 0) {
            return this.priceBlackFriday;
        }
        return isSale ? this.priceSale : this.price;
    }
    
    public boolean belongItem(final int type) {
        for (int i = 0; i < this.typeItem.length; ++i) {
            if (this.typeItem[i] == type) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isBH(final int id) {
        return id >= 0 && id <= 4;
    }
    
    public static boolean isDaMayMan(final int id) {
        return (id >= 5 && id <= 7) || id == 155 || id == 156;
    }
    
    public static boolean isLKD(final int id) {
        return (id >= 8 && id <= 11) || id == 66 || id == 245 || id == 157 || id == 158;
    }
    
    public void khamItem(final Item item) {
        final short[] atb = item.atb;
        final int n = 10 + GemTemplate.indexKham[this.id - 12];
        atb[n] += this.value;
        if (item.nhadSock == 1) {
            item.gemKham.add(this.id);
        }
    }
    
    public static boolean isNgocRong(final int id) {
        for (int i = 0; i < GemTemplate.ID_NGOC_RONG.length; ++i) {
            if (id == GemTemplate.ID_NGOC_RONG[i]) {
                return true;
            }
        }
        return false;
    }
}
