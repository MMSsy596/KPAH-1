/*
 * Decompiled with CFR 0.152.
 */
package jxl;

import java.io.File;

public interface Image {
    public double getColumn();

    public double getRow();

    public double getWidth();

    public double getHeight();

    public File getImageFile();

    public byte[] getImageData();
}

