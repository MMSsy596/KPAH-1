/*
 * Decompiled with CFR 0.152.
 */
package jxl.demo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import jxl.Cell;
import jxl.CellType;
import jxl.FormulaCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.biff.CellReferenceHelper;
import jxl.biff.formula.FormulaException;

public class Formulas {
    public Formulas(Workbook w, OutputStream out, String encoding) throws IOException {
        if (encoding == null || !encoding.equals("UnicodeBig")) {
            encoding = "UTF8";
        }
        try {
            OutputStreamWriter osw = new OutputStreamWriter(out, encoding);
            BufferedWriter bw = new BufferedWriter(osw);
            ArrayList<String> parseErrors = new ArrayList<String>();
            for (int sheet = 0; sheet < w.getNumberOfSheets(); ++sheet) {
                Sheet s = w.getSheet(sheet);
                bw.write(s.getName());
                bw.newLine();
                Cell[] row = null;
                Cell c = null;
                for (int i = 0; i < s.getRows(); ++i) {
                    row = s.getRow(i);
                    for (int j = 0; j < row.length; ++j) {
                        c = row[j];
                        if (c.getType() != CellType.NUMBER_FORMULA && c.getType() != CellType.STRING_FORMULA && c.getType() != CellType.BOOLEAN_FORMULA && c.getType() != CellType.DATE_FORMULA && c.getType() != CellType.FORMULA_ERROR) continue;
                        FormulaCell nfc = (FormulaCell)c;
                        StringBuffer sb = new StringBuffer();
                        CellReferenceHelper.getCellReference(c.getColumn(), c.getRow(), sb);
                        try {
                            bw.write("Formula in " + sb.toString() + " value:  " + c.getContents());
                            bw.flush();
                            bw.write(" formula: " + nfc.getFormula());
                            bw.flush();
                            bw.newLine();
                            continue;
                        }
                        catch (FormulaException e) {
                            bw.newLine();
                            parseErrors.add(s.getName() + '!' + sb.toString() + ": " + e.getMessage());
                        }
                    }
                }
            }
            bw.flush();
            bw.close();
            if (parseErrors.size() > 0) {
                System.err.println();
                System.err.println("There were " + parseErrors.size() + " errors");
                Iterator i = parseErrors.iterator();
                while (i.hasNext()) {
                    System.err.println(i.next());
                }
            }
        }
        catch (UnsupportedEncodingException e) {
            System.err.println(e.toString());
        }
    }
}

