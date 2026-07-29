package real;

import data.Database;
import data.GemItem;
import io.Message;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import real.cmd.LoginHandler;
import server.TeamServer;

public class BossSonTinhThuyTinh extends BossSkelonton {
    int rcvXP = 0;
    int stMapID = 0;
    public Region rg = null;
    long timeExist = System.currentTimeMillis();
    Hashtable<String, CharBeAttack> allCharBeAtk = new Hashtable();
    static int[] idng = new int[]{68, 69, 70, 71, 72, 68, 68, 68, 68, 68, 68, 68, 70, 70, 70, 70, 70, 70, 70, 70, 71, 68, 68, 69, 69, 69, 69, 69, 69, 69, 69, 69, 75, 76, 77, 78, 79, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 76, 76, 76, 76, 76, 77, 76, 76, 76, 76, 76, 76, 77, 77, 77, 77, 78, 82, 83, 84, 85, 86, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 83, 83, 83, 83, 83, 84, 83, 83, 83, 83, 83, 83, 83, 84, 84, 84, 85, 89, 90, 91, 92, 93, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 90, 90, 90, 90, 90, 91, 90, 90, 91, 91, 91, 91, 91, 91, 91, 91, 92, 96, 97, 98, 99, 100, 96, 96, 96, 96, 96, 96, 96, 96, 96, 96, 97, 97, 97, 97, 97, 98, 98, 98, 98, 98, 98, 98, 99, 98, 99, 98, 98};
    static int[] idGem = new int[]{62, 62, 62, 62, 62, 62, 62, 62, 62, 63, 63, 63, 63, 63, 63, 63, 64, 63, 62, 62, 62, 62, 63, 64, 65};
    int allNgavoi = 50;
    int allcuaga = 50;
    int alllongmao = 50;

    public BossSonTinhThuyTinh(Map mapLiveIn, MonsterTemplate template, int x, int y, byte country) {
        super(mapLiveIn, template, x, y, country);
        this.attackDelay = 7000L;
        this.isBoss = true;
    }

    public void update() {
        this.updateEffectBuff();
        if (this.isDead) {
            this.hp = this.maxhp;
            if (System.currentTimeMillis() > this.bornTime) {
                this.bornTime = System.currentTimeMillis();
                this.timeLife = System.currentTimeMillis();
                this.isDead = false;
                this.hp = this.maxhp;
                this.xp = this.rcvXP;
                this.target = null;
                this.x = this.default_x;
                this.y = this.default_y;
                this.toX = this.x;
                this.toY = this.y;
            }

        } else {
            if (System.currentTimeMillis() - this.timeChangehe > 60000L) {
                byte[] he = new byte[]{0, 1, 2, 3, 4};
                this.he = he[Map.r.nextInt(5)];
                this.magic_physic = (byte)((this.magic_physic + 1) % 2);
            }

            this.updateEffKham();
            if (!this.freeze()) {
                if (System.currentTimeMillis() - this.timeHealth > 60000L && this.hp > 0 && this.hp < this.maxhp) {
                    this.hp += this.maxhp * 25 / 100;
                    if (this.hp > this.maxhp) {
                        this.hp = this.maxhp;
                    }

                    this.timeHealth = System.currentTimeMillis();
                }

                if (this.target == null) {
                    this.move();
                } else {
                    if (!this.target.map.equals(this.map) || this.target.region != this.region && this.randomMap || this.target.inCountry != this.inCountry && this.randomMap) {
                        this.target = null;
                        return;
                    }

                    if (this.target.hp <= 0) {
                        this.target = null;
                        return;
                    }

                    if (this.target.hp > 0) {
                        if (Math.abs(this.target.x - this.x) <= 120 && Math.abs(this.target.y - this.y) <= 120) {
                            this.attack();
                            if (Map.r.nextInt(1) == 1) {
                                this.lastTimeMove = 0L;
                                this.move();
                            }

                            if (Math.abs(this.x - this.target.x) > 32 || Math.abs(this.y - this.target.y) > 32) {
                                int xx = this.x;
                                int yy = this.y;
                                if (Math.abs(this.x - this.target.x) > 32) {
                                    if (this.x < this.target.x) {
                                        xx = this.x + 32;
                                    } else {
                                        xx = this.x - 32;
                                    }
                                }

                                if (Math.abs(this.y - this.target.y) > 32) {
                                    if (this.y < this.target.y) {
                                        yy = this.y + 16;
                                    } else {
                                        yy = this.y - 32;
                                    }
                                }

                                if (!this.canMove(xx, yy)) {
                                    this.x = xx;
                                    this.y = yy;
                                }
                            }
                        } else {
                            this.lastTimeMove = 0L;
                            this.move();
                        }
                    } else {
                        this.move();
                        this.target = null;
                    }
                }

            }
        }
    }

    public void setInfo(int level, int maxHp, int rcvXP) {
        this.level = level;
        this.maxhp = maxHp;
        this.hp = maxHp;
        this.rcvXP = rcvXP;
        this.stMapID = this.map.getMapLoad(this.map.mapId);
        this.dmove = 96;
        this.attackDelay = 5000L;
        this.xp = this.rcvXP;
    }

    public int getXpReceive(long hpLost) {
        int xpGet = 0;
        if (this.hp > 0 && hpLost > 0L) {
            if (hpLost >= (long)this.maxhp && this.hp == this.maxhp) {
                return this.xp;
            } else if (hpLost > (long)this.hp && this.hp > 0) {
                return this.xp;
            } else {
                long percentHp = hpLost * 100L / (long)this.hp;
                if (hpLost > 0L && percentHp <= 0L) {
                    percentHp = 1L;
                }

                xpGet = (int)((long)this.xp * percentHp) / 100;
                if (this.xp > 0 && xpGet < 0) {
                    xpGet = 1;
                }

                this.xp -= xpGet;
                return xpGet;
            }
        } else {
            return 0;
        }
    }

    public boolean isActive() {
        return true;
    }

    public Vector<Char> getAllPlayerMap() {
        new Vector();
        Vector<Char> p = this.map.getAllPlayer(this.inCountry, this.region);
        return p;
    }

    public boolean isBossSonTinh() {
        return this.idTemplate == 130;
    }

    public boolean isBossGetItemByHit() {
        return true;
    }

    public boolean isBossThuyTinh() {
        return this.idTemplate == 129;
    }

    public void attack() {
        if (this.target == null || this.target.map.equals(this.map) && (this.target.region == this.region || !this.randomMap) && (this.target.inCountry == this.inCountry || !this.randomMap)) {
            long now = System.currentTimeMillis();
            if (now - this.lastTimeAttack > this.attackDelay) {
                this.lastTimeAttack = now;

                try {
                    Message m = new Message(83);
                    m.dos.writeShort(this.id);
                    int idSkill = Map.r.nextInt(2);
                    if (System.currentTimeMillis() - this.cooldown > (long)this.skill2) {
                        idSkill = 2;
                        this.cooldown = System.currentTimeMillis();
                        int[] timesk = new int[]{10000, 15000, 20000, 5000};
                        this.skill2 = timesk[Map.r.nextInt(timesk.length)];
                    }

                    idSkill = 0;
                    m.dos.writeByte(idSkill);
                    CharBeAttack cbatk = (CharBeAttack)this.allCharBeAtk.get(this.target.getName());
                    if (cbatk == null) {
                        cbatk = new CharBeAttack();
                        cbatk.charname = this.target.charname;
                        cbatk.timeBeAttack = System.currentTimeMillis();
                        this.allCharBeAtk.put(cbatk.charname, cbatk);
                    } else {
                        if (System.currentTimeMillis() - cbatk.timeBeAttack <= 8000L) {
                            cbatk.dam += 2;
                        } else {
                            cbatk.dam = 1;
                        }

                        cbatk.timeBeAttack = System.currentTimeMillis();
                    }

                    long pcdam = (long)cbatk.dam;
                    int ahp = (int)(pcdam * (long)this.target.maxhp / 100L);
                    if (idSkill == 1) {
                        if (ahp < 5000) {
                            ahp = 5000 + Map.r.nextInt(100) + 10;
                        }

                        ahp *= 3;
                    }

                    ahp = this.target.checkHapthuSatThuong(ahp, this);
                    ahp = this.target.checkGiamSatThuong(ahp);
                    ahp = this.target.checkPassAttack(this, ahp);
                    boolean demi = false;
                    if (Map.r.nextInt(100) <= 30) {
                        demi = true;
                    }

                    demi = false;
                    boolean crit = false;
                    if (Map.r.nextInt(100) <= 40) {
                        crit = true;
                    }

                    if (crit) {
                        ahp *= 2;
                    }

                    if (Map.r.nextInt(100) < 20 && this.canHoaNguoiTuyet() && !this.target.isAdmin) {
                        this.target.setNguoiTuyet(2);
                    }

                    boolean ismiss = this.attackMiss(this.target);
                    int freezz = Map.r.nextInt(100);
                    if (freezz <= 20 && !this.canHoaNguoiTuyet() && !this.target.isAdmin) {
                        if (this.idTemplate == 129) {
                            if (Map.r.nextInt(100) > this.target.khamKhangBang()) {
                                this.target.khamEff[Char.KHAM_BANG] = System.currentTimeMillis() / 1000L;
                            }
                        } else if (this.idTemplate == 130 && Map.r.nextInt(100) > this.target.khamKhangHoathach()) {
                            this.target.khamEff[Char.KHAM_HOA_DA] = System.currentTimeMillis() / 1000L;
                        }
                    }

                    int rd = Map.r.nextInt(2);
                    Vector<Char> charid = new Vector();
                    Vector<Integer> damAttack = new Vector();
                    charid.add(this.target);
                    if (this.target.hp - ahp > 0 && demi) {
                        ahp = this.target.hp - 1;
                    }

                    this.target.checkNewEffectItem(1, (long)(ahp / 10), this);
                    if (!demi) {
                        ahp = (int)((long)ahp - this.target.checkMagicShield(ahp));
                    }

                    if (ahp <= 0) {
                        ahp = 10;
                    }

                    damAttack.add(ahp);
                    int nplayer = idSkill == 0 ? 20 : 30;

                    try {
                        Vector<Char> players = this.map.getAllPlayer(this.inCountry, this.region);
                        if (this.findPos == 0) {
                            for(int i = 0; i < players.size(); ++i) {
                                Char c = (Char)players.get(i);
                                if (c.hp > 0 && this.near(c, 220) && c.id != this.target.id && !c.isAdmin && c.region == this.region && c.isBot == -1) {
                                    charid.add(c);
                                    cbatk = (CharBeAttack)this.allCharBeAtk.get(c.getName());
                                    if (cbatk == null) {
                                        cbatk = new CharBeAttack();
                                        cbatk.charname = c.charname;
                                        cbatk.timeBeAttack = System.currentTimeMillis();
                                        this.allCharBeAtk.put(cbatk.charname, cbatk);
                                    } else {
                                        if (System.currentTimeMillis() - cbatk.timeBeAttack <= 8000L) {
                                            cbatk.dam += 2;
                                        } else {
                                            cbatk.dam = 1;
                                        }

                                        cbatk.timeBeAttack = System.currentTimeMillis();
                                    }

                                    pcdam = (long)cbatk.dam;
                                    int dam = (int)(pcdam * (long)c.maxhp / 100L);
                                    if (idSkill == 1) {
                                        if (dam < 5000) {
                                            dam = 5000 + Map.r.nextInt(100) + 10;
                                        }

                                        dam *= 3;
                                    }

                                    if (crit) {
                                        dam *= 2;
                                    }

                                    dam = c.checkHapthuSatThuong(dam, this);
                                    dam = c.checkGiamSatThuong(dam);
                                    dam = c.checkPassAttack(this, dam);
                                    boolean miss = this.attackMiss(c);
                                    if (miss) {
                                        dam = 0;
                                    }

                                    if (c.hp - dam > 0 && demi) {
                                        dam = c.hp - 1;
                                    }

                                    if (!demi) {
                                        dam = (int)((long)dam - c.checkMagicShield(dam));
                                    }

                                    if (dam <= 0) {
                                        dam = 10;
                                    }

                                    if (Map.r.nextInt(100) < 20 && this.idTemplate == 115 && !c.isAdmin) {
                                        c.setNguoiTuyet(2);
                                    }

                                    damAttack.add(dam);
                                    if (freezz <= 20) {
                                        if (this.idTemplate == 129) {
                                            if (Map.r.nextInt(100) > this.target.khamKhangBang()) {
                                                c.khamEff[Char.KHAM_BANG] = System.currentTimeMillis() / 1000L;
                                            }
                                        } else if (this.idTemplate == 130 && Map.r.nextInt(100) > this.target.khamKhangHoathach()) {
                                            c.khamEff[Char.KHAM_HOA_DA] = System.currentTimeMillis() / 1000L;
                                        }
                                    }
                                }

                                if (charid.size() >= nplayer) {
                                    break;
                                }
                            }
                        }
                    } catch (Exception var25) {
                    }

                    this.findPos = 0;
                    m.dos.writeByte(charid.size());

                    for(int i = 0; i < charid.size(); ++i) {
                        Char c = (Char)charid.get(i);
                        m.dos.writeShort(c.id);
                        m.dos.writeInt((Integer)damAttack.get(i));
                        int hp = c.hp - (Integer)damAttack.get(i);
                        if (c.isAdmin) {
                            hp = c.hp;
                        }

                        if (hp < 0) {
                            hp = 0;
                        } else if (idSkill != 0 && !this.randomMap && Map.r.nextInt(100) < 50 && !c.isAdmin && c.addEffBuff(EffectBuff.TREE, System.currentTimeMillis() + 5000L, EffectBuff.BY_ACTOR, 0) != null) {
                            c.sendEffToChar(c);
                            c.sendEffToNearChar();
                        }

                        m.dos.writeInt(hp);
                        c.hp = hp;
                        if (c.map.isMapTrain() && c.isHieuUngCoLongDuongQua() && c.hp <= 0) {
                            c.hp = 1;
                        }

                        if (c.hp <= 0) {
                            Database.instance.saveOrtherLog("", c.getName(), c.hp + "_" + damAttack.get(i) + "_" + this.getMonsterTemplate().name + "_" + Map.getNameMap(this.map.mapId) + "_" + this.region + "_" + c.region, "die");
                        }
                    }

                    m.dos.writeByte(-1);

                    for(int i = 0; i < charid.size(); ++i) {
                        Char c = (Char)charid.get(i);
                        c.sendMessage(m);
                        if (c.hp <= 0) {
                            c.doSetTimeAutoHoiSinh();
                            c.actorDie();

                            try {
                                cbatk = (CharBeAttack)this.allCharBeAtk.get(this.target.getName());
                                cbatk.dam = 1;
                                cbatk.timeBeAttack = System.currentTimeMillis() - 5000L;
                            } catch (Exception var24) {
                            }

                            c.desTroy();

                            try {
                                long xp = c.lvDetail.getExp();
                                xp -= c.lvDetail.getXPLost(c.killer, c);
                                int currentlv = c.lvDetail.lv;
                                c.lvDetail.setExp(xp, c.oldLv, c.getName(), "bossskelonton");
                                if (c.lvDetail.lv <= 1) {
                                    c.lvDetail.lv = 1;
                                    c.lvDetail.percent = 0;
                                }

                                if (currentlv > c.lvDetail.lv) {
                                    c.lvDetail.resetExp2Lv(currentlv, c.killer);
                                    if (c.killer > 0) {
                                        Database.instance.saveOrtherLog("", c.getName(), "tut level do dang trong che do ds " + c.killer, "downlv");
                                    }
                                }

                                c.calculateAttrib();
                                c.doSendProperties();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        for(int j = 0; j < c.nearChars.size(); ++j) {
                            Char p = this.map.getPlayerByID((Short)c.nearChars.get(j));
                            if (p != null && !charid.contains(p)) {
                                p.sendMessage(m);
                                if (c.khamEff[Char.KHAM_BANG] > 0L || c.khamEff[Char.KHAM_HOA_DA] > 0L) {
                                    p.sendMessage(p.sendEffKham(c));
                                }
                            }
                        }

                        for(int kk = charid.size() - 1; kk >= 0; --kk) {
                            Char p = (Char)charid.get(kk);

                            for(int j = 0; j < charid.size(); ++j) {
                                Char pp = (Char)charid.get(j);
                                if (p.khamEff[Char.KHAM_BANG] > 0L || p.khamEff[Char.KHAM_HOA_DA] > 0L) {
                                    pp.sendMessage(p.sendEffKham(p));
                                }
                            }
                        }
                    }

                    m.cleanup();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        } else {
            this.target = null;
        }
    }

    public void actorDie() {
        try {
            this.isDead = true;
            this.bornTime = System.currentTimeMillis() + 10800000L;
            if (TeamServer.isServerLocal()) {
                this.bornTime = System.currentTimeMillis() + 5000L;
            }

            this.allNgavoi = 50;
            this.allcuaga = 50;
            this.alllongmao = 50;
            this.allCharBeAtk.clear();
            this.timeOutPoinson = 0L;
            this.poinson = 0;
            Message m = new Message(90);
            m.dos.writeShort(this.id);
            m.dos.writeByte(this.cat);
            if (this.map != null) {
                this.map.sendAllPlayer(m, this.inCountry);
            }
        } catch (Exception var2) {
        }

        Map.removeBossLocation(0);
    }

    public boolean isBossSonTinhThuyTinh() {
        return true;
    }

    public GemItem dropGemItem() {
        return null;
    }

    public Vector<GemItem> dropListGemItem() {
        Vector<GemItem> gem = new Vector();
        if (Map.r.nextInt(100) < 30) {
            int soluong = 1 + Map.r.nextInt(2);

            for(int i = 0; i < soluong; ++i) {
                int lv = Map.r.nextInt(3) + 1;
                short[] id = GemTemplate.ID_MATERIAL_LOW[lv];
                GemItem g = new GemItem(id[Map.r.nextInt(id.length)]);
                gem.add(g);
                g.islock = Map.r.nextInt(100) < 10;
            }
        } else {
            int[] idbot = new int[]{GemTemplate.DUC_TUONG_KHAM_1};
            int[] slmin = new int[]{3};
            int[] slrandom = new int[]{3};
            int rd = Map.r.nextInt(idbot.length);
            int sl = slmin[rd];
            if (slmin[rd] != slrandom[rd]) {
                sl += Map.r.nextInt(slrandom[rd]);
            }

            for(int i = 0; i < sl; ++i) {
                GemItem g = new GemItem(idbot[rd]);
                gem.add(g);
            }
        }

        return gem;
    }

    public Vector<Item> dropItem() {
        return super.dropItem();
    }

    public synchronized void checkReceivePotion(Char p) {
        if (Map.r.nextInt(100) < 20) {
            int[] a = new int[]{this.allNgavoi, this.allcuaga, this.alllongmao};
            int[] b = new int[]{77, 105, 142};
            int index = Char.getIndexRandom(a);
            if (index > -1) {
                int var10002 = p.potions[b[index]]++;
                if (index == 0) {
                    --this.allNgavoi;
                } else if (index == 1) {
                    --this.allcuaga;
                } else if (index == 2) {
                    --this.alllongmao;
                }

                p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                p.sendMessage(MessageCreator.createMsgChat(p.id, "Nhận được " + LoginHandler.PORTION_NAME[b[index]]));
                Database.instance.saveOrtherLog("", p.charname, "nhan " + LoginHandler.PORTION_NAME[b[index]] + " khi danh son tinh thuy tinh", "sttt");
            }
        }

    }

    public Vector<Potion> dropPotion(Char p) {
        Vector<Potion> pt = new Vector();
        int sl = r.nextInt(3) + 1;
        String info = "nhan " + sl + " dua hau, " + this.allNgavoi + " nga voi, " + this.allcuaga + " cua ga, " + this.alllongmao + " long mao, ";
        int[] var10000 = p.potions;
        var10000[117] += sl;
        var10000 = p.potions;
        var10000[77] += this.allNgavoi;
        var10000 = p.potions;
        var10000[105] += this.allcuaga;
        var10000 = p.potions;
        var10000[142] += this.alllongmao;

        try {
            short[] a = new short[]{100, 93, 95, 112, 114, 113, 121};
            int[] slmin = new int[]{1, 100, 100, 1, 1, 1, 1};
            int[] slmaxn = new int[]{1, 100, 100, 3, 3, 3, 1};
            int index = Map.r.nextInt(a.length);
            if (index > -1) {
                sl = slmin[index];
                if (slmin[index] != slmaxn[index]) {
                    sl += Map.r.nextInt(slmaxn[index] - slmin[index] + 1);
                }

                info = info + sl + " " + LoginHandler.PORTION_NAME[a[index]];
                var10000 = p.potions;
                var10000[a[index]] += sl;
            }
        } catch (Exception var9) {
        }

        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
        Database.instance.saveOrtherLog("", p.charname, info, "ksttt");
        return pt;
    }

    public void setTimeReBornInEvent(long time) {
        this.bornTime = System.currentTimeMillis() + 10800000L;
        this.isDead = true;
        if (TeamServer.isServerLocal()) {
            this.bornTime = System.currentTimeMillis() + 5000L;
        }

    }

    public void setTimeReBorn() {
        this.bornTime = System.currentTimeMillis() + 10800000L;
        this.isDead = true;
        if (TeamServer.isServerLocal()) {
            this.bornTime = System.currentTimeMillis() + 5000L;
        }

    }

    public int getTimeReborn() {
        return -1;
    }

    public int getCharID() {
        return 0;
    }
}
