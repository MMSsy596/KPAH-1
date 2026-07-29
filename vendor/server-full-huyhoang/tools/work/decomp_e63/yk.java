/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

final class yk
implements gj {
    private final Vector a;
    private final int b;
    private final Object c;

    yk(im im2, Vector vector, int n2, Object object) {
        this.a = vector;
        this.b = n2;
        this.c = object;
    }

    public final void a() {
        if (this.a.elementAt(this.b) instanceof ql) {
            acv.s.G.a(((ql)this.c).i, 0, 0);
            return;
        }
        acv.s.G.a(((ub)this.c).d, 2, 0);
    }
}

