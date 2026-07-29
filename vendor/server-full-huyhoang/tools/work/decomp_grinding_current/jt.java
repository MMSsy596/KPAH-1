/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class jt
extends hc {
    private static jt a;
    private String[] b;
    private String c;
    private int d = 50;
    private int e = 20;
    private int f = acv.m - 100;
    private int g = acv.n - 40 - aae.an;
    private static int h;
    private static int i;
    private static int o;
    private static int p;
    private static int q;
    private static int r;
    private static int s;

    static {
        r = 0;
        s = 0;
    }

    public static jt a() {
        if (a == null) {
            a = new jt();
            return a;
        }
        return a;
    }

    public jt() {
        this.l = new s("\u0110\u00f3ng", new bl(this));
        s = (this.g - 30) / 2 / 14;
    }

    public final void a(String string, String string2) {
        this.b = d.h.a(string, this.f - 15);
        this.c = string2;
        q = this.b.length * 14 - (this.g - 25) + 15;
        if (q < 0) {
            q = 0;
        }
        h = 0;
        i = 0;
        r = s;
        acv.w = this;
    }

    public final void a(Graphics graphics) {
        acv.a(graphics);
        yi.d(graphics, this.d, this.e, this.f, this.g);
        d.c.a(graphics, this.c, this.d + this.f / 2, this.e + 2, 2);
        graphics.translate(this.d, this.e + 25);
        graphics.setClip(0, 0, this.f, this.g - 30);
        graphics.translate(0, -i);
        int n2 = i / 14;
        int n3 = n2 + this.g / 14 + 1;
        if (n3 >= this.b.length) {
            n3 = this.b.length;
        }
        while (n2 < n3) {
            d.h.a(graphics, this.b[n2], 7, 5 + n2 * 14, 0);
            ++n2;
        }
        super.a(graphics);
    }

    public final void b() {
        boolean bl2 = false;
        if (acv.e[2]) {
            if (--r < s) {
                r = s;
            }
            bl2 = true;
        } else if (acv.e[8]) {
            bl2 = true;
            if (i < q) {
                ++r;
            }
            if (r > this.b.length - s + 1) {
                r = this.b.length - s + 1;
            }
        }
        if (bl2) {
            h = r * 14 - (this.g - 25) / 2;
            if (h < 0) {
                h = 0;
            }
            if (h > q) {
                h = q;
            }
        }
        if (i != h) {
            p = h - i << 2;
            i += (o += p) >> 4;
            o &= 0xF;
        }
        super.c();
    }
}

