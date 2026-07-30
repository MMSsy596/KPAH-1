/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 *  real.Char
 *  real.CharManager
 *  real.Map
 *  real.Monster
 *  server.TeamServer
 */
package real;

import io.Message;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Vector;
import real.Char;
import real.CharManager;
import real.Map;
import real.Monster;
import server.TeamServer;

public class Dun
extends Map {
    private int deltaXP = 2;

    public Dun(int id) {
        super(id);
    }

    public Dun(int id, int idXaphu, int magic_physic, int mapload, int nregion) {
        super(id, idXaphu, magic_physic, mapload, nregion);
    }

    public boolean isMapTrain() {
        return false;
    }

    public void doRequestMonterInfo(Char p, Message message) throws IOException {
        Monster monster;
        DataInputStream dis = message.dis;
        short id = (short)dis.readUnsignedShort();
        if (p.party.userParty.size() == 0) {
            monster = (Monster)p.monsters.get(id);
        } else {
            Char pLeader = CharManager.instance.getByCharID(p.party.idMaster);
            monster = (Monster)pLeader.monsters.get(id);
        }
        if (monster == null) {
            return;
        }
        Message m = new Message(7);
        m.dos.writeShort(monster.id);
        m.dos.writeByte(monster.getType());
        m.dos.writeShort(monster.x);
        m.dos.writeShort(monster.y);
        m.dos.writeInt(monster.hp);
        m.dos.writeByte(monster.level);
        m.dos.writeByte(monster.he);
        m.dos.writeInt(monster.maxhp);
        p.sendMessage(m);
        m.cleanup();
    }

    public void update() {
        Char p;
        boolean exitBoard = false;
        Vector players = this.getAllPlayer(1, 0);
        int size = players.size();
        int k = 0;
        while (k < size) {
            try {
                p = (Char)players.get(k);
                if ((p.map == null || p.getSession() == null || p.mapID == -1) && p.isBot == -1) {
                    players.remove(k);
                    CharManager.instance.remove(p);
                    continue;
                }
                if (!p.map.equals((Object)this) || p.mapID != this.mapId) {
                    players.remove(k);
                    continue;
                }
                if (CharManager.instance.getCharByCharName(p.getName()) == null && p.isBot == -1) {
                    players.remove(k);
                    CharManager.instance.remove(p);
                    continue;
                }
                if (p.map == this) {
                    p.update();
                    if ((this.mapId == 107 || this.mapId == 2441 || this.mapId == 2442 || this.mapId == 2443 || this.mapId == 2444) && System.currentTimeMillis() - TeamServer.timeOutBoard >= TeamServer.timeDownBoard && TeamServer.timeOutBoard > 0L) {
                        exitBoard = true;
                    }
                }
                ++k;
            }
            catch (Exception e) {
                break;
            }
        }
        if (exitBoard) {
            size = players.size();
            while (size > 0) {
                try {
                    p = (Char)players.remove(0);
                    --size;
                    if (p == null) continue;
                    int id = this.mapId % 2440;
                    id = this.mapId == 107 ? 10 : (id += 500);
                    this.move2Map(p, 62 + Dun.random((int)5), 122 + Dun.random((int)5), id, p.inCountry);
                    if (p.potions[20] <= 0) continue;
                    p.potions[20] = p.potions[20] - 1;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            TeamServer.timeOutBoard = 0L;
        }
        this.deletePotionAndItemOnGround(1);
    }
}

