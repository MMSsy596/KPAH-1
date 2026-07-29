/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class dn
extends di {
    private static int[] o = new int[]{0xFCFCFD, 11188220};
    public Vector n = new Vector();
    private Vector[] p;
    private kt q;
    private long r = 0L;
    private long s = 0L;
    private boolean t = false;
    private boolean u = true;
    private int v = 0;
    private int w = 0;
    private int x = 7;

    public final void b() {
        this.r = System.currentTimeMillis() / 10L;
        this.s = System.currentTimeMillis() / 10L;
        this.n.removeAllElements();
    }

    public final void a(Vector vector, kt kt2, boolean bl2) {
        kt kt3;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        kt kt4;
        int n7;
        int n8;
        int n9;
        if (vector.size() == 0) {
            return;
        }
        this.t = bl2;
        if (!bl2) {
            Vector vector2 = vector;
            n9 = vector2.size();
            n8 = 0;
            while (n8 < n9 - 1) {
                kt kt5 = (kt)vector2.elementAt(n8);
                n7 = n8 + 1;
                while (n7 < n9) {
                    kt4 = (kt)vector2.elementAt(n7);
                    if (kt5.a > kt4.a) {
                        vector2.setElementAt(kt5, n7);
                        vector2.setElementAt(kt4, n8);
                        kt5 = kt4;
                    }
                    ++n7;
                }
                ++n8;
            }
        }
        this.n = vector;
        this.q = kt2;
        this.p = new Vector[vector.size()];
        int n10 = 0;
        while (n10 < this.p.length) {
            this.p[n10] = new Vector();
            ++n10;
        }
        kt2.c = (byte)-1;
        this.p[0].addElement(kt2);
        n10 = -1;
        n9 = 0;
        while (n9 < vector.size()) {
            n8 = kt2.a;
            int n11 = kt2.b;
            if (bl2 && n10 != -1) {
                kt kt6 = (kt)vector.elementAt(n10);
                n8 = kt6.a;
                n11 = kt6.b;
            }
            n10 = !bl2 ? dn.b(vector) : ++n10;
            dn.a(vector);
            n7 = 0;
            n7 = this.p[n10].size() - 1;
            kt4 = (kt)vector.elementAt(n10);
            n6 = yg.a(kt4.a - n8, -(kt4.b - n11));
            n5 = yi.m(15) + 10;
            n4 = 0;
            n3 = 0;
            while (true) {
                n3 = 0;
                if (n4 != 0) {
                    n3 = n6 - 5 + yi.m(10);
                }
                n3 = yg.c(n3);
                n2 = n5 * n4 * yg.b(n3) >> 10;
                n3 = -(n5 * n4 * yg.a(n3)) >> 10;
                kt3 = new kt(n8 + n2, n11 + n3, n7++);
                this.p[n10].addElement(kt3);
                if (yg.a(n8, n11, n8 + n2, n11 + n3) >= yg.a(n8, n11, kt4.a, kt4.b) - 20) break;
                ++n4;
            }
            ++n9;
        }
        n9 = 0;
        while (n9 < this.p.length) {
            n8 = this.p[n9].size();
            kt kt7 = (kt)vector.elementAt(n9);
            ((kt)vector.elementAt(n9)).c = (byte)(this.p[n9].size() - 1);
            kt7.f = (short)-1;
            kt kt8 = new kt(kt7.a, kt7.b, kt7.c);
            new kt(kt7.a, kt7.b, kt7.c).f = (short)-1;
            this.p[n9].addElement(kt8);
            n7 = 1;
            while (n7 < n8) {
                kt4 = (kt)this.p[n9].elementAt(n7);
                n6 = yi.m(2);
                n5 = 0;
                while (n5 < n6) {
                    n4 = 180 + yi.m(180);
                    n3 = 5 + yi.m(10);
                    n2 = n3 * yg.b(yg.c(n4)) >> 10;
                    n3 = -(n3 * yg.a(yg.c(n4))) >> 10;
                    kt3 = new kt(kt4.a + n2, kt4.b + n3, n7);
                    new kt(kt4.a + n2, kt4.b + n3, n7).f = 0;
                    this.p[n9].addElement(kt3);
                    ++n5;
                }
                ++n7;
            }
            ++n9;
        }
    }

    private static int a(Vector vector) {
        int n2 = 0;
        int n3 = 0;
        while (n3 < vector.size()) {
            kt kt2 = (kt)vector.elementAt(n3);
            if (kt2.f != -1) {
                ++n2;
            }
            ++n3;
        }
        if (n2 == 0) {
            return -1;
        }
        n2 = yi.m(n2);
        n3 = 0;
        int n4 = 0;
        while (n4 < vector.size()) {
            kt kt3 = (kt)vector.elementAt(n4);
            if (kt3.f != -1 && n2 == ++n3) {
                return n4;
            }
            ++n4;
        }
        return -1;
    }

    private static int b(Vector vector) {
        int n2 = 0;
        int n3 = 0;
        while (n3 < vector.size()) {
            kt kt2 = (kt)vector.elementAt(n3);
            if (kt2.f == -1) {
                ++n2;
            }
            ++n3;
        }
        if (n2 == 0) {
            return -1;
        }
        n2 = yi.m(n2);
        n3 = 0;
        int n4 = 0;
        while (n4 < vector.size()) {
            kt kt3 = (kt)vector.elementAt(n4);
            if (kt3.f == -1) {
                if (n2 == n3) {
                    kt3.f = 0;
                    return n4;
                }
                ++n3;
            }
            ++n4;
        }
        return -1;
    }

    public final void a() {
        if (acv.l % 2 == 1) {
            this.q.c = (byte)-1;
            this.q.f = (short)-1;
            int n2 = 0;
            while (n2 < this.n.size()) {
                kt kt2 = (kt)this.n.elementAt(n2);
                ((kt)this.n.elementAt(n2)).f = (short)-1;
                kt2.c = (byte)-1;
                ++n2;
            }
            if (this.t && this.u && this.n.size() > 1 && System.currentTimeMillis() / 10L - this.s > 30L) {
                this.s = System.currentTimeMillis() / 10L;
                this.q = (kt)this.n.elementAt(0);
                this.n.removeElementAt(0);
            }
            this.a(this.n, this.q, this.t);
            if (System.currentTimeMillis() / 10L - this.r > (long)(60 + (this.t ? 20 : 0))) {
                this.x = 7;
                abm.b.removeElement(this);
            }
            ++this.r;
        }
    }

    public final void a(Graphics graphics) {
        this.w = 0;
        yi.H.a(this.v / 3, this.q.a, this.q.b, 0, 3, graphics);
        ++this.v;
        if (this.v >= 12) {
            this.v = 0;
        }
        if (this.p != null) {
            int n2 = 0;
            while (n2 < this.p.length) {
                int n3 = 0;
                while (n3 < this.p[n2].size()) {
                    Object object = (kt)this.p[n2].elementAt(n3);
                    if (((kt)object).c >= 0 && ((kt)object).c < this.p[n2].size()) {
                        kt kt2;
                        kt kt3 = kt2 = (kt)this.p[n2].elementAt(((kt)object).c);
                        kt kt4 = object;
                        kt2 = graphics;
                        object = this;
                        kt2.setColor(o[0]);
                        kt2.drawLine(kt4.a, kt4.b, kt3.a, kt3.b);
                        if (kt4.f == -1) {
                            kt2.setColor(o[1]);
                            kt2.drawLine(kt4.a - 1, kt4.b, kt3.a - 1, kt3.b);
                            if (((dn)object).t && ((dn)object).u) {
                                kt2.drawLine(kt4.a + 1, kt4.b, kt3.a + 1, kt3.b);
                            }
                        }
                        if (this.t && this.u) {
                            ++this.w;
                            if (this.w >= this.x) {
                                this.x += 7;
                                return;
                            }
                        }
                    }
                    ++n3;
                }
                kt kt5 = (kt)this.n.elementAt(n2);
                yi.f(32);
                if (yi.I != null) {
                    yi.I.a(1 + kt5.d / 4, kt5.a, kt5.b, 0, 3, graphics);
                }
                kt5.d = (byte)(kt5.d + 1);
                if (kt5.d >= 12) {
                    kt5.d = 0;
                }
                ++n2;
            }
        }
    }
}

