/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class acb
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

    public acb(int n2, int n3, ap ap2, ap ap3, int n4, int n5, int n6, int n7, int n8, boolean bl2) {
        int[] nArray = new int[6];
        nArray[1] = 2;
        nArray[2] = 1;
        nArray[3] = 3;
        nArray[4] = 7;
        nArray[5] = 4;
        this.d = n2;
        this.f = n3;
        if (ap2 != null) {
            this.g = ap2.cL - n5;
            this.h = ap2.cM - n6;
        } else {
            this.g = n7;
            this.h = n8;
        }
        this.a = n4;
        if (bl2 && ap3 != null) {
            ap3.a_();
            abm.a(ap3.cL, ap3.cM + ap3.H - 10, 11);
            acv.s.a("-" + ap3.J, 0, (int)ap3.cL, ap3.cM + ap3.H - 15, 1, -2);
        }
        this.j = n2;
    }

    public final void a(Graphics graphics) {
        try {
            Image image;
            if (this.a != 0 && (image = yi.d(34)) != null) {
                graphics.drawRegion(image, 0, this.k * 15, 24, 15, 0, this.b, this.c, 3);
                return;
            }
        }
        catch (Exception exception) {}
    }

    public final void a(int n2, int n3, int n4, int n5, byte by2, ap ap2, ap ap3) {
    }

    public final void a(int n2) {
    }

    public final void a() {
        this.b = this.g + yg.b(this.d) * this.f / 1024;
        this.c = this.h + yg.a(this.d) * this.f / 1024;
        if (this.a == 0) {
            this.j += 30;
            if (this.j > 360) {
                this.j -= 360;
            }
            if (this.d != this.j) {
                this.d += 10;
                if (this.d > 360) {
                    this.d -= 360;
                }
            }
            if (this.i < 15) {
                this.i += 2;
            }
            abm.a(this.b, this.c, 33);
        } else {
            if (this.i < 24) {
                this.i += 8;
            }
            if (acv.l % 4 == 0) {
                this.k = this.k < 1 ? ++this.k : 0;
            }
        }
        this.f += this.i;
        if (this.b < abj.h - acv.m / 2 || this.b > abj.h + acv.m + acv.m / 2 || this.c < abj.i - acv.n / 2 || this.c > abj.i + acv.n + acv.n / 2) {
            if (acv.s.r.contains(this)) {
                acv.s.r.removeElement(this);
            }
            if (abj.q.contains(this)) {
                abj.q.removeElement(this);
            }
        }
    }
}

