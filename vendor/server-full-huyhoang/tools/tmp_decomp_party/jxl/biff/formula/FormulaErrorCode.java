/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

public class FormulaErrorCode {
    private int errorCode;
    private String description;
    private static FormulaErrorCode[] codes = new FormulaErrorCode[0];
    public static final FormulaErrorCode UNKNOWN = new FormulaErrorCode(255, "?");
    public static final FormulaErrorCode NULL = new FormulaErrorCode(0, "#NULL");
    public static final FormulaErrorCode DIV0 = new FormulaErrorCode(7, "#DIV/0");
    public static final FormulaErrorCode VALUE = new FormulaErrorCode(15, "#VALUE");
    public static final FormulaErrorCode REF = new FormulaErrorCode(23, "#REF");
    public static final FormulaErrorCode NAME = new FormulaErrorCode(29, "#NAME");
    public static final FormulaErrorCode NUM = new FormulaErrorCode(36, "#NUM");
    public static final FormulaErrorCode NA = new FormulaErrorCode(42, "#N/A");

    FormulaErrorCode(int code, String desc) {
        this.errorCode = code;
        this.description = desc;
        FormulaErrorCode[] newcodes = new FormulaErrorCode[codes.length + 1];
        System.arraycopy(codes, 0, newcodes, 0, codes.length);
        newcodes[FormulaErrorCode.codes.length] = this;
        codes = newcodes;
    }

    public int getCode() {
        return this.errorCode;
    }

    public String getDescription() {
        return this.description;
    }

    public static FormulaErrorCode getErrorCode(int code) {
        boolean found = false;
        FormulaErrorCode ec = UNKNOWN;
        for (int i = 0; i < codes.length && !found; ++i) {
            if (FormulaErrorCode.codes[i].errorCode != code) continue;
            found = true;
            ec = codes[i];
        }
        return ec;
    }
}

