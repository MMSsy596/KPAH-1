/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class kp
extends acd {
    private int a;
    private int b;
    private int c;
    private int d;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private ap n;
    private int o;
    private int p;
    private bx q;
    private int r;
    private boolean s;

    public kp(ap ap2, ap ap3, int n2) {
        this.h = n2;
        this.n = ap3;
        this.f = ap2.cL;
        this.g = ap2.cM - 54;
        this.k = ap3.cL;
        this.l = ap3.cM;
        this.d = yg.a(this.f - ap3.cL, ap3.cM - (ap3.cN >> 1) - this.g);
        this.a = 24;
        this.b = this.a * yg.b(this.d) >> 10;
        this.c = this.a * yg.a(this.d) >> 10;
        this.m = ap3.cN;
        if (n2 < 6) {
            this.q = new bx(this.k, this.l, n2 == 3 ? 1 : 0, 0, 0, 0, false, false);
            abm.a.addElement(this.q);
        }
        this.g = ap2.cM - 31;
        if (ap2.D == 2) {
            this.f = ap2.cL - 30;
        } else if (ap2.D == 3) {
            this.f = ap2.cL + 30;
        }
        int n3 = yb.b(yg.a(ap3.cL - this.f, -(ap3.cM + (ap3.cN >> 1) - this.g)));
        this.o = yb.d[n3];
        this.p = yb.c[n3];
    }

    public final void a(Graphics graphics) {
        if (!this.s) {
            if (yi.c(this.h) != null && this.h != 6 && this.h != 7) {
                graphics.drawRegion(yi.c(this.h), 0, this.o * yb.b[1][this.h], (int)yb.b[0][this.h], (int)yb.b[1][this.h], this.p, this.f, this.g, 3);
                return;
            }
        } else if (yi.d(38) != null) {
            graphics.drawRegion(yi.d(38), 0, this.r * 27, 27, 27, 0, this.k, this.l, 33);
        }
    }

    public final void a(int n2, int n3, int n4, int n5, byte by2, ap ap2, ap ap3) {
    }

    public final void a(int n2) {
    }

    public final void a() {
        if (this.n != null) {
            this.k = this.n.cL;
            this.l = this.n.cM;
        }
        this.i = this.k - this.f;
        this.j = this.l - (this.m >> 1) - this.g;
        this.d = yg.a(this.i, this.j);
        this.b = this.a * yg.b(this.d) >> 10;
        this.c = this.a * yg.a(this.d) >> 10;
        this.f += this.b;
        this.g += this.c;
        if (!this.s) {
            if (this.h < 6) {
                abm.a(this.f, this.g, this.h == 3 ? 8 : 6);
            } else if (this.h == 6) {
                abm.a(this.f, this.g, 46);
            }
        }
        if (yi.a(this.f - 20, this.f + 20, this.k - this.m / 2, this.k + this.m / 2, this.g - 20, this.g + 20, this.l - this.m / 2, this.l + this.m / 2)) {
            if (!this.s) {
                if (this.h == 3) {
                    kp.a(this.n, 0, 0, 18);
                    kp.a(this.n, 1, 0, 24);
                    kp.a(this.n, 2, 0, 28);
                    kp.a(this.n, 3, 0, 32);
                } else if (this.h < 6) {
                    abm.a(this.k, this.l - 10, 30);
                    if (acv.s.r.contains(this)) {
                        acv.s.r.removeElement(this);
                    }
                    if (abj.q.contains(this)) {
                        abj.q.removeElement(this);
                    }
                    this.n.l();
                    kp.a(this.n, 0, 1, 40);
                    kp.a(this.n, 1, 1, 40);
                    kp.a(this.n, 2, 1, 40);
                    kp.a(this.n, 3, 1, 40);
                } else if (this.h == 6) {
                    if (acv.s.r.contains(this)) {
                        acv.s.r.removeElement(this);
                    }
                    if (abj.q.contains(this)) {
                        abj.q.removeElement(this);
                    }
                    this.n.l();
                }
            }
            this.s = true;
        }
        if (this.s && acv.l % 2 == 0) {
            ++this.r;
            if (this.r > 4) {
                if (acv.s.r.contains(this)) {
                    acv.s.r.removeElement(this);
                }
                if (abj.q.contains(this)) {
                    abj.q.removeElement(this);
                }
                this.s = false;
                this.r = 0;
            }
        }
    }

    private static void a(ap object, int n2, int n3, int n4) {
        object = new cd((ap)object, n2, n3, n4);
        abm.b.addElement(object);
    }
}

