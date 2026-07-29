/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class pp
extends hc {
    private String[] b;
    private static final int[] c;
    private int d = 0;
    public boolean a = true;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private static int o;
    private static int p;
    private static int q;
    private static int r;
    private static int s;
    private static int t;
    private String[] u;
    private boolean v;
    private int w;

    static {
        int[] nArray = new int[4];
        nArray[1] = 7;
        nArray[2] = 3;
        nArray[3] = 6;
        c = nArray;
        t = 0;
    }

    public final void a(String string, s s2, s s3, s s4) {
        this.b = d.h.a(string, 100);
        this.j = s2;
        this.k = s3;
        this.l = s4;
        this.a();
        this.g = t;
        this.v = false;
    }

    public final void a(String[] stringArray, s s2, s s3, s s4) {
        this.b = stringArray;
        this.j = s2;
        this.k = null;
        this.l = null;
        this.a();
        this.g = t;
        this.v = false;
    }

    public final void a(String string, String[] stringArray, s s2, s s3, s s4) {
        this.b = d.h.a(string, acv.m - 60);
        this.j = s2;
        this.k = s3;
        this.l = s4;
        this.u = stringArray;
        this.a();
        this.g = t;
        this.w = stringArray.length * 20 + 20 * this.b.length;
        this.v = true;
    }

    private void a() {
        this.h = 90;
        this.f = acv.p - 42;
        this.e = 15;
        if (this.a) {
            this.h -= 50;
            this.e += 50;
        }
        this.i = this.h;
        if (this.b.length * 13 < this.h) {
            this.e += this.h / 2 - this.b.length * 13 / 2;
            this.i -= this.h - this.b.length * 13;
        }
        if ((s = this.b.length * 13 - this.h) < 0) {
            s = 0;
        }
        t = this.h / 2 / 13;
    }

    public final void a(Graphics graphics) {
        acv.a(graphics);
        graphics.translate(0, this.f);
        if (this.v) {
            Graphics graphics2 = graphics;
            pp pp2 = this;
            acv.a(graphics2);
            int n2 = acv.p - pp2.w / 2 - 20;
            if (n2 <= 10) {
                n2 = 10;
            }
            yi.c(graphics2, 20, n2, acv.m - 40, pp2.w + 20);
            int n3 = 0;
            while (n3 < pp2.u.length) {
                d.h.a(graphics2, pp2.u[n3], acv.o, n3 * 20 + 10 + n2, 2);
                ++n3;
            }
            n3 = 0;
            while (n3 < pp2.b.length) {
                d.j[0].a(graphics2, pp2.b[n3], acv.o, n3 * 20 + (pp2.u.length - 1) * 20 + 42 + n2 - 4, 2);
                ++n3;
            }
            graphics2.translate(0, pp2.f);
        } else {
            yi.c(graphics, acv.o - 70, 12, 140, 100);
            if (this.a) {
                graphics.drawRegion(yi.z, 0, 0, 25, 25, c[this.d], acv.o, 47, 3);
            }
            graphics.translate(0, this.e);
            graphics.setClip(0, 0, acv.m, this.i);
            graphics.translate(0, -p);
            int n4 = 0;
            while (n4 < this.b.length) {
                d.h.a(graphics, this.b[n4], acv.o, n4 * 13, 2);
                ++n4;
            }
        }
        super.a(graphics);
    }

    public final void b() {
        ++this.d;
        if (this.d > 3) {
            this.d = 0;
        }
        boolean bl2 = false;
        if (acv.e[2]) {
            --this.g;
            if (this.g < t) {
                this.g = t;
            }
            bl2 = true;
        } else if (acv.e[8]) {
            bl2 = true;
            if (p < s) {
                ++this.g;
            }
            if (this.g > this.b.length - t) {
                this.g = this.b.length - t;
            }
        }
        if (bl2) {
            o = this.g * 13 - this.h / 2;
            if (o < 0) {
                o = 0;
            }
            if (o > s) {
                o = s;
            }
        }
        if (p != o) {
            r = o - p << 2;
            p += (q += r) >> 4;
            q &= 0xF;
        }
        super.b();
    }
}

