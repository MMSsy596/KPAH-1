/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.AssertionFailedException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Field;
import com.mysql.jdbc.MysqlDefs;
import com.mysql.jdbc.MysqlIO;
import com.mysql.jdbc.NonRegisteringDriver;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.RowDataStatic;
import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.StringUtils;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class DatabaseMetaData
implements java.sql.DatabaseMetaData {
    private static String mysqlKeywordsThatArentSQL92;
    private static final int DEFERRABILITY = 13;
    private static final int DELETE_RULE = 10;
    private static final int FK_NAME = 11;
    private static final int FKCOLUMN_NAME = 7;
    private static final int FKTABLE_CAT = 4;
    private static final int FKTABLE_NAME = 6;
    private static final int FKTABLE_SCHEM = 5;
    private static final int KEY_SEQ = 8;
    private static final int PK_NAME = 12;
    private static final int PKCOLUMN_NAME = 3;
    private static final int PKTABLE_CAT = 0;
    private static final int PKTABLE_NAME = 2;
    private static final int PKTABLE_SCHEM = 1;
    private static final String SUPPORTS_FK = "SUPPORTS_FK";
    private static final byte[] TABLE_AS_BYTES;
    private static final int UPDATE_RULE = 9;
    private static final byte[] VIEW_AS_BYTES;
    protected Connection conn;
    protected String database = null;
    protected String quotedId = null;

    static java.sql.ResultSet buildResultSet(Field[] fields, ArrayList rows, Connection c) throws SQLException {
        int fieldsLength = fields.length;
        for (int i = 0; i < fieldsLength; ++i) {
            fields[i].setConnection(c);
            fields[i].setUseOldNameMetadata(true);
        }
        return new ResultSet(c.getCatalog(), fields, new RowDataStatic(rows), c, null);
    }

    public DatabaseMetaData(Connection connToSet, String databaseToSet) {
        this.conn = connToSet;
        this.database = databaseToSet;
        try {
            this.quotedId = this.conn.supportsQuotedIdentifiers() ? this.getIdentifierQuoteString() : "";
        }
        catch (SQLException sqlEx) {
            AssertionFailedException.shouldNotHappen(sqlEx);
        }
    }

    public boolean allProceduresAreCallable() throws SQLException {
        return false;
    }

    public boolean allTablesAreSelectable() throws SQLException {
        return false;
    }

    private java.sql.ResultSet buildResultSet(Field[] fields, ArrayList rows) throws SQLException {
        return DatabaseMetaData.buildResultSet(fields, rows, this.conn);
    }

    private void convertToJdbcFunctionList(String catalog, java.sql.ResultSet proceduresRs, boolean needsClientFiltering, String db, Map procedureRowsOrderedByName, int nameIndex) throws SQLException {
        while (proceduresRs.next()) {
            boolean shouldAdd = true;
            if (needsClientFiltering) {
                shouldAdd = false;
                String procDb = proceduresRs.getString(1);
                if (db == null && procDb == null) {
                    shouldAdd = true;
                } else if (db != null && db.equals(procDb)) {
                    shouldAdd = true;
                }
            }
            if (!shouldAdd) continue;
            String functionName = proceduresRs.getString(nameIndex);
            byte[][] rowData = new byte[][]{catalog == null ? null : this.s2b(catalog), null, this.s2b(functionName), null, null, null, null, this.s2b(Integer.toString(2))};
            procedureRowsOrderedByName.put(functionName, rowData);
        }
    }

    private void convertToJdbcProcedureList(boolean fromSelect, String catalog, java.sql.ResultSet proceduresRs, boolean needsClientFiltering, String db, Map procedureRowsOrderedByName, int nameIndex) throws SQLException {
        while (proceduresRs.next()) {
            boolean shouldAdd = true;
            if (needsClientFiltering) {
                shouldAdd = false;
                String procDb = proceduresRs.getString(1);
                if (db == null && procDb == null) {
                    shouldAdd = true;
                } else if (db != null && db.equals(procDb)) {
                    shouldAdd = true;
                }
            }
            if (!shouldAdd) continue;
            String procedureName = proceduresRs.getString(nameIndex);
            byte[][] rowData = new byte[8][];
            rowData[0] = catalog == null ? null : this.s2b(catalog);
            rowData[1] = null;
            rowData[2] = this.s2b(procedureName);
            rowData[3] = null;
            rowData[4] = null;
            rowData[5] = null;
            rowData[6] = null;
            boolean isFunction = fromSelect ? "FUNCTION".equalsIgnoreCase(proceduresRs.getString("type")) : false;
            rowData[7] = this.s2b(isFunction ? Integer.toString(2) : Integer.toString(0));
            procedureRowsOrderedByName.put(procedureName, rowData);
        }
    }

    private byte[][] convertTypeDescriptorToProcedureRow(byte[] procNameAsBytes, String paramName, boolean isOutParam, boolean isInParam, boolean isReturnParam, TypeDescriptor typeDesc) throws SQLException {
        byte[][] row = new byte[14][];
        row[0] = null;
        row[1] = null;
        row[2] = procNameAsBytes;
        row[3] = this.s2b(paramName);
        row[4] = isInParam && isOutParam ? this.s2b(String.valueOf(2)) : (isInParam ? this.s2b(String.valueOf(1)) : (isOutParam ? this.s2b(String.valueOf(4)) : (isReturnParam ? this.s2b(String.valueOf(5)) : this.s2b(String.valueOf(0)))));
        row[5] = this.s2b(Short.toString(typeDesc.dataType));
        row[6] = this.s2b(typeDesc.typeName);
        row[7] = typeDesc.columnSize == null ? null : this.s2b(typeDesc.columnSize.toString());
        row[8] = this.s2b(Integer.toString(typeDesc.bufferLength));
        row[9] = typeDesc.decimalDigits == null ? null : this.s2b(typeDesc.decimalDigits.toString());
        row[10] = this.s2b(Integer.toString(typeDesc.numPrecRadix));
        switch (typeDesc.nullability) {
            case 0: {
                row[11] = this.s2b(Integer.toString(0));
                break;
            }
            case 1: {
                row[11] = this.s2b(Integer.toString(1));
                break;
            }
            case 2: {
                row[11] = this.s2b(Integer.toString(2));
                break;
            }
            default: {
                throw SQLError.createSQLException("Internal error while parsing callable statement metadata (unknown nullability value fount)", "S1000");
            }
        }
        row[12] = null;
        return row;
    }

    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
        return true;
    }

    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
        return false;
    }

    public boolean deletesAreDetected(int type) throws SQLException {
        return false;
    }

    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        return true;
    }

    private int endPositionOfParameterDeclaration(int beginIndex, String procedureDef, String quoteChar) throws SQLException {
        int currentPos = beginIndex + 1;
        int parenDepth = 1;
        while (parenDepth > 0 && currentPos < procedureDef.length()) {
            int closedParenIndex = StringUtils.indexOfIgnoreCaseRespectQuotes(currentPos, procedureDef, ")", quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet());
            if (closedParenIndex != -1) {
                int nextOpenParenIndex = StringUtils.indexOfIgnoreCaseRespectQuotes(currentPos, procedureDef, "(", quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet());
                if (nextOpenParenIndex != -1 && nextOpenParenIndex < closedParenIndex) {
                    ++parenDepth;
                    currentPos = closedParenIndex + 1;
                    continue;
                }
                --parenDepth;
                currentPos = closedParenIndex;
                continue;
            }
            throw SQLError.createSQLException("Internal error when parsing callable statement metadata", "S1000");
        }
        return currentPos;
    }

    public List extractForeignKeyForTable(ArrayList rows, java.sql.ResultSet rs, String catalog) throws SQLException {
        byte[][] row = new byte[3][];
        row[0] = rs.getBytes(1);
        row[1] = this.s2b(SUPPORTS_FK);
        String createTableString = rs.getString(2);
        StringTokenizer lineTokenizer = new StringTokenizer(createTableString, "\n");
        StringBuffer commentBuf = new StringBuffer("comment; ");
        boolean firstTime = true;
        String quoteChar = this.getIdentifierQuoteString();
        if (quoteChar == null) {
            quoteChar = "`";
        }
        while (lineTokenizer.hasMoreTokens()) {
            int afterFk;
            int indexOfRef;
            String line = lineTokenizer.nextToken().trim();
            String constraintName = null;
            if (StringUtils.startsWithIgnoreCase(line, "CONSTRAINT")) {
                boolean usingBackTicks = true;
                int beginPos = line.indexOf(quoteChar);
                if (beginPos == -1) {
                    beginPos = line.indexOf("\"");
                    usingBackTicks = false;
                }
                if (beginPos != -1) {
                    int endPos = -1;
                    endPos = usingBackTicks ? line.indexOf(quoteChar, beginPos + 1) : line.indexOf("\"", beginPos + 1);
                    if (endPos != -1) {
                        constraintName = line.substring(beginPos + 1, endPos);
                        line = line.substring(endPos + 1, line.length()).trim();
                    }
                }
            }
            if (!line.startsWith("FOREIGN KEY")) continue;
            if (line.endsWith(",")) {
                line = line.substring(0, line.length() - 1);
            }
            char quote = this.quotedId.charAt(0);
            int indexOfFK = line.indexOf("FOREIGN KEY");
            String localColumnName = null;
            String referencedCatalogName = this.quotedId + catalog + this.quotedId;
            String referencedTableName = null;
            String referencedColumnName = null;
            if (indexOfFK != -1 && (indexOfRef = StringUtils.indexOfIgnoreCaseRespectQuotes(afterFk = indexOfFK + "FOREIGN KEY".length(), line, "REFERENCES", quote, true)) != -1) {
                int indexOfParenOpen = line.indexOf(40, afterFk);
                int indexOfParenClose = StringUtils.indexOfIgnoreCaseRespectQuotes(indexOfParenOpen, line, ")", quote, true);
                if (indexOfParenOpen == -1 || indexOfParenClose == -1) {
                    // empty if block
                }
                localColumnName = line.substring(indexOfParenOpen + 1, indexOfParenClose);
                int afterRef = indexOfRef + "REFERENCES".length();
                int referencedColumnBegin = StringUtils.indexOfIgnoreCaseRespectQuotes(afterRef, line, "(", quote, true);
                if (referencedColumnBegin != -1) {
                    int indexOfCatalogSep;
                    referencedTableName = line.substring(afterRef, referencedColumnBegin);
                    int referencedColumnEnd = StringUtils.indexOfIgnoreCaseRespectQuotes(referencedColumnBegin + 1, line, ")", quote, true);
                    if (referencedColumnEnd != -1) {
                        referencedColumnName = line.substring(referencedColumnBegin + 1, referencedColumnEnd);
                    }
                    if ((indexOfCatalogSep = StringUtils.indexOfIgnoreCaseRespectQuotes(0, referencedTableName, ".", quote, true)) != -1) {
                        referencedCatalogName = referencedTableName.substring(0, indexOfCatalogSep);
                        referencedTableName = referencedTableName.substring(indexOfCatalogSep + 1);
                    }
                }
            }
            if (!firstTime) {
                commentBuf.append("; ");
            } else {
                firstTime = false;
            }
            if (constraintName != null) {
                commentBuf.append(constraintName);
            } else {
                commentBuf.append("not_available");
            }
            commentBuf.append("(");
            commentBuf.append(localColumnName);
            commentBuf.append(") REFER ");
            commentBuf.append(referencedCatalogName);
            commentBuf.append("/");
            commentBuf.append(referencedTableName);
            commentBuf.append("(");
            commentBuf.append(referencedColumnName);
            commentBuf.append(")");
            int lastParenIndex = line.lastIndexOf(")");
            if (lastParenIndex == line.length() - 1) continue;
            String cascadeOptions = line.substring(lastParenIndex + 1);
            commentBuf.append(" ");
            commentBuf.append(cascadeOptions);
        }
        row[2] = this.s2b(commentBuf.toString());
        rows.add(row);
        return rows;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public java.sql.ResultSet extractForeignKeyFromCreateTable(String catalog, String tableName) throws SQLException {
        Field[] fields;
        ArrayList rows;
        Statement stmt;
        java.sql.ResultSet rs;
        block17: {
            ArrayList<String> tableList = new ArrayList<String>();
            rs = null;
            stmt = null;
            if (tableName != null) {
                tableList.add(tableName);
            } else {
                block16: {
                    try {
                        rs = this.getTables(catalog, "", "%", new String[]{"TABLE"});
                        while (rs.next()) {
                            tableList.add(rs.getString("TABLE_NAME"));
                        }
                        Object var7_6 = null;
                        if (rs == null) break block16;
                    }
                    catch (Throwable throwable) {
                        Object var7_7 = null;
                        if (rs != null) {
                            rs.close();
                        }
                        rs = null;
                        throw throwable;
                    }
                    rs.close();
                }
                rs = null;
                {
                }
            }
            rows = new ArrayList();
            fields = new Field[]{new Field("", "Name", 1, Integer.MAX_VALUE), new Field("", "Type", 1, 255), new Field("", "Comment", 1, Integer.MAX_VALUE)};
            int numTables = tableList.size();
            stmt = this.conn.getMetadataSafeStatement();
            String quoteChar = this.getIdentifierQuoteString();
            if (quoteChar == null) {
                quoteChar = "`";
            }
            try {
                for (int i = 0; i < numTables; ++i) {
                    String tableToExtract = (String)tableList.get(i);
                    String query = "SHOW CREATE TABLE " + quoteChar + catalog + quoteChar + "." + quoteChar + tableToExtract + quoteChar;
                    try {
                        rs = stmt.executeQuery(query);
                    }
                    catch (SQLException sqlEx) {
                        String sqlState = sqlEx.getSQLState();
                        if ("42S02".equals(sqlState) || sqlEx.getErrorCode() == 1146) continue;
                        throw sqlEx;
                    }
                    while (rs.next()) {
                        this.extractForeignKeyForTable(rows, rs, catalog);
                    }
                }
                Object var16_17 = null;
                if (rs == null) break block17;
            }
            catch (Throwable throwable) {
                Object var16_18 = null;
                if (rs != null) {
                    rs.close();
                }
                rs = null;
                if (stmt != null) {
                    stmt.close();
                }
                stmt = null;
                throw throwable;
            }
            rs.close();
        }
        rs = null;
        if (stmt != null) {
            stmt.close();
        }
        stmt = null;
        return this.buildResultSet(fields, rows);
    }

    private int findEndOfReturnsClause(String procedureDefn, String quoteChar, int positionOfReturnKeyword) throws SQLException {
        String[] tokens = new String[]{"LANGUAGE", "NOT", "DETERMINISTIC", "CONTAINS", "NO", "READ", "MODIFIES", "SQL", "COMMENT", "BEGIN", "RETURN"};
        int startLookingAt = positionOfReturnKeyword + "RETURNS".length() + 1;
        for (int i = 0; i < tokens.length; ++i) {
            int endOfReturn = StringUtils.indexOfIgnoreCaseRespectQuotes(startLookingAt, procedureDefn, tokens[i], quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet());
            if (endOfReturn == -1) continue;
            return endOfReturn;
        }
        int endOfReturn = StringUtils.indexOfIgnoreCaseRespectQuotes(startLookingAt, procedureDefn, ":", quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet());
        if (endOfReturn != -1) {
            for (int i = endOfReturn; i > 0; --i) {
                if (!Character.isWhitespace(procedureDefn.charAt(i))) continue;
                return i;
            }
        }
        throw SQLError.createSQLException("Internal error when parsing callable statement metadata", "S1000");
    }

    public java.sql.ResultSet getAttributes(String arg0, String arg1, String arg2, String arg3) throws SQLException {
        Field[] fields = new Field[]{new Field("", "TYPE_CAT", 1, 32), new Field("", "TYPE_SCHEM", 1, 32), new Field("", "TYPE_NAME", 1, 32), new Field("", "ATTR_NAME", 1, 32), new Field("", "DATA_TYPE", 5, 32), new Field("", "ATTR_TYPE_NAME", 1, 32), new Field("", "ATTR_SIZE", 4, 32), new Field("", "DECIMAL_DIGITS", 4, 32), new Field("", "NUM_PREC_RADIX", 4, 32), new Field("", "NULLABLE ", 4, 32), new Field("", "REMARKS", 1, 32), new Field("", "ATTR_DEF", 1, 32), new Field("", "SQL_DATA_TYPE", 4, 32), new Field("", "SQL_DATETIME_SUB", 4, 32), new Field("", "CHAR_OCTET_LENGTH", 4, 32), new Field("", "ORDINAL_POSITION", 4, 32), new Field("", "IS_NULLABLE", 1, 32), new Field("", "SCOPE_CATALOG", 1, 32), new Field("", "SCOPE_SCHEMA", 1, 32), new Field("", "SCOPE_TABLE", 1, 32), new Field("", "SOURCE_DATA_TYPE", 5, 32)};
        return this.buildResultSet(fields, new ArrayList());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public java.sql.ResultSet getBestRowIdentifier(String catalog, String schema, final String table, int scope, boolean nullable) throws SQLException {
        if (table == null) {
            throw SQLError.createSQLException("Table not specified.", "S1009");
        }
        Field[] fields = new Field[]{new Field("", "SCOPE", 5, 5), new Field("", "COLUMN_NAME", 1, 32), new Field("", "DATA_TYPE", 5, 32), new Field("", "TYPE_NAME", 1, 32), new Field("", "COLUMN_SIZE", 4, 10), new Field("", "BUFFER_LENGTH", 4, 10), new Field("", "DECIMAL_DIGITS", 4, 10), new Field("", "PSEUDO_COLUMN", 5, 5)};
        final ArrayList rows = new ArrayList();
        final Statement stmt = this.conn.getMetadataSafeStatement();
        try {
            new IterateBlock(this.getCatalogIterator(catalog)){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                void forEach(Object catalogStr) throws SQLException {
                    block13: {
                        java.sql.ResultSet results = null;
                        try {
                            StringBuffer queryBuf = new StringBuffer("SHOW COLUMNS FROM ");
                            queryBuf.append(DatabaseMetaData.this.quotedId);
                            queryBuf.append(table);
                            queryBuf.append(DatabaseMetaData.this.quotedId);
                            queryBuf.append(" FROM ");
                            queryBuf.append(DatabaseMetaData.this.quotedId);
                            queryBuf.append(catalogStr.toString());
                            queryBuf.append(DatabaseMetaData.this.quotedId);
                            results = stmt.executeQuery(queryBuf.toString());
                            while (results.next()) {
                                String keyType = results.getString("Key");
                                if (keyType == null || !StringUtils.startsWithIgnoreCase(keyType, "PRI")) continue;
                                byte[][] rowVal = new byte[8][];
                                rowVal[0] = Integer.toString(2).getBytes();
                                rowVal[1] = results.getBytes("Field");
                                String type = results.getString("Type");
                                int size = MysqlIO.getMaxBuf();
                                int decimals = 0;
                                if (type.indexOf("enum") != -1) {
                                    String temp = type.substring(type.indexOf("("), type.indexOf(")"));
                                    StringTokenizer tokenizer = new StringTokenizer(temp, ",");
                                    int maxLength = 0;
                                    while (tokenizer.hasMoreTokens()) {
                                        maxLength = Math.max(maxLength, tokenizer.nextToken().length() - 2);
                                    }
                                    size = maxLength;
                                    decimals = 0;
                                    type = "enum";
                                } else if (type.indexOf("(") != -1) {
                                    if (type.indexOf(",") != -1) {
                                        size = Integer.parseInt(type.substring(type.indexOf("(") + 1, type.indexOf(",")));
                                        decimals = Integer.parseInt(type.substring(type.indexOf(",") + 1, type.indexOf(")")));
                                    } else {
                                        size = Integer.parseInt(type.substring(type.indexOf("(") + 1, type.indexOf(")")));
                                    }
                                    type = type.substring(0, type.indexOf("("));
                                }
                                rowVal[2] = DatabaseMetaData.this.s2b(String.valueOf(MysqlDefs.mysqlToJavaType(type)));
                                rowVal[3] = DatabaseMetaData.this.s2b(type);
                                rowVal[4] = Integer.toString(size + decimals).getBytes();
                                rowVal[5] = Integer.toString(size + decimals).getBytes();
                                rowVal[6] = Integer.toString(decimals).getBytes();
                                rowVal[7] = Integer.toString(1).getBytes();
                                rows.add(rowVal);
                            }
                            Object var13_12 = null;
                            if (results == null) break block13;
                        }
                        catch (Throwable throwable) {
                            Object var13_13 = null;
                            if (results != null) {
                                try {
                                    results.close();
                                }
                                catch (Exception ex) {
                                    // empty catch block
                                }
                                results = null;
                            }
                            throw throwable;
                        }
                        try {
                            results.close();
                        }
                        catch (Exception ex) {
                            // empty catch block
                        }
                        results = null;
                        {
                        }
                    }
                }
            }.doForAll();
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        java.sql.ResultSet results = this.buildResultSet(fields, rows);
        return results;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void getCallStmtParameterTypes(String catalog, String procName, String parameterNamePattern, List resultRows) throws SQLException {
        String declaration;
        SQLException sqlEx42;
        SQLException sqlExRethrow2;
        String storageDefnClosures;
        String storageDefnDelims;
        boolean isProcedureInAnsiMode;
        String parameterDef;
        byte[] procNameAsBytes;
        Statement paramRetrievalStmt;
        block47: {
            paramRetrievalStmt = null;
            java.sql.ResultSet paramRetrievalRs = null;
            if (parameterNamePattern == null) {
                if (!this.conn.getNullNamePatternMatchesAll()) throw SQLError.createSQLException("Parameter/Column name pattern can not be NULL or empty.", "S1009");
                parameterNamePattern = "%";
            }
            procNameAsBytes = null;
            try {
                procNameAsBytes = procName.getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException ueEx) {
                procNameAsBytes = this.s2b(procName);
            }
            String quoteChar = this.getIdentifierQuoteString();
            parameterDef = null;
            isProcedureInAnsiMode = false;
            storageDefnDelims = null;
            storageDefnClosures = null;
            try {
                boolean procNameIsNotQuoted;
                paramRetrievalStmt = this.conn.getMetadataSafeStatement();
                if (this.conn.lowerCaseTableNames() && catalog != null && catalog.length() != 0) {
                    String oldCatalog = this.conn.getCatalog();
                    java.sql.ResultSet rs = null;
                    try {
                        this.conn.setCatalog(catalog);
                        rs = paramRetrievalStmt.executeQuery("SELECT DATABASE()");
                        rs.next();
                        catalog = rs.getString(1);
                        Object var16_19 = null;
                    }
                    catch (Throwable throwable) {
                        Object var16_20 = null;
                        this.conn.setCatalog(oldCatalog);
                        if (rs == null) throw throwable;
                        rs.close();
                        throw throwable;
                    }
                    this.conn.setCatalog(oldCatalog);
                    if (rs != null) {
                        rs.close();
                    }
                }
                if (paramRetrievalStmt.getMaxRows() != 0) {
                    paramRetrievalStmt.setMaxRows(0);
                }
                int dotIndex = -1;
                dotIndex = !" ".equals(quoteChar) ? StringUtils.indexOfIgnoreCaseRespectQuotes(0, procName, ".", quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet()) : procName.indexOf(".");
                String dbName = null;
                if (dotIndex != -1 && dotIndex + 1 < procName.length()) {
                    dbName = procName.substring(0, dotIndex);
                    procName = procName.substring(dotIndex + 1);
                } else {
                    dbName = catalog;
                }
                StringBuffer procNameBuf = new StringBuffer();
                if (dbName != null) {
                    if (!" ".equals(quoteChar) && !dbName.startsWith(quoteChar)) {
                        procNameBuf.append(quoteChar);
                    }
                    procNameBuf.append(dbName);
                    if (!" ".equals(quoteChar) && !dbName.startsWith(quoteChar)) {
                        procNameBuf.append(quoteChar);
                    }
                    procNameBuf.append(".");
                }
                boolean bl = procNameIsNotQuoted = !procName.startsWith(quoteChar);
                if (!" ".equals(quoteChar) && procNameIsNotQuoted) {
                    procNameBuf.append(quoteChar);
                }
                procNameBuf.append(procName);
                if (!" ".equals(quoteChar) && procNameIsNotQuoted) {
                    procNameBuf.append(quoteChar);
                }
                boolean parsingFunction = false;
                try {
                    paramRetrievalRs = paramRetrievalStmt.executeQuery("SHOW CREATE PROCEDURE " + procNameBuf.toString());
                    parsingFunction = false;
                }
                catch (SQLException sqlEx2) {
                    paramRetrievalRs = paramRetrievalStmt.executeQuery("SHOW CREATE FUNCTION " + procNameBuf.toString());
                    parsingFunction = true;
                }
                if (paramRetrievalRs.next()) {
                    String procedureDef;
                    String string = procedureDef = parsingFunction ? paramRetrievalRs.getString("Create Function") : paramRetrievalRs.getString("Create Procedure");
                    if (procedureDef == null || procedureDef.length() == 0) {
                        throw SQLError.createSQLException("User does not have access to metadata required to determine stored procedure parameter types. If rights can not be granted, configure connection with \"noAccessToProcedureBodies=true\" to have driver generate parameters that represent INOUT strings irregardless of actual parameter types.", "S1000");
                    }
                    try {
                        String sqlMode = paramRetrievalRs.getString("sql_mode");
                        if (StringUtils.indexOfIgnoreCase(sqlMode, "ANSI") != -1) {
                            isProcedureInAnsiMode = true;
                        }
                    }
                    catch (SQLException sqlEx3) {
                        // empty catch block
                    }
                    String identifierMarkers = isProcedureInAnsiMode ? "`\"" : "`";
                    String identifierAndStringMarkers = "'" + identifierMarkers;
                    storageDefnDelims = "(" + identifierMarkers;
                    storageDefnClosures = ")" + identifierMarkers;
                    procedureDef = StringUtils.stripComments(procedureDef, identifierAndStringMarkers, identifierAndStringMarkers, true, false, true, true);
                    int openParenIndex = StringUtils.indexOfIgnoreCaseRespectQuotes(0, procedureDef, "(", quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet());
                    int endOfParamDeclarationIndex = 0;
                    endOfParamDeclarationIndex = this.endPositionOfParameterDeclaration(openParenIndex, procedureDef, quoteChar);
                    if (parsingFunction) {
                        int declarationStart;
                        int returnsIndex = StringUtils.indexOfIgnoreCaseRespectQuotes(0, procedureDef, " RETURNS ", quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet());
                        int endReturnsDef = this.findEndOfReturnsClause(procedureDef, quoteChar, returnsIndex);
                        for (declarationStart = returnsIndex + "RETURNS ".length(); declarationStart < procedureDef.length() && Character.isWhitespace(procedureDef.charAt(declarationStart)); ++declarationStart) {
                        }
                        String returnsDefn = procedureDef.substring(declarationStart, endReturnsDef).trim();
                        TypeDescriptor returnDescriptor = new TypeDescriptor(returnsDefn, null);
                        resultRows.add(this.convertTypeDescriptorToProcedureRow(procNameAsBytes, "", false, false, true, returnDescriptor));
                    }
                    if (openParenIndex == -1 || endOfParamDeclarationIndex == -1) {
                        throw SQLError.createSQLException("Internal error when parsing callable statement metadata", "S1000");
                    }
                    parameterDef = procedureDef.substring(openParenIndex + 1, endOfParamDeclarationIndex);
                }
                Object var29_46 = null;
                sqlExRethrow2 = null;
                if (paramRetrievalRs == null) break block47;
            }
            catch (Throwable throwable) {
                SQLException sqlEx42;
                Object var29_47 = null;
                SQLException sqlExRethrow2 = null;
                if (paramRetrievalRs != null) {
                    try {
                        paramRetrievalRs.close();
                    }
                    catch (SQLException sqlEx42) {
                        sqlExRethrow2 = sqlEx42;
                    }
                    paramRetrievalRs = null;
                }
                if (paramRetrievalStmt != null) {
                    try {
                        paramRetrievalStmt.close();
                    }
                    catch (SQLException sqlEx42) {
                        sqlExRethrow2 = sqlEx42;
                    }
                    paramRetrievalStmt = null;
                }
                if (sqlExRethrow2 == null) throw throwable;
                throw sqlExRethrow2;
            }
            try {
                paramRetrievalRs.close();
            }
            catch (SQLException sqlEx42) {
                sqlExRethrow2 = sqlEx42;
            }
            paramRetrievalRs = null;
        }
        if (paramRetrievalStmt != null) {
            try {
                paramRetrievalStmt.close();
            }
            catch (SQLException sqlEx42) {
                sqlExRethrow2 = sqlEx42;
            }
            paramRetrievalStmt = null;
        }
        if (sqlExRethrow2 != null) {
            throw sqlExRethrow2;
        }
        if (parameterDef == null) return;
        List parseList = StringUtils.split(parameterDef, ",", storageDefnDelims, storageDefnClosures, true);
        int parseListLen = parseList.size();
        for (int i = 0; i < parseListLen && (declaration = (String)parseList.get(i)).trim().length() != 0; ++i) {
            int wildCompareRes;
            StringTokenizer declarationTok = new StringTokenizer(declaration, " \t");
            String paramName = null;
            boolean isOutParam = false;
            boolean isInParam = false;
            if (!declarationTok.hasMoreTokens()) throw SQLError.createSQLException("Internal error when parsing callable statement metadata (unknown output from 'SHOW CREATE PROCEDURE')", "S1000");
            String possibleParamName = declarationTok.nextToken();
            if (possibleParamName.equalsIgnoreCase("OUT")) {
                isOutParam = true;
                if (!declarationTok.hasMoreTokens()) throw SQLError.createSQLException("Internal error when parsing callable statement metadata (missing parameter name)", "S1000");
                paramName = declarationTok.nextToken();
            } else if (possibleParamName.equalsIgnoreCase("INOUT")) {
                isOutParam = true;
                isInParam = true;
                if (!declarationTok.hasMoreTokens()) throw SQLError.createSQLException("Internal error when parsing callable statement metadata (missing parameter name)", "S1000");
                paramName = declarationTok.nextToken();
            } else if (possibleParamName.equalsIgnoreCase("IN")) {
                isOutParam = false;
                isInParam = true;
                if (!declarationTok.hasMoreTokens()) throw SQLError.createSQLException("Internal error when parsing callable statement metadata (missing parameter name)", "S1000");
                paramName = declarationTok.nextToken();
            } else {
                isOutParam = false;
                isInParam = true;
                paramName = possibleParamName;
            }
            TypeDescriptor typeDesc = null;
            if (!declarationTok.hasMoreTokens()) throw SQLError.createSQLException("Internal error when parsing callable statement metadata (missing parameter type)", "S1000");
            StringBuffer typeInfoBuf = new StringBuffer(declarationTok.nextToken());
            while (declarationTok.hasMoreTokens()) {
                typeInfoBuf.append(" ");
                typeInfoBuf.append(declarationTok.nextToken());
            }
            String typeInfo = typeInfoBuf.toString();
            typeDesc = new TypeDescriptor(typeInfo, null);
            if (paramName.startsWith("`") && paramName.endsWith("`") || isProcedureInAnsiMode && paramName.startsWith("\"") && paramName.endsWith("\"")) {
                paramName = paramName.substring(1, paramName.length() - 1);
            }
            if ((wildCompareRes = StringUtils.wildCompare(paramName, parameterNamePattern)) == -1) continue;
            byte[][] row = this.convertTypeDescriptorToProcedureRow(procNameAsBytes, paramName, isOutParam, isInParam, false, typeDesc);
            resultRows.add(row);
        }
    }

    private int getCascadeDeleteOption(String cascadeOptions) {
        int onDeletePos = cascadeOptions.indexOf("ON DELETE");
        if (onDeletePos != -1) {
            String deleteOptions = cascadeOptions.substring(onDeletePos, cascadeOptions.length());
            if (deleteOptions.startsWith("ON DELETE CASCADE")) {
                return 0;
            }
            if (deleteOptions.startsWith("ON DELETE SET NULL")) {
                return 2;
            }
            if (deleteOptions.startsWith("ON DELETE RESTRICT")) {
                return 1;
            }
            if (deleteOptions.startsWith("ON DELETE NO ACTION")) {
                return 3;
            }
        }
        return 3;
    }

    private int getCascadeUpdateOption(String cascadeOptions) {
        int onUpdatePos = cascadeOptions.indexOf("ON UPDATE");
        if (onUpdatePos != -1) {
            String updateOptions = cascadeOptions.substring(onUpdatePos, cascadeOptions.length());
            if (updateOptions.startsWith("ON UPDATE CASCADE")) {
                return 0;
            }
            if (updateOptions.startsWith("ON UPDATE SET NULL")) {
                return 2;
            }
            if (updateOptions.startsWith("ON UPDATE RESTRICT")) {
                return 1;
            }
            if (updateOptions.startsWith("ON UPDATE NO ACTION")) {
                return 3;
            }
        }
        return 3;
    }

    protected IteratorWithCleanup getCatalogIterator(String catalogSpec) throws SQLException {
        IteratorWithCleanup allCatalogsIter = catalogSpec != null ? (!catalogSpec.equals("") ? new SingleStringIterator(catalogSpec) : new SingleStringIterator(this.database)) : (this.conn.getNullCatalogMeansCurrent() ? new SingleStringIterator(this.database) : new ResultSetIterator(this.getCatalogs(), 1));
        return allCatalogsIter;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public java.sql.ResultSet getCatalogs() throws SQLException {
        SQLException sqlEx22;
        java.sql.ResultSet resultSet;
        Statement stmt;
        block11: {
            java.sql.ResultSet results = null;
            stmt = null;
            try {
                stmt = this.conn.createStatement();
                stmt.setEscapeProcessing(false);
                results = stmt.executeQuery("SHOW DATABASES");
                ResultSetMetaData resultsMD = results.getMetaData();
                Field[] fields = new Field[]{new Field("", "TABLE_CAT", 12, resultsMD.getColumnDisplaySize(1))};
                ArrayList<byte[][]> tuples = new ArrayList<byte[][]>();
                while (results.next()) {
                    byte[][] rowVal = new byte[][]{results.getBytes(1)};
                    tuples.add(rowVal);
                }
                resultSet = this.buildResultSet(fields, tuples);
                Object var8_7 = null;
                if (results == null) break block11;
            }
            catch (Throwable throwable) {
                block14: {
                    SQLException sqlEx22;
                    Object var8_8 = null;
                    if (results != null) {
                        try {
                            results.close();
                        }
                        catch (SQLException sqlEx22) {
                            AssertionFailedException.shouldNotHappen(sqlEx22);
                        }
                        results = null;
                    }
                    if (stmt == null) break block14;
                    try {
                        stmt.close();
                    }
                    catch (SQLException sqlEx22) {
                        AssertionFailedException.shouldNotHappen(sqlEx22);
                    }
                    stmt = null;
                }
                throw throwable;
            }
            try {
                results.close();
            }
            catch (SQLException sqlEx22) {
                AssertionFailedException.shouldNotHappen(sqlEx22);
            }
            results = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
            }
            catch (SQLException sqlEx22) {
                AssertionFailedException.shouldNotHappen(sqlEx22);
            }
            stmt = null;
        }
        return resultSet;
    }

    public String getCatalogSeparator() throws SQLException {
        return ".";
    }

    public String getCatalogTerm() throws SQLException {
        return "database";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public java.sql.ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) throws SQLException {
        ArrayList<byte[][]> grantRows;
        Field[] fields;
        block18: {
            Exception ex22;
            Statement stmt;
            block15: {
                fields = new Field[]{new Field("", "TABLE_CAT", 1, 64), new Field("", "TABLE_SCHEM", 1, 1), new Field("", "TABLE_NAME", 1, 64), new Field("", "COLUMN_NAME", 1, 64), new Field("", "GRANTOR", 1, 77), new Field("", "GRANTEE", 1, 77), new Field("", "PRIVILEGE", 1, 64), new Field("", "IS_GRANTABLE", 1, 3)};
                StringBuffer grantQuery = new StringBuffer("SELECT c.host, c.db, t.grantor, c.user, c.table_name, c.column_name, c.column_priv from mysql.columns_priv c, mysql.tables_priv t where c.host = t.host and c.db = t.db and c.table_name = t.table_name ");
                if (catalog != null && catalog.length() != 0) {
                    grantQuery.append(" AND c.db='");
                    grantQuery.append(catalog);
                    grantQuery.append("' ");
                }
                grantQuery.append(" AND c.table_name ='");
                grantQuery.append(table);
                grantQuery.append("' AND c.column_name like '");
                grantQuery.append(columnNamePattern);
                grantQuery.append("'");
                stmt = null;
                java.sql.ResultSet results = null;
                grantRows = new ArrayList<byte[][]>();
                try {
                    stmt = this.conn.createStatement();
                    stmt.setEscapeProcessing(false);
                    results = stmt.executeQuery(grantQuery.toString());
                    while (results.next()) {
                        String host = results.getString(1);
                        String db = results.getString(2);
                        String grantor = results.getString(3);
                        String user = results.getString(4);
                        if (user == null || user.length() == 0) {
                            user = "%";
                        }
                        StringBuffer fullUser = new StringBuffer(user);
                        if (host != null && this.conn.getUseHostsInPrivileges()) {
                            fullUser.append("@");
                            fullUser.append(host);
                        }
                        String columnName = results.getString(6);
                        String allPrivileges = results.getString(7);
                        if (allPrivileges == null) continue;
                        allPrivileges = allPrivileges.toUpperCase(Locale.ENGLISH);
                        StringTokenizer st = new StringTokenizer(allPrivileges, ",");
                        while (st.hasMoreTokens()) {
                            String privilege = st.nextToken().trim();
                            byte[][] tuple = new byte[][]{this.s2b(db), null, this.s2b(table), this.s2b(columnName), (byte[])(grantor != null ? this.s2b(grantor) : null), this.s2b(fullUser.toString()), this.s2b(privilege), null};
                            grantRows.add(tuple);
                        }
                    }
                    Object var21_20 = null;
                    if (results == null) break block15;
                }
                catch (Throwable throwable) {
                    Exception ex22;
                    Object var21_21 = null;
                    if (results != null) {
                        try {
                            results.close();
                        }
                        catch (Exception ex22) {
                            // empty catch block
                        }
                        results = null;
                    }
                    if (stmt != null) {
                        try {
                            stmt.close();
                        }
                        catch (Exception ex22) {
                            // empty catch block
                        }
                        stmt = null;
                    }
                    throw throwable;
                }
                try {
                    results.close();
                }
                catch (Exception ex22) {
                    // empty catch block
                }
                results = null;
            }
            if (stmt == null) break block18;
            try {
                stmt.close();
            }
            catch (Exception ex22) {
                // empty catch block
            }
            stmt = null;
            {
            }
        }
        return this.buildResultSet(fields, grantRows);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public java.sql.ResultSet getColumns(final String catalog, final String schemaPattern, final String tableNamePattern, String columnNamePattern) throws SQLException {
        if (columnNamePattern == null) {
            if (this.conn.getNullNamePatternMatchesAll()) {
                columnNamePattern = "%";
            } else {
                throw SQLError.createSQLException("Column name pattern can not be NULL or empty.", "S1009");
            }
        }
        final String colPattern = columnNamePattern;
        Field[] fields = new Field[]{new Field("", "TABLE_CAT", 1, 255), new Field("", "TABLE_SCHEM", 1, 0), new Field("", "TABLE_NAME", 1, 255), new Field("", "COLUMN_NAME", 1, 32), new Field("", "DATA_TYPE", 5, 5), new Field("", "TYPE_NAME", 1, 16), new Field("", "COLUMN_SIZE", 4, Integer.toString(Integer.MAX_VALUE).length()), new Field("", "BUFFER_LENGTH", 4, 10), new Field("", "DECIMAL_DIGITS", 4, 10), new Field("", "NUM_PREC_RADIX", 4, 10), new Field("", "NULLABLE", 4, 10), new Field("", "REMARKS", 1, 0), new Field("", "COLUMN_DEF", 1, 0), new Field("", "SQL_DATA_TYPE", 4, 10), new Field("", "SQL_DATETIME_SUB", 4, 10), new Field("", "CHAR_OCTET_LENGTH", 4, Integer.toString(Integer.MAX_VALUE).length()), new Field("", "ORDINAL_POSITION", 4, 10), new Field("", "IS_NULLABLE", 1, 3), new Field("", "SCOPE_CATALOG", 1, 255), new Field("", "SCOPE_SCHEMA", 1, 255), new Field("", "SCOPE_TABLE", 1, 255), new Field("", "SOURCE_DATA_TYPE", 5, 10), new Field("", "IS_AUTOINCREMENT", 1, 3)};
        final ArrayList rows = new ArrayList();
        final Statement stmt = this.conn.getMetadataSafeStatement();
        try {
            new IterateBlock(this.getCatalogIterator(catalog)){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                void forEach(Object catalogStr) throws SQLException {
                    ArrayList<String> tableNameList;
                    block34: {
                        String tableNameFromList;
                        java.sql.ResultSet tables;
                        tableNameList = new ArrayList<String>();
                        if (tableNamePattern == null) {
                            tables = null;
                            try {
                                tables = DatabaseMetaData.this.getTables(catalog, schemaPattern, "%", new String[0]);
                                while (tables.next()) {
                                    tableNameFromList = tables.getString("TABLE_NAME");
                                    tableNameList.add(tableNameFromList);
                                }
                                Object var6_5 = null;
                                if (tables == null) break block34;
                            }
                            catch (Throwable throwable) {
                                Object var6_6 = null;
                                if (tables != null) {
                                    try {
                                        tables.close();
                                    }
                                    catch (Exception sqlEx) {
                                        AssertionFailedException.shouldNotHappen(sqlEx);
                                    }
                                    tables = null;
                                }
                                throw throwable;
                            }
                            try {
                                tables.close();
                            }
                            catch (Exception sqlEx) {
                                AssertionFailedException.shouldNotHappen(sqlEx);
                            }
                            tables = null;
                            {
                                break block34;
                            }
                        }
                        tables = null;
                        try {
                            tables = DatabaseMetaData.this.getTables(catalog, schemaPattern, tableNamePattern, new String[0]);
                            while (tables.next()) {
                                tableNameFromList = tables.getString("TABLE_NAME");
                                tableNameList.add(tableNameFromList);
                            }
                            Object var9_12 = null;
                            if (tables == null) break block34;
                        }
                        catch (Throwable throwable) {
                            Object var9_13 = null;
                            if (tables != null) {
                                try {
                                    tables.close();
                                }
                                catch (SQLException sqlEx) {
                                    AssertionFailedException.shouldNotHappen(sqlEx);
                                }
                                tables = null;
                            }
                            throw throwable;
                        }
                        try {
                            tables.close();
                        }
                        catch (SQLException sqlEx) {
                            AssertionFailedException.shouldNotHappen(sqlEx);
                        }
                        tables = null;
                        {
                        }
                    }
                    Iterator tableNames = tableNameList.iterator();
                    while (tableNames.hasNext()) {
                        Exception ex2;
                        Object var15_25;
                        String tableName = (String)tableNames.next();
                        java.sql.ResultSet results = null;
                        try {
                            StringBuffer queryBuf = new StringBuffer("SHOW ");
                            if (DatabaseMetaData.this.conn.versionMeetsMinimum(4, 1, 0)) {
                                queryBuf.append("FULL ");
                            }
                            queryBuf.append("COLUMNS FROM ");
                            queryBuf.append(DatabaseMetaData.this.quotedId);
                            queryBuf.append(tableName);
                            queryBuf.append(DatabaseMetaData.this.quotedId);
                            queryBuf.append(" FROM ");
                            queryBuf.append(DatabaseMetaData.this.quotedId);
                            queryBuf.append(catalogStr.toString());
                            queryBuf.append(DatabaseMetaData.this.quotedId);
                            queryBuf.append(" LIKE '");
                            queryBuf.append(colPattern);
                            queryBuf.append("'");
                            boolean fixUpOrdinalsRequired = false;
                            HashMap<String, Integer> ordinalFixUpMap = null;
                            if (!colPattern.equals("%")) {
                                fixUpOrdinalsRequired = true;
                                StringBuffer fullColumnQueryBuf = new StringBuffer("SHOW ");
                                if (DatabaseMetaData.this.conn.versionMeetsMinimum(4, 1, 0)) {
                                    fullColumnQueryBuf.append("FULL ");
                                }
                                fullColumnQueryBuf.append("COLUMNS FROM ");
                                fullColumnQueryBuf.append(DatabaseMetaData.this.quotedId);
                                fullColumnQueryBuf.append(tableName);
                                fullColumnQueryBuf.append(DatabaseMetaData.this.quotedId);
                                fullColumnQueryBuf.append(" FROM ");
                                fullColumnQueryBuf.append(DatabaseMetaData.this.quotedId);
                                fullColumnQueryBuf.append(catalogStr.toString());
                                fullColumnQueryBuf.append(DatabaseMetaData.this.quotedId);
                                results = stmt.executeQuery(fullColumnQueryBuf.toString());
                                ordinalFixUpMap = new HashMap<String, Integer>();
                                int fullOrdinalPos = 1;
                                while (results.next()) {
                                    String fullOrdColName = results.getString("Field");
                                    ordinalFixUpMap.put(fullOrdColName, new Integer(fullOrdinalPos++));
                                }
                            }
                            results = stmt.executeQuery(queryBuf.toString());
                            int ordPos = 1;
                            while (results.next()) {
                                byte[][] rowVal = new byte[23][];
                                rowVal[0] = DatabaseMetaData.this.s2b(catalog);
                                rowVal[1] = null;
                                rowVal[2] = DatabaseMetaData.this.s2b(tableName);
                                rowVal[3] = results.getBytes("Field");
                                TypeDescriptor typeDesc = new TypeDescriptor(results.getString("Type"), results.getString("Null"));
                                rowVal[4] = Short.toString(typeDesc.dataType).getBytes();
                                rowVal[5] = DatabaseMetaData.this.s2b(typeDesc.typeName);
                                rowVal[6] = typeDesc.columnSize == null ? null : DatabaseMetaData.this.s2b(typeDesc.columnSize.toString());
                                rowVal[7] = DatabaseMetaData.this.s2b(Integer.toString(typeDesc.bufferLength));
                                rowVal[8] = typeDesc.decimalDigits == null ? null : DatabaseMetaData.this.s2b(typeDesc.decimalDigits.toString());
                                rowVal[9] = DatabaseMetaData.this.s2b(Integer.toString(typeDesc.numPrecRadix));
                                rowVal[10] = DatabaseMetaData.this.s2b(Integer.toString(typeDesc.nullability));
                                try {
                                    rowVal[11] = DatabaseMetaData.this.conn.versionMeetsMinimum(4, 1, 0) ? results.getBytes("Comment") : results.getBytes("Extra");
                                }
                                catch (Exception E) {
                                    rowVal[11] = new byte[0];
                                }
                                rowVal[12] = results.getBytes("Default");
                                rowVal[13] = new byte[]{48};
                                rowVal[14] = new byte[]{48};
                                rowVal[15] = (byte[])(StringUtils.indexOfIgnoreCase(typeDesc.typeName, "CHAR") != -1 || StringUtils.indexOfIgnoreCase(typeDesc.typeName, "BLOB") != -1 || StringUtils.indexOfIgnoreCase(typeDesc.typeName, "TEXT") != -1 || StringUtils.indexOfIgnoreCase(typeDesc.typeName, "BINARY") != -1 ? rowVal[6] : null);
                                if (!fixUpOrdinalsRequired) {
                                    rowVal[16] = Integer.toString(ordPos++).getBytes();
                                } else {
                                    String origColName = results.getString("Field");
                                    Integer realOrdinal = (Integer)ordinalFixUpMap.get(origColName);
                                    if (realOrdinal != null) {
                                        rowVal[16] = realOrdinal.toString().getBytes();
                                    } else {
                                        throw SQLError.createSQLException("Can not find column in full column list to determine true ordinal position.", "S1000");
                                    }
                                }
                                rowVal[17] = DatabaseMetaData.this.s2b(typeDesc.isNullable);
                                rowVal[18] = null;
                                rowVal[19] = null;
                                rowVal[20] = null;
                                rowVal[21] = null;
                                rowVal[22] = DatabaseMetaData.this.s2b("");
                                String extra = results.getString("Extra");
                                if (extra != null) {
                                    rowVal[22] = DatabaseMetaData.this.s2b(StringUtils.indexOfIgnoreCase(extra, "auto_increment") != -1 ? "YES" : "NO");
                                }
                                rows.add(rowVal);
                            }
                            var15_25 = null;
                            if (results == null) continue;
                        }
                        catch (Throwable throwable) {
                            var15_25 = null;
                            if (results != null) {
                                try {
                                    results.close();
                                }
                                catch (Exception ex2) {
                                    // empty catch block
                                }
                                results = null;
                            }
                            throw throwable;
                        }
                        try {
                            results.close();
                        }
                        catch (Exception ex2) {
                            // empty catch block
                        }
                        results = null;
                        {
                        }
                    }
                }
            }.doForAll();
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        java.sql.ResultSet results = this.buildResultSet(fields, rows);
        return results;
    }

    public java.sql.Connection getConnection() throws SQLException {
        return this.conn;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public java.sql.ResultSet getCrossReference(final String primaryCatalog, final String primarySchema, final String primaryTable, final String foreignCatalog, final String foreignSchema, final String foreignTable) throws SQLException {
        if (primaryTable == null) {
            throw SQLError.createSQLException("Table not specified.", "S1009");
        }
        Field[] fields = new Field[]{new Field("", "PKTABLE_CAT", 1, 255), new Field("", "PKTABLE_SCHEM", 1, 0), new Field("", "PKTABLE_NAME", 1, 255), new Field("", "PKCOLUMN_NAME", 1, 32), new Field("", "FKTABLE_CAT", 1, 255), new Field("", "FKTABLE_SCHEM", 1, 0), new Field("", "FKTABLE_NAME", 1, 255), new Field("", "FKCOLUMN_NAME", 1, 32), new Field("", "KEY_SEQ", 5, 2), new Field("", "UPDATE_RULE", 5, 2), new Field("", "DELETE_RULE", 5, 2), new Field("", "FK_NAME", 1, 0), new Field("", "PK_NAME", 1, 0), new Field("", "DEFERRABILITY", 4, 2)};
        final ArrayList tuples = new ArrayList();
        if (this.conn.versionMeetsMinimum(3, 23, 0)) {
            final Statement stmt = this.conn.getMetadataSafeStatement();
            try {
                new IterateBlock(this.getCatalogIterator(foreignCatalog)){

                    /*
                     * WARNING - Removed try catching itself - possible behaviour change.
                     */
                    void forEach(Object catalogStr) throws SQLException {
                        block12: {
                            java.sql.ResultSet fkresults = null;
                            try {
                                if (DatabaseMetaData.this.conn.versionMeetsMinimum(3, 23, 50)) {
                                    fkresults = DatabaseMetaData.this.extractForeignKeyFromCreateTable(catalogStr.toString(), null);
                                } else {
                                    StringBuffer queryBuf = new StringBuffer("SHOW TABLE STATUS FROM ");
                                    queryBuf.append(DatabaseMetaData.this.quotedId);
                                    queryBuf.append(catalogStr.toString());
                                    queryBuf.append(DatabaseMetaData.this.quotedId);
                                    fkresults = stmt.executeQuery(queryBuf.toString());
                                }
                                String foreignTableWithCase = DatabaseMetaData.this.getTableNameWithCase(foreignTable);
                                String primaryTableWithCase = DatabaseMetaData.this.getTableNameWithCase(primaryTable);
                                while (fkresults.next()) {
                                    String dummy;
                                    String comment;
                                    String tableType = fkresults.getString("Type");
                                    if (tableType == null || !tableType.equalsIgnoreCase("innodb") && !tableType.equalsIgnoreCase(DatabaseMetaData.SUPPORTS_FK) || (comment = fkresults.getString("Comment").trim()) == null) continue;
                                    StringTokenizer commentTokens = new StringTokenizer(comment, ";", false);
                                    if (commentTokens.hasMoreTokens()) {
                                        dummy = commentTokens.nextToken();
                                    }
                                    while (commentTokens.hasMoreTokens()) {
                                        String keys = commentTokens.nextToken();
                                        LocalAndReferencedColumns parsedInfo = DatabaseMetaData.this.parseTableStatusIntoLocalAndReferencedColumns(keys);
                                        int keySeq = 0;
                                        Iterator referencingColumns = parsedInfo.localColumnsList.iterator();
                                        Iterator referencedColumns = parsedInfo.referencedColumnsList.iterator();
                                        while (referencingColumns.hasNext()) {
                                            String referencingColumn = DatabaseMetaData.this.removeQuotedId(referencingColumns.next().toString());
                                            byte[][] tuple = new byte[14][];
                                            tuple[4] = foreignCatalog == null ? null : DatabaseMetaData.this.s2b(foreignCatalog);
                                            tuple[5] = foreignSchema == null ? null : DatabaseMetaData.this.s2b(foreignSchema);
                                            dummy = fkresults.getString("Name");
                                            if (dummy.compareTo(foreignTableWithCase) != 0) continue;
                                            tuple[6] = DatabaseMetaData.this.s2b(dummy);
                                            tuple[7] = DatabaseMetaData.this.s2b(referencingColumn);
                                            tuple[0] = primaryCatalog == null ? null : DatabaseMetaData.this.s2b(primaryCatalog);
                                            byte[] byArray = tuple[1] = primarySchema == null ? null : DatabaseMetaData.this.s2b(primarySchema);
                                            if (parsedInfo.referencedTable.compareTo(primaryTableWithCase) != 0) continue;
                                            tuple[2] = DatabaseMetaData.this.s2b(parsedInfo.referencedTable);
                                            tuple[3] = DatabaseMetaData.this.s2b(DatabaseMetaData.this.removeQuotedId(referencedColumns.next().toString()));
                                            tuple[8] = Integer.toString(keySeq).getBytes();
                                            int[] actions = DatabaseMetaData.this.getForeignKeyActions(keys);
                                            tuple[9] = Integer.toString(actions[1]).getBytes();
                                            tuple[10] = Integer.toString(actions[0]).getBytes();
                                            tuple[11] = null;
                                            tuple[12] = null;
                                            tuple[13] = Integer.toString(7).getBytes();
                                            tuples.add(tuple);
                                            ++keySeq;
                                        }
                                    }
                                }
                                Object var18_17 = null;
                                if (fkresults == null) break block12;
                            }
                            catch (Throwable throwable) {
                                Object var18_18 = null;
                                if (fkresults != null) {
                                    try {
                                        fkresults.close();
                                    }
                                    catch (Exception sqlEx) {
                                        AssertionFailedException.shouldNotHappen(sqlEx);
                                    }
                                    fkresults = null;
                                }
                                throw throwable;
                            }
                            try {
                                fkresults.close();
                            }
                            catch (Exception sqlEx) {
                                AssertionFailedException.shouldNotHappen(sqlEx);
                            }
                            fkresults = null;
                            {
                            }
                        }
                    }
                }.doForAll();
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        java.sql.ResultSet results = this.buildResultSet(fields, tuples);
        return results;
    }

    public int getDatabaseMajorVersion() throws SQLException {
        return this.conn.getServerMajorVersion();
    }

    public int getDatabaseMinorVersion() throws SQLException {
        return this.conn.getServerMinorVersion();
    }

    public String getDatabaseProductName() throws SQLException {
        return "MySQL";
    }

    public String getDatabaseProductVersion() throws SQLException {
        return this.conn.getServerVersion();
    }

    public int getDefaultTransactionIsolation() throws SQLException {
        if (this.conn.supportsIsolationLevel()) {
            return 2;
        }
        return 0;
    }

    public int getDriverMajorVersion() {
        return NonRegisteringDriver.getMajorVersionInternal();
    }

    public int getDriverMinorVersion() {
        return NonRegisteringDriver.getMinorVersionInternal();
    }

    public String getDriverName() throws SQLException {
        return "MySQL-AB JDBC Driver";
    }

    public String getDriverVersion() throws SQLException {
        return "mysql-connector-java-5.0.8 ( Revision: ${svn.Revision} )";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public java.sql.ResultSet getExportedKeys(String catalog, String schema, final String table) throws SQLException {
        if (table == null) {
            throw SQLError.createSQLException("Table not specified.", "S1009");
        }
        Field[] fields = new Field[]{new Field("", "PKTABLE_CAT", 1, 255), new Field("", "PKTABLE_SCHEM", 1, 0), new Field("", "PKTABLE_NAME", 1, 255), new Field("", "PKCOLUMN_NAME", 1, 32), new Field("", "FKTABLE_CAT", 1, 255), new Field("", "FKTABLE_SCHEM", 1, 0), new Field("", "FKTABLE_NAME", 1, 255), new Field("", "FKCOLUMN_NAME", 1, 32), new Field("", "KEY_SEQ", 5, 2), new Field("", "UPDATE_RULE", 5, 2), new Field("", "DELETE_RULE", 5, 2), new Field("", "FK_NAME", 1, 255), new Field("", "PK_NAME", 1, 0), new Field("", "DEFERRABILITY", 4, 2)};
        final ArrayList rows = new ArrayList();
        if (this.conn.versionMeetsMinimum(3, 23, 0)) {
            final Statement stmt = this.conn.getMetadataSafeStatement();
            try {
                new IterateBlock(this.getCatalogIterator(catalog)){

                    /*
                     * WARNING - Removed try catching itself - possible behaviour change.
                     */
                    void forEach(Object catalogStr) throws SQLException {
                        block10: {
                            java.sql.ResultSet fkresults = null;
                            try {
                                if (DatabaseMetaData.this.conn.versionMeetsMinimum(3, 23, 50)) {
                                    fkresults = DatabaseMetaData.this.extractForeignKeyFromCreateTable(catalogStr.toString(), null);
                                } else {
                                    StringBuffer queryBuf = new StringBuffer("SHOW TABLE STATUS FROM ");
                                    queryBuf.append(DatabaseMetaData.this.quotedId);
                                    queryBuf.append(catalogStr.toString());
                                    queryBuf.append(DatabaseMetaData.this.quotedId);
                                    fkresults = stmt.executeQuery(queryBuf.toString());
                                }
                                String tableNameWithCase = DatabaseMetaData.this.getTableNameWithCase(table);
                                while (fkresults.next()) {
                                    StringTokenizer commentTokens;
                                    String comment;
                                    String tableType = fkresults.getString("Type");
                                    if (tableType == null || !tableType.equalsIgnoreCase("innodb") && !tableType.equalsIgnoreCase(DatabaseMetaData.SUPPORTS_FK) || (comment = fkresults.getString("Comment").trim()) == null || !(commentTokens = new StringTokenizer(comment, ";", false)).hasMoreTokens()) continue;
                                    commentTokens.nextToken();
                                    while (commentTokens.hasMoreTokens()) {
                                        String keys = commentTokens.nextToken();
                                        DatabaseMetaData.this.getExportKeyResults(catalogStr.toString(), tableNameWithCase, keys, rows, fkresults.getString("Name"));
                                    }
                                }
                                Object var9_8 = null;
                                if (fkresults == null) break block10;
                            }
                            catch (Throwable throwable) {
                                Object var9_9 = null;
                                if (fkresults != null) {
                                    try {
                                        fkresults.close();
                                    }
                                    catch (SQLException sqlEx) {
                                        AssertionFailedException.shouldNotHappen(sqlEx);
                                    }
                                    fkresults = null;
                                }
                                throw throwable;
                            }
                            try {
                                fkresults.close();
                            }
                            catch (SQLException sqlEx) {
                                AssertionFailedException.shouldNotHappen(sqlEx);
                            }
                            fkresults = null;
                            {
                            }
                        }
                    }
                }.doForAll();
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        java.sql.ResultSet results = this.buildResultSet(fields, rows);
        return results;
    }

    private void getExportKeyResults(String catalog, String exportingTable, String keysComment, List tuples, String fkTableName) throws SQLException {
        this.getResultsImpl(catalog, exportingTable, keysComment, tuples, fkTableName, true);
    }

    public String getExtraNameCharacters() throws SQLException {
        return "#@";
    }

    private int[] getForeignKeyActions(String commentString) {
        int[] actions = new int[]{3, 3};
        int lastParenIndex = commentString.lastIndexOf(")");
        if (lastParenIndex != commentString.length() - 1) {
            String cascadeOptions = commentString.substring(lastParenIndex + 1).trim().toUpperCase(Locale.ENGLISH);
            actions[0] = this.getCascadeDeleteOption(cascadeOptions);
            actions[1] = this.getCascadeUpdateOption(cascadeOptions);
        }
        return actions;
    }

    public String getIdentifierQuoteString() throws SQLException {
        if (this.conn.supportsQuotedIdentifiers()) {
            if (!this.conn.useAnsiQuotedIdentifiers()) {
                return "`";
            }
            return "\"";
        }
        return " ";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public java.sql.ResultSet getImportedKeys(String catalog, String schema, final String table) throws SQLException {
        if (table == null) {
            throw SQLError.createSQLException("Table not specified.", "S1009");
        }
        Field[] fields = new Field[]{new Field("", "PKTABLE_CAT", 1, 255), new Field("", "PKTABLE_SCHEM", 1, 0), new Field("", "PKTABLE_NAME", 1, 255), new Field("", "PKCOLUMN_NAME", 1, 32), new Field("", "FKTABLE_CAT", 1, 255), new Field("", "FKTABLE_SCHEM", 1, 0), new Field("", "FKTABLE_NAME", 1, 255), new Field("", "FKCOLUMN_NAME", 1, 32), new Field("", "KEY_SEQ", 5, 2), new Field("", "UPDATE_RULE", 5, 2), new Field("", "DELETE_RULE", 5, 2), new Field("", "FK_NAME", 1, 255), new Field("", "PK_NAME", 1, 0), new Field("", "DEFERRABILITY", 4, 2)};
        final ArrayList rows = new ArrayList();
        if (this.conn.versionMeetsMinimum(3, 23, 0)) {
            final Statement stmt = this.conn.getMetadataSafeStatement();
            try {
                new IterateBlock(this.getCatalogIterator(catalog)){

                    /*
                     * WARNING - Removed try catching itself - possible behaviour change.
                     */
                    void forEach(Object catalogStr) throws SQLException {
                        block10: {
                            java.sql.ResultSet fkresults = null;
                            try {
                                if (DatabaseMetaData.this.conn.versionMeetsMinimum(3, 23, 50)) {
                                    fkresults = DatabaseMetaData.this.extractForeignKeyFromCreateTable(catalogStr.toString(), table);
                                } else {
                                    StringBuffer queryBuf = new StringBuffer("SHOW TABLE STATUS ");
                                    queryBuf.append(" FROM ");
                                    queryBuf.append(DatabaseMetaData.this.quotedId);
                                    queryBuf.append(catalogStr.toString());
                                    queryBuf.append(DatabaseMetaData.this.quotedId);
                                    queryBuf.append(" LIKE '");
                                    queryBuf.append(table);
                                    queryBuf.append("'");
                                    fkresults = stmt.executeQuery(queryBuf.toString());
                                }
                                while (fkresults.next()) {
                                    StringTokenizer commentTokens;
                                    String comment;
                                    String tableType = fkresults.getString("Type");
                                    if (tableType == null || !tableType.equalsIgnoreCase("innodb") && !tableType.equalsIgnoreCase(DatabaseMetaData.SUPPORTS_FK) || (comment = fkresults.getString("Comment").trim()) == null || !(commentTokens = new StringTokenizer(comment, ";", false)).hasMoreTokens()) continue;
                                    commentTokens.nextToken();
                                    while (commentTokens.hasMoreTokens()) {
                                        String keys = commentTokens.nextToken();
                                        DatabaseMetaData.this.getImportKeyResults(catalogStr.toString(), table, keys, rows);
                                    }
                                }
                                Object var8_7 = null;
                                if (fkresults == null) break block10;
                            }
                            catch (Throwable throwable) {
                                Object var8_8 = null;
                                if (fkresults != null) {
                                    try {
                                        fkresults.close();
                                    }
                                    catch (SQLException sqlEx) {
                                        AssertionFailedException.shouldNotHappen(sqlEx);
                                    }
                                    fkresults = null;
                                }
                                throw throwable;
                            }
                            try {
                                fkresults.close();
                            }
                            catch (SQLException sqlEx) {
                                AssertionFailedException.shouldNotHappen(sqlEx);
                            }
                            fkresults = null;
                            {
                            }
                        }
                    }
                }.doForAll();
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        java.sql.ResultSet results = this.buildResultSet(fields, rows);
        return results;
    }

    private void getImportKeyResults(String catalog, String importingTable, String keysComment, List tuples) throws SQLException {
        this.getResultsImpl(catalog, importingTable, keysComment, tuples, null, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public java.sql.ResultSet getIndexInfo(String catalog, String schema, final String table, final boolean unique, boolean approximate) throws SQLException {
        Field[] fields = new Field[]{new Field("", "TABLE_CAT", 1, 255), new Field("", "TABLE_SCHEM", 1, 0), new Field("", "TABLE_NAME", 1, 255), new Field("", "NON_UNIQUE", 1, 4), new Field("", "INDEX_QUALIFIER", 1, 1), new Field("", "INDEX_NAME", 1, 32), new Field("", "TYPE", 1, 32), new Field("", "ORDINAL_POSITION", 5, 5), new Field("", "COLUMN_NAME", 1, 32), new Field("", "ASC_OR_DESC", 1, 1), new Field("", "CARDINALITY", 4, 10), new Field("", "PAGES", 4, 10), new Field("", "FILTER_CONDITION", 1, 32)};
        final ArrayList rows = new ArrayList();
        final Statement stmt = this.conn.getMetadataSafeStatement();
        try {
            java.sql.ResultSet indexInfo;
            new IterateBlock(this.getCatalogIterator(catalog)){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                void forEach(Object catalogStr) throws SQLException {
                    block11: {
                        java.sql.ResultSet results = null;
                        try {
                            block10: {
                                StringBuffer queryBuf = new StringBuffer("SHOW INDEX FROM ");
                                queryBuf.append(DatabaseMetaData.this.quotedId);
                                queryBuf.append(table);
                                queryBuf.append(DatabaseMetaData.this.quotedId);
                                queryBuf.append(" FROM ");
                                queryBuf.append(DatabaseMetaData.this.quotedId);
                                queryBuf.append(catalogStr.toString());
                                queryBuf.append(DatabaseMetaData.this.quotedId);
                                try {
                                    results = stmt.executeQuery(queryBuf.toString());
                                }
                                catch (SQLException sqlEx) {
                                    int errorCode = sqlEx.getErrorCode();
                                    if ("42S02".equals(sqlEx.getSQLState()) || errorCode == 1146) break block10;
                                    throw sqlEx;
                                }
                            }
                            while (results != null && results.next()) {
                                byte[][] row = new byte[14][];
                                row[0] = catalogStr.toString() == null ? new byte[]{} : DatabaseMetaData.this.s2b(catalogStr.toString());
                                row[1] = null;
                                row[2] = results.getBytes("Table");
                                boolean indexIsUnique = results.getInt("Non_unique") == 0;
                                row[3] = !indexIsUnique ? DatabaseMetaData.this.s2b("true") : DatabaseMetaData.this.s2b("false");
                                row[4] = new byte[0];
                                row[5] = results.getBytes("Key_name");
                                row[6] = Integer.toString(3).getBytes();
                                row[7] = results.getBytes("Seq_in_index");
                                row[8] = results.getBytes("Column_name");
                                row[9] = results.getBytes("Collation");
                                row[10] = results.getBytes("Cardinality");
                                row[11] = DatabaseMetaData.this.s2b("0");
                                row[12] = null;
                                if (unique) {
                                    if (!indexIsUnique) continue;
                                    rows.add(row);
                                    continue;
                                }
                                rows.add(row);
                            }
                            Object var7_7 = null;
                            if (results == null) break block11;
                        }
                        catch (Throwable throwable) {
                            Object var7_8 = null;
                            if (results != null) {
                                try {
                                    results.close();
                                }
                                catch (Exception ex) {
                                    // empty catch block
                                }
                                results = null;
                            }
                            throw throwable;
                        }
                        try {
                            results.close();
                        }
                        catch (Exception ex) {
                            // empty catch block
                        }
                        results = null;
                        {
                        }
                    }
                }
            }.doForAll();
            java.sql.ResultSet resultSet = indexInfo = this.buildResultSet(fields, rows);
            return resultSet;
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public int getJDBCMajorVersion() throws SQLException {
        return 3;
    }

    public int getJDBCMinorVersion() throws SQLException {
        return 0;
    }

    public int getMaxBinaryLiteralLength() throws SQLException {
        return 0xFFFFF8;
    }

    public int getMaxCatalogNameLength() throws SQLException {
        return 32;
    }

    public int getMaxCharLiteralLength() throws SQLException {
        return 0xFFFFF8;
    }

    public int getMaxColumnNameLength() throws SQLException {
        return 64;
    }

    public int getMaxColumnsInGroupBy() throws SQLException {
        return 64;
    }

    public int getMaxColumnsInIndex() throws SQLException {
        return 16;
    }

    public int getMaxColumnsInOrderBy() throws SQLException {
        return 64;
    }

    public int getMaxColumnsInSelect() throws SQLException {
        return 256;
    }

    public int getMaxColumnsInTable() throws SQLException {
        return 512;
    }

    public int getMaxConnections() throws SQLException {
        return 0;
    }

    public int getMaxCursorNameLength() throws SQLException {
        return 64;
    }

    public int getMaxIndexLength() throws SQLException {
        return 256;
    }

    public int getMaxProcedureNameLength() throws SQLException {
        return 0;
    }

    public int getMaxRowSize() throws SQLException {
        return 0x7FFFFFF7;
    }

    public int getMaxSchemaNameLength() throws SQLException {
        return 0;
    }

    public int getMaxStatementLength() throws SQLException {
        return MysqlIO.getMaxBuf() - 4;
    }

    public int getMaxStatements() throws SQLException {
        return 0;
    }

    public int getMaxTableNameLength() throws SQLException {
        return 64;
    }

    public int getMaxTablesInSelect() throws SQLException {
        return 256;
    }

    public int getMaxUserNameLength() throws SQLException {
        return 16;
    }

    public String getNumericFunctions() throws SQLException {
        return "ABS,ACOS,ASIN,ATAN,ATAN2,BIT_COUNT,CEILING,COS,COT,DEGREES,EXP,FLOOR,LOG,LOG10,MAX,MIN,MOD,PI,POW,POWER,RADIANS,RAND,ROUND,SIN,SQRT,TAN,TRUNCATE";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public java.sql.ResultSet getPrimaryKeys(String catalog, String schema, final String table) throws SQLException {
        Field[] fields = new Field[]{new Field("", "TABLE_CAT", 1, 255), new Field("", "TABLE_SCHEM", 1, 0), new Field("", "TABLE_NAME", 1, 255), new Field("", "COLUMN_NAME", 1, 32), new Field("", "KEY_SEQ", 5, 5), new Field("", "PK_NAME", 1, 32)};
        if (table == null) {
            throw SQLError.createSQLException("Table not specified.", "S1009");
        }
        final ArrayList rows = new ArrayList();
        final Statement stmt = this.conn.getMetadataSafeStatement();
        try {
            new IterateBlock(this.getCatalogIterator(catalog)){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                void forEach(Object catalogStr) throws SQLException {
                    block8: {
                        java.sql.ResultSet rs = null;
                        try {
                            StringBuffer queryBuf = new StringBuffer("SHOW KEYS FROM ");
                            queryBuf.append(DatabaseMetaData.this.quotedId);
                            queryBuf.append(table);
                            queryBuf.append(DatabaseMetaData.this.quotedId);
                            queryBuf.append(" FROM ");
                            queryBuf.append(DatabaseMetaData.this.quotedId);
                            queryBuf.append(catalogStr.toString());
                            queryBuf.append(DatabaseMetaData.this.quotedId);
                            rs = stmt.executeQuery(queryBuf.toString());
                            ArrayList tuples = new ArrayList();
                            TreeMap<String, byte[][]> sortMap = new TreeMap<String, byte[][]>();
                            while (rs.next()) {
                                String keyType = rs.getString("Key_name");
                                if (keyType == null || !keyType.equalsIgnoreCase("PRIMARY") && !keyType.equalsIgnoreCase("PRI")) continue;
                                byte[][] tuple = new byte[6][];
                                tuple[0] = catalogStr.toString() == null ? new byte[]{} : DatabaseMetaData.this.s2b(catalogStr.toString());
                                tuple[1] = null;
                                tuple[2] = DatabaseMetaData.this.s2b(table);
                                String columnName = rs.getString("Column_name");
                                tuple[3] = DatabaseMetaData.this.s2b(columnName);
                                tuple[4] = DatabaseMetaData.this.s2b(rs.getString("Seq_in_index"));
                                tuple[5] = DatabaseMetaData.this.s2b(keyType);
                                sortMap.put(columnName, tuple);
                            }
                            Iterator sortedIterator = sortMap.values().iterator();
                            while (sortedIterator.hasNext()) {
                                rows.add(sortedIterator.next());
                            }
                            Object var10_9 = null;
                            if (rs == null) break block8;
                        }
                        catch (Throwable throwable) {
                            Object var10_10 = null;
                            if (rs != null) {
                                try {
                                    rs.close();
                                }
                                catch (Exception ex) {
                                    // empty catch block
                                }
                                rs = null;
                            }
                            throw throwable;
                        }
                        try {
                            rs.close();
                        }
                        catch (Exception ex) {
                            // empty catch block
                        }
                        rs = null;
                        {
                        }
                    }
                }
            }.doForAll();
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        java.sql.ResultSet results = this.buildResultSet(fields, rows);
        return results;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public java.sql.ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) throws SQLException {
        ArrayList<String> proceduresToExtractList;
        Field[] fields;
        block14: {
            fields = new Field[]{new Field("", "PROCEDURE_CAT", 1, 0), new Field("", "PROCEDURE_SCHEM", 1, 0), new Field("", "PROCEDURE_NAME", 1, 0), new Field("", "COLUMN_NAME", 1, 0), new Field("", "COLUMN_TYPE", 1, 0), new Field("", "DATA_TYPE", 5, 0), new Field("", "TYPE_NAME", 1, 0), new Field("", "PRECISION", 4, 0), new Field("", "LENGTH", 4, 0), new Field("", "SCALE", 5, 0), new Field("", "RADIX", 5, 0), new Field("", "NULLABLE", 5, 0), new Field("", "REMARKS", 1, 0)};
            proceduresToExtractList = new ArrayList<String>();
            if (this.supportsStoredProcedures()) {
                java.sql.ResultSet procedureNameRs;
                if (procedureNamePattern.indexOf("%") == -1 && procedureNamePattern.indexOf("?") == -1) {
                    proceduresToExtractList.add(procedureNamePattern);
                } else {
                    SQLException rethrowSqlEx;
                    block13: {
                        procedureNameRs = null;
                        procedureNameRs = this.getProcedures(catalog, schemaPattern, procedureNamePattern);
                        while (procedureNameRs.next()) {
                            proceduresToExtractList.add(procedureNameRs.getString(3));
                        }
                        Collections.sort(proceduresToExtractList);
                        Object var9_8 = null;
                        rethrowSqlEx = null;
                        if (procedureNameRs == null) break block13;
                        try {
                            procedureNameRs.close();
                        }
                        catch (SQLException sqlEx) {
                            rethrowSqlEx = sqlEx;
                        }
                    }
                    if (rethrowSqlEx != null) {
                        throw rethrowSqlEx;
                    }
                }
                break block14;
                catch (Throwable throwable) {
                    Object var9_9 = null;
                    SQLException rethrowSqlEx = null;
                    if (procedureNameRs != null) {
                        try {
                            procedureNameRs.close();
                        }
                        catch (SQLException sqlEx) {
                            rethrowSqlEx = sqlEx;
                        }
                    }
                    if (rethrowSqlEx != null) {
                        throw rethrowSqlEx;
                    }
                    throw throwable;
                }
            }
        }
        ArrayList resultRows = new ArrayList();
        Iterator iter = proceduresToExtractList.iterator();
        while (iter.hasNext()) {
            String procName = (String)iter.next();
            this.getCallStmtParameterTypes(catalog, procName, columnNamePattern, resultRows);
        }
        return this.buildResultSet(fields, resultRows);
    }

    public java.sql.ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
        return this.getProceduresAndOrFunctions(catalog, schemaPattern, procedureNamePattern, true, true);
    }

    protected java.sql.ResultSet getProceduresAndOrFunctions(String catalog, String schemaPattern, String procedureNamePattern, final boolean returnProcedures, final boolean returnFunctions) throws SQLException {
        if (procedureNamePattern == null || procedureNamePattern.length() == 0) {
            if (this.conn.getNullNamePatternMatchesAll()) {
                procedureNamePattern = "%";
            } else {
                throw SQLError.createSQLException("Procedure name pattern can not be NULL or empty.", "S1009");
            }
        }
        Field[] fields = new Field[]{new Field("", "PROCEDURE_CAT", 1, 0), new Field("", "PROCEDURE_SCHEM", 1, 0), new Field("", "PROCEDURE_NAME", 1, 0), new Field("", "reserved1", 1, 0), new Field("", "reserved2", 1, 0), new Field("", "reserved3", 1, 0), new Field("", "REMARKS", 1, 0), new Field("", "PROCEDURE_TYPE", 5, 0)};
        final ArrayList procedureRows = new ArrayList();
        if (this.supportsStoredProcedures()) {
            final String procNamePattern = procedureNamePattern;
            final TreeMap procedureRowsOrderedByName = new TreeMap();
            new IterateBlock(this.getCatalogIterator(catalog)){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                void forEach(Object catalogStr) throws SQLException {
                    SQLException sqlEx32;
                    SQLException rethrowSqlEx2;
                    PreparedStatement proceduresStmt;
                    block25: {
                        String db = catalogStr.toString();
                        boolean fromSelect = false;
                        java.sql.ResultSet proceduresRs = null;
                        boolean needsClientFiltering = true;
                        proceduresStmt = DatabaseMetaData.this.conn.clientPrepareStatement("SELECT name, type FROM mysql.proc WHERE name like ? and db <=> ? ORDER BY name");
                        try {
                            boolean hasTypeColumn = false;
                            if (db != null) {
                                proceduresStmt.setString(2, db);
                            } else {
                                proceduresStmt.setNull(2, 12);
                            }
                            int nameIndex = 1;
                            if (proceduresStmt.getMaxRows() != 0) {
                                proceduresStmt.setMaxRows(0);
                            }
                            proceduresStmt.setString(1, procNamePattern);
                            try {
                                proceduresRs = proceduresStmt.executeQuery();
                                fromSelect = true;
                                needsClientFiltering = false;
                                hasTypeColumn = true;
                            }
                            catch (SQLException sqlEx2) {
                                proceduresStmt.close();
                                fromSelect = false;
                                nameIndex = DatabaseMetaData.this.conn.versionMeetsMinimum(5, 0, 1) ? 2 : 1;
                                proceduresStmt = DatabaseMetaData.this.conn.clientPrepareStatement("SHOW PROCEDURE STATUS LIKE ?");
                                if (proceduresStmt.getMaxRows() != 0) {
                                    proceduresStmt.setMaxRows(0);
                                }
                                proceduresStmt.setString(1, procNamePattern);
                                proceduresRs = proceduresStmt.executeQuery();
                            }
                            if (returnProcedures) {
                                DatabaseMetaData.this.convertToJdbcProcedureList(fromSelect, db, proceduresRs, needsClientFiltering, db, procedureRowsOrderedByName, nameIndex);
                            }
                            if (!hasTypeColumn) {
                                if (proceduresStmt != null) {
                                    proceduresStmt.close();
                                }
                                if ((proceduresStmt = DatabaseMetaData.this.conn.clientPrepareStatement("SHOW FUNCTION STATUS LIKE ?")).getMaxRows() != 0) {
                                    proceduresStmt.setMaxRows(0);
                                }
                                proceduresStmt.setString(1, procNamePattern);
                                proceduresRs = proceduresStmt.executeQuery();
                                if (returnFunctions) {
                                    DatabaseMetaData.this.convertToJdbcFunctionList(db, proceduresRs, needsClientFiltering, db, procedureRowsOrderedByName, nameIndex);
                                }
                            }
                            Iterator proceduresIter = procedureRowsOrderedByName.values().iterator();
                            while (proceduresIter.hasNext()) {
                                procedureRows.add(proceduresIter.next());
                            }
                            Object var11_11 = null;
                            rethrowSqlEx2 = null;
                            if (proceduresRs == null) break block25;
                        }
                        catch (Throwable throwable) {
                            SQLException sqlEx32;
                            Object var11_12 = null;
                            SQLException rethrowSqlEx2 = null;
                            if (proceduresRs != null) {
                                try {
                                    proceduresRs.close();
                                }
                                catch (SQLException sqlEx32) {
                                    rethrowSqlEx2 = sqlEx32;
                                }
                            }
                            if (proceduresStmt != null) {
                                try {
                                    proceduresStmt.close();
                                }
                                catch (SQLException sqlEx32) {
                                    rethrowSqlEx2 = sqlEx32;
                                }
                            }
                            if (rethrowSqlEx2 != null) {
                                throw rethrowSqlEx2;
                            }
                            throw throwable;
                        }
                        try {
                            proceduresRs.close();
                        }
                        catch (SQLException sqlEx32) {
                            rethrowSqlEx2 = sqlEx32;
                        }
                    }
                    if (proceduresStmt != null) {
                        try {
                            proceduresStmt.close();
                        }
                        catch (SQLException sqlEx32) {
                            rethrowSqlEx2 = sqlEx32;
                        }
                    }
                    if (rethrowSqlEx2 != null) {
                        throw rethrowSqlEx2;
                    }
                }
            }.doForAll();
        }
        return this.buildResultSet(fields, procedureRows);
    }

    public String getProcedureTerm() throws SQLException {
        return "PROCEDURE";
    }

    public int getResultSetHoldability() throws SQLException {
        return 1;
    }

    private void getResultsImpl(String catalog, String table, String keysComment, List tuples, String fkTableName, boolean isExport) throws SQLException {
        LocalAndReferencedColumns parsedInfo = this.parseTableStatusIntoLocalAndReferencedColumns(keysComment);
        if (isExport && !parsedInfo.referencedTable.equals(table)) {
            return;
        }
        if (parsedInfo.localColumnsList.size() != parsedInfo.referencedColumnsList.size()) {
            throw SQLError.createSQLException("Error parsing foreign keys definition,number of local and referenced columns is not the same.", "S1000");
        }
        Iterator localColumnNames = parsedInfo.localColumnsList.iterator();
        Iterator referColumnNames = parsedInfo.referencedColumnsList.iterator();
        int keySeqIndex = 1;
        while (localColumnNames.hasNext()) {
            byte[][] tuple = new byte[14][];
            String lColumnName = this.removeQuotedId(localColumnNames.next().toString());
            String rColumnName = this.removeQuotedId(referColumnNames.next().toString());
            tuple[4] = catalog == null ? new byte[]{} : this.s2b(catalog);
            tuple[5] = null;
            tuple[6] = this.s2b(isExport ? fkTableName : table);
            tuple[7] = this.s2b(lColumnName);
            tuple[0] = this.s2b(parsedInfo.referencedCatalog);
            tuple[1] = null;
            tuple[2] = this.s2b(isExport ? table : parsedInfo.referencedTable);
            tuple[3] = this.s2b(rColumnName);
            tuple[8] = this.s2b(Integer.toString(keySeqIndex++));
            int[] actions = this.getForeignKeyActions(keysComment);
            tuple[9] = this.s2b(Integer.toString(actions[1]));
            tuple[10] = this.s2b(Integer.toString(actions[0]));
            tuple[11] = this.s2b(parsedInfo.constraintName);
            tuple[12] = null;
            tuple[13] = this.s2b(Integer.toString(7));
            tuples.add(tuple);
        }
    }

    public java.sql.ResultSet getSchemas() throws SQLException {
        Field[] fields = new Field[]{new Field("", "TABLE_SCHEM", 1, 0), new Field("", "TABLE_CATALOG", 1, 0)};
        ArrayList tuples = new ArrayList();
        java.sql.ResultSet results = this.buildResultSet(fields, tuples);
        return results;
    }

    public String getSchemaTerm() throws SQLException {
        return "";
    }

    public String getSearchStringEscape() throws SQLException {
        return "\\";
    }

    public String getSQLKeywords() throws SQLException {
        return mysqlKeywordsThatArentSQL92;
    }

    public int getSQLStateType() throws SQLException {
        if (this.conn.versionMeetsMinimum(4, 1, 0)) {
            return 2;
        }
        if (this.conn.getUseSqlStateCodes()) {
            return 2;
        }
        return 1;
    }

    public String getStringFunctions() throws SQLException {
        return "ASCII,BIN,BIT_LENGTH,CHAR,CHARACTER_LENGTH,CHAR_LENGTH,CONCAT,CONCAT_WS,CONV,ELT,EXPORT_SET,FIELD,FIND_IN_SET,HEX,INSERT,INSTR,LCASE,LEFT,LENGTH,LOAD_FILE,LOCATE,LOCATE,LOWER,LPAD,LTRIM,MAKE_SET,MATCH,MID,OCT,OCTET_LENGTH,ORD,POSITION,QUOTE,REPEAT,REPLACE,REVERSE,RIGHT,RPAD,RTRIM,SOUNDEX,SPACE,STRCMP,SUBSTRING,SUBSTRING,SUBSTRING,SUBSTRING,SUBSTRING_INDEX,TRIM,UCASE,UPPER";
    }

    public java.sql.ResultSet getSuperTables(String arg0, String arg1, String arg2) throws SQLException {
        Field[] fields = new Field[]{new Field("", "TABLE_CAT", 1, 32), new Field("", "TABLE_SCHEM", 1, 32), new Field("", "TABLE_NAME", 1, 32), new Field("", "SUPERTABLE_NAME", 1, 32)};
        return this.buildResultSet(fields, new ArrayList());
    }

    public java.sql.ResultSet getSuperTypes(String arg0, String arg1, String arg2) throws SQLException {
        Field[] fields = new Field[]{new Field("", "TABLE_CAT", 1, 32), new Field("", "TABLE_SCHEM", 1, 32), new Field("", "TYPE_NAME", 1, 32), new Field("", "SUPERTYPE_CAT", 1, 32), new Field("", "SUPERTYPE_SCHEM", 1, 32), new Field("", "SUPERTYPE_NAME", 1, 32)};
        return this.buildResultSet(fields, new ArrayList());
    }

    public String getSystemFunctions() throws SQLException {
        return "DATABASE,USER,SYSTEM_USER,SESSION_USER,PASSWORD,ENCRYPT,LAST_INSERT_ID,VERSION";
    }

    private String getTableNameWithCase(String table) {
        String tableNameWithCase = this.conn.lowerCaseTableNames() ? table.toLowerCase() : table;
        return tableNameWithCase;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public java.sql.ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        ArrayList<byte[][]> grantRows;
        Field[] fields;
        block28: {
            Exception ex32;
            Statement stmt;
            block25: {
                if (tableNamePattern == null) {
                    if (this.conn.getNullNamePatternMatchesAll()) {
                        tableNamePattern = "%";
                    } else {
                        throw SQLError.createSQLException("Table name pattern can not be NULL or empty.", "S1009");
                    }
                }
                fields = new Field[]{new Field("", "TABLE_CAT", 1, 64), new Field("", "TABLE_SCHEM", 1, 1), new Field("", "TABLE_NAME", 1, 64), new Field("", "GRANTOR", 1, 77), new Field("", "GRANTEE", 1, 77), new Field("", "PRIVILEGE", 1, 64), new Field("", "IS_GRANTABLE", 1, 3)};
                StringBuffer grantQuery = new StringBuffer("SELECT host,db,table_name,grantor,user,table_priv from mysql.tables_priv ");
                grantQuery.append(" WHERE ");
                if (catalog != null && catalog.length() != 0) {
                    grantQuery.append(" db='");
                    grantQuery.append(catalog);
                    grantQuery.append("' AND ");
                }
                grantQuery.append("table_name like '");
                grantQuery.append(tableNamePattern);
                grantQuery.append("'");
                java.sql.ResultSet results = null;
                grantRows = new ArrayList<byte[][]>();
                stmt = null;
                try {
                    stmt = this.conn.createStatement();
                    stmt.setEscapeProcessing(false);
                    results = stmt.executeQuery(grantQuery.toString());
                    while (results.next()) {
                        String allPrivileges;
                        String host = results.getString(1);
                        String db = results.getString(2);
                        String table = results.getString(3);
                        String grantor = results.getString(4);
                        String user = results.getString(5);
                        if (user == null || user.length() == 0) {
                            user = "%";
                        }
                        StringBuffer fullUser = new StringBuffer(user);
                        if (host != null && this.conn.getUseHostsInPrivileges()) {
                            fullUser.append("@");
                            fullUser.append(host);
                        }
                        if ((allPrivileges = results.getString(6)) == null) continue;
                        allPrivileges = allPrivileges.toUpperCase(Locale.ENGLISH);
                        StringTokenizer st = new StringTokenizer(allPrivileges, ",");
                        while (st.hasMoreTokens()) {
                            Exception ex22;
                            Object var21_20;
                            String privilege = st.nextToken().trim();
                            java.sql.ResultSet columnResults = null;
                            try {
                                columnResults = this.getColumns(catalog, schemaPattern, table, "%");
                                while (columnResults.next()) {
                                    byte[][] tuple = new byte[8][];
                                    tuple[0] = this.s2b(db);
                                    tuple[1] = null;
                                    tuple[2] = this.s2b(table);
                                    tuple[3] = (byte[])(grantor != null ? this.s2b(grantor) : null);
                                    tuple[4] = this.s2b(fullUser.toString());
                                    tuple[5] = this.s2b(privilege);
                                    tuple[6] = null;
                                    grantRows.add(tuple);
                                }
                                var21_20 = null;
                                if (columnResults == null) continue;
                            }
                            catch (Throwable throwable) {
                                var21_20 = null;
                                if (columnResults != null) {
                                    try {
                                        columnResults.close();
                                    }
                                    catch (Exception ex22) {
                                        // empty catch block
                                    }
                                }
                                throw throwable;
                            }
                            try {
                                columnResults.close();
                            }
                            catch (Exception ex22) {}
                        }
                    }
                    Object var24_23 = null;
                    if (results == null) break block25;
                }
                catch (Throwable throwable) {
                    Exception ex32;
                    Object var24_24 = null;
                    if (results != null) {
                        try {
                            results.close();
                        }
                        catch (Exception ex32) {
                            // empty catch block
                        }
                        results = null;
                    }
                    if (stmt != null) {
                        try {
                            stmt.close();
                        }
                        catch (Exception ex32) {
                            // empty catch block
                        }
                        stmt = null;
                    }
                    throw throwable;
                }
                try {
                    results.close();
                }
                catch (Exception ex32) {
                    // empty catch block
                }
                results = null;
            }
            if (stmt == null) break block28;
            try {
                stmt.close();
            }
            catch (Exception ex32) {
                // empty catch block
            }
            stmt = null;
            {
            }
        }
        return this.buildResultSet(fields, grantRows);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public java.sql.ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, final String[] types) throws SQLException {
        if (tableNamePattern == null) {
            if (this.conn.getNullNamePatternMatchesAll()) {
                tableNamePattern = "%";
            } else {
                throw SQLError.createSQLException("Table name pattern can not be NULL or empty.", "S1009");
            }
        }
        Field[] fields = new Field[]{new Field("", "TABLE_CAT", 12, 255), new Field("", "TABLE_SCHEM", 12, 0), new Field("", "TABLE_NAME", 12, 255), new Field("", "TABLE_TYPE", 12, 5), new Field("", "REMARKS", 12, 0)};
        final ArrayList tuples = new ArrayList();
        final Statement stmt = this.conn.getMetadataSafeStatement();
        final String tableNamePat = tableNamePattern;
        try {
            new IterateBlock(this.getCatalogIterator(catalog)){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                void forEach(Object catalogStr) throws SQLException {
                    block40: {
                        java.sql.ResultSet results = null;
                        try {
                            if (!DatabaseMetaData.this.conn.versionMeetsMinimum(5, 0, 2)) {
                                try {
                                    results = stmt.executeQuery("SHOW TABLES FROM " + DatabaseMetaData.this.quotedId + catalogStr.toString() + DatabaseMetaData.this.quotedId + " LIKE '" + tableNamePat + "'");
                                }
                                catch (SQLException sqlEx) {
                                    if ("08S01".equals(sqlEx.getSQLState())) {
                                        throw sqlEx;
                                    }
                                    Object var12_6 = null;
                                    if (results != null) {
                                        try {
                                            results.close();
                                        }
                                        catch (Exception ex) {
                                            // empty catch block
                                        }
                                        results = null;
                                    }
                                    return;
                                }
                            }
                            try {
                                results = stmt.executeQuery("SHOW FULL TABLES FROM " + DatabaseMetaData.this.quotedId + catalogStr.toString() + DatabaseMetaData.this.quotedId + " LIKE '" + tableNamePat + "'");
                            }
                            catch (SQLException sqlEx) {
                                if ("08S01".equals(sqlEx.getSQLState())) {
                                    throw sqlEx;
                                }
                                Object var12_7 = null;
                                if (results != null) {
                                    try {
                                        results.close();
                                    }
                                    catch (Exception ex) {
                                        // empty catch block
                                    }
                                    results = null;
                                }
                                return;
                            }
                            boolean shouldReportTables = false;
                            boolean shouldReportViews = false;
                            if (types == null || types.length == 0) {
                                shouldReportTables = true;
                                shouldReportViews = true;
                            } else {
                                for (int i = 0; i < types.length; ++i) {
                                    if ("TABLE".equalsIgnoreCase(types[i])) {
                                        shouldReportTables = true;
                                    }
                                    if (!"VIEW".equalsIgnoreCase(types[i])) continue;
                                    shouldReportViews = true;
                                }
                            }
                            int typeColumnIndex = 0;
                            boolean hasTableTypes = false;
                            if (DatabaseMetaData.this.conn.versionMeetsMinimum(5, 0, 2)) {
                                try {
                                    typeColumnIndex = results.findColumn("table_type");
                                    hasTableTypes = true;
                                }
                                catch (SQLException sqlEx) {
                                    try {
                                        typeColumnIndex = results.findColumn("Type");
                                        hasTableTypes = true;
                                    }
                                    catch (SQLException sqlEx2) {
                                        hasTableTypes = false;
                                    }
                                }
                            }
                            TreeMap<String, byte[][]> tablesOrderedByName = null;
                            TreeMap<String, byte[][]> viewsOrderedByName = null;
                            while (results.next()) {
                                byte[][] row = new byte[5][];
                                row[0] = catalogStr.toString() == null ? null : DatabaseMetaData.this.s2b(catalogStr.toString());
                                row[1] = null;
                                row[2] = results.getBytes(1);
                                row[4] = new byte[0];
                                if (hasTableTypes) {
                                    String tableType = results.getString(typeColumnIndex);
                                    if (("table".equalsIgnoreCase(tableType) || "base table".equalsIgnoreCase(tableType)) && shouldReportTables) {
                                        row[3] = TABLE_AS_BYTES;
                                        if (tablesOrderedByName == null) {
                                            tablesOrderedByName = new TreeMap();
                                        }
                                        tablesOrderedByName.put(results.getString(1), row);
                                        continue;
                                    }
                                    if ("view".equalsIgnoreCase(tableType) && shouldReportViews) {
                                        row[3] = VIEW_AS_BYTES;
                                        if (viewsOrderedByName == null) {
                                            viewsOrderedByName = new TreeMap<String, byte[][]>();
                                        }
                                        viewsOrderedByName.put(results.getString(1), row);
                                        continue;
                                    }
                                    if (hasTableTypes) continue;
                                    row[3] = TABLE_AS_BYTES;
                                    if (tablesOrderedByName == null) {
                                        tablesOrderedByName = new TreeMap();
                                    }
                                    tablesOrderedByName.put(results.getString(1), row);
                                    continue;
                                }
                                if (!shouldReportTables) continue;
                                row[3] = TABLE_AS_BYTES;
                                if (tablesOrderedByName == null) {
                                    tablesOrderedByName = new TreeMap<String, byte[][]>();
                                }
                                tablesOrderedByName.put(results.getString(1), row);
                            }
                            if (tablesOrderedByName != null) {
                                Iterator tablesIter = tablesOrderedByName.values().iterator();
                                while (tablesIter.hasNext()) {
                                    tuples.add(tablesIter.next());
                                }
                            }
                            if (viewsOrderedByName != null) {
                                Iterator viewsIter = viewsOrderedByName.values().iterator();
                                while (viewsIter.hasNext()) {
                                    tuples.add(viewsIter.next());
                                }
                            }
                            Object var12_8 = null;
                            if (results == null) break block40;
                        }
                        catch (Throwable throwable) {
                            Object var12_9 = null;
                            if (results != null) {
                                try {
                                    results.close();
                                }
                                catch (Exception ex) {
                                    // empty catch block
                                }
                                results = null;
                            }
                            throw throwable;
                        }
                        try {
                            results.close();
                        }
                        catch (Exception ex) {
                            // empty catch block
                        }
                        results = null;
                        {
                        }
                    }
                }
            }.doForAll();
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        java.sql.ResultSet tables = this.buildResultSet(fields, tuples);
        return tables;
    }

    public java.sql.ResultSet getTableTypes() throws SQLException {
        ArrayList<byte[][]> tuples = new ArrayList<byte[][]>();
        Field[] fields = new Field[]{new Field("", "TABLE_TYPE", 12, 5)};
        byte[][] tableTypeRow = new byte[][]{TABLE_AS_BYTES};
        tuples.add(tableTypeRow);
        if (this.conn.versionMeetsMinimum(5, 0, 1)) {
            byte[][] viewTypeRow = new byte[][]{VIEW_AS_BYTES};
            tuples.add(viewTypeRow);
        }
        byte[][] tempTypeRow = new byte[][]{this.s2b("LOCAL TEMPORARY")};
        tuples.add(tempTypeRow);
        return this.buildResultSet(fields, tuples);
    }

    public String getTimeDateFunctions() throws SQLException {
        return "DAYOFWEEK,WEEKDAY,DAYOFMONTH,DAYOFYEAR,MONTH,DAYNAME,MONTHNAME,QUARTER,WEEK,YEAR,HOUR,MINUTE,SECOND,PERIOD_ADD,PERIOD_DIFF,TO_DAYS,FROM_DAYS,DATE_FORMAT,TIME_FORMAT,CURDATE,CURRENT_DATE,CURTIME,CURRENT_TIME,NOW,SYSDATE,CURRENT_TIMESTAMP,UNIX_TIMESTAMP,FROM_UNIXTIME,SEC_TO_TIME,TIME_TO_SEC";
    }

    public java.sql.ResultSet getTypeInfo() throws SQLException {
        Field[] fields = new Field[]{new Field("", "TYPE_NAME", 1, 32), new Field("", "DATA_TYPE", 5, 5), new Field("", "PRECISION", 4, 10), new Field("", "LITERAL_PREFIX", 1, 4), new Field("", "LITERAL_SUFFIX", 1, 4), new Field("", "CREATE_PARAMS", 1, 32), new Field("", "NULLABLE", 5, 5), new Field("", "CASE_SENSITIVE", 1, 3), new Field("", "SEARCHABLE", 5, 3), new Field("", "UNSIGNED_ATTRIBUTE", 1, 3), new Field("", "FIXED_PREC_SCALE", 1, 3), new Field("", "AUTO_INCREMENT", 1, 3), new Field("", "LOCAL_TYPE_NAME", 1, 32), new Field("", "MINIMUM_SCALE", 5, 5), new Field("", "MAXIMUM_SCALE", 5, 5), new Field("", "SQL_DATA_TYPE", 4, 10), new Field("", "SQL_DATETIME_SUB", 4, 10), new Field("", "NUM_PREC_RADIX", 4, 10)};
        Object rowVal = null;
        ArrayList<byte[][]> tuples = new ArrayList<byte[][]>();
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("BIT");
        rowVal[1] = Integer.toString(-7).getBytes();
        rowVal[2] = this.s2b("1");
        rowVal[3] = this.s2b("");
        rowVal[4] = this.s2b("");
        rowVal[5] = this.s2b("");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("true");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("BIT");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("BOOL");
        rowVal[1] = Integer.toString(-7).getBytes();
        rowVal[2] = this.s2b("1");
        rowVal[3] = this.s2b("");
        rowVal[4] = this.s2b("");
        rowVal[5] = this.s2b("");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("true");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("BOOL");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("TINYINT");
        rowVal[1] = Integer.toString(-6).getBytes();
        rowVal[2] = this.s2b("3");
        rowVal[3] = this.s2b("");
        rowVal[4] = this.s2b("");
        rowVal[5] = this.s2b("[(M)] [UNSIGNED] [ZEROFILL]");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("true");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("true");
        rowVal[12] = this.s2b("TINYINT");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("TINYINT UNSIGNED");
        rowVal[1] = Integer.toString(-6).getBytes();
        rowVal[2] = this.s2b("3");
        rowVal[3] = this.s2b("");
        rowVal[4] = this.s2b("");
        rowVal[5] = this.s2b("[(M)] [UNSIGNED] [ZEROFILL]");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("true");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("true");
        rowVal[12] = this.s2b("TINYINT UNSIGNED");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("BIGINT");
        rowVal[1] = Integer.toString(-5).getBytes();
        rowVal[2] = this.s2b("19");
        rowVal[3] = this.s2b("");
        rowVal[4] = this.s2b("");
        rowVal[5] = this.s2b("[(M)] [UNSIGNED] [ZEROFILL]");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("true");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("true");
        rowVal[12] = this.s2b("BIGINT");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("BIGINT UNSIGNED");
        rowVal[1] = Integer.toString(-5).getBytes();
        rowVal[2] = this.s2b("20");
        rowVal[3] = this.s2b("");
        rowVal[4] = this.s2b("");
        rowVal[5] = this.s2b("[(M)] [ZEROFILL]");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("true");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("true");
        rowVal[12] = this.s2b("BIGINT UNSIGNED");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("LONG VARBINARY");
        rowVal[1] = Integer.toString(-4).getBytes();
        rowVal[2] = this.s2b("16777215");
        rowVal[3] = this.s2b("'");
        rowVal[4] = this.s2b("'");
        rowVal[5] = this.s2b("");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("true");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("LONG VARBINARY");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("MEDIUMBLOB");
        rowVal[1] = Integer.toString(-4).getBytes();
        rowVal[2] = this.s2b("16777215");
        rowVal[3] = this.s2b("'");
        rowVal[4] = this.s2b("'");
        rowVal[5] = this.s2b("");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("true");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("MEDIUMBLOB");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("LONGBLOB");
        rowVal[1] = Integer.toString(-4).getBytes();
        rowVal[2] = Integer.toString(Integer.MAX_VALUE).getBytes();
        rowVal[3] = this.s2b("'");
        rowVal[4] = this.s2b("'");
        rowVal[5] = this.s2b("");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("true");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("LONGBLOB");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("BLOB");
        rowVal[1] = Integer.toString(-4).getBytes();
        rowVal[2] = this.s2b("65535");
        rowVal[3] = this.s2b("'");
        rowVal[4] = this.s2b("'");
        rowVal[5] = this.s2b("");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("true");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("BLOB");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("TINYBLOB");
        rowVal[1] = Integer.toString(-4).getBytes();
        rowVal[2] = this.s2b("255");
        rowVal[3] = this.s2b("'");
        rowVal[4] = this.s2b("'");
        rowVal[5] = this.s2b("");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("true");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("TINYBLOB");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("VARBINARY");
        rowVal[1] = Integer.toString(-3).getBytes();
        rowVal[2] = this.s2b("255");
        rowVal[3] = this.s2b("'");
        rowVal[4] = this.s2b("'");
        rowVal[5] = this.s2b("(M)");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("true");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("VARBINARY");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("BINARY");
        rowVal[1] = Integer.toString(-2).getBytes();
        rowVal[2] = this.s2b("255");
        rowVal[3] = this.s2b("'");
        rowVal[4] = this.s2b("'");
        rowVal[5] = this.s2b("(M)");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("true");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("BINARY");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("LONG VARCHAR");
        rowVal[1] = Integer.toString(-1).getBytes();
        rowVal[2] = this.s2b("16777215");
        rowVal[3] = this.s2b("'");
        rowVal[4] = this.s2b("'");
        rowVal[5] = this.s2b("");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("LONG VARCHAR");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("MEDIUMTEXT");
        rowVal[1] = Integer.toString(-1).getBytes();
        rowVal[2] = this.s2b("16777215");
        rowVal[3] = this.s2b("'");
        rowVal[4] = this.s2b("'");
        rowVal[5] = this.s2b("");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("MEDIUMTEXT");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("LONGTEXT");
        rowVal[1] = Integer.toString(-1).getBytes();
        rowVal[2] = Integer.toString(Integer.MAX_VALUE).getBytes();
        rowVal[3] = this.s2b("'");
        rowVal[4] = this.s2b("'");
        rowVal[5] = this.s2b("");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("LONGTEXT");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("TEXT");
        rowVal[1] = Integer.toString(-1).getBytes();
        rowVal[2] = this.s2b("65535");
        rowVal[3] = this.s2b("'");
        rowVal[4] = this.s2b("'");
        rowVal[5] = this.s2b("");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("TEXT");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("TINYTEXT");
        rowVal[1] = Integer.toString(-1).getBytes();
        rowVal[2] = this.s2b("255");
        rowVal[3] = this.s2b("'");
        rowVal[4] = this.s2b("'");
        rowVal[5] = this.s2b("");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("TINYTEXT");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("CHAR");
        rowVal[1] = Integer.toString(1).getBytes();
        rowVal[2] = this.s2b("255");
        rowVal[3] = this.s2b("'");
        rowVal[4] = this.s2b("'");
        rowVal[5] = this.s2b("(M)");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("CHAR");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        int decimalPrecision = 254;
        if (this.conn.versionMeetsMinimum(5, 0, 3)) {
            decimalPrecision = this.conn.versionMeetsMinimum(5, 0, 6) ? 65 : 64;
        }
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("NUMERIC");
        rowVal[1] = Integer.toString(2).getBytes();
        rowVal[2] = this.s2b(String.valueOf(decimalPrecision));
        rowVal[3] = this.s2b("");
        rowVal[4] = this.s2b("");
        rowVal[5] = this.s2b("[(M[,D])] [ZEROFILL]");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("true");
        rowVal[12] = this.s2b("NUMERIC");
        rowVal[13] = this.s2b("-308");
        rowVal[14] = this.s2b("308");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("DECIMAL");
        rowVal[1] = Integer.toString(3).getBytes();
        rowVal[2] = this.s2b(String.valueOf(decimalPrecision));
        rowVal[3] = this.s2b("");
        rowVal[4] = this.s2b("");
        rowVal[5] = this.s2b("[(M[,D])] [ZEROFILL]");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("true");
        rowVal[12] = this.s2b("DECIMAL");
        rowVal[13] = this.s2b("-308");
        rowVal[14] = this.s2b("308");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("INTEGER");
        rowVal[1] = Integer.toString(4).getBytes();
        rowVal[2] = this.s2b("10");
        rowVal[3] = this.s2b("");
        rowVal[4] = this.s2b("");
        rowVal[5] = this.s2b("[(M)] [UNSIGNED] [ZEROFILL]");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("true");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("true");
        rowVal[12] = this.s2b("INTEGER");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("INTEGER UNSIGNED");
        rowVal[1] = Integer.toString(4).getBytes();
        rowVal[2] = this.s2b("10");
        rowVal[3] = this.s2b("");
        rowVal[4] = this.s2b("");
        rowVal[5] = this.s2b("[(M)] [ZEROFILL]");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("true");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("true");
        rowVal[12] = this.s2b("INTEGER UNSIGNED");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("INT");
        rowVal[1] = Integer.toString(4).getBytes();
        rowVal[2] = this.s2b("10");
        rowVal[3] = this.s2b("");
        rowVal[4] = this.s2b("");
        rowVal[5] = this.s2b("[(M)] [UNSIGNED] [ZEROFILL]");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("true");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("true");
        rowVal[12] = this.s2b("INT");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("INT UNSIGNED");
        rowVal[1] = Integer.toString(4).getBytes();
        rowVal[2] = this.s2b("10");
        rowVal[3] = this.s2b("");
        rowVal[4] = this.s2b("");
        rowVal[5] = this.s2b("[(M)] [ZEROFILL]");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("true");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("true");
        rowVal[12] = this.s2b("INT UNSIGNED");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("MEDIUMINT");
        rowVal[1] = Integer.toString(4).getBytes();
        rowVal[2] = this.s2b("7");
        rowVal[3] = this.s2b("");
        rowVal[4] = this.s2b("");
        rowVal[5] = this.s2b("[(M)] [UNSIGNED] [ZEROFILL]");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("true");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("true");
        rowVal[12] = this.s2b("MEDIUMINT");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("MEDIUMINT UNSIGNED");
        rowVal[1] = Integer.toString(4).getBytes();
        rowVal[2] = this.s2b("8");
        rowVal[3] = this.s2b("");
        rowVal[4] = this.s2b("");
        rowVal[5] = this.s2b("[(M)] [ZEROFILL]");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("true");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("true");
        rowVal[12] = this.s2b("MEDIUMINT UNSIGNED");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("SMALLINT");
        rowVal[1] = Integer.toString(5).getBytes();
        rowVal[2] = this.s2b("5");
        rowVal[3] = this.s2b("");
        rowVal[4] = this.s2b("");
        rowVal[5] = this.s2b("[(M)] [UNSIGNED] [ZEROFILL]");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("true");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("true");
        rowVal[12] = this.s2b("SMALLINT");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("SMALLINT UNSIGNED");
        rowVal[1] = Integer.toString(5).getBytes();
        rowVal[2] = this.s2b("5");
        rowVal[3] = this.s2b("");
        rowVal[4] = this.s2b("");
        rowVal[5] = this.s2b("[(M)] [ZEROFILL]");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("true");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("true");
        rowVal[12] = this.s2b("SMALLINT UNSIGNED");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("FLOAT");
        rowVal[1] = Integer.toString(7).getBytes();
        rowVal[2] = this.s2b("10");
        rowVal[3] = this.s2b("");
        rowVal[4] = this.s2b("");
        rowVal[5] = this.s2b("[(M,D)] [ZEROFILL]");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("true");
        rowVal[12] = this.s2b("FLOAT");
        rowVal[13] = this.s2b("-38");
        rowVal[14] = this.s2b("38");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("DOUBLE");
        rowVal[1] = Integer.toString(8).getBytes();
        rowVal[2] = this.s2b("17");
        rowVal[3] = this.s2b("");
        rowVal[4] = this.s2b("");
        rowVal[5] = this.s2b("[(M,D)] [ZEROFILL]");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("true");
        rowVal[12] = this.s2b("DOUBLE");
        rowVal[13] = this.s2b("-308");
        rowVal[14] = this.s2b("308");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("DOUBLE PRECISION");
        rowVal[1] = Integer.toString(8).getBytes();
        rowVal[2] = this.s2b("17");
        rowVal[3] = this.s2b("");
        rowVal[4] = this.s2b("");
        rowVal[5] = this.s2b("[(M,D)] [ZEROFILL]");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("true");
        rowVal[12] = this.s2b("DOUBLE PRECISION");
        rowVal[13] = this.s2b("-308");
        rowVal[14] = this.s2b("308");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("REAL");
        rowVal[1] = Integer.toString(8).getBytes();
        rowVal[2] = this.s2b("17");
        rowVal[3] = this.s2b("");
        rowVal[4] = this.s2b("");
        rowVal[5] = this.s2b("[(M,D)] [ZEROFILL]");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("true");
        rowVal[12] = this.s2b("REAL");
        rowVal[13] = this.s2b("-308");
        rowVal[14] = this.s2b("308");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("VARCHAR");
        rowVal[1] = Integer.toString(12).getBytes();
        rowVal[2] = this.s2b("255");
        rowVal[3] = this.s2b("'");
        rowVal[4] = this.s2b("'");
        rowVal[5] = this.s2b("(M)");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("VARCHAR");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("ENUM");
        rowVal[1] = Integer.toString(12).getBytes();
        rowVal[2] = this.s2b("65535");
        rowVal[3] = this.s2b("'");
        rowVal[4] = this.s2b("'");
        rowVal[5] = this.s2b("");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("ENUM");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("SET");
        rowVal[1] = Integer.toString(12).getBytes();
        rowVal[2] = this.s2b("64");
        rowVal[3] = this.s2b("'");
        rowVal[4] = this.s2b("'");
        rowVal[5] = this.s2b("");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("SET");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("DATE");
        rowVal[1] = Integer.toString(91).getBytes();
        rowVal[2] = this.s2b("0");
        rowVal[3] = this.s2b("'");
        rowVal[4] = this.s2b("'");
        rowVal[5] = this.s2b("");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("DATE");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("TIME");
        rowVal[1] = Integer.toString(92).getBytes();
        rowVal[2] = this.s2b("0");
        rowVal[3] = this.s2b("'");
        rowVal[4] = this.s2b("'");
        rowVal[5] = this.s2b("");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("TIME");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("DATETIME");
        rowVal[1] = Integer.toString(93).getBytes();
        rowVal[2] = this.s2b("0");
        rowVal[3] = this.s2b("'");
        rowVal[4] = this.s2b("'");
        rowVal[5] = this.s2b("");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("DATETIME");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        rowVal = new byte[18][];
        rowVal[0] = this.s2b("TIMESTAMP");
        rowVal[1] = Integer.toString(93).getBytes();
        rowVal[2] = this.s2b("0");
        rowVal[3] = this.s2b("'");
        rowVal[4] = this.s2b("'");
        rowVal[5] = this.s2b("[(M)]");
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("false");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b("false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b("TIMESTAMP");
        rowVal[13] = this.s2b("0");
        rowVal[14] = this.s2b("0");
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        tuples.add((byte[][])rowVal);
        return this.buildResultSet(fields, tuples);
    }

    public java.sql.ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types) throws SQLException {
        Field[] fields = new Field[]{new Field("", "TYPE_CAT", 12, 32), new Field("", "TYPE_SCHEM", 12, 32), new Field("", "TYPE_NAME", 12, 32), new Field("", "CLASS_NAME", 12, 32), new Field("", "DATA_TYPE", 12, 32), new Field("", "REMARKS", 12, 32)};
        ArrayList tuples = new ArrayList();
        return this.buildResultSet(fields, tuples);
    }

    public String getURL() throws SQLException {
        return this.conn.getURL();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getUserName() throws SQLException {
        if (this.conn.getUseHostsInPrivileges()) {
            Exception ex22;
            String string;
            Statement stmt;
            block12: {
                stmt = null;
                java.sql.ResultSet rs = null;
                try {
                    stmt = this.conn.createStatement();
                    stmt.setEscapeProcessing(false);
                    rs = stmt.executeQuery("SELECT USER()");
                    rs.next();
                    string = rs.getString(1);
                    Object var5_4 = null;
                    if (rs == null) break block12;
                }
                catch (Throwable throwable) {
                    Exception ex22;
                    Object var5_5 = null;
                    if (rs != null) {
                        try {
                            rs.close();
                        }
                        catch (Exception ex22) {
                            AssertionFailedException.shouldNotHappen(ex22);
                        }
                        rs = null;
                    }
                    if (stmt != null) {
                        try {
                            stmt.close();
                        }
                        catch (Exception ex22) {
                            AssertionFailedException.shouldNotHappen(ex22);
                        }
                        stmt = null;
                    }
                    throw throwable;
                }
                try {
                    rs.close();
                }
                catch (Exception ex22) {
                    AssertionFailedException.shouldNotHappen(ex22);
                }
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (Exception ex22) {
                    AssertionFailedException.shouldNotHappen(ex22);
                }
                stmt = null;
            }
            return string;
        }
        return this.conn.getUser();
    }

    public java.sql.ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
        Field[] fields = new Field[]{new Field("", "SCOPE", 5, 5), new Field("", "COLUMN_NAME", 1, 32), new Field("", "DATA_TYPE", 5, 5), new Field("", "TYPE_NAME", 1, 16), new Field("", "COLUMN_SIZE", 1, 16), new Field("", "BUFFER_LENGTH", 1, 16), new Field("", "DECIMAL_DIGITS", 1, 16), new Field("", "PSEUDO_COLUMN", 5, 5)};
        return this.buildResultSet(fields, new ArrayList());
    }

    public boolean insertsAreDetected(int type) throws SQLException {
        return false;
    }

    public boolean isCatalogAtStart() throws SQLException {
        return true;
    }

    public boolean isReadOnly() throws SQLException {
        return false;
    }

    public boolean locatorsUpdateCopy() throws SQLException {
        return !this.conn.getEmulateLocators();
    }

    public boolean nullPlusNonNullIsNull() throws SQLException {
        return true;
    }

    public boolean nullsAreSortedAtEnd() throws SQLException {
        return false;
    }

    public boolean nullsAreSortedAtStart() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 0, 2) && !this.conn.versionMeetsMinimum(4, 0, 11);
    }

    public boolean nullsAreSortedHigh() throws SQLException {
        return false;
    }

    public boolean nullsAreSortedLow() throws SQLException {
        return !this.nullsAreSortedHigh();
    }

    public boolean othersDeletesAreVisible(int type) throws SQLException {
        return false;
    }

    public boolean othersInsertsAreVisible(int type) throws SQLException {
        return false;
    }

    public boolean othersUpdatesAreVisible(int type) throws SQLException {
        return false;
    }

    public boolean ownDeletesAreVisible(int type) throws SQLException {
        return false;
    }

    public boolean ownInsertsAreVisible(int type) throws SQLException {
        return false;
    }

    public boolean ownUpdatesAreVisible(int type) throws SQLException {
        return false;
    }

    private LocalAndReferencedColumns parseTableStatusIntoLocalAndReferencedColumns(String keysComment) throws SQLException {
        String columnsDelimitter = ",";
        char quoteChar = this.quotedId.length() == 0 ? (char)'\u0000' : this.quotedId.charAt(0);
        int indexOfOpenParenLocalColumns = StringUtils.indexOfIgnoreCaseRespectQuotes(0, keysComment, "(", quoteChar, true);
        if (indexOfOpenParenLocalColumns == -1) {
            throw SQLError.createSQLException("Error parsing foreign keys definition, couldn't find start of local columns list.", "S1000");
        }
        String constraintName = this.removeQuotedId(keysComment.substring(0, indexOfOpenParenLocalColumns).trim());
        String keysCommentTrimmed = (keysComment = keysComment.substring(indexOfOpenParenLocalColumns, keysComment.length())).trim();
        int indexOfCloseParenLocalColumns = StringUtils.indexOfIgnoreCaseRespectQuotes(0, keysCommentTrimmed, ")", quoteChar, true);
        if (indexOfCloseParenLocalColumns == -1) {
            throw SQLError.createSQLException("Error parsing foreign keys definition, couldn't find end of local columns list.", "S1000");
        }
        String localColumnNamesString = keysCommentTrimmed.substring(1, indexOfCloseParenLocalColumns);
        int indexOfRefer = StringUtils.indexOfIgnoreCaseRespectQuotes(0, keysCommentTrimmed, "REFER ", this.quotedId.charAt(0), true);
        if (indexOfRefer == -1) {
            throw SQLError.createSQLException("Error parsing foreign keys definition, couldn't find start of referenced tables list.", "S1000");
        }
        int indexOfOpenParenReferCol = StringUtils.indexOfIgnoreCaseRespectQuotes(indexOfRefer, keysCommentTrimmed, "(", quoteChar, false);
        if (indexOfOpenParenReferCol == -1) {
            throw SQLError.createSQLException("Error parsing foreign keys definition, couldn't find start of referenced columns list.", "S1000");
        }
        String referCatalogTableString = keysCommentTrimmed.substring(indexOfRefer + "REFER ".length(), indexOfOpenParenReferCol);
        int indexOfSlash = StringUtils.indexOfIgnoreCaseRespectQuotes(0, referCatalogTableString, "/", this.quotedId.charAt(0), false);
        if (indexOfSlash == -1) {
            throw SQLError.createSQLException("Error parsing foreign keys definition, couldn't find name of referenced catalog.", "S1000");
        }
        String referCatalog = this.removeQuotedId(referCatalogTableString.substring(0, indexOfSlash));
        String referTable = this.removeQuotedId(referCatalogTableString.substring(indexOfSlash + 1).trim());
        int indexOfCloseParenRefer = StringUtils.indexOfIgnoreCaseRespectQuotes(indexOfOpenParenReferCol, keysCommentTrimmed, ")", quoteChar, true);
        if (indexOfCloseParenRefer == -1) {
            throw SQLError.createSQLException("Error parsing foreign keys definition, couldn't find end of referenced columns list.", "S1000");
        }
        String referColumnNamesString = keysCommentTrimmed.substring(indexOfOpenParenReferCol + 1, indexOfCloseParenRefer);
        List referColumnsList = StringUtils.split(referColumnNamesString, columnsDelimitter, this.quotedId, this.quotedId, false);
        List localColumnsList = StringUtils.split(localColumnNamesString, columnsDelimitter, this.quotedId, this.quotedId, false);
        return new LocalAndReferencedColumns(localColumnsList, referColumnsList, constraintName, referCatalog, referTable);
    }

    private String removeQuotedId(String s) {
        if (s == null) {
            return null;
        }
        if (this.quotedId.equals("")) {
            return s;
        }
        s = s.trim();
        int frontOffset = 0;
        int backOffset = s.length();
        int quoteLength = this.quotedId.length();
        if (s.startsWith(this.quotedId)) {
            frontOffset = quoteLength;
        }
        if (s.endsWith(this.quotedId)) {
            backOffset -= quoteLength;
        }
        return s.substring(frontOffset, backOffset);
    }

    private byte[] s2b(String s) throws SQLException {
        return StringUtils.s2b(s, this.conn);
    }

    public boolean storesLowerCaseIdentifiers() throws SQLException {
        return this.conn.lowerCaseTableNames();
    }

    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        return this.conn.lowerCaseTableNames();
    }

    public boolean storesMixedCaseIdentifiers() throws SQLException {
        return !this.conn.lowerCaseTableNames();
    }

    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        return !this.conn.lowerCaseTableNames();
    }

    public boolean storesUpperCaseIdentifiers() throws SQLException {
        return false;
    }

    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        return true;
    }

    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        return true;
    }

    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        return true;
    }

    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        return true;
    }

    public boolean supportsANSI92FullSQL() throws SQLException {
        return false;
    }

    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        return false;
    }

    public boolean supportsBatchUpdates() throws SQLException {
        return true;
    }

    public boolean supportsCatalogsInDataManipulation() throws SQLException {
        return this.conn.versionMeetsMinimum(3, 22, 0);
    }

    public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
        return this.conn.versionMeetsMinimum(3, 22, 0);
    }

    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
        return this.conn.versionMeetsMinimum(3, 22, 0);
    }

    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        return this.conn.versionMeetsMinimum(3, 22, 0);
    }

    public boolean supportsCatalogsInTableDefinitions() throws SQLException {
        return this.conn.versionMeetsMinimum(3, 22, 0);
    }

    public boolean supportsColumnAliasing() throws SQLException {
        return true;
    }

    public boolean supportsConvert() throws SQLException {
        return false;
    }

    public boolean supportsConvert(int fromType, int toType) throws SQLException {
        switch (fromType) {
            case -4: 
            case -3: 
            case -2: 
            case -1: 
            case 1: 
            case 12: {
                switch (toType) {
                    case -6: 
                    case -5: 
                    case -4: 
                    case -3: 
                    case -2: 
                    case -1: 
                    case 1: 
                    case 2: 
                    case 3: 
                    case 4: 
                    case 5: 
                    case 6: 
                    case 7: 
                    case 8: 
                    case 12: 
                    case 91: 
                    case 92: 
                    case 93: 
                    case 1111: {
                        return true;
                    }
                }
                return false;
            }
            case -7: {
                return false;
            }
            case -6: 
            case -5: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: {
                switch (toType) {
                    case -6: 
                    case -5: 
                    case -4: 
                    case -3: 
                    case -2: 
                    case -1: 
                    case 1: 
                    case 2: 
                    case 3: 
                    case 4: 
                    case 5: 
                    case 6: 
                    case 7: 
                    case 8: 
                    case 12: {
                        return true;
                    }
                }
                return false;
            }
            case 0: {
                return false;
            }
            case 1111: {
                switch (toType) {
                    case -4: 
                    case -3: 
                    case -2: 
                    case -1: 
                    case 1: 
                    case 12: {
                        return true;
                    }
                }
                return false;
            }
            case 91: {
                switch (toType) {
                    case -4: 
                    case -3: 
                    case -2: 
                    case -1: 
                    case 1: 
                    case 12: {
                        return true;
                    }
                }
                return false;
            }
            case 92: {
                switch (toType) {
                    case -4: 
                    case -3: 
                    case -2: 
                    case -1: 
                    case 1: 
                    case 12: {
                        return true;
                    }
                }
                return false;
            }
            case 93: {
                switch (toType) {
                    case -4: 
                    case -3: 
                    case -2: 
                    case -1: 
                    case 1: 
                    case 12: 
                    case 91: 
                    case 92: {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    public boolean supportsCoreSQLGrammar() throws SQLException {
        return true;
    }

    public boolean supportsCorrelatedSubqueries() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 1, 0);
    }

    public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
        return false;
    }

    public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
        return false;
    }

    public boolean supportsDifferentTableCorrelationNames() throws SQLException {
        return true;
    }

    public boolean supportsExpressionsInOrderBy() throws SQLException {
        return true;
    }

    public boolean supportsExtendedSQLGrammar() throws SQLException {
        return false;
    }

    public boolean supportsFullOuterJoins() throws SQLException {
        return false;
    }

    public boolean supportsGetGeneratedKeys() {
        return true;
    }

    public boolean supportsGroupBy() throws SQLException {
        return true;
    }

    public boolean supportsGroupByBeyondSelect() throws SQLException {
        return true;
    }

    public boolean supportsGroupByUnrelated() throws SQLException {
        return true;
    }

    public boolean supportsIntegrityEnhancementFacility() throws SQLException {
        return this.conn.getOverrideSupportsIntegrityEnhancementFacility();
    }

    public boolean supportsLikeEscapeClause() throws SQLException {
        return true;
    }

    public boolean supportsLimitedOuterJoins() throws SQLException {
        return true;
    }

    public boolean supportsMinimumSQLGrammar() throws SQLException {
        return true;
    }

    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        return !this.conn.lowerCaseTableNames();
    }

    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
        return !this.conn.lowerCaseTableNames();
    }

    public boolean supportsMultipleOpenResults() throws SQLException {
        return true;
    }

    public boolean supportsMultipleResultSets() throws SQLException {
        return false;
    }

    public boolean supportsMultipleTransactions() throws SQLException {
        return true;
    }

    public boolean supportsNamedParameters() throws SQLException {
        return false;
    }

    public boolean supportsNonNullableColumns() throws SQLException {
        return true;
    }

    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        return false;
    }

    public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
        return false;
    }

    public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
        return false;
    }

    public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
        return false;
    }

    public boolean supportsOrderByUnrelated() throws SQLException {
        return false;
    }

    public boolean supportsOuterJoins() throws SQLException {
        return true;
    }

    public boolean supportsPositionedDelete() throws SQLException {
        return false;
    }

    public boolean supportsPositionedUpdate() throws SQLException {
        return false;
    }

    public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
        switch (type) {
            case 1004: {
                if (concurrency == 1007 || concurrency == 1008) {
                    return true;
                }
                throw SQLError.createSQLException("Illegal arguments to supportsResultSetConcurrency()", "S1009");
            }
            case 1003: {
                if (concurrency == 1007 || concurrency == 1008) {
                    return true;
                }
                throw SQLError.createSQLException("Illegal arguments to supportsResultSetConcurrency()", "S1009");
            }
            case 1005: {
                return false;
            }
        }
        throw SQLError.createSQLException("Illegal arguments to supportsResultSetConcurrency()", "S1009");
    }

    public boolean supportsResultSetHoldability(int holdability) throws SQLException {
        return holdability == 1;
    }

    public boolean supportsResultSetType(int type) throws SQLException {
        return type == 1004;
    }

    public boolean supportsSavepoints() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 0, 14) || this.conn.versionMeetsMinimum(4, 1, 1);
    }

    public boolean supportsSchemasInDataManipulation() throws SQLException {
        return false;
    }

    public boolean supportsSchemasInIndexDefinitions() throws SQLException {
        return false;
    }

    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
        return false;
    }

    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        return false;
    }

    public boolean supportsSchemasInTableDefinitions() throws SQLException {
        return false;
    }

    public boolean supportsSelectForUpdate() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 0, 0);
    }

    public boolean supportsStatementPooling() throws SQLException {
        return false;
    }

    public boolean supportsStoredProcedures() throws SQLException {
        return this.conn.versionMeetsMinimum(5, 0, 0);
    }

    public boolean supportsSubqueriesInComparisons() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 1, 0);
    }

    public boolean supportsSubqueriesInExists() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 1, 0);
    }

    public boolean supportsSubqueriesInIns() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 1, 0);
    }

    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 1, 0);
    }

    public boolean supportsTableCorrelationNames() throws SQLException {
        return true;
    }

    public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
        if (this.conn.supportsIsolationLevel()) {
            switch (level) {
                case 1: 
                case 2: 
                case 4: 
                case 8: {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public boolean supportsTransactions() throws SQLException {
        return this.conn.supportsTransactions();
    }

    public boolean supportsUnion() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 0, 0);
    }

    public boolean supportsUnionAll() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 0, 0);
    }

    public boolean updatesAreDetected(int type) throws SQLException {
        return false;
    }

    public boolean usesLocalFilePerTable() throws SQLException {
        return false;
    }

    public boolean usesLocalFiles() throws SQLException {
        return false;
    }

    static {
        TABLE_AS_BYTES = "TABLE".getBytes();
        VIEW_AS_BYTES = "VIEW".getBytes();
        String[] allMySQLKeywords = new String[]{"ACCESSIBLE", "ADD", "ALL", "ALTER", "ANALYZE", "AND", "AS", "ASC", "ASENSITIVE", "BEFORE", "BETWEEN", "BIGINT", "BINARY", "BLOB", "BOTH", "BY", "CALL", "CASCADE", "CASE", "CHANGE", "CHAR", "CHARACTER", "CHECK", "COLLATE", "COLUMN", "CONDITION", "CONNECTION", "CONSTRAINT", "CONTINUE", "CONVERT", "CREATE", "CROSS", "CURRENT_DATE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_USER", "CURSOR", "DATABASE", "DATABASES", "DAY_HOUR", "DAY_MICROSECOND", "DAY_MINUTE", "DAY_SECOND", "DEC", "DECIMAL", "DECLARE", "DEFAULT", "DELAYED", "DELETE", "DESC", "DESCRIBE", "DETERMINISTIC", "DISTINCT", "DISTINCTROW", "DIV", "DOUBLE", "DROP", "DUAL", "EACH", "ELSE", "ELSEIF", "ENCLOSED", "ESCAPED", "EXISTS", "EXIT", "EXPLAIN", "FALSE", "FETCH", "FLOAT", "FLOAT4", "FLOAT8", "FOR", "FORCE", "FOREIGN", "FROM", "FULLTEXT", "GRANT", "GROUP", "HAVING", "HIGH_PRIORITY", "HOUR_MICROSECOND", "HOUR_MINUTE", "HOUR_SECOND", "IF", "IGNORE", "IN", "INDEX", "INFILE", "INNER", "INOUT", "INSENSITIVE", "INSERT", "INT", "INT1", "INT2", "INT3", "INT4", "INT8", "INTEGER", "INTERVAL", "INTO", "IS", "ITERATE", "JOIN", "KEY", "KEYS", "KILL", "LEADING", "LEAVE", "LEFT", "LIKE", "LIMIT", "LINEAR", "LINES", "LOAD", "LOCALTIME", "LOCALTIMESTAMP", "LOCK", "LONG", "LONGBLOB", "LONGTEXT", "LOOP", "LOW_PRIORITY", "MATCH", "MEDIUMBLOB", "MEDIUMINT", "MEDIUMTEXT", "MIDDLEINT", "MINUTE_MICROSECOND", "MINUTE_SECOND", "MOD", "MODIFIES", "NATURAL", "NOT", "NO_WRITE_TO_BINLOG", "NULL", "NUMERIC", "ON", "OPTIMIZE", "OPTION", "OPTIONALLY", "OR", "ORDER", "OUT", "OUTER", "OUTFILE", "PRECISION", "PRIMARY", "PROCEDURE", "PURGE", "RANGE", "READ", "READS", "READ_ONLY", "READ_WRITE", "REAL", "REFERENCES", "REGEXP", "RELEASE", "RENAME", "REPEAT", "REPLACE", "REQUIRE", "RESTRICT", "RETURN", "REVOKE", "RIGHT", "RLIKE", "SCHEMA", "SCHEMAS", "SECOND_MICROSECOND", "SELECT", "SENSITIVE", "SEPARATOR", "SET", "SHOW", "SMALLINT", "SPATIAL", "SPECIFIC", "SQL", "SQLEXCEPTION", "SQLSTATE", "SQLWARNING", "SQL_BIG_RESULT", "SQL_CALC_FOUND_ROWS", "SQL_SMALL_RESULT", "SSL", "STARTING", "STRAIGHT_JOIN", "TABLE", "TERMINATED", "THEN", "TINYBLOB", "TINYINT", "TINYTEXT", "TO", "TRAILING", "TRIGGER", "TRUE", "UNDO", "UNION", "UNIQUE", "UNLOCK", "UNSIGNED", "UPDATE", "USAGE", "USE", "USING", "UTC_DATE", "UTC_TIME", "UTC_TIMESTAMP", "VALUES", "VARBINARY", "VARCHAR", "VARCHARACTER", "VARYING", "WHEN", "WHERE", "WHILE", "WITH", "WRITE", "X509", "XOR", "YEAR_MONTH", "ZEROFILL"};
        String[] sql92Keywords = new String[]{"ABSOLUTE", "EXEC", "OVERLAPS", "ACTION", "EXECUTE", "PAD", "ADA", "EXISTS", "PARTIAL", "ADD", "EXTERNAL", "PASCAL", "ALL", "EXTRACT", "POSITION", "ALLOCATE", "FALSE", "PRECISION", "ALTER", "FETCH", "PREPARE", "AND", "FIRST", "PRESERVE", "ANY", "FLOAT", "PRIMARY", "ARE", "FOR", "PRIOR", "AS", "FOREIGN", "PRIVILEGES", "ASC", "FORTRAN", "PROCEDURE", "ASSERTION", "FOUND", "PUBLIC", "AT", "FROM", "READ", "AUTHORIZATION", "FULL", "REAL", "AVG", "GET", "REFERENCES", "BEGIN", "GLOBAL", "RELATIVE", "BETWEEN", "GO", "RESTRICT", "BIT", "GOTO", "REVOKE", "BIT_LENGTH", "GRANT", "RIGHT", "BOTH", "GROUP", "ROLLBACK", "BY", "HAVING", "ROWS", "CASCADE", "HOUR", "SCHEMA", "CASCADED", "IDENTITY", "SCROLL", "CASE", "IMMEDIATE", "SECOND", "CAST", "IN", "SECTION", "CATALOG", "INCLUDE", "SELECT", "CHAR", "INDEX", "SESSION", "CHAR_LENGTH", "INDICATOR", "SESSION_USER", "CHARACTER", "INITIALLY", "SET", "CHARACTER_LENGTH", "INNER", "SIZE", "CHECK", "INPUT", "SMALLINT", "CLOSE", "INSENSITIVE", "SOME", "COALESCE", "INSERT", "SPACE", "COLLATE", "INT", "SQL", "COLLATION", "INTEGER", "SQLCA", "COLUMN", "INTERSECT", "SQLCODE", "COMMIT", "INTERVAL", "SQLERROR", "CONNECT", "INTO", "SQLSTATE", "CONNECTION", "IS", "SQLWARNING", "CONSTRAINT", "ISOLATION", "SUBSTRING", "CONSTRAINTS", "JOIN", "SUM", "CONTINUE", "KEY", "SYSTEM_USER", "CONVERT", "LANGUAGE", "TABLE", "CORRESPONDING", "LAST", "TEMPORARY", "COUNT", "LEADING", "THEN", "CREATE", "LEFT", "TIME", "CROSS", "LEVEL", "TIMESTAMP", "CURRENT", "LIKE", "TIMEZONE_HOUR", "CURRENT_DATE", "LOCAL", "TIMEZONE_MINUTE", "CURRENT_TIME", "LOWER", "TO", "CURRENT_TIMESTAMP", "MATCH", "TRAILING", "CURRENT_USER", "MAX", "TRANSACTION", "CURSOR", "MIN", "TRANSLATE", "DATE", "MINUTE", "TRANSLATION", "DAY", "MODULE", "TRIM", "DEALLOCATE", "MONTH", "TRUE", "DEC", "NAMES", "UNION", "DECIMAL", "NATIONAL", "UNIQUE", "DECLARE", "NATURAL", "UNKNOWN", "DEFAULT", "NCHAR", "UPDATE", "DEFERRABLE", "NEXT", "UPPER", "DEFERRED", "NO", "USAGE", "DELETE", "NONE", "USER", "DESC", "NOT", "USING", "DESCRIBE", "NULL", "VALUE", "DESCRIPTOR", "NULLIF", "VALUES", "DIAGNOSTICS", "NUMERIC", "VARCHAR", "DISCONNECT", "OCTET_LENGTH", "VARYING", "DISTINCT", "OF", "VIEW", "DOMAIN", "ON", "WHEN", "DOUBLE", "ONLY", "WHENEVER", "DROP", "OPEN", "WHERE", "ELSE", "OPTION", "WITH", "END", "OR", "WORK", "END-EXEC", "ORDER", "WRITE", "ESCAPE", "OUTER", "YEAR", "EXCEPT", "OUTPUT", "ZONE", "EXCEPTION"};
        TreeMap mySQLKeywordMap = new TreeMap();
        for (int i = 0; i < allMySQLKeywords.length; ++i) {
            mySQLKeywordMap.put(allMySQLKeywords[i], null);
        }
        HashMap sql92KeywordMap = new HashMap(sql92Keywords.length);
        for (int i = 0; i < sql92Keywords.length; ++i) {
            sql92KeywordMap.put(sql92Keywords[i], null);
        }
        Iterator it = sql92KeywordMap.keySet().iterator();
        while (it.hasNext()) {
            mySQLKeywordMap.remove(it.next());
        }
        StringBuffer keywordBuf = new StringBuffer();
        it = mySQLKeywordMap.keySet().iterator();
        if (it.hasNext()) {
            keywordBuf.append(it.next().toString());
        }
        while (it.hasNext()) {
            keywordBuf.append(",");
            keywordBuf.append(it.next().toString());
        }
        mysqlKeywordsThatArentSQL92 = keywordBuf.toString();
    }

    class TypeDescriptor {
        int bufferLength;
        int charOctetLength;
        Integer columnSize;
        short dataType;
        Integer decimalDigits;
        String isNullable;
        int nullability;
        int numPrecRadix = 10;
        String typeName;

        TypeDescriptor(String typeInfo, String nullabilityInfo) throws SQLException {
            String mysqlType = "";
            String fullMysqlType = null;
            mysqlType = typeInfo.indexOf("(") != -1 ? typeInfo.substring(0, typeInfo.indexOf("(")) : typeInfo;
            int indexOfUnsignedInMysqlType = StringUtils.indexOfIgnoreCase(mysqlType, "unsigned");
            if (indexOfUnsignedInMysqlType != -1) {
                mysqlType = mysqlType.substring(0, indexOfUnsignedInMysqlType - 1);
            }
            boolean isUnsigned = false;
            if (StringUtils.indexOfIgnoreCase(typeInfo, "unsigned") != -1) {
                fullMysqlType = mysqlType + " unsigned";
                isUnsigned = true;
            } else {
                fullMysqlType = mysqlType;
            }
            if (DatabaseMetaData.this.conn.getCapitalizeTypeNames()) {
                fullMysqlType = fullMysqlType.toUpperCase(Locale.ENGLISH);
            }
            this.dataType = (short)MysqlDefs.mysqlToJavaType(mysqlType);
            this.typeName = fullMysqlType;
            if (typeInfo != null) {
                if (StringUtils.startsWithIgnoreCase(typeInfo, "enum")) {
                    String temp = typeInfo.substring(typeInfo.indexOf("("), typeInfo.lastIndexOf(")"));
                    StringTokenizer tokenizer = new StringTokenizer(temp, ",");
                    int maxLength = 0;
                    while (tokenizer.hasMoreTokens()) {
                        maxLength = Math.max(maxLength, tokenizer.nextToken().length() - 2);
                    }
                    this.columnSize = new Integer(maxLength);
                    this.decimalDigits = null;
                } else if (StringUtils.startsWithIgnoreCase(typeInfo, "set")) {
                    String temp = typeInfo.substring(typeInfo.indexOf("("), typeInfo.lastIndexOf(")"));
                    StringTokenizer tokenizer = new StringTokenizer(temp, ",");
                    int maxLength = 0;
                    while (tokenizer.hasMoreTokens()) {
                        String setMember = tokenizer.nextToken().trim();
                        if (setMember.startsWith("'") && setMember.endsWith("'")) {
                            maxLength += setMember.length() - 2;
                            continue;
                        }
                        maxLength += setMember.length();
                    }
                    this.columnSize = new Integer(maxLength);
                    this.decimalDigits = null;
                } else if (typeInfo.indexOf(",") != -1) {
                    this.columnSize = new Integer(typeInfo.substring(typeInfo.indexOf("(") + 1, typeInfo.indexOf(",")).trim());
                    this.decimalDigits = new Integer(typeInfo.substring(typeInfo.indexOf(",") + 1, typeInfo.indexOf(")")).trim());
                } else {
                    this.columnSize = null;
                    this.decimalDigits = null;
                    if ((StringUtils.indexOfIgnoreCase(typeInfo, "char") != -1 || StringUtils.indexOfIgnoreCase(typeInfo, "text") != -1 || StringUtils.indexOfIgnoreCase(typeInfo, "blob") != -1 || StringUtils.indexOfIgnoreCase(typeInfo, "binary") != -1 || StringUtils.indexOfIgnoreCase(typeInfo, "bit") != -1) && typeInfo.indexOf("(") != -1) {
                        int endParenIndex = typeInfo.indexOf(")");
                        if (endParenIndex == -1) {
                            endParenIndex = typeInfo.length();
                        }
                        this.columnSize = new Integer(typeInfo.substring(typeInfo.indexOf("(") + 1, endParenIndex).trim());
                        if (DatabaseMetaData.this.conn.getTinyInt1isBit() && this.columnSize == 1 && StringUtils.startsWithIgnoreCase(typeInfo, 0, "tinyint")) {
                            if (DatabaseMetaData.this.conn.getTransformedBitIsBoolean()) {
                                this.dataType = (short)16;
                                this.typeName = "BOOLEAN";
                            } else {
                                this.dataType = (short)-7;
                                this.typeName = "BIT";
                            }
                        }
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "tinyint")) {
                        if (DatabaseMetaData.this.conn.getTinyInt1isBit() && typeInfo.indexOf("(1)") != -1) {
                            if (DatabaseMetaData.this.conn.getTransformedBitIsBoolean()) {
                                this.dataType = (short)16;
                                this.typeName = "BOOLEAN";
                            } else {
                                this.dataType = (short)-7;
                                this.typeName = "BIT";
                            }
                        } else {
                            this.columnSize = new Integer(3);
                            this.decimalDigits = new Integer(0);
                        }
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "smallint")) {
                        this.columnSize = new Integer(5);
                        this.decimalDigits = new Integer(0);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "mediumint")) {
                        this.columnSize = new Integer(isUnsigned ? 8 : 7);
                        this.decimalDigits = new Integer(0);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "int")) {
                        this.columnSize = new Integer(10);
                        this.decimalDigits = new Integer(0);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "integer")) {
                        this.columnSize = new Integer(10);
                        this.decimalDigits = new Integer(0);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "bigint")) {
                        this.columnSize = new Integer(isUnsigned ? 20 : 19);
                        this.decimalDigits = new Integer(0);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "int24")) {
                        this.columnSize = new Integer(19);
                        this.decimalDigits = new Integer(0);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "real")) {
                        this.columnSize = new Integer(12);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "float")) {
                        this.columnSize = new Integer(12);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "decimal")) {
                        this.columnSize = new Integer(12);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "numeric")) {
                        this.columnSize = new Integer(12);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "double")) {
                        this.columnSize = new Integer(22);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "char")) {
                        this.columnSize = new Integer(1);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "varchar")) {
                        this.columnSize = new Integer(255);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "date")) {
                        this.columnSize = null;
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "time")) {
                        this.columnSize = null;
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "timestamp")) {
                        this.columnSize = null;
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "datetime")) {
                        this.columnSize = null;
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "tinyblob")) {
                        this.columnSize = new Integer(255);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "blob")) {
                        this.columnSize = new Integer(65535);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "mediumblob")) {
                        this.columnSize = new Integer(0xFFFFFF);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "longblob")) {
                        this.columnSize = new Integer(Integer.MAX_VALUE);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "tinytext")) {
                        this.columnSize = new Integer(255);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "text")) {
                        this.columnSize = new Integer(65535);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "mediumtext")) {
                        this.columnSize = new Integer(0xFFFFFF);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "longtext")) {
                        this.columnSize = new Integer(Integer.MAX_VALUE);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "enum")) {
                        this.columnSize = new Integer(255);
                    } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "set")) {
                        this.columnSize = new Integer(255);
                    }
                }
            } else {
                this.decimalDigits = null;
                this.columnSize = null;
            }
            this.bufferLength = MysqlIO.getMaxBuf();
            this.numPrecRadix = 10;
            if (nullabilityInfo != null) {
                if (nullabilityInfo.equals("YES")) {
                    this.nullability = 1;
                    this.isNullable = "YES";
                } else {
                    this.nullability = 0;
                    this.isNullable = "NO";
                }
            } else {
                this.nullability = 0;
                this.isNullable = "NO";
            }
        }
    }

    protected class SingleStringIterator
    extends IteratorWithCleanup {
        boolean onFirst = true;
        String value;

        SingleStringIterator(String s) {
            this.value = s;
        }

        void close() throws SQLException {
        }

        boolean hasNext() throws SQLException {
            return this.onFirst;
        }

        Object next() throws SQLException {
            this.onFirst = false;
            return this.value;
        }
    }

    protected class ResultSetIterator
    extends IteratorWithCleanup {
        int colIndex;
        java.sql.ResultSet resultSet;

        ResultSetIterator(java.sql.ResultSet rs, int index) {
            this.resultSet = rs;
            this.colIndex = index;
        }

        void close() throws SQLException {
            this.resultSet.close();
        }

        boolean hasNext() throws SQLException {
            return this.resultSet.next();
        }

        Object next() throws SQLException {
            return this.resultSet.getObject(this.colIndex);
        }
    }

    class LocalAndReferencedColumns {
        String constraintName;
        List localColumnsList;
        String referencedCatalog;
        List referencedColumnsList;
        String referencedTable;

        LocalAndReferencedColumns(List localColumns, List refColumns, String constName, String refCatalog, String refTable) {
            this.localColumnsList = localColumns;
            this.referencedColumnsList = refColumns;
            this.constraintName = constName;
            this.referencedTable = refTable;
            this.referencedCatalog = refCatalog;
        }
    }

    protected abstract class IteratorWithCleanup {
        protected IteratorWithCleanup() {
        }

        abstract void close() throws SQLException;

        abstract boolean hasNext() throws SQLException;

        abstract Object next() throws SQLException;
    }

    protected abstract class IterateBlock {
        IteratorWithCleanup iterator;

        IterateBlock(IteratorWithCleanup i) {
            this.iterator = i;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void doForAll() throws SQLException {
            try {
                while (this.iterator.hasNext()) {
                    this.forEach(this.iterator.next());
                }
            }
            finally {
                this.iterator.close();
            }
        }

        abstract void forEach(Object var1) throws SQLException;
    }
}

