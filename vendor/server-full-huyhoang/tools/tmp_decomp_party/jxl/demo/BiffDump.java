/*
 * Decompiled with CFR 0.152.
 */
package jxl.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import jxl.WorkbookSettings;
import jxl.biff.Type;
import jxl.read.biff.BiffException;
import jxl.read.biff.BiffRecordReader;
import jxl.read.biff.Record;

class BiffDump {
    private BufferedWriter writer;
    private BiffRecordReader reader;
    private HashMap recordNames;
    private int xfIndex;
    private int fontIndex;
    private int bofs;
    private static final int bytesPerLine = 16;

    public BiffDump(File file, OutputStream os) throws IOException, BiffException {
        this.writer = new BufferedWriter(new OutputStreamWriter(os));
        FileInputStream fis = new FileInputStream(file);
        jxl.read.biff.File f = new jxl.read.biff.File(fis, new WorkbookSettings());
        this.reader = new BiffRecordReader(f);
        this.buildNameHash();
        this.dump();
        this.writer.flush();
        this.writer.close();
        fis.close();
    }

    private void buildNameHash() {
        this.recordNames = new HashMap(50);
        this.recordNames.put(Type.BOF, "BOF");
        this.recordNames.put(Type.EOF, "EOF");
        this.recordNames.put(Type.FONT, "FONT");
        this.recordNames.put(Type.SST, "SST");
        this.recordNames.put(Type.LABELSST, "LABELSST");
        this.recordNames.put(Type.WRITEACCESS, "WRITEACCESS");
        this.recordNames.put(Type.FORMULA, "FORMULA");
        this.recordNames.put(Type.FORMULA2, "FORMULA");
        this.recordNames.put(Type.XF, "XF");
        this.recordNames.put(Type.MULRK, "MULRK");
        this.recordNames.put(Type.NUMBER, "NUMBER");
        this.recordNames.put(Type.BOUNDSHEET, "BOUNDSHEET");
        this.recordNames.put(Type.CONTINUE, "CONTINUE");
        this.recordNames.put(Type.FORMAT, "FORMAT");
        this.recordNames.put(Type.EXTERNSHEET, "EXTERNSHEET");
        this.recordNames.put(Type.INDEX, "INDEX");
        this.recordNames.put(Type.DIMENSION, "DIMENSION");
        this.recordNames.put(Type.ROW, "ROW");
        this.recordNames.put(Type.DBCELL, "DBCELL");
        this.recordNames.put(Type.BLANK, "BLANK");
        this.recordNames.put(Type.MULBLANK, "MULBLANK");
        this.recordNames.put(Type.RK, "RK");
        this.recordNames.put(Type.RK2, "RK");
        this.recordNames.put(Type.COLINFO, "COLINFO");
        this.recordNames.put(Type.LABEL, "LABEL");
        this.recordNames.put(Type.SHAREDFORMULA, "SHAREDFORMULA");
        this.recordNames.put(Type.CODEPAGE, "CODEPAGE");
        this.recordNames.put(Type.WINDOW1, "WINDOW1");
        this.recordNames.put(Type.WINDOW2, "WINDOW2");
        this.recordNames.put(Type.MERGEDCELLS, "MERGEDCELLS");
        this.recordNames.put(Type.HLINK, "HLINK");
        this.recordNames.put(Type.HEADER, "HEADER");
        this.recordNames.put(Type.FOOTER, "FOOTER");
        this.recordNames.put(Type.INTERFACEHDR, "INTERFACEHDR");
        this.recordNames.put(Type.MMS, "MMS");
        this.recordNames.put(Type.INTERFACEEND, "INTERFACEEND");
        this.recordNames.put(Type.DSF, "DSF");
        this.recordNames.put(Type.FNGROUPCOUNT, "FNGROUPCOUNT");
        this.recordNames.put(Type.COUNTRY, "COUNTRY");
        this.recordNames.put(Type.TABID, "TABID");
        this.recordNames.put(Type.PROTECT, "PROTECT");
        this.recordNames.put(Type.SCENPROTECT, "SCENPROTECT");
        this.recordNames.put(Type.OBJPROTECT, "OBJPROTECT");
        this.recordNames.put(Type.WINDOWPROTECT, "WINDOWPROTECT");
        this.recordNames.put(Type.PASSWORD, "PASSWORD");
        this.recordNames.put(Type.PROT4REV, "PROT4REV");
        this.recordNames.put(Type.PROT4REVPASS, "PROT4REVPASS");
        this.recordNames.put(Type.BACKUP, "BACKUP");
        this.recordNames.put(Type.HIDEOBJ, "HIDEOBJ");
        this.recordNames.put(Type.NINETEENFOUR, "1904");
        this.recordNames.put(Type.PRECISION, "PRECISION");
        this.recordNames.put(Type.BOOKBOOL, "BOOKBOOL");
        this.recordNames.put(Type.STYLE, "STYLE");
        this.recordNames.put(Type.EXTSST, "EXTSST");
        this.recordNames.put(Type.REFRESHALL, "REFRESHALL");
        this.recordNames.put(Type.CALCMODE, "CALCMODE");
        this.recordNames.put(Type.CALCCOUNT, "CALCCOUNT");
        this.recordNames.put(Type.NAME, "NAME");
        this.recordNames.put(Type.MSODRAWINGGROUP, "MSODRAWINGGROUP");
        this.recordNames.put(Type.MSODRAWING, "MSODRAWING");
        this.recordNames.put(Type.OBJ, "OBJ");
        this.recordNames.put(Type.USESELFS, "USESELFS");
        this.recordNames.put(Type.SUPBOOK, "SUPBOOK");
        this.recordNames.put(Type.LEFTMARGIN, "LEFTMARGIN");
        this.recordNames.put(Type.RIGHTMARGIN, "RIGHTMARGIN");
        this.recordNames.put(Type.TOPMARGIN, "TOPMARGIN");
        this.recordNames.put(Type.BOTTOMMARGIN, "BOTTOMMARGIN");
        this.recordNames.put(Type.HCENTER, "HCENTER");
        this.recordNames.put(Type.VCENTER, "VCENTER");
        this.recordNames.put(Type.ITERATION, "ITERATION");
        this.recordNames.put(Type.DELTA, "DELTA");
        this.recordNames.put(Type.SAVERECALC, "SAVERECALC");
        this.recordNames.put(Type.PRINTHEADERS, "PRINTHEADERS");
        this.recordNames.put(Type.PRINTGRIDLINES, "PRINTGRIDLINES");
        this.recordNames.put(Type.SETUP, "SETUP");
        this.recordNames.put(Type.SELECTION, "SELECTION");
        this.recordNames.put(Type.STRING, "STRING");
        this.recordNames.put(Type.FONTX, "FONTX");
        this.recordNames.put(Type.IFMT, "IFMT");
        this.recordNames.put(Type.WSBOOL, "WSBOOL");
        this.recordNames.put(Type.GRIDSET, "GRIDSET");
        this.recordNames.put(Type.REFMODE, "REFMODE");
        this.recordNames.put(Type.GUTS, "GUTS");
        this.recordNames.put(Type.EXTERNNAME, "EXTERNNAME");
        this.recordNames.put(Type.FBI, "FBI");
        this.recordNames.put(Type.CRN, "CRN");
        this.recordNames.put(Type.HORIZONTALPAGEBREAKS, "HORIZONTALPAGEBREAKS");
        this.recordNames.put(Type.VERTICALPAGEBREAKS, "VERTICALPAGEBREAKS");
        this.recordNames.put(Type.DEFAULTROWHEIGHT, "DEFAULTROWHEIGHT");
        this.recordNames.put(Type.TEMPLATE, "TEMPLATE");
        this.recordNames.put(Type.PANE, "PANE");
        this.recordNames.put(Type.SCL, "SCL");
        this.recordNames.put(Type.PALETTE, "PALETTE");
        this.recordNames.put(Type.PLS, "PLS");
        this.recordNames.put(Type.OBJPROJ, "OBJPROJ");
        this.recordNames.put(Type.DEFCOLWIDTH, "DEFCOLWIDTH");
        this.recordNames.put(Type.ARRAY, "ARRAY");
        this.recordNames.put(Type.WEIRD1, "WEIRD1");
        this.recordNames.put(Type.BOOLERR, "BOOLERR");
        this.recordNames.put(Type.SORT, "SORT");
        this.recordNames.put(Type.BUTTONPROPERTYSET, "BUTTONPROPERTYSET");
        this.recordNames.put(Type.NOTE, "NOTE");
        this.recordNames.put(Type.TXO, "TXO");
        this.recordNames.put(Type.DV, "DV");
        this.recordNames.put(Type.DVAL, "DVAL");
        this.recordNames.put(Type.UNKNOWN, "???");
    }

    private void dump() throws IOException {
        Record r = null;
        boolean cont = true;
        while (this.reader.hasNext() && cont) {
            r = this.reader.next();
            cont = this.writeRecord(r);
        }
    }

    private boolean writeRecord(Record r) throws IOException {
        boolean cont = true;
        int pos = this.reader.getPos();
        int code = r.getCode();
        if (this.bofs == 0) {
            boolean bl = cont = r.getType() == Type.BOF;
        }
        if (!cont) {
            return cont;
        }
        if (r.getType() == Type.BOF) {
            ++this.bofs;
        }
        if (r.getType() == Type.EOF) {
            --this.bofs;
        }
        StringBuffer buf = new StringBuffer();
        this.writeSixDigitValue(pos, buf);
        buf.append(" [");
        buf.append(this.recordNames.get(r.getType()));
        buf.append("]");
        buf.append("  (0x");
        buf.append(Integer.toHexString(code));
        buf.append(")");
        if (code == Type.XF.value) {
            buf.append(" (0x");
            buf.append(Integer.toHexString(this.xfIndex));
            buf.append(")");
            ++this.xfIndex;
        }
        if (code == Type.FONT.value) {
            if (this.fontIndex == 4) {
                ++this.fontIndex;
            }
            buf.append(" (0x");
            buf.append(Integer.toHexString(this.fontIndex));
            buf.append(")");
            ++this.fontIndex;
        }
        this.writer.write(buf.toString());
        this.writer.newLine();
        byte[] standardData = new byte[]{(byte)(code & 0xFF), (byte)((code & 0xFF00) >> 8), (byte)(r.getLength() & 0xFF), (byte)((r.getLength() & 0xFF00) >> 8)};
        byte[] recordData = r.getData();
        byte[] data = new byte[standardData.length + recordData.length];
        System.arraycopy(standardData, 0, data, 0, standardData.length);
        System.arraycopy(recordData, 0, data, standardData.length, recordData.length);
        int lineBytes = 0;
        for (int byteCount = 0; byteCount < data.length; byteCount += lineBytes) {
            int i;
            buf = new StringBuffer();
            this.writeSixDigitValue(pos + byteCount, buf);
            buf.append("   ");
            lineBytes = Math.min(16, data.length - byteCount);
            for (i = 0; i < lineBytes; ++i) {
                this.writeByte(data[i + byteCount], buf);
                buf.append(' ');
            }
            if (lineBytes < 16) {
                for (i = 0; i < 16 - lineBytes; ++i) {
                    buf.append("   ");
                }
            }
            buf.append("  ");
            for (i = 0; i < lineBytes; ++i) {
                char c = (char)data[i + byteCount];
                if (c < ' ' || c > 'z') {
                    c = '.';
                }
                buf.append(c);
            }
            this.writer.write(buf.toString());
            this.writer.newLine();
        }
        return cont;
    }

    private void writeSixDigitValue(int pos, StringBuffer buf) {
        String val = Integer.toHexString(pos);
        for (int i = 6; i > val.length(); --i) {
            buf.append('0');
        }
        buf.append(val);
    }

    private void writeByte(byte val, StringBuffer buf) {
        String sv = Integer.toHexString(val & 0xFF);
        if (sv.length() == 1) {
            buf.append('0');
        }
        buf.append(sv);
    }
}

