/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class bq
extends aae {
    public static bq a;
    public sc[] b;
    sc[] c;
    public int d = 0;
    private s f;
    private s g;
    public s e;
    private s h;

    public static bq e() {
        if (a == null) {
            a = new bq();
            return a;
        }
        return a;
    }

    public final void a(sc[] scArray) {
        if (!aco.a().c) {
            return;
        }
        acv.v.i();
        if (!acv.s.aM) {
            acv.g();
        }
        this.b = scArray;
        this.c = new sc[scArray.length];
        this.c = scArray;
        this.f = new s("Ch\u1ecdn", new aao(this));
        this.g = new s("T\u1ea1o m\u1edbi", new aan(this));
        this.e = new s("X\u00f3a", new aak(this));
        this.h = new s("Tho\u00e1t", new aal(this));
        if (this.d < this.c.length) {
            this.k = this.f;
            this.j = this.e;
        } else {
            this.k = this.g;
            this.j = null;
        }
        this.l = this.h;
        if (scArray.length > 0) {
            if (scArray[this.d].ac == 1) {
                this.e.a = "X\u00f3a";
                return;
            }
            this.e.a = "Kh\u00f4i ph\u1ee5c";
        }
    }

    public final void a(Graphics graphics) {
        yv.e();
        yv.b(graphics);
        acv.a(graphics);
        d.b.a(graphics, "Ch\u1ecdn nh\u00e2n v\u1eadt:", acv.o, acv.p - 65, 2);
        int n2 = acv.o - 60;
        int n3 = acv.p - 30;
        int n4 = 0;
        while (n4 < 3) {
            if (n4 == this.d) {
                graphics.setColor(0xF09F00);
                if (n4 < this.b.length) {
                    graphics.setColor(this.b.length > 0 && this.b[n4].ac != 1 ? 2205861 : 0xF09F00);
                }
            } else {
                graphics.setColor(10644224);
                if (n4 < this.b.length) {
                    graphics.setColor(this.b.length > 0 && this.b[n4].ac != 1 ? 34949 : 10644224);
                }
            }
            graphics.fillRect(n2 + 3, n3 - 10, 34, 84);
            graphics.setColor(1379840);
            graphics.drawRect(n2 + 3, n3 - 10, 34, 84);
            if (n4 < this.c.length) {
                this.c[n4].cL = (short)(n2 + 20);
                this.c[n4].cM = (short)(n3 + 64);
                this.c[n4].a(graphics, this.c[n4].cL, this.c[n4].cM);
            }
            ++n4;
            n2 += 40;
        }
        if (this.d < this.c.length) {
            d.b.a(graphics, String.valueOf(this.b[this.d].an) + " lv: " + (this.b[this.d].o > 0 ? (int)this.b[this.d].o : 1), acv.o, acv.p + 45, 2);
            if (this.b[this.d].ac == 0) {
                d.b.a(graphics, "C\u00f2n l\u1ea1i: " + this.b[this.d].Y + " ng\u00e0y", acv.o, acv.p + 58, 2);
            }
            if (this.b[this.d].cT != -1) {
                n4 = d.b.a(String.valueOf(this.b[this.d].an) + " lv: " + (this.b[this.d].o > 0 ? (int)this.b[this.d].o : 1));
                graphics.drawRegion(yi.O, 0, this.b[this.d].cT * 11, 11, 11, 0, acv.o - n4 / 2 - 15, acv.p + 48, 20);
            }
        }
        super.a(graphics);
    }

    public final void d() {
        acv.v.h();
        int n2 = 0;
        while (n2 < this.c.length) {
            this.c[n2].b();
            ++n2;
        }
        n2 = 0;
        if (acv.b(4)) {
            --this.d;
            n2 = 1;
            if (this.d < 0) {
                this.d = 2;
            }
            if (this.b.length > 0) {
                this.e.a = this.d < this.b.length ? (this.b[this.d].ac == 1 ? "X\u00f3a" : "Kh\u00f4i ph\u1ee5c") : "";
            }
        } else if (acv.b(6)) {
            ++this.d;
            n2 = 1;
            if (this.d > 2) {
                this.d = 0;
            }
            if (this.b.length > 0) {
                this.e.a = this.d < this.b.length ? (this.b[this.d].ac == 1 ? "X\u00f3a" : "Kh\u00f4i ph\u1ee5c") : "";
            }
        }
        int n3 = -1;
        if (acv.g && acv.a(acv.o - 58, acv.p - 40, 115, 84)) {
            n3 = (acv.j - (acv.o - 58)) / 38;
            n2 = 1;
        }
        if (n2 != 0) {
            if (this.d < this.c.length) {
                this.k = this.f;
                this.j = this.e;
            } else {
                this.k = this.g;
                this.j = null;
            }
            if (n3 == this.d) {
                this.k.b.a();
            }
            if (n3 < 3 && n3 >= 0) {
                this.d = n3;
            }
        }
        super.d();
    }
}

