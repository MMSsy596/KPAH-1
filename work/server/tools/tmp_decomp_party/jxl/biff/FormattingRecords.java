/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import common.Assert;
import common.Logger;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import jxl.biff.BuiltInStyle;
import jxl.biff.DisplayFormat;
import jxl.biff.Fonts;
import jxl.biff.FormatRecord;
import jxl.biff.IndexMapping;
import jxl.biff.NumFormatRecordsException;
import jxl.biff.PaletteRecord;
import jxl.biff.XFRecord;
import jxl.format.Colour;
import jxl.format.RGB;
import jxl.write.biff.File;

public class FormattingRecords {
    private static Logger logger = Logger.getLogger(class$jxl$biff$FormattingRecords == null ? (class$jxl$biff$FormattingRecords = FormattingRecords.class$("jxl.biff.FormattingRecords")) : class$jxl$biff$FormattingRecords);
    private HashMap formats;
    private ArrayList formatsList;
    private ArrayList xfRecords = new ArrayList(10);
    private int nextCustomIndexNumber;
    private Fonts fonts;
    private PaletteRecord palette;
    private static final int customFormatStartIndex = 164;
    private static final int maxFormatRecordsIndex = 441;
    private static final int minXFRecords = 21;
    static /* synthetic */ Class class$jxl$biff$FormattingRecords;

    public FormattingRecords(Fonts f) {
        this.formats = new HashMap(10);
        this.formatsList = new ArrayList(10);
        this.fonts = f;
        this.nextCustomIndexNumber = 164;
    }

    public final void addStyle(XFRecord xf) throws NumFormatRecordsException {
        if (!xf.isInitialized()) {
            int pos = this.xfRecords.size();
            xf.initialize(pos, this, this.fonts);
            this.xfRecords.add(xf);
        } else if (xf.getXFIndex() >= this.xfRecords.size()) {
            this.xfRecords.add(xf);
        }
    }

    public final void addFormat(DisplayFormat fr) throws NumFormatRecordsException {
        if (fr.isInitialized() && fr.getFormatIndex() >= 441) {
            logger.warn("Format index exceeds Excel maximum - assigning custom number");
            fr.initialize(this.nextCustomIndexNumber);
            ++this.nextCustomIndexNumber;
        }
        if (!fr.isInitialized()) {
            fr.initialize(this.nextCustomIndexNumber);
            ++this.nextCustomIndexNumber;
        }
        if (this.nextCustomIndexNumber > 441) {
            this.nextCustomIndexNumber = 441;
            throw new NumFormatRecordsException();
        }
        if (fr.getFormatIndex() >= this.nextCustomIndexNumber) {
            this.nextCustomIndexNumber = fr.getFormatIndex() + 1;
        }
        if (!fr.isBuiltIn()) {
            this.formatsList.add(fr);
            this.formats.put(new Integer(fr.getFormatIndex()), fr);
        }
    }

    public final boolean isDate(int pos) {
        XFRecord xfr = (XFRecord)this.xfRecords.get(pos);
        if (xfr.isDate()) {
            return true;
        }
        FormatRecord fr = (FormatRecord)this.formats.get(new Integer(xfr.getFormatRecord()));
        return fr == null ? false : fr.isDate();
    }

    public final DateFormat getDateFormat(int pos) {
        XFRecord xfr = (XFRecord)this.xfRecords.get(pos);
        if (xfr.isDate()) {
            return xfr.getDateFormat();
        }
        FormatRecord fr = (FormatRecord)this.formats.get(new Integer(xfr.getFormatRecord()));
        if (fr == null) {
            return null;
        }
        return fr.isDate() ? fr.getDateFormat() : null;
    }

    public final NumberFormat getNumberFormat(int pos) {
        XFRecord xfr = (XFRecord)this.xfRecords.get(pos);
        if (xfr.isNumber()) {
            return xfr.getNumberFormat();
        }
        FormatRecord fr = (FormatRecord)this.formats.get(new Integer(xfr.getFormatRecord()));
        if (fr == null) {
            return null;
        }
        return fr.isNumber() ? fr.getNumberFormat() : null;
    }

    FormatRecord getFormatRecord(int index) {
        return (FormatRecord)this.formats.get(new Integer(index));
    }

    public void write(File outputFile) throws IOException {
        Iterator i = this.formatsList.iterator();
        FormatRecord fr = null;
        while (i.hasNext()) {
            fr = (FormatRecord)i.next();
            outputFile.write(fr);
        }
        i = this.xfRecords.iterator();
        XFRecord xfr = null;
        while (i.hasNext()) {
            xfr = (XFRecord)i.next();
            outputFile.write(xfr);
        }
        BuiltInStyle style = new BuiltInStyle(16, 3);
        outputFile.write(style);
        style = new BuiltInStyle(17, 6);
        outputFile.write(style);
        style = new BuiltInStyle(18, 4);
        outputFile.write(style);
        style = new BuiltInStyle(19, 7);
        outputFile.write(style);
        style = new BuiltInStyle(0, 0);
        outputFile.write(style);
        style = new BuiltInStyle(20, 5);
        outputFile.write(style);
    }

    protected final Fonts getFonts() {
        return this.fonts;
    }

    public final XFRecord getXFRecord(int index) {
        return (XFRecord)this.xfRecords.get(index);
    }

    protected final int getNumberOfFormatRecords() {
        return this.formatsList.size();
    }

    public IndexMapping rationalizeFonts() {
        return this.fonts.rationalize();
    }

    public IndexMapping rationalize(IndexMapping fontMapping, IndexMapping formatMapping) {
        XFRecord xf;
        int i;
        XFRecord xfr = null;
        Iterator it = this.xfRecords.iterator();
        while (it.hasNext()) {
            xfr = (XFRecord)it.next();
            if (xfr.getFormatRecord() >= 164) {
                xfr.setFormatIndex(formatMapping.getNewIndex(xfr.getFormatRecord()));
            }
            xfr.setFontIndex(fontMapping.getNewIndex(xfr.getFontIndex()));
        }
        ArrayList newrecords = new ArrayList(21);
        IndexMapping mapping = new IndexMapping(this.xfRecords.size());
        int numremoved = 0;
        int numXFRecords = Math.min(21, this.xfRecords.size());
        for (i = 0; i < numXFRecords; ++i) {
            newrecords.add(this.xfRecords.get(i));
            mapping.setMapping(i, i);
        }
        if (numXFRecords < 21) {
            logger.warn("There are less than the expected minimum number of XF records");
            return mapping;
        }
        for (i = 21; i < this.xfRecords.size(); ++i) {
            xf = (XFRecord)this.xfRecords.get(i);
            boolean duplicate = false;
            Iterator it2 = newrecords.iterator();
            while (it2.hasNext() && !duplicate) {
                XFRecord xf2 = (XFRecord)it2.next();
                if (!xf2.equals(xf)) continue;
                duplicate = true;
                mapping.setMapping(i, mapping.getNewIndex(xf2.getXFIndex()));
                ++numremoved;
            }
            if (duplicate) continue;
            newrecords.add(xf);
            mapping.setMapping(i, i - numremoved);
        }
        Iterator i2 = this.xfRecords.iterator();
        while (i2.hasNext()) {
            xf = (XFRecord)i2.next();
            xf.rationalize(mapping);
        }
        this.xfRecords = newrecords;
        return mapping;
    }

    public IndexMapping rationalizeDisplayFormats() {
        ArrayList<DisplayFormat> newformats = new ArrayList<DisplayFormat>();
        int numremoved = 0;
        IndexMapping mapping = new IndexMapping(this.nextCustomIndexNumber);
        Iterator i = this.formatsList.iterator();
        DisplayFormat df = null;
        DisplayFormat df2 = null;
        boolean duplicate = false;
        while (i.hasNext()) {
            df = (DisplayFormat)i.next();
            Assert.verify(!df.isBuiltIn());
            Iterator i2 = newformats.iterator();
            duplicate = false;
            while (i2.hasNext() && !duplicate) {
                df2 = (DisplayFormat)i2.next();
                if (!df2.equals(df)) continue;
                duplicate = true;
                mapping.setMapping(df.getFormatIndex(), mapping.getNewIndex(df2.getFormatIndex()));
                ++numremoved;
            }
            if (duplicate) continue;
            newformats.add(df);
            int indexnum = df.getFormatIndex() - numremoved;
            if (indexnum > 441) {
                logger.warn("Too many number formats - using default format.");
                indexnum = 0;
            }
            mapping.setMapping(df.getFormatIndex(), df.getFormatIndex() - numremoved);
        }
        this.formatsList = newformats;
        i = this.formatsList.iterator();
        while (i.hasNext()) {
            df = (DisplayFormat)i.next();
            df.initialize(mapping.getNewIndex(df.getFormatIndex()));
        }
        return mapping;
    }

    public PaletteRecord getPalette() {
        return this.palette;
    }

    public void setPalette(PaletteRecord pr) {
        this.palette = pr;
    }

    public void setColourRGB(Colour c, int r, int g, int b) {
        if (this.palette == null) {
            this.palette = new PaletteRecord();
        }
        this.palette.setColourRGB(c, r, g, b);
    }

    public RGB getColourRGB(Colour c) {
        if (this.palette == null) {
            return c.getDefaultRGB();
        }
        return this.palette.getColourRGB(c);
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

