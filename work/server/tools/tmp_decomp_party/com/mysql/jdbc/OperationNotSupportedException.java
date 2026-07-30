/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.Messages;
import java.sql.SQLException;

class OperationNotSupportedException
extends SQLException {
    OperationNotSupportedException() {
        super(Messages.getString("RowDataDynamic.10"), "S1009");
    }
}

