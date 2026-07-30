/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public final class fa
extends mo {
    private boolean f = false;
    private byte g;
    private Vector h = new Vector();

    public fa(byte by2, boolean bl2, byte by3) {
        this.f = bl2;
        this.g = by3;
    }

    public final void a(Vector vector) {
        this.h = vector;
    }

    public final void b(Vector vector) {
        this.h = vector;
    }

    public final void a(hw hw2) {
        Object object;
        super.a(hw2);
        if (hw2.cW == 0) {
            return;
        }
        mo.b(hw2);
        if (hw2.av == 10) {
            int n2 = 0;
            while (n2 < this.h.size()) {
                object = (ap)this.h.elementAt(n2);
                abj.b(0, hw2, (ap)object, hw2.cL, hw2.cM - 15, hw2.aM, hw2.bB, this.g);
                ++n2;
            }
        }
        if (this.f && hw2.av == 14) {
            if (this.h.size() > 0) {
                xr xr2 = new xr(this.h, hw2);
                abm.b.addElement(xr2);
            } else {
                Vector<ap> vector = new Vector<ap>();
                vector.addElement(hw2.ap);
                object = new xr(vector, hw2);
                abm.b.addElement(object);
            }
        }
        hw2.av = (short)(hw2.av + 1);
    }

    public final void a(bb bb2) {
        if (this.h != null && this.h.size() > 0) {
            int n2 = 0;
            while (n2 < this.h.size()) {
                ap ap2 = (ap)this.h.elementAt(n2);
                abj.b(0, bb2, ap2, bb2.cL, bb2.cM - 15, 0, (byte)0, this.g);
                ++n2;
            }
        } else {
            abj.b(0, bb2, bb2.e, bb2.cL, bb2.cM - 15, 0, (byte)0, this.g);
        }
        bb2.i = (short)(bb2.i + 1);
    }
}

