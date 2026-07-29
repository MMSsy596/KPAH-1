/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

final class mj
implements gj {
    private abj a;

    mj(abj abj2) {
        this.a = abj2;
    }

    public final void a() {
        this.a.t.db = null;
        this.a.u.db = null;
        if (!this.a.u.N() && this.a.u.g() != 7 && this.a.u.g() != 10 && this.a.u.g() != 22 && this.a.u.g() != 31 && this.a.u.g() != 21 && this.a.u.g() != 25) {
            abj.a(this.a.u, yi.a(this.a.u.g()), 500);
            return;
        }
        if (!this.a.u.N()) {
            abj.a(this.a, this.a.u.g(), new Vector());
        }
    }
}

