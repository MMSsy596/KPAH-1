/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class gw {
    public Vector a = new Vector();
    private qw[] i;
    private byte[][] j;
    private byte[] k;
    private byte l;
    private byte m;
    public boolean b;
    private byte n;
    public int c;
    public long d;
    public boolean e;
    private byte o;
    public static Hashtable f = new Hashtable();
    public byte g;
    private long p;
    public long h;

    public gw(int n2, long l2, boolean bl2, boolean bl3, boolean bl4, int n3, byte by2) {
        new Vector();
        this.j = new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]};
        this.c = 0;
        this.o = 0;
        this.h = l2;
        this.c = (short)n2;
        this.g = by2;
        this.o = l2 == -1L ? (byte)3 : (l2 == 0L ? (byte)1 : (byte)2);
        this.e = bl3;
        hm hm2 = (hm)f.get(String.valueOf(n2));
        if (hm2 != null) {
            this.a(hm2.f);
        }
    }

    public gw(int n2, long l2) {
        new Vector();
        this.j = new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]};
        this.c = 0;
        this.o = 0;
        this.h = l2;
        this.c = (short)n2;
        this.o = l2 == -1L ? (byte)3 : (l2 == 0L ? (byte)1 : (byte)2);
        hm hm2 = (hm)f.get(String.valueOf(n2));
        if (hm2 != null) {
            this.a(hm2.f);
        }
    }

    public final void a(byte by2) {
        this.n = by2;
    }

    public gw(int n2) {
        new Vector();
        this.j = new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]};
        this.c = 0;
        this.o = 0;
        this.c = n2;
        hm hm2 = (hm)f.get(String.valueOf(n2));
        if (hm2 != null) {
            this.a(hm2.f);
        }
    }

    private void a(byte[] object) {
        try {
            int n2;
            DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream((byte[])object));
            object = dataInputStream;
            int n3 = dataInputStream.readByte();
            this.i = new qw[n3];
            int n4 = 0;
            while (n4 < n3) {
                this.i[n4] = new qw(((DataInputStream)object).readUnsignedByte(), ((DataInputStream)object).readUnsignedByte(), ((DataInputStream)object).readUnsignedByte(), ((DataInputStream)object).readUnsignedByte(), ((DataInputStream)object).readUnsignedByte());
                ++n4;
            }
            n4 = ((DataInputStream)object).readShort();
            n3 = 0;
            while (n3 < n4) {
                n2 = ((DataInputStream)object).readByte();
                Vector<hm> vector = new Vector<hm>();
                Vector<hm> vector2 = new Vector<hm>();
                int n5 = 0;
                while (n5 < n2) {
                    hm hm2 = new hm(((DataInputStream)object).readShort(), ((DataInputStream)object).readShort(), ((DataInputStream)object).readByte());
                    new hm(((DataInputStream)object).readShort(), ((DataInputStream)object).readShort(), ((DataInputStream)object).readByte()).d = ((DataInputStream)object).readByte();
                    hm2.e = ((DataInputStream)object).readByte();
                    if (hm2.e == 0) {
                        vector.addElement(hm2);
                    } else {
                        vector2.addElement(hm2);
                    }
                    ++n5;
                }
                this.a.addElement(new bw(vector, vector2));
                ++n3;
            }
            n3 = (short)((DataInputStream)object).readUnsignedByte();
            this.k = new byte[n3];
            n2 = 0;
            while (n2 < n3) {
                this.k[n2] = (byte)((DataInputStream)object).readShort();
                ++n2;
            }
            ((DataInputStream)object).readByte();
            n3 = ((DataInputStream)object).readByte();
            this.j[0] = new byte[n3];
            n2 = 0;
            while (n2 < n3) {
                this.j[0][n2] = ((DataInputStream)object).readByte();
                ++n2;
            }
            n3 = ((DataInputStream)object).readByte();
            this.j[1] = new byte[n3];
            n2 = 0;
            while (n2 < n3) {
                this.j[1][n2] = ((DataInputStream)object).readByte();
                ++n2;
            }
            n3 = ((DataInputStream)object).readByte();
            this.j[3] = new byte[n3];
            n2 = 0;
            while (n2 < n3) {
                this.j[3][n2] = ((DataInputStream)object).readByte();
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

    public final void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        if (n4 < this.a.size()) {
            Object object = (bw)this.a.elementAt(n4);
            try {
                object = ((bw)object).b;
                int n6 = 0;
                while (n6 < ((Vector)object).size()) {
                    hm hm2 = (hm)((Vector)object).elementAt(n6);
                    qw qw2 = this.i[hm2.c];
                    dh dh2 = ko.a((short)this.c);
                    if (dh2 != null && dh2.a != null) {
                        int n7 = hm2.a;
                        int n8 = qw2.c;
                        int n9 = qw2.d;
                        short s2 = qw2.a;
                        short s3 = qw2.b;
                        int n10 = dh2.a.getWidth();
                        int n11 = dh2.a.getHeight();
                        if (s2 > n10) {
                            s2 = 0;
                        }
                        if (s3 > n11) {
                            s3 = 0;
                        }
                        if (s2 + n8 > n10) {
                            n8 = n10 - s2;
                        }
                        if (s3 + n9 > n11) {
                            n9 = n11 - s3;
                        }
                        int n12 = n10 = hm2.d == 1 ? 2 : 0;
                        if (n5 == 2 || n5 == 6) {
                            n10 = n10 == 2 ? 0 : 2;
                            n7 = -(n7 + n8);
                        }
                        graphics.drawRegion(dh2.a, (int)s2, (int)s3, n8, n9, n10, n2 + n7, n3 + hm2.b, 0);
                    }
                    ++n6;
                }
                return;
            }
            catch (Exception exception) {
                object = exception;
                exception.printStackTrace();
            }
        }
    }

    public final void a(Graphics graphics) {
        if (this.l < this.a.size()) {
            Object object = (bw)this.a.elementAt(this.l);
            try {
                object = ((bw)object).b;
                int n2 = 0;
                while (n2 < ((Vector)object).size()) {
                    hm hm2 = (hm)((Vector)object).elementAt(n2);
                    qw qw2 = this.i[hm2.c];
                    dh dh2 = ko.a((short)this.c);
                    if (dh2 != null && dh2.a != null) {
                        short s2 = hm2.a;
                        int n3 = qw2.c;
                        int n4 = qw2.d;
                        short s3 = qw2.a;
                        short s4 = qw2.b;
                        int n5 = dh2.a.getWidth();
                        int n6 = dh2.a.getHeight();
                        if (s3 > n5) {
                            s3 = 0;
                        }
                        if (s4 > n6) {
                            s4 = 0;
                        }
                        if (s3 + n3 > n5) {
                            n3 = n5 - s3;
                        }
                        if (s4 + n4 > n6) {
                            n4 = n6 - s4;
                        }
                        n5 = hm2.d == 1 ? 2 : 0;
                        graphics.drawRegion(dh2.a, (int)s3, (int)s4, n3, n4, n5, s2 + 0, 0 + hm2.b, 0);
                    }
                    ++n2;
                }
                return;
            }
            catch (Exception exception) {
                object = exception;
                exception.printStackTrace();
            }
        }
    }

    public final void b(Graphics graphics) {
        if (this.l < this.a.size()) {
            Object object = (bw)this.a.elementAt(this.l);
            try {
                object = ((bw)object).c;
                int n2 = 0;
                while (n2 < ((Vector)object).size()) {
                    hm hm2 = (hm)((Vector)object).elementAt(n2);
                    qw qw2 = this.i[hm2.c];
                    dh dh2 = ko.a((short)this.c);
                    if (dh2 != null && dh2.a != null) {
                        short s2 = hm2.a;
                        int n3 = qw2.c;
                        int n4 = qw2.d;
                        short s3 = qw2.a;
                        short s4 = qw2.b;
                        int n5 = dh2.a.getWidth();
                        int n6 = dh2.a.getHeight();
                        if (s3 > n5) {
                            s3 = 0;
                        }
                        if (s4 > n6) {
                            s4 = 0;
                        }
                        if (s3 + n3 > n5) {
                            n3 = n5 - s3;
                        }
                        if (s4 + n4 > n6) {
                            n4 = n6 - s4;
                        }
                        n5 = hm2.d == 1 ? 2 : 0;
                        graphics.drawRegion(dh2.a, (int)s3, (int)s4, n3, n4, n5, s2 + 0, 0 + hm2.b, 0);
                    }
                    ++n2;
                }
                return;
            }
            catch (Exception exception) {
                object = exception;
                exception.printStackTrace();
            }
        }
    }

    public final void b(Graphics graphics, int n2, int n3, int n4, int n5) {
        if (n4 < this.a.size()) {
            Object object = (bw)this.a.elementAt(n4);
            try {
                object = ((bw)object).c;
                int n6 = 0;
                while (n6 < ((Vector)object).size()) {
                    hm hm2 = (hm)((Vector)object).elementAt(n6);
                    qw qw2 = this.i[hm2.c];
                    dh dh2 = ko.a((short)this.c);
                    if (dh2 != null && dh2.a != null) {
                        int n7 = hm2.a;
                        int n8 = qw2.c;
                        int n9 = qw2.d;
                        short s2 = qw2.a;
                        short s3 = qw2.b;
                        int n10 = dh2.a.getWidth();
                        int n11 = dh2.a.getHeight();
                        if (s2 > n10) {
                            s2 = 0;
                        }
                        if (s3 > n11) {
                            s3 = 0;
                        }
                        if (s2 + n8 > n10) {
                            n8 = n10 - s2;
                        }
                        if (s3 + n9 > n11) {
                            n9 = n11 - s3;
                        }
                        int n12 = n10 = hm2.d == 1 ? 2 : 0;
                        if (n5 == 2 || n5 == 6) {
                            n10 = n10 == 2 ? 0 : 2;
                            n7 = -(n7 + n8);
                        }
                        graphics.drawRegion(dh2.a, (int)s2, (int)s3, n8, n9, n10, n2 + n7, n3 + hm2.b, 0);
                    }
                    ++n6;
                }
                return;
            }
            catch (Exception exception) {
                object = exception;
                exception.printStackTrace();
            }
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.l < this.a.size()) {
            bw bw2 = (bw)this.a.elementAt(this.l);
            try {
                Vector vector = null;
                vector = bw2.b;
                int n4 = 0;
                while (n4 < vector.size()) {
                    hm hm2 = (hm)vector.elementAt(n4);
                    qw qw2 = this.i[hm2.c];
                    dh dh2 = ko.a((short)this.c);
                    if (dh2 != null && dh2.a != null) {
                        short s2 = hm2.a;
                        int n5 = qw2.c;
                        int n6 = qw2.d;
                        short s3 = qw2.a;
                        short s4 = qw2.b;
                        int n7 = dh2.a.getWidth();
                        int n8 = dh2.a.getHeight();
                        if (s3 > n7) {
                            s3 = 0;
                        }
                        if (s4 > n8) {
                            s4 = 0;
                        }
                        if (s3 + n5 > n7) {
                            n5 = n7 - s3;
                        }
                        if (s4 + n6 > n8) {
                            n6 = n8 - s4;
                        }
                        graphics.drawRegion(dh2.a, (int)s3, (int)s4, n5, n6, hm2.d == 1 ? 2 : 0, n2 + s2, n3 + hm2.b, 0);
                    }
                    ++n4;
                }
                return;
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
        }
    }

    public final void b(Graphics graphics, int n2, int n3) {
        if (this.l < this.a.size()) {
            Object object = (bw)this.a.elementAt(this.l);
            try {
                object = ((bw)object).c;
                int n4 = 0;
                while (n4 < ((Vector)object).size()) {
                    hm hm2 = (hm)((Vector)object).elementAt(n4);
                    qw qw2 = this.i[hm2.c];
                    dh dh2 = ko.a((short)this.c);
                    if (dh2 != null && dh2.a != null) {
                        short s2 = hm2.a;
                        int n5 = qw2.c;
                        int n6 = qw2.d;
                        short s3 = qw2.a;
                        short s4 = qw2.b;
                        int n7 = dh2.a.getWidth();
                        int n8 = dh2.a.getHeight();
                        if (s3 > n7) {
                            s3 = 0;
                        }
                        if (s4 > n8) {
                            s4 = 0;
                        }
                        if (s3 + n5 > n7) {
                            n5 = n7 - s3;
                        }
                        if (s4 + n6 > n8) {
                            n6 = n8 - s4;
                        }
                        graphics.drawRegion(dh2.a, (int)s3, (int)s4, n5, n6, hm2.d == 1 ? 2 : 0, n2 + s2, n3 + hm2.b, 0);
                    }
                    ++n4;
                }
                return;
            }
            catch (Exception exception) {
                object = exception;
                exception.printStackTrace();
            }
        }
    }

    public final void a() {
        try {
            switch (this.o) {
                case 0: 
                case 1: {
                    this.m = (byte)(this.m + 1);
                    if (this.m < this.k.length) {
                        this.l = this.k[this.m];
                    }
                    if (this.m >= this.k.length) {
                        this.m = 0;
                        this.b = true;
                        return;
                    }
                    break;
                }
                case 2: {
                    this.m = (byte)(this.m + 1);
                    if (this.m > this.k.length - 1) {
                        this.m = 0;
                    }
                    if (this.m < this.k.length) {
                        this.l = this.k[this.m];
                    }
                    if (this.h - System.currentTimeMillis() < 0L) {
                        this.b = true;
                        return;
                    }
                    break;
                }
                case 3: {
                    this.m = (byte)(this.m + 1);
                    if (this.m < this.k.length) {
                        this.l = this.k[this.m];
                    }
                    if (this.m <= this.k.length) break;
                    if (this.n > 0) {
                        if (System.currentTimeMillis() - this.p > (long)(this.n * 1000)) {
                            this.m = 0;
                            this.p = System.currentTimeMillis();
                            return;
                        }
                        break;
                    }
                    this.m = 0;
                    return;
                }
                default: {
                    return;
                }
            }
        }
        catch (Exception exception) {}
    }
}

