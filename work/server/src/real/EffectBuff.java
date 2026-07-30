package real;

public class EffectBuff {
   public static short TAN_PHE = 0;
   public static short LAM_THINH = 1;
   public static short FIRE_BURN = 2;
   public static short LIGHT_YELLOW = 3;
   public static short LIGHT_MAGENTA = 4;
   public static short TREE = 5;
   public static short LUA_NHO = 6;
   public static short LUA_VUA = 7;
   public static short LUA_TO = 8;
   public static short UONG_RUOU = 9;
   public static short UONG_RUOU1 = 10;
   public static short UONG_RUOU2 = 11;
   public static short EFF_CHIEM_THANH = 12;
   public static short EFF_RI_MAU = 13;
   public static short EFF_HOANG_SO = 14;
   public static short BUFF_DAM_BOSS = 15;
   public static short BUFF_DAM_5 = 17;
   public static short BUFF_DAM_10 = 18;
   public static short EFF_SKILL_HOA_KY_LAN = 19;
   public static short EFF_SHIELD_1 = 20;
   public static short EFF_SHIELD_2 = 21;
   public static short PET_1 = 24;
   public static short PET_2 = 25;
   public static short PET_3 = 26;
   public static short PET_4 = 27;
   public static short PET_5 = 28;
   public static short THA_DEN = 29;
   public static short RU_NGU = 30;
   public static short CHOANG = 31;
   public static short BONG = 32;
   public static short TRUNG_DOC = 33;
   public static short SUY_YEU = 34;
   public static short HOANG_LOAN = 8745;
   public static short BAT_TU = 8746;
   public static short YEM_BUA = 8747;
   public static short VET_THUONG_SAU = 8748;
   public static short DANH_HIEU_1 = 8749;
   public static short DANH_HIEU_2 = 8750;
   public static short DANH_HIEU_3 = 8751;
   public static short DANH_HIEU_4 = 8752;
   public static short DANH_HIEU_5 = 8753;
   public static short EFF_HAC_LANG = 8754;
   public static short EFF_THAN_NU = 8755;
   public static short EFF_TIEN_KHI = 8761;
   public static short EFF_PHA_TRIEN = 8763;
   public static short EFF_AO_THUY_TINH = 8781;
   public static short EFF_AO_SON_TINH = 8782;
   public static short EFF_TOC_DUONG_QUA = 8783;
   public static short EFF_TOC_TIEU_LONG_NU = 8784;
   public static short EFF_NO_THUNG_GO = 8789;
   public static short EFF_BOM_DIA_HOA = 8790;
   public static short EFF_PHI_PHONG_KHONG_TUOC = 8791;
   public static short EFF_TOC_TIEN_NU = 8792;
   public static short EFF_TOC_TINH_QUAN = 8796;
   public static short EFF_AO_TIEN_NU = 8793;
   public static short EFF_AO_TINH_QUAN = 8797;
   public static short EFF_QUAN_TIEN_NU = 8794;
   public static short EFF_QUAN_TINH_QUAN = 8795;
   public static short EFF_START_NGUYET_ANH = 8799;
   public static short EFF_NGUYET_ANH = 8798;
   public static short EFF_BUFF_NOI_LUC = 8805;
   public static short EFF_PHI_PHONG_HUYEN_VU = 8806;
   public static short EFF_PHI_PHONG_BONG_DA = 8867;
   public static short EFF_AO_DAI_NAM = 8811;
   public static short EFF_AO_DAI_NU = 8814;
   public static short EFF_NON_AO_DAI_NAM = 8810;
   public static short EFF_NON_AO_DAI_NU = 8813;
   public static short EFF_QUAN_AO_DAI_NAM = 8812;
   public static short EFF_QUAN_AO_DAI_NU = 8815;
   public static short EFF_PHI_PHONG_VIP1 = 8807;
   public static short EFF_PHI_PHONG_VIP2 = 8808;
   public static short EFF_PHI_PHONG_VIP3 = 8809;
   public static byte BY_ACTOR = 1;
   public static byte BY_MAP = 0;
   public short idEff;
   public short idCharOwner = 32001;
   public short x;
   public short y;
   public int mapID = -2;
   public int damFly = 0;
   public long timeExist = 0L;
   public long timeCount;
   public byte type = 1;
   public byte idRegion = -1;
   public byte idCountry;
   public static byte[] ODER_PAINT = new byte[]{0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0};
   public static byte[] stune = new byte[]{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
   public static byte[] dyhore = new byte[83];
   public int dam = 0;

   static {
      dyhore[80] = 12;
      dyhore[81] = 12;
      dyhore[82] = 12;
   }

   public EffectBuff() {
   }

   public EffectBuff(int id) {
      this.idEff = (short)id;
   }

   public EffectBuff(int id, long time) 
   {
      this.idEff = (short)id;
      this.timeExist = time;
      this.timeCount = System.currentTimeMillis();
   }

   public byte getODER_PAINT() {
      if (this.idEff != 96 && this.idEff != 95) {
         return this.idEff >= 8700 ? 0 : ODER_PAINT[this.idEff];
      } else {
         return 0;
      }
   }

   public void setTimeExist(long time) {
      this.timeExist = time;
   }

   public long getTimeExist() {
      return this.timeExist;
   }

   public boolean timeOut() {
      return System.currentTimeMillis() - this.timeExist >= 0L;
   }

   public boolean canNotMove() {
      if (this.idEff >= 8700) {
         return false;
      } else {
         return stune[this.idEff] == 1 || this.idEff == EFF_CHIEM_THANH;
      }
   }

   public boolean isTanPhe() {
      return this.idEff == TAN_PHE;
   }

   public boolean isEffBuffNoiLuc() {
      return this.idEff == EFF_BUFF_NOI_LUC;
   }

   public boolean isLamThinh() {
      return this.idEff == LAM_THINH;
   }

   public boolean isLuaNho() {
      return this.idEff == LUA_NHO;
   }

   public boolean isLuaTo() {
      return this.idEff == LUA_TO;
   }

   public boolean isLuaVua() {
      return this.idEff == LUA_VUA;
   }

   public boolean isRiMau() {
      return this.idEff == EFF_RI_MAU;
   }

   public boolean isBong() {
      return this.idEff == BONG;
   }

   public boolean isRuNgu() {
      return this.idEff == RU_NGU;
   }

   public boolean isTrungDoc() {
      return this.idEff == TRUNG_DOC;
   }

   public boolean isSuyYeu() {
      return this.idEff == SUY_YEU;
   }

   public boolean isVetThuongSau() {
      return this.idEff == VET_THUONG_SAU;
   }

   public boolean isHoangLoan() {
      return this.idEff == HOANG_LOAN;
   }

   public boolean isBatTu() {
      return this.idEff == BAT_TU;
   }

   public boolean isYemBua() {
      return this.idEff == YEM_BUA;
   }

   public boolean isEffectTool5Long() {
      return this.idEff == VET_THUONG_SAU || this.idEff == YEM_BUA || this.idEff == HOANG_LOAN || this.idEff == BAT_TU;
   }
}
