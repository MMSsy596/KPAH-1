/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import common.Assert;
import common.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import jxl.WorkbookSettings;
import jxl.biff.DValParser;
import jxl.biff.DataValidityListRecord;
import jxl.biff.DataValiditySettingsRecord;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.write.biff.File;

public class DataValidation {
    private static Logger logger = Logger.getLogger(class$jxl$biff$DataValidation == null ? (class$jxl$biff$DataValidation = DataValidation.class$("jxl.biff.DataValidation")) : class$jxl$biff$DataValidation);
    private DataValidityListRecord validityList;
    private ArrayList validitySettings;
    private WorkbookMethods workbook;
    private ExternalSheet externalSheet;
    private WorkbookSettings workbookSettings;
    private int comboBoxObjectId;
    private boolean copied;
    public static final int DEFAULT_OBJECT_ID = -1;
    static /* synthetic */ Class class$jxl$biff$DataValidation;

    public DataValidation(DataValidityListRecord dvlr) {
        this.validityList = dvlr;
        this.validitySettings = new ArrayList(this.validityList.getNumberOfSettings());
        this.copied = false;
    }

    public DataValidation(int objId, ExternalSheet es, WorkbookMethods wm, WorkbookSettings ws) {
        this.workbook = wm;
        this.externalSheet = es;
        this.workbookSettings = ws;
        this.validitySettings = new ArrayList();
        this.comboBoxObjectId = objId;
        this.copied = false;
    }

    public DataValidation(DataValidation dv, ExternalSheet es, WorkbookMethods wm, WorkbookSettings ws) {
        this.workbook = wm;
        this.externalSheet = es;
        this.workbookSettings = ws;
        this.copied = true;
        this.validityList = new DataValidityListRecord(dv.getDataValidityList());
        this.validitySettings = new ArrayList();
        DataValiditySettingsRecord[] settings = dv.getDataValiditySettings();
        for (int i = 0; i < settings.length; ++i) {
            this.validitySettings.add(new DataValiditySettingsRecord(settings[i], this.externalSheet, this.workbook, this.workbookSettings));
        }
    }

    public void add(DataValiditySettingsRecord dvsr) {
        this.validitySettings.add(dvsr);
        dvsr.setDataValidation(this);
        if (this.copied) {
            Assert.verify(this.validityList != null);
            this.validityList.dvAdded();
        }
    }

    public DataValidityListRecord getDataValidityList() {
        return this.validityList;
    }

    public DataValiditySettingsRecord[] getDataValiditySettings() {
        DataValiditySettingsRecord[] dvlr = new DataValiditySettingsRecord[]{};
        return this.validitySettings.toArray(dvlr);
    }

    public void write(File outputFile) throws IOException {
        if (this.validityList == null) {
            DValParser dvp = new DValParser(this.comboBoxObjectId, this.validitySettings.size());
            this.validityList = new DataValidityListRecord(dvp);
        }
        if (!this.validityList.hasDVRecords()) {
            return;
        }
        outputFile.write(this.validityList);
        Iterator i = this.validitySettings.iterator();
        while (i.hasNext()) {
            DataValiditySettingsRecord dvsr = (DataValiditySettingsRecord)i.next();
            outputFile.write(dvsr);
        }
    }

    public void insertRow(int row) {
        Iterator i = this.validitySettings.iterator();
        while (i.hasNext()) {
            DataValiditySettingsRecord dv = (DataValiditySettingsRecord)i.next();
            dv.insertRow(row);
        }
    }

    public void removeRow(int row) {
        Iterator i = this.validitySettings.iterator();
        while (i.hasNext()) {
            DataValiditySettingsRecord dv = (DataValiditySettingsRecord)i.next();
            if (dv.getFirstRow() == row && dv.getLastRow() == row) {
                i.remove();
                this.validityList.dvRemoved();
                continue;
            }
            dv.removeRow(row);
        }
    }

    public void insertColumn(int col) {
        Iterator i = this.validitySettings.iterator();
        while (i.hasNext()) {
            DataValiditySettingsRecord dv = (DataValiditySettingsRecord)i.next();
            dv.insertColumn(col);
        }
    }

    public void removeColumn(int col) {
        Iterator i = this.validitySettings.iterator();
        while (i.hasNext()) {
            DataValiditySettingsRecord dv = (DataValiditySettingsRecord)i.next();
            if (dv.getFirstColumn() == col && dv.getLastColumn() == col) {
                i.remove();
                this.validityList.dvRemoved();
                continue;
            }
            dv.removeColumn(col);
        }
    }

    public DataValiditySettingsRecord getDataValiditySettings(int col, int row) {
        boolean found = false;
        DataValiditySettingsRecord foundRecord = null;
        Iterator i = this.validitySettings.iterator();
        while (i.hasNext() && !found) {
            DataValiditySettingsRecord dvsr = (DataValiditySettingsRecord)i.next();
            if (dvsr.getFirstColumn() != col || dvsr.getFirstRow() != row) continue;
            found = true;
            foundRecord = dvsr;
        }
        return foundRecord;
    }

    public int getComboBoxObjectId() {
        return this.comboBoxObjectId;
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

