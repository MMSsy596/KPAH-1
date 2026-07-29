/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import common.Assert;
import common.Logger;
import jxl.WorkbookSettings;
import jxl.biff.DVParser;
import jxl.biff.DataValidation;
import jxl.biff.Type;
import jxl.biff.WorkbookMethods;
import jxl.biff.WritableRecordData;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.read.biff.Record;

public class DataValiditySettingsRecord
extends WritableRecordData {
    private static Logger logger = Logger.getLogger(class$jxl$biff$DataValiditySettingsRecord == null ? (class$jxl$biff$DataValiditySettingsRecord = DataValiditySettingsRecord.class$("jxl.biff.DataValiditySettingsRecord")) : class$jxl$biff$DataValiditySettingsRecord);
    private byte[] data;
    private DVParser dvParser;
    private WorkbookMethods workbook;
    private ExternalSheet externalSheet;
    private WorkbookSettings workbookSettings;
    private DataValidation dataValidation;
    static /* synthetic */ Class class$jxl$biff$DataValiditySettingsRecord;

    public DataValiditySettingsRecord(Record t, ExternalSheet es, WorkbookMethods wm, WorkbookSettings ws) {
        super(t);
        this.data = t.getData();
        this.externalSheet = es;
        this.workbook = wm;
        this.workbookSettings = ws;
    }

    DataValiditySettingsRecord(DataValiditySettingsRecord dvsr) {
        super(Type.DV);
        this.data = dvsr.getData();
    }

    DataValiditySettingsRecord(DataValiditySettingsRecord dvsr, ExternalSheet es, WorkbookMethods w, WorkbookSettings ws) {
        super(Type.DV);
        this.workbook = w;
        this.externalSheet = es;
        this.workbookSettings = ws;
        Assert.verify(w != null);
        Assert.verify(es != null);
        this.data = new byte[dvsr.data.length];
        System.arraycopy(dvsr.data, 0, this.data, 0, this.data.length);
    }

    public DataValiditySettingsRecord(DVParser dvp) {
        super(Type.DV);
        this.dvParser = dvp;
    }

    private void initialize() {
        try {
            if (this.dvParser == null) {
                this.dvParser = new DVParser(this.data, this.externalSheet, this.workbook, this.workbookSettings);
            }
        }
        catch (FormulaException e) {
            logger.warn("Cannot read drop down range " + e.getMessage());
        }
    }

    public byte[] getData() {
        if (this.dvParser == null) {
            return this.data;
        }
        return this.dvParser.getData();
    }

    public void insertRow(int row) {
        if (this.dvParser == null) {
            this.initialize();
        }
        this.dvParser.insertRow(row);
    }

    public void removeRow(int row) {
        if (this.dvParser == null) {
            this.initialize();
        }
        this.dvParser.removeRow(row);
    }

    public void insertColumn(int col) {
        if (this.dvParser == null) {
            this.initialize();
        }
        this.dvParser.insertColumn(col);
    }

    public void removeColumn(int col) {
        if (this.dvParser == null) {
            this.initialize();
        }
        this.dvParser.removeColumn(col);
    }

    public int getFirstColumn() {
        if (this.dvParser == null) {
            this.initialize();
        }
        return this.dvParser.getFirstColumn();
    }

    public int getLastColumn() {
        if (this.dvParser == null) {
            this.initialize();
        }
        return this.dvParser.getLastColumn();
    }

    public int getFirstRow() {
        if (this.dvParser == null) {
            this.initialize();
        }
        return this.dvParser.getFirstRow();
    }

    public int getLastRow() {
        if (this.dvParser == null) {
            this.initialize();
        }
        return this.dvParser.getLastRow();
    }

    void setDataValidation(DataValidation dv) {
        this.dataValidation = dv;
    }

    public String getValidationFormula() {
        try {
            if (this.dvParser == null) {
                this.initialize();
            }
            return this.dvParser.getValidationFormula();
        }
        catch (FormulaException e) {
            logger.warn("Cannot read drop down range " + e.getMessage());
            return "";
        }
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

