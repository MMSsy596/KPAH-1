/*
 * Decompiled with CFR 0.152.
 */
public final class gi
extends mo {
    public final void a(bb bb2) {
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
            if (hw2.av % 2 == 0) {
                abm.a(hw2.cL + mo.b[hw2.D], hw2.cM + mo.c[hw2.D], 3);
            }
            hw2.O = (byte)4;
            hw2.ay = (short)4;
        }
        if (hw2.av == 9) {
            abj.a(1, hw2, hw2.ap, hw2.cL + mo.b[hw2.D], hw2.cM + mo.c[hw2.D], hw2.aM, hw2.bB);
        }
        hw2.av = (short)(hw2.av + 1);
    }
}

