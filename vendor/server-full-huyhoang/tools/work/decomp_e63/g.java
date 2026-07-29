/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class g
extends aae {
    public static g a;
    public static final uh[][] b;
    private static String[] h;
    private static String[] i;
    public static int c;
    public static int d;
    public bz e;
    public sc f = new sc();
    int g;
    private int o;

    static {
        b = new uh[][]{{new uh(0, "Ki\u1ebfm Kh\u00e1ch", "Nam", "Ki\u1ebfm", "Mien Bac", "Kim", 3, 0), new uh(0, "Ki\u1ebfm Kh\u00e1ch", "N\u1eef", "Ki\u1ebfm", "Mien Bac", "Kim", 3, 0)}, {new uh(1, "Chi\u1ebfn Binh", "Nam", "\u0110\u1ea1i \u0110ao", "Mien Bac", "H\u1ecfa", 4, 0), new uh(1, "Chi\u1ebfn Binh", "N\u1eef", "\u0110\u1ea1i \u0110ao", "Mien Bac", "H\u1ecfa", 4, 0)}, {new uh(2, "Ph\u00e1p S\u01b0", "Nam", "\u0110\u0169a th\u1ea7n", "Mien Nam", "Th\u1ee7y", 5, 0), new uh(2, "Ph\u00e1p S\u01b0", "N\u1eef", "\u0110\u0169a th\u1ea7n", "Mien Nam", "Th\u1ee7y", 5, 0)}, {new uh(3, "\u0110\u1ea5u s\u0129", "Nam", "R\u00ecu", "Mien Nam", "Th\u1ed5", 6, 0), new uh(3, "\u0110\u1ea5u s\u0129", "N\u1eef", "R\u00ecu", "Mien Nam", "Th\u1ed5", 6, 0)}, {new uh(4, "Cung Th\u1ee7", "Nam", "Cung", "Mien Bac", "M\u1ed9c", 7, 0), new uh(4, "Cung Th\u1ee7", "N\u1eef", "Cung", "Mien Bac", "M\u1ed9c", 7, 0)}};
        h = new String[]{"\u0110\u1ea7u \u0111inh", "T\u00f3c b\u00fai", "T\u00f3c c\u1ed9t cao", "T\u00f3c ch\u00e9o", "T\u00f3c x\u00f9", "T\u00f3c ngang vai"};
        i = new String[]{"Nam", "N\u1eef"};
        c = 0;
        d = 0;
    }

    public static g e() {
        if (a == null) {
            a = new g();
            return a;
        }
        return a;
    }

    public final void a() {
        this.g();
        super.a();
    }

    public final void b() {
        this.e.f = acv.o - 28;
        this.e.g = acv.p - 24;
    }

    public g() {
        this.e = new bz(this);
        this.e.h = 80;
        this.e.i = aae.ao + 2;
        this.e.a = true;
        this.e.c(3);
        this.f.cW = 0;
        this.g = 0;
        this.f.aJ = (short)-1;
        this.f.aK = g.b[this.g][g.c].e;
        this.f.aH = g.b[this.g][g.c].c;
        this.f.aI = g.b[this.g][g.c].d;
        this.f.bo = g.b[this.g][g.c].f;
        this.f.bn = g.b[this.g][g.c].g;
        this.j = new s("\u0110\u00f3ng", new ar(this));
        new s("X\u00f3a", new as(this));
        this.k = new s("T\u1ea1o", new an(this));
        this.b();
    }

    private void f() {
        this.f.aK = g.b[this.g][g.c].e;
        this.f.aH = g.b[this.g][g.c].c;
        this.f.aI = g.b[this.g][g.c].d;
        this.f.bo = g.b[this.g][g.c].f;
        this.f.bn = g.b[this.g][g.c].g;
    }

    private void g() {
        go.a().a(0, this.f.bo, this.f.bn, this.f.bp);
    }

    public final boolean a(int n2) {
        boolean bl2 = false;
        if (this.o == 0) {
            this.e.a(n2);
        }
        if (n2 == -2) {
            ++this.o;
            if (this.o > 4) {
                this.o = 0;
            }
            this.e.a = this.o == 0;
        }
        if (n2 == -1) {
            --this.o;
            if (this.o < 0) {
                this.o = 4;
            }
            this.e.a = this.o == 0;
        }
        if (n2 == -3) {
            if (this.o == 1) {
                ++this.g;
                if (this.g >= 5) {
                    this.g = 0;
                }
                bl2 = true;
            }
            if (this.o == 2) {
                c = c == 1 ? 0 : 1;
                this.f();
            }
            if (this.o == 3) {
                g.b[this.g][g.c].e = (short)(g.b[this.g][g.c].e - 2);
                if (g.b[this.g][g.c].e < 0) {
                    g.b[this.g][g.c].e = (byte)(4 + g.b[this.g][g.c].e);
                }
                bl2 = true;
            }
            if (this.o == 4 && --d < 0) {
                d = 1;
            }
        }
        if (n2 == -4) {
            if (this.o == 1) {
                --this.g;
                if (this.g < 0) {
                    this.g = 4;
                }
                bl2 = true;
            }
            if (this.o == 2) {
                c = c == 0 ? 1 : 0;
                this.f();
            }
            if (this.o == 3) {
                g.b[this.g][g.c].e = (short)(g.b[this.g][g.c].e + 2);
                if (g.b[this.g][g.c].e > 5) {
                    g.b[this.g][g.c].e = (byte)(g.b[this.g][g.c].e % 2);
                }
                bl2 = true;
            }
            if (this.o == 4 && ++d > 1) {
                d = 0;
            }
        }
        if (bl2) {
            this.f.aH = g.b[this.g][g.c].c;
            this.f.aI = g.b[this.g][g.c].d;
            this.f.aK = g.b[this.g][g.c].e;
            this.f.bo = g.b[this.g][g.c].f;
            this.f.bn = g.b[this.g][g.c].g;
            this.g();
        }
        return super.a(n2);
    }

    public final void a(Graphics graphics) {
        yv.e();
        yv.b(graphics);
        acv.a(graphics);
        int n2 = acv.o - 53;
        int n3 = acv.p - 20;
        yi.a(graphics, acv.o - 75, acv.p - 60);
        if (acv.l % 20 > 4) {
            d.j[0].a(graphics, "*", acv.o - 60, n3 + this.o * 17 - 1, 0);
            d.j[0].a(graphics, "*", acv.o + 57, n3 + this.o * 17 - 1, 0);
        }
        d.j[0].a(graphics, "T\u00ean:", n2, n3, 0);
        d.j[0].a(graphics, "D\u00f2ng: " + g.b[this.g][g.c].a, n2, n3 += 17, 0);
        d.j[0].a(graphics, "Gi\u1edbi t\u00ednh: " + i[c], n2, n3 += 17, 0);
        d.j[0].a(graphics, "T\u00f3c: " + h[g.b[this.g][g.c].e], n2, n3 += 17, 0);
        d.j[0].a(graphics, "L.th\u1ed5: " + abj.as[d], n2, n3 += 17, 0);
        d.j[0].a(graphics, "Ng\u0169 h\u00e0nh: " + g.b[this.g][g.c].b, n2, n3 += 17, 0);
        this.f.a(graphics, (short)acv.o, (short)(acv.p - 30));
        if (this.f.bo != -1 && this.f.bn != -1 && this.f.aY != null) {
            n2 = this.f.O == 1 ? 1 : 0;
            graphics.drawImage(this.f.aY, acv.o + this.f.ba, acv.p - 30 + this.f.bb + n2, 0);
        }
        this.e.a(graphics);
        graphics.setClip(0, 0, acv.m, acv.n);
        super.a(graphics);
    }

    public final void d() {
        this.e.d();
        this.f.b();
        super.d();
    }
}

