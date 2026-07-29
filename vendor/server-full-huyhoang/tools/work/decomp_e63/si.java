/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

final class si
implements gj {
    private im a;
    private final byte[][] b;

    si(im im2, byte[][] byArray) {
        this.a = im2;
        this.b = byArray;
    }

    public final void a() {
        int n2 = this.a.y.size();
        --n2;
        while (n2 >= 0) {
            gz gz2 = (gz)this.a.y.elementAt(n2);
            im.a(this.a, gz2);
            --n2;
        }
        go.a().a((short)((Vector)null).size(), (short)0, this.b);
        this.a.l.b.a();
        acv.h();
    }
}

