/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class gy
extends hc {
    private String b;
    public bz a = new bz();
    private gj c;
    private gj d;
    private int e;
    private boolean f = false;

    public gy() {
        this.a.i = 21;
        this.a.g = acv.n - 56;
        this.a.a = true;
    }

    public final void a() {
        this.a.g = acv.n - 56;
        this.e = d.b.a(this.b) + 30;
        if (this.e > acv.m) {
            String string = "";
            int n2 = 0;
            while (n2 < this.b.length() - 1) {
                if (d.b.a(string = String.valueOf(string) + this.b.substring(n2, n2 + 1)) >= acv.m - 50) {
                    this.b = String.valueOf(string) + "...";
                    this.e = d.b.a(this.b) + 30;
                    break;
                }
                ++n2;
            }
        } else if (!this.f) {
            this.e = acv.m - 30;
        }
        if (this.e < 110) {
            this.e = 110;
        }
        this.a.h = this.e - 40;
        this.a.f = acv.o - this.a.h / 2;
    }

    public final void a(String string, gj gj2, int n2, int n3, boolean bl2) {
        this.f = bl2;
        this.a.a("");
        this.a.c(n2);
        this.a.b(n3);
        this.b = string;
        this.c = gj2;
        this.d = new wr(this);
        this.j = new s("\u0110\u00f3ng", this.d);
        this.k = new s("Ok", this.c);
        this.l = this.a.e;
        this.a();
    }

    public final void a(Graphics graphics) {
        acv.a(graphics);
        yi.d(graphics, acv.o - this.e / 2, acv.n - 90, this.e, 69);
        d.b.a(graphics, this.b, acv.o, acv.n - 86, 2);
        this.a.a(graphics);
        super.a(graphics);
    }

    public final void a(int n2) {
        boolean bl2 = this.a.a(n2);
        if (bl2) {
            acv.c[5] = false;
        }
    }

    public final void b() {
        this.a.d();
        super.c();
    }

    public final void c() {
    }
}

