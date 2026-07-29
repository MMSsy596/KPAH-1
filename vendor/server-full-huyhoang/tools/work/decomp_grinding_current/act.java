/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class act
extends aae {
    public static act a;
    private Vector c;
    private int d = 1;
    private aae e;
    private int f = 60;
    private int g = 20;
    private int h = 10;
    private int i;
    private int o;
    private int p;
    private int q;
    private static int r;
    private static int s;
    private static int t;
    private static int u;
    private static int v;
    private static int w;
    private static int x;
    private static int y;
    private yx z;
    private yx A;
    private bz B;
    public static byte b;

    static {
        w = 0;
        b = 0;
    }

    public static act e() {
        if (a == null) {
            a = new act();
            return a;
        }
        return a;
    }

    public final void a() {
        this.e = acv.q;
        this.h();
        b = 0;
        System.currentTimeMillis();
        super.a();
        this.b();
    }

    public act() {
        this.j = new s("Menu", new bj(this));
        this.B = new bz();
        this.b();
        this.B.a = true;
        this.c = new Vector();
        this.A = new yx("Tin \u0111\u1ebfn", null, null, false);
        this.d = 0;
        this.A.e = new Vector();
        this.c.addElement(this.A);
        this.h();
        this.f();
    }

    public final void b() {
        this.i = acv.m - 48;
        this.o = acv.n - 64;
        this.q = 24;
        this.p = 24;
        this.B.f = this.p + 4;
        this.B.g = this.q + this.o - 25;
        this.B.i = 20;
        this.B.h = this.i - 8;
        this.f();
    }

    public final void f() {
        if (this.c != null && this.z != null) {
            this.h = 20;
            this.f = this.i - this.h * this.c.size() + this.h - 1;
            if (this.f < d.b.a(this.z.d) + 15) {
                this.f = d.b.a(this.z.d) + 15;
                this.h = (this.i - this.f) / (this.c.size() - 1);
            }
            if (this.c.size() == 1) {
                this.f = this.i - 1;
            }
        }
    }

    public final void a(String string) {
        int n2 = 0;
        while (n2 < this.c.size()) {
            yx yx2 = (yx)this.c.elementAt(n2);
            if (yx2.d.equals(string)) {
                yx2.g = false;
                this.z = yx2;
                this.d = n2;
            }
            ++n2;
        }
    }

    public final void a(String stringArray, String string) {
        Object object = null;
        if (string != null && !string.equals(this.A.d)) {
            block12: {
                Object object2;
                String string2 = string;
                object = this;
                int n2 = 0;
                while (n2 < ((act)object).c.size()) {
                    yx yx2 = (yx)((act)object).c.elementAt(n2);
                    if (yx2.d.equals(string2)) {
                        object2 = yx2;
                        break block12;
                    }
                    ++n2;
                }
                object2 = object = null;
            }
            if (this != acv.q) {
                b = 1;
            }
        } else {
            object = this.A;
        }
        if (object == null) {
            object = new yx(string, new s("Chat", new bm(this)), this.B.e, true);
            new yx(string, new s("Chat", new bm(this)), this.B.e, true).e = new Vector();
            this.c.addElement(object);
            this.f();
        }
        if (!stringArray.equals("")) {
            stringArray = d.h.a((String)stringArray, this.i - 20);
            int n3 = 0;
            while (n3 < stringArray.length) {
                ((yx)object).e.addElement(stringArray[n3]);
                if (((yx)object).e.size() > 50) {
                    ((yx)object).e.removeElementAt(0);
                }
                ++n3;
            }
        }
        if (object == this.z) {
            if (w + 1 == this.z.e.size() - x) {
                w = this.z.e.size() - x;
                r = w * 14 - y / 2;
            }
            if ((v = this.z.e.size() * 14 - y + 50) < 0) {
                v = 0;
            }
        }
    }

    protected final void g() {
        if (this.B.e().equals("")) {
            return;
        }
        System.currentTimeMillis();
        if (this.z.d.equals("Bang h\u1ed9i")) {
            go.a().e(this.B.e());
        } else {
            this.a(String.valueOf(acv.s.t.an) + ": " + this.B.e(), this.z.d);
            go.a().b(this.z.d, this.B.e());
        }
        this.B.a("");
    }

    private void h() {
        this.z = (yx)this.c.elementAt(this.d);
        this.z.g = false;
        y = this.o - this.g;
        if (this.z.f) {
            y -= 21;
        }
        if ((v = this.z.e.size() * 14 - y + 50) < 0) {
            v = 0;
        }
        r = 0;
        s = 0;
        w = x = (y - 10) / 2 / 14;
        this.k = this.z.a;
        this.l = this.z.b;
    }

    public final void a(Graphics graphics) {
        int n2;
        yx yx2;
        graphics.translate(-graphics.getTranslateX(), -graphics.getTranslateY());
        this.e.a(graphics);
        acv.a(graphics);
        super.a(graphics);
        int n3 = this.o;
        int n4 = this.i;
        int n5 = this.q;
        int n6 = this.p;
        Graphics graphics2 = graphics;
        act act2 = this;
        graphics2.setColor(277044);
        graphics2.fillRect(n6, n5 + act2.g, n4, n3 - act2.g);
        int n7 = 0;
        while (n7 < 3) {
            graphics2.setColor(yi.W[n7]);
            graphics2.drawRect(n6 + n7, n5 + n7 + act2.g, n4 - (n7 << 1) - 1, n3 - (n7 << 1) - act2.g - 1);
            ++n7;
        }
        n7 = act2.c.size() - 1;
        while (n7 > act2.d) {
            yx2 = (yx)act2.c.elementAt(n7);
            n2 = 0;
            while (n2 < 3) {
                graphics2.setColor(yi.W[n2]);
                graphics2.drawRect(n6 + n2 + n7 * act2.h, n5 + n2, act2.f - (n2 << 1), act2.g - (n2 << 1));
                ++n2;
            }
            ++yx2.c;
            if (yx2.c > 10) {
                yx2.c = 0;
            }
            if (yx2.g && yx2.c >= 5) {
                graphics2.setColor(573098);
                graphics2.fillRect(n6 + 3 + act2.h * n7, n5 + 3, act2.f - 5, act2.g - 3);
            } else {
                graphics2.setClip(n6 + act2.h * n7 + act2.f - act2.h, n5, act2.h - 2, act2.g);
                graphics2.drawImage(yi.C[2], n6 + act2.h * n7 + 3 + act2.f - 70, n5 + 3, 0);
                graphics2.setClip(n6, n5, n4, n3);
            }
            --n7;
        }
        n7 = 0;
        while (n7 <= act2.d) {
            int n8 = 0;
            while (n8 < 3) {
                graphics2.setColor(yi.W[n8]);
                graphics2.drawRect(n6 + n8 + n7 * act2.h, n5 + n8, act2.f - (n8 << 1), act2.g - (n8 << 1));
                ++n8;
            }
            yx2 = (yx)act2.c.elementAt(n7);
            ++yx2.c;
            if (yx2.c > 10) {
                yx2.c = 0;
            }
            if (yx2.g && yx2.c >= 5) {
                graphics2.setColor(573098);
                graphics2.fillRect(n6 + 3 + act2.h * n7, n5 + 3, act2.f - 5, act2.g - 3 + (n7 == act2.d ? 4 : 0));
            } else if (n7 == act2.d) {
                graphics2.setClip(n6 + act2.h * n7 + 3, n5, act2.f - 5, act2.g + 3);
                n2 = 0;
                while (n2 < act2.f / 70 + 1) {
                    graphics2.drawImage(yi.C[2], n6 + 3 + act2.h * n7 + n2 * 70, n5 + 3, 0);
                    ++n2;
                }
            } else {
                graphics2.setClip(n6 + act2.h * n7, n5, act2.h, act2.g);
                graphics2.drawImage(yi.C[2], n6 + act2.h * n7 + 3, n5 + 3, 0);
            }
            graphics2.setClip(n6 - 4, n5 - 4, n4 + 8, n3 + 9);
            ++n7;
        }
        graphics2.drawImage(yi.C[0], n6 - 2 + act2.h * act2.d, n5 - 2, 0);
        graphics2.drawRegion(yi.C[0], 0, 0, 18, 19, 2, n6 + 3 + act2.f + act2.h * act2.d, n5 - 2, 24);
        n7 = 0;
        while (n7 < 2) {
            int n9 = 0;
            while (n9 < 2) {
                graphics2.setColor(yi.W[n7 == 0 ? n9 + 1 : 2 - n9]);
                graphics2.fillRect(n6 + act2.d * act2.h + (n9 + 1) + (act2.f - 3) * n7, n5 + act2.g - 8 + 6 + (n7 == 0 ? n9 : 1 - n9), 1, 3);
                ++n9;
            }
            ++n7;
        }
        graphics2.setClip(0, 0, acv.m, acv.n);
        graphics2.drawRegion(yi.C[0], 0, 0, 18, 19, 6, n6 - 2, n5 + n3 + 2, 36);
        graphics2.drawRegion(yi.C[0], 0, 0, 18, 19, 3, n6 + n4 + 2, n5 + n3 + 2, 40);
        graphics2 = graphics;
        act2 = this;
        d.b.a(graphics2, act2.z.d, act2.p + act2.f / 2 + act2.d * act2.h, act2.q + act2.g / 2 - 5, 2);
        if (act2.z.f && !acv.u.a) {
            act2.B.a(graphics2);
        }
        graphics2.setClip(act2.p, act2.q + act2.g + 5, act2.i - 3, y - 9);
        graphics2.translate(0, -s);
        if (act2.z.e.size() > 0) {
            n6 = s / 14;
            n5 = n6 + y / 14 + 1;
            if (n5 >= act2.z.e.size()) {
                n5 = act2.z.e.size();
            }
            n4 = n6;
            while (n4 < n5) {
                String string = (String)act2.z.e.elementAt(n4);
                d.h.a(graphics2, string, act2.p + 8, act2.q + n4 * 14 + act2.g + 3, 0);
                ++n4;
            }
        }
    }

    public final boolean a(int n2) {
        if (this.z.f && this.B.a && !acv.u.a) {
            this.B.a(n2);
        }
        return super.a(n2);
    }

    public final void d() {
        this.e.d();
        if (s != r) {
            u = r - s << 2;
            s += (t += u) >> 4;
            t &= 0xF;
        }
    }

    public final void c() {
        if (this.z.f && !acv.u.a) {
            this.B.d();
        }
        boolean bl2 = false;
        if (acv.b(4)) {
            --this.d;
            if (this.d < 0) {
                this.d = this.c.size() - 1;
            }
            this.h();
        } else if (acv.b(6)) {
            ++this.d;
            if (this.d >= this.c.size()) {
                this.d = 0;
            }
            this.h();
        }
        if (acv.e[2]) {
            if (--w < x) {
                w = x;
            }
            bl2 = true;
        } else if (acv.e[8]) {
            if (s < v) {
                ++w;
            }
            if (w > this.z.e.size() - x) {
                w = this.z.e.size() - x;
            }
            bl2 = true;
        }
        if (bl2) {
            r = w * 14 - y / 2;
            if (r < 0) {
                r = 0;
            }
            if (r > v) {
                r = v;
            }
        }
        super.c();
    }

    static int a(act act2) {
        return act2.d;
    }

    static Vector b(act act2) {
        return act2.c;
    }

    static void a(act act2, int n2) {
        act2.d = n2;
    }

    static void c(act act2) {
        act2.h();
    }

    static aae d(act act2) {
        return act2.e;
    }
}

