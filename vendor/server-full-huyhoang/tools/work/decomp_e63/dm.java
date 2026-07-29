/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class dm
extends di {
    private int n = 0;
    private byte o = 0;
    private static int[] p = new int[]{15006715, 11598072, 7994363, 4062203, 16383610, 16682106};
    private Vector q = new Vector();
    private boolean r = false;
    private boolean s = false;

    public dm(int n2, int n3, short s2, byte by2, boolean bl2) {
        this.r = bl2;
        this.d = n2;
        this.e = n3;
        this.g = s2;
        this.o = by2;
        this.s = false;
    }

    public final void a() {
        if (this.g != 0 && this.d + 20 > abj.h && this.d - 20 < abj.h + acv.m && this.e > abj.i && this.e < abj.i + acv.n) {
            int n2 = 0;
            while (n2 < 2) {
                int n3 = yi.m(360);
                int n4 = 18 * yg.b(yg.c(n3)) >> 10;
                n3 = -(18 * yg.a(yg.c(n3))) >> 10;
                n3 -= n3 / 3;
                kt kt2 = new kt(this.d + n4, this.e + n3, (byte)(15 + yi.m(20)));
                new kt(this.d + n4, this.e + n3, (byte)(15 + yi.m(20))).e = (byte)yi.m(20);
                this.q.addElement(kt2);
                ++n2;
            }
        }
        if (this.r) {
            if (this.g != acv.s.aL) {
                if (this.o == 1) {
                    abm.b.removeAllElements();
                    return;
                }
                if (this.o == 0) {
                    abm.a.removeAllElements();
                    return;
                }
            } else if (!this.s && yg.a(this.d, this.e, (int)acv.s.t.cL, (int)acv.s.t.cM) < 17) {
                go.a().a(this.d, this.e, this.g);
                acv.s.u = null;
                gm.e().a();
                this.s = true;
            }
        }
    }

    public final void a(Graphics graphics) {
        if (this.d + 20 > abj.h && this.d - 20 < abj.h + acv.m && this.e > abj.i && this.e - 40 < abj.i + acv.n) {
            if (this.o == 0) {
                graphics.drawRegion(yi.i(), 0, this.n / 6 * 24, 39, 24, 0, this.d, this.e, 3);
                ++this.n;
                if (this.n >= 12) {
                    this.n = 0;
                    return;
                }
            } else {
                int n2 = 0;
                while (n2 < this.q.size()) {
                    kt kt2 = (kt)this.q.elementAt(n2);
                    if (kt2.e == 5) {
                        graphics.setColor(p[4]);
                    } else if (kt2.e == 15) {
                        graphics.setColor(p[5]);
                    } else {
                        graphics.setColor(p[kt2.c / 10]);
                    }
                    graphics.fillRect(kt2.a, kt2.b - (1 + kt2.c / 3), 1, 1 + kt2.c / 3);
                    kt2.b -= 2;
                    kt2.c = (byte)(kt2.c - 1);
                    if (kt2.c < 0) {
                        this.q.removeElementAt(n2);
                    }
                    ++n2;
                }
            }
        }
    }
}

