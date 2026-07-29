// Source code is decompiled from a .class file using FernFlower decompiler.
package real;

import data.Animal;
import data.Pet;
import io.Message;
import java.util.Hashtable;
import java.util.Vector;

public class LiveActor extends Actor {
   static final byte[] BUFFTYPE = new byte[]{19, 20, 21, 22, 23, 24, 25};
   public static final byte KILL_MOSTER = 0;
   public static final byte GET_ITEM = 2;
   public static final byte TRANSPORT = 1;
   public static final byte TALK_NPC = 3;
   public static final short MAX_ITEM_BAG = 15;
   public static final byte HE_THUY = 0;
   public static final byte HE_MOC = 1;
   public static final byte HE_HOA = 2;
   public static final byte HE_THO = 3;
   public static final byte HE_KIM = 4;
   public int[] percentBuff = new int[3];
   public int[] percentBuffPlayer = new int[3];
   public byte he;
   public byte divSpeed = 1;
   public Vector<EffectBuff> AllEffBuff = new Vector();
   public Hashtable<Short, EffectBuff> hashEffBuff = new Hashtable();
   public int attack;
   public int defend_physic;
   public int defend_magic;
   public byte resistMagic;
   public byte resistPhysic;
   public short accurate;
   public short dodge;
   public short critical;
   public short baokich = 0;
   public int hp;
   public int maxhp;
   public int xp;
   public int maxxp;
   public short poinson;
   public short hpDocLan = 0;
   public long timeOutPoinson;
   public short tDelay = 0;
   public boolean beStune;
   public byte totalTime = 36;
   public long[] khamEff = new long[6];
   public long timeOutKhamDoc = 0L;
   static byte[] nh = new byte[]{1, 4, 0, 2, 3};
   public int percentDam = 100;
   public byte magic_physic = 0;
   
   //hoahinh
   public long timeResetDefReduc = 0L; // Time to reset defense reduction
   public int currentDefReduc = 0; // Current defense reduction percentage
   public long timeFirstDefReduc = 0L; // Thời điểm giảm giáp đầu tiên
    private String charName;

   public LiveActor(byte catagory) {
      super(catagory);
   }

   public LiveActor(Map m) {
   }

   public short getLevel() {
      return -1;
   }

   public boolean attackMiss(LiveActor actor) {
      if (UtilKPAH.random(this.accurate + 10) < this.accurate) {
         return UtilKPAH.random(5000) < actor.dodge;
      } else {
         return true;
      }
   }

   public boolean freeze() {
      return this.khamEff[3] > 0L || this.khamEff[4] > 0L;
   }

   public void updateEffectBuff() {
      for(int i = 0; i < this.AllEffBuff.size(); ++i) {
         EffectBuff ef = (EffectBuff)this.AllEffBuff.get(i);
         if (ef.timeOut()) {
            if (this.cat == 0 && ef.isTanPhe()) {
               this.resetSpeed();
            }

            this.AllEffBuff.remove(i);
            this.hashEffBuff.remove(ef.idEff);
         }
      }

   }

   public void checkActiveSkillPet() {
   }

   public boolean haveDamAnhsang() {
      return false;
   }

   public boolean haveDamBongtoi() {
      return false;
   }

   public boolean isBatTu() {
      return false;
   }

   public void resetSpeed() {
   }

   public void sendRemoveEffToChar(int idEff) {
   }

   public void huyEff(Char p, int idEff) 
   {
      for(int i = 0; i < this.AllEffBuff.size(); ++i) 
      {
         try 
         {
            EffectBuff ef = (EffectBuff)this.AllEffBuff.get(i);
            if (idEff == ef.idEff) 
            {
               ef.timeExist = 1L;
               Message m = MessageCreator.createMsgRemoveDynamicNewEffect(idEff, 1L, 1, p.id, p.x / 16, p.y / 16, p.cat, 1);
               p.sendMessage(m);
               p.sendToNearPlayer(m);
               System.out.println("xóa eff " + ef.idEff +" khỏi nhân vật: " +  ef.timeExist );
               break;
            }
         } 
         catch (Exception var6) 
         {
             System.out.println("Lỗi khi xóa eff khỏi nhân vật: " +  var6);
         }
      }
   }

   
   //thêm 1
   public synchronized void huyAllEff(Char p) 
   { 
      for(int i = 0; i < this.AllEffBuff.size(); ++i) 
      {
         try 
         {
            EffectBuff ef = (EffectBuff)this.AllEffBuff.get(i);
            ef.timeExist = 1L;
            p.sendMessage(MessageCreator.createMsgRemoveDynamicNewEffect(ef.idEff, 1L, 1, p.id, p.x / 16, p.y / 16, p.cat, 1));
         } 
         catch (Exception var4) 
         {
            var4.printStackTrace();
         }
      }
      this.AllEffBuff.removeAllElements();
      System.out.println("xóa All Eff  khỏi nhân vật: " + p.charname + ", eff còn lại: " + AllEffBuff.size());
   }

   public void sendEffToChar(Char p) 
   {
      for(int i = 0; i < this.AllEffBuff.size(); ++i) 
      {
         try 
         {
            EffectBuff ef = (EffectBuff)this.AllEffBuff.get(i);
            p.sendMessage(MessageCreator.createMsgAddDynamicNewEffect(ef.idEff, ef.timeExist, ef.type, this.id, this.x / 16, this.y / 16, this.cat, ef.getODER_PAINT(), ef.damFly));
         } 
         catch (Exception var4) 
         {
             System.out.println("Lỗi khi send eff to nhân vật: " +  var4);
         }
      }

   }

   public void sendEffInMap(EffectBuff ef, Char p) {
      try {
         p.sendMessage(MessageCreator.createMsgAddDynamicNewEffect(ef.idEff, ef.timeExist, ef.type, 32001, ef.x, ef.y, 127, ef.getODER_PAINT(), ef.damFly));
      } catch (Exception var4) {
      }

   }

   public void sendEffInMap(Char p) {
      for(int i = 0; i < this.AllEffBuff.size(); ++i) {
         try {
            EffectBuff ef = (EffectBuff)this.AllEffBuff.get(i);
            p.sendMessage(MessageCreator.createMsgAddDynamicNewEffect(ef.idEff, ef.timeExist, ef.type, 32001, this.x / 16, this.y / 16, 127, ef.getODER_PAINT(), ef.damFly));
         } catch (Exception var4) {
         }
      }

   }

   public void setTarget(Char tg) {
   }

   public void sendEffToNearChar() {
   }

   public void sendEffToNearChar(Char p) {
      for(int i = 0; i < this.AllEffBuff.size(); ++i) {
         try {
            EffectBuff ef = (EffectBuff)this.AllEffBuff.get(i);
            p.sendMessage(MessageCreator.createMsgAddDynamicNewEffect(ef.idEff, ef.timeExist, 1, this.id, this.x / 16, this.y / 16, this.cat, ef.getODER_PAINT(), ef.damFly));
            p.sendToNearPlayer(MessageCreator.createMsgAddDynamicNewEffect(ef.idEff, ef.timeExist, 1, this.id, this.x / 16, this.y / 16, this.cat, ef.getODER_PAINT(), ef.damFly));
         } catch (Exception var4) {
         }
      }

   }

   public boolean haveEffectTree() {
      return this.hashEffBuff.get(EffectBuff.TREE) != null;
   }
   
   // thêm 2
   public synchronized EffectBuff addEffBuff(final int id, final long time, final int type, final int dam)
   {
      if (this.hashEffBuff.get((short)id) != null) 
      {
         if (!((EffectBuff)this.hashEffBuff.get((short)id)).timeOut()) 
         {
            return null;
         }

         this.AllEffBuff.remove(this.hashEffBuff.get((short)id));
         this.hashEffBuff.remove((short)id);
      }

      EffectBuff ef = new EffectBuff(id, time);
      this.AllEffBuff.add(ef);
      ef.type = (byte)type;
      ef.damFly = dam;
      this.hashEffBuff.put((short)id, ef);
      System.out.println("(" + ((Char)this).charname + ") Add Eff Buff: " +  id + " -> exist until: " + ef.timeExist + " (+" + time + "ms)");

      return ef;
   }

   public boolean checkBong() {
      return this.hashEffBuff.get(EffectBuff.BONG) != null;
   }

   public boolean checkSuyYeu() {
      return this.hashEffBuff.get(EffectBuff.SUY_YEU) != null;
   }

   public boolean checkTrungDoc() {
      return this.hashEffBuff.get(EffectBuff.TRUNG_DOC) != null;
   }

   public boolean checkChoang() {
      return this.hashEffBuff.get(EffectBuff.CHOANG) != null;
   }

   public boolean checkRuNgu() {
      return this.hashEffBuff.get(EffectBuff.RU_NGU) != null;
   }

   public boolean checkTanPhe() {
      return false;
   }

   public boolean checkLamthinh() {
      return false;
   }

   public boolean khangDongBang() {
      return false;
   }

   public boolean khangBangLan(int giamKhang) {
      return false;
   }

   public boolean khangLuaLan(int giamKhang) {
      return false;
   }

   public boolean khangBiChay() {
      return false;
   }

   public boolean khangSetLan(int giamKhang) {
      return false;
   }

   public boolean khangDocLan(int giamKhang) {
      return false;
   }

   public int khamKhangMu() {
      return 0;
   }

   public int khamKhangBang() {
      return 0;
   }

   public int khamKhangDoc() {
      return 0;
   }

   public int khamKhangChoang() {
      return 0;
   }

   public int khamKhangHoathach() {
      return 0;
   }

   public int khamKhangGiamtoc() {
      return 0;
   }

   public int khamX2() {
      return 0;
   }

   public int khamDropItem() {
      return 0;
   }

   public void updateEffKham() {
      for(int i = 0; i < this.khamEff.length; ++i) {
         if (this.khamEff[i] > 0L) {
            long t = System.currentTimeMillis() / 1000L;
            if (System.currentTimeMillis() - this.timeOutKhamDoc >= 1L && this.timeOutKhamDoc > 0L && i == 1) {
               this.timeOutKhamDoc = System.currentTimeMillis();
               this.hp -= 1000;
               if (this.hp <= 0) {
                  this.actorDie();
                  this.hp = 0;
                  this.timeOutKhamDoc = 0L;

                  for(int j = 0; j < this.khamEff.length; ++j) {
                     this.khamEff[i] = 0L;
                  }

                  return;
               }
            }

            if (t - this.khamEff[i] > 3L) {
               this.khamEff[i] = 0L;
               this.timeOutKhamDoc = 0L;
            }
         }
      }

   }

   public void update() {
   }

   public void subUpdate() {
   }

   public boolean isCharMonster() {
      return false;
   }

   public boolean isCritical() {
      return UtilKPAH.random(100) < this.critical;
   }

   public int attackDam(LiveActor actor) {
      int dam = this.attack;
      int defvat = actor.defend_physic + actor.percentBuff[0];
      int updef = actor.getValueLua(1);
      defvat += defvat * updef / 100;
      defvat += defvat * 5 / 100;
      defvat += defvat * ((Char)actor).getEffSkillClanMember(1) / 100;
      int defMa = actor.defend_magic + actor.getBuffDefCB(1, true);
      defMa += defMa * updef / 100;
      defMa += defMa * 5 / 100;
      defMa += defMa * ((Char)actor).getEffSkillClanMember(2) / 100;
      int def;
      if (defMa < defvat) {
         def = defvat;
      } else if (defMa > defvat) {
         def = defMa;
      } else {
         def = defvat;
      }

      if (actor.cat == 0) {
         int level = this.getLevel();
         int deltaLV = level - ((Char)actor).lvDetail.lv;
         if (deltaLV > 0) {
            dam += dam * Map.abs(deltaLV / 5);
            if (Map.abs(deltaLV) > 5 && Map.abs(deltaLV) <= 10) {
               def /= 2;
            } else if (Map.abs(deltaLV) > 10 && Map.abs(deltaLV) <= 15) {
               def /= 3;
            } else if (Map.abs(deltaLV) > 15) {
               def /= 10;
            }

            dam += 70 * deltaLV;
            def -= 20 * deltaLV;
         } else if (deltaLV < 0) {
            dam += 70 * deltaLV;
         }
      }

      if (dam < 5) {
         dam = 5 + Map.r.nextInt(10);
      }

      EffectBuff ef;
      if (this.checkSuyYeu()) {
         ef = (EffectBuff)this.hashEffBuff.get(EffectBuff.SUY_YEU);
         dam -= dam * ef.dam / 100;
      }

      if (actor.checkSuyYeu()) {
         ef = (EffectBuff)actor.hashEffBuff.get(EffectBuff.SUY_YEU);
         def -= def * ef.dam / 100;
      }

      dam -= def;
      dam = dam * (30 + UtilKPAH.random(80)) / 100;
      if ((this.he + 2) % 5 == actor.he) {
         dam += dam * 5 / 100;
      } else if ((this.he + 3) % 5 == actor.he) {
         dam -= dam * 5 / 100;
      }

      dam = dam * (this.percentDam + Map.r.nextInt(20)) / 100;
      if (dam < 5) {
         dam = 5 + Map.r.nextInt(10);
      }

      return dam;
   }

   public int getX(int idCountry) {
      return 0;
   }

   public int getY(int idCountry) {
      return 0;
   }

   public void actorDie() {
   }

   public int getBuffDefCB(int type, boolean isMons) {
      return 0;
   }

   public int getHP(int country) {
      if (this.hp < 0) {
         this.hp = 0;
      }

      return this.hp;
   }
   //hoahinh
   public int getDefendPhysic() {
      int def = this.defend_physic;
      // Check if defense reduction is still valid
      if (this.currentDefReduc > 0 && System.currentTimeMillis() - this.timeFirstDefReduc < 10000) {
          def -= def * this.currentDefReduc / 100;
      } else if (this.currentDefReduc > 0) {
          // Reset if time expired
          this.currentDefReduc = 0;
          this.timeFirstDefReduc = 0L;
          this.timeResetDefReduc = 0L;
      }
      return def;
   }
 //hoahinh
   public int getDefendMagic() {
      int def = this.defend_magic;
      // Check if defense reduction is still valid
      if (this.currentDefReduc > 0 && System.currentTimeMillis() - this.timeFirstDefReduc < 10000) {
          def -= def * this.currentDefReduc / 100;
      } else if (this.currentDefReduc > 0) {
          // Reset if time expired
          this.currentDefReduc = 0;
          this.timeFirstDefReduc = 0L;
          this.timeResetDefReduc = 0L;
      }
      return def;
   }

   

   public int getAttack() {
      if (this instanceof Char && ((Char)this).isLinhThueLan()) {
         return this.attack + (this.attack * 5 / 100); // Increase attack by 5%
      }
      return this.attack;
   }

   public int haveRegentHP() {
      return 0;
   }

   public int haveRegentMP() {
      return 0;
   }

   public int haveTanPhe() {
      return 0;
   }

   public int haveLamThinh() {
      return 0;
   }

   public int haveKhangBangLan() {
      return 0;
   }

   public int haveKhangSetLan() {
      return 0;
   }

   public int haveKhangDocLan() {
      return 0;
   }

   public int haveHutHp() {
      return 0;
   }

   public int haveNeTranh() {
      return 0;
   }

   public boolean canAttack(Char p) {
      return false;
   }

   public boolean isMagic() {
      return this.magic_physic == 0;
   }

   public Vector<Actor> onDropItem(Map m, Char p) {
      return new Vector();
   }

   public int getXpReceive(int hpLost) {
      return 0;
   }

   public boolean getPaintHat() {
      return true;
   }

   public boolean isMonsterSumon() {
      return false;
   }

   public boolean isKhangDoSat() {
      return false;
   }

   public boolean isKhangLan(int type, int giamKhang) {
       switch (type) {
           case 1:
               return this.khangSetLan(giamKhang);
           case 0:
               return this.khangBangLan(giamKhang);
           default:
               return type == 2 ? this.khangDocLan(giamKhang) : this.khangLuaLan(giamKhang);
       }
   }

   public boolean isStune() {
      new Vector();

      for(int i = 0; i < this.AllEffBuff.size(); ++i) {
         EffectBuff ef = (EffectBuff)this.AllEffBuff.get(i);
         if (ef.timeOut()) {
            this.AllEffBuff.remove(i);
            this.hashEffBuff.remove(ef.idEff);
         } else if (ef.canNotMove()) {
            return true;
         }
      }

      return false;
   }

   public boolean isNgu() {
      for(int i = 0; i < this.AllEffBuff.size(); ++i) {
         EffectBuff ef = (EffectBuff)this.AllEffBuff.get(i);
         if (ef.idEff == EffectBuff.RU_NGU) {
            return true;
         }
      }

      return false;
   }

   public boolean isHoangSo() {
      for(int i = 0; i < this.AllEffBuff.size(); ++i) {
         EffectBuff ef = (EffectBuff)this.AllEffBuff.get(i);
         if (ef.idEff == EffectBuff.EFF_HOANG_SO) {
            return true;
         }
      }

      return false;
   }

   public boolean beRiMau() {
      for(int i = 0; i < this.AllEffBuff.size(); ++i) {
         EffectBuff ef = (EffectBuff)this.AllEffBuff.get(i);
         if (ef.idEff == EffectBuff.EFF_RI_MAU) {
            return true;
         }
      }

      return false;
   }

   public boolean isRuongMaquai() {
      return false;
   }

   public boolean isNgocRong() {
      return false;
   }

   public boolean isBossBaoCat() {
      return false;
   }

   public boolean isBossVanTienTran() {
      return false;
   }

   public boolean isBoss() {
      return false;
   }

   public boolean isMonsterVanTienTran() {
      return false;
   }

   public int getWave() {
      return 0;
   }

   public boolean isLienHoaTru() {
      return false;
   }

   public boolean isTruMoba() {
      return false;
   }

   public boolean isThungGoNuiChauBau() {
      return false;
   }

   public boolean haveSuyYeu() {
      return false;
   }

   public int getPcSuyyeu() {
      return 0;
   }

   public int getInCountry() {
      return -1;
   }

   public int getCountry() {
      return -1;
   }

   public int checkGiamSatThuongTong(int dam) {
      return 0;
   }

   public boolean isCheckActiveBuffGiamSatThuong() {
      return false;
   }

   public void doHoaNguoiTuyetKhiBiDanh(LiveActor p) {
   }

   public void do8sRongLon(LiveActor p) {
   }

   public boolean isActiveTienKhi() {
      return false;
   }

   public boolean isActive8sRongLon() {
      return false;
   }

   public Animal getAnimalRide() {
      return null;
   }

   public boolean isBossGetItemByHit() {
      return false;
   }

   public boolean isBossSonTinh() {
      return false;
   }

   public boolean isBossThuyTinh() {
      return false;
   }

   public void checkReceivePotion(Char p) {
   }

   public boolean isBossSonTinhThuyTinh() {
      return false;
   }

   public int getMaxDamPerHit() {
      return 0;
   }

   public boolean isMonsterMoba() {
      return false;
   }

   public int getPointChienTruong() {
      return 0;
   }

   public boolean isDie() {
      return false;
   }

   public Pet getPetUsing() {
      return null;
   }
   

   //hoahinh
   public void applyHoaHinhDefenseReduction() {
      long currentTime = System.currentTimeMillis();
      
      // Nếu là lần giảm giáp đầu tiên
      if (this.currentDefReduc == 0) {
          this.timeFirstDefReduc = currentTime;
          this.currentDefReduc = 10;
          this.timeResetDefReduc = currentTime + 10000; // 10 giây
          return;
      }
      
      // Kiểm tra nếu đã quá 10s kể từ lần giảm đầu tiên
      if (currentTime - this.timeFirstDefReduc >= 10000) {
          // Reset toàn bộ
          this.currentDefReduc = 0;
          this.timeFirstDefReduc = 0L;
          this.timeResetDefReduc = 0L;
          return;
      }
      
      // Nếu chưa quá 10s và chưa đạt max (50%)
      if (this.currentDefReduc < 50) {
          this.currentDefReduc += 10;
      }
   }
   
   public int getCurrentDefReduc() {
       // Kiểm tra nếu đã quá 10s kể từ lần giảm đầu tiên
       if (this.timeFirstDefReduc > 0 && System.currentTimeMillis() - this.timeFirstDefReduc >= 10000) {
           this.currentDefReduc = 0;
           this.timeFirstDefReduc = 0L;
           this.timeResetDefReduc = 0L;
       }
       return this.currentDefReduc;
   }
}
