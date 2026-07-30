/*
 * Decompiled with CFR 0.152.
 */
package jxl.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import jxl.WorkbookSettings;
import jxl.biff.BaseCompoundFile;
import jxl.read.biff.BiffException;
import jxl.read.biff.CompoundFile;

class PropertySetsReader {
    private BufferedWriter writer;
    private CompoundFile compoundFile;

    public PropertySetsReader(File file, String propertySet, OutputStream os) throws IOException, BiffException {
        int bytesRead;
        this.writer = new BufferedWriter(new OutputStreamWriter(os));
        FileInputStream fis = new FileInputStream(file);
        int initialFileSize = 0x100000;
        int arrayGrowSize = 0x100000;
        byte[] d = new byte[initialFileSize];
        int pos = bytesRead = fis.read(d);
        while (bytesRead != -1) {
            if (pos >= d.length) {
                byte[] newArray = new byte[d.length + arrayGrowSize];
                System.arraycopy(d, 0, newArray, 0, d.length);
                d = newArray;
            }
            bytesRead = fis.read(d, pos, d.length - pos);
            pos += bytesRead;
        }
        bytesRead = pos + 1;
        this.compoundFile = new CompoundFile(d, new WorkbookSettings());
        fis.close();
        if (propertySet == null) {
            this.displaySets();
        } else {
            this.displayPropertySet(propertySet, os);
        }
    }

    void displaySets() throws IOException {
        int numSets = this.compoundFile.getNumberOfPropertySets();
        for (int i = 0; i < numSets; ++i) {
            BaseCompoundFile.PropertyStorage ps = this.compoundFile.getPropertySet(i);
            this.writer.write(Integer.toString(i));
            this.writer.write(") ");
            this.writer.write(ps.name);
            this.writer.write("(type ");
            this.writer.write(Integer.toString(ps.type));
            this.writer.write(" size ");
            this.writer.write(Integer.toString(ps.size));
            this.writer.write(" prev ");
            this.writer.write(Integer.toString(ps.previous));
            this.writer.write(" next ");
            this.writer.write(Integer.toString(ps.next));
            this.writer.write(" child ");
            this.writer.write(Integer.toString(ps.child));
            this.writer.write(" start block ");
            this.writer.write(Integer.toString(ps.startBlock));
            this.writer.write(")");
            this.writer.newLine();
        }
        this.writer.flush();
        this.writer.close();
    }

    void displayPropertySet(String ps, OutputStream os) throws IOException, BiffException {
        if (ps.equalsIgnoreCase("SummaryInformation")) {
            ps = "\u0005SummaryInformation";
        } else if (ps.equalsIgnoreCase("DocumentSummaryInformation")) {
            ps = "\u0005DocumentSummaryInformation";
        } else if (ps.equalsIgnoreCase("CompObj")) {
            ps = "\u0001CompObj";
        }
        byte[] stream = this.compoundFile.getStream(ps);
        os.write(stream);
    }
}

