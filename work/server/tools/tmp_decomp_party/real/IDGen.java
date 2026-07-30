/*
 * Decompiled with CFR 0.152.
 */
package real;

import java.util.concurrent.ArrayBlockingQueue;

public class IDGen {
    public static final int maxCatagory = 10;
    public static String[] catname = new String[]{"PLAYER", "MONSTER", "NPC", "ITEM", "POTION", "PARTY", "GEMITEM", "SPECIAL_ITEM", "CAT8", "CAT9", "CAT10"};
    private ArrayBlockingQueue<Short>[] queue = new ArrayBlockingQueue[10];

    public void setMaxCatalory(int size, int[] indexgen, int[] sizeGen) {
        int c = 0;
        while (c < size) {
            int queueSize = sizeGen[c];
            this.queue[indexgen[c]] = new ArrayBlockingQueue(queueSize * 2 + 10);
            int i = -queueSize;
            while (i <= queueSize) {
                this.queue[indexgen[c]].add(new Short((short)i));
                ++i;
            }
            ++c;
        }
    }

    public IDGen(int queueSize) {
    }

    public short getID(int catagory, String reason) {
        Short s = this.queue[catagory].poll();
        if (s == null) {
            return 0;
        }
        return s;
    }

    public void putID(short idreturn, int catagory, String reason) {
        Short s;
        if (catagory == 1) {
            System.out.println("PUT CAT=" + catagory + " reason=" + reason + " " + this.queue[catagory].size());
        }
        if (this.queue[catagory].contains(s = new Short(idreturn))) {
            return;
        }
        this.queue[catagory].add(s);
        if (catagory == 1) {
            System.out.println("SL MONS: " + this.queue[catagory].size());
        }
    }

    public int getSize(int catagory) {
        return this.queue[catagory].size();
    }

    public String getSizeAll() {
        String s = "";
        int i = 0;
        while (i < this.queue.length) {
            s = String.valueOf(s) + catname[i] + "=" + this.getSize(i) + ", ";
            ++i;
        }
        return s;
    }
}

