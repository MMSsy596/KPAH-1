/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

final class jn
implements gj {
    final kj a;

    jn(kj kj2) {
        this.a = kj2;
    }

    public final void a() {
        this.a.g = false;
        Vector<s> vector = new Vector<s>();
        vector.addElement(new s("Mua", new jq(this)));
        vector.addElement(new s("Bid", new jp(this)));
        acv.u.a(vector, 0);
    }
}

