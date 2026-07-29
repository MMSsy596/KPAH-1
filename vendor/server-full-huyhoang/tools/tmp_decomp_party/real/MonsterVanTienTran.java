/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 *  real.Char
 *  real.GemTemplate
 *  real.LiveActor
 *  real.Map
 *  real.Monster
 *  real.Potion
 */
package real;

import data.GemItem;
import io.Message;
import java.util.Vector;
import real.Actor;
import real.Char;
import real.GemTemplate;
import real.LiveActor;
import real.Map;
import real.Monster;
import real.MonsterTemplate;
import real.Potion;

public class MonsterVanTienTran
extends Monster {
    static int maxhpTemp = 100000;
    static int defTemp = 5000;
    int wave = 1;

    public MonsterVanTienTran(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
        this.maxhp = maxhpTemp;
        this.defend_magic = this.defend_physic = defTemp;
        this.typeAttack = 1;
    }

    public void upDefAtkByWave(long wave) {
        this.attack = (int)((long)this.attack + (long)this.getMonsterTemplate().attack * (wave * 10L) / 100L);
        this.defend_magic = this.defend_physic = defTemp;
        this.defend_magic = (int)((long)this.defend_magic + (long)defTemp * (wave * 10L) / 100L);
        this.defend_physic = (int)((long)this.defend_physic + (long)defTemp * (wave * 10L) / 100L);
        this.hp = this.maxhp = (int)((long)maxhpTemp + (long)maxhpTemp * (wave * 10L) / 100L) + this.level * 3000;
    }

    public MonsterVanTienTran(byte cat) {
        super(cat);
    }

    public boolean isActive() {
        return true;
    }

    public int attackDam(LiveActor actor) {
        try {
            int def = actor.defend_physic + actor.percentBuff[0];
            if (actor.cat == 0) {
                if (this.magic_physic == 0) {
                    def = actor.defend_magic + actor.getBuffDefCB(1, true);
                    def += def * 5 / 100;
                    def += def * ((Char)actor).getEffSkillClanMember(2) / 100;
                } else {
                    def += def * ((Char)actor).getEffSkillClanMember(1) / 100;
                }
            }
            int dam = this.attack;
            long pcmin = 2 + this.wave;
            long pcmax = 5 + this.wave;
            if ((long)(dam -= def) < (long)actor.hp * pcmin / 100L) {
                dam = (int)((long)actor.hp * pcmin / 100L + (long)Map.r.nextInt(100));
            } else if ((long)dam > (long)actor.hp * pcmax / 100L) {
                dam = (int)((long)actor.hp * pcmax / 100L + (long)Map.r.nextInt(100));
            }
            if (def > dam) {
                def = dam / 2;
            }
            return dam -= def * (5 - this.wave / 2) / 100;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    public void attack() {
        if (this.freeze()) {
            return;
        }
        if (!(this.target == null || this.target.map.equals(this.map) && this.target.region == this.region)) {
            this.target = null;
            return;
        }
        if (this.idTemplate >= 85 && this.idTemplate <= 89 || this.idTemplate == 36 || this.idTemplate == 37) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now - this.lastTimeAttack > this.attackDelay) {
            this.lastTimeAttack = now;
            try {
                boolean ismiss;
                Message m = new Message(10);
                m.dos.writeShort(this.id);
                m.dos.writeShort(this.target.id);
                int ahp = this.attackDam((LiveActor)this.target);
                ahp = this.target.subDam((Monster)this, ahp);
                ahp = this.target.checkHapthuSatThuong(ahp, (LiveActor)this);
                ahp = this.target.checkGiamSatThuong(ahp);
                ahp = this.target.checkPassAttack((LiveActor)this, ahp);
                ahp = (int)((long)ahp - this.target.checkMagicShield(ahp));
                if (ahp <= 0) {
                    ahp = 10;
                }
                if (ismiss = this.attackMiss((LiveActor)this.target)) {
                    ahp = 0;
                } else {
                    this.target.buffAttackSkill((Monster)this, ahp);
                    if (this.hp <= 0) {
                        this.actorDie();
                    }
                    this.target.downDuarable();
                }
                int realdam = 0;
                if (ahp >= 32000) {
                    int defvat = this.target.defend_physic + this.target.percentBuff[0];
                    defvat += defvat * 5 / 100;
                    defvat += defvat * this.target.getEffSkillClanMember(1) / 100;
                    int defMa = this.target.defend_magic + this.target.getBuffDefCB(1, true);
                    defMa += defMa * 5 / 100;
                    defMa += defMa * this.target.getEffSkillClanMember(2) / 100;
                    int def = 0;
                    def = defMa < defvat ? defvat : (defMa > defvat ? defMa : defvat);
                    int dam = this.attack;
                    int deltaLV = this.level - this.target.lvDetail.lv;
                    if (deltaLV > 0) {
                        dam += dam * Map.abs((int)(deltaLV / 5));
                        if (Map.abs((int)deltaLV) > 5 && Map.abs((int)deltaLV) <= 10) {
                            def /= 2;
                        } else if (Map.abs((int)deltaLV) > 10 && Map.abs((int)deltaLV) <= 15) {
                            def /= 3;
                        } else if (Map.abs((int)deltaLV) > 15) {
                            def /= 10;
                        }
                        dam += 70 * deltaLV;
                        def -= 20 * deltaLV;
                    } else if (deltaLV < 0) {
                        dam += 70 * deltaLV;
                    }
                    if (dam <= 0) {
                        dam = 5;
                    }
                    realdam = dam - def;
                    if ((realdam = realdam * 120 / 100) <= 0) {
                        realdam = 5;
                    }
                    if (ahp > realdam) {
                        ahp = 10;
                    }
                }
                if (this.target.isAdmin) {
                    ahp = 0;
                }
                this.target.checkNewEffectItem(1, (long)(ahp / 10), (LiveActor)this);
                m.dos.writeInt(ahp);
                this.target.hp -= Map.abs((int)ahp);
                if (this.target.hp < 0) {
                    this.target.hp = 0;
                }
                m.dos.writeInt(this.target.hp);
                int i = 0;
                while (i < this.target.nearChars.size()) {
                    Char p2 = this.target.map.getPlayerByID(((Short)this.target.nearChars.get(i)).shortValue());
                    if (p2 != null) {
                        p2.sendMessage(m);
                    }
                    ++i;
                }
                if (this.target.hp <= 0) {
                    this.target.desTroy();
                    this.target.actorDie();
                    try {
                        this.target.actorDie();
                        this.target.calculateAttrib();
                        this.target.doSendProperties();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                this.target.sendMessage(m);
                m.cleanup();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public Vector<Actor> onDropItem(Map m, Char p) {
        Vector<Actor> droplist;
        block16: {
            Potion pt;
            block19: {
                block18: {
                    block17: {
                        block15: {
                            int quantity;
                            droplist = new Vector<Actor>();
                            if (Map.r.nextInt(100) < 20) {
                                return droplist;
                            }
                            pt = null;
                            int totalQuantity = quantity = 100000 * (this.wave + 1);
                            pt = new Potion(0, totalQuantity, m);
                            pt.id = m.getIDITEM();
                            pt.x = this.x;
                            pt.y = this.y;
                            m.addPotion(pt, (int)this.inCountry);
                            pt.time_drop = System.currentTimeMillis();
                            droplist.add((Actor)pt);
                            if (this.wave != 0) break block15;
                            int i = 0;
                            while (i < 1) {
                                short gemid = GemTemplate.ID_MATERIAL_LOW[Map.r.nextInt(2)][Map.r.nextInt(GemTemplate.ID_MATERIAL_LOW[1].length)];
                                GemItem gem = new GemItem(gemid);
                                if (gem != null) {
                                    gem.cat = (byte)6;
                                    gem.x = this.x + 5;
                                    gem.y = this.y + 10;
                                    gem.islock = true;
                                    gem.id = m.getIDITEM();
                                    gem.time_drop = System.currentTimeMillis();
                                    gem.belongUser = p.charDBID;
                                    m.addGemItem(gem, (int)this.inCountry);
                                    droplist.add(gem);
                                }
                                ++i;
                            }
                            byte[] ptType = new byte[]{93, 95};
                            pt = new Potion((short)ptType[Map.r.nextInt(ptType.length)], 1, m);
                            pt.id = m.getIDITEM();
                            pt.x = this.x - 4;
                            pt.y = this.y - 4;
                            pt.belongUser = p.charDBID;
                            m.addPotion(pt, (int)this.inCountry);
                            pt.time_drop = System.currentTimeMillis();
                            droplist.add((Actor)pt);
                            break block16;
                        }
                        if (this.wave != 1) break block17;
                        int i = 0;
                        while (i < 1) {
                            short gemid = GemTemplate.ID_MATERIAL_LOW[Map.r.nextInt(2)][Map.r.nextInt(GemTemplate.ID_MATERIAL_LOW[1].length)];
                            GemItem gem = new GemItem(gemid);
                            if (gem != null) {
                                gem.cat = (byte)6;
                                gem.x = this.x + 5;
                                gem.y = this.y + 10;
                                gem.id = m.getIDITEM();
                                gem.time_drop = System.currentTimeMillis();
                                gem.belongUser = p.charDBID;
                                gem.islock = true;
                                m.addGemItem(gem, (int)this.inCountry);
                                droplist.add(gem);
                            }
                            ++i;
                        }
                        i = 0;
                        while (i < 2) {
                            byte[] ptType = new byte[]{93, 95};
                            pt = new Potion((short)ptType[Map.r.nextInt(ptType.length)], 1, m);
                            pt.id = m.getIDITEM();
                            pt.x = this.x - 4;
                            pt.y = this.y - 4;
                            pt.belongUser = p.charDBID;
                            m.addPotion(pt, (int)this.inCountry);
                            pt.time_drop = System.currentTimeMillis();
                            droplist.add((Actor)pt);
                            ++i;
                        }
                        break block16;
                    }
                    if (this.wave != 2) break block18;
                    int i = 0;
                    while (i < 1) {
                        short gemid = GemTemplate.ID_MATERIAL_LOW[Map.r.nextInt(2)][Map.r.nextInt(GemTemplate.ID_MATERIAL_LOW[1].length)];
                        GemItem gem = new GemItem(gemid);
                        if (gem != null) {
                            gem.cat = (byte)6;
                            gem.x = this.x + 5;
                            gem.y = this.y + 10;
                            gem.id = m.getIDITEM();
                            gem.time_drop = System.currentTimeMillis();
                            gem.islock = true;
                            gem.belongUser = p.charDBID;
                            m.addGemItem(gem, (int)this.inCountry);
                            droplist.add(gem);
                        }
                        ++i;
                    }
                    i = 0;
                    while (i < 2) {
                        byte[] ptType = new byte[]{93, 95};
                        pt = new Potion((short)ptType[Map.r.nextInt(ptType.length)], 1, m);
                        pt.id = m.getIDITEM();
                        pt.x = this.x - 4;
                        pt.y = this.y - 4;
                        pt.belongUser = p.charDBID;
                        m.addPotion(pt, (int)this.inCountry);
                        pt.time_drop = System.currentTimeMillis();
                        droplist.add((Actor)pt);
                        ++i;
                    }
                    break block16;
                }
                if (this.wave != 3) break block19;
                int i = 0;
                while (i < 1) {
                    short gemid = GemTemplate.ID_MATERIAL_LOW[Map.r.nextInt(3)][Map.r.nextInt(GemTemplate.ID_MATERIAL_LOW[2].length)];
                    GemItem gem = new GemItem(gemid);
                    if (gem != null) {
                        gem.cat = (byte)6;
                        gem.islock = true;
                        gem.x = this.x + 5;
                        gem.y = this.y + 10;
                        gem.id = m.getIDITEM();
                        gem.time_drop = System.currentTimeMillis();
                        gem.belongUser = p.charDBID;
                        m.addGemItem(gem, (int)this.inCountry);
                        droplist.add(gem);
                    }
                    ++i;
                }
                i = 0;
                while (i < 3) {
                    byte[] ptType = new byte[]{94, 96, 82, 97};
                    pt = new Potion((short)ptType[Map.r.nextInt(ptType.length)], 1, m);
                    pt.id = m.getIDITEM();
                    pt.x = this.x - 4;
                    pt.y = this.y - 4;
                    pt.belongUser = p.charDBID;
                    m.addPotion(pt, (int)this.inCountry);
                    pt.time_drop = System.currentTimeMillis();
                    droplist.add((Actor)pt);
                    ++i;
                }
                break block16;
            }
            if (this.wave != 4) break block16;
            int i = 0;
            while (i < 1) {
                short gemid = GemTemplate.ID_MATERIAL_LOW[Map.r.nextInt(3)][Map.r.nextInt(GemTemplate.ID_MATERIAL_LOW[2].length)];
                GemItem gem = new GemItem(gemid);
                if (gem != null) {
                    gem.cat = (byte)6;
                    gem.x = this.x + 5;
                    gem.y = this.y + 10;
                    gem.islock = true;
                    gem.id = m.getIDITEM();
                    gem.time_drop = System.currentTimeMillis();
                    gem.belongUser = p.charDBID;
                    m.addGemItem(gem, (int)this.inCountry);
                    droplist.add(gem);
                }
                ++i;
            }
            i = 0;
            while (i < 3) {
                byte[] ptType = new byte[]{94, 96, 82, 97};
                pt = new Potion((short)ptType[Map.r.nextInt(ptType.length)], 1, m);
                pt.id = m.getIDITEM();
                pt.x = this.x - 4;
                pt.y = this.y - 4;
                pt.belongUser = p.charDBID;
                m.addPotion(pt, (int)this.inCountry);
                pt.time_drop = System.currentTimeMillis();
                droplist.add((Actor)pt);
                ++i;
            }
        }
        return droplist;
    }

    public void actorDie() {
        try {
            this.isDead = true;
            this.bornTime = System.currentTimeMillis() + 3600000L;
            this.timeOutPoinson = 0L;
            this.poinson = 0;
            Message m = new Message(90);
            m.dos.writeShort(this.id);
            m.dos.writeByte(this.cat);
            if (this.map != null) {
                this.map.sendAllPlayer(m, (int)this.inCountry, (int)this.idregion);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public int getTimeReborn() {
        return -1;
    }

    public boolean isMonsterVanTienTran() {
        return true;
    }

    public int getWave() {
        return this.wave;
    }

    public boolean isLienHoaTru() {
        return super.isLienHoaTru();
    }

    public boolean haveBackDam() {
        return Map.r.nextInt(100) < 80;
    }

    public boolean resistThroughArmor() {
        return Map.r.nextInt(100) < 80;
    }

    public boolean haveDodge() {
        return Map.r.nextInt(100) < 20;
    }

    public int getBackDam(int dam) {
        long pc = Map.r.nextInt(11) + 30;
        return (int)((long)dam * pc / 100L);
    }
}

