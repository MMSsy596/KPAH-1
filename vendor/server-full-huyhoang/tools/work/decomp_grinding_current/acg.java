/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class acg
extends di {
    private ap o;
    private int p;
    public int n;
    private int q;
    private Image r;
    private int[] s;
    private short t;
    private zx u;

    public acg(short s2, int n2, ap ap2) {
        this.o = ap2;
        this.g = s2;
        this.n = n2;
        this.p = (int)(System.currentTimeMillis() / 1000L);
        switch (s2) {
            case 0: {
                if (ap2.cH == acv.s.t.cH) {
                    acv.s.x = true;
                    return;
                }
                ap2.R.removeElement(this);
                return;
            }
            case 1: {
                this.q = (int)(System.currentTimeMillis() / 1000L) - 1;
                this.u = new zx(ap2.cL, ap2.cM - 17, 22);
                return;
            }
            case 2: {
                this.s = new int[]{7053112, 7517233, 16317187, 8386060, 16453379};
                return;
            }
            case 3: 
            case 4: {
                int[] nArray = null;
                nArray = s2 == 3 ? new int[]{-1, -2950406, -4849665, -5778950, -7354371, -9718017, -9786901, -10969884, -11889954, -12811056} : new int[]{-1381912, -3092533, -4408651, -5330011, -6185321, -6711666, -7961734, -8817301, -10330540, -11317177};
                this.r = Image.createImage((int)50, (int)105);
                Object object = this.r.getGraphics();
                object.setColor(-65315);
                object.fillRect(0, 0, 50, 105);
                object.translate(-ap2.cL + 25, -ap2.cM + 105 - 5);
                ap2.a((Graphics)object);
                this.r = yi.a(this.r);
                object = new int[5250];
                this.r.getRGB((int[])object, 0, 50, 0, 0, 50, 105);
                int n3 = 0x1C71C7;
                int n4 = 0;
                while (n4 < ((Graphics)object).length) {
                    if (object[n4] != 0xFFFFFF) {
                        reference var6_8 = object[n4] / n3;
                        object[n4] = (Graphics)nArray[yg.d((int)var6_8)];
                    }
                    ++n4;
                }
                this.r = Image.createRGBImage((int[])object, (int)50, (int)105, (boolean)true);
                ap2.S = true;
                return;
            }
            case 5: {
                this.t = ap2.I;
                ap2.I = (short)(ap2.I / 2);
            }
        }
    }

    public final void a() {
        if (this.g == 1) {
            this.u.a();
            this.u.a(this.o.cL, this.o.cM - 17);
            if (System.currentTimeMillis() / 1000L - (long)this.q > 1L) {
                this.q = (int)(System.currentTimeMillis() / 1000L);
                this.o.v -= 1000;
                acv.s.a("-1000", 4, (int)this.o.cL, this.o.cM - 40, 0, -1);
                if (this.o.v < 0) {
                    this.o.v = 0;
                }
            }
        }
        if (System.currentTimeMillis() / 1000L - (long)this.p > (long)this.n) {
            switch (this.g) {
                case 0: {
                    acv.s.x = false;
                    break;
                }
                case 1: {
                    break;
                }
                case 3: 
                case 4: {
                    this.o.S = false;
                    break;
                }
                case 5: {
                    this.o.I = this.t;
                }
            }
            this.o.R.removeElement(this);
        }
    }

    public final void a(Graphics graphics) {
        switch (this.g) {
            case 1: {
                this.u.a(graphics);
                return;
            }
            case 2: {
                int n2 = 0;
                while (n2 < 12) {
                    int n3 = 8 * yg.b(n2 * 30) >> 10;
                    int n4 = -(8 * yg.a(n2 * 30)) >> 10;
                    graphics.setColor(this.s[yi.m(5)]);
                    graphics.fillRect(this.o.cL + n3, this.o.cM - this.o.cN + 4 + n4, 1, 5);
                    ++n2;
                }
                return;
            }
            case 3: 
            case 4: {
                graphics.drawImage(this.r, (int)this.o.cL, this.o.cM + 5, 33);
            }
        }
    }
}

