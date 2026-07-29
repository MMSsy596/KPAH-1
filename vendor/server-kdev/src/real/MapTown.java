package real;

import data.BossThuThanh;
import data.Database;
import data.DragonTower;
import data.LienHoaTru;
import data.NewClan;
import io.Message;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import server.TeamServer;

public class MapTown extends Map {
   public static byte[] attacker = new byte[]{-1, -1, -1};
   public static byte[] beAttacked = new byte[]{-1, -1, -1};
   public static Vector<Monster> congthanh = new Vector();
   Vector<Monster> allBossThuThanh = new Vector();
   short idMonster = -32000;
   Vector<Monster> allsmallDragon = new Vector();
   Vector<Monster> allmainDragon = new Vector();
   public static int gameOverByAdmin = -1;
   private static Vector<Vector<DragonTower>> smallDragon = new Vector();
   private static Vector<Vector<Monster>> mboss = new Vector();
   private static Vector<Vector<DragonTower>> mainDragon = new Vector();
   public static Vector<Vector<Vector<Monster>>> towerDefend = new Vector();
   public static byte[][] posDragonTower = new byte[][]{{40, 34}, {40, 55}, {26, 47}, {54, 47}};
   public static byte[][] posTower = new byte[][]{
      {37, 36, 37, 31, 43, 31, 43, 36}, {37, 57, 37, 52, 43, 52, 43, 57}, {23, 49, 23, 44, 29, 44, 29, 49}, {51, 49, 51, 44, 57, 44, 57, 49}
   };
   Vector<Monster> allLienHoaTru = new Vector();
   public static int timeStart;
   public static int minuteStart;
   Vector<Monster> allLienHoaTru1 = new Vector();
   static boolean isStartWar;
   public static boolean[] addDragon;
   static int[][] POS_TRU_RONG;
   public static Vector<BossTruRong> allBossTruRong;
   public static boolean isChiemThanh;
   public static int TimeAdminCheat;
   public static int minuteAdminCheat;
   public static int count_all_item_sell_per_day;
   public static boolean isSkipDayCheck = false;

   static {
      for (int i = 0; i < 3; i++) {
         Vector<Vector<Monster>> v = new Vector();
         v.add(new Vector());
         v.add(new Vector());
         v.add(new Vector());
         v.add(new Vector());
         towerDefend.add(v);
      }

      smallDragon.add(new Vector());
      smallDragon.add(new Vector());
      smallDragon.add(new Vector());
      mainDragon.add(new Vector());
      mainDragon.add(new Vector());
      mainDragon.add(new Vector());
      mboss.add(new Vector());
      mboss.add(new Vector());
      mboss.add(new Vector());
      timeStart = 0;
      minuteStart = 0;
      isStartWar = false;
      addDragon = new boolean[3];
      POS_TRU_RONG = new int[][]{{40, 16}, {22, 45}, {56, 45}};
      allBossTruRong = new Vector();
      isChiemThanh = false;
      TimeAdminCheat = -1;
      minuteAdminCheat = 2;
      count_all_item_sell_per_day = 0;
   }

   public MapTown() {
   }

   public MapTown(int id, int idXaphu, int magic_physic, int mapload, int nregion) {
      super(id, idXaphu, magic_physic, mapload, nregion);
      (new Thread() {
         public void run() {
            while(!AdminHandler.isStopServer) {
               try {
                  int i;
                  for(i = 0; i < MapTown.allBossTruRong.size(); ++i) {
                     try {
                        ((BossTruRong)MapTown.allBossTruRong.get(i)).dosendMoveAll(false);
                        ((BossTruRong)MapTown.allBossTruRong.get(i)).update();
                     } catch (Exception var3) {
                     }
                  }

                  for(i = 0; i < MapTown.this.allBossThuThanh.size(); ++i) {
                     try {
                        ((Monster)MapTown.this.allBossThuThanh.get(i)).update();
                        if (((Monster)MapTown.this.allBossThuThanh.get(i)).isDead || ((Monster)MapTown.this.allBossThuThanh.get(i)).hp < 0) {
                           MapTown.this.allBossThuThanh.remove(i);
                        }
                     } catch (Exception var8) {
                     }
                  }

                  for(i = 0; i < MapTown.this.allLienHoaTru.size(); ++i) {
                     try {
                        ((Monster)MapTown.this.allLienHoaTru.get(i)).update();
                        if (((Monster)MapTown.this.allLienHoaTru.get(i)).isDead || ((Monster)MapTown.this.allLienHoaTru.get(i)).hp < 0) {
                           MapTown.this.allLienHoaTru.remove(i);
                        }
                     } catch (Exception var7) {
                     }
                  }

                  for(i = 0; i < MapTown.this.allLienHoaTru1.size(); ++i) {
                     try {
                        ((Monster)MapTown.this.allLienHoaTru1.get(i)).update();
                        ((Monster)MapTown.this.allLienHoaTru1.get(i)).update();
                        if (((Monster)MapTown.this.allLienHoaTru1.get(i)).isDead || ((Monster)MapTown.this.allLienHoaTru1.get(i)).hp < 0) {
                           MapTown.this.allLienHoaTru1.remove(i);
                        }
                     } catch (Exception var6) {
                     }
                  }

                  for(i = 0; i < MapTown.this.allsmallDragon.size(); ++i) {
                     try {
                        ((Monster)MapTown.this.allsmallDragon.get(i)).update();
                        ((Monster)MapTown.this.allsmallDragon.get(i)).update();
                        if (((Monster)MapTown.this.allsmallDragon.get(i)).isDead || ((Monster)MapTown.this.allsmallDragon.get(i)).hp < 0) {
                           MapTown.this.allsmallDragon.remove(i);
                        }
                     } catch (Exception var5) {
                     }
                  }

                  for(i = 0; i < MapTown.this.allmainDragon.size(); ++i) {
                     try {
                        ((Monster)MapTown.this.allmainDragon.get(i)).update();
                        ((Monster)MapTown.this.allmainDragon.get(i)).update();
                        if (((Monster)MapTown.this.allmainDragon.get(i)).isDead || ((Monster)MapTown.this.allmainDragon.get(i)).hp < 0) {
                           MapTown.this.allmainDragon.remove(i);
                        }
                     } catch (Exception var4) {
                     }
                  }

                  Thread.sleep(500L);
               } catch (Exception var9) {
               }
            }

         }
      }).start();
   }

   private void doAddBossThuThanh(int country) {
      Monster m = new BossThuThanh(this, (MonsterTemplate)monsterTemplates.get(46), 640, 304, country);
      m.level = m.getMonsterTemplate().level;
      m.id = this.idMonster--;
      byte[] he = new byte[]{0, 1, 2, 3, 4};
      m.he = he[r.nextInt(5)];
      byte[] t = new byte[]{0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0};
      m.typeAttack = t[r.nextInt(10)];
      m.bornTime = 120000L;
      this.allBossThuThanh.add(m);
      ((Vector)mboss.get(country)).add(m);
      ((Vector)this.tempMonster.get(country)).add(m);
   }

   protected void setCoolDownHp(Char player) {
      try {
         short[] id = new short[]{1, 2, 3, 21, 22, 93, 94, 126, 127};

         for (int i = 0; i < id.length; i++) {
            ((PotionUse)player.coolDownPotion.get(id[i])).setCoolDownTown();
         }
      } catch (Exception var4) {
      }
   }

   protected boolean doUseHP(Char player, short potionType, int hpAdd) throws IOException {
      if (player.checkChoang() && hpAdd > 0) {
         return false;
      } else if (player.hp <= 0) {
         return false;
      } else if (((PotionUse)player.coolDownPotion.get(potionType)).doUsePotion() && !player.isVetThuongSau()) {
         this.setCoolDownHp(player);
         player.hp = player.hp + hpAdd + hpAdd * player.getEffSkillClanMember(3) / 100;
         player.calculatorHPMP();
         if (player.hp <= 0) {
            player.hp = 0;
         }

         Message m = new Message(22);
         m.dos.writeShort(player.id);
         m.dos.writeByte(potionType);
         m.dos.writeShort(hpAdd);
         m.dos.writeInt(player.hp);
         m.dos.writeByte(1);
         player.sendMessage(m);
         player.sendToNearPlayer(m);
         m.cleanup();
         return true;
      } else {
         player.potions[potionType]++;
         player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
         return false;
      }
   }

   protected void setCoolDownMp(Char player) {
      try {
         short[] id = new short[]{4, 5, 6, 23, 24, 95, 96, 126, 127};

         for (int i = 0; i < id.length; i++) {
            ((PotionUse)player.coolDownPotion.get(id[i])).setCoolDown();
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }
   }

   protected boolean doUseMP(Char player, short potionType, int mpAdd) throws IOException {
      if (((PotionUse)player.coolDownPotion.get(potionType)).doUsePotion()) {
         this.setCoolDownMp(player);
         player.mp += mpAdd;
         player.calculatorHPMP();
         Message m = new Message(22);
         m.dos.writeShort(player.id);
         m.dos.writeByte(potionType);
         m.dos.writeShort(mpAdd);
         m.dos.writeInt(player.mp);
         player.sendMessage(m);
         player.sendToNearPlayer(m);
         m.cleanup();
         return true;
      } else {
         player.potions[potionType]++;
         player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
         return false;
      }
   }

   public void doAddDragonTower(int country) {
      for (byte j = 0; j < 4; j++) {
         DragonTower m = new DragonTower(this, (MonsterTemplate)monsterTemplates.get(37), posDragonTower[j][0] * 16, posDragonTower[j][1] * 16, country);
         m.level = m.getMonsterTemplate().level;
         m.id = this.idMonster--;
         byte[] he = new byte[]{0, 1, 2, 3, 4};
         m.he = he[r.nextInt(5)];
         byte[] t = new byte[]{0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0};
         m.typeAttack = t[r.nextInt(10)];
         m.bornTime = 120000L;
         m.posTower = j;
         if (((Vector)smallDragon.get(country)).size() < 4) {
            ((Vector)this.tempMonster.get(country)).add(m);
            ((Vector)smallDragon.get(country)).add(m);
            this.allsmallDragon.add(m);
         }
      }

      DragonTower m = new DragonTower(this, (MonsterTemplate)monsterTemplates.get(36), 640, 272, country);
      m.level = m.getMonsterTemplate().level;
      m.id = this.idMonster--;
      byte[] he = new byte[]{0, 1, 2, 3, 4};
      m.he = he[r.nextInt(5)];
      byte[] t = new byte[]{0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0};
      m.typeAttack = t[r.nextInt(10)];
      m.bornTime = 120000L;
      if (((Vector)mainDragon.get(country)).size() == 0) {
         ((Vector)this.tempMonster.get(country)).add(m);
         ((Vector)mainDragon.get(country)).add(m);
         this.allmainDragon.add(m);
      }
   }

   protected void doGiveCardTown(Char player, Message message) {
   }

   public synchronized void playerGiveCard(Char player, int posNpc) {
      try {
         if (player.myCountry == -1) {
            return;
         }

         if (!getTown[player.inCountry]) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Thời gian giao thẻ đã kết thúc.", ""));
            return;
         }

         if (player.freeze()) {
            return;
         }

         if (player.lvDetail.lv < 50) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải đạt cấp độ 50 trở lên mới có thể giao thẻ.", ""));
            return;
         }

         if (player.canGiveCard == -1) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Bạn chưa giành được quyền giao thẻ", ""));
            return;
         }

         if (player.canGiveCard != posNpc) {
            player.canGiveCard = -1;
            player.sendMessage(MessageCreator.createServerAlertMessage("Bạn không thể giao thẻ tại vị trí này", ""));
            return;
         }

         if (player.potions[33] <= 0) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Chưa có thẻ", ""));
            return;
         }

         if (player.idClan == -1) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Chưa có bang hội", ""));
            return;
         }

         if (player.hp <= 0) {
            if (getTown[player.inCountry] && giveCardFail(player)) {
               this.doSend2AllChar(
                  MessageCreator.createServerAlertAutoOffMessage("Bang " + Map.getClaninfoByID(player.idClan).name + " giao thẻ thất bại"), player.inCountry
               );
               this.sendAllPlayer(MessageCreator.createMsgStartGetTown(player.inCountry), player.inCountry);
            }

            return;
         }

         if (player.map.mapId != idMapTown || player.inCountry != player.myCountry) {
            CharManager.instance.kickPlayer(player, "maptown 1");
            Database.instance.saveOrtherLog("tob_log_other_item", player.charname, "hack giao the >> " + player.charname, "hackc");
            return;
         }

         if (this.givingCard(player)) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể giao thêm thẻ khi đang trong quá trình giao thẻ.", ""));
            return;
         }

         NpcReceiveCard npc = (NpcReceiveCard)((Vector)npcReceiveCard.get(player.inCountry)).get(posNpc);
         if (npc.idClan == player.idClan) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Bang của bạn chưa mất vị trí này.", ""));
            return;
         }

         if (!npc.giveCard(player, false)) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Giao thẻ không hợp lệ.", ""));
            return;
         }

         player.timeGiveCardTown = System.currentTimeMillis();
         player.posNPC = (byte)posNpc;
         player.potions[33]--;
         if (player.potions[33] < 0) {
            player.potions[33] = 0;
         }

         player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
         NewClan clan = getClaninfoByID(player.idClan);
         String namecl = "";
         namecl = clan.name;
         Vector<Char> players = this.getAllPlayer(player.inCountry, player.region);
         this.addEffBuffToMap(EffectBuff.EFF_CHIEM_THANH, System.currentTimeMillis() + 60000L, player.x / 16, player.y / 16, player.inCountry);

         try {
            player.x = player.x / 16 * 16;
            player.y = player.y / 16 * 16 - 1;
            Message m = new Message(4);
            player.writeActorPos(m, player);
            player.sendMessage(m);
            player.sendInfoMove2Near();
         } catch (Exception var10) {
         }

         player.canGiveCard = -1;

         for (int i = 0; i < players.size(); i++) {
            try {
               ((Char)players.get(i))
                  .sendMessage(
                     MessageCreator.createServerAlertAutoOffMessage("Bang " + namecl.toUpperCase() + " bắt đầu giao thẻ tại " + NpcReceiveCard.npc[npc.posNpc])
                  );
               ((Char)players.get(i)).sendMessage(MessageCreator.createMsgStartGetTown(player.inCountry));
            } catch (Exception var9) {
               var9.printStackTrace();
               System.out.println("LOI GUI THONG TIN BAO CO NG GIAO THE");
            }
         }
      } catch (Exception var11) {
         var11.printStackTrace();
         System.out.println("LOI KHI GIAO THE");
      }
   }

   public void doAttackMultiTarget(Char p, Message message) {
      try {
         if (p.countHit() || p.freeze()) {
            return;
         }

         if (p.isHoangSo() || p.isHoangLoan()) {
            return;
         }

         if (p.itemAx == null && this.mapId == 17) {
            return;
         }

         if (p.hp <= 0) {
            p.actorDie();
            return;
         }

         if (!p.checkDurableWeapone()) {
            p.sendMessage(MessageCreator.createServerAlertMessage("Không thể đánh khi vũ khí bị hỏng. Hãy đến Thợ rèn để sửa lại.", ""));
            Message m = new Message(104);

            try {
               m.dos.writeByte(p.typeConfig);
               m.dos.writeByte(0);
               p.sendMessage(m);
            } catch (Exception var38) {
            }

            return;
         }

         DataInputStream dis = message.dis;
         byte skill = dis.readByte();
         byte effect = 0;
         int ahp1 = 0;
         int crit = 0;
         int buffAttack = -1;
         if (buffAttack > 0) {
            return;
         }

         if (buffAttack != -1 && buffAttack == 0 && p.skill[5] + p.addMoreLevelSkill[5] == 0) {
            return;
         }

         if (nwar[p.inCountry] && p.myCountry != p.inCountry && nationBeAttack[p.inCountry] != p.myCountry) {
            int[] mapstart = new int[]{9, 481, 482, 483, 484};
            int homeX = 31 + Database.r.nextInt() % 5;
            int homeY = 79 + Database.r.nextInt(20);
            this.move2Map(p, homeX, homeY, mapstart[r.nextInt(mapstart.length)], p.inCountry);
            return;
         }

         int nMonster = dis.readByte();
         Monster firstMonster = null;
         short idMonster = dis.readShort();
         Monster mt = this.getMonster(idMonster, p.inCountry, p.region);
         firstMonster = mt;
         if (mt == null || mt.isDead) {
            this.onMosterDie(p, idMonster, skill, 1, effect, (byte)0);
            if (mt != null) {
               this.removeMonster(mt, mt.inCountry);
            }

            return;
         }

         if (!inRangeActor(p, mt, MAX_RANGE_CHAR[p.charClass])) {
            return;
         }

         if (mt.map.mapId != p.mapID) {
            return;
         }

         if (mt.inCountry == p.myCountry && !mt.isCongThanh() && !mt.isBossTruRong()) {
            return;
         }

         if (mt.getIDClan() == p.idClan) {
            return;
         }

         if (mt.inCountry != p.myCountry && mt.isCongThanh()) {
            return;
         }

         if (mt.getMonsterTemplate().id == 36 && !this.canAttackMainDragon(mt)) {
            return;
         }

         if (mt.getMonsterTemplate().id == 37 && !this.canAttackSmallDragon(mt)) {
            return;
         }

         int _type = skill;
         int _level = p.skill[skill] + p.addMoreLevelSkill[skill];
         if (_level <= 0) {
            _level = p.addMoreLevelSkill[skill];
         }

         if (_level <= 0 || !inRangeSkill(p, mt, CharManager.getSkillRange((byte)skill, p.charClass))) {
            return;
         }

         long now = System.currentTimeMillis();
         if (now - p.timeLastUseSkills[skill] < (long)p.coolDown[skill][_level]) {
            return;
         }

         p.timeLastUseSkills[skill] = now;
         buffAttack = p.getBuffEffAttack();
         if (mt.resistThroughArmor()) {
            buffAttack = -1;
         }

         int damage = p.attackDam(mt, skill, _level, buffAttack);
         if (mt.haveDodge()) {
            damage = 0;
            buffAttack = -1;
         }

         damage *= CharManager.UP_DAMGE_SKILL[p.charClass][skill][_level - 1];
         boolean critSv = p.havecrit();
         if (critSv) {
            damage *= 2;
            effect = 2;
            if (p.petUsing != null) {
               long pcLienKich = (long)p.petUsing.getLienKich();
               damage = (int)((long)damage + (long)damage * pcLienKich / 100L);
            }
         }

         if (_level > p.skill[skill] + p.addMoreLevelSkill[skill]) {
            return;
         }

         int mplost = CharManager.SKILL_MP[p.charClass][skill][_level];
         if (p.mp + p.percentBuff[1] < mplost) {
            return;
         }

         p.mp -= mplost;
         if (p.mp <= 0) {
            p.mp = 0;
         }

         if (damage > 0 && mt.haveBackDam()) {
            int backdam = mt.getBackDam(damage);
            Message mbd = MessageCreator.createMsgBuffEffect(5, mt.cat, p, backdam, 0, -1);
            p.sendMessage(mbd);
            p.sendToNearPlayer(mbd);
         }

         Message m = null;
         int i = 0;
         int allXP = 0;
         Vector<Monster> mst = new Vector();
         mst.add(mt);
         byte[] nmonster = new byte[]{5, 5, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10};
         Vector<Message> msgMonsterDie = new Vector();
         if (this.mapId == 17) {
            nmonster = new byte[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
         }

         int damNguyetAnh = p.getPCDamNguyetAnh(skill);
         Vector<LiveActor> muctieu = new Vector();

         while (i < nMonster) {
            if (i > 0) {
               idMonster = dis.readShort();
               mt = this.getMonster(idMonster, p.inCountry, p.region);
            }

            if (mt != null) {
               if (i > 0) {
                  if (!inRangeActor(firstMonster, mt, CharManager.getRangeSkillAeo(p.charClass, skill, _level))) {
                     i++;
                     continue;
                  }

                  if (mt.isDead) {
                     onMosterDie(p, mt, skill, damage, effect, (byte)0);
                  } else {
                     if (mt.getIDClan() == p.idClan) {
                        i++;
                        continue;
                     }

                     if (mt.getMonsterTemplate().id == 36 && !this.canAttackMainDragon(mt)) {
                        i++;
                        continue;
                     }

                     mst.add(mt);
                  }
               }

               int dxp = mt.getXpReceive(damage);
               if (dxp == 0) {
                  dxp = 1;
               }

               int[] downPercent = new int[]{1, 5, 10, dxp};
               int targetLv = p.getLevel();
               if (targetLv < 40) {
                  downPercent = new int[]{1, 2, 3, dxp};
               }

               int delta = targetLv - mt.level;
               if (delta > 0) {
                  int a = delta / 4;
                  if (targetLv < 40) {
                     a = delta / 6;
                  }

                  if (a > 3) {
                     a = 3;
                  }

                  dxp /= downPercent[a];
               }

               if (dxp <= 0) {
                  dxp = 1;
               }

               allXP += dxp;
               mt.hp -= damage;
             
               if (p.charthanthu != null && mt.hp > 0) {
                  muctieu.add(mt);
                  int damthanthu = p.getDamtThanThu(mt);
                  allXP += mt.getXpReceive(damthanthu);
                  mt.hp -= damthanthu;
               }

               if (mt.hp > 0 && mt.hp > 0 && damNguyetAnh > 0) {
                  mt.hp = mt.hp - mt.maxhp * damNguyetAnh / 100;
                  damage += mt.maxhp * damNguyetAnh / 100;
                  p.sendEffectBuff(mt, EffectBuff.EFF_NGUYET_ANH, 1000);
               }

               if (mt.hp <= 0) {
                  Vector<Actor> droplist = new Vector();
                  if (!mt.isMaterialMons()) {
                     if (p.killer > 0 && p.isKiller) {
                        p.killer--;
                        p.isKiller = p.killer > 0;
                        if (!p.isKiller) {
                           p.nPKill = 0;
                           p.timeKiller = 0L;
                        }

                        Message mm = new Message(67);
                        mm.dos.writeShort(p.id);
                        mm.dos.writeByte(p.isKiller ? 1 : 0);
                        mm.dos.writeShort(p.killer);
                        p.sendMessage(mm);
                        p.sendToNearPlayer(mm);
                        mm.cleanup();
                     }

                     mt.hp = 0;
                     int x2Player = p.getX2();
                     if (doubleALL > 1) {
                        int var69 = 0;
                     }

                     int n = 10000;
                     m = new Message(17);
                     m.dos.writeShort(p.id);
                     m.dos.writeShort(mt.id);
                     m.dos.writeByte(skill);
                     m.dos.writeInt(damage);
                     m.dos.writeByte(effect);
                     m.dos.writeByte(droplist.size());
                     if (droplist.size() > 0) {
                        for (Actor e : droplist) {
                           writeActorPos(m, e, p.getSession().isOldVersion);
                        }
                     }

                     int xx2 = CharManager.UP_DAMGE_SKILL[p.charClass][_type][_level - 1 >= 0 ? _level - 1 : 0];
                     m.dos.writeByte(xx2);
                     m.dos.writeByte(buffAttack);
                     m.dos.writeByte(_level);
                     msgMonsterDie.add(m);
                     if (p.receiveQuest && QuestTemplate.QUEST_TYPE[p.questID - 1] == 0) {
                        p.checkFinsishQuest(mt.getType(), -1, -1);
                     }
                  }
               } else if (mt.target == null) {
                  mt.target = p;
               }

               if (mt.hp <= 0) {
                  mt.isDead = true;
                  mt.target = null;
                  if (!mt.isBossTruRong()) {
                     this.removeMonster(mt, mt.inCountry);
                     mt.bornTime = System.currentTimeMillis() + 3600000L;
                     ((Vector)this.tempRemoveMonster.get(mt.inCountry)).add(mt);
                     ((Hashtable)this.monsters.get(mt.inCountry)).remove(mt.id);
                  }

                  if (mt.getMonsterTemplate().id == 46) {
                     synchronized ((Hashtable)this.monsters.get(mt.inCountry)) {
                        p.doAddGemItem(11, 3, false);
                        p.sendMessage(MessageCreator.createCharGemItem(p));
                     }
                  } else {
                     mt.charKillBoss(p);
                  }
               }
            } else {
               this.onMosterDie(p, idMonster, skill, 1, effect, (byte)0);
            }

            i++;
            if (mst.size() >= nmonster[_level]) {
               break;
            }
         }

         try {
            if (p.charthanthu != null && muctieu.size() > 0) {
               p.charthanthu.doAttack(muctieu);
            }
         } catch (Exception var40) {
         }

         m = new Message(106);
         m.dos.writeShort(p.id);
         m.dos.writeByte(skill);
         m.dos.writeInt(damage);
         m.dos.writeByte(effect);
         m.dos.writeByte(_level);
         m.dos.writeByte(buffAttack);
         m.dos.writeByte(mst.size());

         for (int j = 0; j < mst.size(); j++) {
            Monster ms = (Monster)mst.elementAt(j);
            m.dos.writeShort(ms.id);
            m.dos.writeInt(ms.hp > 0 ? ms.hp : 0);
         }

         p.sendMessage(m);
         p.sendToNearPlayer(m);

         for (int j = 0; j < msgMonsterDie.size(); j++) {
            try {
               p.sendMessage((Message)msgMonsterDie.get(j));
               p.sendToNearPlayer((Message)msgMonsterDie.get(j));
            } catch (Exception var39) {
            }
         }

         int dxpx = rand10(allXP);
         if (dxpx == 0) {
            dxpx = 1;
         }

         if (dxpx > 0) {
            int newxp = calculatorXpParty(p, dxpx);
            if (newxp == dxpx) {
               int var59 = dxpx * doubleALL;
               var59 = p.expReceive(var59);
               addXPForChar(p, (long)(var59 + p.getEffSkillClan(0) * var59 / 100), false, "maptown doAttackMultiTarget3");
            } else {
               int nUser = p.party.userParty.size();
               if (nUser > 1) {
                  nUser = 5;
               }

               int xpReceive = newxp * 80 / (nUser * 100);
               int maxLv = p.lvDetail.lv;

               for (int k = 0; k < p.party.userParty.size(); k++) {
                  Char pp = (Char)p.party.userParty.get(k);
                  if (pp.id != p.id && p.near(pp, 320) && pp.mapID == p.mapID && pp.inCountry == p.inCountry) {
                     int dlv = abs(maxLv - pp.lvDetail.lv);
                     int temp = 1;
                     if (dlv <= 5) {
                        temp = xpReceive;
                     } else if (dlv <= 10) {
                        temp = xpReceive / 5;
                     } else if (dlv <= 20) {
                        temp = xpReceive / 10;
                     } else if (dlv <= 30) {
                        temp = xpReceive / 15;
                     } else {
                        temp = xpReceive / 20;
                     }

                     if (temp == 0) {
                        temp = 1;
                     }

                     if (pp.hp > 0) {
                        temp *= doubleALL;
                        temp = pp.expReceive(temp);
                        addXPForChar(pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), false, "maptown doAttackMultiTarget1");
                     }
                  }
               }

               xpReceive = newxp * 20 / 100 * doubleALL;
               xpReceive = p.expReceive(xpReceive);
               addXPForChar(p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), false, "maptown doAttackMultiTarget2");
            }
         }

         p.charHireAttackMultiMOnster(mst, _type);
      } catch (Exception var42) {
         var42.printStackTrace();
      }
   }

   protected void doAttackMonster(Char p, Message message) throws IOException {
      if (!p.countHit() && !p.freeze()) {
         if (!p.isHoangSo() && !p.isHoangLoan()) {
            if (p.hp <= 0) {
               p.actorDie();
            } else if (!p.checkDurableWeapone()) {
               p.sendMessage(MessageCreator.createServerAlertMessage("Không thể đánh khi vũ khí bị hỏng. Hãy đến Thợ rèn để sửa lại.", ""));
               Message m = new Message(104);

               try {
                  m.dos.writeByte(p.typeConfig);
                  m.dos.writeByte(0);
                  p.sendMessage(m);
               } catch (Exception var35) {
               }

               System.out.println("KO CHO DANH 1");
            } else {
               p.downDurableWeapone();
               DataInputStream dis = message.dis;
               short idMonster = dis.readShort();
               Monster mt = this.getMonster(idMonster, p.inCountry, p.region);
               short skill = dis.readByte();
               byte effect = 0;
               int ahp = p.attackDamage;
               int crit = 0;
               int buffAttack = -1;
               if (buffAttack > 0) {
                  System.out.println("KO CHO DANH 2");
               } else if (buffAttack != -1 && buffAttack == 0 && p.skill[5] + p.addMoreLevelSkill[5] == 0) {
                  System.out.println("KO CHO DANH 3");
               } else if (nwar[p.inCountry] && p.myCountry != p.inCountry && nationBeAttack[p.inCountry] != p.myCountry) {
                  int[] mapstart = new int[]{9, 481, 482, 483, 484};
                  int homeX = 31 + Database.r.nextInt() % 5;
                  int homeY = 79 + Database.r.nextInt(20);
                  this.move2Map(p, homeX, homeY, mapstart[r.nextInt(mapstart.length)], p.inCountry);
                  System.out.println("KO CHO DANH 4");
               } else if (mt == null || mt.isDead) {
                  this.onMosterDie(p, idMonster, (byte)skill, 1, effect, (byte)0);
                  if (mt != null) {
                     this.removeMonster(mt, mt.inCountry);
                  }
               } else if (mt.inCountry == p.myCountry && !mt.isCongThanh() && !mt.isBossTruRong()) {
                  System.out.println("KO CHO DANH 5");
               } else if (mt.inCountry == p.myCountry || !mt.isCongThanh() && !mt.isBossTruRong()) {
                  if (!inRangeActor(p, mt, MAX_RANGE_CHAR[p.charClass])) {
                     System.out.println("KO CHO DANH 6");
                  } else if (mt.getIDClan() != p.idClan) {
                     if (mt.map.mapId == p.mapID) {
                        if (mt.getMonsterTemplate().id == 36 && !this.canAttackMainDragon(mt)) {
                           System.out.println("KO CHO DANH 7");
                        } else if (mt.getMonsterTemplate().id == 37 && !this.canAttackSmallDragon(mt)) {
                           System.out.println("KO CHO DANH 8");
                        } else {
                           int _type = (byte)skill;
                           int _level = p.skill[_type] + p.addMoreLevelSkill[_type];
                           if (_level <= 0) {
                              _level = p.addMoreLevelSkill[_type];
                           }

                           if (_level != 0) {
                              inRangeSkill(p, mt, CharManager.getSkillRange((byte)_type, p.charClass));
                           }

                           long now = System.currentTimeMillis();
                           if (now - p.timeLastUseSkills[_type] < (long)(CharManager.SKILL_COOLDOWN[p.charClass][_type][_level] * 100)) {
                              System.out.println("KO CHO DANH 9");
                           } else {
                              p.timeLastUseSkills[_type] = now;
                              buffAttack = p.getBuffEffAttack();
                              if (mt.resistThroughArmor()) {
                                 buffAttack = -1;
                              }

                              int damage = p.attackDam(mt, _type, _level, buffAttack);
                              if (mt.haveDodge()) {
                                 damage = 0;
                                 buffAttack = -1;
                              }

                              damage *= CharManager.UP_DAMGE_SKILL[p.charClass][_type][_level - 1];
                              boolean critSv = p.havecrit();
                              if (critSv) {
                                 damage *= 2;
                                 effect = 2;
                                 if (p.petUsing != null) {
                                    long pcLienKich = (long)p.petUsing.getLienKich();
                                    damage = (int)((long)damage + (long)damage * pcLienKich / 100L);
                                 }
                              }

                              if (_level > p.skill[_type] + p.addMoreLevelSkill[_type]) {
                                 System.out.println("KO CHO DANH 10");
                              } else {
                                 int mplost = CharManager.SKILL_MP[p.charClass][_type][_level];
                                 if (p.mp + p.percentBuff[1] < mplost) {
                                    System.out.println("KO CHO DANH 11");
                                 } else {
                                    p.mp -= mplost;
                                    if (p.mp <= 0) {
                                       p.mp = 0;
                                    }

                                    int getXp = mt.getXpReceive(damage);
                                    ahp = damage / CharManager.UP_DAMGE_SKILL[p.charClass][_type][_level - 1];
                                    mt.hp -= damage;
                                    if (damage > 0 && mt.haveBackDam()) {
                                       int backdam = mt.getBackDam(damage);
                                       Message mbd = MessageCreator.createMsgBuffEffect(5, mt.cat, p, backdam, 0, -1);
                                       p.sendMessage(mbd);
                                       p.sendToNearPlayer(mbd);
                                    }

                                    Message m = null;
                                    if (p.charthanthu != null && mt.hp > 0) {
                                       Vector<LiveActor> target = new Vector();
                                       target.add(mt);
                                       p.charthanthu.doAttack(target);
                                       int damthanthu = p.getDamtThanThu(mt);
                                       getXp += mt.getXpReceive(damthanthu);
                                       mt.hp -= damthanthu;
                                    }

                                    if (mt.hp > 0) {
                                       int damNguyetAnh = p.getPCDamNguyetAnh(skill);
                                       if (mt.hp > 0 && damNguyetAnh > 0) {
                                          mt.hp = mt.hp - mt.maxhp * damNguyetAnh / 100;
                                          damage += mt.maxhp * damNguyetAnh / 100;
                                          p.sendEffectBuff(mt, EffectBuff.EFF_NGUYET_ANH, 1000);
                                       }
                                    }

                                    if (mt.hp > 0) {
                                       if (mt.target == null) {
                                          mt.target = p;
                                       }

                                       if (ahp > 0) {
                                          p.buffAttackSkill(damage, mt);
                                       }

                                       if (getXp > 0) {
                                          int x2Player = p.getX2();
                                          if (TeamServer.isDouble) {
                                             int var51 = 0;
                                          }

                                          int dxp = rand10(getXp);
                                          if (dxp == 0) {
                                             dxp = 1;
                                          }

                                          int[] downPercent = new int[]{1, 5, 10, dxp};
                                          int targetLv = p.getLevel();
                                          if (targetLv < 40) {
                                             downPercent = new int[]{1, 2, 3, dxp};
                                          }

                                          int delta = targetLv - mt.level;
                                          if (delta > 0) {
                                             int a = delta / 4;
                                             if (targetLv < 40) {
                                                a = delta / 6;
                                             }

                                             if (a > 3) {
                                                a = 3;
                                             }

                                             dxp /= downPercent[a];
                                             if (dxp <= 0) {
                                                dxp = 1;
                                             }
                                          }

                                          if (dxp > 0) {
                                             int newxp = calculatorXpParty(p, dxp);
                                             if (newxp == dxp) {
                                                int var65 = dxp * doubleALL;
                                                x2Player = p.getX2();
                                                if (doubleALL > 1) {
                                                   x2Player = 0;
                                                }

                                                if (x2Player == 1) {
                                                   var65 += var65 / 2;
                                                } else if (x2Player == 2) {
                                                   var65 *= x2Player;
                                                } else if (x2Player == 3) {
                                                   int exp50 = var65 / 2;
                                                   var65 = var65 * 2 + exp50;
                                                }

                                                addXPForChar(p, (long)(var65 + p.getEffSkillClan(0) * var65 / 100), false, "maptown doAttackMonster3");
                                             } else {
                                                int nUser = p.party.userParty.size();
                                                if (nUser > 1) {
                                                   nUser = 5;
                                                }

                                                int xpReceive = newxp * 80 / (100 * nUser);
                                                int maxLv = p.lvDetail.lv;

                                                for (int i = 0; i < p.party.userParty.size(); i++) {
                                                   Char pp = (Char)p.party.userParty.get(i);
                                                   if (pp.id != p.id && p.near(pp, 320) && pp.mapID == p.mapID && pp.inCountry == p.inCountry) {
                                                      int dlv = abs(maxLv - pp.lvDetail.lv);
                                                      int temp = 1;
                                                      if (dlv <= 5) {
                                                         temp = xpReceive;
                                                      } else if (dlv <= 10) {
                                                         temp = xpReceive / 5;
                                                      } else if (dlv <= 20) {
                                                         temp = xpReceive / 10;
                                                      } else if (dlv <= 30) {
                                                         temp = xpReceive / 15;
                                                      } else {
                                                         temp = xpReceive / 20;
                                                      }

                                                      if (temp == 0) {
                                                         temp = 1;
                                                      }

                                                      if (pp.hp > 0) {
                                                         x2Player = pp.getX2();
                                                         temp *= doubleALL;
                                                         if (doubleALL > 1) {
                                                            x2Player = 0;
                                                         }

                                                         if (x2Player == 1) {
                                                            temp += temp / 2;
                                                         } else if (x2Player == 2) {
                                                            temp *= x2Player;
                                                         } else if (x2Player == 3) {
                                                            int exp50 = temp / 2;
                                                            temp = temp * 2 + exp50;
                                                         }

                                                         addXPForChar(pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), false, "maptown doAttackMonster1");
                                                      }
                                                   }
                                                }

                                                x2Player = p.getX2();
                                                xpReceive = newxp * 20 / 100 * doubleALL;
                                                if (doubleALL > 1) {
                                                   x2Player = 0;
                                                }

                                                if (x2Player == 1) {
                                                   xpReceive += xpReceive / 2;
                                                } else if (x2Player == 2) {
                                                   xpReceive *= x2Player;
                                                } else if (x2Player == 3) {
                                                   int exp50 = xpReceive / 2;
                                                   xpReceive = xpReceive * 2 + exp50;
                                                }

                                                addXPForChar(p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), false, "maptown doAttackMonster2");
                                             }
                                          }
                                       }

                                       m = new Message(9);
                                       m.dos.writeShort(p.id);
                                       m.dos.writeShort(mt.id);
                                       m.dos.writeByte(skill);
                                       m.dos.writeInt(ahp);
                                       m.dos.writeInt(mt.hp);
                                       m.dos.writeByte(effect);
                                       m.dos.writeByte(CharManager.UP_DAMGE_SKILL[p.charClass][_type][_level - 1 >= 0 ? _level - 1 : 0]);
                                       m.dos.writeByte(buffAttack);
                                       m.dos.writeByte(_level);
                                       p.sendMessage(m);
                                       p.sendToNearPlayer(m);
                                       p.buffSkillKham(mt);
                                       p.charHireAttackDam(mt, _type, _level, buffAttack);
                                    } else {
                                       Vector<Actor> droplist = new Vector();
                                       mt.hp = 0;
                                       int x2Playerx = p.getX2();
                                       if (doubleALL > 1) {
                                          int var58 = 0;
                                       }

                                       try {
                                          int dxpx = rand10(mt.xp);
                                          if (dxpx == 0) {
                                             dxpx = 1;
                                          }

                                          int[] downPercentx = new int[]{1, 5, 10, dxpx};
                                          int targetLvx = p.getLevel();
                                          if (targetLvx < 40) {
                                             downPercentx = new int[]{1, 2, 3, dxpx};
                                          }

                                          int deltax = targetLvx - mt.level;
                                          if (deltax > 0) {
                                             int ax = deltax / 4;
                                             if (targetLvx < 40) {
                                                ax = deltax / 6;
                                             }

                                             if (ax > 3) {
                                                ax = 3;
                                             }

                                             dxpx /= downPercentx[ax];
                                             if (dxpx <= 0) {
                                                dxpx = 1;
                                             }
                                          }

                                          if (dxpx > 0) {
                                             int newxp = calculatorXpParty(p, dxpx);
                                             if (newxp == dxpx) {
                                                int var68 = dxpx * doubleALL;
                                                var68 = p.expReceive(var68);
                                                addXPForChar(p, (long)(var68 + p.getEffSkillClan(0) * var68 / 100), false, "maptown doAttackMonster6");
                                             } else {
                                                int nUserx = p.party.userParty.size();
                                                if (nUserx > 1) {
                                                   nUserx = 5;
                                                }

                                                int xpReceive = newxp * 80 / (nUserx * 100);
                                                int maxLv = p.lvDetail.lv;

                                                for (int ix = 0; ix < p.party.userParty.size(); ix++) {
                                                   Char pp = (Char)p.party.userParty.get(ix);
                                                   if (pp.id != p.id && p.near(pp, 320) && pp.mapID == p.mapID && pp.inCountry == p.inCountry) {
                                                      int dlvx = abs(maxLv - pp.lvDetail.lv);
                                                      int tempx = 1;
                                                      if (dlvx <= 5) {
                                                         tempx = xpReceive;
                                                      } else if (dlvx <= 10) {
                                                         tempx = xpReceive / 5;
                                                      } else if (dlvx <= 20) {
                                                         tempx = xpReceive / 10;
                                                      } else if (dlvx <= 30) {
                                                         tempx = xpReceive / 15;
                                                      } else {
                                                         tempx = xpReceive / 20;
                                                      }

                                                      if (tempx == 0) {
                                                         tempx = 1;
                                                      }

                                                      if (pp.hp > 0) {
                                                         tempx *= doubleALL;
                                                         tempx = pp.expReceive(tempx);
                                                         addXPForChar(
                                                            pp, (long)(tempx + pp.getEffSkillClan(0) * tempx / 100), false, "maptown doAttackMonster4"
                                                         );
                                                      }
                                                   }
                                                }

                                                xpReceive = newxp * 20 / 100 * doubleALL;
                                                xpReceive = p.expReceive(xpReceive);
                                                addXPForChar(p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), false, "maptown doAttackMonster5");
                                             }
                                          }
                                       } catch (Exception var38) {
                                       }

                                       try {
                                          m = new Message(17);
                                          m.dos.writeShort(p.id);
                                          m.dos.writeShort(mt.id);
                                          m.dos.writeByte(skill);
                                          m.dos.writeInt(ahp);
                                          m.dos.writeByte(effect);
                                          m.dos.writeByte(droplist.size());
                                          if (droplist.size() > 0) {
                                             for (Actor e : droplist) {
                                                writeActorPos(m, e, p.getSession().isOldVersion);
                                             }
                                          }

                                          int xx2 = CharManager.UP_DAMGE_SKILL[p.charClass][_type][_level - 1 >= 0 ? _level - 1 : 0];
                                          m.dos.writeByte(xx2);
                                          m.dos.writeByte(buffAttack);
                                          m.dos.writeByte(_level);
                                          p.sendMessage(m);
                                          p.sendToNearPlayer(m);
                                          if (p.receiveQuest && QuestTemplate.QUEST_TYPE[p.questID - 1] == 0) {
                                             p.checkFinsishQuest(mt.getType(), -1, -1);
                                          }
                                       } catch (Exception var37) {
                                          System.out.println("loi gui thong tin monsterdie ");
                                       }
                                    }

                                    if (mt.hp <= 0) {
                                       mt.isDead = true;
                                       mt.target = null;
                                       if (!mt.isBossTruRong()) {
                                          this.removeMonster(mt, mt.inCountry);
                                          mt.bornTime = System.currentTimeMillis() + 3600000L;
                                          ((Vector)this.tempRemoveMonster.get(mt.inCountry)).add(mt);
                                          ((Hashtable)this.monsters.get(mt.inCountry)).remove(mt.id);
                                       }

                                       if (mt.getMonsterTemplate().id == 46) {
                                          synchronized ((Hashtable)this.monsters.get(mt.inCountry)) {
                                             p.doAddGemItem(11, 3, false);
                                             p.sendMessage(MessageCreator.createCharGemItem(p));
                                          }
                                       } else {
                                          mt.charKillBoss(p);
                                       }
                                    }

                                    m.cleanup();
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void removeMonster(Monster m, int country) {
      try {
         synchronized (smallDragon) {
            boolean rmok = ((Vector)smallDragon.get(country)).remove(m);
            if (((Vector)smallDragon.get(country)).size() == 0 && rmok) {
               this.doAddBossThuThanh(country);
            }
         }
      } catch (Exception var11) {
         var11.printStackTrace();
      }

      try {
         synchronized (mboss) {
            boolean var15 = ((Vector)mboss.get(country)).remove(m);
         }
      } catch (Exception var9) {
         var9.printStackTrace();
      }

      try {
         synchronized (mainDragon) {
            boolean mok = ((Vector)mainDragon.get(country)).remove(m);
            if (mok) {
               Map.sendAllCharServer(
                  -1,
                  MessageCreator.createServerAlertAutoOffMessage(
                     "Chúc mừng " + nameCountry[nationBeAttack[country]] + " tấn công " + nameCountry[country] + " thành công."
                  )
               );
               String win = nameCountry[nationBeAttack[country]];
               nwar[country] = false;
               ((RegisterAttack)nationWar.get(nationBeAttack[country])).idMyAttack = -1;
               ((RegisterAttack)nationWar.get(nationBeAttack[country])).dayAttack = "";
               ((RegisterAttack)nationWar.get(nationBeAttack[country])).win++;
               nationBeAttack[country] = -1;
               ((RegisterAttack)nationWar.get(country)).idNationAttackMe = -1;
               ((RegisterAttack)nationWar.get(country)).lose++;
               Database.instance.saveEvent(event.getInfo());
               Database.instance.saveOrtherLog("", win, " chien thang " + nameCountry[country], "attackwin");
            }
         }
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      Vector<Vector<Monster>> vm = (Vector<Vector<Monster>>)towerDefend.get(country);
      Vector<Monster> mpos = (Vector<Monster>)vm.get(m.posTower);
      mpos.remove(m);
   }

   public boolean gameOver(int country) {
      Calendar cl = Calendar.getInstance();
      int iHour = cl.get(11);
      boolean is0114 = Char.getDayOpen(0L).equals("2017-01-14");
      if ((iHour <= 21 || TimeAdminCheat != -1) && gameOverByAdmin != country && (!is0114 || iHour <= 15) && (TimeAdminCheat <= -1 || iHour <= TimeAdminCheat)) {
         return false;
      } else {
         this.curday[country] = "";
         taxOfClan[country] = 0;
         resetClanReg();
         return true;
      }
   }

   public boolean canAttackMainDragon(Monster m) {
      if (((Vector)mainDragon.get(m.inCountry)).size() == 0) {
         return false;
      } else {
         return ((Vector)smallDragon.get(m.inCountry)).size() > 0 ? false : ((Vector)mboss.get(m.inCountry)).size() <= 0;
      }
   }

   public boolean canAttackSmallDragon(Monster m) {
      return ((Vector)((Vector)towerDefend.get(m.inCountry)).get(m.posTower)).size() <= 0;
   }

   public void doAddLienHoaTru(int pos, int idClan, int myCountry) {
      Calendar cl = Calendar.getInstance();
      int ihour = cl.get(11);
      int minute = cl.get(12);
      NewClan clan = NewClan.getClan((short)idClan);
      int cos = 0;
      if (clan.money >= (long)cos) {
         Vector<Monster> mons = (Vector<Monster>)((Vector)towerDefend.get(myCountry)).get(pos);
         if (mons.size() < 4) {
            clan.addMoney2Clan((long)(-cos));
            clan.updateNewClandata2DB();
            Monster m = new LienHoaTru(
               this, (MonsterTemplate)monsterTemplates.get(43), posTower[pos][mons.size() * 2] * 16, posTower[pos][mons.size() * 2 + 1] * 16, myCountry
            );
            m.level = m.getMonsterTemplate().level;
            m.id = this.idMonster--;
            byte[] he = new byte[]{0, 1, 2, 3, 4};
            m.he = he[r.nextInt(5)];
            byte[] t = new byte[]{0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0};
            m.typeAttack = t[r.nextInt(10)];
            m.bornTime = 120000L;
            m.posTower = (byte)pos;
            if (((Vector)smallDragon.get(myCountry)).get(pos) != null) {
               ((Vector)this.tempMonster.get(myCountry)).add(m);
               mons.add(m);
               this.allLienHoaTru.add(m);
            }
         }
      }
   }

   public void doAddLienHoaTru(Char player, int pos) {
      Calendar cl = Calendar.getInstance();
      int ihour = cl.get(11);
      int minute = cl.get(12);
      if (ihour == 19 && (ihour != 19 || minute >= 50)) {
         if (player.inCountry == player.myCountry) {
            if (player.rankGov != -1) {
               int x = player.x / 16;
               int y = player.y / 16;
               if (abs(x - posDragonTower[pos][0]) <= 4 && abs(y - posDragonTower[pos][1]) <= 4) {
                  NewClan clan = NewClan.getClan(player.idClan);
                  int cos = 5000000;
                  if (clan.money < (long)cos) {
                     player.sendMessage(MessageCreator.createServerAlertMessage("Bang phải có ít nhất " + cos + " xu để có thể xây trụ.", ""));
                  } else {
                     Vector<Monster> mons = (Vector<Monster>)((Vector)towerDefend.get(player.myCountry)).get(pos);
                     if (mons.size() >= 4) {
                        player.sendMessage(MessageCreator.createServerAlertMessage("Không thể xây thêm trụ tại vị trí này", ""));
                     } else {
                        clan.addMoney2Clan((long)(-cos));
                        clan.updateNewClandata2DB();
                        Monster m = new LienHoaTru(
                           this,
                           (MonsterTemplate)monsterTemplates.get(43),
                           posTower[pos][mons.size() * 2] * 16,
                           posTower[pos][mons.size() * 2 + 1] * 16,
                           player.inCountry
                        );
                        m.level = m.getMonsterTemplate().level;
                        m.id = this.idMonster--;
                        byte[] he = new byte[]{0, 1, 2, 3, 4};
                        m.he = he[r.nextInt(5)];
                        byte[] t = new byte[]{0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0};
                        m.typeAttack = t[r.nextInt(10)];
                        m.bornTime = 120000L;
                        m.posTower = (byte)pos;
                        if (((Vector)smallDragon.get(player.myCountry)).get(pos) != null) {
                           ((Vector)this.tempMonster.get(player.myCountry)).add(m);
                           mons.add(m);
                           this.allLienHoaTru1.add(m);
                        }

                        Database.instance
                           .saveOrtherLog(
                              "",
                              player.charname,
                              clan.name + "_" + clan.money + "_" + nameCountry[player.myCountry] + " xây trụ tại vi tri " + pos,
                              "setLienhoa"
                           );
                     }
                  }
               } else {
                  player.sendMessage(MessageCreator.createServerAlertMessage("Không thể xây vì đứng quá xa trụ phụ", ""));
               }
            }
         }
      } else {
         player.sendMessage(MessageCreator.createServerAlertMessage("Không thể xây trụ trong thời gian này", ""));
      }
   }

   public boolean checkTimeStartWar(String day) {
      if (TeamServer.isServerIndo()) {
         return false;
      } else {
         String dayString = Char.getDayOpen(0L);
         if (dayString.equals(day)) {
            Calendar cl = Calendar.getInstance();
            int ihour = cl.get(11);
            int minute = cl.get(12);
            if (ihour == 19 && minute >= 50) {
               for (int i = 0; i < 3; i++) {
                  if (nationBeAttack[i] != -1 && !addDragon[i] && !nwar[i]) {
                     addDragon[i] = true;
                     this.doAddDragonTower(i);

                     for (int k = 0; k < 4; k++) {
                        for (int j = 0; j < 4; j++) {
                           this.doAddLienHoaTru(k, Map.idClanTown[i], i);
                        }
                     }

                     this.kickAllCharDifCountry(i);
                  }
               }
            }

            if (ihour == 20 && minute < 5 && !isStartWar) {
               String info = "";

               for (int ix = 0; ix < 3; ix++) {
                  if (!nwar[ix] && nationBeAttack[ix] != -1) {
                     nwar[ix] = true;
                     addDragon[ix] = false;
                     info = info + nameCountry[ix] + ",";
                  }

                  ((Vector)topPK.get(ix)).removeAllElements();
               }

               Database.instance.updateLienTram();
               isStartWar = true;
               if (!info.equals("")) {
                  info = "Bắt đầu diễn ra sự kiện chiếm lãnh thổ tại " + info.substring(0, info.length() - 1);

                  try {
                     sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(info));
                  } catch (IOException var9) {
                  }
               }
            }
         }

         return false;
      }
   }

   public void removeAllMonster(int country) {
      this.allsmallDragon.removeAllElements();
      this.allmainDragon.removeAllElements();
      this.allLienHoaTru.removeAllElements();
      this.allLienHoaTru1.removeAllElements();
      ((Vector)this.tempMonster.get(country)).removeAllElements();

      for (int i = 0; i < ((Vector)smallDragon.get(country)).size(); i++) {
         Message m = new Message(90);

         try {
            m.dos.writeShort(((DragonTower)((Vector)smallDragon.get(country)).get(i)).id);
            m.dos.writeByte(((DragonTower)((Vector)smallDragon.get(country)).get(i)).cat);
            this.sendAllPlayer(m, country);
         } catch (Exception var10) {
         }
      }

      ((Vector)smallDragon.get(country)).removeAllElements();

      for (int i = 0; i < ((Vector)mainDragon.get(country)).size(); i++) {
         Message m = new Message(90);

         try {
            m.dos.writeShort(((DragonTower)((Vector)mainDragon.get(country)).get(i)).id);
            m.dos.writeByte(((DragonTower)((Vector)mainDragon.get(country)).get(i)).cat);
            this.sendAllPlayer(m, country);
         } catch (Exception var9) {
         }
      }

      ((Vector)mainDragon.get(country)).removeAllElements();

      for (int i = 0; i < ((Vector)((Vector)towerDefend.get(country)).get(0)).size(); i++) {
         Message m = new Message(90);

         try {
            m.dos.writeShort(((Monster)((Vector)((Vector)towerDefend.get(country)).get(0)).get(i)).id);
            m.dos.writeByte(((Monster)((Vector)((Vector)towerDefend.get(country)).get(0)).get(i)).cat);
            this.sendAllPlayer(m, country);
         } catch (Exception var8) {
         }
      }

      ((Vector)((Vector)towerDefend.get(country)).get(0)).removeAllElements();

      for (int i = 0; i < ((Vector)((Vector)towerDefend.get(country)).get(1)).size(); i++) {
         Message m = new Message(90);

         try {
            m.dos.writeShort(((Monster)((Vector)((Vector)towerDefend.get(country)).get(1)).get(i)).id);
            m.dos.writeByte(((Monster)((Vector)((Vector)towerDefend.get(country)).get(1)).get(i)).cat);
            this.sendAllPlayer(m, country);
         } catch (Exception var7) {
         }
      }

      ((Vector)((Vector)towerDefend.get(country)).get(1)).removeAllElements();

      for (int i = 0; i < ((Vector)((Vector)towerDefend.get(country)).get(2)).size(); i++) {
         Message m = new Message(90);

         try {
            m.dos.writeShort(((Monster)((Vector)((Vector)towerDefend.get(country)).get(2)).get(i)).id);
            m.dos.writeByte(((Monster)((Vector)((Vector)towerDefend.get(country)).get(2)).get(i)).cat);
            this.sendAllPlayer(m, country);
         } catch (Exception var6) {
         }
      }

      ((Vector)((Vector)towerDefend.get(country)).get(2)).removeAllElements();

      for (int i = 0; i < ((Vector)((Vector)towerDefend.get(country)).get(3)).size(); i++) {
         Message m = new Message(90);

         try {
            m.dos.writeShort(((Monster)((Vector)((Vector)towerDefend.get(country)).get(3)).get(i)).id);
            m.dos.writeByte(((Monster)((Vector)((Vector)towerDefend.get(country)).get(3)).get(i)).cat);
            this.sendAllPlayer(m, country);
         } catch (Exception var5) {
         }
      }

      ((Vector)((Vector)towerDefend.get(country)).get(3)).removeAllElements();
      ((Hashtable)this.monsters.get(country)).clear();
      ((Vector)this.tempRemoveMonster.get(country)).removeAllElements();
   }

   public boolean timeOutNationWar(int country) {
      if (Calendar.getInstance().get(11) > 20 && nwar[country]) {
         try {
            String info = nameCountry[nationBeAttack[country]] + " tấn công " + nameCountry[country] + " thất bại";
            nwar[country] = false;
            RegisterAttack reg = (RegisterAttack)nationWar.get(country);
            reg.win++;
            reg.dayAttack = "";
            reg.idNationAttackMe = -1;
            reg.idMyAttack = -1;
            RegisterAttack reg2 = (RegisterAttack)nationWar.get(nationBeAttack[country]);
            reg2.lose++;
            reg2.dayAttack = "";
            reg2.idNationAttackMe = -1;
            reg2.idMyAttack = -1;
            Database.instance.saveOrtherLog("", nameCountry[country], info + "_" + reg.win + "_" + reg.lose + " | " + reg2.win + "_" + reg2.lose, "win");
            sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(info));
            this.removeAllMonster(country);
         } catch (Exception var5) {
         }

         nationBeAttack[country] = -1;
         Database.instance.saveEvent(event.getInfo());
         return true;
      } else {
         return false;
      }
   }

   public void doAddBossTruRong(int wave, int country, int pos) {
      BossTruRong m = new BossTruRong(this, (MonsterTemplate)monsterTemplates.get(120), POS_TRU_RONG[pos][0] * 16, POS_TRU_RONG[pos][1] * 16, country);
      m.level = m.getMonsterTemplate().level;
      m.id = this.idMonster--;
      byte[] he = new byte[]{0, 1, 2, 3, 4};
      m.he = he[r.nextInt(5)];
      byte[] t = new byte[]{0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0};
      m.typeAttack = t[r.nextInt(10)];
      m.bornTime = 120000L;
      m.posTower = (byte)pos;
      this.addMonsterDynamic(m, country, 0);
      allBossTruRong.add(m);
   }

   public boolean checkTimeGetTown(int j) {
      if (TeamServer.isServerIndo()) {
         return false;
      } else if (j == 2) {
         return false;
      } else {
         String day = "Mon";
         if (!this.curday[j].equals("Mon")) {
            String nt = new Date(System.currentTimeMillis()).toString();
            Calendar cl = Calendar.getInstance();
            int iHour = cl.get(11);
            int iMinute = cl.get(12);
            boolean is0114 = Char.getDayOpen(0L).equals("2017-01-14");
            
            // Kiểm tra nếu là admin set time thì không check ngày
            if (TimeAdminCheat > -1) {
               System.out.println("DEBUG: iHour=" + iHour + ", iMinute=" + iMinute + ", TimeAdminCheat=" + TimeAdminCheat + ", minuteAdminCheat=" + minuteAdminCheat);
               if (iHour == TimeAdminCheat && iMinute == minuteAdminCheat) {
                  // Xử lý logic chiếm thành
                  return doGetTown(j, day, cl);
               }
            } else {
               // Trường hợp bình thường - check cả ngày và giờ
               boolean isDay = nt.startsWith("Mon") || nt.startsWith("Fri") || is0114;
//               boolean isDay = true; // set đánh cả tuần
               if (isDay) {
                  if (iHour == 21 && iMinute < 2 || is0114 && iHour == 15 && iMinute < 2) {
                     // Xử lý logic chiếm thành  
                     return doGetTown(j, day, cl);
                  }
               }
            }
         }
         return false;
      }
   }

   // Tách logic xử lý chiếm thành ra method riêng
   private boolean doGetTown(int j, String day, Calendar cl) {
     
      System.out.println("BAT DAU CHIEM THANH " + j);
      this.curday[j] = day;
      getTown[j] = true;
      if (idClanTown[j] > -1) {
         try {
            NewClan.updateTown(idClanTown[j], 0, j);
         } catch (Exception var15) {
         }
      }

      isChiemThanh = true;
      ((Vector)npcReceiveCard.get(j)).removeAllElements();
      ((Vector)npcReceiveCard.get(j)).add(new NpcReceiveCard(0, j));
      ((Vector)npcReceiveCard.get(j)).add(new NpcReceiveCard(1, j));
      ((Vector)npcReceiveCard.get(j)).add(new NpcReceiveCard(2, j));
      idClanTown[j] = -1;
      taxOfClan[j] = 0;
      resetAllShield();
      resetAllGov();
      Database.instance.saveEvent(event.getInfo());
      ((Monster)congthanh.get(j)).initInfo();
      ((Hashtable)this.monsters.get(j)).put(((Monster)congthanh.get(j)).id, (Monster)congthanh.get(j));
      ((Map)RealController.mapList.get(201)).doAddBossTruRong(1, j, 0);
      ((Map)RealController.mapList.get(201)).doAddBossTruRong(1, j, 1);
      ((Map)RealController.mapList.get(201)).doAddBossTruRong(1, j, 2);
      int[] mapstart = new int[]{9, 481, 482, 483, 484};
      int homeX = 31 + Database.r.nextInt() % 5;
      int homeY = 79 + Database.r.nextInt(20);

      for (int i = 0; i < CharManager.instance.vChars.size(); i++) {
         try {
            Char p = (Char)CharManager.instance.vChars.elementAt(i);
            if (p.myCountry > -1) {
               p.sendMessage(MessageCreator.createMsgStartGetTown(p.myCountry));
               p.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Thời gian chiếm thành bắt đầu"));
               if (p.isBot == -1 && p.mapID >= 201 && p.mapID <= 271 && p.mapID != 202) {
                  p.map.move2Map(p, homeX, homeY, mapstart[r.nextInt(MessageCreator.nclone)], p.inCountry);
               }
            }
         } catch (Exception var14) {
         }
      }

      return true;
   }

   public void doRequestSellItemMarket(Char player, Message message) {
   }

   protected void doPopUpMapTown(Char player, int idpopup, int idActor, boolean ok) {
      if (!openMarket) {
         player.sendMessage(MessageCreator.createServerAlertMessage("Chức năng đang bảo trì", ""));
      } else {
         if (idActor == player.id && idpopup == 12) {
            if (ok) {
               if (player.itemBuy == null) {
                  return;
               }

               Market.buyItem(player.itemBuy, player);

               try {
                  Vector<ItemSell> it = player.new_search == 0 ? player.allNewItem : (player.new_search == 1 ? player.allSearchItem : player.allItemBid);
                  it.remove(player.itemBuy);
               } catch (Exception var6) {
               }

               createListItemMarket(player, player.getAllistItemSell(), 2);
               createListItemMarket(player, player.getAllListItemBid(), 4);
               createListItemMarket(player, player.getAllistItemExpire(), 3);
               createListItemMarket(player, player.getAllistNewItemSell(), 0);
               createListItemMarket(player, player.getAllItemSearch(), 1);
            }

            player.itemBuy = null;
         }
      }
   }

   public void doMenuTown(Char player, int idNpc, int idMenu, int idOptionMenu) {
      if (openMarket) {
         if (idOptionMenu == 0) {
            createListItemMarket(player, null, -1);
            createListItemMarket(player, player.getAllistItemSell(), 2);
            createListItemMarket(player, player.getAllListItemBid(), 4);
            createListItemMarket(player, player.getAllistItemExpire(), 3);
            createListItemMarket(player, player.getAllistNewItemSell(), 0);
         }
      }
   }

   protected void doPlayerMove(Char p, Message message) throws IOException {
      if (this.givingCard(p)) {
         Message m = new Message(4);
         p.writeActorPos(m, p);
         p.sendMessage(m);
      } else {
         p.doPlayerMove(message);
      }
   }

   public void doChat(Char player, Message message) throws IOException {
      if (player.isAdmin || !getTown[player.inCountry] && !nwar[player.inCountry]) {
         super.doChat(player, message);
      }
   }

   public boolean isMapTrain() {
      return false;
   }

   
}