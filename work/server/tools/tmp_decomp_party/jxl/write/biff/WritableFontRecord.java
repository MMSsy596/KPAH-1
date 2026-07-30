/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.FontRecord;
import jxl.format.Font;
import jxl.write.WriteException;
import jxl.write.biff.JxlWriteException;

public class WritableFontRecord
extends FontRecord {
    protected WritableFontRecord(String fn, int ps, int bold, boolean it, int us, int ci, int ss) {
        super(fn, ps, bold, it, us, ci, ss);
    }

    protected WritableFontRecord(Font f) {
        super(f);
    }

    protected void setPointSize(int pointSize) throws WriteException {
        if (this.isInitialized()) {
            throw new JxlWriteException(JxlWriteException.formatInitialized);
        }
        super.setFontPointSize(pointSize);
    }

    protected void setBoldStyle(int boldStyle) throws WriteException {
        if (this.isInitialized()) {
            throw new JxlWriteException(JxlWriteException.formatInitialized);
        }
        super.setFontBoldStyle(boldStyle);
    }

    protected void setItalic(boolean italic) throws WriteException {
        if (this.isInitialized()) {
            throw new JxlWriteException(JxlWriteException.formatInitialized);
        }
        super.setFontItalic(italic);
    }

    protected void setUnderlineStyle(int us) throws WriteException {
        if (this.isInitialized()) {
            throw new JxlWriteException(JxlWriteException.formatInitialized);
        }
        super.setFontUnderlineStyle(us);
    }

    protected void setColour(int colour) throws WriteException {
        if (this.isInitialized()) {
            throw new JxlWriteException(JxlWriteException.formatInitialized);
        }
        super.setFontColour(colour);
    }

    protected void setScriptStyle(int scriptStyle) throws WriteException {
        if (this.isInitialized()) {
            throw new JxlWriteException(JxlWriteException.formatInitialized);
        }
        super.setFontScriptStyle(scriptStyle);
    }

    protected void setStruckout(boolean os) throws WriteException {
        if (this.isInitialized()) {
            throw new JxlWriteException(JxlWriteException.formatInitialized);
        }
        super.setFontStruckout(os);
    }
}

