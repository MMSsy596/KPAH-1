/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class po
extends aae {
    private static int I;
    private static int J;
    private static int K;
    private static int L;
    private static Vector M;
    private mm N = new mm();
    public mm a = new mm();
    private mm O = new mm();
    private int P;
    private int Q;
    private int R;
    private int S;
    private int T = 28;
    public int b;
    private int U;
    private int V;
    private int W;
    private int X;
    public aae c;
    public String[] d = new String[]{""};
    private String Y = "C\u1ed9ng thu\u1ed9c t\u00ednh";
    private Vector Z = new Vector();
    public boolean e;
    public byte f;
    private byte aa;
    public boolean g;
    private boolean ab;
    public boolean h;
    public boolean i;
    public boolean o;
    private int ac;
    public int p;
    public int q;
    public int r = 22;
    private s ad;
    s s;
    s t;
    private s ae;
    s u;
    private boolean[] af;
    public boolean[] v;
    private Vector ag;
    private short ah;
    private byte ai;
    private byte aj;
    private Vector ak;
    private Vector al;
    static ql w;
    public ql x;
    public ql y;
    public ql z;
    public ql A;
    private static gz am;
    private static gz ap;
    private static gz aq;
    static gz B;
    private int[] ar;
    private int[] as;
    private int[] at;
    private int[] au;
    boolean C;
    private boolean av;
    private int aw;
    private String ax;
    private Vector ay;
    private int az;
    private int aA;
    private int aB;
    public byte D;
    public byte E;
    public byte F;
    public static Vector G;
    public static boolean H;
    private static short aC;
    private static short aD;
    private static short aE;
    private static byte aF;
    private static byte aG;

    static {
        M = new Vector();
        am = null;
        ap = null;
        aq = null;
        B = null;
        G = new Vector();
        H = true;
        aC = (short)-1;
        aD = (short)-1;
        aE = (short)-1;
        aF = 0;
        aG = 1;
    }

    public final void e() {
        this.N.a();
        this.ag.removeAllElements();
        this.O.a();
        this.a.a();
        this.ak.removeAllElements();
        this.al.removeAllElements();
        this.Z.removeAllElements();
        M.removeAllElements();
        this.b = 0;
        this.W = 0;
        this.av = false;
        this.g = false;
        this.D = 0;
        this.U = 0;
        this.X = 0;
        if (acv.K) {
            this.D = (byte)-1;
            this.U = -1;
            this.X = -1;
        }
        this.X = 0;
        if (acv.K) {
            this.X = -1;
        }
    }

    public final void a(byte by2, boolean bl2) {
        this.f = (byte)5;
        by2 = (byte)(K + I / 2 - 44);
        if (acv.K) {
            by2 = (byte)(K + I / 4 - 44);
        }
        int n2 = L + 27 + (this.V - 28) / 2;
        this.t.a = "Nghi\u1ec1n";
        this.Y = "Nghi\u1ec1n B\u1ed9t";
        this.D = 1;
        this.ar = new int[5];
        this.at = new int[4];
        this.au = new int[4];
        this.as = new int[5];
        this.ar[0] = by2 + 44 - 11;
        this.ar[1] = this.ar[0] - 48;
        this.ar[3] = this.ar[0] + 48;
        this.ar[2] = this.ar[0];
        this.as[0] = n2 - this.r / 2 + 3;
        if (this.as[0] - 2 * this.r < L + 27) {
            this.as[0] = L + 27 + 2 * this.r;
        }
        this.as[2] = this.as[0] - 2 * this.r;
        this.as[1] = this.as[0] + 28;
        this.as[3] = this.as[0] + 28;
        this.at[0] = this.ar[0];
        this.at[1] = this.ar[1];
        this.at[2] = this.ar[2];
        this.at[3] = this.ar[3];
        this.au[0] = this.as[0];
        this.au[1] = this.as[1];
        this.au[2] = this.as[2];
        this.au[3] = this.as[3];
        this.aw = by2 + 44 - 1;
        if (acv.r) {
            this.as[2] = this.as[0] - 2 * this.r + 4;
            this.ar[4] = this.ar[0];
            this.as[4] = this.as[0] + 40;
        } else {
            this.ar[4] = this.ar[0];
            this.as[4] = this.as[0] + 50;
        }
        this.ac = this.au[0];
        this.p = this.r;
        this.q = 0;
        String cfr_ignored_0 = "TYPE SCREENDAPDO " + 5;
        this.C = false;
        this.j = this.t;
        this.h();
        this.a(true, false);
    }

    public po() {
        new Vector();
        this.af = new boolean[6];
        this.v = new boolean[6];
        this.ag = new Vector();
        this.ah = 0;
        this.ai = 0;
        this.aj = 1;
        this.ak = new Vector();
        this.al = new Vector();
        this.ax = "Thong bao";
        this.ay = new Vector();
        this.az = 15;
        this.aA = -1;
        this.F = (byte)5;
        I = acv.m - 10;
        J = acv.n - aae.ao - 10;
        if (I > 176) {
            I = 176;
        }
        if (J > 220) {
            J = 220;
        }
        if (acv.m >= 320 && acv.K) {
            I = 300;
        }
        K = acv.m / 2 - I / 2;
        L = (acv.n - aae.ao) / 2 - J / 2;
        if (L < 5) {
            L = 5;
        }
        if (K < 5) {
            K = 5;
        }
        this.R = I;
        if (acv.K && I == 300) {
            this.P = K + I / 2;
            this.Q = L + 55;
            this.R = I / 2 - 8;
            this.S = J - 54;
        }
        this.V = J - 54;
        this.u = new s("\u0110\u1eadp", new ft(this));
        this.ae = new s("Xin ch\u1edd", new ed(this));
        this.t = new s("Nghi\u1ec1n", new ee(this));
        this.ad = new s("\u0110\u00f3ng", new ea(this));
        this.s = new s("", new eb(this));
        this.l = this.ad;
        this.k = this.s;
        this.U = 0;
        if (acv.K) {
            this.U = -1;
            this.D = (byte)-1;
            this.X = -1;
        }
        this.X = 0;
        if (acv.K) {
            this.X = -1;
        }
    }

    public final void a_(int n2, int n3, int n4) {
        acv.g();
        this.ah = (short)n2;
        this.ai = (byte)n3;
        this.aj = (byte)n4;
        po po2 = this;
        n3 = yb.b[0][10] / 2;
        po2.k = po2.ae;
        po2.t.a = "";
        Object object = new di(po2.ar[2] + n3 - 4, po2.ac + po2.r / 2 - 10, 54);
        po2.ak.addElement(object);
        object = null;
        int n5 = 0;
        while (n5 < po2.ar.length) {
            if (n5 % 4 != 0) {
                object = new gc(po2.ar[n5] + n3 - 4, po2.as[n5] + n3 - 10, po2.ar[2] + n3 - 4, po2.ac + po2.r / 2 - 10, 40, 2);
                po2.al.addElement(object);
            }
            ++n5;
        }
        this.h = true;
    }

    private void b(Graphics graphics) {
        int n2 = 0;
        while (n2 < this.al.size()) {
            gc gc2 = (gc)this.al.elementAt(n2);
            gc2.a(graphics);
            ++n2;
        }
    }

    private void c(Graphics graphics) {
        acv.a(graphics);
        int n2 = 0;
        while (n2 < this.ak.size()) {
            di di2 = (di)this.ak.elementAt(n2);
            di2.a(graphics);
            ++n2;
        }
    }

    protected final void f() {
        Object object;
        if (this.f == 6) {
            if (hw.bv.size() == 0) {
                return;
            }
            if (this.b < 0 || this.b > G.size() - 1) {
                return;
            }
            if (this.F == 2) {
                if (B == null) {
                    return;
                }
                object = yi.a(po.B.a);
                String string = ql.a(((xv)object).j, "0");
                string = String.valueOf(string) + ql.a(((xv)object).k, "0");
                this.d = d.j[0].a(string, this.R - 10);
            }
            if (this.F != 2) {
                object = (ql)G.elementAt(this.b);
                if (!((ql)object).F) {
                    ((ql)object).F = true;
                    go.a().a(((ql)object).i, acv.s.t.cH);
                }
                this.d = d.j[0].a(((ql)object).a(false), this.R - 10);
            }
        }
        if (this.f == 5) {
            if (hw.bv.size() == 0) {
                return;
            }
            if (this.b < 0 || this.b > G.size() - 1) {
                return;
            }
            object = (ql)G.elementAt(this.b);
            if (!((ql)object).F) {
                ((ql)object).F = true;
                go.a().a(((ql)object).i, acv.s.t.cH);
            }
            this.d = d.j[0].a(((ql)object).a(false), this.R - 10);
        }
        if (!acv.K && G.size() > 0) {
            if (this.d.length == 1) {
                this.S = 35;
                this.Q = acv.n / 2 - d.j[0].b() / 2;
            } else {
                this.S = this.d.length * aae.ao + 8;
                this.Q = acv.n / 2 - this.d.length * (aae.ao - 3) / 2;
            }
            if (this.Q < L) {
                this.Q = L;
            }
            if (this.S > 150) {
                this.S = 150;
            }
            this.P = K;
        }
        if (!acv.K) {
            this.e = true;
            this.k = null;
            this.j = null;
        }
    }

    private void d(Graphics graphics) {
        graphics.setColor(25695);
        int n2 = acv.K ? -27 : 10;
        graphics.fillRect(this.P + 2, this.Q + n2, this.R, this.S - 8);
        graphics.setColor(16774720);
        graphics.drawRect(this.P + 2, this.Q + n2, this.R, this.S - 8);
    }

    private void e(Graphics graphics) {
        acv.a(graphics);
        if (I >= 300) {
            this.d(graphics);
            if (!this.e && !acv.K) {
                return;
            }
        } else {
            if (!this.e && !acv.K) {
                return;
            }
            this.d(graphics);
        }
        int n2 = acv.K ? 24 : 0;
        this.a.a(this.d.length, aae.ao - 2, this.P, this.Q - n2, this.R - 2, this.S - 10, true, 0);
        this.a.a(graphics, this.P, this.Q + (acv.K ? 2 : 10) - n2, this.R - 2, this.S - 15);
        int n3 = 0;
        while (n3 < this.d.length) {
            if (!this.d[n3].equals("")) {
                byte by2 = (byte)(this.d[n3].charAt(0) - 48);
                int n4 = 1;
                if (!nu.a(this.d[n3].charAt(0))) {
                    by2 = 0;
                    n4 = 0;
                }
                d.j[by2 >= 5 ? (byte)0 : by2].a(graphics, this.d[n3].substring(n4), this.P + 8, this.Q + n3 * (aae.ao - 3) + (acv.K ? 6 : 14) - (n2 + (acv.K ? 2 : 0)), 0);
            }
            ++n3;
        }
    }

    public final void a(Graphics graphics) {
        ql ql2;
        int n2;
        po po2;
        Object object;
        if (this.c != null) {
            this.c.a(graphics);
        }
        if (this.f == 5) {
            object = graphics;
            po2 = this;
            yi.a(object, K, L, I, J, 25, po2.Y, false, po2.V - 28);
            acv.a(object);
            po2.N.a(G.size(), po2.T, K, L + 28 + po2.V, I - 2, 27, false, 0);
            po2.N.a((Graphics)object, K + 2, L + 23 + po2.V, I - 4, 31);
            n2 = 0;
            while (n2 < G.size()) {
                if (po2.b == n2) {
                    object.setColor(0x636363);
                    object.fillRect(K + po2.T * n2, L + 23 + po2.V, po2.T, 28);
                }
                ql2 = (ql)G.elementAt(n2);
                ql2.a((Graphics)object, K + po2.T * n2 + po2.T / 2, L + 36 + po2.V);
                object.setColor(14527502);
                object.fillRect(K + po2.T * n2, L + 22 + po2.V, 1, 29);
                if (w != null && po.w.i == ql2.i) {
                    object.setColor(3268607);
                    object.drawRect(K + po2.T * n2 + 1, L + 23 + po2.V, po2.T - 2, 28);
                    object.drawRect(K + po2.T * n2 + 2, L + 24 + po2.V, po2.T - 4, 26);
                }
                ++n2;
            }
            object.setColor(14527502);
            object.fillRect(K + po2.T * G.size(), L + 22 + po2.V, 1, 29);
            acv.a(object);
        } else if (this.f == 6) {
            object = graphics;
            po2 = this;
            yi.a(object, K, L, I, J, 25, po2.Y, false, po2.V - 28);
            acv.a(object);
            if (po2.F != 2) {
                po2.N.a(G.size(), po2.T, K, L + 28 + po2.V, I - 2, 27, false, 0);
                po2.N.a((Graphics)object, K + 2, L + 23 + po2.V, I - 4, 31);
                n2 = 0;
                while (n2 < G.size()) {
                    if (po2.b == n2) {
                        object.setColor(0x636363);
                        object.fillRect(K + po2.T * n2, L + 23 + po2.V, po2.T, 28);
                    }
                    ql2 = (ql)G.elementAt(n2);
                    ql2.a((Graphics)object, K + po2.T * n2 + po2.T / 2, L + 36 + po2.V);
                    object.setColor(14527502);
                    object.fillRect(K + po2.T * n2, L + 22 + po2.V, 1, 29);
                    ++n2;
                }
                object.setColor(14527502);
                object.fillRect(K + po2.T * G.size(), L + 22 + po2.V, 1, 29);
            }
            if (B != null && po2.F == 2) {
                object.setColor(0x636363);
                object.fillRect(K + 3, L + 23 + po2.V, po2.T - 3, 28);
                yi.a(object, (int)yi.a((short)po.B.a).l, K + po2.T / 2, L + 36 + po2.V);
                d.i[3].a((Graphics)object, String.valueOf(po.B.c), K + po2.T / 2 + po2.r / 2, L + 50 + po2.V - d.i[3].b(), 1);
                object.setColor(14527502);
                object.fillRect(K + po2.T, L + 22 + po2.V, 1, 29);
            }
            acv.a(object);
            object.setColor(15338765);
            object.fillRect(po2.ar[po2.F] - 1, po2.as[po2.F] - 11, po2.r + 2, po2.r + 2);
        }
        acv.a(graphics);
        this.f(graphics);
        this.b(graphics);
        this.c(graphics);
        if (this.f == 5) {
            this.b(graphics);
            object = graphics;
            po2 = this;
            object.setClip(po2.ar[4] + 4, po2.as[4] - 15 + po2.p + 3, po2.r, po2.r);
            if (po2.ai == 0) {
                yi.e(object, sc.l[po2.ah].e, po2.ar[4] + 4, po2.as[4] - 6, 0);
            } else {
                ko.a(object, (short)(po2.ah + 6500), po2.ar[4] + 12, po2.as[4] + 2, 3);
                d.i[3].a((Graphics)object, String.valueOf(po2.aj), po2.ar[4] + 18, po2.as[4] + 2, 1);
            }
        }
        int n3 = 0;
        while (n3 < this.Z.size()) {
            object = (di)this.Z.elementAt(n3);
            object.a(graphics);
            ++n3;
        }
        this.e(graphics);
        super.a(graphics);
    }

    private void f(Graphics graphics) {
        if (this.f == 5) {
            int n2 = 0;
            while (n2 < this.ar.length) {
                if (n2 % 4 == 0) {
                    graphics.setColor(0xFFFFFF);
                    graphics.fillRect(this.ar[n2], this.as[n2] - 10, this.r, this.r);
                    graphics.setColor(0x636363);
                    graphics.fillRect(this.ar[n2] + 1, this.as[n2] - 9, this.r - 2, this.r - 2);
                } else if (yi.c(10) != null) {
                    graphics.drawRegion(yi.c(10), 0, this.aa * yb.b[1][10], (int)yb.b[0][10], (int)yb.b[1][10], 0, this.ar[n2] - 3, this.as[n2] - 10, 0);
                }
                ++n2;
            }
        } else if (this.f == 6) {
            int n3 = 0;
            while (n3 < this.ar.length) {
                graphics.setColor(0xFFFFFF);
                graphics.fillRect(this.ar[n3], this.as[n3] - 10, this.r, this.r);
                graphics.setColor(0x636363);
                graphics.fillRect(this.ar[n3] + 1, this.as[n3] - 9, this.r - 2, this.r - 2);
                ++n3;
            }
        }
        if (this.f != 1) {
            if (w != null && this.f != 5 && this.f != 6) {
                w.a(graphics, this.ar[0] + 10, this.as[0]);
            }
            if (this.f == 6) {
                if (w != null) {
                    w.a(graphics, this.ar[5] + this.r / 2, this.as[5] + 1);
                    this.af[5] = true;
                }
                if (this.x != null) {
                    this.x.a(graphics, this.ar[1] + this.r / 2, this.as[1] + 1);
                    this.af[0] = true;
                }
                if (this.v[1]) {
                    yi.a(graphics, (int)yi.a((short)po.B.a).l, this.ar[2] + this.r / 2, this.as[2]);
                    this.af[1] = true;
                }
                if (this.y != null) {
                    this.y.a(graphics, this.ar[3] + this.r / 2, this.as[3] + 1);
                    this.af[2] = true;
                }
                if (this.z != null) {
                    this.z.a(graphics, this.ar[4] + this.r / 2, this.as[4] + 1);
                    this.af[3] = true;
                }
                if (this.A != null) {
                    this.A.a(graphics, this.ar[6] + this.r / 2, this.as[6] + 1);
                    this.af[4] = true;
                }
            }
            if (w != null && this.f == 5) {
                acv.a(graphics);
                graphics.setClip(this.ar[0], this.as[0] - this.r / 4 + this.q - 5, this.r, this.r);
                w.a(graphics, this.ar[0] + 11, this.as[0] + 1);
            }
        }
    }

    public final void d() {
        Object object = this;
        if (((po)object).g && acv.l % 2 == 0 && ((po)object).p > 0) {
            --((po)object).p;
            ++((po)object).q;
        }
        object = this;
        int n2 = yb.b[0][10] / 2;
        int n3 = 0;
        while (n3 < ((po)object).al.size()) {
            Object object2;
            gc gc2 = (gc)((po)object).al.elementAt(n3);
            if (gc2 != null && ((po)object).f == 5) {
                if (((po)object).p > 4) {
                    object2 = object;
                    int n4 = yb.b[0][10] / 2;
                    int n5 = 0;
                    while (n5 < 2) {
                        te te2 = new te(((po)object2).ar[2] + n4 - 4, ((po)object2).ac + ((po)object2).r / 2 - 10, n5 * 2, ((po)object2).as[4] + ((po)object2).r / 2 - 10, 0);
                        ((po)object2).ak.addElement(te2);
                        ++n5;
                    }
                }
                if (acv.l % 2 == 0) {
                    object2 = new di(((po)object).ar[2] + n2 - 4, ((po)object).ac + ((po)object).r / 2 - 10, 54);
                    ((po)object).ak.addElement(object2);
                }
            }
            gc2.b();
            if (acv.l % 2 == 0) {
                object2 = new di(gc2.a, gc2.b, 54);
                ((po)object).ak.addElement(object2);
            }
            if (gc2.e) {
                ((po)object).al.removeElement(gc2);
                ((po)object).ab = true;
                ((po)object).i = true;
            }
            ++n3;
        }
        object = this;
        n2 = 0;
        while (n2 < ((po)object).ak.size()) {
            di di2 = (di)((po)object).ak.elementAt(n2);
            if (di2 != null) {
                di2.a();
            }
            if (di2.i && di2.g == -1) {
                ((po)object).g = true;
                ((po)object).ak.removeElement(di2);
            }
            if (di2.i) {
                ((po)object).ak.removeElement(di2);
            }
            ++n2;
        }
        if (this.f == 6 && this.o) {
            this.k = this.ae;
            this.u.a = "";
            if (this.az >= 0) {
                --this.az;
            }
            if (this.az < 0 && this.al.size() <= 0) {
                this.az = 15;
                this.g();
                this.o = false;
            }
        }
        if (acv.K && G.size() > 0) {
            this.f();
        }
        if (this.aA >= 0) {
            --this.aA;
        }
        if (this.aA < 0 && this.i && acv.l % 15 == 0 && this.aB < 3 && this.f == 6) {
            object = null;
            n2 = 1;
            while (n2 < this.ar.length) {
                object = new pn(this.ar[n2] + this.r / 2, this.as[n2], this.ar[0] + this.r / 2, this.as[0]);
                this.Z.addElement(object);
                ++n2;
            }
            ++this.aB;
        }
        int n6 = 0;
        while (n6 < this.Z.size()) {
            di di3 = (di)this.Z.elementAt(n6);
            di3.a();
            if (di3.i) {
                di di4 = null;
                di4 = null;
                int n7 = 0;
                while (n7 < 10) {
                    di4 = new te(this.ar[0] + this.r / 2, this.as[0], 0, this.as[0] + 80, -1);
                    this.ak.addElement(di4);
                    ++n7;
                }
                di4 = new di(this.ar[0] + this.r / 2, this.as[0], 50);
                this.ak.addElement(di4);
                this.ay.addElement(di3);
            }
            ++n6;
        }
        n6 = 0;
        while (n6 < this.ay.size()) {
            this.Z.removeElement(this.ay.elementAt(n6));
            ++n6;
        }
        this.ay.removeAllElements();
        if (this.f == 5 || this.f == 6) {
            this.D = 1;
        }
        this.aa = (byte)(this.aa + 1);
        if (this.aa >= 3) {
            this.aa = 0;
        }
        if (this.ab && this.ak.size() <= 0 && this.f == 5) {
            if (this.az >= 0) {
                --this.az;
            }
            if (this.az < 0) {
                w = null;
                this.ab = false;
                this.h = false;
                this.az = 15;
                this.t.a = "Nghi\u1ec1n";
                this.k = this.s;
                this.h();
            }
        }
        if (this.c != null) {
            this.c.d();
        }
        if (this.E > 0) {
            this.E = (byte)(this.E - 1);
        }
        if (this.E <= 0) {
            this.E = 0;
        }
        n6 = 0;
        boolean bl2 = false;
        if (acv.w == null) {
            if (acv.K) {
                if (this.a.f) {
                    this.N.f = false;
                    this.O.f = false;
                } else if (this.N.f) {
                    this.a.f = false;
                    this.O.f = false;
                } else if (this.O.f) {
                    this.N.f = false;
                    this.a.f = false;
                }
                if (this.N.f && !acv.u.a && this.E == 0) {
                    this.D = 1;
                    aca aca2 = this.N.b();
                    if (aca2.a || aca2.c) {
                        this.b = aca2.b;
                        this.U = aca2.b;
                        n6 = 1;
                        if (this.l != null && this.e && acv.j()) {
                            this.l.b.a();
                        }
                    }
                }
                this.N.c();
                if (this.O.f && !acv.u.a && this.E == 0) {
                    this.D = 0;
                    aca aca3 = this.O.b();
                    if (aca3.a || aca3.c) {
                        this.W = aca3.b;
                        this.X = aca3.b;
                        bl2 = true;
                        if (this.l != null && this.e && acv.j()) {
                            this.l.b.a();
                        }
                    }
                }
                this.O.c();
                Object var3_11 = null;
                if (this.a.f && !acv.u.a) {
                    this.a.b();
                }
                this.a.c();
                if (!acv.f) {
                    if (this.N.f && n6 != 0) {
                        if (this.b > -1 && this.b <= G.size() - 1) {
                            if (this.U != -1) {
                                this.b = this.U;
                            }
                            this.s.b.a();
                        }
                    } else if (this.O.f && bl2 && this.W > -1 && this.W <= M.size() - 1) {
                        if (this.X != -1) {
                            this.W = this.X;
                        }
                        this.s.b.a();
                    }
                    this.a.f = false;
                    this.N.f = false;
                    this.O.f = false;
                }
            } else if (!this.e) {
                aca aca4 = this.N.b();
                if (aca4.a || aca4.c) {
                    this.b = aca4.b;
                    this.N.a(this.b * (this.T + 2));
                }
                this.N.c();
                aca4 = this.O.b();
                if (aca4.a || aca4.c) {
                    this.W = aca4.b;
                    this.O.a(this.W * (this.T + 2));
                }
                this.O.c();
            } else {
                this.a.b();
                Object var3_13 = null;
                this.a.c();
            }
        }
        if (acv.g && this.f == 6) {
            acv.g = false;
            int n8 = 1;
            while (n8 < this.ar.length) {
                if (acv.j >= this.ar[n8] && acv.j <= this.ar[n8] + this.r && acv.k >= this.as[n8] - 10 && acv.k <= this.as[n8] - 10 + this.r) {
                    this.F = (byte)n8;
                    this.b = 0;
                    this.N.a(this.T);
                }
                ++n8;
            }
        }
        super.d();
    }

    public final void c() {
        if (this.e) {
            if (acv.c[2] && this.f != 5) {
                acv.c[2] = false;
                this.a.a -= 50;
                if (this.a.a < 0) {
                    this.a.a = 0;
                }
            } else if (acv.c[8]) {
                acv.c[8] = false;
                this.a.a += 50;
                if (this.a.a > this.a.c) {
                    this.a.a = this.a.c;
                }
            }
        } else {
            if (acv.c[2] && this.f != 5) {
                acv.c[2] = false;
                this.F = (byte)(this.F - 1);
                if (this.x != null) {
                    this.ag.addElement(this.x);
                }
                if (this.y != null) {
                    this.ag.addElement(this.y);
                }
                if (this.z != null) {
                    this.ag.addElement(this.z);
                }
                if (w != null) {
                    this.ag.addElement(w);
                }
                if (this.A != null) {
                    this.ag.addElement(this.A);
                }
                if (this.F < 1) {
                    this.F = (byte)6;
                }
                if (this.f == 6) {
                    this.b = 0;
                    this.N.a(this.T);
                }
            } else if (acv.c[8]) {
                acv.c[8] = false;
                if (this.x != null) {
                    this.ag.addElement(this.x);
                }
                if (this.y != null) {
                    this.ag.addElement(this.y);
                }
                if (this.z != null) {
                    this.ag.addElement(this.z);
                }
                if (w != null) {
                    this.ag.addElement(w);
                }
                if (this.A != null) {
                    this.ag.addElement(this.A);
                }
                this.F = (byte)(this.F + 1);
                if (this.F > 6) {
                    this.F = 1;
                }
                if (this.f == 6) {
                    this.b = 0;
                    this.N.a(this.T);
                }
            }
            if (acv.c[4]) {
                acv.c[4] = false;
                --this.b;
                if (this.b < 0) {
                    this.b = G.size() - 1;
                }
                this.N.a(this.b * this.T);
            } else if (acv.c[6]) {
                acv.c[6] = false;
                ++this.b;
                if (this.b > G.size() - 1) {
                    this.b = 0;
                }
                this.N.a(this.b * this.T);
            }
        }
        super.c();
    }

    private void a(boolean bl2, boolean n2) {
        Object object;
        M.removeAllElements();
        G.removeAllElements();
        Vector vector = sc.g;
        n2 = vector.size();
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        int n3 = 0;
        while (n3 < n2) {
            gz gz2 = (gz)vector.elementAt(n3);
            xv xv2 = yi.a(gz2.a);
            if (this.f == 0) {
                if (xv2.h == xv.b) {
                    if (am != null && po.am.a == gz2.a && !po.am.d) {
                        am = gz2;
                        bl3 = true;
                    }
                    M.addElement(gz2);
                }
            } else if (this.f == 2) {
                if (xv2.h == xv.c) {
                    if (am != null && po.am.a == gz2.a && !po.am.d) {
                        am = gz2;
                    }
                    M.addElement(gz2);
                }
            } else if (this.f == 3) {
                if (xv2.h == xv.d) {
                    if (am != null && po.am.a == gz2.a && !po.am.d) {
                        am = gz2;
                    }
                    M.addElement(gz2);
                }
            } else if (xv2.h == 0) {
                if (am != null && po.am.a == gz2.a && !po.am.d) {
                    am = gz2;
                    bl3 = true;
                }
                if (ap != null && po.ap.a == gz2.a && !po.ap.d) {
                    ap = gz2;
                    bl4 = true;
                }
                if (aq != null && po.aq.a == gz2.a && !po.aq.d) {
                    aq = gz2;
                    bl5 = true;
                }
                if (this.f == 6) {
                    B = gz2;
                }
                M.addElement(gz2);
            }
            ++n3;
        }
        vector = sc.h;
        n2 = vector.size();
        n3 = 0;
        boolean bl6 = false;
        boolean bl7 = false;
        int n4 = 0;
        while (n4 < n2) {
            object = (gz)vector.elementAt(n4);
            xv xv3 = yi.a(((gz)object).a);
            if (this.f == 0) {
                if (xv3.h == xv.b) {
                    if (am != null && po.am.a == ((gz)object).a && po.am.d) {
                        am = object;
                        n3 = 1;
                    }
                    M.addElement(object);
                    ((gz)object).d = true;
                }
            } else if (this.f == 2) {
                if (xv3.h == xv.c) {
                    if (am != null && po.am.a == ((gz)object).a && po.am.d) {
                        am = object;
                    }
                    M.addElement(object);
                    ((gz)object).d = true;
                }
            } else if (this.f == 3) {
                if (xv3.h == xv.d) {
                    if (am != null && po.am.a == ((gz)object).a && po.am.d) {
                        am = object;
                    }
                    M.addElement(object);
                    ((gz)object).d = true;
                }
            } else if (xv3.h == 0) {
                if (am != null && po.am.a == ((gz)object).a && po.am.d) {
                    am = object;
                    n3 = 1;
                }
                if (ap != null && po.ap.a == ((gz)object).a && po.ap.d) {
                    ap = object;
                    bl6 = true;
                }
                if (aq != null && po.aq.a == ((gz)object).a && po.aq.d) {
                    aq = object;
                    bl7 = true;
                }
                M.addElement(object);
                ((gz)object).d = true;
            }
            ++n4;
        }
        if (this.f == 0) {
            if (!bl3 && n3 == 0) {
                am = null;
            }
            if (!bl4 && !bl6) {
                ap = null;
            }
            if (!bl5 && !bl7) {
                aq = null;
            }
        }
        G = this.f == 0 ? ql.c(aG) : (this.f == 2 ? ql.a(aG, 1) : (this.f == 3 ? ql.d(0) : ql.g()));
        n4 = 0;
        while (n4 < G.size()) {
            object = (ql)G.elementAt(n4);
            if (0 == ((ql)object).i) {
                w = object;
                return;
            }
            ++n4;
        }
    }

    private void g() {
        am = null;
        this.u.a = "\u0110\u1eadp";
        this.k = this.s;
        this.i = false;
        this.aB = 0;
        this.x = null;
        B = null;
        this.y = null;
        this.z = null;
        this.A = null;
        this.ag.removeAllElements();
        int n2 = 0;
        while (n2 < this.af.length) {
            this.af[n2] = false;
            this.v[n2] = false;
            ++n2;
        }
        w = null;
        this.a(true, false);
    }

    private void h() {
        if (this.f == 5) {
            this.k = this.s;
        }
        this.i = false;
        this.aB = 0;
        this.o = false;
        this.g();
        this.F = (byte)5;
        this.h = false;
        ap = null;
        aq = null;
        w = null;
        this.a(true, false);
    }

    public final void a() {
        super.a();
    }

    public final void a(aae aae2) {
        this.c = aae2;
        super.a(aae2);
    }

    static boolean a(po po2) {
        int n2 = 0;
        while (n2 < po2.af.length) {
            if (!po2.af[n2]) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    static void b(po po2) {
        gc gc2 = null;
        gc2 = new gc(po2.ar[6] + po2.r / 2, po2.as[6], po2.ar[1] + po2.r / 2, po2.as[1], 100, 3, 3);
        po2.al.addElement(gc2);
        int n2 = 1;
        while (n2 < po2.ar.length - 1) {
            gc2 = new gc(po2.ar[n2] + po2.r / 2, po2.as[n2], po2.ar[n2 + 1] + po2.r / 2, po2.as[n2 + 1], 100, 3, 6 + (n2 << 1));
            po2.al.addElement(gc2);
            po2.aA = 6 + (n2 << 1);
            ++n2;
        }
    }

    static boolean a(po po2, ql ql2) {
        switch (po2.F) {
            case 1: {
                if (!(w != null && po.w.i == ql2.i || po2.y != null && po2.y.i == ql2.i || po2.z != null && po2.z.i == ql2.i) && (po2.A == null || po2.A.i != ql2.i)) break;
                return true;
            }
            case 3: {
                if (!(w != null && po.w.i == ql2.i || po2.x != null && po2.x.i == ql2.i || po2.z != null && po2.z.i == ql2.i) && (po2.A == null || po2.A.i != ql2.i)) break;
                return true;
            }
            case 4: {
                if (!(w != null && po.w.i == ql2.i || po2.y != null && po2.y.i == ql2.i || po2.x != null && po2.x.i == ql2.i) && (po2.A == null || po2.A.i != ql2.i)) break;
                return true;
            }
            case 5: {
                if (!(po2.x != null && po2.x.i == ql2.i || po2.y != null && po2.y.i == ql2.i || po2.z != null && po2.z.i == ql2.i) && (po2.A == null || po2.A.i != ql2.i)) break;
                return true;
            }
            case 6: {
                if (!(w != null && po.w.i == ql2.i || po2.y != null && po2.y.i == ql2.i || po2.z != null && po2.z.i == ql2.i) && (po2.x == null || po2.x.i != ql2.i)) break;
                return true;
            }
        }
        return false;
    }
}

