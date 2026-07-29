/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class hr
extends aae {
    private static hr J;
    private static int K;
    private static int L;
    private static int M;
    private static int N;
    public static Vector a;
    private mm O = new mm();
    public mm b = new mm();
    private mm P = new mm();
    private int Q;
    private int R;
    private int S;
    private int T;
    private int U = 28;
    public int c;
    private int V;
    private int W;
    public int d;
    private int X;
    public aae e;
    public String[] f = new String[]{""};
    public String g = "C\u1ed9ng thu\u1ed9c t\u00ednh";
    public boolean h;
    public byte i;
    private s Y;
    private s Z;
    s o;
    s p;
    s q;
    private int aa;
    static ql r;
    static gz s;
    static gz t;
    static gz u;
    private int[] ab;
    private int[] ac;
    private int[] ad;
    boolean v;
    boolean w;
    private int ae;
    String x = "Thong bao";
    public byte y;
    public byte z;
    private int af;
    public static Vector A;
    public static boolean B;
    public static boolean C;
    public static boolean D;
    public static boolean E;
    public static short F;
    public static short G;
    public static short H;
    public static short I;
    private static byte ag;
    private static byte ah;

    static {
        a = new Vector();
        s = null;
        t = null;
        u = null;
        A = new Vector();
        E = true;
        F = (short)-1;
        G = (short)-1;
        H = (short)-1;
        ag = 0;
        ah = 1;
    }

    public static hr e() {
        if (J == null) {
            J = new hr();
            return J;
        }
        return J;
    }

    public final void f() {
        this.O.a();
        this.P.a();
        this.b.a();
        a = new Vector();
        this.c = 0;
        this.d = 0;
        this.w = false;
        this.a(this.i, false);
        this.y = 0;
        this.V = 0;
        this.X = 0;
        if (acv.K) {
            this.y = (byte)-1;
            this.V = -1;
            this.X = -1;
        }
        this.X = 0;
        if (acv.K) {
            this.X = -1;
        }
    }

    public final void a(byte by2, boolean bl2) {
        int n2 = M + K / 2 - 44;
        int n3 = N + L / 2 - 35;
        if (acv.K) {
            n2 = M + K / 4 - 44;
        }
        if (by2 == 0) {
            this.ab = new int[2];
            this.ad = new int[2];
            this.ac = new int[2];
            this.ab[0] = n2;
            this.ab[1] = n2 + 44 + 22;
            this.ac[0] = n3;
            this.ac[1] = n3;
            this.ad[0] = this.ab[0];
            this.ad[1] = this.ab[1];
        } else if (by2 == 1) {
            this.p.a = "\u0110\u1eadp";
            this.ab = new int[4];
            this.ad = new int[4];
            this.ac = new int[4];
            this.ab[1] = n2 - 22;
            this.ab[2] = n2 + 44 - 11;
            this.ab[3] = n2 + 44 + 22 + 22;
            this.ab[0] = n2 + 44 - 11;
            this.ac[1] = n3 + 11;
            this.ac[2] = n3 + 11;
            this.ac[3] = n3 + 11;
            this.ac[0] = n3 - 18;
            this.ad[0] = this.ab[0];
            this.ad[1] = this.ab[1];
            this.ad[2] = this.ab[2];
            this.ad[3] = this.ab[3];
            this.ae = n2 + 44 - 1;
        } else if (by2 == 4) {
            this.p.a = "\u0110\u1eadp";
            this.ab = new int[3];
            this.ad = new int[3];
            this.ac = new int[3];
            this.ab[1] = n2 + 44 + 20;
            this.ab[0] = n2 + 44 - 11;
            this.ab[2] = n2 + 44 - 42;
            this.ac[1] = n3 + 22;
            this.ac[0] = n3 - 11;
            this.ac[2] = n3 + 22;
            this.ad[0] = this.ab[0];
            this.ad[1] = this.ab[1];
            this.ad[2] = this.ab[1];
            this.ae = n2 + 44 - 1;
        } else {
            this.p.a = "Kh\u00f3a";
            this.ab = new int[2];
            this.ad = new int[2];
            this.ac = new int[2];
            this.ab[1] = n2 + 44 - 11;
            this.ab[0] = n2 + 44 - 11;
            this.ac[1] = n3 + 11;
            this.ac[0] = n3 - 18;
            this.ad[0] = this.ab[0];
            this.ad[1] = this.ab[1];
            this.ae = n2 + 44 - 1;
        }
        String cfr_ignored_0 = "TYPE SCREENDAPDO " + by2;
        this.aa = 0;
        if (bl2) {
            this.y = 0;
            if (acv.K) {
                this.y = (byte)-1;
            }
            this.j = this.p;
            this.h = false;
            this.b.a();
            this.v = false;
        } else {
            this.v = false;
            this.j = this.p;
        }
        this.i = by2;
        this.j();
        this.a(true, false);
    }

    public hr() {
        K = acv.m - 10;
        L = acv.n - aae.ao - 10;
        if (K > 176) {
            K = 176;
        }
        if (L > 220) {
            L = 220;
        }
        if (acv.m >= 320 && acv.K) {
            K = 300;
        }
        M = acv.m / 2 - K / 2;
        N = (acv.n - aae.ao) / 2 - L / 2;
        if (N < 5) {
            N = 5;
        }
        if (M < 5) {
            M = 5;
        }
        this.S = K;
        if (acv.K && K == 300) {
            this.Q = M + K / 2;
            this.R = N + 55;
            this.S = K / 2 - 8;
            this.T = L - 54;
        }
        this.W = L - 54;
        this.Y = new s("\u0110\u00f3ng", new pb(this));
        this.o = new s("", new pa(this));
        this.p = new s("\u0110\u1eadp", new ou(this));
        this.q = new s("Ti\u1ebfp t\u1ee5c", new fq(this));
        this.Z = new s("", new fr(this));
        this.l = this.Y;
        this.k = this.o;
        this.V = 0;
        if (acv.K) {
            this.V = -1;
            this.y = (byte)-1;
            this.X = -1;
        }
        this.X = 0;
        if (acv.K) {
            this.X = -1;
        }
    }

    protected final void g() {
        int n2 = M + K / 2 - 44;
        if (acv.K) {
            n2 = M + K / 4 - 44;
        }
        this.ad[0] = this.ad[1] = n2 + 44 - 11;
        this.aa = 10;
        this.v = true;
    }

    protected final void h() {
        if (this.y == 1) {
            if (hw.bv.size() == 0) {
                return;
            }
            if (this.c < 0 || this.c > A.size() - 1) {
                return;
            }
            ql ql2 = (ql)A.elementAt(this.c);
            if (!ql2.F) {
                ql2.F = true;
                go.a().a(ql2.i, acv.s.t.cH);
            }
            this.f = d.j[0].a(ql2.a(false), this.S - 10);
        } else if (this.y == 0) {
            if (a.size() <= 0) {
                return;
            }
            Object object = (gz)a.elementAt(this.d);
            object = yi.a(((gz)object).a);
            String string = ql.a(((xv)object).j, "0");
            string = String.valueOf(string) + ql.a(((xv)object).k, "0");
            this.f = d.j[0].a(string, this.S - 10);
        }
        if (!acv.K) {
            if (this.f.length == 1) {
                this.T = 35;
                this.R = acv.n / 2 - d.j[0].b() / 2;
            } else {
                this.T = this.f.length * aae.ao + 8;
                this.R = acv.n / 2 - this.f.length * (aae.ao - 3) / 2;
            }
            if (this.R < N) {
                this.R = N;
            }
            if (this.T > 150) {
                this.T = 150;
            }
            this.Q = M;
        }
        this.h = true;
        if (!acv.K) {
            this.k = this.Z;
        }
        this.j = this.Z;
    }

    private void b(Graphics graphics) {
        graphics.setColor(25695);
        int n2 = acv.K ? -27 : 10;
        graphics.fillRect(this.Q + 2, this.R + n2, this.S, this.T - 8);
        graphics.setColor(16774720);
        graphics.drawRect(this.Q + 2, this.R + n2, this.S, this.T - 8);
    }

    private void c(Graphics graphics) {
        acv.a(graphics);
        if (K >= 300) {
            this.b(graphics);
            if (!this.h) {
                return;
            }
        } else {
            if (!this.h) {
                return;
            }
            this.b(graphics);
        }
        int n2 = acv.K ? 24 : 0;
        this.b.a(this.f.length, aae.ao - 2, this.Q, this.R - n2, this.S - 2, this.T - 10, true, 0);
        this.b.a(graphics, this.Q, this.R + (acv.K ? 2 : 10) - n2, this.S - 2, this.T - 15);
        int n3 = 0;
        while (n3 < this.f.length) {
            if (!this.f[n3].equals("")) {
                byte by2 = (byte)(this.f[n3].charAt(0) - 48);
                int n4 = 1;
                if (!nu.a(this.f[n3].charAt(0))) {
                    by2 = 0;
                    n4 = 0;
                }
                d.j[by2 >= 5 ? (byte)0 : by2].a(graphics, this.f[n3].substring(n4), this.Q + 8, this.R + n3 * (aae.ao - 3) + (acv.K ? 6 : 14) - (n2 + (acv.K ? 2 : 0)), 0);
            }
            ++n3;
        }
    }

    public final void a(Graphics graphics) {
        if (this.e != null) {
            this.e.a(graphics);
        }
        yi.a(graphics, M, N, K, L, 25, this.g, true, this.W - 28);
        acv.a(graphics);
        this.O.a(hw.bv.size(), this.U, M, N + 28 + this.W, K - 2, 27, false, 0);
        this.O.a(graphics, M + 2, N + 23 + this.W, K - 4, 31);
        int n2 = 0;
        while (n2 < A.size()) {
            if (this.c == n2 && this.y == 1) {
                graphics.setColor(0x636363);
                graphics.fillRect(M + this.U * n2, N + 23 + this.W, this.U, 28);
            }
            ql ql2 = (ql)A.elementAt(n2);
            ql2.a(graphics, M + this.U * n2 + this.U / 2, N + 36 + this.W);
            graphics.setColor(14527502);
            graphics.fillRect(M + this.U * n2, N + 22 + this.W, 1, 29);
            if (r != null && hr.r.i == ql2.i) {
                graphics.setColor(3268607);
                graphics.drawRect(M + this.U * n2 + 1, N + 23 + this.W, this.U - 2, 28);
                graphics.drawRect(M + this.U * n2 + 2, N + 24 + this.W, this.U - 4, 26);
            }
            ++n2;
        }
        graphics.setColor(14527502);
        graphics.fillRect(M + this.U * A.size(), N + 22 + this.W, 1, 29);
        acv.a(graphics);
        n2 = !acv.K ? K - 2 : K - 2 - this.S;
        this.P.a(a.size(), this.U + 2, M, N + this.W, n2, 27, false, 0);
        this.P.a(graphics, M + 2, N + 23 + this.W - 28, n2 - 2, 31);
        int n3 = 0;
        while (n3 < a.size()) {
            if (this.d == n3 && this.y == 0) {
                graphics.setColor(0x636363);
                graphics.fillRect(M + this.U * n3, N + 24 + this.W - 28, this.U, 26);
            }
            gz gz2 = (gz)a.elementAt(n3);
            if (gz2.d) {
                graphics.setColor(0x320033);
                graphics.fillRect(M + this.U * n3, N + 24 + this.W - 28, this.U, 26);
                if (this.d == n3 && this.y == 0) {
                    graphics.setColor(0x636363);
                    graphics.fillRect(M + this.U * n3, N + 24 + this.W - 28, this.U, 26);
                }
            }
            if (s != null && hr.s.a == gz2.a && hr.s.d == gz2.d) {
                graphics.setColor(3268607);
                graphics.drawRect(M + this.U * n3 + 1, N + 24 + this.W - 28, this.U - 2, 25);
                graphics.drawRect(M + this.U * n3 + 2, N + 24 + this.W - 27, this.U - 4, 23);
            }
            if (t != null && hr.t.a == gz2.a && hr.t.d == gz2.d) {
                graphics.setColor(238149631);
                graphics.drawRect(M + this.U * n3 + 1, N + 24 + this.W - 28, this.U - 2, 25);
                graphics.drawRect(M + this.U * n3 + 2, N + 24 + this.W - 27, this.U - 4, 23);
            }
            if (u != null && hr.u.a == gz2.a && hr.u.d == gz2.d) {
                graphics.setColor(238149631);
                graphics.drawRect(M + this.U * n3 + 1, N + 24 + this.W - 28, this.U - 2, 25);
                graphics.drawRect(M + this.U * n3 + 2, N + 24 + this.W - 27, this.U - 4, 23);
            }
            yi.a(graphics, (int)yi.a((short)gz2.a).l, M + this.U * n3 + this.U / 2, N + 24 + this.W - 28 + 13);
            d.i[3].a(graphics, String.valueOf(gz2.c), M + this.U * n3 + this.U - 1, N + 22 + this.W - d.i[3].b(), 1);
            graphics.setColor(14527502);
            graphics.fillRect(M + this.U * n3, N + 22 + this.W - 28, 1, 29);
            ++n3;
        }
        graphics.fillRect(M + this.U * a.size(), N + 22 + this.W - 28, 1, 28);
        acv.a(graphics);
        this.d(graphics);
        this.c(graphics);
        super.a(graphics);
    }

    private void d(Graphics graphics) {
        int n2 = 0;
        while (n2 < this.ab.length) {
            graphics.setColor(0xFFFFFF);
            graphics.fillRect(this.ab[n2], this.ac[n2], 22, 22);
            if (this.aa % 2 == 0) {
                graphics.setColor(0x636363);
                graphics.fillRect(this.ab[n2] + 1, this.ac[n2] + 1, 20, 20);
            }
            ++n2;
        }
        if (this.i != 1) {
            if (s != null) {
                yi.a(graphics, (int)yi.a((short)hr.s.a).l, this.ab[1] + 10, this.ac[1] + 10);
            }
            if (r != null) {
                r.a(graphics, this.ab[0] + 10, this.ac[0] + 10);
            }
            if (this.i == 4 && t != null) {
                yi.a(graphics, (int)yi.a((short)hr.t.a).l, this.ab[2] + 10, this.ac[2] + 10);
            }
        } else if (this.i == 1) {
            if (s != null) {
                yi.a(graphics, (int)yi.a((short)hr.s.a).l, this.ab[2] + 10, this.ac[2] + 10);
            }
            if (t != null) {
                yi.a(graphics, (int)yi.a((short)hr.t.a).l, this.ab[1] + 10, this.ac[1] + 10);
            }
            if (u != null) {
                yi.a(graphics, (int)yi.a((short)hr.u.a).l, this.ab[3] + 10, this.ac[3] + 10);
            }
            if (r != null) {
                r.a(graphics, this.ab[0] + 10, this.ac[0] + 10);
            }
        }
        if (this.i == 0 || !this.w || this.x.equals("")) {
            return;
        }
        n2 = N + this.W - 24;
        graphics.setColor(0xFFFFFF);
        graphics.fillRect(this.ae - 60, n2 - 3, 120, 20);
        graphics.setColor(3222312);
        graphics.fillRect(this.ae - 59, n2 - 2, 118, 18);
        d.j[0].a(graphics, this.x, this.ae, n2, 2);
    }

    public final void d() {
        if (this.e != null) {
            this.e.d();
        }
        hr hr2 = this;
        --hr2.aa;
        if (hr2.aa < 0) {
            hr2.aa = 0;
        }
        if (hr2.aa <= 0) {
            if (hr2.ab[0] != hr2.ad[0]) {
                hr2.ab[0] = hr2.ad[0] - hr2.ab[0] >> 1 == 0 ? hr2.ad[0] : hr2.ab[0] + (hr2.ad[0] - hr2.ab[0] >> 1);
            }
            if (hr2.ab[1] != hr2.ad[1]) {
                hr2.ab[1] = hr2.ad[1] - hr2.ab[1] >> 1 == 0 ? hr2.ad[1] : hr2.ab[1] + (hr2.ad[1] - hr2.ab[1] >> 1);
            }
        }
        if (this.z > 0) {
            this.z = (byte)(this.z - 1);
        }
        if (this.z <= 0) {
            this.z = 0;
        }
        boolean bl2 = false;
        boolean bl3 = false;
        if (acv.w == null) {
            if (acv.K) {
                aca aca2;
                if (this.b.f) {
                    this.O.f = false;
                    this.P.f = false;
                } else if (this.O.f) {
                    this.b.f = false;
                    this.P.f = false;
                } else if (this.P.f) {
                    this.O.f = false;
                    this.b.f = false;
                }
                if (this.O.f && !acv.u.a && this.z == 0) {
                    this.y = 1;
                    aca2 = this.O.b();
                    if (aca2.a || aca2.c) {
                        this.c = aca2.b;
                        this.V = aca2.b;
                        bl2 = true;
                        if (this.l != null && this.h && acv.j()) {
                            this.l.b.a();
                        }
                    }
                }
                this.O.c();
                if (this.P.f && !acv.u.a && this.z == 0) {
                    this.y = 0;
                    aca2 = this.P.b();
                    if (aca2.a || aca2.c) {
                        this.d = aca2.b;
                        this.X = aca2.b;
                        bl3 = true;
                        if (this.l != null && this.h && acv.j()) {
                            this.l.b.a();
                        }
                    }
                }
                this.P.c();
                aca2 = null;
                if (this.b.f && !acv.u.a) {
                    this.b.b();
                }
                this.b.c();
                if (!acv.f) {
                    if (this.O.f && bl2) {
                        if (this.c > -1 && this.c <= A.size() - 1) {
                            if (this.V != -1) {
                                this.c = this.V;
                            }
                            this.o.b.a();
                        }
                    } else if (this.P.f && bl3 && this.d > -1 && this.d <= a.size() - 1) {
                        if (this.X != -1) {
                            this.d = this.X;
                        }
                        this.o.b.a();
                    }
                    this.b.f = false;
                    this.O.f = false;
                    this.P.f = false;
                }
            } else if (!this.h) {
                aca aca3 = this.O.b();
                if (aca3.a || aca3.c) {
                    this.c = aca3.b;
                    this.O.a(this.c * (this.U + 2));
                }
                this.O.c();
                aca aca4 = this.P.b();
                if (aca4.a || aca4.c) {
                    this.d = aca4.b;
                    this.P.a(this.d * (this.U + 2));
                }
                this.P.c();
            } else {
                this.b.b();
                this.b.c();
            }
        }
        super.d();
    }

    public final void c() {
        if (this.h) {
            if (acv.c[2]) {
                acv.c[2] = false;
                this.b.a -= 50;
                if (this.b.a < 0) {
                    this.b.a = 0;
                }
            } else if (acv.c[8]) {
                acv.c[8] = false;
                this.b.a += 50;
                if (this.b.a > this.b.c) {
                    this.b.a = this.b.c;
                }
            }
        } else {
            if (acv.c[2]) {
                acv.c[2] = false;
                this.y = (byte)(this.y - 1);
                if (this.y < 0) {
                    this.y = 0;
                }
            } else if (acv.c[8]) {
                acv.c[8] = false;
                this.y = (byte)(this.y + 1);
                if (this.y > 1) {
                    this.y = 1;
                }
            }
            if (acv.c[4]) {
                acv.c[4] = false;
                if (this.y == 1) {
                    --this.c;
                    if (this.c < 0) {
                        this.c = A.size() - 1;
                    }
                    this.O.a(this.c * this.U);
                } else if (this.y == 0) {
                    --this.d;
                    if (this.d < 0) {
                        this.d = a.size() - 1;
                    }
                    this.P.a(this.d * this.U);
                } else {
                    --this.af;
                    if (this.af < 0) {
                        this.af = this.ab.length - 1;
                    }
                }
            } else if (acv.c[6]) {
                acv.c[6] = false;
                if (this.y == 1) {
                    ++this.c;
                    if (this.c > A.size() - 1) {
                        this.c = 0;
                    }
                    this.O.a(this.c * this.U);
                } else if (this.y == 0) {
                    ++this.d;
                    if (this.d > a.size() - 1) {
                        this.d = 0;
                    }
                    this.P.a(this.d * this.U);
                } else {
                    --this.af;
                    if (this.af < 0) {
                        this.af = this.ab.length - 1;
                    }
                }
            }
        }
        super.c();
    }

    public static boolean b(int n2) {
        return n2 >= 0 && n2 <= 4;
    }

    public static boolean c(int n2) {
        return n2 >= 5 && n2 <= 7 || n2 == 155 || n2 == 156;
    }

    public static boolean d(int n2) {
        return n2 >= 8 && n2 <= 11 || n2 == 66 || n2 == 245 || n2 == 157 || n2 == 158;
    }

    public static boolean e(int n2) {
        return n2 == xv.e;
    }

    public static boolean f(int n2) {
        return n2 == xv.f;
    }

    private void a(boolean bl2, boolean bl3) {
        Object object;
        a.removeAllElements();
        if (bl2) {
            A.removeAllElements();
        }
        Vector vector = sc.g;
        int n2 = vector.size();
        boolean bl4 = false;
        boolean bl5 = false;
        boolean bl6 = false;
        int n3 = 0;
        while (n3 < n2) {
            gz gz2 = (gz)vector.elementAt(n3);
            xv xv2 = yi.a(gz2.a);
            if (this.i == 0) {
                if (xv2.h == xv.b) {
                    if (s != null && hr.s.a == gz2.a && !hr.s.d) {
                        s = gz2;
                        bl4 = true;
                    }
                    a.addElement(gz2);
                }
            } else if (this.i == 2) {
                if (xv2.h == xv.c) {
                    if (s != null && hr.s.a == gz2.a && !hr.s.d) {
                        s = gz2;
                    }
                    a.addElement(gz2);
                }
            } else if (this.i == 3) {
                if (xv2.h == xv.d) {
                    if (s != null && hr.s.a == gz2.a && !hr.s.d) {
                        s = gz2;
                    }
                    a.addElement(gz2);
                }
            } else if (this.i == 4) {
                if (xv2.h == xv.e || xv2.h == xv.f) {
                    if (s != null && hr.s.a == gz2.a && !hr.s.d && xv2.h == xv.e) {
                        s = gz2;
                        bl4 = true;
                    }
                    if (t != null && hr.t.a == gz2.a && !hr.t.d && xv2.h == xv.f) {
                        t = gz2;
                        bl5 = true;
                    }
                    a.addElement(gz2);
                }
            } else if (xv2.h == 0) {
                if (s != null && hr.s.a == gz2.a && !hr.s.d) {
                    s = gz2;
                    bl4 = true;
                } else if (s == null && bl3 && F == gz2.a && !B) {
                    s = gz2;
                    bl4 = true;
                }
                if (t != null && hr.t.a == gz2.a && !hr.t.d) {
                    t = gz2;
                    bl5 = true;
                } else if (t == null && bl3 && H == gz2.a && !D) {
                    t = gz2;
                    bl5 = true;
                }
                if (u != null && hr.u.a == gz2.a && !hr.u.d) {
                    u = gz2;
                    bl6 = true;
                } else if (u == null && bl3 && G == gz2.a && !C) {
                    u = gz2;
                    bl6 = true;
                }
                a.addElement(gz2);
            }
            ++n3;
        }
        vector = sc.h;
        n2 = vector.size();
        n3 = 0;
        boolean bl7 = false;
        boolean bl8 = false;
        int n4 = 0;
        while (n4 < n2) {
            object = (gz)vector.elementAt(n4);
            xv xv3 = yi.a(((gz)object).a);
            if (this.i == 0) {
                if (xv3.h == xv.b) {
                    if (s != null && hr.s.a == ((gz)object).a && hr.s.d) {
                        s = object;
                        n3 = 1;
                    }
                    a.addElement(object);
                    ((gz)object).d = true;
                }
            } else if (this.i == 2) {
                if (xv3.h == xv.c) {
                    if (s != null && hr.s.a == ((gz)object).a && hr.s.d) {
                        s = object;
                    }
                    a.addElement(object);
                    ((gz)object).d = true;
                }
            } else if (this.i == 3) {
                if (xv3.h == xv.d) {
                    if (s != null && hr.s.a == ((gz)object).a && hr.s.d) {
                        s = object;
                    }
                    a.addElement(object);
                    ((gz)object).d = true;
                }
            } else if (this.i == 4) {
                if (xv3.h == xv.e || xv3.h == xv.f) {
                    if (s != null && hr.s.a == ((gz)object).a && hr.s.d && xv3.h == xv.e) {
                        s = object;
                        n3 = 1;
                    }
                    if (t != null && hr.t.a == ((gz)object).a && hr.t.d && xv3.h == xv.f) {
                        t = object;
                        bl7 = true;
                    }
                    a.addElement(object);
                    ((gz)object).d = true;
                }
            } else if (xv3.h == 0) {
                if (s != null && hr.s.a == ((gz)object).a && hr.s.d) {
                    s = object;
                    n3 = 1;
                } else if (s == null && bl3 && F == ((gz)object).a && B) {
                    s = object;
                    n3 = 1;
                }
                if (t != null && hr.t.a == ((gz)object).a && hr.t.d) {
                    t = object;
                    bl7 = true;
                } else if (t == null && bl3 && H == ((gz)object).a && D) {
                    t = object;
                    bl7 = true;
                }
                if (u != null && hr.u.a == ((gz)object).a && hr.u.d) {
                    u = object;
                    bl8 = true;
                } else if (u == null && bl3 && G == ((gz)object).a && C) {
                    u = object;
                    bl8 = true;
                }
                a.addElement(object);
                ((gz)object).d = true;
            }
            ++n4;
        }
        if (this.i == 0) {
            if (!bl4 && n3 == 0) {
                s = null;
            }
            if (!bl5 && !bl7) {
                t = null;
            }
            if (!bl6 && !bl8) {
                u = null;
            }
        }
        A = this.i == 0 || this.i == 4 ? ql.c(ah) : (this.i == 2 ? ql.a(ah, 1) : (this.i == 3 ? ql.d(0) : ql.h()));
        n4 = 0;
        while (n4 < A.size()) {
            object = (ql)A.elementAt(n4);
            if (I == ((ql)object).i) {
                r = object;
                return;
            }
            ++n4;
        }
    }

    public final void g(int n2) {
        ql ql2;
        if (this.c < A.size() && (ql2 = (ql)A.elementAt(this.c)) != null && ql2.i == n2) {
            this.f = d.j[0].a(ql2.a(false), this.S - 10);
        }
    }

    public final void i() {
        this.a(false, true);
        this.w = true;
    }

    public final void j() {
        s = null;
        t = null;
        u = null;
        r = null;
        this.a(true, false);
    }

    public final void a() {
        super.a();
    }

    public final void a(aae aae2) {
        this.e = aae2;
        super.a(aae2);
    }
}

