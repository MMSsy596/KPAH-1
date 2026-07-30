/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import common.Assert;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import jxl.biff.FontRecord;
import jxl.biff.IndexMapping;
import jxl.write.biff.File;

public class Fonts {
    private ArrayList fonts = new ArrayList();
    private static final int numDefaultFonts = 4;

    public void addFont(FontRecord f) {
        if (!f.isInitialized()) {
            int pos = this.fonts.size();
            if (pos >= 4) {
                ++pos;
            }
            f.initialize(pos);
            this.fonts.add(f);
        }
    }

    public FontRecord getFont(int index) {
        if (index > 4) {
            --index;
        }
        return (FontRecord)this.fonts.get(index);
    }

    public void write(File outputFile) throws IOException {
        Iterator i = this.fonts.iterator();
        FontRecord font = null;
        while (i.hasNext()) {
            font = (FontRecord)i.next();
            outputFile.write(font);
        }
    }

    IndexMapping rationalize() {
        IndexMapping mapping = new IndexMapping(this.fonts.size() + 1);
        ArrayList<FontRecord> newfonts = new ArrayList<FontRecord>();
        FontRecord fr = null;
        int numremoved = 0;
        for (int i = 0; i < 4; ++i) {
            fr = (FontRecord)this.fonts.get(i);
            newfonts.add(fr);
            mapping.setMapping(fr.getFontIndex(), fr.getFontIndex());
        }
        Iterator it = null;
        FontRecord fr2 = null;
        boolean duplicate = false;
        for (int i = 4; i < this.fonts.size(); ++i) {
            fr = (FontRecord)this.fonts.get(i);
            duplicate = false;
            it = newfonts.iterator();
            while (it.hasNext() && !duplicate) {
                fr2 = (FontRecord)it.next();
                if (!fr.equals(fr2)) continue;
                duplicate = true;
                mapping.setMapping(fr.getFontIndex(), mapping.getNewIndex(fr2.getFontIndex()));
                ++numremoved;
            }
            if (duplicate) continue;
            newfonts.add(fr);
            int newindex = fr.getFontIndex() - numremoved;
            Assert.verify(newindex > 4);
            mapping.setMapping(fr.getFontIndex(), newindex);
        }
        it = newfonts.iterator();
        while (it.hasNext()) {
            fr = (FontRecord)it.next();
            fr.initialize(mapping.getNewIndex(fr.getFontIndex()));
        }
        this.fonts = newfonts;
        return mapping;
    }
}

