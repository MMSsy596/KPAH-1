/*
 * Decompiled with CFR 0.152.
 */
final class lp
implements gj {
    private mw a;

    lp(mw mw2) {
        this.a = mw2;
    }

    public final void a() {
        mw mw2 = this.a;
        go.a().b(((hw)mw2.a.c.elementAt((int)na.e)).an);
        mw2 = this.a;
        mw2.a.c.removeElementAt(na.e);
        mw2 = this.a;
        mw2.a.b.removeElementAt(na.e);
        if (--na.e < 0) {
            na.e = 0;
        }
        mw2 = this.a;
        na.c(mw2.a);
        mw mw3 = this.a;
        mw2 = mw3;
        mw mw4 = this.a;
        mw2 = mw4;
        mw2 = this.a;
        na.d = mw3.a.c.size() * na.d(mw4.a) - (na.e(mw2.a) - 32);
        if (na.d < 0) {
            na.d = 0;
        }
        acv.a("\u0110\u00e3 x\u00f3a b\u1ea1n");
    }
}

