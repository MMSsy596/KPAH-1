/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc.exceptions;

import com.mysql.jdbc.exceptions.MySQLTransientException;

public class MySQLTransientConnectionException
extends MySQLTransientException {
    public MySQLTransientConnectionException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    public MySQLTransientConnectionException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    public MySQLTransientConnectionException(String reason) {
        super(reason);
    }

    public MySQLTransientConnectionException() {
    }
}

