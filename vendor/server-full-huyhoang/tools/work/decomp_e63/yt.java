/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class yt
extends bb {
    private Vector ak = new Vector();
    private Vector al = new Vector();
    private byte[][] am = new byte[][]{{1, 1, 1, 1, 2, 2, 2, 2}, {5, 5, 5, 5, 6, 6, 6, 6}, {1, 1, 1, 1, 2, 2, 2, 2}, {1, 1, 1, 1, 2, 2, 2, 2}};
    private byte[][] an = new byte[][]{{3, 3, 3, 3, 3, 3}, {7, 7, 7, 7, 7, 7}, {3, 3, 3, 3, 3, 3}, {3, 3, 3, 3, 3, 3}};
    private byte[][] ao;
    private byte ap;
    private byte aq;

    public yt() {
        byte[][] byArrayArray = new byte[5][];
        byte[] byArray = new byte[10];
        byArray[5] = 1;
        byArray[6] = 1;
        byArray[7] = 1;
        byArray[8] = 1;
        byArray[9] = 1;
        byArrayArray[0] = byArray;
        byArrayArray[1] = new byte[]{5, 5, 5, 5, 5, 6, 6, 6, 6, 6};
        byte[] byArray2 = new byte[10];
        byArray2[5] = 1;
        byArray2[6] = 1;
        byArray2[7] = 1;
        byArray2[8] = 1;
        byArray2[9] = 1;
        byArrayArray[2] = byArray2;
        byte[] byArray3 = new byte[10];
        byArray3[5] = 1;
        byArray3[6] = 1;
        byArray3[7] = 1;
        byArray3[8] = 1;
        byArray3[9] = 1;
        byArrayArray[3] = byArray3;
        byte[] byArray4 = new byte[10];
        byArray4[5] = 1;
        byArray4[6] = 1;
        byArray4[7] = 1;
        byArray4[8] = 1;
        byArray4[9] = 1;
        byArrayArray[4] = byArray4;
        this.ao = byArrayArray;
        this.ap = 0;
    }

    public final void a(Graphics graphics) {
        if (!this.S) {
            if (yi.T[this.l] != null && yi.T[this.l].a != null) {
                int n2;
                graphics.drawImage(yi.j, (int)this.cL, this.cM - 5, 3);
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
            yt yt2 = this;
            int n3 = yt2.ak.size();
            int n4 = 0;
            while (n4 < n3) {
                aas aas2 = (aas)yt2.ak.elementAt(n4);
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
            if (yi.T[((bb)((Object)exception2)).l] != null && yi.T[((bb)((Object)exception2)).l].a != null) {
                graphics.drawRegion(yi.T[((bb)((Object)exception2)).l].a, 0, 0, (int)yi.T[((bb)((Object)exception2)).l].e, yi.T[((bb)((Object)exception2)).l].f / 2, 0, n2 - yi.T[((bb)((Object)exception2)).l].g, 0, 0);
                graphics.drawRegion(yi.A, ((bb)((Object)exception2)).q[((ap)((Object)exception2)).P] << 4, 0, 16, 16, 0, n2 - 15, 23, 20);
                return;
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
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

    public final void b() {
        yt yt2 = this;
        int n2 = 0;
        while (n2 < yt2.ak.size()) {
            aas aas2 = (aas)yt2.ak.elementAt(n2);
            if (aas2 != null) {
                aas2.a();
                if (aas2.c) {
                    yt2.ak.removeElement(aas2);
                    if (yt2.ak.size() == 0) {
                        yt2.cF = true;
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
                this.i = (short)(this.i + 1);
                if (this.i > this.ao[this.D].length - 1) {
                    this.i = 0;
                }
                this.O = this.ao[this.D][this.i];
                break;
            }
            case 3: {
                this.i = (short)(this.i + 1);
                if (this.i % 3 == 0) {
                    abm.b(this.cL - 20, this.cM - 14, 30);
                    abm.b(this.cL + 20, this.cM - 14, 30);
                    abm.b(this.cL - 20, this.cM - 4, 30);
                    abm.b(this.cL + 20, this.cM - 4, 30);
                    abm.b(this.cL, this.cM + 4, 30);
                }
                if (this.i > this.an[this.D].length - 1) {
                    this.i = 0;
                    if (this.aq == 0) {
                        int n3 = 0;
                        while (n3 < this.al.size()) {
                            ap ap2 = (ap)this.al.elementAt(n3);
                            acv.s.a(this, ap2, 4, 52);
                            ++n3;
                        }
                    } else if (this.aq != 1) {
                        int n4 = 0;
                        while (n4 < this.al.size()) {
                            ap ap3 = (ap)this.al.elementAt(n4);
                            if (ap3 != null) {
                                int n5 = ap3.J;
                                ap3.l();
                                acv.s.a("-" + n5, 0, (int)ap3.cL, ap3.cM + ap3.H - 15, 1, -2);
                                n5 = 0;
                                while (n5 < 15) {
                                    abm.a(ap3.cL - 12 + abj.c(ap.W.nextInt() % 25), ap3.cM - 30 + abj.c(ap.W.nextInt() % 35), 33);
                                    ++n5;
                                }
                            }
                            ++n4;
                        }
                    }
                    this.cW = (byte)2;
                }
                this.O = this.an[this.D][this.i];
                break;
            }
            case 5: {
                this.O = (byte)3;
                yt2 = this;
                if (yt2.ap >= 15) break;
                yt2.ap = (byte)(yt2.ap + 1);
                Random random = new Random(System.currentTimeMillis());
                yt2.ak.addElement(new aas(yt2.cL - 12 + abj.c(random.nextInt() % 25), yt2.cM - 30 + abj.c(random.nextInt() % 35), 0));
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
        this.aq = by2;
    }

    public final void a(by by2) {
        super.a(by2);
    }
}

