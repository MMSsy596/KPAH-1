/*
 * Decompiled with CFR 0.152.
 */
package jxl;

import common.Logger;
import java.util.HashMap;
import java.util.Locale;
import jxl.biff.CountryCode;
import jxl.biff.formula.FunctionNames;

public final class WorkbookSettings {
    private static Logger logger = Logger.getLogger(class$jxl$WorkbookSettings == null ? (class$jxl$WorkbookSettings = WorkbookSettings.class$("jxl.WorkbookSettings")) : class$jxl$WorkbookSettings);
    private int initialFileSize = 0x500000;
    private int arrayGrowSize = 0x100000;
    private boolean drawingsDisabled;
    private boolean namesDisabled;
    private boolean formulaReferenceAdjustDisabled;
    private boolean gcDisabled;
    private boolean rationalizationDisabled;
    private boolean mergedCellCheckingDisabled;
    private boolean propertySetsDisabled;
    private boolean cellValidationDisabled;
    private boolean ignoreBlankCells;
    private Locale locale;
    private FunctionNames functionNames;
    private String encoding;
    private int characterSet;
    private String excelDisplayLanguage;
    private String excelRegionalSettings;
    private HashMap localeFunctionNames = new HashMap();
    private static final int DEFAULT_INITIAL_FILE_SIZE = 0x500000;
    private static final int DEFAULT_ARRAY_GROW_SIZE = 0x100000;
    static /* synthetic */ Class class$jxl$WorkbookSettings;

    public WorkbookSettings() {
        this.excelDisplayLanguage = CountryCode.USA.getCode();
        this.excelRegionalSettings = CountryCode.UK.getCode();
        try {
            boolean suppressWarnings = Boolean.getBoolean("jxl.nowarnings");
            this.setSuppressWarnings(suppressWarnings);
            this.drawingsDisabled = Boolean.getBoolean("jxl.nodrawings");
            this.namesDisabled = Boolean.getBoolean("jxl.nonames");
            this.gcDisabled = Boolean.getBoolean("jxl.nogc");
            this.rationalizationDisabled = Boolean.getBoolean("jxl.norat");
            this.mergedCellCheckingDisabled = Boolean.getBoolean("jxl.nomergedcellchecks");
            this.formulaReferenceAdjustDisabled = Boolean.getBoolean("jxl.noformulaadjust");
            this.propertySetsDisabled = Boolean.getBoolean("jxl.nopropertysets");
            this.ignoreBlankCells = Boolean.getBoolean("jxl.ignoreblanks");
            this.cellValidationDisabled = Boolean.getBoolean("jxl.nocellvalidation");
            this.encoding = System.getProperty("file.encoding");
        }
        catch (SecurityException e) {
            logger.warn("Error accessing system properties.", e);
        }
        try {
            this.locale = System.getProperty("jxl.lang") == null || System.getProperty("jxl.country") == null ? Locale.getDefault() : new Locale(System.getProperty("jxl.lang"), System.getProperty("jxl.country"));
            if (System.getProperty("jxl.encoding") != null) {
                this.encoding = System.getProperty("jxl.encoding");
            }
        }
        catch (SecurityException e) {
            logger.warn("Error accessing system properties.", e);
            this.locale = Locale.getDefault();
        }
    }

    public void setArrayGrowSize(int sz) {
        this.arrayGrowSize = sz;
    }

    public int getArrayGrowSize() {
        return this.arrayGrowSize;
    }

    public void setInitialFileSize(int sz) {
        this.initialFileSize = sz;
    }

    public int getInitialFileSize() {
        return this.initialFileSize;
    }

    public boolean getDrawingsDisabled() {
        return this.drawingsDisabled;
    }

    public boolean getGCDisabled() {
        return this.gcDisabled;
    }

    public boolean getNamesDisabled() {
        return this.namesDisabled;
    }

    public void setNamesDisabled(boolean b) {
        this.namesDisabled = b;
    }

    public void setDrawingsDisabled(boolean b) {
        this.drawingsDisabled = b;
    }

    public void setRationalization(boolean r) {
        this.rationalizationDisabled = !r;
    }

    public boolean getRationalizationDisabled() {
        return this.rationalizationDisabled;
    }

    public boolean getMergedCellCheckingDisabled() {
        return this.mergedCellCheckingDisabled;
    }

    public void setMergedCellChecking(boolean b) {
        this.mergedCellCheckingDisabled = !b;
    }

    public void setPropertySets(boolean r) {
        this.propertySetsDisabled = !r;
    }

    public boolean getPropertySetsDisabled() {
        return this.propertySetsDisabled;
    }

    public void setSuppressWarnings(boolean w) {
        logger.setSuppressWarnings(w);
    }

    public boolean getFormulaAdjust() {
        return !this.formulaReferenceAdjustDisabled;
    }

    public void setFormulaAdjust(boolean b) {
        this.formulaReferenceAdjustDisabled = !b;
    }

    public void setLocale(Locale l) {
        this.locale = l;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String enc) {
        this.encoding = enc;
    }

    public FunctionNames getFunctionNames() {
        if (this.functionNames == null) {
            this.functionNames = (FunctionNames)this.localeFunctionNames.get(this.locale);
            if (this.functionNames == null) {
                this.functionNames = new FunctionNames(this.locale);
                this.localeFunctionNames.put(this.locale, this.functionNames);
            }
        }
        return this.functionNames;
    }

    public int getCharacterSet() {
        return this.characterSet;
    }

    public void setCharacterSet(int cs) {
        this.characterSet = cs;
    }

    public void setGCDisabled(boolean disabled) {
        this.gcDisabled = disabled;
    }

    public void setIgnoreBlanks(boolean ignoreBlanks) {
        this.ignoreBlankCells = ignoreBlanks;
    }

    public boolean getIgnoreBlanks() {
        return this.ignoreBlankCells;
    }

    public void setCellValidationDisabled(boolean cv) {
        this.cellValidationDisabled = cv;
    }

    public boolean getCellValidationDisabled() {
        return this.cellValidationDisabled;
    }

    public String getExcelDisplayLanguage() {
        return this.excelDisplayLanguage;
    }

    public String getExcelRegionalSettings() {
        return this.excelRegionalSettings;
    }

    public void setExcelDisplayLanguage(String code) {
        this.excelDisplayLanguage = code;
    }

    public void setExcelRegionalSettings(String code) {
        this.excelRegionalSettings = code;
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

