/*
 * Decompiled with CFR 0.152.
 */
package jxl;

import java.io.File;
import java.net.URL;
import jxl.Range;

public interface Hyperlink {
    public int getRow();

    public int getColumn();

    public Range getRange();

    public boolean isFile();

    public boolean isURL();

    public boolean isLocation();

    public int getLastRow();

    public int getLastColumn();

    public URL getURL();

    public File getFile();
}

