/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public final class mn
extends mo {
    private boolean f = false;
    private Vector g = new Vector();

    public mn(boolean bl2) {
    }

    public final void a(Vector vector) {
        this.g = vector;
    }

    public final void a(hw hw2) {
    }

    public final void a(bb bb2) {
        if (bb2.i == 3 && !this.f) {
            int n2 = 0;
            while (n2 < this.g.size()) {
                ap ap2 = (ap)this.g.elementAt(n2);
                abj.a(7, bb2, ap2, bb2.cL + mo.d[bb2.D], bb2.cM + mo.e[bb2.D], ap2.J, (byte)0);
                ++n2;
            }
        }
        bb2.i = (short)(bb2.i + 1);
    }
}

