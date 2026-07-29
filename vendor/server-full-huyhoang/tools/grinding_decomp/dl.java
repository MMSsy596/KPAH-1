/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class dl
extends di {
    private gx p;
    private int q = 7;
    private int r = -1;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    private int z;
    private int A;
    private int B;
    private Vector C = new Vector();
    public String[] n;
    private ap D;
    boolean o;

    public dl(int n2, int n3, ap ap2, ap ap3, byte by2) {
        this.g = by2;
        this.D = ap3;
        this.p = new gx();
        this.w = n2;
        this.x = n3;
        ap ap4 = ap3;
        if (by2 == 5) {
            this.w = ap3.cL;
            this.x = ap3.cM;
            ap4.cL = (short)this.w;
            ap4.cM = (short)(this.x - 200);
            this.D = ap4;
            this.p.a(by2, this.w, this.x, ap3.D, ap4);
            n2 = yg.a(this.w - this.d, -(this.x - this.e));
            this.p.a(yg.c(n2 - 45));
        } else {
            this.p.a(by2, n2, n3, ap2.D, ap4);
            n2 = yg.a(n2 - this.d, -(n3 - this.e));
            this.p.a(yg.c(n2 - 180));
        }
        this.n = new String[2];
    }

    public final void a() {
        if (this.p != null) {
            this.p.a();
            if (this.p.i) {
                if (this.g != 5) {
                    abm.b(this.p.a, this.p.b, this.g == 4 ? 30 : 28);
                }
                abm.b.removeElement(this);
                this.D.a_();
                if (this.n[0] != null) {
                    acv.s.a(this.n[0], 0, this.d, this.e - 15, 1, -2);
                }
                if (this.n[1] != null) {
                    acv.s.a(this.n[1], 0, this.d, this.e - 15, 2, -2);
                    return;
                }
            } else {
                this.d = this.p.a;
                this.e = this.p.b;
                int n2 = yg.a(this.w - this.d, -(this.x - this.e));
                int n3 = yg.c(n2 + 90);
                int n4 = this.q * yg.b(n3) >> 10;
                n3 = -(this.q * yg.a(n3)) >> 10;
                this.s = this.d + n4;
                this.t = this.e + n3;
                n3 = yg.c(n2 - 90);
                n4 = this.q * yg.b(n3) >> 10;
                n3 = -(this.q * yg.a(n3)) >> 10;
                this.u = this.d + n4;
                this.v = this.e + n3;
                if (this.g == 5 && this.o) {
                    n3 = yg.c(n2 + 135);
                    n4 = this.q * yg.b(n3) >> 10;
                    n3 = -(this.q * yg.a(n3)) >> 10;
                    this.y = this.d + n4 + 10;
                    this.z = this.e + n3 - 10;
                    n2 = yg.c(n2 - 135);
                    n3 = this.q * yg.b(n2) >> 10;
                    n2 = -(this.q * yg.a(n2)) >> 10;
                    this.A = this.d + n3 + 10;
                    this.B = this.e + n2 - 10;
                }
                this.q += this.r * 3;
                if (yg.d(this.q) > 9) {
                    this.r = -this.r;
                }
                kt kt2 = new kt(this.s, this.t, 0);
                this.C.addElement(kt2);
                kt kt3 = new kt(this.u, this.v, 0);
                this.C.addElement(kt3);
                if (this.g == 5 && this.o) {
                    kt2 = new kt(this.y, this.z, 0);
                    this.C.addElement(kt2);
                    kt kt4 = new kt(this.A, this.B, 0);
                    this.C.addElement(kt4);
                }
                this.w = this.d;
                this.x = this.e;
            }
        }
    }

    public final void a(Graphics graphics) {
        int n2;
        if (this.g == 4) {
            n2 = 0;
            while (n2 < this.C.size()) {
                kt kt2 = (kt)this.C.elementAt(n2);
                yi.a(graphics, 29, 0, (17 - kt2.c) * 11, 11, 11, kt2.a, kt2.b, 3);
                kt2.c = (byte)(kt2.c + 1);
                if (kt2.c >= 18) {
                    this.C.removeElement(kt2);
                }
                ++n2;
            }
        } else {
            n2 = 0;
            while (n2 < this.C.size()) {
                kt kt3 = (kt)this.C.elementAt(n2);
                yi.a(graphics, 8, 0, kt3.c * 10, 10, 10, kt3.a, kt3.b, 3);
                kt3.c = (byte)(kt3.c + 1);
                if (kt3.c >= 10) {
                    this.C.removeElement(kt3);
                }
                ++n2;
            }
        }
        if (yi.c(this.g) != null && !this.p.i) {
            if (this.g == 5) {
                Image image = yi.c(3);
                if (image != null) {
                    graphics.drawRegion(image, 0, this.p.g * 24, 24, 24, this.p.h, this.s, this.t, 3);
                    graphics.drawRegion(image, 0, this.p.g * 24, 24, 24, this.p.h, this.u, this.v, 3);
                    if (this.o) {
                        graphics.drawRegion(image, 0, this.p.g * 24, 24, 24, this.p.h, this.y, this.z, 3);
                        graphics.drawRegion(image, 0, this.p.g * 24, 24, 24, this.p.h, this.A, this.B, 3);
                        return;
                    }
                }
            } else {
                Image image = yi.c(this.g);
                if (image != null) {
                    graphics.drawRegion(image, 0, this.p.g * 24, 24, 24, this.p.h, this.s, this.t, 3);
                    graphics.drawRegion(image, 0, this.p.g * 24, 24, 24, this.p.h, this.u, this.v, 3);
                }
            }
        }
    }
}

