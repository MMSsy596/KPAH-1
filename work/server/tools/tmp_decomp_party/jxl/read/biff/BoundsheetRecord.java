/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import java.io.UnsupportedEncodingException;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.read.biff.Record;

class BoundsheetRecord
extends RecordData {
    private int offset;
    private byte typeFlag;
    private byte visibilityFlag;
    private int length;
    private String name;
    public static Biff7 biff7 = new Biff7();

    public BoundsheetRecord(Record t) {
        super(t);
        byte[] data = this.getRecord().getData();
        this.offset = IntegerHelper.getInt(data[0], data[1], data[2], data[3]);
        this.typeFlag = data[5];
        this.visibilityFlag = data[4];
        this.length = data[6];
        if (data[7] == 0) {
            byte[] bytes = new byte[this.length];
            System.arraycopy(data, 8, bytes, 0, this.length);
            this.name = new String(bytes);
        } else {
            byte[] bytes = new byte[this.length * 2];
            System.arraycopy(data, 8, bytes, 0, this.length * 2);
            try {
                this.name = new String(bytes, "UnicodeLittle");
            }
            catch (UnsupportedEncodingException e) {
                this.name = "Error";
            }
        }
    }

    public BoundsheetRecord(Record t, Biff7 biff7) {
        super(t);
        byte[] data = this.getRecord().getData();
        this.offset = IntegerHelper.getInt(data[0], data[1], data[2], data[3]);
        this.typeFlag = data[5];
        this.visibilityFlag = data[4];
        this.length = data[6];
        byte[] bytes = new byte[this.length];
        System.arraycopy(data, 7, bytes, 0, this.length);
        this.name = new String(bytes);
    }

    public String getName() {
        return this.name;
    }

    public boolean isHidden() {
        return this.visibilityFlag != 0;
    }

    public boolean isSheet() {
        return this.typeFlag == 0;
    }

    public boolean isChart() {
        return this.typeFlag == 2;
    }

    private static class Biff7 {
        private Biff7() {
        }
    }
}

