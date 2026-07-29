/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

final class ak
implements gj {
    private gv a;

    ak(gv gv2) {
        this.a = gv2;
    }

    public final void a() {
        Vector<s> vector = new Vector<s>();
        if (this.a.f.size() > 0) {
            xl xl2 = (xl)this.a.f.elementAt(gv.b(this.a));
            if (xl2.d == 0) {
                vector.addElement(this.a.h);
            } else if (xl2.d == 2 || xl2.d == 3) {
                vector.addElement(this.a.i);
            }
            acv.u.a(vector, 0);
        }
    }
}

