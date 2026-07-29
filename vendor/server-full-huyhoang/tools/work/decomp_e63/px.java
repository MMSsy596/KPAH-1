/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class px
extends aae {
    public static px a;
    public dy b;
    private aae d;
    private int e;
    private int f;
    private int g;
    private int h;
    public int c;
    private String i;

    public static px e() {
        if (a == null) {
            a = new px();
            return a;
        }
        return a;
    }

    public final void a() {
        if (acv.q != this) {
            this.d = acv.q;
        }
        super.a();
    }

    public final void a(int n2, int n3, int n4, int n5, String string, s s2) {
        this.c = 0;
        this.e = n2;
        this.f = n3;
        this.g = n4;
        this.h = n5;
        this.i = string;
        this.k = s2;
        this.j = new s("\u0110\u00f3ng", new gu(this));
    }

    public final void d() {
        if (this.d != acv.J) {
            this.d.d();
        }
        if (this.b != null) {
            this.b.a();
        }
    }

    public final boolean a(int n2) {
        this.b.a(n2);
        return false;
    }

    public final void a(Graphics graphics) {
        this.d.a(graphics);
        acv.a(graphics);
        graphics.translate(this.e, this.f);
        yi.d(graphics, 0, 0, this.g, this.h);
        if (this.c == 0) {
            d.c.a(graphics, this.i, this.g / 2, 3, 2);
        } else {
            d.j[0].a(graphics, this.i, this.g / 2, 6, 2);
        }
        if (this.b != null) {
            this.b.a(graphics);
        }
        super.a(graphics);
    }

    public final void a(aae aae2) {
        this.d = aae2;
        super.a(aae2);
    }

    static aae a(px px2) {
        return px2.d;
    }
}

