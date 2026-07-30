/*
 * Decompiled with CFR 0.152.
 */
package jxl.format;

import jxl.format.Colour;
import jxl.format.ScriptStyle;
import jxl.format.UnderlineStyle;

public interface Font {
    public String getName();

    public int getPointSize();

    public int getBoldWeight();

    public boolean isItalic();

    public boolean isStruckout();

    public UnderlineStyle getUnderlineStyle();

    public Colour getColour();

    public ScriptStyle getScriptStyle();
}

