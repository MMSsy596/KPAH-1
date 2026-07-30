/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Logger;
import java.util.ArrayList;
import java.util.Iterator;
import jxl.biff.IntegerHelper;
import jxl.biff.StringHelper;
import jxl.biff.drawing.EscherAtom;
import jxl.biff.drawing.EscherRecordData;
import jxl.biff.drawing.EscherRecordType;

class Opt
extends EscherAtom {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$Opt == null ? (class$jxl$biff$drawing$Opt = Opt.class$("jxl.biff.drawing.Opt")) : class$jxl$biff$drawing$Opt);
    private byte[] data;
    private int numProperties;
    private ArrayList properties;
    static /* synthetic */ Class class$jxl$biff$drawing$Opt;

    public Opt(EscherRecordData erd) {
        super(erd);
        this.numProperties = this.getInstance();
        this.readProperties();
    }

    private void readProperties() {
        this.properties = new ArrayList();
        int pos = 0;
        byte[] bytes = this.getBytes();
        for (int i = 0; i < this.numProperties; ++i) {
            int val = IntegerHelper.getInt(bytes[pos], bytes[pos + 1]);
            int id = val & 0x3FFF;
            int value = IntegerHelper.getInt(bytes[pos + 2], bytes[pos + 3], bytes[pos + 4], bytes[pos + 5]);
            Property p = new Property(id, (val & 0x4000) != 0, (val & 0x8000) != 0, value);
            pos += 6;
            this.properties.add(p);
        }
        Iterator i = this.properties.iterator();
        while (i.hasNext()) {
            Property p = (Property)i.next();
            if (!p.complex) continue;
            p.stringValue = StringHelper.getUnicodeString(bytes, p.value / 2, pos);
            pos += p.value;
        }
    }

    public Opt() {
        super(EscherRecordType.OPT);
        this.properties = new ArrayList();
        this.setVersion(3);
    }

    byte[] getData() {
        Property p;
        this.numProperties = this.properties.size();
        this.setInstance(this.numProperties);
        this.data = new byte[this.numProperties * 6];
        int pos = 0;
        Iterator i = this.properties.iterator();
        while (i.hasNext()) {
            p = (Property)i.next();
            int val = p.id & 0x3FFF;
            if (p.blipId) {
                val |= 0x4000;
            }
            if (p.complex) {
                val |= 0x8000;
            }
            IntegerHelper.getTwoBytes(val, this.data, pos);
            IntegerHelper.getFourBytes(p.value, this.data, pos + 2);
            pos += 6;
        }
        i = this.properties.iterator();
        while (i.hasNext()) {
            p = (Property)i.next();
            if (!p.complex || p.stringValue == null) continue;
            byte[] newData = new byte[this.data.length + p.stringValue.length() * 2];
            System.arraycopy(this.data, 0, newData, 0, this.data.length);
            StringHelper.getUnicodeBytes(p.stringValue, newData, this.data.length);
            this.data = newData;
        }
        return this.setHeaderData(this.data);
    }

    void addProperty(int id, boolean blip, boolean complex, int val) {
        Property p = new Property(id, blip, complex, val);
        this.properties.add(p);
    }

    void addProperty(int id, boolean blip, boolean complex, int val, String s) {
        Property p = new Property(id, blip, complex, val, s);
        this.properties.add(p);
    }

    Property getProperty(int id) {
        boolean found = false;
        Property p = null;
        Iterator i = this.properties.iterator();
        while (i.hasNext() && !found) {
            p = (Property)i.next();
            if (p.id != id) continue;
            found = true;
        }
        return found ? p : null;
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    static final class Property {
        int id;
        boolean blipId;
        boolean complex;
        int value;
        String stringValue;

        public Property(int i, boolean bl, boolean co, int v) {
            this.id = i;
            this.blipId = bl;
            this.complex = co;
            this.value = v;
        }

        public Property(int i, boolean bl, boolean co, int v, String s) {
            this.id = i;
            this.blipId = bl;
            this.complex = co;
            this.value = v;
            this.stringValue = s;
        }
    }
}

