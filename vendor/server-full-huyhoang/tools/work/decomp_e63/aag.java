/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class aag {
    public byte a;
    public byte b = 1;
    public int c;
    public String d;
    public String[] e;

    public final void a(Graphics graphics, int n2, int n3) {
        try {
            switch (this.a) {
                case 3: {
                    yc yc2 = yi.b(this.c);
                    ko.a(graphics, yc2.h, n2, n3);
                    return;
                }
                case 12: {
                    ko.a(graphics, (short)(this.c + 7500), n2, n3);
                    return;
                }
                case 4: {
                    yi.e(graphics, sc.l[this.c].e, n2, n3, 3);
                    return;
                }
                case 6: {
                    yi.b(graphics, (int)yi.a((short)((short)this.c)).l, n2, n3);
                }
            }
            return;
        }
        catch (Exception exception) {
            String cfr_ignored_0 = String.valueOf(this.a) + " ::::ASD " + this.c;
            return;
        }
    }
}

