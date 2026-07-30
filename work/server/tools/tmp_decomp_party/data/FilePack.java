/*
 * Decompiled with CFR 0.152.
 */
package data;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FilePack {
    public static FilePack instance;
    public String[] fname;
    private int[] fpos;
    private int[] flen;
    private byte[] fullData;
    private int nFile;
    private int hSize;
    private String name;
    private byte[] code = new byte[]{78, 103, 117, 121, 101, 110, 86, 97, 110, 77, 105, 110, 104};
    private int codeLen = this.code.length;
    public static final String[] charAvatar;
    public static final String cBody = "/body.c";
    public static final String cHat = "/hat.c";
    public static final String cLeg = "/leg.c";
    public static final String cHead = "/head.c";
    public static final String main = "/main.sh";
    public static final String font = "/font.sh";
    public static final String npc = "/npc.sh";
    public static final String eff = "/eff.sh";
    public static final String tree = "/tree/";
    public static final String type = "/type.sh";
    public static final String wps = "/wpsplash/";
    public static final String arrow = "/arrow.sh";
    public static final String effPublic = "/skillpublic.sh";
    public static final String ground = "/g.sh";
    public static final String nation = "/nation";
    public static final String box = "/box.sh";
    private DataInputStream file;

    static {
        charAvatar = new String[]{"/c/leg/", "/c/body/", "/c/head/", "/c/hat/", "/c/coat/"};
    }

    public FilePack() {
    }

    public static void reset() {
        if (instance != null) {
            instance.close();
        }
        instance = null;
        System.gc();
    }

    public FilePack(String name, byte[] array) {
        int pos = 0;
        int size = 0;
        this.name = name;
        this.hSize = 0;
        if (array == null) {
            this.open();
        } else {
            this.openbyArray(array);
        }
        if (this.file == null) {
            instance = null;
            return;
        }
        try {
            this.nFile = this.encode(this.file.readUnsignedByte());
            ++this.hSize;
            this.fname = new String[this.nFile];
            this.fpos = new int[this.nFile];
            this.flen = new int[this.nFile];
            int i = 0;
            while (i < this.nFile) {
                int lname = this.encode(this.file.readByte());
                byte[] data = new byte[lname];
                this.file.read(data);
                this.encode(data);
                this.fname[i] = new String(data);
                this.fpos[i] = pos;
                this.flen[i] = this.encode(this.file.readUnsignedShort());
                pos += this.flen[i];
                size += this.flen[i];
                this.hSize += lname + 3;
                ++i;
            }
            this.fullData = new byte[size];
            this.file.readFully(this.fullData);
            this.encode(this.fullData);
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.close();
    }

    public static Image getImg(String path) {
        return instance.loadImage(String.valueOf(path) + ".png");
    }

    public static void init(String path) {
        instance = new FilePack(path, null);
    }

    public static void initByArray(byte[] array) {
        instance = new FilePack("", array);
    }

    private int encode(int i) {
        return i;
    }

    private void encode(byte[] bytes) {
        int len = bytes.length;
        int i = 0;
        while (i < len) {
            bytes[i] = (byte)(bytes[i] ^ this.code[i % this.codeLen]);
            ++i;
        }
    }

    private void open() {
        this.file = new DataInputStream(this.getClass().getResourceAsStream(this.name));
    }

    private void openbyArray(byte[] array) {
        this.file = new DataInputStream(new ByteArrayInputStream(array));
    }

    public void close() {
        try {
            if (this.file != null) {
                this.file.close();
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public byte[] loadFile(String fileName) throws Exception {
        int i = 0;
        while (i < this.nFile) {
            if (this.fname[i].compareTo(fileName) == 0) {
                byte[] bytes = new byte[this.flen[i]];
                System.arraycopy(this.fullData, this.fpos[i], bytes, 0, this.flen[i]);
                return bytes;
            }
            ++i;
        }
        throw new Exception("File '" + fileName + "' not found!");
    }

    public Image loadImage(String fileName) {
        int i = 0;
        while (i < this.nFile) {
            if (this.fname[i].compareTo(fileName) == 0) {
                return null;
            }
            ++i;
        }
        return null;
    }

    public InputStream loadData(String name) {
        int i = 0;
        while (i < this.nFile) {
            if (this.fname[i].compareTo(name) == 0) {
                byte[] aa = new byte[this.flen[i]];
                System.arraycopy(this.fullData, this.fpos[i], aa, 0, this.flen[i]);
                return new ByteArrayInputStream(aa);
            }
            ++i;
        }
        return null;
    }

    public byte[] getBinaryFile(String name) {
        int i = 0;
        while (i < this.nFile) {
            if (this.fname[i].compareTo(name) == 0) {
                byte[] array = new byte[this.flen[i]];
                System.arraycopy(this.fullData, this.fpos[i], array, 0, this.flen[i]);
                return array;
            }
            ++i;
        }
        return null;
    }
}

