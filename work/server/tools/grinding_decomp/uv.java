/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class uv
extends bb {
    private static gk ak = new gk();
    private static Image al;
    private static Image am;
    private short[] an = new short[12];
    private short[] ao = new short[12];
    private gx ap;
    private ap aq;
    private ap ar;
    private int as;
    private int at;
    private byte[] au;
    private byte[] av;
    private byte[] aw;
    private byte[] ax;
    private byte[] ay;
    private byte[] az;
    private byte[] aA;
    private byte[] aB;

    static {
        try {
            am = Image.createImage((String)"/rong/shadow.png");
            al = Image.createImage((String)"/rong/Big0.png");
            InputStream inputStream = "".getClass().getResourceAsStream("/rong/dragonData");
            int n2 = inputStream.read();
            uv.ak.a = new byte[n2];
            uv.ak.b = new byte[n2];
            uv.ak.c = new byte[n2];
            uv.ak.d = new byte[n2];
            uv.ak.e = new byte[n2];
            int n3 = 0;
            while (n3 < n2) {
                uv.ak.a[n3] = (byte)inputStream.read();
                uv.ak.b[n3] = (byte)inputStream.read();
                uv.ak.c[n3] = (byte)inputStream.read();
                uv.ak.d[n3] = (byte)inputStream.read();
                uv.ak.e[n3] = (byte)inputStream.read();
                ++n3;
            }
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
        }
    }

    public uv() {
        new kt();
        byte[] byArray = new byte[8];
        byArray[0] = 5;
        byArray[1] = 3;
        byArray[2] = -1;
        byArray[3] = -8;
        byArray[4] = -8;
        byArray[7] = 2;
        this.au = byArray;
        byte[] byArray2 = new byte[8];
        byArray2[0] = -5;
        byArray2[1] = -8;
        byArray2[2] = -10;
        byArray2[3] = -7;
        byArray2[4] = -2;
        byArray2[7] = -3;
        this.av = byArray2;
        byte[] byArray3 = new byte[8];
        byArray3[0] = -12;
        byArray3[1] = -6;
        byArray3[3] = 10;
        byArray3[4] = 10;
        byArray3[5] = 7;
        byArray3[7] = -8;
        this.aw = byArray3;
        this.ax = new byte[]{4, 8, 12, 8, -2, -8, -10, -4};
        byte[] byArray4 = new byte[8];
        byArray4[0] = 2;
        byArray4[1] = 3;
        byArray4[2] = 4;
        byArray4[3] = 3;
        byArray4[4] = 2;
        byArray4[5] = 1;
        byArray4[7] = 1;
        this.ay = byArray4;
        byte[] byArray5 = new byte[8];
        byArray5[3] = 2;
        byArray5[4] = 2;
        byArray5[5] = 2;
        this.az = byArray5;
        this.aA = new byte[]{9, 6, 9, 6, 9, 6, 9, 6};
        byte[] byArray6 = new byte[8];
        byArray6[0] = 4;
        byArray6[1] = 3;
        byArray6[2] = 3;
        byArray6[3] = 1;
        byArray6[4] = 7;
        byArray6[7] = 2;
        this.aB = byArray6;
        this.aq = acv.s.t;
        this.cL = (short)(this.aq.cL - 80);
        this.cM = (short)(this.aq.cM - 80);
        int n2 = 0;
        while (n2 < this.an.length) {
            this.an[n2] = this.cL;
            this.ao[n2] = this.cM;
            ++n2;
        }
        this.ar = new oz(this);
        this.ap = new gx();
        this.ap.f = true;
        this.ap.a(20);
        this.ap.a(7, this.cL, this.cM, 7, this.aq);
    }

    public final void b() {
        int n2;
        this.b_();
        this.ap.a();
        if (this.ap.i) {
            this.ap.i = false;
            this.ap.e = 0;
            this.ar.cL = (short)(this.aq.cL - 80 + yi.m(160));
            this.ar.cM = (short)(this.aq.cM - 80 + yi.m(160));
            this.ap.d = this.ar;
        }
        this.cL = (short)this.ap.a;
        this.cM = (short)this.ap.b;
        this.as = this.ap.c / 2;
        this.at = yb.b(yg.a(this.an[9] - this.an[11], -(this.ao[9] - this.ao[11]))) / 2;
        if (acv.l % 2 == 0) {
            n2 = this.an.length - 1;
            while (n2 > 0) {
                this.an[n2] = this.an[n2 - 1];
                this.ao[n2] = this.ao[n2 - 1];
                --n2;
            }
            this.an[0] = this.cL;
            this.ao[0] = this.cM;
        }
        if (acv.l % 20 == 10) {
            n2 = yg.a(this.aq.cL, (int)this.aq.cM, (int)this.an[2], this.ao[2] - 50);
            int n3 = yg.a(this.aq.cL, (int)this.aq.cM, this.cL + this.au[this.as], this.cM + this.av[this.as] - 50);
            int n4 = yg.a(this.aq.cL - this.an[2], -(this.aq.cM - (this.ao[2] - 50)));
            int n5 = yg.a(this.aq.cL - (this.cL + this.au[this.as]), -(this.aq.cM - (this.cM + this.av[this.as] - 50)));
            if (n2 > n3 && yg.d(n4 - n5) < 50) {
                n2 = yg.a(this.an[2] - (this.cL + this.au[this.as]), -(this.ao[2] - 50 - (this.cM + this.av[this.as] - 50)));
                n3 = -10 * yg.b(n2) >> 10;
                n4 = -(-10 * yg.a(n2)) >> 10;
                abj.a(4, this, this.aq, this.cL + this.au[this.as] + n3, this.cM + this.av[this.as] - 50 + n4, 0, (byte)0, n2);
            }
        }
    }

    public final void a(Graphics graphics) {
        graphics.drawImage(am, (int)this.an[11], (int)this.ao[11], 3);
        int n2 = 0;
        while (n2 < this.an.length - 1) {
            graphics.drawImage(am, (int)this.an[n2], (int)this.ao[n2], 3);
            ++n2;
        }
        graphics.drawImage(am, (int)this.cL, (int)this.cM, 3);
        if (this.U != null) {
            n2 = 0;
            while (n2 < this.U.size()) {
                ((acc)this.U.elementAt(n2)).b(graphics, this.cL, this.cM);
                ++n2;
            }
        }
        graphics.drawRegion(al, (int)uv.ak.b[this.aA[this.at]], (int)uv.ak.c[this.aA[this.at]], (int)uv.ak.d[this.aA[this.at]], (int)uv.ak.e[this.aA[this.at]], (int)this.aB[this.at], this.an[11] + this.aw[this.at], this.ao[11] + this.ax[this.at] - 50, 3);
        if (this.as == 1 || this.as == 2 || this.as == 3) {
            this.d(graphics);
            this.c(graphics);
        } else {
            this.c(graphics);
            this.d(graphics);
        }
        if (this.T != null) {
            n2 = 0;
            while (n2 < this.T.size()) {
                ((acc)this.T.elementAt(n2)).b(graphics, this.cL, this.cM);
                ++n2;
            }
        }
    }

    private void c(Graphics graphics) {
        int n2 = 0;
        while (n2 < this.an.length - 1) {
            graphics.drawRegion(al, (int)uv.ak.b[5], (int)uv.ak.c[5], (int)uv.ak.d[5], (int)uv.ak.e[5], 0, (int)this.an[n2], this.ao[n2] - 50, 3);
            ++n2;
        }
    }

    private void d(Graphics graphics) {
        graphics.drawRegion(al, (int)uv.ak.b[this.ay[this.as]], (int)uv.ak.c[this.ay[this.as]], (int)uv.ak.d[this.ay[this.as]], (int)uv.ak.e[this.ay[this.as]], (int)this.az[this.as], this.cL + this.au[this.as], this.cM + this.av[this.as] - 50, 3);
    }
}

