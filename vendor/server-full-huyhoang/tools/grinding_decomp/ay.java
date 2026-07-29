/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public final class ay
extends mo {
    private Vector f = new Vector();

    public ay(int n2) {
    }

    public final void a(Vector vector) {
        this.f = vector;
    }

    public final void b(Vector vector) {
        this.f = vector;
    }

    public final void a(hw hw2) {
        vh vh2;
        int n2;
        super.a(hw2);
        if (hw2.cW == 0) {
            return;
        }
        mo.b(hw2);
        if (hw2.av == 10) {
            if (this.f.size() > 0) {
                n2 = 0;
                while (n2 < this.f.size()) {
                    vh2 = (vh)this.f.elementAt(n2);
                    abj.b(0, hw2, (ap)vh2, hw2.cL, hw2.cM - 15, hw2.aM, hw2.bB, 1);
                    ++n2;
                }
            } else {
                abj.b(0, hw2, hw2.ap, hw2.cL, hw2.cM - 15, hw2.aM, hw2.bB, 1);
            }
        }
        if (hw2.av >= 13) {
            if (this.f.size() > 0) {
                n2 = 0;
                while (n2 < this.f.size()) {
                    vh2 = (vh)this.f.elementAt(n2);
                    ay.a(hw2, vh2);
                    ++n2;
                }
            } else {
                ay.a(hw2, hw2.ap);
            }
        }
        hw2.av = (short)(hw2.av + 1);
    }

    private static void a(hw hw2, vh vh2) {
        short s2 = vh2.cL;
        int n2 = vh2.cM - 20;
        int n3 = yi.m(3) + 1;
        int n4 = 0;
        while (n4 < n3) {
            int n5 = yi.m(50) + 50;
            int n6 = n5 * yg.b(n4 * 30) >> 10;
            n5 = -(n5 * yg.a(n4 * 30)) >> 10;
            abj.b(0, hw2, (ap)vh2, s2 + n6, n2 + n5, hw2.aM, hw2.bB, yi.m(2));
            ++n4;
        }
    }

    public final void a(bb bb2) {
    }
}

