/*
 * Decompiled with CFR 0.152.
 */
public final class bh
extends mo {
    public final void a(hw hw2) {
        super.a(hw2);
        if (hw2.cW == 0) {
            return;
        }
        if (hw2.av == 15) {
            hw2.cW = 0;
            hw2.av = 0;
            hw2.ay = 0;
        } else if (hw2.av == 12 || hw2.av == 11 || hw2.av == 13 || hw2.av == 14) {
            hw2.O = (byte)5;
            hw2.ay = (short)7;
        } else if (hw2.av == 10 || hw2.av == 9) {
            hw2.O = (byte)5;
            hw2.ay = (short)6;
        } else if (hw2.av == 8 || hw2.av == 7) {
            hw2.O = (byte)4;
            hw2.ay = (short)5;
        } else {
            hw2.O = (byte)4;
            hw2.ay = (short)4;
        }
        if (hw2.av == 8) {
            if (hw2.ap != null) {
                if (hw2.ap.cG == 1) {
                    if (hw2.bB == 0) {
                        abm.a(hw2.ap.cL, hw2.ap.cM - 10, 11);
                        ((bb)hw2.ap).a((vh)hw2);
                    } else if (hw2.bB == 2) {
                        abm.a(hw2.ap.cL, hw2.ap.cM - 10, 12);
                        ((bb)hw2.ap).l();
                    }
                }
                hw2.ap.u = 2;
            }
            if (hw2.bB != 1) {
                abm.b(hw2.ap.cL, hw2.ap.cM - 10, 13);
            }
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

