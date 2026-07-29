/*
 * Decompiled with CFR 0.152.
 */
public final class gg
extends mo {
    public final void a(bb bb2) {
    }

    public final void a(hw hw2) {
        super.a(hw2);
        if (hw2.cW == 0) {
            return;
        }
        mo.e(hw2);
        if (hw2.av == 3 && hw2.ap != null) {
            if (hw2.ap.cG == 1) {
                if (hw2.bB == 0) {
                    abm.a(hw2.ap.cL, hw2.ap.cM - 10, 11);
                    ((bb)hw2.ap).a_();
                } else if (hw2.bB == 2) {
                    abm.a(hw2.ap.cL, hw2.ap.cM - 10, 12);
                    ((bb)hw2.ap).l();
                }
            }
            hw2.ap.u = 2;
            if (hw2.aM != 0 && hw2.aM != 2000000) {
                acv.s.a("-" + hw2.aM, 0, (int)hw2.ap.cL, hw2.ap.cM - 15, -1, -2);
            }
            if (hw2.bB != 0 && hw2.bB < zp.d.length) {
                acv.s.a(zp.d[hw2.bB], 0, (int)hw2.ap.cL, hw2.ap.cM - 15, 2, -2);
            }
        }
        hw2.av = (short)(hw2.av + 1);
    }
}

