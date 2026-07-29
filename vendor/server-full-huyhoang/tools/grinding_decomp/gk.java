/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class gk {
    private Image[] f = new Image[1];
    public byte[] a;
    public byte[] b;
    public byte[] c;
    public byte[] d;
    public byte[] e;
    private short[] g;
    private short[] h;

    public gk() {
    }

    public final void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7) {
        graphics.drawRegion(this.f[0], (int)this.b[n4], (int)this.c[n4], (int)this.d[n4], (int)this.e[n4], n5, n2 + this.g[n4] * n6, n3 + this.h[n4], 3);
    }

    public gk(int n2) {
        try {
            Object object = acf.a.c(String.valueOf(n2) + "_h");
            byte[] byArray = acf.a.c("data");
            this.f[0] = yi.a((byte[])object, byArray);
            object = acf.a.c("pos");
            object = new ByteArrayInputStream((byte[])object);
            DataInputStream dataInputStream = new DataInputStream((InputStream)object);
            object = dataInputStream;
            int n3 = dataInputStream.readByte();
            this.a = new byte[n3];
            this.b = new byte[n3];
            this.c = new byte[n3];
            this.d = new byte[n3];
            this.e = new byte[n3];
            this.g = new short[n3];
            this.h = new short[n3];
            int n4 = 0;
            while (n4 < n3) {
                this.a[n4] = ((DataInputStream)object).readByte();
                this.b[n4] = ((DataInputStream)object).readByte();
                this.c[n4] = ((DataInputStream)object).readByte();
                this.d[n4] = ((DataInputStream)object).readByte();
                this.e[n4] = ((DataInputStream)object).readByte();
                this.g[n4] = ((DataInputStream)object).readByte();
                this.h[n4] = ((DataInputStream)object).readByte();
                ++n4;
            }
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public gk(byte[] object) {
        try {
            object = new DataInputStream(new ByteArrayInputStream((byte[])object));
            this.f = new Image[((DataInputStream)object).readByte()];
            int n2 = 0;
            while (n2 < this.f.length) {
                short s2 = ((DataInputStream)object).readShort();
                byte[] byArray = new byte[s2];
                ((DataInputStream)object).read(byArray);
                this.f[n2] = Image.createImage((byte[])byArray, (int)0, (int)s2);
                ++n2;
            }
            return;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return;
        }
    }

    public final Image a(int n2) {
        if (this.f == null) {
            return null;
        }
        return this.f[n2];
    }
}

