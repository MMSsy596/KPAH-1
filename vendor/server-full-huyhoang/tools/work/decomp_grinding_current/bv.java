/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public final class bv
extends mo {
    private Vector f;
    private boolean g;

    public bv(boolean bl2) {
        this.g = bl2;
    }

    public final void a(Vector vector) {
        this.f = vector;
    }

    public final void b(Vector vector) {
        this.f = vector;
    }

    public final void a(hw hw2) {
        hw2.O = (byte)4;
        if (hw2.av == 16) {
            hw2.cW = 0;
            hw2.av = 0;
            hw2.ay = 0;
        } else if (hw2.av >= 14 && hw2.av < 16) {
            hw2.O = (byte)5;
            hw2.ay = (short)7;
        } else if (hw2.av == 13 || hw2.av == 12) {
            hw2.O = (byte)5;
            hw2.ay = (short)6;
        } else if (hw2.av == 11 || hw2.av == 10) {
            hw2.O = (byte)4;
            hw2.ay = (short)5;
        } else {
            hw2.O = (byte)4;
            hw2.ay = (short)4;
        }
        if (hw2.av == 9 && this.f != null) {
            if (!this.g) {
                if (hw2.aM == 0) {
                    acv.s.a("MISS", 0, (int)hw2.ap.cL, hw2.ap.cM - 15, 1, -2);
                }
                int n2 = 0;
                while (n2 < this.f.size()) {
                    Object object = (ap)this.f.elementAt(n2);
                    object = new dl(hw2.cL + mo.d[hw2.D], hw2.cM + mo.e[hw2.D], hw2, (ap)object, 3);
                    if (hw2.aM != 0 && hw2.aM != 2000000) {
                        ((dl)object).n[0] = "-" + hw2.aM;
                    }
                    if (hw2.bB != 0 && hw2.bB < zp.d.length) {
                        ((dl)object).n[1] = zp.d[hw2.bB];
                    }
                    abm.b.addElement(object);
                    ++n2;
                }
            } else {
                int n3 = 0;
                while (n3 < this.f.size()) {
                    Object object = (ap)this.f.elementAt(n3);
                    object = new kc(hw2.cL, hw2.cM + 30, (ap)object, 0, true);
                    if (hw2.aM != 0 && hw2.aM != 2000000) {
                        ((kc)object).n[0] = "-" + hw2.aM;
                    }
                    if (hw2.bB != 0) {
                        ((kc)object).n[1] = zp.d[hw2.bB];
                    }
                    abm.b.addElement(object);
                    ++n3;
                }
            }
        }
        hw2.av = (short)(hw2.av + 1);
    }

    public final void a(bb bb2) {
    }
}

