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
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class br {
    private int d;
    private int e;
    public int a = 0;
    private int[][] f;
    private int[][] g;
    private int[][] h;
    private int[][] i;
    private int[][] j;
    private int[][] k;
    public Image b;
    private int l;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    public int c;
    private static byte[][] r;

    static {
        byte[][] byArrayArray = new byte[5][];
        byte[] byArray = new byte[6];
        byArray[2] = 1;
        byArray[3] = 2;
        byArray[4] = 3;
        byArray[5] = 4;
        byArrayArray[0] = byArray;
        byte[] byArray2 = new byte[6];
        byArray2[2] = 1;
        byArray2[3] = 2;
        byArray2[4] = 3;
        byArray2[5] = 4;
        byArrayArray[1] = byArray2;
        byArrayArray[2] = new byte[6];
        byArrayArray[3] = new byte[6];
        byte[] byArray3 = new byte[6];
        byArray3[1] = 1;
        byArray3[3] = 1;
        byArray3[5] = 1;
        byArrayArray[4] = byArray3;
        r = byArrayArray;
        int[] nArray = new int[3];
        nArray[1] = 2;
        nArray[2] = 1;
    }

    public final void a(byte[] byArray, int n2) {
        ByteArrayInputStream byteArrayInputStream = null;
        DataInputStream dataInputStream = null;
        ByteArrayInputStream byteArrayInputStream2 = null;
        FilterInputStream filterInputStream = null;
        try {
            try {
                byteArrayInputStream = new ByteArrayInputStream(byArray);
                dataInputStream = new DataInputStream(byteArrayInputStream);
                byArray = new byte[dataInputStream.readShort()];
                dataInputStream.read(byArray, 0, byArray.length);
                this.b = Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
                byArray = new byte[dataInputStream.readShort()];
                dataInputStream.read(byArray, 0, byArray.length);
                byteArrayInputStream2 = new ByteArrayInputStream(byArray);
                filterInputStream = new DataInputStream(byteArrayInputStream2);
                this.n = yi.a(filterInputStream);
                this.o = yi.a(filterInputStream);
                this.p = yi.a(filterInputStream);
                this.q = yi.a(filterInputStream);
                this.l = yi.a(filterInputStream);
                this.m = yi.a(filterInputStream);
                try {
                    if (n2 < 4) {
                        int n3 = 0;
                        while (n3 < 4) {
                            n2 = 0;
                            while (n2 < this.f[n3].length) {
                                this.f[n3][n2] = filterInputStream.read();
                                this.g[n3][n2] = filterInputStream.read();
                                this.h[n3][n2] = filterInputStream.read();
                                this.i[n3][n2] = filterInputStream.read();
                                ++n2;
                            }
                            n2 = 0;
                            while (n2 < 6) {
                                this.j[n3][n2] = yi.a(filterInputStream);
                                this.k[n3][n2] = yi.a(filterInputStream);
                                ++n2;
                            }
                            ++n3;
                        }
                    } else {
                        int n4 = 0;
                        while (n4 < 3) {
                            n2 = 0;
                            while (n2 < this.f[n4].length) {
                                this.f[n4][n2] = filterInputStream.read();
                                this.g[n4][n2] = filterInputStream.read();
                                this.h[n4][n2] = filterInputStream.read();
                                this.i[n4][n2] = filterInputStream.read();
                                ++n2;
                            }
                            ++n4;
                        }
                        n4 = 0;
                        while (n4 < 4) {
                            n2 = 0;
                            while (n2 < 6) {
                                this.j[n4][n2] = yi.a(filterInputStream);
                                this.k[n4][n2] = yi.a(filterInputStream);
                                ++n2;
                            }
                            ++n4;
                        }
                    }
                }
                catch (Exception exception) {}
            }
            catch (Exception exception) {
                try {
                    byteArrayInputStream.close();
                }
                catch (Exception exception2) {}
                try {
                    byteArrayInputStream2.close();
                }
                catch (Exception exception3) {}
                try {
                    filterInputStream.close();
                }
                catch (Exception exception4) {}
                try {
                    dataInputStream.close();
                    return;
                }
                catch (Exception exception5) {
                    return;
                }
            }
        }
        catch (Throwable throwable) {
            try {
                byteArrayInputStream.close();
            }
            catch (Exception exception) {}
            try {
                byteArrayInputStream2.close();
            }
            catch (Exception exception) {}
            try {
                filterInputStream.close();
            }
            catch (Exception exception) {}
            try {
                dataInputStream.close();
            }
            catch (Exception exception) {}
            throw throwable;
        }
        try {
            byteArrayInputStream.close();
        }
        catch (Exception exception) {}
        try {
            byteArrayInputStream2.close();
        }
        catch (Exception exception) {}
        try {
            filterInputStream.close();
        }
        catch (Exception exception) {}
        try {
            dataInputStream.close();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private void a(int n2, int n3) {
        FilterInputStream filterInputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        FilterInputStream filterInputStream2 = null;
        try {
            try {
                filterInputStream = new DataInputStream("".getClass().getResourceAsStream(String.valueOf(acf.b[n2]) + n3));
                byte[] byArray = new byte[((DataInputStream)filterInputStream).readShort()];
                ((DataInputStream)filterInputStream).read(byArray, 0, byArray.length);
                this.b = Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
                byArray = new byte[((DataInputStream)filterInputStream).readShort()];
                ((DataInputStream)filterInputStream).read(byArray, 0, byArray.length);
                byteArrayInputStream = new ByteArrayInputStream(byArray);
                filterInputStream2 = new DataInputStream(byteArrayInputStream);
                this.n = yi.a(filterInputStream2);
                this.o = yi.a(filterInputStream2);
                this.p = yi.a(filterInputStream2);
                this.q = yi.a(filterInputStream2);
                this.l = yi.a(filterInputStream2);
                this.m = yi.a(filterInputStream2);
                int n4 = 0;
                while (n4 < 3) {
                    n3 = 0;
                    while (n3 < this.f[n4].length) {
                        this.f[n4][n3] = filterInputStream2.read();
                        this.g[n4][n3] = filterInputStream2.read();
                        this.h[n4][n3] = filterInputStream2.read();
                        this.i[n4][n3] = filterInputStream2.read();
                        ++n3;
                    }
                    ++n4;
                }
                n4 = 0;
                while (n4 < 4) {
                    n3 = 0;
                    while (n3 < 6) {
                        this.j[n4][n3] = yi.a(filterInputStream2);
                        this.k[n4][n3] = yi.a(filterInputStream2);
                        ++n3;
                    }
                    ++n4;
                }
                filterInputStream2.close();
            }
            catch (Exception exception) {
                try {
                    ((ByteArrayInputStream)null).close();
                }
                catch (Exception exception2) {}
                try {
                    byteArrayInputStream.close();
                }
                catch (Exception exception3) {}
                try {
                    filterInputStream2.close();
                }
                catch (Exception exception4) {}
                try {
                    filterInputStream.close();
                    return;
                }
                catch (Exception exception5) {
                    return;
                }
            }
        }
        catch (Throwable throwable) {
            try {
                ((ByteArrayInputStream)null).close();
            }
            catch (Exception exception) {}
            try {
                byteArrayInputStream.close();
            }
            catch (Exception exception) {}
            try {
                filterInputStream2.close();
            }
            catch (Exception exception) {}
            try {
                filterInputStream.close();
            }
            catch (Exception exception) {}
            throw throwable;
        }
        try {
            ((ByteArrayInputStream)null).close();
        }
        catch (Exception exception) {}
        try {
            byteArrayInputStream.close();
        }
        catch (Exception exception) {}
        try {
            filterInputStream2.close();
        }
        catch (Exception exception) {}
        try {
            filterInputStream.close();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private void b(int n2, int n3) {
        FilterInputStream filterInputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        FilterInputStream filterInputStream2 = null;
        try {
            try {
                filterInputStream = new DataInputStream("".getClass().getResourceAsStream(String.valueOf(acf.b[n2]) + n3));
                byte[] byArray = new byte[((DataInputStream)filterInputStream).readShort()];
                ((DataInputStream)filterInputStream).read(byArray, 0, byArray.length);
                this.b = Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
                byArray = new byte[((DataInputStream)filterInputStream).readShort()];
                ((DataInputStream)filterInputStream).read(byArray, 0, byArray.length);
                byteArrayInputStream = new ByteArrayInputStream(byArray);
                filterInputStream2 = new DataInputStream(byteArrayInputStream);
                this.n = yi.a(filterInputStream2);
                this.o = yi.a(filterInputStream2);
                this.p = yi.a(filterInputStream2);
                this.q = yi.a(filterInputStream2);
                this.l = yi.a(filterInputStream2);
                this.m = yi.a(filterInputStream2);
                try {
                    int n4 = 0;
                    while (n4 < 4) {
                        n3 = 0;
                        while (n3 < this.f[n4].length) {
                            this.f[n4][n3] = filterInputStream2.read();
                            this.g[n4][n3] = filterInputStream2.read();
                            this.h[n4][n3] = filterInputStream2.read();
                            this.i[n4][n3] = filterInputStream2.read();
                            ++n3;
                        }
                        n3 = 0;
                        while (n3 < 6) {
                            this.j[n4][n3] = yi.a(filterInputStream2);
                            this.k[n4][n3] = yi.a(filterInputStream2);
                            ++n3;
                        }
                        ++n4;
                    }
                }
                catch (Exception exception) {}
            }
            catch (Exception exception) {
                try {
                    ((ByteArrayInputStream)null).close();
                }
                catch (Exception exception2) {}
                try {
                    byteArrayInputStream.close();
                }
                catch (Exception exception3) {}
                try {
                    filterInputStream2.close();
                }
                catch (Exception exception4) {}
                try {
                    filterInputStream.close();
                    return;
                }
                catch (Exception exception5) {
                    return;
                }
            }
        }
        catch (Throwable throwable) {
            try {
                ((ByteArrayInputStream)null).close();
            }
            catch (Exception exception) {}
            try {
                byteArrayInputStream.close();
            }
            catch (Exception exception) {}
            try {
                filterInputStream2.close();
            }
            catch (Exception exception) {}
            try {
                filterInputStream.close();
            }
            catch (Exception exception) {}
            throw throwable;
        }
        try {
            ((ByteArrayInputStream)null).close();
        }
        catch (Exception exception) {}
        try {
            byteArrayInputStream.close();
        }
        catch (Exception exception) {}
        try {
            filterInputStream2.close();
        }
        catch (Exception exception) {}
        try {
            filterInputStream.close();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public br(int n2, int n3) {
        int[][] nArrayArray = new int[2][];
        int[] nArray = new int[3];
        nArray[0] = 1;
        nArray[1] = 1;
        nArrayArray[0] = nArray;
        int[] nArray2 = new int[3];
        nArray2[0] = 1;
        nArray2[1] = 1;
        nArrayArray[1] = nArray2;
        int[][] nArrayArray2 = new int[][]{{3, 2, 2}, {3, 2, 2}};
        this.d = n2;
        this.e = n3;
        this.j = new int[4][6];
        this.k = new int[4][6];
        int n4 = 0;
        switch (n2) {
            case 0: 
            case 1: {
                n4 = 5;
                break;
            }
            case 2: 
            case 3: {
                n4 = 1;
                break;
            }
            default: {
                n4 = 2;
            }
        }
        this.f = new int[4][n4];
        this.g = new int[4][n4];
        this.h = new int[4][n4];
        this.i = new int[4][n4];
        if (n2 < 4) {
            this.b(n2, n3);
            return;
        }
        this.a(n2, n3);
    }

    public final void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        if (this.d < 0) {
            return;
        }
        int n6 = 0;
        int n7 = n4;
        if (n4 > 2) {
            n6 = 2;
            n7 = 2;
        }
        if (this.b != null) {
            int n8 = this.j[n7][n5];
            int n9 = this.k[n7][n5];
            int n10 = this.h[n7][r[this.d][n5]];
            int n11 = this.i[n7][r[this.d][n5]];
            int n12 = this.f[n7][r[this.d][n5]];
            int n13 = this.g[n7][r[this.d][n5]];
            if (n12 > this.b.getWidth()) {
                n12 = 0;
            }
            if (n13 > this.b.getHeight()) {
                n13 = 0;
            }
            if (n12 + n10 > this.b.getWidth()) {
                n10 = this.b.getWidth() - n12;
            }
            if (n13 + n11 > this.b.getHeight()) {
                n11 = this.b.getHeight() - n13;
            }
            if (n4 > 2) {
                n8 = -this.j[n7][n5] - n10;
                n9 = this.k[n7][n5];
            }
            graphics.drawRegion(this.b, n12, n13, n10, n11, n6, n2 + n8, n3 + n9, 0);
        } else {
            yi.a(graphics, n2, n4, n5, this.d);
        }
        System.currentTimeMillis();
    }

    public final void a(Graphics graphics, short s2, short s3, int n2, int n3) {
        try {
            if (this.b != null) {
                int n4 = this.h[n2][r[this.d][n3]];
                int n5 = this.i[n2][r[this.d][n3]];
                int n6 = this.f[n2][r[this.d][n3]];
                n2 = this.g[n2][r[this.d][n3]];
                if (n6 > this.b.getWidth()) {
                    n6 = 0;
                }
                if (n2 > this.b.getHeight()) {
                    n2 = 0;
                }
                if (n6 + n4 > this.b.getWidth()) {
                    n4 = this.b.getWidth() - n6;
                }
                if (n2 + n5 > this.b.getHeight()) {
                    n5 = this.b.getHeight() - n2;
                }
                graphics.drawRegion(this.b, n6, n2, n4, n5, 0, (int)s2, (int)s3, 3);
                return;
            }
        }
        catch (Exception exception) {
            System.out.println("LOI PART " + this.e);
        }
    }

    public final void a(Graphics graphics, short s2, short s3) {
        try {
            if (this.b != null) {
                graphics.drawRegion(this.b, this.n, this.o, this.p, this.q, 0, s2 + this.l, s3 + this.m, 0);
                return;
            }
        }
        catch (Exception exception) {}
    }
}

