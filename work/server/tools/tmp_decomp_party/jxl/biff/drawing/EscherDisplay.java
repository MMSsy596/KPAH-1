/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import java.io.BufferedWriter;
import java.io.IOException;
import jxl.biff.drawing.EscherContainer;
import jxl.biff.drawing.EscherRecord;
import jxl.biff.drawing.EscherRecordData;
import jxl.biff.drawing.EscherRecordType;
import jxl.biff.drawing.EscherStream;

public class EscherDisplay {
    private EscherStream stream;
    private BufferedWriter writer;

    public EscherDisplay(EscherStream s, BufferedWriter bw) {
        this.stream = s;
        this.writer = bw;
    }

    public void display() throws IOException {
        EscherRecordData er = new EscherRecordData(this.stream, 0);
        EscherContainer ec = new EscherContainer(er);
        this.displayContainer(ec, 0);
    }

    private void displayContainer(EscherContainer ec, int level) throws IOException {
        this.displayRecord(ec, level);
        ++level;
        EscherRecord[] children = ec.getChildren();
        for (int i = 0; i < children.length; ++i) {
            EscherRecord er = children[i];
            if (er.getEscherData().isContainer()) {
                this.displayContainer((EscherContainer)er, level);
                continue;
            }
            this.displayRecord(er, level);
        }
    }

    private void displayRecord(EscherRecord er, int level) throws IOException {
        this.indent(level);
        EscherRecordType type = er.getType();
        this.writer.write(Integer.toString(type.getValue(), 16));
        this.writer.write(" - ");
        if (type == EscherRecordType.DGG_CONTAINER) {
            this.writer.write("Dgg Container");
            this.writer.newLine();
        } else if (type == EscherRecordType.BSTORE_CONTAINER) {
            this.writer.write("BStore Container");
            this.writer.newLine();
        } else if (type == EscherRecordType.DG_CONTAINER) {
            this.writer.write("Dg Container");
            this.writer.newLine();
        } else if (type == EscherRecordType.SPGR_CONTAINER) {
            this.writer.write("Spgr Container");
            this.writer.newLine();
        } else if (type == EscherRecordType.SP_CONTAINER) {
            this.writer.write("Sp Container");
            this.writer.newLine();
        } else if (type == EscherRecordType.DGG) {
            this.writer.write("Dgg");
            this.writer.newLine();
        } else if (type == EscherRecordType.BSE) {
            this.writer.write("Bse");
            this.writer.newLine();
        } else if (type == EscherRecordType.DG) {
            this.writer.write("Dg");
            this.writer.newLine();
        } else if (type == EscherRecordType.SPGR) {
            this.writer.write("Spgr");
            this.writer.newLine();
        } else if (type == EscherRecordType.SP) {
            this.writer.write("Sp");
            this.writer.newLine();
        } else if (type == EscherRecordType.OPT) {
            this.writer.write("Opt");
            this.writer.newLine();
        } else if (type == EscherRecordType.CLIENT_ANCHOR) {
            this.writer.write("Client Anchor");
            this.writer.newLine();
        } else if (type == EscherRecordType.CLIENT_DATA) {
            this.writer.write("Client Data");
            this.writer.newLine();
        } else if (type == EscherRecordType.CLIENT_TEXT_BOX) {
            this.writer.write("Client Text Box");
            this.writer.newLine();
        } else if (type == EscherRecordType.SPLIT_MENU_COLORS) {
            this.writer.write("Split Menu Colors");
            this.writer.newLine();
        } else {
            this.writer.write("???");
            this.writer.newLine();
        }
    }

    private void indent(int level) throws IOException {
        for (int i = 0; i < level * 2; ++i) {
            this.writer.write(32);
        }
    }
}

