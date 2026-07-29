/*
 * Decompiled with CFR 0.152.
 */
public final class tm
extends mo {
    public final void a(bb bb2) {
    }

    public final void a(hw hw2) {
        super.a(hw2);
        if (hw2.cW == 0) {
            return;
        }
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
            abj.a(0, hw2, hw2.ap, hw2.cL + mo.d[hw2.D], hw2.cM + mo.e[hw2.D], hw2.aM, hw2.bB);
        }
        hw2.av = (short)(hw2.av + 1);
    }
}

