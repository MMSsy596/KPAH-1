/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc.util;

import com.mysql.jdbc.ConnectionProperties;
import java.sql.SQLException;

public class PropertiesDocGenerator
extends ConnectionProperties {
    public static void main(String[] args) throws SQLException {
        System.out.println(new PropertiesDocGenerator().exposeAsXml());
    }
}

