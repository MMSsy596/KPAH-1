/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class na
extends aae {
    public static na a;
    public Vector b;
    public Vector c;
    private int g;
    private int h;
    private int i;
    private int o;
    private static int p;
    private static int q;
    private static int r;
    private static int s;
    public static int d;
    public static int e;
    private String t;
    public byte f = 0;
    private aae u;
    private int v = 0;
    private boolean w = false;
    private int x = 0;
    private int y = 1;
    private int z = 25;
    private int A = 0;
    private int B = 1;
    private int C = 0;
    private int D = 1;
    private String[] E;

    static {
        e = 0;
    }

    public static na e() {
        if (a == null) {
            a = new na();
            return a;
        }
        return a;
    }

    public final void a() {
        if (acv.q == this) {
            return;
        }
        e = 0;
        this.f = 0;
        p = 0;
        q = 0;
        if (this.u == null) {
            this.u = acv.s;
        }
        super.a();
        this.b();
    }

    public final void b() {
        this.g = acv.m - 41;
        this.h = acv.n - 56;
        d = this.c.size() * this.i - (this.h - 32);
        if (d < 0) {
            d = 0;
        }
    }

    public na() {
        this.l = new s("\u0110\u00f3ng", new mr(this));
        this.k = new s("Ch\u1ecdn", new ms(this));
        this.j = new s("Menu", new mx(this));
    }

    protected final void f() {
        Vector<s> vector = new Vector<s>();
        if (this.o == 4 || this.o == 3 || this.o == 5 || this.o == 1) {
            if (this.o == 5) {
                vector.addElement(new s("Danh s\u00e1ch qu\u1ea3n l\u00fd bang.", new my(this)));
            }
            vector.addElement(new s("Xem th\u00eam", new mv(this)));
        }
        if (this.o == 0) {
            vector.addElement(new s("X\u00f3a b\u1ea1n", new mw(this)));
        }
        if (this.o == 1) {
            if (((hw)this.c.elementAt((int)0)).cI == acv.s.t.cI) {
                vector.addElement(new s("Nh\u1eafn tin to\u00e0n bang", new lq(this)));
            }
            if (!(((hw)this.c.elementAt((int)na.e)).cI != acv.s.t.cI || acv.s.t.af != 0 && acv.s.t.af != 1 || ((hw)this.c.elementAt((int)na.e)).an.equals(acv.s.t.an))) {
                vector.addElement(new s("M\u1eddi kh\u1ecfi bang", new hd(this)));
            }
        } else if (this.o == 2 && acv.s.t.af == 0) {
            vector.addElement(new s("X\u00f3a tin nh\u1eafn", new hq(this)));
        }
        if (vector.size() > 0) {
            acv.u.a(vector, 0);
        }
    }

    public final void a(Object object) {
        if (bg2.o == 2) {
            jt.a().a(((kk)object).c, "TIN NH\u1eaeN");
            return;
        }
        if (bg2.o == 5) {
            go.a().n(((zy)object).a);
            return;
        }
        acv.y.a("N\u1ed9i dung tin nh\u1eafn", new hp((na)bg2, object), 0, 100, false);
        bg bg2 = acv.y;
        acv.w = bg2;
    }

    public final void a(Vector vector, int n2, String string) {
        e = 0;
        if (vector.size() == 0) {
            this.f = (byte)-1;
        } else {
            this.c = vector;
        }
        this.o = n2;
        this.i = n2 == 2 ? 30 : 40;
        this.t = string;
        this.g();
        if (this.c != null) {
            d = this.c.size() * this.i - (this.h - 32);
        }
        if (d < 0) {
            d = 0;
        }
        if (n2 == 6 && this.E == null) {
            this.E = new String[]{"Bang ch\u1ee7", "Ph\u00f3 bang", "Tr\u01b0\u1edfng l\u00e3o"};
        }
    }

    private void g() {
        if (this.o != 2 && this.o != 5) {
            int n2 = 0;
            while (n2 < this.c.size()) {
                hw hw2 = (hw)this.c.elementAt(n2);
                ((hw)this.c.elementAt(n2)).cL = (short)25;
                hw2.cM = (short)(n2 * this.i + this.i / 2 + hw2.cN / 2 - 5);
                ++n2;
            }
        }
    }

    public final void d() {
        if (Math.abs(p - q) < 15 && q < 0) {
            p = 0;
        }
        if (Math.abs(p - q) < 10 && q > d) {
            p = d;
        }
    }

    public final void c() {
        int n2 = 0;
        if (acv.a(20, 46, this.g, this.h - 24)) {
            if (acv.f) {
                if (!this.w) {
                    this.v = q;
                    this.w = true;
                }
                if ((p = this.v + (acv.D - acv.k)) < -30) {
                    p = -30;
                }
                if (p > d + 30) {
                    p = d + 30;
                }
            }
            if (acv.g) {
                acv.g = false;
                this.w = false;
                int n3 = acv.D - acv.k;
                if (Math.abs(n3) < 10) {
                    n2 = (p + acv.k - 46) / this.i;
                    if (n2 == e) {
                        if (this.k != null) {
                            this.k.b.a();
                        } else if (this.j != null) {
                            this.j.b.a();
                        }
                    }
                    if ((e = n2) < 0) {
                        e = 0;
                    }
                    if (e >= this.c.size()) {
                        e = this.c.size() - 1;
                    }
                    this.x = 0;
                    n2 = 1;
                }
            }
        }
        if (acv.b(2)) {
            if (--e < 0) {
                e = this.c.size() - 1;
            }
            this.x = 0;
            n2 = 1;
        } else if (acv.b(8)) {
            if (++e >= this.c.size()) {
                e = 0;
            }
            n2 = 1;
            this.x = 0;
        }
        if (n2 != 0) {
            p = e * this.i - (this.h - 31) / 2;
            if (p < 0) {
                p = 0;
            }
            if (p > d) {
                p = d;
            }
        }
        if (q != p) {
            s = p - q << 2;
            q += (r += s) >> 4;
            r &= 0xF;
        }
        super.c();
        if (this.u != null) {
            this.u.d();
        }
    }

    public final void a(Graphics graphics) {
        if (this.u != null) {
            this.u.a(graphics);
        }
        acv.a(graphics);
        yi.d(graphics, 20, 20, this.g, this.h);
        graphics.setClip(25, 20, this.g - 10, 20);
        int n2 = d.h.a(this.t);
        if (n2 > this.g - 25) {
            if (this.y == 1) {
                --this.z;
                if (this.z < this.g - 35 - n2) {
                    this.y = -1;
                }
            } else {
                ++this.z;
                if (this.z > 25) {
                    this.y = 1;
                }
            }
        }
        d.c.a(graphics, this.t, this.z, 24, 0);
        acv.a(graphics);
        graphics.translate(20, 47);
        graphics.setClip(4, 0, this.g - 8, this.h - 31);
        graphics.translate(0, -q);
        yi.b(graphics, 4, e * this.i, this.g - 9, this.i);
        if (this.o == 5) {
            Graphics graphics2 = graphics;
            na na2 = this;
            int n3 = q / na2.i;
            int n4 = n3 + na2.h / na2.i + 1;
            if (n4 >= na2.c.size()) {
                n4 = na2.c.size();
            }
            while (n3 < n4) {
                Object object = (zy)na2.c.elementAt(n3);
                int n5 = 0;
                if (((zy)object).a != -1 && ko.a(((zy)object).a) != null) {
                    n5 = 12;
                    ko.a(graphics2, (short)(((zy)object).a + 1000), 16, n3 * na2.i + 12);
                }
                String string = null;
                string = String.valueOf(((zy)object).b) + " - C\u1ea5p \u0111\u1ed9: " + (((zy)object).g > -1 ? String.valueOf(((zy)object).g) : "?");
                d.j[0].a(graphics2, string, n5 + 11, n3 * na2.i + 4, 0);
                int n6 = d.j[0].a(string);
                if (((zy)object).m > -1) {
                    graphics2.drawRegion(yi.O, 0, ((zy)object).m * 11, 11, 11, 0, n5 + 11 + 17 + n6, n3 * na2.i + 4, 0);
                }
                object = "Bang ch\u1ee7: " + ((zy)object).c + " - Ti\u1ec1n: " + ((zy)object).i + "xu - Th\u00e0nh vi\u00ean: " + ((zy)object).h;
                if (n3 == e && (n5 = d.h.a((String)object)) > na2.g - 10 && e == n3) {
                    if (na2.B == 1) {
                        --na2.A;
                        if (na2.A < na2.g - 20 - n5) {
                            na2.B = -1;
                        }
                    } else {
                        ++na2.A;
                        if (na2.A > 5) {
                            na2.B = 1;
                        }
                    }
                }
                d.h.a(graphics2, (String)object, 11 + (n3 == e ? na2.A : 0), n3 * na2.i + 20, 0);
                ++n3;
            }
        } else if (this.o == 2) {
            this.b(graphics);
        } else {
            this.c(graphics);
        }
        super.a(graphics);
    }

    private void b(Graphics graphics) {
        Object var2_2 = null;
        int n2 = q / this.i;
        int n3 = n2 + this.h / this.i + 1;
        if (n3 >= this.c.size()) {
            n3 = this.c.size();
        }
        int n4 = n3 - 1;
        while (n4 >= n2) {
            Object object = (kk)this.c.elementAt(n4);
            d.b.a(graphics, ((kk)object).a, 7, n4 * this.i, 0);
            object = d.h.a(((kk)object).c) > this.g - 10 ? String.valueOf(d.e.a(((kk)object).c, this.g - 10)[0]) + "..." : ((kk)object).c;
            d.h.a(graphics, (String)object, 7, n4 * this.i + 15, 0);
            --n4;
        }
    }

    private void c(Graphics graphics) {
        int n2 = q / this.i;
        int n3 = n2 + this.h / this.i + 1;
        if (n3 >= this.c.size()) {
            n3 = this.c.size();
        }
        while (n2 < n3) {
            int n4;
            Object object;
            hw hw2 = (hw)this.c.elementAt(n2);
            hw2.c(graphics);
            String string = "C\u1ea5p \u0111\u1ed9: " + (hw2.N > -1 ? String.valueOf(hw2.N) : "?");
            int n5 = 0;
            while (n5 < hw2.aU.size()) {
                object = (ql)hw2.aU.elementAt(n5);
                yc yc2 = yi.b((int)((ql)object).r);
                string = String.valueOf(string) + " - " + yc.b[yc2.c] + ": " + ((ql)object).y;
                if (((ql)object).s > 0) {
                    string = String.valueOf(string) + "+" + ((ql)object).s;
                }
                ++n5;
            }
            n5 = 0;
            if (hw2.cI != -1 && ko.a(hw2.cI) != null) {
                n5 = 8;
                ko.a(graphics, (short)(hw2.cI + 1000), 45, n2 * this.i + 12);
            }
            object = acv.a(hw2.bs, hw2.aW);
            int n6 = d.j[0].a(String.valueOf(hw2.an) + (String)object + (this.o == 6 && hw2.af < 3 ? " - " + this.E[hw2.af] : "") + (this.o == 7 ? " - CT: " + hw2.bZ + " - LT: " + hw2.ca : "")) + 30;
            if (n6 > this.g - 55 && e == n2) {
                if (this.D == 1) {
                    --this.C;
                    if (this.C < this.g - 65 - n6) {
                        this.D = -1;
                    }
                } else {
                    ++this.C;
                    if (this.C > 5) {
                        this.D = 1;
                    }
                }
            }
            graphics.setClip(45, q, this.g - 49, this.h - 31);
            d.j[0].a(graphics, String.valueOf(hw2.an) + " - " + (String)object + (this.o == 6 && hw2.af < 3 ? " - " + this.E[hw2.af] : "") + (this.o == 7 ? " - CT: " + hw2.bZ + " - LT: " + hw2.ca : ""), n5 + 45 + (n2 == e ? this.C : 0), n2 * this.i + 5, 0);
            n6 = 0;
            n6 = d.j[0].a(String.valueOf(hw2.an) + " - " + (String)object + (this.o == 6 && hw2.af < 3 ? " - " + this.E[hw2.af] : "") + (this.o == 7 ? " - CT: " + hw2.bZ + " - LT: " + hw2.ca : ""));
            if (hw2.cT > -1) {
                graphics.drawRegion(yi.O, 0, hw2.cT * 11, 11, 11, 0, n5 + 45 + (n2 == e ? this.C : 0) + n6 + 5, n2 * this.i + 5, 0);
            }
            if ((n4 = d.h.a(string)) > this.g - 45 && e == n2) {
                if (this.y == 1) {
                    --this.x;
                    if (this.x < this.g - 55 - n4) {
                        this.y = -1;
                    }
                } else {
                    ++this.x;
                    if (this.x > 5) {
                        this.y = 1;
                    }
                }
            }
            d.h.a(graphics, string, 45 + (n2 == e ? this.x : 0), n2 * this.i + 20, 0);
            graphics.setClip(0, q, this.g - 4, this.h - 31);
            ++n2;
        }
    }

    static aae a(na na2) {
        return na2.u;
    }

    static void a(na na2, aae aae2) {
        na2.u = null;
    }

    static int b(na na2) {
        return na2.o;
    }

    static void c(na na2) {
        na2.g();
    }

    static int d(na na2) {
        return na2.i;
    }

    static int e(na na2) {
        return na2.h;
    }
}

