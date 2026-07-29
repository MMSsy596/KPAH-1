/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class ef
extends acd {
    private byte a = (byte)6;
    private byte b;
    private byte c;
    private byte d = 0;
    private short f;
    private short g;
    private short h;
    private short i;
    private short j;
    private short k;
    private short l;
    private short m;
    private boolean n;
    private int o;

    public ef(byte by2) {
    }

    public final void a() {
        int n2 = this.c * yg.b(this.l) >> 10;
        int n3 = -(this.c * yg.a(this.l)) >> 10;
        this.f = (short)(this.f + n2);
        this.g = (short)(this.g + n3);
        n2 = this.c * yg.b(this.m) >> 10;
        n3 = -(this.c * yg.a(this.m)) >> 10;
        this.h = (short)(this.h + n2);
        this.i = (short)(this.i + n3);
        if (this.d == 0 && yg.a(this.j, (int)this.k, (int)this.f, (int)this.g) <= this.c << 1) {
            this.f = this.h = this.j;
            this.g = this.i = this.k;
            this.c = 0;
            if (!this.n) {
                this.d = (byte)(yi.m(30) + 20);
                acv.s.r.addElement(this);
                acv.s.a(this.f, (short)(this.g + 10));
            } else {
                if (this.o != 0) {
                    acv.s.a("-" + this.o, 0, (int)this.f, this.g - 15, 1, -2);
                }
                abm.a(this.f, this.g - 20, 12);
            }
            abj.q.removeElement(this);
        } else if (yg.a(this.j, (int)this.k, (int)this.f, (int)this.g) > 1000) {
            abj.q.removeElement(this);
        }
        if (this.d > 0) {
            this.d = (byte)(this.d - 1);
            if (this.d == 0) {
                acv.s.r.removeElement(this);
            }
        }
    }

    public final void a(Graphics graphics) {
        if (yi.c(this.a) != null) {
            graphics.drawImage(yi.j, (int)this.h, (int)this.i, 17);
            Image image = yi.c(this.a);
            if (image != null) {
                graphics.drawRegion(image, 0, yb.d[this.b] * yb.b[1][this.a], (int)yb.b[0][this.a], (int)yb.b[1][this.a], yb.c[this.b], (int)this.f, (int)this.g, 3);
            }
        }
    }

    public final void a(int n2, int n3, int n4, int n5, int n6, byte by2, boolean bl2) {
        this.o = n4;
        this.n = bl2;
        this.c = by2;
        n4 = n5 - n2;
        by2 = (byte)(n6 - n3);
        this.f = this.h = (short)n2;
        this.g = (short)n3;
        this.j = (short)n5;
        this.k = (short)n6;
        this.i = (short)(n3 + 30);
        this.b = (byte)yb.b(yg.a(n4, -by2));
        this.l = (short)yg.a(n5 - n2, -(n6 - n3));
        this.m = (short)yg.a(n5 - this.h, -(n6 - this.i));
    }

    public final void a(int n2, int n3, int n4, int n5, byte by2, ap ap2, ap ap3) {
    }

    public final void a(int n2) {
    }
}

