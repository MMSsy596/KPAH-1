/*
 * Decompiled with CFR 0.152.
 */
package jxl.demo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import jxl.Cell;
import jxl.CellFeatures;
import jxl.CellReferenceHelper;
import jxl.Sheet;
import jxl.Workbook;

public class Features {
    public Features(Workbook w, OutputStream out, String encoding) throws IOException {
        if (encoding == null || !encoding.equals("UnicodeBig")) {
            encoding = "UTF8";
        }
        try {
            OutputStreamWriter osw = new OutputStreamWriter(out, encoding);
            BufferedWriter bw = new BufferedWriter(osw);
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
                        if (c.getCellFeatures() == null) continue;
                        CellFeatures features = c.getCellFeatures();
                        StringBuffer sb = new StringBuffer();
                        CellReferenceHelper.getCellReference(c.getColumn(), c.getRow(), sb);
                        bw.write("Cell " + sb.toString() + " contents:  " + c.getContents());
                        bw.flush();
                        bw.write(" comment: " + features.getComment());
                        bw.flush();
                        bw.newLine();
                    }
                }
            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {
            System.err.println(e.toString());
        }
    }
}

