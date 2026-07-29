/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.Messages;
import java.sql.SQLException;

public class NotImplemented
extends SQLException {
    public NotImplemented() {
        super(Messages.getString("NotImplemented.0"), "S1C00");
    }
}

