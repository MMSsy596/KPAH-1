/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import common.Assert;
import common.Logger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import jxl.WorkbookSettings;
import jxl.biff.BuiltInFormat;
import jxl.biff.DisplayFormat;
import jxl.biff.FontRecord;
import jxl.biff.Fonts;
import jxl.biff.FormatRecord;
import jxl.biff.FormattingRecords;
import jxl.biff.IndexMapping;
import jxl.biff.IntegerHelper;
import jxl.biff.NumFormatRecordsException;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.Font;
import jxl.format.Format;
import jxl.format.Orientation;
import jxl.format.Pattern;
import jxl.format.VerticalAlignment;
import jxl.read.biff.Record;

public class XFRecord
extends WritableRecordData
implements CellFormat {
    private static Logger logger = Logger.getLogger(class$jxl$biff$XFRecord == null ? (class$jxl$biff$XFRecord = XFRecord.class$("jxl.biff.XFRecord")) : class$jxl$biff$XFRecord);
    public int formatIndex;
    private int parentFormat;
    private XFType xfFormatType;
    private boolean date;
    private boolean number;
    private DateFormat dateFormat;
    private NumberFormat numberFormat;
    private byte usedAttributes;
    private int fontIndex;
    private boolean locked;
    private boolean hidden;
    private Alignment align;
    private VerticalAlignment valign;
    private Orientation orientation;
    private boolean wrap;
    private int indentation;
    private boolean shrinkToFit;
    private BorderLineStyle leftBorder;
    private BorderLineStyle rightBorder;
    private BorderLineStyle topBorder;
    private BorderLineStyle bottomBorder;
    private Colour leftBorderColour;
    private Colour rightBorderColour;
    private Colour topBorderColour;
    private Colour bottomBorderColour;
    private Colour backgroundColour;
    private Pattern pattern;
    private int options;
    private int xfIndex;
    private FontRecord font;
    private DisplayFormat format;
    private boolean initialized;
    private boolean read;
    private Format excelFormat;
    private boolean formatInfoInitialized;
    private boolean copied;
    private FormattingRecords formattingRecords;
    private static final int[] dateFormats = new int[]{14, 15, 16, 17, 18, 19, 20, 21, 22, 45, 46, 47};
    private static final DateFormat[] javaDateFormats = new DateFormat[]{new SimpleDateFormat("dd/MM/yyyy"), new SimpleDateFormat("d-MMM-yy"), new SimpleDateFormat("d-MMM"), new SimpleDateFormat("MMM-yy"), new SimpleDateFormat("h:mm a"), new SimpleDateFormat("h:mm:ss a"), new SimpleDateFormat("H:mm"), new SimpleDateFormat("H:mm:ss"), new SimpleDateFormat("M/d/yy H:mm"), new SimpleDateFormat("mm:ss"), new SimpleDateFormat("H:mm:ss"), new SimpleDateFormat("mm:ss.S")};
    private static int[] numberFormats = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 37, 38, 39, 40, 41, 42, 43, 44, 48};
    private static NumberFormat[] javaNumberFormats = new NumberFormat[]{new DecimalFormat("0"), new DecimalFormat("0.00"), new DecimalFormat("#,##0"), new DecimalFormat("#,##0.00"), new DecimalFormat("$#,##0;($#,##0)"), new DecimalFormat("$#,##0;($#,##0)"), new DecimalFormat("$#,##0.00;($#,##0.00)"), new DecimalFormat("$#,##0.00;($#,##0.00)"), new DecimalFormat("0%"), new DecimalFormat("0.00%"), new DecimalFormat("0.00E00"), new DecimalFormat("#,##0;(#,##0)"), new DecimalFormat("#,##0;(#,##0)"), new DecimalFormat("#,##0.00;(#,##0.00)"), new DecimalFormat("#,##0.00;(#,##0.00)"), new DecimalFormat("#,##0;(#,##0)"), new DecimalFormat("$#,##0;($#,##0)"), new DecimalFormat("#,##0.00;(#,##0.00)"), new DecimalFormat("$#,##0.00;($#,##0.00)"), new DecimalFormat("##0.0E0")};
    public static final BiffType biff8 = new BiffType();
    public static final BiffType biff7 = new BiffType();
    private BiffType biffType;
    protected static final XFType cell = new XFType();
    protected static final XFType style = new XFType();
    static /* synthetic */ Class class$jxl$biff$XFRecord;

    public XFRecord(Record t, WorkbookSettings ws, BiffType bt) {
        super(t);
        int i;
        this.biffType = bt;
        byte[] data = this.getRecord().getData();
        this.fontIndex = IntegerHelper.getInt(data[0], data[1]);
        this.formatIndex = IntegerHelper.getInt(data[2], data[3]);
        this.date = false;
        this.number = false;
        for (i = 0; i < dateFormats.length && !this.date; ++i) {
            if (this.formatIndex != dateFormats[i]) continue;
            this.date = true;
            this.dateFormat = javaDateFormats[i];
        }
        for (i = 0; i < numberFormats.length && !this.number; ++i) {
            if (this.formatIndex != numberFormats[i]) continue;
            this.number = true;
            DecimalFormat df = (DecimalFormat)javaNumberFormats[i].clone();
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(ws.getLocale());
            df.setDecimalFormatSymbols(symbols);
            this.numberFormat = df;
        }
        int cellAttributes = IntegerHelper.getInt(data[4], data[5]);
        this.parentFormat = (cellAttributes & 0xFFF0) >> 4;
        int formatType = cellAttributes & 4;
        this.xfFormatType = formatType == 0 ? cell : style;
        this.locked = (cellAttributes & 1) != 0;
        boolean bl = this.hidden = (cellAttributes & 2) != 0;
        if (this.xfFormatType == cell && (this.parentFormat & 0xFFF) == 4095) {
            this.parentFormat = 0;
            logger.warn("Invalid parent format found - ignoring");
        }
        this.initialized = false;
        this.read = true;
        this.formatInfoInitialized = false;
        this.copied = false;
    }

    public XFRecord(FontRecord fnt, DisplayFormat form) {
        super(Type.XF);
        this.initialized = false;
        this.locked = true;
        this.hidden = false;
        this.align = Alignment.GENERAL;
        this.valign = VerticalAlignment.BOTTOM;
        this.orientation = Orientation.HORIZONTAL;
        this.wrap = false;
        this.leftBorder = BorderLineStyle.NONE;
        this.rightBorder = BorderLineStyle.NONE;
        this.topBorder = BorderLineStyle.NONE;
        this.bottomBorder = BorderLineStyle.NONE;
        this.leftBorderColour = Colour.AUTOMATIC;
        this.rightBorderColour = Colour.AUTOMATIC;
        this.topBorderColour = Colour.AUTOMATIC;
        this.bottomBorderColour = Colour.AUTOMATIC;
        this.pattern = Pattern.NONE;
        this.backgroundColour = Colour.DEFAULT_BACKGROUND;
        this.indentation = 0;
        this.shrinkToFit = false;
        this.parentFormat = 0;
        this.xfFormatType = null;
        this.font = fnt;
        this.format = form;
        this.biffType = biff8;
        this.read = false;
        this.copied = false;
        this.formatInfoInitialized = true;
        Assert.verify(this.font != null);
        Assert.verify(this.format != null);
    }

    protected XFRecord(XFRecord fmt) {
        super(Type.XF);
        this.initialized = false;
        this.locked = fmt.locked;
        this.hidden = fmt.hidden;
        this.align = fmt.align;
        this.valign = fmt.valign;
        this.orientation = fmt.orientation;
        this.wrap = fmt.wrap;
        this.leftBorder = fmt.leftBorder;
        this.rightBorder = fmt.rightBorder;
        this.topBorder = fmt.topBorder;
        this.bottomBorder = fmt.bottomBorder;
        this.leftBorderColour = fmt.leftBorderColour;
        this.rightBorderColour = fmt.rightBorderColour;
        this.topBorderColour = fmt.topBorderColour;
        this.bottomBorderColour = fmt.bottomBorderColour;
        this.pattern = fmt.pattern;
        this.xfFormatType = fmt.xfFormatType;
        this.indentation = fmt.indentation;
        this.shrinkToFit = fmt.shrinkToFit;
        this.parentFormat = fmt.parentFormat;
        this.backgroundColour = fmt.backgroundColour;
        this.font = fmt.font;
        this.format = fmt.format;
        this.fontIndex = fmt.fontIndex;
        this.formatIndex = fmt.formatIndex;
        this.formatInfoInitialized = fmt.formatInfoInitialized;
        this.biffType = biff8;
        this.read = false;
        this.copied = true;
    }

    protected XFRecord(CellFormat cellFormat) {
        super(Type.XF);
        Assert.verify(cellFormat != null);
        Assert.verify(cellFormat instanceof XFRecord);
        XFRecord fmt = (XFRecord)cellFormat;
        if (!fmt.formatInfoInitialized) {
            fmt.initializeFormatInformation();
        }
        this.locked = fmt.locked;
        this.hidden = fmt.hidden;
        this.align = fmt.align;
        this.valign = fmt.valign;
        this.orientation = fmt.orientation;
        this.wrap = fmt.wrap;
        this.leftBorder = fmt.leftBorder;
        this.rightBorder = fmt.rightBorder;
        this.topBorder = fmt.topBorder;
        this.bottomBorder = fmt.bottomBorder;
        this.leftBorderColour = fmt.leftBorderColour;
        this.rightBorderColour = fmt.rightBorderColour;
        this.topBorderColour = fmt.topBorderColour;
        this.bottomBorderColour = fmt.bottomBorderColour;
        this.pattern = fmt.pattern;
        this.xfFormatType = fmt.xfFormatType;
        this.parentFormat = fmt.parentFormat;
        this.indentation = fmt.indentation;
        this.shrinkToFit = fmt.shrinkToFit;
        this.backgroundColour = fmt.backgroundColour;
        this.font = new FontRecord(fmt.getFont());
        if (fmt.getFormat() == null) {
            this.format = fmt.format.isBuiltIn() ? fmt.format : new FormatRecord((FormatRecord)fmt.format);
        } else if (fmt.getFormat() instanceof BuiltInFormat) {
            this.excelFormat = (BuiltInFormat)fmt.excelFormat;
            this.format = (BuiltInFormat)fmt.excelFormat;
        } else {
            Assert.verify(fmt.formatInfoInitialized);
            Assert.verify(fmt.excelFormat instanceof FormatRecord);
            FormatRecord fr = new FormatRecord((FormatRecord)fmt.excelFormat);
            this.excelFormat = fr;
            this.format = fr;
        }
        this.biffType = biff8;
        this.formatInfoInitialized = true;
        this.read = false;
        this.copied = false;
        this.initialized = false;
    }

    public DateFormat getDateFormat() {
        return this.dateFormat;
    }

    public NumberFormat getNumberFormat() {
        return this.numberFormat;
    }

    public int getFormatRecord() {
        return this.formatIndex;
    }

    public boolean isDate() {
        return this.date;
    }

    public boolean isNumber() {
        return this.number;
    }

    public byte[] getData() {
        if (!this.formatInfoInitialized) {
            this.initializeFormatInformation();
        }
        byte[] data = new byte[20];
        IntegerHelper.getTwoBytes(this.fontIndex, data, 0);
        IntegerHelper.getTwoBytes(this.formatIndex, data, 2);
        int cellAttributes = 0;
        if (this.getLocked()) {
            cellAttributes |= 1;
        }
        if (this.getHidden()) {
            cellAttributes |= 2;
        }
        if (this.xfFormatType == style) {
            cellAttributes |= 4;
            this.parentFormat = 65535;
        }
        IntegerHelper.getTwoBytes(cellAttributes |= this.parentFormat << 4, data, 4);
        int alignMask = this.align.getValue();
        if (this.wrap) {
            alignMask |= 8;
        }
        alignMask |= this.valign.getValue() << 4;
        IntegerHelper.getTwoBytes(alignMask |= this.orientation.getValue() << 8, data, 6);
        data[9] = 16;
        int borderMask = this.leftBorder.getValue();
        borderMask |= this.rightBorder.getValue() << 4;
        borderMask |= this.topBorder.getValue() << 8;
        IntegerHelper.getTwoBytes(borderMask |= this.bottomBorder.getValue() << 12, data, 10);
        if (borderMask != 0) {
            byte lc = (byte)this.leftBorderColour.getValue();
            byte rc = (byte)this.rightBorderColour.getValue();
            byte tc = (byte)this.topBorderColour.getValue();
            byte bc = (byte)this.bottomBorderColour.getValue();
            int sideColourMask = lc & 0x7F | (rc & 0x7F) << 7;
            int topColourMask = tc & 0x7F | (bc & 0x7F) << 7;
            IntegerHelper.getTwoBytes(sideColourMask, data, 12);
            IntegerHelper.getTwoBytes(topColourMask, data, 14);
        }
        int patternVal = this.pattern.getValue() << 10;
        IntegerHelper.getTwoBytes(patternVal, data, 16);
        int colourPaletteMask = this.backgroundColour.getValue();
        IntegerHelper.getTwoBytes(colourPaletteMask |= 0x2000, data, 18);
        this.options |= this.indentation & 0xF;
        this.options = this.shrinkToFit ? (this.options |= 0x10) : (this.options &= 0xEF);
        data[8] = (byte)this.options;
        if (this.biffType == biff8) {
            data[9] = this.usedAttributes;
        }
        return data;
    }

    protected final boolean getLocked() {
        return this.locked;
    }

    protected final boolean getHidden() {
        return this.hidden;
    }

    protected final void setXFLocked(boolean l) {
        this.locked = l;
    }

    protected final void setXFCellOptions(int opt) {
        this.options |= opt;
    }

    protected void setXFAlignment(Alignment a) {
        Assert.verify(!this.initialized);
        this.align = a;
    }

    protected void setXFIndentation(int i) {
        Assert.verify(!this.initialized);
        this.indentation = i;
    }

    protected void setXFShrinkToFit(boolean s) {
        Assert.verify(!this.initialized);
        this.shrinkToFit = s;
    }

    public Alignment getAlignment() {
        if (!this.formatInfoInitialized) {
            this.initializeFormatInformation();
        }
        return this.align;
    }

    public int getIndentation() {
        if (!this.formatInfoInitialized) {
            this.initializeFormatInformation();
        }
        return this.indentation;
    }

    public boolean isShrinkToFit() {
        if (!this.formatInfoInitialized) {
            this.initializeFormatInformation();
        }
        return this.shrinkToFit;
    }

    public boolean isLocked() {
        if (!this.formatInfoInitialized) {
            this.initializeFormatInformation();
        }
        return this.locked;
    }

    public VerticalAlignment getVerticalAlignment() {
        if (!this.formatInfoInitialized) {
            this.initializeFormatInformation();
        }
        return this.valign;
    }

    public Orientation getOrientation() {
        if (!this.formatInfoInitialized) {
            this.initializeFormatInformation();
        }
        return this.orientation;
    }

    protected void setXFBackground(Colour c, Pattern p) {
        Assert.verify(!this.initialized);
        this.backgroundColour = c;
        this.pattern = p;
    }

    public Colour getBackgroundColour() {
        if (!this.formatInfoInitialized) {
            this.initializeFormatInformation();
        }
        return this.backgroundColour;
    }

    public Pattern getPattern() {
        if (!this.formatInfoInitialized) {
            this.initializeFormatInformation();
        }
        return this.pattern;
    }

    protected void setXFVerticalAlignment(VerticalAlignment va) {
        Assert.verify(!this.initialized);
        this.valign = va;
    }

    protected void setXFOrientation(Orientation o) {
        Assert.verify(!this.initialized);
        this.orientation = o;
    }

    protected void setXFWrap(boolean w) {
        Assert.verify(!this.initialized);
        this.wrap = w;
    }

    public boolean getWrap() {
        if (!this.formatInfoInitialized) {
            this.initializeFormatInformation();
        }
        return this.wrap;
    }

    protected void setXFBorder(Border b, BorderLineStyle ls, Colour c) {
        Assert.verify(!this.initialized);
        if (c == Colour.BLACK) {
            c = Colour.PALETTE_BLACK;
        }
        if (b == Border.LEFT) {
            this.leftBorder = ls;
            this.leftBorderColour = c;
        } else if (b == Border.RIGHT) {
            this.rightBorder = ls;
            this.rightBorderColour = c;
        } else if (b == Border.TOP) {
            this.topBorder = ls;
            this.topBorderColour = c;
        } else if (b == Border.BOTTOM) {
            this.bottomBorder = ls;
            this.bottomBorderColour = c;
        }
    }

    public BorderLineStyle getBorder(Border border) {
        return this.getBorderLine(border);
    }

    public BorderLineStyle getBorderLine(Border border) {
        if (border == Border.NONE || border == Border.ALL) {
            return BorderLineStyle.NONE;
        }
        if (!this.formatInfoInitialized) {
            this.initializeFormatInformation();
        }
        if (border == Border.LEFT) {
            return this.leftBorder;
        }
        if (border == Border.RIGHT) {
            return this.rightBorder;
        }
        if (border == Border.TOP) {
            return this.topBorder;
        }
        if (border == Border.BOTTOM) {
            return this.bottomBorder;
        }
        return BorderLineStyle.NONE;
    }

    public Colour getBorderColour(Border border) {
        if (border == Border.NONE || border == Border.ALL) {
            return Colour.PALETTE_BLACK;
        }
        if (!this.formatInfoInitialized) {
            this.initializeFormatInformation();
        }
        if (border == Border.LEFT) {
            return this.leftBorderColour;
        }
        if (border == Border.RIGHT) {
            return this.rightBorderColour;
        }
        if (border == Border.TOP) {
            return this.topBorderColour;
        }
        if (border == Border.BOTTOM) {
            return this.bottomBorderColour;
        }
        return Colour.BLACK;
    }

    public final boolean hasBorders() {
        if (!this.formatInfoInitialized) {
            this.initializeFormatInformation();
        }
        return this.leftBorder != BorderLineStyle.NONE || this.rightBorder != BorderLineStyle.NONE || this.topBorder != BorderLineStyle.NONE || this.bottomBorder != BorderLineStyle.NONE;
    }

    public final void initialize(int pos, FormattingRecords fr, Fonts fonts) throws NumFormatRecordsException {
        this.xfIndex = pos;
        this.formattingRecords = fr;
        if (this.read || this.copied) {
            this.initialized = true;
            return;
        }
        if (!this.font.isInitialized()) {
            fonts.addFont(this.font);
        }
        if (!this.format.isInitialized()) {
            fr.addFormat(this.format);
        }
        this.fontIndex = this.font.getFontIndex();
        this.formatIndex = this.format.getFormatIndex();
        this.initialized = true;
    }

    public final void uninitialize() {
        if (this.initialized) {
            logger.warn("A default format has been initialized");
        }
        this.initialized = false;
    }

    final void setXFIndex(int xfi) {
        this.xfIndex = xfi;
    }

    public final int getXFIndex() {
        return this.xfIndex;
    }

    public final boolean isInitialized() {
        return this.initialized;
    }

    public final boolean isRead() {
        return this.read;
    }

    public Format getFormat() {
        if (!this.formatInfoInitialized) {
            this.initializeFormatInformation();
        }
        return this.excelFormat;
    }

    public Font getFont() {
        if (!this.formatInfoInitialized) {
            this.initializeFormatInformation();
        }
        return this.font;
    }

    private void initializeFormatInformation() {
        int alignMask;
        this.excelFormat = this.formatIndex < BuiltInFormat.builtIns.length && BuiltInFormat.builtIns[this.formatIndex] != null ? BuiltInFormat.builtIns[this.formatIndex] : this.formattingRecords.getFormatRecord(this.formatIndex);
        this.font = this.formattingRecords.getFonts().getFont(this.fontIndex);
        byte[] data = this.getRecord().getData();
        int cellAttributes = IntegerHelper.getInt(data[4], data[5]);
        this.parentFormat = (cellAttributes & 0xFFF0) >> 4;
        int formatType = cellAttributes & 4;
        this.xfFormatType = formatType == 0 ? cell : style;
        this.locked = (cellAttributes & 1) != 0;
        boolean bl = this.hidden = (cellAttributes & 2) != 0;
        if (this.xfFormatType == cell && (this.parentFormat & 0xFFF) == 4095) {
            this.parentFormat = 0;
            logger.warn("Invalid parent format found - ignoring");
        }
        if (((alignMask = IntegerHelper.getInt(data[6], data[7])) & 8) != 0) {
            this.wrap = true;
        }
        this.align = Alignment.getAlignment(alignMask & 7);
        this.valign = VerticalAlignment.getAlignment(alignMask >> 4 & 7);
        this.orientation = Orientation.getOrientation(alignMask >> 8 & 0xFF);
        int attr = IntegerHelper.getInt(data[8], data[9]);
        this.indentation = attr & 0xF;
        boolean bl2 = this.shrinkToFit = (attr & 0x10) != 0;
        if (this.biffType == biff8) {
            this.usedAttributes = data[9];
        }
        int borderMask = IntegerHelper.getInt(data[10], data[11]);
        this.leftBorder = BorderLineStyle.getStyle(borderMask & 7);
        this.rightBorder = BorderLineStyle.getStyle(borderMask >> 4 & 7);
        this.topBorder = BorderLineStyle.getStyle(borderMask >> 8 & 7);
        this.bottomBorder = BorderLineStyle.getStyle(borderMask >> 12 & 7);
        int borderColourMask = IntegerHelper.getInt(data[12], data[13]);
        this.leftBorderColour = Colour.getInternalColour(borderColourMask & 0x7F);
        this.rightBorderColour = Colour.getInternalColour((borderColourMask & 0x3F80) >> 7);
        borderColourMask = IntegerHelper.getInt(data[14], data[15]);
        this.topBorderColour = Colour.getInternalColour(borderColourMask & 0x7F);
        this.bottomBorderColour = Colour.getInternalColour((borderColourMask & 0x3F80) >> 7);
        if (this.biffType == biff8) {
            int patternVal = IntegerHelper.getInt(data[16], data[17]);
            patternVal &= 0xFC00;
            this.pattern = Pattern.getPattern(patternVal >>= 10);
            int colourPaletteMask = IntegerHelper.getInt(data[18], data[19]);
            this.backgroundColour = Colour.getInternalColour(colourPaletteMask & 0x3F);
            if (this.backgroundColour == Colour.UNKNOWN || this.backgroundColour == Colour.DEFAULT_BACKGROUND1) {
                this.backgroundColour = Colour.DEFAULT_BACKGROUND;
            }
        } else {
            this.pattern = Pattern.NONE;
            this.backgroundColour = Colour.DEFAULT_BACKGROUND;
        }
        this.formatInfoInitialized = true;
    }

    public int hashCode() {
        if (!this.formatInfoInitialized) {
            this.initializeFormatInformation();
        }
        int hashValue = 17;
        int oddPrimeNumber = 37;
        hashValue = oddPrimeNumber * hashValue + (this.hidden ? 1 : 0);
        hashValue = oddPrimeNumber * hashValue + (this.locked ? 1 : 0);
        hashValue = oddPrimeNumber * hashValue + (this.wrap ? 1 : 0);
        hashValue = oddPrimeNumber * hashValue + (this.shrinkToFit ? 1 : 0);
        if (this.xfFormatType == cell) {
            hashValue = oddPrimeNumber * hashValue + 1;
        } else if (this.xfFormatType == style) {
            hashValue = oddPrimeNumber * hashValue + 2;
        }
        hashValue = oddPrimeNumber * hashValue + (this.align.getValue() + 1);
        hashValue = oddPrimeNumber * hashValue + (this.valign.getValue() + 1);
        hashValue = oddPrimeNumber * hashValue + this.orientation.getValue();
        hashValue ^= this.leftBorder.getDescription().hashCode();
        hashValue ^= this.rightBorder.getDescription().hashCode();
        hashValue ^= this.topBorder.getDescription().hashCode();
        hashValue ^= this.bottomBorder.getDescription().hashCode();
        hashValue = oddPrimeNumber * hashValue + this.leftBorderColour.getValue();
        hashValue = oddPrimeNumber * hashValue + this.rightBorderColour.getValue();
        hashValue = oddPrimeNumber * hashValue + this.topBorderColour.getValue();
        hashValue = oddPrimeNumber * hashValue + this.bottomBorderColour.getValue();
        hashValue = oddPrimeNumber * hashValue + this.backgroundColour.getValue();
        hashValue = oddPrimeNumber * hashValue + (this.pattern.getValue() + 1);
        hashValue = oddPrimeNumber * hashValue + this.usedAttributes;
        hashValue = oddPrimeNumber * hashValue + this.parentFormat;
        hashValue = oddPrimeNumber * hashValue + this.fontIndex;
        hashValue = oddPrimeNumber * hashValue + this.formatIndex;
        hashValue = oddPrimeNumber * hashValue + this.indentation;
        return hashValue;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof XFRecord)) {
            return false;
        }
        XFRecord xfr = (XFRecord)o;
        if (!this.formatInfoInitialized) {
            this.initializeFormatInformation();
        }
        if (!xfr.formatInfoInitialized) {
            xfr.initializeFormatInformation();
        }
        if (this.xfFormatType != xfr.xfFormatType || this.parentFormat != xfr.parentFormat || this.locked != xfr.locked || this.hidden != xfr.hidden || this.usedAttributes != xfr.usedAttributes) {
            return false;
        }
        if (this.align != xfr.align || this.valign != xfr.valign || this.orientation != xfr.orientation || this.wrap != xfr.wrap || this.shrinkToFit != xfr.shrinkToFit || this.indentation != xfr.indentation) {
            return false;
        }
        if (this.leftBorder != xfr.leftBorder || this.rightBorder != xfr.rightBorder || this.topBorder != xfr.topBorder || this.bottomBorder != xfr.bottomBorder) {
            return false;
        }
        if (this.leftBorderColour != xfr.leftBorderColour || this.rightBorderColour != xfr.rightBorderColour || this.topBorderColour != xfr.topBorderColour || this.bottomBorderColour != xfr.bottomBorderColour) {
            return false;
        }
        if (this.backgroundColour != xfr.backgroundColour || this.pattern != xfr.pattern) {
            return false;
        }
        return !(this.initialized && xfr.initialized ? this.fontIndex != xfr.fontIndex || this.formatIndex != xfr.formatIndex : !this.font.equals(xfr.font) || !this.format.equals(xfr.format));
    }

    void setFormatIndex(int newindex) {
        this.formatIndex = newindex;
    }

    int getFontIndex() {
        return this.fontIndex;
    }

    void setFontIndex(int newindex) {
        this.fontIndex = newindex;
    }

    protected void setXFDetails(XFType t, int pf) {
        this.xfFormatType = t;
        this.parentFormat = pf;
    }

    void rationalize(IndexMapping xfMapping) {
        this.xfIndex = xfMapping.getNewIndex(this.xfIndex);
        if (this.xfFormatType == cell) {
            this.parentFormat = xfMapping.getNewIndex(this.parentFormat);
        }
    }

    public void setFont(FontRecord f) {
        this.font = f;
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    private static class XFType {
        private XFType() {
        }
    }

    private static class BiffType {
        private BiffType() {
        }
    }
}

