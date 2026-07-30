/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.Fonts;
import jxl.write.WritableFont;
import jxl.write.biff.WritableWorkbookImpl;

public class WritableFonts
extends Fonts {
    public WritableFonts(WritableWorkbookImpl w) {
        this.addFont(w.getStyles().getArial10Pt());
        WritableFont f = new WritableFont(WritableFont.ARIAL);
        this.addFont(f);
        f = new WritableFont(WritableFont.ARIAL);
        this.addFont(f);
        f = new WritableFont(WritableFont.ARIAL);
        this.addFont(f);
    }
}

