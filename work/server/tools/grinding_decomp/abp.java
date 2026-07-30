/*
 * Decompiled with CFR 0.152.
 */
import game.GameMidlet;

final class abp
implements gj {
    private final String a;
    private final String b;

    abp(abj abj2, String string, String string2) {
        this.a = string;
        this.b = string2;
    }

    public final void a() {
        GameMidlet.a(this.a, "sms://" + this.b, new abq(this), new abr(this));
    }
}

