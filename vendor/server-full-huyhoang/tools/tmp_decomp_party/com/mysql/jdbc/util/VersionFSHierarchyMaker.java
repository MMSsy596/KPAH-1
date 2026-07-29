/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc.util;

import com.mysql.jdbc.NonRegisteringDriver;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;

public class VersionFSHierarchyMaker {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            VersionFSHierarchyMaker.usage();
            System.exit(1);
        }
        String jdbcUrl = null;
        String jvmVersion = VersionFSHierarchyMaker.removeWhitespaceChars(System.getProperty("java.version"));
        String jvmVendor = VersionFSHierarchyMaker.removeWhitespaceChars(System.getProperty("java.vendor"));
        String osName = VersionFSHierarchyMaker.removeWhitespaceChars(System.getProperty("os.name"));
        String osArch = VersionFSHierarchyMaker.removeWhitespaceChars(System.getProperty("os.arch"));
        String osVersion = VersionFSHierarchyMaker.removeWhitespaceChars(System.getProperty("os.version"));
        jdbcUrl = System.getProperty("com.mysql.jdbc.testsuite.url");
        String mysqlVersion = "not-available";
        try {
            Connection conn = new NonRegisteringDriver().connect(jdbcUrl, null);
            ResultSet rs = conn.createStatement().executeQuery("SELECT VERSION()");
            rs.next();
            mysqlVersion = VersionFSHierarchyMaker.removeWhitespaceChars(rs.getString(1));
        }
        catch (Throwable t) {
            mysqlVersion = "no-server-running-on-" + VersionFSHierarchyMaker.removeWhitespaceChars(jdbcUrl);
        }
        String jvmSubdirName = jvmVendor + "-" + jvmVersion;
        String osSubdirName = osName + "-" + osArch + "-" + osVersion;
        File baseDir = new File(args[1]);
        File mysqlVersionDir = new File(baseDir, mysqlVersion);
        File osVersionDir = new File(mysqlVersionDir, osSubdirName);
        File jvmVersionDir = new File(osVersionDir, jvmSubdirName);
        jvmVersionDir.mkdirs();
        FileOutputStream pathOut = null;
        try {
            String propsOutputPath = args[2];
            pathOut = new FileOutputStream(propsOutputPath);
            String baseDirStr = baseDir.getAbsolutePath();
            String jvmVersionDirStr = jvmVersionDir.getAbsolutePath();
            if (jvmVersionDirStr.startsWith(baseDirStr)) {
                jvmVersionDirStr = jvmVersionDirStr.substring(baseDirStr.length() + 1);
            }
            pathOut.write(jvmVersionDirStr.getBytes());
            Object var19_19 = null;
            if (pathOut == null) return;
        }
        catch (Throwable throwable) {
            Object var19_20 = null;
            if (pathOut == null) throw throwable;
            pathOut.flush();
            pathOut.close();
            throw throwable;
        }
        pathOut.flush();
        pathOut.close();
    }

    public static String removeWhitespaceChars(String input) {
        if (input == null) {
            return input;
        }
        int strLen = input.length();
        StringBuffer output = new StringBuffer(strLen);
        for (int i = 0; i < strLen; ++i) {
            char c = input.charAt(i);
            if (!Character.isDigit(c) && !Character.isLetter(c)) {
                if (Character.isWhitespace(c)) {
                    output.append("_");
                    continue;
                }
                output.append(".");
                continue;
            }
            output.append(c);
        }
        return output.toString();
    }

    private static void usage() {
        System.err.println("Creates a fs hierarchy representing MySQL version, OS version and JVM version.");
        System.err.println("Stores the full path as 'outputDirectory' property in file 'directoryPropPath'");
        System.err.println();
        System.err.println("Usage: java VersionFSHierarchyMaker unit|compliance baseDirectory directoryPropPath");
    }
}

