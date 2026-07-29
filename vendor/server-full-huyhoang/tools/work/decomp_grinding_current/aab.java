/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class aab {
    private short a;
    private short b;
    private Image c;

    public aab(Image image, int n2, int n3) {
        this.c = image;
        this.a = (short)n2;
        this.b = (short)n3;
        image.getHeight();
    }

    public static aab a(String string, int n2, int n3) {
        return new aab(acf.a(string), n2, n3);
    }

    public final void a(int n2, int n3, int n4, int n5, int n6, Graphics graphics) {
        if (this.c != null) {
            graphics.drawRegion(this.c, 0, n2 * this.b, (int)this.a, (int)this.b, n5, n3, n4, n6);
        }
    }
}

