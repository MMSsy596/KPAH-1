/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.format.Colour;
import jxl.format.RGB;
import jxl.read.biff.Record;

public class PaletteRecord
extends WritableRecordData {
    private RGB[] rgbColours = new RGB[56];
    private boolean dirty;
    private boolean read;
    private boolean initialized;
    private static final int numColours = 56;

    public PaletteRecord(Record t) {
        super(t);
        this.initialized = false;
        this.dirty = false;
        this.read = true;
    }

    public PaletteRecord() {
        super(Type.PALETTE);
        this.initialized = true;
        this.dirty = false;
        this.read = false;
        Colour[] colours = Colour.getAllColours();
        for (int i = 0; i < colours.length; ++i) {
            Colour c = colours[i];
            this.setColourRGB(c, c.getDefaultRGB().getRed(), c.getDefaultRGB().getGreen(), c.getDefaultRGB().getBlue());
        }
    }

    public byte[] getData() {
        if (this.read && !this.dirty) {
            return this.getRecord().getData();
        }
        byte[] data = new byte[226];
        int pos = 0;
        IntegerHelper.getTwoBytes(56, data, pos);
        for (int i = 0; i < 56; ++i) {
            pos = i * 4 + 2;
            data[pos] = (byte)this.rgbColours[i].getRed();
            data[pos + 1] = (byte)this.rgbColours[i].getGreen();
            data[pos + 2] = (byte)this.rgbColours[i].getBlue();
        }
        return data;
    }

    private void initialize() {
        byte[] data = this.getRecord().getData();
        int numrecords = IntegerHelper.getInt(data[0], data[1]);
        for (int i = 0; i < numrecords; ++i) {
            int pos = i * 4 + 2;
            int red = IntegerHelper.getInt(data[pos], (byte)0);
            int green = IntegerHelper.getInt(data[pos + 1], (byte)0);
            int blue = IntegerHelper.getInt(data[pos + 2], (byte)0);
            this.rgbColours[i] = new RGB(red, green, blue);
        }
        this.initialized = true;
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public void setColourRGB(Colour c, int r, int g, int b) {
        int pos = c.getValue() - 8;
        if (pos < 0 || pos >= 56) {
            return;
        }
        if (!this.initialized) {
            this.initialize();
        }
        r = this.setValueRange(r, 0, 255);
        g = this.setValueRange(g, 0, 255);
        b = this.setValueRange(b, 0, 255);
        this.rgbColours[pos] = new RGB(r, g, b);
        this.dirty = true;
    }

    public RGB getColourRGB(Colour c) {
        int pos = c.getValue() - 8;
        if (pos < 0 || pos >= 56) {
            return c.getDefaultRGB();
        }
        if (!this.initialized) {
            this.initialize();
        }
        return this.rgbColours[pos];
    }

    private int setValueRange(int val, int min, int max) {
        val = Math.max(val, min);
        val = Math.min(val, max);
        return val;
    }
}

