/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

final class in
implements gj {
    final kj a;
    private final Vector b;

    in(kj kj2, Vector vector) {
        this.a = kj2;
        this.b = vector;
    }

    public final void a() {
        this.a.g = false;
        Vector<s> vector = new Vector<s>();
        if (kj.h > 0L) {
            vector.addElement(new s("R\u00fat ti\u1ec1n", new ip(this)));
        }
        if (this.b.size() > 0 && this.a.d >= 0) {
            vector.addElement(new s("B\u1ecf v\u00e0o h\u00e0nh trang", new ib(this)));
        }
        acv.u.a(vector, 2);
    }
}

