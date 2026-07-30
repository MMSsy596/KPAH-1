/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Assert;
import common.Logger;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import jxl.CellType;
import jxl.Hyperlink;
import jxl.Range;
import jxl.biff.CellReferenceHelper;
import jxl.biff.IntegerHelper;
import jxl.biff.SheetRangeImpl;
import jxl.biff.StringHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;

public class HyperlinkRecord
extends WritableRecordData {
    private static Logger logger = Logger.getLogger(class$jxl$write$biff$HyperlinkRecord == null ? (class$jxl$write$biff$HyperlinkRecord = HyperlinkRecord.class$("jxl.write.biff.HyperlinkRecord")) : class$jxl$write$biff$HyperlinkRecord);
    private int firstRow;
    private int lastRow;
    private int firstColumn;
    private int lastColumn;
    private URL url;
    private File file;
    private String location;
    private String contents;
    private LinkType linkType;
    private byte[] data;
    private Range range;
    private WritableSheet sheet;
    private boolean modified;
    private static final LinkType urlLink = new LinkType();
    private static final LinkType fileLink = new LinkType();
    private static final LinkType uncLink = new LinkType();
    private static final LinkType workbookLink = new LinkType();
    private static final LinkType unknown = new LinkType();
    static /* synthetic */ Class class$jxl$write$biff$HyperlinkRecord;

    protected HyperlinkRecord(Hyperlink h, WritableSheet s) {
        super(Type.HLINK);
        Assert.verify(h instanceof jxl.read.biff.HyperlinkRecord);
        jxl.read.biff.HyperlinkRecord hl = (jxl.read.biff.HyperlinkRecord)h;
        this.data = hl.getRecord().getData();
        this.sheet = s;
        this.firstRow = hl.getRow();
        this.firstColumn = hl.getColumn();
        this.lastRow = hl.getLastRow();
        this.lastColumn = hl.getLastColumn();
        this.range = new SheetRangeImpl(s, this.firstColumn, this.firstRow, this.lastColumn, this.lastRow);
        this.linkType = unknown;
        if (hl.isFile()) {
            this.linkType = fileLink;
            this.file = hl.getFile();
        } else if (hl.isURL()) {
            this.linkType = urlLink;
            this.url = hl.getURL();
        } else if (hl.isLocation()) {
            this.linkType = workbookLink;
            this.location = hl.getLocation();
        }
        this.modified = false;
    }

    protected HyperlinkRecord(int col, int row, int lastcol, int lastrow, URL url, String desc) {
        super(Type.HLINK);
        this.firstColumn = col;
        this.firstRow = row;
        this.lastColumn = Math.max(this.firstColumn, lastcol);
        this.lastRow = Math.max(this.firstRow, lastrow);
        this.url = url;
        this.contents = desc;
        this.linkType = urlLink;
        this.modified = true;
    }

    protected HyperlinkRecord(int col, int row, int lastcol, int lastrow, File file, String desc) {
        super(Type.HLINK);
        this.firstColumn = col;
        this.firstRow = row;
        this.lastColumn = Math.max(this.firstColumn, lastcol);
        this.lastRow = Math.max(this.firstRow, lastrow);
        this.contents = desc;
        this.file = file;
        this.linkType = file.getPath().startsWith("\\\\") ? uncLink : fileLink;
        this.modified = true;
    }

    protected HyperlinkRecord(int col, int row, int lastcol, int lastrow, String desc, WritableSheet s, int destcol, int destrow, int lastdestcol, int lastdestrow) {
        super(Type.HLINK);
        this.firstColumn = col;
        this.firstRow = row;
        this.lastColumn = Math.max(this.firstColumn, lastcol);
        this.lastRow = Math.max(this.firstRow, lastrow);
        this.setLocation(s, destcol, destrow, lastdestcol, lastdestrow);
        this.contents = desc;
        this.linkType = workbookLink;
        this.modified = true;
    }

    public boolean isFile() {
        return this.linkType == fileLink;
    }

    public boolean isUNC() {
        return this.linkType == uncLink;
    }

    public boolean isURL() {
        return this.linkType == urlLink;
    }

    public boolean isLocation() {
        return this.linkType == workbookLink;
    }

    public int getRow() {
        return this.firstRow;
    }

    public int getColumn() {
        return this.firstColumn;
    }

    public int getLastRow() {
        return this.lastRow;
    }

    public int getLastColumn() {
        return this.lastColumn;
    }

    public URL getURL() {
        return this.url;
    }

    public File getFile() {
        return this.file;
    }

    public byte[] getData() {
        if (!this.modified) {
            return this.data;
        }
        byte[] commonData = new byte[32];
        IntegerHelper.getTwoBytes(this.firstRow, commonData, 0);
        IntegerHelper.getTwoBytes(this.lastRow, commonData, 2);
        IntegerHelper.getTwoBytes(this.firstColumn, commonData, 4);
        IntegerHelper.getTwoBytes(this.lastColumn, commonData, 6);
        commonData[8] = -48;
        commonData[9] = -55;
        commonData[10] = -22;
        commonData[11] = 121;
        commonData[12] = -7;
        commonData[13] = -70;
        commonData[14] = -50;
        commonData[15] = 17;
        commonData[16] = -116;
        commonData[17] = -126;
        commonData[18] = 0;
        commonData[19] = -86;
        commonData[20] = 0;
        commonData[21] = 75;
        commonData[22] = -87;
        commonData[23] = 11;
        commonData[24] = 2;
        commonData[25] = 0;
        commonData[26] = 0;
        commonData[27] = 0;
        int optionFlags = 0;
        if (this.isURL()) {
            optionFlags = 3;
            if (this.contents != null) {
                optionFlags |= 0x14;
            }
        } else if (this.isFile()) {
            optionFlags = 3;
            if (this.contents == null) {
                optionFlags |= 0x14;
            }
        } else if (this.isLocation()) {
            optionFlags = 8;
        } else if (this.isUNC()) {
            optionFlags = 259;
        }
        IntegerHelper.getFourBytes(optionFlags, commonData, 28);
        if (this.isURL()) {
            this.data = this.getURLData(commonData);
        } else if (this.isFile()) {
            this.data = this.getFileData(commonData);
        } else if (this.isLocation()) {
            this.data = this.getLocationData(commonData);
        } else if (this.isUNC()) {
            this.data = this.getUNCData(commonData);
        }
        return this.data;
    }

    public String toString() {
        if (this.isFile()) {
            return this.file.toString();
        }
        if (this.isURL()) {
            return this.url.toString();
        }
        if (this.isUNC()) {
            return this.file.toString();
        }
        return "";
    }

    public Range getRange() {
        return this.range;
    }

    public void setURL(URL url) {
        this.linkType = urlLink;
        this.file = null;
        this.location = null;
        this.contents = null;
        this.url = url;
        this.modified = true;
        if (this.sheet == null) {
            return;
        }
        WritableCell wc = this.sheet.getWritableCell(this.firstColumn, this.firstRow);
        Assert.verify(wc.getType() == CellType.LABEL);
        Label l = (Label)wc;
        l.setString(url.toString());
    }

    public void setFile(File file) {
        this.linkType = fileLink;
        this.url = null;
        this.location = null;
        this.contents = null;
        this.file = file;
        this.modified = true;
        if (this.sheet == null) {
            return;
        }
        WritableCell wc = this.sheet.getWritableCell(this.firstColumn, this.firstRow);
        Assert.verify(wc.getType() == CellType.LABEL);
        Label l = (Label)wc;
        l.setString(file.toString());
    }

    protected void setLocation(String desc, WritableSheet sheet, int destcol, int destrow, int lastdestcol, int lastdestrow) {
        this.linkType = workbookLink;
        this.url = null;
        this.file = null;
        this.modified = true;
        this.contents = desc;
        this.setLocation(sheet, destcol, destrow, lastdestcol, lastdestrow);
        if (sheet == null) {
            return;
        }
        WritableCell wc = sheet.getWritableCell(this.firstColumn, this.firstRow);
        Assert.verify(wc.getType() == CellType.LABEL);
        Label l = (Label)wc;
        l.setString(desc);
    }

    private void setLocation(WritableSheet sheet, int destcol, int destrow, int lastdestcol, int lastdestrow) {
        StringBuffer sb = new StringBuffer();
        sb.append('\'');
        if (sheet.getName().indexOf(39) == -1) {
            sb.append(sheet.getName());
        } else {
            String sheetName = sheet.getName();
            int pos = 0;
            int nextPos = sheetName.indexOf(39, pos);
            while (nextPos != -1 && pos < sheetName.length()) {
                sb.append(sheetName.substring(pos, nextPos));
                sb.append("''");
                pos = nextPos + 1;
                nextPos = sheetName.indexOf(39, pos);
            }
            sb.append(sheetName.substring(pos));
        }
        sb.append('\'');
        sb.append('!');
        lastdestcol = Math.max(destcol, lastdestcol);
        lastdestrow = Math.max(destrow, lastdestrow);
        CellReferenceHelper.getCellReference(destcol, destrow, sb);
        sb.append(':');
        CellReferenceHelper.getCellReference(lastdestcol, lastdestrow, sb);
        this.location = sb.toString();
    }

    void insertRow(int r) {
        Assert.verify(this.sheet != null && this.range != null);
        if (r > this.lastRow) {
            return;
        }
        if (r <= this.firstRow) {
            ++this.firstRow;
            this.modified = true;
        }
        if (r <= this.lastRow) {
            ++this.lastRow;
            this.modified = true;
        }
        if (this.modified) {
            this.range = new SheetRangeImpl(this.sheet, this.firstColumn, this.firstRow, this.lastColumn, this.lastRow);
        }
    }

    void insertColumn(int c) {
        Assert.verify(this.sheet != null && this.range != null);
        if (c > this.lastColumn) {
            return;
        }
        if (c <= this.firstColumn) {
            ++this.firstColumn;
            this.modified = true;
        }
        if (c <= this.lastColumn) {
            ++this.lastColumn;
            this.modified = true;
        }
        if (this.modified) {
            this.range = new SheetRangeImpl(this.sheet, this.firstColumn, this.firstRow, this.lastColumn, this.lastRow);
        }
    }

    void removeRow(int r) {
        Assert.verify(this.sheet != null && this.range != null);
        if (r > this.lastRow) {
            return;
        }
        if (r < this.firstRow) {
            --this.firstRow;
            this.modified = true;
        }
        if (r < this.lastRow) {
            --this.lastRow;
            this.modified = true;
        }
        if (this.modified) {
            Assert.verify(this.range != null);
            this.range = new SheetRangeImpl(this.sheet, this.firstColumn, this.firstRow, this.lastColumn, this.lastRow);
        }
    }

    void removeColumn(int c) {
        Assert.verify(this.sheet != null && this.range != null);
        if (c > this.lastColumn) {
            return;
        }
        if (c < this.firstColumn) {
            --this.firstColumn;
            this.modified = true;
        }
        if (c < this.lastColumn) {
            --this.lastColumn;
            this.modified = true;
        }
        if (this.modified) {
            Assert.verify(this.range != null);
            this.range = new SheetRangeImpl(this.sheet, this.firstColumn, this.firstRow, this.lastColumn, this.lastRow);
        }
    }

    private byte[] getURLData(byte[] cd) {
        String urlString = this.url.toString();
        int dataLength = cd.length + 20 + (urlString.length() + 1) * 2;
        if (this.contents != null) {
            dataLength += 4 + (this.contents.length() + 1) * 2;
        }
        byte[] d = new byte[dataLength];
        System.arraycopy(cd, 0, d, 0, cd.length);
        int urlPos = cd.length;
        if (this.contents != null) {
            IntegerHelper.getFourBytes(this.contents.length() + 1, d, urlPos);
            StringHelper.getUnicodeBytes(this.contents, d, urlPos + 4);
            urlPos += (this.contents.length() + 1) * 2 + 4;
        }
        d[urlPos] = -32;
        d[urlPos + 1] = -55;
        d[urlPos + 2] = -22;
        d[urlPos + 3] = 121;
        d[urlPos + 4] = -7;
        d[urlPos + 5] = -70;
        d[urlPos + 6] = -50;
        d[urlPos + 7] = 17;
        d[urlPos + 8] = -116;
        d[urlPos + 9] = -126;
        d[urlPos + 10] = 0;
        d[urlPos + 11] = -86;
        d[urlPos + 12] = 0;
        d[urlPos + 13] = 75;
        d[urlPos + 14] = -87;
        d[urlPos + 15] = 11;
        IntegerHelper.getFourBytes((urlString.length() + 1) * 2, d, urlPos + 16);
        StringHelper.getUnicodeBytes(urlString, d, urlPos + 20);
        return d;
    }

    private byte[] getUNCData(byte[] cd) {
        String uncString = this.file.getPath();
        byte[] d = new byte[cd.length + uncString.length() * 2 + 2 + 4];
        System.arraycopy(cd, 0, d, 0, cd.length);
        int urlPos = cd.length;
        int length = uncString.length() + 1;
        IntegerHelper.getFourBytes(length, d, urlPos);
        StringHelper.getUnicodeBytes(uncString, d, urlPos + 4);
        return d;
    }

    private byte[] getFileData(byte[] cd) {
        char driveLetter;
        ArrayList<String> path = new ArrayList<String>();
        ArrayList<String> shortFileName = new ArrayList<String>();
        path.add(this.file.getName());
        shortFileName.add(this.getShortName(this.file.getName()));
        for (File parent = this.file.getParentFile(); parent != null; parent = parent.getParentFile()) {
            path.add(parent.getName());
            shortFileName.add(this.getShortName(parent.getName()));
        }
        int upLevelCount = 0;
        int pos = path.size() - 1;
        boolean upDir = true;
        while (upDir) {
            String s = (String)path.get(pos);
            if (s.equals("..")) {
                ++upLevelCount;
                path.remove(pos);
                shortFileName.remove(pos);
            } else {
                upDir = false;
            }
            --pos;
        }
        StringBuffer filePathSB = new StringBuffer();
        StringBuffer shortFilePathSB = new StringBuffer();
        if (this.file.getPath().charAt(1) == ':' && (driveLetter = this.file.getPath().charAt(0)) != 'C' && driveLetter != 'c') {
            filePathSB.append(driveLetter);
            filePathSB.append(':');
            shortFilePathSB.append(driveLetter);
            shortFilePathSB.append(':');
        }
        for (int i = path.size() - 1; i >= 0; --i) {
            filePathSB.append((String)path.get(i));
            shortFilePathSB.append((String)shortFileName.get(i));
            if (i == 0) continue;
            filePathSB.append("\\");
            shortFilePathSB.append("\\");
        }
        String filePath = filePathSB.toString();
        String shortFilePath = shortFilePathSB.toString();
        int dataLength = cd.length + 4 + (shortFilePath.length() + 1) * 2 + 16 + 2 + 4 + filePath.length() + 1 + 4 + 24;
        if (this.contents != null) {
            dataLength += 4 + (this.contents.length() + 1) * 2;
        }
        byte[] d = new byte[dataLength];
        System.arraycopy(cd, 0, d, 0, cd.length);
        int filePos = cd.length;
        if (this.contents != null) {
            IntegerHelper.getFourBytes(this.contents.length() + 1, d, filePos);
            StringHelper.getUnicodeBytes(this.contents, d, filePos + 4);
            filePos += (this.contents.length() + 1) * 2 + 4;
        }
        int curPos = filePos;
        IntegerHelper.getFourBytes(shortFilePath.length() + 1, d, curPos);
        StringHelper.getUnicodeBytes(shortFilePath, d, curPos + 4);
        d[curPos += 4 + (shortFilePath.length() + 1) * 2] = 3;
        d[curPos + 1] = 3;
        d[curPos + 2] = 0;
        d[curPos + 3] = 0;
        d[curPos + 4] = 0;
        d[curPos + 5] = 0;
        d[curPos + 6] = 0;
        d[curPos + 7] = 0;
        d[curPos + 8] = -64;
        d[curPos + 9] = 0;
        d[curPos + 10] = 0;
        d[curPos + 11] = 0;
        d[curPos + 12] = 0;
        d[curPos + 13] = 0;
        d[curPos + 14] = 0;
        d[curPos + 15] = 70;
        IntegerHelper.getTwoBytes(upLevelCount, d, curPos += 16);
        IntegerHelper.getFourBytes(filePath.length() + 1, d, curPos += 2);
        StringHelper.getBytes(filePath, d, curPos += 4);
        d[curPos += filePath.length() + 1] = -1;
        d[curPos + 1] = -1;
        d[curPos + 2] = -83;
        d[curPos + 3] = -34;
        return d;
    }

    private String getShortName(String s) {
        int sep = s.indexOf(46);
        String prefix = null;
        String suffix = null;
        if (sep == -1) {
            prefix = s;
            suffix = "";
        } else {
            prefix = s.substring(0, sep);
            suffix = s.substring(sep + 1);
        }
        if (prefix.length() > 8) {
            prefix = prefix.substring(0, 6) + "~" + (prefix.length() - 6);
            prefix = prefix.substring(0, 8);
        }
        if ((suffix = suffix.substring(0, Math.min(3, suffix.length()))).length() > 0) {
            return prefix + '.' + suffix;
        }
        return prefix;
    }

    private byte[] getLocationData(byte[] cd) {
        byte[] d = new byte[cd.length + 4 + (this.location.length() + 1) * 2];
        System.arraycopy(cd, 0, d, 0, cd.length);
        int locPos = cd.length;
        IntegerHelper.getFourBytes(this.location.length() + 1, d, locPos);
        StringHelper.getUnicodeBytes(this.location, d, locPos + 4);
        return d;
    }

    void initialize(WritableSheet s) {
        this.sheet = s;
        this.range = new SheetRangeImpl(s, this.firstColumn, this.firstRow, this.lastColumn, this.lastRow);
    }

    String getContents() {
        return this.contents;
    }

    protected void setContents(String desc) {
        this.contents = desc;
        this.modified = true;
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    private static class LinkType {
        private LinkType() {
        }
    }
}

