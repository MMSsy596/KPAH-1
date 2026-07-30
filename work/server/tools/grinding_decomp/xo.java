/*
 * Decompiled with CFR 0.152.
 */
import java.util.Random;
import java.util.Vector;

public final class xo
extends mo {
    private boolean f = false;
    private Vector g = new Vector();

    public xo(boolean bl2) {
        this.f = bl2;
    }

    public final void a(Vector vector) {
        this.g = vector;
    }

    public final void a(hw hw2) {
        hw2.O = (byte)4;
        mo.a(hw2, 0);
        if (hw2.av == 13) {
            int n2;
            Random random = new Random(System.currentTimeMillis());
            int n3 = n2 = random.nextInt() % 20;
            if (hw2.D == 0 || hw2.D == 1) {
                n3 = 0;
            } else {
                n2 = 0;
            }
            if (hw2.ap != null) {
                if (hw2.ap.cG == 1) {
                    if (hw2.bB == 0) {
                        abm.a(hw2.ap.cL + n2, hw2.ap.cM - 10 + n3, 11);
                        hw2.ap.a_();
                    } else if (hw2.bB == 2) {
                        abm.a(hw2.ap.cL + n2, hw2.ap.cM - 10 + n3, 12);
                        hw2.ap.l();
                    }
                }
                hw2.ap.u = 2;
            }
            if (!this.f) {
                int n4 = 0;
                while (n4 < this.g.size()) {
                    ap ap2 = (ap)this.g.elementAt(n4);
                    abm.b(ap2.cL - 10 + n2, ap2.cM - 25 + n3, 14);
                    abm.b(ap2.cL + 10 + n2, ap2.cM - 25 + n3, 14);
                    abm.b(ap2.cL + n2, ap2.cM - 25 - 10 + n3, 14);
                    if (hw2.aM != 0 && hw2.aM != 2000000) {
                        acv.s.a("-" + hw2.aM, 0, (int)ap2.cL, ap2.cM - 15, -1, -2);
                    }
                    if (hw2.bB != 0 && hw2.bB < zp.d.length) {
                        acv.s.a(zp.d[hw2.bB], 0, (int)ap2.cL, ap2.cM - 15, 2, -2);
                    }
                    ++n4;
                }
            } else {
                int n5 = 0;
                while (n5 < this.g.size()) {
                    ap ap3 = (ap)this.g.elementAt(n5);
                    kc kc2 = new kc(hw2.cL, hw2.cM + 37, ap3, 1, true);
                    if (hw2.aM != 0 && hw2.aM != 2000000) {
                        kc2.n[0] = "-" + hw2.aM;
                    }
                    if (hw2.bB != 0) {
                        kc2.n[1] = zp.d[hw2.bB];
                    }
                    abm.b.addElement(kc2);
                    ++n5;
                }
            }
        }
        hw2.av = (short)(hw2.av + 1);
    }

    public final void a(bb bb2) {
    }
}

