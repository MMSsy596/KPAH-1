/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class gv
extends aae {
    private static gv p;
    public aae a;
    public String b = "";
    int[] c;
    int[] d;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    private int z = 76;
    public mm e = new mm();
    public Vector f = new Vector();
    private String[] A = new String[]{""};
    s g;
    s h;
    s i;
    private s B;
    s o;
    private boolean C;
    private s D;
    private String E = null;

    public static gv e() {
        if (p == null) {
            p = new gv();
            return p;
        }
        return p;
    }

    public gv() {
        this.i();
        this.B = new s("\u0110\u00f3ng", new ae(this));
        this.i = new s("Thu ho\u1ea1ch", new ag(this));
        this.h = new s("T\u0103ng t\u1ed1c", new ah(this));
        this.g = new s(acv.K ? "" : "Xem", new ai(this));
        this.o = new s("Menu", new ak(this));
        this.D = new s("", new al(this));
        this.l = this.B;
        this.k = this.g;
        this.j = this.o;
        this.v = 300;
        this.w = 180;
        if (!acv.K) {
            this.v = 180;
        }
        this.x = acv.m / 2 - this.v / 2;
        this.y = acv.n / 2 - this.w / 2;
        this.t = 140;
        if (acv.K && this.v == 300) {
            this.r = this.x + this.v / 2;
            this.s = this.y + 25;
            this.t = this.v / 2 - 6;
            this.u = this.w - 36;
        }
    }

    protected final void f() {
        if (this.q < 0 || this.q > this.f.size() - 1) {
            return;
        }
        go.a().n(this.q, 1);
        this.f.elementAt(this.q);
    }

    public final void a(String stringArray) {
        int n2 = acv.K ? this.t - 16 : 120;
        stringArray = yg.a((String)stringArray, "|");
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        while (n5 < stringArray.length) {
            String[] stringArray2 = d.j[0].a(stringArray[n5], n2);
            n3 += stringArray2.length;
            ++n5;
        }
        String[] stringArray3 = new String[n3];
        int n6 = 0;
        while (n6 < stringArray.length) {
            String[] stringArray4 = d.j[0].a(stringArray[n6], n2);
            int n7 = 0;
            while (n7 < stringArray4.length) {
                stringArray3[n4] = stringArray4[n7];
                ++n4;
                ++n7;
            }
            ++n6;
        }
        this.A = stringArray3;
        if (!acv.K) {
            if (this.A.length == 1) {
                this.u = 35;
                this.s = acv.n / 2 - d.j[0].b() / 2;
            } else {
                this.u = this.A.length * (aae.ao - 3) + 5;
                this.s = acv.n / 2 - this.A.length * (aae.ao - 3) / 2;
            }
            if (this.s < 5) {
                this.s = 5;
            }
            if (this.u > 150) {
                this.u = 150;
            }
            this.r = acv.m / 2 - 75;
        }
        this.C = true;
        if (!acv.K) {
            this.k = this.D;
            this.j = this.D;
        }
    }

    protected final void g() {
        go.a().n(this.q, 2);
    }

    protected final void h() {
        go.a().n(this.q, 3);
    }

    public final void i() {
        this.c = new int[8];
        this.d = new int[8];
        int n2 = acv.m / 2 - 75;
        int n3 = acv.n / 2 - 75;
        this.c[0] = 10;
        this.d[0] = 72;
        this.c[1] = 43;
        this.d[1] = 62;
        this.c[2] = 20;
        this.d[2] = 28;
        this.c[3] = 52;
        this.d[3] = 17;
        this.c[4] = 70;
        this.d[4] = 56;
        this.c[5] = 82;
        this.d[5] = 7;
        this.c[6] = 106;
        this.d[6] = 22;
        this.c[7] = 112;
        this.d[7] = 64;
        n3 += 12;
        if (!acv.K) {
            this.z = 0;
        }
        int n4 = 0;
        while (n4 < this.c.length) {
            int n5 = n4;
            this.c[n5] = this.c[n5] + (n2 - this.z);
            int n6 = n4++;
            this.d[n6] = this.d[n6] + n3;
        }
        this.e.a();
        this.f = new Vector();
        this.q = 0;
        this.A = new String[]{""};
    }

    private void b(Graphics graphics) {
        graphics.setColor(25695);
        graphics.fillRect(this.r - 5, this.s + 10, this.t, this.u - 10);
        graphics.setColor(16774720);
        graphics.drawRect(this.r - 5, this.s + 10, this.t, this.u - 10);
        if (!this.C && this.E != null && acv.K) {
            String[] stringArray = yg.a(this.E, "|");
            int n2 = 0;
            while (n2 < stringArray.length) {
                d.j[0].a(graphics, stringArray[n2], this.r, this.s + n2 * 14 + 14, 0);
                ++n2;
            }
        }
    }

    private void c(Graphics graphics) {
        acv.a(graphics);
        if (this.v >= 300) {
            this.b(graphics);
            if (!this.C) {
                return;
            }
        } else {
            if (!this.C) {
                return;
            }
            this.b(graphics);
        }
        this.e.a(this.A.length, aae.ao, this.r, this.s, this.t - 2, this.u - 10, true, 0);
        this.e.a(graphics, this.r - 3, this.s + 10, this.t - 2, this.u - 15);
        int n2 = 0;
        while (n2 < this.A.length) {
            d.j[0].a(graphics, this.A[n2], this.r, this.s + n2 * (aae.ao - 3) + 14, 0);
            ++n2;
        }
    }

    public final void a(Graphics graphics) {
        Object object;
        xl xl2;
        if (this.a != null) {
            this.a.a(graphics);
        }
        yi.a(graphics, this.x, this.y, this.v, this.w, 0, this.b, false, 0);
        int n2 = acv.K ? this.z : this.v / 2;
        ko.b(graphics, (short)8214, this.x + n2, acv.n / 2 + 12, 3);
        this.E = null;
        n2 = 0;
        int n3 = 0;
        int n4 = 0;
        while (n4 < this.f.size()) {
            xl2 = (xl)this.f.elementAt(n4);
            if (xl2 != null) {
                if (this.q == n4) {
                    graphics.setColor(25695);
                    graphics.fillRect(xl2.a, xl2.b, 16, 30);
                    graphics.setColor(16774720);
                    graphics.drawRect(xl2.a, xl2.b, 16, 30);
                    n2 = xl2.a;
                    n3 = xl2.b;
                    object = "";
                    if (xl2.d == 1) {
                        object = "h\u00e9o";
                    }
                    if (xl2.d == 0) {
                        object = "xanh";
                    }
                    if (xl2.d == 2) {
                        object = "ch\u00edn";
                    }
                    this.E = String.valueOf(xl2.e) + "|Tr\u1ea1ng th\u00e1i: " + (String)object;
                }
                object = graphics;
                ko.b(object, (short)(xl2.c + 8200), xl2.a, xl2.b, 0);
            }
            ++n4;
        }
        this.c(graphics);
        if (this.E != null && !acv.K && !this.C) {
            int n5;
            object = this.E;
            xl2 = graphics;
            String[] stringArray = yg.a((String)object, "|");
            int n6 = d.j[0].a(stringArray[0]);
            n6 = n6 < (n5 = d.j[0].a(stringArray[1])) ? n5 : n6;
            xl2.setColor(1593912);
            xl2.fillRect(n2 + 16 - n6 / 2 - 3, n3 - 16, n6 + 5, 30);
            xl2.setColor(0xFFEE00);
            xl2.drawRect(n2 + 16 - n6 / 2 - 3, n3 - 16, n6 + 5, 30);
            d.j[0].a((Graphics)xl2, stringArray[0], n2 + 16, n3 - 15, 2);
            d.j[0].a((Graphics)xl2, stringArray[1], n2 + 16, n3, 2);
        }
        super.a(graphics);
    }

    public final void d() {
        if (this.a != null) {
            this.a.d();
        }
        if (acv.w == null) {
            this.e.b();
            this.e.c();
        }
        super.d();
    }

    private void j() {
        if (acv.c[2]) {
            acv.c[2] = false;
            this.e.a -= 50;
            if (this.e.a < 0) {
                this.e.a = 0;
                return;
            }
        } else if (acv.c[8]) {
            acv.c[8] = false;
            this.e.a += 50;
            if (this.e.a > this.e.c) {
                this.e.a = this.e.c;
            }
        }
    }

    public final void c() {
        if (this.C && !acv.K) {
            this.j();
        } else {
            if (acv.c[4]) {
                acv.c[4] = false;
                --this.q;
                if (this.q < 0) {
                    this.q = this.f.size() - 1;
                }
                if (acv.K) {
                    this.C = false;
                    this.e.a();
                }
            }
            if (acv.c[6]) {
                acv.c[6] = false;
                ++this.q;
                if (this.q > this.f.size() - 1) {
                    this.q = 0;
                }
                if (acv.K) {
                    this.C = false;
                    this.e.a();
                }
            }
            if (acv.K) {
                this.j();
            }
        }
        gv gv2 = this;
        if ((acv.K || !gv2.C) && acv.g) {
            int n2 = 0;
            while (n2 < gv2.c.length) {
                if (acv.j >= gv2.c[n2] && acv.j <= gv2.c[n2] + 15 && acv.k >= gv2.d[n2] && acv.k <= gv2.d[n2] + 30) {
                    acv.g = false;
                    gv2.C = false;
                    gv2.e.a();
                    gv2.q = n2;
                    if (gv2.C || gv2.k == null) break;
                    gv2.k.b.a();
                    break;
                }
                ++n2;
            }
        }
        super.c();
    }

    public final void a() {
        super.a();
    }

    public final void a(aae aae2) {
        this.a = aae2;
        super.a(aae2);
    }

    static boolean a(gv gv2) {
        return gv2.C;
    }

    static void a(gv gv2, boolean bl2) {
        gv2.C = false;
    }

    static int b(gv gv2) {
        return gv2.q;
    }
}

