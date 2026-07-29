/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class yu
extends di {
    private short[] o;
    private short[] p;
    private byte[] q;
    private byte[] r;
    int n;
    private int s;
    private int t;
    private int u;
    private boolean v;
    private boolean w;
    private int x = 0;

    public yu(int n2, int n3, int n4, int n5, int n6, int n7, boolean bl2, boolean bl3) {
        this.v = bl2;
        this.t = n6;
        this.u = n7;
        this.s = n5;
        this.n = n4;
        this.d = n2;
        this.e = n3;
        this.o = new short[6];
        this.p = new short[6];
        this.q = new byte[6];
        this.r = new byte[6];
        n4 = 0;
        while (n4 < 6) {
            this.o[n4] = (short)(n2 - 10 + yi.m(20) - 10);
            this.p[n4] = (short)(n3 + yi.m(20));
            this.q[n4] = (byte)yi.m(6);
            ++n4;
        }
        this.w = bl3;
    }

    public final void a() {
        int n2 = 0;
        while (n2 < this.o.length) {
            int n3 = n2;
            this.q[n3] = (byte)(this.q[n3] + 1);
            if (this.q[n2] >= this.s * 3) {
                this.o[n2] = (short)(this.d + yi.m(30) - 15);
                this.p[n2] = (short)(this.e + yi.m(14) + 3);
                this.q[n2] = (byte)yi.m(6);
                int n4 = n2;
                this.r[n4] = (byte)(this.r[n4] + 1);
                if (this.r[n2] > 3) {
                    if (!this.w) {
                        abm.a.removeElement(this);
                    } else {
                        abm.b.removeElement(this);
                    }
                }
            }
            ++n2;
        }
        ++this.x;
        if (this.x >= 6) {
            this.x = 0;
        }
    }

    public void a(Graphics graphics) {
        int n2 = 0;
        while (n2 < 6) {
            yi.a(graphics, this.n, 0, this.q[n2] / 3 * this.u, this.t, this.u, this.o[n2], this.p[n2], 33);
            ++n2;
        }
        if (this.v) {
            yi.a(graphics, 13, 0, this.x / 2 * 38, 58, 38, this.d, this.e - 3, 3);
        }
    }
}

