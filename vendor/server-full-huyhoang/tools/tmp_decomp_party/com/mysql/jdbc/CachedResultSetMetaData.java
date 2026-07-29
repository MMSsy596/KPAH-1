/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.Field;
import java.sql.ResultSetMetaData;
import java.util.Map;

class CachedResultSetMetaData {
    Map columnNameToIndex = null;
    Field[] fields;
    Map fullColumnNameToIndex = null;
    ResultSetMetaData metadata;

    CachedResultSetMetaData() {
    }
}

