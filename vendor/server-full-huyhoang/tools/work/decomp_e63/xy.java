/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class xy
extends aae {
    private static xy c;
    private static int d;
    private static int e;
    private static int f;
    private static int g;
    private static int h;
    private int i;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    public int a;
    private aae v;
    public Vector b;
    private String w;

    public static xy e() {
        if (c == null) {
            c = new xy();
            return c;
        }
        return c;
    }

    public final void a() {
        this.v = acv.q;
        super.a();
    }

    public xy() {
        this.l = new s("\u0110\u00f3ng", new eg(this));
        this.k = new s("Ch\u1ecdn", new eh(this));
    }

    public final void a(int n2, int n3, int n4, int n5, int n6) {
        this.i = 20;
        this.o = 20;
        this.r = n4;
        this.s = n5;
        this.t = n6;
        this.u = (this.r - 6) / n6;
        this.p = this.q = (this.r - 6 - this.u * n6) / 2;
    }

    public final void a(Vector vector, String string) {
        this.w = string;
        this.b = vector;
        int n2 = 0;
        if (vector.size() % this.t != 0) {
            n2 = 1;
        }
        if ((h = (vector.size() / this.t + n2) * this.u - (this.s - 32)) < 0) {
            h = 0;
        }
    }

    public final void d() {
        if (e != d) {
            g = d - e << 2;
            e += (f += g) >> 4;
            f &= 0xF;
        }
    }

    public final void c() {
        boolean bl2 = false;
        if (acv.b(2)) {
            bl2 = true;
            if (this.a / this.t > 0) {
                this.a -= this.t;
            }
        } else if (acv.b(4)) {
            bl2 = true;
            --this.a;
            if (this.a < 0) {
                this.a = 0;
            }
        } else if (acv.b(6)) {
            bl2 = true;
            ++this.a;
            if (this.a >= this.b.size()) {
                this.a = this.b.size() - 1;
            }
        } else if (acv.b(8)) {
            bl2 = true;
            if (this.a / this.t < this.b.size() / this.t && this.a + this.t < this.b.size()) {
                this.a += this.t;
            }
        }
        if (bl2) {
            d = this.a / this.t * this.u - (this.s - 31) / 2;
            if (d < 0) {
                d = 0;
            }
            if (d > h) {
                d = h;
            }
        }
        super.c();
    }

    public final void a(Graphics graphics) {
        int n2;
        this.v.a(graphics);
        acv.a(graphics);
        yi.d(graphics, this.i, this.o, this.r, this.s);
        d.c.a(graphics, this.w, this.i + this.r / 2, this.o + 2, 2);
        graphics.translate(this.i + 3 + this.p, this.o + 26 + this.q);
        graphics.setClip(0, 0, this.r - (this.p << 1), this.s - 30 - this.q);
        graphics.translate(0, -e);
        yi.b(graphics, this.a % this.t * this.u, this.a / this.t * this.u, this.u, this.u);
        int n3 = e / this.u * this.t;
        if (n3 <= 0) {
            n3 = 0;
        }
        if ((n2 = n3 + ((this.s - 31) / this.u + 2) * this.t) > this.b.size()) {
            n2 = this.b.size();
        }
        while (n3 < n2) {
            s s2 = (s)this.b.elementAt(n3);
            if (s2 != null) {
                graphics.setColor(yi.W[0]);
                graphics.drawRect(n3 % this.t * this.u, n3 / this.t * this.u, this.u, this.u);
                s2.a(graphics, n3 % this.t * this.u + this.u / 2, n3 / this.t * this.u + this.u / 2);
            }
            ++n3;
        }
        super.a(graphics);
    }

    static aae a(xy xy2) {
        return xy2.v;
    }

    static void a(xy xy2, String string) {
        xy2.w = null;
    }

    static void a(xy xy2, aae aae2) {
        xy2.v = null;
    }
}

