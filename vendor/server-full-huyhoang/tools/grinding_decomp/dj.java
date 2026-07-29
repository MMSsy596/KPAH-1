/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class dj
extends bb {
    private Vector ak = new Vector();
    private Vector al = new Vector();
    private int am = -1;
    private int an = 2;
    private int ao = 2;
    private int ap;
    private int aq;
    private byte ar = (byte)4;
    private byte as = 1;
    private byte at;
    private byte au = 0;
    private int av = -1;
    private long aw = 1L;
    private byte ax;
    private long ay = System.currentTimeMillis();

    public final void a(Graphics graphics) {
        int n2;
        this.l = (short)39;
        if (this.au < 15) {
            if (yi.T[this.l].p != null) {
                if (acv.l % 2 == 1) {
                    this.at = (byte)(this.at + this.as);
                    if (yg.d(this.at) > 1) {
                        this.as = -this.as;
                    }
                }
                if (this.U != null) {
                    n2 = 0;
                    while (n2 < this.U.size()) {
                        ((acc)this.U.elementAt(n2)).b(graphics, this.cL, this.cM);
                        ++n2;
                    }
                }
                yi.T[this.l].p.a(graphics, this.cL, this.cM, 0, 0, 1, 1);
                yi.T[this.l].p.a(graphics, this.cL, this.cM, 0, 2, -1, 1);
                yi.T[this.l].p.a(graphics, this.cL, this.cM + this.at, this.ar, 0, 1, 1);
                yi.T[this.l].p.a(graphics, this.cL, this.cM + this.at, this.ar, 2, -1, 1);
                yi.T[this.l].p.a(graphics, this.cL, this.cM, 1, 0, 1, 1);
                yi.T[this.l].p.a(graphics, this.cL, this.cM, 1, 0, -1, 1);
                yi.T[this.l].p.a(graphics, this.cL, this.cM, this.an, 0, 1, 1);
                yi.T[this.l].p.a(graphics, this.cL, this.cM, this.ao, 2, -1, 1);
                if (this.T != null) {
                    n2 = 0;
                    while (n2 < this.T.size()) {
                        ((acc)this.T.elementAt(n2)).b(graphics, this.cL, this.cM);
                        ++n2;
                    }
                }
            } else if (System.currentTimeMillis() > this.ay) {
                this.ay = System.currentTimeMillis() + 10000L;
                yi.T[this.l].o = false;
                yi.T[this.l].b();
            }
        }
        n2 = this.ak.size();
        int n3 = 0;
        while (n3 < n2) {
            xx xx2 = (xx)this.ak.elementAt(n3);
            if (xx2 != null) {
                xx2.a(graphics);
            }
            ++n3;
        }
    }

    public final void a_() {
    }

    public final void a(vh vh2) {
        super.a(vh2);
    }

    public final void a(int n2, int n3) {
        this.cW = (byte)5;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.w == 0) {
            return;
        }
        if (yi.T[this.l].p != null) {
            yi.T[this.l].p.a(graphics, n2, 20, 4, 0, 1, 1);
        }
    }

    /*
     * Unable to fully structure code
     */
    public final void b() {
        this.b_();
        this.l = (short)39;
        var1_1 = this;
        var2_2 = 0;
        while (var2_2 < var1_1.ak.size()) {
            var3_4 = (xx)var1_1.ak.elementAt(var2_2);
            if (var3_4 != null) {
                var3_4.a();
                if (var3_4.c) {
                    var1_1.ak.removeElement(var3_4);
                    if (var1_1.ak.size() == 0) {
                        var1_1.cF = true;
                    }
                }
            }
            ++var2_2;
        }
        if (this.aw != 0L) {
            this.x += this.av;
            ++this.av;
            if (this.av > 1) {
                this.aw = 0L;
                this.av = -1;
            }
        } else if (acv.l % 10 == 0) {
            this.aw = 1L;
        }
        switch (this.cW) {
            case 0: {
                this.ar = (byte)4;
                ** GOTO lbl59
            }
            case 3: {
                this.ar = (byte)5;
                this.i = (short)(this.i + 1);
                if (this.i > 6) {
                    if (this.i <= 15) break;
                    this.i = 0;
                    this.cW = (byte)2;
                    break;
                }
                if (this.i != 2 || this.d == null) break;
                this.d.a(this);
                break;
            }
            case 5: {
                this.ar = (byte)4;
                this.O = (byte)2;
                this.i = (short)(this.i + 1);
                this.cL = (short)(this.cL + this.j);
                this.cM = (short)(this.cM + this.k);
                this.j = (short)(this.j >> 1);
                this.k = (short)(this.k >> 1);
                var1_1 = this;
                if (var1_1.au >= 15) break;
                var1_1.au = (byte)(var1_1.au + 1);
                var2_3 = new Random(System.currentTimeMillis());
                var1_1.ak.addElement(new aas(var1_1.cL - 12 + abj.c(var2_3.nextInt() % 25), var1_1.cM - 30 + abj.c(var2_3.nextInt() % 35), 0));
                break;
            }
            case 2: {
                this.ar = (byte)4;
                this.i = (short)(this.i + 1);
                if (this.i > 6) {
                    this.i = 0;
                }
lbl59:
                // 4 sources

                this.O = 0;
            }
        }
        if (this.am != -1) {
            ++this.am;
            if (this.am >= 8) {
                this.am = -1;
            }
        }
        if (this.aq > 0) {
            --this.aq;
            if (this.aq == 0) {
                this.ao = 2;
                this.d(-1);
            }
        }
        if (this.ap > 0) {
            --this.ap;
            if (this.ap == 0) {
                this.an = 2;
                this.d(1);
            }
        }
    }

    private void d(int n2) {
        int n3 = 0;
        while (n3 < this.al.size()) {
            ap ap2 = (ap)this.al.elementAt(n3);
            kc kc2 = new kc(this.cL + n2 * 40, this.cM + 30, ap2, 1, true);
            if (ap2.J != 0) {
                kc2.n[0] = "-" + ap2.J;
            }
            abm.a(this.cL + n2 * 50, this.cM - 30, 14);
            abm.b.addElement(kc2);
            ++n3;
        }
    }

    public final void a(Vector vector, byte by2) {
        this.al.removeAllElements();
        this.cW = (byte)3;
        this.k = 0;
        this.j = 0;
        this.i = 0;
        this.am = 0;
        yi.m(2);
        this.ax = 0;
        this.O = 1;
        by2 = 0;
        while (by2 < vector.size()) {
            Object object = (ap)vector.elementAt(by2);
            object = new yh(((vh)object).cL, ((vh)object).cM, this.cL, this.cM, ((ap)object).J);
            abm.b.addElement(object);
            by2 = (byte)(by2 + 1);
        }
    }

    public final void a(by by2) {
        super.a(by2);
        yi.T[this.l].e = (byte)50;
        this.ay = System.currentTimeMillis() + 10000L;
        this.cN = (short)100;
    }
}

