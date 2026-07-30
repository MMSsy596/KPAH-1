/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class ht {
    private long e;
    public String a;
    public boolean b;
    public short c;
    public short d;
    private byte f;

    public ht(short s2, short s3, long l2, String string, byte by2) {
        this.e = System.currentTimeMillis() + l2 * 1000L;
        this.a = string;
        this.c = s2;
        this.d = s3;
        this.f = by2;
        if (this.f == 0) {
            this.e = l2;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void a(Graphics graphics, int n2, int n3) {
        if (this.d == -1) {
            if (this.f == 1) {
                int n4 = (int)((this.e - System.currentTimeMillis()) / 1000L);
                if (n4 <= 0) return;
                d.e.a(graphics, String.valueOf(this.a) + " : " + ht.a(n4), n2 - 5, n3 + 1, 1);
                d.h.a(graphics, String.valueOf(this.a) + " : " + ht.a(n4), n2 - 4, n3, 1);
                return;
            }
            d.e.a(graphics, this.a, n2 + 1, n3 + 1, 1);
            d.h.a(graphics, this.a, n2, n3, 1);
            return;
        }
        dh dh2 = ko.a(this.d);
        if (dh2 == null || dh2.a == null) return;
        if (this.f == 0) {
            int n5 = d.e.a(String.valueOf(this.a) + " : ");
            graphics.drawImage(dh2.a, n2 - n5 - (dh2.a.getWidth() << 1), n3 + dh2.a.getHeight() / 4, 0);
            d.e.a(graphics, this.a, n2 - n5 - (dh2.a.getWidth() << 1) + 1 + dh2.a.getWidth(), n3 + 1 + dh2.a.getHeight() / 4, 0);
            d.h.a(graphics, this.a, n2 - n5 - (dh2.a.getWidth() << 1) + dh2.a.getWidth(), n3 + dh2.a.getHeight() / 4, 0);
            return;
        }
        if (this.f != 1) return;
        int n6 = (int)((this.e - System.currentTimeMillis()) / 1000L);
        int n7 = d.e.a(String.valueOf(ht.a(n6)) + ":");
        graphics.drawImage(dh2.a, n2 - n7 - (dh2.a.getWidth() << 1), n3 + dh2.a.getHeight() / 4, 0);
        d.e.a(graphics, " : " + ht.a(n6), n2 - n7 - (dh2.a.getWidth() << 1) + 1 + dh2.a.getWidth(), n3 + 1 + dh2.a.getHeight() / 4, 0);
        d.h.a(graphics, " : " + ht.a(n6), n2 - n7 - (dh2.a.getWidth() << 1) + dh2.a.getWidth(), n3 + dh2.a.getHeight() / 4, 0);
    }

    public final void a(long l2) {
        this.e = System.currentTimeMillis() + l2 * 1000L;
    }

    public final void a() {
        if (this.f == 1 && System.currentTimeMillis() - this.e >= 0L) {
            this.b = true;
        }
    }

    private static String a(int n2) {
        int n3 = n2 % 60;
        int n4 = (n2 /= 60) % 60;
        if ((n2 /= 60) > 0) {
            return String.valueOf(n2) + ":" + n4;
        }
        if (n4 > 0) {
            return String.valueOf(n4) + ":" + n3;
        }
        if (n3 < 0) {
            return "0:" + n3;
        }
        return String.valueOf(n3);
    }
}

