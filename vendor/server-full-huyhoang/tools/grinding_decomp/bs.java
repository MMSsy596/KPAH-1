/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class bs
extends aae {
    private static bs b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g = 10000;
    String[] a = null;
    private int[] h;
    private short i;
    private short o = (short)20;
    private short p;
    private short q;

    public static bs e() {
        if (b == null) {
            b = new bs();
            return b;
        }
        return b;
    }

    public bs() {
        this.i = (short)20;
        this.p = (short)(acv.m - (this.i << 1));
        this.q = (short)(acv.n - (this.o << 1) - aae.an);
        this.l = new s("\u0110\u00f3ng", new su(this));
        go.a().q(0);
        acv.h();
    }

    public final void a(String string) {
        this.a = d.j[0].a(string, acv.m - 30);
        int n2 = 0;
        this.h = new int[this.a.length];
        int n3 = 0;
        while (n3 < this.a.length) {
            if (this.a[n3].startsWith("x0")) {
                this.a[n3] = this.a[n3].substring(3);
                n2 = 0;
            } else if (this.a[n3].startsWith("x1")) {
                this.a[n3] = this.a[n3].substring(3);
                n2 = 1;
            }
            this.h[n3] = n2;
            ++n3;
        }
        this.g = this.a.length * 13 - (this.q - 35);
        acv.g();
    }

    public final boolean a(int n2) {
        return super.a(n2);
    }

    public final void a(Graphics graphics) {
        acv.s.a(graphics);
        graphics.translate((int)this.i, (int)this.o);
        yi.d(graphics, 0, 0, this.p, this.q);
        d.b.a(graphics, "H\u01b0\u1edbng d\u1eabn", this.p / 2, 6, 2);
        graphics.translate(0, 30);
        graphics.setClip(0, -2, this.p - 4, this.q - 32);
        graphics.translate(0, -this.d);
        if (this.a != null) {
            int n2 = this.d / 13;
            int n3 = n2 + (this.q - 35) / 13 + 2;
            if (n3 > this.a.length) {
                n3 = this.a.length;
            }
            while (n2 < n3) {
                d d2 = this.h[n2] == 0 ? d.h : d.j[0];
                d2.a(graphics, this.a[n2], 7, n2 * 13, 0);
                ++n2;
            }
        }
        super.a(graphics);
    }

    public final void a() {
        super.a();
    }

    public final void d() {
        boolean bl2 = false;
        bs bs2 = this;
        if (bs2.d != bs2.c) {
            bs2.f = bs2.c - bs2.d << 2;
            bs2.e += bs2.f;
            bs2.d += bs2.e >> 4;
            bs2.e &= 0xF;
        }
        if (acv.e[8]) {
            this.c += 13;
            bl2 = true;
        }
        if (acv.e[2]) {
            this.c -= 13;
            bl2 = true;
        }
        if (bl2) {
            if (this.c > this.g) {
                this.c = this.g;
            }
            if (this.c < 0) {
                this.c = 0;
            }
        }
        super.d();
    }

    static void a(bs bs2) {
        b = null;
    }
}

