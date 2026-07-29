/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class kc
extends di {
    private ap p;
    private boolean q = false;
    private boolean r = true;
    private int s = 0;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int[] y;
    public String[] n;
    private int z = 0;
    public boolean o = false;
    private static byte[][] A = new byte[][]{{15, 28}, {4, 4}, {15, 28}};
    private static byte[][] B = new byte[][]{{17, 36}, {32, 31}, {14, 14}, {17, 36}, {17, 36}};
    private static byte[] C = new byte[]{3, 5, 3};

    public kc(int n2, int n3, ap ap2, int n4, boolean bl2) {
        this.z = n4;
        this.d = ap2.cL;
        this.e = ap2.cM - 10;
        this.w = n2;
        this.x = n3 - 40;
        this.p = ap2;
        this.s = yg.a(ap2.cL - 10 - this.w, -(ap2.cM - this.x));
        this.t = yg.a(this.w, this.x, ap2.cL - 10, (int)ap2.cM);
        this.y = new int[3];
        n2 = 0;
        while (n2 < 3) {
            this.y[n2] = yi.m(6);
            ++n2;
        }
        this.n = new String[2];
    }

    public final void a() {
        this.s = yg.a(this.d - this.w, -(this.e - this.x));
        this.t = yg.a(this.w, this.x, this.d, this.e);
        this.u += 6;
        ++this.v;
        if (!this.q) {
            this.d = this.p.cL;
            this.e = this.p.cM - 10;
        }
        if (this.u > this.t && !this.q) {
            abm.a(this.p.cL, this.p.cM - 10, 12);
            abm.b.removeElement(this);
            this.p.a_();
            this.q = true;
            if (this.n[0] != null) {
                acv.s.a(this.n[0], 0, this.d, this.e - 15, 1, -2);
            }
            if (this.n[1] != null) {
                acv.s.a(this.n[1], 0, this.d, this.e - 15, 2, -2);
            }
            if (this.r) {
                yu yu2 = null;
                if (this.z == 1) {
                    yu2 = new yu(this.d, this.e, 4, 5, 14, 14, false, false);
                } else {
                    yu2 = new yu(this.d, this.e, 28, 3, 32, 31, true, false);
                    if (this.z == 2) {
                        dl dl2 = new dl(this.p.cL + mo.d[this.p.D], this.p.cM + mo.e[this.p.D], this.p, this.p, 5);
                        new dl(this.p.cL + mo.d[this.p.D], this.p.cM + mo.e[this.p.D], this.p, this.p, 5).o = this.o;
                        abm.b.addElement(dl2);
                    }
                }
                abm.a.addElement(yu2);
            }
        }
    }

    public final void a(Graphics graphics) {
        int n2 = this.u * yg.b(yg.c(this.s)) >> 10;
        int n3 = -(this.u * yg.a(yg.c(this.s))) >> 10;
        graphics.drawImage(yi.j, this.w + n2, this.x + n3, 3);
        int n4 = 0;
        while (n4 < 3) {
            yi.a(graphics, (int)A[this.z][0], 0, this.y[n4] / 2 * B[this.z << 1][1], (int)B[this.z << 1][0], (int)B[this.z << 1][1], this.w + n2 + yi.m(10) - 5, this.x + n3 + yi.m(10) - 10, 3);
            int n5 = n4;
            this.y[n5] = this.y[n5] + 1;
            if (this.y[n4] >= C[this.z]) {
                this.y[n4] = 0;
            }
            ++n4;
        }
    }
}

