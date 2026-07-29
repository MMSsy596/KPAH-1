/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Image;

public final class abt {
    public byte[] a;
    public ad[] b;
    public Image c;
    public uq[] d;
    public short e;

    public final void a(abs abs2) {
        try {
            int n2;
            short s2 = abs2.b().readShort();
            byte[] byArray = new byte[s2];
            abs2.b().read(byArray);
            this.c = yi.b(byArray);
            int n3 = abs2.b().readByte();
            this.b = new ad[n3];
            int n4 = 0;
            while (n4 < n3) {
                this.b[n4] = new ad();
                this.b[n4].c = abs2.b().readByte();
                this.b[n4].d = abs2.b().readByte();
                this.b[n4].e = abs2.b().readByte();
                this.b[n4].a = abs2.b().readByte();
                this.b[n4].b = abs2.b().readByte();
                ++n4;
            }
            n4 = abs2.b().readByte();
            this.d = new uq[n4];
            n3 = 0;
            while (n3 < n4) {
                this.d[n3] = new uq();
                n2 = abs2.b().readByte();
                this.d[n3].a = new byte[n2];
                this.d[n3].b = new byte[n2];
                this.d[n3].c = new byte[n2];
                int n5 = 0;
                while (n5 < n2) {
                    this.d[n3].a[n5] = abs2.b().readByte();
                    this.d[n3].b[n5] = abs2.b().readByte();
                    this.d[n3].c[n5] = abs2.b().readByte();
                    ++n5;
                }
                ++n3;
            }
            n3 = abs2.b().readByte();
            this.a = new byte[n3];
            n2 = 0;
            while (n2 < n3) {
                this.a[n2] = abs2.b().readByte();
                ++n2;
            }
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }
}

