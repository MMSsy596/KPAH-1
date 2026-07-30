/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class ace {
    private static Hashtable q = new Hashtable();
    private int r = -1;
    public Image a;
    byte b;
    byte c = 1;
    byte d;
    public byte e;
    public byte f;
    public byte g;
    public byte h;
    public byte i;
    public byte j;
    public byte k = 0;
    public String l;
    public byte m;
    public byte n;
    public boolean o = false;
    public gk p;
    private Vector s = new Vector();
    private byte[] t = null;

    public final void a(int n2, String string, byte by2, byte by3, byte by4, byte by5, byte by6, byte by7, byte by8, byte by9, byte by10) {
        this.r = n2;
        this.l = string;
        this.b = by2;
        this.c = by3;
        this.d = by4;
        this.e = by5;
        this.f = by6;
        this.g = by7;
        this.h = by8;
        this.i = by9;
        this.j = by10;
    }

    public final void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        if (this.a != null) {
            try {
                graphics.drawRegion(this.a, 0, n6 * this.f, (int)this.e, (int)this.f, 0, n2 - this.g, n3 - this.h, 0);
            }
            catch (Exception exception) {}
        }
        System.currentTimeMillis();
    }

    public final Vector a() {
        try {
            if (this.r > -1) {
                Object object = (Vector)q.get("" + this.n);
                if (object != null) {
                    this.s = object;
                } else {
                    object = null;
                    if (this.t != null) {
                        object = new DataInputStream(new ByteArrayInputStream(this.t));
                    } else {
                        InputStream inputStream = "".getClass().getResourceAsStream("/boss/" + this.n);
                        object = new DataInputStream(inputStream);
                    }
                    int n2 = ((DataInputStream)object).readByte();
                    int n3 = 0;
                    while (n3 < n2) {
                        short s2 = ((DataInputStream)object).readShort();
                        byte[] byArray = new byte[s2];
                        ((DataInputStream)object).read(byArray, 0, byArray.length);
                        this.s.addElement(new il(byArray));
                        ++n3;
                    }
                    q.put("" + this.n, this.s);
                    ((FilterInputStream)object).close();
                }
            }
            System.currentTimeMillis();
        }
        catch (Exception exception) {}
        return this.s;
    }

    public final void b() {
        if (!this.o && this.r > -1) {
            byte by2 = this.n;
            byte by3 = this.m;
            int n2 = this.r;
            go go2 = go.a();
            abs abs2 = new abs(-47);
            try {
                abs2.c().writeByte(by3);
                abs2.c().writeByte(by2);
                abs2.c().writeShort(n2);
            }
            catch (Exception exception) {}
            go2.a.a(abs2);
            this.o = true;
        }
    }

    public final void a(byte[] object) {
        try {
            if (this.m >= 70 && this.m <= 88 || this.m == 90 || this.m == 91 || this.m == 92 || this.k == 1) {
                this.p = new gk((byte[])object);
            } else {
                acf.a(object);
                if (this.m == 39 || this.m == 40) {
                    this.p = new gk(this.n);
                } else {
                    object = acf.a.c(String.valueOf(this.n) + "_h");
                    byte[] byArray = acf.a.c("data");
                    this.a = yi.a(object, byArray);
                }
                acf.a();
            }
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
        }
        this.o = false;
    }

    public final Image a(int n2) {
        if (this.p == null) {
            return null;
        }
        return this.p.a(n2);
    }

    public final void b(byte[] object) {
        try {
            this.t = object;
            Vector vector = (Vector)q.get("" + this.n);
            if (vector != null) {
                this.s = vector;
                return;
            }
            DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream((byte[])object));
            object = dataInputStream;
            int n2 = dataInputStream.readByte();
            int n3 = 0;
            while (n3 < n2) {
                short s2 = ((DataInputStream)object).readShort();
                byte[] byArray = new byte[s2];
                ((DataInputStream)object).read(byArray, 0, byArray.length);
                this.s.addElement(new il(byArray));
                ++n3;
            }
            q.put("" + this.n, this.s);
            ((FilterInputStream)object).close();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }
}

