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
import jxl.biff.drawing.DrawingGroup;
import jxl.biff.drawing.EscherDisplay;
import jxl.read.biff.WorkbookParser;

public class EscherDrawingGroup {
    public EscherDrawingGroup(Workbook w, OutputStream out, String encoding) throws IOException {
        if (encoding == null || !encoding.equals("UnicodeBig")) {
            encoding = "UTF8";
        }
        try {
            OutputStreamWriter osw = new OutputStreamWriter(out, encoding);
            BufferedWriter bw = new BufferedWriter(osw);
            WorkbookParser wp = (WorkbookParser)w;
            DrawingGroup dg = wp.getDrawingGroup();
            if (dg != null) {
                EscherDisplay ed = new EscherDisplay(dg, bw);
                ed.display();
            }
            bw.newLine();
            bw.newLine();
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {
            System.err.println(e.toString());
        }
    }
}

