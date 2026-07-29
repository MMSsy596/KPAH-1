/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class ic
extends di {
    private int o;
    private int p;
    private int q = 5;
    int n;
    private ap r;
    private int[] s;

    public ic(ts ts2, int n2, int n3, ap ap2) {
        int[] nArray = new int[4];
        nArray[2] = 24;
        nArray[3] = 24;
        this.s = nArray;
        this.o = acv.a(n2 - 15, n2 + 15);
        this.p = acv.a(n3 - 20, n3 + 20);
        this.r = ap2;
    }

    public final void a() {
        this.p += this.q;
        this.q += 2;
        if (this.p > this.r.cM - 15) {
            abm.b.removeElement(this);
            abm.a(this.o, this.p - 5, 30);
        }
        if (this.n != 0) {
            acv.s.a("-" + this.n, 0, this.o, this.p - 15, 1, -2);
            return;
        }
        acv.s.a("MISS", 0, this.o, this.p - 15, 1, -2);
    }

    public final void a(Graphics graphics) {
        if (yi.c(4) != null) {
            graphics.drawRegion(yi.c(4), this.s[0], this.s[1], this.s[2], this.s[3], 5, this.o, this.p, 3);
        }
    }
}

