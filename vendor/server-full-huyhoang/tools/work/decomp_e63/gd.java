/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class gd {
    public int a = -1;
    private byte c;
    private int d;
    private static Image e;
    public boolean b = false;

    static {
        byte[] byArray = new byte[10];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 3;
        byArray[4] = 4;
        byArray[6] = 1;
        byArray[7] = 2;
        byArray[8] = 3;
        byArray[9] = 4;
        e = null;
    }

    public static void a() {
        e = acf.a("cooldown");
    }

    public final void a(int n2, boolean bl2) {
        this.a = 1;
        this.c = (byte)n2;
        this.b = bl2;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        try {
            if (this.a == -1) {
                return;
            }
            if (this.a == 1) {
                long l2;
                if (ko.a != null) {
                    ko.a.a(this.c, n2 - 2, n3 - 1, 0, 0, graphics);
                }
                if ((l2 = System.currentTimeMillis() - acv.s.t.ar[this.c]) < acv.s.t.au[this.c]) {
                    int n4 = (int)(l2 * 20L / acv.s.t.au[this.c]);
                    graphics.drawRegion(e, 0, n4, 20, 20 - n4, 0, n2 - 2, n3 - 2 + n4, 20);
                    return;
                }
            } else {
                yi.e(graphics, sc.l[this.d].e, n2, n3, 0);
                d.i[3].a(graphics, String.valueOf(acv.s.t.br[this.d]), n2 + 16, n3 + 9, 1);
                long l3 = System.currentTimeMillis() - acv.s.t.bt[this.d];
                if (l3 < sc.l[this.d].c) {
                    int n5 = (int)(l3 * 20L / sc.l[this.d].c);
                    graphics.drawRegion(e, 0, n5, 20, 20 - n5, 0, n2 - 2, n3 - 2 + n5, 20);
                    return;
                }
            }
        }
        catch (Exception exception) {
            String cfr_ignored_0 = "ERROR QUICKSLOT " + exception.toString();
        }
    }

    public final byte b() {
        return this.c;
    }

    public final int c() {
        return this.d;
    }

    public final void a(int n2) {
        this.a = 2;
        this.d = n2;
    }

    public final void d() {
        this.a = -1;
    }
}

