/*
 * Decompiled with CFR 0.152.
 */
package jxl.format;

public class BoldStyle {
    private int value;
    private String string;
    public static final BoldStyle NORMAL = new BoldStyle(400, "Normal");
    public static final BoldStyle BOLD = new BoldStyle(700, "Bold");

    protected BoldStyle(int val, String s) {
        this.value = val;
        this.string = s;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.string;
    }
}

