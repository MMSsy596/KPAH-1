/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  real.Char
 *  real.Map
 *  real.RealController
 */
package data;

import io.Session;
import real.Char;
import real.Map;
import real.RealController;

public class ChatBot
extends Char {
    public static long timeLive = System.currentTimeMillis();
    public static int[] idMap;

    static {
        int[] nArray = new int[4];
        nArray[1] = 70;
        nArray[2] = 301;
        nArray[3] = 1701;
        idMap = nArray;
    }

    public ChatBot(Session conn) {
        super(conn);
    }

    public void update() {
        super.update();
        if (System.currentTimeMillis() - timeLive >= 0L) {
            if (this.map != null) {
                this.map.playerExit((Char)this);
                this.map = null;
            } else if (System.currentTimeMillis() - timeLive >= 54000000L) {
                this.setTimeLive();
                this.map = (Map)RealController.mapList.get(idMap[Map.r.nextInt(idMap.length)]);
                this.map.playerJoin((Char)this);
            }
        }
    }

    public void setTimeLive() {
        timeLive = System.currentTimeMillis() + 54000000L;
    }
}

