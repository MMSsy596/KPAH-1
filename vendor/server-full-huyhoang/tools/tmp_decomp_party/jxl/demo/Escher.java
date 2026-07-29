/*
 * Decompiled with CFR 0.152.
 */
package jxl.demo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import jxl.Workbook;
import jxl.biff.drawing.DrawingData;
import jxl.biff.drawing.EscherDisplay;
import jxl.read.biff.SheetImpl;

public class Escher {
    public Escher(Workbook w, OutputStream out, String encoding) throws IOException {
        if (encoding == null || !encoding.equals("UnicodeBig")) {
            encoding = "UTF8";
        }
        try {
            OutputStreamWriter osw = new OutputStreamWriter(out, encoding);
            BufferedWriter bw = new BufferedWriter(osw);
            for (int i = 0; i < w.getNumberOfSheets(); ++i) {
                SheetImpl s = (SheetImpl)w.getSheet(i);
                bw.write(s.getName());
                bw.newLine();
                bw.newLine();
                DrawingData dd = s.getDrawingData();
                if (dd != null) {
                    EscherDisplay ed = new EscherDisplay(dd, bw);
                    ed.display();
                }
                bw.newLine();
                bw.newLine();
                bw.flush();
            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {
            System.err.println(e.toString());
        }
    }
}

