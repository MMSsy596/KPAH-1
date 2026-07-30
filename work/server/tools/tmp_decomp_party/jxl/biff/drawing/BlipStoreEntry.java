/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Assert;
import common.Logger;
import java.io.IOException;
import jxl.biff.IntegerHelper;
import jxl.biff.drawing.BlipType;
import jxl.biff.drawing.Drawing;
import jxl.biff.drawing.EscherAtom;
import jxl.biff.drawing.EscherRecordData;
import jxl.biff.drawing.EscherRecordType;

class BlipStoreEntry
extends EscherAtom {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$BlipStoreEntry == null ? (class$jxl$biff$drawing$BlipStoreEntry = BlipStoreEntry.class$("jxl.biff.drawing.BlipStoreEntry")) : class$jxl$biff$drawing$BlipStoreEntry);
    private BlipType type;
    private byte[] data;
    private int imageDataLength;
    private int referenceCount;
    private boolean write;
    private static final int IMAGE_DATA_OFFSET = 61;
    static /* synthetic */ Class class$jxl$biff$drawing$BlipStoreEntry;

    public BlipStoreEntry(EscherRecordData erd) {
        super(erd);
        this.type = BlipType.getType(this.getInstance());
        this.write = false;
        byte[] bytes = this.getBytes();
        this.referenceCount = IntegerHelper.getInt(bytes[24], bytes[25], bytes[26], bytes[27]);
    }

    public BlipStoreEntry(Drawing d) throws IOException {
        super(EscherRecordType.BSE);
        this.type = BlipType.PNG;
        this.setVersion(2);
        this.setInstance(this.type.getValue());
        byte[] imageData = d.getImageBytes();
        this.imageDataLength = imageData.length;
        this.data = new byte[this.imageDataLength + 61];
        System.arraycopy(imageData, 0, this.data, 61, this.imageDataLength);
        this.referenceCount = d.getReferenceCount();
        this.write = true;
    }

    public BlipType getBlipType() {
        return this.type;
    }

    public byte[] getData() {
        if (this.write) {
            this.data[0] = (byte)this.type.getValue();
            this.data[1] = (byte)this.type.getValue();
            IntegerHelper.getFourBytes(this.imageDataLength + 8 + 17, this.data, 20);
            IntegerHelper.getFourBytes(this.referenceCount, this.data, 24);
            IntegerHelper.getFourBytes(0, this.data, 28);
            this.data[32] = 0;
            this.data[33] = 0;
            this.data[34] = 126;
            this.data[35] = 1;
            this.data[36] = 0;
            this.data[37] = 110;
            IntegerHelper.getTwoBytes(61470, this.data, 38);
            IntegerHelper.getFourBytes(this.imageDataLength + 17, this.data, 40);
        } else {
            this.data = this.getBytes();
        }
        return this.setHeaderData(this.data);
    }

    void dereference() {
        --this.referenceCount;
        Assert.verify(this.referenceCount >= 0);
    }

    int getReferenceCount() {
        return this.referenceCount;
    }

    byte[] getImageData() {
        byte[] allData = this.getBytes();
        byte[] imageData = new byte[allData.length - 61];
        System.arraycopy(allData, 61, imageData, 0, imageData.length);
        return imageData;
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }
}

