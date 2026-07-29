/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class bp
extends di {
    private int[] n;
    private int[] o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private byte x;
    private byte y;
    private int z;
    private boolean A;
    private short B;
    private ap C = null;

    public bp(int n2, int n3, int n4, int n5, ap ap2, int n6) {
        this.C = ap2;
        this.d = n2;
        this.e = n3 + 30;
        this.u = n4;
        this.v = n5;
        this.w = 15;
        this.t = n3 - 60;
        this.z = n6;
        bp bp2 = this;
        this.p = bp2.u - bp2.d;
        bp2.q = bp2.v - bp2.t;
        n3 = (Math.abs(bp2.p) + Math.abs(bp2.q)) / 20;
        if (n3 < 2) {
            n3 = 2;
        }
        bp2.n = new int[n3];
        bp2.o = new int[n3];
        n4 = 0;
        while (n4 < n3) {
            bp2.n[n4] = bp2.d + n4 * bp2.p / n3;
            bp2.o[n4] = bp2.t + n4 * bp2.q / n3;
            ++n4;
        }
        this.B = (short)55;
    }

    public final void a(Graphics graphics) {
        if (this.z < 0) {
            Image image;
            if (this.e > this.t && (image = yi.d(this.B)) != null) {
                graphics.drawRegion(image, 0, this.y * image.getHeight() / 2, image.getWidth(), image.getHeight() / 2, 0, this.d, this.e, 3);
            }
            if (this.A && (image = yi.c(this.w)) != null) {
                graphics.drawRegion(image, 0, this.x * yb.b[1][this.w], (int)yb.b[0][this.w], (int)yb.b[1][this.w], 0, this.n[this.r], this.o[this.r], 3);
            }
        }
    }

    public final void a() {
        this.x = (byte)((this.x + 1) % 3);
        this.y = (byte)((this.y + 1) % 2);
        if (this.z >= 0) {
            --this.z;
        }
        if (this.z < 0) {
            if (this.e > this.t) {
                this.e -= 10;
            }
            if (this.e <= this.t && this.s <= 6) {
                ++this.s;
            }
            if (this.s == 2) {
                abm.a(this.d, this.e, 54);
            }
            if (this.s >= 6 && !this.A) {
                this.A = true;
            }
            if (this.s > 6 && this.r < this.n.length) {
                ++this.r;
            }
            if (this.r >= this.n.length) {
                this.r = this.n.length - 1;
                this.n[this.r] = this.u;
                this.o[this.r] = this.v;
                abm.b(this.u, this.v, 53);
                this.i = true;
                if (this.C != null && this.C.J != 0 && this.C.J != 2000000) {
                    acv.s.a("-" + this.C.J, 0, (int)this.C.cL, this.C.cM + this.C.H - 15, 1, -2);
                }
            }
        }
    }
}

