/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public final class bw {
    public Vector a = new Vector();
    public Vector b = new Vector();
    public Vector c = new Vector();
    public byte d = 0;
    public byte e = 0;

    public bw(Vector vector) {
        this.a = vector;
    }

    public bw(Vector vector, Vector vector2) {
        this.c = vector;
        this.b = vector2;
        this.a = vector;
    }
}

