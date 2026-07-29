/*
 * Decompiled with CFR 0.152.
 */
final class kn
implements Runnable {
    private gq a;

    kn(gq gq2) {
        this.a = gq2;
    }

    public final void run() {
        try {
            Thread.sleep(20000L);
        }
        catch (InterruptedException interruptedException) {}
        Object object = this.a;
        if (((gq)object).a.d) {
            try {
                object = this.a;
                aco.a(((gq)object).a).close();
            }
            catch (Exception exception) {}
            aco.j = true;
            object = this.a;
            this.a.a.d = false;
            object = this.a;
            this.a.a.c = false;
            object = this.a;
            object = ((gq)object).a.b;
            acv.s.m();
        }
    }
}

