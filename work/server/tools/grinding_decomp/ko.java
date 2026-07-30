/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 *  javax.microedition.rms.RecordStore
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.rms.RecordStore;

public final class ko {
    private static ko d;
    public static aab a;
    public static Vector b;
    public static Hashtable c;
    private boolean e = false;
    private static byte f;
    private static Image g;

    static {
        b = new Vector();
        c = new Hashtable();
        f = 0;
        try {
            if (g == null) {
                g = Image.createImage((String)"/waiting.png");
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
    }

    public static ko a() {
        if (d == null) {
            d = new ko();
            return d;
        }
        return d;
    }

    public final void b() {
        if (!this.e) {
            Object object;
            try {
                RecordStore.deleteRecordStore((String)"nqshImgPotion");
            }
            catch (Exception exception) {
                // empty catch block
            }
            Object object2 = aai.a("nqshImgPotionNew");
            int n2 = 0;
            if (object2 == null) {
                n2 = 0;
            } else {
                object2 = new ByteArrayInputStream((byte[])object2);
                object2 = new DataInputStream((InputStream)object2);
                object = null;
                object = null;
                try {
                    short s2 = ((DataInputStream)object2).readShort();
                    object = new byte[s2];
                    ((DataInputStream)object2).read((byte[])object);
                    int n3 = ((DataInputStream)object2).readByte();
                    int n4 = 0;
                    while (n4 < n3) {
                        short s3 = ((DataInputStream)object2).readShort();
                        byte[] byArray = new byte[s3];
                        ((DataInputStream)object2).read(byArray);
                        int n5 = ((DataInputStream)object2).readByte();
                        object = new byte[n5][];
                        int n6 = 0;
                        while (n6 < n5) {
                            short s4 = ((DataInputStream)object2).readShort();
                            object[n6] = new byte[s4];
                            ((DataInputStream)object2).read((byte[])object[n6]);
                            ++n6;
                        }
                        ko.a(byArray, (byte[][])object);
                        ++n4;
                    }
                    n2 = ((DataInputStream)object2).readByte();
                    ((FilterInputStream)object2).close();
                }
                catch (IOException iOException) {
                    object = iOException;
                    iOException.printStackTrace();
                }
            }
            go.a().b((byte)2, n2);
            object2 = aai.a("gemItem");
            n2 = 0;
            if (object2 == null) {
                n2 = 0;
            } else {
                object2 = new ByteArrayInputStream((byte[])object2);
                object2 = new DataInputStream((InputStream)object2);
                object = null;
                try {
                    n2 = ((DataInputStream)object2).readByte();
                    short s5 = ((DataInputStream)object2).readShort();
                    byte[] byArray = new byte[s5];
                    ((DataInputStream)object2).read(byArray);
                    ((FilterInputStream)object2).close();
                }
                catch (IOException iOException) {
                    object = iOException;
                    iOException.printStackTrace();
                }
            }
            go.a().b((byte)3, n2);
            this.e = true;
        }
    }

    private static void a(byte[] byArray, byte[][] byArray2) {
        int n2 = 0;
        while (n2 < byArray2.length) {
            b.addElement(yi.a(byArray2[n2], byArray));
            ++n2;
        }
    }

    public static void a(byte by2, byte[] object, byte[][] byArray, byte[][][] byArray2) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeShort(((byte[])object).length);
            ((OutputStream)dataOutputStream).write((byte[])object);
            dataOutputStream.writeByte(byArray.length);
            int n2 = 0;
            while (n2 < byArray.length) {
                dataOutputStream.writeShort(byArray[n2].length);
                ((OutputStream)dataOutputStream).write(byArray[n2]);
                dataOutputStream.writeByte(byArray2[n2].length);
                int n3 = 0;
                while (n3 < byArray2[n2].length) {
                    dataOutputStream.writeShort(byArray2[n2][n3].length);
                    ((OutputStream)dataOutputStream).write(byArray2[n2][n3]);
                    ++n3;
                }
                ko.a(byArray[n2], byArray2[n2]);
                ++n2;
            }
            dataOutputStream.writeByte(by2);
            aai.a("nqshImgPotionNew", byteArrayOutputStream.toByteArray());
            dataOutputStream.close();
            return;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return;
        }
    }

    public static void a(byte[] byArray) {
        a = new aab(Image.createImage((byte[])byArray, (int)0, (int)byArray.length), 20, 20);
    }

    public static void a(byte by2, byte[] byArray) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeByte(by2);
            dataOutputStream.writeShort(byArray.length);
            ((OutputStream)dataOutputStream).write(byArray);
            aai.a("gemItem", byteArrayOutputStream.toByteArray());
            dataOutputStream.close();
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    private static void c() {
        if (acv.l % 5 == 0) {
            f = (byte)((f + 1) % (g.getHeight() / 18));
        }
    }

    public static void a(Graphics graphics, short s2, int n2, int n3) {
        dh dh2 = ko.a(s2);
        if (dh2 != null) {
            if (!dh2.c) {
                int n4 = dh2.a.getWidth();
                int n5 = dh2.a.getHeight();
                int n6 = 1;
                if (n5 > 25) {
                    n5 /= 3;
                    n6 = 3;
                }
                if (acv.l % 5 == 0) {
                    dh2.e = (byte)((dh2.e + 1) % n6);
                }
                graphics.drawRegion(dh2.a, 0, dh2.e * n5, n4, n5, 0, n2, n3, 3);
                return;
            }
            graphics.drawRegion(g, 0, f * 18, 18, 18, 0, n2, n3, 3);
            ko.c();
        }
    }

    public static void a(Graphics graphics, short s2, int n2, int n3, int n4) {
        dh dh2 = ko.a(s2);
        if (dh2 != null) {
            if (!dh2.c) {
                int n5 = dh2.a.getWidth();
                int n6 = dh2.a.getHeight();
                int n7 = 1;
                if (n6 > 25) {
                    n6 /= 3;
                    n7 = 3;
                }
                if (acv.l % 5 == 0) {
                    dh2.e = (byte)((dh2.e + 1) % n7);
                }
                graphics.drawRegion(dh2.a, 0, dh2.e * n6, n5, n6, 0, n2, n3, n4);
                return;
            }
            graphics.drawRegion(g, 0, f * 18, 18, 18, 0, n2, n3, 3);
            ko.c();
        }
    }

    public static void b(Graphics graphics, short s2, int n2, int n3) {
        dh dh2 = ko.a((short)800);
        if (dh2 != null) {
            if (!dh2.c) {
                graphics.drawImage(dh2.a, 98, 95, 3);
                return;
            }
            graphics.drawRegion(g, 0, f * 18, 18, 18, 0, 98, 95, 3);
            ko.c();
        }
    }

    public static void b(Graphics graphics, short s2, int n2, int n3, int n4) {
        dh dh2 = ko.a(s2);
        if (dh2 != null) {
            if (!dh2.c) {
                graphics.drawImage(dh2.a, n2, n3, n4);
                return;
            }
            graphics.drawRegion(g, 0, f * 18, 18, 18, 0, n2, n3, 3);
            ko.c();
        }
    }

    public static dh a(short s2) {
        dh dh2 = (dh)c.get(String.valueOf(s2));
        if (dh2 == null) {
            dh2 = new dh();
            new dh().c = true;
            c.put("" + s2, dh2);
            go.a().j(s2);
            dh2.d = (int)(System.currentTimeMillis() / 1000L);
        } else {
            if (dh2.a == null && System.currentTimeMillis() / 1000L - dh2.d > 30L) {
                go.a().j(s2);
                dh2.d = (int)(System.currentTimeMillis() / 1000L);
            }
            dh2.b = (int)(System.currentTimeMillis() / 1000L);
        }
        return dh2;
    }
}

