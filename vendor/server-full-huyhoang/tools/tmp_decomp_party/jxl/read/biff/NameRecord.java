/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Assert;
import common.Logger;
import java.util.ArrayList;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.biff.StringHelper;
import jxl.read.biff.Record;

public class NameRecord
extends RecordData {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$NameRecord == null ? (class$jxl$read$biff$NameRecord = NameRecord.class$("jxl.read.biff.NameRecord")) : class$jxl$read$biff$NameRecord);
    private String name;
    private int index;
    private int sheetRef = 0;
    private boolean isbiff8;
    public static Biff7 biff7 = new Biff7();
    private static final int commandMacro = 12;
    private static final int builtIn = 32;
    private static final int cellReference = 58;
    private static final int areaReference = 59;
    private static final int subExpression = 41;
    private static final int union = 16;
    private ArrayList ranges;
    private static final String[] builtInNames = new String[]{"Consolidate_Area", "Auto_Open", "Auto_Close", "Extract", "Database", "Criteria", "Print_Area", "Print_Titles", "Recorder", "Data_Form", "Auto_Activate", "Auto_Deactivate", "Sheet_Title", "_FilterDatabase"};
    static /* synthetic */ Class class$jxl$read$biff$NameRecord;

    NameRecord(Record t, WorkbookSettings ws, int ind) {
        super(t);
        this.index = ind;
        this.isbiff8 = true;
        try {
            this.ranges = new ArrayList();
            byte[] data = this.getRecord().getData();
            int option = IntegerHelper.getInt(data[0], data[1]);
            byte length = data[3];
            this.sheetRef = IntegerHelper.getInt(data[8], data[9]);
            if ((option & 0x20) != 0) {
                this.name = data[15] < 13 ? builtInNames[data[15]] : "Builtin_" + Integer.toString(data[15], 16);
                return;
            }
            this.name = StringHelper.getString(data, length, 15, ws);
            if ((option & 0xC) != 0) {
                return;
            }
            int pos = length + 15;
            if (data[pos] == 58) {
                int sheet = IntegerHelper.getInt(data[pos + 1], data[pos + 2]);
                int row = IntegerHelper.getInt(data[pos + 3], data[pos + 4]);
                int columnMask = IntegerHelper.getInt(data[pos + 5], data[pos + 6]);
                int column = columnMask & 0xFF;
                Assert.verify((columnMask & 0xC0000) == 0);
                NameRange r = new NameRange(sheet, column, row, column, row);
                this.ranges.add(r);
            } else if (data[pos] == 59) {
                int sheet1 = 0;
                int r1 = 0;
                int columnMask = 0;
                int c1 = 0;
                int r2 = 0;
                int c2 = 0;
                NameRange range = null;
                while (pos < data.length) {
                    sheet1 = IntegerHelper.getInt(data[pos + 1], data[pos + 2]);
                    r1 = IntegerHelper.getInt(data[pos + 3], data[pos + 4]);
                    r2 = IntegerHelper.getInt(data[pos + 5], data[pos + 6]);
                    columnMask = IntegerHelper.getInt(data[pos + 7], data[pos + 8]);
                    c1 = columnMask & 0xFF;
                    Assert.verify((columnMask & 0xC0000) == 0);
                    columnMask = IntegerHelper.getInt(data[pos + 9], data[pos + 10]);
                    c2 = columnMask & 0xFF;
                    Assert.verify((columnMask & 0xC0000) == 0);
                    range = new NameRange(sheet1, c1, r1, c2, r2);
                    this.ranges.add(range);
                    pos += 11;
                }
            } else if (data[pos] == 41) {
                int sheet1 = 0;
                int r1 = 0;
                int columnMask = 0;
                int c1 = 0;
                int r2 = 0;
                int c2 = 0;
                NameRange range = null;
                if (pos < data.length && data[pos] != 58 && data[pos] != 59) {
                    if (data[pos] == 41) {
                        pos += 3;
                    } else if (data[pos] == 16) {
                        ++pos;
                    }
                }
                while (pos < data.length) {
                    sheet1 = IntegerHelper.getInt(data[pos + 1], data[pos + 2]);
                    r1 = IntegerHelper.getInt(data[pos + 3], data[pos + 4]);
                    r2 = IntegerHelper.getInt(data[pos + 5], data[pos + 6]);
                    columnMask = IntegerHelper.getInt(data[pos + 7], data[pos + 8]);
                    c1 = columnMask & 0xFF;
                    Assert.verify((columnMask & 0xC0000) == 0);
                    columnMask = IntegerHelper.getInt(data[pos + 9], data[pos + 10]);
                    c2 = columnMask & 0xFF;
                    Assert.verify((columnMask & 0xC0000) == 0);
                    range = new NameRange(sheet1, c1, r1, c2, r2);
                    this.ranges.add(range);
                    if ((pos += 11) >= data.length || data[pos] == 58 || data[pos] == 59) continue;
                    if (data[pos] == 41) {
                        pos += 3;
                        continue;
                    }
                    if (data[pos] != 16) continue;
                    ++pos;
                }
            }
        }
        catch (Throwable t1) {
            logger.warn("Cannot read name");
            this.name = "ERROR";
        }
    }

    NameRecord(Record t, WorkbookSettings ws, int ind, Biff7 dummy) {
        super(t);
        this.index = ind;
        this.isbiff8 = false;
        try {
            int pos;
            this.ranges = new ArrayList();
            byte[] data = this.getRecord().getData();
            byte length = data[3];
            this.sheetRef = IntegerHelper.getInt(data[8], data[9]);
            this.name = StringHelper.getString(data, length, 14, ws);
            if (pos >= data.length) {
                return;
            }
            if (data[pos] == 58) {
                int sheet = IntegerHelper.getInt(data[pos + 11], data[pos + 12]);
                int row = IntegerHelper.getInt(data[pos + 15], data[pos + 16]);
                byte column = data[pos + 17];
                NameRange r = new NameRange(sheet, column, row, column, row);
                this.ranges.add(r);
            } else if (data[pos] == 59) {
                int sheet1 = 0;
                int r1 = 0;
                byte c1 = 0;
                int r2 = 0;
                byte c2 = 0;
                NameRange range = null;
                for (pos = length + 14; pos < data.length; pos += 21) {
                    sheet1 = IntegerHelper.getInt(data[pos + 11], data[pos + 12]);
                    r1 = IntegerHelper.getInt(data[pos + 15], data[pos + 16]);
                    r2 = IntegerHelper.getInt(data[pos + 17], data[pos + 18]);
                    c1 = data[pos + 19];
                    c2 = data[pos + 20];
                    range = new NameRange(sheet1, c1, r1, c2, r2);
                    this.ranges.add(range);
                }
            } else if (data[pos] == 41) {
                int sheet1 = 0;
                boolean sheet2 = false;
                int r1 = 0;
                byte c1 = 0;
                int r2 = 0;
                byte c2 = 0;
                NameRange range = null;
                if (pos < data.length && data[pos] != 58 && data[pos] != 59) {
                    if (data[pos] == 41) {
                        pos += 3;
                    } else if (data[pos] == 16) {
                        ++pos;
                    }
                }
                while (pos < data.length) {
                    sheet1 = IntegerHelper.getInt(data[pos + 11], data[pos + 12]);
                    r1 = IntegerHelper.getInt(data[pos + 15], data[pos + 16]);
                    r2 = IntegerHelper.getInt(data[pos + 17], data[pos + 18]);
                    c1 = data[pos + 19];
                    c2 = data[pos + 20];
                    range = new NameRange(sheet1, c1, r1, c2, r2);
                    this.ranges.add(range);
                    if ((pos += 21) >= data.length || data[pos] == 58 || data[pos] == 59) continue;
                    if (data[pos] == 41) {
                        pos += 3;
                        continue;
                    }
                    if (data[pos] != 16) continue;
                    ++pos;
                }
            }
        }
        catch (Throwable t1) {
            logger.warn("Cannot read name.");
            this.name = "ERROR";
        }
    }

    public String getName() {
        return this.name;
    }

    public NameRange[] getRanges() {
        NameRange[] nr = new NameRange[this.ranges.size()];
        return this.ranges.toArray(nr);
    }

    int getIndex() {
        return this.index;
    }

    public int getSheetRef() {
        return this.sheetRef;
    }

    public void setSheetRef(int i) {
        this.sheetRef = i;
    }

    public byte[] getData() {
        return this.getRecord().getData();
    }

    public boolean isBiff8() {
        return this.isbiff8;
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    public class NameRange {
        private int columnFirst;
        private int rowFirst;
        private int columnLast;
        private int rowLast;
        private int externalSheet;

        NameRange(int s1, int c1, int r1, int c2, int r2) {
            this.columnFirst = c1;
            this.rowFirst = r1;
            this.columnLast = c2;
            this.rowLast = r2;
            this.externalSheet = s1;
        }

        public int getFirstColumn() {
            return this.columnFirst;
        }

        public int getFirstRow() {
            return this.rowFirst;
        }

        public int getLastColumn() {
            return this.columnLast;
        }

        public int getLastRow() {
            return this.rowLast;
        }

        public int getExternalSheet() {
            return this.externalSheet;
        }
    }

    private static class Biff7 {
        private Biff7() {
        }
    }
}

