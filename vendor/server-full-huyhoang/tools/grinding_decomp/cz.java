/*
 * Decompiled with CFR 0.152.
 */
import game.GameMidlet;

final class cz
implements Runnable {
    private final String a;
    private final String b;

    cz(xw xw2, String string, String string2) {
        this.a = string;
        this.b = string2;
    }

    public final void run() {
        try {
            Thread.sleep(500L);
        }
        catch (InterruptedException interruptedException) {}
        acv.b();
        go.a().e();
        go.a().a(this.a, this.b, GameMidlet.b);
        abj.aA = false;
    }
}

