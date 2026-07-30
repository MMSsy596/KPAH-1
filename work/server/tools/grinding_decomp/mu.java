/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class mu
extends acd {
    private int a;
    private int b;
    private int c;
    private int d;
    private int f;
    private int g;
    private int h;
    private boolean i;

    public mu(ap ap2, ap ap3, int n2) {
        this.a = ap2.cL;
        this.b = ap2.cM;
        this.g = n2;
        int n3 = acv.a(acv.t.nextInt(2));
        this.h = acv.a(acv.t.nextInt(3)) + 1;
        this.i = n3 == 0;
        this.c = this.i ? 0 : 360;
        this.d = this.c;
        if (ap3 != null) {
            int n4 = ap3.J;
            ap3.a_();
            abm.a(ap3.cL, ap3.cM + ap3.H - 10, 11);
            acv.s.a("-" + n4, 0, (int)ap3.cL, ap3.cM + ap3.H - 15, 1, -2);
        }
    }

    public final void a(Graphics graphics) {
    }

    public final void a(int n2, int n3, int n4, int n5, byte by2, ap ap2, ap ap3) {
    }

    public final void a(int n2) {
    }

    public final void a() {
        if (this.i) {
            this.c += 30;
            if (this.c > 360) {
                this.c -= 360;
            }
            if (this.d != this.c) {
                this.d += this.g;
                if (this.d > 360) {
                    this.d -= 360;
                    ++this.f;
                }
            }
        } else {
            this.c -= 30;
            if (this.c < 0) {
                this.c += 360;
            }
            if (this.d != this.c) {
                this.d -= this.g;
                if (this.d < 0) {
                    this.d += 360;
                    ++this.f;
                }
            }
        }
        if (this.f >= this.h) {
            if (abj.q.contains(this)) {
                abj.q.removeElement(this);
            }
            this.f = 0;
        }
        if (this.d % 30 == 0) {
            acv.s.a(this.d, 10, null, null, 1, false, 0, 0, this.a, this.b, true);
        }
    }
}

