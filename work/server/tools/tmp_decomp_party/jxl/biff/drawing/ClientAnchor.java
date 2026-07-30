/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.drawing.EscherAtom;
import jxl.biff.drawing.EscherRecordData;
import jxl.biff.drawing.EscherRecordType;

class ClientAnchor
extends EscherAtom {
    private static final Logger logger = Logger.getLogger(class$jxl$biff$drawing$ClientAnchor == null ? (class$jxl$biff$drawing$ClientAnchor = ClientAnchor.class$("jxl.biff.drawing.ClientAnchor")) : class$jxl$biff$drawing$ClientAnchor);
    private byte[] data;
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    static /* synthetic */ Class class$jxl$biff$drawing$ClientAnchor;

    public ClientAnchor(EscherRecordData erd) {
        super(erd);
        byte[] bytes = this.getBytes();
        int x1Cell = IntegerHelper.getInt(bytes[2], bytes[3]);
        int x1Fraction = IntegerHelper.getInt(bytes[4], bytes[5]);
        this.x1 = (double)x1Cell + (double)x1Fraction / 1024.0;
        int y1Cell = IntegerHelper.getInt(bytes[6], bytes[7]);
        int y1Fraction = IntegerHelper.getInt(bytes[8], bytes[9]);
        this.y1 = (double)y1Cell + (double)y1Fraction / 256.0;
        int x2Cell = IntegerHelper.getInt(bytes[10], bytes[11]);
        int x2Fraction = IntegerHelper.getInt(bytes[12], bytes[13]);
        this.x2 = (double)x2Cell + (double)x2Fraction / 1024.0;
        int y2Cell = IntegerHelper.getInt(bytes[14], bytes[15]);
        int y2Fraction = IntegerHelper.getInt(bytes[16], bytes[17]);
        this.y2 = (double)y2Cell + (double)y2Fraction / 256.0;
    }

    public ClientAnchor(double x1, double y1, double x2, double y2) {
        super(EscherRecordType.CLIENT_ANCHOR);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    byte[] getData() {
        this.data = new byte[18];
        IntegerHelper.getTwoBytes(1, this.data, 0);
        IntegerHelper.getTwoBytes((int)this.x1, this.data, 2);
        int x1fraction = (int)((this.x1 - (double)((int)this.x1)) * 1024.0);
        IntegerHelper.getTwoBytes(x1fraction, this.data, 4);
        IntegerHelper.getTwoBytes((int)this.y1, this.data, 6);
        int y1fraction = (int)((this.y1 - (double)((int)this.y1)) * 256.0);
        IntegerHelper.getTwoBytes(y1fraction, this.data, 8);
        IntegerHelper.getTwoBytes((int)this.x2, this.data, 10);
        int x2fraction = (int)((this.x2 - (double)((int)this.x2)) * 1024.0);
        IntegerHelper.getTwoBytes(x2fraction, this.data, 12);
        IntegerHelper.getTwoBytes((int)this.y2, this.data, 14);
        int y2fraction = (int)((this.y2 - (double)((int)this.y2)) * 256.0);
        IntegerHelper.getTwoBytes(y2fraction, this.data, 16);
        return this.setHeaderData(this.data);
    }

    double getX1() {
        return this.x1;
    }

    double getY1() {
        return this.y1;
    }

    double getX2() {
        return this.x2;
    }

    double getY2() {
        return this.y2;
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

