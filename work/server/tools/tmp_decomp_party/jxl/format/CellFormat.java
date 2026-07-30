/*
 * Decompiled with CFR 0.152.
 */
package jxl.format;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Font;
import jxl.format.Format;
import jxl.format.Orientation;
import jxl.format.Pattern;
import jxl.format.VerticalAlignment;

public interface CellFormat {
    public Format getFormat();

    public Font getFont();

    public boolean getWrap();

    public Alignment getAlignment();

    public VerticalAlignment getVerticalAlignment();

    public Orientation getOrientation();

    public BorderLineStyle getBorder(Border var1);

    public BorderLineStyle getBorderLine(Border var1);

    public Colour getBorderColour(Border var1);

    public boolean hasBorders();

    public Colour getBackgroundColour();

    public Pattern getPattern();

    public int getIndentation();

    public boolean isShrinkToFit();

    public boolean isLocked();
}

