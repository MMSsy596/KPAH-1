/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

final class om
implements gj {
    final abj a;

    om(abj abj2) {
        this.a = abj2;
    }

    public final void a() {
        aaq aaq2 = yi.c();
        if (aaq2 != null) {
            Vector<s> vector = new Vector<s>();
            int n2 = 0;
            while (n2 < aaq2.a.length) {
                int n3 = n2++;
                int n4 = aaq2.a[n3];
                s s2 = new s(yi.ae[aaq2.a[n3]], new oo(this, aaq2, n3, n4));
                vector.addElement(s2);
            }
            acv.u.a(vector, 3);
        }
    }
}

