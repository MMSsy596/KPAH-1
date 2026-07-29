/*
 * Decompiled with CFR 0.152.
 */
package real;

import real.ItemTemplates;

public class NameAttributeItem {
    public byte id;
    public byte isPercent;
    public byte colorPaint = 0;
    String name = "";

    public NameAttributeItem(byte id, String name, byte isPercent, byte colorpaint) {
        this.id = id;
        this.name = name;
        this.isPercent = isPercent;
        this.colorPaint = colorpaint;
    }

    public boolean isPercent() {
        byte pc = ItemTemplates.ALL_NAME_ATTRIBUTE_ITEM.get((int)new Byte((byte)this.id).byteValue()).isPercent;
        return pc == 1 || pc == 2;
    }

    public String getValue(int value) {
        String vl = String.valueOf(this.id == 33 || this.id == 34 ? value * 1000 : value);
        if (this.isPercent == 2) {
            vl = String.valueOf(value / 10) + "." + value % 10;
        }
        return String.valueOf(vl) + (this.isPercent() ? "%" : "");
    }
}

