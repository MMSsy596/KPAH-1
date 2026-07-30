/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class pn
extends di {
    private int n;
    private int o = 0;
    private int p = 0;
    private int[] q;
    private int[] r;
    private int s;
    private int t;
    private int u;
    private byte v;

    public pn(int n2, int n3, int n4, int n5) {
        this.d = n2;
        this.e = n3;
        this.n = n4;
        this.o = n5;
        pn pn2 = this;
        this.s = pn2.n - pn2.d;
        pn2.t = pn2.o - pn2.e;
        yg.a(pn2.s, pn2.t);
        n3 = (Math.abs(pn2.s) + Math.abs(pn2.t)) / 20;
        if (n3 < 2) {
            n3 = 2;
        }
        pn2.q = new int[n3];
        pn2.r = new int[n3];
        n4 = 0;
        while (n4 < n3) {
            pn2.q[n4] = pn2.d + n4 * pn2.s / n3;
            pn2.r[n4] = pn2.e + n4 * pn2.t / n3;
            ++n4;
        }
        this.u = 10;
    }

    public final void a(Graphics graphics) {
        if (yi.c(10) != null) {
            graphics.drawRegion(yi.c(10), 0, this.v * yb.b[1][10], (int)yb.b[0][10], (int)yb.b[1][10], 0, this.q[this.p], this.r[this.p], 3);
        }
    }

    public final void a() {
        if (this.u >= 0) {
            --this.u;
        }
        this.v = (byte)(this.v + 1);
        if (this.v >= 3) {
            this.v = 0;
        }
        if (this.u < 0) {
            if (this.p < this.q.length) {
                ++this.p;
            }
            if (this.p >= this.q.length) {
                this.p = this.q.length - 1;
                this.q[this.p] = this.n;
                this.r[this.p] = this.o;
                this.i = true;
            }
        }
    }
}

