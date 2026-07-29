/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import jxl.CellReferenceHelper;
import jxl.Hyperlink;
import jxl.Range;
import jxl.Sheet;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.biff.SheetRangeImpl;
import jxl.biff.StringHelper;
import jxl.read.biff.Record;

public class HyperlinkRecord
extends RecordData
implements Hyperlink {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$HyperlinkRecord == null ? (class$jxl$read$biff$HyperlinkRecord = HyperlinkRecord.class$("jxl.read.biff.HyperlinkRecord")) : class$jxl$read$biff$HyperlinkRecord);
    private int firstRow;
    private int lastRow;
    private int firstColumn;
    private int lastColumn;
    private URL url;
    private File file;
    private String location;
    private SheetRangeImpl range;
    private LinkType linkType = unknown;
    private static final LinkType urlLink = new LinkType();
    private static final LinkType fileLink = new LinkType();
    private static final LinkType workbookLink = new LinkType();
    private static final LinkType unknown = new LinkType();
    static /* synthetic */ Class class$jxl$read$biff$HyperlinkRecord;

    HyperlinkRecord(Record t, Sheet s, WorkbookSettings ws) {
        super(t);
        byte[] data = this.getRecord().getData();
        this.firstRow = IntegerHelper.getInt(data[0], data[1]);
        this.lastRow = IntegerHelper.getInt(data[2], data[3]);
        this.firstColumn = IntegerHelper.getInt(data[4], data[5]);
        this.lastColumn = IntegerHelper.getInt(data[6], data[7]);
        this.range = new SheetRangeImpl(s, this.firstColumn, this.firstRow, this.lastColumn, this.lastRow);
        int options = IntegerHelper.getInt(data[28], data[29], data[30], data[31]);
        boolean description = (options & 0x14) != 0;
        int startpos = 32;
        int descbytes = 0;
        if (description) {
            int descchars = IntegerHelper.getInt(data[startpos], data[startpos + 1], data[startpos + 2], data[startpos + 3]);
            descbytes = descchars * 2 + 4;
        }
        startpos += descbytes;
        boolean targetFrame = (options & 0x80) != 0;
        int targetbytes = 0;
        if (targetFrame) {
            int targetchars = IntegerHelper.getInt(data[startpos], data[startpos + 1], data[startpos + 2], data[startpos + 3]);
            targetbytes = targetchars * 2 + 4;
        }
        startpos += targetbytes;
        if ((options & 3) == 3) {
            this.linkType = urlLink;
            if (data[startpos] == 3) {
                this.linkType = fileLink;
            }
        } else if ((options & 1) != 0) {
            this.linkType = fileLink;
            if (data[startpos] == -32) {
                this.linkType = urlLink;
            }
        } else if ((options & 8) != 0) {
            this.linkType = workbookLink;
        }
        if (this.linkType == urlLink) {
            String urlString = null;
            try {
                int bytes = IntegerHelper.getInt(data[startpos += 16], data[startpos + 1], data[startpos + 2], data[startpos + 3]);
                urlString = StringHelper.getUnicodeString(data, bytes / 2 - 1, startpos + 4);
                this.url = new URL(urlString);
            }
            catch (MalformedURLException e) {
                logger.warn("URL " + urlString + " is malformed.  Trying a file");
                try {
                    this.linkType = fileLink;
                    this.file = new File(urlString);
                }
                catch (Exception e3) {
                    logger.warn("Cannot set to file.  Setting a default URL");
                    try {
                        this.linkType = urlLink;
                        this.url = new URL("http://www.andykhan.com/jexcelapi/index.html");
                    }
                    catch (MalformedURLException e2) {
                    }
                }
            }
            catch (Throwable e) {
                StringBuffer sb1 = new StringBuffer();
                StringBuffer sb2 = new StringBuffer();
                CellReferenceHelper.getCellReference(this.firstColumn, this.firstRow, sb1);
                CellReferenceHelper.getCellReference(this.lastColumn, this.lastRow, sb2);
                sb1.insert(0, "Exception when parsing URL ");
                sb1.append('\"').append(sb2.toString()).append("\".  Using default.");
                logger.warn(sb1, e);
                try {
                    this.url = new URL("http://www.andykhan.com/jexcelapi/index.html");
                }
                catch (MalformedURLException e2) {}
            }
        } else if (this.linkType == fileLink) {
            try {
                int upLevelCount = IntegerHelper.getInt(data[startpos += 16], data[startpos + 1]);
                int chars = IntegerHelper.getInt(data[startpos + 2], data[startpos + 3], data[startpos + 4], data[startpos + 5]);
                String fileName = StringHelper.getString(data, chars - 1, startpos + 6, ws);
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < upLevelCount; ++i) {
                    sb.append("..\\");
                }
                sb.append(fileName);
                this.file = new File(sb.toString());
            }
            catch (Throwable e) {
                e.printStackTrace();
                logger.warn("Exception when parsing file " + e.getClass().getName() + ".");
                this.file = new File(".");
            }
        } else if (this.linkType == workbookLink) {
            int chars = IntegerHelper.getInt(data[32], data[33], data[34], data[35]);
            this.location = StringHelper.getUnicodeString(data, chars - 1, 36);
        } else {
            logger.warn("Cannot determine link type");
            return;
        }
    }

    public boolean isFile() {
        return this.linkType == fileLink;
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

    public Record getRecord() {
        return super.getRecord();
    }

    public Range getRange() {
        return this.range;
    }

    public String getLocation() {
        return this.location;
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

