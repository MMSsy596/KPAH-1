/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class de
extends acd {
    private int b;
    private int c;
    private byte d;
    private int f;
    private int g;
    private int h;
    private int i;
    public static Image a;
    private gx j = new gx();
    private static int[] k;
    private byte[][] l;
    private byte m;
    private byte n;

    static {
        k = new int[]{-1, -1, -1, 4, 3, 4, 6, -1, 7, 16, 19};
    }

    public de() {
        byte[][] byArrayArray = new byte[3][];
        byte[] byArray = new byte[4];
        byArray[2] = 1;
        byArray[3] = 1;
        byArrayArray[0] = byArray;
        byArrayArray[1] = new byte[]{2, 2, 3, 3};
        byArrayArray[2] = new byte[]{4, 4, 5, 5};
        this.l = byArrayArray;
        this.m = 0;
    }

    public final void a(int n2) {
        this.j.a(n2);
    }

    public final void a(int n2, int n3, int n4, int n5, byte by2, ap ap2, ap ap3) {
        this.j.a(n2, n3, n4, ap2.D, ap3);
        this.c = n2;
        this.d = by2;
        this.f = n5;
        this.b = n5;
    }

    public final void a() {
        Object object;
        this.j.a();
        this.h = this.j.a;
        this.i = this.j.b;
        if (this.j.i) {
            object = this;
            short s2 = ((de)object).j.d.cL;
            short s3 = ((de)object).j.d.cM;
            switch (((de)object).c) {
                case 0: {
                    acv.s.a(s2, s3);
                    break;
                }
                case 1: {
                    abm.a(s2, s3 - 10, 3);
                    abm.b(s2, s3 - 25, 11);
                    abm.a(s2, s3 - 15, 11);
                    abm.a(s2 - 10, s3 - 20, 11);
                    abm.a(s2 + 10, s3 - 20, 11);
                    break;
                }
                case 2: {
                    abm.a(s2, s3 - 10, 5);
                    break;
                }
                case 3: {
                    abm.a(s2, s3 - 10, 7);
                    break;
                }
                case 4: {
                    abm.b(s2, s3 - 25, 15);
                    abm.a(s2, s3 - 15, 15);
                    abm.a(s2 - 10, s3 - 20, 15);
                    abm.a(s2 + 10, s3 - 20, 15);
                    break;
                }
                case 5: 
                case 7: {
                    abm.a(s2, s3 - 10, 30);
                    break;
                }
                case 8: {
                    abm.a(s2, s3 - 10, 3);
                    abm.b(s2, s3 - 25, 11);
                    abm.a(s2, s3 - 15, 9);
                    abm.a(s2 - 10, s3 - 20, 11);
                    abm.a(s2 + 10, s3 - 20, 9);
                    break;
                }
                case 9: {
                    abm.a(s2, s3 - 15, 50);
                }
            }
            if (((de)object).d != 0 && ((de)object).d < zp.d.length) {
                acv.s.a(zp.d[((de)object).d], 0, (int)s2, s3 - 25, 1, -2);
            }
            if (((de)object).b != 2000000) {
                if (((de)object).b != 0) {
                    if (((de)object).c < 20) {
                        acv.s.a("-" + ((de)object).b, 0, (int)s2, s3 - 15, 1, -2);
                    } else {
                        acv.s.a("-" + ((de)object).f, 0, (int)s2, s3 - 15, 1, -2);
                    }
                } else {
                    acv.s.a("MISS", 0, (int)s2, s3 - 15, 1, -2);
                }
            }
            ((de)object).j.d.u = 2;
            if (((de)object).j.d.cG == 1) {
                if (((de)object).d == 0) {
                    ((bb)((de)object).j.d).a_();
                    abm.b.addElement(new di(s2, s3 - 10, 11));
                } else if (((de)object).d == 2) {
                    ((bb)((de)object).j.d).l();
                    abm.b.addElement(new di(s2, s3 - 10, 12));
                }
            } else if (((de)object).j.d.cG == 0) {
                if (((de)object).d == 0) {
                    ((hw)((de)object).j.d).a_();
                    abm.b.addElement(new di(s2, s3 - 10, 11));
                } else if (((de)object).d == 2) {
                    ((hw)((de)object).j.d).l();
                    abm.b.addElement(new di(s2, s3 - 10, 12));
                }
            }
            ((acd)object).e = true;
        }
        switch (this.c) {
            case 0: {
                abm.a(this.h, this.i, 1);
                return;
            }
            case 5: 
            case 7: {
                abm.a(this.h, this.i, 29);
                return;
            }
            case 1: {
                abm.a(this.h, this.i, 2);
                return;
            }
            case 2: {
                abm.a(this.h, this.i, 4);
                return;
            }
            case 3: {
                abm.a(this.h, this.i, 6);
                abm.a(this.h, this.i, 7);
                return;
            }
            case 4: {
                abm.a(this.h, this.i, 8);
                return;
            }
            case 8: {
                return;
            }
            case 9: {
                abm.b(this.h, this.i + 25, 59);
                object = new c(this.h, this.i + 25);
                abm.b((di)object);
                object = new c(this.h, this.i + 25);
                abm.b((di)object);
                this.m = (byte)(this.m + 1);
                if (this.m <= 3) break;
                this.m = 0;
                return;
            }
            case 10: {
                ++this.g;
                if (this.g > this.l[this.j.g].length - 1) {
                    this.g = 0;
                }
                this.n = this.l[this.j.g][this.g];
            }
        }
    }

    public final void a(Graphics graphics) {
        if (this.c < 20) {
            int n2;
            Image image;
            if (k[this.c] != -1 && (image = yi.c(n2 = k[this.c])) != null) {
                if (this.c != 10 && this.c != 9) {
                    graphics.drawRegion(image, 0, this.j.g * yb.b[1][n2], (int)yb.b[0][n2], (int)yb.b[1][n2], this.j.h, this.h, this.i, 3);
                    return;
                }
                if (this.c == 10) {
                    graphics.drawRegion(image, 0, this.n * yb.b[1][n2], (int)yb.b[0][n2], (int)yb.b[1][n2], this.j.h, this.h, this.i, 3);
                    return;
                }
                if (this.c == 9) {
                    graphics.drawRegion(image, 0, this.m * yb.b[1][n2], (int)yb.b[0][n2], (int)yb.b[1][n2], 0, this.h, this.i, 3);
                    return;
                }
            }
        } else {
            graphics.drawImage(a, this.h, this.i, 3);
        }
    }
}

