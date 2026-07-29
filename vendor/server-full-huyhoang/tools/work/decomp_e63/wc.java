/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import game.GameMidlet;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class wc
extends aae {
    public static wc a;
    private String[][] i = new String[][]{{"C\u1eeda h\u00e0ng"}, {"C\u00e0i \u0111\u1eb7t", "C\u1ea5u h\u00ecnh", "B\u1ea3n \u0111\u1ed3 l\u1edbn", "B\u1eadt/t\u1eaft giao di\u1ec7n", "H\u01b0\u1edbng d\u1eabn", "\u00e2m thanh", "B\u1eadt/t\u1eaft l\u1eddi m\u1eddi", "B\u1eadt/t\u1eaft auto \u0111\u00e1nh", "Ch\u1ebf \u0111\u1ed9 focus"}, {"B\u1ea3n th\u00e2n", "H\u00e0nh trang", "K\u1ef9 n\u0103ng", "Ti\u1ec1m n\u0103ng", "Trang b\u1ecb", "Th\u00f4ng tin", "Trang b\u1ecb th\u00fa", "C\u00e2y th\u1ea7n", "C\u1ed5 v\u1eadt", "Kh\u00e1c"}, {"Nap Xu"}, {"Nhi\u1ec7m v\u1ee5"}, {"\u0110\u1ed3 s\u00e1t"}, {"Kh\u00e1c", "Tin nh\u1eafn", "Nh\u00f3m", "B\u1ea1n b\u00e8", "Top cao th\u1ee7", "Top \u0111\u1ea1i gia", "B\u1ea3ng Top", "K\u00eanh th\u1ebf gi\u1edbi", "Di\u1ec5n \u0111\u00e0n", "R\u1eddi bang"}, {""}, {"Tho\u00e1t"}};
    int b = 0;
    int c = -1;
    int d;
    public int e;
    private int o;
    private int p;
    private int q;
    public int f;
    private int r;
    private int s;
    private int t;
    public int g;
    public int h;
    private boolean u = false;
    private int v = 0;

    public static wc e() {
        if (a == null) {
            a = new wc();
            return a;
        }
        return a;
    }

    public final void f() {
        if (acv.s.t.cI == -1) {
            this.i[7] = new String[]{"Bang h\u1ed9i", "Top bang h\u1ed9i", "\u0110\u0103ng k\u00fd bang h\u1ed9i"};
            return;
        }
        if (acv.s.t.af == 0) {
            this.i[7] = new String[]{"Bang h\u1ed9i", "Top bang h\u1ed9i", "Nhi\u1ec7m v\u1ee5", "Th\u00e0nh vi\u00ean bang h\u1ed9i", "Th\u00f4ng tin bang h\u1ed9i", "Tin nh\u1eafn bang h\u1ed9i", "K\u1ef9 n\u0103ng", "Chat to\u00e0n bang", "Quy\u00ean g\u00f3p", "Gi\u1ea3i t\u00e1n bang h\u1ed9i"};
            if (hw.ae) {
                this.i[7][9] = "Ph\u1ee5c h\u1ed3i bang h\u1ed9i";
                return;
            }
        } else {
            this.i[7] = new String[]{"Bang h\u1ed9i", "Top bang h\u1ed9i", "Nhi\u1ec7m v\u1ee5", "Th\u00e0nh vi\u00ean bang h\u1ed9i", "Th\u00f4ng tin bang h\u1ed9i", "Tin nh\u1eafn bang h\u1ed9i", "K\u1ef9 n\u0103ng", "Chat to\u00e0n bang", "Quy\u00ean g\u00f3p", "R\u1eddi bang"};
        }
    }

    public final void b() {
        this.t = 128;
        this.g = 83;
        this.h = 18;
        if (acv.a.hasPointerEvents()) {
            this.t = 170;
            this.g = 130;
            this.h = 24;
        }
        this.r = (acv.m - this.t) / 2;
        this.s = (acv.n - this.g) / 2;
        this.o = 0;
        this.f = this.i.length * this.h - this.g;
    }

    private wc() {
        this.b();
        this.k = new s("Ch\u1ecdn", new ti(this));
        this.l = new s("\u0110\u00f3ng", new tk(this));
    }

    public final void a(Graphics graphics) {
        acv.s.a(graphics);
        acv.s.b(graphics);
        yi.c(graphics, this.r - 5, this.s - 10, this.t + 10, this.g + 20);
        graphics.setClip(this.r, this.s - 2, this.t + 2, this.g + 4);
        int n2 = this.s;
        graphics.translate(0, -this.o);
        int n3 = 0;
        while (n3 < this.d) {
            if (n3 == this.b) {
                yi.b(graphics, this.r, n2, this.t, this.h);
                d.j[0].a(graphics, this.c == -1 ? this.i[n3][0] : this.i[this.c][n3 + 1], acv.o, n2 + this.h / 2 - d.j[0].b() / 2, 2);
            } else {
                d.h.a(graphics, this.c == -1 ? this.i[n3][0] : this.i[this.c][n3 + 1], acv.o, n2 + this.h / 2 - d.j[0].b() / 2, 2);
            }
            n2 += this.h;
            ++n3;
        }
        super.a(graphics);
    }

    public final void d() {
        acv.s.d();
        wc wc2 = this;
        if (wc2.o != wc2.e) {
            wc2.q = wc2.e - wc2.o << 2;
            wc2.p += wc2.q;
            wc2.o += wc2.p >> 4;
            wc2.p &= 0xF;
        }
        if (Math.abs(this.e - this.o) < 15 && this.o < 0) {
            this.e = 0;
        }
        if (Math.abs(this.e - this.o) < 10 && this.o > this.f) {
            this.e = this.f;
        }
    }

    public final void c() {
        if (acv.a(this.r, this.s, this.t, this.g)) {
            int n2;
            int n3 = acv.D - acv.k;
            if (acv.f) {
                if (!this.u) {
                    this.v = this.o;
                    this.u = true;
                    n2 = (this.e + acv.k - this.s) / this.h;
                    if (n2 == this.b) {
                        this.g();
                    }
                    this.b = n2;
                    if (this.b < 0) {
                        this.b = 0;
                    }
                    if (this.b >= this.i.length) {
                        this.b = this.i.length - 1;
                    }
                    this.e = this.b * this.h - this.g / 2;
                    if (this.e < 0) {
                        this.e = 0;
                    }
                }
                this.e = this.v + (acv.D - acv.k);
                if (this.e < -10) {
                    this.e = -10;
                }
                if (this.e > this.f + 10) {
                    this.e = this.f + 10;
                }
            }
            if (acv.g) {
                acv.g = false;
                this.u = false;
                if (Math.abs(n3) < 5 && (n2 = (this.e + this.s) / this.h) == this.b) {
                    this.g();
                }
            }
        }
        if (acv.b(8)) {
            ++this.b;
            if (this.b >= this.d) {
                this.b = 0;
            }
            this.e = this.b * this.h - this.g / 2;
            if (this.e < 0) {
                this.e = 0;
            }
            if (this.e > this.f) {
                this.e = this.f;
            }
        }
        if (acv.b(2)) {
            --this.b;
            if (this.b < 0) {
                this.b = this.d - 1;
            }
            this.e = this.b * this.h - this.g / 2;
            if (this.e < 0) {
                this.e = 0;
            }
            if (this.e > this.f) {
                this.e = this.f;
            }
        }
        super.c();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void g() {
        if (((wc)((Object)vector)).c == -1) {
            if (((wc)((Object)vector)).i[((wc)((Object)vector)).b].length == 1) {
                switch (((wc)((Object)vector)).b) {
                    case 0: {
                        int n2 = 0;
                        int n3 = 0;
                        while (n3 < yi.f.size()) {
                            xv xv2 = (xv)yi.f.elementAt(n3);
                            if (xv2.g + 1 > n2) {
                                n2 = xv2.g + 1;
                            }
                            ++n3;
                        }
                        byte[] byArray = new byte[n2];
                        int n4 = 0;
                        while (n4 < byArray.length) {
                            byArray[n4] = 20;
                            ++n4;
                        }
                        nu.e().a(0, true, byArray);
                        nu.e().a();
                        return;
                    }
                    case 3: {
                        acv.s.i();
                        ((bg)((Object)vector)).l.b.a();
                        break;
                    }
                    case 4: {
                        wc.b(6);
                        return;
                    }
                    case 6: {
                        break;
                    }
                    case 5: {
                        acv.s.a();
                        acv.s.G.c();
                        return;
                    }
                    case 8: {
                        GameMidlet.a.notifyDestroyed();
                        return;
                    }
                }
            }
            ((wc)((Object)vector)).c = ((wc)((Object)vector)).b;
            ((wc)((Object)vector)).d = ((wc)((Object)vector)).i[((wc)((Object)vector)).b].length - 1;
            ((wc)((Object)vector)).f = ((wc)((Object)vector)).i[((wc)((Object)vector)).b].length * ((wc)((Object)vector)).h - ((wc)((Object)vector)).g;
            if (((wc)((Object)vector)).f < 0) {
                ((wc)((Object)vector)).f = 0;
            }
            ((wc)((Object)vector)).b = 0;
            ((wc)((Object)vector)).e = 0;
            ((wc)((Object)vector)).o = 0;
            return;
        }
        switch (((wc)((Object)vector)).b) {
            case 0: {
                if (((wc)((Object)vector)).c == 1) {
                    acv.s.u();
                    acv.s.a();
                    return;
                }
                if (((wc)((Object)vector)).c == 2) {
                    wc.b(0);
                    return;
                }
                if (((wc)((Object)vector)).c == 6) {
                    acv.s.a();
                    act.e().a();
                    return;
                }
                if (((wc)((Object)vector)).c != 7) return;
                acv.s.a();
                go.a().l(5, 0);
                return;
            }
            case 1: {
                if (((wc)((Object)vector)).c == 1) {
                    acv.s.a();
                    short[][] sArrayArray = new short[][]{{28, 105}, {55, 100}, {24, 138}, {61, 148}, {105, 76}, {80, 128}, {112, 132}, {136, 111}, {76, 60}, {129, 158}, {173, 155}, {153, 160}, {145, 60}, {127, 87}, {126, 62}, {35, 60}, {35, 60}};
                    short[] sArray = new short[17];
                    sArray[1] = 1;
                    sArray[2] = 2;
                    sArray[3] = 3;
                    sArray[4] = 4;
                    sArray[5] = 5;
                    sArray[6] = 6;
                    sArray[7] = 7;
                    sArray[8] = 8;
                    sArray[9] = 9;
                    sArray[10] = 10;
                    sArray[11] = 11;
                    sArray[12] = 110;
                    sArray[13] = 111;
                    sArray[14] = 112;
                    sArray[15] = 202;
                    sArray[16] = 104;
                    short[] sArray2 = sArray;
                    int n5 = 0;
                    int n6 = 0;
                    while (n6 < sArray2.length) {
                        if (acv.s.aL == sArray2[n6]) {
                            n5 = n6;
                            break;
                        }
                        ++n6;
                    }
                    n6 = n5;
                    px.e().a(acv.o - 94, acv.p - 84 - aae.an, 197, 168, yi.ae[n6], null);
                    px.e().c = 1;
                    px.e().j = new s("Tr\u1edf l\u1ea1i", new sn((wc)((Object)vector)));
                    px.e().b = new sp((wc)((Object)vector), sArrayArray, n6);
                    px.e().a();
                    return;
                }
                if (((wc)((Object)vector)).c == 2) {
                    wc.b(3);
                    return;
                }
                if (((wc)((Object)vector)).c == 6) {
                    wc.b(5);
                    return;
                }
                if (((wc)((Object)vector)).c != 7) return;
                acv.s.a();
                acv.h();
                if (acv.s.t.cI == -1) {
                    go.a().k();
                    return;
                }
                go.a().t(0);
                abj.a(0, false, new byte[]{27});
                acv.h();
                return;
            }
            case 2: {
                Vector<hw> vector;
                if (((wc)((Object)vector)).c == 1) {
                    acv.s.a();
                    ++acv.s.s;
                    if (acv.s.s <= 2) return;
                    acv.s.s = 0;
                    return;
                }
                if (((wc)((Object)vector)).c == 2) {
                    wc.b(2);
                    return;
                }
                if (((wc)((Object)vector)).c == 6) {
                    acv.s.a();
                    if (na.e().b == null || na.e().b.size() == 0) {
                        acv.a("Ch\u01b0a c\u00f3 b\u1ea1n");
                        return;
                    }
                    vector = new Vector<hw>();
                    int n7 = 0;
                    while (n7 < na.e().b.size()) {
                        hw hw2 = (hw)na.e().b.elementAt(n7);
                        vector.addElement(hw2);
                        ++n7;
                    }
                    na.e().a(vector, 0, "B\u1ea0N B\u00c8");
                    na.e().a();
                    return;
                }
                if (((wc)((Object)vector)).c != 7) return;
                acv.h();
                go.a().b((int)acv.s.t.cI, (byte)0);
                return;
            }
            case 3: {
                if (((wc)((Object)vector)).c == 1) {
                    acv.s.a();
                    bs.e().a();
                    return;
                }
                if (((wc)((Object)vector)).c == 2) {
                    wc.b(1);
                    return;
                }
                if (((wc)((Object)vector)).c == 6) {
                    go.a().l(4, 0);
                    return;
                }
                if (((wc)((Object)vector)).c != 7) return;
                acv.h();
                go.a().n(acv.s.t.cI);
                return;
            }
            case 4: {
                if (((wc)((Object)vector)).c == 1) {
                    if (cy.a == null) return;
                    if (cy.a.b != 0) {
                        cy.a.b = 0;
                        acv.s.a();
                        return;
                    }
                    cy.a.b = 1;
                    acv.s.a();
                    return;
                }
                if (((wc)((Object)vector)).c == 2) {
                    wc.b(4);
                    return;
                }
                if (((wc)((Object)vector)).c == 6) {
                    acv.s.a();
                    go.a().l(3, 0);
                    return;
                }
                if (((wc)((Object)vector)).c != 7) return;
                acv.s.a();
                go.a().a(null, (byte)1, 0);
                return;
            }
            case 5: {
                if (((wc)((Object)vector)).c == 7) {
                    abj.a(0, true, new byte[]{29, 30});
                    return;
                }
                if (((wc)((Object)vector)).c == 2) {
                    wc.b(7);
                    return;
                }
                if (((wc)((Object)vector)).c == 6) {
                    go.a().l(7, 0);
                    return;
                }
                if (((wc)((Object)vector)).c != 1) return;
                acv.s.w = !acv.s.w;
                go.a().a(!acv.s.w ? 4 : 5);
                acv.s.a();
                acv.a(!acv.s.w ? "\u0110\u00e3 b\u1eadt ch\u1ee9c n\u0103ng nh\u1eadn l\u1eddi m\u1eddi." : "\u0110\u00e3 t\u1eaft ch\u1ee9c n\u0103ng nh\u1eadn l\u1eddi m\u1eddi.");
                return;
            }
            case 6: {
                if (((wc)((Object)vector)).c == 7) {
                    acv.s.a();
                    act.e().a("", "Bang h\u1ed9i");
                    act.e().a("Bang h\u1ed9i");
                    act.e().a();
                    return;
                }
                if (((wc)((Object)vector)).c == 1) {
                    acv.I.a();
                    return;
                }
                if (((wc)((Object)vector)).c == 6) {
                    (b.a == null ? (b.a = new b()) : b.a).a();
                    return;
                }
                if (((wc)((Object)vector)).c != 2) return;
                gv.e().a(acv.s);
                return;
            }
            case 7: {
                Vector<hw> vector;
                if (((wc)((Object)vector)).c == 7) {
                    acv.y.a("S\u1ed1 l\u01b0\u1ee3ng", new tn((wc)((Object)vector)), 1, 100, false);
                    vector = acv.y;
                    acv.w = vector;
                    return;
                }
                if (((wc)((Object)vector)).c == 6) {
                    try {
                        if (GameMidlet.f == "") return;
                        GameMidlet.a.platformRequest(GameMidlet.f);
                        return;
                    }
                    catch (Exception exception) {
                        vector = exception;
                        exception.printStackTrace();
                        return;
                    }
                }
                if (((wc)((Object)vector)).c == 2) {
                    hx.e().a(acv.s);
                    return;
                }
                if (((wc)((Object)vector)).c != 1) return;
                acv.I.a();
                acv.I.e = true;
                acv.I.o = abj.at;
                acv.I.f();
                return;
            }
            case 8: {
                if (((wc)((Object)vector)).c == 2) {
                    go.a().h();
                    return;
                }
                if (acv.s.t.cI == -1) {
                    acv.a("B\u1ea1n ch\u01b0a c\u00f3 bang");
                    return;
                }
                if (acv.s.t.af == 0) {
                    acv.b(hw.ae ? "B\u1ea1n c\u00f3 ch\u1eafc mu\u1ed1n ph\u1ee5c h\u1ed3i l\u1ea1i bang h\u1ed9i kh\u00f4ng ?" : "B\u1ea1n c\u00f3 ch\u1eafc mu\u1ed1n gi\u1ea3i t\u00e1n bang h\u1ed9i kh\u00f4ng ?", new tq((wc)((Object)vector)));
                    return;
                }
                acv.b("B\u1ea1n c\u00f3 ch\u1eafc mu\u1ed1n r\u1eddi bang kh\u00f4ng ?", new tt((wc)((Object)vector)));
            }
        }
    }

    public static void b(int n2) {
        byte[] byArray;
        nu.e().a();
        nu nu2 = nu.e();
        boolean bl2 = nu.e().t;
        if (nu.e().t) {
            byArray = nu.e().aa;
        } else {
            byte[] byArray2 = new byte[8];
            byArray2[1] = 1;
            byArray2[2] = 2;
            byArray2[3] = 3;
            byArray2[4] = 4;
            byArray2[5] = 5;
            byArray2[6] = 6;
            byArray = byArray2;
            byArray2[7] = 31;
        }
        nu2.a(n2, bl2, byArray);
        nu.R = acv.s.t;
        nu.e().j();
    }

    public final void a() {
        this.d = this.i.length;
        this.b = 0;
        this.c = -1;
        this.f = this.d * 18 - this.g;
        this.o = 0;
        this.e = 0;
        if (cy.a != null) {
            this.i[1][5] = cy.a.b == 1 ? "T\u1ea5t \u00e2m thanh" : "B\u1eadt \u00e2m thanh";
        }
        super.a();
        this.b();
    }

    static void a(wc wc2) {
        wc2.g();
    }

    static String[][] b(wc wc2) {
        return wc2.i;
    }
}

