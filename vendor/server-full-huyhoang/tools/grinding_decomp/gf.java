/*
 * Decompiled with CFR 0.152.
 */
public final class gf
extends mo {
    public final void a(bb bb2) {
    }

    public final void a(hw hw2) {
        super.a(hw2);
        if (hw2.cW == 0) {
            return;
        }
        mo.e(hw2);
        if (hw2.av == 3) {
            if (hw2.ap != null) {
                if (hw2.ap.cG == 1) {
                    ((bb)hw2.ap).a_();
                }
                hw2.ap.u = 2;
            }
            abm.a(hw2.ap.cL, hw2.ap.cM - 10, 3);
            if (hw2.aM != 2000000) {
                acv.s.a("-" + hw2.aM, 0, (int)hw2.ap.cL, hw2.ap.cM - 15, -1, -2);
            }
        }
        hw2.av = (short)(hw2.av + 1);
    }
}

