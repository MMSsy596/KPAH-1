/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class rq
extends di {
    private int n;
    private int o;
    private int p;
    private boolean q;

    public rq(int n2, int n3, ap ap2, Vector vector) {
        new Vector();
        this.f = 2;
        this.d = n2;
        this.e = n3;
        int n4 = 0;
        while (n4 < 20) {
            byte by2 = (byte)(20 - yi.m(40));
            byte by3 = (byte)(20 - yi.m(40));
            this.n = n2 + by2;
            this.o = n3 + by3;
            ++n4;
        }
    }

    public final void a() {
        if (this.n < this.d) {
            this.n += this.p;
        }
        if (this.n >= this.d) {
            this.n -= this.p;
        }
        if (this.o < this.e) {
            this.o += this.p;
        }
        if (this.o > this.e) {
            this.o -= this.p;
        }
        ++this.p;
        if (Math.abs(this.d - this.n) <= 5 && Math.abs(this.e - this.o) <= 5) {
            this.q = true;
            this.p = 0;
            abm.b.removeElement(this);
        }
    }

    public final void a(Graphics graphics) {
        int n2 = 0;
        while (n2 < 5) {
            if (!this.q) {
                graphics.drawRegion(hw.ab, 0, 7, 7, 7, 0, this.n, this.o, 0);
                graphics.drawRegion(hw.ab, 0, 14, 7, 7, 0, this.n + 7 - yi.m(15), this.o + 7 - yi.m(15), 0);
            }
            ++n2;
        }
    }
}

