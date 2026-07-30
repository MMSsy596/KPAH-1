/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import jxl.WorkbookSettings;
import jxl.biff.BaseCompoundFile;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.read.biff.BiffException;
import jxl.read.biff.CompoundFile;
import jxl.read.biff.Record;

public class File {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$File == null ? (class$jxl$read$biff$File = File.class$("jxl.read.biff.File")) : class$jxl$read$biff$File);
    private byte[] data;
    private int filePos;
    private int oldPos;
    private int initialFileSize;
    private int arrayGrowSize;
    private CompoundFile compoundFile;
    private WorkbookSettings workbookSettings;
    static /* synthetic */ Class class$jxl$read$biff$File;

    public File(InputStream is, WorkbookSettings ws) throws IOException, BiffException {
        int bytesRead;
        this.workbookSettings = ws;
        this.initialFileSize = this.workbookSettings.getInitialFileSize();
        this.arrayGrowSize = this.workbookSettings.getArrayGrowSize();
        byte[] d = new byte[this.initialFileSize];
        int pos = bytesRead = is.read(d);
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedIOException();
        }
        while (bytesRead != -1) {
            if (pos >= d.length) {
                byte[] newArray = new byte[d.length + this.arrayGrowSize];
                System.arraycopy(d, 0, newArray, 0, d.length);
                d = newArray;
            }
            bytesRead = is.read(d, pos, d.length - pos);
            pos += bytesRead;
            if (!Thread.currentThread().isInterrupted()) continue;
            throw new InterruptedIOException();
        }
        bytesRead = pos + 1;
        if (bytesRead == 0) {
            throw new BiffException(BiffException.excelFileNotFound);
        }
        CompoundFile cf = new CompoundFile(d, ws);
        try {
            this.data = cf.getStream("workbook");
        }
        catch (BiffException e) {
            this.data = cf.getStream("book");
        }
        if (!this.workbookSettings.getPropertySetsDisabled() && cf.getNumberOfPropertySets() > BaseCompoundFile.STANDARD_PROPERTY_SETS.length) {
            this.compoundFile = cf;
        }
        cf = null;
        if (!this.workbookSettings.getGCDisabled()) {
            System.gc();
        }
    }

    public File(byte[] d) {
        this.data = d;
    }

    Record next() {
        Record r = new Record(this.data, this.filePos, this);
        return r;
    }

    Record peek() {
        int tempPos = this.filePos;
        Record r = new Record(this.data, this.filePos, this);
        this.filePos = tempPos;
        return r;
    }

    public void skip(int bytes) {
        this.filePos += bytes;
    }

    public byte[] read(int pos, int length) {
        byte[] ret = new byte[length];
        try {
            System.arraycopy(this.data, pos, ret, 0, length);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            logger.error("Array index out of bounds at position " + pos + " record length " + length);
            throw e;
        }
        return ret;
    }

    public int getPos() {
        return this.filePos;
    }

    public void setPos(int p) {
        this.oldPos = this.filePos;
        this.filePos = p;
    }

    public void restorePos() {
        this.filePos = this.oldPos;
    }

    private void moveToFirstBof() {
        boolean bofFound = false;
        while (!bofFound) {
            int code = IntegerHelper.getInt(this.data[this.filePos], this.data[this.filePos + 1]);
            if (code == Type.BOF.value) {
                bofFound = true;
                continue;
            }
            this.skip(128);
        }
    }

    public void close() {
    }

    public void clear() {
        this.data = null;
    }

    public boolean hasNext() {
        return this.filePos < this.data.length - 4;
    }

    CompoundFile getCompoundFile() {
        return this.compoundFile;
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

