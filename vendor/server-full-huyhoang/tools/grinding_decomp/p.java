/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class p {
    private long b;
    private int c;
    public boolean a;
    private String d = "";
    private int e;
    private int f;

    public p(long l2, String string) {
        this.d = string;
        this.b = l2;
        this.c = 60;
        this.e = (int)(this.b - System.currentTimeMillis());
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4 = 0;
        long l2 = this.b - System.currentTimeMillis();
        n4 = (int)(60L - l2 * (long)this.c / (long)this.e);
        graphics.drawRegion(abj.aj[1], 0, 9, 62, 9, 0, n2, n3, 0);
        graphics.drawRegion(abj.aj[0], 0, 7, n4, 7, 0, n2, n3 + 1, 0);
        d.e.a(graphics, this.d, n2 + this.c / 2 + 1, n3 - 10 + 1 - 3, 2);
        d.h.a(graphics, this.d, n2 + this.c / 2, n3 - 10 - 3, 2);
        d.h.a(graphics, String.valueOf(this.f) + "%", n2 + this.c / 2, n3, 2);
    }

    public final void a() {
        long l2 = this.b - System.currentTimeMillis();
        this.f = (int)(100L - l2 * 100L / (long)this.e);
        if (this.b - System.currentTimeMillis() <= 0L) {
            this.a = true;
        }
    }
}

