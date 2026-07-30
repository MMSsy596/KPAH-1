/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public final class bd
extends mo {
    private Vector f = new Vector();

    public bd(int n2) {
    }

    public final void a(Vector vector) {
        this.f = vector;
    }

    public final void a(hw hw2) {
        super.a(hw2);
        if (hw2.cW == 0) {
            return;
        }
        mo.a(hw2, 0);
        if (hw2.av == 13) {
            if (this.f.size() > 0) {
                int n2 = 0;
                while (n2 < this.f.size()) {
                    vh vh2 = (vh)this.f.elementAt(n2);
                    abm.a.addElement(new ya((ap)vh2, hw2.cL, hw2.cM, false));
                    ++n2;
                }
            } else {
                abm.a.addElement(new ya(hw2.ap, hw2.cL, hw2.cM, false));
            }
        }
        hw2.av = (short)(hw2.av + 1);
    }
}

