/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class b
extends aae {
    public static b a;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    public int b;
    public int c;
    private Vector i = new Vector();
    private boolean o;
    private int p;

    public final void a() {
        super.a();
        this.b();
    }

    public final void b() {
        this.i = new Vector();
        int n2 = 0;
        while (n2 < abj.ar.size()) {
            ck ck2 = (ck)abj.ar.elementAt(n2);
            ((ck)abj.ar.elementAt(n2)).b = d.h.a(ck2.a, acv.m - 44);
            this.i.addElement(ck2);
            this.c += ck2.b.length;
            ++n2;
        }
        this.h = this.c * 15 - (acv.n - 60) + 35;
        if (this.h < 0) {
            this.h = 0;
        }
        this.d = 0;
        this.e = 0;
    }

    public b() {
        this.l = new s("\u0110\u00f3ng", new bu(this));
    }

    public final void a(Graphics graphics) {
        acv.s.a(graphics);
        yi.d(graphics, 20, 20, acv.m - 40, acv.n - 60);
        d.b.a(graphics, "K\u00eanh th\u1ebf gi\u1edbi", acv.m / 2, 26, 2);
        graphics.setClip(20, 46, acv.m - 40, acv.n - 90);
        graphics.translate(20, 47);
        graphics.translate(0, -this.e);
        if (this.i.size() > 0) {
            yi.b(graphics, 4, this.b * 15 + 2, acv.m - 49, 15);
        }
        int n2 = 2;
        int n3 = 0;
        while (n3 < this.i.size()) {
            ck ck2 = (ck)this.i.elementAt(n3);
            int n4 = 0;
            while (n4 < ck2.b.length) {
                d.h.a(graphics, ck2.b[n4], 8, n2, 0);
                n2 += 15;
                ++n4;
            }
            ++n3;
        }
        acv.a(graphics);
        graphics.setClip(0, 0, acv.m, acv.n);
        super.a(graphics);
    }

    public final void c() {
        boolean bl2 = false;
        if (acv.a(20, 20, acv.m - 40, acv.n - 60)) {
            if (acv.f) {
                if (!this.o) {
                    this.p = this.e;
                    this.o = true;
                }
                if (Math.abs(acv.D - acv.k) != 0) {
                    this.d = this.p + (acv.D - acv.k);
                    if (this.d < 0) {
                        this.d = 0;
                    }
                    if (this.d > this.h) {
                        this.d = this.h;
                    }
                }
            }
            if (acv.g) {
                acv.g = false;
                this.b = (this.d + acv.k - 47) / 15;
                if (this.b > this.c - 1) {
                    this.b = this.c - 1;
                }
                if (this.b < 0) {
                    this.b = 0;
                }
                this.o = false;
            }
        }
        if (acv.c[8]) {
            acv.c[8] = false;
            ++this.b;
            if (this.b > this.c - 1) {
                this.b = this.c - 1;
            }
            bl2 = true;
        } else if (acv.c[2]) {
            acv.c[2] = false;
            --this.b;
            if (this.b < 0) {
                this.b = 0;
            }
            bl2 = true;
        }
        if (this.e != this.d) {
            this.g = this.d - this.e << 2;
            this.f += this.g;
            this.e += this.f >> 4;
            this.f &= 0xF;
            if (this.e < 0) {
                this.e = 0;
            }
            if (this.e > this.h) {
                this.e = this.h;
            }
        }
        if (bl2) {
            this.d = this.b * 15 - (acv.n - 60) / 5;
        }
        super.c();
    }

    public final void d() {
        acv.s.d();
        super.d();
    }
}

