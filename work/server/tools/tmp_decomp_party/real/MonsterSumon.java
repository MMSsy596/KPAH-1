/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  real.Map
 *  real.Monster
 */
package real;

import real.Map;
import real.Monster;
import real.MonsterTemplate;

public class MonsterSumon
extends Monster {
    public MonsterSumon(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
    }

    public MonsterSumon(byte cat) {
        super(cat);
    }

    public void update() {
        super.update();
    }

    public boolean isMonsterSumon() {
        return true;
    }
}

