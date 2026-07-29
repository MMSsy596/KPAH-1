/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class vo
extends vh {
    public gl a;
    private int c = 0;
    private int d;
    private long e = 0L;
    private byte f;
    private byte g;
    public boolean b;
    private String h = "";

    public vo(int n2, int n3, int n4, int n5, String string, int n6, int n7, boolean bl2, byte by2, byte by3) {
        this.cH = (short)n2;
        this.cL = (short)n4;
        this.cM = (short)n5;
        this.cG = (byte)10;
        this.dc = string;
        this.c = n7 / 60;
        this.d = n7 % 60;
        this.a = null;
        this.b = bl2;
        this.e = System.currentTimeMillis();
        this.g = by3;
        if (n3 > -1) {
            this.a = new gl(n3, n4 + 16, n5 + 16 + by2, n6);
            acv.s.o.addElement(this.a);
        }
    }

    public final void a(int n2, int n3, int n4, int n5, String string, int n6, int n7, boolean bl2, byte by2, byte by3) {
        this.cH = (short)n2;
        this.cL = (short)n4;
        this.cM = (short)n5;
        this.cG = (byte)10;
        this.dc = string;
        this.c = n7 / 60;
        this.d = n7 % 60;
        this.b = bl2;
        this.e = System.currentTimeMillis();
        this.g = by3;
        if (n3 > -1) {
            if (this.a != null) {
                this.a.a = 1;
                this.a.c = (short)n3;
                this.a.b = (byte)n6;
                return;
            }
            this.a = new gl(n3, n4 + 16, n5 + 16 + by2, n6);
            acv.s.o.addElement(this.a);
            return;
        }
        if (this.a != null) {
            acv.s.o.removeElement(this.a);
            this.a = null;
        }
    }

    public final void a(Graphics graphics) {
        if (yi.x != null) {
            if (this.b) {
                if (this.a == null) {
                    graphics.drawImage(yi.x[0], (int)this.cL, (int)this.cM, 0);
                } else {
                    graphics.drawImage(yi.x[2], (int)this.cL, (int)this.cM, 0);
                }
            } else {
                graphics.drawImage(yi.x[1], (int)this.cL, (int)this.cM, 0);
            }
        }
        if (this.cY) {
            if (this.c > -1 && this.a != null) {
                int n2 = d.j[this.g].a(this.h);
                graphics.setColor(1593912);
                graphics.fillRect(this.cL + 16 - n2 / 2 - 3, this.cM - (this.a == null ? 8 : this.a.a + 15), n2 + 5, 14);
                graphics.setColor(0xFFEE00);
                graphics.drawRect(this.cL + 16 - n2 / 2 - 3, this.cM - (this.a == null ? 8 : this.a.a + 15), n2 + 5, 14);
                d.j[this.g].a(graphics, this.h, this.cL + 16, this.cM - (this.a == null ? 8 : this.a.a + 15), 2);
            }
            if (pw.c != null) {
                pw.c.a(0, this.cL + 16, this.cM - (this.a == null ? 8 : this.a.a) + acv.l % 8, 0, 33, graphics);
            }
        }
    }

    public final void b() {
        this.f = (byte)((long)this.d - (System.currentTimeMillis() - this.e) / 1000L);
        if (this.f <= 0) {
            --this.c;
            this.e = System.currentTimeMillis();
            this.d = 59;
        }
        if (this.cY && this.c > -1 && this.a != null) {
            this.h = String.valueOf(this.c < 10 ? "0" + this.c : String.valueOf(this.c)) + " : " + (this.f < 10 ? "0" + this.f : String.valueOf(this.f));
        }
        super.b();
    }

    public final void a(short s2, short s3) {
    }
}

