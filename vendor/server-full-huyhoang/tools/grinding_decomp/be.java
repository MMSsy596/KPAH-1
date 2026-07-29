/*
 * Decompiled with CFR 0.152.
 */
public final class be
extends mo {
    private int f;
    private int g = 0;
    private byte h = 1;
    private int i;

    public be(int n2) {
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
        if (hw2.av == 20) {
            ((be)((Object)ew2)).i = 0;
        }
        ((mo)((Object)ew2)).a(hw2, true, ((be)((Object)ew2)).h, ((be)((Object)ew2)).g);
        if (hw2.av == 13) {
            if (hw2.ap != null && ((be)((Object)ew2)).g == 0) {
                ++((be)((Object)ew2)).i;
                if (((be)((Object)ew2)).i < ((be)((Object)ew2)).h) {
                    hw2.av = (short)10;
                }
            }
            if (((be)((Object)ew2)).g == 1) {
                ew ew2 = new ew((int)hw2.ap.cL, hw2.ap.cM - 200, hw2.ap);
                abm.b.addElement(ew2);
            }
        }
        hw2.av = (short)(hw2.av + 1);
    }
}

