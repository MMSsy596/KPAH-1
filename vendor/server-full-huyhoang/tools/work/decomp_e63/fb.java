/*
 * Decompiled with CFR 0.152.
 */
import game.GameMidlet;

final class fb
implements gj {
    private final String a;

    fb(bi bi2, String string) {
        this.a = string;
    }

    public final void a() {
        try {
            GameMidlet.a.platformRequest(this.a);
            Thread.sleep(500L);
        }
        catch (Exception exception) {}
        GameMidlet.a.notifyDestroyed();
    }
}

