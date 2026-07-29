/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class zt
extends aae {
    private static zt c;
    private Vector d;
    private short e;
    private short f;
    private aae g;
    private int h = 0;
    private int i = -1;
    private short o = (short)-1;
    private String p;
    private String q = "";
    private static int r;
    private static int s;
    private static int t;
    private static int u;
    private static int v;
    private static int w;
    private static int x;
    int a = 0;
    int b = 1;

    static {
        w = 0;
        x = 0;
    }

    public static zt e() {
        if (c == null) {
            c = new zt();
            return c;
        }
        return c;
    }

    public final void a() {
        if (this.g == null) {
            this.g = acv.q;
        }
        super.a();
    }

    public zt() {
        this.l = new s("\u0110\u00f3ng", new o(this));
        this.j = new s("Menu", new r(this));
        this.e = (short)150;
        this.f = (short)170;
        x = (this.f - 30) / 2 / 14;
    }

    public final void a(String stringArray, String string) {
        this.p = stringArray;
        this.h = 2;
        this.d = new Vector();
        stringArray = d.j[0].a(string, this.e - 15);
        int n2 = 0;
        while (n2 < stringArray.length) {
            this.d.addElement(stringArray[n2]);
            ++n2;
        }
        this.f();
    }

    private void f() {
        v = this.d.size() * 14 - (this.f - 25) + 20;
        if (v < 0) {
            v = 0;
        }
        r = 0;
        s = 0;
        w = x;
    }

    public final void a(zy stringArray) {
        this.p = stringArray.b.toUpperCase();
        this.h = 0;
        this.o = stringArray.a;
        this.d = new Vector();
        this.q = stringArray.e;
        this.d.addElement("Bang ch\u1ee7: " + stringArray.c);
        this.d.addElement("C\u1ea5p \u0111\u1ed9: " + stringArray.g);
        this.d.addElement("Xu: " + acv.a(stringArray.i));
        this.d.addElement("Th\u00e0nh vi\u00ean: " + stringArray.h);
        this.d.addElement("\u0110i\u1ec3m c\u1ed1ng hi\u1ebfn: " + stringArray.l);
        this.d.addElement("\u0110i\u1ec3m kinh ngi\u1ec7m: " + stringArray.j);
        this.d.addElement("Th\u00e0nh l\u1eadp: " + stringArray.d);
        this.i = stringArray.m;
        if (stringArray.k) {
            stringArray = d.j[0].a(stringArray.f, this.e - 15);
            int n2 = 0;
            while (n2 < stringArray.length) {
                this.d.addElement(stringArray[n2]);
                ++n2;
            }
        }
        this.f();
    }

    public final void c() {
        boolean bl2 = false;
        if (acv.e[2]) {
            if (--w < x) {
                w = x;
            }
            bl2 = true;
        } else if (acv.e[8]) {
            bl2 = true;
            if (s < v) {
                ++w;
            }
            if (w > this.d.size() - x + 1) {
                w = this.d.size() - x + 1;
            }
        }
        if (bl2) {
            r = w * 14 - (this.f - 25) / 2;
            if (r < 0) {
                r = 0;
            }
            if (r > v) {
                r = v;
            }
        }
        if (s != r) {
            u = r - s << 2;
            s += (t += u) >> 4;
            t &= 0xF;
        }
        super.c();
        if (this.g != null) {
            this.g.d();
        }
    }

    public final void a(Graphics graphics) {
        if (this.g != null) {
            this.g.a(graphics);
        }
        acv.a(graphics);
        graphics.translate(acv.o - this.e / 2, acv.p - this.f / 2);
        yi.d(graphics, 0, 0, this.e, this.f);
        graphics.setClip(0, 0, (int)this.e, (int)this.f);
        d.c.a(graphics, this.p, this.e >> 1, 4, 2);
        graphics.setClip(0, 27, (int)this.e, this.f - 30);
        graphics.translate(0, -s);
        if (this.h == 2) {
            int n2;
            Graphics graphics2 = graphics;
            zt zt2 = this;
            int n3 = 34;
            if (zt2.o != -1) {
                ko.a(graphics2, (short)(zt2.o + 1000), zt2.e / 2, 34);
                n3 += 6;
            }
            if (zt2.h == 0 && !zt2.q.equals("")) {
                n2 = d.h.a(zt2.q);
                if (n2 > zt2.e) {
                    zt2.a = zt2.b == 1 ? --zt2.a : ++zt2.a;
                    if (Math.abs(zt2.a) > n2 / 2 - zt2.e / 2 + 10) {
                        zt2.b = -zt2.b;
                    }
                }
                graphics2.setClip(3, 0, zt2.e - 6, (int)zt2.f);
                d.b.a(graphics2, zt2.q, (zt2.e >> 1) + zt2.a, n3, 2);
                n3 += 14;
            }
            n2 = 0;
            while (n2 < zt2.d.size()) {
                String string = (String)zt2.d.elementAt(n2);
                d.h.a(graphics2, string, 5, n3, 0);
                n3 += 14;
                ++n2;
            }
        } else {
            Graphics graphics3 = graphics;
            zt zt3 = this;
            int n4 = 34;
            if (zt3.i > -1) {
                graphics3.drawRegion(yi.O, 0, zt3.i * 12, 12, 12, 0, 5, 34, 20);
                n4 += 15;
            }
            int n5 = 0;
            while (n5 < zt3.d.size()) {
                String string = (String)zt3.d.elementAt(n5);
                d.h.a(graphics3, string, 5, n4, 0);
                n4 += 14;
                ++n5;
            }
        }
        super.a(graphics);
    }

    static aae a(zt zt2) {
        return zt2.g;
    }

    static void a(zt zt2, aae aae2) {
        zt2.g = null;
    }

    static Vector b(zt zt2) {
        return zt2.d;
    }

    static void c(zt zt2) {
        c = null;
    }

    static int d(zt zt2) {
        return zt2.h;
    }

    static void a(zt zt2, String string) {
        zt2.q = string;
    }

    static short e(zt zt2) {
        return zt2.o;
    }
}

