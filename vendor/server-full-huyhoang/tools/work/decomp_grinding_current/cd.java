/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class cd
extends di {
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private ap v;
    private int w;
    private int x;
    private int y;
    private int z;

    public cd(ap ap2, int n2, int n3, int n4) {
        this.v = ap2;
        this.t = n2;
        if (n2 == 0) {
            this.d = ap2.cL - (n3 == 0 ? 100 : 20);
            this.e = ap2.cM - 100 - (n3 == 0 ? 0 : 100);
            this.n = ap2.cL + (n3 == 0 ? 0 : -20);
        } else if (n2 == 1) {
            this.d = ap2.cL + (n3 == 0 ? 100 : -10);
            this.e = ap2.cM - 100 - (n3 == 0 ? 0 : 100);
            this.n = ap2.cL + (n3 == 0 ? 0 : -10);
        } else if (n2 == 2) {
            this.d = ap2.cL + (n3 == 0 ? 100 : 10);
            this.e = ap2.cM + (n3 == 0 ? 100 : -200);
            this.n = ap2.cL + (n3 == 0 ? 0 : 10);
        } else {
            this.d = ap2.cL - (n3 == 0 ? 100 : 20);
            this.e = ap2.cM + (n3 == 0 ? 100 : -200);
            this.n = ap2.cL + (n3 == 0 ? 0 : 20);
        }
        this.o = ap2.cM;
        this.u = n3;
        this.s = yg.a(this.d - ap2.cL, ap2.cM - (ap2.cN >> 1) - this.e);
        this.p = n4;
        this.q = n4 * yg.b(this.s) >> 10;
        this.r = n4 * yg.a(this.s) >> 10;
        this.y = ap2.cN;
    }

    public final void a() {
        if (this.v != null) {
            if (this.t == 0) {
                this.n = this.v.cL + (this.u == 0 ? -4 : -20);
                this.o = this.v.cM;
            } else if (this.t == 1) {
                this.n = this.v.cL + (this.u == 0 ? 8 : -10);
                this.o = this.v.cM;
            } else if (this.t == 2) {
                this.n = this.v.cL + (this.u == 0 ? 4 : 10);
                this.o = this.v.cM;
            } else {
                this.n = this.v.cL + (this.u == 0 ? -8 : 20);
                this.o = this.v.cM;
            }
        }
        this.w = this.n - this.d;
        this.x = this.o - (this.y >> 1) - this.e;
        this.s = yg.a(this.w, this.x);
        this.q = this.p * yg.b(this.s) >> 10;
        this.r = this.p * yg.a(this.s) >> 10;
        this.d += this.q;
        this.e += this.r;
        abm.b(this.d, this.e, this.u == 0 ? 32 : 29);
        if (yi.a(this.d - 20, this.d + 20, this.n - this.y / 2, this.n + this.y / 2, this.e - 20, this.e + 20, this.o - this.y / 2, this.o + this.y / 2)) {
            abm.a(this.n, this.o - 10, this.u == 0 ? 38 : 30);
            abm.b.removeElement(this);
            if (this.v != null) {
                int n2 = this.v.J;
                this.v.l();
                acv.s.a("-" + n2, 0, (int)this.v.cL, this.v.cM + this.v.H - 15, 1, -2);
            }
        }
        if (this.u != 1) {
            if (this.t == 0) {
                this.z = 0;
                return;
            }
            if (this.t == 1) {
                this.z = 2;
                return;
            }
            if (this.t == 3) {
                this.z = 1;
                return;
            }
            this.z = 7;
        }
    }

    public final void a(Graphics graphics) {
        if (this.u == 1) {
            if (yi.c(4) != null) {
                graphics.drawRegion(yi.c(4), 0, 0, 24, 24, 5, this.d, this.e, 3);
                return;
            }
        } else if (yi.d(31) != null) {
            graphics.drawRegion(yi.d(31), 0, 0, 20, 20, this.z, this.d, this.e, 3);
        }
    }
}

