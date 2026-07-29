package data;

import io.Message;
import io.Session;
import java.util.Vector;
import real.Char;
import real.CharChienTruong;
import real.EffectBuff;
import real.LiveActor;
import real.Map;
import real.MapChienTruongMoba;
import real.MessageCreator;

public class CharThanthu extends Char {
    public long timeAttack = 0L;
    public int tox;
    public int toy;
    public Char owner = null;
    public byte idThanThu = 0;
    boolean follow = true;
    public long timeMove = System.currentTimeMillis() + 400L;
    long timeBuffOwner = System.currentTimeMillis() + 20000L;

    public CharThanthu(Session conn) {
        super(conn);
        this.speed = 8;
    }

    public void updateEffectBuff() {
        super.updateEffectBuff();
    }

    public String getName() {
        return super.getName();
    }

    public void update() {
        if (this.follow) {
            this.moved = false;
            if (Map.abs(this.x - this.tox) > 48) {
                if (this.x < this.tox) {
                    this.x += Map.abs(this.x - this.tox) / 24 * 24;
                } else if (this.x > this.tox) {
                    this.x -= Map.abs(this.x - this.tox) / 24 * 24;
                }

                this.moved = true;
            }

            if (Map.abs(this.y - this.toy) > 48) {
                if (this.y < this.toy) {
                    this.y += Map.abs(this.y - this.toy) / 24 * 24;
                } else if (this.y > this.toy) {
                    this.y -= Map.abs(this.y - this.toy) / 24 * 24;
                }

                this.moved = true;
            }

            if (this.moved) {
                this.owner.sendMessage(this.owner.writeActorPos(new Message(4), this));
                if (!this.owner.isAdmin) {
                    this.owner.sendToNearPlayer(this.owner.writeActorPos(new Message(4), this));
                }
            }
        } else if (System.currentTimeMillis() - this.timeMove > 0L && this.map.equals(this.owner.map)) {
            this.timeMove = System.currentTimeMillis() + 400L;
            this.owner.sendMessage(this.writeActorPos(new Message(4), this));
            if (!this.owner.isAdmin) {
                this.owner.sendToNearPlayer(this.owner.writeActorPos(new Message(4), this));
            }
        }

        this.doBuffOwner();
    }

    public void doAttackAuto(Vector<LiveActor> target) {
        try {
            if (this.owner != null && this.owner.map != null && this.owner.map.isMapChienTruongMoba()) {
                CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.owner.charname);
                if (c != null && c.getNoiLuc() < 10) {
                    return;
                }
            }

            if (((LiveActor)target.get(0)).cat == 1) {
                Message m = new Message(9);
                m.dos.writeShort(this.id);
                m.dos.writeShort(((LiveActor)target.get(0)).id);
                m.dos.writeByte(0);
                m.dos.writeInt(this.attackDam((LiveActor)target.get(0)));
                m.dos.writeInt(((LiveActor)target.get(0)).hp);
                m.dos.writeByte(0);
                m.dos.writeByte(1);
                m.dos.writeByte(-1);
                m.dos.writeByte(1);
                this.owner.sendMessage(m);
                this.owner.sendToNearPlayer(m);

                for(int i = 0; i < target.size(); ++i) {
                    LiveActor mt = (LiveActor)target.get(i);
                    Message msg = MessageCreator.createMsgAddDynamicNewEffect(EffectBuff.EFF_SKILL_HOA_KY_LAN, 100L, EffectBuff.BY_MAP, mt.id, mt.x / 16, mt.y / 16, 127, EffectBuff.ODER_PAINT[EffectBuff.EFF_SKILL_HOA_KY_LAN], this.attackDam((LiveActor)target.get(0)));
                    this.owner.sendMessage(msg);

                    for(int j = this.owner.nearChars.size() - 1; j >= 0; --j) {
                        try {
                            Char p = this.map.getPlayerByID((Short)this.owner.nearChars.get(j));
                            if (p.getIdCharThanThu() == -1 && p != null && p.id != this.owner.id && p.isBot == -1 && this.near(p, this.rangeAddMonster[0]) && p.map == this.map && p.region == this.region) {
                            }

                            p.sendMessage(msg);
                        } catch (Exception var9) {
                        }
                    }
                }
            } else {
                Char c = (Char)target.get(0);
                Message m = new Message(6);
                m.dos.writeShort(this.id);
                m.dos.writeShort(c.id);
                m.dos.writeByte(0);
                m.dos.writeInt(this.attackDamAuto(c));
                m.dos.writeInt(c.hp);
                m.dos.writeByte(0);
                m.dos.writeByte(1);
                m.dos.writeByte(-1);
                m.dos.writeByte(1);
                this.owner.sendMessage(m);
                this.owner.sendToNearPlayer(m);
                Message msg = MessageCreator.createMsgAddDynamicNewEffect(EffectBuff.EFF_SKILL_HOA_KY_LAN, 100L, EffectBuff.BY_MAP, ((LiveActor)target.get(0)).id, ((LiveActor)target.get(0)).x / 16, ((LiveActor)target.get(0)).y / 16, 127, EffectBuff.ODER_PAINT[EffectBuff.EFF_SKILL_HOA_KY_LAN], this.attackDamAuto(c));
                this.owner.sendMessage(msg);

                for(int j = this.owner.nearChars.size() - 1; j >= 0; --j) {
                    try {
                        Char p = this.map.getPlayerByID((Short)this.owner.nearChars.get(j));
                        if (p.getIdCharThanThu() == -1 && p != null && p.id != this.owner.id && p.isBot == -1 && this.near(p, this.rangeAddMonster[0]) && p.map == this.map && p.region == this.region) {
                        }

                        p.sendMessage(msg);
                    } catch (Exception var8) {
                    }
                }
            }
        } catch (Exception var10) {
        }

    }

    public void doAttack(Vector<LiveActor> target) {
       
        try {
            if (this.owner != null && this.owner.map != null && this.owner.map.isMapChienTruongMoba()) {
                CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.owner.charname);
                if (c != null && c.getNoiLuc() < 10) {
                    return;
                }
            }

            if (((LiveActor)target.get(0)).cat == 1) {
                Message m = new Message(9);
                m.dos.writeShort(this.id);
                m.dos.writeShort(((LiveActor)target.get(0)).id);
                m.dos.writeByte(0);
                m.dos.writeInt(this.attackDam((LiveActor)target.get(0)));
                m.dos.writeInt(((LiveActor)target.get(0)).hp);
                m.dos.writeByte(0);
                m.dos.writeByte(1);
                m.dos.writeByte(-1);
                m.dos.writeByte(1);
                this.owner.sendMessage(m);
                this.owner.sendToNearPlayer(m);

                for(int i = 0; i < target.size(); ++i) {
                    LiveActor mt = (LiveActor)target.get(i);
                    Message msg = MessageCreator.createMsgAddDynamicNewEffect(EffectBuff.EFF_SKILL_HOA_KY_LAN, 100L, EffectBuff.BY_MAP, mt.id, mt.x / 16, mt.y / 16, 127, EffectBuff.ODER_PAINT[EffectBuff.EFF_SKILL_HOA_KY_LAN], this.owner.attack * 5 / 100);
                    this.owner.sendMessage(msg);

                    for(int j = this.owner.nearChars.size() - 1; j >= 0; --j) {
                        try {
                            Char p = this.map.getPlayerByID((Short)this.owner.nearChars.get(j));
                            if (p.getIdCharThanThu() == -1 && p != null && p.id != this.owner.id && p.isBot == -1 && this.near(p, this.rangeAddMonster[0]) && p.map == this.map && p.region == this.region) {
                            }

                            p.sendMessage(msg);
                        } catch (Exception var9) {
                        }
                    }
                }
            } else {
                Char c = (Char)target.get(0);
                Message m = new Message(6);
                m.dos.writeShort(this.id);
                m.dos.writeShort(c.id);
                m.dos.writeByte(0);
                m.dos.writeInt(this.attackDam(c));
                m.dos.writeInt(c.hp);
                m.dos.writeByte(0);
                m.dos.writeByte(1);
                m.dos.writeByte(-1);
                m.dos.writeByte(1);
                this.owner.sendMessage(m);
                this.owner.sendToNearPlayer(m);
                Message msg = MessageCreator.createMsgAddDynamicNewEffect(EffectBuff.EFF_SKILL_HOA_KY_LAN, 100L, EffectBuff.BY_MAP, ((LiveActor)target.get(0)).id, ((LiveActor)target.get(0)).x / 16, ((LiveActor)target.get(0)).y / 16, 127, EffectBuff.ODER_PAINT[EffectBuff.EFF_SKILL_HOA_KY_LAN], this.owner.attack * 5 / 100);
                this.owner.sendMessage(msg);

                for(int j = this.owner.nearChars.size() - 1; j >= 0; --j) {
                    try {
                        Char p = this.map.getPlayerByID((Short)this.owner.nearChars.get(j));
                        if (p.getIdCharThanThu() == -1 && p != null && p.id != this.owner.id && p.isBot == -1 && this.near(p, this.rangeAddMonster[0]) && p.map == this.map && p.region == this.region) {
                        }

                        p.sendMessage(msg);
                    } catch (Exception var8) {
                    }
                }
            }
        } catch (Exception var10) {
        }

    }

    public void doBuffOwner() {
        try {
            if (this.owner != null && this.owner.map != null && this.owner.map.isMapChienTruongMoba()) {
                CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.owner.charname);
                if (c != null && c.getNoiLuc() < 10) {
                    return;
                }
            }

            if (this.getIdCharThanThu() > 0 && System.currentTimeMillis() - this.timeBuffOwner >= 0L) {
                this.timeBuffOwner = System.currentTimeMillis() + 20000L;
                if (this.owner.addEffBuff(this.getIdCharThanThu() == 1 ? EffectBuff.EFF_SHIELD_1 : EffectBuff.EFF_SHIELD_2, System.currentTimeMillis() + 10000L, EffectBuff.BY_ACTOR, 0) != null) {
                    this.owner.sendEffToChar(this.owner);
                    this.owner.sendEffToNearChar();
                    Message m = new Message(6);
                    m.dos.writeShort(this.id);
                    m.dos.writeShort(this.owner.id);
                    m.dos.writeByte(0);
                    m.dos.writeInt(0);
                    m.dos.writeInt(this.owner.hp);
                    m.dos.writeByte(0);
                    m.dos.writeByte(1);
                    m.dos.writeByte(-1);
                    m.dos.writeByte(1);
                    this.owner.sendMessage(m);
                    this.owner.sendToNearPlayer(m);
                    Message msg = MessageCreator.createMsgAddDynamicNewEffect(EffectBuff.EFF_SKILL_HOA_KY_LAN, 100L, EffectBuff.BY_MAP, this.owner.id, this.owner.x / 16, this.owner.y / 16, 127, EffectBuff.ODER_PAINT[EffectBuff.EFF_SKILL_HOA_KY_LAN], 0);
                    this.owner.sendMessage(msg);

                    for(int j = this.owner.nearChars.size() - 1; j >= 0; --j) {
                        try {
                            Char p = this.map.getPlayerByID((Short)this.owner.nearChars.get(j));
                            if (p.getIdCharThanThu() == -1 && p != null && p.id != this.owner.id && p.isBot == -1 && this.near(p, this.rangeAddMonster[0]) && p.map == this.map && p.region == this.region) {
                            }

                            p.sendMessage(msg);
                        } catch (Exception var5) {
                        }
                    }
                }
            }
        } catch (Exception var6) {
        }

    }

    public int attackDamAuto(LiveActor actor) {
        if (this.owner != null && this.owner.map != null && this.owner.map.isMapChienTruongMoba()) {
            CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.owner.charname);
            if (c != null && c.getNoiLuc() < 10) {
                return 0;
            }
        }

        if (!actor.isRuongMaquai() && !actor.isNgocRong() && !actor.isBossSonTinh() && !actor.isBossThuyTinh() && !actor.isBossSonTinhThuyTinh()) {
            if (actor.cat == 0) {
                if (actor.isBatTu()) {
                    return 0;
                }

                if (this.getIdCharThanThu() == 1) {
                    return this.owner.attack * 20 / 100;
                }

                if (this.getIdCharThanThu() == 2) {
                    return actor.maxhp * 20 / 100;
                }
            } else {
                if (this.getIdCharThanThu() == 1) {
                    return this.owner.attack * 20 / 100;
                }

                if (this.getIdCharThanThu() == 2) {
                    return this.owner.attack * 30 / 100;
                }
            }

            return this.owner.attack * 5 / 100;
        } else {
            return 0;
        }
    }

    public int attackDam(LiveActor actor) {
        if (this.owner != null && this.owner.map != null && this.owner.map.isMapChienTruongMoba()) {
            CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.owner.charname);
            if (c != null && c.getNoiLuc() < 10) {
                return 0;
            }
        }

        if (!actor.isRuongMaquai() && !actor.isNgocRong() && !actor.isBossSonTinh() && !actor.isBossThuyTinh() && !actor.isBossSonTinhThuyTinh()) {
            if (actor.cat == 0) {
                if (actor.isBatTu()) {
                    return 0;
                }

                int deltalv = this.owner.lvDetail.lv - actor.getLevel();
                int att = this.owner.attack;
                if (deltalv < 0 && Map.abs(deltalv) > 5) {
                    att -= att * (Map.abs(deltalv) - 5) * 2 / 100;
                }

                if (this.getIdCharThanThu() == 1) {
                    int dam = actor.checkGiamSatThuongTong(att * 20 / 100);
                    return dam;
                }

                if (this.getIdCharThanThu() == 2) {
                    int dam = actor.checkGiamSatThuongTong(att * 30 / 100);
                    return dam;
                }
            } else {
                if (this.getIdCharThanThu() == 1) {
                    return this.owner.attack * 20 / 100;
                }

                if (this.getIdCharThanThu() == 2) {
                    return this.owner.attack * 30 / 100;
                }
            }

            return this.owner.attack * 5 / 100;
        } else {
            return 0;
        }
    }

    public int getIdCharThanThu() {
        return this.idThanThu;
    }

    public void setXtoYto(int x, int y) {
        this.tox = x;
        this.toy = y;
    }

    public boolean canFocus(Char me) {
        return false;
    }
}
