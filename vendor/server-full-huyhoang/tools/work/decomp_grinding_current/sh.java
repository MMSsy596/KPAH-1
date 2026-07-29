/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class sh
extends aae {
    private static sh e;
    private Image f;
    private Image g;
    private Image h;
    private aab i;
    private int o;
    private int p;
    private int q;
    private int r;
    public int a;
    private int s;
    private int t;
    private kt u;
    private boolean v;
    private boolean w;
    private boolean x = false;
    public aae b;
    private s y;
    private s z;
    private s A;
    private s B;
    private s C;
    private s D;
    private Vector E = new Vector();
    private long F = 0L;
    private boolean[] G;
    private Vector H;
    public boolean c;
    private Random I = new Random(System.currentTimeMillis());
    private static int[] J;
    private static int[] K;
    private byte L = (byte)-1;
    public static byte[] d;

    static {
        J = new int[3];
        K = new int[3];
        d = null;
    }

    public static sh e() {
        if (e == null) {
            e = new sh();
            return e;
        }
        return e;
    }

    public final void a() {
        acv.e[5] = false;
        super.a();
        this.f();
        this.j = this.B;
    }

    public sh() {
        acf.b("/qs/qs");
        this.g = acf.a("8Chars");
        this.h = acf.a("q");
        this.i = aab.a("st", 11, 11);
        this.f = acf.a("wood21");
        acf.a();
        this.o = acv.m < 200 ? 80 : 90;
        this.u = new kt(acv.m, acv.p + 1);
        this.q = 30;
        this.s = 360 / this.q;
        this.y = new s("B\u1eaft \u0111\u1ea7u", new m(this));
        this.z = new s("Xin ch\u1edd", new k(this));
        this.A = new s("\u0110\u00f3ng", new ab(this));
        this.B = new s("", new aa(this));
        this.D = new s("Ch\u1ecdn l\u1ea1i", new y(this));
        this.C = new s("Ti\u1ebfp t\u1ee5c", new w(this));
        this.k = this.y;
        this.l = this.A;
        this.a = 90;
        this.G = new boolean[3];
        this.H = new Vector();
    }

    protected final void f() {
        this.w = false;
        this.v = false;
        this.k = this.y;
        this.l = this.A;
        this.F = 0L;
        int n2 = 0;
        while (n2 < 3) {
            this.G[n2] = false;
            ++n2;
        }
        this.H.removeAllElements();
        this.E.removeAllElements();
        this.L = (byte)-1;
    }

    public final void d() {
        int n2;
        sh sh2;
        if (this.b != null) {
            this.b.d();
        }
        if (this.r > 0) {
            this.p -= this.r;
            if (this.p < 0) {
                this.p += 7200;
            }
            if (this.r < 10) {
                if (this.p / 20 % 30 == 0) {
                    this.r = 0;
                }
            } else {
                --this.r;
            }
        } else if (this.v) {
            sh2 = this;
            J = new int[d.length];
            K = new int[d.length];
            sh2.v = false;
            sh2.w = true;
            sh2.x = false;
            sh2.F = System.currentTimeMillis() / 100L;
            int n3 = 0;
            while (n3 < d.length) {
                n2 = 0;
                n2 = n3 == 0 ? 150 : (n3 == 1 ? 180 : 210);
                n2 = yg.c(n2);
                int n4 = sh2.o * yg.b(n2) >> 10;
                n2 = -(sh2.o * yg.a(n2)) >> 10;
                sh.J[n3] = sh2.u.a + n4;
                sh.K[n3] = sh2.u.b + n2;
                ++n3;
            }
        }
        if (this.L == 2 && this.H.size() == 0) {
            sh2 = this;
            String string = "B\u1ea1n nh\u1eadn \u0111\u01b0\u1ee3c: ";
            if (d != null) {
                n2 = 0;
                while (n2 < d.length) {
                    if (System.currentTimeMillis() / 100L - sh2.F > (long)((n2 + 1) * 5)) {
                        aag aag2 = (aag)km.b.elementAt(d[n2]);
                        string = String.valueOf(string) + "\n" + aag2.d;
                    }
                    ++n2;
                }
            }
            acv.a(string);
            this.L = (byte)3;
        }
        if (this.w) {
            if (this.k == this.z) {
                int n5 = 0;
                n2 = 0;
                while (n2 < this.G.length) {
                    if (this.G[n2]) {
                        ++n5;
                    }
                    ++n2;
                }
                if (n5 == 3) {
                    this.k = this.A;
                    this.j = this.C;
                    this.l = this.D;
                }
            }
            int n6 = 0;
            while (n6 < this.H.size()) {
                ec ec2 = (ec)this.H.elementAt(n6);
                ec2.a += ec2.c;
                if (ec2.c > 1 || ec2.c < -1) {
                    int n7 = ec2.c;
                    ec2.c = ec2.c - n7 / (n7 < 0 ? -n7 : n7);
                }
                ec2.b += ec2.e;
                ++ec2.e;
                ++ec2.f;
                if (ec2.f > 20) {
                    this.H.removeElement(ec2);
                }
                ++n6;
            }
            if (d != null) {
                n6 = 0;
                while (n6 < d.length) {
                    if (!this.G[n6] && System.currentTimeMillis() / 100L - this.F > (long)((n6 + 1) * 5)) {
                        this.G[n6] = true;
                        this.a(J[n6], K[n6]);
                    }
                    ++n6;
                }
            }
        }
    }

    private void a(int n2, int n3) {
        int n4 = 0;
        while (n4 < 10) {
            int n5 = 1;
            if (n4 % 2 == 0) {
                n5 = -1;
            }
            ec ec2 = new ec(n2, n3);
            new ec(n2, n3).f = 0;
            ec2.c = n5 * (this.I.nextInt(80) / 10);
            ec2.e = -this.I.nextInt(70) / 10;
            this.H.addElement(ec2);
            ++n4;
        }
    }

    public final void c() {
        if (!this.w) {
            if (acv.i() == 1) {
                if (acv.f) {
                    this.x = true;
                    acv.e[5] = true;
                }
                if (acv.H) {
                    acv.d[5] = true;
                }
            }
            if (acv.e[5] && !this.v && this.x) {
                if (this.a < 270) {
                    this.a += 3;
                }
            } else if (this.a > 90 && this.c) {
                this.a -= 3;
            }
            if (acv.d[5]) {
                if (this.a > 90) {
                    sh sh2 = this;
                    if (!sh2.v && sh2.x) {
                        sh2.t = sh2.a;
                        go.a().v(km.c);
                        sh2.k = sh2.z;
                    }
                }
                acv.d[5] = false;
            }
        }
        super.c();
    }

    public final void g() {
        this.k = this.z;
        this.r = 100 + (this.t - 90);
        this.v = true;
        this.l = this.B;
        this.c = true;
        acv.g();
    }

    public final void a(Graphics graphics) {
        aag aag2;
        int n2;
        int n3;
        int n4;
        if (this.b != null) {
            this.b.a(graphics);
        }
        acv.a(graphics);
        int n5 = this.p / 20;
        int n6 = 0;
        while (n6 < this.s) {
            int n7 = n5 + n6 * this.q;
            if (n7 > 360) {
                n7 -= 360;
            }
            if (n7 >= 82 && n7 <= 278) {
                n4 = yg.c(n7);
                n3 = this.o * yg.b(n4) >> 10;
                n2 = -(this.o * yg.a(n4)) >> 10;
                graphics.drawImage(this.f, this.u.a + n3, this.u.b + n2, 3);
            }
            ++n6;
        }
        if (this.w) {
            Graphics graphics2 = graphics;
            sh sh2 = this;
            if (d != null) {
                n4 = 0;
                while (n4 < d.length) {
                    if (System.currentTimeMillis() / 100L - sh2.F > (long)((n4 + 1) * 5)) {
                        aag aag3 = (aag)km.b.elementAt(d[n4]);
                        aag3.a(graphics2, J[n4], K[n4]);
                    }
                    ++n4;
                }
            }
        }
        int n8 = 0;
        int n9 = 0;
        while (n9 < this.s) {
            n4 = n5 + n9 * this.q;
            if (n4 > 360) {
                n4 -= 360;
            }
            if (n4 >= 82 && n4 <= 278) {
                n3 = yg.c(n4);
                n2 = this.o * yg.b(n3) >> 10;
                n3 = -(this.o * yg.a(n3)) >> 10;
                long l2 = System.currentTimeMillis() / 100L - this.F;
                if (!this.w || n4 < 150 || n4 > 210 || l2 <= (long)((n8 + 1) * 5) && l2 > (long)((n8 + 1) * 5 - 5)) {
                    graphics.drawImage(this.h, this.u.a + n2, this.u.b + n3, 3);
                } else {
                    ++n8;
                }
            }
            ++n9;
        }
        graphics.setColor(0x898787);
        graphics.fillRect(this.u.a - 18, this.u.b - 18, 18, 36);
        graphics.drawRegion(this.g, 0, 0, this.g.getWidth(), this.g.getHeight(), 0, this.u.a, this.u.b, 40);
        graphics.drawRegion(this.g, 0, 0, this.g.getWidth(), this.g.getHeight(), 1, this.u.a, this.u.b, 24);
        if (km.c >= 0 && km.c < km.b.size() && (aag2 = (aag)km.b.elementAt(km.c)) != null) {
            aag2.a(graphics, this.u.a - this.g.getWidth() + 28, this.u.b);
        }
        Graphics graphics3 = graphics;
        sh sh3 = this;
        n4 = yg.c(sh3.a);
        n3 = (sh3.o / 3 + 2) * yg.b(n4) >> 10;
        n5 = -((sh3.o / 3 + 2) * yg.a(n4)) >> 10;
        n4 = sh3.a + 90;
        if (n4 > 360) {
            n4 -= 360;
        }
        n4 = yg.c(n4);
        n2 = 6 * yg.b(n4) >> 10;
        n4 = -(6 * yg.a(n4)) >> 10;
        int n10 = sh3.a - 90;
        if (n10 < 0) {
            n10 += 360;
        }
        n10 = yg.c(n10);
        int n11 = 6 * yg.b(n10) >> 10;
        n10 = -(6 * yg.a(n10)) >> 10;
        graphics3.setColor(13935404);
        graphics3.fillTriangle(sh3.u.a + n3, sh3.u.b + n5, sh3.u.a + n2, sh3.u.b + n4, sh3.u.a + n11, sh3.u.b + n10);
        graphics3.fillRoundRect(sh3.u.a - 6, sh3.u.b - 6, 12, 12, 12, 12);
        if (this.w || this.r > 0) {
            graphics3 = graphics;
            sh3 = this;
            n4 = 0;
            while (n4 < sh3.H.size()) {
                ec ec2 = (ec)sh3.H.elementAt(n4);
                n5 = ec2.f / 5;
                if (n5 < 4) {
                    sh3.i.a(n5, ec2.a, ec2.b, 0, 3, graphics3);
                }
                sh3.L = (byte)(sh3.L + 1);
                if (sh3.L >= 2) {
                    sh3.L = (byte)2;
                }
                ++n4;
            }
        }
        super.a(graphics);
    }

    static void a(sh sh2, boolean bl2) {
        sh2.x = true;
    }

    static s a(sh sh2) {
        return sh2.y;
    }

    static s b(sh sh2) {
        return sh2.B;
    }

    static s c(sh sh2) {
        return sh2.A;
    }
}

