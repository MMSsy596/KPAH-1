/*
 * Decompiled with CFR 0.152.
 */
package real;

public class IndexWearing {
    byte[] pos;

    public IndexWearing(byte[] pos) {
        this.pos = pos;
    }

    public int getIndex(int posNhan) {
        return this.pos[posNhan];
    }
}

