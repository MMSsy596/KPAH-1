/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

final class jo
implements gj {
    private kj a;

    jo(kj kj2) {
        this.a = kj2;
    }

    public final void a() {
        if (this.a.d < 0) {
            return;
        }
        ql ql2 = (ql)((Vector)kj.a.elementAt(kj.a(this.a))).elementAt(this.a.d);
        this.a.a(0, ql2);
    }
}

