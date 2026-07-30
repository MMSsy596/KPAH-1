/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.StringUtils;
import java.sql.SQLException;
import java.util.List;

class VersionedStringProperty {
    int majorVersion;
    int minorVersion;
    int subminorVersion;
    boolean preferredValue = false;
    String propertyInfo;

    VersionedStringProperty(String property) {
        property = property.trim();
        if (property.startsWith("*")) {
            property = property.substring(1);
            this.preferredValue = true;
        }
        if (property.startsWith(">")) {
            char c;
            property = property.substring(1);
            int charPos = 0;
            for (charPos = 0; charPos < property.length() && (Character.isWhitespace(c = property.charAt(charPos)) || Character.isDigit(c) || c == '.'); ++charPos) {
            }
            String versionInfo = property.substring(0, charPos);
            List versionParts = StringUtils.split(versionInfo, ".", true);
            this.majorVersion = Integer.parseInt(versionParts.get(0).toString());
            this.minorVersion = versionParts.size() > 1 ? Integer.parseInt(versionParts.get(1).toString()) : 0;
            this.subminorVersion = versionParts.size() > 2 ? Integer.parseInt(versionParts.get(2).toString()) : 0;
            this.propertyInfo = property.substring(charPos);
        } else {
            this.subminorVersion = 0;
            this.minorVersion = 0;
            this.majorVersion = 0;
            this.propertyInfo = property;
        }
    }

    VersionedStringProperty(String property, int major, int minor, int subminor) {
        this.propertyInfo = property;
        this.majorVersion = major;
        this.minorVersion = minor;
        this.subminorVersion = subminor;
    }

    boolean isOkayForVersion(Connection conn) throws SQLException {
        return conn.versionMeetsMinimum(this.majorVersion, this.minorVersion, this.subminorVersion);
    }

    public String toString() {
        return this.propertyInfo;
    }
}

