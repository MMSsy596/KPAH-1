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
import jxl.Sheet;
import jxl.Workbook;

public class CSV {
    public CSV(Workbook w, OutputStream out, String encoding, boolean hide) throws IOException {
        if (encoding == null || !encoding.equals("UnicodeBig")) {
            encoding = "UTF8";
        }
        try {
            OutputStreamWriter osw = new OutputStreamWriter(out, encoding);
            BufferedWriter bw = new BufferedWriter(osw);
            for (int sheet = 0; sheet < w.getNumberOfSheets(); ++sheet) {
                Sheet s = w.getSheet(sheet);
                if (hide && s.getSettings().isHidden()) continue;
                bw.write("*** " + s.getName() + " ****");
                bw.newLine();
                Cell[] row = null;
                for (int i = 0; i < s.getRows(); ++i) {
                    row = s.getRow(i);
                    if (row.length > 0) {
                        if (!hide || !row[0].isHidden()) {
                            bw.write(row[0].getContents());
                        }
                        for (int j = 1; j < row.length; ++j) {
                            bw.write(44);
                            if (hide && row[j].isHidden()) continue;
                            bw.write(row[j].getContents());
                        }
                    }
                    bw.newLine();
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

