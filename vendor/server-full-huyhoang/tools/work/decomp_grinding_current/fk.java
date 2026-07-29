/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class fk
extends di {
    private int n = 120;
    private byte o = 0;
    private byte p = (byte)5;
    private byte q = (byte)10;
    private byte r;
    private byte[] s;
    private int t;

    public fk(int n2, int n3, int n4) {
        byte[] byArray = new byte[4];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 3;
        this.s = byArray;
        this.d = n2;
        this.e = n3;
        this.q = (byte)acv.a(5, 20);
        di di2 = new di(n2, n3, 34);
        new di(n2, n3, 34).k = (byte)3;
        abm.b(di2);
        this.t = n4;
    }

    public final void a() {
        this.r = (byte)(this.r + 1);
        if (this.r > this.s.length - 1) {
            this.r = 0;
        }
        this.o = this.s[this.r];
        if (this.q > 0) {
            this.q = (byte)(this.q - 1);
        }
        if (this.q <= 0 && this.n > 0) {
            this.n -= this.p;
            this.p = (byte)(this.p + 8);
            if (this.n < 0) {
                this.n = 0;
                abm.b.removeElement(this);
                abm.a.addElement(this);
            }
        }
        if (this.n == 0 && acv.l % 2 == 1) {
            abm.b(this.d, this.e, 62);
            abm.a.removeElement(this);
            acv.s.a("- " + this.t, 0, this.d, this.e - 25, 1, -2);
        }
    }

    public final void a(Graphics graphics) {
        Image image = yi.d(61);
        if (image != null) {
            graphics.drawRegion(image, 0, this.o * di.c[61], (int)di.b[61], (int)di.c[61], 0, this.d, this.e - this.n, 3);
        }
    }
}

