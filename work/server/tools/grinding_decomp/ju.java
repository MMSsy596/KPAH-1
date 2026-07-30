/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class ju
extends aae {
    public static int a = 50;
    public static int b = 50;
    public static int c = 1;
    private int r = 0;
    private int s;
    private int t;
    private boolean u;
    private int v;
    private int w;
    private static String x = "";
    private bz y;
    private bz z;
    public boolean d;
    private boolean A;
    private boolean B;
    private boolean C;
    private boolean D;
    public boolean e;
    public boolean f;
    public boolean g;
    public boolean h;
    private s E;
    private s F;
    private s G;
    private s H;
    private static int I;
    private static int J;
    private static int K;
    private static int L;
    private static int M;
    public static boolean i;
    private String[] N = new String[]{"Ch\u1ebf \u0111\u1ed9 th\u01b0\u1eddng", "\u0110\u00e1nh qu\u00e1i ", "Ch\u1ecdn NPC", "Ch\u1ebf \u0111\u1ed9 chi\u1ebfm th\u00e0nh", "Ch\u1ebf \u0111\u1ed9 qu\u1ed1c chi\u1ebfn"};
    private String[] O = new String[]{"Ch\u1ecdn to\u00e0n b\u1ed9", "Ch\u1ec9 ch\u1ecdn qu\u00e1i, ng\u01b0\u1eddi trong ch\u1ebf \u0111\u1ed9 \u0111\u1ed3 s\u00e1t v\u00e0 kh\u00e1c qu\u1ed1c gia ", "Ch\u1ec9 ch\u1ecdn NPC", "Ch\u1ec9 ch\u1ecdn ng\u01b0\u1eddi kh\u00e1c bang", "Ch\u1ec9 ng\u01b0\u1eddi kh\u00e1c n\u01b0\u1edbc"};
    private String[] P;
    public byte o;
    int[] p = new int[]{-1, -1, -1};
    int[] q = new int[]{-1, -1, -1};

    public final void a() {
        this.b();
        this.k.a = "Ch\u1ecdn";
        this.k = this.G;
        this.e = false;
        super.a();
    }

    public final void b() {
        this.z.f = this.y.f = acv.o;
        this.y.g = acv.p - 50;
        this.z.g = acv.p - 25;
        this.r = 0;
        this.s = 0;
        this.y.a = false;
        this.z.a = false;
        this.u = true;
    }

    public ju() {
        this.y = new bz();
        this.y.h = 30;
        this.y.i = 20;
        this.y.a = false;
        this.y.c(1);
        this.y.a(String.valueOf(a));
        this.z = new bz();
        this.z.h = 30;
        this.z.i = 20;
        this.z.a = false;
        this.z.c(1);
        this.z.a(String.valueOf(b));
        this.k = this.G = new s("", new abh(this));
        this.E = new s("", new abc(this));
        this.F = new s("", new abe(this));
        this.H = new s("", new aax(this));
        this.j = new s("\u0110\u00f3ng", new aaz(this));
        this.l = null;
    }

    public final void e() {
        try {
            a = Integer.parseInt(this.y.e());
            b = Integer.parseInt(this.z.e());
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void b(int n2) {
        this.f = false;
        this.h = false;
        this.g = false;
        if (n2 == 0) {
            this.f = true;
            abj.az = true;
            abj.aB = false;
        } else if (n2 == 1) {
            this.g = true;
            abj.az = false;
            abj.aB = true;
        } else if (n2 == 2) {
            this.h = true;
            abj.az = true;
            abj.aB = true;
        } else {
            abj.az = false;
            abj.aB = false;
        }
        abj.aA = abj.az;
        a = Integer.parseInt(this.y.e());
        b = Integer.parseInt(this.z.e());
        c = (int)(System.currentTimeMillis() / 100L) + 10;
        abj.aC = (int)(System.currentTimeMillis() / 1000L) + 1;
        acv.s.a();
        if (abj.az) {
            acv.s.t.ah = acv.s.t.cL;
            acv.s.t.ai = acv.s.t.cM;
        }
    }

    public final void d() {
        acv.s.d();
        if (!this.e) {
            if (J != I) {
                L = I - J << 2;
                J += (K += L) >> 4;
                K &= 0xF;
            }
            if (Math.abs(I - J) < 15 && J < 0) {
                I = 0;
            }
            if (Math.abs(I - J) < 10 && J > M) {
                I = M;
            }
            this.y.d();
            this.z.d();
        }
        super.d();
    }

    public final void c() {
        if (this.e) {
            boolean bl2 = false;
            boolean bl3 = false;
            if (acv.a(acv.m / 2 - 75, acv.n / 2 - 80, 75, 30)) {
                bl2 = true;
            } else if (acv.a(acv.m / 2, acv.n / 2 - 80, 75, 30)) {
                bl3 = true;
            }
            if (acv.g) {
                if (bl2) {
                    acv.c[4] = true;
                    acv.g = false;
                } else if (bl3) {
                    acv.c[6] = true;
                    acv.g = false;
                }
            }
            if (acv.c[4]) {
                acv.c[4] = false;
                if ((abj.at = (byte)(abj.at - 1)) < 0) {
                    abj.at = (byte)(this.N.length - 1);
                }
                this.f();
                this.v = 5;
            } else if (acv.c[6]) {
                acv.c[6] = false;
                if ((abj.at = (byte)(abj.at + 1)) > this.N.length - 1) {
                    abj.at = 0;
                }
                this.f();
                this.w = 5;
            }
        } else {
            switch (this.s) {
                case 0: {
                    x = "Tr\u1ecb";
                    if (acv.c[2]) {
                        --this.r;
                        if (this.r == 0) {
                            this.u = true;
                            this.y.a = false;
                            this.z.a = false;
                            this.k.a = "Ch\u1ecdn";
                            this.k = this.G;
                            this.l = null;
                        }
                        if (this.r < 0) {
                            this.r = 0;
                        }
                    } else if (acv.c[8]) {
                        this.u = false;
                        ++this.r;
                        if (this.r > 4) {
                            this.r = 4;
                        }
                    }
                    if (this.r == 1) {
                        this.y.a = true;
                        this.z.a = false;
                        this.l = this.y.e;
                        break;
                    }
                    if (this.r == 2) {
                        this.y.a = false;
                        this.z.a = true;
                        this.l = this.z.e;
                        this.k.a = "Ch\u1ecdn";
                        this.k = this.G;
                        break;
                    }
                    if (this.r == 3) {
                        this.y.a = false;
                        this.z.a = false;
                        this.k.a = "Ch\u1ecdn";
                        this.k = this.E;
                        break;
                    }
                    if (this.r != 4) break;
                    this.y.a = false;
                    this.z.a = false;
                    this.k.a = "Ch\u1ecdn";
                    this.k = this.F;
                    break;
                }
                case 1: {
                    x = "\u0110\u00e1nh";
                    M = acv.s.t.v() * 23 - 114;
                    if (M < 0) {
                        M = 0;
                    }
                    if (acv.c[2]) {
                        --this.r;
                        if (this.r < 0) {
                            this.r = 0;
                        }
                        if (this.r == 0) {
                            this.u = true;
                            this.k = this.G;
                            this.k.a = "Ch\u1ecdn";
                            this.k = this.G;
                        }
                    } else if (acv.c[8]) {
                        ++this.r;
                        this.u = false;
                        if (this.r > 1) {
                            this.r = 1;
                        }
                        if (this.r == 1) {
                            this.k = this.H;
                            this.k.a = "";
                        }
                    }
                    if (this.r != 1) break;
                    if (acv.c[4]) {
                        --this.t;
                        if (this.t >= 0) break;
                        this.t = 0;
                        break;
                    }
                    if (acv.c[6]) {
                        ++this.t;
                        if (this.t <= acv.s.t.v() - 1) break;
                        this.t = acv.s.t.v() - 1;
                        break;
                    }
                    if (!acv.c[5]) break;
                    this.k.b.a();
                    break;
                }
                case 2: {
                    x = "Nh\u1eb7t";
                    if (acv.c[2]) {
                        --this.r;
                        if (this.r == 0) {
                            this.u = true;
                            this.k.a = abj.az || abj.aB ? "T\u1eaft" : "B\u1eadt";
                            this.k = this.G;
                        }
                        if (this.r >= 0) break;
                        this.r = 0;
                        break;
                    }
                    if (!acv.c[8]) break;
                    this.u = false;
                    ++this.r;
                    if (this.r == 1) {
                        this.k = this.E;
                        this.k.a = "Ch\u1ecdn";
                    }
                    if (this.r <= 4) break;
                    this.r = 4;
                }
            }
            if (this.r == 0) {
                if (acv.c[4]) {
                    --this.s;
                    this.v = 5;
                    if (this.s < 0) {
                        this.s = 1;
                    }
                } else if (acv.c[6]) {
                    ++this.s;
                    this.w = 5;
                    if (this.s > 1) {
                        this.s = 0;
                    }
                }
            }
            if (this.s == 1 && this.r == 1) {
                I = this.t % acv.s.t.v() * 23 - 57;
                if (I < 0) {
                    I = 0;
                }
                if (I > M) {
                    I = M;
                }
            } else {
                I = 0;
                J = 0;
                this.t = 0;
            }
        }
        super.c();
    }

    public final boolean a(int n2) {
        boolean bl2;
        boolean bl3;
        if (this.y.a ? (bl3 = this.y.a(n2)) : this.z.a && (bl2 = this.z.a(n2))) {
            return true;
        }
        return super.a(n2);
    }

    public final void f() {
        this.P = d.j[0].a(this.O[abj.at], 135);
        this.k = new s("Ch\u1ecdn", new acy(this));
        this.l = new s("\u0110\u00f3ng", new acu(this));
        this.j = new s("", new acw(this));
    }

    public final void a(Graphics graphics) {
        acv.s.a(graphics);
        if (this.v > 0) {
            --this.v;
        }
        if (this.w > 0) {
            --this.w;
        }
        if (this.e) {
            yi.d(graphics, acv.m / 2 - 80, acv.n / 2 - 80, 160, 160);
            graphics.drawImage(yi.D, acv.m / 2 - 65 - this.v, acv.n / 2 - 66, 3);
            graphics.drawRegion(yi.D, 0, 0, 11, 7, 2, acv.m / 2 + 65 + this.w, acv.n / 2 - 66, 3);
            d.j[0].a(graphics, this.N[abj.at], acv.m / 2, acv.n / 2 - 73, 2);
            int n2 = 0;
            while (n2 < this.P.length) {
                d.j[0].a(graphics, this.P[n2], acv.m / 2 - 72, acv.n / 2 - 50 + n2 * aae.ao, 0);
                ++n2;
            }
            super.a(graphics);
            return;
        }
        graphics.translate(acv.o - 70, acv.p - 80);
        yi.d(graphics, 2, 2, 140, 145);
        graphics.drawImage(yi.D, 13 - this.v, 16, 3);
        graphics.drawRegion(yi.D, 0, 0, 11, 7, 2, 127 + this.w, 17, 3);
        graphics.setColor(0x797B79);
        graphics.fillRect(27, 8, 88, 16);
        graphics.fillRect(26, 9, 90, 14);
        graphics.setColor(this.u ? 30611 : 0x242424);
        graphics.fillRect(27, 9, 88, 14);
        d.j[0].a(graphics, x, 72, 9, 2);
        acv.a(graphics);
        switch (this.s) {
            case 0: {
                d.j[0].a(graphics, "HP: ", acv.o - 60, acv.p - 45, 0);
                d.j[0].a(graphics, "MP: ", acv.o - 60, acv.p - 20, 0);
                d.j[0].a(graphics, "%", acv.o + 45, acv.p - 45, 1);
                d.j[0].a(graphics, "%", acv.o + 45, acv.p - 20, 1);
                graphics.setColor(0xFFFFFF);
                graphics.fillRect(acv.o - 65, acv.p + 5, 135, 1);
                d.j[0].a(graphics, "T\u1ef1 v\u00e0o nh\u00f3m:", acv.o - 60, acv.p + 15, 0);
                yi.J.a(this.r == 3 ? 1 : 0, acv.o + 30, acv.p + 23, 0, 3, graphics);
                d.j[0].a(graphics, "T\u1ef1 v\u1ec1 l\u00e0ng:", acv.o - 60, acv.p + 35, 0);
                yi.J.a(this.r == 4 ? 1 : 0, acv.o + 30, acv.p + 43, 0, 3, graphics);
                if (this.d) {
                    yi.J.a(2, acv.o + 30, acv.p + 23, 0, 3, graphics);
                }
                if (i) {
                    yi.J.a(2, acv.o + 30, acv.p + 43, 0, 3, graphics);
                }
                this.y.a(graphics);
                acv.a(graphics);
                this.z.a(graphics);
                break;
            }
            case 1: {
                int n3;
                d.j[0].a(graphics, "\u0110\u00e1nh:", acv.o - 15, acv.p - 50, 0);
                graphics.setColor(0xFFFFFF);
                graphics.drawRect(acv.o - 50, acv.p - 35, 17, 17);
                graphics.drawRect(acv.o - 8, acv.p - 35, 17, 17);
                graphics.drawRect(acv.o + 35, acv.p - 35, 17, 17);
                d.j[0].a(graphics, "H\u1ed5 tr\u1ee3:", acv.o - 15, acv.p - 8, 0);
                graphics.setColor(0xFFFFFF);
                graphics.drawRect(acv.o - 50, acv.p + 7, 17, 17);
                graphics.drawRect(acv.o - 8, acv.p + 7, 17, 17);
                graphics.drawRect(acv.o + 35, acv.p + 7, 17, 17);
                graphics.fillRect(acv.o - 65, acv.p + 33, 135, 1);
                Graphics graphics2 = graphics;
                ju ju2 = this;
                graphics2.setClip(acv.o - 60, acv.p + 35, 125, 26);
                graphics2.translate(-J, 0);
                int n4 = 0;
                while (n4 < acv.s.t.v()) {
                    ko.a.a(n4, n4 * 23 + 11 + acv.o - 60, acv.p + 48, 0, 3, graphics2);
                    graphics2.drawImage(yi.N, n4 * 23 + 11 + acv.o - 60, acv.p + 48, 3);
                    d.i[1].a(graphics2, String.valueOf(hw.aT[n4]), n4 * 23 + 23 - 4 + acv.o - 60, acv.p + 48 + 5, 1);
                    ++n4;
                }
                if (ju2.r == 1 && acv.l % 10 > 3) {
                    graphics2.setColor(0xFA1111);
                    graphics2.drawRect(ju2.t * 23 + acv.o - 60, acv.p + 38, 21, 21);
                }
                acv.a(graphics2);
                int[] nArray = new int[]{acv.o - 51, acv.o - 9, acv.o + 34};
                if (ju2.q != null) {
                    n3 = 0;
                    while (n3 < ju2.q.length) {
                        if (ju2.q[n3] != -1) {
                            ko.a.a(ju2.q[n3], nArray[n3], acv.p - 36, 0, 0, graphics2);
                        }
                        ++n3;
                    }
                }
                if (ju2.p == null) break;
                n3 = 0;
                while (n3 < ju2.p.length) {
                    if (ju2.p[n3] != -1) {
                        ko.a.a(ju2.p[n3], nArray[n3], acv.p + 6, 0, 0, graphics2);
                    }
                    ++n3;
                }
                break;
            }
            case 2: {
                d.j[0].a(graphics, "HP + MP: ", acv.o - 60, acv.p - 45, 0);
                yi.J.a(this.r == 1 ? 1 : 0, acv.o + 30, acv.p - 38, 0, 3, graphics);
                if (this.A) {
                    yi.J.a(2, acv.o + 30, acv.p - 38, 0, 3, graphics);
                }
                d.j[0].a(graphics, "Trang b\u1ecb: ", acv.o - 60, acv.p - 25, 0);
                yi.J.a(this.r == 2 ? 1 : 0, acv.o + 30, acv.p - 18, 0, 3, graphics);
                if (this.B) {
                    yi.J.a(2, acv.o + 30, acv.p - 18, 0, 3, graphics);
                }
                d.j[0].a(graphics, "Nguy\u00ean li\u1ec7u: ", acv.o - 60, acv.p - 5, 0);
                yi.J.a(this.r == 3 ? 1 : 0, acv.o + 30, acv.p + 2, 0, 3, graphics);
                if (this.C) {
                    yi.J.a(2, acv.o + 30, acv.p + 2, 0, 3, graphics);
                }
                d.j[0].a(graphics, "Nh\u1eb7t h\u1ebft: ", acv.o - 60, acv.p + 15, 0);
                yi.J.a(this.r == 4 ? 1 : 0, acv.o + 30, acv.p + 22, 0, 3, graphics);
                if (!this.D) break;
                yi.J.a(2, acv.o + 30, acv.p + 22, 0, 3, graphics);
            }
        }
        super.a(graphics);
    }

    private boolean g() {
        byte[][] byArrayArray = new byte[][]{{4, 5}, {4, 5}, {4, 5, 6, 7}, {4, 5}, {4, 5}};
        int n2 = 0;
        while (n2 < byArrayArray[acv.s.t.aP].length) {
            if (this.t == byArrayArray[acv.s.t.aP][n2]) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    private void a(int n2, boolean bl2) {
        Vector<s> vector = new Vector<s>();
        int n3 = 0;
        while (n3 < 3) {
            int n4 = n3;
            vector.addElement(new s("Ph\u00edm s\u1ed1 " + (1 + (n3 << 1)), new cj(this, bl2, n4, n2)));
            ++n3;
        }
        acv.u.a(vector, 2);
    }

    static void a(ju ju2) {
        try {
            if (ju2.y.e().equals("") || ju2.z.e().equals("")) {
                acv.a("HP, Mp kh\u00f4ng \u0111\u01b0\u1ee3c b\u1ecf tr\u1ed1ng.");
                return;
            }
            if (Integer.parseInt(ju2.y.e()) > 100 || Integer.parseInt(ju2.z.e()) > 100) {
                acv.a("Gi\u00e1 tr\u1ecb kh\u00f4ng h\u1ee3p l\u1ec7.");
                return;
            }
            Vector<s> vector = new Vector<s>();
            String[] stringArray = new String[]{"Auto \u0111\u00e1nh", "Auto b\u01a1m m\u00e1u", "Auto \u0111\u00e1nh, b\u01a1m m\u00e1u"};
            if (abj.az || abj.aB) {
                stringArray = new String[]{"Auto \u0111\u00e1nh", "Auto b\u01a1m m\u00e1u", "Auto \u0111\u00e1nh, b\u01a1m m\u00e1u", "T\u1eaft auto"};
            }
            int n2 = 0;
            while (n2 < stringArray.length) {
                int n3 = n2;
                s s2 = new s(stringArray[n2], new acx(ju2, n3));
                vector.addElement(s2);
                ++n2;
            }
            acv.u.a(vector, 2);
            return;
        }
        catch (Exception exception) {
            acv.a("Vui l\u00f2ng ch\u1ec9 nh\u1eadp s\u1ed1.");
            return;
        }
    }

    static void b(ju ju2) {
        if (ju2.s == 0) {
            ju2.d = !ju2.d;
            return;
        }
        if (ju2.s == 2) {
            switch (ju2.r) {
                case 1: {
                    ju2.A = !ju2.A;
                    break;
                }
                case 2: {
                    ju2.B = !ju2.B;
                    break;
                }
                case 3: {
                    ju2.C = !ju2.C;
                    break;
                }
                case 4: {
                    boolean bl2 = ju2.D = !ju2.D;
                    if (ju2.D) {
                        ju2.A = true;
                        ju2.B = true;
                        ju2.C = true;
                        break;
                    }
                    ju2.A = false;
                    ju2.B = false;
                    ju2.C = false;
                }
            }
            if (ju2.A && ju2.B && ju2.C) {
                ju2.D = true;
                return;
            }
            ju2.D = false;
        }
    }

    static void c(ju ju2) {
        if (hw.aT[ju2.t] == -1) {
            acv.a("Xin g\u1eb7p L\u00e2m t\u01b0\u1edbng qu\u00e2n \u0111\u1ec3 h\u1ecdc k\u1ef9 n\u0103ng n\u00e0y");
            return;
        }
        new Vector();
        boolean bl2 = false;
        boolean bl3 = ju2.g();
        if (bl3) {
            boolean bl4 = bl2 = qz.d[acv.s.t.aP][ju2.t - 4] == -1;
        }
        if (!bl2) {
            if (hw.aT[ju2.t] > 0) {
                if (acv.s.t.aP != 2) {
                    ju2.a(ju2.t, bl3);
                    return;
                }
                if (ju2.t == 6) {
                    acv.a("Kh\u00f4ng th\u1ec3 d\u00f9ng skill n\u00e0y");
                    return;
                }
                ju2.a(ju2.t, bl3);
                return;
            }
            acv.a("Ch\u01b0a h\u1ecdc k\u1ef9 n\u0103ng n\u00e0y");
        }
    }
}

