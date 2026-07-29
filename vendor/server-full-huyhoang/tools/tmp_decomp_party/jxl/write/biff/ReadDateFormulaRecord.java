/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import java.text.DateFormat;
import java.util.Date;
import jxl.DateFormulaCell;
import jxl.biff.FormulaData;
import jxl.write.biff.ReadFormulaRecord;

class ReadDateFormulaRecord
extends ReadFormulaRecord
implements DateFormulaCell {
    public ReadDateFormulaRecord(FormulaData f) {
        super(f);
    }

    public Date getDate() {
        return ((DateFormulaCell)((Object)this.getReadFormula())).getDate();
    }

    public boolean isTime() {
        return ((DateFormulaCell)((Object)this.getReadFormula())).isTime();
    }

    public DateFormat getDateFormat() {
        return ((DateFormulaCell)((Object)this.getReadFormula())).getDateFormat();
    }
}

