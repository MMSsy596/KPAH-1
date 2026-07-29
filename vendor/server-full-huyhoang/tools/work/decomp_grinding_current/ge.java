/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public final class ge
extends mo {
    private Vector f = new Vector();

    public final void a(Vector vector) {
        this.f = vector;
    }

    public final void a(hw hw2) {
        super.a(hw2);
        if (hw2.cW == 0) {
            return;
        }
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
        if (hw2.av == 9) {
            if (this.f.size() > 0) {
                int n2 = 0;
                while (n2 < this.f.size()) {
                    Object object = (vh)this.f.elementAt(n2);
                    object = new ki(((vh)object).cL, ((vh)object).cM);
                    abm.b.addElement(object);
                    ++n2;
                }
            } else {
                ki ki2 = new ki(hw2.ap.cL, hw2.ap.cM);
                abm.b.addElement(ki2);
            }
        }
        hw2.av = (short)(hw2.av + 1);
    }

    public final void a(bb bb2) {
    }
}

