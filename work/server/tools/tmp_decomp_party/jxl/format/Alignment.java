/*
 * Decompiled with CFR 0.152.
 */
package jxl.format;

public class Alignment {
    private int value;
    private String string;
    private static Alignment[] alignments = new Alignment[0];
    public static Alignment GENERAL = new Alignment(0, "general");
    public static Alignment LEFT = new Alignment(1, "left");
    public static Alignment CENTRE = new Alignment(2, "centre");
    public static Alignment RIGHT = new Alignment(3, "right");
    public static Alignment FILL = new Alignment(4, "fill");
    public static Alignment JUSTIFY = new Alignment(5, "justify");

    protected Alignment(int val, String s) {
        this.value = val;
        this.string = s;
        Alignment[] oldaligns = alignments;
        alignments = new Alignment[oldaligns.length + 1];
        System.arraycopy(oldaligns, 0, alignments, 0, oldaligns.length);
        Alignment.alignments[oldaligns.length] = this;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.string;
    }

    public static Alignment getAlignment(int val) {
        for (int i = 0; i < alignments.length; ++i) {
            if (alignments[i].getValue() != val) continue;
            return alignments[i];
        }
        return GENERAL;
    }
}

