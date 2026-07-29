/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import javax.microedition.lcdui.Image;

public final class vp {
    public int a;
    public int b;
    public int c;
    public int d;
    public Image e;
    public short f;
    long g;

    public final void a(byte[] object, int n2) {
        try {
            DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream((byte[])object));
            object = dataInputStream;
            Object object2 = new byte[dataInputStream.readShort()];
            ((DataInputStream)object).read((byte[])object2, 0, ((byte[])object2).length);
            this.e = Image.createImage((byte[])object2, (int)0, (int)((byte[])object2).length);
            object2 = new byte[((DataInputStream)object).readShort()];
            ((DataInputStream)object).read((byte[])object2, 0, ((byte[])object2).length);
            object2 = new DataInputStream(new ByteArrayInputStream((byte[])object2));
            this.a = ((DataInputStream)object2).readByte();
            this.b = ((DataInputStream)object2).readByte();
            ((DataInputStream)object2).readUnsignedByte();
            ((DataInputStream)object2).readUnsignedByte();
            ((DataInputStream)object2).readUnsignedByte();
            ((DataInputStream)object2).readUnsignedByte();
            this.c = this.e.getWidth();
            this.d = this.e.getHeight();
            ((FilterInputStream)object).close();
            ((FilterInputStream)object2).close();
            return;
        }
        catch (Exception exception) {
            System.out.println("LOI LOAD CAY TRONG TREEINFO " + n2);
            this.e = null;
            exception.printStackTrace();
            return;
        }
    }
}

