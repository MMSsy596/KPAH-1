/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.io.IOException;
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class jw
extends bb {
    private Vector ak = new Vector();
    private Vector al = new Vector();
    private byte[][] am = new byte[][]{{11, 11, 11, 11, 12, 12, 12, 12}, {19, 19, 19, 19, 20, 20, 20, 20}, {3, 3, 3, 3, 4, 4, 4, 4}, {3, 3, 3, 3, 4, 4, 4, 4}};
    private byte[][][] an = new byte[][][]{new byte[][]{{13, 13, 13, 13, 13, 13, 13, 13, 14, 14, 15, 15, 15, 15, 15, 15}, {21, 21, 21, 21, 21, 21, 21, 21, 22, 22, 23, 23, 23, 23, 23, 23}, {5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 7, 7, 7, 7, 7, 7}, {5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 7, 7, 7, 7, 7, 7}}, new byte[][]{{13, 13, 13, 13, 13, 13, 13, 13, 14, 14, 15, 15, 15, 15, 15, 15}, {21, 21, 21, 21, 21, 21, 21, 21, 22, 22, 23, 23, 23, 23, 23, 23}, {5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 7, 7, 7, 7, 7, 7}, {5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 7, 7, 7, 7, 7, 7}}};
    private byte[][] ao = new byte[][]{{10, 10, 10}, {18, 18, 18}, {2, 2, 2}, {2, 2, 2}};
    private byte[][] ap;
    private byte aq;
    private byte ar;

    static {
        try {
            Image.createImage((String)"/m/bosshothanh.png");
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
        }
    }

    public jw() {
        byte[][] byArrayArray = new byte[4][];
        byArrayArray[0] = new byte[]{8, 8, 8, 8, 8, 9, 9, 9, 9, 9};
        byArrayArray[1] = new byte[]{16, 16, 16, 16, 16, 17, 17, 17, 17, 17};
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
        this.ar = 0;
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
                graphics.drawRegion(yi.T[this.l].a, 0, this.O * yi.T[this.l].f, (int)yi.T[this.l].e, (int)yi.T[this.l].f, this.D == 3 ? 2 : 0, (int)this.cL, this.cM - 20, 3);
                if (this.T != null) {
                    n2 = 0;
                    while (n2 < this.T.size()) {
                        ((acc)this.T.elementAt(n2)).b(graphics, this.cL, this.cM);
                        ++n2;
                    }
                }
            }
            Graphics graphics2 = graphics;
            jw jw2 = this;
            int n3 = jw2.ak.size();
            int n4 = 0;
            while (n4 < n3) {
                aas aas2 = (aas)jw2.ak.elementAt(n4);
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
                graphics.drawRegion(yi.T[this.l].a, 0, 8 * yi.T[this.l].f, (int)yi.T[this.l].e, 35, 0, n2 - yi.T[this.l].g, -5, 0);
                graphics.drawRegion(yi.A, this.q[this.P] << 4, 0, 16, 16, 0, n2 - 15, 23, 20);
                return;
            }
        }
        catch (Exception exception) {}
    }

    public final void b() {
        jw jw2 = this;
        int n2 = 0;
        while (n2 < jw2.ak.size()) {
            aas aas2 = (aas)jw2.ak.elementAt(n2);
            if (aas2 != null) {
                aas2.a();
                if (aas2.c) {
                    jw2.ak.removeElement(aas2);
                    if (jw2.ak.size() == 0) {
                        jw2.cF = true;
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
                    if (this.ar == 0) {
                        int n3 = 0;
                        while (n3 < this.al.size()) {
                            ap ap2 = (ap)this.al.elementAt(n3);
                            abj.b(7, this, ap2, this.cL - 20, this.cM - 50, ap2.J, (byte)2, 8);
                            abj.b(7, this, ap2, this.cL, this.cM - 30, ap2.J, (byte)2, 8);
                            abj.b(7, this, ap2, this.cL - 20, this.cM - 10, ap2.J, (byte)2, 8);
                            ap2.l();
                            ++n3;
                        }
                    } else if (this.ar == 1) {
                        acv.s.C = 20;
                        abm.a.addElement(new ya(this.e, this.cL, this.cM, true));
                        if (this.e.J != 0 && this.e.J != 2000000) {
                            acv.s.a("-" + this.e.J, 0, (int)this.e.cL, this.e.cM - 15, -1, -2);
                        }
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
                jw2 = this;
                if (jw2.aq >= 15) break;
                jw2.aq = (byte)(jw2.aq + 1);
                Random random = new Random(System.currentTimeMillis());
                jw2.ak.addElement(new aas(jw2.cL - 12 + abj.c(random.nextInt() % 25), jw2.cM - 30 + abj.c(random.nextInt() % 35), 0));
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

    public final void a_() {
    }

    public final void a(vh vh2) {
        super.a(vh2);
    }

    public final void a(int n2, int n3) {
        this.cW = (byte)5;
    }

    public final void a(Vector vector, byte by2) {
        this.al = vector;
        this.cW = (byte)3;
        this.k = 0;
        this.j = 0;
        this.i = 0;
        this.ar = by2;
        if (by2 == 1) {
            this.e = (ap)vector.elementAt(0);
        }
    }

    public final void a(by by2) {
        super.a(by2);
    }
}

