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
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class bk {
    private Vector b = new Vector();
    private Vector c = new Vector();
    private qw[] d;
    private byte[] e;
    private short f;
    private short g;
    private String h = "";
    public byte a = 0;
    private static byte[][] i;

    static {
        byte[][] byArrayArray = new byte[3][];
        byte[] byArray = new byte[14];
        byArray[2] = 1;
        byArray[3] = 2;
        byArray[4] = 3;
        byArray[5] = 1;
        byArray[6] = 1;
        byArray[7] = 1;
        byArray[8] = 1;
        byArray[9] = 1;
        byArray[10] = 1;
        byArray[11] = 1;
        byArray[12] = 1;
        byArray[13] = 1;
        byArrayArray[0] = byArray;
        byArrayArray[1] = new byte[]{4, 4, 5, 6, 7, 5, 5, 5, 5, 5, 5, 5, 5, 5};
        byArrayArray[2] = new byte[]{8, 8, 9, 10, 11, 9, 9, 9, 9, 9, 9, 9, 9, 9};
        i = byArrayArray;
    }

    public bk(byte[] byArray, int n2) {
        this.a(byArray);
    }

    private void a(byte[] object) {
        int n2 = 0;
        FilterInputStream filterInputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        this.b.removeAllElements();
        this.c.removeAllElements();
        try {
            try {
                byteArrayInputStream = new ByteArrayInputStream((byte[])object);
                filterInputStream = new DataInputStream(byteArrayInputStream);
                int n3 = ((DataInputStream)filterInputStream).readByte();
                this.d = new qw[n3];
                n2 = 0;
                while (n2 < n3) {
                    this.d[n2] = new qw(((DataInputStream)filterInputStream).readUnsignedByte(), ((DataInputStream)filterInputStream).readUnsignedByte(), ((DataInputStream)filterInputStream).readUnsignedByte(), ((DataInputStream)filterInputStream).readUnsignedByte(), ((DataInputStream)filterInputStream).readUnsignedByte());
                    ++n2;
                }
                n2 = 0;
                n3 = -1000000;
                int n4 = ((DataInputStream)filterInputStream).readShort();
                int n5 = 0;
                while (n5 < n4) {
                    int n6 = ((DataInputStream)filterInputStream).readByte();
                    Vector<hm> vector = new Vector<hm>();
                    int n7 = 0;
                    while (n7 < n6) {
                        hm hm2 = new hm(((DataInputStream)filterInputStream).readShort(), ((DataInputStream)filterInputStream).readShort(), ((DataInputStream)filterInputStream).readByte());
                        new hm(((DataInputStream)filterInputStream).readShort(), ((DataInputStream)filterInputStream).readShort(), ((DataInputStream)filterInputStream).readByte()).d = ((DataInputStream)filterInputStream).readByte();
                        hm2.e = ((DataInputStream)filterInputStream).readByte();
                        vector.addElement(hm2);
                        if (n5 == 0) {
                            if (n3 < hm2.b + this.d[hm2.c].d) {
                                n3 = hm2.b + this.d[hm2.c].d;
                            }
                            if (n2 < yg.d(hm2.b)) {
                                n2 = yg.d(hm2.b);
                            }
                        }
                        ++n7;
                    }
                    if (n5 == 0 && n3 <= -5) {
                        this.a = (byte)n3;
                    }
                    this.b.addElement(new bw(vector, null));
                    ++n5;
                }
                this.f = this.d[0].c;
                this.g = (short)n2;
                n2 = ((DataInputStream)filterInputStream).readShort();
                this.e = new byte[n2];
                n5 = 0;
                while (n5 < n2) {
                    this.e[n5] = (byte)((DataInputStream)filterInputStream).readShort();
                    ++n5;
                }
                byte by2 = ((DataInputStream)filterInputStream).readByte();
                n2 = by2;
                byte[] byArray = new byte[by2];
                ((DataInputStream)filterInputStream).read(byArray);
                ao ao2 = new ao(byArray);
                this.c.addElement(ao2);
                byte by3 = ((DataInputStream)filterInputStream).readByte();
                n2 = by3;
                byArray = new byte[by3];
                ((DataInputStream)filterInputStream).read(byArray);
                ao2 = new ao(byArray);
                this.c.addElement(ao2);
                byte by4 = ((DataInputStream)filterInputStream).readByte();
                n2 = by4;
                byArray = new byte[by4];
                ((DataInputStream)filterInputStream).read(byArray);
                ao2 = new ao(byArray);
                this.c.addElement(ao2);
                byte by5 = ((DataInputStream)filterInputStream).readByte();
                n2 = by5;
                byArray = new byte[by5];
                ((DataInputStream)filterInputStream).read(byArray);
                ao2 = new ao(byArray);
                this.c.addElement(ao2);
                byte by6 = ((DataInputStream)filterInputStream).readByte();
                n2 = by6;
                byArray = new byte[by6];
                ((DataInputStream)filterInputStream).read(byArray);
                ao2 = new ao(byArray);
                this.c.addElement(ao2);
                byte by7 = ((DataInputStream)filterInputStream).readByte();
                n2 = by7;
                byArray = new byte[by7];
                ((DataInputStream)filterInputStream).read(byArray);
                ao2 = new ao(byArray);
                this.c.addElement(ao2);
                byte by8 = ((DataInputStream)filterInputStream).readByte();
                n2 = by8;
                byArray = new byte[by8];
                ((DataInputStream)filterInputStream).read(byArray);
                ao2 = new ao(byArray);
                this.c.addElement(ao2);
                byte by9 = ((DataInputStream)filterInputStream).readByte();
                n2 = by9;
                byArray = new byte[by9];
                ((DataInputStream)filterInputStream).read(byArray);
                ao2 = new ao(byArray);
                this.c.addElement(ao2);
                if (filterInputStream.available() > 0) {
                    ((DataInputStream)filterInputStream).readByte();
                    int n8 = 0;
                    while (n8 < n4) {
                        bw bw2 = (bw)this.b.elementAt(n8);
                        ((bw)this.b.elementAt(n8)).d = ((DataInputStream)filterInputStream).readByte();
                        bw2.e = ((DataInputStream)filterInputStream).readByte();
                        ++n8;
                    }
                }
                if (filterInputStream.available() > 0) {
                    byte by10 = ((DataInputStream)filterInputStream).readByte();
                    n2 = by10;
                    byArray = new byte[by10];
                    ((DataInputStream)filterInputStream).read(byArray);
                    ao2 = new ao(byArray);
                    this.c.addElement(ao2);
                    byte by11 = ((DataInputStream)filterInputStream).readByte();
                    n2 = by11;
                    byArray = new byte[by11];
                    ((DataInputStream)filterInputStream).read(byArray);
                    ao2 = new ao(byArray);
                    this.c.addElement(ao2);
                    byte by12 = ((DataInputStream)filterInputStream).readByte();
                    n2 = by12;
                    byArray = new byte[by12];
                    ((DataInputStream)filterInputStream).read(byArray);
                    ao2 = new ao(byArray);
                    this.c.addElement(ao2);
                    byte by13 = ((DataInputStream)filterInputStream).readByte();
                    n2 = by13;
                    byArray = new byte[by13];
                    ((DataInputStream)filterInputStream).read(byArray);
                    ao2 = new ao(byArray);
                    this.c.addElement(ao2);
                }
            }
            catch (Exception exception) {
                object = exception;
                exception.printStackTrace();
                try {
                    filterInputStream.close();
                }
                catch (Exception exception2) {}
                try {
                    byteArrayInputStream.close();
                    return;
                }
                catch (Exception exception3) {
                    return;
                }
            }
        }
        catch (Throwable throwable) {
            try {
                filterInputStream.close();
            }
            catch (Exception exception) {}
            try {
                byteArrayInputStream.close();
            }
            catch (Exception exception) {}
            throw throwable;
        }
        try {
            filterInputStream.close();
        }
        catch (Exception exception) {}
        try {
            byteArrayInputStream.close();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final ao a(int n2, int n3) {
        return (ao)this.c.elementAt(i[n3][n2]);
    }

    public final int a(int n2, int n3, int n4) {
        ao ao2 = (ao)((bk)((Object)ao2)).c.elementAt(i[n4][n3]);
        if (n2 < ao2.a.length) {
            return ao2.a[n2];
        }
        return 0;
    }

    public final byte[] a(int n2) {
        bw bw2 = (bw)((bk)((Object)bw2)).b.elementAt(n2);
        if (bw2 != null) {
            return new byte[]{bw2.d, bw2.e};
        }
        return new byte[2];
    }

    public final void a(Graphics graphics, int n2, int n3, int n4, int n5, Image image, int n6) {
        if (image == null) {
            return;
        }
        bw bw2 = (bw)this.b.elementAt(n2);
        if (bw2 != null) {
            bw2.getClass();
        }
        if (bw2.c == null) {
            return;
        }
        try {
            int n7 = 0;
            while (n7 < bw2.c.size()) {
                int n8;
                int n9;
                int n10;
                short s2;
                short s3;
                Image image2;
                Graphics graphics2;
                hm hm2 = (hm)bw2.c.elementAt(n7);
                qw qw2 = this.d[hm2.c];
                int n11 = hm2.a;
                int n12 = hm2.b;
                if (n12 < -40) {
                    n12 += n6;
                }
                int n13 = qw2.c;
                int n14 = qw2.d;
                short s4 = qw2.a;
                short s5 = qw2.b;
                if (s4 > image.getWidth()) {
                    s4 = 0;
                }
                if (s5 > image.getHeight()) {
                    s5 = 0;
                }
                if (s4 + n13 > image.getWidth()) {
                    n13 = image.getWidth() - s4;
                }
                if (s5 + n14 > image.getHeight()) {
                    n14 = image.getHeight() - s5;
                }
                if (n5 == 2) {
                    n11 = -n11 - n13;
                }
                if (hm2.d != 1) {
                    graphics2 = graphics;
                    image2 = image;
                    s3 = s4;
                    s2 = s5;
                    n10 = n13;
                    n9 = n14;
                    n8 = n5;
                } else {
                    graphics2 = graphics;
                    image2 = image;
                    s3 = s4;
                    s2 = s5;
                    n10 = n13;
                    n9 = n14;
                    n8 = n5 == 2 ? 0 : 2;
                }
                graphics2.drawRegion(image2, (int)s3, (int)s2, n10, n9, n8, n3 + n11, n4 + n12, 0);
                ++n7;
            }
            return;
        }
        catch (Exception exception) {
            System.out.println("loi dataeff: " + this.h);
            exception.printStackTrace();
            return;
        }
    }

    public final short a() {
        return this.f;
    }

    public final short b() {
        return this.g;
    }
}

