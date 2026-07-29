/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class aaa
extends aae {
    public Vector a = new Vector();
    byte b;
    private int c;
    private int d;
    private static aaa e;

    public static aaa e() {
        if (e == null) {
            e = new aaa();
            return e;
        }
        return e;
    }

    public final void a(abs abs2) {
        try {
            this.a = new Vector();
            int n2 = abs2.b().readByte();
            int n3 = 0;
            while (n3 < n2) {
                byte by2 = abs2.b().readByte();
                this.a.addElement(String.valueOf(by2 < 10 ? "0" : "") + by2);
                ++n3;
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a(Graphics graphics) {
        acv.s.a(graphics);
        acv.a(graphics);
        int n2 = this.c;
        int n3 = this.d;
        yi.c(graphics, n2 - 5, n3 - 5, 171, 171);
        graphics.setColor(0xFFFFFF);
        graphics.fillRect(n2 + (this.b % 10 << 4), n3 + (this.b / 10 << 4), 16, 16);
        int n4 = 0;
        while (n4 < 100) {
            graphics.drawImage(yi.t, n2 + (n4 % 10 << 4), n3 + (n4 / 10 << 4), 0);
            ++n4;
        }
        n4 = 0;
        while (n4 < this.a.size()) {
            String string = (String)this.a.elementAt(n4);
            d.j[1].a(graphics, string, n2 + 3 + (n4 % 10 << 4), n3 + 2 + (n4 / 10 << 4), 0);
            ++n4;
        }
        super.a(graphics);
    }

    public final void d() {
        acv.s.d();
        super.d();
    }

    public final void c() {
        if (acv.c[8]) {
            acv.c[8] = false;
            this.b = (byte)(this.b + 10);
            if (this.b > 99) {
                this.b = 0;
            }
        } else if (acv.c[4]) {
            acv.c[4] = false;
            this.b = (byte)(this.b - 1);
            if (this.b < 0) {
                this.b = (byte)99;
            }
        } else if (acv.c[6]) {
            acv.c[6] = false;
            this.b = (byte)(this.b + 1);
            if (this.b > 99) {
                this.b = 0;
            }
        } else if (acv.c[2]) {
            acv.c[2] = false;
            this.b = (byte)(this.b - 10);
            if (this.b < 0) {
                this.b = (byte)99;
            }
        }
        if (acv.a(this.c, this.d, 160, 160) && acv.g) {
            int n2 = (acv.E - this.c) / 16;
            int n3 = (acv.D - this.d) / 16;
            this.b = (byte)(n3 * 10 + n2);
            if (this.b <= 0) {
                this.b = 0;
            }
            if (this.b > 99) {
                this.b = (byte)99;
            }
            acv.g = false;
            if (this.k != null) {
                this.k.b.a();
            }
        }
        super.c();
    }

    public final void a() {
        super.a();
        this.b();
    }

    public final void b() {
        super.b();
        this.k = new s("Ch\u1ecdn", new aac(this));
        this.l = new s("\u0110\u00f3ng", new zw(this));
        this.c = acv.o - 80;
        this.d = (acv.n - 35) / 2 - 80;
        if (this.d < 10) {
            this.d = 10;
        }
    }
}

