/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class zz {
    private Vector a = new Vector();
    private Vector b = new Vector();
    private qw[] c;
    private byte[] d;
    private String e = "";
    private static byte[][] f;

    static {
        byte[][] byArrayArray = new byte[2][];
        byte[] byArray = new byte[13];
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
        byArrayArray[0] = byArray;
        byArrayArray[1] = new byte[]{4, 4, 5, 6, 7, 5, 5, 5, 5, 5, 5, 5, 5};
        f = byArrayArray;
    }

    public zz(byte[] byArray) {
        this.a(byArray);
    }

    private void a(byte[] object) {
        int n2 = 0;
        try {
            DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream((byte[])object));
            object = dataInputStream;
            n2 = dataInputStream.readByte();
            this.c = new qw[n2];
            int n3 = 0;
            while (n3 < n2) {
                this.c[n3] = new qw(((DataInputStream)object).readUnsignedByte(), ((DataInputStream)object).readUnsignedByte(), ((DataInputStream)object).readUnsignedByte(), ((DataInputStream)object).readUnsignedByte(), ((DataInputStream)object).readUnsignedByte());
                ++n3;
            }
            n2 = ((DataInputStream)object).readShort();
            n3 = 0;
            while (n3 < n2) {
                int n4 = ((DataInputStream)object).readByte();
                Vector<hm> vector = new Vector<hm>();
                Vector<hm> vector2 = new Vector<hm>();
                int n5 = 0;
                while (n5 < n4) {
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
            n2 = ((DataInputStream)object).readShort();
            this.d = new byte[n2];
            n3 = 0;
            while (n3 < n2) {
                this.d[n3] = (byte)((DataInputStream)object).readShort();
                ++n3;
            }
            byte by2 = ((DataInputStream)object).readByte();
            n2 = by2;
            byte[] byArray = new byte[by2];
            ((DataInputStream)object).read(byArray);
            ao ao2 = new ao(byArray);
            this.b.addElement(ao2);
            byte by3 = ((DataInputStream)object).readByte();
            n2 = by3;
            byArray = new byte[by3];
            ((DataInputStream)object).read(byArray);
            ao2 = new ao(byArray);
            this.b.addElement(ao2);
            byte by4 = ((DataInputStream)object).readByte();
            n2 = by4;
            byArray = new byte[by4];
            ((DataInputStream)object).read(byArray);
            ao2 = new ao(byArray);
            this.b.addElement(ao2);
            byte by5 = ((DataInputStream)object).readByte();
            n2 = by5;
            byArray = new byte[by5];
            ((DataInputStream)object).read(byArray);
            ao2 = new ao(byArray);
            this.b.addElement(ao2);
            byte by6 = ((DataInputStream)object).readByte();
            n2 = by6;
            byArray = new byte[by6];
            ((DataInputStream)object).read(byArray);
            ao2 = new ao(byArray);
            this.b.addElement(ao2);
            byte by7 = ((DataInputStream)object).readByte();
            n2 = by7;
            byArray = new byte[by7];
            ((DataInputStream)object).read(byArray);
            ao2 = new ao(byArray);
            this.b.addElement(ao2);
            byte by8 = ((DataInputStream)object).readByte();
            n2 = by8;
            byArray = new byte[by8];
            ((DataInputStream)object).read(byArray);
            ao2 = new ao(byArray);
            this.b.addElement(ao2);
            byte by9 = ((DataInputStream)object).readByte();
            n2 = by9;
            byArray = new byte[by9];
            ((DataInputStream)object).read(byArray);
            ao2 = new ao(byArray);
            this.b.addElement(ao2);
            return;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return;
        }
    }

    public final ao a(int n2, int n3) {
        return (ao)this.b.elementAt(f[n3][n2]);
    }

    public final int a(int n2, int n3, int n4) {
        ao ao2 = (ao)((zz)((Object)ao2)).b.elementAt(f[n4][n3]);
        if (n2 < ao2.a.length) {
            return ao2.a[n2];
        }
        return 0;
    }

    public final void a(Graphics graphics, int n2, int n3, int n4, int n5, Image image) {
        if (image == null) {
            return;
        }
        Object object = (bw)this.a.elementAt(n2);
        try {
            Vector vector = new Vector();
            int n6 = 0;
            while (n6 < ((bw)object).b.size()) {
                vector.addElement(((bw)object).b.elementAt(n6));
                ++n6;
            }
            n6 = 0;
            while (n6 < ((bw)object).a.size()) {
                vector.addElement(((bw)object).a.elementAt(n6));
                ++n6;
            }
            object = vector;
            int n7 = 0;
            while (n7 < ((Vector)object).size()) {
                int n8;
                int n9;
                int n10;
                short s2;
                short s3;
                Image image2;
                Graphics graphics2;
                hm hm2 = (hm)((Vector)object).elementAt(n7);
                qw qw2 = this.c[hm2.c];
                int n11 = hm2.a;
                int n12 = qw2.c;
                int n13 = qw2.d;
                short s4 = qw2.a;
                short s5 = qw2.b;
                if (s4 > image.getWidth()) {
                    s4 = 0;
                }
                if (s5 > image.getHeight()) {
                    s5 = 0;
                }
                if (s4 + n12 > image.getWidth()) {
                    n12 = image.getWidth() - s4;
                }
                if (s5 + n13 > image.getHeight()) {
                    n13 = image.getHeight() - s5;
                }
                if (n5 == 2) {
                    n11 = -n11 - n12;
                }
                if (hm2.d != 1) {
                    graphics2 = graphics;
                    image2 = image;
                    s3 = s4;
                    s2 = s5;
                    n10 = n12;
                    n9 = n13;
                    n8 = n5;
                } else {
                    graphics2 = graphics;
                    image2 = image;
                    s3 = s4;
                    s2 = s5;
                    n10 = n12;
                    n9 = n13;
                    n8 = n5 == 2 ? 0 : 2;
                }
                graphics2.drawRegion(image2, (int)s3, (int)s2, n10, n9, n8, n3 + n11, n4 + hm2.b, 0);
                ++n7;
            }
            return;
        }
        catch (Exception exception) {
            System.out.println("loi dataeff: " + this.e);
            exception.printStackTrace();
            return;
        }
    }
}

