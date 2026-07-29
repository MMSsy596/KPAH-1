/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class kj
extends aae {
    private mm i;
    private mm o;
    public static Vector a = new Vector();
    private static String[] p = new String[]{"Ch\u1ee3", "T\u00ecm", "\u0110ang b\u00e1n", "H\u00e0nh trang", "Kho", "Bid"};
    public static short[] b = new short[6];
    public static short[] c = new short[6];
    private static String[][] q = new String[][]{{"Lo\u1ea1i:", "Level:", "Ph\u1ea9m:", "M\u00e0u:", "C\u1ed9ng:"}, {"N.v\u1eadt"}, new String[0]};
    private static String[] r = new String[]{"\u00e1o", "Qu\u1ea7n", "N\u00f3n", "Ki\u1ebfm", "\u0110ao", "B\u00fat", "B\u00faa", "Cung", "Nh\u1eabn", "D\u00e2y chuy\u1ec1n", "Gi\u00e0y", "G\u0103ng tay", "Ng\u1ecdc b\u1ed9i", "Gi\u00e1p linh th\u00fa", "H\u1ed9 uy\u1ec3n", "N\u00f3n linh th\u00fa", "B\u00e0n \u0111\u1ea1p", "Y\u00ean c\u01b0\u01a1ng", "Phi phong"};
    private static byte[] s;
    private static String[] t;
    private static String[] u;
    private static short v;
    public int d;
    private int w;
    private int x;
    private int y;
    private int z;
    private int A;
    private int B;
    private int C;
    private int D;
    private int E;
    private int F;
    private int G;
    private int H;
    private int I = 1;
    private int J;
    private int K;
    private int L = 0;
    private s M;
    private s N;
    private s O;
    public aae e;
    private boolean P = true;
    private boolean Q;
    private byte R = 0;
    private String[] S = new String[]{""};
    public String f = "";
    public boolean g;
    private int T;
    private int U;
    private Image V = null;
    private byte[] W;
    public static long h;

    static {
        byte[] byArray = new byte[19];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 3;
        byArray[4] = 4;
        byArray[5] = 5;
        byArray[6] = 6;
        byArray[7] = 7;
        byArray[8] = 8;
        byArray[9] = 9;
        byArray[10] = 10;
        byArray[11] = 11;
        byArray[12] = 12;
        byArray[13] = 14;
        byArray[14] = 15;
        byArray[15] = 16;
        byArray[16] = 17;
        byArray[17] = 18;
        byArray[18] = 19;
        s = byArray;
        t = new String[]{"Nh\u1ea5t ph\u1ea9m", "Nh\u1ecb ph\u1ea9m", "Tam ph\u1ea9m", "T\u1ee9 ph\u1ea9m", "Kh\u00e1c"};
        u = new String[]{"Tr\u1eafng", "Xanh", "\u0110\u1ecf", "Ho\u00e0n m\u1ef9"};
        h = 0L;
    }

    public final void e() {
        this.d = -1;
        this.w = 0;
        this.G = 1;
        this.P = true;
        this.Q = !acv.K;
        this.H = 0;
        this.J = 0;
        this.K = 0;
        this.I = 1;
        this.g = false;
        this.S = new String[]{""};
        this.i.a();
        this.o.a();
        this.l();
    }

    public kj() {
        this.M = new s("", new dg(this));
        a.addElement(new Vector());
        a.addElement(new Vector());
        a.addElement(new Vector());
        a.addElement(new Vector());
        a.addElement(new Vector());
        a.addElement(new Vector());
    }

    public final void a() {
        this.b();
        super.a();
    }

    public final void a(aae aae2) {
        this.e = aae2;
        this.b();
        super.a(aae2);
    }

    public final void b() {
        if (acv.K) {
            kj.q[1][0] = "Nh\u00e2n v\u1eadt";
        }
        if (this.V == null) {
            try {
                this.V = Image.createImage((String)"/0.png");
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        int n2 = acv.m - 6;
        int n3 = acv.n - 30;
        if (n2 > 320) {
            n2 = 320;
        }
        if (n3 > 240) {
            n3 = 240;
        }
        this.z = n2;
        this.A = n3;
        this.x = acv.m / 2 - this.z / 2;
        this.y = acv.n / 2 - this.A / 2 - 10;
        if (this.y < 8) {
            this.y = 8;
        }
        this.D = acv.m - 40;
        if (this.D > 200) {
            this.D = 200;
        }
        this.E = acv.n - 80;
        if (this.E > 180) {
            this.E = 180;
        }
        this.B = acv.m / 2 - this.D / 2;
        this.C = acv.n / 2 - this.E / 2;
        if (this.C < 5) {
            this.C = 5;
        }
        n2 = 0;
        while (n2 < q[0].length) {
            if (v < d.j[0].a(q[0][n2])) {
                v = (short)d.j[0].a(q[0][n2]);
            }
            ++n2;
        }
        v = (short)(v + 5);
        this.d = -1;
        this.Q = !acv.K;
        this.g = false;
        this.H = 0;
        this.J = 0;
        this.K = 0;
        this.I = 1;
        this.k = new s(acv.K ? "" : "Ch\u1ecdn", new df(this));
        this.l = new s("\u0110\u00f3ng", new dd(this));
        this.N = new s("Menu", new cx(this));
        this.O = new s("Th\u00f4ng tin ", new cw(this));
        this.i = new mm();
        this.i.a();
        this.o = new mm();
        this.o.a();
        this.l();
        super.b();
        if (acv.K) {
            this.l();
            this.A -= 10;
        }
    }

    public final void a(Vector vector) {
        if (this.Q) {
            return;
        }
        this.g = false;
        vector.addElement(new s("B\u00e1n", new cv(this)));
        vector.addElement(new s("B\u00e1n \u0111\u1ea5u gi\u00e1", new cs(this)));
    }

    public final void a(int n2, ql ql2) {
        if (ql2.e.equals(acv.s.t.an)) {
            acv.a("Kh\u00f4ng th\u1ec3 mua v\u1eadt ph\u1ea9m do m\u00ecnh \u0111\u0103ng b\u00e1n");
            return;
        }
        acv.b("B\u1ea1n c\u00f3 mu\u1ed1n mua v\u1eadt ph\u1ea9m n\u00e0y kh\u00f4ng ?", new kb(this, n2));
    }

    protected final void f() {
        String[] stringArray = (String[])a.elementAt(this.G);
        if (this.Q && !acv.K || stringArray.size() == 0) {
            return;
        }
        this.w = 0;
        ql ql2 = null;
        if (this.d >= 0 && this.d < stringArray.size()) {
            ql2 = (ql)stringArray.elementAt(this.d);
            if (!ql2.F) {
                ql2.F = true;
                go.a().a(ql2.i, acv.s.t.cH);
            }
        }
        stringArray = new String[]{""};
        if (ql2 != null) {
            if (ql2.J > 0) {
                this.W = new byte[ql2.J];
                int n2 = 0;
                while (n2 < ql2.J) {
                    this.W[n2] = -1;
                    ++n2;
                }
                n2 = 0;
                int n3 = 0;
                while (n3 < ql2.H.size()) {
                    zu zu2 = (zu)ql2.H.elementAt(n3);
                    if (zu2.a(false) == 3) {
                        n3 = 0;
                        while (n3 < ql2.I) {
                            int n4 = n2;
                            n2 = (byte)(n4 + 1);
                            this.W[n4] = yi.ad[zu2.c() - 10];
                            ++n3;
                        }
                        break;
                    }
                    ++n3;
                }
            }
            stringArray = d.j[0].a(ql2.a(false), this.D - 10);
        }
        v1.S = new String[]{""};
        this.S = stringArray;
        this.g = true;
    }

    protected final void g() {
        Object object = (Vector)a.elementAt(((kj)((Object)ql2)).G);
        if (((kj)((Object)ql2)).d >= 0 && ((kj)((Object)ql2)).d < ((Vector)object).size()) {
            ql ql2 = (ql)((Vector)object).elementAt(((kj)((Object)ql2)).d);
            if (ql.a(ql2.b().k[9]) || ql2.w > 0) {
                acv.a("Kh\u00f4ng th\u1ec3 b\u00e1n v\u1eadt ph\u1ea9m n\u00e0y");
                return;
            }
            object = new bz(10, 40, 120, aae.an);
            ((bz)object).c(3);
            ((bz)object).a = true;
            ((bz)object).a("100000");
            px.e().l = ((bz)object).e;
            bz bz2 = new bz(10, 75, 120, aae.an);
            bz2.c(3);
            bz2.a("10000");
            px.e().a(acv.o - 70, acv.n - aae.an - 120, 140, 100, "NH\u1eacP GI\u00c1 B\u00c1N", new s("B\u00e1n", new hv((bz)object, bz2, ql2)));
            px.e().b = new hj((bz)object, bz2);
            px.e().a(acv.J);
        }
    }

    private void a(Graphics graphics, int n2, int n3) {
        graphics.setColor(0x929292);
        graphics.fillRect(n2, n3, this.z + 2, 1);
    }

    public final void a(Graphics graphics) {
        if (this.e != null) {
            this.e.a(graphics);
        }
        acv.a(graphics);
        yi.c(graphics, this.x, this.y, this.z, this.A);
        graphics.setColor(0x797B79);
        graphics.fillRect(acv.o - 51, this.y + 7, 102, 22);
        graphics.setColor(!this.Q ? 0x242424 : 30611);
        graphics.fillRect(acv.o - 50, this.y + 8, 100, 20);
        d.j[0].a(graphics, p[this.G], acv.m / 2, this.y + 10, 2);
        graphics.drawImage(yi.D, acv.o - 40 + this.T, this.y + 17, 3);
        graphics.drawRegion(yi.D, 0, 0, 11, 7, 2, acv.o + 40 + this.U, this.y + 17, 3);
        d.i[0].a(graphics, String.valueOf(h) + "$", this.x + this.z - 4, this.y + this.A - 12, 1);
        int n2 = 0;
        int n3 = 0;
        if (this.G == 1) {
            n2 = aae.ao + 10;
            n3 = this.y + 40;
            if (this.P) {
                this.i.a(q[0].length, n2, this.x + 6, this.y + 40, this.z, this.A - 40, true, 0);
                this.i.a(graphics, this.x + 6, this.y + 40, this.z - 12, this.A - 55);
                int n4 = 0;
                while (n4 < q[this.R].length) {
                    d.j[0].a(graphics, q[this.R][n4], this.x + 6, n3 + n4 * n2, 0);
                    ++n4;
                }
                String[][] stringArrayArray = new String[][]{{r[this.H], String.valueOf(this.I), t[this.J], u[this.K], String.valueOf(this.L)}, {this.f.equals("") ? "Nh\u1eadp t\u00ean" : this.f}};
                int n5 = this.K;
                if (this.K == 1) {
                    n5 = 3;
                } else if (this.K == 3) {
                    n5 = 1;
                }
                int n6 = 0;
                while (n6 < stringArrayArray[this.R].length) {
                    int n7 = n6;
                    d d2 = d.j[n6 == 3 ? n5 : 0];
                    String string = stringArrayArray[this.R][n6];
                    int n8 = n3 + n6 * n2;
                    int n9 = acv.m / 2 - 40 <= this.x + 6 + v ? this.x + 6 + v : acv.m / 2 - 40;
                    Graphics graphics2 = graphics;
                    kj kj2 = this;
                    graphics2.drawImage(yi.D, n9 + 100 + (kj2.d == n7 ? kj2.F : 0), n8 + 7, 3);
                    graphics2.drawRegion(yi.D, 0, 0, 11, 7, 2, n9 - (kj2.d == n7 ? kj2.F : 0), n8 + 7, 3);
                    d2.a(graphics2, string, n9 + 50, n8, 2);
                    ++n6;
                }
            } else {
                this.a(graphics, (Vector)a.elementAt(1), true);
            }
        } else {
            this.a(graphics, (Vector)a.elementAt(this.G), false);
        }
        if (this.g) {
            this.b(graphics);
        }
        super.a(graphics);
    }

    private void a(Graphics graphics, Vector vector, boolean bl2) {
        int n2 = aae.ao * 3 + 10;
        this.i.a(vector.size(), n2, this.x + 6, this.y + 40, this.z, this.A - 40, true, 0);
        this.i.a(graphics, this.x + 6, this.y + 40, this.z - 12, this.A - 55);
        yc yc2 = null;
        String[] stringArray = null;
        boolean bl3 = false;
        int n3 = 0;
        while (n3 < vector.size()) {
            stringArray = (String[])vector.elementAt(n3);
            int n4 = this.y + 40 + n3 * n2;
            yc2 = yi.b((int)stringArray.r);
            if (n4 + n2 - this.i.b >= this.y + 30 && n4 - this.i.b <= this.y + this.A) {
                if (this.d == n3) {
                    graphics.setColor(34949);
                    graphics.fillRect(this.x - 1, n4 - 5, this.z - 2, n2);
                    bl3 = true;
                }
                if (stringArray.N > 0) {
                    graphics.drawImage(this.V, this.x + 12, n4 + 20, 0);
                }
                this.a(graphics, this.x, n4 - 5);
                graphics.drawImage(yi.t, this.x + 6, n4, 0);
                stringArray = stringArray.c();
                int n5 = 0;
                while (n5 < stringArray.length) {
                    byte by2 = (byte)(stringArray[n5].charAt(0) - 48);
                    int n6 = 1;
                    if (!nu.a(stringArray[n5].charAt(0))) {
                        by2 = 0;
                        n6 = 0;
                    }
                    d.j[by2 >= 5 ? (byte)0 : by2].a(graphics, stringArray[n5].substring(n6), 30 + this.x, n4 + n5 * aae.ao + 2, 0);
                    ++n5;
                }
                ko.a(graphics, yc2.h, 16 + this.x, n4 + 8);
            }
            ++n3;
        }
        this.a(graphics, this.x, this.y + 40 + vector.size() * n2 - 5);
        if (bl3 && (this.d == 0 || this.d == 1)) {
            acv.a(graphics);
            vector.elementAt(this.d);
        }
    }

    private void b(Graphics graphics) {
        acv.a(graphics);
        graphics.setColor(16774720);
        graphics.fillRect(this.B - 1, this.C - 1, this.D + 2, this.E + 2);
        graphics.setColor(25695);
        graphics.fillRect(this.B, this.C, this.D, this.E);
        this.o.a(this.S.length, aae.ao + 2, this.B, this.C, this.D, this.E, true, 0);
        this.o.a(graphics, this.B, this.C, this.D, this.E);
        int n2 = 0;
        int n3 = 0;
        while (n3 < this.S.length) {
            int n4;
            if (n3 == 1 && this.W != null && this.W.length > 0) {
                n4 = 0;
                while (n4 < this.W.length) {
                    graphics.drawImage(yi.M, this.B + 12 + n4 * 20, this.C + 12 + n2, 3);
                    if (this.W[n4] != -1) {
                        yi.a(graphics, (int)this.W[n4], this.B + 12 + n4 * 20, this.C + 12 + n2);
                    }
                    ++n4;
                }
                n2 += 18;
            }
            if (!this.S[n3].equals("")) {
                n4 = (byte)(this.S[n3].charAt(0) - 48);
                int n5 = 1;
                if (!nu.a(this.S[n3].charAt(0))) {
                    n4 = 0;
                    n5 = 0;
                }
                d.j[n4 >= 5 ? 0 : n4].a(graphics, this.S[n3].substring(n5), this.B + 4, this.C + 4 + n3 * (aae.ao + 2) + n2, 0);
            }
            ++n3;
        }
    }

    public final void d() {
        if (this.e != null) {
            this.e.d();
        }
        if (this.F > 0) {
            --this.F;
        }
        if (this.F <= 0) {
            this.F = 5;
        }
        if (this.T < 0) {
            ++this.T;
        }
        if (this.U > 0) {
            --this.U;
        }
        if (acv.w == null && !acv.u.a) {
            if (this.g) {
                aca aca2 = this.o.b();
                if (aca2.a || aca2.c) {
                    this.w = aca2.b;
                }
                this.o.c();
            } else {
                aca aca3 = this.i.b();
                if (aca3.a || aca3.c) {
                    this.d = aca3.b;
                }
                if (acv.a(this.x, this.y + 30, this.z, this.A - 30) && acv.g) {
                    acv.g = false;
                    if (aca3.b != -1 && this.k != null) {
                        this.k.b.a();
                    }
                }
                this.i.c();
            }
        }
        super.d();
    }

    public final void c() {
        int n2;
        if (this.Q) {
            if (acv.c[4]) {
                acv.c[4] = false;
                --this.G;
                if (this.G < 0) {
                    this.G = p.length - 1;
                }
                this.T = -5;
                this.i.a();
                this.d = -1;
                this.l();
            } else if (acv.c[6]) {
                acv.c[6] = false;
                ++this.G;
                if (this.G > p.length - 1) {
                    this.G = 0;
                }
                this.U = 5;
                this.i.a();
                this.d = -1;
                this.l();
            }
            if (acv.c[8]) {
                acv.c[8] = false;
                this.d = 0;
                this.Q = false;
                this.l();
            }
        } else {
            Vector vector = (Vector)a.elementAt(this.G);
            n2 = vector.size();
            if (this.P && this.G == 1) {
                if (acv.c[2]) {
                    acv.c[2] = false;
                    --this.d;
                    if (this.d <= -1) {
                        this.d = -1;
                        this.Q = true;
                        this.l();
                    }
                    this.i.a(this.d * this.i.e);
                } else if (acv.c[8]) {
                    acv.c[8] = false;
                    ++this.d;
                    if (this.d > q[this.R].length - 1) {
                        this.d = 0;
                    }
                    this.i.a(this.d * this.i.e);
                }
            } else if (!this.Q) {
                if (acv.c[2]) {
                    acv.c[2] = false;
                    if (this.g) {
                        --this.w;
                        if (this.w < 0) {
                            this.w = this.S.length - 1;
                        }
                        this.o.a(this.w * this.o.e);
                    } else {
                        --this.d;
                        if (this.d <= -1) {
                            this.d = -1;
                            this.Q = true;
                        }
                        this.l();
                        this.i.a(this.d * this.i.e);
                    }
                } else if (acv.c[8]) {
                    acv.c[8] = false;
                    if (this.g) {
                        ++this.w;
                        if (this.w > this.S.length - 1) {
                            this.w = 0;
                        }
                        this.o.a(this.w * this.o.e);
                    } else {
                        ++this.d;
                        if (this.d > n2 - 1) {
                            this.d = 0;
                        }
                        this.l();
                        this.i.a(this.d * this.i.e);
                    }
                }
            }
        }
        super.c();
        if (acv.w == null && !acv.u.a && !this.g) {
            switch (this.G) {
                case 0: {
                    if (!acv.g || !acv.a(acv.m / 2 - 40 <= this.x + 6 + v ? this.x + 6 + v : acv.m / 2 - 40, this.y + 30, 100, 4 * (aae.ao + 10))) break;
                    n2 = 0;
                    n2 = (acv.k - (this.y + 30)) / (aae.ao + 10);
                    acv.g = false;
                    this.l();
                    if (n2 < 0 || n2 > 3) break;
                    this.d = n2;
                    if (this.k == null) break;
                    this.k.b.a();
                }
            }
            if (acv.g) {
                this.Q = false;
                acv.g = false;
                if (acv.k >= this.y && acv.k <= this.y + 25) {
                    if (acv.j > this.x && acv.j < acv.m / 2) {
                        --this.G;
                        if (this.G < 0) {
                            this.G = p.length - 1;
                        }
                        this.T = -5;
                        this.i.a();
                        this.d = -1;
                        this.l();
                    } else if (acv.j < acv.m / 2 + this.z / 2 && acv.j > acv.m / 2) {
                        ++this.G;
                        if (this.G > p.length - 1) {
                            this.G = 0;
                        }
                        this.U = 5;
                        this.i.a();
                        this.d = -1;
                        this.l();
                    }
                }
                if (((Vector)a.elementAt(this.G)).size() > 0 && this.d == -1) {
                    this.d = 0;
                }
                this.l();
            }
        }
    }

    public final void h() {
        this.P = false;
        this.d = -1;
        if (this.R == 0) {
            go.a().b(this.I, s[this.H], this.K, this.J + 1, this.L);
            return;
        }
        if (this.R == 1) {
            go.a().g(this.f);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void i() {
        if (acv.q == px.e()) {
            return;
        }
        if (this.G == 1) {
            if (this.P) {
                Vector<s> vector = new Vector<s>();
                s s2 = null;
                if (this.R == 0) {
                    switch (this.d) {
                        case 0: {
                            int n2 = 0;
                            while (n2 < r.length) {
                                s2 = new s(r[n2], new ke(this));
                                vector.addElement(s2);
                                ++n2;
                            }
                            acv.u.a(vector, 2);
                            return;
                        }
                        case 1: {
                            acv.y.a("Nh\u1eadp s\u1ed1", new kd(this), 3, 3, true);
                            gy gy2 = acv.y;
                            acv.w = gy2;
                            return;
                        }
                        case 2: {
                            int n3 = 0;
                            while (n3 < t.length) {
                                s2 = new s(t[n3], new jx(this));
                                vector.addElement(s2);
                                ++n3;
                            }
                            acv.u.a(vector, 2);
                            return;
                        }
                        case 3: {
                            int n4 = 0;
                            while (n4 < u.length) {
                                s2 = new s(u[n4], new jv(this));
                                vector.addElement(s2);
                                ++n4;
                            }
                            acv.u.a(vector, 2);
                            return;
                        }
                        case 4: {
                            int n5 = 0;
                            while (n5 < 16) {
                                s2 = new s("C\u1ed9ng " + n5, new jz(this));
                                vector.addElement(s2);
                                ++n5;
                            }
                            acv.u.a(vector, 2);
                        }
                    }
                    return;
                }
                if (this.R != 1 || this.d <= -1) return;
                acv.y.a("Nh\u1eadp t\u00ean", new jy(this), 0, 25, true);
                gy gy3 = acv.y;
                acv.w = gy3;
                return;
            }
            if (acv.K) {
                Vector<s> vector = new Vector<s>();
                vector.addElement(this.O);
                acv.u.a(vector, 2);
                return;
            }
            this.f();
            return;
        }
        if (acv.K) {
            Vector<s> vector = new Vector<s>();
            vector.addElement(this.O);
            acv.u.a(vector, 2);
            return;
        }
        this.f();
    }

    public static void j() {
        a.setElementAt(ql.f(), 3);
    }

    public final boolean k() {
        Object object;
        if (((kj)object).G == 0) {
            if (((Vector)a.elementAt(((kj)object).G)).size() > 0 && !((kj)object).Q && ((kj)object).d < ((Vector)a.elementAt(((kj)object).G)).size()) {
                if (((kj)object).d < 0) {
                    return false;
                }
                object = (ql)((Vector)a.elementAt(((kj)object).G)).elementAt(((kj)object).d);
                if (((ql)object).N > 0) {
                    return true;
                }
            }
        } else if (((kj)object).G == 1 && !((kj)object).P && ((Vector)a.elementAt(((kj)object).G)).size() > 0 && !((kj)object).Q && ((kj)object).d < ((Vector)a.elementAt(((kj)object).G)).size()) {
            if (((kj)object).d < 0) {
                return false;
            }
            object = (ql)((Vector)a.elementAt(((kj)object).G)).elementAt(((kj)object).d);
            if (((ql)object).N > 0) {
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void l() {
        if (this.G == 3) {
            kj.j();
        }
        if (this.G == 0) {
            if (!this.k()) {
                if (this.Q) {
                    this.j = new s("", new ka(this));
                    return;
                }
                if (((Vector)a.elementAt(this.G)).size() <= 0) return;
                this.j = new s("Mua", new jo(this));
                return;
            }
            this.j = new s("Menu", new jn(this));
            return;
        }
        if (this.G == 1) {
            this.j = new s("Menu", new jg(this));
            return;
        }
        if (this.G == 2) {
            this.j = this.M;
            return;
        }
        if (this.G == 3) {
            this.j = this.N;
            return;
        }
        if (this.G == 4) {
            Vector vector = (Vector)a.elementAt(this.G);
            if (vector.size() > 0 || h > 0L) {
                this.j = new s("Menu", new in(this, vector));
                return;
            }
            this.j = new s("", new ia(this));
            return;
        }
        if (this.G != 5) return;
        if (((Vector)a.elementAt(this.G)).size() == 0 || this.Q) {
            this.j = new s("", new ie(this));
            return;
        }
        this.j = new s("Menu", new id(this));
    }

    public final void m() {
        if (this.g) {
            this.f();
        }
    }

    static int a(kj kj2) {
        return kj2.G;
    }

    static void a(kj kj2, int n2) {
        kj2.H = n2;
    }

    static void b(kj kj2, int n2) {
        kj2.I = n2;
    }

    static int b(kj kj2) {
        return kj2.I;
    }

    static void c(kj kj2, int n2) {
        kj2.J = n2;
    }

    static void d(kj kj2, int n2) {
        kj2.K = n2;
    }

    static void e(kj kj2, int n2) {
        kj2.L = n2;
    }

    static boolean c(kj kj2) {
        return kj2.Q;
    }

    static boolean d(kj kj2) {
        return kj2.P;
    }

    static byte e(kj kj2) {
        return kj2.R;
    }

    static void a(kj kj2, byte by2) {
        kj2.R = by2;
    }

    static void a(kj kj2, boolean bl2) {
        kj2.P = false;
    }
}

