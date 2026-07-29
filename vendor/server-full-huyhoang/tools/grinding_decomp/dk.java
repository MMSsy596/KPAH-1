/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class dk
extends bb {
    private Vector ak = new Vector();
    private Vector al = new Vector();
    private byte[][] am;
    private byte[][] an;
    private byte ao;
    private byte ap;

    public dk() {
        byte[][] byArrayArray = new byte[4][];
        byte[] byArray = new byte[6];
        byArray[3] = 1;
        byArray[4] = 1;
        byArray[5] = 1;
        byArrayArray[0] = byArray;
        byArrayArray[1] = new byte[]{4, 4, 4, 5, 5, 5};
        byte[] byArray2 = new byte[6];
        byArray2[3] = 1;
        byArray2[4] = 1;
        byArray2[5] = 1;
        byArrayArray[2] = byArray2;
        byte[] byArray3 = new byte[6];
        byArray3[3] = 1;
        byArray3[4] = 1;
        byArray3[5] = 1;
        byArrayArray[3] = byArray3;
        this.am = byArrayArray;
        this.an = new byte[][]{{2, 2, 2, 2, 2, 2}, {6, 6, 6, 6, 6, 6}, {2, 2, 2, 2, 2, 2}, {2, 2, 2, 2, 2, 2}};
        this.ao = 0;
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
                graphics.drawRegion(yi.T[this.l].a, 0, this.O * yi.T[this.l].f, (int)yi.T[this.l].e, (int)yi.T[this.l].f, this.D == 3 ? 2 : 0, (int)this.cL, (int)this.cM, 33);
                if (this.T != null) {
                    n2 = 0;
                    while (n2 < this.T.size()) {
                        ((acc)this.T.elementAt(n2)).b(graphics, this.cL, this.cM);
                        ++n2;
                    }
                }
            }
            Graphics graphics2 = graphics;
            dk dk2 = this;
            int n3 = dk2.ak.size();
            int n4 = 0;
            while (n4 < n3) {
                aas aas2 = (aas)dk2.ak.elementAt(n4);
                if (aas2 != null) {
                    aas2.a(graphics2);
                }
                ++n4;
            }
        }
        super.b(graphics);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        try {
            if (yi.T[this.l] != null && yi.T[this.l].a != null) {
                graphics.drawRegion(yi.T[this.l].a, 0, this.O * yi.T[this.l].f, (int)yi.T[this.l].e, (int)yi.T[this.l].f, 0, n2 - yi.T[this.l].g, 0, 0);
                graphics.drawRegion(yi.A, this.q[this.P] << 4, 0, 16, 16, 0, n2 - 15, 23, 20);
                return;
            }
        }
        catch (Exception exception) {}
    }

    public final void a_() {
    }

    public final void a(vh vh2) {
        super.a(vh2);
    }

    public final void a(int n2, int n3) {
        this.cW = (byte)5;
    }

    public final void b() {
        dk dk2 = this;
        int n2 = 0;
        while (n2 < dk2.ak.size()) {
            aas aas2 = (aas)dk2.ak.elementAt(n2);
            if (aas2 != null) {
                aas2.a();
                if (aas2.c) {
                    dk2.ak.removeElement(aas2);
                    if (dk2.ak.size() == 0) {
                        dk2.cF = true;
                    }
                }
            }
            ++n2;
        }
        this.b_();
        switch (this.cW) {
            case 4: {
                if (this.D != 1) {
                    this.O = (byte)3;
                    break;
                }
                this.O = (byte)7;
                break;
            }
            case 0: {
                if (this.D != 1) {
                    this.O = 0;
                    break;
                }
                this.O = (byte)4;
                break;
            }
            case 3: {
                this.i = (short)(this.i + 1);
                if (this.i % 3 == 0) {
                    abm.b(this.cL - 20, this.cM - 14, 4);
                    abm.b(this.cL + 20, this.cM - 14, 4);
                    abm.b(this.cL - 20, this.cM - 4, 4);
                    abm.b(this.cL + 20, this.cM - 4, 4);
                    abm.b(this.cL, this.cM + 4, 4);
                }
                if (this.ap == 0 && this.i == 1) {
                    int n3 = 0;
                    n2 = 0;
                    n3 = this.D == 3 ? 10 : -10;
                    if (this.D != 1) {
                        abm.a(this.cL + n3, this.cM + -22, 37);
                    }
                }
                if (this.i > this.an[this.D].length - 1) {
                    if (this.ap == 0) {
                        int n4 = 0;
                        while (n4 < this.al.size()) {
                            ap ap2 = (ap)this.al.elementAt(n4);
                            int n5 = 0;
                            n5 = 0;
                            n5 = this.D == 3 ? 10 : -10;
                            abm.a(this.cL + n5, this.cM + -22, 11);
                            acv.s.a(this, ap2, n5, -22, (int)this.D, ap2.J, 0);
                            ++n4;
                        }
                    } else if (this.ap == 1) {
                        int n6 = 0;
                        while (n6 < this.al.size()) {
                            ap ap3 = (ap)this.al.elementAt(n6);
                            abj.a(this, ap3, 30);
                            ++n6;
                        }
                    } else {
                        int n7 = 0;
                        while (n7 < this.al.size()) {
                            ap ap4 = (ap)this.al.elementAt(n7);
                            if (ap4 != null) {
                                int n8 = ap4.J;
                                ap4.l();
                                acv.s.a("-" + n8, 0, (int)ap4.cL, ap4.cM + ap4.H - 15, 1, -2);
                                n8 = 0;
                                while (n8 < 10) {
                                    abm.a(ap4.cL - 12 + abj.c(ap.W.nextInt() % 25), ap4.cM - 30 + abj.c(ap.W.nextInt() % 35), 28);
                                    ++n8;
                                }
                            }
                            ++n7;
                        }
                    }
                    this.i = 0;
                    this.cW = (byte)2;
                }
                this.O = this.an[this.D][this.i];
                break;
            }
            case 5: {
                this.O = 0;
                dk2 = this;
                if (dk2.ao >= 15) break;
                dk2.ao = (byte)(dk2.ao + 1);
                Random random = new Random(System.currentTimeMillis());
                dk2.ak.addElement(new aas(dk2.cL - 12 + abj.c(random.nextInt() % 25), dk2.cM - 30 + abj.c(random.nextInt() % 35), 0));
                break;
            }
            case 2: {
                this.i = (short)(this.i + 1);
                if (this.i > this.am[this.D].length - 1) {
                    this.i = 0;
                }
                this.O = this.am[this.D][this.i];
                if (this.S) break;
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
        this.ap = by2;
    }

    public final void a(by by2) {
        super.a(by2);
    }
}

