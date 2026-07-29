/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Assert;
import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.read.biff.Record;

public class ObjRecord
extends WritableRecordData {
    private static final Logger logger = Logger.getLogger(class$jxl$biff$drawing$ObjRecord == null ? (class$jxl$biff$drawing$ObjRecord = ObjRecord.class$("jxl.biff.drawing.ObjRecord")) : class$jxl$biff$drawing$ObjRecord);
    private ObjType type;
    private boolean read;
    private int objectId;
    public static final ObjType TBD2 = new ObjType(1, "TBD2");
    public static final ObjType TBD = new ObjType(2, "TBD");
    public static final ObjType CHART = new ObjType(5, "Chart");
    public static final ObjType TEXT = new ObjType(6, "Text");
    public static final ObjType BUTTON = new ObjType(7, "Button");
    public static final ObjType PICTURE = new ObjType(8, "Picture");
    public static final ObjType CHECKBOX = new ObjType(14, "Checkbox");
    public static final ObjType OPTION = new ObjType(12, "Option");
    public static final ObjType EDITBOX = new ObjType(13, "Edit Box");
    public static final ObjType LABEL = new ObjType(14, "Label");
    public static final ObjType DIALOGUEBOX = new ObjType(15, "Dialogue Box");
    public static final ObjType LISTBOX = new ObjType(18, "List Box");
    public static final ObjType GROUPBOX = new ObjType(19, "Group Box");
    public static final ObjType COMBOBOX = new ObjType(20, "Combo Box");
    public static final ObjType MSOFFICEDRAWING = new ObjType(30, "MS Office Drawing");
    public static final ObjType FORMCONTROL = new ObjType(20, "Form Combo Box");
    public static final ObjType EXCELNOTE = new ObjType(25, "Excel Note");
    public static final ObjType UNKNOWN = new ObjType(255, "Unknown");
    private static final int COMMON_DATA_LENGTH = 22;
    private static final int CLIPBOARD_FORMAT_LENGTH = 6;
    private static final int PICTURE_OPTION_LENGTH = 6;
    private static final int NOTE_STRUCTURE_LENGTH = 26;
    private static final int COMBOBOX_STRUCTURE_LENGTH = 44;
    private static final int END_LENGTH = 4;
    static /* synthetic */ Class class$jxl$biff$drawing$ObjRecord;

    public ObjRecord(Record t) {
        super(t);
        byte[] data = t.getData();
        int objtype = IntegerHelper.getInt(data[4], data[5]);
        this.read = true;
        this.type = ObjType.getType(objtype);
        if (this.type == UNKNOWN) {
            logger.warn("unknown object type code " + objtype);
        }
        this.objectId = IntegerHelper.getInt(data[6], data[7]);
    }

    ObjRecord(int objId, ObjType t) {
        super(Type.OBJ);
        this.objectId = objId;
        this.type = t;
    }

    public byte[] getData() {
        if (this.read) {
            return this.getRecord().getData();
        }
        if (this.type == PICTURE || this.type == CHART) {
            return this.getPictureData();
        }
        if (this.type == EXCELNOTE) {
            return this.getNoteData();
        }
        if (this.type == COMBOBOX) {
            return this.getComboBoxData();
        }
        Assert.verify(false);
        return null;
    }

    private byte[] getPictureData() {
        int dataLength = 38;
        int pos = 0;
        byte[] data = new byte[dataLength];
        IntegerHelper.getTwoBytes(21, data, pos);
        IntegerHelper.getTwoBytes(18, data, pos + 2);
        IntegerHelper.getTwoBytes(this.type.value, data, pos + 4);
        IntegerHelper.getTwoBytes(this.objectId, data, pos + 6);
        IntegerHelper.getTwoBytes(24593, data, pos + 8);
        IntegerHelper.getTwoBytes(7, data, pos += 22);
        IntegerHelper.getTwoBytes(2, data, pos + 2);
        IntegerHelper.getTwoBytes(65535, data, pos + 4);
        IntegerHelper.getTwoBytes(8, data, pos += 6);
        IntegerHelper.getTwoBytes(2, data, pos + 2);
        IntegerHelper.getTwoBytes(1, data, pos + 4);
        IntegerHelper.getTwoBytes(0, data, pos += 6);
        IntegerHelper.getTwoBytes(0, data, pos + 2);
        pos += 4;
        return data;
    }

    private byte[] getNoteData() {
        int dataLength = 52;
        int pos = 0;
        byte[] data = new byte[dataLength];
        IntegerHelper.getTwoBytes(21, data, pos);
        IntegerHelper.getTwoBytes(18, data, pos + 2);
        IntegerHelper.getTwoBytes(this.type.value, data, pos + 4);
        IntegerHelper.getTwoBytes(this.objectId, data, pos + 6);
        IntegerHelper.getTwoBytes(16401, data, pos + 8);
        IntegerHelper.getTwoBytes(13, data, pos += 22);
        IntegerHelper.getTwoBytes(22, data, pos + 2);
        IntegerHelper.getTwoBytes(0, data, pos += 26);
        IntegerHelper.getTwoBytes(0, data, pos + 2);
        pos += 4;
        return data;
    }

    private byte[] getComboBoxData() {
        int dataLength = 70;
        int pos = 0;
        byte[] data = new byte[dataLength];
        IntegerHelper.getTwoBytes(21, data, pos);
        IntegerHelper.getTwoBytes(18, data, pos + 2);
        IntegerHelper.getTwoBytes(this.type.value, data, pos + 4);
        IntegerHelper.getTwoBytes(this.objectId, data, pos + 6);
        IntegerHelper.getTwoBytes(0, data, pos + 8);
        IntegerHelper.getTwoBytes(12, data, pos += 22);
        IntegerHelper.getTwoBytes(20, data, pos + 2);
        data[pos + 14] = 1;
        data[pos + 16] = 4;
        data[pos + 20] = 16;
        data[pos + 24] = 19;
        data[pos + 26] = -18;
        data[pos + 27] = 31;
        data[pos + 30] = 4;
        data[pos + 34] = 1;
        data[pos + 35] = 6;
        data[pos + 38] = 2;
        data[pos + 40] = 8;
        data[pos + 42] = 64;
        IntegerHelper.getTwoBytes(0, data, pos += 44);
        IntegerHelper.getTwoBytes(0, data, pos + 2);
        pos += 4;
        return data;
    }

    public Record getRecord() {
        return super.getRecord();
    }

    public ObjType getType() {
        return this.type;
    }

    public int getObjectId() {
        return this.objectId;
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    private static final class ObjType {
        public int value;
        public String desc;
        private static ObjType[] types = new ObjType[0];

        ObjType(int v, String d) {
            this.value = v;
            this.desc = d;
            ObjType[] oldtypes = types;
            types = new ObjType[types.length + 1];
            System.arraycopy(oldtypes, 0, types, 0, oldtypes.length);
            ObjType.types[oldtypes.length] = this;
        }

        public String toString() {
            return this.desc;
        }

        public static ObjType getType(int val) {
            ObjType retval = UNKNOWN;
            for (int i = 0; i < types.length && retval == UNKNOWN; ++i) {
                if (ObjType.types[i].value != val) continue;
                retval = types[i];
            }
            return retval;
        }
    }
}

