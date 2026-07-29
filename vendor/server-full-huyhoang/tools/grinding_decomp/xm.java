/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class xm
extends bb {
    private Vector ak = new Vector();
    private byte[] al;
    private byte[] am;
    private int an;
    private byte ao;
    private Vector ap;

    public xm(int n2) {
        byte[] byArray = new byte[8];
        byArray[2] = 1;
        byArray[3] = 1;
        byArray[4] = 1;
        byArray[5] = 1;
        byArray[6] = 1;
        byArray[7] = 1;
        this.al = byArray;
        byte[] byArray2 = new byte[6];
        byArray2[3] = 1;
        byArray2[4] = 1;
        byArray2[5] = 1;
        this.am = byArray2;
        this.ao = 0;
        this.ap = new Vector();
        this.an = n2;
    }

    public final void a(Graphics graphics) {
        if (!this.S) {
            if (yi.T[this.l] != null && yi.T[this.l].a != null) {
                int n2;
                if (this.U != null) {
                    n2 = 0;
                    while (n2 < this.U.size()) {
                        ((acc)this.U.elementAt(n2)).b(graphics, this.cL, this.cM);
                        ++n2;
                    }
                }
                graphics.drawRegion(yi.T[this.l].a, 0, this.O * yi.T[this.l].f, (int)yi.T[this.l].e, (int)yi.T[this.l].f, 0, this.cL - yi.T[this.l].e / 2, this.cM - yi.T[this.l].f, 0);
                if (this.T != null) {
                    n2 = 0;
                    while (n2 < this.T.size()) {
                        ((acc)this.T.elementAt(n2)).b(graphics, this.cL, this.cM);
                        ++n2;
                    }
                }
            }
            Graphics graphics2 = graphics;
            xm xm2 = this;
            int n3 = xm2.ak.size();
            int n4 = 0;
            while (n4 < n3) {
                aas aas2 = (aas)xm2.ak.elementAt(n4);
                aas2.a(graphics2);
                ++n4;
            }
        }
        super.b(graphics);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        try {
            if (yi.T[this.l] != null && yi.T[this.l].a != null) {
                graphics.drawRegion(yi.T[this.l].a, 0, 0 * yi.T[this.l].f, (int)yi.T[this.l].e, 40, 0, n2 - yi.T[this.l].g, 0, 0);
                graphics.drawRegion(yi.A, this.q[this.P] << 4, 0, 16, 16, 0, n2 - 15, 23, 20);
                return;
            }
        }
        catch (Exception exception) {}
    }

    public final void b() {
        this.b_();
        xm xm2 = this;
        int n2 = 0;
        while (n2 < xm2.ak.size()) {
            aas aas2 = (aas)xm2.ak.elementAt(n2);
            aas2.a();
            if (aas2.c) {
                xm2.ak.removeElement(aas2);
                if (xm2.ak.size() == 0) {
                    xm2.cF = true;
                }
            }
            ++n2;
        }
        switch (this.cW) {
            case 4: {
                this.O = 0;
                break;
            }
            case 0: {
                if (this.an == 0) {
                    this.O = 0;
                    this.ap.removeAllElements();
                    break;
                }
                if (this.an == 1) {
                    this.O = 0;
                    break;
                }
                this.O = 0;
                break;
            }
            case 2: {
                this.O = 0;
                break;
            }
            case 3: {
                if (this.an == 0) {
                    this.i = (short)(this.i + 1);
                    if (this.i == this.al.length - 5) {
                        abm.a(this.cL - 3, this.cM - 30, 24);
                        int n3 = 0;
                        while (n3 < 10) {
                            rq rq2 = new rq(this.cL - 5, this.cM - 70, this, this.ap);
                            abm.b.addElement(rq2);
                            ++n3;
                        }
                    }
                    if (this.i >= this.al.length - 3 && this.i <= this.al.length - 1) {
                        abm.a(this.cL - 5, this.cM - 65, 3);
                    }
                    if (this.i == this.al.length - 1 && this.ap.size() > 0) {
                        int n4 = 0;
                        while (n4 < this.ap.size()) {
                            ap ap2 = (ap)this.ap.elementAt(n4);
                            acv.s.a(this, ap2, 0, -65, 0, ap2.J, 2);
                            ++n4;
                        }
                    }
                    if (this.i > this.al.length - 1) {
                        this.i = 0;
                        this.cW = 0;
                    }
                    this.O = this.al[this.i];
                    break;
                }
                this.O = 0;
                break;
            }
            case 5: {
                this.O = 0;
                xm2 = this;
                if (xm2.ao >= 15) break;
                xm2.ao = (byte)(xm2.ao + 1);
                Random random = new Random(System.currentTimeMillis());
                xm2.ak.addElement(new aas(xm2.cL - 12 + abj.c(random.nextInt() % 25), xm2.cM - 30 + abj.c(random.nextInt() % 35), 0));
            }
        }
        this.d_();
    }

    public final void a_() {
    }

    public final void a(vh vh2) {
        super.a(vh2);
    }

    public final void a(int n2, int n3) {
        this.cW = (byte)5;
    }

    public final void a(Vector vector, byte by2) {
        this.cW = (byte)3;
        this.ap = vector;
        this.k = 0;
        this.j = 0;
        this.i = 0;
    }

    public final void a(by by2) {
        super.a(by2);
    }

    public final boolean S() {
        return true;
    }
}

