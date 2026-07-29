/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Logger;
import java.io.IOException;
import java.io.OutputStream;
import jxl.WorkbookSettings;
import jxl.biff.ByteData;
import jxl.write.biff.CompoundFile;
import jxl.write.biff.JxlWriteException;

public final class File {
    private static Logger logger = Logger.getLogger(class$jxl$write$biff$File == null ? (class$jxl$write$biff$File = File.class$("jxl.write.biff.File")) : class$jxl$write$biff$File);
    private byte[] data;
    private int pos;
    private OutputStream outputStream;
    private int initialFileSize;
    private int arrayGrowSize;
    private WorkbookSettings workbookSettings;
    jxl.read.biff.CompoundFile readCompoundFile;
    static /* synthetic */ Class class$jxl$write$biff$File;

    File(OutputStream os, WorkbookSettings ws, jxl.read.biff.CompoundFile rcf) {
        this.initialFileSize = ws.getInitialFileSize();
        this.arrayGrowSize = ws.getArrayGrowSize();
        this.data = new byte[this.initialFileSize];
        this.pos = 0;
        this.outputStream = os;
        this.workbookSettings = ws;
        this.readCompoundFile = rcf;
    }

    void close(boolean cs) throws IOException, JxlWriteException {
        CompoundFile cf = new CompoundFile(this.data, this.pos, this.outputStream, this.readCompoundFile);
        cf.write();
        this.outputStream.flush();
        if (cs) {
            this.outputStream.close();
        }
        this.data = null;
        if (!this.workbookSettings.getGCDisabled()) {
            System.gc();
        }
    }

    public void write(ByteData record) throws IOException {
        try {
            byte[] bytes = record.getBytes();
            while (this.pos + bytes.length > this.data.length) {
                byte[] newdata = new byte[this.data.length + this.arrayGrowSize];
                System.arraycopy(this.data, 0, newdata, 0, this.pos);
                this.data = newdata;
            }
            System.arraycopy(bytes, 0, this.data, this.pos, bytes.length);
            this.pos += bytes.length;
        }
        catch (Throwable t) {
            t.printStackTrace();
            throw new RuntimeException(t);
        }
    }

    int getPos() {
        return this.pos;
    }

    void setData(byte[] newdata, int pos) {
        System.arraycopy(newdata, 0, this.data, pos, newdata.length);
    }

    public void setOutputFile(OutputStream os) {
        if (this.data != null) {
            logger.warn("Rewriting a workbook with non-empty data");
        }
        this.outputStream = os;
        this.data = new byte[this.initialFileSize];
        this.pos = 0;
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

