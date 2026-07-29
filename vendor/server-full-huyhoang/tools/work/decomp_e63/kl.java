/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class kl
extends di {
    private byte o = 0;
    private int p = 0;
    private int q;
    private int r;
    private Vector s = new Vector();
    public boolean n;
    private static int t = 5;
    private static int u;
    private static int v;
    private short w;

    static {
        v = yi.d(1, -1);
    }

    public kl(byte by2, boolean n2, int n3, int n4) {
        this.q = n4;
        this.r = (int)(System.currentTimeMillis() / 1000L);
        this.o = by2;
        this.p = n3;
        switch (by2) {
            case 0: {
                break;
            }
            case 1: {
                this.w = (short)801;
                break;
            }
            case 2: {
                this.w = (short)805;
                break;
            }
            case 3: {
                this.w = (short)802;
                this.o = (byte)3;
                break;
            }
            case 4: {
                this.w = (short)803;
                this.o = (byte)3;
                break;
            }
            case 5: {
                this.w = (short)804;
                this.o = (byte)3;
            }
        }
        n2 = 0;
        while (n2 < n3) {
            ec ec2 = null;
            ec2 = new ec((abj.h - acv.o + yi.m(acv.m << 1)) * 10, (abj.i - (acv.n << 1) + yi.m(acv.n << 1)) * 10);
            ec2.e = by2 == 2 || this.o == 3 ? yi.m(3) : yi.m(4);
            ec2.g = 16 + (yi.m(3) << 2);
            ec2.d = yi.d(-1, 1);
            ec2.f = yi.m(ec2.g);
            ec2.h = (byte)yi.m(20);
            this.s.addElement(ec2);
            ++n2;
        }
    }

    public final void a(Graphics graphics) {
        acv.a(graphics);
        graphics.translate(-abj.h, -abj.i);
        switch (this.o) {
            case 0: {
                graphics.setColor(0xDDDDDD);
                int n2 = 0;
                while (n2 < this.p) {
                    ec ec2 = (ec)this.s.elementAt(n2);
                    if (ec2.a / 10 > abj.h && ec2.a / 10 < abj.h + acv.m && ec2.b / 10 > abj.i) {
                        graphics.fillRect(ec2.a / 10, ec2.b / 10, 1, ec2.e + 2);
                    }
                    ++n2;
                }
                return;
            }
            case 1: {
                Image image = null;
                if (ko.a(this.w) != null) {
                    image = ko.a((short)this.w).a;
                }
                if (image != null) {
                    int n3 = 0;
                    while (n3 < this.p) {
                        ec ec3 = (ec)this.s.elementAt(n3);
                        if (ec3.a / 10 > abj.h && ec3.a / 10 < abj.h + acv.m && ec3.b / 10 > abj.i) {
                            graphics.drawRegion(image, 0, ec3.f / (ec3.g / 4) * 10, 16, 10, 0, ec3.a / 10, ec3.b / 10, 3);
                        }
                        ++n3;
                    }
                }
                return;
            }
            case 3: {
                Image image = null;
                if (ko.a(this.w) != null) {
                    image = ko.a((short)this.w).a;
                }
                int n4 = 0;
                if (image != null) {
                    int n5 = 0;
                    while (n5 < this.p) {
                        ec ec4 = (ec)this.s.elementAt(n5);
                        if (ec4.a / 10 > abj.h && ec4.a / 10 < abj.h + acv.m && ec4.b / 10 > abj.i) {
                            n4 = 2 - ec4.e + 1;
                            if (n4 < 2) {
                                n4 = ec4.h / 10;
                            }
                            graphics.drawRegion(image, 0, n4 * 10, 10, 10, 0, ec4.a / 10, ec4.b / 10, 3);
                            ec4.h = (byte)(ec4.h + 1);
                            if (ec4.h >= 20) {
                                ec4.h = 0;
                            }
                        }
                        ++n5;
                    }
                }
                return;
            }
            case 2: {
                Image image = null;
                if (ko.a(this.w) != null) {
                    image = ko.a((short)this.w).a;
                }
                if (image == null) break;
                int n6 = 0;
                while (n6 < this.p) {
                    ec ec5 = (ec)this.s.elementAt(n6);
                    if (ec5.a / 10 > abj.h && ec5.a / 10 < abj.h + acv.m && ec5.b / 10 > abj.i && ko.a(this.w) != null) {
                        graphics.drawRegion(image, 0, (2 - ec5.e) * 5, 5, 5, 0, ec5.a / 10, ec5.b / 10, 3);
                    }
                    ++n6;
                }
                break;
            }
        }
    }

    public static void b() {
        int n2 = 1;
        if (acv.l % 6 == 3) {
            n2 = yi.m(15);
        }
        if (n2 == 0 && t == 5) {
            t = 5 + yi.m(20);
            u = 50 + yi.m(100);
        }
        if (u > 0) {
            --u;
        }
        if (u == 0 && t > 5 && acv.l % 4 == 2) {
            --t;
        }
    }

    public final void a() {
        if (this.q > 0 && System.currentTimeMillis() / 1000L - (long)this.r > (long)this.q) {
            this.n = true;
        }
        switch (this.o) {
            case 0: {
                int n2 = 0;
                while (n2 < this.p) {
                    ec ec2 = (ec)this.s.elementAt(n2);
                    ec2.b += (ec2.e + 1) * 15 + (3 - ec2.e) * 3;
                    ++ec2.c;
                    ec2.a += (3 - ec2.e + 1 << 1) + t * v;
                    if (ec2.b / 10 < abj.i - acv.o || ec2.b / 10 > abj.i + (acv.n + acv.p) - (4 - ec2.e) * 50 || ec2.a / 10 < abj.h - acv.o || ec2.a / 10 > abj.h + acv.m + acv.o) {
                        this.a(ec2);
                    }
                    ++n2;
                }
                return;
            }
            case 1: {
                int n3 = 0;
                while (n3 < this.p) {
                    ec ec3 = (ec)this.s.elementAt(n3);
                    ec3.b += 10;
                    ec3.a += ec3.d * 10 + t * v;
                    ++ec3.f;
                    if (ec3.f >= ec3.g) {
                        ec3.f = 0;
                    }
                    if (ec3.b / 10 < abj.i - acv.o || ec3.b / 10 > abj.i + (acv.n + acv.p) - (4 - ec3.e) * 50 || ec3.a / 10 < abj.h - acv.o || ec3.a / 10 > abj.h + acv.m + acv.o) {
                        this.a(ec3);
                    }
                    ++n3;
                }
                return;
            }
            case 3: {
                int n4 = 0;
                while (n4 < this.p) {
                    ec ec4 = (ec)this.s.elementAt(n4);
                    ec4.b += (ec4.e + 2) * 5;
                    ec4.a += (ec4.e + 1 << 1) + t * v;
                    if (ec4.b / 10 < abj.i - acv.o || ec4.b / 10 > abj.i + (acv.n + acv.p) - (4 - ec4.e) * 50 || ec4.a / 10 < abj.h - acv.o || ec4.a / 10 > abj.h + acv.m + acv.o) {
                        this.a(ec4);
                    }
                    ++n4;
                }
                return;
            }
            case 2: {
                int n5 = 0;
                while (n5 < this.p) {
                    ec ec5 = (ec)this.s.elementAt(n5);
                    ec5.b += (ec5.e + 4) * 3;
                    ec5.a += (ec5.e + 1 << 1) + t * v;
                    if (ec5.b / 10 < abj.i - acv.o || ec5.b / 10 > abj.i + (acv.n + acv.p) - (4 - ec5.e) * 50 || ec5.a / 10 < abj.h - acv.o || ec5.a / 10 > abj.h + acv.m + acv.o) {
                        this.a(ec5);
                    }
                    ++n5;
                }
                break;
            }
        }
    }

    private void a(ec ec2) {
        if (this.n) {
            this.s.removeElement(ec2);
            this.p = this.s.size();
            if (this.s.size() == 0) {
                abj.am.removeElement(this);
                return;
            }
        } else {
            ec2.b = (abj.i - acv.p + yi.m(acv.n << 1)) * 10;
            ec2.a = (abj.h - acv.o + yi.m(acv.m << 1)) * 10;
            if (this.o == 2 || this.o == 3) {
                ec2.e = yi.m(3);
                return;
            }
            ec2.e = yi.m(4);
        }
    }
}

