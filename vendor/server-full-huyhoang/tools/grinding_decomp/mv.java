/*
 * Decompiled with CFR 0.152.
 */
final class mv
implements gj {
    private na a;

    mv(na na2) {
        this.a = na2;
    }

    public final void a() {
        acv.h();
        if (na.b(this.a) == 1) {
            this.a.f = (byte)(this.a.f + 1);
            go.a().b((int)((hw)this.a.c.elementAt((int)na.e)).cI, this.a.f);
            return;
        }
        this.a.f = (byte)(this.a.f + 1);
        go.a().l(na.b(this.a), this.a.f);
    }
}

