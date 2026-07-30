/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Assert;
import common.Logger;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.biff.CellReferenceHelper;
import jxl.biff.CountryCode;
import jxl.biff.Fonts;
import jxl.biff.FormattingRecords;
import jxl.biff.IndexMapping;
import jxl.biff.IntegerHelper;
import jxl.biff.RangeImpl;
import jxl.biff.WorkbookMethods;
import jxl.biff.drawing.Drawing;
import jxl.biff.drawing.DrawingGroup;
import jxl.biff.drawing.DrawingGroupObject;
import jxl.biff.drawing.Origin;
import jxl.biff.formula.ExternalSheet;
import jxl.format.Colour;
import jxl.format.RGB;
import jxl.read.biff.WorkbookParser;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.biff.BOFRecord;
import jxl.write.biff.BackupRecord;
import jxl.write.biff.BookboolRecord;
import jxl.write.biff.BoundsheetRecord;
import jxl.write.biff.ButtonPropertySetRecord;
import jxl.write.biff.CellValue;
import jxl.write.biff.CodepageRecord;
import jxl.write.biff.CountryRecord;
import jxl.write.biff.DSFRecord;
import jxl.write.biff.DateRecord;
import jxl.write.biff.EOFRecord;
import jxl.write.biff.ExternalNameRecord;
import jxl.write.biff.ExternalSheetRecord;
import jxl.write.biff.File;
import jxl.write.biff.FunctionGroupCountRecord;
import jxl.write.biff.HideobjRecord;
import jxl.write.biff.InterfaceEndRecord;
import jxl.write.biff.InterfaceHeaderRecord;
import jxl.write.biff.JxlWriteException;
import jxl.write.biff.MMSRecord;
import jxl.write.biff.NameRecord;
import jxl.write.biff.NineteenFourRecord;
import jxl.write.biff.ObjProjRecord;
import jxl.write.biff.PasswordRecord;
import jxl.write.biff.PrecisionRecord;
import jxl.write.biff.Prot4RevPassRecord;
import jxl.write.biff.Prot4RevRecord;
import jxl.write.biff.ProtectRecord;
import jxl.write.biff.RefreshAllRecord;
import jxl.write.biff.SharedStrings;
import jxl.write.biff.Styles;
import jxl.write.biff.SupbookRecord;
import jxl.write.biff.TabIdRecord;
import jxl.write.biff.UsesElfsRecord;
import jxl.write.biff.Window1Record;
import jxl.write.biff.WindowProtectRecord;
import jxl.write.biff.WritableFonts;
import jxl.write.biff.WritableFormattingRecords;
import jxl.write.biff.WritableSheetImpl;
import jxl.write.biff.WriteAccessRecord;

public class WritableWorkbookImpl
extends WritableWorkbook
implements ExternalSheet,
WorkbookMethods {
    private static Logger logger = Logger.getLogger(class$jxl$write$biff$WritableWorkbookImpl == null ? (class$jxl$write$biff$WritableWorkbookImpl = WritableWorkbookImpl.class$("jxl.write.biff.WritableWorkbookImpl")) : class$jxl$write$biff$WritableWorkbookImpl);
    private FormattingRecords formatRecords;
    private File outputFile;
    private ArrayList sheets;
    private Fonts fonts;
    private ExternalSheetRecord externSheet;
    private ArrayList supbooks;
    private ArrayList names;
    private HashMap nameRecords;
    private SharedStrings sharedStrings;
    private boolean closeStream;
    private boolean wbProtected;
    private WorkbookSettings settings;
    private ArrayList rcirCells;
    private DrawingGroup drawingGroup;
    private Styles styles;
    private boolean containsMacros;
    private ButtonPropertySetRecord buttonPropertySet;
    private CountryRecord countryRecord;
    private String[] addInFunctionNames;
    static /* synthetic */ Class class$jxl$write$biff$WritableWorkbookImpl;

    public WritableWorkbookImpl(OutputStream os, boolean cs, WorkbookSettings ws) throws IOException {
        this.outputFile = new File(os, ws, null);
        this.sheets = new ArrayList();
        this.sharedStrings = new SharedStrings();
        this.nameRecords = new HashMap();
        this.closeStream = cs;
        this.wbProtected = false;
        this.containsMacros = false;
        this.settings = ws;
        this.rcirCells = new ArrayList();
        this.styles = new Styles();
        WritableWorkbook.ARIAL_10_PT.uninitialize();
        WritableWorkbook.HYPERLINK_FONT.uninitialize();
        WritableWorkbook.NORMAL_STYLE.uninitialize();
        WritableWorkbook.HYPERLINK_STYLE.uninitialize();
        WritableWorkbook.HIDDEN_STYLE.uninitialize();
        DateRecord.defaultDateFormat.uninitialize();
        WritableFonts wf = new WritableFonts(this);
        this.fonts = wf;
        WritableFormattingRecords wfr = new WritableFormattingRecords(this.fonts, this.styles);
        this.formatRecords = wfr;
    }

    public WritableWorkbookImpl(OutputStream os, Workbook w, boolean cs, WorkbookSettings ws) throws IOException {
        int i;
        WorkbookParser wp;
        block12: {
            wp = (WorkbookParser)w;
            WritableWorkbook.ARIAL_10_PT.uninitialize();
            WritableWorkbook.HYPERLINK_FONT.uninitialize();
            WritableWorkbook.NORMAL_STYLE.uninitialize();
            WritableWorkbook.HYPERLINK_STYLE.uninitialize();
            WritableWorkbook.HIDDEN_STYLE.uninitialize();
            DateRecord.defaultDateFormat.uninitialize();
            this.closeStream = cs;
            this.sheets = new ArrayList();
            this.sharedStrings = new SharedStrings();
            this.nameRecords = new HashMap();
            this.fonts = wp.getFonts();
            this.formatRecords = wp.getFormattingRecords();
            this.wbProtected = false;
            this.settings = ws;
            this.rcirCells = new ArrayList();
            this.styles = new Styles();
            this.outputFile = new File(os, ws, wp.getCompoundFile());
            this.containsMacros = false;
            if (!ws.getPropertySetsDisabled()) {
                this.containsMacros = wp.containsMacros();
            }
            if (wp.getCountryRecord() != null) {
                this.countryRecord = new CountryRecord(wp.getCountryRecord());
            }
            this.addInFunctionNames = wp.getAddInFunctionNames();
            if (wp.getExternalSheetRecord() == null) break block12;
            this.externSheet = new ExternalSheetRecord(wp.getExternalSheetRecord());
            jxl.read.biff.SupbookRecord[] readsr = wp.getSupbookRecords();
            this.supbooks = new ArrayList(readsr.length);
            for (i = 0; i < readsr.length; ++i) {
                jxl.read.biff.SupbookRecord readSupbook;
                block14: {
                    block13: {
                        readSupbook = readsr[i];
                        if (readSupbook.getType() == jxl.read.biff.SupbookRecord.INTERNAL) break block13;
                        if (readSupbook.getType() != jxl.read.biff.SupbookRecord.EXTERNAL) break block14;
                    }
                    this.supbooks.add(new SupbookRecord(readSupbook, this.settings));
                    continue;
                }
                if (readSupbook.getType() == jxl.read.biff.SupbookRecord.ADDIN) continue;
                logger.warn("unsupported supbook type - ignoring");
            }
        }
        if (wp.getDrawingGroup() != null) {
            this.drawingGroup = new DrawingGroup(wp.getDrawingGroup());
        }
        if (this.containsMacros && wp.getButtonPropertySet() != null) {
            this.buttonPropertySet = new ButtonPropertySetRecord(wp.getButtonPropertySet());
        }
        if (!this.settings.getNamesDisabled()) {
            jxl.read.biff.NameRecord[] na = wp.getNameRecords();
            this.names = new ArrayList(na.length);
            for (i = 0; i < na.length; ++i) {
                if (na[i].isBiff8()) {
                    NameRecord n = new NameRecord(na[i], i);
                    this.names.add(n);
                    String name = n.getName();
                    this.nameRecords.put(name, n);
                    continue;
                }
                logger.warn("Cannot copy Biff7 name records - ignoring");
            }
        }
        this.copyWorkbook(w);
        if (this.drawingGroup != null) {
            this.drawingGroup.updateData(wp.getDrawingGroup());
        }
    }

    public WritableSheet[] getSheets() {
        WritableSheet[] sheetArray = new WritableSheet[this.getNumberOfSheets()];
        for (int i = 0; i < this.getNumberOfSheets(); ++i) {
            sheetArray[i] = this.getSheet(i);
        }
        return sheetArray;
    }

    public String[] getSheetNames() {
        String[] sheetNames = new String[this.getNumberOfSheets()];
        for (int i = 0; i < sheetNames.length; ++i) {
            sheetNames[i] = this.getSheet(i).getName();
        }
        return sheetNames;
    }

    public Sheet getReadSheet(int index) {
        return this.getSheet(index);
    }

    public WritableSheet getSheet(int index) {
        return (WritableSheet)this.sheets.get(index);
    }

    public WritableSheet getSheet(String name) {
        boolean found = false;
        Iterator i = this.sheets.iterator();
        WritableSheet s = null;
        while (i.hasNext() && !found) {
            s = (WritableSheet)i.next();
            if (!s.getName().equals(name)) continue;
            found = true;
        }
        return found ? s : null;
    }

    public int getNumberOfSheets() {
        return this.sheets.size();
    }

    public void close() throws IOException, JxlWriteException {
        this.outputFile.close(this.closeStream);
    }

    public void setOutputFile(java.io.File fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        this.outputFile.setOutputFile(fos);
    }

    private WritableSheet createSheet(String name, int index, boolean handleRefs) {
        SupbookRecord supbook;
        WritableSheetImpl w = new WritableSheetImpl(name, this.outputFile, this.formatRecords, this.sharedStrings, this.settings, this);
        int pos = index;
        if (index <= 0) {
            pos = 0;
            this.sheets.add(0, w);
        } else if (index > this.sheets.size()) {
            pos = this.sheets.size();
            this.sheets.add(w);
        } else {
            this.sheets.add(index, w);
        }
        if (handleRefs && this.externSheet != null) {
            this.externSheet.sheetInserted(pos);
        }
        if (this.supbooks != null && this.supbooks.size() > 0 && (supbook = (SupbookRecord)this.supbooks.get(0)).getType() == SupbookRecord.INTERNAL) {
            supbook.adjustInternal(this.sheets.size());
        }
        return w;
    }

    public WritableSheet createSheet(String name, int index) {
        return this.createSheet(name, index, true);
    }

    public void removeSheet(int index) {
        SupbookRecord supbook;
        int pos = index;
        if (index <= 0) {
            pos = 0;
            this.sheets.remove(0);
        } else if (index >= this.sheets.size()) {
            pos = this.sheets.size() - 1;
            this.sheets.remove(this.sheets.size() - 1);
        } else {
            this.sheets.remove(index);
        }
        if (this.externSheet != null) {
            this.externSheet.sheetRemoved(pos);
        }
        if (this.supbooks != null && this.supbooks.size() > 0 && (supbook = (SupbookRecord)this.supbooks.get(0)).getType() == SupbookRecord.INTERNAL) {
            supbook.adjustInternal(this.sheets.size());
        }
        if (this.names != null && this.names.size() > 0) {
            for (int i = 0; i < this.names.size(); ++i) {
                NameRecord n = (NameRecord)this.names.get(i);
                int oldRef = n.getSheetRef();
                if (oldRef == pos + 1) {
                    n.setSheetRef(0);
                    continue;
                }
                if (oldRef <= pos + 1) continue;
                if (oldRef < 1) {
                    oldRef = 1;
                }
                n.setSheetRef(oldRef - 1);
            }
        }
    }

    public WritableSheet moveSheet(int fromIndex, int toIndex) {
        fromIndex = Math.max(fromIndex, 0);
        fromIndex = Math.min(fromIndex, this.sheets.size() - 1);
        toIndex = Math.max(toIndex, 0);
        toIndex = Math.min(toIndex, this.sheets.size() - 1);
        WritableSheet sheet = (WritableSheet)this.sheets.remove(fromIndex);
        this.sheets.add(toIndex, sheet);
        return sheet;
    }

    public void write() throws IOException {
        int i;
        int i2;
        WritableSheetImpl wsi = null;
        for (int i3 = 0; i3 < this.getNumberOfSheets(); ++i3) {
            wsi = (WritableSheetImpl)this.getSheet(i3);
            wsi.checkMergedBorders();
        }
        if (!this.settings.getRationalizationDisabled()) {
            this.rationalize();
        }
        BOFRecord bof = new BOFRecord(BOFRecord.workbookGlobals);
        this.outputFile.write(bof);
        InterfaceHeaderRecord ihr = new InterfaceHeaderRecord();
        this.outputFile.write(ihr);
        MMSRecord mms = new MMSRecord(0, 0);
        this.outputFile.write(mms);
        InterfaceEndRecord ier = new InterfaceEndRecord();
        this.outputFile.write(ier);
        WriteAccessRecord wr = new WriteAccessRecord();
        this.outputFile.write(wr);
        CodepageRecord cp = new CodepageRecord();
        this.outputFile.write(cp);
        DSFRecord dsf = new DSFRecord();
        this.outputFile.write(dsf);
        TabIdRecord tabid = new TabIdRecord(this.getNumberOfSheets());
        this.outputFile.write(tabid);
        if (this.containsMacros) {
            ObjProjRecord objproj = new ObjProjRecord();
            this.outputFile.write(objproj);
        }
        if (this.buttonPropertySet != null) {
            this.outputFile.write(this.buttonPropertySet);
        }
        FunctionGroupCountRecord fgcr = new FunctionGroupCountRecord();
        this.outputFile.write(fgcr);
        WindowProtectRecord wpr = new WindowProtectRecord(false);
        this.outputFile.write(wpr);
        ProtectRecord pr = new ProtectRecord(this.wbProtected);
        this.outputFile.write(pr);
        PasswordRecord pw = new PasswordRecord(null);
        this.outputFile.write(pw);
        Prot4RevRecord p4r = new Prot4RevRecord(false);
        this.outputFile.write(p4r);
        Prot4RevPassRecord p4rp = new Prot4RevPassRecord();
        this.outputFile.write(p4rp);
        Window1Record w1r = new Window1Record();
        this.outputFile.write(w1r);
        BackupRecord bkr = new BackupRecord(false);
        this.outputFile.write(bkr);
        HideobjRecord ho = new HideobjRecord(false);
        this.outputFile.write(ho);
        NineteenFourRecord nf = new NineteenFourRecord(false);
        this.outputFile.write(nf);
        PrecisionRecord pc = new PrecisionRecord(false);
        this.outputFile.write(pc);
        RefreshAllRecord rar = new RefreshAllRecord(false);
        this.outputFile.write(rar);
        BookboolRecord bb = new BookboolRecord(true);
        this.outputFile.write(bb);
        this.fonts.write(this.outputFile);
        this.formatRecords.write(this.outputFile);
        if (this.formatRecords.getPalette() != null) {
            this.outputFile.write(this.formatRecords.getPalette());
        }
        UsesElfsRecord uer = new UsesElfsRecord();
        this.outputFile.write(uer);
        int[] boundsheetPos = new int[this.getNumberOfSheets()];
        WritableSheet sheet = null;
        for (i2 = 0; i2 < this.getNumberOfSheets(); ++i2) {
            boundsheetPos[i2] = this.outputFile.getPos();
            sheet = this.getSheet(i2);
            BoundsheetRecord br = new BoundsheetRecord(sheet.getName());
            if (sheet.getSettings().isHidden()) {
                br.setHidden();
            }
            if (((WritableSheetImpl)this.sheets.get(i2)).isChartOnly()) {
                br.setChartOnly();
            }
            this.outputFile.write(br);
        }
        if (this.countryRecord == null) {
            CountryCode lang = CountryCode.getCountryCode(this.settings.getExcelDisplayLanguage());
            if (lang == CountryCode.UNKNOWN) {
                logger.warn("Unknown country code " + this.settings.getExcelDisplayLanguage() + " using " + CountryCode.USA.getCode());
                lang = CountryCode.USA;
            }
            CountryCode region = CountryCode.getCountryCode(this.settings.getExcelRegionalSettings());
            this.countryRecord = new CountryRecord(lang, region);
            if (region == CountryCode.UNKNOWN) {
                logger.warn("Unknown country code " + this.settings.getExcelDisplayLanguage() + " using " + CountryCode.UK.getCode());
                region = CountryCode.UK;
            }
        }
        this.outputFile.write(this.countryRecord);
        if (this.addInFunctionNames != null && this.addInFunctionNames.length > 0) {
            SupbookRecord supbook = new SupbookRecord();
            this.outputFile.write(supbook);
            for (int i4 = 0; i4 < this.addInFunctionNames.length; ++i4) {
                ExternalNameRecord enr = new ExternalNameRecord(this.addInFunctionNames[i4]);
                this.outputFile.write(enr);
            }
        }
        if (this.externSheet != null) {
            for (i2 = 0; i2 < this.supbooks.size(); ++i2) {
                SupbookRecord supbook = (SupbookRecord)this.supbooks.get(i2);
                this.outputFile.write(supbook);
            }
            this.outputFile.write(this.externSheet);
        }
        if (this.names != null) {
            for (i2 = 0; i2 < this.names.size(); ++i2) {
                NameRecord n = (NameRecord)this.names.get(i2);
                this.outputFile.write(n);
            }
        }
        if (this.drawingGroup != null) {
            this.drawingGroup.write(this.outputFile);
        }
        this.sharedStrings.write(this.outputFile);
        EOFRecord eof = new EOFRecord();
        this.outputFile.write(eof);
        boolean sheetSelected = false;
        WritableSheetImpl wsheet = null;
        for (i = 0; i < this.getNumberOfSheets() && !sheetSelected; ++i) {
            wsheet = (WritableSheetImpl)this.getSheet(i);
            if (!wsheet.getSettings().isSelected()) continue;
            sheetSelected = true;
        }
        if (!sheetSelected) {
            wsheet = (WritableSheetImpl)this.getSheet(0);
            wsheet.getSettings().setSelected(true);
        }
        for (i = 0; i < this.getNumberOfSheets(); ++i) {
            this.outputFile.setData(IntegerHelper.getFourBytes(this.outputFile.getPos()), boundsheetPos[i] + 4);
            wsheet = (WritableSheetImpl)this.getSheet(i);
            wsheet.write();
        }
    }

    private void copyWorkbook(Workbook w) {
        int numSheets = w.getNumberOfSheets();
        this.wbProtected = w.isProtected();
        Sheet s = null;
        WritableSheetImpl ws = null;
        for (int i = 0; i < numSheets; ++i) {
            s = w.getSheet(i);
            ws = (WritableSheetImpl)this.createSheet(s.getName(), i, false);
            ws.copy(s);
        }
    }

    public void copySheet(int s, String name, int index) {
        WritableSheet sheet = this.getSheet(s);
        WritableSheetImpl ws = (WritableSheetImpl)this.createSheet(name, index);
        ws.copy(sheet);
    }

    public void copySheet(String s, String name, int index) {
        WritableSheet sheet = this.getSheet(s);
        WritableSheetImpl ws = (WritableSheetImpl)this.createSheet(name, index);
        ws.copy(sheet);
    }

    public void setProtected(boolean prot) {
        this.wbProtected = prot;
    }

    private void rationalize() {
        IndexMapping fontMapping = this.formatRecords.rationalizeFonts();
        IndexMapping formatMapping = this.formatRecords.rationalizeDisplayFormats();
        IndexMapping xfMapping = this.formatRecords.rationalize(fontMapping, formatMapping);
        WritableSheetImpl wsi = null;
        for (int i = 0; i < this.sheets.size(); ++i) {
            wsi = (WritableSheetImpl)this.sheets.get(i);
            wsi.rationalize(xfMapping, fontMapping, formatMapping);
        }
    }

    public String getExternalSheetName(int index) {
        int supbookIndex = this.externSheet.getSupbookIndex(index);
        SupbookRecord sr = (SupbookRecord)this.supbooks.get(supbookIndex);
        int firstTab = this.externSheet.getFirstTabIndex(index);
        if (sr.getType() == SupbookRecord.INTERNAL) {
            WritableSheet ws = this.getSheet(firstTab);
            return ws.getName();
        }
        if (sr.getType() == SupbookRecord.EXTERNAL) {
            String name = sr.getFileName() + sr.getSheetName(firstTab);
            return name;
        }
        return "[UNKNOWN]";
    }

    public String getLastExternalSheetName(int index) {
        int supbookIndex = this.externSheet.getSupbookIndex(index);
        SupbookRecord sr = (SupbookRecord)this.supbooks.get(supbookIndex);
        int lastTab = this.externSheet.getLastTabIndex(index);
        if (sr.getType() == SupbookRecord.INTERNAL) {
            WritableSheet ws = this.getSheet(lastTab);
            return ws.getName();
        }
        if (sr.getType() == SupbookRecord.EXTERNAL) {
            Assert.verify(false);
        }
        return "[UNKNOWN]";
    }

    public jxl.read.biff.BOFRecord getWorkbookBof() {
        return null;
    }

    public int getExternalSheetIndex(int index) {
        if (this.externSheet == null) {
            return index;
        }
        Assert.verify(this.externSheet != null);
        int firstTab = this.externSheet.getFirstTabIndex(index);
        return firstTab;
    }

    public int getLastExternalSheetIndex(int index) {
        if (this.externSheet == null) {
            return index;
        }
        Assert.verify(this.externSheet != null);
        int lastTab = this.externSheet.getLastTabIndex(index);
        return lastTab;
    }

    public int getExternalSheetIndex(String sheetName) {
        if (this.externSheet == null) {
            this.externSheet = new ExternalSheetRecord();
            this.supbooks = new ArrayList();
            this.supbooks.add(new SupbookRecord(this.getNumberOfSheets(), this.settings));
        }
        boolean found = false;
        Iterator i = this.sheets.iterator();
        int sheetpos = 0;
        WritableSheetImpl s = null;
        while (i.hasNext() && !found) {
            s = (WritableSheetImpl)i.next();
            if (s.getName().equals(sheetName)) {
                found = true;
                continue;
            }
            ++sheetpos;
        }
        if (found) {
            SupbookRecord supbook = (SupbookRecord)this.supbooks.get(0);
            if (supbook.getType() != SupbookRecord.INTERNAL || supbook.getNumberOfSheets() != this.getNumberOfSheets()) {
                logger.warn("Cannot find sheet " + sheetName + " in supbook record");
            }
            return this.externSheet.getIndex(0, sheetpos);
        }
        int closeSquareBracketsIndex = sheetName.lastIndexOf(93);
        int openSquareBracketsIndex = sheetName.lastIndexOf(91);
        if (closeSquareBracketsIndex == -1 || openSquareBracketsIndex == -1) {
            return -1;
        }
        String worksheetName = sheetName.substring(closeSquareBracketsIndex + 1);
        String workbookName = sheetName.substring(openSquareBracketsIndex + 1, closeSquareBracketsIndex);
        String path = sheetName.substring(0, openSquareBracketsIndex);
        String fileName = path + workbookName;
        boolean supbookFound = false;
        SupbookRecord externalSupbook = null;
        int supbookIndex = -1;
        for (int ind = 0; ind < this.supbooks.size() && !supbookFound; ++ind) {
            externalSupbook = (SupbookRecord)this.supbooks.get(ind);
            if (externalSupbook.getType() != SupbookRecord.EXTERNAL || !externalSupbook.getFileName().equals(fileName)) continue;
            supbookFound = true;
            supbookIndex = ind;
        }
        if (!supbookFound) {
            externalSupbook = new SupbookRecord(fileName, this.settings);
            supbookIndex = this.supbooks.size();
            this.supbooks.add(externalSupbook);
        }
        int sheetIndex = externalSupbook.getSheetIndex(worksheetName);
        return this.externSheet.getIndex(supbookIndex, sheetIndex);
    }

    public int getLastExternalSheetIndex(String sheetName) {
        if (this.externSheet == null) {
            this.externSheet = new ExternalSheetRecord();
            this.supbooks = new ArrayList();
            this.supbooks.add(new SupbookRecord(this.getNumberOfSheets(), this.settings));
        }
        boolean found = false;
        Iterator i = this.sheets.iterator();
        int sheetpos = 0;
        WritableSheetImpl s = null;
        while (i.hasNext() && !found) {
            s = (WritableSheetImpl)i.next();
            if (s.getName().equals(sheetName)) {
                found = true;
                continue;
            }
            ++sheetpos;
        }
        if (!found) {
            return -1;
        }
        SupbookRecord supbook = (SupbookRecord)this.supbooks.get(0);
        Assert.verify(supbook.getType() == SupbookRecord.INTERNAL && supbook.getNumberOfSheets() == this.getNumberOfSheets());
        return this.externSheet.getIndex(0, sheetpos);
    }

    public void setColourRGB(Colour c, int r, int g, int b) {
        this.formatRecords.setColourRGB(c, r, g, b);
    }

    public RGB getColourRGB(Colour c) {
        return this.formatRecords.getColourRGB(c);
    }

    public String getName(int index) {
        Assert.verify(index >= 0 && index < this.names.size());
        NameRecord n = (NameRecord)this.names.get(index);
        return n.getName();
    }

    public int getNameIndex(String name) {
        NameRecord nr = (NameRecord)this.nameRecords.get(name);
        return nr != null ? nr.getIndex() : -1;
    }

    void addRCIRCell(CellValue cv) {
        this.rcirCells.add(cv);
    }

    void columnInserted(WritableSheetImpl s, int col) {
        int externalSheetIndex = this.getExternalSheetIndex(s.getName());
        Iterator i = this.rcirCells.iterator();
        while (i.hasNext()) {
            CellValue cv = (CellValue)i.next();
            cv.columnInserted(s, externalSheetIndex, col);
        }
    }

    void columnRemoved(WritableSheetImpl s, int col) {
        int externalSheetIndex = this.getExternalSheetIndex(s.getName());
        Iterator i = this.rcirCells.iterator();
        while (i.hasNext()) {
            CellValue cv = (CellValue)i.next();
            cv.columnRemoved(s, externalSheetIndex, col);
        }
    }

    void rowInserted(WritableSheetImpl s, int row) {
        int externalSheetIndex = this.getExternalSheetIndex(s.getName());
        Iterator i = this.rcirCells.iterator();
        while (i.hasNext()) {
            CellValue cv = (CellValue)i.next();
            cv.rowInserted(s, externalSheetIndex, row);
        }
    }

    void rowRemoved(WritableSheetImpl s, int row) {
        int externalSheetIndex = this.getExternalSheetIndex(s.getName());
        Iterator i = this.rcirCells.iterator();
        while (i.hasNext()) {
            CellValue cv = (CellValue)i.next();
            cv.rowRemoved(s, externalSheetIndex, row);
        }
    }

    public WritableCell findCellByName(String name) {
        NameRecord nr = (NameRecord)this.nameRecords.get(name);
        if (nr == null) {
            return null;
        }
        NameRecord.NameRange[] ranges = nr.getRanges();
        int sheetIndex = this.getExternalSheetIndex(ranges[0].getExternalSheet());
        WritableSheet s = this.getSheet(sheetIndex);
        WritableCell cell = s.getWritableCell(ranges[0].getFirstColumn(), ranges[0].getFirstRow());
        return cell;
    }

    public Range[] findByName(String name) {
        NameRecord nr = (NameRecord)this.nameRecords.get(name);
        if (nr == null) {
            return null;
        }
        NameRecord.NameRange[] ranges = nr.getRanges();
        Range[] cellRanges = new Range[ranges.length];
        for (int i = 0; i < ranges.length; ++i) {
            cellRanges[i] = new RangeImpl(this, this.getExternalSheetIndex(ranges[i].getExternalSheet()), ranges[i].getFirstColumn(), ranges[i].getFirstRow(), this.getLastExternalSheetIndex(ranges[i].getExternalSheet()), ranges[i].getLastColumn(), ranges[i].getLastRow());
        }
        return cellRanges;
    }

    void addDrawing(DrawingGroupObject d) {
        if (this.drawingGroup == null) {
            this.drawingGroup = new DrawingGroup(Origin.WRITE);
        }
        this.drawingGroup.add(d);
    }

    void removeDrawing(Drawing d) {
        Assert.verify(this.drawingGroup != null);
        this.drawingGroup.remove(d);
    }

    DrawingGroup getDrawingGroup() {
        return this.drawingGroup;
    }

    public String[] getRangeNames() {
        String[] n = new String[this.names.size()];
        for (int i = 0; i < this.names.size(); ++i) {
            NameRecord nr = (NameRecord)this.names.get(i);
            n[i] = nr.getName();
        }
        return n;
    }

    Styles getStyles() {
        return this.styles;
    }

    public void addNameArea(String name, WritableSheet sheet, int firstCol, int firstRow, int lastCol, int lastRow) {
        if (this.names == null) {
            this.names = new ArrayList();
        }
        int externalSheetIndex = this.getExternalSheetIndex(sheet.getName());
        NameRecord nr = new NameRecord(name, this.names.size(), externalSheetIndex, firstRow, lastRow, firstCol, lastCol);
        this.names.add(nr);
        this.nameRecords.put(name, nr);
    }

    WorkbookSettings getSettings() {
        return this.settings;
    }

    public WritableCell getWritableCell(String loc) {
        WritableSheet s = this.getSheet(CellReferenceHelper.getSheet(loc));
        return s.getWritableCell(loc);
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

