/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class e
extends bb {
    private Vector ak = new Vector();
    private static int[] al;
    private byte am = 0;

    static {
        int[] nArray = new int[2];
        nArray[1] = 2;
        al = nArray;
    }

    public final void a(Graphics graphics) {
        int n2;
        int n3;
        if (this.x != 0) {
            this.O = (byte)(this.O + 1);
            if (this.O > 1) {
                this.O = 0;
            }
        }
        if (yi.T[this.l] != null) {
            n3 = bb.c[yi.T[this.l].b][this.D][this.O];
            graphics.drawImage(yi.j, (int)this.cL, (int)this.cM, 3);
            if (this.U != null) {
                n2 = 0;
                while (n2 < this.U.size()) {
                    ((acc)this.U.elementAt(n2)).b(graphics, this.cL, this.cM);
                    ++n2;
                }
            }
            graphics.drawRegion(yi.T[this.l].a, 0, n3 % 4 * yi.T[this.l].f, (int)yi.T[this.l].e, (int)yi.T[this.l].f, al[n3 / 4], this.cL - yi.T[this.l].g + this.B, this.cM - yi.T[this.l].h + this.x + this.C, 0);
            if (this.T != null) {
                n2 = 0;
                while (n2 < this.T.size()) {
                    ((acc)this.T.elementAt(n2)).b(graphics, this.cL, this.cM);
                    ++n2;
                }
            }
        }
        n3 = this.ak.size();
        n2 = 0;
        while (n2 < n3) {
            aas aas2 = (aas)this.ak.elementAt(n2);
            if (aas2 != null) {
                aas2.a(graphics);
            }
            ++n2;
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
        Object object = this;
        int n2 = 0;
        while (n2 < ((e)object).ak.size()) {
            aas aas2 = (aas)((e)object).ak.elementAt(n2);
            if (aas2 != null) {
                aas2.a();
                if (aas2.c) {
                    ((e)object).ak.removeElement(aas2);
                    if (((e)object).ak.size() == 0) {
                        ((vh)object).cF = true;
                    }
                }
            }
            ++n2;
        }
        this.b_();
        if (this.x < 0) {
            this.x += this.y;
            ++this.y;
            if (this.x > 0) {
                this.x = 0;
            }
        }
        this.B += this.z;
        this.C += this.A;
        if (this.z > 0) {
            --this.z;
        }
        if (this.z < 0) {
            ++this.z;
        }
        if (this.A > 0) {
            --this.A;
        }
        if (this.A < 0) {
            ++this.A;
        }
        if (this.z == 0 && this.A == 0) {
            this.B = this.B > 1 ? (this.B >>= 1) : 0;
            this.C = this.C > 1 ? (this.C >>= 1) : 0;
        }
        if (this.u > 0) {
            --this.u;
            if (this.u == 0) {
                if (this.t < 0) {
                    this.t = 0;
                }
                if (this.v > this.t || this.t == 0) {
                    this.v = this.t;
                }
                if (this.v == 0) {
                    this.cW = (byte)4;
                }
            }
        }
        switch (this.cW) {
            case 4: {
                return;
            }
            case 0: {
                this.O = 0;
                return;
            }
            case 3: {
                this.O = (byte)2;
                this.i = (short)(this.i + 1);
                if (this.i % 6 == 3) {
                    object = null;
                    if (this.ai != 0) {
                        acv.s.a("-" + this.ai, 0, (int)this.e.cL, this.e.cM + this.e.H - 15, 1, -2);
                    } else {
                        acv.s.a("MISS", 0, (int)this.e.cL, this.e.cM + this.e.H - 15, 1, -2);
                    }
                    object = bb.c[yi.T[this.l].b][this.D][this.O] < 4 ? new xp(this.e, this.cL - 20, this.cM - 30) : new xp(this.e, this.cL + 20, this.cM - 30);
                    abm.b.addElement(object);
                }
                if (this.i <= 10) break;
                this.i = 0;
                this.cW = (byte)2;
                return;
            }
            case 5: {
                this.O = (byte)3;
                this.i = (short)(this.i + 1);
                this.cL = (short)(this.cL + this.j);
                this.cM = (short)(this.cM + this.k);
                this.j = (short)(this.j >> 1);
                this.k = (short)(this.k >> 1);
                object = this;
                if (((e)object).am < 15) {
                    ((e)object).am = (byte)(((e)object).am + 1);
                    Random random = new Random(System.currentTimeMillis());
                    ((e)object).ak.addElement(new aas(((vh)object).cL - 12 + abj.c(random.nextInt() % 25), ((vh)object).cM - 30 + abj.c(random.nextInt() % 35), 0));
                }
                return;
            }
            case 2: {
                this.i = (short)(this.i + 1);
                if (this.i > 6) {
                    this.i = 0;
                }
                this.O = this.i > 2 ? (byte)1 : 0;
                this.v();
            }
        }
    }
}

