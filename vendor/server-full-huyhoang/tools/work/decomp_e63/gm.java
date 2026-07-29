/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class gm
extends aae {
    private static gm a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private Vector i = new Vector();
    private int o = -1;
    private int p;
    private int q;

    public static gm e() {
        if (a == null) {
            a = new gm();
            return a;
        }
        return a;
    }

    public final void a(int n2, int n3, int n4) {
        this.o = n2;
        this.p = n3;
        this.q = n4;
    }

    public final void d() {
        int n2;
        if (this.o > -1 && System.currentTimeMillis() / 1000L - (long)this.h > 30L) {
            this.h = (int)(System.currentTimeMillis() / 1000L);
            go.a().b(this.o, this.p, this.q);
        }
        this.f += 15;
        if (this.f >= 360) {
            this.f -= 360;
        }
        if ((n2 = this.f - 15) < 0) {
            n2 += 360;
        }
        int n3 = this.g * yg.b(yg.c(this.f)) >> 10;
        int n4 = -(this.g * yg.a(yg.c(this.f))) >> 10;
        int n5 = this.g * yg.b(yg.c(n2)) >> 10;
        n2 = -(this.g * yg.a(yg.c(n2))) >> 10;
        if (this.g < 30) {
            ++this.g;
        }
        this.b = acv.o + n3;
        this.c = acv.p + n4;
        n2 = yb.b(yg.a(this.b - (acv.o + n5), -(this.c - (acv.p + n2))));
        this.d = yb.d[n2];
        this.e = yb.c[n2];
        this.i.addElement(new kt(this.b, this.c));
        n2 = 0;
        while (n2 < this.i.size()) {
            kt kt2 = (kt)this.i.elementAt(n2);
            kt2.f = (short)(kt2.f + 1);
            if (kt2.f >= 20) {
                this.i.removeElement(kt2);
            }
            ++n2;
        }
    }

    public final void a(Graphics graphics) {
        graphics.setColor(0);
        graphics.fillRect(0, 0, acv.m, acv.n);
        int n2 = 0;
        while (n2 < this.i.size()) {
            kt kt2 = (kt)this.i.elementAt(n2);
            if (yi.d(8) != null) {
                graphics.drawRegion(yi.d(8), 0, kt2.f / 2 * 10, 10, 10, 0, kt2.a, kt2.b, 3);
            }
            ++n2;
        }
        if (yi.c(3) != null) {
            graphics.drawRegion(yi.c(3), 0, this.d * 24, 24, 24, this.e, this.b, this.c, 3);
        }
    }

    public final void a() {
        this.f = 0;
        this.g = 5;
        this.i.removeAllElements();
        this.h = (int)(System.currentTimeMillis() / 1000L);
        super.a();
    }
}

