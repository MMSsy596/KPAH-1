/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  real.Monster
 */
package real;

import java.util.HashMap;
import real.Monster;

public class MonsterManager {
    protected static MonsterManager instance;
    private final HashMap<Short, Monster> monsterID = new HashMap();

    protected MonsterManager() {
    }

    public static MonsterManager getInstance() {
        if (instance == null) {
            instance = new MonsterManager();
        }
        return instance;
    }

    public void put(Monster p) {
        this.monsterID.put(p.id, p);
    }

    public void remove(Monster p) {
        this.monsterID.remove(p.id);
    }

    public Monster get(short id) {
        return this.monsterID.get(id);
    }
}

