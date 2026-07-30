/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Assert;
import common.Logger;
import jxl.CellType;
import jxl.LabelCell;
import jxl.StringFormulaCell;
import jxl.WorkbookSettings;
import jxl.biff.FormattingRecords;
import jxl.biff.FormulaData;
import jxl.biff.IntegerHelper;
import jxl.biff.StringHelper;
import jxl.biff.Type;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;
import jxl.read.biff.CellValue;
import jxl.read.biff.File;
import jxl.read.biff.Record;
import jxl.read.biff.SheetImpl;

class StringFormulaRecord
extends CellValue
implements LabelCell,
FormulaData,
StringFormulaCell {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$StringFormulaRecord == null ? (class$jxl$read$biff$StringFormulaRecord = StringFormulaRecord.class$("jxl.read.biff.StringFormulaRecord")) : class$jxl$read$biff$StringFormulaRecord);
    private String value;
    private ExternalSheet externalSheet;
    private WorkbookMethods nameTable;
    private String formulaString;
    private byte[] data;
    static /* synthetic */ Class class$jxl$read$biff$StringFormulaRecord;

    public StringFormulaRecord(Record t, File excelFile, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si, WorkbookSettings ws) {
        super(t, fr, si);
        int count;
        this.externalSheet = es;
        this.nameTable = nt;
        this.data = this.getRecord().getData();
        int pos = excelFile.getPos();
        Record nextRecord = excelFile.next();
        for (count = 0; nextRecord.getType() != Type.STRING && count < 4; ++count) {
            nextRecord = excelFile.next();
        }
        Assert.verify(count < 4, " @ " + pos);
        this.readString(nextRecord.getData(), ws);
    }

    public StringFormulaRecord(Record t, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si) {
        super(t, fr, si);
        this.externalSheet = es;
        this.nameTable = nt;
        this.data = this.getRecord().getData();
        this.value = "";
    }

    private void readString(byte[] d, WorkbookSettings ws) {
        boolean richString;
        int pos = 0;
        int chars = IntegerHelper.getInt(d[0], d[1]);
        if (chars == 0) {
            this.value = "";
            return;
        }
        byte optionFlags = d[pos += 2];
        ++pos;
        if ((optionFlags & 0xF) != optionFlags) {
            pos = 0;
            chars = IntegerHelper.getInt(d[0], (byte)0);
            optionFlags = d[1];
            pos = 2;
        }
        boolean extendedString = (optionFlags & 4) != 0;
        boolean bl = richString = (optionFlags & 8) != 0;
        if (richString) {
            pos += 2;
        }
        if (extendedString) {
            pos += 4;
        }
        boolean asciiEncoding = (optionFlags & 1) == 0;
        this.value = asciiEncoding ? StringHelper.getString(d, chars, pos, ws) : StringHelper.getUnicodeString(d, chars, pos);
    }

    public String getContents() {
        return this.value;
    }

    public String getString() {
        return this.value;
    }

    public CellType getType() {
        return CellType.STRING_FORMULA;
    }

    public byte[] getFormulaData() throws FormulaException {
        if (!this.getSheet().getWorkbook().getWorkbookBof().isBiff8()) {
            throw new FormulaException(FormulaException.BIFF8_SUPPORTED);
        }
        byte[] d = new byte[this.data.length - 6];
        System.arraycopy(this.data, 6, d, 0, this.data.length - 6);
        return d;
    }

    public String getFormula() throws FormulaException {
        if (this.formulaString == null) {
            byte[] tokens = new byte[this.data.length - 22];
            System.arraycopy(this.data, 22, tokens, 0, tokens.length);
            FormulaParser fp = new FormulaParser(tokens, this, this.externalSheet, this.nameTable, this.getSheet().getWorkbook().getSettings());
            fp.parse();
            this.formulaString = fp.getFormula();
        }
        return this.formulaString;
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

