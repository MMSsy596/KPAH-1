/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 *  real.Char
 *  real.Map
 */
package data;

import data.DragonTower;
import io.Message;
import java.util.Vector;
import real.Actor;
import real.Char;
import real.Map;
import real.MonsterTemplate;

public class DragonMoba
extends DragonTower {
    private long timeSendMove;

    public DragonMoba(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
    }

    @Override
    public int khamKhangMu() {
        return 50;
    }

    @Override
    public int khamKhangBang() {
        return 50;
    }

    @Override
    public int khamKhangDoc() {
        return 50;
    }

    @Override
    public int khamKhangChoang() {
        return 50;
    }

    @Override
    public int khamKhangHoathach() {
        return 50;
    }

    @Override
    public int khamKhangGiamtoc() {
        return 50;
    }

    @Override
    public boolean haveBackDam() {
        return Map.r.nextInt(100) < 20;
    }

    @Override
    public boolean resistThroughArmor() {
        return Map.r.nextInt(100) < 25;
    }

    @Override
    public boolean haveDodge() {
        return Map.r.nextInt(100) < 20;
    }

    @Override
    public int getBackDam(int dam) {
        int pc = Map.r.nextInt(25) + 15;
        return dam * pc / 100;
    }

    @Override
    public boolean isEnemy(Char p) {
        return true;
    }

    @Override
    public boolean allWayAdd() {
        return true;
    }

    @Override
    public boolean isBoss() {
        return true;
    }

    @Override
    public Vector<Actor> onDropItem(Map m, Char p) {
        try {
            if (Map.r.nextInt(1000) < 5) {
                Map.doCreateBookSkillPet((Char)p, (int)0);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return super.onDropItem(m, p);
    }

    @Override
    public void update() {
        block6: {
            if (!this.isDead) {
                try {
                    if (System.currentTimeMillis() - this.timeSendMove < 0L) break block6;
                    this.timeSendMove = System.currentTimeMillis() + 5000L;
                    try {
                        Vector players = this.map.getAllPlayer((int)(!Map.openLog ? this.inCountry : (byte)0), this.region);
                        int i = 0;
                        while (i < players.size()) {
                            Char p = (Char)players.get(i);
                            p.sendMessage(p.writeActorPos(new Message(4), (Actor)((Object)this)));
                            ++i;
                        }
                    }
                    catch (Exception exception) {}
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        super.update();
    }
}

