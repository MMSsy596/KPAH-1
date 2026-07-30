/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public final class abl
extends mo {
    private boolean f;
    private Vector g = new Vector();

    public abl(boolean bl2) {
        this.f = bl2;
    }

    public final void a(Vector vector) {
        this.g = vector;
    }

    public final void a(hw hw2) {
        hw2.O = (byte)4;
        if (hw2.av == 18) {
            hw2.cW = 0;
            hw2.av = 0;
            hw2.ay = 0;
        } else if (hw2.av >= 6 && hw2.av < 18) {
            hw2.O = (byte)5;
            hw2.ay = (short)7;
        } else if (hw2.av == 5 || hw2.av == 4) {
            hw2.O = (byte)5;
            hw2.ay = (short)6;
        } else if (hw2.av == 3 || hw2.av == 2) {
            hw2.O = (byte)4;
            hw2.ay = (short)5;
        } else {
            hw2.O = (byte)4;
            hw2.ay = (short)4;
        }
        if (hw2.av == 3) {
            if (!this.f) {
                int n2 = 0;
                while (n2 < this.g.size()) {
                    ap ap2 = (ap)this.g.elementAt(n2);
                    abj.a(5, hw2, ap2, hw2.cL + mo.d[hw2.D], hw2.cM + mo.e[hw2.D], hw2.aM, hw2.bB);
                    ++n2;
                }
            } else {
                int n3 = 0;
                while (n3 < this.g.size()) {
                    Object object = (ap)this.g.elementAt(n3);
                    object = new dl(hw2.cL + mo.d[hw2.D], hw2.cM + mo.e[hw2.D], hw2, (ap)object, 4);
                    if (hw2.aM != 0 && hw2.aM != 2000000) {
                        ((dl)object).n[0] = "-" + hw2.aM;
                    }
                    if (hw2.bB != 0 && hw2.bB < zp.d.length) {
                        ((dl)object).n[1] = zp.d[hw2.bB];
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

