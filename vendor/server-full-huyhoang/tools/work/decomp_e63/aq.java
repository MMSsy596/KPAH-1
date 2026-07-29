/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class aq
extends ba {
    public static String[] a = new String[]{""};
    public static byte[] b = new byte[1];

    public final void a(Graphics graphics) {
        ko.a(graphics, (short)(b[this.c] + 8000), this.cL, this.cM - this.e);
    }

    public final String a() {
        return a[this.c];
    }

    public final boolean c() {
        return true;
    }
}

