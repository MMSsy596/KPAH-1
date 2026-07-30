/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class km
extends aae {
    private static km e;
    public aae a;
    private mm f = new mm();
    public static Vector b;
    private static int g;
    private static int h;
    private static int i;
    private static int o;
    public static int c;
    private static int p;
    private static int q;
    public static short d;
    private int r = 20;

    static {
        b = new Vector();
        p = 8;
        d = (short)32;
    }

    public static km e() {
        if (e == null) {
            e = new km();
            return e;
        }
        return e;
    }

    public final void f() {
        this.f.a();
        b = new Vector();
        d = (short)32;
        c = 0;
    }

    public final void a(Graphics graphics) {
        if (this.a != null) {
            this.a.a(graphics);
        }
        acv.a(graphics);
        yi.c(graphics, g, h, i, o);
        int n2 = d;
        this.f.a(n2 / p, this.r, g + 5, h + 5, i, o - 5, true, 8);
        this.f.a(graphics, g + 5, h + 5, i, o - 3);
        n2 = c / p;
        int n3 = c - n2 * p;
        graphics.setColor(10595790);
        graphics.fillRect(g + n3 * this.r + 5, h + n2 * this.r + 5, this.r, this.r);
        n2 = 0;
        n3 = 0;
        int n4 = 0;
        while (n4 < b.size()) {
            aag aag2 = (aag)b.elementAt(n4);
            if (aag2.b != 1) break;
            aag2.a(graphics, g + n2 * this.r + this.r / 2 + 5, h + n3 * this.r + this.r / 2 + 5);
            byte by2 = (byte)(n2 + 1);
            n2 = by2;
            if (by2 >= p) {
                n2 = 0;
                n3 = (byte)(n3 + 1);
            }
            ++n4;
        }
        n4 = 0;
        while (n4 < q) {
            int n5 = 0;
            while (n5 < p) {
                graphics.setColor(0x848282);
                graphics.drawRect(g + n5 * this.r + 5, h + n4 * this.r + 5, this.r, this.r);
                ++n5;
            }
            ++n4;
        }
        super.a(graphics);
    }

    public final void a() {
        super.a();
        this.k = new s("Ch\u1ecdn", new kh(this));
        this.j = new s("Quay s\u1ed1", new kf(this));
        this.l = new s("\u0110\u00f3ng", new kg(this));
        this.f.a();
    }

    protected static void g() {
        if (c < b.size() && c >= 0) {
            aag aag2 = (aag)b.elementAt(c);
            if (aag2.b == 1) {
                String[] stringArray;
                if (aag2.e == null) {
                    String[] stringArray2 = new String[1];
                    stringArray = stringArray2;
                    stringArray2[0] = aag2.d;
                } else {
                    stringArray = aag2.e;
                }
                acv.a(stringArray);
            }
        }
    }

    public km() {
        g = acv.m / 2 - 80 - 10;
        h = acv.p - 40 - 10;
        i = 170;
        o = 90;
    }

    public final void d() {
        if (this.a != null) {
            this.a.d();
        }
        if (acv.w == null) {
            aca aca2 = this.f.b();
            if (aca2.a || aca2.c) {
                c = aca2.b;
            }
            q = d / p;
            this.f.c();
        }
        super.d();
    }

    public final void c() {
        if (acv.c[2]) {
            acv.c[2] = false;
            if (c >= 0 && c < p) {
                c = 0;
            } else if (c - p >= 0) {
                c -= p;
            }
            this.f.a(c / p * this.r);
        } else if (acv.c[8]) {
            acv.c[8] = false;
            if (c + p <= d - 1) {
                c += p;
            }
            this.f.a(c / p * this.r);
        } else if (acv.c[4]) {
            acv.c[4] = false;
            if (--c < 0) {
                c = d - 1;
            }
            this.f.a(c / p * this.r);
        } else if (acv.c[6]) {
            acv.c[6] = false;
            if (++c > d) {
                c = 0;
            }
            this.f.a(c / p * this.r);
        }
        super.c();
    }
}

