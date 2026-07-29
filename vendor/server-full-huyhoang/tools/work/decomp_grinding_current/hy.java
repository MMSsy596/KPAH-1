/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class hy
extends bb {
    private Vector ak = new Vector();
    private Vector al = new Vector();
    private byte[] am = new byte[]{4, 6, 5};
    private byte an;
    private byte ao;
    private byte ap;
    private byte aq = (byte)10;
    private byte ar = 0;
    private byte as;
    private long at = System.currentTimeMillis();

    public final void a(Graphics graphics) {
        if (!this.S) {
            if (yi.T[this.l].p != null) {
                int n2;
                graphics.drawImage(yi.j, (int)this.cL, this.cM + 15, 3);
                if (this.U != null) {
                    n2 = 0;
                    while (n2 < this.U.size()) {
                        ((acc)this.U.elementAt(n2)).b(graphics, this.cL, this.cM);
                        ++n2;
                    }
                }
                n2 = this.an / 5;
                int n3 = this.cM - n2 * 3;
                if (n2 > 2) {
                    n2 = 2;
                }
                yi.T[this.l].p.a(graphics, this.cL, n3, 0, 0, 1, 1);
                yi.T[this.l].p.a(graphics, this.cL, n3, this.ap + 1, this.ao, 1, 1);
                yi.T[this.l].p.a(graphics, this.cL, n3, this.am[n2], 0, 1, 1);
                yi.T[this.l].p.a(graphics, this.cL, n3, this.am[n2], 2, -1, 1);
                if (this.cW == 3) {
                    yi.T[this.l].p.a(graphics, this.cL, n3, 3, 0, 1, 1);
                }
                if (this.T != null) {
                    n2 = 0;
                    while (n2 < this.T.size()) {
                        ((acc)this.T.elementAt(n2)).b(graphics, this.cL, this.cM);
                        ++n2;
                    }
                }
            } else if (System.currentTimeMillis() > this.at) {
                this.at = System.currentTimeMillis() + 10000L;
                yi.T[this.l].o = false;
                yi.T[this.l].b();
            }
            Graphics graphics2 = graphics;
            hy hy2 = this;
            int n4 = hy2.ak.size();
            int n5 = 0;
            while (n5 < n4) {
                aas aas2 = (aas)hy2.ak.elementAt(n5);
                if (aas2 != null) {
                    aas2.a(graphics2);
                }
                ++n5;
            }
        }
        super.b(graphics);
    }

    public final void a_() {
    }

    private void d(int n2) {
        if (this.x > 20) {
            this.ap = (byte)n2;
            this.x = 0;
        }
    }

    public final void a(vh vh2) {
        super.a(vh2);
    }

    public final void a(int n2, int n3) {
        this.cW = (byte)5;
    }

    public final void b() {
        Object object;
        this.b_();
        ++this.x;
        this.an = (byte)(this.an + 1);
        if (this.an >= this.aq) {
            this.an = 0;
        }
        this.ao = 0;
        Object object2 = this;
        int n2 = 0;
        while (n2 < ((hy)object2).ak.size()) {
            object = (aas)((hy)object2).ak.elementAt(n2);
            if (object != null) {
                ((aas)object).a();
                if (((xx)object).c) {
                    ((hy)object2).ak.removeElement(object);
                    if (((hy)object2).ak.size() == 0) {
                        ((vh)object2).cF = true;
                    }
                }
            }
            ++n2;
        }
        switch (this.cW) {
            case 4: {
                break;
            }
            case 0: {
                this.aq = (byte)10;
                this.d(1);
                break;
            }
            case 3: {
                this.aq = (byte)20;
                this.d(0);
                this.i = (short)(this.i + 1);
                if (this.an / 5 <= 2 || this.i <= 20) break;
                this.i = 0;
                this.cW = (byte)2;
                if (this.as == 1) {
                    object2 = new byte[]{-1, 1};
                    n2 = 0;
                    while (n2 < 30) {
                        object = new ef(6);
                        int n3 = yi.m(2);
                        ((ef)object).a(this.cL + object2[n3] * 25, this.cM - this.an / 5 * 3, 0, this.cL + yi.m(200) * object2[n3] - 10 * object2[n3], this.cM + 20 + yi.m(150), (byte)(yi.m(10) + 15), false);
                        abj.q.addElement(object);
                        ++n2;
                    }
                    n2 = 0;
                    while (n2 < this.al.size()) {
                        object = (ap)this.al.elementAt(n2);
                        ef ef2 = new ef(6);
                        int n4 = yi.m(2);
                        ef2.a(this.cL + object2[n4] * 25, this.cM - this.an / 5 * 3, ((ap)object).J, (int)((vh)object).cL, ((vh)object).cM, (byte)20, true);
                        abj.q.addElement(ef2);
                        ++n2;
                    }
                } else {
                    int n5 = 0;
                    while (n5 < this.al.size()) {
                        ap ap2 = (ap)this.al.elementAt(n5);
                        object = new tx(ap2.cL, ap2.cM, ap2.J);
                        abm.b.addElement(object);
                        ++n5;
                    }
                }
                break;
            }
            case 5: {
                this.d(1);
                object2 = this;
                if (((hy)object2).ar >= 15) break;
                ((hy)object2).ar = (byte)(((hy)object2).ar + 1);
                Random random = new Random(System.currentTimeMillis());
                ((hy)object2).ak.addElement(new aas(((vh)object2).cL - 12 + abj.c(random.nextInt() % 25), ((vh)object2).cM - 30 + abj.c(random.nextInt() % 35), 0));
                break;
            }
            case 2: {
                this.aq = (byte)10;
                this.d(0);
                if (this.an / 5 == 1 && this.ap == 0) {
                    this.ao = (byte)2;
                }
                this.v();
            }
        }
        this.d_();
    }

    public final void a(Vector vector, byte by2) {
        this.al = vector;
        this.cW = (byte)3;
        this.k = 0;
        this.j = 0;
        this.i = 0;
        this.as = by2;
    }

    public final void a(by by2) {
        super.a(by2);
        yi.T[this.l].e = (byte)50;
        this.at = System.currentTimeMillis() + 10000L;
        this.cN = (short)100;
    }
}

