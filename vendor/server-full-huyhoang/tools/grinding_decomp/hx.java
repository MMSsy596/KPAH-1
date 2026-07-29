/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class hx
extends aae {
    private static hx h;
    public static int a;
    public static int b;
    private static int i;
    private static int o;
    private static int p;
    private static Vector q;
    private mm r = new mm();
    private mm s = new mm();
    public mm c = new mm();
    private int t = -1;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y = 80;
    private int z;
    private int A;
    public aae d;
    public String[] e = new String[]{""};
    private String[] B = new String[]{"Tab 1", "Tab 2", "Tab 3", "Tab 4"};
    private String C = "C\u1ed5 v\u1eadt";
    public boolean f;
    private s D;
    private s E;
    s g;

    static {
        a = 80;
        q = new Vector();
    }

    public static hx e() {
        if (h == null) {
            h = new hx();
            return h;
        }
        return h;
    }

    public final void a(abs abs2) {
        q = new Vector();
        ys ys2 = null;
        int n2 = o + 6;
        int n3 = p + 55;
        try {
            int n4 = abs2.b().readByte();
            this.B = new String[n4];
            int n5 = 0;
            while (n5 < n4) {
                this.B[n5] = abs2.b().readUTF();
                int n6 = abs2.b().readByte();
                Vector<ys> vector = new Vector<ys>();
                int n7 = 0;
                while (n7 < n6) {
                    ys2 = new ys(this);
                    new ys(this).c = abs2.b().readUTF();
                    ys2.f = String.valueOf(ys2.c) + "|" + abs2.b().readUTF();
                    String[] stringArray = yg.a(abs2.b().readUTF(), "|");
                    ys2.e = new String[stringArray.length + 1];
                    ys2.e[0] = ys2.c;
                    int n8 = 0;
                    while (n8 < stringArray.length) {
                        ys2.e[n8 + 1] = stringArray[n8];
                        ++n8;
                    }
                    ys2.d = abs2.b().readByte();
                    ys2.a = n2;
                    ys2.b = n3 + n7 * (a + 3);
                    vector.addElement(ys2);
                    ++n7;
                }
                q.addElement(vector);
                ++n5;
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public hx() {
        b = acv.m - 10;
        i = acv.n - aae.ao - 10;
        if (b > 176) {
            b = 176;
        }
        if (i > 220) {
            i = 220;
        }
        if (acv.m >= 320 && acv.K) {
            b = 300;
        }
        o = acv.m / 2 - b / 2;
        p = (acv.n - aae.ao) / 2 - i / 2;
        if (p < 5) {
            p = 5;
        }
        if (o < 5) {
            o = 5;
        }
        this.w = b;
        if (acv.K && b == 300) {
            this.u = o + b / 2;
            this.v = p + 55;
            this.w = b / 2 - 8;
            this.x = i - 54;
        }
        String cfr_ignored_0 = "H cell = " + i + " Hcanvas = " + acv.n;
        this.D = new s("\u0110\u00f3ng", new ds(this));
        this.g = new s(acv.K ? "" : "Xem", new dt(this));
        this.E = new s("", new dr(this));
        this.l = this.D;
        this.k = this.g;
        if (!acv.K) {
            this.t = 0;
        }
        this.a((abs)null);
    }

    protected final void f() {
        if (q.size() == 0 || this.A <= -1) {
            return;
        }
        Object object = (Vector)q.elementAt(this.A);
        if (this.t < 0 || this.t > ((Vector)object).size() - 1) {
            return;
        }
        object = (ys)((Vector)object).elementAt(this.t);
        this.e = yg.a(((ys)object).f, "|");
        if (!acv.K) {
            if (this.e.length == 1) {
                this.x = 35;
                this.v = acv.n / 2 - d.j[0].b() / 2;
            } else {
                this.x = this.e.length * (aae.ao - 3) + 5;
                this.v = acv.n / 2 - this.e.length * (aae.ao - 3) / 2;
            }
            if (this.v < p) {
                this.v = p;
            }
            if (this.x > 150) {
                this.x = 150;
            }
            this.u = o;
        }
        this.f = true;
        if (!acv.K) {
            this.k = this.E;
        }
    }

    private void b(Graphics graphics) {
        graphics.setColor(25695);
        graphics.fillRect(this.u, this.v + (acv.K ? 0 : 10), this.w, this.x - 10);
        graphics.setColor(16774720);
        graphics.drawRect(this.u, this.v + (acv.K ? 0 : 10), this.w, this.x - 10);
    }

    public final void a(Graphics graphics) {
        block16: {
            int n2;
            Graphics graphics2;
            Object object;
            block15: {
                block14: {
                    if (this.d != null) {
                        this.d.a(graphics);
                    }
                    int n3 = acv.K && b == 300 ? b / 2 : b;
                    yi.a(graphics, o, p, b, i, 25, this.C, false, 0);
                    acv.a(graphics);
                    this.s.a(this.B.length, this.y + 2, o, p + 32, b - 2, 25, false, 0);
                    this.s.a(graphics, o + 2, p + 25, b - 4, 25);
                    int n4 = 0;
                    while (n4 < this.B.length) {
                        if (this.z == n4) {
                            graphics.setColor(0x636363);
                            graphics.fillRect(o + this.y * n4, p + 26, this.y, 25);
                        }
                        d.j[0].a(graphics, this.B[n4], o + this.y * n4 + this.y / 2, p + 32, 2);
                        graphics.setColor(14527502);
                        graphics.fillRect(o + this.y * n4, p + 25, 1, 25);
                        ++n4;
                    }
                    graphics.fillRect(o + this.y * this.B.length, p + 25, 1, 25);
                    acv.a(graphics);
                    if (q.size() > 0) {
                        if (this.A != this.z && this.z > -1 && this.z < q.size()) {
                            this.A = this.z;
                        }
                        Vector vector = (Vector)q.elementAt(this.A);
                        this.r.a(vector.size(), a + 2, o, p + 55, n3 - 2, i - 65, true, 0);
                        this.r.a(graphics, o, p + 52, n3 - 2, i - 55);
                        int n5 = 0;
                        while (n5 < vector.size()) {
                            object = (ys)vector.elementAt(n5);
                            if (object != null) {
                                if (this.t == n5) {
                                    graphics.setColor(0x636363);
                                    graphics.fillRect(o + 4, ((ys)object).b, n3, a);
                                }
                                graphics2 = graphics;
                                n2 = 0;
                                graphics2.setColor(8023616);
                                graphics2.fillRect(((ys)object).a, ((ys)object).b + a + 1, b, 1);
                                dh dh2 = ko.a((short)(((ys)object).d + 8500));
                                if (dh2 != null && dh2.a != null) {
                                    n2 = dh2.a.getWidth() + 2;
                                }
                                ko.b(graphics2, (short)(((ys)object).d + 8500), ((ys)object).a, ((ys)object).b, 0);
                                int n6 = 0;
                                while (n6 < ((ys)object).e.length) {
                                    d.j[0].a(graphics2, ((ys)object).e[n6], ((ys)object).a + n2, ((ys)object).b + n6 * (aae.ao - 3), 0);
                                    ++n6;
                                }
                            }
                            ++n5;
                        }
                    }
                    graphics2 = graphics;
                    object = this;
                    acv.a(graphics2);
                    if (b < 300) break block14;
                    ((hx)object).b(graphics2);
                    if (((hx)object).f) break block15;
                    break block16;
                }
                if (!((hx)object).f) break block16;
                ((hx)object).b(graphics2);
            }
            ((hx)object).c.a(((hx)object).e.length, aae.ao, ((hx)object).u, ((hx)object).v, ((hx)object).w - 2, ((hx)object).x - 10, true, 0);
            ((hx)object).c.a(graphics2, ((hx)object).u, ((hx)object).v + (acv.K ? 2 : 10), ((hx)object).w - 2, ((hx)object).x - 15);
            n2 = 0;
            while (n2 < ((hx)object).e.length) {
                d.j[0].a(graphics2, ((hx)object).e[n2], ((hx)object).u + 4, ((hx)object).v + n2 * (aae.ao - 3) + (acv.K ? 6 : 14), 0);
                ++n2;
            }
        }
        super.a(graphics);
    }

    public final void d() {
        if (this.d != null) {
            this.d.d();
        }
        if (acv.w == null) {
            if (acv.K) {
                aca aca2 = this.r.b();
                if (aca2.a || aca2.c) {
                    if (this.f) {
                        this.l.b.a();
                    }
                    this.t = aca2.b;
                    if (!this.r.d && !this.f && this.k != null) {
                        this.k.b.a();
                    }
                }
                this.r.c();
                aca2 = this.s.b();
                if (aca2.a || aca2.c) {
                    this.z = aca2.b;
                    this.s.a(this.z * (this.y + 2));
                    if (this.l != null && this.f) {
                        this.l.b.a();
                    }
                }
                this.s.c();
                this.c.b();
                this.c.c();
            } else if (!this.f) {
                aca aca3 = this.r.b();
                if (aca3.a || aca3.c) {
                    this.t = aca3.b;
                }
                this.r.c();
                aca3 = this.s.b();
                if (aca3.a || aca3.c) {
                    this.z = aca3.b;
                    this.s.a(this.z * (this.y + 2));
                }
                this.s.c();
            } else {
                this.c.b();
                this.c.c();
            }
        }
        super.d();
    }

    public final void c() {
        if (this.f) {
            if (acv.c[2]) {
                acv.c[2] = false;
                this.c.a -= 50;
                if (this.c.a < 0) {
                    this.c.a = 0;
                }
            } else if (acv.c[8]) {
                acv.c[8] = false;
                this.c.a += 50;
                if (this.c.a > this.c.c) {
                    this.c.a = this.c.c;
                }
            }
        } else {
            Vector vector = null;
            if (this.z > -1 && this.z < q.size()) {
                vector = (Vector)q.elementAt(this.z);
                if (acv.c[2]) {
                    acv.c[2] = false;
                    --this.t;
                    if (this.t < 0) {
                        this.t = vector.size() - 1;
                    }
                    this.r.a(this.t * (a + 2));
                } else if (acv.c[8]) {
                    acv.c[8] = false;
                    ++this.t;
                    if (this.t > vector.size() - 1) {
                        this.t = 0;
                    }
                    this.r.a(this.t * (a + 2));
                }
            }
            if (acv.c[4]) {
                acv.c[4] = false;
                --this.z;
                if (this.z < 0) {
                    this.z = this.B.length - 1;
                }
                this.s.a(this.z * (this.y + 2));
            } else if (acv.c[6]) {
                acv.c[6] = false;
                ++this.z;
                if (this.z > this.B.length - 1) {
                    this.z = 0;
                }
                this.s.a(this.z * (this.y + 2));
            }
        }
        super.c();
    }

    public final void a() {
        super.a();
    }

    public final void a(aae aae2) {
        this.d = aae2;
        super.a(aae2);
    }
}

