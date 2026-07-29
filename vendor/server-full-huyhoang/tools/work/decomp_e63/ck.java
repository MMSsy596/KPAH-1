/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class ck {
    public String a;
    public String[] b;
    private int c;
    private int d;
    private long e = -1L;

    public ck(String string) {
        this.a = string;
        this.b = d.h.a(string, acv.m - 8);
        this.d = d.h.a(this.b[0]);
        this.c = acv.m + this.d / 2;
    }

    public final void a(Graphics graphics) {
        int n2 = 0;
        while (n2 < this.b.length) {
            d.h.a(graphics, this.b[n2], this.c, 1 + n2 * 13 + (acv.s.aH == null ? 0 : 18), 2);
            ++n2;
        }
    }

    public final void a() {
        if (this.c != acv.m / 2) {
            this.c = acv.m / 2 - this.c >> 1 == 0 ? acv.m / 2 : (this.c += acv.m / 2 - this.c >> 1);
        }
        if (this.c == acv.m / 2) {
            if (this.e == -1L) {
                this.e = System.currentTimeMillis() / 1000L + 6L;
            }
            if (this.e - System.currentTimeMillis() / 1000L <= 0L) {
                abj.aq.removeElement(this);
            }
        }
    }
}

