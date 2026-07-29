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

public final class il {
    private Vector b = new Vector();
    private Vector c = new Vector();
    private qw[] d;
    public byte[] a;
    private static byte[] e;

    static {
        byte[] byArray = new byte[9];
        byArray[2] = 1;
        byArray[3] = 2;
        byArray[4] = 3;
        byArray[5] = 1;
        byArray[6] = 1;
        byArray[7] = 1;
        byArray[8] = 1;
        e = byArray;
    }

    public il(byte[] object) {
        try {
            Object object2;
            int n2;
            DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream((byte[])object));
            object = dataInputStream;
            int n3 = dataInputStream.readByte();
            this.d = new qw[n3];
            int n4 = 0;
            while (n4 < n3) {
                this.d[n4] = new qw(((DataInputStream)object).readUnsignedByte(), ((DataInputStream)object).readUnsignedByte(), ((DataInputStream)object).readUnsignedByte(), ((DataInputStream)object).readUnsignedByte(), ((DataInputStream)object).readUnsignedByte());
                ++n4;
            }
            n4 = ((DataInputStream)object).readShort();
            n3 = 0;
            while (n3 < n4) {
                n2 = ((DataInputStream)object).readByte();
                Vector<hm> vector = new Vector<hm>();
                int n5 = 0;
                while (n5 < n2) {
                    object2 = new hm(((DataInputStream)object).readShort(), ((DataInputStream)object).readShort(), ((DataInputStream)object).readByte());
                    vector.addElement((hm)object2);
                    ++n5;
                }
                this.b.addElement(new bw(vector));
                ++n3;
            }
            n3 = ((DataInputStream)object).readShort();
            this.a = new byte[n3];
            n2 = 0;
            while (n2 < n3) {
                this.a[n2] = (byte)((DataInputStream)object).readShort();
                ++n2;
            }
            ((DataInputStream)object).readByte();
            ((DataInputStream)object).readByte();
            byte by2 = ((DataInputStream)object).readByte();
            n3 = by2;
            byte[] byArray = new byte[by2];
            ((DataInputStream)object).read(byArray);
            object2 = new ao(byArray);
            this.c.addElement(object2);
            byte by3 = ((DataInputStream)object).readByte();
            n3 = by3;
            byArray = new byte[by3];
            ((DataInputStream)object).read(byArray);
            object2 = new ao(byArray);
            this.c.addElement(object2);
            byte by4 = ((DataInputStream)object).readByte();
            n3 = by4;
            byArray = new byte[by4];
            ((DataInputStream)object).read(byArray);
            object2 = new ao(byArray);
            this.c.addElement(object2);
            byte by5 = ((DataInputStream)object).readByte();
            n3 = by5;
            byArray = new byte[by5];
            ((DataInputStream)object).read(byArray);
            object2 = new ao(byArray);
            this.c.addElement(object2);
            return;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return;
        }
    }

    public final ao a(int n2) {
        return (ao)this.c.elementAt(e[n2]);
    }

    public final int a(int n2, int n3) {
        ao ao2 = (ao)((il)((Object)ao2)).c.elementAt(e[n3]);
        if (n2 < ao2.a.length) {
            return ao2.a[n2];
        }
        return 0;
    }

    public final void a(Graphics graphics, int n2, int n3, int n4, int n5, Image image) {
        if (image == null) {
            return;
        }
        bw bw2 = (bw)this.b.elementAt(n2);
        try {
            int n6 = 0;
            while (n6 < bw2.a.size()) {
                hm hm2 = (hm)bw2.a.elementAt(n6);
                qw qw2 = this.d[hm2.c];
                int n7 = hm2.a;
                if (n5 == 2) {
                    n7 = -n7 - qw2.c;
                }
                graphics.drawRegion(image, (int)qw2.a, (int)qw2.b, (int)qw2.c, (int)qw2.d, n5, n3 + n7, n4 + hm2.b, 0);
                ++n6;
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

