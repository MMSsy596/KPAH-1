/*
 * Decompiled with CFR 0.152.
 */
public final class ax
extends mo {
    private int f;
    private int g = 0;
    private byte h = 1;
    private int i = 0;

    public ax(int n2) {
    }

    public final void a(int n2) {
        this.f = n2;
    }

    public final int a() {
        this.h = (byte)mo.a[this.f];
        return mo.a[this.f];
    }

    public final void a(hw hw2) {
        super.a(hw2);
        if (hw2.cW == 0) {
            return;
        }
        if (hw2.av == 15) {
            this.i = 0;
        }
        mo.b(hw2);
        if (hw2.av == 10) {
            if (this.g == 0) {
                abj.b(0, hw2, hw2.ap, hw2.cL, hw2.cM - 15, hw2.aM != 2000000 ? hw2.aM / this.h : hw2.aM, hw2.bB, 2);
                ++this.i;
                if (this.i < this.h) {
                    hw2.av = (short)7;
                }
            } else {
                int n2 = 0;
                n2 = hw2.D == 3 ? 10 : -10;
                acv.s.a(hw2, hw2.ap, n2, -22, (int)hw2.D, hw2.aM != 2000000 ? hw2.aM / this.h : hw2.aM, 1);
                abm.a(hw2.ap.cL, hw2.ap.cM - 15, 32);
                abm.a(hw2.ap.cL, hw2.ap.cM - 10, 9);
            }
        }
        hw2.av = (short)(hw2.av + 1);
    }

    public final void a(bb bb2) {
    }
}

