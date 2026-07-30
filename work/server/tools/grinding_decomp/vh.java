/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public abstract class vh {
    public boolean cF;
    public byte cG;
    public short cH = (short)32001;
    public short cI = (short)-1;
    public short cJ = (short)-1;
    public short cK = (short)-1;
    public short cL;
    public short cM;
    public short cN;
    public short cO;
    public short cP;
    public short cQ;
    public short cR;
    public byte cS = (byte)-1;
    public byte cT = (byte)-1;
    public byte cU = (byte)-1;
    public byte cV;
    public byte cW;
    public boolean cX;
    public boolean cY;
    public boolean cZ;
    public long da;
    public rx db;
    public String dc;
    public boolean dd = false;
    public int de = -1;
    public Vector df = new Vector();
    public short dg;
    public long dh;
    public short di = 0;
    public boolean dj;
    public boolean dk = true;

    public vh() {
        System.currentTimeMillis();
    }

    public abstract void a(Graphics var1);

    public void E() {
    }

    public abstract void a(short var1, short var2);

    public void b() {
        if (this.db != null) {
            this.db.a((int)this.cL, this.cM - this.cN - 10);
            rx rx2 = this.db;
            if (rx2.a > 0) {
                --rx2.a;
            }
            if (rx2.a == 0) {
                this.db = null;
            }
        }
    }

    public boolean d() {
        return false;
    }

    public final void a(zx zx2) {
        int n2 = 0;
        while (n2 < this.df.size()) {
            zx zx3 = (zx)this.df.elementAt(n2);
            if (zx3.g == zx2.g) {
                this.df.removeElementAt(n2);
                break;
            }
            ++n2;
        }
        this.df.addElement(zx2);
    }

    public void a(short s2) {
    }

    public void a(Graphics graphics, int n2, int n3) {
        graphics.translate(-this.cL + n2, -this.cM + 20);
        this.a(graphics);
    }

    public void a_(Graphics graphics) {
        if (((vh)((Object)rx2)).db != null) {
            rx rx2 = ((vh)((Object)rx2)).db;
            boolean bl2 = true;
            int n2 = 0xFFFFFF;
            int n3 = rx2.d;
            int n4 = rx2.e;
            int n5 = rx2.c - rx2.d - 4;
            int n6 = rx2.b - rx2.e / 2;
            Graphics graphics2 = graphics;
            graphics2.drawRegion(rx.h, 0, 0, 8, 8, 0, n6, n5, 0);
            graphics2.drawRegion(rx.h, 0, 8, 8, 8, 0, n6 + n4 - 8, n5, 0);
            graphics2.drawRegion(rx.h, 0, 24, 8, 8, 0, n6, n5 + n3 - 8, 0);
            graphics2.drawRegion(rx.h, 0, 16, 8, 8, 0, n6 + n4 - 8, n5 + n3 - 8, 0);
            graphics2.setColor(n2);
            graphics2.fillRect(n6 + 8, n5, n4 - 16, 8);
            graphics2.fillRect(n6 + 8, n5 + n3 - 8, n4 - 16, 7);
            graphics2.fillRect(n6, n5 + 8, n4, n3 - 16);
            graphics2.setColor(1);
            graphics2.fillRect(n6 + 8, n5, n4 - 16, 1);
            graphics2.fillRect(n6 + 8, n5 + n3 - 1, n4 - 16, 1);
            graphics2.fillRect(n6, n5 + 8, 1, n3 - 16);
            graphics2.fillRect(n6 + n4 - 1, n5 + 8, 1, n3 - 16);
            if (rx2.f == 1) {
                graphics.drawImage(rx.i, rx2.b, rx2.c - 5, 17);
            }
            int n7 = rx2.c - rx2.d;
            n6 = 0;
            while (n6 < rx2.g.length) {
                d.d.a(graphics, rx2.g[n6], rx2.b, n7, 2);
                n7 += 14;
                ++n6;
            }
        }
    }

    public int e_() {
        return this.cM;
    }

    public String a() {
        return "Actor C=" + this.cG + " ID=" + this.cH;
    }

    public boolean S() {
        return false;
    }

    public void t() {
    }

    public boolean g_() {
        return false;
    }

    public boolean x() {
        return false;
    }

    public boolean y() {
        return false;
    }

    public boolean O() {
        return false;
    }

    public boolean c() {
        return false;
    }

    public int f() {
        return 0;
    }

    public int g() {
        return 32000;
    }

    public boolean N() {
        return false;
    }

    public void j(int n2) {
    }

    public void k(int n2) {
    }

    public void l(int n2) {
    }

    public void m(int n2) {
    }

    public int L() {
        return 0;
    }

    public int M() {
        return 0;
    }

    public void h(int n2) {
    }

    public short p() {
        return 0;
    }

    public byte o() {
        return 0;
    }

    public short q() {
        return 0;
    }

    public boolean r() {
        return false;
    }

    public void a(int n2, int n3, int n4, long l2) {
    }

    public void a(Vector vector, byte by2, short s2, int[] nArray, int[] nArray2) {
    }

    public void b(int n2) {
    }

    public void a(int n2, int n3, int n4, long l2, boolean bl2, boolean bl3, boolean bl4, int n5, byte by2, byte by3) {
    }

    public short P() {
        return 0;
    }
}

