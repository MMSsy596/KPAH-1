/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc.exceptions;

import com.mysql.jdbc.exceptions.MySQLNonTransientException;

public class MySQLIntegrityConstraintViolationException
extends MySQLNonTransientException {
    public MySQLIntegrityConstraintViolationException() {
    }

    public MySQLIntegrityConstraintViolationException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    public MySQLIntegrityConstraintViolationException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    public MySQLIntegrityConstraintViolationException(String reason) {
        super(reason);
    }
}

