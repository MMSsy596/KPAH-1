/*
 * Decompiled with CFR 0.152.
 */
package jxl.format;

public final class UnderlineStyle {
    private int value;
    private String string;
    private static UnderlineStyle[] styles = new UnderlineStyle[0];
    public static final UnderlineStyle NO_UNDERLINE = new UnderlineStyle(0, "none");
    public static final UnderlineStyle SINGLE = new UnderlineStyle(1, "single");
    public static final UnderlineStyle DOUBLE = new UnderlineStyle(2, "double");
    public static final UnderlineStyle SINGLE_ACCOUNTING = new UnderlineStyle(33, "single accounting");
    public static final UnderlineStyle DOUBLE_ACCOUNTING = new UnderlineStyle(34, "double accounting");

    protected UnderlineStyle(int val, String s) {
        this.value = val;
        this.string = s;
        UnderlineStyle[] oldstyles = styles;
        styles = new UnderlineStyle[oldstyles.length + 1];
        System.arraycopy(oldstyles, 0, styles, 0, oldstyles.length);
        UnderlineStyle.styles[oldstyles.length] = this;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.string;
    }

    public static UnderlineStyle getStyle(int val) {
        for (int i = 0; i < styles.length; ++i) {
            if (styles[i].getValue() != val) continue;
            return styles[i];
        }
        return NO_UNDERLINE;
    }
}

