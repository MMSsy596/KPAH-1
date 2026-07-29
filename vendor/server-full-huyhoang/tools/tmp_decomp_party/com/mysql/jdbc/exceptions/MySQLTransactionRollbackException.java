/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc.exceptions;

import com.mysql.jdbc.exceptions.MySQLTransientException;

public class MySQLTransactionRollbackException
extends MySQLTransientException {
    public MySQLTransactionRollbackException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    public MySQLTransactionRollbackException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    public MySQLTransactionRollbackException(String reason) {
        super(reason);
    }

    public MySQLTransactionRollbackException() {
    }
}

