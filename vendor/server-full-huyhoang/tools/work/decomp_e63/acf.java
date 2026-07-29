/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Image;

public final class acf {
    public static acf a;
    private String[] c;
    private int[] d;
    private int[] e;
    private byte[] f;
    private int g;
    private int h;
    private String i;
    private byte[] j = new byte[]{78, 103, 117, 121, 101, 110, 86, 97, 110, 77, 105, 110, 104};
    private int k = this.j.length;
    public static final String[] b;
    private DataInputStream l;

    static {
        b = new String[]{"/c/leg/", "/c/body/", "/c/head/", "/c/hat/", "/c/coat/"};
    }

    public acf() {
    }

    public static void a() {
        if (a != null) {
            a.b();
        }
        a = null;
        System.gc();
    }

    public acf(String string, byte[] object) {
        int n2 = 0;
        int n3 = 0;
        this.i = string;
        this.h = 0;
        if (object == null) {
            object = this;
            this.l = new DataInputStream(object.getClass().getResourceAsStream(((acf)object).i));
        } else {
            byte[] byArray = object;
            object = this;
            this.l = new DataInputStream(new ByteArrayInputStream(byArray));
        }
        if (this.l == null) {
            a = null;
            return;
        }
        try {
            int n4;
            this.g = n4 = this.l.readUnsignedByte();
            ++this.h;
            this.c = new String[this.g];
            this.d = new int[this.g];
            this.e = new int[this.g];
            int n5 = 0;
            while (n5 < this.g) {
                byte by2;
                n4 = this.l.readByte();
                byte[] byArray = new byte[by2];
                this.l.read(byArray);
                this.b(byArray);
                this.c[n5] = new String(byArray);
                this.d[n5] = n2;
                this.e[n5] = n4 = this.l.readUnsignedShort();
                n2 += this.e[n5];
                n3 += this.e[n5];
                this.h += by2 + 3;
                ++n5;
            }
            this.f = new byte[n3];
            this.l.readFully(this.f);
            this.b(this.f);
        }
        catch (Exception exception) {
            String cfr_ignored_0 = String.valueOf(string) + " Error in fileback constructor > " + exception.toString();
        }
        this.b();
    }

    public static Image a(String string) {
        return a.d(String.valueOf(string) + ".png");
    }

    public static void b(String string) {
        a = new acf(string, null);
    }

    public static void a(byte[] byArray) {
        a = new acf("", byArray);
    }

    private void b(byte[] byArray) {
        int n2 = byArray.length;
        int n3 = 0;
        while (n3 < n2) {
            byArray[n3] = (byte)(byArray[n3] ^ this.j[n3 % this.k]);
            ++n3;
        }
    }

    public final void b() {
        try {
            if (this.l != null) {
                this.l.close();
                return;
            }
        }
        catch (IOException iOException) {}
    }

    public final byte[] c(String object) {
        int n2 = 0;
        while (n2 < this.g) {
            if (this.c[n2].compareTo((String)object) == 0) {
                object = new byte[this.e[n2]];
                System.arraycopy(this.f, this.d[n2], object, 0, this.e[n2]);
                return object;
            }
            ++n2;
        }
        throw new Exception("File '" + (String)object + "' not found!");
    }

    public final Image d(String string) {
        int n2 = 0;
        while (n2 < this.g) {
            if (this.c[n2].compareTo(string) == 0) {
                return Image.createImage((byte[])this.f, (int)this.d[n2], (int)this.e[n2]);
            }
            ++n2;
        }
        return null;
    }

    public final InputStream e(String object) {
        int n2 = 0;
        while (n2 < this.g) {
            if (this.c[n2].compareTo((String)object) == 0) {
                object = new byte[this.e[n2]];
                System.arraycopy(this.f, this.d[n2], object, 0, this.e[n2]);
                return new ByteArrayInputStream((byte[])object);
            }
            ++n2;
        }
        return null;
    }
}

