/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.StringUtils;
import com.mysql.jdbc.VersionedStringProperty;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

public class CharsetMapping {
    private static final Properties CHARSET_CONFIG = new Properties();
    public static final String[] INDEX_TO_CHARSET;
    public static final String[] INDEX_TO_COLLATION;
    private static final Map JAVA_TO_MYSQL_CHARSET_MAP;
    private static final Map JAVA_UC_TO_MYSQL_CHARSET_MAP;
    private static final Map ERROR_MESSAGE_FILE_TO_MYSQL_CHARSET_MAP;
    private static final Map MULTIBYTE_CHARSETS;
    private static final Map MYSQL_TO_JAVA_CHARSET_MAP;
    private static final String NOT_USED = "ISO8859_1";
    public static final Map STATIC_CHARSET_TO_NUM_BYTES_MAP;

    public static final String getJavaEncodingForMysqlEncoding(String mysqlEncoding, Connection conn) throws SQLException {
        if (conn != null && conn.versionMeetsMinimum(4, 1, 0) && "latin1".equalsIgnoreCase(mysqlEncoding)) {
            return "Cp1252";
        }
        return (String)MYSQL_TO_JAVA_CHARSET_MAP.get(mysqlEncoding);
    }

    public static final String getMysqlEncodingForJavaEncoding(String javaEncodingUC, Connection conn) throws SQLException {
        List mysqlEncodings = (List)JAVA_UC_TO_MYSQL_CHARSET_MAP.get(javaEncodingUC);
        if (mysqlEncodings != null) {
            Iterator iter = mysqlEncodings.iterator();
            VersionedStringProperty versionedProp = null;
            while (iter.hasNext()) {
                VersionedStringProperty propToCheck = (VersionedStringProperty)iter.next();
                if (conn == null) {
                    return propToCheck.toString();
                }
                if (versionedProp != null && !versionedProp.preferredValue && versionedProp.majorVersion == propToCheck.majorVersion && versionedProp.minorVersion == propToCheck.minorVersion && versionedProp.subminorVersion == propToCheck.subminorVersion) {
                    return versionedProp.toString();
                }
                if (!propToCheck.isOkayForVersion(conn)) break;
                if (propToCheck.preferredValue) {
                    return propToCheck.toString();
                }
                versionedProp = propToCheck;
            }
            if (versionedProp != null) {
                return versionedProp.toString();
            }
        }
        return null;
    }

    static final int getNumberOfCharsetsConfigured() {
        return MYSQL_TO_JAVA_CHARSET_MAP.size() / 2;
    }

    static final String getCharacterEncodingForErrorMessages(Connection conn) throws SQLException {
        int lastSlashIndex;
        String errorMessageFile = conn.getServerVariable("language");
        if (errorMessageFile == null || errorMessageFile.length() == 0) {
            return "Cp1252";
        }
        int endWithoutSlash = errorMessageFile.length();
        if (errorMessageFile.endsWith("/") || errorMessageFile.endsWith("\\")) {
            --endWithoutSlash;
        }
        if ((lastSlashIndex = errorMessageFile.lastIndexOf(47, endWithoutSlash - 1)) == -1) {
            lastSlashIndex = errorMessageFile.lastIndexOf(92, endWithoutSlash - 1);
        }
        if (lastSlashIndex == -1) {
            lastSlashIndex = 0;
        }
        if (lastSlashIndex == endWithoutSlash || endWithoutSlash < lastSlashIndex) {
            return "Cp1252";
        }
        String errorMessageEncodingMysql = (String)ERROR_MESSAGE_FILE_TO_MYSQL_CHARSET_MAP.get(errorMessageFile = errorMessageFile.substring(lastSlashIndex + 1, endWithoutSlash));
        if (errorMessageEncodingMysql == null) {
            return "Cp1252";
        }
        String javaEncoding = CharsetMapping.getJavaEncodingForMysqlEncoding(errorMessageEncodingMysql, conn);
        if (javaEncoding == null) {
            return "Cp1252";
        }
        return javaEncoding;
    }

    static final boolean isAliasForSjis(String encoding) {
        return "SJIS".equalsIgnoreCase(encoding) || "WINDOWS-31J".equalsIgnoreCase(encoding) || "MS932".equalsIgnoreCase(encoding) || "SHIFT_JIS".equalsIgnoreCase(encoding) || "CP943".equalsIgnoreCase(encoding);
    }

    static final boolean isMultibyteCharset(String javaEncodingName) {
        String javaEncodingNameUC = javaEncodingName.toUpperCase(Locale.ENGLISH);
        return MULTIBYTE_CHARSETS.containsKey(javaEncodingNameUC);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void populateMapWithKeyValuePairs(String configKey, Map mapToPopulate, boolean addVersionedProperties, boolean addUppercaseKeys) {
        String javaToMysqlConfig = CHARSET_CONFIG.getProperty(configKey);
        if (javaToMysqlConfig == null) throw new RuntimeException("Could not find configuration value \"" + configKey + "\" in Charsets.properties resource");
        List mappings = StringUtils.split(javaToMysqlConfig, ",", true);
        if (mappings == null) throw new RuntimeException("Missing/corrupt entry for \"" + configKey + "\" in Charsets.properties.");
        Iterator mappingsIter = mappings.iterator();
        while (mappingsIter.hasNext()) {
            String aMapping = (String)mappingsIter.next();
            List parsedPair = StringUtils.split(aMapping, "=", true);
            if (parsedPair.size() != 2) throw new RuntimeException("Syntax error in Charsets.properties resource for token \"" + aMapping + "\".");
            String key = parsedPair.get(0).toString();
            String value = parsedPair.get(1).toString();
            if (addVersionedProperties) {
                List<VersionedStringProperty> versionedProperties = (ArrayList<VersionedStringProperty>)mapToPopulate.get(key);
                if (versionedProperties == null) {
                    versionedProperties = new ArrayList<VersionedStringProperty>();
                    mapToPopulate.put(key, versionedProperties);
                }
                VersionedStringProperty verProp = new VersionedStringProperty(value);
                versionedProperties.add(verProp);
                if (!addUppercaseKeys) continue;
                String keyUc = key.toUpperCase(Locale.ENGLISH);
                versionedProperties = (List)mapToPopulate.get(keyUc);
                if (versionedProperties == null) {
                    versionedProperties = new ArrayList();
                    mapToPopulate.put(keyUc, versionedProperties);
                }
                versionedProperties.add(verProp);
                continue;
            }
            mapToPopulate.put(key, value);
            if (!addUppercaseKeys) continue;
            mapToPopulate.put(key.toUpperCase(Locale.ENGLISH), value);
        }
    }

    static {
        HashMap<String, Integer> tempNumBytesMap = new HashMap<String, Integer>();
        tempNumBytesMap.put("big5", new Integer(2));
        tempNumBytesMap.put("dec8", new Integer(1));
        tempNumBytesMap.put("cp850", new Integer(1));
        tempNumBytesMap.put("hp8", new Integer(1));
        tempNumBytesMap.put("koi8r", new Integer(1));
        tempNumBytesMap.put("latin1", new Integer(1));
        tempNumBytesMap.put("latin2", new Integer(1));
        tempNumBytesMap.put("swe7", new Integer(1));
        tempNumBytesMap.put("ascii", new Integer(1));
        tempNumBytesMap.put("ujis", new Integer(3));
        tempNumBytesMap.put("sjis", new Integer(2));
        tempNumBytesMap.put("hebrew", new Integer(1));
        tempNumBytesMap.put("tis620", new Integer(1));
        tempNumBytesMap.put("euckr", new Integer(2));
        tempNumBytesMap.put("koi8u", new Integer(1));
        tempNumBytesMap.put("gb2312", new Integer(2));
        tempNumBytesMap.put("greek", new Integer(1));
        tempNumBytesMap.put("cp1250", new Integer(1));
        tempNumBytesMap.put("gbk", new Integer(2));
        tempNumBytesMap.put("latin5", new Integer(1));
        tempNumBytesMap.put("armscii8", new Integer(1));
        tempNumBytesMap.put("utf8", new Integer(3));
        tempNumBytesMap.put("ucs2", new Integer(2));
        tempNumBytesMap.put("cp866", new Integer(1));
        tempNumBytesMap.put("keybcs2", new Integer(1));
        tempNumBytesMap.put("macce", new Integer(1));
        tempNumBytesMap.put("macroman", new Integer(1));
        tempNumBytesMap.put("cp852", new Integer(1));
        tempNumBytesMap.put("latin7", new Integer(1));
        tempNumBytesMap.put("cp1251", new Integer(1));
        tempNumBytesMap.put("cp1256", new Integer(1));
        tempNumBytesMap.put("cp1257", new Integer(1));
        tempNumBytesMap.put("binary", new Integer(1));
        tempNumBytesMap.put("geostd8", new Integer(1));
        tempNumBytesMap.put("cp932", new Integer(2));
        tempNumBytesMap.put("eucjpms", new Integer(3));
        STATIC_CHARSET_TO_NUM_BYTES_MAP = Collections.unmodifiableMap(tempNumBytesMap);
        CHARSET_CONFIG.setProperty("javaToMysqlMappings", "US-ASCII =\t\t\tusa7,US-ASCII =\t\t\t>4.1.0 ascii,Big5 = \t\t\t\tbig5,GBK = \t\t\t\tgbk,SJIS = \t\t\t\tsjis,EUC_CN = \t\t\tgb2312,EUC_JP = \t\t\tujis,EUC_JP_Solaris = \t>5.0.3 eucjpms,EUC_KR = \t\t\teuc_kr,EUC_KR = \t\t\t>4.1.0 euckr,ISO8859_1 =\t\t\t*latin1,ISO8859_1 =\t\t\tlatin1_de,ISO8859_1 =\t\t\tgerman1,ISO8859_1 =\t\t\tdanish,ISO8859_2 =\t\t\tlatin2,ISO8859_2 =\t\t\tczech,ISO8859_2 =\t\t\thungarian,ISO8859_2  =\t\tcroat,ISO8859_7  =\t\tgreek,ISO8859_7  =\t\tlatin7,ISO8859_8  = \t\thebrew,ISO8859_9  =\t\tlatin5,ISO8859_13 =\t\tlatvian,ISO8859_13 =\t\tlatvian1,ISO8859_13 =\t\testonia,Cp437 =             *>4.1.0 cp850,Cp437 =\t\t\t\tdos,Cp850 =\t\t\t\tcp850,Cp852 = \t\t\tcp852,Cp866 = \t\t\tcp866,KOI8_R = \t\t\tkoi8_ru,KOI8_R = \t\t\t>4.1.0 koi8r,TIS620 = \t\t\ttis620,Cp1250 = \t\t\tcp1250,Cp1250 = \t\t\twin1250,Cp1251 = \t\t\t*>4.1.0 cp1251,Cp1251 = \t\t\twin1251,Cp1251 = \t\t\tcp1251cias,Cp1251 = \t\t\tcp1251csas,Cp1256 = \t\t\tcp1256,Cp1251 = \t\t\twin1251ukr,Cp1252 =             latin1,Cp1257 = \t\t\tcp1257,MacRoman = \t\t\tmacroman,MacCentralEurope = \tmacce,UTF-8 = \t\tutf8,UnicodeBig = \tucs2,US-ASCII =\t\tbinary,Cp943 =        \tsjis,MS932 =\t\t\tsjis,MS932 =        \t>4.1.11 cp932,WINDOWS-31J =\tsjis,WINDOWS-31J = \t>4.1.11 cp932,CP932 =\t\t\tsjis,CP932 =\t\t\t*>4.1.11 cp932,SHIFT_JIS = \tsjis,ASCII =\t\t\tascii,LATIN5 =\t\tlatin5,LATIN7 =\t\tlatin7,HEBREW =\t\thebrew,GREEK =\t\t\tgreek,EUCKR =\t\t\teuckr,GB2312 =\t\tgb2312,LATIN2 =\t\tlatin2");
        HashMap javaToMysqlMap = new HashMap();
        CharsetMapping.populateMapWithKeyValuePairs("javaToMysqlMappings", javaToMysqlMap, true, false);
        JAVA_TO_MYSQL_CHARSET_MAP = Collections.unmodifiableMap(javaToMysqlMap);
        HashMap mysqlToJavaMap = new HashMap();
        Set keySet = JAVA_TO_MYSQL_CHARSET_MAP.keySet();
        Iterator javaCharsets = keySet.iterator();
        while (javaCharsets.hasNext()) {
            Object javaEncodingName = javaCharsets.next();
            List mysqlEncodingList = (List)JAVA_TO_MYSQL_CHARSET_MAP.get(javaEncodingName);
            Iterator mysqlEncodings = mysqlEncodingList.iterator();
            String mysqlEncodingName = null;
            while (mysqlEncodings.hasNext()) {
                VersionedStringProperty mysqlProp = (VersionedStringProperty)mysqlEncodings.next();
                mysqlEncodingName = mysqlProp.toString();
                mysqlToJavaMap.put(mysqlEncodingName, javaEncodingName);
                mysqlToJavaMap.put(mysqlEncodingName.toUpperCase(Locale.ENGLISH), javaEncodingName);
            }
        }
        mysqlToJavaMap.put("cp932", "Windows-31J");
        mysqlToJavaMap.put("CP932", "Windows-31J");
        MYSQL_TO_JAVA_CHARSET_MAP = Collections.unmodifiableMap(mysqlToJavaMap);
        TreeMap ucMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        Iterator javaNamesKeys = JAVA_TO_MYSQL_CHARSET_MAP.keySet().iterator();
        while (javaNamesKeys.hasNext()) {
            String key = (String)javaNamesKeys.next();
            ucMap.put(key.toUpperCase(Locale.ENGLISH), JAVA_TO_MYSQL_CHARSET_MAP.get(key));
        }
        JAVA_UC_TO_MYSQL_CHARSET_MAP = Collections.unmodifiableMap(ucMap);
        HashMap tempMapMulti = new HashMap();
        CHARSET_CONFIG.setProperty("multibyteCharsets", "Big5 = \t\t\tbig5,GBK = \t\t\tgbk,SJIS = \t\t\tsjis,EUC_CN = \t\tgb2312,EUC_JP = \t\tujis,EUC_JP_Solaris = eucjpms,EUC_KR = \t\teuc_kr,EUC_KR = \t\t>4.1.0 euckr,Cp943 =        \tsjis,Cp943 = \t\tcp943,WINDOWS-31J =\tsjis,WINDOWS-31J = \tcp932,CP932 =\t\t\tcp932,MS932 =\t\t\tsjis,MS932 =        \tcp932,SHIFT_JIS = \tsjis,EUCKR =\t\t\teuckr,GB2312 =\t\tgb2312,UTF-8 = \t\tutf8,utf8 =          utf8,UnicodeBig = \tucs2");
        CharsetMapping.populateMapWithKeyValuePairs("multibyteCharsets", tempMapMulti, false, true);
        MULTIBYTE_CHARSETS = Collections.unmodifiableMap(tempMapMulti);
        INDEX_TO_CHARSET = new String[211];
        try {
            int i;
            CharsetMapping.INDEX_TO_CHARSET[1] = CharsetMapping.getJavaEncodingForMysqlEncoding("big5", null);
            CharsetMapping.INDEX_TO_CHARSET[2] = CharsetMapping.getJavaEncodingForMysqlEncoding("czech", null);
            CharsetMapping.INDEX_TO_CHARSET[3] = NOT_USED;
            CharsetMapping.INDEX_TO_CHARSET[4] = NOT_USED;
            CharsetMapping.INDEX_TO_CHARSET[5] = CharsetMapping.getJavaEncodingForMysqlEncoding("german1", null);
            CharsetMapping.INDEX_TO_CHARSET[6] = NOT_USED;
            CharsetMapping.INDEX_TO_CHARSET[7] = CharsetMapping.getJavaEncodingForMysqlEncoding("koi8_ru", null);
            CharsetMapping.INDEX_TO_CHARSET[8] = CharsetMapping.getJavaEncodingForMysqlEncoding("latin1", null);
            CharsetMapping.INDEX_TO_CHARSET[9] = CharsetMapping.getJavaEncodingForMysqlEncoding("latin2", null);
            CharsetMapping.INDEX_TO_CHARSET[10] = NOT_USED;
            CharsetMapping.INDEX_TO_CHARSET[11] = CharsetMapping.getJavaEncodingForMysqlEncoding("usa7", null);
            CharsetMapping.INDEX_TO_CHARSET[12] = CharsetMapping.getJavaEncodingForMysqlEncoding("ujis", null);
            CharsetMapping.INDEX_TO_CHARSET[13] = CharsetMapping.getJavaEncodingForMysqlEncoding("sjis", null);
            CharsetMapping.INDEX_TO_CHARSET[14] = CharsetMapping.getJavaEncodingForMysqlEncoding("cp1251", null);
            CharsetMapping.INDEX_TO_CHARSET[15] = CharsetMapping.getJavaEncodingForMysqlEncoding("danish", null);
            CharsetMapping.INDEX_TO_CHARSET[16] = CharsetMapping.getJavaEncodingForMysqlEncoding("hebrew", null);
            CharsetMapping.INDEX_TO_CHARSET[17] = NOT_USED;
            CharsetMapping.INDEX_TO_CHARSET[18] = CharsetMapping.getJavaEncodingForMysqlEncoding("tis620", null);
            CharsetMapping.INDEX_TO_CHARSET[19] = CharsetMapping.getJavaEncodingForMysqlEncoding("euc_kr", null);
            CharsetMapping.INDEX_TO_CHARSET[20] = CharsetMapping.getJavaEncodingForMysqlEncoding("estonia", null);
            CharsetMapping.INDEX_TO_CHARSET[21] = CharsetMapping.getJavaEncodingForMysqlEncoding("hungarian", null);
            CharsetMapping.INDEX_TO_CHARSET[22] = "KOI8_R";
            CharsetMapping.INDEX_TO_CHARSET[23] = CharsetMapping.getJavaEncodingForMysqlEncoding("win1251ukr", null);
            CharsetMapping.INDEX_TO_CHARSET[24] = CharsetMapping.getJavaEncodingForMysqlEncoding("gb2312", null);
            CharsetMapping.INDEX_TO_CHARSET[25] = CharsetMapping.getJavaEncodingForMysqlEncoding("greek", null);
            CharsetMapping.INDEX_TO_CHARSET[26] = CharsetMapping.getJavaEncodingForMysqlEncoding("win1250", null);
            CharsetMapping.INDEX_TO_CHARSET[27] = CharsetMapping.getJavaEncodingForMysqlEncoding("croat", null);
            CharsetMapping.INDEX_TO_CHARSET[28] = CharsetMapping.getJavaEncodingForMysqlEncoding("gbk", null);
            CharsetMapping.INDEX_TO_CHARSET[29] = CharsetMapping.getJavaEncodingForMysqlEncoding("cp1257", null);
            CharsetMapping.INDEX_TO_CHARSET[30] = CharsetMapping.getJavaEncodingForMysqlEncoding("latin5", null);
            CharsetMapping.INDEX_TO_CHARSET[31] = CharsetMapping.getJavaEncodingForMysqlEncoding("latin1_de", null);
            CharsetMapping.INDEX_TO_CHARSET[32] = NOT_USED;
            CharsetMapping.INDEX_TO_CHARSET[33] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[34] = "Cp1250";
            CharsetMapping.INDEX_TO_CHARSET[35] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[36] = CharsetMapping.getJavaEncodingForMysqlEncoding("cp866", null);
            CharsetMapping.INDEX_TO_CHARSET[37] = "Cp895";
            CharsetMapping.INDEX_TO_CHARSET[38] = CharsetMapping.getJavaEncodingForMysqlEncoding("macce", null);
            CharsetMapping.INDEX_TO_CHARSET[39] = CharsetMapping.getJavaEncodingForMysqlEncoding("macroman", null);
            CharsetMapping.INDEX_TO_CHARSET[40] = "latin2";
            CharsetMapping.INDEX_TO_CHARSET[41] = CharsetMapping.getJavaEncodingForMysqlEncoding("latvian", null);
            CharsetMapping.INDEX_TO_CHARSET[42] = CharsetMapping.getJavaEncodingForMysqlEncoding("latvian1", null);
            CharsetMapping.INDEX_TO_CHARSET[43] = CharsetMapping.getJavaEncodingForMysqlEncoding("macce", null);
            CharsetMapping.INDEX_TO_CHARSET[44] = CharsetMapping.getJavaEncodingForMysqlEncoding("macce", null);
            CharsetMapping.INDEX_TO_CHARSET[45] = CharsetMapping.getJavaEncodingForMysqlEncoding("macce", null);
            CharsetMapping.INDEX_TO_CHARSET[46] = CharsetMapping.getJavaEncodingForMysqlEncoding("macce", null);
            CharsetMapping.INDEX_TO_CHARSET[47] = CharsetMapping.getJavaEncodingForMysqlEncoding("latin1", null);
            CharsetMapping.INDEX_TO_CHARSET[48] = CharsetMapping.getJavaEncodingForMysqlEncoding("latin1", null);
            CharsetMapping.INDEX_TO_CHARSET[49] = CharsetMapping.getJavaEncodingForMysqlEncoding("latin1", null);
            CharsetMapping.INDEX_TO_CHARSET[50] = CharsetMapping.getJavaEncodingForMysqlEncoding("cp1251", null);
            CharsetMapping.INDEX_TO_CHARSET[51] = CharsetMapping.getJavaEncodingForMysqlEncoding("cp1251", null);
            CharsetMapping.INDEX_TO_CHARSET[52] = CharsetMapping.getJavaEncodingForMysqlEncoding("cp1251", null);
            CharsetMapping.INDEX_TO_CHARSET[53] = CharsetMapping.getJavaEncodingForMysqlEncoding("macroman", null);
            CharsetMapping.INDEX_TO_CHARSET[54] = CharsetMapping.getJavaEncodingForMysqlEncoding("macroman", null);
            CharsetMapping.INDEX_TO_CHARSET[55] = CharsetMapping.getJavaEncodingForMysqlEncoding("macroman", null);
            CharsetMapping.INDEX_TO_CHARSET[56] = CharsetMapping.getJavaEncodingForMysqlEncoding("macroman", null);
            CharsetMapping.INDEX_TO_CHARSET[57] = CharsetMapping.getJavaEncodingForMysqlEncoding("cp1256", null);
            CharsetMapping.INDEX_TO_CHARSET[58] = NOT_USED;
            CharsetMapping.INDEX_TO_CHARSET[59] = NOT_USED;
            CharsetMapping.INDEX_TO_CHARSET[60] = NOT_USED;
            CharsetMapping.INDEX_TO_CHARSET[61] = NOT_USED;
            CharsetMapping.INDEX_TO_CHARSET[62] = NOT_USED;
            CharsetMapping.INDEX_TO_CHARSET[63] = CharsetMapping.getJavaEncodingForMysqlEncoding("binary", null);
            CharsetMapping.INDEX_TO_CHARSET[64] = "ISO8859_2";
            CharsetMapping.INDEX_TO_CHARSET[65] = CharsetMapping.getJavaEncodingForMysqlEncoding("ascii", null);
            CharsetMapping.INDEX_TO_CHARSET[66] = CharsetMapping.getJavaEncodingForMysqlEncoding("cp1250", null);
            CharsetMapping.INDEX_TO_CHARSET[67] = CharsetMapping.getJavaEncodingForMysqlEncoding("cp1256", null);
            CharsetMapping.INDEX_TO_CHARSET[68] = CharsetMapping.getJavaEncodingForMysqlEncoding("cp866", null);
            CharsetMapping.INDEX_TO_CHARSET[69] = "US-ASCII";
            CharsetMapping.INDEX_TO_CHARSET[70] = CharsetMapping.getJavaEncodingForMysqlEncoding("greek", null);
            CharsetMapping.INDEX_TO_CHARSET[71] = CharsetMapping.getJavaEncodingForMysqlEncoding("hebrew", null);
            CharsetMapping.INDEX_TO_CHARSET[72] = "US-ASCII";
            CharsetMapping.INDEX_TO_CHARSET[73] = "Cp895";
            CharsetMapping.INDEX_TO_CHARSET[74] = CharsetMapping.getJavaEncodingForMysqlEncoding("koi8r", null);
            CharsetMapping.INDEX_TO_CHARSET[75] = "KOI8_r";
            CharsetMapping.INDEX_TO_CHARSET[76] = NOT_USED;
            CharsetMapping.INDEX_TO_CHARSET[77] = CharsetMapping.getJavaEncodingForMysqlEncoding("latin2", null);
            CharsetMapping.INDEX_TO_CHARSET[78] = CharsetMapping.getJavaEncodingForMysqlEncoding("latin5", null);
            CharsetMapping.INDEX_TO_CHARSET[79] = CharsetMapping.getJavaEncodingForMysqlEncoding("latin7", null);
            CharsetMapping.INDEX_TO_CHARSET[80] = CharsetMapping.getJavaEncodingForMysqlEncoding("cp850", null);
            CharsetMapping.INDEX_TO_CHARSET[81] = CharsetMapping.getJavaEncodingForMysqlEncoding("cp852", null);
            CharsetMapping.INDEX_TO_CHARSET[82] = NOT_USED;
            CharsetMapping.INDEX_TO_CHARSET[83] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[84] = CharsetMapping.getJavaEncodingForMysqlEncoding("big5", null);
            CharsetMapping.INDEX_TO_CHARSET[85] = CharsetMapping.getJavaEncodingForMysqlEncoding("euckr", null);
            CharsetMapping.INDEX_TO_CHARSET[86] = CharsetMapping.getJavaEncodingForMysqlEncoding("gb2312", null);
            CharsetMapping.INDEX_TO_CHARSET[87] = CharsetMapping.getJavaEncodingForMysqlEncoding("gbk", null);
            CharsetMapping.INDEX_TO_CHARSET[88] = CharsetMapping.getJavaEncodingForMysqlEncoding("sjis", null);
            CharsetMapping.INDEX_TO_CHARSET[89] = CharsetMapping.getJavaEncodingForMysqlEncoding("tis620", null);
            CharsetMapping.INDEX_TO_CHARSET[90] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[91] = CharsetMapping.getJavaEncodingForMysqlEncoding("ujis", null);
            CharsetMapping.INDEX_TO_CHARSET[92] = "US-ASCII";
            CharsetMapping.INDEX_TO_CHARSET[93] = "US-ASCII";
            CharsetMapping.INDEX_TO_CHARSET[94] = CharsetMapping.getJavaEncodingForMysqlEncoding("latin1", null);
            CharsetMapping.INDEX_TO_CHARSET[95] = CharsetMapping.getJavaEncodingForMysqlEncoding("cp932", null);
            CharsetMapping.INDEX_TO_CHARSET[96] = CharsetMapping.getJavaEncodingForMysqlEncoding("cp932", null);
            CharsetMapping.INDEX_TO_CHARSET[97] = CharsetMapping.getJavaEncodingForMysqlEncoding("eucjpms", null);
            CharsetMapping.INDEX_TO_CHARSET[98] = CharsetMapping.getJavaEncodingForMysqlEncoding("eucjpms", null);
            for (i = 99; i < 128; ++i) {
                CharsetMapping.INDEX_TO_CHARSET[i] = NOT_USED;
            }
            CharsetMapping.INDEX_TO_CHARSET[128] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[129] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[130] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[131] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[132] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[133] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[134] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[135] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[136] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[137] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[138] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[139] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[140] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[141] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[142] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[143] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[144] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[145] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[146] = CharsetMapping.getJavaEncodingForMysqlEncoding("ucs2", null);
            for (i = 147; i < 192; ++i) {
                CharsetMapping.INDEX_TO_CHARSET[i] = NOT_USED;
            }
            CharsetMapping.INDEX_TO_CHARSET[192] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[193] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[194] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[195] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[196] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[197] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[198] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[199] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[200] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[201] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[202] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[203] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[204] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[205] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[206] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[207] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[208] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[209] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[210] = CharsetMapping.getJavaEncodingForMysqlEncoding("utf8", null);
            for (i = 1; i < INDEX_TO_CHARSET.length; ++i) {
                if (INDEX_TO_CHARSET[i] != null) continue;
                throw new RuntimeException("Assertion failure: No mapping from charset index " + i + " to a Java character set");
            }
        }
        catch (SQLException sqlEx) {
            // empty catch block
        }
        INDEX_TO_COLLATION = new String[211];
        CharsetMapping.INDEX_TO_COLLATION[1] = "big5_chinese_ci";
        CharsetMapping.INDEX_TO_COLLATION[2] = "latin2_czech_cs";
        CharsetMapping.INDEX_TO_COLLATION[3] = "dec8_swedish_ci";
        CharsetMapping.INDEX_TO_COLLATION[4] = "cp850_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[5] = "latin1_german1_ci";
        CharsetMapping.INDEX_TO_COLLATION[6] = "hp8_english_ci";
        CharsetMapping.INDEX_TO_COLLATION[7] = "koi8r_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[8] = "latin1_swedish_ci";
        CharsetMapping.INDEX_TO_COLLATION[9] = "latin2_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[10] = "swe7_swedish_ci";
        CharsetMapping.INDEX_TO_COLLATION[11] = "ascii_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[12] = "ujis_japanese_ci";
        CharsetMapping.INDEX_TO_COLLATION[13] = "sjis_japanese_ci";
        CharsetMapping.INDEX_TO_COLLATION[14] = "cp1251_bulgarian_ci";
        CharsetMapping.INDEX_TO_COLLATION[15] = "latin1_danish_ci";
        CharsetMapping.INDEX_TO_COLLATION[16] = "hebrew_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[18] = "tis620_thai_ci";
        CharsetMapping.INDEX_TO_COLLATION[19] = "euckr_korean_ci";
        CharsetMapping.INDEX_TO_COLLATION[20] = "latin7_estonian_cs";
        CharsetMapping.INDEX_TO_COLLATION[21] = "latin2_hungarian_ci";
        CharsetMapping.INDEX_TO_COLLATION[22] = "koi8u_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[23] = "cp1251_ukrainian_ci";
        CharsetMapping.INDEX_TO_COLLATION[24] = "gb2312_chinese_ci";
        CharsetMapping.INDEX_TO_COLLATION[25] = "greek_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[26] = "cp1250_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[27] = "latin2_croatian_ci";
        CharsetMapping.INDEX_TO_COLLATION[28] = "gbk_chinese_ci";
        CharsetMapping.INDEX_TO_COLLATION[29] = "cp1257_lithuanian_ci";
        CharsetMapping.INDEX_TO_COLLATION[30] = "latin5_turkish_ci";
        CharsetMapping.INDEX_TO_COLLATION[31] = "latin1_german2_ci";
        CharsetMapping.INDEX_TO_COLLATION[32] = "armscii8_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[33] = "utf8_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[34] = "cp1250_czech_cs";
        CharsetMapping.INDEX_TO_COLLATION[35] = "ucs2_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[36] = "cp866_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[37] = "keybcs2_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[38] = "macce_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[39] = "macroman_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[40] = "cp852_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[41] = "latin7_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[42] = "latin7_general_cs";
        CharsetMapping.INDEX_TO_COLLATION[43] = "macce_bin";
        CharsetMapping.INDEX_TO_COLLATION[44] = "cp1250_croatian_ci";
        CharsetMapping.INDEX_TO_COLLATION[47] = "latin1_bin";
        CharsetMapping.INDEX_TO_COLLATION[48] = "latin1_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[49] = "latin1_general_cs";
        CharsetMapping.INDEX_TO_COLLATION[50] = "cp1251_bin";
        CharsetMapping.INDEX_TO_COLLATION[51] = "cp1251_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[52] = "cp1251_general_cs";
        CharsetMapping.INDEX_TO_COLLATION[53] = "macroman_bin";
        CharsetMapping.INDEX_TO_COLLATION[57] = "cp1256_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[58] = "cp1257_bin";
        CharsetMapping.INDEX_TO_COLLATION[59] = "cp1257_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[63] = "binary";
        CharsetMapping.INDEX_TO_COLLATION[64] = "armscii8_bin";
        CharsetMapping.INDEX_TO_COLLATION[65] = "ascii_bin";
        CharsetMapping.INDEX_TO_COLLATION[66] = "cp1250_bin";
        CharsetMapping.INDEX_TO_COLLATION[67] = "cp1256_bin";
        CharsetMapping.INDEX_TO_COLLATION[68] = "cp866_bin";
        CharsetMapping.INDEX_TO_COLLATION[69] = "dec8_bin";
        CharsetMapping.INDEX_TO_COLLATION[70] = "greek_bin";
        CharsetMapping.INDEX_TO_COLLATION[71] = "hebrew_bin";
        CharsetMapping.INDEX_TO_COLLATION[72] = "hp8_bin";
        CharsetMapping.INDEX_TO_COLLATION[73] = "keybcs2_bin";
        CharsetMapping.INDEX_TO_COLLATION[74] = "koi8r_bin";
        CharsetMapping.INDEX_TO_COLLATION[75] = "koi8u_bin";
        CharsetMapping.INDEX_TO_COLLATION[77] = "latin2_bin";
        CharsetMapping.INDEX_TO_COLLATION[78] = "latin5_bin";
        CharsetMapping.INDEX_TO_COLLATION[79] = "latin7_bin";
        CharsetMapping.INDEX_TO_COLLATION[80] = "cp850_bin";
        CharsetMapping.INDEX_TO_COLLATION[81] = "cp852_bin";
        CharsetMapping.INDEX_TO_COLLATION[82] = "swe7_bin";
        CharsetMapping.INDEX_TO_COLLATION[83] = "utf8_bin";
        CharsetMapping.INDEX_TO_COLLATION[84] = "big5_bin";
        CharsetMapping.INDEX_TO_COLLATION[85] = "euckr_bin";
        CharsetMapping.INDEX_TO_COLLATION[86] = "gb2312_bin";
        CharsetMapping.INDEX_TO_COLLATION[87] = "gbk_bin";
        CharsetMapping.INDEX_TO_COLLATION[88] = "sjis_bin";
        CharsetMapping.INDEX_TO_COLLATION[89] = "tis620_bin";
        CharsetMapping.INDEX_TO_COLLATION[90] = "ucs2_bin";
        CharsetMapping.INDEX_TO_COLLATION[91] = "ujis_bin";
        CharsetMapping.INDEX_TO_COLLATION[92] = "geostd8_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[93] = "geostd8_bin";
        CharsetMapping.INDEX_TO_COLLATION[94] = "latin1_spanish_ci";
        CharsetMapping.INDEX_TO_COLLATION[95] = "cp932_japanese_ci";
        CharsetMapping.INDEX_TO_COLLATION[96] = "cp932_bin";
        CharsetMapping.INDEX_TO_COLLATION[97] = "eucjpms_japanese_ci";
        CharsetMapping.INDEX_TO_COLLATION[98] = "eucjpms_bin";
        CharsetMapping.INDEX_TO_COLLATION[99] = "cp1250_polish_ci";
        CharsetMapping.INDEX_TO_COLLATION[128] = "ucs2_unicode_ci";
        CharsetMapping.INDEX_TO_COLLATION[129] = "ucs2_icelandic_ci";
        CharsetMapping.INDEX_TO_COLLATION[130] = "ucs2_latvian_ci";
        CharsetMapping.INDEX_TO_COLLATION[131] = "ucs2_romanian_ci";
        CharsetMapping.INDEX_TO_COLLATION[132] = "ucs2_slovenian_ci";
        CharsetMapping.INDEX_TO_COLLATION[133] = "ucs2_polish_ci";
        CharsetMapping.INDEX_TO_COLLATION[134] = "ucs2_estonian_ci";
        CharsetMapping.INDEX_TO_COLLATION[135] = "ucs2_spanish_ci";
        CharsetMapping.INDEX_TO_COLLATION[136] = "ucs2_swedish_ci";
        CharsetMapping.INDEX_TO_COLLATION[137] = "ucs2_turkish_ci";
        CharsetMapping.INDEX_TO_COLLATION[138] = "ucs2_czech_ci";
        CharsetMapping.INDEX_TO_COLLATION[139] = "ucs2_danish_ci";
        CharsetMapping.INDEX_TO_COLLATION[140] = "ucs2_lithuanian_ci ";
        CharsetMapping.INDEX_TO_COLLATION[141] = "ucs2_slovak_ci";
        CharsetMapping.INDEX_TO_COLLATION[142] = "ucs2_spanish2_ci";
        CharsetMapping.INDEX_TO_COLLATION[143] = "ucs2_roman_ci";
        CharsetMapping.INDEX_TO_COLLATION[144] = "ucs2_persian_ci";
        CharsetMapping.INDEX_TO_COLLATION[145] = "ucs2_esperanto_ci";
        CharsetMapping.INDEX_TO_COLLATION[146] = "ucs2_hungarian_ci";
        CharsetMapping.INDEX_TO_COLLATION[192] = "utf8_unicode_ci";
        CharsetMapping.INDEX_TO_COLLATION[193] = "utf8_icelandic_ci";
        CharsetMapping.INDEX_TO_COLLATION[194] = "utf8_latvian_ci";
        CharsetMapping.INDEX_TO_COLLATION[195] = "utf8_romanian_ci";
        CharsetMapping.INDEX_TO_COLLATION[196] = "utf8_slovenian_ci";
        CharsetMapping.INDEX_TO_COLLATION[197] = "utf8_polish_ci";
        CharsetMapping.INDEX_TO_COLLATION[198] = "utf8_estonian_ci";
        CharsetMapping.INDEX_TO_COLLATION[199] = "utf8_spanish_ci";
        CharsetMapping.INDEX_TO_COLLATION[200] = "utf8_swedish_ci";
        CharsetMapping.INDEX_TO_COLLATION[201] = "utf8_turkish_ci";
        CharsetMapping.INDEX_TO_COLLATION[202] = "utf8_czech_ci";
        CharsetMapping.INDEX_TO_COLLATION[203] = "utf8_danish_ci";
        CharsetMapping.INDEX_TO_COLLATION[204] = "utf8_lithuanian_ci ";
        CharsetMapping.INDEX_TO_COLLATION[205] = "utf8_slovak_ci";
        CharsetMapping.INDEX_TO_COLLATION[206] = "utf8_spanish2_ci";
        CharsetMapping.INDEX_TO_COLLATION[207] = "utf8_roman_ci";
        CharsetMapping.INDEX_TO_COLLATION[208] = "utf8_persian_ci";
        CharsetMapping.INDEX_TO_COLLATION[209] = "utf8_esperanto_ci";
        CharsetMapping.INDEX_TO_COLLATION[210] = "utf8_hungarian_ci";
        HashMap<String, String> tempMap = new HashMap<String, String>();
        tempMap.put("czech", "latin2");
        tempMap.put("danish", "latin1");
        tempMap.put("dutch", "latin1");
        tempMap.put("english", "latin1");
        tempMap.put("estonian", "latin7");
        tempMap.put("french", "latin1");
        tempMap.put("german", "latin1");
        tempMap.put("greek", "greek");
        tempMap.put("hungarian", "latin2");
        tempMap.put("italian", "latin1");
        tempMap.put("japanese", "ujis");
        tempMap.put("japanese-sjis", "sjis");
        tempMap.put("korean", "euckr");
        tempMap.put("norwegian", "latin1");
        tempMap.put("norwegian-ny", "latin1");
        tempMap.put("polish", "latin2");
        tempMap.put("portuguese", "latin1");
        tempMap.put("romanian", "latin2");
        tempMap.put("russian", "koi8r");
        tempMap.put("serbian", "cp1250");
        tempMap.put("slovak", "latin2");
        tempMap.put("spanish", "latin1");
        tempMap.put("swedish", "latin1");
        tempMap.put("ukrainian", "koi8u");
        ERROR_MESSAGE_FILE_TO_MYSQL_CHARSET_MAP = Collections.unmodifiableMap(tempMap);
    }
}

