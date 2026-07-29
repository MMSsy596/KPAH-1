package real;

import data.Database;
import io.Message;
import java.io.IOException;
import java.util.Calendar;
import java.util.Vector;

public class BossThuongLuong extends Boss {
    public boolean isOpen = false;
    public int hour = 0;
    public int minute = 0;

    public BossThuongLuong(Map mapLiveIn, MonsterTemplate template, int x, int y, byte country) {
        super(mapLiveIn, template, x, y, country);
        this.isBoss = true;
        this.percentDam = 200;
    }

    public void attack() {
        long now = System.currentTimeMillis();
        if (now - this.lastTimeAttack > this.attackDelay) {
            this.lastTimeAttack = now;

            try {
                Message m = new Message(83);
                m.dos.writeShort(this.id);
                m.dos.writeByte(1);
                int ahp = this.attackDam(this.target);
                boolean ismiss = this.attackMiss(this.target);
                if (ismiss) {
                    ahp = 0;
                }

                int rd = Map.r.nextInt(2);
                Vector<Char> charid = new Vector();
                Vector<Integer> damAttack = new Vector();
                charid.add(this.target);
                damAttack.add(ahp);
                if (rd != 0) {
                    Vector<Char> players = this.map.getAllPlayer(this.inCountry, this.region);

                    for(int i = 0; i < players.size(); ++i) {
                        Char c = (Char)players.get(i);
                        if (c.hp > 0 && this.near(c, 120)) {
                            charid.add(c);
                            int dam = this.attackDam(c);
                            boolean miss = this.attackMiss(this.target);
                            if (miss) {
                                dam = 0;
                            }

                            damAttack.add(dam);
                        }

                        if (charid.size() >= 10) {
                            break;
                        }
                    }
                }

                m.dos.writeByte(charid.size());

                for(int i = 0; i < charid.size(); ++i) {
                    Char c = (Char)charid.get(i);
                    m.dos.writeShort(c.id);
                    m.dos.writeShort((Integer)damAttack.get(i));
                    int hp = c.hp - (Integer)damAttack.get(i);
                    if (hp < 0) {
                        hp = 0;
                    }

                    m.dos.writeInt(hp);
                    c.hp = hp;
                }

                m.dos.writeByte(-1);

                for(int i = 0; i < charid.size(); ++i) {
                    Char c = (Char)charid.get(i);
                    c.sendMessage(m);
                    if (c.map.isMapTrain() && c.isHieuUngCoLongDuongQua() && c.hp <= 0) {
                        c.hp = 1;
                    }

                    if (c.hp <= 0) {
                        c.desTroy();

                        try {
                            long xp = c.lvDetail.getExp();
                            xp -= c.lvDetail.getXPLost(c.killer, c);
                            int currentlv = c.lvDetail.lv;
                            c.lvDetail.setExp(xp, c.oldLv, c.getName(), "bossthuongluong");
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
                        } catch (Exception var14) {
                        }
                    }

                    for(int j = 0; j < c.nearChars.size(); ++j) {
                        Char p = this.map.getPlayerByID((Short)c.nearChars.get(j));
                        if (p != null && !charid.contains(p)) {
                            p.sendMessage(m);
                        }
                    }
                }
            } catch (IOException var15) {
            }
        }

    }

    public void checkTimeLife() {
        if (this.isOpen) {
            Calendar cl = Calendar.getInstance();
            int iHour = cl.get(11);
            int iMinute = cl.get(12);
            if (this.isDead) {
                this.hp = this.maxhp;
                if (iHour == this.hour && iMinute < this.minute) {
                    Map.createLocationGate(0);
                    this.isDead = false;
                    byte[] he = new byte[]{0, 1, 2, 3, 4};
                    this.he = he[Map.r.nextInt(5)];
                    int[] idmap = new int[]{111, 2521, 2522, 2523, 2524};

                    try {
                        int mapid = 0;
                        int pos = 0;
                        this.map = (Map)RealController.mapList.get(mapid = idmap[pos = Map.r.nextInt(5)]);
                        this.map.removeMonster(this.id, this.inCountry, this.region);
                        this.map.addMonsterDynamic(this, this.inCountry, this.region);
                        System.out.println(this.idTemplate + " TEMPLATE thuongluong");
                        System.out.println(pos + " thuong luong TAI MAP " + mapid);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        Message m = MessageCreator.createServerAlertAutoOffMessage(Map.getBossAppearMessage(this));

                        for(int i = 0; i < CharManager.instance.vChars.size(); ++i) {
                            ((Char)CharManager.instance.vChars.elementAt(i)).sendMessage(m);
                        }
                    } catch (Exception var10) {
                    }
                }
            } else if (iHour != this.hour && !this.isDead) {
                this.actorDie();

                try {
                    Message m = MessageCreator.createServerAlertAutoOffMessage("Thuồng luồng đã biến mất");

                    for(int i = 0; i < CharManager.instance.vChars.size(); ++i) {
                        ((Char)CharManager.instance.vChars.elementAt(i)).sendMessage(m);
                    }
                } catch (Exception var9) {
                }
            }

        }
    }

    public void update() {
        if (this.isDead) {
            this.hp = this.maxhp;
        } else {
            if (this.target == null) {
                this.move();
            } else {
                if (this.target.map != this.map) {
                    this.target = null;
                    return;
                }

                if (this.target.hp <= 0) {
                    this.target = null;
                    return;
                }

                if (this.target.hp > 0 && Math.abs(this.target.x - this.x) <= 96 && Math.abs(this.target.y - this.y) <= 96) {
                    this.attack();
                } else {
                    this.move();
                }
            }

            this.updateEffKham();
        }
    }

    public void actorDie() {
        super.actorDie();

        try {
            this.map.removeMonster(this.id, this.inCountry, this.region);
        } catch (Exception var2) {
        }

        Map.removeBossLocation(0);
    }

    public int getCharID() {
        return 0;
    }
}
