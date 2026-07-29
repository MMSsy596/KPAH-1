/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class gn
extends vh {
    public int a;
    public int b;
    private Image[] e;
    public String c;
    public byte d = 0;
    private static String[] f = new String[]{"D\u00ec \u00fat HP", "B\u00e0 t\u00e1m t\u1ea1p h\u00f3a", "H\u1eafc ng\u01b0u", "Thi\u1ebft b\u00ec", "L\u00ednh g\u00e1c", "Tr\u01b0\u1edfng l\u00e0ng", "Ph\u00fa \u00f4ng", "Xa phu", "\u00f4ng n\u1ed9i", "Anh b\u1ea3y", "Hoa ti\u00eau", "Nh\u1ea5t gi\u00e1p", "Nh\u1ecb gi\u00e1p", "Tam gi\u00e1p", "T\u1ee9 gi\u00e1p", "Ng\u0169 gi\u00e1p", "Nh\u1ea5t ng\u01b0u", "Nh\u1ecb ng\u01b0u", "Tam ng\u01b0u", "T\u1ee9 ng\u01b0u", "Ng\u0169 ng\u01b0u", "L\u00e2m t\u01b0\u1edbng qu\u00e2n", "Nh\u1eadt th\u01b0\u01a1ng nh\u00e2n", "H\u1ecfa x\u00edch", "B\u1ea3o ng\u1ecdc", "Tr\u1ea7n th\u1ed1ng l\u0129nh", "Kim hoa", "Gi\u00e1p S\u01b0", "Ki\u1ebfm S\u01b0", "B\u1ed9i Ch\u00e2u", "An T\u00e2m", "L\u1ed9c Ph\u00e1t"};
    private static byte[] g;
    private int h = 0;
    private int i = 0;
    private static byte[] j;
    private static byte[] k;

    static {
        byte[] byArray = new byte[32];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 3;
        byArray[4] = 4;
        byArray[5] = 5;
        byArray[6] = 6;
        byArray[7] = 7;
        byArray[8] = 8;
        byArray[9] = 9;
        byArray[10] = 10;
        byArray[11] = 11;
        byArray[12] = 11;
        byArray[13] = 11;
        byArray[14] = 11;
        byArray[15] = 11;
        byArray[16] = 12;
        byArray[17] = 12;
        byArray[18] = 12;
        byArray[19] = 12;
        byArray[20] = 12;
        byArray[21] = 14;
        byArray[22] = 13;
        byArray[23] = 15;
        byArray[24] = 16;
        byArray[25] = 17;
        byArray[26] = 1;
        byArray[27] = 3;
        byArray[28] = 2;
        byArray[29] = 6;
        byArray[30] = 9;
        byArray[31] = 13;
        g = byArray;
        byte[] byArray2 = new byte[18];
        byArray2[2] = 2;
        byArray2[4] = 1;
        byArray2[7] = 2;
        j = byArray2;
        byte[] byArray3 = new byte[18];
        byArray3[0] = -7;
        byArray3[1] = -4;
        byArray3[2] = -5;
        byArray3[3] = -5;
        byArray3[4] = -5;
        byArray3[5] = -9;
        byArray3[6] = -9;
        byArray3[7] = -10;
        byArray3[8] = -7;
        byArray3[10] = -11;
        byArray3[11] = -10;
        byArray3[12] = -9;
        byArray3[13] = -10;
        byArray3[15] = -5;
        byArray3[16] = -9;
        byArray3[17] = -5;
        k = byArray3;
    }

    public gn() {
    }

    public gn(int n2, int n3, int n4, acf acf2) {
        this.cG = (byte)2;
        this.cL = (short)((n2 << 4) + 8);
        this.cM = (short)((n3 << 4) + 8);
        this.cN = (short)30;
        this.cO = (short)40;
        this.a = n4;
        this.e = new Image[2];
        this.e[0] = acf2.d("npc" + g[n4] + "0" + ".png");
        this.e[1] = acf2.d("npc" + g[n4] + "1" + ".png");
        if (n4 == 4) {
            this.b = ++ls.l;
        }
    }

    public final String a() {
        if (this.c != null) {
            return this.c;
        }
        return f[this.a];
    }

    public void a(Graphics graphics) {
        Image image;
        graphics.drawImage(this.e[1], (int)this.cL, (int)this.cM, 33);
        if (this.e[0] != null) {
            graphics.drawImage(this.e[0], this.cL + j[g[this.a]], this.cM + k[g[this.a]] + this.i / 5, 33);
        }
        if (!(image = abj.e(this.a)).equals(yi.l)) {
            graphics.drawImage(image, (int)this.cL, this.cM - this.cN - 5, 33);
        }
    }

    public void a(Graphics graphics, int n2, int n3) {
        graphics.setClip(n2 - 10, 32 - this.cN, 20, 22);
        if (this.e[0] != null) {
            graphics.drawImage(this.e[0], n2, 32 - this.cN, 17);
            return;
        }
        if (this.e[1] != null) {
            graphics.drawImage(this.e[1], n2, 32 - this.cN, 17);
        }
    }

    public void a(short s2, short s3) {
        this.cL = s2;
        this.cM = s3;
    }

    public void b() {
        super.b();
        if (this.db != null && this != acv.s.u) {
            this.db = null;
        }
        if (this.e[0] != null) {
            ++this.i;
            if (this.i > 9) {
                this.i = 0;
            }
        }
        if (acv.s.W != null && yg.d(this.cL / 16 - acv.s.W.a / 16) <= 1 && yg.d(this.cM / 16 - acv.s.W.b / 16) <= 1) {
            if (this.h > -2) {
                --this.h;
                return;
            }
            this.h = 0;
        }
    }

    public boolean g_() {
        return true;
    }

    public int f() {
        return this.d;
    }

    public final int g() {
        return this.a;
    }
}

