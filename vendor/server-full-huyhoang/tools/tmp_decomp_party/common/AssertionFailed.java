/*
 * Decompiled with CFR 0.152.
 */
package common;

public class AssertionFailed
extends RuntimeException {
    public AssertionFailed() {
        this.printStackTrace();
    }

    public AssertionFailed(String s) {
        super(s);
    }
}

