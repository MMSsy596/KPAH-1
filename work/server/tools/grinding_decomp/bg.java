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

public abstract class bg {
    public s j;
    public s k;
    public s l;
    public static Image m;
    boolean n = false;

    public void a(Graphics graphics) {
        acv.a(graphics);
        if (this.j != null) {
            d.b.a(graphics, this.j.a, 5, acv.n - aae.an, 0);
        }
        if (this.k != null) {
            d.b.a(graphics, this.k.a, acv.o, acv.n - aae.an, 2);
        }
        if (this.l != null) {
            d.b.a(graphics, this.l.a, acv.m - 5, acv.n - aae.an, 1);
        }
        if (act.b > 0) {
            graphics.drawImage(yi.G, acv.m - 7, 7, 3);
        }
        if (abj.aq.size() <= 0 && m != null) {
            graphics.drawImage(m, acv.o, 0, 17);
        }
    }

    public void c() {
        if (acv.g) {
            if (m != null && acv.a(acv.o - 40, 0, 80, 20)) {
                if (acv.q == acv.s) {
                    if (!acv.G) {
                        Vector<s> vector = new Vector<s>();
                        vector.addElement(new s("B\u00e0n ph\u00edm", new cr(this)));
                        vector.addElement(new s("Thay \u0111\u1ed3", new cp(this)));
                        vector.addElement(new s("T\u00ecm b\u1ea1n", new cq(this)));
                        acv.u.a(vector, 3);
                    } else {
                        if (this.n) {
                            bz.b = true;
                        }
                        acv.G = !acv.G;
                        acv.a.sizeChanged(0, 0);
                        acv.g = false;
                    }
                } else {
                    if (!acv.G) {
                        if (bz.b) {
                            this.n = true;
                            bz.b = false;
                        }
                    } else if (this.n) {
                        bz.b = true;
                    }
                    acv.G = !acv.G;
                    acv.a.sizeChanged(0, 0);
                    acv.g = false;
                }
            }
            if (Math.abs(acv.D - acv.k) <= 10 && Math.abs(acv.E - acv.j) <= 10) {
                switch (acv.i()) {
                    case 0: {
                        if (this.j == null || this.j.b == null) break;
                        this.j.b.a();
                        acv.g = false;
                        break;
                    }
                    case 1: {
                        if (this.k == null || this.k.b == null) break;
                        acv.g();
                        this.k.b.a();
                        acv.g = false;
                        break;
                    }
                    case 2: {
                        if (this.l == null || this.l.b == null) break;
                        this.l.b.a();
                        acv.g = false;
                    }
                }
            }
        }
        if (acv.c[5]) {
            if (this.k != null && this.k.b != null) {
                this.k.b.a();
                acv.c[5] = false;
                return;
            }
        } else if (acv.c[12]) {
            if (this.j != null && this.j.b != null) {
                this.j.b.a();
                acv.c[12] = false;
                return;
            }
        } else if (acv.A) {
            if (acv.d[13] && this.l != null && this.l.b != null) {
                this.l.b.a();
                acv.d[13] = false;
                return;
            }
        } else if (acv.c[13] && this.l != null && this.l.b != null) {
            this.l.b.a();
            acv.c[13] = false;
        }
    }
}

