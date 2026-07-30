/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Image;

public final class rx {
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    String[] g;
    public static Image h;
    public static Image i;

    public rx() {
    }

    public rx(int n2, String string, int n3) {
        this.a(n2, string);
        this.f = n3;
    }

    public final void a(int n2, int n3) {
        this.b = n2;
        this.c = n3;
    }

    private void a(int n2, String string) {
        this.g = d.d.a(string, 100);
        abj.aE = this.g.length << 3;
        this.d = 14 * this.g.length + 4 + 4;
        this.e = 30;
        int n3 = 0;
        while (n3 < this.g.length) {
            int n4 = d.d.a(this.g[n3]) + 5;
            if (n4 > this.e) {
                this.e = n4;
            }
            ++n3;
        }
        this.a = n2;
    }
}

