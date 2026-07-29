/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class dv
extends bb {
    private Vector ak = new Vector();
    private Vector al = new Vector();
    private byte[][] am = new byte[][]{{10, 10, 10, 10, 11, 11, 11, 11}, {19, 19, 19, 19, 20, 20, 20, 20}, {1, 1, 1, 1, 2, 2, 2, 2}, {1, 1, 1, 1, 2, 2, 2, 2}};
    private byte[][][] an = new byte[][][]{new byte[][]{{12, 12, 12, 12, 12, 12, 12, 12, 15, 15, 16, 16, 16, 16, 16, 16}, {21, 21, 21, 21, 21, 21, 21, 21, 24, 24, 25, 25, 25, 25, 25, 25}, {3, 3, 3, 3, 3, 3, 3, 3, 6, 6, 7, 7, 7, 7, 7, 7}, {3, 3, 3, 3, 3, 3, 3, 3, 6, 6, 7, 7, 7, 7, 7, 7}}, new byte[][]{{12, 12, 12, 12, 12, 12, 12, 12, 13, 13, 14, 14, 14, 14, 14, 14}, {21, 21, 21, 21, 21, 21, 21, 21, 22, 22, 23, 23, 23, 23, 23, 23}, {3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 5, 5, 5, 5, 5, 5}, {3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 5, 5, 5, 5, 5, 5}}};
    private byte[][] ao = new byte[][]{{17, 17, 17}, {26, 26, 26}, {8, 8, 8}, {8, 8, 8}};
    private byte[][] ap;
    private byte aq;
    private byte ar;

    public dv() {
        byte[][] byArrayArray = new byte[4][];
        byArrayArray[0] = new byte[]{9, 9, 9, 9, 9, 10, 10, 10, 10, 10};
        byArrayArray[1] = new byte[]{18, 18, 18, 18, 18, 19, 19, 19, 19, 19};
        byte[] byArray = new byte[10];
        byArray[5] = 1;
        byArray[6] = 1;
        byArray[7] = 1;
        byArray[8] = 1;
        byArray[9] = 1;
        byArrayArray[2] = byArray;
        byte[] byArray2 = new byte[10];
        byArray2[5] = 1;
        byArray2[6] = 1;
        byArray2[7] = 1;
        byArray2[8] = 1;
        byArray2[9] = 1;
        byArrayArray[3] = byArray2;
        this.ap = byArrayArray;
        this.aq = 0;
        this.g = this.cL;
        this.h = this.cM;
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
                graphics.drawRegion(yi.T[this.l].a, 0, this.O * yi.T[this.l].f, (int)yi.T[this.l].e, (int)yi.T[this.l].f, this.D == 3 ? 2 : 0, (int)this.cL, this.cM + 10, 33);
                if (this.T != null) {
                    n2 = 0;
                    while (n2 < this.T.size()) {
                        ((acc)this.T.elementAt(n2)).b(graphics, this.cL, this.cM);
                        ++n2;
                    }
                }
            }
            Graphics graphics2 = graphics;
            dv dv2 = this;
            int n3 = dv2.ak.size();
            int n4 = 0;
            while (n4 < n3) {
                aas aas2 = (aas)dv2.ak.elementAt(n4);
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
                graphics.drawRegion(yi.T[this.l].a, 0, 0 * yi.T[this.l].f, (int)yi.T[this.l].e, 40, 0, n2 - yi.T[this.l].g, -10, 0);
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
        dv dv2 = this;
        int n2 = 0;
        while (n2 < dv2.ak.size()) {
            aas aas2 = (aas)dv2.ak.elementAt(n2);
            if (aas2 != null) {
                aas2.a();
                if (aas2.c) {
                    dv2.ak.removeElement(aas2);
                    if (dv2.ak.size() == 0) {
                        dv2.cF = true;
                    }
                }
            }
            ++n2;
        }
        this.b_();
        switch (this.cW) {
            case 4: {
                this.i = (short)(this.i + 1);
                if (this.i > this.ao[this.D].length - 1) {
                    this.i = 0;
                }
                this.O = this.ao[this.D][this.i];
                break;
            }
            case 0: {
                this.i = (short)(this.i + 1);
                if (this.i > this.ap[this.D].length - 1) {
                    this.i = 0;
                }
                this.O = this.ap[this.D][this.i];
                break;
            }
            case 3: {
                this.i = (short)(this.i + 1);
                if (this.i == this.an[this.ar == 2 ? (byte)0 : this.ar][this.D].length - 7) {
                    int n3 = 0;
                    while (n3 < this.al.size()) {
                        ap ap2 = (ap)this.al.elementAt(n3);
                        acv.s.b(this, ap2, (this.ar == 2 ? (byte)0 : this.ar) + 3);
                        ++n3;
                    }
                }
                if (this.i > this.an[this.ar == 2 ? (byte)0 : this.ar][this.D].length - 1) {
                    this.i = 0;
                    this.cW = (byte)2;
                    break;
                }
                this.O = this.an[this.ar == 2 ? (byte)0 : this.ar][this.D][this.i];
                break;
            }
            case 5: {
                this.O = 0;
                dv2 = this;
                if (dv2.aq >= 15) break;
                dv2.aq = (byte)(dv2.aq + 1);
                Random random = new Random(System.currentTimeMillis());
                dv2.ak.addElement(new aas(dv2.cL - 12 + abj.c(random.nextInt() % 25), dv2.cM - 30 + abj.c(random.nextInt() % 35), 0));
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
        this.ar = by2;
    }

    public final void a(by by2) {
        super.a(by2);
    }
}

