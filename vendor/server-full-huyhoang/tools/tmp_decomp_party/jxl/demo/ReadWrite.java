/*
 * Decompiled with CFR 0.152.
 */
package jxl.demo;

import common.Logger;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import jxl.CellType;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Blank;
import jxl.write.DateFormat;
import jxl.write.DateFormats;
import jxl.write.DateTime;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableHyperlink;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ReadWrite {
    private static Logger logger = Logger.getLogger(class$jxl$demo$ReadWrite == null ? (class$jxl$demo$ReadWrite = ReadWrite.class$("jxl.demo.ReadWrite")) : class$jxl$demo$ReadWrite);
    private File inputWorkbook;
    private File outputWorkbook;
    static /* synthetic */ Class class$jxl$demo$ReadWrite;

    public ReadWrite(String input, String output) {
        this.inputWorkbook = new File(input);
        this.outputWorkbook = new File(output);
        logger.setSuppressWarnings(Boolean.getBoolean("jxl.nowarnings"));
        logger.info("Input file:  " + input);
        logger.info("Output file:  " + output);
    }

    public void readWrite() throws IOException, BiffException, WriteException {
        logger.info("Reading...");
        Workbook w1 = Workbook.getWorkbook(this.inputWorkbook);
        logger.info("Copying...");
        WritableWorkbook w2 = Workbook.createWorkbook(this.outputWorkbook, w1);
        if (this.inputWorkbook.getName().equals("jxlrwtest.xls")) {
            this.modify(w2);
        }
        w2.write();
        w2.close();
        logger.info("Done");
    }

    private void modify(WritableWorkbook w) throws WriteException {
        Number n;
        logger.info("Modifying...");
        WritableSheet sheet = w.getSheet("modified");
        WritableCell cell = null;
        WritableCellFormat cf = null;
        Label l = null;
        WritableCellFeatures wcf = null;
        cell = sheet.getWritableCell(1, 3);
        WritableFont bold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        cf = new WritableCellFormat(bold);
        cell.setCellFormat(cf);
        cell = sheet.getWritableCell(1, 4);
        WritableFont underline = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.SINGLE);
        cf = new WritableCellFormat(underline);
        cell.setCellFormat(cf);
        cell = sheet.getWritableCell(1, 5);
        WritableFont tenpoint = new WritableFont(WritableFont.ARIAL, 10);
        cf = new WritableCellFormat(tenpoint);
        cell.setCellFormat(cf);
        cell = sheet.getWritableCell(1, 6);
        if (cell.getType() == CellType.LABEL) {
            Label lc = (Label)cell;
            lc.setString(lc.getString() + " - mod");
        }
        cell = sheet.getWritableCell(1, 9);
        NumberFormat sevendps = new NumberFormat("#.0000000");
        cf = new WritableCellFormat(sevendps);
        cell.setCellFormat(cf);
        cell = sheet.getWritableCell(1, 10);
        NumberFormat exp4 = new NumberFormat("0.####E0");
        cf = new WritableCellFormat(exp4);
        cell.setCellFormat(cf);
        cell = sheet.getWritableCell(1, 11);
        cell.setCellFormat(WritableWorkbook.NORMAL_STYLE);
        cell = sheet.getWritableCell(1, 12);
        if (cell.getType() == CellType.NUMBER) {
            n = (Number)cell;
            n.setValue(42.0);
        }
        if ((cell = sheet.getWritableCell(1, 13)).getType() == CellType.NUMBER) {
            n = (Number)cell;
            n.setValue(n.getValue() + 0.1);
        }
        cell = sheet.getWritableCell(1, 16);
        DateFormat df = new DateFormat("dd MMM yyyy HH:mm:ss");
        cf = new WritableCellFormat(df);
        cell.setCellFormat(cf);
        cell = sheet.getWritableCell(1, 17);
        cf = new WritableCellFormat(DateFormats.FORMAT9);
        cell.setCellFormat(cf);
        cell = sheet.getWritableCell(1, 18);
        if (cell.getType() == CellType.DATE) {
            DateTime dt = (DateTime)cell;
            Calendar cal = Calendar.getInstance();
            cal.set(1998, 1, 18, 11, 23, 28);
            Date d = cal.getTime();
            dt.setDate(d);
        }
        if ((cell = sheet.getWritableCell(1, 22)).getType() == CellType.NUMBER) {
            Number n2 = (Number)cell;
            n2.setValue(6.8);
        }
        if ((cell = sheet.getWritableCell(1, 29)).getType() == CellType.LABEL) {
            l = (Label)cell;
            l.setString("Modified string contents");
        }
        sheet.insertRow(34);
        sheet.removeRow(38);
        sheet.insertColumn(9);
        sheet.removeColumn(11);
        sheet.removeRow(43);
        sheet.insertRow(43);
        WritableHyperlink[] hyperlinks = sheet.getWritableHyperlinks();
        for (int i = 0; i < hyperlinks.length; ++i) {
            WritableHyperlink wh = hyperlinks[i];
            if (wh.getColumn() == 1 && wh.getRow() == 39) {
                try {
                    wh.setURL(new URL("http://www.andykhan.com/jexcelapi/index.html"));
                }
                catch (MalformedURLException e) {
                    logger.warn(e.toString());
                }
                continue;
            }
            if (wh.getColumn() == 1 && wh.getRow() == 40) {
                wh.setFile(new File("../jexcelapi/docs/overview-summary.html"));
                continue;
            }
            if (wh.getColumn() == 1 && wh.getRow() == 41) {
                wh.setFile(new File("d:/home/jexcelapi/docs/jxl/package-summary.html"));
                continue;
            }
            if (wh.getColumn() != 1 || wh.getRow() != 44) continue;
            sheet.removeHyperlink(wh);
        }
        WritableCell c = sheet.getWritableCell(5, 30);
        WritableCellFormat newFormat = new WritableCellFormat(c.getCellFormat());
        newFormat.setBackground(Colour.RED);
        c.setCellFormat(newFormat);
        l = new Label(0, 49, "Modified merged cells");
        sheet.addCell(l);
        Number n3 = (Number)sheet.getWritableCell(0, 70);
        n3.setValue(9.0);
        n3 = (Number)sheet.getWritableCell(0, 71);
        n3.setValue(10.0);
        n3 = (Number)sheet.getWritableCell(0, 73);
        n3.setValue(4.0);
        Formula f = new Formula(1, 80, "ROUND(COS(original!B10),2)");
        sheet.addCell(f);
        f = new Formula(1, 83, "value1+value2");
        sheet.addCell(f);
        f = new Formula(1, 84, "AVERAGE(value1,value1*4,value2)");
        sheet.addCell(f);
        Label label = new Label(0, 88, "Some copied cells", (CellFormat)cf);
        sheet.addCell(label);
        label = new Label(0, 89, "Number from B9");
        sheet.addCell(label);
        WritableCell wc = sheet.getWritableCell(1, 9).copyTo(1, 89);
        sheet.addCell(wc);
        label = new Label(0, 90, "Label from B4 (modified format)");
        sheet.addCell(label);
        wc = sheet.getWritableCell(1, 3).copyTo(1, 90);
        sheet.addCell(wc);
        label = new Label(0, 91, "Date from B17");
        sheet.addCell(label);
        wc = sheet.getWritableCell(1, 16).copyTo(1, 91);
        sheet.addCell(wc);
        label = new Label(0, 92, "Boolean from E16");
        sheet.addCell(label);
        wc = sheet.getWritableCell(4, 15).copyTo(1, 92);
        sheet.addCell(wc);
        label = new Label(0, 93, "URL from B40");
        sheet.addCell(label);
        wc = sheet.getWritableCell(1, 39).copyTo(1, 93);
        sheet.addCell(wc);
        for (int i = 0; i < 6; ++i) {
            Number number = new Number(1, 94 + i, (double)(i + 1) + (double)i / 8.0);
            sheet.addCell(number);
        }
        label = new Label(0, 100, "Formula from B27");
        sheet.addCell(label);
        wc = sheet.getWritableCell(1, 26).copyTo(1, 100);
        sheet.addCell(wc);
        label = new Label(0, 101, "A brand new formula");
        sheet.addCell(label);
        Formula formula = new Formula(1, 101, "SUM(B94:B96)");
        sheet.addCell(formula);
        label = new Label(0, 102, "A copy of it");
        sheet.addCell(label);
        wc = sheet.getWritableCell(1, 101).copyTo(1, 102);
        sheet.addCell(wc);
        WritableImage wi = sheet.getImage(1);
        sheet.removeImage(wi);
        wi = new WritableImage(1.0, 116.0, 2.0, 9.0, new File("resources/littlemoretonhall.png"));
        sheet.addImage(wi);
        label = new Label(0, 151, "Added drop down validation");
        sheet.addCell(label);
        Blank b = new Blank(1, 151);
        wcf = new WritableCellFeatures();
        ArrayList<String> al = new ArrayList<String>();
        al.add("The Fellowship of the Ring");
        al.add("The Two Towers");
        al.add("The Return of the King");
        wcf.setDataValidationList(al);
        b.setCellFeatures(wcf);
        sheet.addCell(b);
        label = new Label(0, 152, "Added number validation 2.718 < x < 3.142");
        sheet.addCell(label);
        b = new Blank(1, 152);
        wcf = new WritableCellFeatures();
        wcf.setNumberValidation(2.718, 3.142, WritableCellFeatures.BETWEEN);
        b.setCellFeatures(wcf);
        sheet.addCell(b);
        cell = sheet.getWritableCell(0, 156);
        l = (Label)cell;
        l.setString("Label text modified");
        cell = sheet.getWritableCell(0, 157);
        wcf = cell.getWritableCellFeatures();
        wcf.setComment("modified comment text");
        cell = sheet.getWritableCell(0, 158);
        wcf = cell.getWritableCellFeatures();
        wcf.removeComment();
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

