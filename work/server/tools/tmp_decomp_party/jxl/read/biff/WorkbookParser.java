/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Assert;
import common.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.biff.CellReferenceHelper;
import jxl.biff.DisplayFormat;
import jxl.biff.FontRecord;
import jxl.biff.Fonts;
import jxl.biff.FormatRecord;
import jxl.biff.FormattingRecords;
import jxl.biff.NumFormatRecordsException;
import jxl.biff.PaletteRecord;
import jxl.biff.RangeImpl;
import jxl.biff.RecordData;
import jxl.biff.Type;
import jxl.biff.WorkbookMethods;
import jxl.biff.WritableRecordData;
import jxl.biff.XFRecord;
import jxl.biff.drawing.DrawingGroup;
import jxl.biff.drawing.MsoDrawingGroupRecord;
import jxl.biff.drawing.Origin;
import jxl.biff.formula.ExternalSheet;
import jxl.read.biff.BOFRecord;
import jxl.read.biff.BiffException;
import jxl.read.biff.BoundsheetRecord;
import jxl.read.biff.ButtonPropertySetRecord;
import jxl.read.biff.CodepageRecord;
import jxl.read.biff.CompoundFile;
import jxl.read.biff.CountryRecord;
import jxl.read.biff.ExternalNameRecord;
import jxl.read.biff.ExternalSheetRecord;
import jxl.read.biff.File;
import jxl.read.biff.NameRecord;
import jxl.read.biff.NineteenFourRecord;
import jxl.read.biff.PasswordException;
import jxl.read.biff.ProtectRecord;
import jxl.read.biff.Record;
import jxl.read.biff.SSTRecord;
import jxl.read.biff.SheetImpl;
import jxl.read.biff.SupbookRecord;

public class WorkbookParser
extends Workbook
implements ExternalSheet,
WorkbookMethods {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$WorkbookParser == null ? (class$jxl$read$biff$WorkbookParser = WorkbookParser.class$("jxl.read.biff.WorkbookParser")) : class$jxl$read$biff$WorkbookParser);
    private File excelFile;
    private int bofs;
    private boolean nineteenFour;
    private SSTRecord sharedStrings;
    private ArrayList boundsheets;
    private FormattingRecords formattingRecords;
    private Fonts fonts;
    private ArrayList sheets;
    private SheetImpl lastSheet;
    private int lastSheetIndex;
    private HashMap namedRecords;
    private ArrayList nameTable;
    private ArrayList addInFunctions;
    private ExternalSheetRecord externSheet;
    private ArrayList supbooks;
    private BOFRecord workbookBof;
    private MsoDrawingGroupRecord msoDrawingGroup;
    private ButtonPropertySetRecord buttonPropertySet;
    private boolean wbProtected;
    private boolean containsMacros;
    private WorkbookSettings settings;
    private DrawingGroup drawingGroup;
    private CountryRecord countryRecord;
    static /* synthetic */ Class class$jxl$read$biff$WorkbookParser;

    public WorkbookParser(File f, WorkbookSettings s) {
        this.excelFile = f;
        this.boundsheets = new ArrayList(10);
        this.fonts = new Fonts();
        this.formattingRecords = new FormattingRecords(this.fonts);
        this.sheets = new ArrayList(10);
        this.supbooks = new ArrayList(10);
        this.namedRecords = new HashMap();
        this.lastSheetIndex = -1;
        this.wbProtected = false;
        this.containsMacros = false;
        this.settings = s;
    }

    public Sheet[] getSheets() {
        Sheet[] sheetArray = new Sheet[this.getNumberOfSheets()];
        return this.sheets.toArray(sheetArray);
    }

    public Sheet getReadSheet(int index) {
        return this.getSheet(index);
    }

    public Sheet getSheet(int index) {
        if (this.lastSheet != null && this.lastSheetIndex == index) {
            return this.lastSheet;
        }
        if (this.lastSheet != null) {
            this.lastSheet.clear();
            if (!this.settings.getGCDisabled()) {
                System.gc();
            }
        }
        this.lastSheet = (SheetImpl)this.sheets.get(index);
        this.lastSheetIndex = index;
        this.lastSheet.readSheet();
        return this.lastSheet;
    }

    public Sheet getSheet(String name) {
        int pos = 0;
        boolean found = false;
        Iterator i = this.boundsheets.iterator();
        BoundsheetRecord br = null;
        while (i.hasNext() && !found) {
            br = (BoundsheetRecord)i.next();
            if (br.getName().equals(name)) {
                found = true;
                continue;
            }
            ++pos;
        }
        return found ? this.getSheet(pos) : null;
    }

    public String[] getSheetNames() {
        String[] names = new String[this.boundsheets.size()];
        BoundsheetRecord br = null;
        for (int i = 0; i < names.length; ++i) {
            br = (BoundsheetRecord)this.boundsheets.get(i);
            names[i] = br.getName();
        }
        return names;
    }

    public int getExternalSheetIndex(int index) {
        if (this.workbookBof.isBiff7()) {
            return index;
        }
        Assert.verify(this.externSheet != null);
        int firstTab = this.externSheet.getFirstTabIndex(index);
        return firstTab;
    }

    public int getLastExternalSheetIndex(int index) {
        if (this.workbookBof.isBiff7()) {
            return index;
        }
        Assert.verify(this.externSheet != null);
        int lastTab = this.externSheet.getLastTabIndex(index);
        return lastTab;
    }

    public String getExternalSheetName(int index) {
        if (this.workbookBof.isBiff7()) {
            BoundsheetRecord br = (BoundsheetRecord)this.boundsheets.get(index);
            return br.getName();
        }
        int supbookIndex = this.externSheet.getSupbookIndex(index);
        SupbookRecord sr = (SupbookRecord)this.supbooks.get(supbookIndex);
        int firstTab = this.externSheet.getFirstTabIndex(index);
        int lastTab = this.externSheet.getLastTabIndex(index);
        String firstTabName = "";
        String lastTabName = "";
        if (sr.getType() == SupbookRecord.INTERNAL) {
            BoundsheetRecord br;
            if (firstTab == 65535) {
                firstTabName = "#REF";
            } else {
                br = (BoundsheetRecord)this.boundsheets.get(firstTab);
                firstTabName = br.getName();
            }
            if (lastTab == 65535) {
                lastTabName = "#REF";
            } else {
                br = (BoundsheetRecord)this.boundsheets.get(lastTab);
                lastTabName = br.getName();
            }
            String sheetName = firstTab == lastTab ? firstTabName : firstTabName + ':' + lastTabName;
            return sheetName.indexOf(32) == -1 ? sheetName : '\'' + sheetName + '\'';
        }
        if (sr.getType() == SupbookRecord.EXTERNAL) {
            StringBuffer sb = new StringBuffer();
            java.io.File fl = new java.io.File(sr.getFileName());
            sb.append("'");
            sb.append(fl.getAbsolutePath());
            sb.append("[");
            sb.append(fl.getName());
            sb.append("]");
            sb.append(firstTab == 65535 ? "#REF" : sr.getSheetName(firstTab));
            if (lastTab != firstTab) {
                sb.append(sr.getSheetName(lastTab));
            }
            sb.append("'");
            return sb.toString();
        }
        return "[UNKNOWN]";
    }

    public String getLastExternalSheetName(int index) {
        if (this.workbookBof.isBiff7()) {
            BoundsheetRecord br = (BoundsheetRecord)this.boundsheets.get(index);
            return br.getName();
        }
        int supbookIndex = this.externSheet.getSupbookIndex(index);
        SupbookRecord sr = (SupbookRecord)this.supbooks.get(supbookIndex);
        int lastTab = this.externSheet.getLastTabIndex(index);
        if (sr.getType() == SupbookRecord.INTERNAL) {
            if (lastTab == 65535) {
                return "#REF";
            }
            BoundsheetRecord br = (BoundsheetRecord)this.boundsheets.get(lastTab);
            return br.getName();
        }
        if (sr.getType() == SupbookRecord.EXTERNAL) {
            StringBuffer sb = new StringBuffer();
            java.io.File fl = new java.io.File(sr.getFileName());
            sb.append("'");
            sb.append(fl.getAbsolutePath());
            sb.append("[");
            sb.append(fl.getName());
            sb.append("]");
            sb.append(lastTab == 65535 ? "#REF" : sr.getSheetName(lastTab));
            sb.append("'");
            return sb.toString();
        }
        return "[UNKNOWN]";
    }

    public int getNumberOfSheets() {
        return this.sheets.size();
    }

    public void close() {
        if (this.lastSheet != null) {
            this.lastSheet.clear();
        }
        this.excelFile.clear();
        if (!this.settings.getGCDisabled()) {
            System.gc();
        }
    }

    final void addSheet(Sheet s) {
        this.sheets.add(s);
    }

    protected void parse() throws BiffException, PasswordException {
        BOFRecord bof;
        Record r = null;
        this.workbookBof = bof = new BOFRecord(this.excelFile.next());
        ++this.bofs;
        if (!bof.isBiff8() && !bof.isBiff7()) {
            throw new BiffException(BiffException.unrecognizedBiffVersion);
        }
        if (!bof.isWorkbookGlobals()) {
            throw new BiffException(BiffException.expectedGlobals);
        }
        ArrayList<Record> continueRecords = new ArrayList<Record>();
        this.nameTable = new ArrayList();
        this.addInFunctions = new ArrayList();
        while (this.bofs == 1) {
            WritableRecordData fr;
            RecordData nr;
            Record nextrec;
            r = this.excelFile.next();
            if (r.getType() == Type.SST) {
                continueRecords.clear();
                nextrec = this.excelFile.peek();
                while (nextrec.getType() == Type.CONTINUE) {
                    continueRecords.add(this.excelFile.next());
                    nextrec = this.excelFile.peek();
                }
                Record[] records = new Record[continueRecords.size()];
                records = continueRecords.toArray(records);
                this.sharedStrings = new SSTRecord(r, records, this.settings);
                continue;
            }
            if (r.getType() == Type.FILEPASS) {
                throw new PasswordException();
            }
            if (r.getType() == Type.NAME) {
                nr = null;
                nr = bof.isBiff8() ? new NameRecord(r, this.settings, this.namedRecords.size()) : new NameRecord(r, this.settings, this.namedRecords.size(), NameRecord.biff7);
                this.namedRecords.put(((NameRecord)nr).getName(), nr);
                this.nameTable.add(nr);
                continue;
            }
            if (r.getType() == Type.FONT) {
                fr = null;
                fr = bof.isBiff8() ? new FontRecord(r, this.settings) : new FontRecord(r, this.settings, FontRecord.biff7);
                this.fonts.addFont((FontRecord)fr);
                continue;
            }
            if (r.getType() == Type.PALETTE) {
                PaletteRecord palette = new PaletteRecord(r);
                this.formattingRecords.setPalette(palette);
                continue;
            }
            if (r.getType() == Type.NINETEENFOUR) {
                nr = new NineteenFourRecord(r);
                this.nineteenFour = ((NineteenFourRecord)nr).is1904();
                continue;
            }
            if (r.getType() == Type.FORMAT) {
                fr = null;
                fr = bof.isBiff8() ? new FormatRecord(r, this.settings, FormatRecord.biff8) : new FormatRecord(r, this.settings, FormatRecord.biff7);
                try {
                    this.formattingRecords.addFormat((DisplayFormat)((Object)fr));
                }
                catch (NumFormatRecordsException e) {
                    e.printStackTrace();
                    Assert.verify(false, e.getMessage());
                }
                continue;
            }
            if (r.getType() == Type.XF) {
                XFRecord xfr = null;
                xfr = bof.isBiff8() ? new XFRecord(r, this.settings, XFRecord.biff8) : new XFRecord(r, this.settings, XFRecord.biff7);
                try {
                    this.formattingRecords.addStyle(xfr);
                }
                catch (NumFormatRecordsException e) {
                    Assert.verify(false, e.getMessage());
                }
                continue;
            }
            if (r.getType() == Type.BOUNDSHEET) {
                BoundsheetRecord br = null;
                br = bof.isBiff8() ? new BoundsheetRecord(r) : new BoundsheetRecord(r, BoundsheetRecord.biff7);
                if (br.isSheet()) {
                    this.boundsheets.add(br);
                    continue;
                }
                if (!br.isChart() || this.settings.getDrawingsDisabled()) continue;
                this.boundsheets.add(br);
                continue;
            }
            if (r.getType() == Type.EXTERNSHEET) {
                if (bof.isBiff8()) {
                    this.externSheet = new ExternalSheetRecord(r, this.settings);
                    continue;
                }
                this.externSheet = new ExternalSheetRecord(r, this.settings, ExternalSheetRecord.biff7);
                continue;
            }
            if (r.getType() == Type.CODEPAGE) {
                CodepageRecord cr = new CodepageRecord(r);
                this.settings.setCharacterSet(cr.getCharacterSet());
                continue;
            }
            if (r.getType() == Type.SUPBOOK) {
                nextrec = this.excelFile.peek();
                while (nextrec.getType() == Type.CONTINUE) {
                    r.addContinueRecord(this.excelFile.next());
                    nextrec = this.excelFile.peek();
                }
                SupbookRecord sr = new SupbookRecord(r, this.settings);
                this.supbooks.add(sr);
                continue;
            }
            if (r.getType() == Type.EXTERNNAME) {
                ExternalNameRecord enr = new ExternalNameRecord(r, this.settings);
                if (!enr.isAddInFunction()) continue;
                this.addInFunctions.add(enr.getName());
                continue;
            }
            if (r.getType() == Type.PROTECT) {
                ProtectRecord pr = new ProtectRecord(r);
                this.wbProtected = pr.isProtected();
                continue;
            }
            if (r.getType() == Type.OBJPROJ) {
                this.containsMacros = true;
                continue;
            }
            if (r.getType() == Type.COUNTRY) {
                this.countryRecord = new CountryRecord(r);
                continue;
            }
            if (r.getType() == Type.MSODRAWINGGROUP) {
                if (this.settings.getDrawingsDisabled()) continue;
                this.msoDrawingGroup = new MsoDrawingGroupRecord(r);
                if (this.drawingGroup == null) {
                    this.drawingGroup = new DrawingGroup(Origin.READ);
                }
                this.drawingGroup.add(this.msoDrawingGroup);
                nextrec = this.excelFile.peek();
                while (nextrec.getType() == Type.CONTINUE) {
                    this.drawingGroup.add(this.excelFile.next());
                    nextrec = this.excelFile.peek();
                }
                continue;
            }
            if (r.getType() == Type.BUTTONPROPERTYSET) {
                this.buttonPropertySet = new ButtonPropertySetRecord(r);
                continue;
            }
            if (r.getType() != Type.EOF) continue;
            --this.bofs;
        }
        bof = null;
        if (this.excelFile.hasNext() && (r = this.excelFile.next()).getType() == Type.BOF) {
            bof = new BOFRecord(r);
        }
        while (bof != null && this.getNumberOfSheets() < this.boundsheets.size()) {
            BoundsheetRecord br;
            SheetImpl s;
            if (!bof.isBiff8() && !bof.isBiff7()) {
                throw new BiffException(BiffException.unrecognizedBiffVersion);
            }
            if (bof.isWorksheet()) {
                s = new SheetImpl(this.excelFile, this.sharedStrings, this.formattingRecords, bof, this.workbookBof, this.nineteenFour, this);
                br = (BoundsheetRecord)this.boundsheets.get(this.getNumberOfSheets());
                s.setName(br.getName());
                s.setHidden(br.isHidden());
                this.addSheet(s);
            } else if (bof.isChart()) {
                s = new SheetImpl(this.excelFile, this.sharedStrings, this.formattingRecords, bof, this.workbookBof, this.nineteenFour, this);
                br = (BoundsheetRecord)this.boundsheets.get(this.getNumberOfSheets());
                s.setName(br.getName());
                s.setHidden(br.isHidden());
                this.addSheet(s);
            } else {
                logger.warn("BOF is unrecognized");
                while (this.excelFile.hasNext() && r.getType() != Type.EOF) {
                    r = this.excelFile.next();
                }
            }
            bof = null;
            if (!this.excelFile.hasNext() || (r = this.excelFile.next()).getType() != Type.BOF) continue;
            bof = new BOFRecord(r);
        }
    }

    public FormattingRecords getFormattingRecords() {
        return this.formattingRecords;
    }

    public ExternalSheetRecord getExternalSheetRecord() {
        return this.externSheet;
    }

    public MsoDrawingGroupRecord getMsoDrawingGroupRecord() {
        return this.msoDrawingGroup;
    }

    public SupbookRecord[] getSupbookRecords() {
        SupbookRecord[] sr = new SupbookRecord[this.supbooks.size()];
        return this.supbooks.toArray(sr);
    }

    public NameRecord[] getNameRecords() {
        NameRecord[] na = new NameRecord[this.nameTable.size()];
        return this.nameTable.toArray(na);
    }

    public Fonts getFonts() {
        return this.fonts;
    }

    public Cell getCell(String loc) {
        Sheet s = this.getSheet(CellReferenceHelper.getSheet(loc));
        return s.getCell(loc);
    }

    public Cell findCellByName(String name) {
        NameRecord nr = (NameRecord)this.namedRecords.get(name);
        if (nr == null) {
            return null;
        }
        NameRecord.NameRange[] ranges = nr.getRanges();
        Sheet s = this.getSheet(ranges[0].getExternalSheet());
        Cell cell = s.getCell(ranges[0].getFirstColumn(), ranges[0].getFirstRow());
        return cell;
    }

    public Range[] findByName(String name) {
        NameRecord nr = (NameRecord)this.namedRecords.get(name);
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

    public String[] getRangeNames() {
        Object[] keys = this.namedRecords.keySet().toArray();
        String[] names = new String[keys.length];
        System.arraycopy(keys, 0, names, 0, keys.length);
        return names;
    }

    public BOFRecord getWorkbookBof() {
        return this.workbookBof;
    }

    public boolean isProtected() {
        return this.wbProtected;
    }

    public WorkbookSettings getSettings() {
        return this.settings;
    }

    public int getExternalSheetIndex(String sheetName) {
        return 0;
    }

    public int getLastExternalSheetIndex(String sheetName) {
        return 0;
    }

    public String getName(int index) {
        Assert.verify(index >= 0 && index < this.nameTable.size());
        return ((NameRecord)this.nameTable.get(index)).getName();
    }

    public int getNameIndex(String name) {
        NameRecord nr = (NameRecord)this.namedRecords.get(name);
        return nr != null ? nr.getIndex() : 0;
    }

    public DrawingGroup getDrawingGroup() {
        return this.drawingGroup;
    }

    public CompoundFile getCompoundFile() {
        return this.excelFile.getCompoundFile();
    }

    public boolean containsMacros() {
        return this.containsMacros;
    }

    public ButtonPropertySetRecord getButtonPropertySet() {
        return this.buttonPropertySet;
    }

    public CountryRecord getCountryRecord() {
        return this.countryRecord;
    }

    public String[] getAddInFunctionNames() {
        String[] addins = new String[]{};
        return this.addInFunctions.toArray(addins);
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

