/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class xe {
    public static byte[][][] a = new byte[][][]{new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}};
    public static byte[][][] b = new byte[][][]{new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}};
    public static byte[][][] c = new byte[][][]{new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}};
    public static Image[] d = new Image[10];
    public static int[] e = new int[10];
    public static int[] f = new int[10];
    public static int[] g = new int[10];
    public static int[] h = new int[10];
    public byte i = 0;
    public int j;
    private int l;
    private int m;
    public int k;

    public final void a(Graphics graphics, int n2, int n3) {
        if (d[this.i] == null) {
            return;
        }
        graphics.drawRegion(d[this.i], 0, this.l * f[this.i], e[this.i], f[this.i], 0, n2 + g[this.i], n3 + h[this.i], 33);
    }

    public final void a() {
        try {
            switch (this.j) {
                case 0: {
                    ++this.m;
                    if (this.m > b[this.i][this.k].length - 1) {
                        this.m = 0;
                    }
                    this.l = b[this.i][this.k][this.m];
                    return;
                }
                case 1: {
                    ++this.m;
                    if (this.m > a[this.i][this.k].length - 1) {
                        this.m = 0;
                    }
                    this.l = a[this.i][this.k][this.m];
                    return;
                }
                case 2: {
                    ++this.m;
                    if (this.m > c[this.i][this.k].length - 1) {
                        this.m = c[this.i][this.k].length - 1;
                    }
                    this.l = c[this.i][this.k][this.m];
                }
            }
            return;
        }
        catch (Exception exception) {
            System.out.println(String.valueOf(this.i) + " >> " + this.m + " >> " + this.k);
            return;
        }
    }
}

