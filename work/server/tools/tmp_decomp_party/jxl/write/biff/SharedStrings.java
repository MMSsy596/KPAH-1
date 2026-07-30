/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import jxl.write.biff.ExtendedSSTRecord;
import jxl.write.biff.File;
import jxl.write.biff.SSTContinueRecord;
import jxl.write.biff.SSTRecord;

class SharedStrings {
    private HashMap strings = new HashMap(100);
    private ArrayList stringList = new ArrayList(100);
    private int totalOccurrences = 0;

    public int getIndex(String s) {
        Integer i = (Integer)this.strings.get(s);
        if (i == null) {
            i = new Integer(this.strings.size());
            this.strings.put(s, i);
            this.stringList.add(s);
        }
        ++this.totalOccurrences;
        return i;
    }

    public String get(int i) {
        return (String)this.stringList.get(i);
    }

    public void write(File outputFile) throws IOException {
        int charsLeft = 0;
        String curString = null;
        SSTRecord sst = new SSTRecord(this.totalOccurrences, this.stringList.size());
        ExtendedSSTRecord extsst = new ExtendedSSTRecord(this.stringList.size());
        int bucketSize = extsst.getNumberOfStringsPerBucket();
        Iterator i = this.stringList.iterator();
        int stringIndex = 0;
        while (i.hasNext() && charsLeft == 0) {
            curString = (String)i.next();
            int relativePosition = sst.getOffset() + 4;
            charsLeft = sst.add(curString);
            if (stringIndex % bucketSize == 0) {
                extsst.addString(outputFile.getPos(), relativePosition);
            }
            ++stringIndex;
        }
        outputFile.write(sst);
        if (charsLeft != 0 || i.hasNext()) {
            SSTContinueRecord cont = this.createContinueRecord(curString, charsLeft, outputFile);
            while (i.hasNext()) {
                curString = (String)i.next();
                int relativePosition = cont.getOffset() + 4;
                charsLeft = cont.add(curString);
                if (stringIndex % bucketSize == 0) {
                    extsst.addString(outputFile.getPos(), relativePosition);
                }
                ++stringIndex;
                if (charsLeft == 0) continue;
                outputFile.write(cont);
                cont = this.createContinueRecord(curString, charsLeft, outputFile);
            }
            outputFile.write(cont);
        }
        outputFile.write(extsst);
    }

    private SSTContinueRecord createContinueRecord(String curString, int charsLeft, File outputFile) throws IOException {
        SSTContinueRecord cont = null;
        while (charsLeft != 0) {
            cont = new SSTContinueRecord();
            if ((charsLeft = charsLeft == curString.length() || curString.length() == 0 ? cont.setFirstString(curString, true) : cont.setFirstString(curString.substring(curString.length() - charsLeft), false)) == 0) continue;
            outputFile.write(cont);
            cont = new SSTContinueRecord();
        }
        return cont;
    }
}

