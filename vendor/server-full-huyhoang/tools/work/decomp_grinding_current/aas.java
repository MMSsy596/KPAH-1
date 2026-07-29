/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class aas
extends xx {
    private int d;
    private int e;
    private static byte[] f = new byte[]{15};
    private static final byte[][] g;
    private byte h;

    static {
        byte[][] byArrayArray = new byte[1][];
        byte[] byArray = new byte[8];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 1;
        byArray[5] = 1;
        byArray[6] = 2;
        byArrayArray[0] = byArray;
        g = byArrayArray;
    }

    public aas(int n2, int n3, int n4) {
        this.a = n2;
        this.b = n3;
        this.h = 0;
    }

    public final void a(Graphics graphics) {
        yi.a(graphics, (int)f[this.h], 0, g[this.h][this.d] * di.c[f[this.h]], (int)di.b[f[this.h]], (int)di.c[f[this.h]], this.a, this.b, 33);
    }

    public final void a() {
        ++this.e;
        if (this.e % 2 == 0) {
            ++this.d;
            if (this.d >= g[this.h].length) {
                this.d = 0;
                this.c = true;
            }
        }
    }
}

