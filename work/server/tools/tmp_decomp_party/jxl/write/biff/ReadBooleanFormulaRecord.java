/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.BooleanFormulaCell;
import jxl.biff.FormulaData;
import jxl.write.biff.ReadFormulaRecord;

class ReadBooleanFormulaRecord
extends ReadFormulaRecord
implements BooleanFormulaCell {
    public ReadBooleanFormulaRecord(FormulaData f) {
        super(f);
    }

    public boolean getValue() {
        return ((BooleanFormulaCell)((Object)this.getReadFormula())).getValue();
    }
}

