/*
 * Decompiled with CFR 0.152.
 */
final class bn
implements gj {
    private bj a;

    bn(bj bj2) {
        this.a = bj2;
    }

    public final void a() {
        bj bj2 = this.a;
        bj bj3 = bj2;
        bj3 = this.a;
        act.b(bj2.a).removeElementAt(act.a(bj3.a));
        bj bj4 = this.a;
        bj3 = bj4;
        bj3 = this.a;
        if (act.a(bj4.a) >= act.b(bj3.a).size()) {
            bj bj5 = this.a;
            bj3 = bj5;
            bj3 = this.a;
            act.a(bj5.a, act.b(bj3.a).size() - 1);
        }
        bj3 = this.a;
        bj3.a.f();
        bj3 = this.a;
        act.c(bj3.a);
    }
}

