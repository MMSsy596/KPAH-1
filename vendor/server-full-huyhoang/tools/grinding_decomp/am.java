/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class am
extends aae {
    private static am o;
    Vector a = new Vector();
    private int p;
    private boolean q;
    private boolean r;
    private boolean s;
    s b;
    private s t;
    private s u;
    s c = new s("", new ac(this));
    long d;
    public String[] e;
    public String[] f;
    public int g;
    public int h;
    public int i;

    public static am e() {
        if (o == null) {
            o = new am();
            return o;
        }
        return o;
    }

    public final void a() {
        super.a();
        this.b();
    }

    public final void f() {
        int n2;
        this.p = 0;
        this.q = false;
        this.s = false;
        this.r = false;
        this.k = this.b;
        this.l = this.c;
        this.g = 0;
        this.i = 0;
        this.h = 0;
        if (this.f != null) {
            n2 = 0;
            while (n2 < this.f.length) {
                this.f[n2] = "";
                n2 = (byte)(n2 + 1);
            }
        }
        this.a.removeAllElements();
        if (this.e != null) {
            n2 = 0;
            while (n2 < this.e.length) {
                this.e[n2] = "";
                n2 = (byte)(n2 + 1);
            }
        }
    }

    public final void a(byte by2, String stringArray, String[] stringArray2) {
        this.e = stringArray2;
        stringArray = d.h.a((String)stringArray, 100);
        int n2 = 0;
        while (n2 < this.a.size()) {
            tz tz2 = (tz)this.a.elementAt(n2);
            if (tz2.e == by2) {
                tz2.h = 1;
                n2 = tz2.b;
                int n3 = tz2.a;
                String[] stringArray3 = stringArray;
                v0.f = stringArray3;
                this.g = n3;
                this.h = n2;
                this.i = n2 - 20;
                return;
            }
            ++n2;
        }
    }

    public final void a(abs object) {
        try {
            byte by2 = ((abs)object).b().readByte();
            int n2 = ((abs)object).b().readByte();
            this.e = new String[n2];
            n2 = 0;
            while (n2 < this.e.length) {
                this.e[n2] = ((abs)object).b().readUTF();
                n2 = (byte)(n2 + 1);
            }
            object = this;
            ((am)object).a.removeAllElements();
            n2 = 0;
            while (n2 < by2) {
                tz tz2 = new tz();
                new tz().e = n2;
                ((am)object).a.addElement(tz2);
                ++n2;
            }
            this.b();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void b() {
        if (this.a != null && this.a.size() > 0) {
            am am2 = this;
            int n2 = acv.m / 2;
            int n3 = acv.n / 2;
            int n4 = 0;
            int[][] nArrayArray = new int[][]{{acv.m / 2 - acv.m / 5, acv.m / 2 + acv.m / 5, acv.m / 2, acv.m / 2 - acv.m / 5, acv.m / 2 + acv.m / 5}, {acv.m / 2 - acv.m / 5, acv.m / 2, acv.m / 2 + acv.m / 5, acv.m / 2, acv.m / 2 - acv.m / 5, acv.m / 2, acv.m / 2 + acv.m / 5}, {acv.m / 2 - acv.m / 5, acv.m / 2, acv.m / 2 + acv.m / 5, acv.m / 2 - acv.m / 5, acv.m / 2, acv.m / 2 + acv.m / 5, acv.m / 2 - acv.m / 5, acv.m / 2, acv.m / 2 + acv.m / 5}};
            int[][] nArrayArray2 = new int[][]{{acv.n / 2 - acv.n / 5, acv.n / 2 - acv.n / 5, acv.n / 2, acv.n / 2 + acv.n / 5, acv.n / 2 + acv.n / 5}, {acv.n / 2 - acv.n / 5, acv.n / 2 - acv.n / 5, acv.n / 2 - acv.n / 5, acv.n / 2, acv.n / 2 + acv.n / 5, acv.n / 2 + acv.n / 5, acv.n / 2 + acv.n / 5}, {acv.n / 2 - acv.n / 5, acv.n / 2 - acv.n / 5, acv.n / 2 - acv.n / 5, acv.n / 2, acv.n / 2, acv.n / 2, acv.n / 2 + acv.n / 5, acv.n / 2 + acv.n / 5, acv.n / 2 + acv.n / 5}};
            if (am2.a.size() == 7) {
                n4 = 1;
            }
            if (am2.a.size() == 9) {
                n4 = 2;
            }
            int n5 = 0;
            while (n5 < am2.a.size()) {
                tz tz2 = (tz)am2.a.elementAt(n5);
                ((tz)am2.a.elementAt(n5)).a = n2;
                tz2.b = n3;
                tz2.c = nArrayArray[n4][n5];
                tz2.d = nArrayArray2[n4][n5];
                tz2.f = tz2.c;
                tz2.g = tz2.d;
                ++n5;
            }
        }
        super.b();
    }

    public am() {
        this.b = new s("B\u1eaft \u0111\u1ea7u", new x(this));
        this.t = new s("Ch\u1ecdn", new z(this));
        this.k = this.b;
        this.u = new s("M\u1edf h\u1ebft", new l(this));
    }

    public final void a(Graphics graphics) {
        acv.s.a(graphics);
        yi.d(graphics, 10, 20, acv.m - 20, acv.n - 60);
        d.b.a(graphics, "H\u1ed9p may m\u1eafn", acv.o, 25, 2);
        int n2 = 0;
        while (n2 < this.a.size()) {
            tz tz2 = (tz)this.a.elementAt(n2);
            if (tz2 != null) {
                Graphics graphics2 = graphics;
                tz tz3 = tz2;
                graphics2.setColor(15852810);
                graphics2.fillRect(tz3.a - 16, tz3.b - 16, 32, 32);
                graphics2.setColor(34949);
                graphics2.fillRect(tz3.a - 14, tz3.b - 14, 28, 28);
                graphics2.drawImage(yi.F[tz3.h], tz3.a, tz3.b, 3);
                if (acv.l % 4 == 0 && this.p == n2 && !this.q) {
                    graphics.setColor(0xFFFFFF);
                    graphics.drawRect(tz2.a - 17, tz2.b - 17, 34, 34);
                }
            }
            ++n2;
        }
        if (this.h != this.i) {
            n2 = 0;
            while (n2 < this.f.length) {
                d.j[0].a(graphics, this.f[n2], this.g, this.h + n2 * 14, 2);
                n2 = (byte)(n2 + 1);
            }
        }
        super.a(graphics);
    }

    public final void d() {
        Object object;
        int n2 = 0;
        while (n2 < this.a.size()) {
            object = (tz)this.a.elementAt(n2);
            if (object != null) {
                if (((tz)object).a != ((tz)object).c) {
                    ((tz)object).a = ((tz)object).c - ((tz)object).a >> 1 == 0 ? ((tz)object).c : (((tz)object).a += ((tz)object).c - ((tz)object).a >> 1);
                }
                if (((tz)object).b != ((tz)object).d) {
                    ((tz)object).b = ((tz)object).d - ((tz)object).b >> 1 == 0 ? ((tz)object).d : (((tz)object).b += ((tz)object).d - ((tz)object).b >> 1);
                }
            }
            ++n2;
        }
        if (this.q && this.d - System.currentTimeMillis() / 1000L <= 0L && this.r) {
            this.r = false;
            this.g();
            this.k = this.t;
            this.l = this.u;
            this.s = true;
        }
        object = this;
        if (((am)object).h != ((am)object).i) {
            --((am)object).h;
        } else {
            ((am)object).h = ((am)object).i;
            if (((am)object).f != null) {
                n2 = 0;
                while (n2 < ((am)object).f.length) {
                    ((am)object).f[n2] = "";
                    n2 = (byte)(n2 + 1);
                }
            }
        }
        super.d();
    }

    public final void c() {
        if (acv.c[4]) {
            acv.c[4] = false;
            if (!this.q) {
                --this.p;
                if (this.p < 0) {
                    this.p = 0;
                }
            }
        } else if (acv.c[6]) {
            acv.c[6] = false;
            if (!this.q) {
                ++this.p;
                if (this.p > this.a.size() - 1) {
                    this.p = this.a.size() - 1;
                }
            }
        }
        am am2 = this;
        if (acv.g && am2.a.size() > 0) {
            int n2 = 0;
            while (n2 < am2.a.size()) {
                tz tz2 = (tz)am2.a.elementAt(n2);
                if (tz2 != null && acv.j >= tz2.a - 16 && acv.j <= tz2.a + 16 && acv.k >= tz2.b - 16 && acv.k <= tz2.b + 16) {
                    acv.g = false;
                    if (am2.s) {
                        am2.p = tz2.e;
                        am2.k.b.a();
                        break;
                    }
                }
                ++n2;
            }
        }
        super.c();
    }

    public final void g() {
        this.q = !this.q;
        int n2 = 0;
        while (n2 < this.a.size()) {
            tz tz2 = (tz)this.a.elementAt(n2);
            if (this.q) {
                tz2.c = acv.m / 2;
                tz2.d = acv.n / 2;
            } else {
                tz2.c = tz2.f;
                tz2.d = tz2.g;
            }
            ++n2;
        }
    }

    static void a(am am2, boolean bl2) {
        am2.r = bl2;
    }

    static int a(am am2) {
        return am2.p;
    }

    static void a(am am2, int n2) {
        am2.p = 0;
    }

    static void b(am am2, boolean bl2) {
        am2.q = false;
    }

    static void c(am am2, boolean bl2) {
        am2.s = false;
    }
}

