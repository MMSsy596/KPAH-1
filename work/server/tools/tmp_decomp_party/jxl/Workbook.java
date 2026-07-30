/*
 * Decompiled with CFR 0.152.
 */
package jxl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.read.biff.File;
import jxl.read.biff.PasswordException;
import jxl.read.biff.WorkbookParser;
import jxl.write.WritableWorkbook;
import jxl.write.biff.WritableWorkbookImpl;

public abstract class Workbook {
    private static final String VERSION = "2.6";

    protected Workbook() {
    }

    public abstract Sheet[] getSheets();

    public abstract String[] getSheetNames();

    public abstract Sheet getSheet(int var1) throws IndexOutOfBoundsException;

    public abstract Sheet getSheet(String var1);

    public static String getVersion() {
        return VERSION;
    }

    public abstract int getNumberOfSheets();

    public abstract Cell findCellByName(String var1);

    public abstract Cell getCell(String var1);

    public abstract Range[] findByName(String var1);

    public abstract String[] getRangeNames();

    public abstract boolean isProtected();

    protected abstract void parse() throws BiffException, PasswordException;

    public abstract void close();

    public static Workbook getWorkbook(java.io.File file) throws IOException, BiffException {
        return Workbook.getWorkbook(file, new WorkbookSettings());
    }

    public static Workbook getWorkbook(java.io.File file, WorkbookSettings ws) throws IOException, BiffException {
        FileInputStream fis = new FileInputStream(file);
        File dataFile = null;
        try {
            dataFile = new File(fis, ws);
        }
        catch (IOException e) {
            fis.close();
            throw e;
        }
        catch (BiffException e) {
            fis.close();
            throw e;
        }
        fis.close();
        WorkbookParser workbook = new WorkbookParser(dataFile, ws);
        ((Workbook)workbook).parse();
        return workbook;
    }

    public static Workbook getWorkbook(InputStream is) throws IOException, BiffException {
        return Workbook.getWorkbook(is, new WorkbookSettings());
    }

    public static Workbook getWorkbook(InputStream is, WorkbookSettings ws) throws IOException, BiffException {
        File dataFile = new File(is, ws);
        WorkbookParser workbook = new WorkbookParser(dataFile, ws);
        ((Workbook)workbook).parse();
        return workbook;
    }

    public static WritableWorkbook createWorkbook(java.io.File file) throws IOException {
        return Workbook.createWorkbook(file, new WorkbookSettings());
    }

    public static WritableWorkbook createWorkbook(java.io.File file, WorkbookSettings ws) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        WritableWorkbookImpl w = new WritableWorkbookImpl(fos, true, ws);
        return w;
    }

    public static WritableWorkbook createWorkbook(java.io.File file, Workbook in) throws IOException {
        return Workbook.createWorkbook(file, in, new WorkbookSettings());
    }

    public static WritableWorkbook createWorkbook(java.io.File file, Workbook in, WorkbookSettings ws) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        WritableWorkbookImpl w = new WritableWorkbookImpl(fos, in, true, ws);
        return w;
    }

    public static WritableWorkbook createWorkbook(OutputStream os, Workbook in) throws IOException {
        return Workbook.createWorkbook(os, in, ((WorkbookParser)in).getSettings());
    }

    public static WritableWorkbook createWorkbook(OutputStream os, Workbook in, WorkbookSettings ws) throws IOException {
        WritableWorkbookImpl w = new WritableWorkbookImpl(os, in, false, ws);
        return w;
    }

    public static WritableWorkbook createWorkbook(OutputStream os) throws IOException {
        return Workbook.createWorkbook(os, new WorkbookSettings());
    }

    public static WritableWorkbook createWorkbook(OutputStream os, WorkbookSettings ws) throws IOException {
        WritableWorkbookImpl w = new WritableWorkbookImpl(os, false, ws);
        return w;
    }
}

