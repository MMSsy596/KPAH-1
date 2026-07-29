/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Assert;
import common.Logger;
import jxl.WorkbookSettings;
import jxl.biff.EncodedURLHelper;
import jxl.biff.IntegerHelper;
import jxl.biff.StringHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class SupbookRecord
extends WritableRecordData {
    private static Logger logger = Logger.getLogger(class$jxl$write$biff$SupbookRecord == null ? (class$jxl$write$biff$SupbookRecord = SupbookRecord.class$("jxl.write.biff.SupbookRecord")) : class$jxl$write$biff$SupbookRecord);
    private SupbookType type;
    private byte[] data;
    private int numSheets;
    private String fileName;
    private String[] sheetNames;
    private WorkbookSettings workbookSettings;
    public static final SupbookType INTERNAL = new SupbookType();
    public static final SupbookType EXTERNAL = new SupbookType();
    public static final SupbookType ADDIN = new SupbookType();
    public static final SupbookType LINK = new SupbookType();
    public static final SupbookType UNKNOWN = new SupbookType();
    static /* synthetic */ Class class$jxl$write$biff$SupbookRecord;

    public SupbookRecord() {
        super(Type.SUPBOOK);
        this.type = ADDIN;
        try {
            throw new Exception();
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public SupbookRecord(int sheets, WorkbookSettings ws) {
        super(Type.SUPBOOK);
        this.numSheets = sheets;
        this.type = INTERNAL;
        this.workbookSettings = ws;
    }

    public SupbookRecord(String fn, WorkbookSettings ws) {
        super(Type.SUPBOOK);
        this.fileName = fn;
        this.numSheets = 1;
        this.sheetNames = new String[0];
        this.workbookSettings = ws;
        this.type = EXTERNAL;
    }

    public SupbookRecord(jxl.read.biff.SupbookRecord sr, WorkbookSettings ws) {
        super(Type.SUPBOOK);
        this.workbookSettings = ws;
        if (sr.getType() == jxl.read.biff.SupbookRecord.INTERNAL) {
            this.type = INTERNAL;
            this.numSheets = sr.getNumberOfSheets();
        } else if (sr.getType() == jxl.read.biff.SupbookRecord.EXTERNAL) {
            this.type = EXTERNAL;
            this.numSheets = sr.getNumberOfSheets();
            this.fileName = sr.getFileName();
            this.sheetNames = new String[this.numSheets];
            for (int i = 0; i < this.numSheets; ++i) {
                this.sheetNames[i] = sr.getSheetName(i);
            }
        }
        if (sr.getType() == jxl.read.biff.SupbookRecord.ADDIN) {
            logger.warn("Supbook type is addin");
        }
    }

    private void initInternal(jxl.read.biff.SupbookRecord sr) {
        this.numSheets = sr.getNumberOfSheets();
        this.initInternal();
    }

    private void initInternal() {
        this.data = new byte[4];
        IntegerHelper.getTwoBytes(this.numSheets, this.data, 0);
        this.data[2] = 1;
        this.data[3] = 4;
        this.type = INTERNAL;
    }

    void adjustInternal(int sheets) {
        Assert.verify(this.type == INTERNAL);
        this.numSheets = sheets;
        this.initInternal();
    }

    private void initExternal() {
        int totalSheetNameLength = 0;
        for (int i = 0; i < this.numSheets; ++i) {
            totalSheetNameLength += this.sheetNames[i].length();
        }
        byte[] fileNameData = EncodedURLHelper.getEncodedURL(this.fileName, this.workbookSettings);
        int dataLength = 6 + fileNameData.length + this.numSheets * 3 + totalSheetNameLength * 2;
        this.data = new byte[dataLength];
        IntegerHelper.getTwoBytes(this.numSheets, this.data, 0);
        int pos = 2;
        IntegerHelper.getTwoBytes(fileNameData.length + 1, this.data, pos);
        this.data[pos + 2] = 0;
        this.data[pos + 3] = 1;
        System.arraycopy(fileNameData, 0, this.data, pos + 4, fileNameData.length);
        pos += 4 + fileNameData.length;
        for (int i = 0; i < this.sheetNames.length; ++i) {
            IntegerHelper.getTwoBytes(this.sheetNames[i].length(), this.data, pos);
            this.data[pos + 2] = 1;
            StringHelper.getUnicodeBytes(this.sheetNames[i], this.data, pos + 3);
            pos += 3 + this.sheetNames[i].length() * 2;
        }
    }

    private void initAddin() {
        this.data = new byte[]{1, 0, 1, 58};
    }

    public byte[] getData() {
        if (this.type == INTERNAL) {
            this.initInternal();
        } else if (this.type == EXTERNAL) {
            this.initExternal();
        } else if (this.type == ADDIN) {
            this.initAddin();
        } else {
            logger.warn("unsupported supbook type - defaulting to internal");
            this.initInternal();
        }
        return this.data;
    }

    public SupbookType getType() {
        return this.type;
    }

    public int getNumberOfSheets() {
        return this.numSheets;
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getSheetIndex(String s) {
        boolean found = false;
        int sheetIndex = 0;
        for (int i = 0; i < this.sheetNames.length && !found; ++i) {
            if (!this.sheetNames[i].equals(s)) continue;
            found = true;
            sheetIndex = 0;
        }
        if (found) {
            return sheetIndex;
        }
        String[] names = new String[this.sheetNames.length + 1];
        names[this.sheetNames.length] = s;
        this.sheetNames = names;
        return this.sheetNames.length - 1;
    }

    public String getSheetName(int s) {
        return this.sheetNames[s];
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    private static class SupbookType {
        private SupbookType() {
        }
    }
}

